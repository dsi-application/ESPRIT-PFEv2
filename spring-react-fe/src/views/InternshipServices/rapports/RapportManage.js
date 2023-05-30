import {
  CAlert,
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol, CForm, CFormGroup,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow, CSelect,
  CSpinner, CTextarea, CTooltip
} from "@coreui/react";
import axios from "axios";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import GetApp from "@material-ui/icons/GetApp";
import Search from "@material-ui/icons/Search";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {
  getEtudiant,
  selectetudiant,
} from "../../../redux/slices/FichePFESlice";
import {queryApi} from "../../../utils/queryApi";
import {useFormik} from "formik";
import Spinner from "react-bootstrap/Spinner";

import TheSidebar from "../../../containers/TheSidebar";
import {
  selectStudentsToStat,
  selectStudentsDoneStat,
  fetchValidatedReports,
  fetchUploadedReports, fetchStudentToSTNStat, fetchRefusedReports
} from "../../../redux/slices/DepotSlice";

import {createMuiTheme, MuiThemeProvider} from "@material-ui/core";

import {
  selectNbrUploadedReports,
  selectNbrValidatedReports,
  selectNbrRefusedReports
} from "../../../redux/slices/DepotSlice";

import * as Yup from "yup";
import {setErrors} from "../../../redux/slices/ChefCoordConfigSlice";
import AuthService from "../../services/auth.service";
import moment from "moment";
import CIcon from "@coreui/icons-react";

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
const API_URL_RSS = process.env.REACT_APP_API_URL_RSS;

const validationSchema = Yup.object().shape({
  yearLabel: Yup.string().required("* Année est un Champ obligatoire !."),
});

const validationSchemaRef = Yup.object().shape({
  cancelMotif: Yup.string().max(300, "Please don't depass 300 caractères  !").required("* Motif Refus is obligatory !"),
});

