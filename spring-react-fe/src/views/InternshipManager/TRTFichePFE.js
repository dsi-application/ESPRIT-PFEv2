import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CRow,
  CSpinner,
} from "@coreui/react";
import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  selectrequests,
  selectrequest,
  getFiche,
} from "../../redux/slices/RequestSlice";
import { MuiThemeProvider } from "@material-ui/core";

import { createMuiTheme } from "@material-ui/core";
import "moment/locale/fr";
import { getEtudiant } from "../../redux/slices/FichePFESlice";
import MUIDataTable from "mui-datatables";
export const getRequestType = (e) => {
  if (e === "01") {
    return <CBadge color="info">MODIFICATION</CBadge>;
  }
  if (e === "02") {
    return <CBadge color="danger">ANNULATION</CBadge>;
  }
};
export const getRequestState = (e) => {
  if (e === "01") {
    return <CBadge color="warning">EN ATTENTE</CBadge>;
  }
  if (e === "02") {
    return <CBadge color="success">TRAITEE</CBadge>;
  }
};
const TRTFichePFE = () => {
  const [Requests, err] = useSelector(selectrequests);

  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const dispatch = useDispatch();

  const postStatus = useSelector(
    (state) => state.persistedReducer.requests.status
  );

  const onClickRequest = (i) => {
    dispatch(
      getFiche(
        i.traitementFichePK.fichePFEPK.idEt,
        i.traitementFichePK.fichePFEPK.dateDepotFiche
      )
    );
    dispatch(selectrequest(i));
    dispatch(getEtudiant(i.traitementFichePK.fichePFEPK.idEt));
  };

  const columns = [
    {
      name: "traitementFichePK.fichePFEPK.idEt",
      label: "Identifiant Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "traitementFichePK.fichePFEPK.dateDepotFiche",
      label: "Date Dépôt Fiche",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td>{moment(e).format("LLLL")} </td>;
        },
      },
    },
    {
      name: "traitementFichePK.dateTrtFiche",
      label: "Date Dépôt Demande",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td>{moment(e).format(" Do  MMM YYYY")} </td>;
        },
      },
    },
    {
      name: "typeTrtFiche",
      label: "Type Demande",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return getRequestType(e);
        },
      },
    },
    {
      name: "etatTrt",
      label: "État",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return getRequestState(e);
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
              <Link to={"/RequestDetails"}>
                <CButton
                  variant="outline"
                  color="danger"
                  size="sm"
                  onClick={() => onClickRequest(Requests[dataIndex])}
                >
                  Voir détails
                </CButton>
              </Link>
            </>
          );
        },
      },
    },
  ];
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

  return (
    <>
      {postStatus === "loading" || postStatus === "noData" ? (
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
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>
                      Liste des Demandes de Modifications & Annulations des Fiches PFEs</span>
                  </CCol>
                </CRow>
                <br/>
                <CCardBody>
                  {Requests ? (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      data={Requests}
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

export default TRTFichePFE;
