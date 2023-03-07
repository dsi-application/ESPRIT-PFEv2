import CIcon from "@coreui/icons-react";
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CRow,
  CSpinner, CTooltip,
} from "@coreui/react";
import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import {
  fetchEtudiantbySession,
  selectEtudiantbySession,
} from "../../../redux/slices/ListingSlice";
import { selectSessions } from "../../../redux/slices/SessionSlice";
import { ExportCSV } from "../../../utils/ExcportCSV";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const DepotDossierRapport = () => {
  const [EtudiantbySession, err] = useSelector(selectEtudiantbySession);
  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [sessions, errs] = useSelector(selectSessions);
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "2px 2px 2px 20px",
        },
      },
    },
  });
  const StudentsSoutenancesStatus = useSelector(
    (state) => state.persistedReducer.listing.EtudiantbySessionStatus
  );
  let listtoExport = [];
  if (EtudiantbySession != null && EtudiantbySession.length !== 0) {
    EtudiantbySession.map((s) => {
      var obj = {
        "Identifiant Etudiant": s.idEt,
        "Nom Etudiant": s.prenomet,
        "Prénom Etudiant": s.nomet,
        "Numéro Téléphone Etudiant": s.telet,
        "Adresse électronique ": s.adressemailesp,
        "Etat Fiche": s.etatFiche,
        "Encadrant Pédagogique": s.encadrant,
      };
      listtoExport.push(obj);
    });
  }
  const options = {
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
        text: "ligne sélectionnée",
        delete: "Delete",
        deleteAria: "Delete Selected Rows",
      },
    },
  };
  const handleClick = (e) => {
    dispatch(fetchEtudiantbySession(sessions[e.data[0].dataIndex].idSession));
  };
  const optionsSession = {
    filter: true,
    filterType: "dropdown",
    responsive,
    tableBodyHeight,
    tableBodyMaxHeight,
    download: false,
    fixedSelectColumn: true,
    selectableRows: "single",
    customToolbarSelect: (selectedRows) => (
      <div className={"custom-toolbar-select"}>
        <CButton
          style={{
            marginRight: "50px",
            display: "inline-block",
            position: "relative",
          }}
          name="btnsubmit"
          color="danger"
          onClick={() => {
            handleClick(selectedRows);
          }}
        >
          {" "}
          <CIcon name="cil-magnifying-glass" />
          &nbsp; Chercher &nbsp;
          {StudentsSoutenancesStatus === "loading" ||
          StudentsSoutenancesStatus === "noData" ? (
            <CSpinner color="dark" size="sm" />
          ) : (
            <></>
          )}
        </CButton>
      </div>
    ),
    selectableRowsHeader: false,
    selectableRowsOnClick: true,
    enableNestedDataAccess: ".",
    rowsPerPage: 5,
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
        text: "ligne sélectionnée",
        delete: "Delete",
        deleteAria: "Delete Selected Rows",
      },
    },
  };
  const columns = [
    {
      name: "idEt",
      label: "Identifiant Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "nomet",
      label: "Nom étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "prenomet",
      label: "Prénom Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "adressemailesp",
      label: "Adresse Electronique",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "etatFiche",
      label: "Etat Fiche",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "encadrant",
      label: "Encadrant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "",
      label: "Rapport",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
            <>
              {" "}
              {EtudiantbySession[dataIndex].pathRapport ? (
                <CButton
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => {
                    Download(EtudiantbySession[dataIndex].pathRapport);
                  }}
                >
                   <CTooltip content="Télécharger Rapport">
            <CIcon name="cil-save"></CIcon>
          </CTooltip>
                </CButton>
              ) : (
                <CButton
                  disabled
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => {
                    Download(EtudiantbySession[dataIndex].pathRapport);
                  }}
                >
       <CTooltip content="Télécharger Rapport">
            <CIcon name="cil-save"></CIcon>
          </CTooltip>
                </CButton>
              )}
            </>
          );
        },
      },
    },
  ];
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
        //console.log(url);
        window.open(url);
        a.click();
      });
  };
  const columnssessions = [
    {
      name: "libelleSession",
      label: "Nom / libellé Session",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "dateDebut",
      label: "Date Début",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td> {moment(e).format("LL")} </td>;
        },
      },
    },
    {
      name: "dateFin",
      label: "Date Fin",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td> {moment(e).format("LL")} </td>;
        },
      },
    },
  ];
  return (
    <>
      <CRow>
        <CCol>
          <CCard>
            <CCardHeader style={{ textAlign: "center" }}>
              <b>
                {" "}
                Chercher les étudiants avec Soutenance Validée ou en Instance de
                Soutenance
              </b>
            </CCardHeader>
            <CCardBody>
              {sessions ? (
                
                <MuiThemeProvider theme={theme}>
                <MUIDataTable
                  title={" Liste des Sessions"}
                  data={sessions}
                  columns={columnssessions}
                  options={optionsSession}
                /></MuiThemeProvider>
              ) : (
                <MuiThemeProvider theme={theme}>
                <MUIDataTable
                  title={" Liste des Sessions"}
                  //   data={sessions}
                  columns={columnssessions}
                  options={optionsSession}
                /></MuiThemeProvider>
              )}
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
      {StudentsSoutenancesStatus === "loading" ||
      StudentsSoutenancesStatus === "noData" ? (
        <>
          <div style={{ textAlign: "center" }}>
            <CSpinner color="warning" grow size="lg" />
            &nbsp;
            <CSpinner color="danger" grow size="lg" />
            &nbsp;
            <CSpinner color="primary" grow size="lg" />
            &nbsp;
            <br></br>
          </div>
          <br></br>
        </>
      ) : (
        <>
          <CRow>
            <CCol>
              <CCard>
                <CCardHeader>
                  <ExportCSV csvData={listtoExport} fileName="StudentList" />
                </CCardHeader>
                <CCardBody>
                  {EtudiantbySession ? (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={"Etudiant associés "}
                      data={EtudiantbySession}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={"Etudiant associés "}
                      //   data={sessions}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  )}
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
        </>
      )}
    </>
  );
};

export default DepotDossierRapport;
