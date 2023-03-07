import React, { Component } from 'react';
import { isInt, isEmail } from "validator";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";

const supervFirstName = value => {
  if (value.length < 5 && value.length > 51) {
    return (
      <div className="alert alert-danger" role="alert">
        The supervFirstName must be between 5 and 50 characters.
      </div>
    );
  }
};

const supervLastName = value => {
  if (value.length < 5 && value.length > 51) {
    return (
      <div className="alert alert-danger" role="alert">
        The supervLastName must be between 5 and 50 characters.
      </div>
    );
  }
};

const supervEmail = value => {
  if (!isEmail(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        It's not a valid supervEmail form.
      </div>
    );
  }
};

const supervPhoneNumber = value =>
{
    if(value !== undefined)
    {
         if(!isInt(value))
         {
            return (
              <div className="alert alert-danger" role="alert">
                It's not a valid Phone Number form.
              </div>
            );
         }
    }
};

const supervPhoneNumberMax = value =>
{
    if(value !== undefined)
    {
        if (value.length < 8)
        {
            return (
                <div className="alert alert-danger" role="alert">
                    This number {value} isn't a valid Phone Number.
                     <br/>
                    It should be between 8 and 50 digits.
                </div>
            );
        }
    }
};


const required = value => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required !.
      </div>
    );
  }
};

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

