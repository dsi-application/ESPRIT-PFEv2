import { combineReducers } from "redux";
import myConventions from "./slices/MyConventionsSlice";
import myStudents from "./slices/MyStudentSlice";
import myStudentsTBS from "./slices/MyStudentTBSSlice";
import students from "./slices/StudentSlice";
import visits from "./slices/VisteSlice";
import evaluations from "./slices/EvaluationSlice";
import conventions from "./slices/ConventionSlice";
import avenants from "./slices/AvenantSlice";
import fichePFEs from "./slices/FichePFESlice";
import requests from "./slices/RequestSlice";
import rdvPedas from "./slices/RDVPedaSlice";
import encadrement from "./slices/EncadrementSlice";
import pedagogicalCoordinatorPriv from "./slices/PedagogicalCoordinatorSlice";
import stat from "./slices/StatSlice";
import depot from "./slices/DepotSlice";
import sidebarRed from "./slices/SidebarSlice";
import sessions from "./slices/SessionSlice";
import secteurs from "./slices/SecteurSlice";
import listing from "./slices/ListingSlice";
import studentConfigs from "./slices/StudentConfigSlice";
import teacherConfigs from "./slices/TeacherConfigSlice";
import chefCoordConfigs from "./slices/ChefCoordConfigSlice";
import respServiceStgConfigs from "./slices/RespServiceStgConfigSlice";

const reducers = combineReducers({
  myConventions,
  myStudents,
  myStudentsTBS,
  students,
  evaluations,
  visits,
  conventions,
  avenants,
  fichePFEs,
  requests,
  rdvPedas,
  encadrement,
  stat,
  depot,
  sidebarRed,
  sessions,
  secteurs,
  listing,
  pedagogicalCoordinatorPriv,
  studentConfigs,
  teacherConfigs,
  chefCoordConfigs,
  respServiceStgConfigs,
});

export default reducers;
