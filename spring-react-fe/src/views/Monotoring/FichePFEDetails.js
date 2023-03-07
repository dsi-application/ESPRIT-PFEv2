import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import AuthService from "../../views/services/auth.service";
import Tour from "reactour";
import { makeStyles } from "@material-ui/core/styles";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import axios from "axios";
import { Wave, Random } from "react-animated-text";
import "../css/style.css";

import {
  CCol,
  CNav,
  CNavItem,
  CNavLink,
  CRow,
  CTabContent,
  CTabPane,
  CCard,
  CCardBody,
  CTabs,
  CCardHeader,
  CListGroup,
  CListGroupItem,
  CButton,
  CModal,
  CModalHeader,
  CModalTitle,
  CModalFooter,
  CForm,
  CFormGroup,
  CLabel,
  CInput,
  CSelect,
  CSpinner,
  CAlert,
  CDataTable,
  CBadge,
  CInputRadio, CModalBody
} from "@coreui/react";
import * as Yup from "yup";
import { useFormik } from "formik";
import CIcon from "@coreui/icons-react";
import Moment from "moment";
import {
  getEtatFiche,
  selectEtatFiche,
  selectFiche,
  selectSelectedFiche,
} from "../../redux/slices/StudentSlice";
import { queryApi } from "../../utils/queryApi";
import { useHistory } from "react-router";
import {
  fecthEvaluations,
  selectEvaluations,
  selectListCompetences,
  populateEvaluations,
  setErrors,
} from "../../redux/slices/EvaluationSlice";
import {
  addVisit,
  deleteVisit,
  fetchVisits,
  fetchVisitsEtudiant,
  selectVisit,
  selectVisits,
} from "../../redux/slices/VisteSlice";
import { Link } from "react-router-dom";
import { getEtudiant, selectetudiant } from "../../redux/slices/FichePFESlice";
import moment from "moment";
import {
  addRdvPedas,
  fetchEntreprises,
  fetchRdvPedas,
  selectEntreprises,
  selectRdvPeda,
  selectRdvPedas,
} from "../../redux/slices/RDVPedaSlice";
import {
  fetchVisitesTypes,
  selectVisitesTypes,
} from "../../redux/slices/ListingSlice";
import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import StepContent from "@material-ui/core/StepContent";
import Button from "@material-ui/core/Button";
import Paper from "@material-ui/core/Paper";
import { AddEventGoogleWithMeet, AddEventGoogle } from "../../utils/GoogleApi";
import "moment/locale/fr";
const currentTeacher = AuthService.getCurrentTeacher();
export const getEvalType = (e) => {
  if (e === "01") {
    return <b>Note encadrement pédagogique </b>;
  }
  if (e === "02") {
    return <b>Note encadrement entreprise </b>;
  }
  if (e === "03") {
    return <b>Note soutenance finale </b>;
  }
};
const validationSchema = Yup.object().shape({
  observation: Yup.string()
      .max(1000, "Ne pas dépasser 1000 caractères  !")
      .required("* Champs obligatoire !"),
  lieuVisite: Yup.string()
      .max(100, "Ne pas dépasser 100 caractéres !")
      .required("* Champs obligatoire !"),
  typeVisite: Yup.string().required("* Champs obligatoire !"),
  heureDebut: Yup.string().required("* Champs obligatoire !"),
  heureFin: Yup.string().required("* Champs obligatoire !"),
});
const validationSchemaSuivi = Yup.object().shape({
  observation: Yup.string().max(2000, "Ne pas dépasser 2000 caractères  !"),
  typeRDV: Yup.string().required("* Champs obligatoire !"),
  lieuRDV: Yup.string()
      .max(100, "Ne pas dépasser 100 caractères !")
      .required("* Champs obligatoire !"),
  heureDebut: Yup.string().required("* Champs obligatoire !"),
  dateRendezVous: Yup.date().required("* Champs obligatoire !"),
});
const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
  },
  button: {
    marginTop: theme.spacing(1),
    marginRight: theme.spacing(1),
    backgroundColor: "#DB4437",
  },
  actionsContainer: {
    marginBottom: theme.spacing(2),
  },
  resetContainer: {
    padding: theme.spacing(3),
  },
  button2: {
    marginTop: theme.spacing(1),
    marginRight: theme.spacing(1),
    backgroundColor: "#808080",
  },
}));

