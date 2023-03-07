import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import AuthService from "../../services/auth.service";
import AcademicEncadrantService from "../../services/academicEncadrant.service";

import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CDataTable,
  CFormGroup,
  CInputRadio,
  CLabel,
  CRow,
  CTooltip,
  CCardHeader,
  CContainer,
  CAlert
} from "@coreui/react";import SessionTimeout from "../../services/SessionTimeout";
import { Container, Box, Button } from "@material-ui/core";
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import axios from "axios";
import moment from "moment";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import styled, { keyframes, css } from "styled-components";

import ReactTextTransition from "react-text-transition";

import Spinner from "react-bootstrap/Spinner";
import "../../css/style.css";
import TextScrollerTeacher from "../../css/TextScrollerTeacher";
import TextScrollerPCAE from "../../css/TextScrollerPCAE";

import PhoneInput from "react-phone-input-2";
import "react-phone-input-2/lib/style.css";

import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const classes = {
  root: {
    display: "flex",
    flexDirection: "column",
    minHeight: "100vh",
  },
  cardContainer: {
    paddingBottom: 80,
    paddingTop: 80,
  },
};

const LoginButton = (props) => {
  return (
      <Button
          color="secondary"
          data-testid="submit"
          fullWidth
          size="large"
          onClick={props.onClick}
          variant="contained"
      >
        Log In
      </Button>
  );
};

//
const LogoutButton = (props) => {
  return (
      <Button
          color="secondary"
          fullWidth
          size="large"
          onClick={props.onClick}
          variant="contained"
      >
        Logout
      </Button>
  );
};

const currentTeacher = AuthService.getCurrentTeacher();

//
export default class TeacherProfile extends Component {
  constructor(props) {
    super(props);

    this.handleClick = this.handleClick.bind(this);
    this.changeMyPhoneNumber = this.changeMyPhoneNumber.bind(this);
    this.logout = this.logout.bind(this);
    this.openModalFillMyPhoneNbr = this.openModalFillMyPhoneNbr.bind(this);
    this.closeModalFillMyPhoneNbr = this.closeModalFillMyPhoneNbr.bind(this);
    this.openModalUpdateMyPhoneNbr = this.openModalUpdateMyPhoneNbr.bind(this);
    this.openModalConfirmMAJPhoneNbr = this.openModalConfirmMAJPhoneNbr.bind(this);
    this.closeModalUpdateMyPhoneNbr = this.closeModalUpdateMyPhoneNbr.bind(this);
    this.closeModalSuccessUpdateMyPhoneNbr = this.closeModalSuccessUpdateMyPhoneNbr.bind(this);


    this.state = {
      redirect: null,
      userReady: false,
      currentTeacher: { id: "" },
      userTEAEntity: {},
      isAuthenticated: true,
      button: null,
      teacherRole: "",
      openModalMAJPhone: false,
      confirmMAJPhone: false,
      successMAJPhone: false,
      loadSpinnerMAJPhoneNbr: false,
      fillPhoneNbr: false,
      initPhoneValue:  "--"

    };
  }

  logout(e) {
    // console.log("FUNC----------------------------out 1");

    // console.log("FUNC---------------------------- Responsible");
    AuthService.logoutResponsible();
    this.props.history.push("/");

    // console.log("FUNC----------------------------out 2");
  }

  openModalConfirmMAJPhoneNbr()
  {

  }

  openModalFillMyPhoneNbr()
  {
    this.setState({
      fillPhoneNbr : true,
      phoneVerify: "NO"
    })

    if(this.state.userTEAEntity.phone !== null)
    {
      this.setState({
        phoneVerify: "YES"
      })
    }

  }

  closeModalFillMyPhoneNbr()
  {
    this.setState({
      fillPhoneNbr : false
    })
  }

  openModalUpdateMyPhoneNbr()
  {
     this.setState({
       fillPhoneNbr: false,
       openModalMAJPhone : true
     })
  }

  closeModalUpdateMyPhoneNbr()
  {
    this.setState({
      openModalMAJPhone : false
    })
  }

  closeModalSuccessUpdateMyPhoneNbr()
  {
    this.setState({
      successMAJPhone : false
    })
  }

