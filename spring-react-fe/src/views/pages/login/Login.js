import React, {Component} from "react";
import {Link} from 'react-router-dom';
import esprit from '../../images/esprit2022.png';
import {CButton, CCard, CCardBody, CCardGroup, CCol, CContainer, CRow, CTooltip} from '@coreui/react';

import '../../css/style.css';
import TextScrollerBackground from "../../css/TextScrollerBackground";
import {Wave} from "react-animated-text";

import SupervisorAccountIcon from '@material-ui/icons/SupervisorAccount';
import SchoolIcon from '@material-ui/icons/School'; //
import PersonIcon from '@material-ui/icons/Person';
import sample from "../../images/backgroundPFE.mp4";


export default class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeIndex: 1
        };
    }

    render() {
        return (

            <div className="video-container">
                <video autoPlay muted loop id="myVideo">
                    <source src={sample} type="video/mp4" width="2000px"
                            height="2000px"/>
                </video>

                <CRow className="headerContent">
                    <CCol md="12">
                        <center>
                        <CRow>
                            <CCol md="1">
                                <img src={esprit} alt="logo" title="Welcome to Esprit" height={55} width={150}/>
                            </CCol>
                            <CCol md="10">
                                <h1>
                                    <span style={{color: "#cc0000"}}>Plateforme Gestion des Stages</span>
                                </h1>
                                <TextScrollerBackground text="Session de Septembre 2022" />
                            </CCol>
                            <CCol md="1"/>
                        </CRow>
                        </center>
                    </CCol>
                </CRow>

                <CRow className="footerContent">
                    <CCol md="12">
                        <center>
                            &nbsp;&nbsp;&nbsp;
                            <Link to="/student">
                                <CButton shape="pill" color="info">
                                    <CTooltip content="Espace Étudiant" placement="top">
                                        <SchoolIcon/>
                                    </CTooltip>
                                </CButton>
                            </Link>

                            &nbsp;&nbsp;&nbsp;
                            <Link to="/responsableServiceStage">
                                <CButton shape="pill" color="danger">
                                    <CTooltip content="Espace Responsable des Stages" placement="top">
                                        <PersonIcon/>
                                    </CTooltip>
                                </CButton>
                            </Link>

                            &nbsp;&nbsp;&nbsp;
                            <Link to="/pedagogicalCoordinator">
                                <CButton shape="pill" color="warning">
                                    <CTooltip content="Espace Coordinateur Pédagogique"
                                              placement="top">
                                        <SupervisorAccountIcon/>
                                    </CTooltip>
                                </CButton>
                            </Link>

                            &nbsp;&nbsp;&nbsp;
                            <Link to="/teacher">
                                <CButton shape="pill" color="success">
                                    <CTooltip content="Espace Enseignant"
                                              placement="top">
                                        <SupervisorAccountIcon/>
                                    </CTooltip>
                                </CButton>
                            </Link>
                        </center>
                    </CCol>
                </CRow>
            </div>
        );
    }
}
