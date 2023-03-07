import CIcon from "@coreui/icons-react";
import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CRow,
  CSpinner,
  CTooltip,
} from "@coreui/react";
import axios from "axios";
import moment from "moment";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { selectAvenant, selectAvenants } from "../../redux/slices/AvenantSlice";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import { getEtudiant } from "../../redux/slices/FichePFESlice";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver La liste des avenants `,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: () => (
      <Text>
        Içi, vous pouvez interpréter l'état de l'avenant :
        <strong>EN ATTENTE - TRAITE</strong>.
      </Text>
    ),
  },

  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
      <Text>
        Pour voir les détails de l'avenant et la traiter cliquer sur ce bouton :
        <strong> Afficher détails</strong>.
      </Text>
    ),
  },
];
export const getBadge = (traiter) => {
  switch (traiter) {
    case "01":
      return "warning";
    case "02":
      return "success";

    default:
      return "danger";
  }
};
export const getEtat = (etat) => {
  switch (etat) {
    case "01":
      return "DEPOSEE";
    case "02":
      return "TRAITER";

    default:
      return "ANNULEE";
  }
};
const AvenantsManage = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [avenants, errAv] = useSelector(selectAvenants);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const avenantsstatus = useSelector(
    (state) => state.persistedReducer.avenants.avenantsstatus
  );
  const dispatch = useDispatch();
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "2px 2px 2px 20px",
        },
      },
    },
  });
  const columnsAvenants = [
    {
      name: "avenantPK.conventionPK.idEt",
      label: "Identifiant Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "avenantPK.conventionPK.dateConvention",
      label: "Date dépôt Convention",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td> {moment(e).format("LLLL")} </td>;
        },
      },
    },
    {
      name: "avenantPK.dateAvenant",
      label: "Date dépôt Avenant",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return <td>{moment(e).format("LLLL")}</td>;
        },
      },
    },
    {
      name: "traiter",
      label: "ÉTAT",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return (
            <td data-tut="reactour__2">
              
              {e ? (
                <CBadge color="success">TRAITE</CBadge>
              ) : (
                <CBadge color="warning">EN ATTENTE</CBadge>
              )}
            </td>
          );
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
            
                  <Link to={"/AvenantDetails"}>
                    <CButton
                      variant="outline"
                      color="dark"
                      size="sm"
                      onClick={() => onClickAven(avenants[dataIndex])}
                      data-tut="reactour__3"
                    >
                      
                      <CTooltip content=" Afficher détails">
                        <CIcon name="cil-magnifying-glass"></CIcon>
                      </CTooltip>
                    </CButton>
                  </Link>
                  &nbsp; &nbsp;
             
                
                  {avenants[dataIndex].traiter ? (
                    <CButton
                      variant="outline"
                      color="danger"
                      size="sm"
                      onClick={() => {
                        Download(avenants[dataIndex].pathAvenant);
                      }}
                    >
                      <CTooltip content="Télécharger Avenant">
                        <CIcon name="cil-save"></CIcon>
                      </CTooltip>
                      &nbsp;
                    </CButton>
                  ) : (
                    <CButton
                      variant="outline"
                      color="danger"
                      size="sm"
                      disabled
                    >
                      <CTooltip content="Télécharger Avenant">
                        <CIcon name="cil-save"></CIcon>
                      </CTooltip>
                    </CButton>
                  )}

              &nbsp; &nbsp;


              {avenants[dataIndex].pathSignedAvenant !== null ? (
                  <CButton
                      variant="outline"
                      color="success"
                      size="sm"
                      onClick={() => {
                        DownloadSignedAvenant(avenants[dataIndex].pathSignedAvenant);
                      }}
                  >
                    <CTooltip content="Télécharger Avenant Signé">
                      <CIcon name="cil-save"></CIcon>
                    </CTooltip>
                    &nbsp;
                  </CButton>
              ) : (
                  <>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  </>
              )}
               
          
            </>
          );
        },
      },
    },
  ];
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

  const onClickAven = (i) => {
    dispatch(getEtudiant(i.avenantPK.conventionPK.idEt));
    dispatch(selectAvenant(i));
    document.body.style.overflowY = "auto";
  };
  const Download = (p) => {
    console.log('--------------------> 19.09.22' + p);
    axios
      .get(
        `${process.env.REACT_APP_API_URL}` +
          "encadrement/download?fileName=" +
          encodeURIComponent(encodeURIComponent(p)),

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

  const DownloadSignedAvenant = (p) => {
    console.log('--------------------> 19.09.22' + p);
    let encodedURL = encodeURIComponent(encodeURIComponent(p));
    axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
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

  return (
    <>
      <Tour
        steps={steps}
        isOpen={isTourOpen}
        onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
        onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
        onRequestClose={() => setIsTourOpen(false)}
      />
      {avenantsstatus === "loading" || avenantsstatus === "noData" ? (
        <>
          <CRow>
            <CCol xs="12">
              <center>
                <br/><br/>
                <span className="waitIcon" />
                <br></br>
              </center>
            </CCol>
          </CRow>
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
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Avenants</span>
                  </CCol>
                </CRow>
                <br/>
                <CCardBody data-tut="reactour__1">
                  {avenants ? (
                      <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
                      data={avenants}
                      columns={columnsAvenants}
                      options={options}
                    /></MuiThemeProvider>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
                      //data={avenants}
                      columns={columnsAvenants}
                      options={options}
                    /></MuiThemeProvider>
                  )}
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
          {avenants ? (
              ""
          ) : (
            <></>
          )}
        </>
      )}
    </>
  );
};

export default AvenantsManage;
