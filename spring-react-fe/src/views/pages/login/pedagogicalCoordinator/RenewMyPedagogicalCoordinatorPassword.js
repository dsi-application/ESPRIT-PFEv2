import React, { Component } from "react";
import { Link } from "react-router-dom";
import AuthService from "../../../services/auth.service";
import { CTooltip, CButton, CCard, CCardBody, CCardGroup, CCol, CContainer, CForm, CInput, CInputGroup, CInputGroupPrepend, CInputGroupText, CRow } from '@coreui/react';
//import Tour from "reactour";
import { freeSet } from '@coreui/icons';
import Modal from "react-modal";
import "react-datepicker/dist/react-datepicker.css";
import CIcon from "@coreui/icons-react";
import "../../../css/style.css";
import Spinner from "react-bootstrap/Spinner";
import esprit from "../../../images/esprit.png";
import Text from "antd/lib/typography/Text";
import Tour from "reactour";
import PersonIcon from '@material-ui/icons/Person';

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

const steps = [
  {
    selector: '[data-tut="step1"]',
    content: () => (
        <Text>
          <span className="pinkAlert">Madame/Monsieur,</span><br/>
          <span className="pinkAlert">Vous êtes</span>&nbsp;
          <span className="pinkAlertBold">Chef(fe) Département ou Coordinateur(trice) Pédagogique</span>&nbsp;
          <span className="pinkAlert">?.</span>
          <br/><br/>
          Vous êtes la/le Bienvenue sur notre Plateforme :)
        </Text>
    ),
  },
  {
    selector: '[data-tut="step2"]',
    content: () => (
        <Text>
          En tant que&nbsp;
          <span className="pinkAlertBold">CD ou CPS</span>&nbsp;:
          <br/><br/>
          Veuillez insérer le <span className="purpleMarkCourrier">Token</span>
          <br/>
          que vous avez réçu par Mail.
        </Text>
    ),
  },
  {
    selector: '[data-tut="step3"]',
    content: () => (
        <Text>
          En tant que&nbsp;
          <span className="pinkAlertBold">CD ou CPS</span>&nbsp;:
          <br/><br/>
          Merci d'insérer un <span className="purpleMarkCourrier">Mot de Passe</span>
          <br/>
          selon votre propre choix.
        </Text>
    )
  },
  {
    selector: '[data-tut="step4"]',
    content: () => (
        <Text>
          En tant que&nbsp;
          <span className="pinkAlertBold">CD ou CPS</span>&nbsp;:
          <br/><br/>
          Vous êtes invité(e)s à re-taper votre&nbsp;<span className="purpleMarkCourrier">nouveau</span>&nbsp;Mot de Passe.
        </Text>
    )
  },
  {
    selector: '[data-tut="step5"]',
    content: () => (
        <Text>
          En tant que&nbsp;
          <span className="pinkAlertBold">CD ou CPS</span>&nbsp;:
          <br/><br/>
          Et maintenant, cliquer sur ce bouton pour <span className="purpleMarkCourrier">confirmer</span> votre requête.
        </Text>
    )
  },
  {
    selector: '[data-tut="step6"]',
    content: () => (
        <Text>
          <center>
            <span className="pinkAlertBold">Bonne visite</span>
            <br/>
            <span className="pinkAlertBold">À très bientôt :)</span>
          </center>
        </Text>
    )
  }
];