  changeMyPhoneNumber()
  {
    this.setState({
      loadSpinnerMAJPhoneNbr : true
    })
     console.log('-------------------> SARS 1: ' + this.state.telephone);
    let phoneNbr = this.state.telephone;
     AcademicEncadrantService.updateMyPhoneNumber(currentTeacher.idEns, phoneNbr).then(
         (response) => {
           this.setState({
             userTEAEntity: {},
             initPhoneValue: phoneNbr,
             loadSpinnerMAJPhoneNbr : false,
             confirmMAJPhone: false,
             openModalMAJPhone: false,
             successMAJPhone: true
           })


           let urlMe = API_URL_MESP + "teacherProfile/" + currentTeacher.id;
           // const AuthStr = 'Bearer '.concat(currentTeacher.token);
           axios.get(urlMe).then(response => {
             // If request is good...
             // console.log('____________0711_____#############_______HI: ', this.authHeader());
             // console.log('____________0711_____jNEW__hhh________HI: ', response.data);
             let obj = response.data;
             let phNbr = obj.phone;
             console.log("-CURRENT------------------------1--> AE", obj.phone);
             this.setState({
               currentTeacher: currentTeacher,
               userReady: true,
               userTEAEntity: obj,
               initPhoneValue: phNbr

             })
           })
           console.log('-------------------> SARS 2: ' + this.state.initPhoneValue);
         },
         (error) => {
           // console.log("Check Disponibility --------cr----- FAIL");
         }
     );
  }

  handleClick() {
    // console.log("----------------> NO 0");
    if (this.state.isAuthenticated === true) {
      this.setState({ isAuthenticated: false });
      this.logout();
    }

    if (this.state.isAuthenticated === false) {
      this.setState({ isAuthenticated: true });
    }

    if (this.state.isAuthenticated) {
      // console.log(  "------------------> ComponentDidMount 1: " + this.state.isAuthenticated );
      this.setState({
        button: <LoginButton onClick={this.handleClick} />,
      });
    } else {
      // console.log(b"------------------> ComponentDidMount 2: " + this.state.isAuthenticated);
      this.setState({
        button: <LogoutButton onClick={this.handleClick} />,
      });
    }
    // console.log("----------------> NO 1: " + this.state.isAuthenticated);
    // pwd = fullPwd.substring(0, fullPwd.lastIndexOf("$$$$$"));
  }

  authHeader() {
    const currentTeacher = AuthService.getCurrentTeacher();
    if (currentTeacher && currentTeacher.accessToken) {
      return { Authorization: 'Bearer ' + currentTeacher.accessToken };
    } else {
      return {};
    }
  }

