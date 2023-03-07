import React, { Component } from "react";
import { Link } from "react-router-dom";
import AuthService from "../../services/auth.service";
import { CRow, CCol } from "@coreui/react";

import Modal from "react-modal";
import "react-datepicker/dist/react-datepicker.css";
import CIcon from "@coreui/icons-react";
import "../../css/style.css";
import Spinner from "react-bootstrap/Spinner";

const currentTeacher = AuthService.getCurrentTeacher();

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-30%, -70%)",
  },
};

//
export default class RenewTeacherPassword extends Component {
  constructor(props) {
    super(props);

    this.onChangeNewPassword = this.onChangeNewPassword.bind(this);
    this.onChangeRetypeNewPassword = this.onChangeRetypeNewPassword.bind(this);
    this.renewMyNewPassword = this.renewMyNewPassword.bind(this);
    this.closeModalRenewMyNewPassword = this.closeModalRenewMyNewPassword.bind(
        this
    );

    this.state = {
      teacherEntity: {},
      newPassword: "",
      retypeNewPassword: "",
      successRenewTeacherPassword: false,
      passwordsDontMatchErrorMsg: "",
      requiredPasswordLength: "",
      loadSecureMdp: false
    };




    let sessionPriv = "";
    if(currentTeacher !== null && currentTeacher.password.includes('$2a$'))
    {
        sessionPriv = "MODIFINSIDE";
        let requestTeaJWT = new XMLHttpRequest();
        requestTeaJWT.open("GET",API_URL_MESP + "encryptedPWDJWTByMailId/" + encodeURIComponent(currentTeacher.id) + "/" + encodeURIComponent(currentTeacher.idEns), false);
        requestTeaJWT.send(null);
        let pwdTeaJWT = requestTeaJWT.responseText;

        if(pwdTeaJWT === currentTeacher.password)
        {
            let requestb = new XMLHttpRequest();
            requestb.open(
                "GET",
                API_URL_MESP + "teacher/" + currentTeacher.id,
                false
            );
            requestb.send(null);
            this.state.teacherEntity = JSON.parse(requestb.responseText);
        }
        else
        {
          localStorage.clear();
          sessionStorage.clear();
          window.location.href = "/";
        }
    }
    else
    {
        sessionPriv = "MODIFOUTSIDE";
        let requestTea = new XMLHttpRequest();
        requestTea.open("GET",API_URL_MESP + "encryptedPWDByMailId/" + encodeURIComponent(encodeURIComponent(currentTeacher.id)) + "/" + encodeURIComponent(encodeURIComponent(currentTeacher.idEns)), false);
        requestTea.send(null);
        let pwdTea = requestTea.responseText;

        let requestTkn = new XMLHttpRequest();
        requestTkn.open("GET", API_URL_MESP + "encryptedPWDByMailIdPwd/" + encodeURIComponent(currentTeacher.id) + "/" + encodeURIComponent(currentTeacher.idEns) + "/" + encodeURIComponent(currentTeacher.password), false);
        requestTkn.send(null);
        let tkBack = requestTkn.responseText;

        if(pwdTea === tkBack)
        {
          let requestb = new XMLHttpRequest();
          requestb.open(
              "GET",
              API_URL_MESP + "teacher/" + currentTeacher.id,
              false
          );
          requestb.send(null);
          this.state.teacherEntity = JSON.parse(requestb.responseText);
        }
        else
        {
          localStorage.clear();
          sessionStorage.clear();
          window.location.href = "/";
        }
    }




    /*
    let requestTkn = new XMLHttpRequest();
    requestTkn.open("GET", API_URL_MESP + "encryptedPWDByMailIdPwd/" + encodeURIComponent(currentTeacher.id) + "/" + encodeURIComponent(currentTeacher.idEns) + "/" + encodeURIComponent(currentTeacher.password), false);
    requestTkn.send(null);
    let tkBack = requestTkn.responseText;
*/



    //console.log('-------------loli------ 0: ' + pwdTea);
    //console.log('-------------loli------ 1: ' + pwdTeaJWT);
    //console.log('--------------loli----- 2: ' + currentTeacher.password);

  }

  onChangeNewPassword(e) {
    if (e.target.value.length < 5 || e.target.value.length > 15) {
      this.setState({
        newPassword: e.target.value,
        requiredPasswordLength:
            "The Password should be between 5 and 15 characters.",
        retypeNewPassword: "",
        passwordsDontMatchErrorMsg: "",
      });
    } else {
      this.setState({
        newPassword: e.target.value,
        requiredPasswordLength: "Done, Confirmed Length.",
        retypeNewPassword: "",
        passwordsDontMatchErrorMsg: "",
      });
    }
  }

  onChangeRetypeNewPassword(e) {
    // console.log('------------------> Password: ' + this.state.newPassword)

    if (e.target.value !== this.state.newPassword) {
      this.setState({ passwordsDontMatchErrorMsg: "Passwords don't match." });
    }

    if (e.target.value === this.state.newPassword) {
      this.setState({
        passwordsDontMatchErrorMsg: "Ok, Passwords are confirmed.",
      });
    }

    this.setState({ retypeNewPassword: e.target.value });
  }

