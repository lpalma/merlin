#!/bin/bash
set -e

function usage() {
    echo USAGE: $0 application-name zip-file [environment] [solution-stack]>&2
    echo $@
    exit 1
}

function buildDeployable() {
                ./node_modules/.bin/webpack -d
		gradle clean fatJar
		cd scripts
		zip -j ../"$1" Dockerfile ../build/libs/"$2"-standalone.jar
		#zip -ur ../"$1" .ebextensions
		cd ..
}

function getEnvDescriptor() {
    aws elasticbeanstalk describe-environments \
        --application-name "$1" \
        --environment-name "$2" | \
        jq -r '.Environments | .[]?'
}

function getRoleDescriptor() {
    aws iam list-roles | \
        jq -r '.Roles[] | select(.RoleName == "'"$1"'")'
}



function getEnvironmentStatus() {
    local APPNAME="$1"
    local ENV="$2"
    ENV_DESCRIPTOR=$(getEnvDescriptor "$APPNAME" "$ENV")
    ENV_STATUS=$(echo "$ENV_DESCRIPTOR" | jq -r '.Status')
}


function createApplicationIfNotExists() {
		local NAME="$1"
		APP_DESCRIPTOR=$(aws elasticbeanstalk describe-applications \
				--application-name "$NAME"  | \
				jq -r '.Applications | .[]?')
		if [ -z "$APP_DESCRIPTOR" ] ; then
				echo INFO: creating application "$NAME"
				APP_DESCRIPTOR=$(aws elasticbeanstalk create-application \
				--application-name "$NAME" )
		else
				echo INFO: application "$NAME" already exists
		fi
}

function createAppEnvironmentIfNotExists() {
		local NAME="$1"
  	local INSTANCE_PROFILE="${NAME}_instance_profile"
		local ENVNAME="$2"
		local STACK="$3"
		createApplicationIfNotExists "$NAME"
		ENV_DESCRIPTOR=$(getEnvDescriptor "$NAME" "$ENVNAME")
		if [ -z "$ENV_DESCRIPTOR" ] ; then
				echo INFO: Creating environment "$ENVNAME" for "$NAME"
				ENV_DESCRIPTOR=$(aws elasticbeanstalk create-environment \
				--application-name "$NAME" \
				--solution-stack "$STACK" \
				--environment-name "$ENVNAME" \
				--option-settings '[{"Namespace":"aws:autoscaling:launchconfiguration",
"OptionName":"IamInstanceProfile","Value":"'"$INSTANCE_PROFILE"'"}]')
		else
				echo INFO: environment "$ENVNAME" for "$NAME" already exists
		fi
}

function createAppRoleIfNotExists() {
		local NAME="$1"
  	local ROLE="${NAME}_exec_role"
  	local INSTANCE_PROFILE="${NAME}_instance_profile"
  	local POLICY="${NAME}_elasticbeanstalk.json"
  	local TRUST="trust_policy_elasticbeanstalk.json"

		ROLE_DESCRIPTOR=$(getRoleDescriptor "$ROLE")
		if [ -z "$ROLE_DESCRIPTOR" ] ; then
				echo INFO: Creating role $ROLE 
  			aws iam create-role --role-name $ROLE --assume-role-policy-document file://scripts/iam/$TRUST
				aws iam create-instance-profile --instance-profile-name $INSTANCE_PROFILE
				aws iam add-role-to-instance-profile --role-name $ROLE --instance-profile-name $INSTANCE_PROFILE
		  	aws iam put-role-policy --role-name $ROLE --policy-name $ROLE --policy-document file://scripts/iam/$POLICY
		else
				echo INFO: role $ROLE already exists
		fi
}



