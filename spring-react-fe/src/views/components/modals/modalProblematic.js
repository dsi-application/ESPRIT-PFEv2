import React, { Component } from 'react';

class ModalProblematic extends Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.state = {
            probLibelle: '',
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            probLibelle: nextProps.probLibelle,
        });
    }

    titleHandler(e) {
        this.setState({ probLibelle: e.target.value });
    }

    handleSave() {
        const item = this.state;
        this.props.saveModalDetailsProblematic(item)
    }

    render() {
        return (
            <div className="modal fade" id="exampleModalProblematic" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <span className="myModalTitle">Mettre à Jour Problématique</span>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                                <span className="myModalField">Description:</span>
                                <br/>
                                <textarea className="form-control"
                                          rows={5}
                                          value={this.state.probLibelle}
                                          onChange={(e) => this.titleHandler(e)} />
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button"
                                    className="btn btn-primary"
                                    disabled={this.state.probLibelle.length !== 0 && this.state.probLibelle.length > 9 && this.state.probLibelle.length < 3001 ? "" : "disabled"}
                                    data-dismiss="modal" onClick={() => { this.handleSave() }}>Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ModalProblematic;
