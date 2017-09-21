import React, { Component } from 'react';
import '../css/App.css';
import Route from './Routes.js'
import CommitmentsTimeline from './CommitmentsTimeline.js'
import Header from './Header.js'
import moment from 'moment'

class App extends Component {
    constructor(props) {
        super(props)
        this.route = new Route()
    }

    render() {
        return (
            <div>
                <Header />
                <div className="container-fluid commitments-board">
                    <CommitmentsTimeline />
                </div>
            </div>
       );
    }
}

export default App;
