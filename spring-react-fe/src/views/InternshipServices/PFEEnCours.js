import CIcon from "@coreui/icons-react";
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CRow,
  CSpinner,
} from "@coreui/react";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {
  fetchStudentPFEDepasseList,
  selectStudentPFEDepasseList,
  populateStudentPFEDepasseListNull,
} from "../../redux/slices/ListingSlice";
import Autocomplete from "@material-ui/lab/Autocomplete";
import { FormGroup, TextField } from "@material-ui/core";
import { Timeline, TimelineEvent } from "@mailtop/horizontal-timeline";
import {
  FaCheckCircle,
  FaBug,
  FaRegCalendarCheck,
  FaRegFileAlt,
  FaPaperPlane
} from "react-icons/fa";
const PFEEnCours = () => {
  const [StudentPFEDepasseList, err] = useSelector(selectStudentPFEDepasseList);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [show, setshow] = useState(false);

  const StudentPFEDepasseListStatus = useSelector(
    (state) => state.persistedReducer.listing.StudentPFEDepasseListStatus
  );
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
  const list = [
    { Mois: "1", label: "1 Mois" },
    { Mois: "2", label: "2 Mois" },
    { Mois: "3", label: "3 Mois" },
    { Mois: "4", label: "4 Mois" },
    { Mois: "5", label: "5 Mois" },
    { Mois: "6", label: "6 Mois" },
    { Mois: "7", label: "7 Mois" },
    { Mois: "8", label: "8 Mois" },
    { Mois: "9", label: "9 Mois" },
    { Mois: "10", label: "10 Mois" },
    { Mois: "11", label: "11 Mois" },
    { Mois: "12", label: "12 Mois" },
  ];
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
      name: "adressemailesp",
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
      name: "etatFiche",
      label: "État Fiche",
      options: {
        filter: true,
        sort: true,
      },
    },
  ];
  const handleChange = (e) => {
    setshow(true);
    dispatch(fetchStudentPFEDepasseList(e));
  };
  const handleRest = (e) => {
    dispatch(populateStudentPFEDepasseListNull());
  };
  return (
    <>
      <CRow>
        <CCol>
          <CCard>
            <CCardHeader style={{ textAlign: "center" }}>
              <h5>
                
                Chercher les étudiants qui ont dépassé les 24 semaines de leurs
                stages
              </h5>
              <br></br>
              <div>
              <Timeline minEvents={4} placeholder variant="180px">
                <TimelineEvent
                  icon={FaRegFileAlt}
                  title="Début du Stage"
                  subtitle="Date début Convention"/>
                <TimelineEvent
                  color="#87a2c7"
                  icon={FaRegCalendarCheck}
                  title="Stage en cours . . ."
                  //  subtitle="26/03/2019 09:51"
                />
                <TimelineEvent
                  color="green"
                  icon={FaCheckCircle}
                  title="24 Semaines "
                  subtitle="+24 semaines"
                />
                <TimelineEvent
                  color="#9c2919"
                  icon={FaPaperPlane}
                  title="+ N Mois "
                  subtitle=". . ."
                />
              </Timeline>
              </div>
                <br></br>
            </CCardHeader>
            <CCardBody>
              <br></br>
              <FormGroup style={{ flexDirection: "row" }}>
                <Autocomplete
                  id="country-select-demo"
                  defaultValue={{ Mois: "0", label: "pas de mois" }}
                  style={{ width: 1100 }}
                  options={list}
                  autoHighlight
                  getOptionLabel={(option) => option.Mois}
                  renderOption={(option) => (
                    <React.Fragment>{option.label}</React.Fragment>
                  )}
                  renderInput={(params) => (
                    <>
                      
                      <CRow>
                        <CCol sm="12" md="6">
                          <TextField
                            {...params}
                            label="Choisir un Mois"
                            variant="outlined"
                            inputProps={{
                              ...params.inputProps,
                              autoComplete: "new-password", // disable autocomplete and autofill
                            }}
                          />
                        </CCol>
                        <CCol sm="12" md="6">
                          <div
                            style={{
                              display: "inline",
                            }}
                          >
                            <CButton
                              //  className="float-right"
                              name="btnsubmit"
                              type="submit"
                              color="success"
                              size="lg"
                              onClick={() =>
                                handleChange(params.inputProps.value)
                              }
                            >
                              <CIcon name="cil-magnifying-glass" />
                              &nbsp; Chercher &nbsp;
                              {StudentPFEDepasseListStatus === "loading" ? (
                                <CSpinner color="dark" size="sm" />
                              ) : (
                                <></>
                              )}
                            </CButton>
                            &nbsp;
                            <CButton
                              //    className="float-left"
                              variant="outline"
                              color="danger"
                              size="lg"
                              type="submit"
                              //   type="reset"
                              onClick={handleRest}
                            >
                              
                              <CIcon name="cil-x" /> &nbsp; Annuler
                            </CButton>
                          </div>
                        </CCol>
                        <CCol md="4"></CCol>
                      </CRow>
                    </>
                  )}
                />
              </FormGroup>
              <br></br>

              <CRow>
                <CCol xs="12">
                  <br/>
                  &nbsp;
                  <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>
                    Liste des Étudiants</span>
                </CCol>
              </CRow>
              <br/>

              {StudentPFEDepasseListStatus === "loading" ? (
                <>
                  <div style={{ textAlign: "center" }}>
                    <br/><br/>
                    <CSpinner color="danger" grow size="lg" />

                    <br/><br/>
                    <b>Veuillez patienter le chargement des données ...</b>
                  </div>
                  <br></br>
                </>
              ) : StudentPFEDepasseList ? (
                <MUIDataTable
                  data={StudentPFEDepasseList}
                  columns={columns}
                  options={options}
                />
              ) : (
                <MUIDataTable
                  // data={StudentPFEDepasseList}
                  columns={columns}
                  options={options}
                />
              )}
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>
  );
};

export default PFEEnCours;
