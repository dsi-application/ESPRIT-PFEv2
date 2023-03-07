import React, { Component } from "react";
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";

import Modal from "react-modal";
import "react-datepicker/dist/react-datepicker.css";
import { CRow, CCol } from "@coreui/react";
import CIcon from "@coreui/icons-react";
import "../css/style.css";
//
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
export default class SecureResponsableServiceStagePassword extends Component {
  constructor(props) {
    super(props);

    this.onChangeNewPassword = this.onChangeNewPassword.bind(this);
    this.onChangeRetypeNewPassword = this.onChangeRetypeNewPassword.bind(this);
    this.secureMyNewPassword = this.secureMyNewPassword.bind(this);
    this.closeModalSecureMyNewPassword =
      this.closeModalSecureMyNewPassword.bind(this);

    this.state = {
      redirect: null,
      currentResponsableServiceStage: { id: "", password: "" },
      responsableServiceStageEntity: {},
      newPassword: "",
      retypeNewPassword: "",
      successSecureResponsableServiceStagePassword: false,
      passwordsDontMatchErrorMsg: "",
      requiredPasswordLength: "",
    };
  }

  componentDidMount() {
    const currentResponsableServiceStage =
      AuthService.getCurrentResponsableServiceStage();

    // console.log('---------------> 1: ' + currentResponsableServiceStage.id + " - " + currentResponsableServiceStage.password);

    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      "http://localhost:8086/api/auth/responsableServiceStage/" +
        currentResponsableServiceStage.id,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestb.send(null);

    // console.log('---------------> 2: ' + currentResponsableServiceStage.id);

    if (!currentResponsableServiceStage) this.setState({ redirect: "/home" });
    this.setState({
      currentResponsableServiceStage: currentResponsableServiceStage,
      responsableServiceStageEntity: JSON.parse(requestb.responseText),
    });
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

  secureMyNewPassword() {
    AuthService.secureResponsableServiceStagePassword(
      this.state.currentResponsableServiceStage.id,
      this.state.retypeNewPassword
    ).then(
      (response) => {
        // console.log('secure My Password ------------- 1: ' + this.state.currentResponsableServiceStage.id + " - " + this.state.retypeNewPassword);
        this.setState({ successSecureResponsableServiceStagePassword: true });
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

  closeModalSecureMyNewPassword(e) {
    this.setState({
      successSecureResponsableServiceStagePassword: false,
    });
  }

  render() {
    const {
      currentResponsableServiceStage,
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
          <span className="menuTitleRedNet">
            {responsableServiceStageEntity.nomet}
            {responsableServiceStageEntity.prenomet}
          </span>
        </center>
        <br />
        <hr />

        <div style={{ textAlign: "right" }}>
          <p className="upNote">
            Pour sécuriser votre espace contre toute attaque, vous devez créer
            un nouveau mot de passe qui sera crypté.
          </p>
        </div>

        <br />

        <CRow>
          <CCol xs="1" />
          <CCol xs="10">
            <div className="twoHorizontalParts">
              <div style={{ width: 250 }} />
              <div style={{ width: 500 }}>
                <p>
                  <span className="convFieldLibelle">
                    Mot de Passe ancient :
                  </span>
                </p>
              </div>
              <div style={{ width: 700 }}>
                <input
                  type="text"
                  disabled
                  className="form-control"
                  name="newPassword"
                  value={currentResponsableServiceStage.password}
                />
              </div>
              <div style={{ width: 20 }} />
              <div style={{ width: 700 }} />
            </div>

            <br />

            <div className="twoHorizontalParts">
              <div style={{ width: 250 }} />
              <div style={{ width: 500 }}>
                <p>
                  <span className="convFieldLibelle">Nouvel Mot de Passe</span>
                  <span className="requiredStar">&nbsp;(*) :</span>
                </p>
              </div>
              <div style={{ width: 700 }}>
                <input
                  type="password"
                  className="form-control"
                  name="newPassword"
                  value={this.state.newPassword}
                  onChange={this.onChangeNewPassword}
                />
              </div>
              <div style={{ width: 20 }} />
              <div style={{ width: 700 }}>
                {requiredPasswordLength.includes("Done") && (
                  <p className="confirmPasswordsSuccess">
                    {requiredPasswordLength}
                  </p>
                )}
                {!requiredPasswordLength.includes("Done") && (
                  <p className="confirmPasswordsFail">
                    {requiredPasswordLength}
                  </p>
                )}
              </div>
            </div>

            <div className="twoHorizontalParts">
              <div style={{ width: 250 }} />
              <div style={{ width: 500 }}>
                <p>
                  <span className="convFieldLibelle">
                    Saisir à nouveau votre Mdp
                  </span>
                  <span className="requiredStar">&nbsp;(*) :</span>
                </p>
              </div>
              <div style={{ width: 700 }}>
                <input
                  type="password"
                  className="form-control"
                  name="retypeNewPassword"
                  value={this.state.retypeNewPassword}
                  onChange={this.onChangeRetypeNewPassword}
                />
              </div>
              <div style={{ width: 20 }} />
              <div style={{ width: 700 }}>
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
              <div style={{ width: 500 }} />
              <div style={{ width: 700, textAlign: "right" }}>
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
                    this.secureMyNewPassword();
                  }}
                >
                  Sécuriser Mot de Passe
                </button>
              </div>
              <div style={{ width: 20 }} />
              <div style={{ width: 700 }} />
            </div>

            {this.state.successSecureResponsableServiceStagePassword && (
              <Modal
                isOpen={this.state.successSecureResponsableServiceStagePassword}
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
          </CCol>
          <CCol xs="1" />
        </CRow>
      </div>
    );
  }
}
