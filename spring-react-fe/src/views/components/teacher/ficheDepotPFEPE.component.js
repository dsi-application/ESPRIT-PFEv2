// init 1
import React, {Component} from "react";

import AuthService from "../../services/auth.service";
import AEService from "../../services/academicEncadrant.service";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CTabContent, CTabPane, CTabs, CNav, CNavItem, CNavLink,
  CAlert,
  CCol,
  CContainer,
  CDataTable,
  CListGroup,
  CListGroupItem,
  CRow,
  CTooltip
} from "@coreui/react";

import Spinner from "react-bootstrap/Spinner";

import CIcon from "@coreui/icons-react";
import {freeSet} from '@coreui/icons';

import "../../css/style.css";
import TextField from '@material-ui/core/TextField';
import "react-datepicker/dist/react-datepicker.css";
import ReactTextTransition from "react-text-transition";
import moment from "moment";
import axios from "axios";

import Select from "react-select";
import makeAnimated from "react-select/animated";

const animatedComponents = makeAnimated();
const API_URL_PC = process.env.REACT_APP_API_URL_PC;
// init2
const API_URL = process.env.REACT_APP_API_URL_MESP;
const API_URL_AE = process.env.REACT_APP_API_URL_AE;
const currentTeacher = AuthService.getCurrentTeacher();


const getBadgeEtatFiche = (etatFiche) => {
  switch (etatFiche) {
    case "PAS ENCORE":
      return "danger";
    case "DEPOSE":
      return "info";
    case "VALIDE":
      return "success";
    default:
      return "dark";
  }
};

const getBadgeEtatTreatDepot = (etatTreatDepot) => {
  switch (etatTreatDepot) {
    case "TRAITE":
      return "success";
    case "EN ATTENTE":
      return "danger";
    default:
      return "info";
  }
};

