import React, { Component } from "react";
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";

import Modal from "react-modal";
import "react-datepicker/dist/react-datepicker.css";
import CIcon from "@coreui/icons-react";
import "../css/style.css";

const currentResponsableServiceStage =
  AuthService.getCurrentResponsableServiceStage();

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
export default class RenewResponsableServiceStagePassword extends Component {
  constructor(props) {
    super(props);

    this.onChangeNewPassword = this.onChangeNewPassword.bind(this);
    this.onChangeRetypeNewPassword = this.onChangeRetypeNewPassword.bind(this);
    this.renewMyNewPassword = this.renewMyNewPassword.bind(this);
    this.closeModalRenewMyNewPassword =
      this.closeModalRenewMyNewPassword.bind(this);

    this.state = {
      responsableServiceStageEntity: {},
      newPassword: "",
      retypeNewPassword: "",
      successRenewResponsableServiceStagePassword: false,
      passwordsDontMatchErrorMsg: "",
      requiredPasswordLength: "",
    };

    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      "http://localhost:8086/api/auth/responsableServiceStage/" +
        currentResponsableServiceStage.id,
      false
    );
    requestb.send(null);
    this.state.responsableServiceStageEntity = JSON.parse(
      requestb.responseText
    );
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
    AuthService.secureResponsableServiceStagePassword(
      this.state.responsableServiceStageEntity.idEt,
      this.state.retypeNewPassword
    ).then(
      (response) => {
        // console.log('secure My Password ------------- 1: ' + this.state.currentResponsableServiceStage.id + " - " + this.state.retypeNewPassword);
        this.setState({ successRenewResponsableServiceStagePassword: true });
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
      successRenewResponsableServiceStagePassword: false,
    });
  }

  render() {
    const {
      responsableServiceStageEntity,
      passwordsDontMatchErrorMsg,
      requiredPasswordLength,
    } = this.state;
    return (
      <div>
        <center>
          <strong>
            <span className="menuTitleRedNet">Espace Étudiant</span>
          </strong>
          <br />
          <span className="menuTitleNet">
            {responsableServiceStageEntity.nomet.trim()}{" "}
            {responsableServiceStageEntity.prenomet.trim()}
          </span>

          <br />
          <hr />
          <div style={{ textAlign: "right" }}>
            <p className="upNote">
              Comme indiqué, pour des raisons de sécurité, vous ne pouvez pas
              récupérer votre ancien mot de passe!.
            </p>
            <p className="upNote">
              Par conséquent, vous devez créer un nouveau mot de passe qui sera
              crypté.
            </p>
          </div>
          <br />

          <div className="twoHorizontalParts">
            <div style={{ width: 250 }} />
            <div style={{ width: 255 }}>
              <p>
                <span className="convFieldLibelle">Nom Utilisateur :</span>
              </p>
            </div>
            <div style={{ width: 450 }}>
              <input
                type="text"
                disabled
                className="form-control"
                name="idEt"
                value={responsableServiceStageEntity.idEt}
              />
            </div>
            <div style={{ width: 20 }} />
            <div style={{ width: 500 }} />
          </div>

          <br />

          <div className="twoHorizontalParts">
            <div style={{ width: 250 }} />
            <div style={{ width: 255 }}>
              <p>
                <span className="convFieldLibelle">Nouvel Mot de Passe :</span>
                <span className="requiredStar">&nbsp;(*)</span>
              </p>
            </div>
            <div style={{ width: 450 }}>
              <input
                type="password"
                className="form-control"
                name="newPassword"
                value={this.state.newPassword}
                onChange={this.onChangeNewPassword}
              />
            </div>
            <div style={{ width: 20 }} />
            <div style={{ width: 500 }}>
              {requiredPasswordLength.includes("Done") && (
                <p className="confirmPasswordsSuccess">
                  {requiredPasswordLength}
                </p>
              )}
              {!requiredPasswordLength.includes("Done") && (
                <p className="confirmPasswordsFail">{requiredPasswordLength}</p>
              )}
            </div>
          </div>

          <div className="twoHorizontalParts">
            <div style={{ width: 250 }} />
            <div style={{ width: 255 }}>
              <p>
                <span className="convFieldLibelle">Saisir à nouveau Mdp :</span>
                <span className="requiredStar">&nbsp;(*)</span>
              </p>
            </div>
            <div style={{ width: 450 }}>
              <input
                type="password"
                className="form-control"
                name="retypeNewPassword"
                value={this.state.retypeNewPassword}
                onChange={this.onChangeRetypeNewPassword}
              />
            </div>
            <div style={{ width: 20 }} />
            <div style={{ width: 500 }}>
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
            </div>
          </div>

          <br />
          <br />

          <div className="twoHorizontalParts">
            <div style={{ width: 250 }} />
            <div style={{ width: 255 }} />
            <div style={{ width: 450, textAlign: "right" }}>
              <button
                type="button"
                className="btn btn-primary"
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
            </div>
            <div style={{ width: 20 }} />
            <div style={{ width: 500 }} />
          </div>

          {this.state.successRenewResponsableServiceStagePassword && (
            <Modal
              isOpen={this.state.successRenewResponsableServiceStagePassword}
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
                <Link to={"/responsableServiceStage"}>
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
        </center>
      </div>
    );
  }
}
