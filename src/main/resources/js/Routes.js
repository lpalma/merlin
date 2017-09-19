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
    }]

class Route {
    getCommitments() {
        return axios.get('/api/commitments').then((response) => {
            return Promise.resolve(response.data)
        })
    }

    allCraftspeople() {
        return Promise.resolve(craftspeople)
    }
}

export default Route