//
export default class RenewMyPedagogicalCoordinatorPassword extends Component {
  constructor(props) {
    super(props);

    this.onChangeNewPassword = this.onChangeNewPassword.bind(this);
    this.onChangeRetypeNewPassword = this.onChangeRetypeNewPassword.bind(this);
    this.renewMyNewPassword = this.renewMyNewPassword.bind(this);
    this.closeModalRenewMyNewPassword = this.closeModalRenewMyNewPassword.bind(
        this
    );
    this.onChangeToken = this.onChangeToken.bind(this);
    this.handleReadMe = this.handleReadMe.bind(this);


    this.state = {
      teacherEntity: {},
      newPassword: "",
      retypeNewPassword: "",
      successRenewTeacherPassword: false,
      passwordsDontMatchErrorMsg: "",
      requiredPasswordLength: "",
      loadSecureMdp: false,
      token : "",
      tourOpen: false
    };


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

  onChangeToken(e) {
    this.setState({ token: e.target.value });
    this.state.token = e.target.value;
    console.log('------------------> token: ' + this.state.token)
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
    console.log('secure My Password ----HIHIHI--------- 1');

    AuthService.resetMyCDCPSPassword(
        encodeURIComponent(encodeURIComponent(encodeURIComponent(this.state.token))),
        encodeURIComponent(encodeURIComponent(encodeURIComponent(this.state.retypeNewPassword))),
    ).then(
        (response) => {
          console.log('secure My Password ----HIHIHI--------- 2: ' + response.data);
          this.setState({
              successRenewTeacherPassword: true,
              loadSecureMdp: false,
              tokenStatus: response.data
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

  handleReadMe(e) {
    this.setState({
      tourOpen : true
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
          <div>
            <div className="c-app c-default-layout flex-row align-items-center">
              <CContainer>
                <CRow className="justify-content-center">
                  <CCol md="12">
                    <CCardGroup>
                      <CCard className="p-4">
                        <CCardBody>

                            <CRow>
                              <CCol md="9">
                                <h2>Changer mon Mot de Passe</h2>
                              </CCol>
                              <CCol md="3">
                                <span className="readMeIcon" onClick={() => {this.handleReadMe()}}/>
                              </CCol>
                            </CRow>

                            <Tour  steps={steps}
                                   isOpen={this.state.tourOpen}
                                   onRequestClose={() => {this.setState({tourOpen: false});}}/>

                          <br/><br/>
                              <CRow>
                                <CCol md="5">
                                  <span className="convFieldLibelle">Token :</span>
                                  <span className="requiredStar">&nbsp;(*)</span>
                                </CCol>
                                <CCol md="7" className="colPadding">
                                  <input data-tut="step2"
                                      className="form-control"
                                      name="token"
                                      value={this.state.token}
                                      onChange={this.onChangeToken}
                                  />
                                </CCol>
                              </CRow>

                              <br/>
                              <CRow>
                                <CCol md="5">
                                  <span className="convFieldLibelle">Nouvel Mot de Passe :</span>
                                  <span className="requiredStar">&nbsp;(*)</span>
                                </CCol>
                                <CCol md="3" className="colPadding">
                                  <input data-tut="step3"
                                      type="password"
                                      className="form-control"
                                      name="newPassword"
                                      value={this.state.newPassword}
                                      onChange={this.onChangeNewPassword}
                                  />
                                </CCol>
                                <CCol md="4" className="colPaddingRight">
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
                                <CCol md="5">
                                  <span className="convFieldLibelle">Saisir à nouveau Mdp :</span>
                                  <span className="requiredStar">&nbsp;(*)</span>
                                </CCol>
                                <CCol md="3" className="colPadding">
                                  <input data-tut="step4"
                                      type="password"
                                      className="form-control"
                                      name="retypeNewPassword"
                                      value={this.state.retypeNewPassword}
                                      onChange={this.onChangeRetypeNewPassword}
                                  />
                                </CCol>
                                <CCol md="4" className="colPaddingRight">
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

                              <br/><br/><br/>
                              <CRow>
                                <CCol/>
                                <CCol>
                                  {
                                    loadSecureMdp === false &&
                                    <button data-tut="step5"
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
                                </CCol>
                                <CCol/>
                              </CRow>

                          {this.state.successRenewTeacherPassword && (
                              <Modal
                                  isOpen={this.state.successRenewTeacherPassword}
                                  contentLabel="Example Modal"
                                  style={customStyles}
                              >
                                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                <hr/>
                                  <center>
                                      {
                                          this.state.tokenStatus === "TOKENEXIST" &&
                                          <>
                                              <br/>
                                              <span className="greenAlert">Congratulations,</span>
                                              <br/>
                                              Votre Mot de Passe a été modifiée avec <span className="greenAlert">succès</span> !.
                                              <br/>
                                              Vous pouvez accéder de nouveau à&nbsp;
                                              <Link to="/pedagogicalCoordinator">
                                                  <CButton color="link" className="px-0" data-tut="step4">votre Espace</CButton>
                                              </Link>.
                                              <br/><br/><br/>
                                              <Link to={"/pedagogicalCoordinator"}>
                                                  <button
                                                      className="btn btn-sm btn-success"
                                                      onClick={this.closeModalRenewMyNewPassword}
                                                  >
                                                      Ok, je vais y accéder                                                                           </button>
                                              </Link>
                                          </>
                                      }
                                      {
                                          this.state.tokenStatus === "TOKENINVALID" &&
                                          <>
                                              <br/>
                                              <span className="redAlert">Désolé,</span>
                                              <br/>
                                              Le Token que vous avez saisi est <span className="redAlert">INVALIDE</span> !.
                                              <br/><br/><br/>

                                              <div style={{textAlign: 'left'}} className="dimGreyNote">
                                                  <strong>Détails :</strong>
                                                  <br/>
                                                  La non validité du ce Token <ins>inséré</ins> est due à :
                                                  <br/>
                                                  - Soit il est <span className="dimRedNote">incorrect</span>, alors vous êtes invité(e)s à bien vérifier votre dernier mail réçu.
                                                  <br/>&nbsp;&nbsp;ou<br/>
                                                  - Il est <span className="dimRedNote">déjà utilisé</span> par vous précédement. Et dans ce cas, vous devez créer un nouveau via&nbsp;
                                                  <a href="https://pfe.esprit.tn/forgotPedagogicalPassword" target="_blank" rel="linkFP" style={{color:'blue'}}>Mot de passe oublié</a>.

                                              </div>

                                              <br/><br/><br/>

                                              <button
                                                  className="btn btn-sm btn-danger"
                                                  onClick={() => {this.setState({
                                                      successRenewTeacherPassword: false
                                                  });
                                                      this.state.token = "";
                                                      this.state.newPassword = "";
                                                      this.state.retypeNewPassword = "";
                                                  }}
                                              >
                                                  Ok, je vais vérifier.
                                              </button>
                                          </>
                                      }
                                      {
                                          this.state.tokenStatus === "TOKENEXPIRED" &&
                                          <>
                                              <br/>
                                              <span className="redAlert">Désolé,</span>
                                              <br/>
                                              Comme convenu, la validité du Token est limitée pour juste 30 minutes.
                                              <br/>
                                              Pour la présente, ç'a été <span className="redAlert">expirée</span> !.
                                              <br/><br/>
                                              Vous êtes invité(e)s à créer un nouveau via&nbsp;
                                              <a href="https://pfe.esprit.tn/forgotPedagogicalPassword" target="_blank" rel="linkFP" style={{color:'blue'}}>Mot de passe oublié !</a>

                                              <br/><br/><br/>
                                              <Link to={"/forgotPedagogicalPassword"}>
                                                  <button
                                                      className="btn btn-sm btn-danger"
                                                      onClick={this.closeModalRenewMyNewPassword}
                                                  >
                                                      Ok, je vais essayer                                                                           </button>
                                              </Link>
                                          </>
                                      }
                                      <br />
                                      <br />
                                  </center>
                              </Modal>
                          )}

                          <br />
                          <br />

                        </CCardBody>
                      </CCard>
                      <CCard className="text-white bg-light py-4">
                        <CCardBody className="text-center">
                          <div>
                            <CRow>
                              <CCol xs={8}></CCol>
                              <CCol xs={4}>
                                <img src={esprit} alt="logo" title="Welcome to Esprit" height={50} width={100} data-tut="step1"/>
                              </CCol>
                            </CRow>

                            <br/><br/>
                            <span className="platformTitle">Gestion des Stages</span>

                            <br/>
                              <p className="userSpace"><p className="userSpace">Espace Chef(fe) de Département</p>
                                  -
                                  <br/><p>Coordinateur Pédagogique</p></p>

                            <br/>
                            <Link to="/">
                              <CButton shape="pill" color="danger" data-tut="step6">
                                <CTooltip content="Page d'Accueil" placement="top">
                                  <CIcon content={freeSet.cilHome}/>
                                </CTooltip>
                              </CButton>
                            </Link>
                          </div>
                        </CCardBody>
                      </CCard>
                    </CCardGroup>
                  </CCol>
                </CRow>
              </CContainer>
            </div>
          </div>

        </>
    );
  }
}
