import axios from "axios";
import authHeader from "./auth-header";

const API_URL = process.env.REACT_APP_API_URL_MESP;

class AuthService
{

  loginStudent(id, password) {
    return axios.post(API_URL + "simpleSigninStudent", { id, password });
  }

  loginResponsableServiceStage(id, password) {
    // console.log('Service -------------------------> Sign-In Student: ' + id + " - " + password);
    return axios.post(API_URL + "simpleSigninResponsableServiceStage", {
      id,
      password,
    });
  }

  loginPedagogicalCoordinator(id, password) {
    // console.log('Service -------------------------> Sign-In Student: ' + id + " - " + password);
    return axios.post(API_URL + "simpleSigninPedagogicalCoordinator", {
      id,
      password,
    });
  }

  loginTeacher(id, password) {
    // console.log('Service -------------------------> Sign-In Teacher: ' + id + " - " + password);
    return axios.post(API_URL + "simpleSigninTeacher", { id, password });
  }

  forgotStudentPassword(id, password) {
    // console.log('Service -------------------------> Forgot-Pwd Student: ' + id + " - " + password);
    return axios.post(API_URL + "forgotStudentPassword", { id, password });
  }

  forgotResponsableServiceStagePassword(id, password) {
    // console.log('Service -------------------------> Forgot-Pwd Responsible: ' + id + " - " + password);
    return axios.post(API_URL + "forgotResponsableServiceStagePassword", {
      id,
      password,
    });
  }

  forgotPedagogicalCoordinatorPassword(id, password) {
    // console.log('Service -------------------------> Forgot-Pwd Responsible: ' + id + " - " + password);
    return axios.post(API_URL + "forgotPedagogicalCoordinatorPassword", {
      id,
      password,
    });
  }

  forgotTeacherPassword(id, password) {
    // console.log('Service -------------------------> Forgot-Pwd Teacher: ' + id + " - " + password);
    return axios.post(API_URL + "forgotTeacherPassword", { id, password });
  }

  getCurrentStudent() {
    return JSON.parse(sessionStorage.getItem("student"));
  }

  getCurrentResponsableServiceStage() {
    return JSON.parse(sessionStorage.getItem("responsableServiceStage"));
  }

  getCurrentTeacher() {
    return JSON.parse(sessionStorage.getItem("teacher"));
  }

  getCurrentPedagogicalCoordinator() {
    return JSON.parse(sessionStorage.getItem("pedagogicalCoordinator"));
  }

  secureStudentPassword(idEt, newPasswordStudent) {
    // console.log('Service -------------------------> Secure-Pwd Student: ' + idEt + " - " + newPasswordStudent);
    return axios.post(
      API_URL + "secureStudentPassword/" + idEt + "/" + encodeURIComponent(newPasswordStudent)
    );
  }
  securePedagogicalCoordinatorPassword(idEt, newPasswordPedagogicalCoordinator) {
    // console.log('Service -------------------------> Secure-Pwd PedagogicalCoordinator: ' + idEt + " - " + newPasswordPedagogicalCoordinator);
    return axios.post(
        API_URL + "securePedagogicalCoordinatorPassword/" + idEt + "/" + encodeURIComponent(newPasswordPedagogicalCoordinator)
    );
  }


  secureResponsableServiceStagePassword(idResponsible, newPasswordResponsible) {
    // console.log('Service -------------------------> Secure-Pwd Responsible: ' + idResponsible + " - " + newPasswordResponsible);
    return axios.post(
      API_URL +
        "secureResponsableServiceStagePassword/" +
        idResponsible +
        "/" +
        encodeURIComponent(newPasswordResponsible)
    );
  }

  secureTeacherPassword(idEns, newPasswordTeacher) {
    // console.log('Service -------------------------> Secure-Pwd Student: ' + idEns + " - " + newPasswordTeacher);
    return axios.post(
      API_URL + "secureTeacherPassword/" + idEns + "/" + encodeURIComponent(newPasswordTeacher)
    );
  }

  logoutResponsableServiceStage() {
    // console.log('---------------------------- Responsible');
    sessionStorage.removeItem("responsableServiceStage");
    sessionStorage.removeItem("persist:root");
    window.location.href = "/";
  }

  logoutStudent() {
    // console.log('---------------------------- Student');
    sessionStorage.removeItem("student");
    window.location.href = "/";
  }

