import { CButton, CCard, CCardBody, CCol, CRow, CSpinner } from "@coreui/react";
import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  getEtudiant,
  selectfiche,
  selectfichePFEs,
} from "../../redux/slices/FichePFESlice";
import { getEtatFiche } from "../../redux/slices/StudentSlice";
import { etat } from "../Monotoring/StudentSupervision";
import "moment/locale/fr";
import MUIDataTable from "mui-datatables";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
import { getFiche } from "../../redux/slices/RequestSlice";
const FichePFEManagement = () => {
  const [fichePFEs, err] = useSelector(selectfichePFEs);

  const dispatch = useDispatch();
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const fichePFEsStatus = useSelector(
      (state) => state.persistedReducer.fichePFEs.fichePFEsStatus
  );
  console.log('fiche',fichePFEs)
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "3px 3px 3px 10px",
        },
      },
    },
  });
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
      name: "dateFiche",
      label: "Date dépôt Fiche",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td>{moment(e).format(" Do  MMM YYYY")} </td>;
        },
      },
    },
    {
      name: "fichePFE.etatFiche",
      label: "État",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return etat(e);
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
          return (
              <>
                <Link to={"/FichePFE"}>
                  <CButton
                      variant="outline"
                      color="danger"
                      size="sm"
                      onClick={() => onClickFiche(fichePFEs[dataIndex])}
                  >
                    Afficher détails
                  </CButton>
                </Link>
              </>
          );
        },
      },
    },
  ];

  const onClickFiche = (i) => {
    console.log('Fiche==',i)
    dispatch(selectfiche(i));
    dispatch(getEtatFiche(i.fichePFE.etatFiche));
    dispatch(getEtudiant(i.idEt));
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
  return (
      <>
        {fichePFEsStatus === "loading" || fichePFEsStatus === "noData" ? (
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
                        <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                          Liste des Fiches PFE</span>
                      </CCol>
                    </CRow>
                    <br/>

                    <CCardBody>
                      {fichePFEs ? (
                          <MuiThemeProvider theme={theme}>
                            <MUIDataTable
                                data={fichePFEs}
                                columns={columns}
                                options={options}
                            /></MuiThemeProvider>
                      ) : (
                          <MuiThemeProvider theme={theme}>
                            <MUIDataTable
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

export default FichePFEManagement;