function determineS3BucketAndKey() {
    local FILE="$1"
    local VERSION="$2"
    S3BUCKET=$(aws elasticbeanstalk create-storage-location | jq -r '.S3Bucket')
    S3KEY="$VERSION-$(basename $FILE)"
}
function uploadBinaryArtifact() {
    local APPNAME="$1"
    local FILE="$2"
    local VERSION="$3"
    determineS3BucketAndKey "$FILE" "$VERSION"
    local EXISTS=$(aws s3 ls s3://$S3BUCKET/$S3KEY)
    if [ -z "$EXISTS" ] ; then
        echo INFO: Uploading $FILE for "$APPNAME", version $VERSION.
        aws s3 cp $FILE s3://$S3BUCKET/$S3KEY
    else
        echo INFO: Version $VERSION of $FILE already uploaded.
    fi
}

function createApplicationVersionIfNotExists() {
    local APPNAME="$1"
    local VERSION="$2"
    APP_VERSION=$(
        aws elasticbeanstalk describe-application-versions \
            --application-name "$APPNAME" \
            --version-label "$APPNAME-$VERSION" | \
        jq -r '.ApplicationVersions | .[]?')

    if [ -z "$APP_VERSION" ] ; then
        echo INFO: Creating version $VERSION of application "$APPNAME"
        APP_VERSION=$(aws elasticbeanstalk create-application-version \
            --application-name "$APPNAME" \
            --version-label "$APPNAME-$VERSION" \
            --source-bundle S3Bucket=$S3BUCKET,S3Key=$S3KEY \
            --auto-create-application)
    else
        echo INFO: Version $VERSION of "$APPNAME" already exists.
    fi
}

function busyWaitEnvironmentStatus() {
    local APPNAME="$1"
    local ENV="$2"
    local STATUS="${3:-Ready}"
    getEnvironmentStatus "$APPNAME" "$ENV"
    while [ "$ENV_STATUS" != "$STATUS" ] ; do 
        echo INFO: "$ENV" in status $ENV_STATUS, waiting to get to $STATUS..
        sleep 5
        getEnvironmentStatus "$APPNAME" "$ENV"
    done
}

function updateEnvironment() {
    local APPNAME="$1"
    local ENV="$2"
    local VERSION="$3"
    local ENV_DESCRIPTOR=$(getEnvDescriptor "$APPNAME" $ENV)
    local ENV_VERSION=$(echo "$ENV_DESCRIPTOR" | jq -r '.VersionLabel')
    local ENV_STATUS=$(echo "$ENV_DESCRIPTOR" | jq -r '.Status')

    if [ "$ENV_VERSION" != "$APPNAME-$VERSION" ] ; then
        busyWaitEnvironmentStatus "$APPNAME" "$ENV"
        echo "INFO: Updating environment $ENV with version $VERSION of $APPNAME"
        ENV_DESCRIPTOR=$(aws elasticbeanstalk update-environment \
            --environment-name "$ENV" \
            --version-label "$APPNAME-$VERSION")
        busyWaitEnvironmentStatus "$APPNAME" "$ENV"
        echo INFO: Version $VERSION of "$APPNAME" deployed in environment
        echo INFO: current status is $ENV_STATUS, goto http://$(echo $ENV_DESCRIPTOR | jq -r .CNAME)
    else
        echo INFO: Version $VERSION of "$APPNAME" already deployed in environment
        echo INFO:  current status is $ENV_STATUS, goto http://$(echo $ENV_DESCRIPTOR | jq -r .CNAME)
    fi
}

function checkEnvironment() {
    local APPNAME="$1"
    local ENVNAME="$2"
    local ENV_APP_NAME=$(aws elasticbeanstalk describe-environments --environment-name "$ENVNAME" | \
                jq -r ' .Environments | .[]? | .ApplicationName')
    if [ -n "$ENV_APP_NAME" -a "$APPNAME" != "$ENV_APP_NAME" ] ; then
        echo ERROR: Environment name "$ENVNAME" already taken by application "$ENV_APP_NAME".
        exit 2
    fi
}

function configureAWS() {
  aws configure set default.region eu-west-1
  aws configure set default.output json
}

APPNAME=$1
FILE="build/$2"
ENV=${3:-dev}
SOLUTION_STACK=${4:-64bit Amazon Linux 2017.03 v2.7.2 running Docker 17.03.1-ce}
if [ $# -lt 2 ] ; then
    usage
fi

PATH=$PATH:/usr/local/bin

buildDeployable "$FILE" "$APPNAME"

VERSION=$(stat --format=%Y "$FILE")

configureAWS
checkEnvironment "$APPNAME" "$ENV"
createAppRoleIfNotExists "$APPNAME"
createAppEnvironmentIfNotExists "$APPNAME" "$ENV" "$SOLUTION_STACK"
uploadBinaryArtifact "$APPNAME" "$FILE" "$VERSION"
createApplicationVersionIfNotExists "$APPNAME" "$VERSION"
updateEnvironment "$APPNAME" "$ENV" "$VERSION"
