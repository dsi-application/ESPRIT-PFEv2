import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";

import axios from "axios";
import AuthService from "../../services/auth.service";

import {PDFExport} from "@progress/kendo-react-pdf";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Button from "@material-ui/core/Button";
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
import Badge from '@mui/material/Badge';
import StyleIcon from '@material-ui/icons/Style';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import DescriptionIcon from '@material-ui/icons/Description';
import BusinessCenterIcon from '@material-ui/icons/BusinessCenter';
import MenuBookIcon from '@material-ui/icons/MenuBook';
import ThumbsUpDownIcon from '@material-ui/icons/ThumbsUpDown';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import Divider from '@mui/material/Divider';
import ListItemText from '@mui/material/ListItemText';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Typography from '@mui/material/Typography';
import CIcon from "@coreui/icons-react";
import { freeSet } from '@coreui/icons';
import { useFormik } from "formik";
import HomeWorkIcon from '@mui/icons-material/HomeWork';
import { CNav,
    CForm,
    CListGroupItem,
    CListGroup,
    CFormGroup,
    CSpinner,
    CNavItem,
    CNavLink,
    CTabContent,
    CTabPane,
    CTabs,
    CLabel,
    CTextarea,
    CWidgetDropdown,
    CAlert, CCard, CCardBody, CCardFooter, CCol, CRow, CButton, CTooltip, CCardHeader, CLink, CBadge, CJumbotron, CCallout, CWidgetBrand} from "@coreui/react";
import * as Yup from "yup";
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';

import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';

import AlarmIcon from '@mui/icons-material/Alarm';

import { pink } from '@mui/material/colors';
import { purple } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import HistoryIcon from '@mui/icons-material/History';
import ManageSearchIcon from '@mui/icons-material/ManageSearch';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { withStyles } from "@material-ui/core/styles";
import GetApp from "@material-ui/icons/GetApp";
import company from "../../images/company.jpg";

import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import "../../css/pdfstyle.css";
import "../../css/style.css";
import Spinner from "react-bootstrap/Spinner";

import Avatar from '@mui/material/Avatar';
import { deepOrange, deepPurple } from '@mui/material/colors';
import news from "../../images/new.gif";
import {Wave} from "react-animated-text";
import {queryApi} from "../../../utils/queryApi";
import {updateFiche} from "../../../redux/slices/FichePFESlice";
import {fetchEtatValidationFiche} from "../../../redux/slices/ListingSlice";
import {
    fetchConventionDetails, fetchDepotReportsDetails, fetchFichePFEDetails, fetchMyFichesPFEsHistoric,
    fetchTimelineSteps,
    gotTraitementFichePFE,
    selectMyFichesPFEsHistoric,
} from "../../../redux/slices/MyStudentSlice";

const LightTooltip = styled(({ className, ...props }) => (
    <Tooltip {...props} classes={{ popper: className }} />
))(({ theme }) => ({
    [`& .${tooltipClasses.tooltip}`]: {
        backgroundColor: theme.palette.common.white,
        color: 'rgba(0, 0, 0, 0.87)',
        boxShadow: theme.shadows[1],
        fontSize: 11,
    },
}));

const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

const styles = theme => ({
    probsBdg: {
        backgroundColor: "#cc0000",
        color: "white"
    },
    funcsBdg: {
        backgroundColor: "#009900",
        color: "white"
    },
    techsBdg: {
        backgroundColor: "#005ce6",
        color: "white"
    }
});

const currentStudent = AuthService.getCurrentStudent();

