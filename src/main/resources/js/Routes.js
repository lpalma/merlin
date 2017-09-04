const axios = require('axios')

class Route {

    getCommitments() {
        return axios.get('/api/commitments').then((response) => {
            return Promise.resolve(response.data)
        })
    }
}

export default Route
