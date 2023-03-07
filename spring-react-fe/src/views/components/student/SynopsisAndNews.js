import React, {useEffect} from "react";

import {useDispatch, useSelector} from "react-redux";
import PropTypes from "prop-types";
import { styled } from "@mui/material/styles";
import Stack from "@mui/material/Stack";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Check from "@mui/icons-material/Check";
import StepConnector, {stepConnectorClasses} from "@mui/material/StepConnector";
import {Link} from 'react-router-dom';
import AccessAlarmIcon from '@material-ui/icons/AccessAlarm';
import SchoolIcon from '@material-ui/icons/School';
import StyleIcon from '@material-ui/icons/Style';
import AirplayIcon from '@mui/icons-material/Airplay';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import PeopleIcon from '@mui/icons-material/People';
import ArticleIcon from '@mui/icons-material/Article';

import DescriptionIcon from '@material-ui/icons/Description';
import BusinessCenterIcon from '@material-ui/icons/BusinessCenter';
import MenuBookIcon from '@material-ui/icons/MenuBook';
import ThumbsUpDownIcon from '@material-ui/icons/ThumbsUpDown';
import loadingGrad from "../../images/loadingGrad.jpg";
import doneGrad from "../../images/doneGrad.jpg";
import {CAlert, CCard, CCardImg, CCardBody, CCardFooter, CCol, CRow, CButton, CTooltip} from "@coreui/react";
import {selectConventionTraitee} from "../../../redux/slices/MyConventionsSlice";
import {
    fetchActiveStudentTimelineStep,
    fetchConventionDetails,
    fetchDepotReportsDetails,
    fetchFichePFEDetails,
    fetchTimelineSteps,
    fetchVerifyAffectPEtoStudent,
    gotActiveStudentTimelineStep,
    gotConventionDetails,
    gotDateDepotFichePFE,
    gotDepotReportsDetails,
    gotFichePFEDetails,
    gotStudentGradCongrat,
    gotStudentSTNGrad,
    gotTimelineSteps, gotVerifyAffectPEtoStudent
} from "../../../redux/slices/MyStudentSlice";
import TextScroller from "../../css/TextScroller";
import news from "../../images/new.gif";
import {Wave} from "react-animated-text";
import {
    fetchRefusedReports,
    fetchStudentDoneSTNStat,
    fetchStudentPlanifSTNStat,
    fetchStudentToSTNStat, fetchUploadedReports, fetchValidatedReports
} from "../../../redux/slices/DepotSlice";
import {fectchServiceStat, fectchServiceStatEM, fectchServiceStatGC} from "../../../redux/slices/StatSlice";
import {fetchFichePFEsHistoric} from "../../../redux/slices/MyStudentTBSSlice";
import AuthService from "../../services/auth.service";
import myDoc from "../../images/myDoc.jpg";
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';
import congrat from "../../images/congrat.mp4";
import emptyBack from "../../images/emptyBack.png";

const QontoConnector = styled(StepConnector)(({ theme }) => ({
    [`&.${stepConnectorClasses.alternativeLabel}`]: {
        top: 10,
        left: "calc(-50% + 16px)",
        right: "calc(50% + 16px)"
    },
    [`&.${stepConnectorClasses.active}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            borderColor: "#784af4"
        }
    },
    [`&.${stepConnectorClasses.completed}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            borderColor: "#784af4"
        }
    },
    [`& .${stepConnectorClasses.line}`]: {
        borderColor:
            theme.palette.mode === "dark" ? theme.palette.grey[800] : "#eaeaf0",
        borderTopWidth: 3,
        borderRadius: 1
    }
}));

const QontoStepIconRoot = styled("div")(({ theme, ownerState }) => ({
    color: theme.palette.mode === "dark" ? theme.palette.grey[700] : "#eaeaf0",
    display: "flex",
    height: 22,
    alignItems: "center",
    ...(ownerState.active && {
        color: "#784af4"
    }),
    "& .QontoStepIcon-completedIcon": {
        color: "#784af4",
        zIndex: 1,
        fontSize: 18
    },
    "& .QontoStepIcon-circle": {
        width: 8,
        height: 8,
        borderRadius: "50%",
        backgroundColor: "currentColor"
    }
}));

function QontoStepIcon(props) {
    const { active, completed, className } = props;

    return (
        <QontoStepIconRoot ownerState={{ active }} className={className}>
            {completed ? (
                <Check className="QontoStepIcon-completedIcon" />
            ) : (
                <div className="QontoStepIcon-circle" />
            )}
        </QontoStepIconRoot>
    );
}