function ConsultESPFile(props) {

    const dispatch = useDispatch();
    const { classes } = props;
    const [fichePFEsHistoric, err1] = useSelector(selectMyFichesPFEsHistoric);
    const [expanded, setExpanded] = React.useState(false);

    const loadDataOnlyOnce = () => {
        dispatch(fetchFichePFEDetails(currentStudent.id));
        dispatch(fetchMyFichesPFEsHistoric(currentStudent.id));
    };

    useEffect(() => {
        loadDataOnlyOnce(); // this will fire only on first render
    }, []);

    const fichePFEsHistoricBadge = (e) => {
        if (e === "01")
        {
            return (
                <CBadge color="secondary" className="float-right">
                    SAUVEGARDÉE
                </CBadge>
            );
        }
        if (e === "02")
        {
            return (
                <CBadge color="info" className="float-right">
                    DÉPOSÉE
                </CBadge>
            );
        }
        if (e === "03")
        {
            return (
                <CBadge color="success" className="float-right">
                    VALIDÉE
                </CBadge>
            );
        }
        if (e === "04")
        {
            return (
                <CBadge color="danger" className="float-right">
                    REFUSÉE
                </CBadge>
            );
        }
        if (e === "05")
        {
            return (
                <CBadge color="warning" className="float-right">
                    ANNULÉE
                </CBadge>
            );
        }
        if (e === "06")
        {
            return (
                <CBadge color="dark" className="float-right">
                    À SOUTENIR
                </CBadge>
            );
        }
        if (e === "07")
        {
            return (
                <CBadge color="primary" className="float-right">
                    SOUTENUE
                </CBadge>
            );
        }
        if (e === "08")
        {
            return (
                <CBadge color="primary" className="float-right">
                    PLANIFIÉE
                </CBadge>
            );
        }
    };
    const companySupervisorsForESPPFileHist = (cs) => {

        let companySupervisorFN = cs.lastName + ' ' + cs.firstName;

        let splitStr = companySupervisorFN.toLowerCase().split(' ');
        for (let i = 0; i < splitStr.length; i++)
        {
            splitStr[i] = splitStr[i].charAt(0).toUpperCase();
        }
        let csAbbv = splitStr.join('');


        return (
            <>
                <ListItem alignItems="flex-start">
                    <ListItemAvatar>
                        <Avatar sx={{ bgcolor: deepPurple[500] }}>{csAbbv}</Avatar>
                    </ListItemAvatar>
                    <ListItemText primary={companySupervisorFN}
                                  secondary={
                                      <React.Fragment>
                                          {
                                              cs.numTelephone === "--" ?
                                                  <>--</>
                                                  :
                                                  <>+{cs.numTelephone}</>
                                          }
                                          <br/>
                                          {cs.email}
                                      </React.Fragment>
                                  }
                    />
                </ListItem>
            </>
        );
    };
    const treatmentsForESPPFileHist = (tf) => {

        function downloadGanttDiagram(gdDecodedFullPath)
        {
            // console.log('--------------1003----> 0');
            let lol = encodeURIComponent(gdDecodedFullPath);
            // console.log('--------------1003----> 1: ', gdDecodedFullPath);
            // console.log('--------------1003----> 2: ', lol);

            /* axios
                 .get(
                     `${process.env.REACT_APP_API_URL}` +
                     "encadrement/download?fileName=" +
                     encodeURIComponent(p),

                     { responseType: "blob" }
                 )*/

            axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadGanttDiagramByUnit?gdDecodedFullPath=" + lol, { responseType: "blob" })
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
                });
        }

        return (
            <>
                {
                    (tf.typeTrtFiche === "Sauvegarde" || tf.typeTrtFiche === "Remise") &&
                    <>
                        <CRow>
                            <CCol md="5" className="colPaddingRight">
                                <span className="orangeMark">{tf.typeTrtFiche}</span>
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date {tf.typeTrtFiche}:
                                </span>
                            </CCol>
                            <CCol md="10" className="colPaddingLeft">
                                <span className="trtHistoricDateBlackField">{tf.dateSaisieDemande}</span>
                            </CCol>
                        </CRow>
                    </>
                }
                {
                    tf.typeTrtFiche === "Modification" &&
                    <>
                        <CRow>
                            <CCol md="8">
                                <span className="orangeMark">Demande {tf.typeTrtFiche}</span>
                            </CCol>
                            <CCol md="4">
                                {
                                    tf.etatTrt === "01" &&
                                    <div className="float-right" style={{border: '0.5mm ridge rgba(102, 0, 102, .6)'}}>
                                        <span className="clignotePurple">
                                            &nbsp;Demande {tf.typeTrtFiche} En Cours&nbsp;
                                        </span>
                                    </div>
                                }
                                {
                                    tf.etatTrt === "02" &&
                                    <div className="float-right">
                                        <CTooltip content="Demande Acceptée" placement="top">
                                            <span className="acceptIcon"/>
                                        </CTooltip>
                                    </div>
                                }
                                {
                                    tf.etatTrt === "03" &&
                                    <div className="float-right">
                                        <CTooltip content="Demande Réfusée" placement="top">
                                            <span className="refuseIcon"/>
                                        </CTooltip>
                                    </div>
                                }
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Saisie Demande:
                                </span>
                            </CCol>
                            <CCol md="10" className="colPaddingLeft">
                                <span className="trtHistoricDateBlackField">{tf.dateSaisieDemande}</span>
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Traitement Demande:
                                </span>
                            </CCol>
                            <CCol md="10" className="colPaddingLeft">
                                <span className="trtHistoricDateBlackField">{tf.dateTrtFiche}</span>
                            </CCol>
                        </CRow>
                        <br/><br/>
                        <CRow>
                            <CCol md="9" style={{background: "#f2f2f2"}}>
                                <br/>
                                <Divider textAlign="right">Motif Demande {tf.typeTrtFiche}</Divider>
                                <div style={{wordWrap: "break-word"}}
                                     className="editor"
                                     dangerouslySetInnerHTML={{__html: tf.description}}/>
                                <br/>
                            </CCol>
                        </CRow>
                        {
                            tf.etatTrt === "03" &&
                            <>
                                <br/><br/>
                                <CRow>
                                    <br/>
                                    <CCol md="9" style={{background: "#ffe6e6"}}>
                                        <br/>
                                        <Divider textAlign="right">Motif Refus Demande {tf.typeTrtFiche}</Divider>
                                        <div style={{wordWrap: "break-word"}}
                                             className="editor"
                                             dangerouslySetInnerHTML={{__html: tf.motifRefus}}/>
                                        <br/>
                                    </CCol>
                                </CRow>
                            </>
                        }
                    </>
                }
                {
                    tf.typeTrtFiche === "Annulation" &&
                    <>
                        <CRow>
                            <CCol md="8">
                                <span className="orangeMark">Demande {tf.typeTrtFiche}</span>
                            </CCol>
                            <CCol md="4">
                                {
                                    tf.etatTrt === "01" &&
                                    <div className="float-right" style={{border: '0.5mm ridge rgba(102, 0, 102, .6)'}}>
                                        <span className="clignotePurple">
                                            &nbsp;Demande {tf.typeTrtFiche} En Cours&nbsp;
                                        </span>
                                    </div>
                                }
                                {
                                    tf.etatTrt === "02" &&
                                    <div className="float-right">
                                        <CTooltip content="Demande Acceptée" placement="top">
                                            <span className="acceptIcon"/>
                                        </CTooltip>
                                    </div>
                                }
                                {
                                    tf.etatTrt === "03" &&
                                    <div className="float-right">
                                        <CTooltip content="Demande Réfusée" placement="top">
                                            <span className="refuseIcon"/>
                                        </CTooltip>
                                    </div>
                                }
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2">
                                <span className="trtHistoricDeepGreyField">
                                    Date Saisie Demande:
                                </span>
                            </CCol>
                            <CCol md="10">
                                <span className="trtHistoricDateBlackField">{tf.dateSaisieDemande}</span>
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2">
                                <span className="trtHistoricDeepGreyField">
                                    Date Traitement Demande:
                                </span>
                            </CCol>
                            <CCol md="10">
                                <span className="trtHistoricDateBlackField">{tf.dateTrtFiche}</span>
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2">
                                <span className="trtHistoricDeepGreyField">
                                    Type Traitement Convention:
                                </span>
                            </CCol>
                            <CCol md="10">
                                {
                                    tf.typeTrtConv === "AUT" ?
                                        <span className="noteCancelConvToo">
                                            Demande Annulation Plan de Travail AUTOMATIQUE <br/>
                                            suite à une Demande Annulation Convention <br/>
                                            - demandée par Vous <br/>
                                            - accéptée par le Responsable Service Stage.
                                        </span>
                                        :
                                        <span className="trtHistoricDateBlackField">{tf.typeTrtConv}</span>
                                }
                            </CCol>
                        </CRow>
                        <br/>
                        <CRow>
                            <CCol md="9" style={{background: "#f2f2f2"}}>
                                <br/>
                                <Divider textAlign="right">Motif Demande {tf.typeTrtFiche}</Divider>
                                <div style={{wordWrap: "break-word"}}
                                     className="editor"
                                     dangerouslySetInnerHTML={{__html: tf.description}}/>

                                {
                                    tf.cancellingAgreementPath !== null &&
                                    <LightTooltip title="Télécharger Accord Annulation" placement="right">
                                        <span className="cancelAgreeIcon" onClick={() => {downloadGanttDiagram(tf.cancellingAgreementPath);}}/>
                                    </LightTooltip>
                                }
                                <br/>
                            </CCol>
                        </CRow>

                        {
                            tf.etatTrt === "03" &&
                            <>
                                <br/><br/>
                                <CRow>
                                    <CCol md="9" style={{background: "#ffe6e6"}}>
                                        <br/>
                                        <Divider textAlign="right">Motif Refus Demande {tf.typeTrtFiche}</Divider>
                                        <div style={{wordWrap: "break-word"}}
                                             className="editor"
                                             dangerouslySetInnerHTML={{__html: tf.motifRefus}}/>
                                        <br/>
                                    </CCol>
                                </CRow>
                            </>
                        }
                    </>
                }
                {
                    !(tf.typeTrtFiche === "Modification" || tf.typeTrtFiche === "Annulation" || tf.typeTrtFiche === "Sauvegarde" || tf.typeTrtFiche === "Remise") &&
                    <>
                        <CRow>
                            <CCol md="5">
                                <span className="orangeMark">
                                    {tf.typeTrtFiche}
                                </span>
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Saisie Demande:
                                </span>
                            </CCol>
                            <CCol md="10" className="colPaddingLeft">
                                <span className="trtHistoricDateBlackField">{tf.dateSaisieDemande}</span>
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="2" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Traitement Fiche:
                                </span>
                            </CCol>
                            <CCol md="10" className="colPaddingLeft">
                                <span className="trtHistoricDateBlackField">{tf.dateTrtFiche}</span>
                            </CCol>
                        </CRow>
                    </>
                }
            </>
        );
    };

    const handleExpandClick = () => {setExpanded(!expanded);};

    const contentConsultESPFile = () =>
    {
        let fichePFEStatus = '';
        let cancelFichePFEType = '';
        let cancelFichePFEEtat = '';
        let cancelConventionType = '';

        if(fichePFEsHistoric.length > 0)
        {
            fichePFEStatus = fichePFEsHistoric[0].etat;
            cancelFichePFEType = fichePFEsHistoric[0].treatmentFiches[0].typeTrtFiche;
            cancelFichePFEEtat = fichePFEsHistoric[0].treatmentFiches[0].etatTrt;
            cancelConventionType = fichePFEsHistoric[0].treatmentFiches[0].typeTrtConv;
        }

        let currentDt = new Date();
        let currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();

        function gotGanttDiagLabel(ganDiagFP) {
            let gdName = ganDiagFP.substring(ganDiagFP.lastIndexOf("C:/ESP/uploads/") + 15, ganDiagFP.lastIndexOf("espdsi2020"));
            let gdExt = ganDiagFP.substring(ganDiagFP.lastIndexOf("."));

            let ganttDiagramFP = gdName + gdExt;
            return ganttDiagramFP;
        }

      function downloadGanttDiagramOFF(gdDecodedFullPath) {

        let encodedURL = encodeURIComponent(encodeURIComponent(gdDecodedFullPath));

        axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadAllFilesTypes/" + encodedURL, {responseType: "blob"})
          .then((response) => {

            // console.log('2910Response Headers:', response.headers);
            const contentDispo = response.headers['content-disposition'];
            const fileName = contentDispo.substring(21);

            const file = new File([response.data], fileName);
            const fileURL = URL.createObjectURL(file);

            let a = document.createElement('a');
            a.href = fileURL;
            a.download = fileName;
            a.click();
          });

      }

        function downloadGanttDiagram(gdDecodedFullPath)
        {
               console.log('--------------PIKO 16.11.22----> 0');
                let lol = encodeURIComponent(encodeURIComponent(gdDecodedFullPath));
                // console.log('--------------1003----> 1: ', gdDecodedFullPath);
                // console.log('--------------1003----> 2: ', lol);

                /* axios
                     .get(
                         `${process.env.REACT_APP_API_URL}` +
                         "encadrement/download?fileName=" +
                         encodeURIComponent(p),

                         { responseType: "blob" }
                     )*/

                axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + lol, { responseType: "blob" })
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
                });
        }

        function piko(gdDecodedFullPath)
        {
            // console.log('--------------1003----> 0');
            let lol = encodeURIComponent(gdDecodedFullPath);
            // console.log('--------------1003----> 1: ', gdDecodedFullPath);
            // console.log('--------------1003----> 2: ', lol);

            /* axios
                 .get(
                     `${process.env.REACT_APP_API_URL}` +
                     "encadrement/download?fileName=" +
                     encodeURIComponent(p),

                     { responseType: "blob" }
                 )*/

            axios.get(`${process.env.REACT_APP_API_URL_STU}` + "hello/" + lol, { responseType: "blob" })
                .then((response) => {

                    console.log('-----PIKO 1511');
                    const file = new Blob([response.data], {type: 'application/pdf'});
                    const fileURL = URL.createObjectURL(file);

                    const contentDispo = response.headers['content-disposition'];
                    const fileName = contentDispo.substring(21);

                    let a = document.createElement('a');
                    a.href = fileURL;
                    a.download = fileName;
                    a.click();
                });
        }

        function downloadPlanTravail(studentId, dateDepotPT)
        {
            // console.log('--------------1003----> 0');
            //let lol = encodeURIComponent(dateDepotPT);
            // console.log('--------------1003----> 1: ', gdDecodedFullPath);
            // console.log('--------------1003----> 2: ', lol);

            /* axios
                 .get(
                     `${process.env.REACT_APP_API_URL}` +
                     "encadrement/download?fileName=" +
                     encodeURIComponent(p),

                     { responseType: "blob" }
                 )*/  // dateDepotFiche

            axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadPlanTravail?studentId=" + studentId + "&&dateDepotPT=" + dateDepotPT, { responseType: "blob" })
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
                });
        }

        return (
            <>
                {
                    (
                        fichePFEsHistoric.length === 0  // -Fiche PFE
                        ||
                        (fichePFEStatus !== undefined && fichePFEStatus === "05" && cancelFichePFEType === "Annulation" && cancelConventionType === "Oui" && cancelFichePFEEtat === "02")  // +FichePFE & Demande Annulation with Annulation Convention : Treated
                        ||
                        (fichePFEStatus === "05" && cancelFichePFEType === "Annulation" && cancelConventionType === "Non" && cancelFichePFEEtat === "02")  // +FichePFE & Demande Annulation without Annulation Convention : Treated
                        ||
                        (fichePFEStatus !== undefined && fichePFEStatus === "04")
                        ||
                        (fichePFEStatus !== undefined && fichePFEStatus === "01")
                        //||
                        //(fichePFEStatus !== undefined && fichePFEStatus === "01" && cancelFichePFEType === "01" && cancelFichePFEEtat === "02")  // +FichePFE DEPOSE-Demande of Modification
                    ) &&
                    <CRow>
                        <CCol md="2"/>
                        <CCol md="8">
                            <br/><br/>
                            <CCard accentColor="danger">
                                <CCardBody>
                                    <center>
                                        <br/><br/>
                                        Vous &nbsp;
                                        <span className="clignoteRed">
                                          n'avez pas encore déposé
                                        </span> &nbsp;votre Plan de Travail.

                                        <br/>

                                        Merci de procéder au &nbsp;
                                        <Link to={"/addESPFile"}>
                                            <span className="redMarkAlert">
                                              <ins>Processus de la Remise</ins>
                                            </span>
                                        </Link>
                                        &nbsp;.
                                        <br/><br/>
                                        <br/>
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
                    fichePFEsHistoric.length > 0 &&
                    (
                        fichePFEStatus === "02" ||
                        fichePFEStatus === "03" ||
                        (fichePFEStatus === "05" && cancelFichePFEType === "Annulation" && cancelConventionType === "Non" && cancelFichePFEEtat === "01") ||  // +FichePFE & Demande Annulation without Annulation Convention : Not Treated
                        (fichePFEStatus === "05" && cancelFichePFEType === "Annulation" && cancelConventionType === "Oui" && cancelFichePFEEtat === "01") ||  // +FichePFE & Demande Annulation with Annulation Convention : Not Treated
                        (fichePFEStatus === "05" && cancelFichePFEType === "Annulation" && cancelConventionType === "AUT" && cancelFichePFEEtat === "02") ||  // Annulation Convention ---> +FichePFE Annulation Not Treated
                        fichePFEStatus === "06" ||
                        fichePFEStatus === "07" ||
                        fichePFEStatus === "08"
                    )
                    /*(
                      !(this.state.projectTitle !== undefined && fichePFEStatus === "04") // +Fiche PFE REFUSEE
                      ||
                      !(this.state.projectTitle !== undefined && fichePFEStatus === "05" && cancelFichePFEType === "02" && cancelFichePFEEtat === "02")  // +Fiche PFE not ANNULEE-ANNULEE
                    )*/ &&
                    <>
                        {fichePFEsHistoric.map((fPFE) => (
                            <>
                                {fichePFEsHistoricBadge(fPFE.etat)}
                                <br/>
                                <CCard accentColor="danger">
                                    <CCardBody>
                                        <CRow>
                                            <CCol md="11" className="colPaddingRight">
                                                <CCallout color="dark" className={'bg-secondary'}>
                                                    <br/>
                                                    <span className="myModalSubTitleHistoric">
                                                        Titre Projet:
                                                    </span>
                                                    &nbsp;&nbsp;&nbsp;{fPFE.titreProjet}
                                                    <br/><br/>
                                                </CCallout>
                                            </CCol>
                                            <CCol md="1" className="colPaddingleft">
                                                <center>
                                                    <br/>
                                                    <LightTooltip title="Télécharger mon Plan de Travail" placement="top">
                                                        <span className="downloadGrayIcon" onClick={() => {downloadPlanTravail(currentStudent.id, fPFE.dateDepotFiche);}}/>
                                                    </LightTooltip>
                                                </center>
                                            </CCol>
                                        </CRow>

                                        <br/>
                                        <CTabs activeTab="desc">
                                            <CNav variant="tabs">
                                                <CNavItem>
                                                    <CNavLink data-tab="desc">
                                                        <span className="greyMark">Description</span>
                                                    </CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink data-tab="probs">
                                                        <span className="redMark">Problématiques</span> &nbsp;&nbsp;&nbsp;
                                                        <Badge badgeContent={fPFE.problematics.length}
                                                               classes={{ badge: classes.probsBdg}}/>
                                                    </CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink data-tab="funcs">
                                                        <span className="greenMark">Fonctionnalités</span> &nbsp;&nbsp;&nbsp;
                                                        <Badge badgeContent={fPFE.functionnalities.length}
                                                               classes={{ badge: classes.funcsBdg}}/>
                                                    </CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink data-tab="techs">
                                                        <span className="blueMark">Technologies</span> &nbsp;&nbsp;&nbsp;
                                                        <Badge badgeContent={fPFE.technologies.length}
                                                               classes={{ badge: classes.techsBdg}}/>
                                                    </CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink data-tab="gantDiag">
                                                        <span className="purpleMark">Diagramme de Gantt</span>
                                                    </CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink data-tab="compSups">
                                                        <span className="pinkMark">Entreprise Accueil & Encadrant(s)</span>
                                                    </CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink data-tab="trtms">
                                                        <span className="orangeMark">Traitements</span>
                                                    </CNavLink>
                                                </CNavItem>
                                            </CNav>
                                            <CTabContent>
                                                <CTabPane data-tab="desc">
                                                    <br/>
                                                    {
                                                        fPFE.descriptionProjet === "----------" ?
                                                            <span className="greyMarkCourrierSmalLabel">No Description !.</span>
                                                            :
                                                            <>{fPFE.descriptionProjet}</>
                                                    }
                                                </CTabPane>
                                                <CTabPane data-tab="probs">
                                                    <CListGroup accent>
                                                        {
                                                            fPFE.problematics.map((prob) => (
                                                                <>
                                                                    <br/>
                                                                    <CListGroupItem accent="danger">
                                                                        {prob.name}
                                                                    </CListGroupItem>
                                                                </>
                                                            ))}
                                                    </CListGroup>
                                                </CTabPane>
                                                <CTabPane data-tab="funcs">
                                                    <CListGroup accent>
                                                        {
                                                            fPFE.functionnalities.map((func) => (
                                                                <>
                                                                    <br/>
                                                                    <CListGroupItem accent="success">
                                                                        {func.name}
                                                                    </CListGroupItem>
                                                                </>
                                                            ))}
                                                    </CListGroup>
                                                </CTabPane>
                                                <CTabPane data-tab="techs">
                                                    <br/>
                                                    <CListGroup accent>
                                                        {
                                                            fPFE.technologies.map((tech) => (
                                                                <>
                                                                    <br/>
                                                                    <CListGroupItem accent="info">
                                                                        {tech.name}
                                                                    </CListGroupItem>
                                                                </>
                                                            ))}
                                                    </CListGroup>
                                                </CTabPane>
                                                <CTabPane data-tab="gantDiag">
                                                    <br/>
                                                    Votre Diagramme de Gantt est:
                                                    <br/>
                                                    <span className="greyMarkCourrierSmalLabel">{gotGanttDiagLabel(fPFE.pathDiagrammeGantt)}</span>
                                                    <br/><br/>
                                                  <LightTooltip title="Télécharger Diagramme de Gantt" placement="right">
                                                    <span className="downloadPurpleIcon" onClick={() => {
                                                      downloadGanttDiagramOFF(fPFE.pathDiagrammeGantt);
                                                    }}/>
                                                  </LightTooltip>
                                                </CTabPane>
                                                <CTabPane data-tab="compSups">
                                                    <br/>
                                                    <CRow>
                                                        <CCol md="7">
                                                            <Card sx={{ maxWidth: 530 }}>
                                                                <CardHeader avatar={<Avatar src={company} aria-label={fPFE.companyDto.designation}/>}
                                                                            title={fPFE.companyDto.designation}
                                                                            subheader="Entreprise Accueil"/>
                                                                <CardMedia  component="img"
                                                                            height="120"
                                                                            src={company}
                                                                            alt="Entreprise Accueil"/>
                                                                <CardContent>
                                                                    <Typography variant="body2" color="text.secondary">
                                                                        <CRow>
                                                                            <CCol md="3" className="colPaddingRight">
                                                                            <span className="convHistoricGreyField">
                                                                                Siége Social:
                                                                            </span>
                                                                            </CCol>
                                                                            <CCol md="9">
                                                                                {
                                                                                    fPFE.companyDto.siegeSocial === "---" ?
                                                                                        <>--</>
                                                                                        :
                                                                                        <>{fPFE.companyDto.siegeSocial}</>
                                                                                }
                                                                            </CCol>

                                                                            <CCol md="3" className="colPaddingRight">
                                                                            <span className="convHistoricGreyField">
                                                                                Secteur Activité:
                                                                            </span>
                                                                            </CCol>
                                                                            <CCol md="9">
                                                                                {fPFE.companyDto.libelleSecteur}
                                                                            </CCol>
                                                                        </CRow>
                                                                    </Typography>
                                                                </CardContent>
                                                                <CardActions disableSpacing>
                                                                    <ExpandMore expand={expanded}
                                                                                onClick={handleExpandClick}
                                                                                aria-expanded={expanded}
                                                                                aria-label="show more">
                                                                        <ExpandMoreIcon />
                                                                    </ExpandMore>
                                                                </CardActions>
                                                                <Collapse in={expanded} timeout="auto" unmountOnExit>
                                                                    <CardContent>
                                                                        <CRow>
                                                                            <CCol md="2" className="colPaddingRight">
                                                                            <span className="convHistoricGreyField">
                                                                                Adresse:
                                                                            </span>
                                                                            </CCol>
                                                                            <CCol md="10">
                                                                                {fPFE.companyDto.address}
                                                                                {
                                                                                    fPFE.companyDto.nomPays === null ?
                                                                                        <></>
                                                                                        :
                                                                                        <>{', ' + fPFE.companyDto.nomPays}</>
                                                                                }
                                                                            </CCol>

                                                                            <CCol md="2" className="colPaddingRight">
                                                                                    <span className="convHistoricGreyField">
                                                                                        E-Mail:
                                                                                    </span>
                                                                            </CCol>
                                                                            <CCol md="10">
                                                                                {fPFE.companyDto.mail}
                                                                            </CCol>

                                                                            <CCol md="2" className="colPaddingRight">
                                                                                    <span className="convHistoricGreyField">
                                                                                        Téléphone:
                                                                                    </span>
                                                                            </CCol>
                                                                            <CCol md="10">
                                                                                {
                                                                                    fPFE.companyDto.phone === "--------" ?
                                                                                        <>--</>
                                                                                        :
                                                                                        <>{fPFE.companyDto.phone}</>
                                                                                }
                                                                            </CCol>

                                                                            <CCol md="2" className="colPaddingRight">
                                                                            <span className="convHistoricGreyField">
                                                                                Fax:
                                                                            </span>
                                                                            </CCol>
                                                                            <CCol md="10">
                                                                                {
                                                                                    fPFE.companyDto.fax === "--------" ?
                                                                                        <>--</>
                                                                                        :
                                                                                        <>{fPFE.companyDto.fax}</>
                                                                                }
                                                                            </CCol>
                                                                        </CRow>
                                                                    </CardContent>
                                                                </Collapse>
                                                            </Card>
                                                        </CCol>
                                                        <CCol md="5">
                                                        <span className="compSupervHistoricPurpleTitle">
                                                            Encadrant(s) Entreprise
                                                        </span>
                                                            <hr/>
                                                            {fPFE.companySupervisors.map((cs) => (
                                                                <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                                                                    {companySupervisorsForESPPFileHist(cs)}
                                                                </List>
                                                            ))}
                                                        </CCol>
                                                    </CRow>
                                                </CTabPane>
                                                <CTabPane data-tab="trtms">
                                                    <br/>
                                                    <CListGroup accent>
                                                        {
                                                            fPFE.treatmentFiches.length === 0 ?
                                                                <>Pas Encore de Traitement pour Cette Fiche !.</>
                                                                :
                                                                <>
                                                                    {fPFE.treatmentFiches.map((tf) => (
                                                                        <>
                                                                            <br/>
                                                                            <CListGroupItem accent="warning">
                                                                                {treatmentsForESPPFileHist(tf)}
                                                                            </CListGroupItem>
                                                                        </>
                                                                    ))}
                                                                </>
                                                        }
                                                    </CListGroup>
                                                </CTabPane>
                                            </CTabContent>
                                        </CTabs>
                                        <br/>
                                        <br/>
                                    </CCardBody>
                                </CCard>
                            </>
                        ))}
                    </>
                }
            </>
        )
    };

    return (
        <>
            {contentConsultESPFile()}
        </>
    );
};

ConsultESPFile.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(styles)(ConsultESPFile);
