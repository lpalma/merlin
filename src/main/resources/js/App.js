import React, { Component } from 'react';
import '../css/App.css';
import Route from './Routes.js'
import Timeline from 'react-calendar-timeline/lib'
import Header from './Header.js'
import moment from 'moment'

class App extends Component {
    constructor(props) {
        super(props)
        this.state = this.loadInitialState()
        this.route = new Route()
    }

    loadInitialState() {
        return {
            craftspeople: [{
                id: '',
                title: ''
            }],
            commitments: [{
                id: '',
                group: '',
                title: '',
                start_time: '',
                end_time: '',
                canMove: '',
                className: '',
            }],
        }
    }

    componentWillMount() {
        this.route
            .allCraftspeople()
            .then(craftspeople => {
                this.setState((prevState, props) => ({
                    craftspeople: this.setCraftspeople(craftspeople)
                }))
            })
    }

    setCraftspeople(craftspeople) {
        return craftspeople.map(craftsperson => ({
            id: craftsperson.id,
            title: craftsperson.name
        }))
    }

    formatDate = (date) => {
        return moment(date.year + "-" + date.month + "-" + date.day, "YYYY-MM-DD")
    }

    calculateClassName (classList, projectName) {
        if (!classList.includes(projectName)) {
            classList.push(projectName)
        }

        return 'project-' + classList.indexOf(projectName)
    }

    handleCommitmentResize = (commitmentId, time, edge) => {
        const { commitments } = this.state
        
        const newCommitments = commitments.map(commitment => {
            let newCommitment = Object.assign({}, commitment)

            if (newCommitment.id === commitmentId) {
                newCommitment.start_time = edge === 'left' ? time : commitment.start_time
                newCommitment.end_time = edge === 'right' ? time : commitment.end_time
            }

            return newCommitment
        })

        this.setState((prevState, props) => ({ commitments: newCommitments }))
    }

    render() {
        return (
            <div>
                <Header />
                <div className="container-fluid commitments-board">
                    <Timeline groups={this.state.craftspeople}
                        items={[]}
                        defaultTimeStart={moment()}
                        defaultTimeEnd={moment().add(6, 'month')}
                        timeSteps={{second: 0, minute: 0, hour: 0, day: 1, month: 1, year: 1}}
                        onItemResize={this.handleCommitmentResize}
                        dragSnap={60 * 60 * 1000}
                    />
                </div>
            </div>
       );
    }
}

export default App;