QontoStepIcon.propTypes = {
    /**
     * Whether this step is active.
     * @default false
     */
    active: PropTypes.bool,
    className: PropTypes.string,
    /**
     * Mark the step as completed. Is passed to child components.
     * @default false
     */
    completed: PropTypes.bool
};

const ColorlibConnector = styled(StepConnector)(({ theme }) => ({
    [`&.${stepConnectorClasses.alternativeLabel}`]: {
        top: 22
    },
    [`&.${stepConnectorClasses.active}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            backgroundImage:
                "linear-gradient( 95deg,rgb(242,113,33) 0%,rgb(233,64,87) 50%,rgb(138,35,135) 100%)"
        }
    },
    [`&.${stepConnectorClasses.completed}`]: {
        [`& .${stepConnectorClasses.line}`]: {
            backgroundImage:
                "linear-gradient( 95deg,rgb(242,113,33) 0%,rgb(233,64,87) 50%,rgb(138,35,135) 100%)"
        }
    },
    [`& .${stepConnectorClasses.line}`]: {
        height: 3,
        border: 0,
        backgroundColor:
            theme.palette.mode === "dark" ? theme.palette.grey[800] : "#eaeaf0",
        borderRadius: 1
    }
}));

const ColorlibStepIconRoot = styled("div")(({ theme, ownerState }) => ({
    backgroundColor:
        theme.palette.mode === "dark" ? theme.palette.grey[700] : "#ccc",
    zIndex: 1,
    color: "#fff",
    width: 50,
    height: 50,
    display: "flex",
    borderRadius: "50%",
    justifyContent: "center",
    alignItems: "center",
    ...(ownerState.active && {
        backgroundImage:
            "linear-gradient( 136deg, rgb(242,113,33) 0%, rgb(233,64,87) 50%, rgb(138,35,135) 100%)",
        boxShadow: "0 4px 10px 0 rgba(0,0,0,.25)"
    }),
    ...(ownerState.completed && {
        backgroundImage:
            "linear-gradient( 136deg, rgb(242,113,33) 0%, rgb(233,64,87) 50%, rgb(138,35,135) 100%)"
    })
}));

function ColorlibStepIcon(props) {
    const { active, completed, className } = props;

    const icons = {
        1: <BusinessCenterIcon/>,
        2: <ThumbsUpDownIcon/>,
        3: <AssignmentIndIcon/>,
        4: <ArticleIcon/>,
        5: <StyleIcon/>,
        6: <AirplayIcon/>,
        7: <PeopleIcon/>,
        8: <StyleIcon/>,
        9: <MenuBookIcon/>,
        10: <AirplayIcon/>,
        11: <StyleIcon/>,
        12: <MenuBookIcon/>
    };

    return (
        <ColorlibStepIconRoot
            ownerState={{ completed, active }}
            className={className}
        >
            {icons[String(props.icon)]}
        </ColorlibStepIconRoot>
    );
}

ColorlibStepIcon.propTypes = {
    /**
     * Whether this step is active.
     * @default false
     */
    active: PropTypes.bool,
    className: PropTypes.string,
    /**
     * Mark the step as completed. Is passed to child components.
     * @default false
     */
    completed: PropTypes.bool,
    /**
     * The label displayed in the step icon.
     */
    icon: PropTypes.node
};

const currentStudent = AuthService.getCurrentStudent();

export default function SynopsisAndNews() {

    const dispatch = useDispatch();
    const [steps, errds1] = useSelector(gotTimelineSteps);
    const [conventionDetails, errds2] = useSelector(gotConventionDetails);
    const [fichePFEDetails, errds3] = useSelector(gotFichePFEDetails);
    const [depotReportsDetails, errds4] = useSelector(gotDepotReportsDetails);
    const [activeStudentTimelineStep, errds5] = useSelector(gotActiveStudentTimelineStep);
    const [studentGradCongrat, errds6] = useSelector(gotStudentGradCongrat);
    const [verifyAffectPEtoStudent, errds7] = useSelector(gotVerifyAffectPEtoStudent);

    // console.log('-------------2212--> LOL: ', fichePFEDetails.etat);

    // Do Updates JUST Once on Access This Page
    const loadDataOnlyOnce = () => {
        dispatch(fetchTimelineSteps(currentStudent.id));  // Date Step
        dispatch(fetchConventionDetails());
        dispatch(fetchFichePFEDetails());
        dispatch(fetchDepotReportsDetails());
        dispatch(fetchActiveStudentTimelineStep(currentStudent.id));
        dispatch(fetchVerifyAffectPEtoStudent(currentStudent.id));
    };

    useEffect(() => {
        loadDataOnlyOnce(); // this will fire only on first render
    }, []);


    /*
    useEffect(() => {
        dispatch(fetchFichePFEsHistoric(currentStudent.id)); // Update Fields of All Existing Fiches PFE History
        dispatch(fetchActiveStudentTimelineStep(currentStudent.id)); // Active Step
        dispatch(fetchFichePFEDetails(currentStudent.id)); // Badge
        dispatch(fetchTimelineSteps(currentStudent.id));  // Date Step

        dispatch(fetchTimelineSteps());
        dispatch(fetchActiveStudentTimelineStep());
        dispatch(fetchConventionDetails());
        dispatch(fetchFichePFEDetails());
        dispatch(fetchDepotReportsDetails());
    });
*/
    /*const steps = [
        "Demande \n Convention",
        "Remise Plan Travail",
        "Démarrage Stage PFE",
        "Dépôt Journal de Bord",
        "Dépôt Bilan Version 1",
        "Dépôt Bilan Version 2",
        "Dépôt Rapport Version 1",
        "Dépôt Bilan Version 3",
        "Dépôt Rapport Version 2"
    ];*/

    let currentDt = new Date();
    let currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();

    return (
        <>
            {
                verifyAffectPEtoStudent === "" &&
                <CRow>
                    <CCol md="2"/>
                    <CCol md="8">
                        <br/><br/>
                        <CCard accentColor="danger">
                            <CCardBody>
                                <center>
                                    <br/><br/><br/>
                                    Vous n'avez pas encore un Encadrant Académique.
                                    <br/>
                                    Prière de contacter le &nbsp;
                                    <span style={{color: "#cc0000", fontSize: "13px", fontWeight: "bold"}}>
                                        Responsable Service Stage
                                    </span>&nbsp;
                                    pour régler votre affectation.
                                    <br/><br/><br/><br/>
                                </center>
                            </CCardBody>
                            <CCardFooter>
                                <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                                  Today is {currentDay}
                                </span>
                            </CCardFooter>
                        </CCard>
                    </CCol>
                    <CCol md="2"/>
                </CRow>
            }

            {
                verifyAffectPEtoStudent !== "" &&
                <CRow>
                <CCol md="12">
                    <span style={{fontWeight: "bold", fontSize: "15px", color: "#cc0000"}}>
                        <TextScroller text="Session Juin 2022"/>
                    </span>

                    {
                        fichePFEDetails.etat === "SOUTENUE" &&
                        <center>
                            <br/><br/>
                            <div className="congrat-container" height="100%" width="100%">
                                <video autoPlay muted loop id="myVideo" width="555" height="360">
                                    <source src={congrat} type="video/mp4"/>
                                </video>
                            </div>
                            <br/><br/><br/>
                        </center>
                    }

                    {
                        fichePFEDetails.etat === "A_SOUTENIR" &&
                        <>
                            <br/>
                            <CRow>
                                <CCol md="12">
                                    <center>
                                        <CCard style={{width: '50rem'}}>
                                            <CCardImg variant="top" src={doneGrad} height="215px"/>
                                            <CCardBody>
                                                You're invited to pass your Grad Ceremony on&nbsp;
                                                <span className="goldMarkSmall">{studentGradCongrat.dateSoutenance}</span>&nbsp;
                                                at&nbsp;
                                                <span className="goldMarkSmall">{studentGradCongrat.heureSoutenance}</span>&nbsp;
                                                in the&nbsp;
                                                <span className="goldMarkSmall">{studentGradCongrat.salle}</span> Room Space.
                                                <br/>
                                                <br/>
                                                <Divider>
                                                    <span className="goldMark">Jury Members</span>
                                                </Divider>
                                                <span className="greyMark11">Mrs/Mr </span><span className="greyMarkCourrierHst">{studentGradCongrat.juryPresident}</span> <span className="greyMarkItalic">(Jury Prseident)</span><br/>
                                                <span className="greyMark11">Mrs/Mr </span><span className="greyMarkCourrierHst">{studentGradCongrat.pedagogicalEncadrant}</span> <span className="greyMarkItalic">(Expert)</span><br/>
                                                <span className="greyMark11">Mrs/Mr </span><span className="greyMarkCourrierHst">{studentGradCongrat.expert}</span> <span className="greyMarkItalic">(Académic Supervisor)</span><br/>
                                                <span className="greyMark11">Mrs/Mr </span><span className="greyMarkCourrierHst">--</span> <span className="greyMarkItalic">(Entreprise Supervisor)</span><br/>
                                                <br/>
                                            </CCardBody>
                                        </CCard>
                                    </center>
                                </CCol>
                            </CRow>
                        </>
                    }

                    <br/>
                    <CCard accentColor="danger">
                        <CCardBody>
                            <CRow>
                                <CCol md="3">
                                    {(
                                        conventionDetails.length === 0 &&
                                        <CAlert color="secondary">
                                            <h6 className="alert-heading"><strong>Traitement Convention</strong></h6>
                                            <CRow>
                                                <CCol md="7">
                                                    <span className="synopsisField">
                                                        Date Demande:
                                                    </span>
                                                </CCol>
                                                <CCol md="5" className="colPadding">
                                                    <span className="text-black" style={{fontSize: "11px"}}>
                                                        --
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="7">
                                                    <span className="synopsisField">
                                                        État Convention:
                                                    </span>
                                                </CCol>
                                                <CCol md="5" className="colPadding">
                                                    <span className="text-black" style={{fontSize: "11px"}}>
                                                        --
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    )}

                                    {(
                                        conventionDetails.length > 0 &&
                                        <>
                                            {
                                                conventionDetails[0].traiter === "01" &&
                                                <CAlert color="info">
                                                    <h6 className="alert-heading"><strong>Traitement
                                                        Convention</strong></h6>
                                                    <CRow>
                                                        <CCol md="7" className="colPaddingRight">
                                                        <span className="synopsisField">
                                                            Date Demande:
                                                        </span>
                                                        </CCol>
                                                        <CCol md="5" className="colPadding">
                                                        <span className="synopsisField">
                                                            {conventionDetails[0].dateConvention}
                                                        </span>
                                                        </CCol>
                                                    </CRow>

                                                    <CRow>
                                                        <CCol md="7" className="colPaddingRight">
                                                        <span className="synopsisField">
                                                            État Convention:
                                                        </span>
                                                        </CCol>
                                                        <CCol md="5" className="colPadding">
                                                        <span className="clignotePrimary">
                                                            DEPOSEE
                                                        </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }
                                        </>
                                    )}

                                    {
                                        conventionDetails.length > 0 &&
                                        <>
                                            {
                                                conventionDetails[0].traiter === "02" &&
                                                <CAlert color="success">
                                                    <h6 className="alert-heading"><strong>Traitement Convention</strong></h6>
                                                    <CRow>
                                                        <CCol md="7" className="colPaddingRight">
                                                                <span className="synopsisField">
                                                                    Date Demande:
                                                                </span>
                                                        </CCol>
                                                        <CCol md="5" className="colPadding">
                                                                <span className="synopsisField">
                                                                    {conventionDetails[0].dateConvention}
                                                                </span>
                                                        </CCol>
                                                    </CRow>
                                                    <CRow>
                                                        <CCol md="7" className="colPaddingRight">
                                                                <span className="synopsisField">
                                                                    État Convention:
                                                                </span>
                                                        </CCol>
                                                        <CCol md="5" className="colPadding">
                                                                <span className="clignoteGreen">
                                                                    TRAITEE
                                                                </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }
                                        </>
                                    }

                                    {
                                        conventionDetails.length > 0 &&
                                        <>
                                            {
                                                conventionDetails[0].traiter === "03" &&
                                                <CAlert color="danger">
                                                    <h6 className="alert-heading"><strong>Traitement Convention</strong></h6>
                                                    <CRow>
                                                        <CCol md="6" className="colPaddingRight">
                                                            <span className="synopsisField">
                                                                Date Demande:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6" className="colPadding">
                                                            <span className="synopsisField">
                                                                {conventionDetails[0].dateConvention}
                                                            </span>
                                                        </CCol>
                                                    </CRow>
                                                    <CRow>
                                                        <CCol md="6" className="colPaddingRight">
                                                            <span className="synopsisField">
                                                                État Convention:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6" className="colPadding">
                                                            <span className="clignoteRedAD">
                                                                ANNULATION VALIDEE
                                                            </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }
                                        </>
                                    }

                                    {
                                        conventionDetails.length > 0 &&
                                        <>
                                            {
                                                conventionDetails[0].traiter === "04" &&
                                                <CAlert color="warning">
                                                    <h6 className="alert-heading"><strong>Traitement Convention</strong></h6>
                                                    <CRow>
                                                        <CCol md="5" className="colPaddingRight">
                                                            <span className="synopsisField">
                                                                Date Demande:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="7" className="colPadding">
                                                            <span className="synopsisField">
                                                                {conventionDetails[0].dateConvention}
                                                            </span>
                                                        </CCol>
                                                    </CRow>

                                                    <CRow>
                                                        <CCol md="5" className="colPaddingRight">
                                                            <span className="synopsisField">
                                                                État Convention:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="7" className="colPadding">
                                                            <span className="clignoteOrange">
                                                                ANNULATION DEMANDEE
                                                            </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }
                                        </>
                                    }
                                </CCol>

                                <CCol md="3">
                                    {(
                                        (fichePFEDetails.etat === null || fichePFEDetails.etat === undefined) &&
                                        <CAlert color="secondary">
                                            <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        Date Dépôt:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        --
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                    --
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    )}

                                    {
                                        fichePFEDetails.etat === "SAUVEGARDEE" &&
                                        <CAlert color="info">
                                            <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        Date Dépôt:
                                                    </span>
                                                </CCol>
                                                <CCol md="6" className="colPadding">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateDepot}
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                                                </CCol>
                                                <CCol md="6" className="colPadding">
                                                    <span className="clignotePrimary">
                                                        {fichePFEDetails.etat}
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    }

                                    {
                                        fichePFEDetails.etat === "DEPOSEE" &&
                                        <CAlert color="info">
                                            <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        Date Remise:
                                                    </span>
                                                </CCol>
                                                <CCol md="6" className="colPadding">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateRemise}
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                                                </CCol>
                                                <CCol md="6" className="colPadding">
                                                    <span className="clignotePrimary">
                                                        {fichePFEDetails.etat}
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    }

                                    {(
                                        (
                                            fichePFEDetails.etat === "VALIDEE" ||
                                            fichePFEDetails.etat === "A_SOUTENIR" ||
                                            fichePFEDetails.etat === "SOUTENUE" ||
                                            fichePFEDetails.etat === "PLANIFIED"
                                        ) &&
                                        <CAlert color="success">
                                            <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        Date Remise:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateRemise}
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="clignoteGreen">
                                                        {fichePFEDetails.etat}
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    )}

                                    {(
                                        (fichePFEDetails.etat === "ANNULEE" || fichePFEDetails.etat === "REFUSEE") &&
                                        <CAlert color="danger">
                                            <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        Date Remise:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateRemise}
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="clignoteRed">
                                                        {fichePFEDetails.etat}
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    )}
                                </CCol>

                                <CCol md="3">
                                    {
                                        depotReportsDetails === "" &&
                                        <CAlert color="secondary">
                                            <h6 className="alert-heading"><strong>Traitement Dépôt Rapports</strong></h6>
                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        Date Dépôt:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        --
                                                    </span>
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        État Dépôt:
                                                    </span>
                                                </CCol>
                                                <CCol md="6">
                                                    <span className="synopsisField">
                                                        --
                                                    </span>
                                                </CCol>
                                            </CRow>
                                        </CAlert>
                                    }

                                    {
                                        depotReportsDetails !== "" &&
                                        <>
                                            {
                                                depotReportsDetails.etatDepotReports === "DEPOSE" &&
                                                <CAlert color="info">
                                                    <h6 className="alert-heading"><strong>Traitement Dépôt Rapports</strong></h6>
                                                    <CRow>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                Date Dépôt:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                {depotReportsDetails.dateDepotReports}
                                                            </span>
                                                        </CCol>
                                                    </CRow>

                                                    <CRow>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                État Dépôt:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6">
                                                            <span className="clignotePrimary">
                                                                {depotReportsDetails.etatDepotReports}
                                                            </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }

                                            {
                                                depotReportsDetails.etatDepotReports === "ACCEPTE" &&
                                                <CAlert color="success">
                                                    <h6 className="alert-heading"><strong>Traitement Dépôt Rapports</strong></h6>
                                                    <CRow>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                Date Dépôt:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                {depotReportsDetails.dateDepotReports}
                                                            </span>
                                                        </CCol>
                                                    </CRow>

                                                    <CRow>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                État Dépôt:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6">
                                                            <span className="clignoteGreen">
                                                                {depotReportsDetails.etatDepotReports}
                                                            </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }

                                            {
                                                depotReportsDetails.etatDepotReports === "REFUSE" &&
                                                <CAlert color="danger">
                                                    <h6 className="alert-heading"><strong>Traitement Dépôt Rapports</strong></h6>
                                                    <CRow>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                Date Dépôt:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                {depotReportsDetails.dateDepotReports}
                                                            </span>
                                                        </CCol>
                                                    </CRow>

                                                    <CRow>
                                                        <CCol md="6">
                                                            <span className="synopsisField">
                                                                État Dépôt:
                                                            </span>
                                                        </CCol>
                                                        <CCol md="6">
                                                            <span className="clignoteRed">
                                                                {depotReportsDetails.etatDepotReports}
                                                            </span>
                                                        </CCol>
                                                    </CRow>
                                                </CAlert>
                                            }
                                        </>
                                    }
                                </CCol>

                                <CCol md="3">
                                    {
                                        conventionDetails.length === 0 &&
                                        <>
                                            <div className="imgCont">
                                                <img src={emptyBack} alt="Snow" style={{width:"100%", height: "80px"}}/>
                                                <div className="imgCentNotYetGrad">
                                                    <span className="clignoteBlack">
                                                        End of Studies Project <br/>
                                                        NOT YET
                                                        <br/>
                                                        started
                                                    </span>
                                                </div>
                                            </div>
                                        </>
                                    }
                                    {
                                        conventionDetails.length > 0 &&
                                        <>
                                            {
                                                (
                                                    conventionDetails[0].traiter === "01" ||
                                                    conventionDetails[0].traiter === "03" ||
                                                    conventionDetails[0].traiter === "04"
                                                )
                                                &&
                                                <>
                                                    <div className="imgCont">
                                                        <img src={emptyBack} alt="Snow" style={{width:"100%", height: "80px"}}/>
                                                        <div className="imgCentNotYetGrad">
                                                            <span className="clignoteBlack">
                                                                End of Studies Project <br/>
                                                                NOT YET
                                                                <br/>
                                                                started
                                                            </span>
                                                        </div>
                                                    </div>
                                                </>
                                            }
                                        </>
                                    }
                                    {
                                        conventionDetails.length > 0 &&
                                        <>
                                            {
                                                conventionDetails[0].traiter === "02" &&
                                                    <>
                                                        {
                                                            (fichePFEDetails.etat === "A_SOUTENIR" || fichePFEDetails.etat === "SOUTENUE") ?
                                                                <>
                                                                    <div className="imgCont">
                                                                        <img src={doneGrad} alt="Snow" style={{width:"100%", height: "80px"}}/>
                                                                        <div className="imgCentDoneGrad">
                                                            <span className="clignoteWhite">
                                                                Graduation Done
                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                </>
                                                                :
                                                                <>
                                                                    <div className="imgCont">
                                                                        <img src={loadingGrad} alt="Snow" style={{width:"100%", height: "80px"}}/>
                                                                        <div className="imgCentPendingGrad">
                                                            <span className="clignoteRed">
                                                                Grad Party <br/> IS LOADING ...
                                                            </span>
                                                                        </div>
                                                                    </div>
                                                                </>
                                                        }
                                                    </>
                                            }
                                        </>
                                    }
                                </CCol>
                            </CRow>
                        </CCardBody>
                    </CCard>

                    <CCard accentColor="danger">
                        <CCardBody>
                            <div style={{overflowX : "auto"}}>
                                <Stepper alternativeLabel
                                         activeStep={activeStudentTimelineStep}
                                         connector={<ColorlibConnector />}>
                                    {steps.map((step) => (
                                        <Step key={step}>
                                            <CRow>
                                                <CCol md="12">
                                                    <StepLabel StepIconComponent={ColorlibStepIcon}>
                                                        <span className="redMark">{step.stepDate}</span>
                                                        <br/><br/>
                                                        <Link to={step.stepPath}>
                                                            <CButton  shape="pill"
                                                                      color="light"
                                                                      size="sm"
                                                                      className="float-center"
                                                                      aria-expanded="true">
                                                                <p style={{whiteSpace: 'pre'}}> {step.stepLabel} </p>
                                                            </CButton>
                                                        </Link>
                                                    </StepLabel>
                                                </CCol>
                                            </CRow>
                                        </Step>
                                    ))}
                                </Stepper>
                                <br/><br/>
                            </div>
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>
            }
        </>
    );
}
