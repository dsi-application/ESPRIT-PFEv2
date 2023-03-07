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
  CInput,
  CLabel,
  CTextarea,
  CSelect} from "@coreui/react";
import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetApp from "@material-ui/icons/GetApp";
import Search from "@material-ui/icons/Search";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {
  selectFichebydep,
  updateFichebydep,
  deleteElem
} from "../../../redux/slices/ConventionSlice";
import {
  getEtudiant,
  selectetudiant,
} from "../../../redux/slices/FichePFESlice";
import { etat } from "../../Monotoring/StudentSupervision";
import { queryApi } from "../../../utils/queryApi";
import { useFormik } from "formik";
import axios from "axios";

import TheSidebar from "../../../containers/TheSidebar";

import {
  selectStudentsToStat,
  selectStudentsDoneStat,
  selectNbrUploadedReports,
  selectNbrValidatedReports,
  selectNbrRefusedReports,
  fetchUploadedReports,
  fetchValidatedReports,
  fetchRefusedReports,
  fetchStudentToSTNStat
} from "../../../redux/slices/DepotSlice";

import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
import * as Yup from "yup";
import moment from "moment";


const validationSchema = Yup.object().shape({
  cancelMotif: Yup.string().max(300, "Please don't depass 300 caractères  !").required("* Motif Refus is obligatory !"),
});

const RapportManage = () => {
  const dispatch = useDispatch();
  const Etu = useSelector(selectetudiant);
  const [Fiches, err] = useSelector(selectFichebydep);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("5");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("5");
  const [info, setInfo] = useState(false);
  const [danger, setDanger] = useState(false);
  const [showLoader, setShowLoader] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState({ visible: false, message: "" });
  const [date, setdate] = useState("");
  const [id, setid] = useState("");
  // console.log("Fiches", Fiches);
  const Fichebydepstatus = useSelector(
      (state) => state.persistedReducer.conventions.Fichebydepstatus
  );
  const [studentsToStat, errsts] = useSelector(selectStudentsToStat);
  const [studentsDoneStat, errsds] = useSelector(selectStudentsDoneStat);

  const [nbUploadedReports, errnur] = useSelector(selectNbrUploadedReports);
  const [nbValidatedReports, errnvr] = useSelector(selectNbrValidatedReports);
  const [nbIncompletedReports, errnir] = useSelector(selectNbrRefusedReports);

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
              <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                <CButton  variant="outline"
                          color="success"
                          size="sm"
                          className="float-left;"
                          onClick={() => handleValideModal(e.idEt, e.dateFiche)}>

                  <CTooltip content="Valider Dépôt" placement="top">
                    <CIcon name="cil-check"/>
                  </CTooltip>
                </CButton>
                &nbsp;&nbsp;&nbsp;
                <CButton  variant="outline"
                          color="danger"
                          size="sm"
                          className="float-left;"
                          onClick={() => handleRefuseModal(e.idEt, e.dateFiche)}>
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
      label: "Rapport | Plagiat | Attestation Stage",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          // console.log(Fiches[dataIndex].pathRapport);
          return (
              <>
                {Fiches[dataIndex].etatDepot ? (
                    <>
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
          return renderbtn(Fiches[dataIndex])
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
                      onClick={() => onClickEtudiant(Fiches[dataIndex].idEt)}
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
      cancelMotif: ""
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      setShowLoader(true);
      const [res, err] = await queryApi(
          "serviceStage/updateDepotToREFUSE?idET=" +
          id +
          "&dateFiche=" +
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
        console.log('-----------------2-> 0306')
        dispatch(updateFichebydep(res));
        dispatch(deleteElem(res));
        dispatch(fetchUploadedReports());
        dispatch(fetchValidatedReports());
        dispatch(fetchRefusedReports());
        dispatch(fetchStudentToSTNStat());
        setDanger(false);
        setShowLoader(false);
      }
    },
  });

  const handleValidate = async (id, date) => {

    setShowLoader(true);
    const [res, err] = await queryApi(
        "serviceStage/updateDepotToVALIDE?idET=" + id + "&dateFiche=" + date,
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
      dispatch(fetchUploadedReports());
      dispatch(fetchValidatedReports());
      dispatch(fetchRefusedReports());
      dispatch(fetchStudentToSTNStat());
      dispatch(deleteElem(res));
      setSuccess(false);
      setShowLoader(false);
      //window.location.reload();
    }
  };

  const Download = (p) => {

	  let encodedURL = encodeURIComponent(encodeURIComponent(p));
      axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
          .then((response) => {
      console.log('-----------DOWNLOAD 13.11-------> LOL 1: ' + p);
      console.log('------DOWNLOAD 13.11------------> LOL 2: ' + encodeURIComponent(p));

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
  
  return (
      <>

        <TheSidebar data1={studentsToStat} data2={studentsDoneStat}
                    dataUR={nbUploadedReports} dataVR={nbValidatedReports} dataIR={nbIncompletedReports}/>

        {Fichebydepstatus === "loading" || Fichebydepstatus === "noData" ? (
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
                        <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Rapports Non Traités</span>
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
                            />
                          </MuiThemeProvider>
                      ) : (
                          <MuiThemeProvider theme={theme}>
                            <MUIDataTable
                                title={""}
                                //data={Fiches}
                                columns={columns}
                                options={options}
                            />
                          </MuiThemeProvider>
                      )}
                    </CCardBody>
                  </CCard>
                </CCol>
              </CRow>
            </>
        )}

        <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
          <CModalHeader closeButton>
            <CModalTitle>Détails Étudiant</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CCol xs="12" sm="12" md="12">
              <b> Identifiant : </b> {Etu.idEt} <br></br>
              <b> Nom : </b> {Etu.nomet} <br></br>
              <b> Prénom : </b> {Etu.prenomet} <br></br>
              <b> Numéro Téléphone : </b> {Etu.telet} <br></br>
              <b> Adresse Éléctronique : </b> {Etu.adressemailesp} <br></br>
              <b> Classe : </b> {Etu.classe} <br></br>
            </CCol>
          </CModalBody>
          <CModalFooter>
            <CButton color="secondary" onClick={() => setInfo(!info)}>
              Exit
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
              <center>
                Merci d'insérer le Motif du Dépôt Incomplet :
              </center>

              <br/>
              <CFormGroup row>
                <CCol md="10">
                  <CTextarea
                      value={formik.values.cancelMotif}
                      onChange={formik.handleChange}
                      name="cancelMotif"
                      rows="4"
                      cols="70"
                      placeholder="Saisir Motif Refus Dépôt .."
                  />
                  {formik.errors.cancelMotif &&
                  formik.touched.cancelMotif && (
                      <p style={{ color: "red" }}>
                        {formik.errors.cancelMotif}
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
      </>
  );
};

export default RapportManage;