  logoutTeacher() {
    // console.log('---------------------------- Teacher');
    sessionStorage.removeItem("teacher");
    sessionStorage.removeItem("persist:root");
    window.location.href = "/";
    //sessionStorage.removeItem("responsableStage");
  }

  logoutPedagogicalCoordinator() {
    // console.log('---------------------------- Teacher');
    sessionStorage.removeItem("pedagogicalCoordinator");
    sessionStorage.removeItem("persist:root");
    window.location.href = "/";
    //sessionStorage.removeItem("responsableStage");
  }

  validateUniquePlanification(idEtToSTN)
  {
    return axios.get(API_URL + "validateUniquePlanification/" + idEtToSTN);
  }

  rdvsAndVisiteStgByStudent(idEtToSTN)
  {
    return axios.get(API_URL + "rdvsAndVisiteStgByStudent/" + idEtToSTN);
  }

  validateAndNotifySoutenanceActors(idEtToSTN, selectedDate, selectedStartHour, selectedEndHour, salle, pedagogicalEncadrant, presjury, expertEns)
  {
    // console.log('---------------------------> LOL-1: ' + 'http://localhost:1236/api/auth/mail/upload/validateAndNotifySoutenanceActors/');
    return axios.get(API_URL + "validateAndNotifySoutenanceActors/" +
        idEtToSTN +
        "/" +
        selectedDate +
        "/" +
        selectedStartHour +
        "/" +
        selectedEndHour +
        "/" +
        salle +
        "/" +
        pedagogicalEncadrant +
        "/" +
        presjury +
        "/" +
        expertEns
    );
  }

  validateAndNotifySoutenanceActorsForMP(idEtToSTN)
  {
    return axios.get(API_URL + "validateAndNotifySoutenanceActorsForMP/" + idEtToSTN);
  }

  validateMyDepot(idStu)
  {
      return axios.get(API_URL + "upload/validateMyDepot/" + idStu);
  }

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
    // console.log('-------------------------> registerProjectCompany: ' + designation + " - " + TextScroller.js + " - " + addressMail + " - " + telephone + " - " + fax + " - " + paysLibelle + " - " + sectorEntrepriseLibelle + " - " + siegeSocial);

