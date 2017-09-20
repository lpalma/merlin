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
        "startDate": { 
            "year": 2017,
            "month": 9,
            "day": 1
        },
        "endDate": { 
            "year": 2017,
            "month": 12,
            "day": 22
        },
    },
    {
        "id": "2",
        "craftspersonId": "lagertha@codurance.com",
        "projectId": "123-abc",
        "startDate": { 
            "year": 2017,
            "month": 9,
            "day": 1
        },
        "endDate": { 
            "year": 2017,
            "month": 12,
            "day": 22
        },
    },
    {
        "id": "3",
        "craftspersonId": "floki@codurance.com",
        "projectId": "321-bca",
        "startDate": { 
            "year": 2017,
            "month": 9,
            "day": 1
        },
        "endDate": { 
            "year": 2017,
            "month": 10,
            "day": 31
        },
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
}

export default Route