const RapportManage = () => {
  const dispatch = useDispatch();
  const Etu = useSelector(selectetudiant);
  const [notYetTreatedDepots, setNotYetTreatedDepots] = useState([]);
  //const [notYetTreatedDepots, setNotYetTreatedDepots] = useSelector(selectFichebydepVal);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("10");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [info, setInfo] = useState(false);
  const [danger, setDanger] = useState(false);
  const [showLoader, setShowLoader] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState({visible: false, message: ""});
  const [date, setdate] = useState("");
  const [id, setid] = useState("");
  const [confirmNotif, setConfirmNotif] = useState(false);
  const [successNotif, setSuccessNotif] = useState(false);
  const [loadSpinnerNotif, setLoadSpinnerNotif] = useState(false);
  const [selectedYear, setSelectedYear] = useState(false);
  /*
  const Fichebydepstatus = useSelector(
    (state) => state.persistedReducer.conventions.Fichebydepstatus
  );
  */
  const [studentsToStat, errsts] = useSelector(selectStudentsToStat);
  const [studentsDoneStat, errsds] = useSelector(selectStudentsDoneStat);

  const [nbUploadedReports, errnur] = useSelector(selectNbrUploadedReports);
  const [nbValidatedReports, errnvr] = useSelector(selectNbrValidatedReports);
  const [nbIncompletedReports, errnir] = useSelector(selectNbrRefusedReports);
  const [allOpts, setAllOpts] = useState([]);

  useEffect(() => {
    const response1 = axios
      .get(API_URL_RSS + `allOptionsForActivatedYears/` + currentResponsableServiceStage.id)
      .then((res) => {
        let result = res.data;
        console.log('--------------> HI-HELLO: ', res.data);
        setAllOpts(result);
      })
  }, [])

  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "0px 0px 0px 10px",
        },
      },
    },
  });

  const etatDepot = (e) => {
    if (e === "DEPOSE") {
      return (
        <CBadge color="info" className="float-center">
          {e}
        </CBadge>
      );
    }
    if (e === "ACCEPTE") {
      return (
        <CBadge color="success" className="float-center">
          {e}
        </CBadge>
      );
    }
    if (e === "REFUSE") {
      return (
        <CBadge color="danger" className="float-center">
          {e}
        </CBadge>
      );
    } else {
      return <p>Cet étudiant n'a pas encore déposé son rapport</p>;
    }
  };

  const renderbtn = (e) => {
    console.log("===================> PIKA 0: " , e)
    if (e.etatDepot === "DEPOSE") {
      return (
        <CRow>
          <CCol xs="12" sm="4" md="6">
            <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
              <CButton  variant="outline"
                        color="success"
                        size="sm"
                        className="float-left;"
                        onClick={() => handleValideModal(e.idEt, e.dateDepotFiche)}>

                <CTooltip content="Valider Dépôt" placement="top">
                  <CIcon name="cil-check"/>
                </CTooltip>
              </CButton>
              &nbsp;&nbsp;&nbsp;
              <CButton  variant="outline"
                        color="danger"
                        size="sm"
                        className="float-left;"
                        onClick={() => handleRefuseModal(e.idEt, e.dateDepotFiche)}>
                <CTooltip content="Dépôt non complet" placement="top">
                  <CIcon name="cil-x"/>
                </CTooltip>
              </CButton>
            </div>
          </CCol>
        </CRow>
      );
    } else {
      return (
        <></>
      );
    }
  };

  const columns = [
    {
      name: "idEt",
      label: "Identifiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "fullName",
      label: "Nom & Prénom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "classeEt",
      label: "Classe",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "optionEt",
      label: "Option",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "dateDepotReports",
      label: "Date Dépôt",
      options: {
        filter: true,
        sort: true,
        customBodyRender: value =>
          moment(new Date(value)).format("DD-MM-YYYY HH:mm")
      }
    },
    {
      name: "trainingDuration",
      label: "Durée Stage",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "",
      label: "Rapport | Plagiat | Attestation Stage",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          // console.log(Fiches[dataIndex].pathRapport);
          return (
            <>
              {notYetTreatedDepots[dataIndex].etatDepot ? (
                <>
                  <Tooltip title=" Télécharger Rapport">
                    <IconButton
                      onClick={() => {
                        Download(notYetTreatedDepots[dataIndex].pathRapport);
                      }}
                    >
                      <GetApp style={{ color: "#DB4437" }} />
                    </IconButton>
                  </Tooltip>
                  &nbsp;
                  <Tooltip title=" Télécharger Plagiat">
                    <IconButton
                      onClick={() => {
                        Download(notYetTreatedDepots[dataIndex].pathPlagiat);
                      }}
                    >
                      <GetApp style={{ color: "#DB4437" }} />
                    </IconButton>
                  </Tooltip>
                  &nbsp;
                  <Tooltip title=" Télécharger Attestation Stage">
                    <IconButton
                      onClick={() => {
                        Download(notYetTreatedDepots[dataIndex].pathAttestationStage);
                      }}
                    >
                      <GetApp style={{ color: "#DB4437" }} />
                    </IconButton>
                  </Tooltip>
                  &nbsp;
                  {(
                    notYetTreatedDepots[dataIndex].pathDossierTechnique !== null &&
                    <Tooltip title=" Télécharger Dossier Technique">
                      <IconButton
                        onClick={() => {
                          Download(notYetTreatedDepots[dataIndex].pathDossierTechnique);
                        }}
                      >
                        <GetApp style={{ color: "#DB4437" }} />
                      </IconButton>
                    </Tooltip>
                  )}
                </>
              ) : (
                <> </>
              )}
            </>
          );
        },
      },
    },
    {
      name: "",
      label: "Action",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return renderbtn(notYetTreatedDepots[dataIndex])
        },
      },
    },
    {
      name: "",
      label: "Détails",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
            <div className="float-center">
              <Tooltip title="Voir détails Etudiant">
                <IconButton
                  onClick={() => onClickEtudiant(notYetTreatedDepots[dataIndex].idEt)}
                >
                  <Search style={{ color: "#000000" }} />
                </IconButton>
              </Tooltip>
            </div>
          );
        },
      },
    },

  ];

  const onClickEtudiant = (id) => {
    setInfo(!info);
    dispatch(getEtudiant(id));
  };
  const options = {
    rowsPerPage: 5,
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

  const handleValideModal = (id, date) => {
    console.log("===================> PIKA 1: " + date)
    setdate(date);
    setid(id);
    setSuccess(true);
  };
  const handleRefuseModal = (id, date) => {
    setdate(date);
    setid(id);
    setDanger(true);
  };

  const formikRef = useFormik({
    initialValues: {
      cancelMotif: ""
    },
    validationSchema: validationSchemaRef,
    onSubmit: async (values) => {
      setShowLoader(true);

      const [res, err] = await queryApi(
        "serviceStage/updateDepotToREFUSE?idET=" +
        id +
        "&dateDepotFiche=" +
        date +
        "&cancelMotif=" +
        encodeURIComponent(values.cancelMotif),
        {},
        "PUT",
        false
      );
      // console.log("resupdate", res);
      if (err) {
        setShowLoader(false);
        console.log('-----------------1-> 0306')
        setError({
          visible: true,
          message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        console.log("****dddd***** sars3005 1", selectedYear);

        const [res, err] = await queryApi("respServStg/loadNotYetTreatedDepots?idRSS=" + currentResponsableServiceStage.id +
          "&yearLabel=" + selectedYear,
          {},
          "GET",
          false
        );

        setNotYetTreatedDepots([]);
        setNotYetTreatedDepots(res);

        if (err) {
          setShowLoader(false);
          // console.log('-----------------1-> 0306')
          setError({
            visible: true, message: JSON.stringify(err.errors, null, 2),
          });
        } else {
          // console.log('-----------------2-> 0508')
          console.log("****dddd***** sars0508 2", res);
          dispatch(fetchUploadedReports());
          dispatch(fetchValidatedReports());
          dispatch(fetchRefusedReports());
          dispatch(fetchStudentToSTNStat());
          setDanger(false);
          setShowLoader(false);
        }
        console.log("-----------------> AZERTYYY 1: " , res)
      }
    },
  });

  const handleValidate = async (id, date) => {

    console.log("===================> AZERTYYY 0: " + date)
    setShowLoader(true);
    const [res, err] = await queryApi(
      "serviceStage/updateDepotToVALIDE?idET=" + id + "&dateDepotFiche=" + date,
      {},
      "PUT",
      false
    );
    // console.log("resupdate", res);
    if (err) {
      setShowLoader(false);
      setError({
        visible: true,
        message: JSON.stringify(err.errors, null, 2),
      });
    } else {

      console.log("****dddd***** sars3005 1", selectedYear);

      const [res, err] = await queryApi("respServStg/loadNotYetTreatedDepots?idRSS=" + currentResponsableServiceStage.id +
        "&yearLabel=" + selectedYear,
        {},
        "GET",
        false
      );

      setNotYetTreatedDepots([]);
      setNotYetTreatedDepots(res);

      if (err) {
        setShowLoader(false);
        // console.log('-----------------1-> 0306')
        setError({
          visible: true, message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        // console.log('-----------------2-> 0508')
        console.log("****dddd***** sars0508 2", res);
        dispatch(fetchUploadedReports());
        dispatch(fetchValidatedReports());
        dispatch(fetchRefusedReports());
        dispatch(fetchStudentToSTNStat());
        setDanger(false);
        setSuccess(false);
        setShowLoader(false);
      }
      console.log("-----------------> AZERTYYY 1: " , res)

    }
  };

  const Download = (p) => {

    console.log('------------AZERTY------> LOL 0: ' + p);

    // console.log('--------------------------AZERTY-LOL: ' + encodeURIComponent(p));

    // console.log('-----------AZERTY-------> LOL 1: ' + p);

    let encodedURL = encodeURIComponent(encodeURIComponent(p));
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
      .then((response) => {

        const file = new Blob([response.data], {type: "application/pdf"});
        let url = window.URL.createObjectURL(file);

        // let a = document.createElement("a");
        // a.href = url;
        // a.download = p.substring(p.lastIndexOf("/") + 1);
        if (p.includes('.pdf')) {
          window.open(url);
        }
        // a.click();
      });
  };

  const downloadGrilleAE = (p) => {
    console.log('------------AZERTY------> LOL 0: ' + p);
    axios.get(`${process.env.REACT_APP_API_URL_AE}` + "downloadGrilleAE/" + p + "/" + selectedYear, {responseType: "blob"})
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

  const askForNotif = (p) => {
    setConfirmNotif(!confirmNotif);
    sessionStorage.setItem("idEt", p);
  };

  const notifyAEToFillGrille = async () => {
    let studentId = sessionStorage.getItem("idEt")
    console.log('----------HIHIHI---------> 20.11 1: ', studentId);
    setLoadSpinnerNotif(!loadSpinnerNotif);
    const [err] = await queryApi(
      "academicEncadrant/notifyAEToFillGrille/" + studentId,
      "GET"
    );
    if (err) {
      // console.log('-------------------> 20.11 2');
      dispatch(setErrors(err));
    } else {
      // console.log('-------------------> 20.11 3');
      setLoadSpinnerNotif(!loadSpinnerNotif);
      setConfirmNotif(!confirmNotif);
      setSuccessNotif(!successNotif);
    }
  };

  const formik = useFormik({
    initialValues: {
      yearLabel: "",
      allYears: ["2022", "2021"]
    }, validationSchema: validationSchema, onSubmit: async (values) => {

      // console.log('------------------> yearLabel: ' + values.yearLabel);

      setSelectedYear(values.yearLabel)
      setNotYetTreatedDepots([]);

      setShowLoader(true);
      // console.log('-----------------1-> 0908 id RSS: ' + currentResponsableServiceStage.id);idRSS,yearLabel

      const [res, err] = await queryApi("respServStg/loadNotYetTreatedDepots?idRSS=" + currentResponsableServiceStage.id +
        "&yearLabel=" + values.yearLabel,
        {},
        "GET",
        false
      );

      console.log("****dddd***** sars0508 1", res);
      setNotYetTreatedDepots([]);
      setNotYetTreatedDepots(res);

      if (err) {
        setShowLoader(false);
        // console.log('-----------------1-> 0306')
        setError({
          visible: true, message: JSON.stringify(err.errors, null, 2),
        });
      } else {
        // console.log('-----------------2-> 0508')
        console.log("****dddd***** sars0508 2", res);
        dispatch(fetchUploadedReports());
        dispatch(fetchValidatedReports());
        dispatch(fetchRefusedReports());
        dispatch(fetchStudentToSTNStat());
        setDanger(false);
        setShowLoader(false);
      }
    },
  });

  return (
    <>

      <TheSidebar data1={studentsToStat} data2={studentsDoneStat}
                  dataUR={nbUploadedReports} dataVR={nbValidatedReports} dataIR={nbIncompletedReports}/>

      <CRow>
        <CCol>
          <CCard>
            <CRow>
              <CCol md="3"/>
              <CCol md="6">
                <CForm onSubmit={formik.handleSubmit}>
                  <CFormGroup>
                    {error.visible && <p>{error.message}</p>}
                  </CFormGroup>
                  <center>
                    <span className="greyLabel_Dark_Cr_12">Merci de choisir une Promotion</span>
                  </center>
                  <br/>
                  <CFormGroup row>
                    <CCol md="1"/>
                    <CCol md="10">
                      <CSelect value={formik.values.yearLabel}
                               onChange={formik.handleChange}
                        //onSelect={gotAllOptionsByPromotion(formik.values.yearLabel)}
                               onBlur={formik.handleBlur}
                               custom
                               size="sm"
                               name="yearLabel">
                        <option style={{display: "none"}}>
                          ---- Choisir une Promotion ----
                        </option>
                        {formik.values.allYears?.map((e, i) => (<option value={e} key={i}>
                          {e}
                        </option>))}
                      </CSelect>
                      {formik.errors.yearLabel && formik.touched.yearLabel &&
                        <p style={{color: "red"}}>{formik.errors.yearLabel}</p>}
                      <br/>
                    </CCol>
                    <CCol md="1"/>
                  </CFormGroup>
                  <center>
                    <CButton color="danger" type="submit">
                      {showLoader && <CSpinner grow size="sm"/>} &nbsp; Confirmer
                    </CButton>
                  </center>
                </CForm>
              </CCol>
              <CCol md="3"/>
            </CRow>

            <br/>

            <CCardBody>
              {notYetTreatedDepots.length === 0 ? (
                <center>
                  <hr/>
                  <br/><br/>
                  <span className="greyLabel_Dark_Cr_13">Sorry, no Data is available.</span>
                  <br/><br/><br/><br/>
                </center>
              ) : (
                <MUIDataTable
                  data={notYetTreatedDepots}
                  columns={columns}
                  options={{
                    selectableRows: 'none' // <===== will turn off checkboxes in rows
                  }}
                />
              )}
            </CCardBody>

          </CCard>
        </CCol>
      </CRow>

      <CModal size="md" show={info} onClose={() => setInfo(!info)} color="dark">
        <CModalHeader closeButton>
          <CModalTitle>
            <span style={{fontSize: "14px"}}>
            Détails Étudiant(e)
            </span>
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CCol xs="12" sm="12" md="12">
            <CRow>
              <CCol md="5">
                <b> Identifiant : </b>
              </CCol>
              <CCol md="7">
                {Etu.idEt}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="5">
                <b> Nom & Prénom : </b>
              </CCol>
              <CCol md="7">
                {Etu.nomet} &nbsp; {Etu.prenomet}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="5">
                <b> Numéro Téléphone : </b>
              </CCol>
              <CCol md="7">
                {Etu.telet}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="5">
                <b> E-Mail : </b>
              </CCol>
              <CCol md="7">
                {Etu.adressemailesp}
              </CCol>
            </CRow>
          </CCol>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => {
            setInfo(!info);
            setLoadSpinnerNotif(loadSpinnerNotif)
          }}>
            EXIT
          </CButton>
        </CModalFooter>
      </CModal>

      <CModal
        show={confirmNotif}
        onClose={() => setConfirmNotif(!confirmNotif)}
        color="danger"
      >
        <CModalHeader closeButton>
          <CModalTitle>
            Confirmation Envoie
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <center>
            <br/><br/>
            Êtes-vous sûrs(es) de vouloir notifier
            <br/>
            l'Encadrant Académique de cet Étudiant
            <br/>
            concernant l'obligation de remplissage de la Grille Encadrement ?.
            <br/><br/><br/>
          </center>
        </CModalBody>
        <CModalFooter>
          <CRow>
            <CCol md="12">
              <div className="float-right">
                {loadSpinnerNotif === true ? (
                  <>
                    <Spinner animation="grow" variant="danger"/>
                  </>
                ) : (
                  <>
                    <CButton color="danger"
                             onClick={() => notifyAEToFillGrille()}>
                      YES, I Confirm
                    </CButton>
                  </>
                )}

                &nbsp;&nbsp;
                <CButton color="secondary"
                         onClick={() => setConfirmNotif(!confirmNotif)}>
                  EXIT
                </CButton>
              </div>
            </CCol>
          </CRow>
        </CModalFooter>
      </CModal>

      <CModal
        show={successNotif}
        onClose={() => setSuccessNotif(!successNotif)}
        color="success"
      >
        <CModalHeader closeButton>
          <CModalTitle>
            Information
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <center>
            <br/><br/>
            Notification envoyée avec succès à
            <br/>
            l'Encadrant Académique, le CPS, ainsi que le Chef de Département corresponsant(e)s.
            <br/><br/><br/>
          </center>
        </CModalBody>
        <CModalFooter>
          <CRow>
            <CCol md="12">
              <div className="float-right">
                <CButton color="secondary"
                         onClick={() => setSuccessNotif(!successNotif)}>
                  Ok
                </CButton>
              </div>
            </CCol>
          </CRow>
        </CModalFooter>
      </CModal>

      <CModal
        show={success}
        onClose={() => setSuccess(!success)}
        color="success"
      >
        <CModalHeader closeButton>
          <CModalTitle>
            Confirmation Validation Dépôt
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <br/>
          Êtes-vous sûres de vouloir valider le Dépôt ?
        </CModalBody>

        <br/><br/>
        <CRow>
          <CCol>
            <div className="float-right">
              <CButton  color="success" onClick={() => handleValidate(id, date)}>
                {showLoader && <CSpinner grow size="sm" />}
                Confirmer
              </CButton>
              &nbsp;&nbsp;&nbsp;

              <CButton  color="secondary"
                        onClick={() => setSuccess(!success)}>
                EXIT
              </CButton>
              &nbsp;&nbsp;
            </div>
          </CCol>
        </CRow>
        <br/><br/>
      </CModal>

      <CModal
        show={danger}
        onClose={() => setDanger(!danger)}
        color="danger"
      >
        <CModalHeader closeButton>
          <CModalTitle>
            Dépôt Incomplet
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CForm onSubmit={formikRef.handleSubmit}>
            <CFormGroup>

              {error.visible && <p>{error.message}</p>}
            </CFormGroup>
            <center>
              Merci d'insérer le Motif du Dépôt Incomplet :
            </center>

            <br/>
            <CFormGroup row>
              <CCol md="10">
                <CTextarea
                  value={formikRef.values.cancelMotif}
                  onChange={formikRef.handleChange}
                  name="cancelMotif"
                  rows="4"
                  cols="70"
                  placeholder="Saisir Motif Refus Dépôt .."
                />
                {formikRef.errors.cancelMotif &&
                  formikRef.touched.cancelMotif && (
                    <p style={{ color: "red" }}>
                      {formikRef.errors.cancelMotif}
                    </p>
                  )}
                <br />

              </CCol>
              <CCol md="1"/>
              <br/><br/>
            </CFormGroup>
            {showLoader && (
              <CAlert color="danger">
                Attendre un petit peu ... Nous allons notifer cet
                étudiant par email ... .
              </CAlert>
            )}
            <div className="float-right">
              <CButton  color="danger" type="submit">
                {showLoader && <CSpinner grow size="sm" />} Confirmer
              </CButton>
              &nbsp;&nbsp;&nbsp;
              <CButton color="secondary" onClick={() => setDanger(!danger)}>
                EXIT
              </CButton>
            </div>
          </CForm>
        </CModalBody>

      </CModal>

    </>
  );
};

export default RapportManage;
