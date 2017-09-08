const axios = require('axios')

class Route {

    getCommitments() {
        return axios.get('/json').then((response) => {
            return Promise.resolve(response.data)
        })
    }
}

export default Route
