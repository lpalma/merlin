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

class Route {
    allCommitments() {
        return axios.get('api/commitments')
            .then(response => {
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
