import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSpinner,
} from "@coreui/react";
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import "moment/locale/fr";
import MUIDataTable from "mui-datatables";
import {
  getEancadrant,
  selectEncadrant,
  selectOptionStudents,
} from "../../redux/slices/EncadrementSlice";
import CIcon from "@coreui/icons-react";
import { ExportCSV } from "../../utils/ExcportCSV";
import ReactTextTransition from "react-text-transition";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";
const AffectationManage = () => {
  const dispatch = useDispatch();
  const ens = useSelector(selectEncadrant);
  const [studentsbyOption, err] = useSelector(selectOptionStudents);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");
  const optionStudentsStatus = useSelector(
      (state) => state.persistedReducer.encadrement.optionStudentsStatus
  );
  // console.log('ens',ens)
  const [largeC, setLargeC] = useState(false);
  const theme = createMuiTheme({
    overrides: {
      MuiTableCell: {
        root: {
          padding: "3px 3px 3px 10px",
        },
      },
    },
  });
  let listtoExport = [];
  if (studentsbyOption.length !== 0) {
    studentsbyOption.map((s) => {
      var obj = {
        "Identifiant Etudiant": s.idEt,
        "Nom Etudiant": s.prenomet,
        "Prénom Etudiant": s.nomet,
        "Numéro Téléphone Etudiant": s.telet,
        "Classe Etudiant": s.classe,
        "Année universitaire Associée": s.anneeDebInscription,
        "Encadrant pédagogique": s.encadrant,
      };
      listtoExport.push(obj);
    });
  }
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
      label: "Numéro Téléphone",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "anneeDebInscription",
      label: "Année Universitaire Associée",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "classe",
      label: "Classe Courante",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "Encadrant Académique",
      label: "Encadrant Académique",
      options: {
        filter: true,
        sort: true,
        customBodyRenderLite: (dataIndex) => {
          return (
              <>
                <center>
                  <CButton variant="outline"
                           color="danger"
                           size="sm"
                           onClick={() => toggle(studentsbyOption[dataIndex].idEt)}>
                    <CIcon name="cil-magnifying-glass" />
                  </CButton>
                </center>
              </>
          );
        },
      },
    },
  ];

  const toggle = (id) => {
    setLargeC(!largeC);
    dispatch(getEancadrant(id));
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
      {optionStudentsStatus === "loading" ||
      optionStudentsStatus === "noData" ? (
        <>
          <CRow>
            <CCol md="12">
              <center>
                <br/><br/><br/><br/>
                <span className="waitIcon"/>
                <br/><br/>
              </center>
            </CCol>
          </CRow>
          <br></br>
        </>
      ) : (
        <>
          <CRow>
            <CCol>
              <CCard>

                <CCardBody>
                  {studentsbyOption ? (
                    <>
                      <CRow>
                        <CCol xs="12">
                          &nbsp;
                          <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                            Liste des Étudiants en 5éme année</span>
                        </CCol>
                      </CRow>
                      <br/><br/>
                      <MuiThemeProvider theme={theme}>
                      <MUIDataTable
                        data={studentsbyOption}
                        columns={columns}
                        options={options}
                      />
                     </MuiThemeProvider>
                    </>
                  ) : (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      //   data={studentsbyOption}
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

      <CModal show={largeC} onClose={() => setLargeC(!largeC)} size="lg">
        <CModalHeader closeButton>
          <CModalTitle>
            <CRow>
              <CCol xs="12">
                &nbsp;
                <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                  Détails Encadrant Académique
                </span>
              </CCol>
            </CRow>
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          <CCard>
            <CCardHeader>
              <br></br>
              <CRow>
                {
                  ens.idEns ?
                      <CCol xs="12" sm="6" md="6">
                        <h5>
                          <ReactTextTransition text={ens.nomEns} springConfig={{tension: 10, friction: 10}}/>
                        </h5>
                        <br/>

                        <CRow>
                          <CCol md="5">
                            <b>Identifiant Encadrant : </b>
                          </CCol>
                          <CCol md="7">
                            {ens.idEns}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol md="5">
                            <b>E-Mail : </b>
                          </CCol>
                          <CCol md="7">
                            {ens.mailEns}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol md="5">
                            <b>Num. Téléphone : </b>
                          </CCol>
                          <CCol md="7">
                            {ens.tel1 ? <>{ens.tel1}</> : <>--</>}
                            &nbsp; / &nbsp;
                            {ens.tel2 ? <>{ens.tel2}</> : <>--</>}
                          </CCol>
                        </CRow>
                      </CCol>
                      :
                      <CCol xs="12" sm="6" md="12">
                        <center>
                          Cet Étudiant n'a pas encore un Encadrant Académique.
                        </center>
                      </CCol>
                }
              </CRow>
              <br/>
            </CCardHeader>
          </CCard>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => setLargeC(!largeC)}>
            EXIT
          </CButton>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default AffectationManage;
