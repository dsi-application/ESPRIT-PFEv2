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

import React, { useState ,useEffect} from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchDemandesAnnulationConventions,
  fetchFichebydepInc,
  selectConventions,
  selectDemandesAnnulationConventions,
  selectNbrDemandesAnnulationConvention,
  selectNbrDemandesAnnulationConventionNotTreated,
  selectNbrDepositedConventions,
  selectNbrValidatedConventions
} from "../../redux/slices/ConventionSlice";
import { selectConvention } from "../../redux/slices/ConventionSlice";
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
import TheSidebar from "../../containers/TheSidebar";
import {selectNbrRefusedReports} from "../../redux/slices/DepotSlice";
const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver La 
    liste des conventions `,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: () => (
      <Text>
        Içi, vous pouvez interpréter l'état de la convention :
        <strong>DEPOSEE - TRAITEE - ANNULEE</strong>.
      </Text>
    ),
  },

  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
      <Text>
        Pour voir les détails de la convention et la traiter cliquer sur ce
        bouton : <strong> Afficher détails</strong>.
      </Text>
    ),
  },
];

export const getBadge = (traiter) => {
  switch (traiter) {
    case "03":
      return "success";
    case "04":
      return "danger";
    default:
      return "dark";
  }
};
export const getEtat = (etat) => {
  switch (etat) {
    case "03":
      return "TRAITEE";
    case "04":
      return "EN ATTENTE";
    default:
      return "PAS ENCORE";
  }
};
const ConventionsDemandesAnnulation = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [demandesAnnulationConventions, err] = useSelector(selectDemandesAnnulationConventions);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const DemandesAnnulationConventionsstatus = useSelector(
    (state) => state.persistedReducer.conventions.demandesAnnulationConventions
  );
  const dispatch = useDispatch();
  const columnsConventions = [
    {
      name: "conventionPK.idEt",
      label: "Identifiant Étudiant",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "conventionPK.dateConvention",
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
      name: "dateDebut",
      label: "Date Début",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "dateFin",
      label: "Date Fin",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "traiter",
      label: "État",
      options: {
        filter: true,
        sort: true,
        customBodyRender: (e) => {
          return (
            <CBadge data-tut="reactour__2" color={getBadge(e)}>
              {getEtat(e)}
            </CBadge>
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
              <td className="py-2" data-tut="reactour__3">
                <Link to={"/DemandeAnnulationConventionDetails"}>
                  <CButton
                    variant="outline"
                    color="dark"
                    size="sm"
                    onClick={() => onClickConv(demandesAnnulationConventions[dataIndex])}
                  >
                    <CTooltip content=" Afficher Détails">
                      <CIcon name="cil-magnifying-glass"></CIcon>
                    </CTooltip>
                  </CButton>
                </Link>
              </td>
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
          padding: "0px 0px 0px 20px",
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

  const onClickConv = (i) => {
    dispatch(getEtudiant(i.conventionPK.idEt));
    dispatch(selectConvention(i));
    document.body.style.overflowY = "auto";
  };

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

        // let a = document.createElement("a");
        // a.href = url;
        // a.download = p.substring(p.lastIndexOf("/") + 1);
        // console.log(url);
        window.open(url);
        // a.click();
      });
  };

  const [nbDemandesAnnulationConvention, errnir] = useSelector(selectNbrDemandesAnnulationConventionNotTreated);
  const [nbDepositedConventions, errndc] = useSelector(selectNbrDepositedConventions);
  const [nbValidatedConventions, errnvc] = useSelector(selectNbrValidatedConventions);
  
  useEffect(() => {
    dispatch(fetchDemandesAnnulationConventions())
  }, [demandesAnnulationConventions]);

  return (
    <>

      <TheSidebar dataDAC={nbDemandesAnnulationConvention} dataDC={nbDepositedConventions} dataVC={nbValidatedConventions}/>

      <Tour
        steps={steps}
        isOpen={isTourOpen}
        onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
        onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
        onRequestClose={() => setIsTourOpen(false)}
      />
      {DemandesAnnulationConventionsstatus === "loading" || DemandesAnnulationConventionsstatus === "noData" ? (
        <div>
          <CRow>
            <CCol md="12">
              <center>
                <br/><br/>
                <span className="waitIcon" />
                <br></br>
              </center>
            </CCol>
          </CRow>
        </div>
      ) : (
        <>
          
          <CRow>
            <CCol>
              <CCard data-tut="reactour__1">


                <CRow>
                  <CCol xs="12">
                    <br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>
                      Liste des demandes Annulations Conventions</span>
                  </CCol>
                </CRow>
                <br/>

                <CCardBody>
                  {demandesAnnulationConventions ? (
                    <MuiThemeProvider theme={theme}>
                      <MUIDataTable
                        data={demandesAnnulationConventions}
                        columns={columnsConventions}
                        options={options}
                      />
                    </MuiThemeProvider>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      columns={columnsConventions}
                      options={options}
                    /> </MuiThemeProvider>
                  )}
                </CCardBody>
              </CCard>
            </CCol>
          </CRow>
          {demandesAnnulationConventions ? (
            ""
          ) : (
            <></>
          )}
        </>
      )}
    </>
  );
};

export default ConventionsDemandesAnnulation;
