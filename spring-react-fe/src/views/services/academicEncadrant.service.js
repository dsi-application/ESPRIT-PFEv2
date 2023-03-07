import axios from "axios";
import authHeader from "./auth-header";

const API_URL = process.env.REACT_APP_API_URL_AE;

class AcademicEncadrantService
{

  giveMarkforStudentEngTrainingship(idEns, idEt, noteStageIng)
  {
    return axios.get(API_URL + "/giveMarkforStudentEngTrainingship/" + encodeURIComponent(idEns) + "/" + idEt + "/" + noteStageIng);
  }

  giveMarkforStudentByExpert(idEns, idEt, noteRest)
  {
    return axios.get(API_URL + "/giveMarkforStudentByExpert/" + encodeURIComponent(idEns) + "/" + idEt + "/" + noteRest);
  }

  giveMarkforStudentByExpertAndYear(idEns, idEt, noteRest, year)
  {
    return axios.get(API_URL + "/giveMarkforStudentByExpertAndYear/" + encodeURIComponent(idEns) + "/" + idEt + "/" + noteRest + "/" + year);
  }

  modifyMarkforStudentEngTrainingship(idEns, idEt, noteStageIng)
  {
    return axios.get(API_URL + "/modifyMarkforStudentEngTrainingship/" + encodeURIComponent(idEns) + "/" + idEt + "/" + noteStageIng);
  }

  modifyMarkforStudentByExpert(idEns, idEt, noteRest)
  {
    return axios.get(API_URL + "/modifyMarkforStudentByExpert/" + encodeURIComponent(idEns) + "/" + idEt + "/" + noteRest);
  }

  modifyMarkforStudentByExpertAndYear(idEns, idEt, noteRest, year)
  {
    // console.log('--222-----------> ' + year)
    return axios.get(API_URL + "/modifyMarkforStudentByExpertAndYear/" + encodeURIComponent(idEns) + "/" + idEt + "/" + noteRest + "/" + year);
  }

  studentsCJByAEAndYear(mailTea, year)
  {
    // console.log('-------------loli11---------21.09', API_URL + "/findStudentsCJByAEAndYear/" + mailTea + "/" + year)
    return axios.get(API_URL + "/findStudentsCJByAEAndYear/" + mailTea + "/" + year);
  }

  studentsTBSByAE(mailTea)
  {
    return axios.get(API_URL + "/findStudentsTBSByAE/" + mailTea);
  }

  studentsTBSByAEAndYear(mailTea, year)
  {
    // console.log('--------------------------- SERV 21.09');
    return axios.get(API_URL + "/findStudentsTBSByAEAndYear/" + mailTea + "/" + year);
  }

  studentsForExpertiseByExpert(mailTea)
  {
    return axios.get(API_URL + "/studentsForExpertiseByExpert/" + mailTea);
  }

  studentsForExpertiseByExpertAndYear(mailTea, year)
  {
    return axios.get(API_URL + "/studentsForExpertiseByExpertAndYear/" + mailTea + "/" + year);
  }

  studentsByJuryMemberForSTN(mailMmbr, session)
  {
    return axios.get(API_URL + "/studentsByJuryMemberForSTN/" + mailMmbr + "/" + session);
  }

  studentsByJuryPresidenceForSTN(mailMmbr, session)
  {
    return axios.get(API_URL + "/studentsByJuryPresidenceForSTN/" + mailMmbr + "/" + session);
  }

  studentsTrainedByAE(idTea)
  {
    return axios.get(API_URL + "/studentsTrainedByPE/" + idTea);
  }

  studentsTrainedByAEAndYear(idTea, year)
  {
    return axios.get(API_URL + "/studentsTrainedByPEAndYear/" + idTea + "/" + year);
  }

  saveGrilleAE(idEt, noteGEA1, noteGEA2, noteGEA3, noteGEA4, noteGEA5, noteGEA6,
               noteGEAa, noteGEAz, noteGEAe, noteGEAr, noteGEAt, noteGEAy, noteGEAu, noteGEAi, noteGEAo, etatGrille)
  {

    return axios.get(API_URL + "/saveGrilleAE/" +
        idEt +
        "/" +
        noteGEA1 +
        "/" +
        noteGEA2 +
        "/" +
        noteGEA3 +
        "/" +
        noteGEA4 +
        "/" +
        noteGEA5 +
        "/" +
        noteGEA6 +
        "/" +
        noteGEAa +
        "/" +
        noteGEAz +
        "/" +
        noteGEAe +
        "/" +
        noteGEAr +
        "/" +
        noteGEAt +
        "/" +
        noteGEAy +
        "/" +
        noteGEAu +
        "/" +
        noteGEAi +
        "/" +
        noteGEAo +
        "/" +
        etatGrille
    );
  }

  saveGrilleAEAndYear(idEt, noteGEA1, noteGEA2, noteGEA3, noteGEA4, noteGEA5, noteGEA6,
                      noteGEAa, noteGEAz, noteGEAe, noteGEAr, noteGEAt, noteGEAy, noteGEAu, noteGEAi, noteGEAo, etatGrille, year)
  {

    return axios.get(API_URL + "/saveGrilleAEAndYear/" +
        idEt +
        "/" +
        noteGEA1 +
        "/" +
        noteGEA2 +
        "/" +
        noteGEA3 +
        "/" +
        noteGEA4 +
        "/" +
        noteGEA5 +
        "/" +
        noteGEA6 +
        "/" +
        noteGEAa +
        "/" +
        noteGEAz +
        "/" +
        noteGEAe +
        "/" +
        noteGEAr +
        "/" +
        noteGEAt +
        "/" +
        noteGEAy +
        "/" +
        noteGEAu +
        "/" +
        noteGEAi +
        "/" +
        noteGEAo +
        "/" +
        etatGrille +
        "/" +
        year
    );
  }

  refuseDepotStageIngenieur(idEt, motifRefusDepot)
  {
    // console.log('--------------------------- SERV 21.09');
    return axios.get(API_URL + "/refuseDepotStageIngenieur/" + idEt + "/" + encodeURIComponent(motifRefusDepot));
  }

  acceptDemandeModifDepotStageIngenieur(idEt, year)
  {
    // console.log('--------------------------- SERV 21.09');
    return axios.get(API_URL + "/acceptDemandeModifDepotStageIngenieur/" + idEt + "/" + year);
  }

  updateMyPhoneNumber(idEns, phoneNbr)
  {
    console.log('--------------------------- SERV 21.09');
    return axios.get(API_URL + "/updateMyPhoneNumber/" + idEns + "/" + phoneNbr);
  }

}

export default new AcademicEncadrantService();
