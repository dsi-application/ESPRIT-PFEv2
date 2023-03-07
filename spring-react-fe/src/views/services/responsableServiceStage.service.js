import axios from "axios";
import authHeader from "./auth-header";

const API_URL = process.env.REACT_APP_API_URL_RSS;

class ResponsableServiceStageService
{

  gotListStudentsByDepartement(codeDept)
  {
    return axios.get(API_URL + "/listStudentsByDept/" + codeDept);
  }

  downloadAllEncadrementStatus()
  {
    return axios.get(API_URL + "/downloadAllEncadrementStatus", {responseType: 'blob'});
  }
  
  downloadAllEncadrementStatusByYear(year)
  {
    return axios.get(API_URL + "/downloadAllEncadrementStatusByYear/" + year, {responseType: 'blob'});
  }

  downloadAllExpertiseStatusByYear(year)
  {
    return axios.get(API_URL + "/downloadAllExpertiseStatusByYear/" + year, {responseType: 'blob'});
  }
  
  downloadAllEncadrementAndExpertiseStatusByYear(year)
  {
    return axios.get(API_URL + "/downloadAllEncadrementAndExpertiseStatusByYear/" + year, {responseType: 'blob'});
  }
  
  downloadAPresidenceAndMembrejuryForSTNStatusByYear(year)
  {
    return axios.get(API_URL + "/downloadAPresidenceAndMembrejuryForSTNStatusByYear/" + year, {responseType: 'blob'});
  }

}

export default new ResponsableServiceStageService();
