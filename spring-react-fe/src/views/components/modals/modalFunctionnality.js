import React, { Component } from 'react';

class ModalFunctionnality extends Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.state = {
            funcLibelle: '',
            funcDescription: '',
            result: 0,
        }
    }

    componentWillReceiveProps(nextProps)
    {
        if(nextProps.funcDescription === null)
        {
            this.setState({
                funcLibelle: nextProps.funcLibelle,
                funcDescription: '',
            });
        }
        if(nextProps.funcDescription !== null)
        {
            this.setState({
                funcLibelle: nextProps.funcLibelle,
                funcDescription: nextProps.funcDescription,
            });
        }
    }

    functionnalityLibelleHandler(e)
    {
        const calcRes = 80 - e.target.value.length;
        // console.log('-------------------> HI: ' + calcRes);

        this.setState({ funcLibelle: e.target.value, result: calcRes });
    }

    functionnalityDescriptionHandler(e) {
        this.setState({ funcDescription: e.target.value });
    }

    handleSave() {
        const item = this.state;
        this.props.saveModalDetailsFunctionnality(item)
    }


    render() {


        return (
            <div className="modal fade" id="exampleModalFunctionnality" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <span className="myModalTitle">Mettre à Jour Fonctionnalité</span>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                                <span className="myModalField">Libellé:</span>
                                <br/>
                                <input className="form-control" value={this.state.funcLibelle} onChange={(e) => this.functionnalityLibelleHandler(e)} />
                                <span className="remainingNbrChar">Remaining {this.state.result} chs</span>
                        </div>

                        <div className="modal-body">
                            <p>
                                <span className="myModalField">Description:</span>
                                <br/>
                                <textarea className="form-control"
                                          rows={5}
                                          value={this.state.funcDescription}
                                          onChange={(e) => this.functionnalityDescriptionHandler(e)} />
                            </p>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button"
                                    className="btn btn-primary"
                                    data-dismiss="modal"
                                    disabled={((this.state.funcLibelle.length !== 0 && this.state.funcLibelle.length > 9 && this.state.funcLibelle.length < 81 && this.state.funcDescription.length=== 0) || (this.state.funcLibelle.length !== 0 && this.state.funcLibelle.length > 9 && this.state.funcLibelle.length < 81 && this.state.funcDescription.length!== 0 && this.state.funcDescription.length>9 && this.state.funcDescription.length<3001)) ? "" : "disabled"}
                                    onClick={() => { this.handleSave() }}>Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ModalFunctionnality;
