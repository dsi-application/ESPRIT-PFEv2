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

    // console.log('---------- projectDescription: ' , projectDescription);
    if (projectDescription === "") {
      projectDescription = "----------";
    }

    if (pairId === "" || pairId === undefined) {
      pairId = "--"; // 151JMT1632 //219JMT0000";
    }

    // console.log('############################################> codeEtudiant: ' + codeEtudiant + " - " + pairId);
    // console.log('############################################> projectTitle: ' + projectTitle);
    // console.log('############################################> projectDescription: ' + projectDescription);

    /*
        for (let pi of listOfProblematics)
        {
            // console.log('################----------##################> Add Prob Unit: ' + pi);
        }

        for (let fi of listOfFunctionnalities)
        {
            console.log('################----------##################> Add Func Unit: ' + fi);
        }

        for (let ti of listSelectedLibelleTechnologies)
        {
            console.log('################----------##################> Add Tech Unit: ' + ti);
        }

        // console.log('############################################> traineeshipCompany: ' + traineeshipCompany);

        for (let si of listOfSupervisors)
        {
            console.log('################----------##################> Add Superv Unit: ' + si);
        }
        */

    // console.log('Service ---------------- Add Plan Travail for Etudiant: ' + codeEtudiant);


    let newProblems = [];
    for (let sl of listOfProblematics) {
      // console.log('aze=================***============================' + sl);
      newProblems.push(encodeURIComponent(sl));
    }

    let newFuncs = [];
    for (let sl of listOfFunctionnalities) {
      // console.log('aze=================***============================' + sl);
      newFuncs.push(encodeURIComponent(sl));
    }

    let newTechs = [];
    for (let sl of listSelectedLibelleTechnologies) {
      // console.log('aze=================***============================' + sl);
      newTechs.push(encodeURIComponent(sl));
    }

    let newSupervs = [];
    for (let sl of listOfSupervisors) {
      newSupervs.push(encodeURIComponent(sl));
      // console.log('aze=================***=gggg========supervs====', sl);
    }

    let url =
        "addFichePFE/" +
        codeEtudiant +
        "/" +
        encodeURIComponent(projectTitle) +
        "/" +
        encodeURIComponent(projectDescription) +
        "/" +
        newProblems +
        "/" +
        newFuncs +
        "/" +
        newTechs +
        "/" +
        newSupervs +
        "/" +
        pairId +
        "/" +
        encodeURIComponent(diagramGanttFullPath);

    // console.log(url);

    return axios.post(API_URL + url);
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

    // console.log('Service ---------------- Update Plan Travail for Etudiant: ' + codeEtudiant);

    let newProblems = [];
    for (let sl of listOfProblematics) {
      // console.log('aze=================***============================' + sl);
      newProblems.push(encodeURIComponent(sl));
    }

    let newFuncs = [];
    for (let sl of listOfFunctionnalities) {
      // console.log('aze=================***============================' + sl);
      newFuncs.push(encodeURIComponent(sl));
    }

    let newTechs = [];
    for (let sl of listSelectedLibelleTechnologies) {
      // console.log('aze=================***============================' + sl);
      newTechs.push(encodeURIComponent(sl));
    }

    let newSupervs = [];
    for (let sl of listOfSupervisors) {
      // console.log('aze=================***============================', sl);
      newSupervs.push(encodeURIComponent(sl));
    }
    let url =
        "updateFichePFE/" +
        encodeURIComponent(codeEtudiant) +
        "/" +
        encodeURIComponent(projectTitle) +
        "/" +
        encodeURIComponent(projectDescription) +
        "/" +
        newProblems +
        "/" +
        newFuncs +
        "/" +
        newTechs +
        "/" +
        traineeshipCompany +
        "/" +
        newSupervs +
        "/" +
        pairId +
        "/" +
        encodeURIComponent(diagramGanttFullPath);

    // console.log('--------------------- 0311 -------------> RESULT: ' + API_URL + url);

    return axios.post(API_URL + url);
  }

  sauvegarderFichePFE(code) {
    // console.log('------------------------->Sauvegarder : ' + code);
    return axios.post(API_URL + "sauvegarderFichePFE/" + code);
  }

}

export default new StudentService();