const fields = [
  {
    key: 'identifiant',
    label: 'Identifiant',
    _style: {width: '7%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'fullName',
    label: 'Prénom & Nom',
    _style: {width: '25%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'option',
    label: 'Option',
    _style: {width: '6%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'classe',
    label: 'Classe',
    _style: {width: '9%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'dateDepot',
    label: 'Date Dépot',
    _style: {width: '11%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'session',
    label: 'Session',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'etatFiche',
    label: 'État Dossier',
    _style: {width: '9%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'etatTreatDepot',
    label: 'État Fiche Dépôt',
    _style: {width: '9%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'action',
    label: 'Action',
    _style: {width: '10%'},
    sorter: false,
    filter: false
  }
];


export default class FicheDepotPFE extends Component {
  constructor(props) {
    super(props);

    // init3
    this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
    this.loadStudentsAffectedToAE = this.loadStudentsAffectedToAE.bind(this);
    this.loadStudentsAffectedToAEByDefaultYear = this.loadStudentsAffectedToAEByDefaultYear.bind(this);
    this.handleConsultStudentDetails = this.handleConsultStudentDetails.bind(this);
    this.handleClosePopupShowStudentDetails = this.handleClosePopupShowStudentDetails.bind(this);
    this.handleClosePopupSaveDepotPFE = this.handleClosePopupSaveDepotPFE.bind(this);
    this.handleClosePopupValidateDepotPFE = this.handleClosePopupValidateDepotPFE.bind(this);
    this.handleClosePopupTreatDepotPFE = this.handleClosePopupTreatDepotPFE.bind(this);
    this.handleFillFicheDepotPFE = this.handleFillFicheDepotPFE.bind(this);
    this.handleValidateFicheDepotPFE = this.handleValidateFicheDepotPFE.bind(this);
    this.downloadGrilleAE = this.downloadGrilleAE.bind(this);
    this.handleOpenPopupRequestSaveFichePFE = this.handleOpenPopupRequestSaveFichePFE.bind(this);
    this.handleOpenPopupRequestConfirmFichePFE = this.handleOpenPopupRequestConfirmFichePFE.bind(this);
    this.handleClosePopupRequestSaveFichePFE = this.handleClosePopupRequestSaveFichePFE.bind(this);
    this.handleClosePopupRequestConfirmFichePFE = this.handleClosePopupRequestConfirmFichePFE.bind(this);
    this.handleTreatDepotPFE = this.handleTreatDepotPFE.bind(this);

    this.state = {
      listOfStudentsTrainedByPE: [],
      listOfAuthorizedStudentsToSTNByFilter: [],
      openPopupShowStudentDetails: false,
      openPopupTreatDepotPFE: false,
      openPopupSaveDepotPFE: false,
      openPopupValidateDepotPFE: false,
      selectedStudentId: "",
      selectedStudentFullName: "",
      selectedStudentMail: "",
      selectedStudentTel: "",
      selectedStudentDepartment: "",
      selectedStudentOption: "",
      selectedStudentSocieteLibelle: "",
      selectedStudentProjectTitle: "",
      selectedStudentClasse: "",
      restMarkStatus: "",
      selectedStudentExpertMarkForRest: 0,
      noteGEA1: 2,
      noteGEA2: 0,
      noteGEA3: 1.6,
      noteGEA5: 0,
      noteGEA6: 0,
      noteGEAa: 0,
      noteGEAz: 0,
      noteGEAe: 0,
      noteGEAr: 0,
      noteGEAt: 0,
      noteGEAy: 1,
      noteGEAu: 1,
      noteGEAi: 0,
      noteGEAo: 0,
      showSpinnerSaveDepotPFE: false,
      showSpinnerValidateDepotPFE: false,
      etatTreat: "NO",
      emptyTreat: "INIT",
      showButtons: "INIT",
      loadList: true,
      openPopupShowRequestSaveFD: false,
      openPopupShowRequestConfirmFD: false,
      allSessionsLabel: [],
      selectedYear: '2022',
      loadStudentsCJByYear: true
    };

    let requestSessions = new XMLHttpRequest();
    requestSessions.open(
      "GET",
      API_URL_PC + "findAllSessions",
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestSessions.send(null);
    let resultSession = JSON.parse(requestSessions.responseText);
    // console.log('-------------------> >sars 1110: ' + result);

    for (let cls of resultSession) {
      // console.log('----sars 1110---------- unit cls: ' + cls);
      this.state.allSessionsLabel.push({
        value: cls,
        label: cls,
        color: "#00B8D9",
      });
    }

    let requestAv = new XMLHttpRequest();
    requestAv.open("GET",API_URL + "pwdESPTea/" + encodeURIComponent(currentTeacher.id) + "/" + encodeURIComponent(currentTeacher.idEns), false);
    requestAv.send(null);
    let pwdTea = requestAv.responseText;

    let requestTkn = new XMLHttpRequest();
    requestTkn.open("GET", API_URL + "validateJWT/" + currentTeacher.token, false);
    requestTkn.send(null);
    let tkBack = requestTkn.responseText;

    let requestATkn = new XMLHttpRequest();
    requestATkn.open("GET", API_URL + "validateJWT/" + currentTeacher.accessToken, false);
    requestATkn.send(null);
    let atkBack = requestATkn.responseText;


    if(currentTeacher !== null && pwdTea === currentTeacher.password && atkBack === 'YES' && tkBack === 'YES')
    {
      this.loadStudentsAffectedToAEByDefaultYear();
    }
    else
    {
      localStorage.clear();
      sessionStorage.clear();
      window.location.href = "/";
    }


  }
  loadStudentsAffectedToAEByDefaultYear() {
    AEService.studentsTrainedByAEAndYear(currentTeacher.id, this.state.selectedYear).then(
      // AEService.studentsTrainedByAE(currentTeacher.id).then(
      (response) => {
        // console.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);

        let stuList = this.state.listOfStudentsTrainedByPE.slice();
        for (let stu of response.data) {
          // console.log('------------------------> TREATMENT 2.1 : ', stu);
          const studentObj = stu;
          stuList.push(stu);
        }

        this.setState({
          loadStudentsCJByYear: false,
          listOfStudentsTrainedByPE: stuList
        });
        // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

      },
      (error) => {
      }
    );
  }


  loadStudentsAffectedToAE = (selectedYearObject) => {

    // console.log('-------------1----------> 21.09.22: ' , this.state.listOfStudentsCJTrainedByPE);
    // console.log('-------------2.1----------> 21.09.22: ' , selectedYearObject.label);

    this.setState({
      loadStudentsCJByYear: true,
      selectedYear: selectedYearObject.label
    });

    // console.log('----dd---------2.2----------> 21.09.22: ' , this.state.loadStudentsCJByYear);
    // console.log('-------------2.3----------> 21.09.22: ' , this.state.selectedYear);

    // console.log('-------------2.4----------> 21.09.22: ' , currentTeacher.id);
    // console.log('-------------2.5----------> 21.09.22: ' , selectedYearObject.label);

    AEService.studentsTrainedByAEAndYear(currentTeacher.id, selectedYearObject.label).then(
      (response) => {

        this.setState({
          listOfStudentsTrainedByPE: []
        });
        // console.log('-------------3----------->  21.09.22: ' + new Date() + " - " + this.state.loadStudentsCJByYear);

        let stuList = this.state.listOfStudentsTrainedByPE.slice();
        for (let stu of response.data) {
          // console.log('------------------------> TREATMENT 2.1 : ', stu);
          const studentObj = stu;
          stuList.push(stu);
        }

        this.setState({
          loadStudentsCJByYear: false,
          listOfStudentsTrainedByPE: stuList
        });
        // console.log('--------------4----------> 21.09.22: ' + new Date() + " - " + this.state.loadStudentsCJByYear);
      },
      (error) => {
      }
    );

  };

  onChangeAuthorizedStudentsToSTNByFilter(filtredItems) {
    this.setState({listOfAuthorizedStudentsToSTNByFilter: []})

    let filteredIdentifiants = [];
    if (filtredItems.length !== this.state.listOfStudentsTrainedByPE.length) {
      for (let item of filtredItems) {
        filteredIdentifiants.push(item.identifiant);
      }
      // console.log("Check Disponibility --excel------TextScroller.js-------------> THIS-2: ", filteredIdentifiants);

      this.setState({
        listOfAuthorizedStudentsToSTNByFilter: filteredIdentifiants,
        planifyKind: "PWFILTER",
      })
    }
  }

  handleOpenPopupRequestSaveFichePFE(e)
  {
    this.setState({openPopupShowRequestSaveFD: true});
  }

  handleOpenPopupRequestConfirmFichePFE(e)
  {
    this.setState({openPopupShowRequestConfirmFD: true});
  }

  handleClosePopupRequestSaveFichePFE(e)
  {
    this.setState({openPopupShowRequestSaveFD: false});
  }

  handleClosePopupRequestConfirmFichePFE(e)
  {
    this.setState({openPopupShowRequestConfirmFD: false});
  }

  handleFillFicheDepotPFE(e) {

    this.setState({
      showSpinnerSaveDepotPFE: true
    });

    // console.log('--------------1406----------> RES-2: ' + this.state.noteGEA1);
    // console.log('--------------1406----------> RES-3: ' + this.state.noteGEA2);
    // console.log('--------------1406----------> RES-4: ' + this.state.noteGEA3);
    // console.log('--------------1406----------> RES-6: ' + this.state.noteGEA5);
    // console.log('--------------1406----------> RES-7: ' + this.state.noteGEA6);

    // console.log('--------------1406----------> RES-9: ' + this.state.noteGEAa);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAz);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAe);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAr);
    // console.log('--------------1406----------> RES-9: ' + this.state.noteGEAt);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAy);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAu);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAi);
    // console.log('--------------1506----------> RES-A: ' + this.state.noteGEAo);

    let restMarkByExpert = this.state.selectedStudentExpertMarkForRest;
    if(this.state.restMarkStatus === "NOTYET")
    {
      restMarkByExpert = Number(-1);
    }

    AEService.saveGrilleAEAndYear(this.state.selectedStudentId,
      this.state.noteGEA1,
      this.state.noteGEA2,
      this.state.noteGEA3,
      restMarkByExpert,//this.state.selectedStudentExpertMarkForRest,
      this.state.noteGEA5,
      this.state.noteGEA6,
      this.state.noteGEAa,
      this.state.noteGEAz,
      this.state.noteGEAe,
      this.state.noteGEAr,
      this.state.noteGEAt,
      this.state.noteGEAy,
      this.state.noteGEAu,
      this.state.noteGEAi,
      this.state.noteGEAo,
      "01",
      this.state.selectedYear).then(
      (response) => {
        // console.log('--------------------------------> DONE 1506');
        this.setState({
          showSpinnerSaveDepotPFE: false,
          openPopupShowRequestSaveFD: false,
          openPopupSaveDepotPFE: true,
          etatTreat: "01"
        });
      },
      (error) => {
      }
    );
  }

  handleValidateFicheDepotPFE(e) {

    this.setState({
      showSpinnerValidateDepotPFE: true
    });

    // console.log('--------------1406----------> RES-2: ' + this.state.noteGEA1);
    // console.log('--------------1406----------> RES-3: ' + this.state.noteGEA2);
    // console.log('--------------1406----------> RES-4: ' + this.state.noteGEA3);
    // console.log('--------------1406----------> RES-6: ' + this.state.noteGEA5);
    // console.log('--------------1406----------> RES-7: ' + this.state.noteGEA6);

    // console.log('--------------1406----------> RES-9: ' + this.state.noteGEAa);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAz);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAe);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAr);
    // console.log('--------------1406----------> RES-9: ' + this.state.noteGEAt);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAy);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAu);
    // console.log('--------------1406----------> RES-A: ' + this.state.noteGEAi);
    // console.log('--------------1506----------> RES-A: ' + this.state.noteGEAo);

    let restMarkByExpert = this.state.selectedStudentExpertMarkForRest;
    if(this.state.restMarkStatus === "NOTYET")
    {
      restMarkByExpert = Number(-1);
    }

    AEService.saveGrilleAEAndYear(this.state.selectedStudentId,
      this.state.noteGEA1,
      this.state.noteGEA2,
      this.state.noteGEA3,
      restMarkByExpert, //selectedStudentExpertMarkForRest,
      this.state.noteGEA5,
      this.state.noteGEA6,
      this.state.noteGEAa,
      this.state.noteGEAz,
      this.state.noteGEAe,
      this.state.noteGEAr,
      this.state.noteGEAt,
      this.state.noteGEAy,
      this.state.noteGEAu,
      this.state.noteGEAi,
      this.state.noteGEAo,
      "02",
      this.state.selectedYear).then(
      (response) => {

        let etatTreatDepot = "TRAITE";
        let idEt = this.state.selectedStudentId;

        this.setState({
          listOfStudentsTrainedByPE: this.state.listOfStudentsTrainedByPE.map(el => (el.identifiant === idEt ? {
            ...el,
            etatTreatDepot
          } : el)),
          showSpinnerValidateDepotPFE: false,
          openPopupShowRequestConfirmFD: false,
          openPopupValidateDepotPFE: true,
          showButtons: "HIDE",
          etatTreat: "02"
        });

        // console.log('---------------------------> SARS 2006 : ', this.state.etatTreat);

      },
      (error) => {
      }
    );

  }

  downloadGrilleAE(e) {
    axios.get(`${process.env.REACT_APP_API_URL_AE}` + "downloadGrilleAE/" + this.state.selectedStudentId + "/" + this.state.selectedYear, { responseType: "blob" })
      .then((response) => {
        const file = new Blob([response.data], {type: 'application/pdf'});
        const fileURL = URL.createObjectURL(file);


        const contentDispo = response.headers['content-disposition'];
        const fileName = contentDispo.substring(21);

        let a = document.createElement('a');
        a.href = fileURL;
        a.download = fileName;
        a.click();
        window.open(fileURL);
      });
  }

  handleConsultStudentDetails(idEt, fullName) {
    var requestStu = new XMLHttpRequest();
    requestStu.open("GET", API_URL + "studentMailTel/" + idEt, false);
    requestStu.send(null);
    let stu = JSON.parse(requestStu.responseText);

    this.setState({
      openPopupShowStudentDetails: true,
      selectedStudentFullName: fullName,
      selectedStudentMail: stu.mail,
      selectedStudentTel: stu.tel,
    });
  }

  handleClosePopupShowStudentDetails() {
    this.setState({openPopupShowStudentDetails: false});
  }

  handleClosePopupSaveDepotPFE() {
    this.setState({
      openPopupSaveDepotPFE: false,
      openPopupTreatDepotPFE: false
    });
  }

  handleClosePopupValidateDepotPFE() {
    this.setState({openPopupValidateDepotPFE: false});
  }

  handleTreatDepotPFE(idEt, fullName, classe, dateDepot) {
    this.setState({
      emptyTreat: "INIT"
    })
    // console.log("---------------------- Selected Student" + idEt + " - " + fullName);

    let requestPE = new XMLHttpRequest();
    requestPE.open("GET", API_URL_AE + "studentGrilleEncadrement/" + idEt + "/" + this.state.selectedYear, false);
    requestPE.send(null);
    let pe = JSON.parse(requestPE.responseText);

    console.log("----------------------2605", pe)

    let requestDFP = new XMLHttpRequest();
    requestDFP.open("GET", API_URL_AE + "grilleEncadrementDto/" + idEt, false);
    requestDFP.send(null);
    let depotFP = JSON.parse(requestDFP.responseText);

    this.setState({
      openPopupTreatDepotPFE: true,
      selectedStudentFullName: fullName,
      selectedStudentClasse: classe,
      selectedStudentId: idEt,
      selectedStudentDepartment: pe.department,
      selectedStudentOption: pe.option,
      selectedStudentSocieteLibelle: pe.societeLibelle,
      selectedStudentProjectTitle: pe.titreProjet,
      selectedStudentDateFinStage: pe.dateDepot,
      // selectedStudentExpertMarkForRest: pe.noteRest,
      // noteGEA6: Number(this.state.noteGEA3)*40/100 + Number(pe.noteRest)*40/100 + Number(this.state.noteGEA5)*20/100
    });

    // console.log('--------------26.10------1-----> VERIF: ' + pe.noteRest);

    if(Number(pe.noteRest) === Number(-1))
    {
      // console.log('-------Object--- Status 1 -------');
      this.setState({
        selectedStudentExpertMarkForRest: 0,
        restMarkStatus: "NOTYET",
        noteGEA6: Number(this.state.noteGEA3)*40/100 + Number(this.state.noteGEA5)*20/100
      });
      // console.log('--------------26.10-----2.1------> VERIF: ' + this.state.restMarkStatus);
    }
    if(Number(pe.noteRest) !== Number(-1))
    {
      // console.log('-----Object----- Status 2 -------');
      this.setState({
        selectedStudentExpertMarkForRest: pe.noteRest,
        restMarkByExpert: 0,
        restMarkStatus: "DONE",
        noteGEA6: Number(this.state.noteGEA3)*40/100 + Number(pe.noteRest)*40/100 + Number(this.state.noteGEA5)*20/100
      });
      // console.log('--------------26.10-----2.2------> VERIF: ' + this.state.restMarkStatus +"--" + this.state.selectedStudentExpertMarkForRest );
    }

    if (depotFP.emptyObject === "EMPTY")
    {
      // console.log('---------- Status 3 -------');
      this.setState({
        etatTreat: "NO",
        emptyTreat: "YES",
        showButtons: "INIT"
      })
    }

    if (depotFP.emptyObject === "FILLED")
    {
      // console.log('---------- Status 4 -------');
      this.setState({
        etatTreat: depotFP.etatGrille,
        noteGEAa: depotFP.notePlanningStage,
        noteGEAz: depotFP.noteBilanPeriodiqueDebutStage,
        noteGEAe: depotFP.noteBilanPeriodiqueMilieuStage,
        noteGEAr: depotFP.noteBilanPeriodiqueFinStage,
        noteGEAt: depotFP.noteJournalBord,
        noteGEAy: depotFP.noteFicheEvaluationMiParcour,
        noteGEAu: depotFP.noteFicheEvaluationFinale,
        noteGEAi: depotFP.noteRestitution1,
        noteGEAo: depotFP.noteRestitution2,
        noteGEA1: depotFP.noteRDVPedagogique,
        noteGEA2: depotFP.noteAppreciationGlobale,
        noteGEA3: depotFP.noteAcademicEncadrant,
        noteGEA5: depotFP.noteEncadrantEntreprise,
        noteGEA6: depotFP.noteFinaleEncadrement
      })

      // console.log('--------------26.10-----3------> VERIF: ' + this.state.restMarkStatus);
      if(Number(depotFP.noteExpert) === Number(-1))
      {
        // console.log('-------Object--- Status 11 -------');
        this.setState({
          selectedStudentExpertMarkForRest: 0,
          restMarkStatus: "NOTYET",
        });
        // console.log('--------------26.10-----3.1------> VERIF: ' + this.state.restMarkStatus);
      }
      if(Number(depotFP.noteExpert) !== Number(-1))
      {
        // console.log('-----Object----- Status 22 -------');
        this.setState({
          selectedStudentExpertMarkForRest: depotFP.noteExpert,
          restMarkByExpert: 0,
          restMarkStatus: "DONE",
        });
        // console.log('--------------26.10-----3.2------> VERIF: ' + this.state.restMarkStatus);
      }
    }
    // console.log('--------------26.10-----4------> VERIF: ' + this.state.restMarkStatus);
    // console.log('--------Object--------PIKA---------------> SARS 200617 : ' + idEt + " --> " + this.state.selectedStudentExpertMarkForRest + " ** " + depotFP.noteExpert + " ** " + this.state.restMarkStatus)
  }

  handleClosePopupTreatDepotPFE() {
    this.setState({
      openPopupTreatDepotPFE: false,
    });

    // console.log("----------------------------> PIKA - 2006: " + this.state.etatTreat);
  }

  lol()
  {
    // console.log('_____________________> HI 26.10')
  }

  render() {
    const {
      listOfStudentsTrainedByPE,
      listOfAuthorizedStudentsToSTNByFilter,
      openPopupShowStudentDetails,
      openPopupTreatDepotPFE,
      openPopupSaveDepotPFE,
      openPopupValidateDepotPFE,
      selectedStudentFullName,
      selectedStudentMail,
      selectedStudentTel,
      selectedStudentDepartment,
      selectedStudentExpertMarkForRest,
      selectedStudentOption,
      selectedStudentSocieteLibelle,
      selectedStudentProjectTitle,
      selectedStudentClasse,
      showSpinnerSaveDepotPFE,
      showSpinnerValidateDepotPFE,
      etatTreat,
      emptyTreat,
      showButtons,
      loadList,
      restMarkStatus,
      noteGEAa,
      noteGEAz,
      noteGEAe,
      noteGEAr,
      noteGEAt,
      noteGEAy,
      noteGEAu,
      noteGEAi,
      noteGEAo,
      noteGEA1,
      noteGEA2,
      noteGEA3,
      noteGEA5,
      openPopupShowRequestSaveFD,
      openPopupShowRequestConfirmFD,
      selectedYear,
      loadStudentsCJByYear,
      allSessionsLabel
    } = this.state;
    // init6

    return (
      <>
        <CRow>
          <CCol md="4"/>
          <CCol md="4">
            <br/>
            <p className="greyMarkForSelectComp">Merci de choisir une Année pour consulter la résultante</p>
            <Select  placeholder="Please Select an Academic Year"
                     defaultValue={{value: '2022', label: '2022', color: "#00B8D9"}}
                     value={allSessionsLabel.value}
                     components={animatedComponents}
                     options={allSessionsLabel}
                     onChange={this.loadStudentsAffectedToAE}/>
          </CCol>
          <CCol md="4"/>
        </CRow>
        <br/>
        {
          selectedYear === 'EMPTY' &&
          <>
            <br/><br/><br/>
          </>
        }
        {
          selectedYear !== 'EMPTY' &&
          <>
            {
              loadStudentsCJByYear === true ?
                <center>
                  <br/><br/><br/><br/><br/>
                  <span className="waitIcon"/>
                  <br/><br/><br/><br/><br/>
                </center>
                :
                <>
                  <br/>
                  <CRow>
                    <CCol>
                      <CCard>
                        <CCardHeader>
                          <CContainer>
                            <CRow>
                              <CCol md="10" class="bg-success py-2">
                                                                <span style={{color: "#cc0000", fontSize: "14px", fontWeight: "bold"}}>
                                                                    Liste des Étudiants pour l'Année Universitaire &nbsp;&nbsp;<span className="clignoteRed">{selectedYear} - {Number(selectedYear)+1}</span>
                                                                </span>
                              </CCol>
                            </CRow>
                          </CContainer>
                        </CCardHeader>
                        <CCardBody>
                          <CDataTable items={listOfStudentsTrainedByPE}
                                      fields={fields}
                                      tableFilter
                                      columnFilter
                                      itemsPerPageSelect
                                      hover
                                      sorter
                                      striped
                                      bordered
                                      size="sm"
                                      itemsPerPage={10}
                                      pagination
                                      noItemsContent='azerty'
                                      onFilteredItemsChange={(event) => {
                                        this.onChangeAuthorizedStudentsToSTNByFilter(event)
                                      }}
                                      scopedSlots={{
                                        dateDepot: (item)=>(
                                          <td>
                                            {
                                              item.dateDepot?
                                                <>{moment(item.dateDepot).format("DD-MM-YYYY hh:mm")}</>
                                                :
                                                <>--</>
                                            }
                                          </td>
                                        ),
                                        etatFiche: (item) => (
                                          <td>
                                            <CBadge
                                              color={getBadgeEtatFiche(item.etatFiche)}>{item.etatFiche}</CBadge>
                                          </td>
                                        ),
                                        etatTreatDepot: (item) => (
                                          <td>
                                            <CBadge
                                              color={getBadgeEtatTreatDepot(item.etatTreatDepot)}>{item.etatTreatDepot}</CBadge>
                                          </td>
                                        ),
                                        action: (item) => (
                                          <td>
                                            <center>

                                              <CButton shape="pill"
                                                       color="dark"
                                                       size="sm"
                                                       aria-expanded="true"
                                                       onClick={() => this.handleConsultStudentDetails(item.identifiant, item.fullName)}>
                                                <CTooltip
                                                  content="Consulter Coordonnées Étudiant(e)"
                                                  placement="top">
                                                  <CIcon content={freeSet.cilPhone}/>
                                                </CTooltip>
                                              </CButton>
                                              &nbsp;
                                              {
                                                (item.etatFiche === "PLANIFIE" || item.etatFiche === "VALIDE" || item.etatTreatDepot === "TRAITE") &&
                                                <CButton shape="pill"
                                                         color="primary"
                                                         size="sm"
                                                         aria-expanded="true"
                                                         onClick={() => this.handleTreatDepotPFE(item.identifiant, item.fullName, item.classe, item.dateDepot)}>
                                                  <CTooltip
                                                    content="Traiter Dépôt PFE"
                                                    placement="top">
                                                    <CIcon
                                                      content={freeSet.cilClipboard}/>
                                                  </CTooltip>
                                                </CButton>                                                              }
                                            </center>
                                          </td>
                                        ),
                                      }}
                          />
                        </CCardBody>
                      </CCard>
                    </CCol>
                  </CRow>

                  <Dialog fullHight fullWidth
                          maxWidth="sm"
                          keepMounted
                          open={openPopupShowStudentDetails}
                          onClose={this.handleClosePopupShowStudentDetails}
                          aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">

                      <CRow>
                        <CCol md="4">
                                            <span className="myModalTitleMarginTop">
                                                Contact de l'Étudiant(e)
                                            </span>
                        </CCol>
                        <CCol md="8" className="colPadding">
                          <div className="myDivMarginTop">
                                                <span className="myModalSubTitle">
                                                  <ReactTextTransition text={selectedStudentFullName} springConfig={{tension: 10, friction: 10}}/>
                                                </span>
                          </div>
                        </CCol>
                      </CRow>


                      <hr/>
                    </DialogTitle>
                    <DialogContent>
                      <CRow>
                        <CCol xs="3">
                            <span className="myModalField">
                              Num. Téléphone:
                            </span>
                        </CCol>
                        <CCol xs="9">
                          {selectedStudentTel}
                        </CCol>
                      </CRow>

                      <CRow>
                        <CCol xs="3">
                            <span className="myModalField">
                              E-Mail:
                            </span>
                        </CCol>
                        <CCol xs="9">
                          {selectedStudentMail}
                        </CCol>
                      </CRow>

                      <br/>
                    </DialogContent>
                    <DialogActions>
                      <Button onClick={this.handleClosePopupShowStudentDetails} color="primary">
                        Exit
                      </Button>
                    </DialogActions>
                  </Dialog>

                  <Dialog fullHight
                          fullWidth
                          maxWidth="lg"
                          keepMounted
                          open={openPopupTreatDepotPFE}
                          onClose={this.handleClosePopupTreatDepotPFE}
                          aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">

                      <CRow>
                        <CCol md="2">
                                            <span className="myModalTitleMarginTop">
                                                Traiter Dépôt PFE
                                            </span>
                        </CCol>
                        <CCol md="10" className="colPadding">
                          <div className="myDivMarginTop">
                                                <span className="myModalSubTitle">
                                                  <ReactTextTransition text={selectedStudentFullName} springConfig={{tension: 10, friction: 10}}/>
                                                </span>
                          </div>
                        </CCol>
                      </CRow>

                      <hr/>
                    </DialogTitle>
                    <DialogContent>
                      <CRow>
                        <CCol xs="8">
                          <CRow>
                            <CCol xs="3">
                                                    <span className="myModalField">
                                                        Département:
                                                    </span>
                            </CCol>
                            <CCol xs="9">
                              {selectedStudentDepartment}
                            </CCol>
                          </CRow>

                          <CRow>
                            <CCol xs="3">
                                                    <span className="myModalField">
                                                        Option:
                                                    </span>
                            </CCol>
                            <CCol xs="9">
                              {selectedStudentOption}
                            </CCol>
                          </CRow>

                          <CRow>
                            <CCol xs="3">
                                                    <span className="myModalField">
                                                        Classe:
                                                    </span>
                            </CCol>
                            <CCol xs="9">
                              {selectedStudentClasse}
                            </CCol>
                          </CRow>

                          <CRow>
                            <CCol xs="3">
                                                    <span className="myModalField">
                                                        Entreprise d'Accueil:
                                                    </span>
                            </CCol>
                            <CCol xs="9">
                              {selectedStudentSocieteLibelle}
                            </CCol>
                          </CRow>

                          <CRow>
                            <CCol xs="3">
                                                    <span className="myModalField">
                                                        Nom du ou des projets:
                                                    </span>
                            </CCol>
                            <CCol xs="9">
                              {selectedStudentProjectTitle}
                            </CCol>
                          </CRow>
                        </CCol>
                        <CCol xs="3">
                          <center>
                            <TextField id="markNFE"
                                       label="Note Finale Encadrement ( ... / 20 )"
                                       type="number"
                                       fullWidth
                                       margin="normal"
                                       variant="filled"
                                       disabled
                                       value={Number(this.state.noteGEA6)}
                            />
                          </center>
                        </CCol>
                        <CCol xs="1">
                          <br/>
                          <center>

                            {
                              etatTreat === "NO" ?
                                <CTooltip content="Grille Not Yet Filled" placement="top">
                                  <span className="downloadGrayIcon"/>
                                </CTooltip>
                                :
                                <CTooltip content="Download Grille" placement="top">
                                  <span className="downloadRedIcon" onClick={() => {this.downloadGrilleAE()}}/>
                                </CTooltip>
                            }
                          </center>
                        </CCol>
                      </CRow>
                      <br/>
                      <hr/>
                      <br/>
                      <CTabs activeTab="desc">
                        <CNav variant="tabs">
                          <CNavItem>
                            <CNavLink data-tab="desc">
                              <span className="greyMark">Note RDV Pédagogique</span>
                            </CNavLink>
                          </CNavItem>
                          <CNavItem>
                            <CNavLink data-tab="probs">
                              <span className="lightPinkMark">Note Appréciation Globale</span> &nbsp;&nbsp;&nbsp;
                            </CNavLink>
                          </CNavItem>
                          <CNavItem>
                            <CNavLink data-tab="funcs">
                              <span className="purpleMark">Synthèse Note Encadrement</span> &nbsp;&nbsp;&nbsp;
                            </CNavLink>
                          </CNavItem>
                        </CNav>
                        <CTabContent>
                          <CTabPane data-tab="desc">
                            <br/>
                            <CCard>
                              <CCardHeader>
                                                    <span className="miLittleCardTitle">
                                                        * Livrables (7 points)
                                                    </span>
                              </CCardHeader>
                              <CCardBody>
                                <CRow>
                                  <CCol md="4">
                                                            <span className="subTitleBlackField">
                                                                Livrable
                                                            </span>
                                  </CCol>
                                  <CCol md="2">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Dûment rempli et rendu à temps
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="2">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Dûment rempli et rendu en retard
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="2">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Non rendu
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="2">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Note
                                                                </center>
                                                            </span>
                                  </CCol>
                                </CRow>
                                <CListGroup accent>
                                  <CListGroupItem accent="danger" color="danger">
                                    <CRow>
                                      <CCol md="4">
                                                                        <span className="blackField">
                                                                        <br/>
                                                                        Planning de Stage
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            3 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            2 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="marka"
                                                     label="( ... / 3 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "3",
                                                       step: "0.25"
                                                     }}
                                                     value={Number(this.state.noteGEAa)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 3) this.setState({noteGEAa: e.target.value});
                                                       if (e.target.value > 3) this.setState({noteGEAa: 3});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // // console.log('2907 -----------> a:' + e.target.value + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 a --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));
                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                  <CListGroupItem accent="dark" color="dark">
                                    <CRow>
                                      <CCol md="4">
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        Bilan Périodique Début de Stage
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            1 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0.5 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="markz"
                                                     label="( ... / 1 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "1",
                                                       step: "0.5"
                                                     }}
                                                     value={Number(this.state.noteGEAz)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 1) this.setState({noteGEAz: e.target.value});
                                                       if (e.target.value > 1) this.setState({noteGEAz: 1});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(e.target.value) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + e.target.value + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 z --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                  <CListGroupItem accent="secondary" color="secondary">
                                    <CRow>
                                      <CCol md="4">
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        Bilan Périodique Milieu de Stage
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            1 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0.5 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="marke"
                                                     label="( ... / 1 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "1",
                                                       step: "0.5"
                                                     }}
                                                     value={Number(this.state.noteGEAe)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 1) this.setState({noteGEAe: e.target.value});
                                                       if (e.target.value > 1) this.setState({noteGEAe: 1});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(e.target.value) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + e.target.value + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 e --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                  <CListGroupItem accent="light" color="light">
                                    <CRow>
                                      <CCol md="4">
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        Bilan Périodique Fin de Stage
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            1 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0.5 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="markr"
                                                     label="( ... / 1 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "1",
                                                       step: "0.5"
                                                     }}
                                                     value={Number(this.state.noteGEAr)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 1) this.setState({noteGEAr: e.target.value});
                                                       if (e.target.value > 1) this.setState({noteGEAr: 1});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(e.target.value) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + e.target.value + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 r --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                  <CListGroupItem accent="warning" color="warning">
                                    <CRow>
                                      <CCol md="4">
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        Journal de Bord
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            1 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0.5 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pt
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="markt"
                                                     label="( ... / 1 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "1",
                                                       step: "0.5"
                                                     }}
                                                     value={Number(this.state.noteGEAt)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 1) this.setState({noteGEAt: e.target.value});
                                                       if (e.target.value > 1) this.setState({noteGEAt: 1});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(e.target.value) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + e.target.value + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 t --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                </CListGroup>
                              </CCardBody>
                            </CCard>

                            <CCard>
                              <CCardHeader>
                                                    <span className="miLittleCardTitle">
                                                        * Fiches Évaluation (4 points)
                                                    </span>
                              </CCardHeader>
                              <CCardBody>
                                <CRow>
                                  <CCol md="4">
                                                            <span className="subTitleBlackField">
                                                                Fiche Évaluation
                                                            </span>
                                  </CCol>
                                  <CCol md="3">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Encadrant Satisfait
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="3">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Encadrant Moyennement Satisfait
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="2">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Note
                                                                </center>
                                                            </span>
                                  </CCol>
                                </CRow>
                                <CListGroup accent>
                                  <CListGroupItem accent="primary" color="primary">
                                    <CRow>
                                      <CCol md="4">
                                        <br/>
                                        <span className="blackField">
                                                                        Fiche Évaluation Mi-parcours
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            2 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            1 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="marky"
                                                     label="( ... / 2 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "1",
                                                       max: "2",
                                                       step: "1"
                                                     }}
                                                     value={Number(this.state.noteGEAy)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 2) this.setState({noteGEAy: e.target.value});
                                                       if (e.target.value > 2) this.setState({noteGEAy: 2});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(e.target.value) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + e.target.value + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 y --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                  <CListGroupItem accent="info" color="info">
                                    <CRow>
                                      <CCol md="4">
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        Fiche Évaluation Finale
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            2 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            1 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="marku"
                                                     label="( ... / 2 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "1",
                                                       max: "2",
                                                       step: "1"
                                                     }}
                                                     value={Number(this.state.noteGEAu)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 2) this.setState({noteGEAu: e.target.value});
                                                       if (e.target.value > 2) this.setState({noteGEAu: 2});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(e.target.value) + Number(this.state.noteGEAi) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + e.target.value + " + i:" + this.state.noteGEAi + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 u --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>
                                </CListGroup>
                              </CCardBody>
                            </CCard>

                            <CCard>
                              <CCardHeader>
                                                    <span className="miLittleCardTitle">
                                                        * RDV Pédagogique (9 points)
                                                    </span>
                              </CCardHeader>
                              <CCardBody>
                                <CRow>
                                  <CCol md="4">
                                                            <span className="subTitleBlackField">
                                                                RDV
                                                            </span>
                                  </CCol>
                                  <CCol md="3">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Assurée
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="3">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Non assurée
                                                                </center>
                                                            </span>
                                  </CCol>
                                  <CCol md="2">
                                                            <span className="subTitleBlackField">
                                                                <center>
                                                                    Note
                                                                </center>
                                                            </span>
                                  </CCol>
                                </CRow>
                                <CListGroup accent>
                                  <CListGroupItem accent="success" color="success">
                                    <CRow>
                                      <CCol md="4">
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        1ère Restitution
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            4.5 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="marki"
                                                     label="( ... / 4.5 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "4.50",
                                                       step: "4.5"
                                                     }}
                                                     value={Number(this.state.noteGEAi)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 4.5) this.setState({noteGEAi: e.target.value});
                                                       if (e.target.value > 4.5) this.setState({noteGEAi: 4.5});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(e.target.value) + Number(this.state.noteGEAo);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + e.target.value + " + o:" + this.state.noteGEAo);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 i --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>

                                  <CListGroupItem accent="secondary" color="secondary">
                                    <CRow>
                                      <CCol md="4" >
                                                                    <span className="blackField">
                                                                        <br/>
                                                                        2ème Restitution
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            4.5 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="3">
                                                                    <span className="blackField">
                                                                        <center>
                                                                            <br/>
                                                                            0 pts
                                                                        </center>
                                                                    </span>
                                      </CCol>
                                      <CCol md="2">
                                        <center>
                                          <TextField id="marko"
                                                     label="( ... / 4.5 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "4.50",
                                                       step: "4.5"
                                                     }}
                                                     value={Number(this.state.noteGEAo)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 4.5) this.setState({noteGEAo: e.target.value});
                                                       if (e.target.value > 4.5) this.setState({noteGEAo: 4.5});

                                                       this.state.noteGEA1 = Number(this.state.noteGEAa) + Number(this.state.noteGEAz) + Number(this.state.noteGEAe) + Number(this.state.noteGEAr) + Number(this.state.noteGEAt) + Number(this.state.noteGEAy) + Number(this.state.noteGEAu) + Number(this.state.noteGEAi) + Number(e.target.value);
                                                       // console.log('2907 -----------> a:' + this.state.noteGEAa  + " + z:" + this.state.noteGEAz + " + e:" + this.state.noteGEAe + " + r:" + this.state.noteGEAr + " + t:" + this.state.noteGEAt + " + y:" + this.state.noteGEAy + " + u:" + this.state.noteGEAu + " + i:" + this.state.noteGEAi + " + o:" + e.target.value);

                                                       this.setState({noteGEA1: this.state.noteGEA1});
                                                       // console.log('----------------------------------- Result - 2907 o --> ' + this.state.noteGEA1);

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(this.state.noteGEA1)*80/100 + Number(this.state.noteGEA2)*20/100;
                                                       //console.log('----------------------------------- 1 - 0108a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 0108a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       //console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 0108a --> ' + Number(this.state.noteGEA6));

                                                     }}/>
                                        </center>
                                      </CCol>
                                    </CRow>
                                  </CListGroupItem>
                                </CListGroup>
                              </CCardBody>
                            </CCard>
                          </CTabPane>
                          <CTabPane data-tab="probs" onActivetabchange={this.lol}>
                            <br/>
                            <CRow>
                              <CCol md="6">
                                <CCard >
                                  <CCardHeader className="pinkBackDeg2">
                                    <center>
                                      Note Encadrant Académique = ( Note RDV Pédagogiques * 80% + Note Appréciation Globale * 20% )
                                    </center>
                                  </CCardHeader>
                                  <CCardBody>
                                    <CAlert className="purpleBackDeg1">
                                      <CRow>
                                        <CCol md="9" className="colPaddingRight">
                                          <br/>
                                          Note RDV Pédagogiques
                                        </CCol>
                                        <CCol md="3" className="colPaddingLeft">
                                          <TextField id="mark1"
                                                     label="( ... / 20 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     value={Number(this.state.noteGEA1)}
                                                     disabled/>
                                        </CCol>
                                      </CRow>
                                    </CAlert>
                                    <CAlert className="purpleBackDeg1">
                                      <CRow>
                                        <CCol md="9" className="colPaddingRight">
                                          <br/>
                                          Note Appréciation Globale Encadrant Académique
                                        </CCol>
                                        <CCol md="3" className="colPaddingLeft">
                                          <TextField id="mark2"
                                                     label="( ... / 20 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "20",
                                                       step: "0.25"
                                                     }}
                                                     value={Number(this.state.noteGEA2)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 20) this.setState({noteGEA2: e.target.value});
                                                       if (e.target.value > 20) this.setState({noteGEA2: 20});

                                                       // Note Encadrant Académique = ( Note RDV Pédagogique * 80% + Note Appréciation Globale * 20% )
                                                       this.state.noteGEA3 = Number(e.target.value)*20/100 + Number(this.state.noteGEA1)*80/100;
                                                       // console.log('----------------------------------- 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*20/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA3: this.state.noteGEA3});
                                                       // console.log('----------------------------------- 2 - 1406a --> ' + Number(this.state.noteGEA3));

                                                       // Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(this.state.noteGEA5)*20/100;
                                                       // console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA3);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 1406a --> ' + Number(this.state.noteGEA6));
                                                     }}/>
                                        </CCol>
                                      </CRow>
                                    </CAlert>
                                    <CAlert className="pinkBackDeg2">
                                      <center>
                                        Note Encadrant Académique
                                      </center>
                                      <TextField id="mark3"
                                                 label="( ... / 20 )"
                                                 type="number"
                                                 fullWidth
                                                 margin="normal"
                                                 variant="filled"
                                                 disabled
                                                 value={Number(this.state.noteGEA3)}
                                      />
                                    </CAlert>
                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol md="6"/>
                            </CRow>
                          </CTabPane>
                          <CTabPane data-tab="funcs">
                            <br/>
                            <CRow>
                              <CCol md="6">
                                <CCard>
                                  <CCardHeader className="purpleBackDeg2">
                                    <center>
                                      Note Finale Encadrement =
                                      <br/>
                                      ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                    </center>
                                  </CCardHeader>
                                  <CCardBody>
                                    <CAlert className="pinkBackDeg1">
                                      <CRow>
                                        <CCol md="9">
                                          <br/>
                                          Note Encadrant Académique
                                        </CCol>
                                        <CCol md="3">
                                          <TextField id="mark4"
                                                     label="( ... / 20 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     value={Number(this.state.noteGEA3)}
                                                     disabled
                                          />
                                        </CCol>
                                      </CRow>
                                    </CAlert>
                                    <CAlert className="pinkBackDeg1">
                                      <CRow>
                                        <CCol md="9">
                                          <br/>
                                          Note Expert
                                          <br/>
                                          {restMarkStatus}--{selectedStudentExpertMarkForRest}
                                          <br/>
                                          {
                                            restMarkStatus === "DONE" && Number(selectedStudentExpertMarkForRest) === 0 &&
                                            <span className="warningBlueIcon">&nbsp;&nbsp;&nbsp;&nbsp;La&nbsp;Note&nbsp;Expert&nbsp;de&nbsp;cet&nbsp;Étudiant&nbsp;est&nbsp;0&nbsp;.</span>
                                          }
                                          {
                                            restMarkStatus === "NOTYET" &&
                                            <span className="warningRedIcon">&nbsp;&nbsp;&nbsp;&nbsp;Note&nbsp;Expert&nbsp;doesn't&nbsp;yet&nbsp;affected&nbsp;!.</span>
                                          }
                                        </CCol>
                                        <CCol md="3">
                                          <TextField id="mark5"
                                                     label="( ... / 20 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     value={Number(selectedStudentExpertMarkForRest)}
                                                     disabled/>
                                        </CCol>
                                      </CRow>
                                    </CAlert>
                                    <CAlert className="pinkBackDeg1">
                                      <CRow>
                                        <CCol md="9">
                                          <br/>
                                          Note Encadrant Professionnel
                                        </CCol>
                                        <CCol md="3">
                                          <TextField id="mark6"
                                                     label="( ... / 20 )"
                                                     type="number"
                                                     fullWidth
                                                     margin="normal"
                                                     variant="filled"
                                                     inputProps={{
                                                       shrink: true,
                                                       min: "0",
                                                       max: "20",
                                                       step: "0.25"
                                                     }}
                                                     value={Number(this.state.noteGEA5)}
                                                     disabled={etatTreat === "02"}
                                                     onChange={(e) => {
                                                       if (e.target.value <= 20) this.setState({noteGEA5: e.target.value});
                                                       if (e.target.value > 20) this.setState({noteGEA5: 20});

                                                       // Note Encadrant Académique = Note Finale Encadrement = ( Note Encadrant Académique * 40% + Note Expert * 40% + Note Encadrant Professionnel * 20% )
                                                       this.state.noteGEA6 = Number(this.state.noteGEA3)*40/100 + Number(this.state.selectedStudentExpertMarkForRest)*40/100 + Number(e.target.value)*20/100;
                                                       // console.log('---------------------------------- ** - 1 - 1406a --> ' + Number(e.target.value) + " - " + Number(e.target.value)*40/100 + " = " + this.state.noteGEA5);
                                                       this.setState({noteGEA6: this.state.noteGEA6});
                                                       // console.log('---------------------------------- ** - 2 - 1406a --> ' + Number(this.state.noteGEA6));
                                                     }}/>
                                        </CCol>
                                      </CRow>
                                    </CAlert>
                                    <CAlert className="purpleBackDeg2">
                                      Note Finale Encadrement
                                      <TextField id="mark7"
                                                 label="( ... / 20 )"
                                                 type="number"
                                                 fullWidth
                                                 margin="normal"
                                                 variant="filled"
                                                 value={Number(this.state.noteGEA6)}
                                                 disabled/>
                                    </CAlert>

                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol md="6"/>
                            </CRow>
                            <br/><br/>
                            <CRow>
                              <CCol>
                                {
                                  (
                                    (showButtons === "INIT" && !showSpinnerSaveDepotPFE && !showSpinnerValidateDepotPFE && etatTreat === "01")
                                    ||
                                    (showButtons === "INIT" && !showSpinnerSaveDepotPFE && !showSpinnerValidateDepotPFE && emptyTreat === "YES")
                                  )
                                  &&
                                  <CButton color="primary" className="float-right"
                                           onClick={() => this.handleOpenPopupRequestSaveFichePFE()}>
                                    Sauvegarder
                                  </CButton>
                                }
                                <p className="float-right">&nbsp;&nbsp;</p>

                                {
                                  (
                                    (showButtons === "INIT" && !showSpinnerSaveDepotPFE && !showSpinnerValidateDepotPFE && etatTreat === "01" && restMarkStatus === "DONE")
                                    ||
                                    (showButtons === "INIT" && !showSpinnerSaveDepotPFE && !showSpinnerValidateDepotPFE && emptyTreat === "YES" && restMarkStatus === "DONE")
                                  )
                                  &&
                                  <CButton color="success" className="float-right"
                                           disabled={showSpinnerSaveDepotPFE}
                                           onClick={() => this.handleOpenPopupRequestConfirmFichePFE()}>
                                    Valider
                                  </CButton>
                                }
                              </CCol>
                            </CRow>
                          </CTabPane>
                        </CTabContent>
                      </CTabs>
                    </DialogContent>
                    <DialogActions>
                      <div>
                        <CRow>
                          <br/><br/>
                        </CRow>
                        <CRow>
                          <CCol md="12">
                            <span className="noteGrilleEval">En cliquant sur le bouton </span> &nbsp;&nbsp;
                            <span class="text-primary" style={{fontWeight: "bold"}}>Sauvegarder</span>
                            &nbsp;&nbsp;
                            <span className="noteGrilleEval">
                                                    , vous allez <ins>uniquement</ins> sauvegarder une première version de la Grille <ins>que vous pourrez la changer à tout moment</ins> .
                                                </span>
                            <br/>
                            <span className="noteGrilleEval">En cliquant sur le bouton </span> &nbsp;&nbsp;
                            <span class="text-success" style={{fontWeight: "bold"}}>Valider</span>
                            &nbsp;&nbsp;
                            <span className="noteGrilleEval">
                                                    , vous allez valider la Grille pour la dernière fois. Ainsi, <ins>vous ne pourrez plus la changer</ins> .
                                                </span>
                            <br/><br/>
                            {
                              restMarkStatus === "NOTYET" &&
                              <>
                                <span className="warningRedIconOnly"/>
                                &nbsp;&nbsp;
                                <span className="alertRedText">Le Bouton </span>
                                <CButton color="success" size="sm" disabled>
                                  Valider
                                </CButton>
                                <span className="alertRedText"> n'est pas visible car la <ins>Note Expert</ins> n'est pas encore attribuée .</span>
                              </>
                            }
                          </CCol>
                        </CRow>
                        <CRow>
                          <br/><br/>
                        </CRow>
                      </div>
                      <div style={{flex: '1 0 0'}} />

                      <CButton variant="outline"
                               color="dark"
                               onClick={this.handleClosePopupTreatDepotPFE}>
                        Exit
                      </CButton>
                    </DialogActions>
                  </Dialog>

                  <Dialog fullHight fullWidth
                          maxWidth="sm"
                          keepMounted
                          open={openPopupShowRequestSaveFD}
                          onClose={this.handleClosePopupRequestSaveFichePFE}
                          aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">
                      <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                      <hr/>
                    </DialogTitle>
                    <DialogContent>
                      <center>
                        Êtes-vous sûr(es) de vouloir sauvegarder cette version de Grille <br/>
                        pour votre Étudiant(e) &nbsp;
                        <span className="text-primary" style={{fontSize: "12px"}}>{selectedStudentFullName}</span>
                        &nbsp;?.
                        <br/><br/>
                        <span className="redMarkCourrierSmalLabel">Remarque : Vous pouvez modifier cette version à tout moment.</span>
                      </center>
                      <br/><br/>
                    </DialogContent>
                    <DialogActions>
                      {
                        showSpinnerSaveDepotPFE ?
                          <Spinner animation="grow" variant="primary"/>
                          :
                          <CButton variant="outline" color="primary" onClick={() => this.handleFillFicheDepotPFE()}>
                            Oui
                          </CButton>
                      }
                      &nbsp;&nbsp;
                      <Button onClick={this.handleClosePopupRequestSaveFichePFE} color="primary">
                        No
                      </Button>
                    </DialogActions>
                  </Dialog>

                  <Dialog fullHight fullWidth
                          maxWidth="sm"
                          keepMounted
                          open={openPopupShowRequestConfirmFD}
                          onClose={this.handleClosePopupRequestConfirmFichePFE}
                          aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">
                      <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                      <hr/>
                    </DialogTitle>
                    <DialogContent>
                      <center>
                        Êtes-vous sûr(es) de vouloir confirmer cette version de Grille <br/>
                        pour votre Étudiant(e) &nbsp;
                        <span className="text-primary" style={{fontSize: "12px"}}>{selectedStudentFullName}</span>
                        &nbsp;?.
                        <br/><br/>
                        <span className="redMarkCourrierSmalLabel">Remarque : Vous ne pourrez plus modifier cette version.</span>
                      </center>
                      <br/>
                    </DialogContent>
                    <DialogActions>
                      {
                        showSpinnerValidateDepotPFE  ?
                          <Spinner animation="grow" variant="success"/>
                          :
                          <CButton variant="outline" color="success" onClick={() => this.handleValidateFicheDepotPFE()}>
                            Oui
                          </CButton>
                      }
                      &nbsp;&nbsp;
                      <Button onClick={this.handleClosePopupRequestConfirmFichePFE} color="primary">
                        No
                      </Button>
                    </DialogActions>
                  </Dialog>

                  <Dialog fullHight fullWidth
                          maxWidth="sm"
                          keepMounted
                          open={openPopupSaveDepotPFE}
                          onClose={this.handleClosePopupSaveDepotPFE}
                          aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">
                      <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                      <hr/>
                    </DialogTitle>
                    <DialogContent>
                      <center>
                        Vous avez sauvegardé la Grille Encadrement de l'Étudiant(e) &nbsp;
                        <span className="text-info" style={{fontSize: "12px"}}>
                                          {selectedStudentFullName}
                                        </span>
                      </center>
                      <br/>
                    </DialogContent>
                    <DialogActions>
                      <Button onClick={this.handleClosePopupSaveDepotPFE} color="primary">
                        Exit
                      </Button>
                    </DialogActions>
                  </Dialog>

                  <Dialog fullHight fullWidth
                          maxWidth="sm"
                          keepMounted
                          open={openPopupValidateDepotPFE}
                          onClose={this.handleClosePopupValidateDepotPFE}
                          aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">
                      <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                      <hr/>
                    </DialogTitle>
                    <DialogContent>
                      <center>
                        Vous avez validé le Dépôt Fiche PFE de l'Étudiant(e) &nbsp;
                        <span className="text-primary" style={{fontSize: "12px"}}>
                                          {selectedStudentFullName}
                                        </span>
                      </center>
                      <br/>
                    </DialogContent>
                    <DialogActions>
                      <Button onClick={this.handleClosePopupValidateDepotPFE} color="primary">
                        Exit
                      </Button>
                    </DialogActions>
                  </Dialog>
                </>
            }
          </>
        }
      </>
    );
  }
}
