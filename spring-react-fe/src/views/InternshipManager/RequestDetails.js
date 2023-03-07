import CIcon from "@coreui/icons-react";
import {
  CAlert,
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CListGroup,
  CListGroupItem,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSpinner,
} from "@coreui/react";
import "../css/style.css";
import moment from "moment";
import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useHistory} from "react-router";
import {selectetudiant} from "../../redux/slices/FichePFESlice";
import {getFiche, selectfichePFE, selectSelectedRequest, updateRequest,} from "../../redux/slices/RequestSlice";
import {getEtatFiche, selectEtatFiche} from "../../redux/slices/StudentSlice";
import {queryApi} from "../../utils/queryApi";
import {getRequestState, getRequestType} from "./TRTFichePFE";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import {fetchEtatValidationFiche} from "../../redux/slices/ListingSlice";

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
          Içi, Vous allez trouver les détails de :
          <strong>la fiches de projet de fin d'études</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__4"]',
    content: () => (
        <Text>
          Pour traiter la demande , vous pouvez cliquer sur ce bouton :
          <strong>
            
            Traiter la demande et donner l'accés à cet Étudiant de modifier sa
            Fiche
          </strong>
          .
        </Text>
    ),
  },
];
const RequestDetails = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [danger, setDanger] = useState(false);
  const [danger2, setDanger2] = useState(false);
  const [info, setInfo] = useState(false);
  const fichePFE = useSelector(selectfichePFE);
  const [etatFiche, errEtat] = useSelector(selectEtatFiche);
  const dispatch = useDispatch();
  const history = useHistory();
  const Request = useSelector(selectSelectedRequest);
  const Etu = useSelector(selectetudiant);
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({visible: false, message: ""});

  const togglemodal = (id, date) => {
    setInfo(!info);
    dispatch(getFiche(id, date));
    dispatch(getEtatFiche(fichePFE.etatFiche));
  };
  const handleValidate = async () => {
    setShowLoader(true);
    const [res, err] = await queryApi(
        "responsableStage/traiteDemandeModif?idET=" +
        Request.traitementFichePK.fichePFEPK.idEt +
        "&dateDepotFiche=" +
        Request.traitementFichePK.fichePFEPK.dateDepotFiche +
        "&dateTrtFiche=" +
        Request.traitementFichePK.dateTrtFiche,
        {},
        "PUT",
        false
    );

    console.log('----------------- TRT-1: ' + Request.traitementFichePK.fichePFEPK.dateDepotFiche);
    console.log('----------------- TRT-2: ' + Request.traitementFichePK.dateTrtFiche);

    if (err) {
      setShowLoader(false);
      setError({
        visible: true,
        message: JSON.stringify(err.errors, null, 2),
      });
    } else {
      dispatch(updateRequest(res));
      dispatch(fetchEtatValidationFiche(""));
      history.push("/FichePFETraitement");
    }
  };
  const handleAnnule = async () => {
    setShowLoader(true);
    const [res, err] = await queryApi(
        "responsableStage/traiteDemandeAnnule?idET=" +
        Request.traitementFichePK.fichePFEPK.idEt +
        "&dateDepotFiche=" +
        Request.traitementFichePK.fichePFEPK.dateDepotFiche +
        "&dateTrtFiche=" +
        Request.traitementFichePK.dateTrtFiche,
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
      dispatch(updateRequest(res));
      dispatch(fetchEtatValidationFiche(""));
      history.push("/FichePFETraitement");
    }
  };
  const displayTraitementBTN = (R) => {
    if (R.etatTrt === "01") {
      if (R.typeTrtFiche === "01") {
        return (
            <CCol col="2" className="text-center mt-3">
              <CButton
                  className="float-right"
                  variant="outline"
                  color="dark"
                  size="sm"
                  onClick={() => setDanger(!danger)}
                  data-tut="reactour__4"
              >
                Traiter la demande et donner l'accés à cet Étudiant de modifier sa
                Fiche &nbsp;<CIcon name="cil-check"></CIcon>
              </CButton>
            </CCol>
        );
      }
      if (R.typeTrtFiche === "02") {
        return (
            <CCol col="2" className="text-center mt-3">
              <CButton
                  className="float-right"
                  variant="outline"
                  color="danger"
                  size="sm"
                  onClick={() => setDanger2(!danger2)}
                  data-tut="reactour__4"
              >
                Traiter la demande et Annuler cette Fiche &nbsp;&nbsp;
                <CIcon name="cil-check"></CIcon>
              </CButton>
            </CCol>
        );
      }
    } else {
      return;
    }
  };
  return (
      <>
        <Tour
            steps={stepsTour}
            isOpen={isTourOpen}
            onAfterOpen={(target) => (document.body.style.overflowY = "hidden")}
            onBeforeClose={(target) => (document.body.style.overflowY = "auto")}
            onRequestClose={() => setIsTourOpen(false)}
        />
        <CRow>
          <CCol>
            <CCard>
              <CCardHeader data-tut="reactour__1">
                <CRow>
                  <CCol xs="12" sm="6" md="6">
                  <span style={{color: "#cc0000", fontSize: "13px", fontWeight: "bold"}}>
                  Détails du demande
                  </span>
                  </CCol>
                  <CCol xs="12" sm="6" md="6">
                    <h6 className="float-right">
                      <b>
                        {Etu.prenomet} {Etu.nomet}&nbsp;&nbsp; {Etu.classe}
                      </b>
                    </h6>
                    <br></br> <br></br>
                    <h6 className="float-right"> {Etu.adressemailesp}</h6>
                  </CCol>
                </CRow>
                <br></br>
                {displayTraitementBTN(Request)}
                <CCol col="2" className="text-center mt-3">
                  <CButton
                      className="float-left"
                      variant="outline"
                      color="dark"
                      size="sm"
                      onClick={() =>
                          togglemodal(
                              Request.traitementFichePK.fichePFEPK.idEt,
                              Request.traitementFichePK.fichePFEPK.dateDepotFiche
                          )
                      }
                      data-tut="reactour__3"
                  >
                    Consulter Détails Fiche &nbsp;
                    <CIcon name="cil-magnifying-glass"></CIcon>
                  </CButton>
                </CCol>
              </CCardHeader>

              <CCardBody data-tut="reactour__2">
                <CCol xs="12" sm="12" md="12">
                  <CCard accentColor="danger">
                    <CCardHeader>
                      <h6 style={{fontWeight: "bold"}}>
                        Cette demande est une demande de &nbsp;
                        {getRequestType(Request.typeTrtFiche)} <br></br>
                        Elle est {getRequestState(Request.etatTrt)}
                      </h6>
                      <br></br>

                      <CRow>
                        <CCol md="2">
                          <span className="greyFieldLibelleCardSmall">Identifiant Étudiant:</span>
                        </CCol>
                        <CCol md="10">
                          {Request.traitementFichePK.fichePFEPK.idEt}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="2">
                          <span className="greyFieldLibelleCardSmall">Date Saisie Demande:</span>
                        </CCol>
                        <CCol md="10">
                          {moment(Request.traitementFichePK.dateTrtFiche).format("MMM Do YY")}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol md="2">
                          <span className="greyFieldLibelleCardSmall">Date Dépôt Fiche:</span>
                        </CCol>
                        <CCol md="10">
                          {moment(Request.traitementFichePK.fichePFEPK.dateDepotFiche).format("LLLL")}
                        </CCol>
                      </CRow>

                      <br/>

                    </CCardHeader>
                    <CCardBody>
                      <b style={{color: "#737373"}}>Description de la demande :</b>
                      <br></br>
                      {Request.description}
                    </CCardBody>
                  </CCard>
                </CCol>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>

        <CModal show={danger} onClose={() => setDanger(!danger)} color="danger">
          <CModalHeader closeButton>
            <CModalTitle>
              <span style={{fontSize:"12px"}}>Confirmation de validation</span>
              </CModalTitle>
          </CModalHeader>
          <CModalBody>

            <CRow>
              <CCol md="12">
                <br/><br/>
                <center>
                  Voulez vous vraiment donner un accés à cet Étudiant pour modifier sa Plan Travail ?.
                </center>
                <br/><br/>
              </CCol>
            </CRow>
          </CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => handleValidate()}>
              
              {showLoader && <CSpinner grow size="sm"/>} OUI
            </CButton>
            {showLoader && (
                <CAlert color="danger">
                  Attendre un petit peu ... Nous allons notifier cet étudiant par
                  email ....
                </CAlert>
            )}
            <CButton color="secondary" onClick={() => setDanger(!danger)}>
              Annuler
            </CButton>
          </CModalFooter>
        </CModal>
        <CModal
            show={danger2}
            onClose={() => setDanger2(!danger2)}
            color="danger"
        >
          <CModalHeader closeButton>
            <CModalTitle>
              <span style={{fontSize: "14px"}}>
                Confirmation
              </span>
            </CModalTitle>
          </CModalHeader>
          <CModalBody>
            <center>
              <br/>
            Voulez-vous vraiment annuler cette Fiche ?.
              {
                Request.typeTrtConv === "Oui" &&
                    <>
                      <br/>
                      <span className="redNote">
                        Remarque: La convention va être annulée aussi.
                      </span>
                    </>
              }
            <br/><br/>
            </center></CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => handleAnnule()}>
              {showLoader && <CSpinner grow size="sm"/>} Oui
            </CButton>
           &nbsp;&nbsp;
            <CButton color="secondary" onClick={() => setDanger2(!danger2)}>
              Non
            </CButton>

            {showLoader &&
                <><br/><br/><br/>
            <CAlert color="danger">
              Attendre un petit peu ...
              <br/>
              Nous allons notifier cet Étudiant de votre confirmation par
              E-Mail ... .

            </CAlert>
                </>
            }
          </CModalFooter>
        </CModal>

        <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
          <CModalHeader closeButton>
           <span style={{fontSize:"12px"}}>
              Détails du Plan Travail</span>
          </CModalHeader>
          <CModalBody>
            <CCol xs="12" sm="12" md="12">
              {fichePFE.idFichePFE ? (
                  <CCard accentColor="danger">
                    <CCardHeader>
                          <span className="redMark">
                            {fichePFE.titreProjet}
                          </span>
                          <br/><br/>

                          <CRow>
                            <CCol md="3">
                              <span className="greyFieldLibelleCardSmall">Identifiant Étudiant :</span>
                            </CCol>
                            <CCol md="9">
                              {fichePFE.idFichePFE.idEt}
                            </CCol>
                          </CRow>

                          <CRow>
                            <CCol md="3">
                              <span className="greyFieldLibelleCardSmall">Date Dépôt Fiche :</span>
                            </CCol>
                            <CCol md="9">
                              {moment(fichePFE.idFichePFE.dateDepotFiche).locale("fr").format("LLLL")}
                            </CCol>
                          </CRow>

                          <CRow>
                            <CCol md="3">
                              <span className="greyFieldLibelleCardSmall">État Fiche :</span>
                            </CCol>
                            <CCol md="9">
                              <span className="darkMark">{etatFiche.etat}</span>
                            </CCol>
                          </CRow>
                      <br/>
                    </CCardHeader>
                    <CCardBody>
                      <CRow>
                        <CCol xs="12" sm="6" md="6">
                          <span className="primaryMark">Description Projet :</span>
                            <p>
                              {fichePFE.descriptionProjet
                                  ? fichePFE.descriptionProjet
                                  : "Pas de decsription"}
                            </p>
                          <span className="infoMark">Problématiques Projet : </span>
                          <CListGroup accent>
                            {fichePFE.problematics?.map((item, i) => (
                                <CListGroupItem accent="info">
                                  {item.name}
                                </CListGroupItem>
                            ))}
                          </CListGroup>
                        </CCol>
                        <CCol xs="12" sm="6" md="6">
                          <span className="secondaryMark">Fonctionnalités Projet : </span>
                          <CListGroup accent>
                            {fichePFE.functionnalities?.map((item, i) => (
                                <CListGroupItem accent="dark">
                                  {item.name} <br></br> <b>Description : </b>
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
                          <span className="lightMark">Téchnologies Utilisées : </span>
                          <CListGroup accent>
                            {fichePFE.technologies?.map((item, i) => (
                                <CListGroupItem accent="secondary">
                                  {item.name} <br></br>
                                </CListGroupItem>
                            ))}
                          </CListGroup>
                        </CCol>
                      </CRow>
                    </CCardBody>
                  </CCard>
              ) : (
                  <></>
              )}
            </CCol>
          </CModalBody>
          <CModalFooter>
            <CButton color="secondary" onClick={() => setInfo(!info)}>
              EXIT
            </CButton>
          </CModalFooter>
        </CModal>
      </>
  );
};

export default RequestDetails;
