import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import AuthService from "../../services/auth.service";

import { CAlert, CRow, CCol, CCard, CCardHeader, CCardBody } from "@coreui/react";
import SessionTimeout from "../../services/SessionTimeout";
import { Container, Box, Button } from "@material-ui/core";
import VpnKeyIcon from '@material-ui/icons/VpnKey';

import "../../css/style.css";
import TextScrollerRSS from "../../css/TextScrollerRSS";
import TextScrollerPC from "../../css/TextScrollerPC";
import TextScrollerTeacher from "../../css/TextScrollerTeacher";

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

//
export default class PedagogicalCoordinatorProfile extends Component {
  constructor(props) {
    super(props);

    this.handleClick = this.handleClick.bind(this);
    this.logout = this.logout.bind(this);

    this.state = {
      redirect: null,
      userReady: false,
      currentPedagogicalCoordinator: { id: "" },
      userPCEntity: {},
      isAuthenticated: true,
      button: null,
      pedagogicalCoordinatorRole: ""
    };
  }

  logout(e) {
    // console.log("FUNC----------------------------out 1");

    // console.log("FUNC---------------------------- Responsible");
    AuthService.logoutPedagogicalCoordinator();
    this.props.history.push("/");

    // console.log("FUNC----------------------------out 2");
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
      // console.log("------------------> ComponentDidMount 1: " + this.state.isAuthenticated);
      this.setState({
        button: <LoginButton onClick={this.handleClick} />,
      });
    } else {
      // console.log( "------------------> ComponentDidMount 2: " + this.state.isAuthenticated);
      this.setState({
        button: <LogoutButton onClick={this.handleClick} />,
      });
    }
    // console.log("----------------> NO 1: " + this.state.isAuthenticated);
  }

  componentDidMount() {
    const currentPedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();

    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "pedagogicalCoordinator/" +
        currentPedagogicalCoordinator.id,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);

    // console.log("--------------------------------> Result: " + currentPedagogicalCoordinator.id);

    if (!currentPedagogicalCoordinator) this.setState({ redirect: "/home" });

    let pcMail = currentPedagogicalCoordinator.id;

    let pcOption = "";
    if(pcMail.includes("CPS_") || pcMail.includes("CPS-"))
    {
      pcOption = "Coordinateur Pédagogique " + pcMail.substring(4, pcMail.lastIndexOf("@esprit.tn"));
    }

    if(pcMail.includes("CD_INF"))
    {
      pcOption = "Chef Département Informatique";
    }

    if(pcMail.includes("CD_TEL"))
    {
      pcOption = "Chef Département Télécommunications";
    }

    if(pcMail.includes("CD_EM"))
    {
      pcOption = "Chef Département Électromécanique";
    }

    if(pcMail.includes("CD_GC"))
    {
      pcOption = "Chef Département Génie Civil";
    }

    if(pcMail.includes("CD-S-Tic"))
    {
      pcOption = "Chef Département TIC";
    }

    if(pcMail.includes("CD-S-GC-EM"))
    {
      pcOption = "Chef Département EM & GC";
    }

    if(pcMail.includes("CD-Alt"))
    {
      pcOption = "Chef Département Alternances";
    }



    this.setState({
      currentPedagogicalCoordinator: currentPedagogicalCoordinator,
      userReady: true,
      userPCEntity: JSON.parse(requestb.responseText),
      pedagogicalCoordinatorRole: pcOption
    });

  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }

    const { currentPedagogicalCoordinator, userPCEntity, pedagogicalCoordinatorRole } = this.state;

    return (
      <>
        {this.state.userReady ? (
          <>

            <br /><br/>
            <CRow>
              <CCol md="2"/>
              <CCol md="8">
                <CCard color="gradient-secondary">
                  <CCardHeader style={{ fontWeight: "bold", fontSize: "14px", color: "#cc0000" }}>
                    {(
                      currentPedagogicalCoordinator &&
                      <TextScrollerPC text={pedagogicalCoordinatorRole} />
                    )}
                  </CCardHeader>
                  <CCardBody>
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
                        {currentPedagogicalCoordinator.id === null && <span className="stuProfileInfo">--</span>}
                        {currentPedagogicalCoordinator.id !== null && (
                          <a
                            href="https://mail.google.com/"
                            target="_blank"
                            rel="noopener noreferrer"
                          >
                        <span className="stuProfileInfoMail">
                          <ins>{currentPedagogicalCoordinator.id}</ins>
                        </span>
                          </a>
                        )}
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
                      <CCol md="9">
                        <span className="stuProfileInfo">
                        {
                          userPCEntity.numPhone !== null ?
                              <>
                                {userPCEntity.numPhone}
                              </>
                              :
                              <>--</>
                        }
                        </span>
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
                        userPCEntity.dateModifyJwtPwd !== null ?
                            <>
                              {userPCEntity.dateModifyJwtPwd}
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
                        currentPedagogicalCoordinator.accessToken !== null ?
                            <>
                              {currentPedagogicalCoordinator.accessToken.substring(0, 20)}
                              ...
                              {currentPedagogicalCoordinator.accessToken.substr(currentPedagogicalCoordinator.accessToken.length - 20)}
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

            <br />
          </>
        ) : null}
      </>
    );
  }
}
