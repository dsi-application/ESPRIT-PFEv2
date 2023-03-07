import React, { useState } from "react";
import {
  fecthAvenants,
  fecthConventions,
  fecthFichePFEs,
  getEtatFiche,
  selectAvenants,
  selectConventions,
  selectFiche,
  selectFiches,
  selectStudents,
  unselectAvenants,
  unselectConventions,
} from "../../redux/slices/StudentSlice";
import { useDispatch, useSelector } from "react-redux";
import {
  CBadge,
  CCardBody,
  CButton,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CCard,
  CCardHeader,
  CRow,
  CCol,CTooltip
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import Moment from "moment";
import { Link } from "react-router-dom";
import { ExportCSV } from "../../utils/ExcportCSV";
import { fecthEvaluations } from "../../redux/slices/EvaluationSlice";
import { getEtudiant } from "../../redux/slices/FichePFESlice";
import {
  fetchEntreprises,
  fetchRdvPedas,
  unselectRdvPedaList,
} from "../../redux/slices/RDVPedaSlice";
import MUIDataTable from "mui-datatables";
import "moment/locale/fr";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import { createMuiTheme } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core";

import {fetchVisitsEtudiant} from "../../redux/slices/VisteSlice";
const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver la liste des étudiants que vous allez les encadrer.`,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: `Vous pouvez l'exporter en fichier XLSX`,
  },
  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
      <Text>
        Si vous voulez aller consulter les conventions de votre
        étudiants,cliquer sur le bouton.
        <strong>
          Consulter l'historique des <b>Conventions</b>
        </strong>
        .
      </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__4"]',
    content: () => (
      <Text>
        Si vous voulez aller consulter les avenants de votre étudiants,cliquer
        sur le bouton.
        <strong>
          Consulter l'historique des <b>Avenants</b>
        </strong>
        .
      </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__5"]',
    content: () => (
      <Text>
        Si vous voulez aller consulter les fiches PFE de votre étudiants,cliquer
        sur le bouton.
        <strong>
          Consulter l'historique des<b> Fiches PFEs</b>
        </strong>
        .
      </Text>
    ),
  },
];
export const etat = (e) => {
  if (e === "02") {
    return (
      <p>
        <CBadge color="info" className="float-center">
          DEPOSEE
        </CBadge>

      </p>
    );
  }
  if (e === "03") {
    return (
      <p>
        <CBadge color="success" className="float-center">
          VALIDEE
        </CBadge>

      </p>
    );
  }
  if (e === "04") {
    return (
      <p>
        <CBadge color="danger" className="float-center">
          REFUSEE
        </CBadge>

      </p>
    );
  }
  if (e === "05") {
    return (
      <p>
        <CBadge color="warning" className="float-center">
          ANNULEE
        </CBadge>
      </p>
    );
  }
  if (e === "06") {
    return (
      <p>
        <CBadge color="primary" className="float-center">
          A_SOUTENIR
        </CBadge>
      </p>
    );
  }
  if (e === "07") {
    return (
      <p>
        <CBadge color="success" className="float-center">
          SOUTENUE
        </CBadge>
      </p>
    );
  }
  if (e === "08") {
    return (
        <p>
          <CBadge color="success" className="float-center">
            PLANIFIÉE
          </CBadge>
        </p>
    );
  }
};

