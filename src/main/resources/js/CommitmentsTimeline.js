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
                craftspersonId: '',
                projectId: '',
                startDate: '',
                endDate: ''
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
        this.loadProjects()
        this.loadCraftspeople()
        this.loadCommitments()
    }

    loadProjects = () => {
        this.route
            .allProjects()
            .then(projects =>
                this.setState(() => ({ projects }))
            )
    }

    loadCraftspeople = () => {
        this.route
            .allCraftspeople()
            .then(craftspeople =>
                this.setState(() => ({ craftspeople }))
            )
    }

    loadCommitments = () => {
        this.route
            .allCommitments()
            .then(commitments =>
                this.setState(() => ({ commitments }))
            )
    }

    toGroups = (craftspeople) => {
        return craftspeople.map(craftsperson => ({
            id: craftsperson.id,
            title: craftsperson.name
        }))
    }

    toItems(commitments) {
        return commitments.map(commitment => this.asItem(commitment))
    }

    getProject = (id) => {
        return this.state
            .projects
            .find(project => project.id === id)
    }

    getCommitment = (id) => {
        return this.state
            .commitments
            .find(c => c.id == id)
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
                this.setState((prevState) => ({
                    commitments: prevState.commitments.concat([newCommitment]),
                    isEditingCommitment: false
                }))
            })
    }

    asItem = (commitment) => {
        const item = this.emptyItem()

        if (commitment.id) {
            const projectId = commitment.projectId
            item.id = commitment.id,
            item.group = commitment.craftspersonId,
            item.title = this.getProject(projectId).name,
            item.start_time = moment(commitment.startDate),
            item.end_time = moment(commitment.endDate),
            item.canMove = false,
            item.canResize = 'both',
            item.className = this.projectCSSClass(projectId)
        }

        return item
    }

    emptyItem = () => {
        return {
            id: '',
            group: '',
            title: '',
            start_time: '',
            end_time: '',
            canMove: '',
            canResize: '',
            className: ''
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
        const commitment = this.getCommitment(itemId)

        this.setState((prevState) => ({
            isEditingCommitment: true,
            commitmentData: {
                id: commitment.id,
                craftspersonId: commitment.craftspersonId,
                startDate: moment(commitment.startDate),
                endDate: moment(commitment.endDate),
                projectId: commitment.projectId
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
                    items={this.toItems(this.state.commitments)}
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
