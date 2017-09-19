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
        "name": "Swift London",
        "description": "Swift meetup ",
        "activitytype": "Daily",
        "is_billable": false
    }]

class Route {
    getCommitments() {
        return axios.get('/api/commitments').then((response) => {
            return Promise.resolve(response.data)
        })
    }

    allCraftspeople() {
        const activesOnly = craftspeople.filter(craftsperson => craftsperson.active == true)

        return Promise.resolve(activesOnly)
    }

    allProjects() {
        return Promise.resolve(projects)
    }
}

export default Route
