import React, { Component } from 'react';
import {ModalContainer, ModalDialog} from 'react-modal-dialog'
import { DateRangePicker, SingleDatePicker, DayPickerRangeController } from 'react-dates'

class NewCommitment extends Component {
    constructor(props) {
        super(props)
        
        this.state = {
            startDate: props.defaultStartDate,
            endDate: null,
            focusedInput: null
        }
    }

    handleCraftspersonChange = (event) => {
        this.props.onCraftspersonChange(event.target.value)
    }

    handleProjectChange = (event) => {
        this.props.onProjectChange(event.target.value)
    }

    handleDatesChange = ({ startDate, endDate }) => {
        this.setState({ startDate, endDate })

        this.props.onStartDateChange(startDate)
        this.props.onEndDateChange(endDate)
    }

    handleFocusChange = (focusedInput) => {
        this.setState({ focusedInput })
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
                                        <option value={project.id} key={project.id}>{project.name}</option>
                                    ))}
                            </select>
                        </div>
                    </div>
                    <div className="form-group row">
                        <div className="col-sm-9 ml-auto">
                            <DateRangePicker
                                startDate={this.state.startDate}
                                endDate={this.state.endDate}
                                onDatesChange={this.handleDatesChange}
                                focusedInput={this.state.focusedInput}
                                onFocusChange={this.handleFocusChange}
                                displayFormat={'DD/MM/YYYY'}
                            />
                        </div>
                    </div>
                </form>
            </div>
        )
    }
}

export default NewCommitment;
