import CIcon from "@coreui/icons-react";
import {
  CAlert,
  CBadge,
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
import moment from "moment";
import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useHistory} from "react-router";
import {selectSelectedConvention, updateDemandeAnnulationConvention,} from "../../redux/slices/ConventionSlice";
import {selectetudiant} from "../../redux/slices/FichePFESlice";
import {queryApi} from "../../utils/queryApi";

import {getBadge, getEtat} from "./ConventionsManage";
import "moment/locale/fr";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";

const steps = [
  {
    selector: '[data-tut="reactour__1"]',
    content: `Içi, Vous allez trouver les détails de l'étudiant `,
  },
  {
    selector: '[data-tut="reactour__2"]',
    content: () => (
        <Text>
          Dans cette partie , vous allez trouver
          <strong>les détails de la convention </strong>
        </Text>
    ),
  },

  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
        <Text>
          Içi, vous pouvez interpréter l'état de la convention :
          <strong>DEPOSEE - TRAITEE - ANNULEE</strong>.
        </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__4"]',
    content: () => (
        <Text>
          Pour traiter la convention , cliquer sur ce bouton :
          <strong>

            Valider la convention et l'envoyer à l'Étudiant par Email
          </strong>
          .
        </Text>
    ),
  },
];
const DemandeAnnulationConventionDetails = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const Convention = useSelector(selectSelectedConvention);
  const Etu = useSelector(selectetudiant);
  const [warning, setWarning] = useState(false);
  const history = useHistory();
  const dispatch = useDispatch();
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({visible: false, message: ""});

  const handleUpdateEvent = async () => {
    setShowLoader(true);

    const [res, err] = await queryApi(
        "serviceStage/updateDemandeAnnulationConventionState?idET=" +
        Convention.conventionPK.idEt +
        "&date=" +
        Convention.conventionPK.dateConvention,
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
      dispatch(updateDemandeAnnulationConvention(res));
      // dispatch(updateDemandeAnnulationConvention(res));
      // useSelector(selectDemandesAnnulationConventions)
      history.push("/ConventionsDemandesAnnulation");
    }
  };
  const openModal = () => {
    setWarning(!warning);
    setIsTourOpen(false);

    document.body.style.overflowY = "auto";
  };
  const displayTraitementBtn = (c) => {
    if (c.traiter === "04") {
      return (
          <CButton
              size="sm"
              className="float-left;"
              style={{backgroundColor: "#ed1c24", color: "white"}}
              onClick={() => openModal()}
              data-tut="reactour__4"
          >
            Valider la demande Annulation Convention
          </CButton>
      );
    }
    if (c.traiter === "03") {
      return (
          <CButton
              size="sm"
              className="float-left;"
              color="dark"
              onClick={() => openModal()}
              data-tut="reactour__4"
              disabled
          >
            Demande Annulation Convention VALIDÉE
          </CButton>
      );
    }
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
      <CRow>
        <CCol>
          <CCard>
            <CCardHeader data-tut="reactour__1">
              <CRow>

                <CCol xs="12" sm="6" md="6">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Détails Demande Annulation Convention</span>
                </CCol>
                <CCol xs="12" sm="6" md="6">
                  <h5 className="float-right">
                    <span style={{color: "#999999", fontSize: "14px", fontWeight: "bold"}}>{Etu.prenomet} {Etu.nomet}</span>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style={{color: "#999999", fontSize: "14px", fontWeight: "bold"}}>{Etu.classe}</span>
                  </h5>
                  <br></br> <br></br>
                  <h6 className="float-right"> {Etu.adressemailesp}</h6>
                </CCol>
              </CRow>
            </CCardHeader>

            <CCardBody data-tut="reactour__2">
              <CCol xs="12" sm="12" md="12">
                <CCard accentColor="danger">
                  <CCardHeader>
                    <br/>
                    <span style={{fontWeight: "bold"}}>
                      Identifiant Étudiant:
                    </span>&nbsp;
                    {Convention.conventionPK.idEt}
                    <br/>
                    <span style={{fontWeight: "bold"}}>
                      Date Dépôt Convention:
                    </span>&nbsp;
                    {moment(Convention.conventionPK.dateConvention).format("LLLL")}
                    <br/><br/>
                    {displayTraitementBtn(Convention)}
                    <br/><br/>
                </CCardHeader>
                <CCardBody>
                  <b style={{color: "red"}}>Informations sur Entreprise</b>
                  <br/>

                  <CRow>
                    <CCol md="2">
                      <b>Libellé:</b>
                    </CCol>
                    <CCol md="10">
                      {Convention.entrepriseAccueilConvention.designation}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol md="2">
                      <b>Secteur Activité:</b>
                    </CCol>
                    <CCol md="10">
                      {Convention.entrepriseAccueilConvention.secteurEntreprise ? Convention.entrepriseAccueilConvention.secteurEntreprise.libelleSecteur : "--"}
                    </CCol>
                  </CRow>
                  <CRow>
                    <CCol md="2">
                      <b>Siège Social:</b>
                    </CCol>
                    <CCol md="10">
                      {
                        Convention.entrepriseAccueilConvention.siegeSocial === '---' ?
                            "--" : <>{Convention.entrepriseAccueilConvention.siegeSocial}</>}
                    </CCol>
                  </CRow>

                  <br/>

                  <CRow>
                    <CCol md="2">
                      <b>Adresse:</b>
                    </CCol>
                    <CCol md="10">
                      {Convention.entrepriseAccueilConvention.address}
                      {Convention.entrepriseAccueilConvention.pays ?
                          <>{' ,' + Convention.entrepriseAccueilConvention.pays.nomPays}</> : ""}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol md="2">
                      <b>E-Mail:</b>
                    </CCol>
                    <CCol md="10">
                      {Convention.entrepriseAccueilConvention.addressMail}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol md="2">
                      <b>Numéro Téléphone:</b>
                    </CCol>
                    <CCol md="10">
                      {Convention.entrepriseAccueilConvention.telephone === '--------' && <>--</>}
                      {Convention.entrepriseAccueilConvention.telephone !== '--------' && <>{Convention.entrepriseAccueilConvention.telephone}</>}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol md="2">
                      <b>Numéro Fax:</b>
                    </CCol>
                    <CCol md="10">
                      {Convention.entrepriseAccueilConvention.fax === '--------' && <>--</>}
                      {Convention.entrepriseAccueilConvention.fax !== '--------' && <>{Convention.entrepriseAccueilConvention.fax}</>}
                    </CCol>
                  </CRow>

                </CCardBody>
          </CCard>
        </CCol>
      </CCardBody>
      </CCard>
</CCol>
</CRow>

  <CModal show={warning} onClose={() => setWarning(!warning)} color="warning">
    <CModalHeader closeButton>
      <CModalTitle>
        <span style={{fontSize: "13px"}}>
          Confirmation
        </span>
      </CModalTitle>
    </CModalHeader>
    <CModalBody>
      <center>
        <br/>
        Êtes-vous sûre de vouloir valider cette demande Annulation Convention ?.
        <br/><br/><br/><br/>
          <CButton color="warning" onClick={() => handleUpdateEvent()}>
            {showLoader && <CSpinner grow size="sm"/>}&nbsp;&nbsp;
            Oui
          </CButton>
          &nbsp;&nbsp;
          <CButton color="secondary" onClick={() => setWarning(!warning)}>
            Non
          </CButton>

          <br/><br/><br/>

          {showLoader && (
              <CAlert color="info">
                Attendre un petit peu ... .
                <br/>
                Envoie du Mail en cours ... .
              </CAlert>
          )}
      </center>
    </CModalBody>
  </CModal>
</>
)
  ;
};

export default DemandeAnnulationConventionDetails;
