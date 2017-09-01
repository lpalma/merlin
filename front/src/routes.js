const axios = require('axios')
const JSON = require('./commitments.json')

class Route {

    getCommitments() {
        return Promise.resolve(JSON)
    }
}

export default Route
