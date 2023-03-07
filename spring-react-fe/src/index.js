import "react-app-polyfill/ie11"; // For IE 11 support
import "react-app-polyfill/stable";
import "./polyfill";
import React, { useEffect } from "react";
import ReactDOM from "react-dom";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import { icons } from "./assets/icons";
import { Provider } from "react-redux";
import store from "./store";

import { fetchStudentConfigs } from "./redux/slices/StudentConfigSlice";
import { fetchTeacherConfigs } from "./redux/slices/TeacherConfigSlice";
import { fetchChefCoordConfigs } from "./redux/slices/ChefCoordConfigSlice";
import { fetchRespServiceStgConfigs } from "./redux/slices/RespServiceStgConfigSlice";

import { fetchRSSDepotStat, // HooksMe - 0
  fetchRSSUploadedDepotStat,
  fetchRSSValidatedDepotStat,
  fetchRSSRefusedDepotStat,
  fetchRSSSoutenanceStat,
  fetchStudentToSTNStat,
  fetchStudentDoneSTNStat,
  fetchPlanifiedSoutenances,
  fetchUnplanifiedSoutenances
} from "./redux/slices/DepotSlice";
import {
  fecthAvenants,
  fecthFichePFEs,
  fetchStudents,
  getEtatFiche,
} from "./redux/slices/StudentSlice";
import {
  fecthEvaluations,
  fecthListCompetences,
} from "./redux/slices/EvaluationSlice";
import { PersistGate } from "redux-persist/integration/react";
import { persistStore } from "redux-persist";
import {fetchVisits, fetchVisitsEtudiant} from "./redux/slices/VisteSlice";
import {
  fetchConventions,
  fetchConventionsForRSS,
  fetchConventionsValidatedForRSS,
  fetchFichebydep,
  fetchFichebydepVal,
  fetchFicheValALL,
  fetchFichebydepInc,
  fetchDemandesAnnulationConventions,
  fetchNbrDemandesAnnulationConvention,
  fetchNbrDepositedConventions,
  fetchNbrDemandesAnnulationConventionNotTreated, fetchConventionsDtoForRSS
} from "./redux/slices/ConventionSlice";
import { fetchAvenants } from "./redux/slices/AvenantSlice";
import { fetchfichePFEs } from "./redux/slices/FichePFESlice";
import { fetchRequests, getFiche } from "./redux/slices/RequestSlice";
import { fetchEntreprises, fetchRdvPedas } from "./redux/slices/RDVPedaSlice";
import {
  fetchOptionStudents,
  getEancadrant,
  fetchEnseignants,
  fetchEtudiantsbyEns,
} from "./redux/slices/EncadrementSlice";
import {
  fectchEnseignantStat,
  fectchEnseignantStat2,
  fectchResponsableStat,
  fectchServiceStat,
} from "./redux/slices/StatSlice";
import { fetchSessions } from "./redux/slices/SessionSlice";
import {
  fetchActiveStudentTimelineStep,
  fetchAllTypesTrtFPFE,
  fetchConventionDetails,
  fetchDateDepotFichePFE,
  fetchDepotReportsDetails,
  fetchESPFileStatus,
  fetchFichePFEDetails,
  fetchStudentMsg,
  fetchTimelineSteps,
  fetchTraitementFichePFE,
  fetchMyFichesPFEsHistoric,
  fetchStudentGradCongrat,
  fetchVerifyAffectPEtoStudent
} from "./redux/slices/MyStudentSlice";
import {
  fetchAllAnnee,
  fetchAllClasse,
  fetchAllFilére,
  fetchAlltechno,
  fetchEncadrementValidation,
  fetchEtatValidationFiche,
  fetchEtudiantbySession,
  fetchEtudiantSansFiche,
  fetchListRechercheFiche,
  fetchStudentPFEDepasseList,
  fetchStudentsSoutenances
} from "./redux/slices/ListingSlice";
import { fetchSeceturs } from "./redux/slices/SecteurSlice";
import AuthService from "./views/services/auth.service";
import {fetchConventionsHistoric, fetchConventionTraitee} from "./redux/slices/MyConventionsSlice";

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
const currentTeacher = AuthService.getCurrentTeacher();
const currentStudent = AuthService.getCurrentStudent();
const pedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();

let persistor = persistStore(store);

React.icons = icons;

if (currentStudent !== null) {
  store.dispatch(fetchStudentMsg());
  store.dispatch(fetchConventionTraitee());
  store.dispatch(fetchDateDepotFichePFE());
  store.dispatch(fetchTimelineSteps());
  store.dispatch(fetchConventionDetails());
  store.dispatch(fetchFichePFEDetails());
  store.dispatch(fetchDepotReportsDetails());
  store.dispatch(fetchActiveStudentTimelineStep());
  store.dispatch(fetchTraitementFichePFE());
  store.dispatch(fetchAllTypesTrtFPFE());
  store.dispatch(fetchMyFichesPFEsHistoric());
  store.dispatch(fetchStudentGradCongrat());
  store.dispatch(fetchVerifyAffectPEtoStudent());
}

