import React, { Component } from 'react';
import Route from './Routes.js'
import NewCommitment from './NewCommitment.js'
import Timeline from 'react-calendar-timeline/lib'
import {ModalContainer, ModalDialog} from 'react-modal-dialog'
import moment from 'moment'

class CommitmentsTimeline extends Component {
    constructor(props) {
        super(props)

        this.route = new Route()
        this.state = this.loadInitialState()
    }

    loadInitialState() {
        return {
            craftspeople: [{
                id: '',
                title: ''
            }],
            projects: [{
                id: '',
                name: ''
            }],
            commitments: [{
                id: '',
                group: '',
                title: '',
                start_time: '',
                end_time: '',
                canMove: '',
                className: '',
            }],
        }
    }

    componentWillMount() {
        this.route
            .allProjects()
            .then(projects => 
                this.setState((prevState, props) => ({
                    projects: this.mapProjects(projects)
                }))
            )

        this.route
            .allCraftspeople()
            .then(craftspeople => 
                this.setState((prevState, props) => ({
                    craftspeople: this.mapCraftspeople(craftspeople)
                }))
            )

        this.route
            .getCommitments()
            .then(commitments => {
                this.setState((prevState, props) => ({
                    commitments: this.createCommitments(commitments)
                }))
            })
    }

    mapCraftspeople(craftspeople) {
        return craftspeople.map(craftsperson => ({
            id: craftsperson.id,
            title: craftsperson.name
        }))
    }

    mapProjects(projects) {
        return projects.map(project => ({
            id: project.id,
            name: project.name
        }))
    }

    createCommitments(commitments) {
        let classNames = []
        let result = []

        commitments.forEach(commitment => {
            const project = this.getProject(commitment.projectId)
            const className = this.calculateClassName(classNames, project.name)

            result.push({
                id: commitment.id,
                group: commitment.craftspersonId,
                title: project.name,
                start_time: this.formatDate(commitment.startDate),
                end_time: this.formatDate(commitment.endDate),
                canMove: false,
                canResize: 'both',
                className: className
            })
        })

        return result
    }

    getProject(id) {
        return this.state
            .projects
            .find(project => project.id === id)
    }

    formatDate = (date) => {
        return moment(date.year + "-" + date.month + "-" + date.day, "YYYY-MM-DD")
    }

    calculateClassName (classList, projectName) {
        const fixedName = projectName.toLowerCase().replace(/\s/g, '')

        if (!classList.includes(projectName)) {
            classList.push(projectName)
        }

        return 'project-' + classList.indexOf(projectName)
    }

    updateCommitment = (commitmentId, time, edge) => {
        const { commitments } = this.state
        
        const newCommitments = commitments.map(commitment => {
            let newCommitment = Object.assign({}, commitment)

            if (newCommitment.id === commitmentId) {
                newCommitment.start_time = edge === 'left' ? time : commitment.start_time
                newCommitment.end_time = edge === 'right' ? time : commitment.end_time
            }

            return newCommitment
        })

        this.setState((prevState, props) => ({ commitments: newCommitments }))
    }

    render() {
        const creatingCommitment = this.state.creatingCommitment

        return (
            <div className="container-fluid commitments-board">
                {
                    this.state.isCreatingCommitment &&
                    <ModalContainer onClose={this.closeCommitmentForm}>
                        <ModalDialog className="new-commitment-form" onClose={this.closeCommitmentForm}>
                            <div className="modal-header">
                                <h2 className="modal-title">New Commitment</h2>
                            </div>
                            <NewCommitment
                                craftspeople={this.allCraftspeople}
                                projects={this.allProjects}
                                defaultCraftsperson={creatingCommitment.craftspersonId}
                                defaultProject={creatingCommitment.projectId}
                                defaultStartDate={creatingCommitment.startDate}
                                defaultEndDate={creatingCommitment.endDate}
                                onCraftspersonChange={this.handleFormCraftspersonChange}
                                onProjectChange={this.handleFormProjectChange}
                                onStartDateChange={this.handleFormStartDateChange}
                                onEndDateChange={this.handleFormEndDateChange}
                            />
                            <div className="modal-footer">
                                <button
                                    type="button"
                                    className="btn btn-secondary"
                                    onClick={this.closeCommitmentForm}>
                                        Cancel
                                </button>
                                <button
                                    type="button"
                                    className="btn btn-primary"
                                    onClick={this.createCommitment}>
                                        Save
                                </button>
                            </div>
                        </ModalDialog>
                    </ModalContainer>
                }
                <Timeline groups={this.state.craftspeople}
                    items={this.state.commitments}
                    defaultTimeStart={moment()}
                    defaultTimeEnd={moment().add(6, 'month')}
                    timeSteps={{second: 0, minute: 0, hour: 0, day: 1, month: 1, year: 1}}
                    onItemResize={this.updateCommitment}
                    dragSnap={60 * 60 * 1000}
                />
            </div>
       );
    }
}

export default CommitmentsTimeline;
