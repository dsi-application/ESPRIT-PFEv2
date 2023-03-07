import CIcon from "@coreui/icons-react";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import axios from "axios";
import Typography from "@material-ui/core/Typography";
import "../css/style.css";
import {
  CAlert,
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CCollapse,
  CDataTable,
  CListGroup,
  CListGroupItem,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CNav,
  CNavItem,
  CNavLink,
  CRow,
  CSpinner,
  CTabContent,
  CTabPane,
  CTabs,
} from "@coreui/react";
import moment from "moment";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import {
  selectetudiant,
  selectSelectedfichePFE,
  updateFiche,
} from "../../redux/slices/FichePFESlice";
import { getEtatFiche, selectEtatFiche } from "../../redux/slices/StudentSlice";
import { queryApi } from "../../utils/queryApi";
import { makeStyles } from "@material-ui/core/styles";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import "moment/locale/fr";
import { Wave, Random } from "react-animated-text";

import { fetchEtatValidationFiche } from "../../redux/slices/ListingSlice";
const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
  },
}));
const stepsTour = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Tous ça commence avec les détails de l'étudiant.`,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: `Içi, Vous allez trouver les détails de la fiches de projet de fin d'études`,
  },
  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
        <Text>
          Si vous voulez aller consulter les documents déposés de votre étudiants,
          consultez cette partie: <strong>Documents déposés</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__4"]',
    content: () => (
        <Text>
          Si vous voulez aller consulter ou planifier les rendez-vous de suivi
          pédagogique pour votre étudiant, consultez cette partie:
          <strong>Rendez-vous de suivi</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__5"]',
    content: () => (
        <Text>
          Si vous voulez aller consulter ou planifier la visite du mis-parcous
          pour votre étudiant, consultez cette partie:
          <strong>Visite Stagiaire</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__6"]',
    content: () => (
        <Text>
          <strong>Enfin </strong>Evaluation de votre étudiant ,sera la dernier
          partie: <strong>Evaluation de l'étudiant</strong>.
        </Text>
    ),
  },
];
const FichePFE = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const dispatch = useDispatch();
  const classes = useStyles();
  const history = useHistory();
  const Fiche = useSelector(selectSelectedfichePFE);
  const Etu = useSelector(selectetudiant);
  const [danger, setDanger] = useState(false);
  const [success, setSuccess] = useState(false);

  const [details, setDetails] = useState([]);
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({ visible: false, message: "" });
  const [etatFiche, errEtat] = useSelector(selectEtatFiche);
  // console.log('Fiche1',Fiche)
  // console.log('Fiche2',Fiche.rdvSuiviStages)
  // // console.log('Fiche.3',Fiche.rdvSuiviStages.length)

  useEffect(() => {
    if (etatFiche.etat === null) {
      dispatch(getEtatFiche(Fiche.fichePFE.etatFiche));
    }
  }, [Fiche, dispatch]);
  const Download = (p) => {
    axios
        .get(
            `${process.env.REACT_APP_API_URL}` +
            "encadrement/download?fileName=" +
            p,

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
  const handleRefuse = async () => {
    setShowLoader(true);
    const [res, err] = await queryApi(
        "responsableStage/updateFichePFEREFUSEE?idET=" +
        Fiche.idEt +
        "&dateFiche=" +
        Fiche.fichePFE.idFichePFE.dateDepotFiche,
        {},
        "PUT",
        false
    );
    if (err) {
      setShowLoader(false);
      setError({
        visible: true,
        message: JSON.stringify(err.errors, null, 2),
      });
    } else {
      dispatch(updateFiche(res));
      dispatch(fetchEtatValidationFiche(""));
      history.push("/FichePFEManagement");
    }
  };
  const getEvalModule = (note) => {
    if (36 <= note) return "A";
    if (28 <= note && note <= 35) return "B";
    if (12 <= note && note <= 27) return "C";
    if (note <= 11) return "D";
  };
  const getCompetenceColor = (color) => {
    switch (color) {
      case "A":
        return "Purple";
      case "B":
        return "DeepPink";
      case "C":
        return "DarkRed";
      case "D":
        return "Grey";
      default:
        return "info";
    }
  };
  const getCompetence = (code) => {
    switch (code) {
      case "01":
        return "Comprendre et intégrer les enjeux et la stratégie de l’entreprise";
      case "02":
        return "Analyser et/ou chercher les solutions à un problème de conception, de réalisation, d’amélioration de produit, de systèmes ou de services au sein d’une organisation";
      case "03":
        return "Conduire un projet de création, de conception, de réalisation, d’amélioration de produit, de système ou deservice";
      case "04":
        return "Mettre en œuvre sa maîtrise scientifique ou technique au sein de l’organisation";
      case "05":
        return "Organiser sa mission et manager les ressources";
      case "06":
        return "Qualité et présentation du document";
      case "07":
        return "Qualité de la présentation orale";
      case "08":
        return "Qualité de l’argumentation";
      default:
        return "primary";
    }
  };
  const fields = [
    {
      key: "dateRendezVous",
      _style: { width: "10%" },
      label: "Date Rendez-Vous",
    },
    { key: "heureDebut", _style: { width: "10%" }, label: "Horaire" },
    { key: "lieuRDV", _style: { width: "10%" }, label: "Lieu" },
    { key: "etat", _style: { width: "7%" }, label: "État" },
    { key: "typeRDV", _style: { width: "7%" }, label: "Type" },
    {
      key: "show_details",
      label: "",
      _style: { width: "10%" },
    },
  ];
  const getBadge = (etat) => {
    switch (etat) {
      case "02":
        return "success";
      case "E":
        return "info";
      case "03":
        return "warning";
      case "01":
        return "danger";
      default:
        return "primary";
    }
  };
  const toggleDetails = (index) => {
    const position = details.indexOf(index);
    let newDetails = details.slice();
    if (position !== -1) {
      newDetails.splice(position, 1);
    } else {
      newDetails = [...details, index];
    }
    setDetails(newDetails);
  };
  const getBadge2 = (etat) => {
    switch (etat) {
      case "P":
        return "success";
      case "D":
        return "info";
      default:
        return "primary";
    }
  };
  const getType = (etat) => {
    switch (etat) {
      case "P":
        return "Présentiel";
      case "D":
        return "En Ligne";
      default:
        return "rien";
    }
  };
  const getEtat = (etat) => {
    switch (etat) {
      case "02":
        return "Realisé";
      case "E":
        return "En attente";
      case "03":
        return "Annulé";
      case "01":
        return "Raté";
      default:
        return "rien";
    }
  };
  const handleValidate = async () => {
    setShowLoader(true);
    const [res, err] = await queryApi(
        "responsableStage/updateFichePFEVALIDEE?idET=" +
        Fiche.fichePFE.idFichePFE.idEt +
        "&dateFiche=" +
        Fiche.fichePFE.idFichePFE.dateDepotFiche,
        {},
        "PUT",
        false
    );
    if (err) {
      setShowLoader(false);
      setError({
        visible: true,
        message: JSON.stringify(err.errors, null, 2),
      });
    } else {
      dispatch(updateFiche(res));
      dispatch(fetchEtatValidationFiche(""));
      history.push("/FichePFEManagement");
    }
  };
  const displayVisit = (V) => {
    if (V.visiteStagiaire === null) {
      return (
          <>
            <CCardHeader>
              <b>
                L'Encadrant Pédagogique n'a pas encore planifié une Visite
                Stagiaire pour cet Étudiant.
              </b>
            </CCardHeader>
            <CCardBody></CCardBody>
          </>
      );
    } else {
      return (
          <>
            
            <CCardBody>
              <CCol xs="12" sm="12" md="12">
                <CCard color="gradient-secondary">
                  <CCardHeader>
                    <b>
                      
                      {moment(
                          V.visiteStagiaire.visiteStagiairePK.dateVisite
                      ).format("MMMM Do YYYY")}
                    </b>
                    {V.visiteStagiaire.typeVisite === "P" ? (
                        <p>
                          Cette visite va être <span className="redMark">présentielle</span> &nbsp;à&nbsp;
                        <ins>{V.visiteStagiaire.lieuVisite}</ins>
                        </p>
                    ): (
                        <p>
                          Cette visite va être <ins>en ligne </ins> &nbsp;à&nbsp;
                      <ins>{V.visiteStagiaire.lieuVisite}</ins>
                        </p>
                    )}
                  </CCardHeader>
                  <CCardBody>
                    <b>Observation: </b>
                    <div
                        style={{
                          wordWrap: "break-word",
                        }}
                        className="editor"
                        dangerouslySetInnerHTML={{
                          __html: V.visiteStagiaire.observation,
                        }}
                    ></div>
                    <br></br> <br></br>
                  </CCardBody>
                </CCard>
              </CCol>
            </CCardBody>
          </>
      );
    }
  };
  const displayTraitementBTN = (F) => {
    if (F.fichePFE.etatFiche === "02") {
      return (
          <CRow className="align-items-center">
            <CCol col="2" className="text-center mt-3">
              <CButton
                  className=""
                  variant="outline"
                  color="success"
                  size="sm"
                  onClick={() => setSuccess(!success)}
              >
                Valider la fiche <CIcon name="cil-check"></CIcon>
              </CButton>
            </CCol>
            <CCol col="2" className="text-center mt-3">
              <CButton
                  className=""
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => setDanger(!danger)}
              >
                Refuser la fiche <CIcon name="cil-x"></CIcon>
              </CButton>
            </CCol>
          </CRow>
      );
    } else {
      return;
    }
  };
  return (
      <>
        <div>
          <Tour
              steps={stepsTour}
              isOpen={isTourOpen}
              onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
              onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
              onRequestClose={() => setIsTourOpen(false)}
          />
          <CCol xs="12" md="12" className="mb-4">
            <CCard>
              <CCardHeader data-tut="reactour__1">
                <CRow>
                  
                  <CCol xs="12" sm="6" md="6">
                    <span style={{color: "#cc0000", fontSize: "14px", fontWeight: "bold"}}>
                      Détails Plan Travail
                    </span>
                  </CCol>
                  <CCol xs="12" sm="6" md="6">
                    <h6 className="float-right">
                      <b>
                        {Etu.prenomet} {Etu.nomet} &nbsp;&nbsp; {Etu.classe}
                      </b>
                    </h6>
                    <br></br> <br></br>
                    <h6 className="float-right"> {Etu.adressemailesp}</h6>
                  </CCol>
                </CRow>
                <br></br>
                {displayTraitementBTN(Fiche)}
              </CCardHeader>
              <CCardBody>
                <CTabs>
                  <CNav variant="tabs">
                    <CNavItem data-tut="reactour__2">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        
                        Détails Projet
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__3">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Documents Déposés
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__4">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Rendez-Vous de Suivi
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__5">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Visite Stagiaire
                      </CNavLink>
                    </CNavItem>
                    <CNavItem data-tut="reactour__6">
                      <CNavLink style={{ fontWeight: "bold", color: "black" }}>
                        Évaluation de l'Étudiant
                      </CNavLink>
                    </CNavItem>
                  </CNav>
                  <CTabContent>
                    <CTabPane>
                      <br></br>
                      <CCol xs="12" sm="12" md="12">
                        <CCard accentcolor="dark">
                          <CCardHeader>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <span className="redMark">

                                  <Wave style={{fontSize:"50px"}} text={Fiche.fichePFE.titreProjet} effect="fadeOut" effectChange={1.0} />

                                </span>
                                <br/>
                                <span style={{color: "grey", fontSize: "12px", fontWeight: "bold"}}>
                                  Identifiant Étudiant:
                                </span>&nbsp; {Fiche.fichePFE.idFichePFE.idEt}
                                <br/>

                                <span style={{color: "grey", fontSize: "12px", fontWeight: "bold"}}>
                                  Date Dépôt Fiche:
                                </span>&nbsp; {moment(Fiche.fichePFE.idFichePFE.dateDepotFiche)
                                  .locale("fr")
                                  .format("LLLL")}
                                <br/>

                                <span style={{color: "grey", fontSize: "12px", fontWeight: "bold"}}>
                                  État Fiche:
                                </span>&nbsp; <span className="darkMark">
                                {etatFiche.etat}
</span>

                              </CCol>
                            </CRow>
                            <br/>
                          </CCardHeader>
                          <CCardBody>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <span className="primaryMark">Description Projet:</span>
                                <p>
                                  {Fiche.fichePFE.descriptionProjet
                                      ? Fiche.fichePFE.descriptionProjet
                                     : "Pas de decsription"}
                                </p>
                                <span className="infoMark">Problématiques Projet: </span>
                                <CListGroup accent>
                                  {Fiche.fichePFE.problematics?.map((item, i) => (
                                      <CListGroupItem accent="info">
                                        {item.name}
                                      </CListGroupItem>
                                  ))}
                                </CListGroup>
                              </CCol>
                              <CCol xs="12" sm="6" md="6">
                                <span className="secondaryMark">Fonctionnalités Projet:</span>
                                <CListGroup accent>
                                  {Fiche.fichePFE.functionnalities?.map((item, i) => (
                                      <CListGroupItem accent="dark">
                                        {item.name} <br></br> <b>Description: </b>
                                        {
                                          item.description === "null" ?
                                              <>--</>
                                              :
                                              <>{item.description}</>
                                        }

                                      </CListGroupItem>
                                  ))}
                                </CListGroup>
                                <br/>
                                <span className="lightMark">Téchnologies utilisées:</span>
                                <CListGroup accent>
                                  {Fiche.fichePFE.technologies?.map((item, i) => (
                                      <CListGroupItem accent="secondary">
                                        {item.name} <br></br>
                                      </CListGroupItem>
                                  ))}
                                </CListGroup>
                              </CCol>
                            </CRow>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    </CTabPane>

                    <CTabPane>
                      
                      <br></br>
                      <CCol xs="12" sm="12" md="12">
                        <CCard accentcolor="dark">
                          <CCardHeader>
                            <span className="redMark">
                              Journal du Bord & Bilans Périodiques
                            </span>
                          </CCardHeader>
                          <CCardBody>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b>Bilans Périodiques</b>
                                  </CCardHeader>
                                  <CCardBody>
                                    <CListGroup accent>
                                      <CListGroupItem accent="dark">
                                        {Fiche.fichePFE.pathBilan1 ? (
                                            <>
                                              <b>Date Dépôt Premier Bilan: &nbsp;&nbsp;</b>
                                              {moment(Fiche.fichePFE.dateDepotBilan1).format(
                                                  "MM/DD/YYYY"
                                              )}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.fichePFE.pathBilan1);
                                                  }}
                                              >
                                                Télécharger Premier Bilan &nbsp;
                                                &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ): (
                                            "Pas de document déposé pour le Premier Bilan."
                                        )}
                                      </CListGroupItem>
                                      <CListGroupItem accent="secondary">
                                        {Fiche.fichePFE.pathBilan2 ? (
                                            <>
                                              <b>Date Dépôt Deuxième Bilan: </b>&nbsp;&nbsp;
                                              {moment(Fiche.fichePFE.dateDepotBilan2).format(
                                                  "MM/DD/YYYY"
                                              )}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.fichePFE.pathBilan2);
                                                  }}
                                              >
                                                Télécharger le Deuxième Bilan &nbsp;
                                                &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ): (
                                            "Pas de document déposé pour le Deuxième Bilan."
                                        )}
                                      </CListGroupItem>
                                      <CListGroupItem accent="light">
                                        {Fiche.fichePFE.pathBilan3 ? (
                                            <>
                                              <b>Date Dépôt Dernier Bilan: </b>&nbsp;&nbsp;

                                              {moment(Fiche.fichePFE.dateDepotBilan3).format(
                                                  "MM/DD/YYYY"
                                              )}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.fichePFE.pathBilan3);
                                                  }}
                                              >
                                                Télécharger le Dernier Bilan
                                                &nbsp;&nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ): (
                                            "Pas de document déposé pour le Dernier Bilan."
                                        )}
                                      </CListGroupItem>
                                    </CListGroup>
                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol xs="12" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b>Journal du Bord</b>
                                  </CCardHeader>
                                  <CCardBody>
                                    {Fiche.fichePFE.pathJournalStg ? (
                                        <>
                                          <b>Date Dépôt Journal du Bord: </b>&nbsp;&nbsp;

                                          {moment(Fiche.fichePFE.dateDepotJournalStg).format(
                                              "MM/DD/YYYY"
                                          )}
                                          <br/>
                                          <CButton
                                              className="float-left"
                                              variant="outline"
                                              color="dark"
                                              size="sm"
                                              onClick={() => {
                                                Download(Fiche.fichePFE.pathJournalStg);
                                              }}
                                          >
                                            Télécharger la derniere version du
                                            Journal du Bord &nbsp; &nbsp;
                                            <CIcon name="cil-save"></CIcon>
                                          </CButton>
                                        </>
                                    ): (
                                        "Pas de document déposé pour le Journal du Bord."
                                    )}
                                  </CCardBody>
                                </CCard>
                              </CCol>
                            </CRow>
                          </CCardBody>
                        </CCard>

                        <CCard accentcolor="dark">
                          <CCardHeader>
                            <span className="redMark">
                              Rapports PFE & Rapport Anti-Plagiat
                            </span>
                          </CCardHeader>
                          <CCardBody>
                            <CRow>
                              <CCol xs="12" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b> Rapports PFE</b>
                                  </CCardHeader>
                                  <CCardBody>
                                    <CListGroup accent>
                                      <CListGroupItem accent="dark">
                                        {Fiche.fichePFE.pathRapportVersion1 ? (
                                            <>
                                              <b>
                                                Date Dépôt Première Version du Rapport:&nbsp;&nbsp;
                                              </b>
                                              {moment(
                                                  Fiche.fichePFE.dateDepotRapportVersion1
                                              ).format("MM/DD/YYYY")}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(
                                                        Fiche.fichePFE.pathRapportVersion1
                                                    );
                                                  }}
                                              >
                                                Télécharger la Première Version du
                                                Rapport &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ): (
                                            "Pas de document déposé pour la Première Version du Rapport."
                                        )}
                                      </CListGroupItem>
                                      <CListGroupItem accent="secondary">
                                        
                                        {Fiche.fichePFE.pathRapportVersion2 ? (
                                            <>
                                              <b>Date Dépôt Version Finale: </b>&nbsp;&nbsp;
                                              {moment(
                                                  Fiche.fichePFE.dateDepotRapportVersion2
                                              ).format("MM/DD/YYYY")}
                                              <br/>
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(
                                                        Fiche.fichePFE.pathRapportVersion2
                                                    );
                                                  }}
                                              >
                                                Télécharger la derniére version du
                                                Rapport &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ): (
                                            "Pas de document déposé pour la Version Finale."
                                        )}
                                      </CListGroupItem>
                                    </CListGroup>
                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol xs="6" sm="6" md="6">
                                <CCard accentColor="dark">
                                  <CCardHeader>
                                    <b> Rapport Anti-Plagiat </b>
                                  </CCardHeader>
                                  <CCardBody>
                                        {Fiche.fichePFE.pathPlagiat ? (
                                            <>
                                              <b>Date Dépôt Rapport Anti-Plagiat: </b>&nbsp;&nbsp;
                                              {moment(
                                                  Fiche.fichePFE.dateDepotPlagiat
                                              ).format("MM/DD/YYYY")}
                                              <br />
                                              <CButton
                                                  className="float-left"
                                                  variant="outline"
                                                  color="dark"
                                                  size="sm"
                                                  onClick={() => {
                                                    Download(Fiche.fichePFE.pathPlagiat);
                                                  }}
                                              >
                                                Télécharger le Rapport Anti-Plagiat &nbsp; &nbsp;
                                                <CIcon name="cil-save"></CIcon>
                                              </CButton>
                                            </>
                                        ): (
                                            "Pas de document déposé pour le Rapport Anti-Plagiat."
                                        )}
                                  </CCardBody>
                                </CCard>
                              </CCol>
                            </CRow>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    </CTabPane>
                    <CTabPane>
                      <br></br>

                      <CCol xs="12" sm="12" md="12">
                        <CCard accentcolor="dark">
                          <CCardHeader>
                            <span className="redMark">Liste des rendez vous de suivi stagiaire</span>
                            <br/>
                          </CCardHeader>
                          <CCardBody>
                            <CDataTable
                                items={Fiche.fichePFE.rdvSuiviStages}
                                fields={fields}
                                hover
                                tableFilter
                                sorter
                                striped
                                bordered
                                size="sm"
                                itemsPerPageSelect
                                itemsPerPage={5}
                                pagination
                                scopedSlots={{
                                  etat: (item) => (
                                      <td>
                                        <CBadge color={getBadge(item.etat)}>
                                          {getEtat(item.etat)}
                                        </CBadge>
                                      </td>
                                  ),
                                  typeRDV: (item) => (
                                      <td>
                                        <CBadge color={getBadge2(item.typeRDV)}>
                                          {getType(item.typeRDV)}
                                        </CBadge>
                                      </td>
                                  ),
                                  show_details: (item, index) => {
                                    return (
                                        <td className="py-2">
                                          <CButton
                                              variant="outline"
                                              color="dark"
                                              size="sm"
                                              onClick={() => {
                                                toggleDetails(index);
                                              }}
                                          >
                                            {details.includes(index)
                                                ? "Cacher Observation Encadrant"
                                               : "Affiche Observation Encadrant"}
                                          </CButton>
                                        </td>
                                    );
                                  },
                                  details: (item, index) => {
                                    return (
                                        <>
                                          <CCollapse show={details.includes(index)}>
                                            <CCardBody>
                                              <p className="text-muted">
                                                <div
                                                    style={{
                                                      wordWrap: "break-word",
                                                    }}
                                                    className="editor"
                                                    dangerouslySetInnerHTML={{
                                                      __html: item.observation,
                                                    }}
                                                ></div>
                                              </p>
                                            </CCardBody>
                                          </CCollapse>
                                        </>
                                    );
                                  },
                                }}
                            />
                          </CCardBody>
                        </CCard>
                      </CCol>
                    </CTabPane>
                    <CTabPane>
                      <br></br>

                      <CCol xs="12" sm="12" md="12">
                        <CCard accentcolor="dark">{displayVisit(Fiche.fichePFE)}</CCard>
                      </CCol>
                    </CTabPane>
                    <CTabPane>
                      <br></br>

                      <CCol xs="12" sm="12" md="12">
                        <CCard accentcolor="dark">
                          <CCardBody>
                            <CRow>
                              {Fiche.fichePFE.evaluationStages.length === 0 ? (
                                  <>
                                    &nbsp;&nbsp;&nbsp;L'Encadrant n'a pas encore saisi la Note.
                                  </>
                              ): (
                                  <>
                                    <List className={classes.root}>
                                      <div
                                          style={{
                                            display: "flex",
                                            justifyContent: "center",
                                            alignItems: "center",
                                          }}
                                      >
                                        <ListItem button>

                                          <CRow>
                                            <CCol xs="12">
                                              &nbsp;
                                              <span style={{color: "#b30000", fontSize: "13px", fontWeight: "bold"}}>Évaluation Module</span>
                                              &nbsp;&nbsp;
                                              <span style={{color: "#b30000", fontSize: "15px", fontWeight: "bold"}}>
                                                {Fiche.fichePFE.evaluationStages[0].noteEvaluation}
                                                &nbsp;-&nbsp;
                                                {getEvalModule(parseInt(Fiche.fichePFE.evaluationStages[0].noteEvaluation))}
                                              </span>
                                            </CCol>
                                          </CRow>

                                        </ListItem>
                                      </div>
                                      {Fiche.fichePFE.evaluationStages?.map((e, i) => (
                                          <>
                                            <ListItem alignItems="flex-start">
                                              <ListItemAvatar>
                                                <Avatar
                                                    style={{
                                                      backgroundColor:
                                                          getCompetenceColor(e.resultat),
                                                      height:"30px", width:"30px"
                                                    }}
                                                    alt={e.resultat}
                                                    src="/static/images/avatar/1.jpg"
                                                />
                                              </ListItemAvatar>
                                              <ListItemText
                                                  primary={getCompetence(
                                                      e.evaluationStagePK.codeCompetence
                                                  )}
                                                  secondary={
                                                    <>
                                                      <Typography
                                                          component="span"
                                                          variant="body2"
                                                          className={classes.inline}
                                                          color="textPrimary"
                                                      ></Typography>
                                                    </>
                                                  }
                                              />
                                            </ListItem>
                                            <Divider variant="inset" component="li" />
                                          </>
                                      ))}
                                    </List>
                                  </>
                              )}
                            </CRow>
                          </CCardBody>
                        </CCard>
                      </CCol>
                    </CTabPane>
                  </CTabContent>
                </CTabs>
              </CCardBody>
            </CCard>
          </CCol>

        </div>

        <CModal
            show={success}
            onClose={() => setSuccess(!success)}
            color="success"
        >
          <CModalHeader closeButton>
            <CModalTitle>
              <span style={{fontSize: "14px"}}>Confirmation
              </span>
              </CModalTitle>
          </CModalHeader>
          <CModalBody>
            <center>
              <br/>
              Voulez-vous vraiment valider cette Fiche ?.
              <br/><br/>
            </center></CModalBody>
          <CModalFooter>
            <CRow>
              <CCol md="1"/>
              <CCol md="10">
                <center>
                  <CButton color="success" onClick={() => handleValidate()}>

                    {showLoader && <CSpinner grow size="sm" />} &nbsp;&nbsp; Oui
                  </CButton>

                  &nbsp;&nbsp;
                  <CButton color="dark" onClick={() => setSuccess(!success)}>
                    Non
                  </CButton>
                  <br/><br/><br/>
                  {showLoader &&
                        <CAlert color="dark">
                          Attendre un petit peu ...
                          <br/>
                          Nous allons notifer cet Étudiant par E-Mail ... .
                        </CAlert>
                  }
                </center>
              </CCol>
              <CCol md="1"/>
            </CRow>
          </CModalFooter>
        </CModal>

        <CModal show={danger} onClose={() => setDanger(!danger)} color="dark">
          <CModalHeader closeButton>
            <CModalTitle>
              <span style={{fontSize: "14px"}}>Confirmation
              </span>
              </CModalTitle>
          </CModalHeader>
          <CModalBody>
            <center>
              <br/>
            Voulez-vous vraiment refuser cette Fiche ?.
              <br/><br/>
            </center>
          </CModalBody>
          <CModalFooter>

                <center>
                  <CButton color="dark" onClick={() => handleRefuse()}>

                    {showLoader && <CSpinner grow size="sm" />} &nbsp;&nbsp; Oui
                  </CButton>
                  &nbsp;&nbsp;
                  <CButton color="dark" onClick={() => setDanger(!danger)}>
                    Non
                  </CButton>


                  {showLoader &&
                      <>
                        <br/><br/><br/>
                        <CAlert color="dark">
                          Attendre un petit peu ...
                          <br/>
                          Nous allons notifer cet Étudiant par E-Mail ... .
                        </CAlert>
                      </>
                  }
                </center>

          </CModalFooter>
        </CModal>
      </>
  );
};

export default FichePFE;
