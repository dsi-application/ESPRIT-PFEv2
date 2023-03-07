import React, { useState } from "react";
import { useSelector } from "react-redux";

import "moment/locale/fr";
import MUIDataTable from "mui-datatables";
import { CButton, CCard, CCardBody, CCol, CRow, CSpinner } from "@coreui/react";
import { selectStudentsForSoutenance } from "../../../redux/slices/ListingSlice";
import CIcon from "@coreui/icons-react";
import axios from "axios";

import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const ListRapportPresident = () => {
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const [StudentsForSoutenance, err] = useSelector(selectStudentsForSoutenance);
  const StudentsSoutenancesStatus = useSelector(
    (state) => state.persistedReducer.listing.StudentsSoutenancesStatus
  );
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
  const Download = (p) => {
    axios
      .get(
        `${process.env.REACT_APP_API_URL_PC}` +
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
      label: "Nom Étudiant",
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
      label: "E-Mail Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "anneDeb",
      label: "Année Universitaire",
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
              {StudentsForSoutenance[dataIndex].pathRapport ? (
                <CButton
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => {
                    Download(StudentsForSoutenance[dataIndex].pathRapport);
                  }}
                >
                   <CIcon name="cil-save"></CIcon>
                </CButton>
              ) : (
                <CButton
                  disabled
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => {
                    Download(StudentsForSoutenance[dataIndex].pathRapport);
                  }}
                >
                   <CIcon name="cil-save"></CIcon>
                </CButton>
              )}
            </>
          );
        },
      },
    },
  ];
  return (
    <>
      {StudentsSoutenancesStatus === "loading" ||
      StudentsSoutenancesStatus === "noData" ? (
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
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Rapports</span>
                  </CCol>
                </CRow>
                <br/>
                <CCardBody>
                  {StudentsForSoutenance ? (
                 <MuiThemeProvider theme={theme}>

                    <MUIDataTable
                      title={""}
                      data={StudentsForSoutenance}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
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

export default ListRapportPresident;
