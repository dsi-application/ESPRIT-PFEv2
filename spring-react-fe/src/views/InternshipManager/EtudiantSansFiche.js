import React, { useState } from "react";
import { useSelector } from "react-redux";

import "moment/locale/fr";
import { CCard, CCardBody, CCol, CRow, CSpinner } from "@coreui/react";
import MUIDataTable from "mui-datatables";
import { selectEtudiantSansFiche } from "../../redux/slices/ListingSlice";

import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const EtudiantSansFiche = () => {
  const [EtudiantSansFiche, err] = useSelector(selectEtudiantSansFiche);

  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const EtudiantSansFichesStatus = useSelector(
    (state) => state.persistedReducer.listing.EtudiantSansFichesStatus
  );
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "3px 3px 3px 20px",
        },
      },
    },
  });
  const options = {
    //count: 1000000000,
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
      name: "telet",
      label: "Num. Téléphone",
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
      name: "anneeDebInscription",
      label: "Année Universitaire",
      options: {
        filter: true,
        sort: true,
      },
    },
  ];
  return (
    <>
      {EtudiantSansFichesStatus === "loading" ||
      EtudiantSansFichesStatus === "noData" ? (
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
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Étudiants Sans Plan Travail</span>
                  </CCol>
                </CRow>
                <br/>
                <CCardBody>
                  {EtudiantSansFiche ? (
                    <>
                      <MuiThemeProvider theme={theme}>
                      <MUIDataTable
                        title={""}
                        data={EtudiantSansFiche}
                        columns={columns}
                        options={options}
                      />
                    </MuiThemeProvider>

                    </>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
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

export default EtudiantSansFiche;
