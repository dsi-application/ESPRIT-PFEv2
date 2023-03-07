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
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router";
import {
  selectSelectedAvenant,
  updateAvenant,
} from "../../redux/slices/AvenantSlice";
import { selectetudiant } from "../../redux/slices/FichePFESlice";
import { queryApi } from "../../utils/queryApi";
import "moment/locale/fr";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";
import {getBadge, getEtat} from "./ConventionsManage";
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
        <strong>les détails de l'avenant </strong>
      </Text>
    ),
  },

  {
    selector: '[data-tut="reactour__3"]',
    content: () => (
      <Text>
        Içi, vous pouvez interpréter l'état de l'avenant :
        <strong>EN ATTENTE - TRAITE</strong>.
      </Text>
    ),
  },
  {
    selector: '[data-tut="reactour__4"]',
    content: () => (
      <Text>
        Pour traiter l'Avenant, cliquer sur ce bouton :
        <strong> Valider l'Avenant et l'envoyer à l'Étudiant par E-Mail.</strong>.
      </Text>
    ),
  },
];
const AvenantDetails = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const [warning, setWarning] = useState(false);
  const history = useHistory();
  const dispatch = useDispatch();
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({ visible: false, message: "" });
  const Etu = useSelector(selectetudiant);
  const Avenant = useSelector(selectSelectedAvenant);

  const handleUpdateEvent = async () => {
    setShowLoader(true);
    const [res, err] = await queryApi(
      "serviceStage/updateAvenantState?idET=" +
        Avenant.avenantPK.conventionPK.idEt +
        "&dateConvention=" +
        Avenant.avenantPK.conventionPK.dateConvention +
        "&dateAvenant=" +
        Avenant.avenantPK.dateAvenant,
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
      dispatch(updateAvenant(res));
      history.push("/AvenantsManagement");
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
                  <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Détails Avenant</span>
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
                    <h6>
                      <b style={{color: "red"}}>Cette avenant est </b>
                      {Avenant.traiter ? (
                          <CBadge data-tut="reactour__3" color="success">
                            TRAITE
                          </CBadge>
                      ) : (
                          <CBadge data-tut="reactour__3" color="warning">
                            EN ATTENTE
                          </CBadge>
                      )}
                    </h6>
                    <br></br>

                    <span style={{fontWeight: "bold"}}>
                      Identifiant Étudiant:
                    </span>&nbsp;
                    {Avenant.avenantPK.conventionPK.idEt}
                    <br/>
                    <span style={{fontWeight: "bold"}}>
                      Date Dépôt Avenant:
                    </span>&nbsp;
                    {moment(Avenant.avenantPK.dateAvenant).format("LLLL")}
                    <br/><br/>

                    {Avenant.traiter ? (
                      <CButton
                        size="sm"
                        className="float-left;"
                        disabled
                        style={{ backgroundColor: "#808580", color: "white" }}
                        data-tut="reactour__4"
                      >
                        Valider l'Avenant et l'envoyer à l'Étudiant par E-Mail
                        &nbsp; &nbsp;
                        <CIcon name="cil-check"></CIcon>
                      </CButton>
                    ) : (
                      <CButton
                        size="sm"
                        className="float-left;"
                        style={{ backgroundColor: "red", color: "white" }}
                        color="warning"
                        onClick={() => setWarning(!warning)}
                        data-tut="reactour__4"
                      >
                        Valider l'Avenant et l'envoyer à l'Étudiant par E-Mail
                        &nbsp; &nbsp;
                        <CIcon name="cil-check"></CIcon>
                      </CButton>
                    )}
                  </CCardHeader>
                  <CCardBody>
                    <b style={{ color: "red" }}>
                      Date Dépôt Convention Associée: &nbsp;&nbsp;&nbsp;
                    </b>
                    {moment(Avenant.avenantPK.conventionPK.dateConvention).format("LLLL")}
                    <br></br><br></br>

                    <CRow>
                      <CCol md="3" className="colPaddingRight">
                        <b>Nouvelle Date Début:</b>
                      </CCol>
                      <CCol md="9" className="colPaddingLeft">
                        {moment(Avenant.dateDebut).format("MMMM Do YYYY")}
                      </CCol>
                    </CRow>

                    <CRow>
                      <CCol md="3" className="colPaddingRight">
                        <b>Nouvelle Date Fin:</b>
                      </CCol>
                      <CCol md="9" className="colPaddingLeft">
                        {moment(Avenant.dateFin).format("MMMM Do YYYY")}
                      </CCol>
                    </CRow>

                    <CRow>
                      <CCol md="3" className="colPaddingRight">
                        <b>Date Signature Convention:</b>
                      </CCol>
                      <CCol md="9" className="colPaddingLeft">
                        {moment(Avenant.dateSignatureConvention).format("MMMM Do YYYY")}
                      </CCol>
                    </CRow>

                    <br/>
                    <CRow>
                      <CCol md="3" className="colPaddingRight">
                        <b>Responsable Entreprise:</b>
                      </CCol>
                      <CCol md="9" className="colPaddingLeft">
                        {Avenant.responsableEntreprise}
                      </CCol>
                    </CRow>

                    <CRow>
                      <CCol md="3" className="colPaddingRight">
                        <b>Qualité Responsable:</b>
                      </CCol>
                      <CCol md="9" className="colPaddingLeft">
                        {Avenant.qualiteResponsable}
                      </CCol>
                    </CRow>

                    <CRow>
                      <CCol md="3" className="colPaddingRight">
                        <b>Numéro SIREN:</b>
                      </CCol>
                      <CCol md="9" className="colPaddingLeft">
                        {Avenant.numSiren}
                      </CCol>
                    </CRow>

                  </CCardBody>
                </CCard>
              </CCol>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>

      <CModal
        show={warning}
        onClose={() => setWarning(!warning)}
        color="warning"
      >
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
            Êtes-vous sûre de valider cet Avenant ?
            <br/><br/>
            En validant cet Avenant, l'Étudiant correspondant sera averti par
            E-Mail joint par le document d'Avenant en format PDF.
            <br/><br/><br/>
          </center>
        </CModalBody>
        <CModalFooter>
          <CRow>
            <CCol md="1"/>
            <CCol md="10">
              <center>
          <CButton color="warning" onClick={() => handleUpdateEvent()}>
            {showLoader && <CSpinner grow size="sm" />} &nbsp;&nbsp;Oui
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
                Nous allons envoyer l'Avenant à cet Étudiant par email ... .
              </CAlert>
          )}

              </center>
            </CCol>
            <CCol md="1"/>
          </CRow>
        </CModalFooter>
      </CModal>
    </>
  );
};

export default AvenantDetails;
