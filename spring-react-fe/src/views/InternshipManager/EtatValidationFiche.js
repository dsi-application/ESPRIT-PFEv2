import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import "moment/locale/fr";
import {
  CButton,CTooltip,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CListGroup,
  CListGroupItem,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSpinner,
  CWidgetProgressIcon,
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import MUIDataTable from "mui-datatables";
import {
  selectEtatValidationFiche,
  fetchEtatValidationFiche,
  selectAllAnnee,
} from "../../redux/slices/ListingSlice";
import { FormGroup, TextField } from "@material-ui/core";
import Autocomplete from "@material-ui/lab/Autocomplete";

import { getEtudiant } from "../../redux/slices/FichePFESlice";
import "../css/style.css";
import { getFiche, selectfichePFE } from "../../redux/slices/RequestSlice";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const EtatValidationFiche = () => {
  const [EtatValidationFiche, err] = useSelector(selectEtatValidationFiche);
  const Fiche = useSelector(selectfichePFE);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [AllAnnee, err4] = useSelector(selectAllAnnee);
  const [largeF, setLargeF] = useState(false);
  const EtatValidationFichesStatus = useSelector(
    (state) => state.persistedReducer.listing.EtatValidationFichesStatus
  );
  let list = [];
  if (AllAnnee.length !== 0) {
    AllAnnee.map((s) => {
      var obj = {
        valueAnnee: s[0],
        label: s[0] + "-" + s[1],
      };
      list.push(obj);
    });
  }
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "3px 3px 3px 10px",
        },
      },
    },
  });
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
  const onClickb = (f) => {
    setLargeF(!largeF);
    dispatch(getFiche(f.idEt, f.dateFiche));
    dispatch(getEtudiant(f.idEt));
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
      name: "nom",
      label: "Nom",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "prenom",
      label: "Prénom",
      options: {
        filter: true,
        sort: true,
      },
    },

    {
      name: "email",
      label: "E-Mail",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "classe",
      label: "Classe",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "anneeDeb",
      label: "Année Universitaire",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "dateFiche",
      label: "Date Dépôt Fiche",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td>{moment(e).format(" Do  MMM YYYY")} </td>;
        },
      },
    },
    {
      name: "etatFiche",
      label: "État Fiche",
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
              <CButton
                variant="outline"
                color="danger"
                size="sm"
                onClick={() => onClickb(EtatValidationFiche[dataIndex])}
              >
                <CTooltip content=" Consulter détails Plan Travail">
                <CIcon name="cil-magnifying-glass" />
                </CTooltip>

              </CButton>
            </>
          );
        },
      },
    },
  ];
  const handleChange = (e) => {
    dispatch(fetchEtatValidationFiche(e));
  };
  return (
    <>
      <CRow>
        <CCol>
          <CCard>
            <CCardBody>
              <CCol md="12">
                <CWidgetProgressIcon
                  header={EtatValidationFiche.length}
                  text=" Nombre Total Fiches"
                  color="gradient-dark"
                  inverse
                >
                  <CIcon name="cil-speedometer" height="36" />
                </CWidgetProgressIcon>
              </CCol>
              <br></br>
              <FormGroup style={{ flexDirection: "row" }}>
                <Autocomplete
                  id="country-select-demo"
                  style={{ width: 1300 }}
                  options={list}
                  autoHighlight
                  getOptionLabel={(option) => option.valueAnnee}
                  renderOption={(option) => (
                    <React.Fragment>{option.label}</React.Fragment>
                  )}
                  renderInput={(params) => (
                    <>
                      
                      <CRow>
                        <CCol sm="12" md="10">
                          <TextField
                            {...params}
                            label="Choisir une Année Universitaire"
                            variant="outlined"
                            inputProps={{
                              ...params.inputProps,
                              autoComplete: "new-password", // disable autocomplete and autofill
                            }}
                          />
                        </CCol>
                        <CCol sm="12" md="2">
                          <CButton
                            className="float-left"
                            variant="outline"
                            color="danger"

                            onClick={() =>
                              handleChange(params.inputProps.value)
                            }
                          >
                            
                            <CIcon name="cil-magnifying-glass" />

                            {EtatValidationFichesStatus === "loading" && (
                              <CSpinner color="dark" size="sm" />
                            )}
                          </CButton>
                        </CCol>
                      </CRow>
                    </>
                  )}
                />
              </FormGroup>
              <br></br>
              {EtatValidationFichesStatus === "loading" ||
              EtatValidationFichesStatus === "noData" ? (
                <>
                  <div style={{ textAlign: "center" }}>
                    <CSpinner color="dark" grow size="lg" />
                    &nbsp;
                    <CSpinner color="danger" grow size="lg" />
                    &nbsp;
                    <CSpinner color="dark" grow size="lg" />
                    &nbsp;
                    <br></br>
                  </div>
                  <br></br>
                </>
              ) : (
                <>
                  <CRow>
                    <CCol xs="12">
                      <br/>
                      &nbsp;
                      <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>État de Validation Fiche</span>
                    </CCol>
                  </CRow>
                  <br/>
                  {EtatValidationFiche ? (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
                      data={EtatValidationFiche}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
                      //   data={EtatValidationFiche}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  )}
                </>
              )}
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

      <CModal
        size="lg"
        show={largeF}
        onClose={() => setLargeF(!largeF)}
        color="dark"
      >
        <CModalHeader closeButton>
          <CModalTitle>
            <span style={{fontSize:"12px"}}>
              Détails du Plan Travail
            </span>
            </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CCol xs="12" sm="12" md="12">
            {Fiche.idFichePFE ? (
              <CCard accentColor="danger">
                <CCardHeader>
                  <CRow>
                    <CCol xs="12" sm="6" md="6">
                      <span className="redMark">
                        {Fiche.titreProjet}
                      </span>
                      <br/><br/>
                      <b>Identifiant Étudiant:</b>&nbsp;&nbsp;{Fiche.idFichePFE.idEt}
                      <br/>
                      <b>Date Dépôt Fiche:</b>&nbsp;&nbsp;{moment(Fiche.idFichePFE.dateDepotFiche).locale("fr").format("LLLL")}
                    </CCol>
                  </CRow>
                </CCardHeader>
                <CCardBody>
                  <CRow>
                    <CCol xs="12" sm="6" md="6">
                      <span className="primaryMark">Description Projet :</span>
                      <p>
                        {Fiche.descriptionProjet
                            ? Fiche.descriptionProjet
                            : "Pas de decsription"}
                      </p>
                      <br/>
                      <span className="infoMark">Problématiques Projet : </span>
                      <CListGroup accent>
                        {Fiche.problematics?.map((item, i) => (
                          <CListGroupItem accent="info">
                            {item.name}
                          </CListGroupItem>
                        ))}
                      </CListGroup>
                    </CCol>
                    <CCol xs="12" sm="6" md="6">
                      <span className="secondaryMark">Fonctionnalités Projet : </span>
                      <CListGroup accent>
                        {Fiche.functionnalities?.map((item, i) => (
                            <CListGroupItem accent="dark">
                              {item.name} <br></br> <b>Description : </b>
                              {
                                item.description === "null" ?
                                    <>--</>
                                    :
                                    <>{item.description}</>
                              }
                            </CListGroupItem>
                        ))}
                      </CListGroup>
                      <br/>
                      <span className="lightMark">Téchnologies Utilisées : </span>
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
            ) : (
              <></>
            )}
          </CCol>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => setLargeF(!largeF)}>
            EXIT
          </CButton>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default EtatValidationFiche;
