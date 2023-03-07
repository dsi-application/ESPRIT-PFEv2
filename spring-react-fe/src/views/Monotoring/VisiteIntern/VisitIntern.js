import React, {useState} from "react";

import {useDispatch, useSelector} from "react-redux";
import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCol,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
  CSpinner
} from "@coreui/react";
import {VerticalTimeline, VerticalTimelineElement,} from "react-vertical-timeline-component";
import "react-vertical-timeline-component/style.min.css";
import Moment from "moment";
import {selectVisitsForEns} from "../../../redux/slices/VisteSlice";
import CIcon from "@coreui/icons-react";

import "moment/locale/fr";
import Pagination from "react-js-pagination";
import {getEtudiant, selectetudiant,} from "../../../redux/slices/FichePFESlice";
import "../../css/style.css";

export const getVisitType = (v) => {
  if (v.typeVisite === "P") {
    return (
        <span style={{color: "#737373", fontSize: "13px"}}>
          <br/>
          Cette visite va être <ins>PRESENTIELLE</ins> à <ins>{v.lieuVisite}</ins>
          <br/>
        </span>
    );
  }
  if (v.typeVisite === "D") {
    return (
        <span style={{color: "#737373", fontSize: "13px"}}>
          <br/>
          Cette visite va être <ins>DISTANTIELLE</ins> à <ins>{v.lieuVisite}</ins>
          <br/>
        </span>
    );
  }
};
const VisitIntern = () => {
  const dispatch = useDispatch();
  const Etu = useSelector(selectetudiant);
  const [visits, err] = useSelector(selectVisitsForEns);
  const [info, setInfo] = useState(false);
  const visitsStatus = useSelector(
      (state) => state.persistedReducer.visits.visitsStatus
  );
  const todosPerPage = 3;
  const [activePage, setCurrentPage] = useState(1);

  // Logic for displaying current todos
  const indexOfLastTodo = activePage * todosPerPage;
  const indexOfFirstTodo = indexOfLastTodo - todosPerPage;
  const currentvisits = visits.slice(indexOfFirstTodo, indexOfLastTodo);
  const togglemodal = (id) => {
    setInfo(!info);
    dispatch(getEtudiant(id));
  };
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };
  return (
      <>
        {visitsStatus === "loading" || visitsStatus === "noData" ? (
            <>
              <div style={{textAlign: "center"}}>
                <br/><br/>
                <CSpinner color="danger" grow size="lg"/>
                <br/><br/>
                <b>Veuillez patienter le chargement des données ...</b>
              </div>
              <br></br>
            </>
        ) : (
            <>
              {visits.length === 0 ? (
                  <CRow>
                    <CCol md="2"/>
                    <CCol md="8">
                      <br/><br/>
                      <CCard accentColor="danger">
                        <CCardBody>
                          <center>
                            <br/><br/>
                            Vous
                            &nbsp;
                            <span className="clignoteRed">
                      n'avez pas encore planifié
                    </span> &nbsp;
                            des Visites Stagiaires du Mis-Parcours.
                            <br/><br/>
                            <br/>
                          </center>
                        </CCardBody>
                        <CCardFooter>
                  <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                  Today is --
                  </span>
                        </CCardFooter>
                      </CCard>
                    </CCol>
                    <CCol md="2"/>
                  </CRow>
              ) : (
                  <>
                    <br/>
                    <VerticalTimeline>
                      {currentvisits.length &&
                      currentvisits?.map((v, i) => (
                          <VerticalTimelineElement
                              key={i}
                              className="vertical-timeline-element--work"
                              //contentStyle={{ background: 'rgb(169,169,169)', color: '#fff' }}
                              //contentArrowStyle={{ borderRight: '7px solid  rgb(33, 150, 243)' }}
                              date={Moment(v.dateVisite).format(
                                  "MMM Do YYYY"
                              )}
                              iconStyle={
                                v.typeVisite === "P"
                                    ? {background: "#808080", color: "#fff"}
                                    : {background: "#ed1c24", color: "#fff"}
                              }
                              icon={
                                v.typeVisite === "P" ? (
                                    <CIcon
                                        style={{width: 25, height: 25}}
                                        name="cil-home"
                                    />
                                ) : (
                                    <CIcon
                                        style={{width: 25, height: 25}}
                                        name="cil-speech"
                                    />
                                )
                              }
                          >
                            <span style={{color: "#737373", fontSize: "14px", fontWeight: "bold"}}>
                              Visite pour l'Étudiant(e):&nbsp;&nbsp;
                              <span className="clignoteRed">{v.fullNameEt} &nbsp;-&nbsp; {v.idEt} </span>
                            </span>
                            <br/><br/>
                            <h6 className="vertical-timeline-element-subtitle">
                              Heure Début: {v.heureDebut} h
                            </h6>
                            <h6 className="vertical-timeline-element-subtitle">
                              Heure Fin: {v.heureFin} h
                            </h6>
                            <CRow>
                              <CCol md="1">
                                <div style={{marginTop:"10px"}}>
                                  <span className="mapIcon"/>
                                </div>
                              </CCol>
                              <CCol md="11" className="colPadding">
                                {getVisitType(v)}
                              </CCol>
                            </CRow>

                            <br/><br/>
                            <span style={{color: "grey", fontSize: "13px", fontWeight: "bold"}}>
                              Observation:
                            </span>
                            <div
                                style={{wordWrap: "break-word", fontSize: "12px"}}
                                className="editor"
                                dangerouslySetInnerHTML={{__html: v.observation,}}/>
                            <br></br> <br></br>
                            <CButton
                                className="float-right"
                                variant="outline"
                                color="dark"
                                size="sm"
                                onClick={() =>
                                    togglemodal(v.idEt)
                                }
                            >
                              Voir détails Étudiant &nbsp;
                              <CIcon name="cil-magnifying-glass"></CIcon>
                            </CButton>
                          </VerticalTimelineElement>
                      ))}
                    </VerticalTimeline>
                    <div
                        style={{
                          height: "20vh",
                          display: "flex",
                          justifyContent: "center",
                          alignItems: "center",
                        }}
                    >
                      <Pagination
                          itemClass="page-item"
                          linkClass="page-link"
                          activePage={activePage}
                          itemsCountPerPage={3}
                          totalItemsCount={visits.length}
                          pageRangeDisplayed={3}
                          onChange={handlePageChange}
                      />
                    </div>
                  </>
              )}
            </>
        )}

        <br></br>
        <CModal size="lg" show={info} onClose={() => setInfo(!info)} color="dark">
          <CModalHeader closeButton>
            <CModalTitle>
              <span style={{fontSize:"14px"}}>
                Détails Étudiant
              </span>
            </CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CCol xs="12" sm="12" md="12">
              <CRow>
                <CCol md="3">
                  <b> Identifiant: </b>
                </CCol>
                <CCol md="9">
                  {Etu.idEt}
                </CCol>
              </CRow>

              <CRow>
                <CCol md="3">
                  <b> Nom & Prénom: </b>
                </CCol>
                <CCol md="9">
                  {Etu.nomet} &nbsp; {Etu.prenomet}
                </CCol>
              </CRow>

              <CRow>
                <CCol md="3">
                  <b> E-Mail: </b>
                </CCol>
                <CCol md="9">
                  {Etu.adressemailesp}
                </CCol>
              </CRow>

              <CRow>
                <CCol md="3">
                  <b> Numéro Téléphone: </b>
                </CCol>
                <CCol md="9">
                  {Etu.telet}
                </CCol>
              </CRow>

              <CRow>
                <CCol md="3">
                  <b>Classe Courante:</b>
                </CCol>
                <CCol md="9">
                  {Etu.classe}
                </CCol>
              </CRow>
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

export default VisitIntern;
