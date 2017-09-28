import React, { Component } from 'react';
import NewCommitment from './NewCommitment.js'
import {ModalContainer, ModalDialog} from 'react-modal-dialog'

class CommitmentModal extends Component {
    constructor(props) {
        super(props)
        this.props = props

        this.state = {
            commitment: props.defaultCommitment
        }
    }

    onSave = () => {
        this.props.onSave(this.state.commitment)
    }

    onChange = (update) => {
        this.setState(({ commitment }) => {
            update(commitment)

            return { commitment }
        })
    }

    render() {
        return (
            <ModalContainer onClose={this.props.onClose}>
                <ModalDialog className="new-commitment-form" onClose={this.props.onClose}>
                    <div className="modal-header">
                        <h2 className="modal-title">Commitment</h2>
                    </div>
                    <NewCommitment
                        craftspeople={this.props.craftspeople}
                        projects={this.props.projects}
                        defaultCraftsperson={this.props.defaultCommitment.craftspersonId}
                        defaultProject={this.props.defaultCommitment.projectId}
                        defaultStartDate={this.props.defaultCommitment.startDate}
                        defaultEndDate={this.props.defaultCommitment.endDate}
                        onCommitmentChange={this.onChange}
                    />
                    <div className="modal-footer">
                        {
                            (this.props.defaultCommitment.id != '') &&
                            <button
                                type="button"
                                className="btn btn-danger"
                                onClick={this.props.onDelete}>
                                    Delete
                            </button>
                        }
                        <button
                            type="button"
                            className="btn btn-secondary"
                            onClick={this.props.onClose}>
                                Cancel
                        </button>
                        <button
                            type="button"
                            className="btn btn-primary"
                            onClick={this.onSave}>
                                Save
                        </button>
                    </div>
                </ModalDialog>
            </ModalContainer>
        )
    }
}

export default CommitmentModal
