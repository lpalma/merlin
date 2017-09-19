import React, { Component } from 'react';
import Route from './Routes.js'
import Timeline from 'react-calendar-timeline/lib'
import moment from 'moment'

class CommitmentsTimeline extends Component {
    constructor(props) {
        super(props)

        this.route = new Route()
        this.state = this.loadInitialState()
    }

    loadInitialState() {
        return {
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
                    commitments: this.createCommitments(commitments)
                }))
            })
    }

    createCommitments(commitments) {
        let classNames = []
        let result = []

        commitments.forEach(commitment => {
            const className = this.calculateClassName(classNames, 'deal with it')

            result.push({
                id: commitment.id,
                group: commitment.craftspersonId,
                title: 'deal with it (no, srly, you gotta get the correct name',
                start_time: this.formatDate(commitment.startDate),
                end_time: this.formatDate(commitment.endDate),
                canMove: false,
                canResize: 'both',
                className: className
            })
        })

        return result
    }

    formatDate = (date) => {
        return moment(date.year + "-" + date.month + "-" + date.day, "YYYY-MM-DD")
    }

    calculateClassName (classList, projectName) {
        const fixedName = projectName.toLowerCase().replace(/\s/g, '')

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
            <div className="container-fluid commitments-board">
                <Timeline groups={this.props.craftspeople}
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

export default CommitmentsTimeline;
