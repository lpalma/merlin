import React, { Component } from 'react';
import './App.css';
import Route from './routes.js'
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
            commitments: [{
                name: '',
                role: '',
                projects: [{
                    name: '',
                    startDate: '',
                    endDate:''
                }]
            }]
        }
    }

    componentWillMount() {
        this.route
            .getCommitments()
            .then(result => {
                this.setState((precState, props) => ({
                    commitments: result
                }))
        })
    }

    groups() {
        return this.state.commitments.map(commitment => ({
            id: commitment.name,
            title: commitment.name
        }))
    }

    items() {
        let result = []
        this.state.commitments.forEach(commitment => {
            commitment.projects.forEach(project => {
                const className = 'project-' + project.name.toLowerCase()
                result.push({
                    id: project.name + commitment.name,
                    group: commitment.name,
                    title: project.name,
                    start_time: moment(project.startDate),
                    end_time: moment(project.endDate),
                    canMove: false,
                    canResize: false,
                    className: className,
                    itemProps: {
                        onDoubleClick: () => { console.log('clicked!') }
                    }
                })
            })
        })

        return result
    }

    render() {
        return (
            <div className="commitments">
                <Timeline groups={this.groups()}
                    items={this.items()}
                    defaultTimeStart={moment()}
                    defaultTimeEnd={moment().add(6, 'month')}
                />
            </div>
       );
    }
}

export default App;