  renewMyNewPassword() {
    this.setState({loadSecureMdp: true});
    AuthService.secureTeacherPassword(
        currentTeacher.id,
        encodeURIComponent(this.state.retypeNewPassword)
    ).then(
        (response) => {
          // console.log('secure My Password ------------- 1: ' + this.state.currentTeacher.id + " - " + this.state.retypeNewPassword);
          this.setState({ successRenewTeacherPassword: true,
            loadSecureMdp: false
          });
        },
        (error) => {
          this.setState({
            content:
                (error.response && error.response.data) ||
                error.message ||
                error.toString(),
          });
        }
    );
  }

  closeModalRenewMyNewPassword(e) {
    this.setState({
      successRenewTeacherPassword: false,
    });
  }

  render() {
    const {
      teacherEntity,
      passwordsDontMatchErrorMsg,
      requiredPasswordLength,
      loadSecureMdp
    } = this.state;
    return (
        <>

          <div style={{ textAlign: "right" }}>
            <p className="upNote">
              Comme indiqué, pour des raisons de sécurité, en cas d'oubli, vous ne pouvez pas récupérer votre Mot de Passe actuel !.
            </p>
            <p className="upNote">
              Par conséquent, vous devez créer un nouveau qui sera crypté.
            </p>
          </div>
          <br /><br /><br />


          <CRow>
            <CCol md="2"/>

            <CCol md="10">
              <br/>
              <CRow>
                <CCol md="3">
                  <span className="convFieldLibelle">Nom Utilisateur :</span>
                </CCol>
                <CCol md="4">
                  <input
                      type="text"
                      disabled
                      className="form-control"
                      name="mailEns"
                      value={currentTeacher.id}
                  />
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="3">
                  <span className="convFieldLibelle">Nouvel Mot de Passe :</span>
                  <span className="requiredStar">&nbsp;(*)</span>
                </CCol>
                <CCol md="4">
                  <input
                      type="password"
                      className="form-control"
                      name="newPassword"
                      value={this.state.newPassword}
                      onChange={this.onChangeNewPassword}
                  />
                </CCol>
                <CCol md="5">
                  {requiredPasswordLength.includes("Done") && (
                      <p className="confirmPasswordsSuccess">
                        {requiredPasswordLength}
                      </p>
                  )}
                  {!requiredPasswordLength.includes("Done") && (
                      <p className="confirmPasswordsFail">{requiredPasswordLength}</p>
                  )}
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="3">
                  <span className="convFieldLibelle">Saisir à nouveau Mdp :</span>
                  <span className="requiredStar">&nbsp;(*)</span>
                </CCol>
                <CCol md="4">
                  <input
                      type="password"
                      className="form-control"
                      name="retypeNewPassword"
                      value={this.state.retypeNewPassword}
                      onChange={this.onChangeRetypeNewPassword}
                  />
                </CCol>
                <CCol md="5">
                  {passwordsDontMatchErrorMsg.includes("Ok") && (
                      <p className="confirmPasswordsSuccess">
                        {passwordsDontMatchErrorMsg}
                      </p>
                  )}
                  {!passwordsDontMatchErrorMsg.includes("Ok") && (
                      <p className="confirmPasswordsFail">
                        {passwordsDontMatchErrorMsg}
                      </p>
                  )}
                </CCol>
              </CRow>

              <br/><br/>
              <CRow>
                <CCol md="3"/>
                <CCol md="4">
                  <div className="float-right">
                    {
                      loadSecureMdp === false &&
                      <button
                          type="button"
                          className="btn btn-danger"
                          disabled={
                            (!passwordsDontMatchErrorMsg.includes("Ok") &&
                                !requiredPasswordLength.includes("Confirmed")) ||
                            (!passwordsDontMatchErrorMsg.includes("Ok") &&
                                requiredPasswordLength.includes("Confirmed")) ||
                            (passwordsDontMatchErrorMsg.includes("Ok") &&
                                !requiredPasswordLength.includes("Confirmed"))
                          }
                          onClick={() => {
                            this.renewMyNewPassword();
                          }}
                      >
                        Changer Mot de Passe
                      </button>
                    }
                    {
                      loadSecureMdp === true &&
                      <Spinner animation="grow" variant="danger"/>
                    }
                  </div>
                </CCol>
                <CCol md="5"/>
              </CRow>

            </CCol>

          </CRow>

          {this.state.successRenewTeacherPassword && (
              <Modal
                  isOpen={this.state.successRenewTeacherPassword}
                  contentLabel="Example Modal"
                  style={customStyles}
              >
                <CIcon name="cil-warning" style={{ color: "blue" }} />
                &nbsp;&nbsp;
                <span style={{ color: "blue" }}>Information</span>
                <hr />
                <center>
                  <br />

                  <span className="dialogMessage">
                  Vous avez déjà changé votre mot de passe !.
                </span>

                  <br />
                  <br />
                  <br />

                  <span className="note">
                  Remarque: ce nouveau mot de passe est privé et uniquement
                  connu de vous, vous ne pouvez donc pas le récupérer.
                </span>
                  <br />
                  <span className="note">
                  En cas d'oubli, vous ne pouvez que créer un autre.
                </span>

                  <br />
                  <br />
                  <br />
                  <Link to={"/teacher"}>
                    <button
                        className="btn btn-sm btn-success"
                        onClick={this.closeModalRenewMyNewPassword}
                    >
                      Ok, je suis d'accord.
                    </button>
                  </Link>
                  <br />
                  <br />
                </center>
              </Modal>
          )}

          <br />
          <br />
        </>
    );
  }
}
