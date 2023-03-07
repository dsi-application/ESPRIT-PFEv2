import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import AuthService from "../../services/auth.service";

import { CAlert, CRow, CCol, CCard, CCardHeader, CCardBody } from "@coreui/react";
import SessionTimeout from "../../services/SessionTimeout";
import { Container, Box, Button } from "@material-ui/core";
import VpnKeyIcon from '@material-ui/icons/VpnKey';

import "../../css/style.css";
import TextScrollerRSS from "../../css/TextScrollerRSS";
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
export default class ResponsableServiceStageProfile extends Component {
  constructor(props) {
    super(props);

    this.handleClick = this.handleClick.bind(this);
    this.logout = this.logout.bind(this);

    this.state = {
      redirect: null,
      userReady: false,
      currentResponsableServiceStage: { id: "" },
      userRSSEntity: {},
      isAuthenticated: true,
      button: null,
    };
  }

  logout(e) {
    // console.log("FUNC----------------------------out 1");

    // console.log("FUNC---------------------------- Responsible");
    AuthService.logoutResponsible();
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
      // console.log("------------------> ComponentDidMount 2: " + this.state.isAuthenticated);
      this.setState({
        button: <LogoutButton onClick={this.handleClick} />,
      });
    }
    // console.log("----------------> NO 1: " + this.state.isAuthenticated);
  }

  componentDidMount() {
    const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();

    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "responsableServiceStage/" +
        currentResponsableServiceStage.id,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);

    // console.log("--------------------------------> Result: " +currentResponsableServiceStage.id);

    if (!currentResponsableServiceStage) this.setState({ redirect: "/home" });
    this.setState({
      currentResponsableServiceStage: currentResponsableServiceStage,
      userReady: true,
      userRSSEntity: JSON.parse(requestb.responseText),
    });

    /*
        if (this.state.isAuthenticated)
        {
            console.log('------------------> ComponentDidMount 1: ' + this.state.isAuthenticated);
            this.setState({
                button : <LogoutButton onClick={this.handleClick}/>,
            });
        }
        else
        {
            console.log('------------------> ComponentDidMount 2: ' + this.state.isAuthenticated);
            this.setState({
                button : <LoginButton onClick={this.handleClick}/>,
            });
        }
        */
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }

    const { currentResponsableServiceStage, userRSSEntity } = this.state;

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
                      currentResponsableServiceStage.id.includes("IT") &&
                        <TextScrollerTeacher text="RSS Informatique & Télécommunication" />
                    )}
                    {(
                      currentResponsableServiceStage.id.includes("EM") &&
                        <TextScrollerRSS text="RSS Électro-Mécanique" />
                    )}
                    {(
                      currentResponsableServiceStage.id.includes("GC") &&
                      <TextScrollerRSS text="RSS Génie Civil" />
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
                        {userRSSEntity.mailUserSce === null && <span className="stuProfileInfo">--</span>}
                        {userRSSEntity.mailUserSce !== null && (
                          <a
                            href="https://mail.google.com/"
                            target="_blank"
                            rel="noopener noreferrer"
                          >
                        <span className="stuProfileInfoMail">
                          <ins>{userRSSEntity.mailUserSce}</ins>
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
                          userRSSEntity.telUserSce !== null ?
                              <>
                                {userRSSEntity.telUserSce}
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
                        userRSSEntity.dateModifyJwtPwd !== null ?
                            <>
                              {userRSSEntity.dateModifyJwtPwd}
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
                        currentResponsableServiceStage.accessToken !== null ?
                            <>
                              {currentResponsableServiceStage.accessToken.substring(0, 20)}
                              ...
                              {currentResponsableServiceStage.accessToken.substr(currentResponsableServiceStage.accessToken.length - 20)}
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
