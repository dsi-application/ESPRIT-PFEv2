import React, { Component } from 'react';

class ModalTechnology extends Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.state = {
            techLibelle: '',
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            techLibelle: nextProps.techLibelle,
        });
    }

    titleHandler(e) {
        this.setState({ techLibelle: e.target.value });
    }

    handleSave()
    {
        const item = this.state;
        this.props.saveModalDetailsTechnology(item)
    }

    render() {
        return (
            <div className="modal fade" id="exampleModalTechnology" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <span className="myModalTitle">Mettre à Jour Technologie</span>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                                <span className="myModalField">Libellé:</span>
                                <br/>
                                <input className="form-control" value={this.state.techLibelle} onChange={(e) => this.titleHandler(e)} />
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button"
                                    className="btn btn-primary"
                                    disabled={this.state.techLibelle.length !== 0 && this.state.techLibelle.length < 256 ? "" : "disabled"}
                                    data-dismiss="modal"
                                    onClick={() => { this.handleSave() }}>Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ModalTechnology;