if(pedagogicalCoordinator !== null)
{
  store.dispatch(fetchFicheValALL());
}

//Teacher + responsable
if (currentTeacher !== null && currentTeacher.codeOptions !== null) {
  /*store.dispatch(fectchResponsableStat());
  store.dispatch(fectchEnseignantStat());
  store.dispatch(fectchEnseignantStat2());
  store.dispatch(fetchEnseignants());
  store.dispatch(fetchAllClasse());
  store.dispatch(fetchAlltechno());
  store.dispatch(fetchAllFilére());
  store.dispatch(fetchAllAnnee());
  store.dispatch(fetchEntreprises());
  store.dispatch(fetchRequests());
  store.dispatch(fetchStudents());
  store.dispatch(fecthFichePFEs());
  store.dispatch(getEtatFiche());
  store.dispatch(fetchVisits());
  store.dispatch(getFiche());
  store.dispatch(fecthAvenants());
  store.dispatch(fetchOptionStudents());
  store.dispatch(fetchEtudiantsbyEns());
  store.dispatch(getEancadrant());
  store.dispatch(fecthEvaluations());
  store.dispatch(fetchStudentsSoutenances());
  store.dispatch(fetchEtudiantSansFiche());
  store.dispatch(fetchfichePFEs());
  store.dispatch(fetchEtatValidationFiche(""));
  store.dispatch(fetchRdvPedas());
  store.dispatch(fecthListCompetences());
  //store.dispatch(fetchEncadrementValidation(""));
  store.dispatch(fetchListRechercheFiche("", "", "", "", []));
  */
}
if (currentTeacher !== null && currentTeacher.codeOption === null) {
  /*store.dispatch(fetchVisitsEtudiant());
  store.dispatch(fecthEvaluations());
  store.dispatch(getEancadrant());
  store.dispatch(fetchStudentsSoutenances());
  store.dispatch(fetchRdvPedas());
  store.dispatch(fetchEntreprises());
  store.dispatch(fectchEnseignantStat());
  store.dispatch(fectchEnseignantStat2());
  store.dispatch(fetchStudents());
  store.dispatch(fecthFichePFEs());
  store.dispatch(getEtatFiche());
  store.dispatch(fetchVisits());
  store.dispatch(getFiche());
  store.dispatch(fecthAvenants());
  store.dispatch(fecthListCompetences());
  */
}

if (currentResponsableServiceStage !== null) {
  //store.dispatch(fetchRSSDepotStat1());
  store.dispatch(fetchRSSDepotStat());  // HooksMe - 1
  store.dispatch(fetchRSSSoutenanceStat());
  store.dispatch(fetchStudentToSTNStat());
  store.dispatch(fetchStudentDoneSTNStat());
  store.dispatch(fetchNbrDemandesAnnulationConvention());
  store.dispatch(fetchNbrDemandesAnnulationConventionNotTreated());
  store.dispatch(fetchNbrDepositedConventions());
  //store.dispatch(fetchJwtPwdRSS());
  store.dispatch(fetchRSSUploadedDepotStat());
  store.dispatch(fetchPlanifiedSoutenances());
  store.dispatch(fetchUnplanifiedSoutenances());
  store.dispatch(fetchRSSValidatedDepotStat());
  store.dispatch(fetchRSSRefusedDepotStat());
  store.dispatch(fectchServiceStat());
  store.dispatch(fetchAvenants());
  store.dispatch(fetchConventions());
  store.dispatch(fetchConventionsForRSS());
  store.dispatch(fetchConventionsValidatedForRSS());
  store.dispatch(fetchDemandesAnnulationConventions());
  store.dispatch(fetchFichebydep(0));
  store.dispatch(fetchFichebydepVal());
  store.dispatch(fetchFichebydepInc());
  store.dispatch(getEancadrant());
  store.dispatch(fetchAllAnnee());
  store.dispatch(fetchEtudiantbySession(0));
  //store.dispatch(fetchStudentPFEDepasseList(0));
  store.dispatch(fetchAllClasse());
  store.dispatch(fetchAlltechno());
  store.dispatch(fetchAllFilére());
  store.dispatch(fetchSessions());
  store.dispatch(fetchStudentConfigs());
  store.dispatch(fetchTeacherConfigs());
  store.dispatch(fetchChefCoordConfigs());
  store.dispatch(fetchRespServiceStgConfigs());
  store.dispatch(fetchSeceturs());
  store.dispatch(fetchListRechercheFiche("", "", "", "", []));
}

ReactDOM.render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <App />
    </PersistGate>
  </Provider>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
