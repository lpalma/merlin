import React, { Component } from 'react';
import {ModalContainer, ModalDialog} from 'react-modal-dialog'

class NewCommitment extends Component {
    constructor(props) {
        super(props)
    }

    handleCraftspersonChange = (event) => {
        this.props.onCraftspersonChange(event.target.value)
    }

    handleProjectChange = (event) => {
        this.props.onProjectChange(event.target.value)
    }

    handleStartDateChange = (event) => {
        this.props.onStartDateChange(event.target.value)
    }

    handleEndDateChange = (event) => {
        this.props.onEndDateChange(event.target.value)
    }

    render() {
        return (
            <div className="modal-body">
                <form>
                    <div className="form-group row">
                        <label htmlFor="craftsperson" className="col-sm-3 col-form-label">Craftsperson</label>
                        <div className="col-sm-9">
                            <select
                                className="form-control"
                                id="craftsperson"
                                value={this.props.defaultCraftsperson}
                                onChange={this.handleCraftspersonChange}>
                                    {this.props.craftspeople().map(craftsperson => (
                                        <option value={craftsperson.id} key={craftsperson.id}>{craftsperson.title}</option>
                                    ))}
                            </select>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="project" className="col-sm-3 col-form-label">Project</label>
                        <div className="col-sm-9">
                            <select
                                className="form-control"
                                id="project" value={this.props.defaultProject}
                                onChange={this.handleProjectChange}>
                                    {this.props.projects().map(project => (
                                        <option key={project.id}>{project.name}</option>
                                    ))}
                            </select>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="startDate" className="col-sm-3 col-form-label">Start Date</label>
                        <div className="col-sm-9">
                            <input
                                type="text"
                                className="form-control col-sm-6"
                                id="startDate"
                                defaultValue={this.props.defaultStartDate}
                                onChange={this.handleStartDateChange}>
                            </input>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="endDate" className="col-sm-3 col-form-label">End Date</label>
                        <div className="col-sm-9">
                            <input
                                type="text"
                                className="form-control col-sm-6"
                                id="endDate"
                                defaultValue={this.props.defaultEndDate}
                                onChange={this.handleEndDateChange}>
                            </input>
                        </div>
                    </div>
                </form>
            </div>
        )
    }
}

export default NewCommitment;
