import React, { Component } from 'react';
import '../css/App.css';
import Route from './Routes.js'
import Timeline from 'react-calendar-timeline/lib'
import moment from 'moment'

class App extends Component {
    constructor(props) {
        super(props)
        this.state = this.loadInitialState()
        this.route = new Route()
    }

    loadInitialState() {
        return {
            people: [{
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
                //canResize: false,
                className: '',
            }],
        }
    }

    componentWillMount() {
        this.route
            .getCommitments()
            .then(commitments => {
                this.setState((prevState, props) => ({
                    people: this.setPeople(commitments),
                    commitments: this.setCommitments(commitments)
                }))
        })
    }

    setPeople(commitments) {
        return commitments.map(commitment => ({
            id: commitment.name,
            title: commitment.name
        }))
    }

    setCommitments(commitments) {
        let classNames = []
        let result = []
        commitments.forEach(commitment => {
            commitment.projects.forEach(project => {
                const className = this.calculateClassName(classNames, project.name.toLowerCase())
                result.push({
                    id: project.name + commitment.name,
                    group: commitment.name,
                    title: project.name,
                    start_time: moment(project.startDate),
                    end_time: moment(project.endDate),
                    canMove: false,
                    canResize: 'both',
                    className: className,
                })
            })
        })

        return result
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
            <div className="commitments">
                <Timeline groups={this.state.people}
                    items={this.state.commitments}
                    defaultTimeStart={moment()}
                    defaultTimeEnd={moment().add(6, 'month')}
                    timeSteps={{second: 0, minute: 0, hour: 0, day: 1, month: 1, year: 1}}
                    onItemResize={this.handleCommitmentResize}
                    dragSnap={60 * 60 * 1000}
                />
            </div>
       );
    }
}

export default App;
