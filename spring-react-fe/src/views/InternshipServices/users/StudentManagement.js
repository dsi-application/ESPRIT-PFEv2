
import React, {useState, useEffect} from "react";
import axios from "axios";
import {useDispatch, useSelector} from "react-redux";
import "moment/locale/fr";
import MUIDataTable from "mui-datatables";
import "../../css/style.css";
import Spinner from "react-bootstrap/Spinner";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CForm,
  CFormGroup,
  CInput,
  CLabel,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CTooltip
} from "@coreui/react";
import PhoneInput from "react-phone-input-2";
import "react-phone-input-2/lib/style.css";
import {
  addStudentConfig,
  deleteStudentConfig, fetchClearStudentConfigs, fetchStudentConfigsByYear,
  selectSelectedStudentConfig,
  selectStudentConfig,
  selectStudentConfigs,
  setErrors,
  updateStudentConfig,
} from "../../../redux/slices/StudentConfigSlice";
import * as Yup from "yup";
import {useFormik} from "formik";
import CIcon from "@coreui/icons-react";
import {queryApi} from "../../../utils/queryApi";
import {isEmail, isInt} from "validator";
import {createMuiTheme, MuiThemeProvider} from "@material-ui/core";
import {
  fetchActiveStudentTimelineStep,
  fetchConventionDetails,
  fetchDepotReportsDetails,
  fetchFichePFEDetails,
  fetchTimelineSteps, fetchVerifyAffectPEtoStudent
} from "../../../redux/slices/MyStudentSlice";
import AuthService from "../../services/auth.service";
import store from "../../../store";
import {fetchConventionsHistoric, fetchDateDepotFichePFE} from "../../../redux/slices/MyStudentTBSSlice";
import {fetchEtudiantbySession} from "../../../redux/slices/ListingSlice";
const API_URL_PC = process.env.REACT_APP_API_URL_PC;

const animatedComponents = makeAnimated();

