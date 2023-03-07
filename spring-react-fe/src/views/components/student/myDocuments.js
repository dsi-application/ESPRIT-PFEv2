import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";

import {CTooltip, CCard, CCardImg, CCardBody, CCardFooter, CCol, CRow, CButton, CWidgetIcon, CCallout} from "@coreui/react";
import myDoc from "../../images/myOwnDoc.png";
import pdfDoc from "../../images/pdf.jpg";
import wordDoc from "../../images/word.png";
import Divider from '@mui/material/Divider';
import axios from "axios";
import AuthService from "../../services/auth.service";
import Button from "@material-ui/core/Button";

import CIcon from "@coreui/icons-react";

import {
    fetchActiveStudentTimelineStep,
    fetchConventionDetails, fetchDepotReportsDetails,
    fetchFichePFEDetails,
    fetchTimelineSteps,
    gotStudentMsg
} from "../../../redux/slices/MyStudentSlice";
import excelBlueIcon from "../../images/excelBlueIcon.jpg";

import "../../css/style.css";
import {fetchConventionTraitee, selectConventionTraitee} from "../../../redux/slices/MyConventionsSlice";

import bilanPeriodiqueDebutStage from '../../studentDocs/Bilan périodique début du stage.docx';
import bilanPeriodiqueFinStage from '../../studentDocs/Bilan périodique fin du stage.docx';
import bilanPeriodiqueMilieuStage from '../../studentDocs/Bilan périodique milieu du stage.docx';
import guideJournalBord from '../../studentDocs/Guide pour rédiger votre journal de bord en stage.pdf';
import rapportStageNotePeriodique from '../../studentDocs/Rapport de stage note pédagogique.pdf';
import pageGardeRapportStage from '../../studentDocs/Page garde rapport stage.docx';

import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import SkipPreviousIcon from '@mui/icons-material/SkipPrevious';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import SkipNextIcon from '@mui/icons-material/SkipNext';

const currentStudent = AuthService.getCurrentStudent();


const downloadDemandeStage = () => {
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadDemandeStage/" + currentStudent.id, { responseType: "blob" })
        .then((response) => {

            // console.log('2910Response Headers:', response.headers);
            const file = new Blob([response.data], {type: 'application/pdf'});
            const fileURL = URL.createObjectURL(file);

            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
            window.open(fileURL);
        });
};

const downloadConventionStage = () => {
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadConventionStage/" + currentStudent.id, { responseType: "blob" })
        .then((response) => {
            const file = new Blob([response.data], {type: 'application/pdf'});
            const fileURL = URL.createObjectURL(file);

            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
            window.open(fileURL);
        });
};

const downloadMandatoryInternship = () => {
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMandatoryInternship/" + currentStudent.id, { responseType: "blob" })
        .then((response) => {
            const file = new Blob([response.data], {type: 'application/pdf'});
            const fileURL = URL.createObjectURL(file);

            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
            window.open(fileURL);
        });
};

const downloadJustificatifStage = () => {
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadJustificatifStage/" + currentStudent.id, { responseType: "blob" })
        .then((response) => {
            const file = new Blob([response.data], {type: 'application/pdf'});
            const fileURL = URL.createObjectURL(file);

            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
            window.open(fileURL);
        });
};

const downloadLettreAffectation = () => {
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadLettreAffectation/" + currentStudent.id, { responseType: "blob" })
        .then((response) => {
            const file = new Blob([response.data], {type: 'application/pdf'});
            const fileURL = URL.createObjectURL(file);

            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
            window.open(fileURL);
        });
};

const downloadPlanTravail = () => {
    let studentId = currentStudent.id;
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadNewestPlanTravail?studentId=" + studentId, { responseType: "blob" })
        .then((response) => {
            const file = new Blob([response.data], {type: 'application/pdf'});
            const fileURL = URL.createObjectURL(file);

            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
            window.open(fileURL);
        });
};

