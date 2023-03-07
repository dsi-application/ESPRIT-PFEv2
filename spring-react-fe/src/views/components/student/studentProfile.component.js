import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import AuthService from "../../services/auth.service";
import { CAlert, CCardGroup, CCard, CCardHeader, CCardBody, CRow, CCol } from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Icon } from '@iconify/react';

import MailIcon from '@material-ui/icons/Mail';
import HomeIcon from '@material-ui/icons/Home';
import PhoneAndroidIcon from '@material-ui/icons/PhoneAndroid';
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import "../../css/style.css";
//import { Wave1, Wave2, Random1, Random2 } from './example.js';
import { Wave, Random } from "react-animated-text";

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;
export default class StudentProfile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentStudent: {id: ""},
      studentEntity: {},
      mailStudent: "",
      studentHierarchy: {}
    }
  }

  componentDidMount() {
    const currentStudent = AuthService.getCurrentStudent();

    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "studentSpeed/" + currentStudent.id,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);

    var requestsh = new XMLHttpRequest();
    requestsh.open(
      "GET",
      API_URL_STU + "studentHierarchy/" + currentStudent.id,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestsh.send(null);

    // console.log('--------------------------------> Result: ' + currentStudent.id);

    if (!currentStudent) this.setState({ redirect: "/home" });
    this.setState({
      currentStudent: currentStudent,
      userReady: true,
      studentEntity: JSON.parse(requestb.responseText),
      studentHierarchy: JSON.parse(requestsh.responseText)
    });
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }

    const { currentStudent, studentEntity, studentHierarchy } = this.state;

    return (
      <div className="container">
        {this.state.userReady ? (
          <div>

            <CRow>
              <CCol md="12">
                <CCard accentColor="danger">
                  <CCardHeader style={{ fontWeight: "bold", fontSize: "14px", color: "#cc0000" }}>
                    Votre Encadrant Académique &nbsp;&nbsp;&nbsp;
                    {
                      studentHierarchy.pedagogicalEncadrant === "--" ?
                          <>
                            <br/>
                            <span className="greyMarkCourrierSmalLabel">Not Yet affected Supervisor</span>
                          </>
                          :
                          <Wave style={{fontSize:"50px"}} text={studentHierarchy.pedagogicalEncadrant} effect="fadeOut" effectChange={1.0} />
                    }

                  </CCardHeader></CCard>
              </CCol>
            </CRow>

            <br/>
            <CRow>
              <CCol md="8">
                <CCard color="gradient-secondary">
                  <CCardHeader
                    style={{ fontWeight: "bold", fontSize: "14px", color: "#cc0000" }}>
                    {studentEntity.fullName}
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
                        {studentEntity.adressemailesp === null && <span className="stuProfileInfo">--</span>}
                        {studentEntity.adressemailesp !== null && (
                          <a
                            href="https://mail.google.com/"
                            target="_blank"
                            rel="noopener noreferrer"
                          >
                        <span className="stuProfileInfoMail">
                          <ins>{studentEntity.adressemailesp}</ins>
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
                          studentEntity.telet !== null ?
                              <>
                                {studentEntity.telet}
                              </>
                              :
                              <>--</>
                        }
                        </span>
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="3">
                        <CRow>
                          <CCol md="3">
                            <span className="homeIcon" />
                          </CCol>
                          <CCol md="9">
                            <span className="stuProfileLabel">Adresse :</span>
                          </CCol>
                        </CRow>
                      </CCol>
                      <CCol md="9">
                        {studentEntity.adresseet === null && <span className="stuProfileInfo">--</span>}
                        {studentEntity.adresseet !== null && (
                          <span className="stuProfileInfo">{studentEntity.adresseet}</span>
                        )}
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
                        studentEntity.dateModifyJwtPwd !== null ?
                            <>
                              {studentEntity.dateModifyJwtPwd}
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
                        currentStudent.accessToken !== null ?
                            <>
                              {currentStudent.accessToken.substring(0, 20)}
                              ...
                              {currentStudent.accessToken.substr(currentStudent.accessToken.length - 20)}
                            </>
                            :
                            <>--</>
                      }
                      </span>
                    </CCol>
                  </CRow>
                </CAlert>
              </CCol>
              <CCol md="4">
                <CCard color="gradient-secondary">
                  <CCardHeader
                    style={{ fontWeight: "bold", fontSize: "14px", color: "#cc0000" }}>
                    Hiérarchie
                  </CCardHeader>
                  <CCardBody>
                    <CRow>
                      <CCol md="5">
                    <span className="stuProfileLabel">
                      Pôle :
                    </span>
                      </CCol>
                      <CCol md="7">
                        <span className="stuProfileInfo">{studentHierarchy.coursesPole}</span>
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="5">
                    <span className="stuProfileLabel">
                      Département :
                    </span>
                      </CCol>
                      <CCol md="7">
                        <span className="stuProfileInfo">{studentHierarchy.department}</span>
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="5">
                    <span className="stuProfileLabel">
                      Option :
                    </span>
                      </CCol>
                      <CCol md="7">
                        <span className="stuProfileInfo">{studentHierarchy.option}</span>
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="5">
                    <span className="stuProfileLabel">
                      Classe :
                    </span>
                      </CCol>
                      <CCol md="7">
                        <span className="stuProfileInfo">{studentHierarchy.classe}</span>
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="5">
                    <span className="stuProfileLabel">
                      Identifiant :
                    </span>
                      </CCol>
                      <CCol md="7">
                        <span className="stuProfileInfo">{currentStudent.id}</span>
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="5">
                    <span className="stuProfileLabel">
                      Session :
                    </span>
                      </CCol>
                      <CCol md="7">
                        <span className="stuProfileInfo">Juin 2022</span>
                      </CCol>
                    </CRow>

                  </CCardBody>
                </CCard>
              </CCol>
            </CRow>

          <br />
          </div>
        ) : null}
      </div>
    );
  }
}
