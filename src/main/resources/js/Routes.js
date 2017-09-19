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
}

export default Route
