import moment from 'moment'

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
                return Promise.resolve(this.toCommitments(response.data))
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
        return axios.put('api/commitments', this.toJson(commitmentData))
            .then((response) => {
                return Promise.resolve(this.toCommitment(response.data))
            }).catch((error) => {
                console.log(error)
            })
    }

    deleteCommitment(id) {
        axios.delete('api/commitments/' + id)
            .catch((error) => {
                console.log(error)
            })
    }

    toCommitments(data) {
        return data.map(d => this.toCommitment(d))
    }

    toCommitment(data) {
        return {
            id: data.id,
            craftspersonId: data.craftspersonId,
            projectId: data.projectId,
            startDate: moment(data.startDate),
            endDate: moment(data.endDate)
        }
    }

    toJson(commitment) {
        return {
            id: commitment.id,
            craftspersonId: commitment.craftspersonId,
            projectId: commitment.projectId,
            startDate: this.formatDate(commitment.startDate),
            endDate: this.formatDate(commitment.endDate)
        }
    }

    formatDate = (date) => {
        return date == null ? '' : date.format("YYYY-MM-DD")
    }
}

export default Route
