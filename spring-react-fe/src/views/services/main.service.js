import axios from "axios";
import authHeader from "./auth-header";

const API_URL_MAIN = process.env.REACT_APP_API_URL_MAIN;

class MainService {
  
  registerProjectCompany(
    designation,
    lol,
    addressMail,
    telephone,
    fax,
    paysLibelle,
    sectorEntrepriseLibelle,
    siegeSocial
  ) {
    // console.log('-------------------------> registerProjectCompany: ' + designation + " - " + lol + " - " + addressMail + " - " + telephone + " - " + fax + " - " + paysLibelle + " - " + sectorEntrepriseLibelle + " - " + siegeSocial);
    return axios.post(API_URL_MAIN + "addProjectCompany/", {
      designation,
      lol,
      addressMail,
      telephone,
      fax,
      paysLibelle,
      sectorEntrepriseLibelle,
      siegeSocial,
    });
  }

  addConvention(
    mail,
    dateDebut,
    dateFin,
    nomSociete,
    address,
    telephone,
    responsable
  ) {
    // console.log('---------------- ADD CONV ---------> 1: ' + mail);
    // console.log('---------------- ADD CONV ---------> 2: ' + dateDebut);
    // console.log('---------------- ADD CONV ---------> 3: ' + dateFin);
    // console.log('---------------- ADD CONV ---------> 4: ' + nomSociete);
    // console.log('---------------- ADD CONV ---------> 5: ' + address);
    // console.log('---------------- ADD CONV ---------> 6: ' + telephone);
    // console.log('---------------- ADD CONV ---------> 7: ' + responsable);

    if (address.trim() === "") {
      address = "--";
    }

    if (telephone.trim() === "") {
      telephone = "--";
    }
    // console.log('----------------222---------> Add new Convention: ' + JSON.parse(sessionStorage.getItem('student')).id);

    if (
      address.includes("?") ||
      address.includes("#") ||
      address.includes("%")
    ) {
      address = address
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    if (
      responsable.includes("?") ||
      responsable.includes("#") ||
      responsable.includes("%")
    ) {
      responsable = responsable
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    return axios.post(
      API_URL_MAIN +
        "addConvention/" +
        JSON.parse(sessionStorage.getItem("student")).id,
      {
        mail,
        dateDebut,
        dateFin,
        nomSociete,
        address,
        telephone,
        responsable,
      }
    );
  }

  createFichePFEP1(otherMail, adresse, numTel) {
    // console.log('-------------------------> createFichePFE - P1: ' + JSON.parse(sessionStorage.getItem('student')).id + " - " + otherMail + " - " + adresse + " - "+ numTel);
    if (
      adresse.includes("?") ||
      adresse.includes("#") ||
      adresse.includes("%")
    ) {
      adresse = adresse
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }
    return axios.post(
      API_URL_MAIN +
        "updateStudent/" +
        JSON.parse(sessionStorage.getItem("student")).id +
        "/" +
        otherMail +
        "/" +
        adresse +
        "/" +
        numTel
    );
  }

  addTechnology(nameTechnology) {
    // console.log('SERVICE -------------------------> Add new Technology');
    if (
      nameTechnology.includes("?") ||
      nameTechnology.includes("#") ||
      nameTechnology.includes("%")
    ) {
      nameTechnology = nameTechnology
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }
    return axios.post(API_URL_MAIN + "addProjectTechnology/" + nameTechnology);
  }

  updateTechnology(oldTechnologyName, newTechnologyName) {
    // console.log('-------------------------> Update the Technology name from - ' + oldTechnologyName + ' - to' + newTechnologyName);
    if (
      oldTechnologyName.includes("?") ||
      oldTechnologyName.includes("#") ||
      oldTechnologyName.includes("%")
    ) {
      oldTechnologyName = oldTechnologyName
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    if (
      newTechnologyName.includes("?") ||
      newTechnologyName.includes("#") ||
      newTechnologyName.includes("%")
    ) {
      newTechnologyName = newTechnologyName
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    return axios.post(
      API_URL_MAIN +
        "updateProjectTechnology/" +
        oldTechnologyName +
        "/" +
        newTechnologyName
    );
  }

  deleteTechnologyByName(nameTechnology) {
    // console.log('-------------------------> Delete selected Technology');
    if (
      nameTechnology.includes("?") ||
      nameTechnology.includes("#") ||
      nameTechnology.includes("%")
    ) {
      nameTechnology = nameTechnology
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }
    return axios.delete(API_URL_MAIN + "projectTechnologies/" + nameTechnology);
  }

  deleteSupervisorCompanyByEmail(email) {
    // console.log('-------------------------> Delete selected Supervisor Company');
    return axios.delete(API_URL_MAIN + "companySupervisorsByMail/" + email);
  }

  addFichePFE(
    codeEtudiant,
    projectTitle,
    projectDescription,
    listOfProblematics,
    listOfFunctionnalities,
    listSelectedLibelleTechnologies,
    listOfSupervisors,
    pairId
  ) {
    if (projectDescription === "") {
      projectDescription = "----------";
    }

    if (pairId === "" || pairId === undefined) {
      pairId = "219JMT0000";
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

    /*let url = "addFichePFE/" + codeEtudiant + "/" + projectTitle + "/" + projectDescription + "/" + listOfProblematics + "/" + listOfFunctionnalities + "/" + listSelectedLibelleTechnologies + "/" + listOfSupervisors + "/" + pairId;
        let formedUrl1 = ""; let formedUrl2 = ""; let formedUrl3 = "";
        if (url.includes("?"))
        {
            formedUrl1 = url.replaceAll("?", "допрос");
        }
        else
        if (url.includes("#"))
        {
            formedUrl2 = formedUrl1.replaceAll("#", "острый");
        }
        if (url.includes("%"))
        {
            formedUrl3 = formedUrl2.replaceAll("%", "процент");
        }*/

    let url =
      "addFichePFE/" +
      codeEtudiant +
      "/" +
      projectTitle +
      "/" +
      projectDescription +
      "/" +
      listOfProblematics +
      "/" +
      listOfFunctionnalities +
      "/" +
      listSelectedLibelleTechnologies +
      "/" +
      listOfSupervisors +
      "/" +
      pairId;
    if (url.includes("?") || url.includes("#") || url.includes("%")) {
      url = url
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    return axios.post(API_URL_MAIN + url);
  }

  sauvegarderFichePFE(code) {
    // console.log('------------------------->Sauvegarder : ' + code);
    return axios.post(API_URL_MAIN + "sauvegarderFichePFE/" + code);
  }

  addProjectSupervisor(
    idEntreprise,
    projectSupervisorFirstName,
    projectSupervisorLastName,
    projectSupervisorPhoneNumber,
    projectSupervisorEmail
  ) {
    if (projectSupervisorPhoneNumber.trim() === "") {
      // console.log('---------------- nullllllllllllll');
      projectSupervisorPhoneNumber = "--";
    }
    // console.log('---------------- Add Proj Super');
    // console.log('---------------- Details: ' + projectSupervisorFirstName + " - " + projectSupervisorLastName + " - " + projectSupervisorPhoneNumber + " - " + projectSupervisorEmail);
    return axios.post(
      API_URL_MAIN +
        "addProjectSupervisor/" +
        idEntreprise +
        "/" +
        projectSupervisorFirstName +
        "/" +
        projectSupervisorLastName +
        "/" +
        projectSupervisorPhoneNumber +
        "/" +
        projectSupervisorEmail
    );
  }

  updateSupervisorCompany(oldEmail, firstName, lastName, numTelephone, email) {
    // console.log('-------------------------> Update Supervisor Company with email ' + oldEmail + " -to- " + email);
    // console.log('-------------------------> Update Supervisor Company with firstName ' + firstName);
    // console.log('-------------------------> Update Supervisor Company with lastName ' + lastName);
    // console.log('-------------------------> Update Supervisor Company with email ' + email);
    // console.log('-------------------------> Update Supervisor Company with numTelephone ' + numTelephone);
    if (numTelephone === "") {
      numTelephone = "--";
    }
    return axios.post(
      API_URL_MAIN +
        "updateCompanySupervisor/" +
        oldEmail +
        "/" +
        firstName +
        "/" +
        lastName +
        "/" +
        numTelephone +
        "/" +
        email
    );
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
    pairId
  ) {
    if (projectDescription === "") {
      projectDescription = "----------";
    }

    if (pairId === "Without Pair") {
      pairId = "219JMT0000";
    }

    // console.log('############################################> codeEtudiant: ' + codeEtudiant);
    // console.log('############################################> projectTitle: ' + projectTitle);
    // console.log('############################################> projectDescription: ' + projectDescription);

    /*
        for (let pi of listOfProblematics)
        {
            console.log('################----------##################> Add Prob Unit: ' + pi);
        }

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

    let url =
      "updateFichePFE/" +
      codeEtudiant +
      "/" +
      projectTitle +
      "/" +
      projectDescription +
      "/" +
      listOfProblematics +
      "/" +
      listOfFunctionnalities +
      "/" +
      listSelectedLibelleTechnologies +
      "/" +
      traineeshipCompany +
      "/" +
      listOfSupervisors +
      "/" +
      pairId;
    if (url.includes("?") || url.includes("#") || url.includes("%")) {
      url = url
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    return axios.post(API_URL_MAIN + url);
  }

  treatFichePFE(idEt, libTRTFiche, causeCancellingApplication) {
    // console.log('-------------------------> Cancel Plan Travail: ' + idEt + ' - ' + libTRTFiche + ' - ' + causeCancellingApplication);

    if (
      causeCancellingApplication.includes("?") ||
      causeCancellingApplication.includes("#") ||
      causeCancellingApplication.includes("%")
    ) {
      causeCancellingApplication = causeCancellingApplication
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }
    return axios.post(
      API_URL_MAIN +
        "treatFichePFE/" +
        idEt +
        "/" +
        libTRTFiche +
        "/" +
        causeCancellingApplication
    );
  }

  addAvenant(
    dateSignatureConvention,
    dateDebut,
    dateFin,
    numSiren,
    qualiteResponsable,
    responsableEntreprise
  ) {
    // console.log('----------> Date Signature Convention: ' + dateSignatureConvention);
    // console.log('----------> Date Debut: ' + dateDebut);
    // console.log('----------> Date Fin: ' + dateFin);
    // console.log('----------> Num Siren: ' + numSiren);
    // console.log('----------> Qualite Responsable: ' + qualiteResponsable);
    // console.log('----------> Responsable Entreprise: ' + responsableEntreprise);

    if (
      qualiteResponsable.includes("?") ||
      qualiteResponsable.includes("#") ||
      qualiteResponsable.includes("%")
    ) {
      qualiteResponsable = qualiteResponsable
        .replaceAll("?", "допрос")
        .replaceAll("#", "острый")
        .replaceAll("%", "процент");
    }

    return axios.post(
      API_URL_MAIN + "addAvenant/" + JSON.parse(sessionStorage.getItem("student")).id,
      {
        dateSignatureConvention,
        dateDebut,
        dateFin,
        numSiren,
        qualiteResponsable,
        responsableEntreprise,
      }
    );
  }

  getReports(studentCode) {
    return axios.get(API_URL_MAIN + "/reports/" + studentCode);
  }

  getBalanceSheets(studentCode) {
    return axios.get(API_URL_MAIN + "/balanceSheets/" + studentCode);
  }

  getNewspaper(studentCode) {
    return axios.get(API_URL_MAIN + "/newspaper/" + studentCode);
  }

  uploadReportV1(file, currentUserCode, checked, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    // console.log('----------------SER--------> Upload-Cheked: ' + checked);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/reportv1/" + currentUserCode + "/" + checked,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  uploadReportV2(file, currentUserCode, traineeKind, checked, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    // console.log('----------------SER--------> Upload-Cheked: ' + checked);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/reportv2/" + currentUserCode + "/" + traineeKind + "/" + checked,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  uploadPlagiat(file, currentUserCode, checked, onUploadProgress) {
    // console.log('-------------------------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/plagiat/" + currentUserCode + "/" + checked,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

uploadSupplement(file, currentUserCode, checked, onUploadProgress) {
    // console.log('-------------------------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/supplement/" + currentUserCode + "/" + checked,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }


  uploadInternshipCertifcate(file, currentUserCode, checked, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    // console.log('----------------SER--------> Upload-Cheked: ' + checked);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/internshipCertificate/" + currentUserCode + "/" + checked,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  updateMyDepot(idStu)
  {
    return axios.get(API_URL_MAIN + "/updateFichePFEDepot/" + idStu);
  }

  updateMyRFV(idStu)
  {
    return axios.get(API_URL_MAIN + "/updateReportFinalVersion/" + idStu);
  }

  updateMyRAP(idStu)
  {
    return axios.get(API_URL_MAIN + "/updateReportAntiPlagiat/" + idStu);
  }

  updateMyASP(idStu)
  {
    return axios.get(API_URL_MAIN + "/updateAttestationStagePFE/" + idStu);
  }

  updateMyDCS(idStu)
  {
    return axios.get(API_URL_MAIN + "/updateSupplement/" + idStu);
  }

  uploadBalanceSheetV1(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/balanceSheetv1/" + currentUserCode,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  uploadBalanceSheetV2(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/balanceSheetv2/" + currentUserCode,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  uploadBalanceSheetV3(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/balanceSheetv3/" + currentUserCode,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  uploadNewspaper(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL_MAIN + "/upload/newspaper/" + currentUserCode,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  deleteReportById(id) {
    // console.log('-------------------------> Delete File By Id');
    return axios.delete(API_URL_MAIN + "files/" + id);
  }

  hi(id) {
    // console.log('-------------------------> Delete File By Id');
    return axios.get(API_URL_MAIN + "hi/" + id);
  }

  findTeachersDisponibility(selectedDate, startHour, endHour, idPE) {
    console.log(
      "Check Disponibility -------SERV------ SUCCESS: " +
        selectedDate +
        " - " +
        startHour +
        " - " +
        endHour
    );
    return axios.get(
      API_URL_MAIN +
        "/disponibility/teachers/" +
        selectedDate +
        "/" +
        startHour +
        "/" +
        endHour +
        "/" +
        idPE
    );
  }

  findClassroomsDisponibility(selectedDate, startHour, endHour) {
    return axios.get(
      API_URL_MAIN +
        "/disponibility/classrooms/" +
        selectedDate +
        "/" +
        startHour +
        "/" +
        endHour
    );
  }

  affectJuryPresidentEns(idEt, idJuryPresident, date, startHour, endHour) {
    console.log("Check Disponibility -----SERVLOL-------- SUCCESS", date);
    return axios.get(
      API_URL_MAIN +
        "/affectJuryPresidentEns/" +
        idEt +
        "/" +
        idJuryPresident +
        "/" +
        date +
        "/" +
        startHour +
        "/" +
        endHour
    );
  }

  affectExpertEns(idEt, idExpert, date, startHour, endHour) {
    return axios.get(
      API_URL_MAIN +
        "/affectExpertEns/" +
        idEt +
        "/" +
        idExpert +
        "/" +
        date +
        "/" +
        startHour +
        "/" +
        endHour
    );
  }

  affectJuryPresidentNotEns(idEt, fullNameJuryPresident, date, startHour, endHour) {
    return axios.get(
      API_URL_MAIN +
        "/affectJuryPresidentNotEns/" +
        idEt +
        "/" +
        fullNameJuryPresident +
        "/" +
        date +
        "/" +
        startHour +
        "/" +
        endHour
    );
  }

  affectClassRoom(idEt, codeSalle, selectedDate, startHour, endHour) {
    return axios.get(
      API_URL_MAIN +
        "affectClassRoom/" +
        idEt +
        "/" +
        codeSalle +
        "/" +
        selectedDate +
        "/" +
        startHour +
        "/" +
        endHour
    );
  }

  turnSoutenanceToPlanified(idEtToSTN)
  {
    return axios.get(API_URL_MAIN + "/turnSoutenanceToPlanified/" + idEtToSTN);
  }

  findTeacherByIdEns(idEns)
  {
    return axios.get(API_URL_MAIN + "/findTeacherByIdEns/" + idEns);
  }

  // /downloadStudentPV/{idEt}/{nomet}/{classe}/{juryPresident}/{expert}/{pedagocalEncadrant}/{stnDate}
  downloadStudentPV(idEt, nomet, classe, juryPresident, expert, pedagogicalEncadrant, stnDate)
  {
      if(juryPresident === undefined || juryPresident === "")
      {
          juryPresident = "--";
      }

      if(expert === undefined || expert === "")
      {
          expert = "--";
      }

      if(pedagogicalEncadrant === undefined || pedagogicalEncadrant === "")
      {
          pedagogicalEncadrant = "--";
      }

      if(stnDate === undefined || stnDate === "")
      {
          stnDate = "--";
      }

      return axios.get(API_URL_MAIN + "/downloadStudentPV/" + 
        idEt + 
        "/" +
        nomet +
        "/" +
        classe +
        "/" +
        juryPresident +
        "/" +
        expert +
        "/" +
        pedagogicalEncadrant +
        "/" +
        stnDate, {responseType: 'blob'});
  }

  downloadPlanningSoutenanceExcel(idSession, idRespStage)
  {
      return axios.get(API_URL_MAIN + "/downloadPlanningSoutenanceExcel/" + idSession + "/" + idRespStage, {responseType: 'blob'});
  }
  
  downloadPlanningSoutenancePlanifiéesExcel(idSession, idRespStage)
  {
      return axios.get(API_URL_MAIN + "/downloadPlanningSoutenancePlanifiéesExcel/" + idSession + "/" + idRespStage, {responseType: 'blob'});
  }

  downloadPlanningSoutenanceExcelByFilter(filteredItems)
  {
      return axios.get(API_URL_MAIN + "/downloadPlanningSoutenanceExcelByFilter/" + filteredItems, {responseType: 'blob'});
  }

  downloadFicheDepotPFE(idEt, nomet, classe)
  {
    return axios.get(API_URL_MAIN + "/downloadFicheDepotPFE/" + 
      idEt + 
      "/" +
      nomet +
      "/" +
      classe, {responseType: 'blob'});
  }

  initiliazeSoutenanceTime(idEt)
  {
      return axios.get(API_URL_MAIN + "/initiliazeSoutenanceTime/" + idEt);
  }


  fillFicheDepotPFE(idEt, 
                    notePE,
                    onPlanningStg,
                    onBilanPerDebStg,
                    onBilanPerMilStg,
                    onBilanPerFinStg,
                    onFicheEvalMiPar,
                    onFicheEvalFin,
                    onJournalBord,
                    onRapportStg,
                    dateRDV1,
                    dateRDV2,
                    dateRDV3,
                    presenceKindRDV1,
                    presenceKindRDV2,
                    presenceKindRDV3,
                    satisfactionRDV1,
                    satisfactionRDV2,
                    satisfactionRDV3,
                    etatTreat)
  {

    if(notePE === "")
    {
        notePE = 0;
    }

    if(satisfactionRDV1 === "" || satisfactionRDV1 === null)
    {
        satisfactionRDV1 = "--";
    }

    if(satisfactionRDV2 === "" || satisfactionRDV2 === null)
    {
        satisfactionRDV2 = "--";
    }

    if(satisfactionRDV3 === "" || satisfactionRDV3 === null)
    {
        satisfactionRDV3 = "--";
    }

    return axios.get(API_URL_MAIN + "/fillFicheDepotPFE/" + 
      idEt + 
      "/" +
      notePE + 
      "/" +
      onPlanningStg +
      "/" +
      onBilanPerDebStg +
      "/" +
      onBilanPerMilStg +
      "/" +
      onBilanPerFinStg +
      "/" +
      onFicheEvalMiPar +
      "/" +
      onFicheEvalFin +
      "/" +
      onJournalBord +
      "/" +
      onRapportStg +
      "/" +
      dateRDV1 +
      "/" +
      dateRDV2 +
      "/" +
      dateRDV3 +
      "/" +
      presenceKindRDV1 +
      "/" +
      presenceKindRDV2 +
      "/" +
      presenceKindRDV3 +
      "/" +
      satisfactionRDV1 +
      "/" +
      satisfactionRDV2 +
      "/" +
      satisfactionRDV3 +
      "/" +
      etatTreat);
  }

  studentsTrainedByPCE(option)
  {
      return axios.get(API_URL_MAIN + "/studentsTrainedByPCE/" + option);
  }

  studentsTrainedByAE(idTea)
  {
      return axios.get(API_URL_MAIN + "/studentsTrainedByPE/" + idTea);
  }

  studentsAuthorizedToSTN(idSession, idRSS)
  {
      return axios.get(API_URL_MAIN + "/authorizedStudentsToSTN/" + idSession + "/" + idRSS);
  }

  soutenancesPlanifiées(idSession, idRSS)
  {
      return axios.get(API_URL_MAIN + "/soutenancesPlanifiées/" + idSession + "/" + idRSS);
  }

  validateFicheDepotPFEBtCP(idEt)
  {
      return axios.get(API_URL_MAIN + "/validateFicheDepotPFEBtCP/" + idEt);
  }

}

export default new MainService();
