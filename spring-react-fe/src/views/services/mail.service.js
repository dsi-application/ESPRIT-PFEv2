import axios from "axios";

const API_URL_MAIL = process.env.REACT_APP_API_URL_MAIL;

class MailService
{

  validateAndNotifySoutenanceActors(idEtToSTN, selectedDate, selectedStartHour, selectedEndHour, salle, pedagogicalEncadrant, presjury, expertEns)
  {
//console.log('---------------------------> LOL-1: ' + 'http://localhost:1236/api/auth/mail/upload/validateAndNotifySoutenanceActors/');
    return axios.get(API_URL_MAIL + "validateAndNotifySoutenanceActors/" +
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

  validateMyDepot(idStu) // LOL
  {
    return axios.get(API_URL_MAIL + "upload/validateMyDepot/" + idStu);
  }


}

export default new MailService();
