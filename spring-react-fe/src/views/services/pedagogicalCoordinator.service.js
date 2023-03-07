import axios from "axios";
import authHeader from "./auth-header";

const API_URL = process.env.REACT_APP_API_URL_PC;

class PedagogicalCoordinatorService
{

  sampleService()
  {
    return axios.get(API_URL + "/sample");
  }

  downloadEncadrementStatusByOption(mailPC)
  {
    return axios.get(API_URL + "/downloadEncadrementStatusByOption/" + mailPC, {responseType: 'blob'});
  }

  downloadEncadrementStatusByOptionAndYear(mailPC, year)
  {
    return axios.get(API_URL + "/downloadEncadrementStatusByOptionAndYear/" + mailPC + "/" + year, {responseType: 'blob'});
  }

  downloadExpertiseStatusByOptionAndYear(mailPC, selectedYear)
  {
    return axios.get(API_URL + "/downloadExpertiseStatusByOptionAndYear/" + mailPC + "/" + selectedYear, {responseType: 'blob'});
  }

  downloadExpertiseStatusByOption(mailPC)
  {
    return axios.get(API_URL + "/downloadExpertiseStatusByOption/" + mailPC, {responseType: 'blob'});
  }

  affectAcademicEncadrantToStudent(idEns, idEt, mailPC)
  {
    return axios.get(API_URL + "/affectAcademicEncadrantToStudent/" + idEns + "/" + idEt + "/" + mailPC);
  }

  affectExpertToStudent(idEns, idEt, mailPC)
  {
    return axios.get(API_URL + "/affectExpertToStudent/" + idEns + "/" + idEt + "/" + mailPC);
  }

  cancelAffectAcademicEncadrantToStudent(idEns, idEt, mailPC, justifCancelling)
  {
    return axios.get(API_URL + "/cancelAffectAcademicEncadrantToStudent/" + idEns + "/" + idEt + "/" + mailPC + "/" + justifCancelling);
  }

  gotListAcademicEncadrants()
  {
    return axios.get(API_URL + "/findAllAcademicEncadrants");
  }

  gotListAcademicEncadrantsByYear(year)
  {
    return axios.get(API_URL + "/findAllAcademicEncadrantsByYear/" + year);
  }

  gotListAcademicExperts()
  {
    return axios.get(API_URL + "/findAllAcademicExperts");
  }

  gotListAcademicExpertsByYear(year)
  {
    return axios.get(API_URL + "/findAllAcademicExpertsByYear/" + year);
  }

  gotListExperts(labelCEP)
  {
    return axios.get(API_URL + "/findAllExperts/" + encodeURIComponent(labelCEP));
  }

  gotListExpertsByCEPAndYear(labelCEP, selectedYear)
  {
    return axios.get(API_URL + "/findAllExpertsByCEPAndYear/" + encodeURIComponent(labelCEP) + "/" + selectedYear);
  }

  gotListSessions(labelSession)
  {
    return axios.get(API_URL + "/findAllSessions/" + encodeURIComponent(labelSession));
  }

  cancelAffectExpertToStudent(idEns, idEt, mailPC, justifCancelling)
  {
    return axios.get(API_URL + "/cancelAffectExpertToStudent/" + idEns + "/" + idEt + "/" + mailPC + "/" + justifCancelling);
  }

  studentsTrainedByPCE(idTea)
  {
    return axios.get(API_URL + "/studentsTrainedByPCE/" + idTea);
  }

  studentsTrainedByPCEAndYear(idTea, year)
  {
    return axios.get(API_URL + "/studentsTrainedByPCEAndYear/" + idTea + "/" + year);
  }

  findAllAcademicEncadrantsAndExpertsByYear(year)
  {
    return axios.get(API_URL + "/findAllAcademicEncadrantsAndExpertsByYear/" + year);
  }

  findAllTeachersForPresidenceAndMembreByYear(year)
  {
    return axios.get(API_URL + "/findAllTeachersForPresidenceAndMembreByYear/" + year);
  }

  gotListLibCEPByYearPARAM(year)
  {
    return axios.get(API_URL + "/listLibCEPByYearPARAM/" + year);
  }


}

export default new PedagogicalCoordinatorService();
