import CIcon from "@coreui/icons-react";
import {
  CAlert,
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSpinner,
  CTooltip,
  CForm,
CFormGroup,
CLabel,
CTextarea
} from "@coreui/react";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetApp from "@material-ui/icons/GetApp";
import Search from "@material-ui/icons/Search";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {
  selectFichebydepVal, fetchFichebydepVal, fetchFicheValALL,
  updateFichebydep, selectFicheValALL,
} from "../../../redux/slices/ConventionSlice";
import {
  getEtudiant,
  selectetudiant,
} from "../../../redux/slices/FichePFESlice";
import { etat } from "../../Monotoring/StudentSupervision";
import { queryApi } from "../../../utils/queryApi";
import { useFormik } from "formik";
import Spinner from "react-bootstrap/Spinner";

import TheSidebar from "../../../containers/TheSidebar";
import { selectStudentsToStat, selectStudentsDoneStat} from "../../../redux/slices/DepotSlice";

import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";

import { 
  selectNbrUploadedReports,
  selectNbrValidatedReports,
  selectNbrRefusedReports,
  fetchUploadedReports,
  fetchValidatedReports,
  fetchRefusedReports
} from "../../../redux/slices/DepotSlice";

import * as Yup from "yup";
import {setErrors} from "../../../redux/slices/ChefCoordConfigSlice";
const validationSchema = Yup.object().shape({
  observation: Yup.string()
    .max(100, "Ne pas dépasser 100 caractères  !")
    .required("* Champs obligatoire !"),
});
const ValidatedReportsForCPS = () => {
  const dispatch = useDispatch();
  const Etu = useSelector(selectetudiant);
  const [Fiches, err] = useSelector(selectFicheValALL);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("10");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [info, setInfo] = useState(false);
  const [danger, setDanger] = useState(false);
  const [showLoader, setShowLoader] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState({ visible: false, message: "" });
  const [date, setdate] = useState("");
  const [id, setid] = useState("");
  const [confirmNotif, setConfirmNotif] = useState(false);
  const [successNotif, setSuccessNotif] = useState(false);
  const [loadSpinnerNotif, setLoadSpinnerNotif] = useState(false);
  // console.log("Fiches", Fiches);
  const FichestatusValALL = useSelector(
    (state) => state.persistedReducer.conventions.FichestatusALL
  );
  const [studentsToStat, errsts] = useSelector(selectStudentsToStat);
  const [studentsDoneStat, errsds] = useSelector(selectStudentsDoneStat);

  const [nbUploadedReports, errnur] = useSelector(selectNbrUploadedReports);
  const [nbValidatedReports, errnvr] = useSelector(selectNbrValidatedReports);
  const [nbIncompletedReports, errnir] = useSelector(selectNbrRefusedReports);

  useEffect(() => { dispatch(fetchFicheValALL()) }, [Fiches]);

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
    if (e.etatDepot === "DEPOSE") {
      return (
        <CRow>
          <CCol xs="12" sm="4" md="6">
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >

              <CButton
                variant="outline"
                color="success"
                size="sm"
                className="float-left;"
                onClick={() => handleValideModal(e.idEt, e.dateFiche)}
              >

              <CTooltip content="Valider Dépôt" placement="top">
                              <CIcon name="cil-check"/>
                            </CTooltip>


              </CButton>
            </div>
          </CCol>
          <CCol xs="12" sm="4" md="6">
            
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <CButton
                variant="outline"
                color="danger"
                size="sm"
                className="float-left;"
                onClick={() => handleRefuseModal(e.idEt, e.dateFiche)}
              >
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
      name: "nomet",
      label: "Nom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "prenomet",
      label: "Prénom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "etatDepot",
      label: "État du dépôt",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return etatDepot(e);
        },
      },
    },

    {
      name: "",
      label: "",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          // console.log(Fiches[dataIndex].pathRapport);
          return (
            <>
              {Fiches[dataIndex].etatDepot ? (
                <>
                 <CRow>
                   <CCol>
                     <Tooltip title=" Télécharger Rapport">
                       <IconButton
                           onClick={() => {
                             Download(Fiches[dataIndex].pathRapport);
                           }}
                       >
                         <GetApp style={{ color: "#DB4437" }} />
                       </IconButton>
                     </Tooltip>
                     &nbsp;
                     <Tooltip title=" Télécharger Plagiat">
                       <IconButton
                           onClick={() => {
                             Download(Fiches[dataIndex].pathPlagiat);
                           }}
                       >
                         <GetApp style={{ color: "#DB4437" }} />
                       </IconButton>
                     </Tooltip>
                     &nbsp;
                     <Tooltip title=" Télécharger Attestation Stage">
                       <IconButton
                           onClick={() => {
                             Download(Fiches[dataIndex].pathAttestationStage);
                           }}
                       >
                         <GetApp style={{ color: "#DB4437" }} />
                       </IconButton>
                     </Tooltip>
                     &nbsp;
                     {(
                         Fiches[dataIndex].pathDossierTechnique !== null &&
                         <Tooltip title=" Télécharger Dossier Technique">
                           <IconButton
                               onClick={() => {
                                 Download(Fiches[dataIndex].pathDossierTechnique);
                               }}
                           >
                             <GetApp style={{ color: "#DB4437" }} />
                           </IconButton>
                         </Tooltip>
                     )}
                   </CCol>
                 </CRow>
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
      label: "Grille Encadrement",

      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
              <center>
                  <div className="float-center">
                      {
                          Fiches[dataIndex].etatGrilleEncadrement === "DONE" &&
                          <Tooltip title="Télécharger Grille Evaluation">
                            <span className="downloadGreenIconSmall" onClick={() => {downloadGrilleAE(Fiches[dataIndex].idEt)}}/>
                          </Tooltip>
                      }
                      {
                          Fiches[dataIndex].etatGrilleEncadrement === "NOTYET" &&
                          <Tooltip title="Notifier Encadrant Académique">
                            <span className="bellRedIconSmall" onClick={() => {askForNotif(Fiches[dataIndex].idEt)}}/>
                          </Tooltip>
                      }
                  </div>
              </center>
          );
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
              <Tooltip title="Voir Détails Étudiant">
                <IconButton
                  onClick={() => onClickEtudiant(Fiches[dataIndex].idEt)}
                >
                  <Search style={{ color: "#000000" }} />
                </IconButton>
              </Tooltip>
            </div>
          );
        },
      },
    }
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
    setdate(date);
    setid(id);
    setSuccess(true);
  };
  const handleRefuseModal = (id, date) => {
    setdate(date);
    setid(id);
    setDanger(true);
  };
 const formik = useFormik({
    initialValues: {
      observation: "",
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setShowLoader(true);
      const [res, err] = await queryApi(
        "serviceStage/updateDepotToREFUSE?idET=" +
          id +
          "&dateFiche=" +
          date +
          "&observation=" +
          values.observation,
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
        dispatch(updateFichebydep(res));
           window.location.reload();
        //setDanger(false);
      }
    },
  });

  const Download = (p) => {

    console.log('------------AZERTY------> LOL 0: ' + p);

    // console.log('--------------------------AZERTY-LOL: ' + encodeURIComponent(p));

    // console.log('-----------AZERTY-------> LOL 1: ' + p);

      let encodedURL = encodeURIComponent(encodeURIComponent(p));
      axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
          .then((response) => {

      const file = new Blob([response.data], { type: "application/pdf" });
      let url = window.URL.createObjectURL(file);

      // let a = document.createElement("a");
      // a.href = url;
      // a.download = p.substring(p.lastIndexOf("/") + 1);
      if(p.includes('.pdf'))
      {
        window.open(url);
      }
      // a.click();
    });
  };

  const downloadGrilleAE = (p) => {
    console.log('------------AZERTY------> LOL 0: ' + p);
    axios.get(`${process.env.REACT_APP_API_URL_AE}` + "downloadGrilleAE/" + p, { responseType: "blob" })
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

  return (
    <>

      <TheSidebar data1={studentsToStat} data2={studentsDoneStat} 
      dataUR={nbUploadedReports} dataVR={nbValidatedReports} dataIR={nbIncompletedReports}/>

      {FichestatusValALL === "loading" || FichestatusValALL === "noData" ? (
        <>
          <div style={{ textAlign: "center" }}>
            <br/><br/>
            <CSpinner color="danger" grow size="lg" />

            <br/><br/>
            <b>Veuillez patienter le chargement des données ...</b>
          </div>
          <br></br>
        </>
      ) : (
        <>
          <CRow>
            <CCol>
              <CCard>
                <CRow>
                  <CCol xs="12">
                    <br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Rapports Validés</span>
                  </CCol>
                </CRow>
                <br/>
                <CCardBody>
                  {Fiches ? (
                      <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
                      data={Fiches}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  ) : (
                      <center>
                        <br/><br/><br/><br/><br/>
                        <span className="waitIcon"/>
                        <br/><br/><br/><br/><br/>
                      </center>
                  )}
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </>
      )}

      <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
        <CModalHeader closeButton>
          <CModalTitle>
            <span style={{fontSize:"14px"}}>
            Détails Étudiant
            </span>
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CCol xs="12" sm="12" md="12">
            <CRow>
              <CCol md="3">
                <b> Identifiant : </b>
              </CCol>
              <CCol md="9">
                {Etu.idEt}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="3">
                <b> Nom & Prénom : </b>
              </CCol>
              <CCol md="9">
                {Etu.nomet} &nbsp; {Etu.prenomet}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="3">
                <b> Numéro Téléphone : </b>
              </CCol>
              <CCol md="9">
                {Etu.telet}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="3">
                <b> E-Mail : </b>
              </CCol>
              <CCol md="9">
                {Etu.adressemailesp}
              </CCol>
            </CRow>

            <CRow>
              <CCol md="3">
                <b> Classe Courante : </b>
              </CCol>
              <CCol md="9">
                {Etu.classe}
              </CCol>
            </CRow>
          </CCol>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => {setInfo(!info); setLoadSpinnerNotif(loadSpinnerNotif)}}>
            EXIT
          </CButton>
        </CModalFooter>
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
          <CForm onSubmit={formik.handleSubmit}>
            <CFormGroup>
              
              {error.visible && <p>{error.message}</p>}
            </CFormGroup>
            <CFormGroup row>
              <CCol md="3">
                <CLabel htmlFor="selectSm">
                  <b>Motif de dépot Incomplet : </b>
                </CLabel>
              </CCol>
              <CCol xs="12" md="9">
                <CTextarea
                    value={formik.values.observation}
                    onChange={formik.handleChange}
                    name="observation"
                    rows="3"
                    cols="70"
                    placeholder="Saisir votre motif ..."
                />
                {formik.errors.observation &&
                formik.touched.observation && (
                    <p style={{ color: "red" }}>
                      {formik.errors.observation}
                    </p>
                )}

                <br />
              </CCol>
            </CFormGroup>
            {showLoader && (
                <CAlert color="danger">
                  Attendre un petit peu ... Nous allons notifer cet
                  étudiant par email ....
                </CAlert>
            )}
            <CButton
                className="float-right"
                color="secondary"
                onClick={() => setDanger(!danger)}
            >
              Annuler
            </CButton>
            <CButton
                className="float-right"
                color="danger"
                type="submit"
            >
              {showLoader && <CSpinner grow size="sm" />} OUI
            </CButton>
          </CForm>
        </CModalBody>


      </CModal>
      <CModal
          show={success}
          onClose={() => setSuccess(!success)}
          color="success"
      >
        <CModalHeader closeButton>
          <CModalTitle>
            Confirmation de validation de l'étudiant
          </CModalTitle>
        </CModalHeader>
        <CModalBody>Voulez vous valider le dépot ?</CModalBody>
        <CModalFooter>

          {showLoader && (
              <CAlert color="danger">
                Attendre un petit peu ... Nous allons notifer cet
                étudiant par email ....
              </CAlert>
          )}
          <CButton
              color="secondary"
              onClick={() => setSuccess(!success)}
          >
            Annuler
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

    </>
  );
};

export default ValidatedReportsForCPS;