const MyDocuments = () => {

    const dispatch = useDispatch();
    const [conventionTraitee, errds1] = useSelector(selectConventionTraitee);
    // console.log('---------------> LOL1512: ', conventionTraitee);
    const theme = useTheme();

    const loadDataOnlyOnce = () => {
        dispatch(fetchConventionTraitee());
        // console.log('--------------------> 2812 1')
    };

    useEffect(() => {
        loadDataOnlyOnce(); // this will fire only on first render
        // console.log('--------------------> 2812 2')
    }, []);



    return (
        <>
            <br/>
            <Divider textAlign="center">
                <span className="clignoteModeleStudentDocs">Mes Propres Documents</span>
            </Divider>
            <br/>
            {
                (
                    conventionTraitee.includes("00") ||
                    conventionTraitee.includes("01") ||
                    conventionTraitee.includes("03") ||
                    conventionTraitee.includes("04")
                )
                &&
                <center>
                    <CCard style={{ width: '15rem' }}>
                        <CCardImg variant="top" src={myDoc} height="125px"/>
                        <CCardBody>
                            <center>
                                <span className="clignotePurple">Demande Stage</span>
                                <br/><br/>
                                <span className="downloadPurpleIcon" onClick={() => {downloadDemandeStage();}}/>
                            </center>
                        </CCardBody>
                    </CCard>
                </center>
            }

            {
                (conventionTraitee.includes("02") && conventionTraitee.includes("TN")) &&
                <CRow>
                    <CCol md="2"/>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Demande Stage</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadDemandeStage();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Convention Stage</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadConventionStage();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Lettre Affectation</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadLettreAffectation();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Plan Travail</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadPlanTravail();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2"/>
                </CRow>
            }

            {
                (
                    conventionTraitee.includes("02") &&
                    (conventionTraitee.includes("EN") || conventionTraitee.includes("--"))
                ) &&
                <CRow>
                    <CCol md="1"/>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Demande Stage</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadDemandeStage();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Convention Stage</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadConventionStage();}}/>
                                    <br/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Mandatory Internship</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadMandatoryInternship();}}/>
                                    <br/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Lettre Affectation</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadLettreAffectation();}}/>
                                    <br/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="2">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Plan Travail</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadPlanTravail();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="1"/>
                </CRow>
            }

            {
                (conventionTraitee.includes("02") && conventionTraitee.includes("FR")) &&
                <CRow>
                    <CCol md="1"/>
                    <CCol md="3">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Demande Stage</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadDemandeStage();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="3">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Lettre Affectation</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadLettreAffectation();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="3">
                        <CCard style={{ width: '15rem' }}>
                            <CCardImg variant="top" src={myDoc} height="125px"/>
                            <CCardBody>
                                <center>
                                    <span className="clignotePurple">Plan Travail</span>
                                    <br/><br/>
                                    <span className="downloadPurpleIcon" onClick={() => {downloadPlanTravail();}}/>
                                </center>
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol md="1"/>
                </CRow>
            }

            <br/><br/>
            <Divider textAlign="center">
                <span className="clignoteModeleStudentDocs">Modèles pour télécharger et remplir ...</span>
            </Divider>
            <br/>
            <center>
                <CRow>
                    <CCol sm="2">
                        <Card>
                            <br/>
                            <CRow>
                                <CCol sm="1"/>
                                <CCol sm="3" className="colPadding">
                                    <CCardImg src={wordDoc} height="50px" width="25px"/>
                                </CCol>
                                <CCol sm="7" className="colPaddingLeft">
                                    <a href={bilanPeriodiqueDebutStage} download="Bilan Périodique Début Stage.docx">Bilan Périodique Début Stage</a>
                                </CCol>
                                <CCol sm="1"/>
                            </CRow>
                            <br/>
                        </Card>
                    </CCol>
                    <CCol sm="2">
                        <Card>
                            <br/>
                            <CRow>
                                <CCol sm="1"/>
                                <CCol sm="3" className="colPadding">
                                    <CCardImg src={wordDoc} height="50px" width="25px"/>
                                </CCol>
                                <CCol sm="7" className="colPaddingLeft">
                                    <a href={bilanPeriodiqueMilieuStage} download="Bilan Périodique Milieu Stage.docx">Bilan Périodique Milieu Stage</a>
                                </CCol>
                                <CCol sm="1"/>
                            </CRow>
                            <br/>
                        </Card>
                    </CCol>
                    <CCol sm="2">
                        <Card>
                            <br/>
                            <CRow>
                                <CCol sm="1"/>
                                <CCol sm="3" className="colPadding">
                                    <CCardImg src={wordDoc} height="50px" width="25px"/>
                                </CCol>
                                <CCol sm="7" className="colPaddingLeft">
                                    <a href={bilanPeriodiqueFinStage} download="Bilan Périodique Fin Stage.docx">Bilan Périodique Fin Stage</a>
                                </CCol>
                                <CCol sm="1"/>
                            </CRow>
                            <br/>
                        </Card>
                    </CCol>
                    <CCol sm="2">
                        <Card>
                            <br/>
                            <CRow>
                                <CCol sm="1"/>
                                <CCol sm="3" className="colPadding">
                                    <CCardImg src={pdfDoc} height="50px" width="25px"/>
                                </CCol>
                                <CCol sm="7" className="colPaddingLeft">
                                    <a href={guideJournalBord} download="Guide Rédaction Journal Bord.pdf">Guide Rédaction Journal Bord</a>
                                </CCol>
                                <CCol sm="1"/>
                            </CRow>
                            <br/>
                        </Card>
                    </CCol>
                    <CCol sm="2">
                        <Card>
                            <br/>
                            <CRow>
                                <CCol sm="1"/>
                                <CCol sm="3" className="colPadding">
                                    <CCardImg src={pdfDoc} height="50px" width="25px"/>
                                </CCol>
                                <CCol sm="7" className="colPaddingLeft">
                                    <a href={rapportStageNotePeriodique} download="Rapport Stage Pédagogique.pdf">Rapport Stage Pédagogique</a>
                                </CCol>
                                <CCol sm="1"/>
                            </CRow>
                            <br/>
                        </Card>
                    </CCol>
                    <CCol sm="2">
                        <Card>
                            <br/>
                            <CRow>
                                <CCol sm="1"/>
                                <CCol sm="3" className="colPadding">
                                    <CCardImg src={wordDoc} height="50px" width="25px"/>
                                </CCol>
                                <CCol sm="7" className="colPaddingLeft">
                                    <a href={pageGardeRapportStage} download="Page Garde Rapport Stage.docx">Page Garde Rapport Stage</a>
                                </CCol>
                                <CCol sm="1"/>
                            </CRow>
                            <br/>
                        </Card>
                    </CCol>
                </CRow>
            </center>
        </>
    );
};

export default MyDocuments
