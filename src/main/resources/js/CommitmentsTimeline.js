import React, { Component } from 'react';
import Route from './Routes.js'
import CommitmentModal from './CommitmentModal.js'
import Timeline from 'react-calendar-timeline/lib'
import moment from 'moment'

class CommitmentsTimeline extends Component {
    constructor(props) {
        super(props)

        this.route = new Route()
        this.state = this.loadInitialState()
        this.displayedProjects = []
    }

    loadInitialState() {
        return {
            craftspeople: [{
                id: '',
                title: ''
            }],
            projects: [{
                id: '',
                name: ''
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
            commitmentData: {
                id: '',
                craftspersonId: '',
                projectId: '',
                startDate: '',
                endDate: ''
            },
            isEditingCommitment: false,
        }
    }

    componentWillMount() {
        this.route
            .allProjects()
            .then(projects => 
                this.setState((prevState, props) => ({
                    projects: this.mapProjects(projects)
                }))
            )

        this.route
            .allCraftspeople()
            .then(craftspeople => 
                this.setState((prevState, props) => ({
                    craftspeople: this.mapCraftspeople(craftspeople)
                }))
            )

        this.route
            .allCommitments()
            .then(commitments => {
                this.setState((prevState, props) => ({
                    commitments: this.createCommitments(commitments)
                }))
            })
    }

    mapCraftspeople(craftspeople) {
        return craftspeople.map(craftsperson => ({
            id: craftsperson.id,
            title: craftsperson.name
        }))
    }

    mapProjects(projects) {
        return projects.map(project => ({
            id: project.id,
            name: project.name
        }))
    }

    createCommitments(commitments) {
        return commitments.map(commitment => this.asItem(commitment))
    }

    getProject = (id) => {
        return this.state
            .projects
            .find(project => project.id === id)
    }

    projectCSSClass = (projectId) => {
        if (!this.displayedProjects.includes(projectId)) {
            this.displayedProjects.push(projectId)
        }

        return 'project-' + this.displayedProjects.indexOf(projectId)
    }

    updateCommitment = (commitmentId, time, edge) => {
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

    onFormSave = (commitment) => {
        this.route
            .addCommitment(commitment)
            .then(newCommitment => {
                const asItem = this.asItem(newCommitment)

                this.setState((prevState) => ({
                    commitments: prevState.commitments.concat([asItem]),
                    isEditingCommitment: false
                }))
            })
    }

    asItem = (commitment) => {
        return {
            id: commitment.id,
            group: commitment.craftspersonId,
            title: this.getProject(commitment.projectId).name,
            start_time: moment(commitment.startDate),
            end_time: moment(commitment.endDate),
            canMove: false,
            canResize: 'both',
            className: this.projectCSSClass(commitment.projectId)
        }
    }

    onNewFormOpen = (craftsperson, time, e) => {
        this.setState((prevState) => ({
            isEditingCommitment: true, 
            commitmentData: {
                id: '',
                craftspersonId: craftsperson.id,
                startDate: moment(time),
                endDate: null,
                projectId: prevState.projects[0].id
            }
        }))
    }

    onEditFormOpen = (itemId, e) => {
        const item = this.state
            .commitments
            .find(c => c.id == itemId)

        const project = this.state
            .projects
            .find(c => c.name == item.title)

        this.setState((prevState) => ({
            isEditingCommitment: true,
            commitmentData: {
                id: itemId,
                craftspersonId: item.group,
                startDate: item.start_time,
                endDate: item.end_time,
                projectId: project.id
            }
        }))
    }

    onFormClose = () => (
        this.setState((prevState) => ({ isEditingCommitment: false }))
    )

    onFormDelete = () => {
        console.log('start destruction process')
    }

    render() {
        return (
            <div className="container-fluid commitments-board">
                {
                    this.state.isEditingCommitment &&
                    <CommitmentModal
                        onClose={this.onFormClose}
                        onSave={this.onFormSave}
                        onDelete={this.onFormDelete}
                        craftspeople={this.state.craftspeople}
                        projects={this.state.projects}
                        defaultCommitment={this.state.commitmentData}
                    />
                }
                <Timeline groups={this.state.craftspeople}
                    items={this.state.commitments}
                    defaultTimeStart={moment()}
                    defaultTimeEnd={moment().add(6, 'month')}
                    timeSteps={{second: 0, minute: 0, hour: 0, day: 1, month: 1, year: 1}}
                    onItemResize={this.updateCommitment}
                    onCanvasDoubleClick={this.onNewFormOpen}
                    onItemDoubleClick={this.onEditFormOpen}
                    dragSnap={60 * 60 * 1000}
                />
            </div>
       );
    }
}

export default CommitmentsTimeline;
