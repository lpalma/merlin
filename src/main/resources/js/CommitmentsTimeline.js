import React, { Component } from 'react';
import Route from './Routes.js'
import NewCommitment from './NewCommitment.js'
import Timeline from 'react-calendar-timeline/lib'
import {ModalContainer, ModalDialog} from 'react-modal-dialog'
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
            newCommitment: {
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

    createCommitment = () => {
        const newCommitment = Object.assign({}, this.state.newCommitment)

        this.route
            .addCommitment(newCommitment)
            .then(commitment => {
                const asItem = this.asItem(commitment)

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

    newCommitmentForm = (craftsperson, time, e) => {
        this.setState((prevState) => ({
            isEditingCommitment: true, 
            newCommitment: {
                id: '',
                craftspersonId: craftsperson.id,
                startDate: moment(time),
                endDate: null,
                projectId: prevState.projects[0].id
            }
        }))
    }

    newEditForm = (itemId, e) => {
        const item = this.state
            .commitments
            .find(c => c.id == itemId)

        const project = this.state
            .projects
            .find(c => c.name == item.title)

        this.setState((prevState) => ({
            isEditingCommitment: true,
            newCommitment: {
                id: itemId,
                craftspersonId: item.group,
                startDate: item.start_time,
                endDate: item.end_time,
                projectId: project.id
            }
        }))
    }

    closeCommitmentForm = () => (
        this.setState((prevState) => ({ isEditingCommitment: false }))
    )

    updateNewCommitment = (update) => {
        this.setState(({ newCommitment }) => {
            update(newCommitment)

            return { newCommitment }
        })
    }

    deleteCommitment = () => {
        console.log('start destruction process')
    }

    render() {
        const newCommitment = this.state.newCommitment

        return (
            <div className="container-fluid commitments-board">
                {
                    this.state.isEditingCommitment &&
                    <ModalContainer onClose={this.closeCommitmentForm}>
                        <ModalDialog className="new-commitment-form" onClose={this.closeCommitmentForm}>
                            <div className="modal-header">
                                <h2 className="modal-title">New Commitment</h2>
                            </div>
                            <NewCommitment
                                craftspeople={this.state.craftspeople}
                                projects={this.state.projects}
                                defaultCraftsperson={newCommitment.craftspersonId}
                                defaultProject={newCommitment.projectId}
                                defaultStartDate={newCommitment.startDate}
                                defaultEndDate={newCommitment.endDate}
                                onCommitmentChange={this.updateNewCommitment}
                            />
                            <div className="modal-footer">
                                {
                                    (this.state.newCommitment.id != '') &&
                                    <button
                                        type="button"
                                        className="btn btn-danger"
                                        onClick={this.deleteCommitment}>
                                            Delete
                                    </button>
                                }
                                <button
                                    type="button"
                                    className="btn btn-secondary"
                                    onClick={this.closeCommitmentForm}>
                                        Cancel
                                </button>
                                <button
                                    type="button"
                                    className="btn btn-primary"
                                    onClick={this.createCommitment}>
                                        Save
                                </button>
                            </div>
                        </ModalDialog>
                    </ModalContainer>
                }
                <Timeline groups={this.state.craftspeople}
                    items={this.state.commitments}
                    defaultTimeStart={moment()}
                    defaultTimeEnd={moment().add(6, 'month')}
                    timeSteps={{second: 0, minute: 0, hour: 0, day: 1, month: 1, year: 1}}
                    onItemResize={this.updateCommitment}
                    onCanvasDoubleClick={this.newCommitmentForm}
                    onItemDoubleClick={this.newEditForm}
                    dragSnap={60 * 60 * 1000}
                />
            </div>
       );
    }
}

export default CommitmentsTimeline;