const stepsTour = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Tous ça commence avec les détails de l'Étudiant.`,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: `Içi, Vous allez trouver les détails de la fiches de projet de fin d'études`,
  },
  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
        <Text>
          Si vous voulez aller consulter les documents déposés de votre étudiants,
          consultez cette partie : <strong>Documents déposés</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__4"]',
    content: () => (
        <Text>
          Si vous voulez aller consulter ou planifier les rendez-vous de suivi
          pédagogique pour votre Étudiant, consultez cette partie :
          <strong>Rendez-vous de suivi</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__5"]',
    content: () => (
        <Text>
          Si vous voulez aller consulter ou planifier la visite du mi-parcous pour
          votre Étudiant, consultez cette partie :
          <strong>Visite Stagiaire</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__6"]',
    content: () => (
        <Text>
          <strong>Enfin </strong>Evaluation de votre Étudiant ,sera la dernier
          partie : <strong>Evaluation de l'Étudiant</strong>.
        </Text>
    ),
  },
];
const FichePFEDetails = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);

  const DataIndex = sessionStorage.getItem("DataIndex");
  const dispatch = useDispatch();
  const history = useHistory();
  const Etu = useSelector(selectetudiant);
  const [showForm, setshowForm] = useState(false);
  const [showStepperError, setshowStepperError] = useState(false);
  const [showFormRDV, setshowFormRDV] = useState(false);
  const [showLoader, setShowLoader] = useState(false);
  const [evaluations, errEval] = useSelector(selectEvaluations);
  const [competences, errComp] = useSelector(selectListCompetences);
  const [RDVSuivis, errRDV] = useSelector(selectRdvPedas);
  const [ent, errent] = useSelector(selectEntreprises);
  const [VisiteTypes, errVisiteTypes] = useSelector(selectVisitesTypes);
  const Fiche = useSelector(selectSelectedFiche);
  const [etatFiche, errEtat] = useSelector(selectEtatFiche);
  const [error, setError] = useState({ visible: false, message: "" });
  const [info, setInfo] = useState(false);
  const [danger2, setDanger2] = useState(false);
  const [showError, setshowError] = useState(false);
  const [showError2, setshowError2] = useState(false);
  const [showObserv, setshowObserv] = useState(false);
  const [Item, setItem] = useState({});
  const [value0, setvalue0] = useState("");
  const [value1, setvalue1] = useState("");
  const [value2, setvalue2] = useState("");
  const [value3, setvalue3] = useState("");
  const [value4, setvalue4] = useState("");
  const [value5, setvalue5] = useState("");
  const [value6, setvalue6] = useState("");
  const [value7, setvalue7] = useState("");
  const classes = useStyles();
  const [activeStep, setActiveStep] = React.useState(0);
  const [visits, err] = useSelector(selectVisits);
  // console.log("Fiche",Fiche)
  // console.log('ent===',ent)
  const getSteps = () => {
    return [
      {
        label: competences[0][0],
        value: competences[0][1],
        color: "#DB4437",
      },
      {
        label: competences[1][0],
        value: competences[1][1],
        color: "#DB4437",
      },
      {
        label: competences[2][0],
        value: competences[2][1],
        color: "#DB4437",
      },
      {
        label: competences[3][0],
        value: competences[3][1],
        color: "#DB4437",
      },
      {
        label: competences[4][0],
        value: competences[4][1],
        color: "#DB4437",
      },
      {
        label: competences[5][0],
        value: competences[5][1],
        color: "#DB4437",
      },
      {
        label: competences[6][0],
        value: competences[6][1],
        color: "#DB4437",
      },
      {
        label: competences[7][0],
        value: competences[7][1],
        color: "#DB4437",
      },
    ];
  };
  const ListValue = [
    { value: "A" },
    { value: "B" },
    { value: "C" },
    { value: "D" },
  ];

  function getStepContent(step, code) {
    switch (step) {
      case 0:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase0(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value0 === e.value}
                              custom
                              id={e.value}
                              name="resultat"
                              value={value0}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 1:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase1(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value1 === e.value}
                              custom
                              id={e.value}
                              name="resultat1"
                              value={value1}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 2:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase2(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value2 === e.value}
                              custom
                              id={e.value}
                              name="resultat2"
                              value={value1}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 3:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase3(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value3 === e.value}
                              custom
                              id={e.value}
                              name="resultat3"
                              value={value1}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 4:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase4(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value4 === e.value}
                              custom
                              id={e.value}
                              name="resultat4"
                              value={value1}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 5:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase5(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value5 === e.value}
                              custom
                              id={e.value}
                              name="resultat5"
                              value={value1}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 6:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase6(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value6 === e.value}
                              custom
                              id={e.value}
                              name="resultat6"
                              value={value1}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );
      case 7:
        return (
            <>
              <CFormGroup row>
                <CCol md="2">
                  <CLabel>Votre Choix : </CLabel>
                </CCol>
                <CCol md="10">
                  {ListValue?.map((e, i) => (
                      <>
                        <CFormGroup
                            variant="custom-radio"
                            inline
                            onChange={() => oncChangeCase7(e.value, code, step)}
                        >
                          <CInputRadio
                              checked={value7 === e.value}
                              custom
                              id={e.value}
                              name="resultat7"
                              value={value7}
                          />
                          <CLabel variant="custom-checkbox" htmlFor={e.value}>
                            {e.value}
                          </CLabel>
                        </CFormGroup>
                      </>
                  ))}
                </CCol>
              </CFormGroup>
            </>
        );

      default:
        return "Unknown step";
    }
  }
  const useIconStyles = makeStyles(
      (() => {
        return getSteps().reduce((styles, step, index) => {
          styles[index] = { color: `${step.color} !important` };
          return styles;
        }, {});
      })()
  );
  const iconClasses = useIconStyles();
  const steps = getSteps();
  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleReset = () => {
    setActiveStep(0);
    if (showStepperError === true) setshowStepperError(false);
  };

  const deleteVisitEvent = async (v) => {
    // console.log(v)
    const [err] = await queryApi(
        "encadrement/deleteVisite?idET=" +
        v.visiteStagiairePK.fichePFEPK.idEt +
        "&dateFiche=" +
        v.visiteStagiairePK.fichePFEPK.dateDepotFiche +
        "&dateVisite=" +
        moment(v.visiteStagiairePK.dateVisite)
            .format("YYYY-MM-DD")
            .toString()
        ,
        {},
        "DELETE"
    );
    if (err) {
      dispatch(setErrors(err));
    } else {
      dispatch(deleteVisit(v));
      dispatch(fetchVisits());
      if(showForm === true ) setshowForm(false);
      if(showLoader === true ) setShowLoader(false);
      formik.resetForm();
      setDanger2(false);
    }
  };
  // console.log("visits",visits)
      useEffect(() => {
        if (visits === null || visits.length === 0 || visits[0] === "")
        {

            dispatch(fetchVisitsEtudiant(Fiche.idFichePFE.idEt, Fiche.idFichePFE.dateDepotFiche));

    }
    if (VisiteTypes === null || VisiteTypes.length === 0) {
      dispatch(fetchVisitesTypes());
    }

    if (etatFiche.etat === null) {
      dispatch(getEtatFiche(Fiche.etatFiche));
    }
    if (RDVSuivis === null) {
      dispatch(
          fetchRdvPedas(Fiche.idFichePFE.idEt, Fiche.idFichePFE.dateDepotFiche)
      );
    }
    if (ent.length === 0) {
      dispatch(fetchEntreprises(Fiche.idFichePFE.idEt));
    }
    if (evaluations === null || evaluations.length === 0) {
      dispatch(
          fecthEvaluations(Fiche.idFichePFE.idEt, Fiche.idFichePFE.dateDepotFiche)
      );
    }
    if (!Fiche) history.replace("/StudentSupervision");
  }, [dispatch]);
  const getCompetence = (code) => {
    switch (code) {
      case "01":
        return "Comprendre et intégrer les enjeux et la stratégie de l’entreprise";
      case "02":
        return "Analyser et/ou chercher les solutions à un problème de conception, de réalisation, d’amélioration de produit, de systèmes ou de services au sein d’une organisation";
      case "03":
        return "Conduire un projet de création, de conception, de réalisation, d’amélioration de produit, de système ou deservice";
      case "04":
        return "Mettre en œuvre sa maîtrise scientifique ou technique au sein de l’organisation";
      case "05":
        return "Organiser sa mission et manager les ressources";
      case "06":
        return "Qualité et présentation du document";
      case "07":
        return "Qualité de la présentation orale";
      case "08":
        return "Qualité de l’argumentation";
      default:
        return "primary";
    }
  };
  const getEvalModule = (note) => {
    if (36 <= note) return "A";
    if (28 <= note && note <= 35) return "B";
    if (12 <= note && note <= 27) return "C";
    if (note <= 11) return "D";
  };
  const getCompetenceColor = (color) => {
    switch (color) {
      case "A":
        return "Purple";
      case "B":
        return "DeepPink";
      case "C":
        return "DarkRed";
      case "D":
        return "Grey";
      default:
        return "info";
    }
  };
  const onClickUpdateVisite = (v) => {
    dispatch(fetchEntreprises(Fiche.idFichePFE.idEt));
    dispatch(selectVisit(v));
    dispatch(selectFiche(Fiche));
  };
  const onClickUpdateRDV = (r) => {
    dispatch(getEtudiant(r.rdvSuiviStagePK.fichePFEPK.idEt));
    dispatch(selectRdvPeda(r));
    setInfo(!info);
  };
  const onClickUpdateRDV2 = (r) => {
    dispatch(getEtudiant(r.rdvSuiviStagePK.fichePFEPK.idEt));
    dispatch(selectFiche(Fiche));
    dispatch(selectRdvPeda(r));
  };
  const Download = (p) => {
    axios
        .get(
            `${process.env.REACT_APP_API_URL}` +
            "encadrement/download?fileName=" +
            encodeURIComponent(p),

            { responseType: "blob" }
        )
        .then((response) => {
          //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];

          const file = new Blob([response.data], { type: "application/pdf" });
          let url = window.URL.createObjectURL(file);

          let a = document.createElement("a");
          a.href = url;
          a.download = p.substring(p.lastIndexOf("/") + 1);
          // console.log(url);
          window.open(url);
          a.click();
        });
  };
  const toggleForm = () => {
    setshowForm(showForm ? false : true);
  };
  const toggleFormRDV = () => {
    setshowFormRDV(showFormRDV ? false : true);
  };
  const oncChangeCase0 = (A, code, step) => {
    setvalue0(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase1 = (A, code, step) => {
    setvalue1(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase2 = (A, code, step) => {
    setvalue2(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase3 = (A, code, step) => {
    setvalue3(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase4 = (A, code, step) => {
    setvalue4(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase5 = (A, code, step) => {
    setvalue5(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase6 = (A, code, step) => {
    setvalue6(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const oncChangeCase7 = (A, code, step) => {
    setvalue7(A);
    formikStepper.values.ArrayValues[step] = {
      evaluationStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        codeEvaluation: "01",
        codeCompetence: code,
      },
      resultat: A,
    };
  };
  const formikStepper = useFormik({
    initialValues: {
      ArrayValues: [
        {
          evaluationStagePK: {
            fichePFEPK: {
              idEt: "",
              dateDepotFiche: "",
            },
            codeEvaluation: "",
            codeCompetence: "",
          },
          resultat: "",
        },
      ],
    },

    onSubmit: async (values) => {
      if (
          value0 &&
          value1 &&
          value2 &&
          value3 &&
          value4 &&
          value5 &&
          value6 &&
          value7
      ) {
        setShowLoader(true);
        const [res, err] = await queryApi(
            "encadrement/saisiEncadrantPedaEval",
            JSON.stringify(values.ArrayValues),
            "POST",
            false
        );
        if (err) {
          setShowLoader(false);
          setError({
            visible: true,
            message: JSON.stringify(err.errors, null, 2),
          });
        } else {
          sessionStorage.setItem("DataIndex", 4);
          dispatch(populateEvaluations(res));
          setshowStepperError(false);
          setShowLoader(false);
        }
      } else {
        setshowStepperError(true);
      }
    },
  });

  const formik = useFormik({
    initialValues: {
      visiteStagiairePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        dateVisite: "",
      },
      typeVisite: "",
      lieuVisite: "",
      heureDebut: "",
      heureFin: "",
      observation: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const date = values.visiteStagiairePK.dateVisite;
      const dateFormat = "DD-MM-YYYY";
      const toDateFormat = moment(new Date(date)).format(dateFormat);
      if (
          values.visiteStagiairePK.dateVisite !== "" &&
          moment(toDateFormat, dateFormat, true).isValid() &&
          values.heureDebut < values.heureFin
      ) {
        setShowLoader(true);
        setshowError(false);
        const [res, err] = await queryApi(
            "encadrement/planVisiteStagiaire",
            values,
            "POST",
            false
        );
        /*  var DateVisite = new Date(values.visiteStagiairePK.dateVisite);
          var heureDeb = values.heureDebut;
          var heureFin = values.heureFin;
          var dateDeb = new Date(
            parseInt(DateVisite.getFullYear()),
            parseInt(DateVisite.getMonth()),
            parseInt(DateVisite.getDate()),
            parseInt(heureDeb.substr(0, 2)),
            parseInt(heureDeb.substr(3, 4)),
            0
          );
          var dateFin = new Date(
            parseInt(DateVisite.getFullYear()),
            parseInt(DateVisite.getMonth()),
            parseInt(DateVisite.getDate()),
            parseInt(heureFin.substr(0, 2)),
            parseInt(heureFin.substr(3, 4)),
            0
          );
          if (values.typeVisite === "P") {
            AddEventGoogle(
              currentTeacher.id,
              Etu.adressemailesp,
              dateDeb,
              dateFin,
              "Visite Mi-parcours",
              values.lieuRDV,
              "Visite Mi-parcours En Présentiel"
            );
          } else {
            AddEventGoogleWithMeet(
              currentTeacher.id,
              Etu.adressemailesp,
              dateDeb,
              dateFin,
              "Visite Mi-parcours",
              values.lieuRDV,
              "Visite Mi-parcours En Ligne"
            );
          }*/
        if (err) {
          setShowLoader(false);
          setError({
            visible: true,
            message: JSON.stringify(err.errors, null, 2),
          });
        } else {
          if (showError === true) setshowError(false);

          dispatch(addVisit(res));
          dispatch(fetchVisits());
          sessionStorage.setItem("DataIndex", 3);


        }
      } else if (
          (values.visiteStagiairePK.dateVisite === "" &&
              !moment(toDateFormat, dateFormat, true).isValid()) ||
          values.heureDebut > values.heureFin
      ) {
        values.visiteStagiairePK.dateVisite === "" &&
        !moment(toDateFormat, dateFormat, true).isValid()
            ? setshowError(true)
            : setshowError(false);
        values.heureDebut > values.heureFin
            ? setshowError2(true)
            : setshowError2(false);
      }
    },
  });
  const formikRDV = useFormik({
    initialValues: {
      rdvSuiviStagePK: {
        fichePFEPK: {
          idEt: Fiche.idFichePFE.idEt,
          dateDepotFiche: Fiche.idFichePFE.dateDepotFiche,
        },
        dateSaisieRendezVous: new Date(),
      },
      dateRendezVous: new Date(),
      etat: "",
      heureDebut: "",
      lieuRDV: "",
      observation: "",
      typeRDV: "",
    },
    validationSchema: validationSchemaSuivi,
    onSubmit: async (values) => {
      setShowLoader(true);

      const [res, err] = await queryApi(
          "encadrement/planRendezVous",
          values,
          "POST",
          false
      );

      if (err) {
        setShowLoader(false);
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {

        dispatch(addRdvPedas(res));
        setshowFormRDV(!showFormRDV);
        setShowLoader(false);
        formikRDV.resetForm();
      }
    },
  });

  const toggleObservation = (item) => {
    setItem(item);
    setshowObserv(!showObserv);
  };
  const fields = [
    {
      key: "dateRendezVous",
      _style: { width: "10%" },
      label: "Date",
    },
    { key: "heureDebut", _style: { width: "7%" }, label: "Horaire" },
    { key: "lieuRDV", _style: { width: "10%" }, label: "Adresse" },
    { key: "etat", _style: { width: "7%" }, label: "État" },
    { key: "typeRDV", _style: { width: "7%" }, label: "Type" },
    {
      key: "show_details",
      label: "",
      _style: { width: "20%" },
    },
    {
      key: "modifier",
      label: "",
      _style: { width: "20%" },
    },
  ];
  const getBadge = (etat) => {
    switch (etat) {
      case "02":
        return "success";
      case "04":
        return "info";
      case "03":
        return "warning";
      case "01":
        return "danger";
      default:
        return "primary";
    }
  };
  const getBadge2 = (etat) => {
    switch (etat) {
      case "P":
        return "success";
      case "D":
        return "info";
      default:
        return "primary";
    }
  };
  const getType = (etat) => {
    switch (etat) {
      case "P":
        return "PRESENTIEL";
      case "D":
        return "DISTANTIEL";
      default:
        return "--";
    }
  };
  const getEtat = (etat) => {
    switch (etat) {
      case "02":
        return "Realisé";
      case "04":
        return "En attente";
      case "03":
        return "Annulé";
      case "01":
        return "Raté";
      default:
        return "rien";
    }
  };

  const displayVisit = () => {
    if (visits === null || visits.length === 0 ) {
      return (
          <>

            {Fiche.etatFiche === "01" || Fiche.etatFiche === "02"
            || Fiche.etatFiche === "04" || Fiche.etatFiche === "05"  ?
                <CCardHeader> <b >
                  Vous ne pouvez pas planifier une Visite Stagiaire pour cet
                  Étudiant
                </b></CCardHeader> :
                <CCardHeader> <CButton
                    className="float-right"
                    variant="outline"
                    color="danger"
                    size="sm"
                    onClick={toggleForm}
                >
                  Planifier une <b> Visite Stagiaire </b> pour cet Étudiant
                </CButton>

                  <b>
                    Vous n'avez pas encore planifier une Visite Stagiaire pour cet
                    Étudiant
                  </b>  </CCardHeader>}


            <CCardBody>
              {showForm ? (
                  <CForm onSubmit={formik.handleSubmit}>
                    <CFormGroup>
                      
                      {error.visible && <p>{error.message}</p>}
                    </CFormGroup>
                    <CFormGroup row>
                      <CCol md="3">
                        <CLabel htmlFor="selectSm">
                          <b>Type du visite</b>
                        </CLabel>
                      </CCol>
                      <CCol xs="12" md="9">
                        <CSelect
                            value={formik.values.typeVisite}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            custom
                            size="sm"
                            name="typeVisite"
                        >
                          <option style={{ display: "none" }}>
                            
                            ---- Choisir une option ----
                          </option>
                          {VisiteTypes?.map((e, i) => (
                              <option value={e[1]} key={i}>
                                {e[0]}
                              </option>
                          ))}
                        </CSelect>
                        {formik.errors.typeVisite && formik.touched.typeVisite && (
                            <p style={{ color: "red" }}>{formik.errors.typeVisite}</p>
                        )}
                        <br />
                      </CCol>
                    </CFormGroup>
                    <CFormGroup row>
                      <CCol md="3">
                        <CLabel>
                          <b>Lieu :</b>
                        </CLabel>
                      </CCol>
                      <CCol xs="12" md="9">
                        <CSelect
                            value={formik.values.lieuVisite}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            custom
                            size="sm"
                            name="lieuVisite"
                        >
                          <option style={{ display: "none" }}>
                            
                            ---- Choisir une option ----
                          </option>
                          {ent?.map((e, i) => (
                              <option value={e.address} key={i}>
                                {e.address}
                              </option>
                          ))}
                        </CSelect>
                        {formik.errors.lieuVisite && formik.touched.lieuVisite && (
                            <p style={{ color: "red" }}>{formik.errors.lieuVisite}</p>
                        )}
                      </CCol>
                    </CFormGroup>
                    <CFormGroup row>
                      <CCol md="3">
                        <CLabel>
                          <b>Observation :</b>
                        </CLabel>
                      </CCol>
                      <CCol xs="12" md="9">
                        <CKEditor
                            editor={ClassicEditor}
                            config={{
                              toolbar: [
                                "heading",
                                "|",
                                "bold",
                                "italic",
                                "link",
                                "bulletedList",
                                "numberedList",
                                "blockQuote",
                              ],
                            }}
                            //data="<p>Hello from CKEditor 5!</p>"
                            onReady={(editor) => {
                              // You can store the "editor" and use when it is needed.
                            }}
                            onChange={(event, editor) => {
                              const data = editor.getData();
                              formik.setFieldValue("observation", data);
                            }}
                            onBlur={(event, editor) => {
                              // console.log("Blur.", editor);
                            }}
                            onFocus={(event, editor) => {
                              // console.log("Focus.", editor);
                            }}
                        />

                        {formik.errors.observation &&
                        formik.touched.observation && (
                            <p style={{ color: "red" }}>
                              {formik.errors.observation}
                            </p>
                        )}
                      </CCol>
                    </CFormGroup>
                    <CFormGroup row>
                      <CCol md="3">
                        <CLabel>
                          <b>Date du visite:</b>
                        </CLabel>
                      </CCol>
                      <CCol xs="12" md="9">
                        <CInput
                            value={moment(formik.values.visiteStagiairePK.dateVisite)
                                .format("yyyy-MM-DD")
                                .toString()}
                            onChange={formik.handleChange}
                            type="date"
                            name="visiteStagiairePK.dateVisite"
                            placeholder="Visite Date"
                        />
                        {showError && (
                            <p style={{ color: "red" }}>
                              * Date invalide ou Champ vide ! veuillez saisir une date
                              valide 
                            </p>
                        )}
                      </CCol>
                    </CFormGroup>
                    <CFormGroup row>
                      <CCol md="3">
                        <CLabel htmlFor="date-input">
                          
                          <b>Heure du début:</b>
                        </CLabel>
                      </CCol>
                      <CCol xs="12" md="9">
                        <CInput
                            value={formik.values.heureDebut}
                            onChange={formik.handleChange}
                            type="time"
                            name="heureDebut"
                            placeholder="Visite Start time"
                        />
                        {formik.errors.heureDebut && formik.touched.heureDebut && (
                            <p style={{ color: "red" }}>{formik.errors.heureDebut}</p>
                        )}
                        {showError2 && (
                            <p style={{ color: "red" }}>
                              * L'heure du début doit être inférieure à l'heure de la
                              fin.
                            </p>
                        )}
                      </CCol>
                    </CFormGroup>
                    <CFormGroup row>
                      <CCol md="3">
                        <CLabel htmlFor="date-input">
                          
                          <b>Heure du fin:</b>
                        </CLabel>
                      </CCol>
                      <CCol xs="12" md="9">
                        <CInput
                            value={formik.values.heureFin}
                            onChange={formik.handleChange}
                            type="time"
                            name="heureFin"
                            placeholder="Visite End time"
                        />
                        {formik.errors.heureFin && formik.touched.heureFin && (
                            <p style={{ color: "red" }}>{formik.errors.heureFin}</p>
                        )}
                        {showError2 && (
                            <p style={{ color: "red" }}>
                              * L'heure du début doit être inférieure à l'heure de la
                              fin.
                            </p>
                        )}
                      </CCol>
                    </CFormGroup>
                    <CButton
                        className="float-right"
                        disabled={showLoader}
                        name="btnsubmit"
                        type="submit"
                        color="danger"
                    >
                      {showLoader && <CSpinner grow size="sm" />}
                      Enregister
                    </CButton>
                    <br></br> <br></br>
                    {showLoader && (
                        <CAlert color="danger">
                          Attendre un petit peu... Nous allons notifer votre Étudiant
                          et Vous allez reçevoir un Email de confirmation ....
                        </CAlert>
                    )}
                  </CForm>
              ) : (
                  <></>
              )}
            </CCardBody>
          </>
      );
    } else {
      return (
          <>
            
            <CCardBody>
              <CCol xs="12" sm="12" md="12">
                <CCard color="gradient-secondary">
                  {visits.length  &&
                  visits?.map((V, i) => (
                      <>
                        <CCardHeader>
                          <b>
                            
                            {Moment(
                                V.visiteStagiairePK.dateVisite
                            ).format("MMMM Do YYYY")}
                          </b>&nbsp;
                          de    {V.heureDebut} H à  {V.heureFin} H
                          {V.typeVisite === "P" ? (
                              <p>
                                Cette visite va être <b>Présentielle</b> à&nbsp;
                                <b>{V.lieuVisite}</b>
                              </p>
                          ) : (
                              <p>
                                Cette visite va être <b>En Ligne</b> à&nbsp;
                                <b>{V.lieuVisite}</b>
                              </p>
                          )}


                        </CCardHeader>
                        <CCardBody>
                          <b>Observation: </b>
                          <br></br>
                          <div
                              style={{
                                wordWrap: "break-word",
                              }}
                              className="editor"
                              dangerouslySetInnerHTML={{
                                __html: V.observation,
                              }}
                          ></div>
                          <br></br> <br></br>
                          <CButton
                              variant="outline"
                              color="danger"
                              size="sm"
                              onClick={() => setDanger2(!danger2)}
                          >
                            <CIcon name="cil-trash"></CIcon>
                          </CButton> &nbsp;&nbsp;
                          <Link to={"/UpdateVisitInformation"}>
                            <CButton
                                color="dark"
                                size="sm"
                                onClick={() => onClickUpdateVisite(V)}
                            >
                              
                              <CIcon name="cil-align-left"></CIcon>&nbsp;&nbsp;
                              Modifier les informations du Visite
                            </CButton>
                          </Link>
                        </CCardBody>
                        <CModal
                            show={danger2}
                            onClose={() => setDanger2(!danger2)}
                            color="danger"
                        >
                          <CModalHeader closeButton>
                            <CModalTitle>
                              <span style={{fontSize:"13px"}}>
                                Confirmation
                              </span>
                            </CModalTitle>
                          </CModalHeader>
                          <CModalBody>
                           <br/>
                            <center>
                              Voulez-vous vraiment supprimer cette Visite Stagiaire ?.
                            </center>
                            <br/><br/>
                          </CModalBody>
                          <CModalFooter>
                            <CButton
                                color="danger"
                                onClick={() => deleteVisitEvent(V)}
                            >
                              YES
                            </CButton>
                            <CButton color="secondary" onClick={() => setDanger2(!danger2)}>
                              Cancel
                            </CButton>
                          </CModalFooter>
                        </CModal>
                      </>


                  ) )}


                </CCard>
              </CCol>
            </CCardBody>

          </>
      );
    }
  };
  return (
      <>
        <Tour
            steps={stepsTour}
            isOpen={isTourOpen}
            onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
            onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
            onRequestClose={() => setIsTourOpen(false)}
        />
        <div>
          <CCol xs="12" md="12" className="mb-4">
            <CCard>
              <CCardHeader data-tut="reactour__1">
                <CRow>
                  
                  <CCol xs="12" sm="6" md="6">
                    <CRow>
                      <CCol xs="12">
                        &nbsp;
                        <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                          Détails Plan Travail</span>
                      </CCol>
                    </CRow>
                  </CCol>
                  <CCol xs="12" sm="6" md="6">
                    <h6 className="float-right">
                      <b>
                        {Etu.prenomet} {Etu.nomet} &nbsp;&nbsp; {Etu.classe}
                      </b>
                    </h6>
                    <br></br> <br></br>
                    <h6 className="float-right"> {Etu.adressemailesp}</h6>
                  </CCol>
                </CRow>
              </CCardHeader>

              <CCardBody>
                <CTabs activeTab={Number(DataIndex)}>
                  <CNav variant="tabs">
                    <CNavItem data-tut="reactour__2">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        
                        Détails Projet
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__3">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Documents Déposés
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__4">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Rendez-Vous de Suivi
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__5">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Visite Stagiaire
                      </CNavLink>
                    </CNavItem>
                    {Fiche.etatFiche === "01" || Fiche.etatFiche === "02"
                    || Fiche.etatFiche === "04" || Fiche.etatFiche === "05"  ? <></> :
                        <CNavItem data-tut="reactour__6">
                          <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                            Évaluation de l'Étudiant
                          </CNavLink>
                        </CNavItem> }

                  </CNav>
                  <CTabContent>
                    <CTabPane>
                      <br></br>
                      <CCol xs="12" sm="12" md="12">
                        <CCard accentColor="danger">
                          <CCardHeader>
                            <CRow>
                              <CCol xs="12" sm="6" md="12">
                                <span className="advancedPlanifPolyCriteria">
        <Wave style={{fontSize:"50px"}} text={Fiche.titreProjet} effect="fadeOut" effectChange={1.0} />

                              </span>
                                <br/>

                                <CRow>
                                  <CCol md="2">
                                    <b>Identifiant Étudiant:</b>
                                  </CCol>
                                  <CCol md="8">
                                    {Fiche.idFichePFE.idEt}
                                  </CCol>
                                </CRow>

                                <CRow>
                                  <CCol md="2">
                                    <b>Date Dépôt Fiche:</b>
                                  </CCol>
                                  <CCol md="8">
                                    {Moment(Fiche.idFichePFE.dateDepotFiche).locale("fr").format("LLLL")}
                                  </CCol>
                                </CRow>

                                <CRow>
                                  <CCol md="2">
                                    <b>État Fiche:</b>
                                  </CCol>
                                  <CCol md="8">
                                  <span className="darkMark">
                                    {etatFiche.etat}
                                  </span>
                                  </CCol>
                                </CRow>

                              </CCol>
                            </CRow>
                            <br/>
                          </CCardHeader>
                          <CCardBody>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <span className="primaryMark">Description Projet:</span>
                                <p>
                                  {Fiche.descriptionProjet !== "----------"
                                      ? Fiche.descriptionProjet
                                      : "--"}
                                </p>
                                <br/>
                                <span className="infoMark">Problématiques Projet:</span>
                                <CListGroup accent>
                                  {Fiche.problematics?.map((item, i) => (
                                      <>
                                        <CListGroupItem accent="info">
                                          {item.name}
                                        </CListGroupItem>
                                        <br></br>
                                      </>
                                  ))}
                                </CListGroup>
                              </CCol>
                              <CCol xs="12" sm="6" md="6">
                                <span className="secondaryMark">Fonctionnalités Projet:</span>
                                <CListGroup accent>
                                  {Fiche.functionnalities?.map((item, i) => (
                                      <>
                                        
                                        <CListGroupItem accent="dark">
                                          <b> {item.name} </b>
                                          <br></br> <b>Description : </b>
                                          {
                                            item.description === "null" ?
                                                <>--</>
                                                :
                                                <>{item.description}</>
                                          }
                                        </CListGroupItem>
                                        <br></br>
                                      </>
                                  ))}
                                </CListGroup>
                                <br/>
                                <span className="lightMark">Téchnologies utilisées:</span>
                                <CListGroup accent>
                                  {Fiche.technologies?.map((item, i) => (
                                      <CListGroupItem accent="secondary">
                                        {item.name} <br></br>
                                      </CListGroupItem>
                                  ))}
                                </CListGroup>
                              </CCol>
                            </CRow>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    </CTabPane>


                    <CTabPane>
                      
                      <br></br>
                      <CCol xs="12" sm="12" md="12">
                        <CCard accentColor="danger">
                          <CCardHeader>
                            <span className="redMark">
                              Journal de Bord & Bilans Périodiques
                            </span>
                          </CCardHeader>
                          <CCardBody>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b>Bilans Périodiques</b>
                                  </CCardHeader>
                                  <CCardBody>
                                    <CListGroup accent>
                                      <CListGroupItem accent="dark">
                                        {Fiche.pathBilan1 ? (
                                            <>
                                              <b>Date dépôt Premier Bilan. : </b>&nbsp;&nbsp;
                                              {Moment(Fiche.dateDepotBilan1).format(
                                                  "DD/MM/YYYY"
                                              )}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.pathBilan1);
                                                  }}
                                              >
                                                Télécharger le Premier Bilan &nbsp;
                                                &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour le Premier Bilan."
                                        )}
                                      </CListGroupItem>
                                      <CListGroupItem accent="secondary">
                                        {Fiche.pathBilan2 ? (
                                            <>
                                              <b>Date dépôt Deuxième Bilan : </b>&nbsp;&nbsp;
                                              {Moment(Fiche.dateDepotBilan2).format(
                                                  "DD/MM/YYYY"
                                              )}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.pathBilan2);
                                                  }}
                                              >
                                                Télécharger le Deuxième Bilan &nbsp;
                                                &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour le Deuxième Bilan."
                                        )}
                                      </CListGroupItem>
                                      <CListGroupItem accent="light">
                                        {Fiche.pathBilan3 ? (
                                            <>
                                              <b>Date dépôt Dernier Bilan : </b>&nbsp;&nbsp;
                                              {Moment(Fiche.dateDepotBilan3).format(
                                                  "DD/MM/YYYY"
                                              )}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.pathBilan3);
                                                  }}
                                              >
                                                Télécharger le Dernier Bilan &nbsp;
                                                &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour le Dernier Bilan."
                                        )}
                                      </CListGroupItem>
                                    </CListGroup>
                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol xs="12" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b>Journal de Bord</b>
                                  </CCardHeader>
                                  <CCardBody>
                                    {Fiche.pathJournalStg ? (
                                        <>
                                          <b>Date dépôt Journal de Bord: </b>&nbsp;&nbsp;
                                          {Moment(Fiche.dateDepotJournalStg).format(
                                              "DD/MM/YYYY"
                                          )}
                                          <br/>
                                          <CButton
                                              className="float-left"
                                              variant="outline"
                                              color="dark"
                                              size="sm"
                                              onClick={() => {
                                                Download(Fiche.pathJournalStg);
                                              }}
                                          >
                                            Télécharger la Derniere Version du
                                            Journal de Bord &nbsp; &nbsp;
                                            <CIcon name="cil-save"></CIcon>
                                          </CButton>
                                        </>
                                    ) : (
                                        "Pas de document déposé pour le Journal de Bord."
                                    )}
                                  </CCardBody>
                                </CCard>
                              </CCol>
                            </CRow>
                          </CCardBody>
                        </CCard>

                        <CCard accentColor="danger">
                          <CCardHeader>
                            <span className="redMark">
                              Rapports PFE & Rapport Anti-Plagiat & Attestation de Stage
                            </span>
                          </CCardHeader>
                          <CCardBody>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b> Rapports PFE</b>
                                  </CCardHeader>
                                  <CCardBody>
                                    <CListGroup accent>
                                      <CListGroupItem accent="dark">
                                        {Fiche.pathRapportVersion1 ? (
                                            <>
                                              <b>
                                                Date dépôt Première Version du Rapport :&nbsp;&nbsp;
                                              </b>
                                              {Moment(
                                                  Fiche.dateDepotRapportVersion1
                                              ).format("DD/MM/YYYY")}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(
                                                        Fiche.pathRapportVersion1
                                                    );
                                                  }}
                                              >
                                                Télécharger la Première Version du
                                                Rapport &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour la Première Version du Rapport."
                                        )}
                                      </CListGroupItem>
                                      <CListGroupItem accent="secondary">
                                        {Fiche.pathRapportVersion2 ? (
                                            <>
                                              <b>Date Dépot de la Version Finale du Rapport : </b>&nbsp;&nbsp;
                                              {Moment(
                                                  Fiche.dateDepotRapportVersion2
                                              ).format("DD/MM/YYYY")}
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(
                                                        Fiche.pathRapportVersion2
                                                    );
                                                  }}
                                              >
                                                Télécharger la Derniére Version du
                                                Rapport &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour la Version Finale du Rapport."
                                        )}
                                      </CListGroupItem>
                                    </CListGroup>
                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol xs="6" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b> Rapport Anti-Plagiat</b>
                                  </CCardHeader>
                                  <CCardBody>
                                        {Fiche.pathPlagiat ? (
                                            <>
                                              <b>Date dépôt Rapport Anti-Plagiat : </b>&nbsp;&nbsp;
                                              {Moment(
                                                  Fiche.dateDepotPlagiat
                                              ).format("DD/MM/YYYY")}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.pathPlagiat);
                                                  }}
                                              >
                                                Télécharger le Rapport Anti-Plagiat &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour le Rapport Anti-Plagiat."
                                        )}
                                  </CCardBody>
                                </CCard>
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b> Attestation Stage</b>
                                  </CCardHeader>
                                  <CCardBody>
                                        {Fiche.pathAttestationStage ? (
                                            <>
                                              <b>Date Dépot Attestation Stage : </b>&nbsp;&nbsp;
                                              {Moment(
                                                  Fiche.dateDepotAttestationStage
                                              ).format("DD/MM/YYYY")}
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.pathAttestationStage);
                                                  }}
                                              >
                                                Télécharger Attestation Stage : &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ) : (
                                            "Pas de document déposé pour l'Attestation du Stage."
                                        )}
                                  </CCardBody>
                                </CCard>
                              </CCol>
                            </CRow>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    </CTabPane>


                    <CTabPane>
                      <br></br>

                      <CCol xs="12" sm="12" md="12">
                        <CCard accentColor="danger">
                          <>

                            {Fiche.etatFiche === "01" || Fiche.etatFiche === "02" || Fiche.etatFiche === "04" || Fiche.etatFiche === "05" ?

                                <CCardHeader> <b >
                                  Vous ne pouvez pas planifier un Rendez vous de suivi pour cet
                                  Étudiant
                                </b></CCardHeader>
                                :  <CCardHeader><CButton
                                    className="float-right"
                                    variant="outline"
                                    color="danger"
                                    size="sm"
                                    onClick={toggleFormRDV}
                                >
                                  Planifier un <b> Rendez-Vous de Suivi </b> pour
                                  cet Étudiant
                                </CButton> </CCardHeader>}


                            <CCardBody>
                              {showFormRDV ? (
                                  <CForm onSubmit={formikRDV.handleSubmit}>
                                    <CFormGroup>
                                      
                                      {error.visible && <p>{error.message}</p>}
                                    </CFormGroup>
                                    <CFormGroup row>
                                      <CCol md="3">
                                        <CLabel htmlFor="selectSm">
                                          <b>Type Rendez-Vous</b>
                                        </CLabel>
                                      </CCol>
                                      <CCol xs="12" md="9">
                                        <CSelect
                                            value={formikRDV.values.typeRDV}
                                            onChange={formikRDV.handleChange}
                                            onBlur={formikRDV.handleBlur}
                                            custom
                                            size="sm"
                                            name="typeRDV"
                                        >
                                          <option style={{ display: "none" }}>
                                            
                                            ---- Select an option ----
                                          </option>
                                          {VisiteTypes?.map((e, i) => (
                                              <option value={e[1]} key={i}>
                                                {e[0]}
                                              </option>
                                          ))}
                                        </CSelect>
                                        {formikRDV.errors.typeRDV &&
                                        formikRDV.touched.typeRDV && (
                                            <p style={{ color: "red" }}>
                                              {formikRDV.errors.typeRDV}
                                            </p>
                                        )}

                                        <br />
                                      </CCol>
                                    </CFormGroup>
                                    <CFormGroup row>
                                      <CCol md="3">
                                        <CLabel>
                                          <b>Lieu :</b>
                                        </CLabel>
                                      </CCol>
                                      <CCol xs="12" md="9">
                                        <CInput
                                            value={formikRDV.values.lieuRDV}
                                            onChange={formikRDV.handleChange}
                                            type="input"
                                            name="lieuRDV"
                                            placeholder="Saisir le lieu du rendez vous"
                                        />
                                        {formikRDV.errors.lieuRDV &&
                                        formikRDV.touched.lieuRDV && (
                                            <p style={{ color: "red" }}>
                                              {formikRDV.errors.lieuRDV}
                                            </p>
                                        )}

                                        <br />
                                      </CCol>
                                    </CFormGroup>
                                    <CFormGroup row>
                                      <CCol md="3">
                                        <CLabel>
                                          <b>Observation :</b>
                                        </CLabel>
                                      </CCol>
                                      <CCol xs="12" md="9">
                                        <CKEditor
                                            editor={ClassicEditor}
                                            config={{
                                              toolbar: [
                                                "heading",
                                                "|",
                                                "bold",
                                                "italic",
                                                "link",
                                                "bulletedList",
                                                "numberedList",
                                                "blockQuote",
                                              ],
                                            }}
                                            //data="<p>Hello from CKEditor 5!</p>"
                                            onReady={(editor) => {
                                              // You can store the "editor" and use when it is needed.
                                              // console.log("Editor is ready to use!",editor);
                                            }}
                                            onChange={(event, editor) => {
                                              const data = editor.getData();
                                              formikRDV.setFieldValue(
                                                  "observation",
                                                  data
                                              );
                                            }}
                                            onBlur={(event, editor) => {
                                              // console.log("Blur.", editor);
                                            }}
                                            onFocus={(event, editor) => {
                                              // console.log("Focus.", editor);
                                            }}
                                        />

                                        {formikRDV.errors.observation &&
                                        formikRDV.touched.observation && (
                                            <p style={{ color: "red" }}>
                                              {formikRDV.errors.observation}
                                            </p>
                                        )}

                                        <br />
                                      </CCol>
                                    </CFormGroup>
                                    <CFormGroup row>
                                      <CCol md="3">
                                        <CLabel htmlFor="date-input">
                                          
                                          <b>Date :</b>
                                        </CLabel>
                                      </CCol>
                                      <CCol xs="12" md="9">
                                        <CInput
                                            value={moment(
                                                formikRDV.values.dateRendezVous
                                            )
                                                .format("YYYY-MM-DD")
                                                .toString()}
                                            onChange={formikRDV.handleChange}
                                            type="date"
                                            name="dateRendezVous"
                                            placeholder="Date du rendez vous"
                                        />
                                        {formikRDV.errors.dateRendezVous &&
                                        formikRDV.touched.dateRendezVous && (
                                            <p style={{ color: "red" }}>
                                              {formikRDV.errors.dateRendezVous}
                                            </p>
                                        )}

                                        <br />
                                      </CCol>
                                    </CFormGroup>
                                    <CFormGroup row>
                                      <CCol md="3">
                                        <CLabel htmlFor="date-input">

                                          <b>Start Hour:</b>
                                        </CLabel>
                                      </CCol>
                                      <CCol xs="12" md="9">
                                        <CInput
                                            value={formikRDV.values.heureDebut}
                                            onChange={formikRDV.handleChange}
                                            type="time"
                                            name="heureDebut"
                                            placeholder="Visite Start time"
                                        />
                                        {formikRDV.errors.heureDebut &&
                                        formikRDV.touched.heureDebut && (
                                            <p style={{ color: "red" }}>
                                              {formikRDV.errors.heureDebut}
                                            </p>
                                        )}

                                        <br />
                                      </CCol>
                                    </CFormGroup>
                                    <CButton
                                        disabled={showLoader}
                                        className="float-right"
                                        name="btnsubmit"
                                        type="submit"
                                        color="danger"
                                    >
                                      {showLoader && <CSpinner grow size="sm" />}
                                      &nbsp;&nbsp;Ajouter un Rendez-Vous de suivi
                                    </CButton>
                                    <br></br> <br></br>
                                    {showLoader && (
                                        <CAlert color="danger" closeButton>
                                          Attendre un petit peu ...
                                        </CAlert>
                                    )}
                                  </CForm>
                              ) : (
                                  <></>
                              )}
                            </CCardBody>
                          </>

                          <>
                            <CCardHeader>
                              <CRow>
                                <CCol xs="12">&nbsp;
                                  <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                                    Liste des Rendez-Vous de Suivi Stagiaire
                                  </span>
                                </CCol>
                              </CRow>
                            </CCardHeader>
                            <CCardBody>
                              <CDataTable
                                  items={RDVSuivis}
                                  fields={fields}
                                  hover
                                  tableFilter
                                  sorter
                                  striped
                                  bordered
                                  size="sm"
                                  itemsPerPageSelect
                                  itemsPerPage={5}
                                  pagination
                                  scopedSlots={{
                                    dateRendezVous: (item) => (
                                        <td>
                                          {Moment(item.dateRendezVous).format(
                                              "DD/MM/YYYY"
                                          )}
                                        </td>
                                    ),
                                    etat: (item) => (
                                        <td>
                                          <CBadge color={getBadge(item.etat)}>
                                            {getEtat(item.etat)}
                                          </CBadge>
                                        </td>
                                    ),
                                    typeRDV: (item) => (
                                        <td>
                                          <CBadge color={getBadge2(item.typeRDV)}>
                                            {getType(item.typeRDV)}
                                          </CBadge>
                                        </td>
                                    ),
                                    modifier: (item, index) => {
                                      if( item.etat === "04" )
                                      {
                                        return ( <td className="py-2">
                                          <Link to="/UpdateRDVSuivi">
                                            <CButton
                                                className="float-right"
                                                variant="outline"
                                                color="danger"
                                                size="sm"
                                                onClick={() => {
                                                  onClickUpdateRDV2(item);
                                                }}
                                            >
                                              Modifier les informations du Rendez-Vous
                                            </CButton>
                                          </Link>
                                        </td>);
                                      } else  {
                                        return (
                                            <td className="py-2">
                                              <Link to="/UpdateRDVSuivi">
                                                <CButton
                                                    disabled={true}
                                                    className="float-right"
                                                    variant="outline"
                                                    color="danger"
                                                    size="sm"
                                                    onClick={() => {
                                                      onClickUpdateRDV2(item);
                                                    }}
                                                >
                                                  Modifier les informations du Rendez-Vous
                                                </CButton>
                                              </Link>
                                            </td>
                                        );
                                      }

                                    },
                                    show_details: (item, index) => {
                                      return (
                                          <>
                                            <td className="py-2">
                                              <CButton
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    toggleObservation(item);
                                                  }}
                                              >
                                                Consulter Détails Observations
                                              </CButton>
                                            </td>
                                          </>
                                      );
                                    },
                                  }}
                              />
                            </CCardBody>
                          </>
                        </CCard>
                      </CCol>
                    </CTabPane>
                    <CTabPane>
                      <br></br>

                      <CCol xs="12" sm="12" md="12">
                        <CCard accentColor="danger">{displayVisit()}</CCard>
                      </CCol>
                    </CTabPane>
                    <CTabPane>
                      <br></br>

                      {evaluations ? (
                          <>
                            <List className={classes.root}>
                              <div
                                  style={{
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "center",
                                  }}
                              >
                                <ListItem button>

                                  <CRow>
                                    <CCol xs="12">
                                      &nbsp;
                                      <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                                        Évaluation Module</span>
                                      &nbsp;&nbsp;
                                      <span style={{color: "#b30000", fontSize: "15px", fontWeight: "bold"}}>
                                        {evaluations[0].noteEvaluation} &nbsp;-&nbsp;
                                        {getEvalModule(parseInt(evaluations[0].noteEvaluation))}
                                      </span>
                                    </CCol>
                                  </CRow>

                                </ListItem>
                              </div>
                              {evaluations?.map((e, i) => (
                                  <>
                                    <ListItem alignItems="flex-start">
                                      <ListItemAvatar>
                                        <Avatar
                                            style={{
                                              backgroundColor: getCompetenceColor(
                                                  e.resultat
                                              ),
                                              height:"30px", width:"30px"
                                            }}
                                            alt={e.resultat}
                                            src="/static/images/avatar/1.jpg"
                                        />
                                      </ListItemAvatar>
                                      <ListItemText
                                          primary={getCompetence(
                                              e.evaluationStagePK.codeCompetence
                                          )}
                                          secondary={
                                            <>
                                              <Typography
                                                  component="span"
                                                  variant="body2"
                                                  className={classes.inline}
                                                  color="textPrimary"
                                              ></Typography>
                                            </>
                                          }
                                      />
                                    </ListItem>
                                    <Divider variant="inset" component="li" />
                                  </>
                              ))}
                            </List>
                          </>
                      ) : (
                          <CCol xs="12" sm="12" md="12">
                            <CCard accentColor="danger">
                              <CCardBody>
                                <CRow>
                                  <div className={classes.root}>
                                    <CForm
                                        id="formikid"
                                        onSubmit={formikStepper.handleSubmit}
                                    >
                                      <Stepper
                                          activeStep={activeStep}
                                          orientation="vertical"
                                      >
                                        {steps.map(
                                            ({ label, value, color }, index) => (
                                                <Step key={label}>
                                                  <StepLabel
                                                      StepIconProps={{
                                                        classes: {
                                                          root: iconClasses[index],
                                                        },
                                                      }}
                                                  >
                                                    {label}
                                                  </StepLabel>
                                                  <StepContent>
                                                    <Typography>
                                                      {getStepContent(index, value)}
                                                    </Typography>
                                                    <div
                                                        className={
                                                          classes.actionsContainer
                                                        }
                                                    >
                                                      <div>
                                                        <Button
                                                            size="sm"
                                                            disabled={activeStep === 0}
                                                            onClick={handleBack}
                                                            className={classes.button}
                                                        >
                                                          Retour
                                                        </Button>
                                                        <Button
                                                            size="sm"
                                                            variant="contained"
                                                            onClick={handleNext}
                                                            className={classes.button}
                                                        >
                                                          {activeStep ===
                                                          steps.length - 1
                                                              ? "Terminer"
                                                              : "Suivant"}
                                                        </Button>
                                                      </div>
                                                    </div>
                                                  </StepContent>
                                                </Step>
                                            )
                                        )}
                                      </Stepper>
                                    </CForm>
                                    {activeStep === steps.length && (
                                        <Paper
                                            square
                                            elevation={0}
                                            className={classes.resetContainer}
                                        >
                                          <Typography>
                                            Toutes les étapes sont terminées.
                                            <br/>>
                                            Si vous
                                            voulez changer vos choix, vous pouvez
                                            cliquer sur <b>Réinitialiser</b>
                                            <br></br>
                                            <br></br>
                                            <b>
                                              Remarque: Aprés avoir confirmer vos
                                              choix, vous ne pouvez pas le modifier.
                                            </b>
                                            <br></br>
                                            {showStepperError ? (
                                                <CAlert color="danger">
                                                  Veuillez compléter vos choix, s'il
                                                  vous plait.
                                                </CAlert>
                                            ) : (
                                                <></>
                                            )}
                                          </Typography>
                                          <Button
                                              onClick={handleReset}
                                              className={classes.button2}
                                          >
                                            Réinitialiser
                                          </Button>
                                          <Button
                                              className={classes.button}
                                              type="submit"
                                              form="formikid"
                                          >
                                            Confirmer votre évaluation
                                          </Button>
                                        </Paper>
                                    )}
                                  </div>
                                </CRow>
                              </CCardBody>
                            </CCard>
                          </CCol>
                      )}
                    </CTabPane>
                  </CTabContent>
                </CTabs>
              </CCardBody>
            </CCard>
          </CCol>

        </div>
        <CModal
            show={showObserv}
            onClose={() => setshowObserv(!showObserv)}
            color="dark"
            size="lg"
        >
          <CModalHeader closeButton>
            <CModalTitle>
              <span style={{fontSize:"13px"}}>
              Détails Observation d'un Rendez-Vous de Suivi Pédagogique
                </span>
            </CModalTitle>
          </CModalHeader>
          <CCardBody>
            <div
                style={{
                  wordWrap: "break-word",
                }}
                className="editor"
                dangerouslySetInnerHTML={{
                  __html: Item.observation,
                }}
            ></div>
          </CCardBody>
          <CModalFooter>
            {Item.etat ==="04" ?  <Link to="/UpdateObservState">
              <CButton
                  //variant="outline"
                  color="dark"
                  // size="sm"
                  className="float-left;"
                  onClick={() => {
                    onClickUpdateRDV(Item);
                  }}
              >
                Changer votre <b>observation </b> et
                <b> l'état du Rendez-Vous </b>
              </CButton>
            </Link>: <></>
            }

            <CButton color="secondary" onClick={() => setshowObserv(!showObserv)}>
              EXIT
            </CButton>
          </CModalFooter>
        </CModal>
      </>
  );
};

export default FichePFEDetails;