const validationSchema = Yup.object().shape({
  telStudent: Yup.string()
                 .min(5, "Numéro Tél. must has for minimum 5 characters !")
                 .max(25, "Numéro Tél. must has for maximum 25 characters !")
                 .required("* Numéro Tél. is required !"),
  mailStudent: Yup.string()
                  .email('It is an Invalid Email Form')
                  .min(5, "E-Mail must has for minimum 5 characters !")
                  .max(25, "E-Mail must has for maximum 25 characters !")
                  .required('* E-Mail is required !')
});
const StudentManagement = () => {
  const [showError2, setshowError2] = useState(false);

  const [showError3, setshowError3] = useState(false);
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [danger, setDanger] = useState(false);
  const [info, setInfo] = useState(false);
  const [initPwdJWT, setInitPwdJWT] = useState(false);
  const [successInitPwdJWT, setSuccessInitPwdJWT] = useState(false);
  const [loadInitPwdJWT, setLoadInitPwdJWT] = useState(false);
  const [allowModifContactCoord, setAllowModifContactCoord] = useState("INIT");
  const [pika, setPika] = useState("INIT");
  const [successModifContactCoord, setSuccessModifContactCoord] = useState(false);
  const [loadStudentBySelectedYear, setLoadStudentBySelectedYear] = useState(false);
  const [selectedYear, setSelectedYear] = useState("2022");
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [studentConfigs, err] = useSelector(selectStudentConfigs) || [];
  const [showForm, setshowForm] = useState(false);
  const SelectedStudentConfig = useSelector(selectSelectedStudentConfig);
  const [error, setError] = useState({visible: false, message: ""});
  const [allSessionsLabel, setAllSessionsLabel] = useState([]);

  const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();

  const toggleFormRDV = () => {
    setshowForm(showForm ? false : true);
  };
  const toggleModifModal = (s) => {
    dispatch(selectStudentConfig(s));
    setInfo(!info);
  };

  const toggleInitJWTPasswordModal = (s) => {
    dispatch(selectStudentConfig(s));
    setInitPwdJWT(!initPwdJWT);
  };


  const toggleDeleteModal = (s) => {
    dispatch(selectStudentConfig(s));
    setDanger(!danger);
  };
  const deleteStudentConfigevent = async () => {
    const [err] = await queryApi(
        "config/studentConfigs/" + SelectedStudentConfig.idStudentConfig,
        {},
        "DELETE"
    );
    if (err) {
      dispatch(setErrors(err));
    } else {
      dispatch(deleteStudentConfig(SelectedStudentConfig));
      setDanger(!danger);
    }
  };

  const initializeStudentPwdForPFEPlatform = async () => {
    // console.log('-------------------> 20.11 1');
    setLoadInitPwdJWT(!loadInitPwdJWT);
    const [err] = await queryApi(
        "config/studentConfigs/initializePwdPFE/" + SelectedStudentConfig.idStudent + "/" + currentResponsableServiceStage.id,
        "GET"
    );
    if (err) {
      // console.log('-------------------> 20.11 2');
      dispatch(setErrors(err));
    } else {
      // console.log('-------------------> 20.11 3');
      setLoadInitPwdJWT(false);
      setInitPwdJWT(false);
      setSuccessInitPwdJWT(true);
    }
  };

  const onChangeStudentsByYear = async (selectedYearObject) => {
    // console.log('-----PIKATCHO---> 20.11 1: ');
    // studentConfigs.clear();
    setSelectedYear(selectedYearObject.value);
    setLoadStudentBySelectedYear(!loadStudentBySelectedYear);
    // console.log('-----PIKATCHO---> 20.11 before: ');
   // store.dispatch(fetchStudentConfigsByYear(selectedYearObject.value));
    // console.log('-----PIKATCHO---> 20.11 after: ');
    setLoadStudentBySelectedYear(!loadStudentBySelectedYear);


    dispatch(fetchClearStudentConfigs());
    dispatch(fetchStudentConfigsByYear(selectedYearObject.value));

  };
  const options = {
    rowsPerPage: 10,
    filter: true,
    filterType: "dropdown",
    responsive,
    tableBodyHeight,
    tableBodyMaxHeight,
    download: false,
    fixedSelectColumn: true,
    selectableRows: "none",
    selectableRowsHeader: false,
    enableNestedDataAccess: ".",
    textLabels: {
      body: {
        noMatch: "Désolé, aucune donnée correspondante n'a été trouvée",
        toolTip: "Sort",
        columnHeaderTooltip: (column) => `Sort for ${column.label}`,
      },
      pagination: {
        next: "Page suivante",
        previous: "Page précédente",
        rowsPerPage: "Lignes par page:",
        displayRows: "de",
      },
      toolbar: {
        search: "Chercher",
        downloadCsv: "Download CSV",
        print: "Télécharger",
        viewColumns: "Afficher les colonnes",
        filterTable: "Tableau des filtres",
      },
      filter: {
        all: "Tout",
        title: "FILTRES",
        reset: "RÉINITIALISER",
      },
      viewColumns: {
        title: "Afficher les colonnes",
        titleAria: "Afficher / masquer les colonnes du tableau",
      },
      selectedRows: {
        text: "row(s) selected",
        delete: "Delete",
        deleteAria: "Delete Selected Rows",
      },
    },
  };
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "1px 1px 1px 10px",
        },
      },
    },
  });
  const columns = [
    {
      name: "optionStudent",
      label: "Option",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "classeStudent",
      label: "Classe",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "idStudent",
      label: "Identifiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "fullNameStudent",
      label: "Nom & Prénom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "mailStudent",
      label: "E-Mail",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "telStudent",
      label: "Téléphone",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "",
      label: "",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
              <>
                <CTooltip content="Modifier Contact" placement="top">
                  <CButton  variant="outline"
                            color="primary"
                            size="sm"
                            onClick={() => toggleModifModal(studentConfigs[dataIndex])}>
                    <CIcon name="cil-pencil"></CIcon>
                  </CButton>
                </CTooltip>
                &nbsp;
                <CTooltip content="Initialiser Mot de Passe" placement="top">
                      <CButton  variant="outline"
                              color="success"
                              size="sm"
                              onClick={() => toggleInitJWTPasswordModal(studentConfigs[dataIndex])}>
                      <CIcon name="cil-paper-plane"></CIcon>
                    </CButton>
                </CTooltip>
              </>
          );
        },
      },
    },
  ];
  const formik = useFormik({
    initialValues: {
      libelleStudentConfig: "",
      dateDebut: "",
      dateFin: "",
      fichePfes: [],
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      const [res, err] = await queryApi(
          "config/studentConfigs",
          values,
          "POST",
          false
      );
      if (err) {
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        if (values.dateFin > values.dateDebut) {
          dispatch(addStudentConfig(res));
          formik.resetForm();
          setshowError2(false);
          setshowForm(!showForm);

        } else {
          setshowError2(true)
        }

      }
    },
  });
  const formikModif = useFormik({
    enableReinitialize: true,
    initialValues: {
      idStudentConfig: SelectedStudentConfig.idStudent,
      telStudent: SelectedStudentConfig.telStudent,
      idStudent: SelectedStudentConfig.idStudent,
      fullNameStudent: SelectedStudentConfig.fullNameStudent,
      mailStudent: SelectedStudentConfig.mailStudent,
      dateFin: SelectedStudentConfig.dateFin,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setAllowModifContactCoord("ALLOW")
        const [res, err] = await queryApi(
            "config/studentConfigs/updateStudentContact/" + SelectedStudentConfig.idStudent + "/" + currentResponsableServiceStage.id + "/" + selectedYear,
            values,
            "PUT",
            false
        );
        if (err) {
          // console.log('--------- SARIA 4 ERR');
          setError({
            visible: true,
            message: JSON.stringify(err.errors, null, 2),
          });
        } else {
          // console.log('--------- SARIA 5');
          dispatch(updateStudentConfig(res));
          // console.log('--------- SARIA 6');
          setAllowModifContactCoord("INIT");
          // console.log('--------- SARIA 7');
          setSuccessModifContactCoord(!successModifContactCoord);
          setshowError3(false);
          setInfo(!info);
        }
    },
  });

  const loadDataOnlyOnce = () => {
    axios.get(API_URL_PC + "findAllSessions")
        .then((response) => {
          let allLabels = response.data;
          let allYears = [];
          for (let cls of allLabels) {
            let lol = {value: cls, label: cls,color: "#00B8D9"}
            allYears.push(lol);
            setAllSessionsLabel(allYears);
          }
        });
  };

  useEffect(() => {
    // console.log('----------YEARS---------> 20.11 C: ', allSessionsLabel);
    loadDataOnlyOnce(); // this will fire only on first render
  }, []);

  return (
      <>
        <CCard>
          <CRow>
            <CCol md="4"/>
            <CCol md="4">
              <br/>
              <p className="greyMarkForSelectComp">Merci de choisir une Année pour consulter la résultante</p>
              <Select  placeholder="Please Select an Academic Year"
                       defaultValue={{value: '2022', label: '2022', color: "#00B8D9"}}
                       value={allSessionsLabel.value}
                       components={animatedComponents}
                       options={allSessionsLabel}
                       onChange={onChangeStudentsByYear}/>

            </CCol>
            <CCol md="4"/>
          </CRow>
          <br/>

          <CCardBody data-tut="reactour__1">
            {studentConfigs ? (
                <>
                  <CRow>
                    <CCol xs="12">
                      <br/>
                      <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Étudiants des Classes Terminaux</span>
                      <br/>
                      <span className="clignoteGreyNew">{selectedYear} - {Number(selectedYear)+1}</span>
                      <br/>
                      <span className="greyMarkCourrier">
                        {studentConfigs.length}
                      </span>&nbsp;
                      <span className="greyMarkCourrierSmalLabel">étudiants</span>

                    </CCol>
                  </CRow>
                  <br/>
                  <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                        title={""}
                        data={studentConfigs}
                        columns={columns}
                        options={options}
                    /></MuiThemeProvider>
                </>
            ) : (
              <center>
                  <br/><br/><br/><br/><br/>
                  <span className="waitIcon"/>
                  <br/><br/><br/><br/><br/>
              </center>
            )}
          </CCardBody>
        </CCard>

        <CModal size="md" show={info} onClose={() => setInfo(!info)} color="primary">
          <CModalHeader closeButton>
            <CModalTitle><span style={{fontSize: "14px"}}>Mettre À Jour Contact pour l'Étudiant(e) : {formikModif.values.fullNameStudent}</span></CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CForm onSubmit={formikModif.handleSubmit}>
              <CFormGroup> {error.visible && <p>{error.message}</p>}</CFormGroup>
              <CFormGroup row>
                <CCol md="3">
                  <CLabel>
                    <b>Numéro Tél.:</b>
                  </CLabel>
                </CCol>
                <CCol xs="12" md="9">
                  <CInput
                      value={formikModif.values.telStudent}
                      onChange={formikModif.handleChange}
                      type="input"
                      name="telStudent"
                      placeholder="Téléphone Étudiant(e)"
                  />

                  {formikModif.errors.telStudent &&
                  formikModif.touched.telStudent && (
                      <p style={{color: "red"}}>
                        {formikModif.errors.telStudent}
                      </p>
                  )}

                  <br/>
                </CCol>
              </CFormGroup>
              <CFormGroup row>
                <CCol md="3">
                  <CLabel htmlFor="date-input">
                    <b>Adresse E-Mail :</b>
                  </CLabel>
                </CCol>
                <CCol>
                <CInput
                    value={formikModif.values.mailStudent}
                    onChange={formikModif.handleChange}
                    type="input"
                    name="mailStudent"
                    placeholder="Mail Étudiant(e)"
                />
                {
                  formikModif.errors.mailStudent &&
                  formikModif.touched.mailStudent && (
                      <p style={{color: "red"}}>
                        {formikModif.errors.mailStudent}
                      </p>
                  )}
                <br/>
              </CCol>
              </CFormGroup>
              <CRow>
                <CCol md="12">
                  <div className="float-right">
                    {allowModifContactCoord === "ALLOW" ? (
                        <>
                          <Spinner animation="grow" variant="primary"/>
                        </>
                    ) : (
                        <>
                          <CButton
                              name="btnsubmit"
                              type="submit"
                              color="primary"
                          >
                            Mettre À Jour
                          </CButton>
                        </>
                    )}

                    &nbsp;&nbsp;
                    <CButton
                        color="secondary"
                        onClick={() => setInfo(!info)}
                    >
                      EXIT
                    </CButton>
                  </div>
                </CCol>
              </CRow>
            </CForm>
          </CModalBody>
        </CModal>
        <CModal size="md" show={initPwdJWT} onClose={() => setInitPwdJWT(!initPwdJWT)} color="success">
          <CModalHeader closeButton>
            <CModalTitle><span style={{fontSize: "14px"}}>Initialiser Mot de Passe Plateforme PFE pour l'Étudiant(e) : <br/>
              {formikModif.values.fullNameStudent}</span></CModalTitle>
          </CModalHeader>
          <CModalBody>
            <br/><br/>
            <center>
                Êtes-vous sûrs(es) de vouloir
              <br/>initialiser le Mot de Passe propre à la Plateforme PFE pour l'Étudiant(e) <span className="greenAlert">{formikModif.values.fullNameStudent}</span> ?.
            </center>
            <br/><br/><br/>
            <CRow>
              <CCol md="12">
                <div className="float-right">
                  {loadInitPwdJWT === true ? (
                      <>
                        <Spinner animation="grow" variant="success"/>
                      </>
                  ) : (
                      <>
                        <CButton color="success"
                                 onClick={() => initializeStudentPwdForPFEPlatform()}>
                          YES, I Confirm
                        </CButton>
                      </>
                  )}

                  &nbsp;&nbsp;
                  <CButton color="secondary"
                           onClick={() => setInitPwdJWT(!initPwdJWT)}>
                    EXIT
                  </CButton>
                </div>
              </CCol>
            </CRow>
          </CModalBody>
        </CModal>
        <CModal size="md" show={successInitPwdJWT} onClose={() => setSuccessInitPwdJWT(!successInitPwdJWT)} color="success">
          <CModalHeader closeButton>
            <CModalTitle><span style={{fontSize: "14px"}}>Information</span></CModalTitle>
          </CModalHeader>
          <CModalBody>
            <br/><br/>
            <center>
             Le Mot de Passe propre à l'Étudiant(e) <span className="greenAlert">{formikModif.values.fullNameStudent}</span> a été bien initialisée.<br/>
             Un mail de Notification a été envoyé.
            </center>
            <br/><br/><br/>
            <CRow>
              <CCol md="12">
                <div className="float-right">
                  <CButton color="secondary"
                           onClick={() => setSuccessInitPwdJWT(!successInitPwdJWT)}>
                    Ok, thanks
                  </CButton>
                </div>
              </CCol>
            </CRow>
          </CModalBody>
        </CModal>
        <CModal size="md" show={successModifContactCoord} onClose={() => setSuccessModifContactCoord(!successModifContactCoord)} color="success">
          <CModalHeader closeButton>
            <CModalTitle><span style={{fontSize: "14px"}}>Information</span></CModalTitle>
          </CModalHeader>
          <CModalBody>
            <br/><br/>
            <center>
              Les Coordonnées de Contacts propre à l'Étudiant(e) <span className="greenAlert">{formikModif.values.fullNameStudent}</span> ont été mises à jour avec succès.<br/>
              Un mail de Notification a été envoyé.
            </center>
            <br/><br/><br/>
            <CRow>
              <CCol md="12">
                <div className="float-right">
                  <CButton color="secondary"
                           onClick={() => setSuccessModifContactCoord(!successModifContactCoord)}>
                    Ok, thanks
                  </CButton>
                </div>
              </CCol>
            </CRow>
          </CModalBody>
        </CModal>
        <CModal show={danger} onClose={() => setDanger(!danger)} color="danger">
          <CModalHeader closeButton>
            <CModalTitle><span style={{fontSize: "14px"}}>Confirmation
            </span></CModalTitle>
          </CModalHeader>
          <CModalBody>
            <center>
              <br/>
              Voulez-vous vraiment supprimer la session
              <br/>
              <span className="redMark">{SelectedStudentConfig.libelleStudentConfig}</span> ?.
              <br/><br/>
            </center>
          </CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => deleteStudentConfigevent()}>
              Oui
            </CButton>
            <CButton color="secondary" onClick={() => setDanger(!danger)}>
              Non
            </CButton>
          </CModalFooter>
        </CModal>
      </>
  );
};

export default StudentManagement;