    return axios.post(API_URL + "addProjectCompany/", {
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

  modifyProjectCompany(
	      designation,
	      lol,
	      addressMail,
	      telephone,
	      fax,
	      paysLibelle,
	      sectorEntrepriseLibelle,
	      siegeSocial
	  ) {
	    // console.log('-------------------------> registerProjectCompany: ' + designation + " - " + TextScroller.js + " - " + addressMail + " - " + telephone + " - " + fax + " - " + paysLibelle + " - " + sectorEntrepriseLibelle + " - " + siegeSocial);

	    return axios.post(API_URL + "modifyProjectCompany/", {
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
    return axios.post(
      API_URL +
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

  modifyConvention(
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
	    console.log('---------------- 0905 ---------> Add new Convention: ' + sessionStorage.getItem('studentId'));
	    return axios.post(
	        API_URL +
	        "modifyConvention/" +
	        sessionStorage.getItem("studentId"),
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

  modifyConventionByStudent(studentId,
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
    console.log('---------------- 0905 ---------> Add new Convention: ' + sessionStorage.getItem('studentId'));
    return axios.post(
        API_URL +
        "modifyConventionByStudent/" +
        studentId,
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

  createFichePFEP1(studentId, otherMail, adresse, numTel) {
    // console.log('-------------------------> createFichePFE - P1: ' + JSON.parse(sessionStorage.getItem('student')).id + " - " + otherMail + " - " + adresse + " - "+ numTel);

    return axios.post(
      API_URL +
        "updateStudent/" +
        studentId +
        "/" +
        encodeURIComponent(otherMail) +
        "/" +
        encodeURIComponent(adresse) +
        "/" +
        numTel
    );
  }

  addTechnology(nameTechnology) {
    // console.log('SERVICE -------------------------> Add new Technology');

    return axios.post(API_URL + "addProjectTechnology/" + encodeURIComponent(nameTechnology));
  }

  updateTechnology(oldTechnologyName, newTechnologyName) {
    // console.log('-------------------------> Update the Technology name from - ' + oldTechnologyName + ' - to' + newTechnologyName);

    return axios.post(
      API_URL +
        "updateProjectTechnology/" +
        encodeURIComponent(oldTechnologyName) +
        "/" +
        encodeURIComponent(newTechnologyName)
    );
  }

  deleteTechnologyByName(nameTechnology) {
    // console.log('-------------------------> Delete selected Technology');

    return axios.delete(API_URL + "projectTechnologies/" + encodeURIComponent(nameTechnology));
  }

  deleteSupervisorCompanyByEmail(email) {
    // console.log('-------------------------> Delete selected Supervisor Company');
    return axios.get(API_URL + "deleteCompanySupervisorByMail/" + email);
  }

  addProjectSupervisor(
    idEt,
    projectSupervisorFirstName,
    projectSupervisorLastName,
    projectSupervisorPhoneNumber,
    projectSupervisorEmail
  ) {

    // console.log('---------------- Add Proj Super');
    // console.log('---------------- Details: ' + projectSupervisorFirstName + " - " + projectSupervisorLastName + " - " + projectSupervisorPhoneNumber + " - " + projectSupervisorEmail);

    let sars = API_URL +
        "addProjectSupervisor/" +
        idEt +
        "/" +
        encodeURIComponent(projectSupervisorFirstName) +
        "/" +
        encodeURIComponent(projectSupervisorLastName) +
        "/" +
        projectSupervisorPhoneNumber +
        "/" +
        encodeURIComponent(projectSupervisorEmail)
    console.log('------0312---------- Add Proj sars ', sars);
    return axios.post(sars);
  }

  verifyExistMailEncadrantEntreprise(idStudent, mailEE) {
    // // console.log('-------------------------> Update the Technology name from - ' + oldTechnologyName + ' - to' + newTechnologyName);

    return axios.get(
      API_URL +
      "verifyExistMailEncadrantEntreprise/" +
      idStudent +
      "/" +
      encodeURIComponent(mailEE)
    );
  }

  updateSupervisorCompany(currentStudent, oldEmail, firstName, lastName, numTelephone, email) {
     // console.log('------------USupP-------------> Update Supervisor Company with email ' + oldEmail + " -to- " + email);
     // console.log('-----------USupP--------------> Update Supervisor Company with firstName ' + firstName);
     // console.log('-----------USupP--------------> Update Supervisor Company with lastName ' + lastName);
     // console.log('-----------USupP--------------> Update Supervisor Company with email ' + email);
     // console.log('-----------USupP--------------> Update Supervisor Company with numTelephone ' + numTelephone);
    if (numTelephone === "") {
      numTelephone = "--";
    }
    return axios.post(
      API_URL +
        "updateCompanySupervisor/" +
        currentStudent +
        "/" +
        encodeURIComponent(oldEmail) +
        "/" +
        encodeURIComponent(firstName) +
        "/" +
        encodeURIComponent(lastName) +
        "/" +
        numTelephone +
        "/" +
        encodeURIComponent(email)
    );
  }

  treatFichePFE(idEt, libTRTFiche, libTRTConv, causeCancellingApplication) {
    // console.log('-------------------------> Cancel Plan Travail: ' + idEt + ' - ' + libTRTFiche + ' - ' + causeCancellingApplication);

    return axios.post(
      API_URL +
        "treatFichePFE/" +
        idEt +
        "/" +
        encodeURIComponent(libTRTFiche) +
        "/" +
        libTRTConv +
        "/" +
        encodeURIComponent(causeCancellingApplication)
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

    return axios.post(
      API_URL + "addAvenant/" + JSON.parse(sessionStorage.getItem("student")).id,
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

  findCompanyByName(designation) {
    return axios.get(API_URL + "/companyByName/" + encodeURIComponent(designation));
  }

  getReports(studentCode) {
    return axios.get(API_URL + "/reports/" + studentCode);
  }

  getBalanceSheets(studentCode) {
    return axios.get(API_URL + "/balanceSheets/" + studentCode);
  }

  getNewspaper(studentCode) {
    return axios.get(API_URL + "/newspaper/" + studentCode);
  }

  getEvaluationStageINGFile(studentCode) {
    // console.log('----------------24.08--------> Got File.');
    return axios.get(API_URL + "/evaluationStageING/" + studentCode);
  }

  getGanttDiagram(studentCode) {
    return axios.get(API_URL + "/ganttDiagram/" + studentCode);
  }

  uploadReportV1(file, currentUserCode, checked, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    // console.log('----------------SER--------> Upload-Cheked: ' + checked);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL + "/upload/reportv1/" + currentUserCode + "/" + checked,
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
      API_URL + "/upload/reportv2/" + currentUserCode + "/" + traineeKind + "/" + checked,
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
      API_URL + "/upload/plagiat/" + currentUserCode + "/" + checked,
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
      API_URL + "/upload/supplement/" + currentUserCode + "/" + checked,
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
      API_URL + "/upload/internshipCertificate/" + currentUserCode + "/" + checked,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  updateMyBV1(idStu)
  {
    return axios.get(API_URL + "/updateBilanFirstVersion/" + idStu);
  }

  updateMyBV2(idStu)
  {
    return axios.get(API_URL + "/updateBilanSecondVersion/" + idStu);
  }

  updateMyBV3(idStu)
  {
    return axios.get(API_URL + "/updateBilanThirdVersion/" + idStu);
  }

  updateMyDepot(idStu)
  {
    return axios.get(API_URL + "/updateFichePFEDepot/" + idStu);
  }

  updateMyRLV(idStu)
  {
    return axios.get(API_URL + "/updateReportLaunchVersion/" + idStu);
  }

  updateMyRFV(idStu)
  {
    return axios.get(API_URL + "/updateReportFinalVersion/" + idStu);
  }

  updateMyRAP(idStu)
  {
    return axios.get(API_URL + "/updateReportAntiPlagiat/" + idStu);
  }

  updateMyASP(idStu)
  {
    return axios.get(API_URL + "/updateAttestationStagePFE/" + idStu);
  }

  updateMyDCS(idStu)
  {
    return axios.get(API_URL + "/updateSupplement/" + idStu);
  }

  uploadBalanceSheetV1(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
      API_URL + "/upload/balanceSheetv1/" + currentUserCode,
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
      API_URL + "/upload/balanceSheetv2/" + currentUserCode,
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
      API_URL + "/upload/balanceSheetv3/" + currentUserCode,
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
      API_URL + "/upload/newspaper/" + currentUserCode,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      }
    );
  }

  uploadGanttDiagram(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
        API_URL + "/upload/ganttDiagram/" + currentUserCode,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          onUploadProgress,
        }
    );
  }

  uploadCancellationAgreement(file, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
        API_URL + "/upload/cancellationAgreement",
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
    return axios.delete(API_URL + "files/" + id);
  }

  hi(id) {
    // console.log('-------------------------> Delete File By Id');
    return axios.get(API_URL + "hi/" + id);
  }

  findTeachersDisponibility(selectedDate, startHour, endHour, idPE) {
    /*console.log(
      "Check Disponibility -------SERV------ SUCCESS: " +
        selectedDate +
        " - " +
        startHour +
        " - " +
        endHour
    );*/
    return axios.get(
      API_URL +
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

  findTeachersDisponibilityForAP(selectedDate, startHour, endHour, checkedStudents) {
    return axios.get(
      API_URL +
        "/teachersDisponibilityForAP/" +
        selectedDate +
        "/" +
        startHour +
        "/" +
        endHour +
        "/" +
        checkedStudents
    );
  }

  findClassroomsDisponibility(selectedDate, startHour, endHour) {
    return axios.get(
      API_URL +
        "/disponibility/classrooms/" +
        selectedDate +
        "/" +
        startHour +
        "/" +
        endHour
    );
  }

  affectJuryPresidentEns(idEt, idJuryPresident, date, startHour, endHour) {
    // console.log("Check Disponibility -----SERVLOL-------- SUCCESS", date);
    return axios.get(
      API_URL +
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
      API_URL +
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
      API_URL +
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
      API_URL +
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
    return axios.get(API_URL + "/turnSoutenanceToPlanified/" + idEtToSTN);
  }

  findTeacherByIdEns(idEns)
  {
    return axios.get(API_URL + "/findTeacherByIdEns/" + idEns);
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

      return axios.get(API_URL + "/downloadStudentPV/" +
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

  downloadBinomePV(idStu, idEt, nomet, classe, juryPresident, expert, pedagogicalEncadrant, stnDate)
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

      return axios.get(API_URL + "/downloadBinomePV/" +
        idStu +
        "/" +
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
      return axios.get(API_URL + "/downloadPlanningSoutenanceExcel/" + idSession + "/" + idRespStage, {responseType: 'blob'});
  }

  downloadPlanningSoutenancePlanifiéesExcel(idSession, idRespStage)
  {
      return axios.get(API_URL + "/downloadPlanningSoutenancePlanifiéesExcel/" + idSession + "/" + idRespStage, {responseType: 'blob'});
  }

  downloadPlanningSoutenanceNotifiéesExcel(idSession, idRespStage)
  {
      return axios.get(API_URL + "/downloadPlanningSoutenanceNotifiéesExcel/" + idSession + "/" + idRespStage, {responseType: 'blob'});
  }

  downloadPlanningSoutenanceExcelByFilter(filteredItems)
  {
      return axios.get(API_URL + "/downloadPlanningSoutenanceExcelByFilter/" + filteredItems, {responseType: 'blob'});
  }

  downloadFicheDepotPFE(idEt, classe, nomet, pairClass)
  {
    return axios.get(API_URL + "/downloadFicheDepotPFE/" +
      idEt +
      "/" +
      classe +
      "/" +
      nomet +
      "/" +
      pairClass, {responseType: 'blob'});
  }

  initiliazeSoutenanceTime(idEt)
  {
      return axios.get(API_URL + "/initiliazeSoutenanceTime/" + idEt);
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

    return axios.get(API_URL + "/fillFicheDepotPFE/" +
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
    // console.log('----------------------> 1505: ' + API_URL + "/studentsTrainedByPCE/" + option)
      return axios.get(API_URL + "/studentsTrainedByPCE/" + option);
  }

  studentsAuthorizedToSTN(idSession, idRSS)
  {
      return axios.get(API_URL + "/authorizedStudentsToSTN/" + idSession + "/" + idRSS);
  }

  studentsAllAuthorizedToSTN(idRSS)
  {
    return axios.get(API_URL + "/allAuthorizedStudentsToSTN/" + idRSS);
  }

  checkedStudentsToMultiplePlanifSTN(idRSS, idCheckedStuds)
  {
      return axios.get(API_URL + "/checkedStudentsToMultiplePlanifSTN/" + idRSS + "/" + idCheckedStuds);
  }

  giveOkConfirmNotifyMultiplePlanif(idCheckedStuds)
  {
      return axios.get(API_URL + "/giveOkConfirmNotifyMultiplePlanif/" + idCheckedStuds);
  }

  checkAllFilledEntriesToBePlanifiedSTN(idRSS, idCheckedStuds)
  {
      return axios.get(API_URL + "/checkAllFilledEntriesToBePlanifiedSTN/" + idRSS + "/" + idCheckedStuds);
  }

  checkedStudentsToAdvancedPlanifSTN(idCheckedStuds, selectedDate, selectedStartHour, selectedEndHour)
  {
      return axios.get(API_URL + "/checkedStudentsToAdvancedPlanifSTN/" + idCheckedStuds);
  }

  checkDisponibilityEncadrantForAP(idRSS, idCheckedStuds, selectedDate, selectedStartHour, selectedEndHour)
  {
      return axios.get(API_URL + "/checkDisponibilityEncadrantForAP/" + idRSS
                               + "/" + idCheckedStuds
                               + "/" + selectedDate
                               + "/" + selectedStartHour
                               + "/" + selectedEndHour);
  }

  handleAutoPlanifSallesExpertsJuryPresidentsForAP(idRSS, idCheckedStuds, selectedSalles, selectedExperts, selectedJuryPresidents)
  {
      return axios.get(API_URL + "/handleAutoPlanifSallesExpertsJuryPresidentsForAP/" + idRSS
                               + "/" + idCheckedStuds
                               + "/" + selectedSalles
                               + "/" + selectedExperts
                               + "/" + selectedJuryPresidents);
  }

  soutenancesPlanifiées(idSession, idRSS)
  {
      return axios.get(API_URL + "/soutenancesPlanifiées/" + idSession + "/" + idRSS);
  }

  soutenancesNotifiées(idSession, idRSS)
  {
      return axios.get(API_URL + "/soutenancesNotifiées/" + idSession + "/" + idRSS);
  }

  soutenancesAllPlanifiées(idRSS)
  {
    return axios.get(API_URL + "/soutenancesAllPlanifiées/" + idRSS);
  }

  soutenancesAllNotifiées(idRSS)
  {
    return axios.get(API_URL + "/soutenancesAllNotifiées/" + idRSS);
  }

  soutenancesAllNotifiéesForCPS()
  {
    return axios.get(API_URL + "/soutenancesAllNotifiéesForCPS");
  }

  validateFicheDepotPFEBtCP(idEt)
  {
      return axios.get(API_URL + "/validateFicheDepotPFEBtCP/" + idEt);
  }

  /*********************************************************** Multiple Planification ***/

  affectExpertMP(idEt, expertId)
  {
      return axios.get(API_URL + "/affectExpertMP/" + idEt + "/" + expertId);
  }

  affectJuryPresidentMP(idEt, juryPresidentId)
  {
      return axios.get(API_URL + "/affectJuryPresidentMP/" + idEt + "/" + juryPresidentId);
  }

  affectSalleMP(idEt, salleId)
  {
      return axios.get(API_URL + "/affectSalleMP/" + idEt + "/" + salleId);
  }

  affectDateSoutenanceMPWithInit(idEt, dateSoutenance)
  {
    return axios.get(API_URL + "/affectDateSoutenanceMPWithInit/" + idEt + "/" + dateSoutenance);
  }

  affectStartHourSoutenanceMPWithInit(idEt, startHourSoutenance)
  {
    return axios.get(API_URL + "/affectStartHourSoutenanceMPWithInit/" + idEt + "/" + startHourSoutenance);
  }

  affectDateSoutenanceMP(idEt, dateSoutenance)
  {
      return axios.get(API_URL + "/affectDateSoutenanceMP/" + idEt + "/" + dateSoutenance);
  }

  affectStartHourSoutenanceMP(idEt, startHourSoutenance)
  {
      return axios.get(API_URL + "/affectStartHourSoutenanceMP/" + idEt + "/" + startHourSoutenance);
  }

  affectEndHourSoutenanceMP(idEt, endHourSoutenance)
  {
      return axios.get(API_URL + "/affectEndHourSoutenanceMP/" + idEt + "/" + endHourSoutenance);
  }

  gotListSudentsByClass(codeClass, pcMail)
  {
    return axios.get(API_URL + "/listStudentsByClass/" + codeClass + "/" + pcMail);
  }

  gotListSudentsByYearAndClass(selectedYear, codeClass, pcMail)
  {
    return axios.get(API_URL + "/listStudentsByYearAndClass/" + selectedYear + "/" + codeClass + "/" + pcMail);
  }

  gotListSudentsByClassForExp(codeClass, pcMail)
  {
    return axios.get(API_URL + "/listStudentsByClassForExp/" + codeClass + "/" + pcMail);
  }

  gotListSudentsByClassAndYearForExp(codeClass, pcMail)
  {
    return axios.get(API_URL + "/listStudentsByClassAndYearForExp/" + codeClass + "/" + pcMail);
  }

  gotListStudentsByClassAndYearForExpPARAM(year, codeClass, pcMail)
  {
    return axios.get(API_URL + "/listStudentsByClassAndYearForExpPARAM/" + year + "/"+ codeClass + "/" + pcMail);
  }

  findAllAffectedStudentsToAE(idAE)
  {
    return axios.get(API_URL + "/findAllAffectedStudentsToAE/" + idAE);
  }

  findAllAffectedStudentsToAEByYear(idAE, year)
  {
    return axios.get(API_URL + "/findAllAffectedStudentsToAEByYear/" + idAE + "/" + year);
  }

  findAllAffectedStudentsToAExp(idAExp)
  {
    return axios.get(API_URL + "/findAllAffectedStudentsToAExp/" + idAExp);
  }


  getJournalStageINGFile(studentCode) {
	// console.log('----------------24.08--------> Got File.');
	return axios.get(API_URL + "/journalStageING/" + studentCode);
  }

  getAttestationStageINGFile(studentCode) {
	// console.log('----------------24.08--------> Got File.');
	return axios.get(API_URL + "/attestationStageING/" + studentCode);
  }

  getRapportStageINGFile(studentCode) {
	// console.log('----------------24.08--------> Got File.');
	return axios.get(API_URL + "/rapportStageING/" + studentCode);
  }

  applyForUpdatingMyDepot(idStudent) {
    return axios.get(
        API_URL +
        "/applyForUpdatingMyDepot/" +
        idStudent
    );
  }

  uploadJournalStageINGFile(fileBase64, currentUserCode, fileName, onUploadProgress) {
    console.log('-----------------------lolm---- PHONE - B : ' , fileName);
    let formData = new FormData();

    formData.append("fileBase64", fileBase64);

    return axios.post(
      API_URL + "/upload/journalStageING/" + currentUserCode + "/" + encodeURIComponent(fileName),
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: 'Bearer ' + student.accessToken
        },
        onUploadProgress,
      }
    );
  }

  applyForUpdatingMyDepot(idStudent) {
    return axios.get(
        API_URL +
        "/applyForUpdatingMyDepot/" +
        idStudent
    );
  }

  confirmMyDepot(idStudent) {
    return axios.get(
        API_URL +
        "/confirmMyDepotStageING/" +
        idStudent
    );
  }

  uploadAttestationStageINGFile(file, currentUserCode, onUploadProgress) {
	    console.log('----------------24.08--------> Upload: ' + file.name);
	    let formData = new FormData();

	    formData.append("file", file);

	    return axios.post(
	        API_URL + "/upload/attestationStageING/" + currentUserCode,
	        formData,
	        {
	          headers: {
	            "Content-Type": "multipart/form-data",
	          },
	          onUploadProgress,
	        }
	    );
	  }

  uploadRapportStageINGFile(file, currentUserCode, onUploadProgress) {
	    console.log('----------------24.08--------> Upload: ' + file.name);
	    let formData = new FormData();

	    formData.append("file", file);

	    return axios.post(
	        API_URL + "/upload/rapportStageING/" + currentUserCode,
	        formData,
	        {
	          headers: {
	            "Content-Type": "multipart/form-data",
	          },
	          onUploadProgress,
	        }
	    );
	  }


  findAllAffectedStudentsToAExpByYear(idAExp, year)
  {
    return axios.get(API_URL + "/findAllAffectedStudentsToAExpByYear/" + idAExp + "/" + year);
  }

  applyToInitializeMyOwnPassword(email) {
    // console.log('Service -------------------------> Forgot-Pwd Teacher: ' + id + " - " + password);
    return axios.post(API_URL + "applyToInitializeMyOwnPassword", { email });
  }

  resetMyOwnPassword(token, password) {
    //console.log('Service --------------------PIKOOO-----> Forgot-Pwd Teacher: ' + token + " - " + password);
    return axios.get(API_URL + "resetMyOwnPassword/" + token + "/" + password );
  }

  applyToInitializeMyStudentPassword(email) {
    // console.log('Service -------------------------> Forgot-Pwd Teacher: ' + id + " - " + password);
    return axios.post(API_URL + "applyToInitializeMyStudentPassword", { email });
  }

  resetMyStudentPassword(token, password) {
    //console.log('Service --------------------PIKOOO-----> Forgot-Pwd Teacher: ' + token + " - " + password);
    return axios.get(API_URL + "resetMyStudentPassword/" + token + "/" + password );
  }

  applyToInitializeMyCDCPSPassword(email) {
	// console.log('Service -------------------------> Forgot-Pwd Teacher: ' + id + " - " + password);
	return axios.post(API_URL + "applyToInitializeMyCDCPSPassword", { email });
  }

  resetMyCDCPSPassword(token, password) {
	//console.log('Service --------------------PIKOOO-----> Forgot-Pwd Teacher: ' + token + " - " + password);
	return axios.get(API_URL + "resetMyCDCPSPassword/" + token + "/" + password );
  }

  getSignedConvention(studentCode) {
    return axios.get(API_URL + "/signedConvention/" + studentCode);
  }

  uploadSignedConvention(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
        API_URL + "/upload/signedConv/" + currentUserCode,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          onUploadProgress,
        }
    );
  }

  getSignedAvenant(studentCode) {
    return axios.get(API_URL + "/signedAvenant/" + studentCode);
  }

  uploadSignedAvenant(file, currentUserCode, onUploadProgress) {
    // console.log('----------------SER--------> Upload: ' + file.name);
    let formData = new FormData();

    formData.append("file", file);

    return axios.post(
        API_URL + "/upload/signedAven/" + currentUserCode,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          onUploadProgress,
        }
    );
  }

  notifyAEToFillGrille(idEt)
  {
    return axios.get(API_URL + "/notifyAEToFillGrille/" + idEt);
  }

  listClassesByOptionAndByYear(idTea, year)
  {
    return axios.get(API_URL + "/listClassesByOptionAndByYear/" + idTea + "/" + year);
  }

}

export default new AuthService();