  componentDidMount() {

    let requestAv = new XMLHttpRequest();
    requestAv.open("GET",API_URL_MESP + "pwdESPTea/" + encodeURIComponent(currentTeacher.id) + "/" + encodeURIComponent(currentTeacher.idEns), false);
    requestAv.send(null);
    let pwdTea = requestAv.responseText;

    let requestTkn = new XMLHttpRequest();
    requestTkn.open("GET", API_URL_MESP + "validateJWT/" + currentTeacher.token, false);
    requestTkn.send(null);
    let tkBack = requestTkn.responseText;

    let requestATkn = new XMLHttpRequest();
    requestATkn.open("GET", API_URL_MESP + "validateJWT/" + currentTeacher.accessToken, false);
    requestATkn.send(null);
    let atkBack = requestATkn.responseText;

    console.log('-------------------------> 0711 Profile: ' ,  moment(new Date()).format("hh:mm:ss"));

    /*
    console.log('--------dd-------------0711 Profile: ' , new Date());

    console.log('-------ff-token----0711--------- currentTeacher.id : ' , currentTeacher.token);
    console.log('--------mail----0711--------- currentTeacher.id : ' , currentTeacher.id);

    console.log('--------teaID----0711--------- id : ' , sessionStorage.getItem("teaID"));
    console.log('--------teaENS----0711--------- ens : ' , sessionStorage.getItem("teaENS"));

    console.log('____________0711_____PIKO 1________HI: ', pwdTea);
    console.log('____________0711_____PIKO 2________HI: ', currentTeacher.password);
    */
    //if(currentTeacher !== null && sessionStorage.getItem("teaID") === currentTeacher.id && sessionStorage.getItem("teaENS") === currentTeacher.idEns)
    if(currentTeacher !== null && pwdTea === currentTeacher.password && atkBack === 'YES' && tkBack === 'YES')
    {
      let accessToken = currentTeacher.token;
      let urlMe = API_URL_MESP + "teacherProfile/" + currentTeacher.id;
      // const AuthStr = 'Bearer '.concat(currentTeacher.token);
      axios.get(urlMe, { headers: {headers: {
            token: accessToken
          }} }).then(response => {
        // If request is good...
       // console.log('____________0711_____#############_______HI: ', this.authHeader());
       // console.log('____________0711_____jNEW__hhh________HI: ', response.data);
        let obj = response.data;
        let phNbr = obj.phone;
        console.log("-CURRENT------------------------1--> AE", obj.phone);
        this.setState({
          currentTeacher: currentTeacher,
          userReady: true,
          userTEAEntity: obj,
          initPhoneValue: phNbr

        })
        this.state.initPhoneValue = phNbr;
        console.log("-CURRENT-----------------------2---> AE", this.state.initPhoneValue);
      })
          .catch((error) => {
            console.log('error ' + error);
          });
      if (currentTeacher.codeOptions === null && currentTeacher.nbrAcademicEncadrement === 0)
      {
        // console.log("-CURRENT--------------------------> AE");
        this.setState({
          teacherRole: "NOTPCAE"
        });
      }

      if (currentTeacher.codeOptions === null && currentTeacher.nbrAcademicEncadrement !== 0)
      {
        // console.log("-CURRENT--------------------------> AE");
        this.setState({
          teacherRole: "AE"
        });
      }

      if (currentTeacher.codeOptions !== null && currentTeacher.nbrAcademicEncadrement === 0)
      {
        // console.log("-CURRENT--------------------------> PC");
        this.setState({
          teacherRole: "PC"
        });
      }

      if (currentTeacher.codeOptions !== null && currentTeacher.nbrAcademicEncadrement !== 0)
      {
        // console.log("-CURRENT--------------------------> AE & PC");
        this.setState({
          teacherRole: "AEandPC"
        });
      }
    }
    else
    {
      localStorage.clear();
      sessionStorage.clear();
      window.location.href = "/login";
    }

    // req.headers.authorization = 'my secret token';
    /* const headers = {
       'Authorization': 'Bearer ' + currentTeacher.token,
     };*/


    /*
    let accessToken = currentTeacher.token;
    let urlMe = API_URL_MESP + "teacherProfile/" + currentTeacher.id;
    // const AuthStr = 'Bearer '.concat(currentTeacher.token);
    axios.get(urlMe, { headers: this.authHeader() }).then(response => {
      // If request is good...
      console.log('____________0711_____#############_______HI: ', this.authHeader());
      console.log('____________0711_____jNEW__hhh________HI: ', response.data);
      this.setState({
        currentTeacher: currentTeacher,
        userReady: true,
        userTEAEntity: response.data

      })
    })
        .catch((error) => {
          console.log('error ' + error);
        });
*/

    /* M3
             axios.get(urlMe)
            .then(response => {
              // If request is good...
              console.log('____________0711_____jNEW__hhh________HI: ', response.data);
              this.setState({
                currentTeacher: currentTeacher,
                userReady: true,
                userTEAEntity: response.data

              })
            })
            .catch((error) => {
              console.log('error ' + error);
            });
    */

    /*
    const headers = {
      'Authorization': 'Bearer kjfgi'
    };

    axios.get(API_URL_MESP + "teacherProfile/" + currentTeacher.id, { headers })
        .then(response =>
            {
              console.log('____________0711_____j__________HI: ', response.data);
              this.setState({
                currentTeacher: currentTeacher,
                userReady: true,
                userTEAEntity: response.data

              })
            }
        );

     */

    /*
    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "teacherProfile/" +
        currentTeacher.id,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    //requestb.headers.authorization = currentTeacher.token;
    requestb.send(null);

    console.log('____________0711_______________HI: ', JSON.parse(requestb.responseText));

    this.setState({
      currentTeacher: currentTeacher,
      userReady: true,
      userTEAEntity: JSON.parse(requestb.responseText),
    });
    */

    //if (!currentTeacher) this.setState({ redirect: "/home" });


    // console.log("------------------------------------------opt------> 1: " + currentTeacher.codeOption);
    // console.log(this.state.userTEAEntity);
    // console.log("------------------------------------------------> 2");

    console.log("-CURRENT-----------------------3---> AE", this.state.initPhoneValue);


  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }

    const { currentTeacher, userTEAEntity, teacherRole, openModalMAJPhone, successMAJPhone, confirmMAJPhone, loadSpinnerMAJPhoneNbr, fillPhoneNbr } = this.state;

    return (
        < >
          {this.state.userReady ? (
              <>
                <br/><br/>
                <SessionTimeout />

                <CRow>
                  <CCol xs="2"/>
                  <CCol xs="8">
                    <CCard color="gradient-secondary">
                      <CCardHeader style={{ fontWeight: "bold", fontSize: "14px", color: "#cc0000" }}>
                        {(
                            teacherRole === "AEandPC" &&
                            <span style={{ fontWeight: "bold", fontSize: "15px", color: "#cc0000" }}>
                        <TextScrollerPCAE text="Coordinateur Pédagogique & Encadrant Académique" />
                      </span>
                        )}
                        {(
                            teacherRole === "AE" &&
                            <span style={{ fontWeight: "bold", fontSize: "15px", color: "#cc0000" }}>
                        <TextScrollerTeacher text="Encadrant Académique" />
                      </span>
                        )}
                        {(
                            teacherRole === "PC" &&
                            <span style={{ fontWeight: "bold", fontSize: "15px", color: "#cc0000" }}>
                        <TextScrollerTeacher text="Coordinateur Pédagogique" />
                      </span>
                        )}
                        {(
                            teacherRole === "NOTPCAE" &&
                            <span style={{ fontWeight: "bold", fontSize: "15px", color: "#cc0000" }}>
                        <TextScrollerTeacher text="Enseignant" />
                      </span>
                        )}
                      </CCardHeader>
                      <CCardBody>
                        <CRow>
                          <CCol md="3">
                            <CRow>
                              <CCol md="3">
                                <span className="idIcon"/>
                              </CCol>
                              <CCol md="9">
                                <span className="stuProfileLabel">Identifiant :</span>
                              </CCol>
                            </CRow>
                          </CCol>
                          <CCol md="9">
                            <span className="stuProfileInfo">{currentTeacher.idEns}</span>
                          </CCol>
                        </CRow>

                        <br/>
                        <CRow>
                          <CCol md="3">
                            <CRow>
                              <CCol md="3">
                                <span className="gmailIcon"/>
                              </CCol>
                              <CCol md="9">
                                <span className="stuProfileLabel">E-Mail :</span>
                              </CCol>
                            </CRow>
                          </CCol>
                          <CCol md="9">
                            <a
                                href="https://mail.google.com/"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                            <span className="stuProfileInfoMail">
                              <ins>{currentTeacher.id}</ins>
                            </span>
                            </a>
                          </CCol>
                        </CRow>

                        <br/>
                        <CRow>
                          <CCol md="3">
                            <CRow>
                              <CCol md="3">
                                <span className="phoneIcon" />
                              </CCol>
                              <CCol md="9">
                                <span className="stuProfileLabel">Téléphone :</span>
                              </CCol>
                            </CRow>
                          </CCol>
                          <CCol md="8">
                            <span className="stuProfileInfo">
                              {
                                userTEAEntity.phone !== null ?
                                    <>
                                      {userTEAEntity.phone}
                                    </>
                                    :
                                    <>--</>
                              }
                            </span>
                          </CCol>
                          <CCol md="1">
                            <CTooltip content="Mettre à Jour mon Numéro Téléphone" placement="top">
                              <span className="penRedIcon" onClick={() => {this.openModalFillMyPhoneNbr();}}/>
                            </CTooltip>
                          </CCol>
                        </CRow>

                      </CCardBody>
                    </CCard>
                    <CAlert color="info">

                      <h6 className="alert-heading"><VpnKeyIcon /> &nbsp;&nbsp; <strong>Détails Authentification</strong></h6>
                      <CRow>
                        <CCol md="6">
                          <span className="stuProfileNote">
                            Dernière Date Modification Mot de Passe :
                          </span>
                        </CCol>
                        <CCol md="6">
                          <span className="stuProfileNoteInfo">
                          {
                            userTEAEntity.dateModifyJwtPwd !== null ?
                                <>
                                  {userTEAEntity.dateModifyJwtPwd}
                                </>
                                :
                                <>--</>
                          }
                          </span>
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="6">
                          <span className="stuProfileNote">Token :</span>
                        </CCol>
                        <CCol md="6">
                          <span className="stuProfileNoteInfo">
                            {
                              currentTeacher.accessToken !== null ?
                                  <>
                                    {currentTeacher.accessToken.substring(0, 20)}
                                    ...
                                    {currentTeacher.accessToken.substr(currentTeacher.accessToken.length - 20)}
                                  </>
                                  :
                                  <>--</>
                            }
                          </span>
                        </CCol>
                      </CRow>
                    </CAlert>
                  </CCol>
                  <CCol md="2"/>
                </CRow>

                <Dialog fullHight fullWidth
                        maxWidth="sm"
                        keepMounted
                        open={fillPhoneNbr}
                        onClose={this.closeModalFillMyPhoneNbr}
                        aria-labelledby="form-dialog-title">
                  <DialogTitle id="form-dialog-title">
                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                    <hr/>
                  </DialogTitle>
                  <DialogContent>
                    <center>
                      <br/>
                      <p className="greyMarkForSelectComp">Merci d'insérer votre Numéro Téléphone</p>
                      <br/>
                        <CRow>
                        <CCol md="2"/>
                        <CCol>
                          <PhoneInput
                              country={"tn"}
                              name="telephone"
                              value={userTEAEntity.phone}
                              onChange={(telephone) => {
                                console.log('-----------------LOL 1: ' + telephone + " - " + telephone.length);
                                if(telephone.length > 10)
                                {
                                  this.setState({telephone, phoneVerify: "OK"})
                                  console.log('-----------------LOL 1.1: ' + telephone + " - " + this.state.phoneVerify);
                                }
                                else
                                {
                                  console.log('-----------------LOL 1.2: ' + telephone + " - " + this.state.phoneVerify);
                                  this.setState({telephone, phoneVerify: "NO"})
                                }
                              }}/>
                        </CCol>
                        <CCol md="2"/>
                      </CRow>
                      <br/><br/><br/>
                    </center>
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={this.openModalUpdateMyPhoneNbr} disabled = {this.state.phoneVerify === "NO"} color="primary">
                        Oui
                    </Button>
                    &nbsp;&nbsp;
                    <Button onClick={this.closeModalFillMyPhoneNbr} color="primary">
                      Non
                    </Button>
                  </DialogActions>
                </Dialog>

                <Dialog fullHight fullWidth
                        maxWidth="sm"
                        keepMounted
                        open={openModalMAJPhone}
                        onClose={this.openModalUpdateMyPhoneNbr}
                        aria-labelledby="form-dialog-title">
                  <DialogTitle id="form-dialog-title">
                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                    <hr/>
                  </DialogTitle>
                  <DialogContent>
                    <center>
                      <br/>
                      Êtes vous sûr(e)s de vouloir mettre à jour
                      <br/>
                      votre Numéro de Téléphone ?.
                      <br/><br/><br/>
                    </center>
                  </DialogContent>
                  <DialogActions>

                    {
                      loadSpinnerMAJPhoneNbr === true ?
                          <Spinner animation="grow" variant="primary"/>
                          :
                          <Button  color="primary" onClick={() => {this.changeMyPhoneNumber();}}>
                            Oui
                          </Button>
                    }
                    &nbsp;&nbsp;
                    <Button onClick={this.closeModalUpdateMyPhoneNbr} color="primary">
                      Non
                    </Button>
                  </DialogActions>
                </Dialog>

                <Dialog fullHight fullWidth
                        maxWidth="sm"
                        keepMounted
                        open={successMAJPhone}
                        onClose={this.closeModalSuccessUpdateMyPhoneNbr}
                        aria-labelledby="form-dialog-title">
                  <DialogTitle id="form-dialog-title">
                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                    <hr/>
                  </DialogTitle>
                  <DialogContent>
                    <center>
                      <br/>
                      Votre Mise À Jour a été effectuée avec succès.
                      <br/><br/><br/>
                    </center>
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={this.closeModalSuccessUpdateMyPhoneNbr} color="primary">
                      EXIT
                    </Button>
                  </DialogActions>
                </Dialog>

                <br />

                <br />
              </>
          ) : null}
        </>
    );
  }
}
