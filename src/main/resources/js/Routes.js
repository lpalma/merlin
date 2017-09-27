const axios = require('axios')
const craftspeople = [
    {
        "id": "rollo@codurance.com",
        "name": "Rollo Lothbrok",
        "email": "rollo@codurance.com",
        "role": "admin",
        "active": true
    },
    {
        "id": "lagertha@codurance.com",
        "name": "Lagertha",
        "email": "lagertha@codurance.com",
        "role": "admin",
        "active": true
    },
    {
        "id": "floki@codurance.com",
        "name": "Floki",
        "email": "floki@codurance.com",
        "role": "user",
        "active": true
    },
    {
        "id": "ragnar@codurance.com",
        "name": "Ragnar Lothbrok",
        "email": "ragnar@codurance.com",
        "role": "admin",
        "active": false
    }]

const projects = [
    {
        "id": "123-abc",
        "name": "Setup & onboarding",
        "description": "Generally use this to book time used when setting up machine and accounts (new starter)",
        "activitytype": "Hourly",
        "is_billable": false
    },
    {
        "id": "321-bca",
        "name": "Alpha London",
        "description": "Alpha meetup ",
        "activitytype": "Daily",
        "is_billable": false
    }]

const commitments =  [
    {
        "id": "1",
        "craftspersonId": "rollo@codurance.com",
        "projectId": "123-abc",
        "startDate": "2017-09-01",
        "endDate": "2017-12-22" 
    },
    {
        "id": "2",
        "craftspersonId": "lagertha@codurance.com",
        "projectId": "123-abc",
        "startDate": "2017-09-01",
        "endDate": "2017-12-22" 
    },
    {
        "id": "3",
        "craftspersonId": "floki@codurance.com",
        "projectId": "321-bca",
        "startDate": "2017-09-01",
        "endDate": "2017-10-31" 
    }]

class Route {
    getCommitments() {
        return Promise.resolve(commitments)
    }

    allCraftspeople() {
        const activesOnly = craftspeople.filter(craftsperson => craftsperson.active == true)

        return Promise.resolve(activesOnly)
    }

    allProjects() {
        return Promise.resolve(projects)
    }

    addCommitment(commitmentData) {
        return axios.put('api/commitments', commitmentData)
            .then((response) => {
                return Promise.resolve(response.data)
            }).catch((error) => {
                console.log(error)
            })
    }
}

export default Route