class ModalSupervisor extends Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);

        this.firstNameHandler = this.firstNameHandler.bind(this);
        this.lastNameHandler = this.lastNameHandler.bind(this);
        this.phoneNumberHandler = this.phoneNumberHandler.bind(this);
        this.emailHandler = this.emailHandler.bind(this);

        this.state = {
            supervFirstName: '------',
            supervLastName: '-------',
            supervPhoneNumber: '11111111',
            supervEmail: 'email@mail.tn',
            isValid: false,
            oldSupervMail:"",
        }
    }

    componentWillReceiveProps(nextProps) {
        // // console.log('---------------------------------------> TextScroller.js 1: ' + this.state.supervFirstName);
        // console.log("----------------------------- idStudent 1 --> " + nextProps.idStudent);
        // console.log("----------------------------- idStudent 2 --> " + nextProps.supervLastName);
        // console.log("----------------------------- 1 --> " + nextProps.supervPhoneNumber);
        // console.log("----------------------------- 1 --> " + nextProps.supervEmail);
        this.setState({
            idStudent: nextProps.idStudent,
            supervFirstName: nextProps.supervFirstName,
            supervLastName: nextProps.supervLastName,
            supervPhoneNumber: nextProps.supervPhoneNumber,
            supervEmail: nextProps.supervEmail,
            isValid:true,
            oldSupervMail: nextProps.supervEmail,
        });
        // console.log('---------------------------------------> idStudent 3: ' + this.state.supervFirstName);
    }

    firstNameHandler(e)
    {
        if (e.target.value.length < 5)
        {
            this.setState({ isValid: false });
        }
        else
        {
            this.setState({ isValid: true });
        }
        // console.log('--------------------------- First Name : ' + this.state.isValid);

        this.setState
        ({
            supervFirstName: e.target.value
        });
    }

    lastNameHandler(e)
    {
        if (e.target.value.length < 5)
        {
            this.setState({ isValid: false });
        }
        else
        {
            this.setState({ isValid: true });
        }
        // console.log('--------------------------- Last Name : ' + this.state.isValid);

        this.setState
        ({
            supervLastName: e.target.value
        });
    }

    phoneNumberHandler(e)
    {
        if(e.target.value !== undefined)
        {
            // console.log('-----------------------> 1: ' + e.target.value);
            if (!isInt(e.target.value) || e.target.value.length <8 || e.target.value.length>50)
            {
              // console.log('-----------------------> 2: ' + e.target.value);
                this.setState({ isValid: false });
            }
          if (isInt(e.target.value) && e.target.value.length>7 && e.target.value.length<51)
          {
            // console.log('-----------------------> 3: ' + e.target.value);
            this.setState({ isValid: true });
          }
        }
        else
        {
          // console.log('-----------------------> 4: ' + e.target.value)
            this.setState({ isValid: false });
        }
        // console.log('--------------------------- Phone Number : ' + this.state.isValid);
        this.setState
        ({
            supervPhoneNumber: e.target.value
        });
    }

    emailHandler(e)
    {
        var requestfp = new XMLHttpRequest();
        requestfp.open(
          "GET",
          API_URL_MESP + "verifyExistMailEncadrantEntreprise/" + this.state.idStudent + "/" + e.target.value,
          false
        );
        requestfp.send(null);

        let result = JSON.parse(requestfp.responseText);

        // console.log('------------------ idStudent ----------> VERIF EE: ' + result);

        if (isEmail(e.target.value) || result === false)
        {
            this.setState({ isValid: true });
        }
        else
        {
            this.setState({ isValid: false });
        }
        // console.log('--------------------------- Email : ' + this.state.isValid);
        this.setState
        ({
            supervEmail: e.target.value
        });
    }

    handleSave(e)
    {

        /*
        e.preventDefault();
        this.form.validateAll();
        console.log(this.form.validateAll());
        */


        e.preventDefault();  // Modal displays validation results & don't closed
        // e.stopPropagation(); // Modal don't closed
        // console.log('---------------------**-----> 0: ' + this.state.isValid + " - " + this.state.oldSupervMail);
        this.form.validateAll();
        // console.log('---------------------**-----> 1: ' + this.state.isValid);
        // console.log(this.form.validate("supervEmail"));
        // console.log('---------------------**-----> 2');

        /*if (!this.form.isValid())
        {
            console.log('form is invalid: do not submit');
        }
        else
        {
            console.log('form is valid: submit');
        }*/
        const item = this.state;
        // console.log('---------------------**-----> 3');
        this.props.saveModalDetailsSupervisor(item, this.state.oldSupervMail);
        // console.log('---------------------**-----> 4');

        // console.log('---------------------**-----> 5: ' + this.state.supervFirstName);
    }

    render() {
        return (
            <div className="modal fade" id="exampleModalSupervisor" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLabel">
                                <span className="myModalTitle">Mettre à Jour Profil Encadrant Entreprise</span>
                            </h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <Form onSubmit={this.handleSave} ref={c => { this.form = c; }}>
                            <div className="modal-body">
                                    <span className="myModalField">Nom:</span>
                                    <br/>
                                    <Input type="text"
                                           className="form-control"
                                           name="supervFirstName"
                                           value={this.state.supervFirstName}
                                           onChange={this.firstNameHandler}
                                           validations={[required, supervFirstName]}
                                           maxLength="50"/>

                            </div>

                            <div className="modal-body">
                                    <span className="myModalField">Prénom:</span>
                                    <br/>
                                    <Input type="text"
                                           className="form-control"
                                           name="supervLastName"
                                           value={this.state.supervLastName}
                                           onChange={this.lastNameHandler}
                                           validations={[required, supervLastName]}
                                           maxLength="50"/>
                            </div>

                            <div className="modal-body">
                                    <span className="myModalField">Numéro Téléphone:</span>
                                    <br/>
                                    <Input type="text"
                                           className="form-control"
                                           name="supervPhoneNumber"
                                           value={this.state.supervPhoneNumber}
                                           onChange={this.phoneNumberHandler}
                                           validations={[supervPhoneNumber, supervPhoneNumberMax]}
                                           maxLength="50"/>
                            </div>

                            <div className="modal-body">
                                    <span className="myModalField">Email:</span>
                                    <br/>
                                    <Input Input type="text"
                                           className="form-control"
                                           name="supervEmail"
                                           value={this.state.supervEmail}
                                           onChange={this.emailHandler}
                                           validations={[required, supervEmail]}
                                           maxLength="50"/>
                            </div>

                            <div className="modal-footer">
                                <button className="btn btn-primary"
                                        disabled={this.state.isValid ? "" : "disabled"}>
                                    Save Changes
                                </button>

                                <button type="button"
                                className="btn btn-secondary"
                                data-dismiss="modal">Close</button>
                            </div>
                        </Form>

                    </div>
                </div>
            </div>
        );
    }
}

export default ModalSupervisor;
