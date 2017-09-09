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
            employees: [{
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
            .getCommitments()
            .then(commitments => {
                this.setState((prevState, props) => ({
                    employees: this.setEmployess(commitments),
                    commitments: this.setCommitments(commitments)
                }))
        })
    }

    setEmployess(commitments) {
        let employees = []

        commitments.forEach(commitment => {
            const id = commitment.employee.id.value
            const name = commitment.employee.name

            if (!employees.some(e => (e.id == id))) {
                employees.push({
                    id: id,
                    title: name,
                })
            }
        })

        return employees
    }

    setCommitments(commitments) {
        let classNames = []
        let result = []

        commitments.forEach(commitment => {
            const project = commitment.project
            const employee = commitment.employee
            const className = this.calculateClassName(classNames, project.name.toLowerCase())

            if (!result.some(r => (r.group == employee.id.value))) {
                result.push({
                    id: commitment.id.value,
                    group: employee.id.value,
                    title: project.name,
                    start_time: this.formatDate(commitment.startDate),
                    end_time: this.formatDate(commitment.endDate),
                    canMove: false,
                    canResize: 'both',
                    className: className,
                })
            }
        })

        return result
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
            <div className="commitments">
                <Timeline groups={this.state.employees}
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
