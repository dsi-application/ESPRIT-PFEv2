import React, {useState, useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Button from "@material-ui/core/Button";
import PropTypes from "prop-types";
import {styled} from "@mui/material/styles";
import Stack from "@mui/material/Stack";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Check from "@mui/icons-material/Check";
import StepConnector, {stepConnectorClasses} from "@mui/material/StepConnector";
import ArticleIcon from '@mui/icons-material/Article';
import AirplayIcon from '@mui/icons-material/Airplay';
import PeopleIcon from '@mui/icons-material/People';
import Badge from '@mui/material/Badge';
import StyleIcon from '@material-ui/icons/Style';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
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
import {freeSet} from '@coreui/icons';
import {useFormik} from "formik";

import HomeWorkIcon from '@mui/icons-material/HomeWork';
import {
  CNav,
  CForm,
  CListGroupItem,
  CListGroup,
  CFormGroup,
  CNavItem,
  CNavLink,
  CTabContent,
  CTabPane,
  CTabs,
  CTextarea,
  CWidgetDropdown,
  CAlert,
  CWidgetIcon,
  CCard,
  CCardImg,
  CCardBody,
  CCol,
  CRow,
  CButton,
  CTooltip,
  CBadge,
  CJumbotron,
  CCallout,
  CWidgetBrand
} from "@coreui/react";
import * as Yup from "yup";
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import axios from "axios";
import {purple} from '@mui/material/colors';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {withStyles} from "@material-ui/core/styles";
import Tooltip, {tooltipClasses} from '@mui/material/Tooltip';
import company from "../../images/company.jpg";

import {
  gotActiveStudentTimelineStep,
  gotConventionDetails,
  gotDepotReportsDetails,
  gotFichePFEDetails,
  gotStudentFullName,
  gotTimelineSteps,
  selectConventionsHistoric,
  selectFichePFEsHistoric,
  gotStudentId,
  fetchFichePFEDetails,
  fetchTimelineSteps,
  fetchActiveStudentTimelineStep,
  fetchFichePFEsHistoric,
  gotStudentExpert
} from "../../../redux/slices/MyStudentTBSSlice";

import TextScrollerStuTL from "../../css/TextScrollerStuTL";
import Spinner from "react-bootstrap/Spinner";
import Avatar from '@mui/material/Avatar';
import {deepOrange, deepPurple} from '@mui/material/colors';
import {Wave} from "react-animated-text";
import {queryApi} from "../../../utils/queryApi";
import AuthService from "../../services/auth.service";
import myDoc from "../../images/myDoc.jpg";
import emptyBack from "../../images/emptyBack.png";

import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import makeAnimated from "react-select/animated";

const currentTeacher = AuthService.getCurrentTeacher();

const LightTooltip = styled(({className, ...props}) => (
  <Tooltip {...props} classes={{popper: className}}/>
))(({theme}) => ({
  [`& .${tooltipClasses.tooltip}`]: {
    backgroundColor: theme.palette.common.white,
    color: 'rgba(0, 0, 0, 0.87)',
    boxShadow: theme.shadows[1],
    fontSize: 11,
  },
}));

const ExpandMore = styled((props) => {
  const {expand, ...other} = props;
  return <IconButton {...other} />;
})(({theme, expand}) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

const QontoConnector = styled(StepConnector)(({theme}) => ({
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

const QontoStepIconRoot = styled("div")(({theme, ownerState}) => ({
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
  const {active, completed, className} = props;

  return (
    <QontoStepIconRoot ownerState={{active}} className={className}>
      {completed ? (
        <Check className="QontoStepIcon-completedIcon"/>
      ) : (
        <div className="QontoStepIcon-circle"/>
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

const ColorlibConnector = styled(StepConnector)(({theme}) => ({
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

const ColorlibStepIconRoot = styled("div")(({theme, ownerState}) => ({
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
  const {active, completed, className} = props;

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
    12: <MenuBookIcon/>,
    13: <BusinessCenterIcon/>
  };

  return (
    <ColorlibStepIconRoot
      ownerState={{completed, active}}
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
const validationSchema = Yup.object().shape({
  observation: Yup.string()
    .max(500, "Merci de ne pas dépasser 500 caractères !.")
    .required("(*) Motif Refus Fiche PFE est un champs obligatoire !."),
});

const validationSchemaSARS = Yup.object().shape({
  observation: Yup.string()
    .max(500, "Merci de ne pas dépasser 500 caractères !.")
    .required("(*) lol !."),
});

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

function MyStudentTimeline(props) {

  const dispatch = useDispatch();
  const [steps, errds1] = useSelector(gotTimelineSteps);
  const [conventionDetails, errds2] = useSelector(gotConventionDetails);
  const [fichePFEDetails, errds3] = useSelector(gotFichePFEDetails);
  const [depotReportsDetails, errds4] = useSelector(gotDepotReportsDetails);
  const [activeStudentTimelineStep, errds5] = useSelector(gotActiveStudentTimelineStep);
  const [studentFullName, errds6] = useSelector(gotStudentFullName);
  const [conventionsHistoric, errds7] = useSelector(selectConventionsHistoric);
  const [fichePFEsHistoric, errds8] = useSelector(selectFichePFEsHistoric);
  const [studentId, errds9] = useSelector(gotStudentId);
  const [studentExpertObj, errds10] = useSelector(gotStudentExpert);
  const [submitAgreement, setSubmitAgreement] = useState(false);
  const [myDocuments, setMyDocuments] = useState(false);
  const [consultESPFile, setConsultESPFile] = useState(false);
  const [uploadNewspaper, setUploadNewspaper] = useState(false);
  const [uploadBalanceSheet, setUploadBalanceSheet] = useState(false);
  const [uploadReport, setUploadReport] = useState(false);

  const [dateDepotFiche, setDateDepotFiche] = useState("");
  const [dateSaisieDemande, setDateSaisieDemande] = useState("");
  const [typeTrtConv, setTypeTrtConv] = useState("");

  const [error, setError] = useState({visible: false, message: ""});
  const [stepTitle, setStepTitle] = useState(null);
  const [stepId, setStepId] = useState(null);
  const [stepDate, setStepDate] = useState(null);
  const [stepComment, setStepComment] = useState(null);
  const [visible, setVisible] = useState(false);

  const [showVerifValidateFichePFE, setShowVerifValidateFichePFE] = useState(false);
  const [showVerifRefuseFichePFE, setShowVerifRefuseFichePFE] = useState(false);
  const [showLoadSpinnerForValidateFichePFE, setShowLoadSpinnerForValidateFichePFE] = useState(false);
  const [showLoadSpinnerForRefuseFichePFE, setShowLoadSpinnerForRefuseFichePFE] = useState(false);
  const [showSuccessYESValidateFichePFE, setShowSuccessYESValidateFichePFE] = useState(false);
  const [showSuccessYESRefuseFichePFE, setShowSuccessYESRefuseFichePFE] = useState(false);
  const [showPresentMotifForRefuseFichePFE, setShowPresentMotifForRefuseFichePFE] = useState(false);

  const [showVerifValidateDemandeModif, setShowVerifValidateDemandeModif] = useState(false);
  const [showVerifRefuseDemandeModif, setShowVerifRefuseDemandeModif] = useState(false);
  const [showLoadSpinnerForValidateDemandeModif, setShowLoadSpinnerForValidateDemandeModif] = useState(false);
  const [showLoadSpinnerForRefuseDemandeModif, setShowLoadSpinnerForRefuseDemandeModif] = useState(false);
  const [showSuccessYESValidateDemandeModif, setShowSuccessYESValidateDemandeModif] = useState(false);
  const [showSuccessYESRefuseDemandeModif, setShowSuccessYESRefuseDemandeModif] = useState(false);
  const [showPresentMotifForRefuseDemandeModif, setShowPresentMotifForRefuseDemandeModif] = useState(false);

  const [showVerifValidateDemandeAnnul, setShowVerifValidateDemandeAnnul] = useState(false);
  const [showVerifRefuseDemandeAnnul, setShowVerifRefuseDemandeAnnul] = useState(false);
  const [showLoadSpinnerForValidateDemandeAnnul, setShowLoadSpinnerForValidateDemandeAnnul] = useState(false);
  const [showLoadSpinnerForRefuseDemandeAnnul, setShowLoadSpinnerForRefuseDemandeAnnul] = useState(false);
  const [showSuccessYESValidateDemandeAnnul, setShowSuccessYESValidateDemandeAnnul] = useState(false);
  const [showSuccessYESRefuseDemandeAnnul, setShowSuccessYESRefuseDemandeAnnul] = useState(false);
  const [showPresentMotifForRefuseDemandeAnnul, setShowPresentMotifForRefuseDemandeAnnul] = useState(false);

  const [showModalRequestConfirm, setShowModalRequestConfirm] = useState(false);
  const [showLoadSpinnerSendMail, setShowLoadSpinnerSendMail] = useState(false);
  const [showModalSuccessSendRequest, setShowModalSuccessSendRequest] = useState(false);
  const [showModalSpecifyKeyWords, setShowModalSpecifyKeyWords] = useState(false);

  const [expanded, setExpanded] = React.useState(false);

  const [selectedThem, setSelectedThem] = useState([]);
  const [selectedTech, setSelectedTech] = useState([]);
  const [selectedDesc, setSelectedDesc] = useState([]);

  const nbaThems = ['Exemple Thématique 1', 'Exemple Thématique 2', 'Saisir Manuellement Tag(s) Thématique(s)']
  const nbaTechs = ['Spring Boot', 'Tensorflow', 'Saisir Manuellement Tag(s) Technologie(s)']
  const nbaDescs = ['Exemple Descipline 1', 'Exemple Descipline 2', 'Saisir Manuellement Tag(s) Descipline(s)']

  const {classes} = props;

  const confirmAffectExpertRequestUP = () => {
    setShowModalSpecifyKeyWords(true);
    setVisible(false);
  };

  const toggleItem = (stepItem = null) => {
    // console.log('---------------------1-------> 2911-stepPath: ', stepItem);
    setStepId(stepItem.stepId)
    setStepTitle(stepItem.stepTitle);
    setStepComment(stepItem.stepComment);
    setStepDate(stepItem.stepDate);
    setVisible(true);
    // console.log('---------------------2-------> 2911-stepPath: ', stepItem);

  };

  // Manage ESP File
  const verifyValidateESPFile = () => {
    // console.log('---------------------1-------> 0412-verifyValidateESPFile: ');
    setShowVerifValidateFichePFE(true);
    // console.log('---------------------2-------> 0412-verifyValidateESPFile: ');
  };

  const verifyRefuseESPFile = () => {
    // console.log('---------------------1-------> 0412-verifyValidateESPFile: ');
    setShowVerifRefuseFichePFE(true);
    // console.log('---------------------2-------> 0412-verifyValidateESPFile: ');
  };

  const handleValidateESPFileYES = async () => {
    // console.log('------------studentId---------1-------> 0412-handleValidateESPFile: ', studentId);
    setShowLoadSpinnerForValidateFichePFE(true);
    const [res, err] = await queryApi(
      "academicEncadrant/validateESPFile?idEt=" + studentId + "&mailAE=" + currentTeacher.id,
      {},
      "PUT",
      false
    );
    if (err) {
      /*setShowLoader(false);
            setError({
                visible: true,
                message: JSON.stringify(err.errors, null, 2),
            });*/
      // console.log('NOOOOOOOOOOOOOOOOOOOOOOO');
    } else {
      dispatch(fetchFichePFEsHistoric(studentId)); // ...
      dispatch(fetchActiveStudentTimelineStep(studentId)); // Active Step
      dispatch(fetchFichePFEDetails(studentId)); // Badge
      dispatch(fetchTimelineSteps(studentId));  // Date Step

      setShowLoadSpinnerForValidateFichePFE(false);
      setShowVerifValidateFichePFE(false);
      setShowSuccessYESValidateFichePFE(true);
    }
    // console.log('------------studenvvtId---------2-------> 0412-handleValidateESPFile: ', res);
  };

  const handlePresentMotifForRefuseESPFileYES = async () => {
    // console.log('------------studentId---------1-------> 0412-handlePresentMotifForRefuseESPFileYES:');
    setShowPresentMotifForRefuseFichePFE(true);
    setShowVerifRefuseFichePFE(false);
  };

  const handleValidateESPFileNO = () => {
    // console.log('---------------------1-------> 0412-handleValidateESPFile: ');
    setShowLoadSpinnerForValidateFichePFE(false);
    setShowVerifValidateFichePFE(false);
    // console.log('---------------------2-------> 0412-handleValidateESPFile: ');
  };

  const handleRefuseESPFileNO = () => {
    // console.log('---------------------1-------> 0412-handleRefuseESPFileNO: ');
    setShowLoadSpinnerForRefuseFichePFE(false);
    setShowVerifRefuseFichePFE(false);
    // console.log('---------------------2-------> 0412-handleRefuseESPFileNO: ');
  };

  const handleExitSuccessYESValidateESPFile = () => {
    setShowSuccessYESValidateFichePFE(false);
  }

  const handleExitSuccessYESRefuseESPFile = () => {
    setShowSuccessYESRefuseFichePFE(false);
  }

  const handleExitPresentMotifForRefuseFichePFE = () => {
    setShowPresentMotifForRefuseFichePFE(false);
  }

  // Manage Demande Modification
  const verifyValidateDemandeModification = (dateDepotFiche, dateSaisieDemande) => {
    // console.log('---------------------1-------> 0412-verifyValidateDemandeModification: ');
    setShowVerifValidateDemandeModif(true);
    setDateDepotFiche(dateDepotFiche);
    setDateSaisieDemande(dateSaisieDemande);
    // console.log('---------------------0712-------> 0412-dateDepotFiche: ', dateDepotFiche);
    // console.log('---------------------0712-------> 0412-dateSaisieDemande: ', dateSaisieDemande);
  };

  const verifyRefuseDemandeModification = (dateDepotFiche, dateSaisieDemande) => {
    // console.log('---------------------1-------> 0412-verifyRefuseDemandeModification: ');
    setShowVerifRefuseDemandeModif(true);
    setDateDepotFiche(dateDepotFiche);
    setDateSaisieDemande(dateSaisieDemande);
    // console.log('---------------------2-------> 0412-verifyRefuseDemandeModification: ');
  };

  //
  const handleValidateDemandeModifYES = async () => {
    // console.log('------------studentId---------0712-------> 0412-handleValidateESPFile: ', studentId);

    // console.log('------------studentId---------0712-------> 0412-dateDepotFiche: ', dateDepotFiche);
    // console.log('------------studentId---------0712-------> 0412-dateSaisieDemande: ', dateSaisieDemande);

    setShowLoadSpinnerForValidateDemandeModif(true);
    const [res, err] = await queryApi(
      "academicEncadrant/validateDemandeModifESPFile?idEt=" + studentId + "&mailAE=" + currentTeacher.id + "&dateDepotFiche=" + dateDepotFiche + "&dateSaisieDemande=" + dateSaisieDemande,
      {},
      "PUT",
      false
    );
    if (err) {
      /*setShowLoader(false);
            setError({
                visible: true,
                message: JSON.stringify(err.errors, null, 2),
            });*/
      // console.log('NOOOOOOOOOOOOOOOOOOOOOOO****0712');
    } else {
      dispatch(fetchFichePFEsHistoric(studentId)); // Update Fields of All Existing Fiches PFE History
      dispatch(fetchActiveStudentTimelineStep(studentId)); // Active Step
      dispatch(fetchFichePFEDetails(studentId)); // Badge
      dispatch(fetchTimelineSteps(studentId));  // Date Step

      setShowLoadSpinnerForValidateDemandeModif(false);
      setShowVerifValidateDemandeModif(false);
      setShowSuccessYESValidateDemandeModif(true);
    }
    // console.log('------------studentId---------0712-------> 0412-validateDemandeModifESPFile: ', res);
  };

  const handlePresentMotifForRefuseDemandeModifYES = async () => {
    // console.log('------------studentId---------1-------> 0412-handlePresentMotifForRefuseDemandeModifYES:');
    setShowPresentMotifForRefuseDemandeModif(true);
    setShowVerifRefuseDemandeModif(false);
  };

  const handleValidateDemandeModifNO = () => {
    // console.log('---------------------1-------> 0412-handleValidateDemandeModifNO: ');
    setShowLoadSpinnerForValidateDemandeModif(false);
    setShowVerifValidateDemandeModif(false);
    // console.log('---------------------2-------> 0412-handleValidateDemandeModifNO: ');
  };

  const handleRefuseDemandeModifNO = () => {
    // console.log('---------------------1-------> 0412-handleRefuseDemandeModifNO: ');
    setShowLoadSpinnerForRefuseDemandeModif(false);
    setShowVerifRefuseDemandeModif(false);
    // console.log('---------------------2-------> 0412-handleRefuseDemandeModifNO: ');
  };

  const handleExitSuccessYESValidateDemandeModif = () => {
    setShowSuccessYESValidateDemandeModif(false);
  }

  const handleExitSuccessYESRefuseDemandeModif = () => {
    setShowSuccessYESRefuseDemandeModif(false);
  }

  const handleExitPresentMotifForRefuseDemandeModif = () => {
    setShowPresentMotifForRefuseDemandeModif(false);
  }


  // Manage Demande Annulation
  const verifyValidateDemandeAnnulation = (dateDepotFiche, dateSaisieDemande, typeTrtConv) => {
    // console.log('---------------------1-------> 0412-verifyValidateDemandeAnnulation: ');
    setShowVerifValidateDemandeAnnul(true);
    setDateDepotFiche(dateDepotFiche);
    setDateSaisieDemande(dateSaisieDemande);
    setTypeTrtConv(typeTrtConv);
    // console.log('---------------------0712-------> 0412-dateDepotFiche: ', dateDepotFiche);
    // console.log('---------------------0712-------> 0412-dateSaisieDemande: ', dateSaisieDemande);
  };

  const verifyRefuseDemandeAnnulation = (dateDepotFiche, dateSaisieDemande, typeTrtConv) => {
    // console.log('---------------------1-------> 0412-verifyRefuseDemandeAnnulation: ');
    setShowVerifRefuseDemandeAnnul(true);
    setDateDepotFiche(dateDepotFiche);
    setDateSaisieDemande(dateSaisieDemande);
    setTypeTrtConv(typeTrtConv);
    // console.log('---------------------2-------> 0412-verifyRefuseDemandeAnnulation: ');
  };

  const handleValidateDemandeAnnulYES = async () => {
    // console.log('------------studentId---------0712-------> 0412-handleValidateESPFile: ', studentId);

    // console.log('------------studentId---------0712-------> 0412-dateDepotFiche: ', dateDepotFiche);
    // console.log('------------studentId---------0712-------> 0412-dateSaisieDemande: ', dateSaisieDemande);

    setShowLoadSpinnerForValidateDemandeAnnul(true);
    const [res, err] = await queryApi(
      "academicEncadrant/validateDemandeAnnulESPFile?idEt=" + studentId + "&mailAE=" + currentTeacher.id + "&dateDepotFiche=" + dateDepotFiche + "&dateSaisieDemande=" + dateSaisieDemande + "&typeTrtConvention=" + typeTrtConv,
      {},
      "PUT",
      false
    );
    if (err) {
      /*setShowLoader(false);
            setError({
                visible: true,
                message: JSON.stringify(err.errors, null, 2),
            });*/
      // console.log('NOOOOOOOOOOOOOOOOOOOOOOO****0712');
    } else {
      dispatch(fetchFichePFEsHistoric(studentId));
      dispatch(fetchFichePFEDetails(studentId));
      dispatch(fetchTimelineSteps(studentId));  // Date Step
      dispatch(fetchActiveStudentTimelineStep(studentId)); // Active Step
      setShowLoadSpinnerForValidateDemandeAnnul(false);
      setShowVerifValidateDemandeAnnul(false);
      setShowSuccessYESValidateDemandeAnnul(true);
    }
    // console.log('------------studentId---------0712-------> 0412-validateDemandeAnnulESPFile: ', res);
  };

  const handlePresentMotifForRefuseDemandeAnnulYES = async () => {
    // console.log('------------studentId---------1-------> 0412-handlePresentMotifForRefuseDemandeAnnulYES:');
    setShowPresentMotifForRefuseDemandeAnnul(true);
    setShowVerifRefuseDemandeAnnul(false);
  };

  const handleValidateDemandeAnnulNO = () => {
    // console.log('---------------------1-------> 0412-handleValidateDemandeAnnulNO: ');
    setShowLoadSpinnerForValidateDemandeAnnul(false);
    setShowVerifValidateDemandeAnnul(false);
    // console.log('---------------------2-------> 0412-handleValidateDemandeAnnulNO: ');
  };

  const handleRefuseDemandeAnnulNO = () => {
    // console.log('---------------------1-------> 0412-handleRefuseDemandeAnnulNO: ');
    setShowLoadSpinnerForRefuseDemandeAnnul(false);
    setShowVerifRefuseDemandeAnnul(false);
    // console.log('---------------------2-------> 0412-handleRefuseDemandeAnnulNO: ');
  };

  const handleExitSuccessYESValidateDemandeAnnul = () => {
    setShowSuccessYESValidateDemandeAnnul(false);
  }

  const handleExitSuccessYESRefuseDemandeAnnul = () => {
    setShowSuccessYESRefuseDemandeAnnul(false);
  }

  const handleExitPresentMotifForRefuseDemandeAnnul = () => {
    setShowPresentMotifForRefuseDemandeAnnul(false);
  }

  const openModal = (submitAgreement) => {
    setSubmitAgreement(!(!submitAgreement));
  };

  const closeModal = () => {
    setVisible(false);
  };

  const convHistoric = () => {
    return (
      <>
        {
          conventionsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucune Convevention VALIDÉE !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {conventionsHistoric.map((conv) => (
                <>
                  {convHistoricBadge(conv.traiter)}
                  <br/>
                  <CCard accentColor="danger">
                    <CCardBody>
                      <CRow>
                        <CCol md="5">
                          <CCard className="darkBorder">
                            <CCardBody>
                              <Stack direction="row"
                                     justifyContent="flex-start"
                                     alignItems="flex-start"
                                     spacing={0.5}>
                                <span className="calendarIcon"/>
                                <span className="grey13">
                                                                    Dates
                                                                </span>
                              </Stack>
                              <br/>
                              <CAlert color="dark">
                                <center>
                                  <strong className="text-muted">
                                    Date Demande Convention
                                  </strong>
                                  <br/>
                                  {conv.dateConvention}
                                </center>
                                <hr/>
                                <CRow>
                                  <CCol md="6">
                                    <center>
                                      <strong className="text-muted">
                                        Date Début Stage
                                      </strong>
                                      <br/>
                                      {conv.dateDebut}
                                    </center>
                                  </CCol>
                                  <CCol md="6">
                                    <center>
                                      <strong className="text-muted">
                                        Date Fin stage
                                      </strong>
                                      <br/>
                                      {conv.dateFin}
                                    </center>
                                  </CCol>
                                </CRow>
                              </CAlert>
                            </CCardBody>
                          </CCard>

                          {
                            conv.traiter === '03' &&
                            <CCard className="orangeBorder">
                              <CCardBody>
                                <Stack direction="row"
                                       justifyContent="flex-start"
                                       alignItems="flex-start"
                                       spacing={0.5}>
                                  <span className="warningOrangeIcon"/>
                                  <span className="orange13">
                                                                        Motif Annulation Convention:
                                                                    </span>
                                </Stack>
                                <br/>
                                {conv.motifAnnulation}
                              </CCardBody>
                            </CCard>
                          }
                          {
                            conv.avenantsHistoryDto.length > 0 &&
                            <>
                              <CCard className="pinkBorder">
                                <CCardBody>
                                  <Stack direction="row"
                                         justifyContent="flex-start"
                                         alignItems="flex-start"
                                         spacing={0.5}>
                                    <span className="historyIcon"/>
                                    <span className="pink13">
                                                                            Historiques des Avenants
                                                                        </span>
                                  </Stack>
                                  {conv.avenantsHistoryDto.map((avn) => (
                                    <CCallout color="secondary" className={'bg-light'}>
                                      <br/>
                                      <strong className="text-muted">Date Demande Avenant: </strong>{avn.dateAvenant}
                                      <br/>
                                      <strong className="text-muted">Durée Stage: </strong>
                                      <strong className="text-muted">[ </strong>
                                      {avn.dateDebut}
                                      <strong className="text-muted"> ... </strong>
                                      {avn.dateFin}
                                      <strong className="text-muted"> ]</strong>
                                      <br/><br/>
                                      <CRow>
                                        <CCol md="2">
                                          <HomeWorkIcon sx={{color: purple[400]}}/>
                                        </CCol>
                                        <CCol md="10">
                                          Entreprise Accueil présentée par :<br/>
                                          <span className="greyMarkCourrierHst">{avn.responsableEntreprise}</span><br/>
                                          <span className="greyMarkItalic">({avn.qualiteResponsable})</span>
                                        </CCol>
                                      </CRow>
                                      <br/>
                                    </CCallout>
                                  ))}
                                </CCardBody>
                              </CCard>
                            </>
                          }
                        </CCol>
                        <CCol md="7">
                          <Card sx={{maxWidth: 530}}>
                            <CardHeader avatar={<Avatar src={company} aria-label={conv.companyDesignation}/>}
                                        title={conv.companyDesignation}
                                        subheader={'Entreprise Accueil présentée par Mme/Mr ' + conv.responsable}/>
                            <CardMedia component="img"
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
                                      conv.companySiegeSocial === "---" ?
                                        <>--</>
                                        :
                                        <>{conv.companySiegeSocial}</>
                                    }
                                  </CCol>

                                  <CCol md="3" className="colPaddingRight">
                                                                        <span className="convHistoricGreyField">
                                                                            Secteur Activité:
                                                                        </span>
                                  </CCol>
                                  <CCol md="9">
                                    {conv.companyLibelleSecteur}
                                  </CCol>
                                </CRow>
                              </Typography>
                            </CardContent>
                            <CardActions disableSpacing>
                              <ExpandMore expand={expanded}
                                          onClick={handleExpandClick}
                                          aria-expanded={expanded}
                                          aria-label="show more">
                                <ExpandMoreIcon/>
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
                                    {conv.address}
                                    {
                                      conv.companyNomPays === "--" ?
                                        <></>
                                        :
                                        <>{', ' + conv.companyNomPays}</>
                                    }
                                  </CCol>

                                  <CCol md="2" className="colPaddingRight">
                                                                        <span className="convHistoricGreyField">
                                                                            E-Mail:
                                                                        </span>
                                  </CCol>
                                  <CCol md="10">
                                    {conv.companyAddressMail}
                                  </CCol>

                                  <CCol md="2" className="colPaddingRight">
                                                                        <span className="convHistoricGreyField">
                                                                            Téléphone:
                                                                        </span>
                                  </CCol>
                                  <CCol md="10">
                                    {
                                      conv.telephone === "--------" ?
                                        <>--</>
                                        :
                                        <>{conv.telephone}</>
                                    }
                                  </CCol>

                                  <CCol md="2" className="colPaddingRight">
                                                                            <span className="convHistoricGreyField">
                                                                                Fax:
                                                                            </span>
                                  </CCol>
                                  <CCol md="10">
                                    {
                                      conv.companyFax === "--------" ?
                                        <>--</>
                                        :
                                        <>{conv.companyFax}</>
                                    }
                                  </CCol>
                                </CRow>
                              </CardContent>
                            </Collapse>
                          </Card>
                        </CCol>
                      </CRow>
                    </CCardBody>
                  </CCard>
                </>
              ))}
            </>
        }
      </>
    );
  };

  const fichePFEHistoric = () => {

    function gotGanttDiagLabel(ganDiagFP) {
      let gdName = ganDiagFP.substring(ganDiagFP.lastIndexOf("C:/ESP/uploads/") + 15, ganDiagFP.lastIndexOf("espdsi2020"));
      let gdExt = ganDiagFP.substring(ganDiagFP.lastIndexOf("."));

      let ganttDiagramFP = gdName + gdExt;
      return ganttDiagramFP;
    }


    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
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
                            <LightTooltip title="Télécharger Plan de Travail" placement="top">
                              <span className="downloadGrayIcon" onClick={() => {
                                downloadPlanTravail(fPFE.dateDepotFiche);
                              }}/>
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
                                     classes={{badge: classes.probsBdg}}/>
                            </CNavLink>
                          </CNavItem>
                          <CNavItem>
                            <CNavLink data-tab="funcs">
                              <span className="greenMark">Technologies</span> &nbsp;&nbsp;&nbsp;
                              <Badge badgeContent={fPFE.functionnalities.length}
                                     classes={{badge: classes.funcsBdg}}/>
                            </CNavLink>
                          </CNavItem>
                          <CNavItem>
                            <CNavLink data-tab="techs">
                              <span className="blueMark">Technologies</span> &nbsp;&nbsp;&nbsp;
                              <Badge badgeContent={fPFE.technologies.length}
                                     classes={{badge: classes.techsBdg}}/>
                            </CNavLink>
                          </CNavItem>
                          <CNavItem>
                            <CNavLink data-tab="gantDiag">
                              <span className="purpleMark">Diag. Gantt</span>
                            </CNavLink>
                          </CNavItem>
                          <CNavItem>
                            <CNavLink data-tab="compSups">
                              <span className="pinkMark">Entreprise Accueil</span>
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
                            <span
                              className="greyMarkCourrierSmalLabel">{gotGanttDiagLabel(fPFE.pathDiagrammeGantt)}</span>
                            <br/><br/>
                            <LightTooltip title="Télécharger Diagramme de Gantt" placement="right">
                              <span className="downloadPurpleIcon" onClick={() => {
                                downloadPDFFile(fPFE.pathDiagrammeGantt);
                              }}/>
                            </LightTooltip>
                          </CTabPane>
                          <CTabPane data-tab="compSups">
                            <br/>
                            <CRow>
                              <CCol md="7">
                                <Card sx={{maxWidth: 530}}>
                                  <CardHeader avatar={<Avatar src={company} aria-label={fPFE.companyDto.designation}/>}
                                              title={fPFE.companyDto.designation}
                                              subheader="Entreprise Accueil"/>
                                  <CardMedia component="img"
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
                                      <ExpandMoreIcon/>
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
                                                                                    <span
                                                                                      className="convHistoricGreyField">
                                                                                        E-Mail:
                                                                                    </span>
                                        </CCol>
                                        <CCol md="10">
                                          {fPFE.companyDto.mail}
                                        </CCol>

                                        <CCol md="2" className="colPaddingRight">
                                                                                    <span
                                                                                      className="convHistoricGreyField">
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
                                  <List sx={{width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>
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
                      {fichePFEsHistoricTreatment(fPFE.etat)}
                    </CCardBody>
                  </CCard>
                </>
              ))}
            </>
        }
      </>
    );
  };

  const downloadPDFFile = (filePath) => {

    let aze = encodeURIComponent(encodeURIComponent(filePath));
    console.log('------------------------------- AZE-2: ', aze)
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + aze, {responseType: "blob"})
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

  const journalUnitHistoric = (fPFE) => {

    let filePath = fPFE.pathJournalStg;
    let fileName = "--";
    if (filePath !== null) {
      fileName = filePath.substring(filePath.indexOf("uploads") + 8, filePath.indexOf("espdsi2020"));
    }

    let e = fPFE.etat;
    let color = "--";
    if (e === "01") {
      color = "secondary";
    }
    if (e === "02") {
      color = "info";
    }
    if (e === "03") {
      color = "success";
    }
    if (e === "04") {
      color = "danger";
    }
    if (e === "05") {
      color = "warning";
    }
    if (e === "06") {
      color = "dark";
    }
    if (e === "07" || e === "08") {
      color = "primary";
    }

    return (
      <>

        <CCard color="light">
          <CCardBody>
            <CRow>
              <CCol md="4">
                {fichePFEsHistoricBadge(fPFE.etat)}
                <br/>
                <CWidgetIcon text={fPFE.titreProjet} header='Titre Projet' color={color}>
                  <MenuBookIcon/>
                </CWidgetIcon>
              </CCol>
              <CCol md="8">
                {
                  filePath !== null &&
                  <center>
                    <CCard>
                      <CCardImg variant="top" src={emptyBack} height="80px"/>
                      <CCardBody>
                        <center>
                          <span className="clignoteBoldGrey">Journal de Bord</span>
                          <br/><br/>

                          <CRow>
                            <CCol md='9'>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Label:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fileName + '.pdf'}
                                </CCol>
                              </CRow>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Date Dépôt:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fPFE.dateDepotJournalStg}
                                </CCol>
                              </CRow>
                            </CCol>
                            <CCol md='3'>
                              <Tooltip title="Télécharger Journal de Bord" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(filePath);
                                }}/>
                              </Tooltip>
                            </CCol>
                          </CRow>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
                {
                  filePath === null &&
                  <center>
                    <CCard>
                      <CCardBody>
                        <center>
                          <span className="warningGreyBigIcon"/>
                          <br/>
                          <span className="greyMarkCourrier">Désolé, il n'y a pas Dépôt <ins>Journal de Bord</ins> pour ce Plan de Travail.</span>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </>
    )
  }

  const journalHistoric = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {fichePFEsHistoric.map((fPFE) => (
                <>
                  {journalUnitHistoric(fPFE)}
                </>
              ))}
            </>
        }
      </>
    );
  };

  const bilan1UnitHistoric = (fPFE) => {

    let filePath = fPFE.pathBilan1;
    let fileName = "--";
    if (filePath !== null) {
      fileName = filePath.substring(filePath.indexOf("uploads") + 8, filePath.indexOf("espdsi2020"));
    }


    let e = fPFE.etat;
    let color = "--";
    if (e === "01") {
      color = "secondary";
    }
    if (e === "02") {
      color = "info";
    }
    if (e === "03") {
      color = "success";
    }
    if (e === "04") {
      color = "danger";
    }
    if (e === "05") {
      color = "warning";
    }
    if (e === "06") {
      color = "dark";
    }
    if (e === "07" || e === "08") {
      color = "primary";
    }

    return (
      <>

        <CCard color="light">
          <CCardBody>
            <CRow>
              <CCol md="4">
                {fichePFEsHistoricBadge(fPFE.etat)}
                <br/>
                <CWidgetIcon text={fPFE.titreProjet} header='Titre Projet' color={color}>
                  <MenuBookIcon/>
                </CWidgetIcon>
              </CCol>
              <CCol md="8">
                {
                  filePath !== null &&
                  <center>
                    <CCard>
                      <CCardImg variant="top" src={emptyBack} height="80px"/>
                      <CCardBody>
                        <center>
                          <span className="clignoteBoldGrey">Bilan Version 1</span>
                          <br/><br/>

                          <CRow>
                            <CCol md='9'>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Label:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fileName + '.pdf'}
                                </CCol>
                              </CRow>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Date Dépôt:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fPFE.dateDepotBilan1}
                                </CCol>
                              </CRow>
                            </CCol>
                            <CCol md='3'>
                              <Tooltip title="Télécharger Bilan Version 1" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(filePath);
                                }}/>
                              </Tooltip>
                            </CCol>
                          </CRow>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
                {
                  filePath === null &&
                  <center>
                    <CCard>
                      <CCardBody>
                        <center>
                          <span className="warningGreyBigIcon"/>
                          <br/>
                          <span className="greyMarkCourrier">Désolé, il n'y a pas Dépôt <ins>Bilan version 1</ins> pour ce Plan de Travail.</span>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </>
    )
  }

  const bilan1Historic = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {fichePFEsHistoric.map((fPFE) => (
                <>
                  {bilan1UnitHistoric(fPFE)}
                </>
              ))}
            </>
        }
      </>
    );
  };

  const bilan2UnitHistoric = (fPFE) => {

    let filePath = fPFE.pathBilan2;
    let fileName = "--";
    if (filePath !== null) {
      fileName = filePath.substring(filePath.indexOf("uploads") + 8, filePath.indexOf("espdsi2020"));
    }


    let e = fPFE.etat;
    let color = "--";
    if (e === "01") {
      color = "secondary";
    }
    if (e === "02") {
      color = "info";
    }
    if (e === "03") {
      color = "success";
    }
    if (e === "04") {
      color = "danger";
    }
    if (e === "05") {
      color = "warning";
    }
    if (e === "06") {
      color = "dark";
    }
    if (e === "07" || e === "08") {
      color = "primary";
    }

    return (
      <>

        <CCard color="light">
          <CCardBody>
            <CRow>
              <CCol md="4">
                {fichePFEsHistoricBadge(fPFE.etat)}
                <br/>
                <CWidgetIcon text={fPFE.titreProjet} header='Titre Projet' color={color}>
                  <MenuBookIcon/>
                </CWidgetIcon>
              </CCol>
              <CCol md="8">
                {
                  filePath !== null &&
                  <center>
                    <CCard>
                      <CCardImg variant="top" src={emptyBack} height="80px"/>
                      <CCardBody>
                        <center>
                          <span className="clignoteBoldGrey">Bilan Version 2</span>
                          <br/><br/>

                          <CRow>
                            <CCol md='9'>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Label:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fileName + '.pdf'}
                                </CCol>
                              </CRow>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Date Dépôt:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fPFE.dateDepotBilan2}
                                </CCol>
                              </CRow>
                            </CCol>
                            <CCol md='3'>
                              <Tooltip title="Télécharger Bilan Version 2" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(filePath);
                                }}/>
                              </Tooltip>
                            </CCol>
                          </CRow>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
                {
                  filePath === null &&
                  <center>
                    <CCard>
                      <CCardBody>
                        <center>
                          <span className="warningGreyBigIcon"/>
                          <br/>
                          <span className="greyMarkCourrier">Désolé, il n'y a pas Dépôt <ins>Bilan version 2</ins> pour ce Plan de Travail.</span>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </>
    )
  }

  const bilan2Historic = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {fichePFEsHistoric.map((fPFE) => (
                <>
                  {bilan2UnitHistoric(fPFE)}
                </>
              ))}
            </>
        }
      </>
    );
  };

  const presentation1Details = () => {

    function handleConfirmAffectExpertRequestNO() {
      setShowModalSpecifyKeyWords(false);
      // console.log('------- 3105 ----c---s--**-------> 1: ' + formikSARS.values.thematicsPIK.length)

      setSelectedThem([]);
      setSelectedTech([]);
      setSelectedDesc([]);

      // console.log('------- 3105 ----c--s---**-------> 2: ' + formikSARS.values.thematicsPIK.length)
    }

    function handleSpecifyKeyWords() {
      // console.log('------- 23.05 ----------------> studentId: ' + studentId)
      setShowModalRequestConfirm(true);
      setShowModalSpecifyKeyWords(false);
    }

    function handleConfirmSpecifyKeyWordsRequestNO() {
      setShowModalRequestConfirm(false);
      setShowModalSpecifyKeyWords(true);
    }

    async function handleConfirmAffectExpertRequestYES() // lol2022
    {
      setShowLoadSpinnerSendMail(true);

      // console.log('2 -----3105---**aze**--->', selectedThem);
      // console.log('2 -----3105---**aze**--->', selectedTech);
      // console.log('2 -----3105---**aze**--->', selectedDesc);

      // values.thematicsPIK.length === 0 && values.technologiesPIK.length === 0 &&
      const [res, err] = await queryApi(
        "academicEncadrant/requestForExpertFormik?idEt=" + studentId + "&themTags=" + encodeURIComponent(encodeURIComponent(selectedThem)) + "&techTags=" + encodeURIComponent(encodeURIComponent(selectedTech)) + "&descTags=" + encodeURIComponent(encodeURIComponent(selectedDesc)),
        {},
        "PUT",
        false
      );
      if (err) {
        // console.log('lol2022********* ERROR');
      } else {
        // console.log('3105******** OK');
        setShowLoadSpinnerSendMail(false);
        setShowModalSpecifyKeyWords(false);
        setShowModalSuccessSendRequest(true);
        setShowModalRequestConfirm(false);

        // console.log('------- 3105 ----SUBMIT-------> 1: ' + formikSARS.values.thematicsPIK.length)

        setSelectedThem([]);
        setSelectedTech([]);
        setSelectedDesc([]);

        // console.log('------- 3105 ----SUBMIT-------> 2: ' + formikSARS.values.thematicsPIK.length)

      }

    }

    function handleSuccessSendRequestExit() {
      setShowModalSuccessSendRequest(false);
    }

    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {
                (Object.keys(studentExpertObj).length === 0) ?
                  <>
                    <span className="greyMarkCourrierSmalLabel">Votre Étudiant </span>
                    &nbsp;
                    <span className="blueMarkCourrierSmalLabel">
                                           {studentFullName.substring(32)}
                                        </span>
                    &nbsp;
                    <span className="errorMarkCourrierSmalLabel"> n'est pas encore affecté </span>
                    <span className="greyMarkCourrierSmalLabel"> à aucun Expert.</span>
                    <br/>
                    <span className="greyMarkCourrierSmalLabel">Si vous voulez demander un Expert, merci de cliquer sur ce bouton</span>
                    <br/><br/>
                    <span className="expertRequestIcon" onClick={() => {
                      confirmAffectExpertRequestUP();
                    }}/>
                    <br/>

                    <Dialog fullHight fullWidth
                            maxWidth="sm"
                            keepMounted
                            open={showModalRequestConfirm}
                            aria-labelledby="form-dialog-title">
                      <DialogTitle id="form-dialog-title">
                                                <span className="myModalTitleMarginTop">
                                                Demande d'affectation d'Expert :
                                                </span>
                        <hr/>
                      </DialogTitle>
                      <DialogContent>
                        <center>
                          Êtes-vous sûr de vouloir envoyer une demande d'affectation d'Expert au CEP <br/>
                          pour votre Étudiant(e) &nbsp;
                          <span className="text-primary" style={{fontSize: "12px"}}>
                                                        {studentFullName.substring(32)}
                                                    </span>
                          &nbsp;?.
                          <br/><br/>
                        </center>
                      </DialogContent>
                      <DialogActions>

                        {
                          !showLoadSpinnerSendMail &&
                          <Button onClick={() => handleConfirmAffectExpertRequestYES()} color="primary">
                            Oui
                          </Button>
                        }

                        {
                          showLoadSpinnerSendMail &&
                          <Spinner animation="grow" variant="primary"/>
                        }

                        <Button onClick={() => handleConfirmSpecifyKeyWordsRequestNO()}
                                color="primary">
                          Non
                        </Button>
                      </DialogActions>
                    </Dialog>

                    <Dialog fullHight fullWidth
                            maxWidth="sm"
                            keepMounted
                            open={showModalSpecifyKeyWords}
                            aria-labelledby="form-dialog-title">
                      <DialogTitle id="form-dialog-title">
                                                <span className="myModalTitleMarginTop">
                                                Spécification Mots Clefs :
                                                </span>
                        <hr/>
                      </DialogTitle>
                      <DialogContent>
                        <CForm onSubmit={formikSARS.handleSubmit}>
                          <center>
                            Merci d'insérer quelques &nbsp;
                            <span className="text-primary" style={{fontSize: "12px"}}>
                                                            Tags
                                                        </span>
                            &nbsp;
                            pour aider le CEP à mieux spécifier l'Expert adéquat :
                          </center>

                          <br/><br/>

                          <CFormGroup row>
                            <CCol xs="12" md="9">
                              <Stack spacing={3} sx={{width: 500}}>
                                <Autocomplete
                                  id="nba thems"
                                  freeSolo
                                  multiple
                                  options={nbaThems}
                                  renderInput={params => (
                                    <TextField {...params} label="Thématiques" variant="outlined"/>
                                  )}
                                  getOptionLabel={option => option}
                                  style={{width: 550}}
                                  value={selectedThem}
                                  onChange={(_event, newThem) => {

                                    // console.log('------------> poi: ' , newThem)
                                    setSelectedThem(newThem);
                                  }}
                                />
                                {
                                  selectedThem.length === 0 &&
                                  <p className="errorMarkCourrierSmalLabel">
                                    (*) Thématique est un champs obligatoire.
                                  </p>
                                }
                              </Stack>
                            </CCol>
                          </CFormGroup>
                          <br/>
                          <CFormGroup row>
                            <CCol xs="12" md="9">
                              <Stack spacing={3} sx={{width: 500}}>
                                <Autocomplete
                                  id="nba techs"
                                  freeSolo
                                  multiple
                                  options={nbaTechs}
                                  renderInput={params => (
                                    <TextField {...params} label="Saisir des Tags Technologies" variant="outlined"/>
                                  )}
                                  getOptionLabel={option => option}
                                  style={{width: 550}}
                                  value={selectedTech}
                                  onChange={(_event, newTech) => {

                                    // console.log('------------> poi: ' , newTech)
                                    setSelectedTech(newTech);
                                  }}
                                />

                                {
                                  selectedTech.length === 0 &&
                                  <p className="errorMarkCourrierSmalLabel">
                                    (*) Thechnologie est un champs obligatoire.
                                  </p>
                                }
                              </Stack>
                            </CCol>
                          </CFormGroup>
                          <br/>
                          <CFormGroup row>
                            <CCol xs="12" md="9">
                              <Stack spacing={3} sx={{width: 500}}>
                                <Autocomplete
                                  id="nba Desc"
                                  freeSolo
                                  multiple
                                  options={nbaDescs}
                                  renderInput={params => (
                                    <TextField {...params} label="Disciplines" variant="outlined"/>
                                  )}
                                  getOptionLabel={option => option}
                                  style={{width: 550}}
                                  value={selectedDesc}
                                  onChange={(_event, newDesc) => {

                                    // console.log('------------> poi: ' , newDesc)
                                    setSelectedDesc(newDesc);
                                  }}
                                />

                                {
                                  selectedDesc.length === 0 &&
                                  <p className="errorMarkCourrierSmalLabel">
                                    (*) Discipline est un champs obligatoire.
                                  </p>
                                }
                              </Stack>
                            </CCol>
                          </CFormGroup>

                          <br/>
                          <div className="float-right">
                            <Button color="primary"
                                    type="submit"
                                    disabled={selectedThem.length !== 0 && selectedTech.length !== 0 && selectedDesc.length !== 0 ? "" : "disabled"}>
                              Confirmer
                            </Button>

                            <Button onClick={() => handleConfirmAffectExpertRequestNO()}
                                    disabled={showLoadSpinnerSendMail}
                                    color="primary">
                              Exit
                            </Button>
                          </div>
                        </CForm>
                        <br/><br/>
                      </DialogContent>
                    </Dialog>

                    <Dialog fullHight fullWidth
                            maxWidth="sm"
                            keepMounted
                            open={showModalSuccessSendRequest}
                            aria-labelledby="form-dialog-title">
                      <DialogTitle id="form-dialog-title">
                                                <span className="myModalTitleMarginTop">
                                                    Information
                                                </span>
                        <hr/>
                      </DialogTitle>
                      <DialogContent>
                        <center>
                          La demande d'affectation d'Expert <br/>
                          pour votre Étudiant(e) &nbsp;
                          <span className="text-primary" style={{fontSize: "12px"}}>
                                                        {studentFullName.substring(32)}
                                                    </span>
                          &nbsp; a été envoyé avec succes au CEP .
                          <br/><br/>
                        </center>
                      </DialogContent>
                      <DialogActions>
                        <Button onClick={() => handleSuccessSendRequestExit()}
                                color="primary">
                          Ok
                        </Button>
                      </DialogActions>
                    </Dialog>
                  </>
                  :
                  <>
                    <span className="myModalDetailCr">L'expert affecté à votre Étudiant est : </span>
                    <br/><br/>
                    <span className="greyMarkCourrierSmalLabel"></span>

                    <CRow>
                      <CCol md="10">
                        <CRow>
                          <CCol xs="3">
                                                      <span className="myModalFieldCr">
                                                        Nom & Prénom:
                                                      </span>
                          </CCol>
                          <CCol xs="9" className="greyMarkCourrierPlusSmalLab">
                            {studentExpertObj.nom}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol xs="3">
                                                      <span className="myModalFieldCr">
                                                        Identifiant:
                                                      </span>
                          </CCol>
                          <CCol xs="9" className="greyMarkCourrierPlusSmalLab">
                            {studentExpertObj.identifiant}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol xs="3">
                                                      <span className="myModalFieldCr">
                                                        E-Mail:
                                                      </span>
                          </CCol>
                          <CCol xs="9" className="greyMarkCourrierPlusSmalLab">
                            {
                              studentExpertObj.mail ?
                                <>{studentExpertObj.mail}</>
                                :
                                <>--</>
                            }
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol xs="3">
                                                      <span className="myModalFieldCr">
                                                        Numéro Téléphone:
                                                      </span>
                          </CCol>
                          <CCol xs="9" className="greyMarkCourrierPlusSmalLab">
                            {
                              studentExpertObj.téléphone ?
                                <>{studentExpertObj.téléphone}</>
                                :
                                <>--</>
                            }
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol xs="3">
                                                      <span className="myModalFieldCr">
                                                        Type:
                                                      </span>
                          </CCol>
                          <CCol xs="9" className="greyMarkCourrierPlusSmalLab">
                            {studentExpertObj.type === "P" && <>Permanant</>}
                            {studentExpertObj.type === "V" && <>Vacataire</>}
                            {studentExpertObj.type === "S" && <>Stagiaire</>}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol xs="3">
                                                      <span className="myModalFieldCr">
                                                        Unité Pédagogique:
                                                      </span>
                          </CCol>
                          <CCol xs="9" className="greyMarkCourrierPlusSmalLab">
                            {
                              studentExpertObj.up ?
                                <>{studentExpertObj.up}</>
                                :
                                <>--</>
                            }
                          </CCol>
                        </CRow>
                      </CCol>
                    </CRow>
                  </>

              }
            </>
        }
      </>
    );
  };

  const presentation2Details = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              <span className="greyMarkCourrierSmalLabel">Not Yet Deployed 2 !.</span>
              <br/>
              <Wave text=". . ." effect="stretch" effectChange={2.8}/>
              {fichePFEsHistoric.map((fPFE) => (
                <></>
              ))}
            </>
        }
      </>
    );
  };

  const visiteMiParcoursDetails = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              <span className="greyMarkCourrierSmalLabel">Not Yet Deployed 3 !.</span>
              <br/>
              <Wave text=". . ." effect="stretch" effectChange={2.8}/>
              {fichePFEsHistoric.map((fPFE) => (
                <></>
              ))}
            </>
        }
      </>
    );
  };

  const bilan3UnitHistoric = (fPFE) => {

    let filePath = fPFE.pathBilan3;
    let fileName = "--";
    if (filePath !== null) {
      fileName = filePath.substring(filePath.indexOf("uploads") + 8, filePath.indexOf("espdsi2020"));
    }

    let e = fPFE.etat;
    let color = "--";
    if (e === "01") {
      color = "secondary";
    }
    if (e === "02") {
      color = "info";
    }
    if (e === "03") {
      color = "success";
    }
    if (e === "04") {
      color = "danger";
    }
    if (e === "05") {
      color = "warning";
    }
    if (e === "06") {
      color = "dark";
    }
    if (e === "07" || e === "08") {
      color = "primary";
    }

    return (
      <>

        <CCard color="light">
          <CCardBody>
            <CRow>
              <CCol md="4">
                {fichePFEsHistoricBadge(fPFE.etat)}
                <br/>
                <CWidgetIcon text={fPFE.titreProjet} header='Titre Projet' color={color}>
                  <MenuBookIcon/>
                </CWidgetIcon>
              </CCol>
              <CCol md="8">
                {
                  filePath !== null &&
                  <center>
                    <CCard>
                      <CCardImg variant="top" src={emptyBack} height="80px"/>
                      <CCardBody>
                        <center>
                          <span className="clignoteBoldGrey">Bilan Version 3</span>
                          <br/><br/>

                          <CRow>
                            <CCol md='9'>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Label:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fileName + '.pdf'}
                                </CCol>
                              </CRow>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Date Dépôt:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fPFE.dateDepotBilan3}
                                </CCol>
                              </CRow>
                            </CCol>
                            <CCol md='3'>
                              <Tooltip title="Télécharger Bilan Version 3" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(filePath);
                                }}/>
                              </Tooltip>
                            </CCol>
                          </CRow>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
                {
                  filePath === null &&
                  <center>
                    <CCard>
                      <CCardBody>
                        <center>
                          <span className="warningGreyBigIcon"/>
                          <br/>
                          <span className="greyMarkCourrier">Désolé, il n'y a pas Dépôt <ins>Bilan version 3</ins> pour ce Plan de Travail.</span>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </>
    )
  }

  const bilan3Historic = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {fichePFEsHistoric.map((fPFE) => (
                <>
                  {bilan3UnitHistoric(fPFE)}
                </>
              ))}
            </>
        }
      </>
    );
  };

  const rapport1UnitHistoric = (fPFE) => {

    let filePath = fPFE.pathRapportVersion1;
    let fileName = "--";
    if (filePath !== null) {
      fileName = filePath.substring(filePath.indexOf("uploads") + 8, filePath.indexOf("espdsi2020"));
    }


    let e = fPFE.etat;
    let color = "--";
    if (e === "01") {
      color = "secondary";
    }
    if (e === "02") {
      color = "info";
    }
    if (e === "03") {
      color = "success";
    }
    if (e === "04") {
      color = "danger";
    }
    if (e === "05") {
      color = "warning";
    }
    if (e === "06") {
      color = "dark";
    }
    if (e === "07" || e === "08") {
      color = "primary";
    }

    return (
      <>

        <CCard color="light">
          <CCardBody>
            <CRow>
              <CCol md="4">
                {fichePFEsHistoricBadge(fPFE.etat)}
                <br/>
                <CWidgetIcon text={fPFE.titreProjet} header='Titre Projet' color={color}>
                  <MenuBookIcon/>
                </CWidgetIcon>
              </CCol>
              <CCol md="8">
                {
                  filePath !== null &&
                  <center>
                    <CCard>
                      <CCardImg variant="top" src={emptyBack} height="80px"/>
                      <CCardBody>
                        <center>
                          <span className="clignoteBoldGrey">Rapport Version 1</span>
                          <br/><br/>

                          <CRow>
                            <CCol md='9'>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Label:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fileName + '.pdf'}
                                </CCol>
                              </CRow>
                              <CRow>
                                <CCol>
                                  <strong className="text-muted">
                                    Date Dépôt:
                                  </strong>
                                </CCol>
                                <CCol>
                                  {fPFE.dateDepotRapportVersion1}
                                </CCol>
                              </CRow>
                            </CCol>
                            <CCol md='3'>
                              <Tooltip title="Télécharger Rapport Version 1" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(filePath);
                                }}/>
                              </Tooltip>
                            </CCol>
                          </CRow>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
                {
                  filePath === null &&
                  <center>
                    <CCard>
                      <CCardBody>
                        <center>
                          <span className="warningGreyBigIcon"/>
                          <br/>
                          <span className="greyMarkCourrier">Désolé, il n'y a pas Dépôt <ins>Rapport version 1</ins> pour ce Plan de Travail.</span>
                        </center>
                      </CCardBody>
                    </CCard>
                  </center>
                }
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </>
    )
  }

  const rapport1Historic = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {fichePFEsHistoric.map((fPFE) => (
                <>
                  {rapport1UnitHistoric(fPFE)}
                </>
              ))}
            </>
        }
      </>
    );
  };

  const rapport2UnitHistoric = (fPFE) => {

    let attestPath = fPFE.pathAttestationStage;
    let attestName = "--";
    if (attestPath !== null) {
      attestName = attestPath.substring(attestPath.indexOf("uploads") + 8, attestPath.indexOf("espdsi2020"));
    }

    let urkundPath = fPFE.pathPlagiat;
    let urkundName = "--";
    if (urkundPath !== null) {
      urkundName = urkundPath.substring(urkundPath.indexOf("uploads") + 8, urkundPath.indexOf("espdsi2020"));
    }

    let rapportPath = fPFE.pathRapportVersion2;
    let rapportName = "--";
    if (rapportPath !== null) {
      rapportName = rapportPath.substring(rapportPath.indexOf("uploads") + 8, rapportPath.indexOf("espdsi2020"));
    }


    let e = fPFE.etat;
    let color = "--";
    if (e === "01") {
      color = "secondary";
    }
    if (e === "02") {
      color = "info";
    }
    if (e === "03") {
      color = "success";
    }
    if (e === "04") {
      color = "danger";
    }
    if (e === "05") {
      color = "warning";
    }
    if (e === "06") {
      color = "dark";
    }
    if (e === "07" || e === "08") {
      color = "primary";
    }

    return (
      <>

        <CCard color="light">
          <CCardBody>
            <CRow>
              <CCol md="3">
                {fichePFEsHistoricBadge(fPFE.etat)}
                <br/>
                <CWidgetIcon text={fPFE.titreProjet} header='Titre Projet' color={color}>
                  <MenuBookIcon/>
                </CWidgetIcon>
              </CCol>
              <CCol md="9">
                <center>
                  <CRow>
                    {
                      attestPath !== null &&
                      <CCol md="4">
                        <CCard style={{width: '14rem'}}>
                          <CCardImg variant="top" src={myDoc} height="125px"/>
                          <CCardBody>
                            <center>
                              <span className="clignoteBoldGrey">Attestation Stage</span>
                              <br/><br/>
                              {attestName + '.pdf'}
                              <br/>
                              <span className="greyMarkItalic">{fPFE.dateDepotAttestationStage}</span>
                              <br/><br/>
                              <Tooltip title="Télécharger Attestation Stage"
                                       placement="top">
                                                                <span className="downloadGreyIcon" onClick={() => {
                                                                  downloadPDFFile(attestPath);
                                                                }}/>
                              </Tooltip>
                            </center>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    }
                    {
                      attestPath === null &&
                      <CCol md="4">
                        <CCard style={{width: '14rem'}}>
                          <CCardBody>
                            <center>
                              <span className="warningGreyBigIcon"/>
                              <br/>
                              <span className="greyMarkCourrier">
                                                                Désolé, <br/>
                                                                il n'y a pas Dépôt <ins>Attestation Stage</ins> pour ce Plan de Travail.
                                                            </span>
                            </center>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    }

                    {
                      urkundPath !== null &&
                      <CCol md="4">
                        <CCard style={{width: '14rem'}}>
                          <CCardImg variant="top" src={myDoc} height="125px"/>
                          <CCardBody>
                            <center>
                              <span className="clignoteBoldGrey">Rapport URKUND</span>
                              <br/><br/>
                              {urkundName + '.pdf'}
                              <br/>
                              <span className="greyMarkItalic">{fPFE.dateDepotPlagiat}</span>
                              <br/><br/>
                              <Tooltip title="Télécharger Rapport Anti-Plagiat" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(urkundPath);
                                }}/>
                              </Tooltip>
                            </center>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    }
                    {
                      urkundPath === null &&
                      <CCol md="4">
                        <CCard style={{width: '14rem'}}>
                          <CCardBody>
                            <center>
                              <span className="warningGreyBigIcon"/>
                              <br/>
                              <span className="greyMarkCourrier">
                                                                Désolé, <br/>
                                                                il n'y a pas Dépôt <ins>Rapport Anti-Plagiat</ins> pour ce Plan de Travail.
                                                            </span>
                            </center>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    }

                    {
                      rapportPath !== null &&
                      <CCol md="4">
                        <CCard style={{width: '14rem'}}>
                          <CCardImg variant="top" src={myDoc} height="125px"/>
                          <CCardBody>
                            <center>
                              <span className="clignoteBoldGrey">Rapport Version 2</span>
                              <br/><br/>
                              {rapportName + '.pdf'}
                              <br/>
                              <span className="greyMarkItalic">{fPFE.dateDepotRapportVersion2}</span>
                              <br/><br/>
                              <Tooltip title="Télécharger Rapport Version 2" placement="top">
                                <span className="downloadGreyIcon" onClick={() => {
                                  downloadPDFFile(rapportPath);
                                }}/>
                              </Tooltip>
                            </center>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    }
                    {
                      rapportPath === null &&
                      <CCol md="4">
                        <CCard style={{width: '14rem'}}>
                          <CCardBody>
                            <center>
                              <span className="warningGreyBigIcon"/>
                              <br/>
                              <span className="greyMarkCourrier">
                                                                Désolé, <br/>
                                                                il n'y a pas Dépôt <ins>Rapport version 2</ins> pour ce Plan de Travail.
                                                            </span>
                            </center>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    }
                  </CRow>
                </center>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </>
    )
  }

  const rapport2Historic = () => {
    return (
      <>
        {
          fichePFEsHistoric.length === 0 ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore aucun Plan de Travail VALIDÉ !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <>
              {fichePFEsHistoric.map((fPFE) => (
                <>
                  {rapport2UnitHistoric(fPFE)}
                </>
              ))}
            </>
        }
      </>
    );
  };

  const surveyFirstJob = () => {
    return (
      <>
        <span className="greyMarkCourrierSmalLabel">Not Yet Deployed !.</span>
        <br/>
        <Wave text=". . ." effect="stretch" effectChange={2.8}/>
      </>
    );
  };

  const startTrDateNote = () => {
    let studentFN = studentFullName.substring(32);
    return (
      <>
        {
          stepDate === "--" ?
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CWidgetDropdown
                  color="gradient-dark"
                  header={studentFullName.substring(32)}
                  text="n'a encore commencé son Stage PFE !.">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="warningBigIcon"/>
                  <br/><br/><br/><br/>
                </CWidgetDropdown>
              </CCol>
              <CCol md="3"/>
            </CRow>
            :
            <center>
              <CRow>
                <CCol md="3"/>
                <CCol>
                  <CWidgetBrand color="facebook"
                                rightHeader={stepComment.substring(0, stepComment.lastIndexOf('-'))}
                                rightFooter="Nombre Conventions Validées"
                                leftHeader={stepComment.substring(stepComment.lastIndexOf('-') + 1)}
                                leftFooter="Nombre Avenants Validés">
                    <CIcon content={freeSet.cilAvTimer}
                           height="25"
                           className="my-3"/>
                  </CWidgetBrand>
                </CCol>
                <CCol md="3"/>
              </CRow>

              <CRow>
                <CCol md="2"/>
                <CCol>
                  <CJumbotron>
                    <span className="redMark">{studentFN}</span> a commencé son Projet Fin Études à <span
                    className="redMark">{stepDate}</span> .
                  </CJumbotron>
                </CCol>
                <CCol md="2"/>
              </CRow>
            </center>
        }
      </>
    );
  };

  const convHistoricBadge = (e) => {
    if (e === "01") {
      return (
        <CBadge color="info" className="float-right">
          DEPOSÉE
        </CBadge>
      );
    }
    if (e === "02") {
      return (
        <CBadge color="success" className="float-right">
          TRAITÉE
        </CBadge>
      );
    }
    if (e === "03") {
      return (
        <CBadge color="danger" className="float-right">
          ANNULÉE
        </CBadge>
      );
    }
    if (e === "04") {
      return (
        <CBadge color="warning" className="float-right">
          ANNULATION DEMANDEE
        </CBadge>
      );
    }
  };

  const fichePFEsHistoricBadge = (e) => {
    if (e === "01") {
      return (
        <CBadge color="secondary" className="float-right">
          SAUVEGARDÉE
        </CBadge>
      );
    }
    if (e === "02") {
      return (
        <CBadge color="info" className="float-right">
          DÉPOSÉE
        </CBadge>
      );
    }
    if (e === "03") {
      return (
        <CBadge color="success" className="float-right">
          VALIDÉE
        </CBadge>
      );
    }
    if (e === "04") {
      return (
        <CBadge color="danger" className="float-right">
          REFUSÉE
        </CBadge>
      );
    }
    if (e === "05") {
      return (
        <CBadge color="warning" className="float-right">
          ANNULÉE
        </CBadge>
      );
    }
    if (e === "06") {
      return (
        <CBadge color="dark" className="float-right">
          À SOUTENIR
        </CBadge>
      );
    }
    if (e === "07") {
      return (
        <CBadge color="primary" className="float-right">
          SOUTENUE
        </CBadge>
      );
    }
    if (e === "08") {
      return (
        <CBadge color="primary" className="float-right">
          PLANIFIÉE
        </CBadge>
      );
    }
  };

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const treatmentsForESPPFileHist = (tf) => {

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
              <CCol md="3" className="colPaddingRight">
                                    <span className="trtHistoricDeepGreyField">
                                        Date {tf.typeTrtFiche} Fiche:
                                    </span>
              </CCol>
              <CCol md="9" className="colPaddingLeft">
                {tf.dateSaisieDemande}
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
                  <div className="float-right">
                    <CButton shape="pill" size="sm" color="success"
                             onClick={() => verifyValidateDemandeModification(tf.dateDepotFiche, tf.dateSaisieDemande)}>
                      Valider DM
                    </CButton>
                    &nbsp;&nbsp;&nbsp;
                    <CButton shape="pill" size="sm" color="danger"
                             onClick={() => verifyRefuseDemandeModification(tf.dateDepotFiche, tf.dateSaisieDemande)}>
                      Refuser DM
                    </CButton>
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
              <CCol md="3" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Saisie Demande:
                                </span>
              </CCol>
              <CCol md="9" className="colPaddingLeft">
                {tf.dateSaisieDemande}
              </CCol>
            </CRow>
            <CRow>
              <CCol md="3" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Traitement Demande:
                                </span>
              </CCol>
              <CCol md="9" className="colPaddingLeft">
                {tf.dateTrtFiche}
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
                  tf.etatTrt === "01" && tf.typeTrtConv !== "AUT" &&
                  <div className="float-right">
                    <CButton shape="pill" size="sm" color="success"
                             onClick={() => verifyValidateDemandeAnnulation(tf.dateDepotFiche, tf.dateSaisieDemande, tf.typeTrtConv)}>
                      Valider DA
                    </CButton>
                    &nbsp;&nbsp;&nbsp;
                    <CButton shape="pill" size="sm" color="danger"
                             onClick={() => verifyRefuseDemandeAnnulation(tf.dateDepotFiche, tf.dateSaisieDemande, tf.typeTrtConv)}>
                      Refuser DA
                    </CButton>
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
              <CCol md="3">
                                <span className="trtHistoricDeepGreyField">
                                    Date Saisie Demande:
                                </span>
              </CCol>
              <CCol md="9">
                {tf.dateSaisieDemande}
              </CCol>
            </CRow>
            <CRow>
              <CCol md="3">
                                <span className="trtHistoricDeepGreyField">
                                    Date Traitement Demande:
                                </span>
              </CCol>
              <CCol md="9">
                {tf.dateTrtFiche}
              </CCol>
            </CRow>
            <CRow>
              <CCol md="3">
                                <span className="trtHistoricDeepGreyField">
                                    Type Traitement Convention:
                                </span>
              </CCol>
              <CCol md="9">
                {
                  tf.typeTrtConv === "AUT" ?
                    <span className="noteCancelConvToo">
                                            Demande Annulation Plan de Travail AUTOMATIQUE <br/>
                                            suite à une Demande Annulation Convention <br/>
                                            - demandée par l'Étudiant <br/>
                                            - accéptée par le Responsable Service Stage.
                                        </span>
                    :
                    <>{tf.typeTrtConv}</>
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
                    <span className="cancelAgreeIcon" onClick={() => {
                      downloadGanttDiagramOFF(tf.cancellingAgreementPath);
                    }}/>
                  </LightTooltip>
                }
                <br/>
              </CCol>
            </CRow>

            {
              tf.etatTrt === "03" &&
              <>
                <br/>
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
              <CCol md="3" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Saisie Demande:
                                </span>
              </CCol>
              <CCol md="9" className="colPaddingLeft">
                {tf.dateSaisieDemande}
              </CCol>
            </CRow>
            <CRow>
              <CCol md="3" className="colPaddingRight">
                                <span className="trtHistoricDeepGreyField">
                                    Date Traitement Fiche:
                                </span>
              </CCol>
              <CCol md="9" className="colPaddingLeft">
                {tf.dateTrtFiche}
              </CCol>
            </CRow>
          </>
        }
      </>
    );
  };


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

  const fichePFEsHistoricTreatment = (e) => {
    if (e === "02") {
      return (
        <div className="float-right">
          <CButton shape="pill" size="sm" color="success" onClick={() => verifyValidateESPFile()}>
            Valider F
          </CButton>
          &nbsp;&nbsp;&nbsp;
          <CButton shape="pill" size="sm" color="danger" onClick={() => verifyRefuseESPFile()}>
            Refuser F
          </CButton>
        </div>
      );
    }
  };

  const formikRefuseESPFile = useFormik({

    initialValues: {
      observation: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setShowLoadSpinnerForRefuseFichePFE(true);
      // console.log('------------motifRefusESPFile---------1-------> 0412-refuseESPFile: ', values.observation);

      const [res, err] = await queryApi(
        "academicEncadrant/refuseESPFile?idEt=" + studentId + "&motifRefusESPFile=" + values.observation + "&mailAE=" + currentTeacher.id,
        {},
        "PUT",
        false
      );
      if (err) {
        /*setShowLoader(false);
                setError({
                    visible: true,
                    message: JSON.stringify(err.errors, null, 2),
                });*/
        // console.log('NOOOOOOOOOOOOOOOOOOOOOOO');
      } else {
        dispatch(fetchFichePFEsHistoric(studentId));
        dispatch(fetchActiveStudentTimelineStep(studentId));
        dispatch(fetchFichePFEDetails(studentId));

        setShowLoadSpinnerForRefuseFichePFE(false);
        setShowVerifRefuseFichePFE(false);
        setShowSuccessYESRefuseFichePFE(true);
        setShowPresentMotifForRefuseFichePFE(false);
      }
    },
  });

  const formikRefuseDemandeModif = useFormik({

    initialValues: {
      observation: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setShowLoadSpinnerForRefuseDemandeModif(true);
      // console.log('------------refuseDemandeModifESPFile------0712---1-------> 0412-refuseDemandeModifESPFile: ', values.observation);

      const [res, err] = await queryApi(
        "academicEncadrant/refuseDemandeModifESPFile?idEt=" + studentId + "&motifRefusDemandeModif=" + values.observation + "&mailAE=" + currentTeacher.id + "&dateDepotFiche=" + dateDepotFiche + "&dateSaisieDemande=" + dateSaisieDemande,
        {},
        "PUT",
        false
      );
      if (err) {
        /*setShowLoader(false);
                setError({
                    visible: true,
                    message: JSON.stringify(err.errors, null, 2),
                });*/
        // console.log('NOOOOOOOOOOOOOOOOOOOOOOO*********0712');
      } else {
        dispatch(fetchFichePFEsHistoric(studentId)); // Update Fields of All Existing Fiches PFE History
        dispatch(fetchActiveStudentTimelineStep(studentId)); // Active Step
        dispatch(fetchFichePFEDetails(studentId)); // Badge
        dispatch(fetchTimelineSteps(studentId));  // Date Step

        setShowLoadSpinnerForRefuseDemandeModif(false);
        setShowVerifRefuseDemandeModif(false);
        setShowSuccessYESRefuseDemandeModif(true);
        setShowPresentMotifForRefuseDemandeModif(false);

        // console.log('DONNNNNNNNNNNNNNNNNNNNNNE********0712');
      }
    },
  });

  const formikRefuseDemandeAnnul = useFormik({

    initialValues: {
      observation: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setShowLoadSpinnerForRefuseDemandeAnnul(true);
      // console.log('------------refuseDemandeAnnulESPFile------0712---1-------> 0412-refuseDemandeAnnulESPFile: ', values.observation);

      const [res, err] = await queryApi(
        "academicEncadrant/refuseDemandeAnnulESPFile?idEt=" + studentId + "&motifRefusDemandeAnnul=" + values.observation + "&mailAE=" + currentTeacher.id + "&dateDepotFiche=" + dateDepotFiche + "&dateSaisieDemande=" + dateSaisieDemande,
        {},
        "PUT",
        false
      );
      if (err) {
        /*setShowLoader(false);
                setError({
                    visible: true,
                    message: JSON.stringify(err.errors, null, 2),
                });*/
        // console.log('NOOOOOOOOOOOOOOOOOOOOOOO*********0712');
      } else {
        dispatch(fetchFichePFEsHistoric(studentId));

        setShowLoadSpinnerForRefuseDemandeAnnul(false);
        setShowVerifRefuseDemandeAnnul(false);
        setShowSuccessYESRefuseDemandeAnnul(true);
        setShowPresentMotifForRefuseDemandeAnnul(false);

        // console.log('DONNNNNNNNNNNNNNNNNNNNNNE********0712');
      }
    },
  });

  const validationSchemaSARSS = Yup.object().shape({
    thematicsPIK: Yup.array().required("required"),
    technologiesPIK: Yup.array().required("required"),
    desciplinesPIK: Yup.array().required("required"),  // desciplinesPIK: Yup.array().min(1, "at least 1 desc ").required("required"),
  });

  const formikSARS = useFormik({

    initialValues: {
      thematicsPIK: ["poiuytre", "plo"], //[],
      technologiesPIK: ["poiugf"],
      desciplinesPIK: [],
    },
    validationSchema: validationSchemaSARSS,
    onSubmit: async (values) => {// sars
      setShowModalRequestConfirm(true);
      setShowModalSpecifyKeyWords(false);
    },
  });

  const companySupervisorsForESPPFileHist = (cs) => {

    let companySupervisorFN = cs.lastName + ' ' + cs.firstName;

    let splitStr = companySupervisorFN.toLowerCase().split(' ');
    for (let i = 0; i < splitStr.length; i++) {
      splitStr[i] = splitStr[i].charAt(0).toUpperCase();
    }
    let csAbbv = splitStr.join('');


    return (
      <>
        <ListItem alignItems="flex-start">
          <ListItemAvatar>
            <Avatar sx={{bgcolor: deepPurple[500]}}>{csAbbv}</Avatar>
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

  function downloadPlanTravail(dateDepotPT) {
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

    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadPlanTravail?studentId=" + studentId + "&&dateDepotPT=" + dateDepotPT, {responseType: "blob"})
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
      <CRow>
        <CCol md="12">
                    <span style={{fontWeight: "bold", fontSize: "14px", color: "#cc0000"}}>
                        <TextScrollerStuTL text={studentFullName}/>
                    </span>
          <br/>
          <CCard accentColor="danger">
            <CCardBody>
              <CRow>
                <CCol md="3">
                  {(
                    conventionDetails[0] === undefined &&
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
                    conventionDetails[0] !== undefined && conventionDetails[0].traiter === "01" &&
                    <CAlert color="info">
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
                                                    <span className="clignotePrimary">
                                                        DEPOSEE
                                                    </span>
                        </CCol>
                      </CRow>
                    </CAlert>
                  )}

                  {
                    conventionDetails[0] !== undefined && conventionDetails[0].traiter === "02" &&
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

                  {
                    conventionDetails[0] !== undefined && conventionDetails[0].traiter === "03" &&
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

                  {(
                    conventionDetails[0] !== undefined && conventionDetails[0].traiter === "04" &&
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
                  )}
                </CCol>

                <CCol md="6">
                  {
                    fichePFEDetails.etat === null &&
                    <CAlert color="secondary">
                      <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        Date Dépôt:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="synopsisField">
                                                        --
                                                    </span>
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="synopsisField">
                                                    --
                                                    </span>
                        </CCol>
                      </CRow>
                    </CAlert>
                  }

                  {
                    fichePFEDetails.etat === "SAUVEGARDEE" &&
                    <CAlert color="info">
                      <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        Date Dépôt:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateDepot}
                                                    </span>
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                        </CCol>
                        <CCol md="9">
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
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        Date Remise:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateRemise}
                                                    </span>
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="clignotePrimary">
                                                        {fichePFEDetails.etat}
                                                    </span>
                        </CCol>
                      </CRow>
                    </CAlert>
                  }

                  {
                    (
                      fichePFEDetails.etat === "VALIDEE" ||
                      fichePFEDetails.etat === "A_SOUTENIR" ||
                      fichePFEDetails.etat === "SOUTENUE" ||
                      fichePFEDetails.etat === "PLANIFIED"
                    ) &&
                    <CAlert color="success">
                      <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        Date Remise:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateRemise}
                                                    </span>
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="clignoteGreen">
                                                        {fichePFEDetails.etat}
                                                    </span>
                        </CCol>
                      </CRow>
                    </CAlert>
                  }

                  {
                    (fichePFEDetails.etat === "ANNULEE" || fichePFEDetails.etat === "REFUSEE") &&
                    <CAlert color="danger">
                      <h6 className="alert-heading"><strong>Traitement Plan Travail</strong></h6>
                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        Date Remise:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="synopsisField">
                                                        {fichePFEDetails.dateDepot}
                                                    </span>
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                                                    <span className="synopsisField">
                                                        État Fiche:
                                                    </span>
                        </CCol>
                        <CCol md="9">
                                                    <span className="clignoteRed">
                                                        {fichePFEDetails.etat}
                                                    </span>
                        </CCol>
                      </CRow>
                    </CAlert>
                  }
                </CCol>

                <CCol md="3">
                  {
                    depotReportsDetails.etatDepotReports === null &&
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
                    depotReportsDetails.etatDepotReports !== null &&
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
              </CRow>
            </CCardBody>
          </CCard>

          <CCard accentColor="danger">
            <CCardBody>
              <div style={{overflowX: "auto"}}>
                <Stepper alternativeLabel
                         activeStep={activeStudentTimelineStep}
                         connector={<ColorlibConnector/>}>
                  {steps.map((step) => (
                    <Step key={step}>
                      <CRow>
                        <CCol md="12">
                          <StepLabel StepIconComponent={ColorlibStepIcon}>
                            <span className="redMark">{step.stepDate}</span>
                            <br/><br/>
                            <CButton shape="pill"
                                     color="light"
                                     size="sm"
                                     className="float-center"
                                     aria-expanded="true"
                                     onClick={() => toggleItem(step)}>
                              <p style={{whiteSpace: 'pre', textAlign: 'center'}}> {step.stepLabel} </p>
                            </CButton>
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

          <Dialog fullHight fullWidth
                  maxWidth="md"
                  keepMounted
                  open={visible}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                {stepId}. {stepTitle}
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              {
                stepId === 1 &&
                convHistoric()
              }
              {
                stepId === 2 &&
                startTrDateNote()
              }
              {
                stepId === 3 &&
                fichePFEHistoric()
              }
              {
                stepId === 4 &&
                journalHistoric()
              }
              {
                stepId === 5 &&
                bilan1Historic()
              }
              {
                stepId === 6 &&
                presentation1Details()
              }
              {
                stepId === 7 &&
                visiteMiParcoursDetails()
              }
              {
                stepId === 8 &&
                bilan2Historic()
              }
              {
                stepId === 9 &&
                rapport1Historic()
              }
              {
                stepId === 10 &&
                presentation2Details()
              }
              {
                stepId === 11 &&
                bilan3Historic()
              }
              {
                stepId === 12 &&
                rapport2Historic()
              }
              {
                stepId === 13 &&
                surveyFirstJob()
              }
            </DialogContent>
            <DialogActions>
              <Button onClick={() => closeModal()} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showVerifValidateFichePFE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Validation Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-vous sûr de vouloir valider <br/>
                le Plan de Travail de votre Étudiant(e) &nbsp;
                <span className="text-info" style={{fontSize: "12px"}}>
                                    {studentFullName.substring(32)}
                                </span>
                &nbsp;?.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              {
                !showLoadSpinnerForValidateFichePFE &&
                <Button onClick={() => handleValidateESPFileYES()} color="primary">
                  Oui
                </Button>
              }

              {(
                showLoadSpinnerForValidateFichePFE &&
                <Spinner animation="grow" variant="primary"/>
              )}
              <Button onClick={() => handleValidateESPFileNO()}
                      disabled={showLoadSpinnerForValidateFichePFE}
                      color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showSuccessYESValidateFichePFE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                Le Plan de Travail de votre Étudiant a été validé avec succès.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handleExitSuccessYESValidateESPFile()}
                      color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showVerifRefuseFichePFE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Refus Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-vous sûr de vouloir refuser <br/>
                le Plan de Travail de votre Étudiant(e) &nbsp;
                <span className="text-info" style={{fontSize: "12px"}}>
                                            {studentFullName.substring(32)}
                                        </span>
                &nbsp;?.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handlePresentMotifForRefuseESPFileYES()} color="primary">
                Oui
              </Button>
              <Button onClick={() => handleRefuseESPFileNO()}
                      color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showPresentMotifForRefuseFichePFE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Présentation Motif de Refus Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <CForm onSubmit={formikRefuseESPFile.handleSubmit}>
                  <CFormGroup>
                    {error.visible && <p>{error.message}</p>}
                  </CFormGroup>
                  <CFormGroup>
                    <CRow>
                      <CCol md="2"/>
                      <CCol md="8">
                        Merci de présenter le Motif de Refus du Plan de Travail :
                        <br/><br/>
                        <CTextarea value={formikRefuseESPFile.values.observation}
                                   onChange={formikRefuseESPFile.handleChange}
                                   disabled={showLoadSpinnerForRefuseFichePFE}
                                   name="observation"
                                   rows="4"
                                   cols="70"
                                   placeholder="Présenter le Motif de Refus de la Fiche ..."/>

                        {
                          formikRefuseESPFile.errors.observation && formikRefuseESPFile.touched.observation && (
                            <p style={{color: "red"}}>
                              {formikRefuseESPFile.errors.observation}
                            </p>
                          )}
                        <br/>
                      </CCol>
                      <CCol md="2"/>
                    </CRow>
                  </CFormGroup>
                  <div className="float-right">
                    {
                      !showLoadSpinnerForRefuseFichePFE &&
                      <Button color="success" type="submit">
                        Confirmer
                      </Button>
                    }

                    {(
                      showLoadSpinnerForRefuseFichePFE &&
                      <Spinner animation="grow" variant="primary"/>
                    )}
                    <Button onClick={() => handleExitPresentMotifForRefuseFichePFE()}
                            disabled={showLoadSpinnerForRefuseFichePFE}
                            color="dark">
                      Exit
                    </Button>
                  </div>
                </CForm>
              </center>
            </DialogContent>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showSuccessYESRefuseFichePFE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                Le Plan de Travail de votre Étudiant a été refusé avec succès.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handleExitSuccessYESRefuseESPFile()}
                      color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showVerifValidateDemandeModif}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Validation Demande Modification Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-vous sûr de vouloir valider <br/>
                la Demande de Modification du Plan de Travail de votre Étudiant(e) &nbsp;
                <span className="text-info" style={{fontSize: "12px"}}>
                                    {studentFullName.substring(32)}
                                </span>
                &nbsp;?.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              {
                !showLoadSpinnerForValidateDemandeModif &&
                <Button onClick={() => handleValidateDemandeModifYES()} color="primary">
                  Oui
                </Button>
              }

              {(
                showLoadSpinnerForValidateDemandeModif &&
                <Spinner animation="grow" variant="primary"/>
              )}
              <Button onClick={() => handleValidateDemandeModifNO()}
                      disabled={showLoadSpinnerForValidateDemandeModif}
                      color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showSuccessYESValidateDemandeModif}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                La Demande de Modification du Plan de Travail de votre Étudiant a été validé avec succès.
                <br/><br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handleExitSuccessYESValidateDemandeModif()}
                      color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showVerifRefuseDemandeModif}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Refus Demande Modification Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-vous sûr de vouloir refuser <br/>
                la Demande de Modification du Plan de Travail de votre Étudiant(e) &nbsp;
                <span className="text-info" style={{fontSize: "12px"}}>
                                    {studentFullName.substring(32)}
                                </span>
                &nbsp;?.

              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handlePresentMotifForRefuseDemandeModifYES()} color="primary">
                Oui
              </Button>
              <Button onClick={() => handleRefuseDemandeModifNO()}
                      color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showPresentMotifForRefuseDemandeModif}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Présentation Motif de Refus de Demande Modification Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <CForm onSubmit={formikRefuseDemandeModif.handleSubmit}>
                  <CFormGroup>
                    {error.visible && <p>{error.message}</p>}
                  </CFormGroup>
                  <CFormGroup>
                    <CRow>
                      <CCol md="1"/>
                      <CCol md="10">
                        Merci de présenter le Motif de Refus de la Demande de Modification de la Fiche :
                        <br/><br/>
                        <CTextarea value={formikRefuseDemandeModif.values.observation}
                                   onChange={formikRefuseDemandeModif.handleChange}
                                   disabled={showLoadSpinnerForRefuseDemandeModif}
                                   name="observation"
                                   rows="4"
                                   cols="70"
                                   placeholder="Présenter le Motif de Refus de la Demande de Modification du Plan de Travail ..."/>

                        {
                          formikRefuseDemandeModif.errors.observation && formikRefuseDemandeModif.touched.observation && (
                            <p style={{color: "red"}}>
                              {formikRefuseDemandeModif.errors.observation}
                            </p>
                          )}
                        <br/>
                      </CCol>
                      <CCol md="1"/>
                    </CRow>
                  </CFormGroup>
                  <div className="float-right">
                    {
                      !showLoadSpinnerForRefuseDemandeModif &&
                      <Button color="success" type="submit">
                        Confirmer
                      </Button>
                    }

                    {(
                      showLoadSpinnerForRefuseDemandeModif &&
                      <Spinner animation="grow" variant="primary"/>
                    )}
                    <Button onClick={() => handleExitPresentMotifForRefuseDemandeModif()}
                            disabled={showLoadSpinnerForRefuseDemandeModif}
                            color="dark">
                      Exit
                    </Button>
                  </div>
                </CForm>
              </center>
            </DialogContent>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showSuccessYESRefuseDemandeModif}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                La Demande de Modification du Plan de Travail de votre Étudiant a été refusé avec succès.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handleExitSuccessYESRefuseDemandeModif()}
                      color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showVerifValidateDemandeAnnul}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Validation Demande Annulation Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-vous sûr de vouloir valider <br/>
                la Demande d'Annulation du Plan de Travail de votre Étudiant(e) &nbsp;
                <span className="text-info" style={{fontSize: "12px"}}>
                                    {studentFullName.substring(32)}
                                </span>
                &nbsp;?.
                {
                  typeTrtConv === "Oui" &&
                  <>
                    <br/><br/>
                    <span className="noteCancelConvToo"> Cette Demande est accompagnée avec une Demande d'Annulation de Convention !.</span>
                  </>
                }
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              {
                !showLoadSpinnerForValidateDemandeAnnul &&
                <Button onClick={() => handleValidateDemandeAnnulYES()} color="primary">
                  Oui
                </Button>
              }

              {(
                showLoadSpinnerForValidateDemandeAnnul &&
                <Spinner animation="grow" variant="primary"/>
              )}
              <Button onClick={() => handleValidateDemandeAnnulNO()}
                      disabled={showLoadSpinnerForValidateDemandeAnnul}
                      color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showSuccessYESValidateDemandeAnnul}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                La Demande d'Annulation du Plan de Travail de votre Étudiant a été validé avec succès.
                <br/><br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handleExitSuccessYESValidateDemandeAnnul()}
                      color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showVerifRefuseDemandeAnnul}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Refus Demande Annulation Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-vous sûr de vouloir refuser <br/>
                la Demande d'Annulation du Plan de Travail de votre Étudiant(e) &nbsp;
                <span className="text-info" style={{fontSize: "12px"}}>
                                            {studentFullName.substring(32)}
                                        </span>
                &nbsp;?.
                {
                  typeTrtConv === "Oui" &&
                  <>
                    <br/><br/>
                    <span className="noteCancelConvToo"> Cette Demande est accompagnée avec une Demande d'Annulation de Convention !.</span>
                  </>
                }
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handlePresentMotifForRefuseDemandeAnnulYES()} color="primary">
                Oui
              </Button>
              <Button onClick={() => handleRefuseDemandeAnnulNO()}
                      color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showPresentMotifForRefuseDemandeAnnul}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
                            <span className="myModalTitleMarginTop">
                                Présentation Motif de Refus de Demande Annulation Plan Travail
                            </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <CForm onSubmit={formikRefuseDemandeAnnul.handleSubmit}>
                  <CFormGroup>
                    {error.visible && <p>{error.message}</p>}
                  </CFormGroup>
                  <CFormGroup>
                    <CRow>
                      <CCol md="1"/>
                      <CCol md="10">
                        Merci de présenter le Motif de Refus de la Demande d'Annulation de la Fiche:
                        <br/><br/>
                        <CTextarea value={formikRefuseDemandeAnnul.values.observation}
                                   onChange={formikRefuseDemandeAnnul.handleChange}
                                   disabled={showLoadSpinnerForRefuseDemandeAnnul}
                                   name="observation"
                                   rows="4"
                                   cols="70"
                                   placeholder="Présenter le Motif de Refus de la Demande d'Annulation du Plan de Travail ..."/>

                        {
                          formikRefuseDemandeAnnul.errors.observation && formikRefuseDemandeAnnul.touched.observation && (
                            <p style={{color: "red"}}>
                              {formikRefuseDemandeAnnul.errors.observation}
                            </p>
                          )}
                        <br/>
                      </CCol>
                      <CCol md="1"/>
                    </CRow>
                  </CFormGroup>
                  <div className="float-right">
                    {
                      !showLoadSpinnerForRefuseDemandeAnnul &&
                      <Button color="success" type="submit">
                        Confirmer
                      </Button>
                    }

                    {(
                      showLoadSpinnerForRefuseDemandeAnnul &&
                      <Spinner animation="grow" variant="primary"/>
                    )}
                    <Button onClick={() => handleExitPresentMotifForRefuseDemandeAnnul()}
                            disabled={showLoadSpinnerForRefuseDemandeAnnul}
                            color="dark">
                      Exit
                    </Button>
                  </div>
                </CForm>
              </center>
            </DialogContent>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={showSuccessYESRefuseDemandeAnnul}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                La Demande d'Annulation du Plan de Travail de votre Étudiant a été refusé avec succès.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => handleExitSuccessYESRefuseDemandeAnnul()}
                      color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

        </CCol>
      </CRow>
    </>
  );
}

MyStudentTimeline.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(MyStudentTimeline);
