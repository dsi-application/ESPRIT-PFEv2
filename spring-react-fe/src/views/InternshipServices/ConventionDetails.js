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
import {selectSelectedConvention, updateConventionForRSS} from "../../redux/slices/ConventionSlice";
import {selectetudiant} from "../../redux/slices/FichePFESlice";
import {queryApi} from "../../utils/queryApi";

import {getBadge, getEtat} from "./ConventionsManage";
import "moment/locale/fr";
import Tour from "reactour";
import LocalLibrary from "@material-ui/icons/LocalLibrary";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import Text from "antd/lib/typography/Text";


import AuthService from "../services/auth.service";

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();

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

const ConventionDetails = () => {
  const [isTourOpen, setIsTourOpen] = useState(false);
  const Convention = useSelector(selectSelectedConvention);
  const Etu = useSelector(selectetudiant);
  const [warning, setWarning] = useState(false);
  const [openModalRefusConv, setOpenModalRefusConv] = useState(false);

  const history = useHistory();
  const dispatch = useDispatch();
  const [showLoader, setShowLoader] = useState(false);
  const [error, setError] = useState({visible: false, message: ""});

  console.log('---------------loli------------------Convention:', Convention)

  const handleUpdateEvent = async () => {
    setShowLoader(true);

    const [res, err] = await queryApi(
      "serviceStage/updateConventionStateFD?idET=" +
      Convention.idEt +
      "&date=" +
      Convention.dateConvention,
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
      dispatch(updateConventionForRSS(res));
      history.push("/ConventionsManagement");
    }
  };

  const handleRefuseEvent = async () => {
    setShowLoader(true);
    console.log('_________________PIKA 0')
    const [res, err] = await queryApi(
      "serviceStage/refuseConventionStateFD?idET=" +
      Convention.idEt +
      "&date=" +
      Convention.dateConvention,
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
      console.log('_________________PIKA 1')
    } else {
      dispatch(updateConventionForRSS(res));
      history.push("/ConventionsManagement");
      console.log('___________dd______PIKA 2')
    }
  };

  const openModal = () => {
    setWarning(!warning);
    setIsTourOpen(false);

    document.body.style.overflowY = "auto";
  };

  const openModalRefus = () => {
    setOpenModalRefusConv(!openModalRefusConv);
    document.body.style.overflowY = "auto";
  };

  const displayTraitementBtn = (c) => {
    if (c.traiter === "02" || c.traiter === "03") {
      return (
        <CButton
          size="sm"
          className="float-left;"
          disabled
          color="danger"
          style={{backgroundColor: "green", color: "white"}}
          data-tut="reactour__4"
        >
          Valider la Convention et l'envoyer à l'Étudiant par E-Mail&nbsp;
          &nbsp;
          <CIcon name="cil-check"></CIcon>
        </CButton>
      );
    }
    if (c.traiter === "01") {
      return (
        <>
          {
            currentResponsableServiceStage.id.includes("IT") ? (
              <CButton
                size="sm"
                className="float-left;"
                style={{backgroundColor: "green", color: "white"}}
                color="success"
                onClick={() => openModal()}
                data-tut="reactour__4"
              >
                Valider la Convention et l'envoyer à l'Étudiant par E-Mail
              </CButton>
            ) : (
              <CButton
                size="sm"
                className="float-left;"
                style={{backgroundColor: "green", color: "white"}}
                color="success"
                disabled>
                Valider la Convention et l'envoyer à l'Étudiant par E-Mail
              </CButton>
            )
          }
        </>
      );
    }
  };

  const displayTraitementRefusBtn = (c) => {
    if (c.traiter === "02" || c.traiter === "03") {
      return (
        <CButton
          size="sm"
          className="float-left;"
          disabled
          color="danger"
          style={{backgroundColor: "red", color: "white"}}
          data-tut="reactour__4"
        >
          Refuser la Convention &nbsp;
          &nbsp;
          <CIcon name="cil-check"></CIcon>
        </CButton>
      );
    }
    if (c.traiter === "01") {
      return (
        <>
          {
            currentResponsableServiceStage.id.includes("IT") ? (
              <CButton
                size="sm"
                className="float-left;"
                style={{backgroundColor: "red", color: "white"}}
                color="danger"
                onClick={() => openModalRefus()}
                data-tut="reactour__4"
              >
                Refuser la Convention 1
              </CButton>
            ) : (
              <CButton
                size="sm"
                className="float-left;"
                style={{backgroundColor: "red", color: "white"}}
                color="danger"
                disabled>
                Refuser la Convention 1
              </CButton>
            )
          }
        </>
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
                  <span style={{color: "#b30000", fontSize: "14px", fontWeight: "bold"}}>Détails Convention</span>
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
                      <b style={{color: "red"}}>Cette convention est </b>
                      <CBadge data-tut="reactour__3" color={getBadge(Convention.traiter)}>
                        {getEtat(Convention.traiter)}
                      </CBadge>
                    </h6>
                    <br></br>

                    <span style={{fontWeight: "bold"}}>
                      Identifiant Étudiant:
                    </span>&nbsp;
                    {Convention.idEt}
                    <br/>
                    <span style={{fontWeight: "bold"}}>
                      Date Dépôt Convention:
                    </span>&nbsp;
                    {Convention.dateConvention}
                    <br/><br/>

                    {displayTraitementBtn(Convention)}
                    &nbsp;&nbsp;&nbsp;
                    {displayTraitementRefusBtn(Convention)}
                    <br/><br/>
                  </CCardHeader>
                  <CCardBody>
                    <b style={{color: "red"}}>Informations sur Entreprise</b>
                    <br/><br/>
                    <CRow>
                      <CCol md="2">
                        <b>Adresse:</b>
                      </CCol>
                      <CCol md="10">
                        {Convention.entrepriseAccueilConvention.address}
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
                        <b>Libellé:</b>
                      </CCol>
                      <CCol md="10">
                        {Convention.entrepriseAccueilConvention.designation}
                      </CCol>
                    </CRow>

                    <CRow>
                      <CCol md="2">
                        <b>Pays:</b>
                      </CCol>
                      <CCol md="10">
                        {Convention.entrepriseAccueilConvention.pays ? Convention.entrepriseAccueilConvention.pays.nomPays : "--"}
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

      <CModal
        show={openModalRefusConv}
        onClose={() => setOpenModalRefusConv(!openModalRefusConv)}
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
            Êtes-vous sûre de vouloir refuser cette Convention ?.
            <br/><br/>
            En refusant cette Convention, l'Étudiant correspondant peut ajouter une nouvelle.
            <br/><br/><br/>
          </center>

        </CModalBody>
        <CModalFooter>
          <CRow>
            <CCol md="1"/>
            <CCol md="10">
              <center>
                <CButton color="warning" onClick={() => handleRefuseEvent()}>
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
                    Nous allons envoyer la Convention à cet Étudiant par email ... .
                  </CAlert>
                )}
              </center>
            </CCol>
            <CCol md="1"/>
          </CRow>
        </CModalFooter>
      </CModal>

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
            Êtes-vous sûre de valider cette Convention ?.
            <br/><br/>
            En validant cette Convention, l'Étudiant correspondant sera averti par
            E-Mail joint par le document du Convention en format PDF.
            <br/><br/><br/>
          </center>

        </CModalBody>
        <CModalFooter>
          <CRow>
            <CCol md="1"/>
            <CCol md="10">
              <center>
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
                    Nous allons envoyer la Convention à cet Étudiant par email ... .
                  </CAlert>
                )}
              </center>
            </CCol>
            <CCol md="1"/>
          </CRow>
        </CModalFooter>
      </CModal>
    </>
  )
};

export default ConventionDetails;
