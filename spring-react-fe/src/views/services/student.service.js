import axios from "axios";
import authHeader from "./auth-header";

const API_URL = process.env.REACT_APP_API_URL_STU;

class StudentService
{

  /*applyForCancelAgreement(idEt, cancellingMotif, mailCompSuperv)
  {
    return axios.get(API_URL + "/applyForCancelAgreement/" + idEt + "/" + encodeURIComponent(cancellingMotif) + "/" + encodeURIComponent(mailCompSuperv));
  }*/

  applyForCancelAgreement(idEt, cancellingMotif, mailCompSuperv)
  {
    return axios.get(API_URL + "/applyForCancelAgreement/" + idEt + "/" + encodeURIComponent(encodeURIComponent(cancellingMotif)));
  }

  addFichePFE(
    codeEtudiant,
    projectTitle,
    projectDescription,
    listOfProblematics,
    listOfFunctionnalities,
    listSelectedLibelleTechnologies,
    listOfSupervisors,
    pairId,
    diagramGanttFullPath
  ) {

    if (projectDescription === "") {
      projectDescription = "----------";
    }

    if (pairId === "" || pairId === undefined) {
      pairId = "--";
    }

    const planTravailRequest = {projectTitle: projectTitle, projectDescription: projectDescription, listOfProblematics: listOfProblematics, listOfFunctionnalities: listOfFunctionnalities, listSelectedLibelleTechnologies: listSelectedLibelleTechnologies, listOfSupervisors: listOfSupervisors, pairId: pairId, diagramGanttFullPath: diagramGanttFullPath}

    return axios.post(API_URL + "addFichePFE/" + codeEtudiant, planTravailRequest);
  }

  updateFichePFE(
    codeEtudiant,
    projectTitle,
    projectDescription,
    listOfProblematics,
    listOfFunctionnalities,
    listSelectedLibelleTechnologies,
    traineeshipCompany,
    listOfSupervisors,
    pairId,
    diagramGanttFullPath
  ) {
    if (projectDescription === "") {
      projectDescription = "----------";
    }

    if (pairId === "Without Pair") {
      pairId = "--" // "219JMT0000";
    }

    // console.log('############################################> codeEtudiant: ' + codeEtudiant);
    // console.log('############################################> projectTitle: ' + projectTitle);
    // console.log('############################################> projectDescription: ' + projectDescription);

    /*
        for (let pi of listOfProblematics)
        {
            console.log('################----------##################> Add Prob Unit: ' + pi);
        }
*/


    /*
    for (let fi of listOfFunctionnalities)
    {
      console.log('################----------##################> Add Func Unit: ' + fi);
    }

           for (let ti of listSelectedLibelleTechnologies)
           {
               console.log('################----------##################> Add Tech Unit: ' + ti);
           }

           console.log('############################################> traineeshipCompany: ' + traineeshipCompany);

           for (let si of listOfSupervisors)
           {
               console.log('################----------##################> Add Superv Unit: ' + si);
           }
           */

    // console.log('Service ---------------- Update Fiche PFE for Etudiant: ' + codeEtudiant);

    let newProblems = [];
    for (let sl of listOfProblematics) {
      // console.log('aze=================***============================' + sl);
      newProblems.push(sl);
    }

    let newFuncs = [];
    for (let sl of listOfFunctionnalities) {
      // console.log('aze=================***============================' + sl);
      newFuncs.push(sl);
    }

    let newTechs = [];
    for (let sl of listSelectedLibelleTechnologies) {
      // console.log('aze=================***============================' + sl);
      newTechs.push(sl);
    }

    let newSupervs = [];
    for (let sl of listOfSupervisors) {
      // console.log('aze=================***============================', sl);
      newSupervs.push(sl);
    }

    const planTravailRequest = {projectTitle: projectTitle, projectDescription: projectDescription, listOfProblematics: newProblems, listOfFunctionnalities: newFuncs, listSelectedLibelleTechnologies: newTechs, listOfSupervisors: newSupervs, pairId: pairId, diagramGanttFullPath: diagramGanttFullPath}

    return axios.post(API_URL + "updateFichePFE/" + codeEtudiant, planTravailRequest);
  }

  sauvegarderFichePFE(code) {
    // console.log('------------------------->Sauvegarder : ' + code);
    return axios.post(API_URL + "sauvegarderFichePFE/" + code);
  }

}

export default new StudentService();