const StudentSupervision = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const dispatch = useDispatch();
  const [students, err] = useSelector(selectStudents);
  const studentsStatus = useSelector(
    (state) => state.persistedReducer.students.studentsStatus
  );
  const [fichlists, errf] = useSelector(selectFiches);
  const [conventionlists, errc] = useSelector(selectConventions);
  const [avenantlists, erra] = useSelector(selectAvenants);
  const [largeC, setLargeC] = useState(false);
  const [largeF, setLargeF] = useState(false);
  const [largeA, setLargeA] = useState(false);
  const [responsive, setResponsive] = useState("vertical");
  const [tableBodyHeight, setTableBodyHeight] = useState("300");
  const [tableBodyMaxHeight, setTableBodyMaxHeight] = useState("");

  let listtoExport = [];
  if (students.length > 0) {
    students.map((s) => {
      var obj = {
        "Identifiant Etudiant": s.idEt,
        "Nom Etudiant": s.prenomet,
        "Prénom Etudiant": s.nomet,
        "Numéro Téléphone Etudiant": s.telet,
        "Classe Etudiant": s.classe,
        "Année universitaire Associée": s.anneeDebInscription,
      };
      listtoExport.push(obj);
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
  const toggle = (item) => {
    dispatch(fecthFichePFEs(item));
    setLargeF(!largeF);

    document.body.style.overflowY = "auto";
    setIsTourOpen(false);
  };

  const toggle2 = (item) => {
    dispatch(fecthConventions(item));
    setLargeC(!largeC);

    document.body.style.overflowY = "auto";
    setIsTourOpen(false);
  };
  const toggle3 = (item) => {
    dispatch(fecthAvenants(item));
    setLargeA(!largeA);

    document.body.style.overflowY = "auto";
    setIsTourOpen(false);
  };
  const onClickb = (f, id, date) => {
    sessionStorage.setItem("DataIndex", 0);
    dispatch(fetchEntreprises(id))
    dispatch(fetchVisitsEtudiant(id, date));
    dispatch(selectFiche(f));
    dispatch(getEtudiant(id));
    dispatch(getEtatFiche(f.etatFiche));
    dispatch(fecthEvaluations(id, date));
    dispatch(unselectRdvPedaList());
    dispatch(fetchRdvPedas(id, date));
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
      label: "Numéro Téléphone",
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
      label: "Année Universitaire Associée",
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

                  <CButton data-tut="reactour__3"
                    variant="outline"
                    color="danger"
                    size="sm"
                    onClick={() => toggle2(students[dataIndex].idEt)}
                  >
                  <CTooltip content=" Consulter l'historique des Conventions">
                  <CIcon name="cil-list-rich"></CIcon>
                </CTooltip>
                  </CButton>

                  &nbsp;

                  <CButton data-tut="reactour__4"
                    variant="outline"
                    color="warning"
                    size="sm"
                    onClick={() => toggle3(students[dataIndex].idEt)}
                  >

<CTooltip content="Consulter l'historique des Avenants">
                  <CIcon name="cil-layers"></CIcon>
                </CTooltip>

                  </CButton>
                  &nbsp;
                  <CButton data-tut="reactour__5"
                    variant="outline"
                    color="success"
                    size="sm"
                    onClick={() => toggle(students[dataIndex].idEt)}
                  >
                     <CTooltip content="Consulter l'historique des Fiches PFEs">
                  <CIcon name="cil-map"></CIcon>
                </CTooltip>

                  </CButton>


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
  const etatConv = (e) => {
    if (e === "01") {
      return (
        <>
          <h5>
            <CBadge color="info" className="float-right">
              DEPOSEE
            </CBadge>
            <br></br>
            <p className="float-right">
              Le service des stages n'a pas encore traité la Convention.
            </p>
          </h5>
          <br></br>
        </>
      );
    }
    if (e === "02") {
      return (
        <h5>
          <CBadge color="success" className="float-right">
            TRAITEE
          </CBadge>
          <br></br>
        </h5>
      );
    }
    if (e === "03") {
      return (
        <h5>
          <CBadge color="danger" className="float-right">
            ANNULEE
          </CBadge>
        </h5>
      );
    }
  };

  const cancelconventions = () => {
    dispatch(unselectConventions());
    setLargeC(!largeC);
  };
  const cancelavenants = () => {
    dispatch(unselectAvenants());
    setLargeA(!largeA);
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
      {studentsStatus === "loading" || studentsStatus === "noData" ? (
        <>
          <div style={{ textAlign: "center" }}>
            <br/><br/>
            <span className="waitIcon"/>

            <br/><br/>
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
                    <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Liste des Étudiants à encadrer</span>
                  </CCol>
                </CRow>
                <br/>
                <CCardHeader>
                </CCardHeader>
                <CCardBody data-tut="reactour__1">
                  {students ? (
                    <MuiThemeProvider theme={theme}>
                    <MUIDataTable
                      title={""}
                      data={students}
                      columns={columns}
                      options={options}
                    /></MuiThemeProvider>
                  ) : (
                    <MuiThemeProvider theme={theme}>

                    <MUIDataTable
                      title={""}
                      // data={students}
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
      <CModal show={largeA} onClose={() => cancelavenants()} size="lg">
        <CModalHeader closeButton>
          <CModalTitle>&nbsp;
            <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                  Historiques des Avenants</span></CModalTitle>
        </CModalHeader>
        <CModalBody>
          {avenantlists.length === 0 ? (
            <b>
              <h6 style={{ textAlign: "center" }}>
                
                Cet Étudiant n'a pas encore d'Avenant.
              </h6>
            </b>
          ) : (
            <>
              {avenantlists.length &&
                avenantlists.map((a, i) => (
                  <CCard key={i}>
                    <CCardHeader>
                      {a.traiter ? (
                        <CBadge className="float-right" color="success">
                          <h6>TRAITE</h6>
                        </CBadge>
                      ) : (
                        <CBadge className="float-right" color="danger">
                          <h6>Traitement en cours</h6>
                        </CBadge>
                      )}
                      <br/><br/>

                      <CRow>
                        <CCol md="3">
                          <b>Date dépot Avenant: </b>
                        </CCol>
                        <CCol md="9">
                          {Moment(a.avenantPK.dateAvenant).format("LLLL")}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                          <b>Nouvelle Date Début: </b>
                        </CCol>
                        <CCol md="9">
                          {Moment(a.dateDebut).format("DD/MM/YYYY")}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                          <b>Nouvelle Date Fin: </b>
                        </CCol>
                        <CCol md="9">
                          {Moment(a.dateFin).format("DD/MM/YYYY")}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                          <b>Date Signature Convention: </b>
                        </CCol>
                        <CCol md="9">
                          {Moment(a.dateSignatureConvention).format("DD/MM/YYYY")}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="3">
                          <b>Responsable Entreprise:</b>
                        </CCol>
                        <CCol md="9">
                          {a.responsableEntreprise}
                        </CCol>
                      </CRow>
                    </CCardHeader>
                  </CCard>
                ))}
            </>
          )}
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => cancelavenants()}>
            EXIT
          </CButton>
        </CModalFooter>
      </CModal>
      <CModal show={largeC} onClose={() => cancelconventions()} size="lg">
        <CModalHeader closeButton>
          <CModalTitle>
            <CRow>
              <CCol xs="12">
                &nbsp;
                <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                  Historiques des Conventions</span>
              </CCol>
            </CRow>
            </CModalTitle>
        </CModalHeader>
        <CModalBody>
          {conventionlists.length === 0 ? (
            <b>
              <h6 style={{ textAlign: "center" }}>
                
                Cet Étudiant n'a pas encore de Convention.
              </h6>
            </b>
          ) : (
            <>
              {conventionlists.length &&
                conventionlists.map(
                  (
                    {
                      dateDebut,
                      conventionPK,
                      traiter,
                      dateFin,
                      responsable,
                      mail,
                      address,
                      telephone,
                      entrepriseAccueilConvention,
                      encadrantEntreprises,
                    },
                    i
                  ) => (
                    <CCard key={i}>
                      <CCardHeader>
                        {etatConv(traiter)}
                        <br></br>
                        <CRow>
                          <CCol xs="12" sm="4" md="5">
                            <b> Identifiant Étudiant : </b> {conventionPK.idEt}
                            <br></br>
                            <b> E-Mail : </b> {mail}
                            <br></br><br></br>
                            <b>Date Début Stage : </b>
                            {Moment(dateDebut).format("DD/MM/YYYY")} <br></br>
                            <b>Date Fin Stage : </b>
                            {Moment(dateFin).format("DD/MM/YYYY")} <br></br>
                          </CCol>
                          <CCol xs="12" sm="8" md="7">
                            <b style={{ color: "#cc0000" }}>
                              Coordonnées de l'Entreprise d'Accueil:
                            </b>
                            <br></br>

                            <CRow>
                              <CCol md="3">
                                <b> Addresse: </b>
                              </CCol>
                              <CCol md="9">
                                {entrepriseAccueilConvention
                                    ? entrepriseAccueilConvention.address
                                   : "--"}
                              </CCol>
                            </CRow>

                            <CRow>
                              <CCol md="3">
                                <b>E-Mail:</b>
                              </CCol>
                              <CCol md="9">
                                {entrepriseAccueilConvention
                                    ? entrepriseAccueilConvention.addressMail
                                   : "--"}
                              </CCol>
                            </CRow>

                            <CRow>
                              <CCol md="3">
                                <b> Responsable: </b>
                              </CCol>
                              <CCol md="9">
                                {responsable ? responsable : "--"}
                              </CCol>
                            </CRow>

                            <CRow>
                              <CCol md="3">
                                <b> Libellé: </b>
                              </CCol>
                              <CCol md="9">
                                {entrepriseAccueilConvention
                                    ? entrepriseAccueilConvention.designation
                                   : "--"}
                              </CCol>
                            </CRow>

                            <CRow>
                              <CCol md="3">
                                <b> Téléphone: </b>
                              </CCol>
                              <CCol md="9">
                                {
                                  entrepriseAccueilConvention.telephone !== "--------" ?
                                      <>{entrepriseAccueilConvention.telephone}</>
                                      :
                                      <>--</>
                                }
                              </CCol>
                            </CRow>

                            <CRow>
                              <CCol md="3">

                                <b> Fax: </b>
                              </CCol>
                              <CCol md="9">
                                {
                                  entrepriseAccueilConvention.fax !== "--------" ?
                                      <>{entrepriseAccueilConvention.fax}</>
                                      :
                                      <>--</>
                                }
                              </CCol>
                            </CRow>

                            <CRow>
                              <CCol md="3">
                                <b> Siége Sociale: </b>
                              </CCol>
                              <CCol md="9">
                                {
                                  entrepriseAccueilConvention.siegeSocial !== "---" ?
                                      <>{entrepriseAccueilConvention.siegeSocial}</>
                                      :
                                      <>--</>
                                }
                              </CCol>
                            </CRow>

                          </CCol>
                        </CRow>
                      </CCardHeader>
                    </CCard>
                  )
                )}
            </>
          )}
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => cancelconventions()}>
            EXIT
          </CButton>
        </CModalFooter>
      </CModal>
      <CModal show={largeF} onClose={() => setLargeF(!largeF)} size="lg">
        <CModalHeader closeButton>
          <CModalTitle>
            &nbsp;
            <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>
                  Historiques des Fiches PFE</span>
          </CModalTitle>
        </CModalHeader>
        <CModalBody>
          {fichlists.length === 0 ? (
            <b>
              <h6 style={{ textAlign: "center" }}>
                Cet Étudiant n'a pas encore de Plan Travail.
              </h6>
            </b>
          ) : (
            <>
              {fichlists.length &&
                fichlists.map((f, i) => (
                  <CCard key={i}>
                    <CCardHeader>
                      <div className="card-header-actions">
                        {etat(f.etatFiche)}
                      </div>
                      <span style={{color: "#808080", fontSize: "12px", fontWeight: "bold"}}>
                        {f.titreProjet}
                      </span>
                      <br/>

                      <b>Identifiant Étudiant: </b>
                      {f.idFichePFE.idEt} <br></br>
                      <b> Date Dépôt Fiche: </b>
                      {Moment(f.idFichePFE.dateDepotFiche)
                        .locale("fr")
                        .format("LLLL")}
                      <br></br>
                      <br></br>
                      <Link to={"/PFESheetDetails"}>
                        <CButton
                          size="sm"
                          className="float-right"
                          style={{ backgroundColor: "#343a40", color: "white" }}
                          onClick={() =>
                            onClickb(
                              f,
                              f.idFichePFE.idEt,
                              f.idFichePFE.dateDepotFiche
                            )
                          }
                        >
                          
                          Consulter détails de la fiche
                        </CButton>
                      </Link>
                    </CCardHeader>
                  </CCard>
                ))}
            </>
          )}
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" onClick={() => setLargeF(!largeF)}>
            Ok
          </CButton>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default StudentSupervision;
