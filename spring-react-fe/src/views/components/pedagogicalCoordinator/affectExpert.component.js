import React, {Component} from "react";
import PedagogicalCoordinatorService from "../../services/pedagogicalCoordinator.service";
import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CDataTable,
  CFormGroup,
  CInputRadio,
  CLabel,
  CRow,
  CTooltip,
  CAlert
} from "@coreui/react";

import excelGreenIcon from "../../images/excelGreenIcon.png";
import CIcon from "@coreui/icons-react";

import Badge from '@material-ui/core/Badge';
import PersonIcon from '@material-ui/icons/Person';

import {freeSet} from '@coreui/icons';
import "../../css/style.css";
//import { Wave1, Wave2, Random1, Random2 } from './example.js';

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import ReactTextTransition from "react-text-transition";

import Spinner from "react-bootstrap/Spinner";
import excelBlueIcon from "../../images/excelBlueIcon.jpg";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import PCService from "../../services/pedagogicalCoordinator.service";
import AuthService from "../../services/auth.service";

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_PC = process.env.REACT_APP_API_URL_PC;

const currentPedagogicalCoordinator =
  AuthService.getCurrentPedagogicalCoordinator();

const animatedComponents = makeAnimated();

const studentsByClassFields = [
  {
    key: 'id',
    label: 'Identifiant',
    _style: {width: '6%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'fullName',
    label: 'Nom & Prénom',
    _style: {width: '28%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'mail',
    label: 'E-Mail',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'tel',
    label: 'Numéro Téléphone',
    _style: {width: '14%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'option',
    label: 'Option',
    _style: {width: '14%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'noteRestitution',
    label: 'Note Restitution',
    _style: {width: '14%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'affected',
    label: 'Affecté',
    _style: {width: '14%'},
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

const expertsFields = [
  {
    key: 'identifiant',
    label: 'Identifiant',
    _style: {width: '6%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'nom',
    label: 'Nom & Prénom',
    _style: {width: '39%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'nbrExpertises',
    label: 'Nbr Expertises',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'cep',
    label: 'CEP',
    _style: {width: '30%'},
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

const getBadgeAffectAE = (affected) => {
  switch (affected) {
    case "PAS ENCORE":
      return "dark";
    case "AFFECTEE":
      return "success";
    default:
      return "secondary";
  }
};

export default class AffectExpertComponent extends Component {
  constructor(props) {
    super(props);

    this.onChangeClass = this.onChangeClass.bind(this);
    this.handleConsultDetailsExpert = this.handleConsultDetailsExpert.bind(this);
    this.handleAffectExpert = this.handleAffectExpert.bind(this);
    this.handleClosePopupShowAEDetails = this.handleClosePopupShowAEDetails.bind(this);
    this.handleClosePopupAffectAE = this.handleClosePopupAffectAE.bind(this);
    this.handleClosePopupCancelAffectAE = this.handleClosePopupCancelAffectAE.bind(this);
    this.handleGotListCEPs = this.handleGotListCEPs.bind(this);
    this.handleGotListExpertsForModif = this.handleGotListExpertsForModif.bind(this);
    this.handleOpenPopupJustifyDeleteAffectation = this.handleOpenPopupJustifyDeleteAffectation.bind(this);
    this.handleClosePopupJustifyDeleteAffectation = this.handleClosePopupJustifyDeleteAffectation.bind(this);
    this.handleDeleteAffectation = this.handleDeleteAffectation.bind(this);
    this.handleClosePopupSuccessAffectExpert = this.handleClosePopupSuccessAffectExpert.bind(this);
    this.handleDownloadExpertiseStatus = this.handleDownloadExpertiseStatus.bind(this);
    this.onChangeJustifCancelAffectation = this.onChangeJustifCancelAffectation.bind(this);
    this.onChangeSelectCEP = this.onChangeSelectCEP.bind(this);
    this.handleClosePopupOKAffectExpert = this.handleClosePopupOKAffectExpert.bind(this);
    this.handleConfirmAffectEXPtoET = this.handleConfirmAffectEXPtoET.bind(this);
    this.handleGotListCEPsForModif = this.handleGotListCEPsForModif.bind(this);


    this.state = {
      myClasses: [],
      checked: null,
      studentsByClass: [],
      experts: [],
      loadStudentsByClass: false,
      loadExperts: false,
      loadExpertsForModif: false,
      loadAffectation: false,
      loadVerifAffectation: false,
      expert: {},
      academicEncadrant: {},
      tooltipNbrEncadrements: "",
      openPopupShowAEDetails: false,
      openPopupAffectAE: false,
      openPopupSuccessAffectAE: false,
      openPopupSuccessCancelAffectAE: false,
      selectedStudentFullName: "",
      selectedStudentId: "",
      selectedExpFullName: "",
      selectedExpertId: "",
      selectedClass: "",
      tooltipnbrExpertises: "",
      noteNotCompleteSend: "",
      showLoadingExcelIcon: false,
      privilegeKind: "",
      justifCancelAffectation: "",
      openPopupJustifyCancelAffectation: false,
      loadCancelAffectation: false,
      allLabelCEP: [],
      openPopupVerifAffectEXP: false,
      allSessionsLabel: [],
      selectedYear: "2021"
    }

    let pcMail = currentPedagogicalCoordinator.id;
    if(pcMail.includes("CPS_") || pcMail.includes("CPS-"))
    {
      this.state.privilegeKind = "Option";
    }

    if(pcMail.includes("CD_") || pcMail.includes("CD-"))
    {
      this.state.privilegeKind = "Département";
    }

    // Load Classes of 2021
    let requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "listClassesByOptionAndByYear/" + currentPedagogicalCoordinator.id + "/" + this.state.selectedYear,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);
    let result = JSON.parse(requestb.responseText);
    // console.log('-------------------> >sars 1110: ' + result);

    for (let cls of result) {
      // console.log('----sars 1110---------- unit cls: ' + cls);
      this.state.myClasses.push({
        value: cls,
        label: cls,
        color: "#00B8D9",
      });
    }

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

  }

  onChangeClass(iCLS, optionCLS) {
    // console.log('-lol123-------------->', optionCLS); // Object { value: "Non", label: "Non"}
    // console.log('-----lol123-----------> 1.1: ' + this.state.checked + "_" + iCLS);
    this.setState({
      checked: iCLS,
      selectedClass: optionCLS,
      loadStudentsByClass: true
    });

    // console.log('---------lol123-------> 1.2: ' + this.state.checked + "_" + iCLS + " - " + optionCLS);

    AuthService.gotListStudentsByClassAndYearForExpPARAM(this.state.selectedYear, optionCLS, currentPedagogicalCoordinator.id).then(
      (response) => {
        this.setState({
          studentsByClass: []
        });

        let stuList = this.state.studentsByClass.slice();
        for (let stu of response.data) {
          // console.log('----------------lol123--------> Students : ', stu);
          const studentObj = stu;
          stuList.push(stu);
        }

        this.setState({
          loadStudentsByClass: false,
          studentsByClass: stuList
        });

      },
      (error) => {
      }
    );

    // console.log(' 1 ---**************** Clear: ' + this.state.checked + ' - ' + val + ' - ' + this.state.trtType);
  }

  onChangeJustifCancelAffectation(e) {
    this.setState({ justifCancelAffectation: e.target.value });
  }

  onChangeSelectCEP = (selectedCompOption) => {

    let cepLabel = selectedCompOption.label;

    this.setState({
      loadExperts: true,
      experts: []
    });

    PedagogicalCoordinatorService.gotListExpertsByCEPAndYear(encodeURIComponent(cepLabel), this.state.selectedYear).then(
      (response) => {

        let aeList = this.state.experts.slice();
        for (let ae of response.data)
        {
          aeList.push(ae);
        }

        this.setState({
          loadExperts: false,
          experts: aeList,
          openPopupAffectAE: true
        });

      },
      (error) => {
      }
    );

  };

  handleDownloadExpertiseStatus()
  {
    this.setState({showLoadingExcelIcon: true})

    PedagogicalCoordinatorService.downloadExpertiseStatusByOptionAndYear(currentPedagogicalCoordinator.id, this.state.selectedYear).then(
      (response) => {
        this.setState({showLoadingExcelIcon: false})

        const contentDispo = response.headers['content-disposition'];
        const fileName = contentDispo.substring(21);

        const file = new File([response.data], fileName);
        const fileURL = URL.createObjectURL(file);
        // console.log("Check Disponibility ------------- DONE EX ", response.data);
        let a = document.createElement('a');
        a.href = fileURL;
        a.download = fileName;
        a.click();
        // window.open(fileURL);
      },
      (error) => {
        // console.log("Check Disponibility --------cr----- FAIL");
      }
    );
  }

  handleConsultDetailsExpert(idEt, fullName)
  {
    let requestAE = new XMLHttpRequest();
    requestAE.open(
      "GET",
      API_URL_PC + "findPedagogicalEncadrantByStudentAndYearOptimized/" + idEt + "/" + this.state.selectedYear,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestAE.send(null);
    let resultAE = JSON.parse(requestAE.responseText);

    let noteAE = resultAE.nom + " posséde " + resultAE.nbrEncadrements + " Encadrements." ;

    this.setState({
      expert: {},
      selectedStudentFullName: fullName,
      selectedStudentId: idEt
    });

    let requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_PC + "findExpertByStudentAndyear/" + idEt + "/" + this.state.selectedYear,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);
    let result = JSON.parse(requestb.responseText);
    console.log('-------------------> >sars 1110: ', resultAE);

    let note = result.nom + " posséde " + result.nbrExpertises + " Expertises." ;
    this.setState({
      expert: result,
      openPopupShowAEDetails: true,
      tooltipnbrExpertises: note,
      academicEncadrant: resultAE,
      tooltipNbrEncadrements: noteAE
    });
  }

  handleClosePopupShowAEDetails()
  {
    this.setState({openPopupShowAEDetails: false});
  }

  handleClosePopupAffectAE()
  {
    this.setState({openPopupAffectAE: false});
  }

  handleClosePopupCancelAffectAE()
  {
    this.setState({openPopupSuccessCancelAffectAE: false});
  }

  handleClosePopupOKAffectExpert() {
    this.setState({
      openPopupVerifAffectEXP: false,
      openPopupAffectAE: true
    });
  }

  handleClosePopupSuccessAffectExpert()
  {
    this.setState({openPopupSuccessAffectAE: false});
  }

  handleGotListCEPs(idEt, fullName)
  {
    console.log('-------------- selectedYear 1702: ' + this.state.selectedYear);
    this.setState({
      allLabelCEP: [],
    });

    PedagogicalCoordinatorService.gotListLibCEPByYearPARAM(this.state.selectedYear).then(
      (response) => {

        let aeList = this.state.experts.slice();
        for (let comp of response.data)
        {
          console.log('-------------- SARS1702: ' , comp);
          aeList.push({value: comp, label: comp, color: "#00B8D9"});
        }

        this.setState({
          allLabelCEP: aeList
        });

      },
      (error) => {
      }
    );

    // Load CEP
    /*let strIntoObjComp = [];
    var requestComp = new XMLHttpRequest();
    requestComp.open(
        "GET",
        API_URL_PC + "listLibCEPByYearPARAM/" + this.state.selectedYear,
        false
    ); // `false` makes the request synchronous
    requestComp.send(null);
    strIntoObjComp = JSON.parse(requestComp.responseText);
    strIntoObjComp.forEach((comp) => {
      // console.log('-------------- Verify companies: ' + comp);
      this.state.allLabelCEP.push({
        value: comp,
        label: comp,
        color: "#00B8D9",
      });
    });*/


    let requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_PC + "findPedagogicalEncadrantByStudentOptimized/" + idEt,
      false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);
    let result = JSON.parse(requestb.responseText);

    let note = result.nom + " posséde " + result.nbrEncadrements + " Encadrements." ;

    console.log('-----0102-------ET-> id: ' + idEt);
    console.log('-----0102-------ET-> fn: ' + fullName);

    this.setState({
      selectedStudentFullName: fullName,
      selectedStudentId: idEt,
      experts: [],
      noteNotCompleteSend: "",
      openPopupAffectAE: true,
      academicEncadrant: result,
      tooltipNbrEncadrements: note
    });

  }

  handleGotListCEPsForModif()
  {

    this.setState({
      experts: [],
      allLabelCEP: [],
      noteNotCompleteSend: "",
      openPopupAffectAE: true,
      openPopupShowAEDetails: false
    });

    PedagogicalCoordinatorService.gotListLibCEPByYearPARAM(this.state.selectedYear).then(
      (response) => {

        let aeList = this.state.experts.slice();
        for (let comp of response.data)
        {
          console.log('-------------- SARS1702: ' , comp);
          aeList.push({value: comp, label: comp, color: "#00B8D9"});
        }

        this.setState({
          allLabelCEP: aeList
        });

      },
      (error) => {
      }
    );

  }

  handleConfirmAffectEXPtoET() {
    this.setState({
      loadAffectation: true
    });

    PedagogicalCoordinatorService.affectExpertToStudent(this.state.selectedExpertId, this.state.selectedStudentId, currentPedagogicalCoordinator.id).then(
      (response) => {

        let result = response.data;
        // console.log('---------------- RES: ', response.data)
        let affected = "AFFECTEE";
        let id = this.state.selectedStudentId;
        //let nbrExpertises = Number(nbrEncs+1);

        if(result === "NO-AEEXP")
        {
          this.setState({noteNotCompleteSend: "les E-Mails Expert et Encadrant Académique sont inexistant(s) ou avec format incorrecte."});
        }
        if(result === "NO-AE")
        {
          this.setState({noteNotCompleteSend: "E-Mail Encadrant Académique est inexistant ou avec format incorrecte."});
        }
        if(result === "NO-EXP")
        {
          this.setState({noteNotCompleteSend: "E-Mail Expert est inexistant ou avec format incorrecte."});
        }

        this.setState({
          openPopupShowAEDetails: false,
          openPopupAffectAE: false,
          openPopupSuccessAffectAE: true,
          loadAffectation: false,
          openPopupVerifAffectEXP: false,
          studentsByClass: this.state.studentsByClass.map(el => (el.id === id ? {...el, affected } : el))
          //experts: this.state.experts.map(el => (el.id === id ? {...el, nbrExpertises } : el))
        });
      },
      (error) => {
      }
    );
  }

  handleGotListExpertsForModif()
  {
    this.setState({
      experts: [],
      loadExpertsForModif: true,
      noteNotCompleteSend: ""
    });

    PedagogicalCoordinatorService.gotListExperts().then(
      (response) => {

        let aeList = this.state.experts.slice();
        for (let ae of response.data)
        {
          aeList.push(ae);
        }

        this.setState({
          loadExpertsForModif: false,
          experts: aeList
        });

      },
      (error) => {
      }
    );
  }


  handleOpenPopupJustifyDeleteAffectation()
  {
    this.setState({
      justifCancelAffectation: "",
      openPopupShowAEDetails: false,
      openPopupJustifyCancelAffectation: true
    });
  }

  handleClosePopupJustifyDeleteAffectation()
  {
    this.setState({ openPopupJustifyCancelAffectation: false});
  }

  handleDeleteAffectation()
  {
    // console.log('-----------------------> Affect 1: ' + this.state.selectedStudentId);
    // console.log('-----------------------> Affect 2: ' + this.state.selectedStudentFullName);
    // console.log('-----------------------> Affect 1: ' + this.state.selectedExpertId);

    this.setState({
      loadCancelAffectation: true
    });

    PedagogicalCoordinatorService.cancelAffectExpertToStudent(this.state.expert.identifiant, this.state.selectedStudentId, currentPedagogicalCoordinator.id, this.state.justifCancelAffectation).then(
      (response) => {
        let result = response.data;
        // console.log('---------------- RES: ', response.data)
        let affected = "PAS ENCORE";
        let id = this.state.selectedStudentId;
        //let nbrExpertises = Number(nbrEncs+1);

        if(result === "NO-AEEXP")
        {
          this.setState({noteNotCompleteSend: "les E-Mails Expert et Encadrant Académique sont inexistant(s) ou avec format incorrecte."});
        }
        if(result === "NO-AE")
        {
          this.setState({noteNotCompleteSend: "E-Mail Encadrant Académique est inexistant ou avec format incorrecte."});
        }
        if(result === "NO-EXP")
        {
          this.setState({noteNotCompleteSend: "E-Mail Expert est inexistant ou avec format incorrecte."});
        }

        this.setState({
          openPopupJustifyCancelAffectation: false,
          openPopupSuccessCancelAffectAE: true,
          loadCancelAffectation: false,
          studentsByClass: this.state.studentsByClass.map(el => (el.id === id ? {...el, affected } : el))
          //experts: this.state.experts.map(el => (el.id === id ? {...el, nbrExpertises } : el))
        });
      },
      (error) => {
      }
    );
  }

  handleAffectExpert(idEns, nom)
  {

    // console.log('-----------------------> Affect 1: ' + this.state.selectedStudentId);
    // console.log('-----------------------> Affect 2: ' + this.state.selectedStudentFullName);

    // console.log('-----0102-------EXP-> id: ' + idEns);
    // console.log('-----0102-------EXP-> fn: ' + nom);

    this.setState({
      selectedExpertId: idEns,
      selectedExpFullName: nom,
      openPopupVerifAffectEXP: true,
      openPopupAffectAE: false
    });
  }


  loadClassesByPCEAndYear = (selectedYearObject) => {
    console.log('------------------dd------> ESSID1702 - 0');
    this.setState({
      loadList: true,
      selectedYear: selectedYearObject.label,
      studentsByClass: [],
      checked: null
    });

    AuthService.listClassesByOptionAndByYear(currentPedagogicalCoordinator.id, selectedYearObject.label).then(
      (response) => {
        console.log('------------------------> ESSID1702 - 1: ' + new Date() + " - " + this.state.loadList);
        // console.log('--------------opt----------> TREATMENT 2: ' +currentTeacher.codeOptions);
        this.setState({
          myClasses: []
        });
        let stuList = this.state.myClasses.slice();
        for (let cls of response.data)
        {
          console.log('------------------dd------> ESSID1702 - 2: ', cls);
          stuList.push({value: cls, label: cls, color: "#00B8D9"});
        }

        this.setState({
          loadList: false,
          myClasses: stuList
        });

      },
      (error) => {
      }
    );


    /*


    PCService.studentsTrainedByPCEAndYear(currentPedagogicalCoordinator.id, selectedYearObject.label).then(
        (response) => {
          // console.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);
          // console.log('--------------opt----------> TREATMENT 2: ' +currentTeacher.codeOptions);
          this.setState({
            listOfStudentsTrainedByPE: []
          });
          let stuList = this.state.listOfStudentsTrainedByPE.slice();
          for (let stu of response.data)
          {
            // console.log('------------------dd------> TREATMENT 2.1 : ', stu);
            const studentObj = stu;
            stuList.push(stu);
          }

          this.setState({
            loadList: false,
            listOfStudentsTrainedByPE: stuList
          });
          // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

        },
        (error) => {
        }
    );
     */
  }

  render() {

    const {
      myClasses,
      checked,
      studentsByClass,
      loadStudentsByClass,
      loadExperts,
      loadExpertsForModif,
      loadAffectation,
      loadVerifAffectation,
      openPopupShowAEDetails,
      openPopupAffectAE,
      openPopupSuccessAffectAE,
      selectedStudentFullName,
      selectedExpFullName,
      selectedStudentId,
      selectedExpertId,
      expert,
      academicEncadrant,
      tooltipNbrEncadrements,
      experts,
      selectedClass,
      tooltipnbrExpertises,
      noteNotCompleteSend,
      showLoadingExcelIcon,
      privilegeKind,
      justifCancelAffectation,
      openPopupJustifyCancelAffectation,
      loadCancelAffectation,
      openPopupCancelAffectAE,
      openPopupSuccessCancelAffectAE,
      allLabelCEP,
      openPopupVerifAffectEXP,
      allSessionsLabel
    } = this.state;

    return (
      <>
        <CRow>
          <CCol md="4"/>
          <CCol md="4">
            <p className="greyMarkForSelectComp">Merci de choisir une Année pour consulter la résultante</p>
            <Select  placeholder="Please Select an Academic Year"
                     defaultValue={{value: '2021', label: '2021', color: "#00B8D9"}}
                     value={allSessionsLabel.value}
                     components={animatedComponents}
                     options={allSessionsLabel}
                     onChange={this.loadClassesByPCEAndYear}/>
          </CCol>
          <CCol md="4"/>
        </CRow>
        <br/>
        <CCard accentColor="danger">
          <CCardBody>
            {
              myClasses.length === 0 &&
              <center>
                <br/><br/>
                <CIcon name="cil-warning" style={{color: "#666666", width:"30px", height: "30px"}}/>
                <br/><br/>
                <span className="greyMarkCourrier">Désolé, il n'y a pas encore une Classe Courante pour Votre Option.</span>
                <br/><br/><br/><br/>
              </center>
            }

            {
              myClasses.length > 0 &&
              <>
                <CRow>
                  <CCol md="1"/>
                  <CCol md="10">
                    <center>
                      <br/>
                      <span className="redMark">Les Classes de mon {privilegeKind}</span>
                      <br/><br/>
                      {
                        myClasses.map(
                          (optionCLS, iCLS) => {
                            return (
                              <CFormGroup id="grRDV1" variant="custom-radio" inline>
                                <CInputRadio custom id={iCLS}
                                             checked={checked === iCLS ? true : false}
                                             onChange={this.onChangeClass.bind(this, iCLS, optionCLS.label)}/>
                                <CLabel variant="custom-checkbox" htmlFor={iCLS}>{optionCLS.label}</CLabel>
                              </CFormGroup>
                            );
                          }
                        )}
                      <br/><br/>
                    </center>
                  </CCol>
                  <CCol md="1">
                    {
                      !showLoadingExcelIcon &&
                      <CButton shape="pill"
                               onClick={() => this.handleDownloadExpertiseStatus()}>
                        <CTooltip content="Télécharger l'État Expertise de mon Option"
                                  placement="top">
                          <img src={excelGreenIcon} className="cursorPointer"
                               width="40px" height="40px"/>
                        </CTooltip>
                      </CButton>
                    }
                    {
                      showLoadingExcelIcon &&
                      <Spinner animation="grow" variant="success"/>
                    }
                  </CCol>
                </CRow>
                {
                  loadStudentsByClass === true ?
                    <center>
                      <br/><br/><br/>
                      <span className="waitIcon"/>
                      <br/><br/><br/><br/><br/>
                    </center>
                    :
                    <>
                      {
                        selectedClass && studentsByClass.length !== 0 &&
                        <>
                          <hr/>
                          <span className="redMark">Liste des Étudiants de la Classe</span>
                          <br/>
                          <span className="greyMarkCourrier">{selectedClass}
                            <br/>
                            {studentsByClass.length}
                              </span>&nbsp;
                          <span className="greyMarkCourrierSmalLabel">étudiants</span>

                          <br/><br/><br/>

                          <CDataTable items={studentsByClass}
                                      fields={studentsByClassFields}
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
                                      scopedSlots={{
                                        affected: (item) => (
                                          <td>
                                            <CBadge color={getBadgeAffectAE(item.affected)}>{item.affected}</CBadge>
                                          </td>
                                        ),
                                        action: (item) => (
                                          <td>
                                            <center>
                                              {
                                                item.affected === "AFFECTEE" ?
                                                  <CButton shape="pill"
                                                           color="success"
                                                           size="sm"
                                                           aria-expanded="true"
                                                           onClick={() => this.handleConsultDetailsExpert(item.id, item.fullName)}>
                                                    <CTooltip content="Consulter Coordonnées Expert"
                                                              placement="top">
                                                      <CIcon content={freeSet.cilClipboard}/>
                                                    </CTooltip>
                                                  </CButton>
                                                  :
                                                  <>
                                                    {
                                                      (
                                                        (selectedStudentId !== item.id && loadExperts === true)
                                                        ||
                                                        loadExperts === false
                                                      ) &&
                                                      <>
                                                        <CButton shape="pill"
                                                                 color="dark"
                                                                 size="sm"
                                                                 aria-expanded="true"
                                                                 onClick={() => this.handleGotListCEPs(item.id, item.fullName)}>
                                                          <CTooltip content="Affecter Expert" placement="top">
                                                            <CIcon content={freeSet.cilUser}/>
                                                          </CTooltip>
                                                        </CButton>
                                                      </>

                                                    }
                                                  </>
                                              }
                                            </center>
                                          </td>
                                        ),
                                      }}
                          />
                        </>
                      }
                    </>
                }
                <br/><br/>
              </>
            }
          </CCardBody>


          <Dialog fullHight fullWidth
                  maxWidth="md"
                  keepMounted
                  open={openPopupShowAEDetails}
                  onClose={this.handleClosePopupShowAEDetails}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <CRow>
                <CCol md="7">
                  <span className="myModalTitleMarginTop">
                    Contacts Expert & Encadrant Académique pour l'Étudiant(e)
                  </span>
                </CCol>
                <CCol md="5" className="colPadding">
                  <div className="myDivMarginTop">
                    <span className="myModalSubTitle">
                      <ReactTextTransition text={selectedStudentFullName}
                                           springConfig={{tension: 10, friction: 10}}/>
                    </span>
                  </div>
                </CCol>
              </CRow>

              <hr/>
            </DialogTitle>
            <DialogContent>
              <CRow>
                <CCol md="6">
                  <CAlert color="success">
                    <CRow>
                      <CCol md="10">
                        <strong>Expert</strong>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <span className="clignoteGreen">{expert.nom}</span>
                      </CCol>
                      <CCol md="2">
                        <CTooltip content={tooltipnbrExpertises} placement="bottom">
                          <Badge badgeContent={expert.nbrExpertises} showZero
                                 color="secondary" style={{marginTop: "5px", marginBottom: "-10px"}}>
                            <PersonIcon/>
                          </Badge>
                        </CTooltip>
                      </CCol>
                    </CRow>
                    <hr/>
                    <CRow>
                      <CCol xs="5">
                        <span className="myModalFieldCr">
                          Identifiant:
                        </span>
                      </CCol>
                      <CCol xs="7">
                        {expert.identifiant}
                      </CCol>
                    </CRow>
                    <CRow>
                      <CCol xs="5">
                         <span className="myModalFieldCr">
                            Unité Pédagogique:
                         </span>
                      </CCol>
                      <CCol xs="7">
                        {
                          expert.up ?
                            <>{expert.up}</>
                            :
                            <>--</>
                        }
                      </CCol>
                    </CRow>
                    <CRow>
                      <CCol xs="5">
                        <span className="myModalFieldCr">
                          E-Mail:
                        </span>
                      </CCol>
                      <CCol xs="7">
                        {
                          expert.mail ?
                            <>{expert.mail}</>
                            :
                            <>--</>
                        }
                      </CCol>
                    </CRow>
                    <CRow>
                      <CCol xs="5">
                        <span className="myModalFieldCr">
                          Numéro Téléphone:
                        </span>
                      </CCol>
                      <CCol xs="7">
                        {
                          expert.téléphone ?
                            <>{expert.téléphone}</>
                            :
                            <>--</>
                        }
                      </CCol>
                    </CRow>
                  </CAlert>
                </CCol>
                <CCol md="6">
                  <CAlert color="info">
                    {
                      academicEncadrant.identifiant === null &&
                      <>
                        <strong>Encadrant Académique</strong>
                        <hr/>
                        <center>
                          <span className="warningBlueIconOnly"/>
                          <br/>
                          <span className="blueAlertlignote" style={{marginBottom: "14px"}}>Affectation&nbsp;<strong> pas encore </strong>&nbsp;effectuée !.</span>
                          <br/><br/>
                        </center>
                      </>
                    }

                    {
                      academicEncadrant.identifiant !== null &&
                      <>
                        <CRow>
                          <CCol md="10">
                            <strong>Encadrant Académique</strong>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span className="clignoteBlue">{academicEncadrant.nom}</span>
                          </CCol>
                          <CCol md="2">
                            <CTooltip content={tooltipNbrEncadrements} placement="bottom">
                              <Badge badgeContent={academicEncadrant.nbrEncadrements} showZero
                                     color="secondary" style={{marginTop: "5px", marginBottom: "-10px"}}>
                                <PersonIcon/>
                              </Badge>
                            </CTooltip>
                          </CCol>
                        </CRow>
                        <hr/>
                        <CRow>
                          <CCol xs="5">
                          <span className="myModalFieldCr">
                            Identifiant:
                          </span>
                          </CCol>
                          <CCol xs="7">
                            {academicEncadrant.identifiant}
                          </CCol>
                        </CRow>
                        <CRow>
                          <CCol xs="5">
                              <span className="myModalFieldCr">
                                Unité Pédagogique:
                              </span>
                          </CCol>
                          <CCol xs="7">
                            {
                              academicEncadrant.up ?
                                <>{academicEncadrant.up}</>
                                :
                                <>--</>
                            }
                          </CCol>
                        </CRow>
                        <CRow>
                          <CCol xs="5">
                              <span className="myModalFieldCr">
                                E-Mail:
                              </span>
                          </CCol>
                          <CCol xs="7">
                            {
                              academicEncadrant.mail ?
                                <>{academicEncadrant.mail}</>
                                :
                                <>--</>
                            }
                          </CCol>
                        </CRow>
                        <CRow>
                          <CCol xs="5">
                              <span className="myModalFieldCr">
                                Numéro Téléphone:
                              </span>
                          </CCol>
                          <CCol xs="7">
                            {
                              academicEncadrant.téléphone ?
                                <>{academicEncadrant.téléphone}</>
                                :
                                <>--</>
                            }
                          </CCol>
                        </CRow>
                      </>
                    }
                  </CAlert>
                </CCol>
              </CRow>

              {
                loadExpertsForModif === false &&
                <center>
                  <br/><br/>
                  <CButton shape="pill"
                           color="warning"
                           size="sm"
                           aria-expanded="true"
                           onClick={() => this.handleGotListCEPsForModif()}>
                    <CTooltip content="Modifier Affectation Expert" placement="top">
                      <CIcon content={freeSet.cilReload}/>
                    </CTooltip>
                  </CButton>
                  &nbsp;&nbsp;
                  <CButton shape="pill"
                           color="dark"
                           size="sm"
                           aria-expanded="true"
                           onClick={() => this.handleOpenPopupJustifyDeleteAffectation()}>
                    <CTooltip content="Annuler Affectation Expert" placement="top">
                      <CIcon content={freeSet.cilDelete}/>
                    </CTooltip>
                  </CButton>
                </center>
              }

              {
                loadExpertsForModif === true &&
                <center>
                  <br/><br/>
                  <Spinner animation="grow" variant="dark"/>
                </center>
              }

              <br/>
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClosePopupShowAEDetails} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="md"
                  open={openPopupAffectAE}
                  onClose={this.handleClosePopupAffectAE}
                  aria-labelledby="form-dialog-title"
                  PaperProps={{style: {overflowY: 'visible'}}}>
            <DialogTitle id="form-dialog-title">
              <CRow>
                <CCol md="5">
                  <span className="myModalTitleMarginTop">
                    Affectation de l'Expert à l'Étudiant(e)
                  </span>
                </CCol>
                <CCol md="6" className="colPadding">
                  <div className="myDivMarginTop">
                    <span className="myModalSubTitle">
                      <ReactTextTransition text={selectedStudentFullName}
                                           springConfig={{tension: 10, friction: 10}}/>
                    </span>
                  </div>
                </CCol>
                <CCol md="1">
                  <CButton onClick={this.handleClosePopupAffectAE} size="sm" className="float-right">
                    <CIcon content={freeSet.cilX}/>
                  </CButton>
                </CCol>
              </CRow>
              <hr/>

            </DialogTitle>
            <DialogContent style={{ overflowY: 'visible' }}>
              {
                academicEncadrant.identifiant === null &&
                <CRow>
                  <CCol md="3"/>
                  <CCol md="6">
                    <CAlert color="info">
                      <center>
                        <span className="warningBlueIconOnly"/>
                        &nbsp;&nbsp;
                        <span className="blueAlertlignote"><strong>Encadrant Académique&nbsp;</strong> pas encore affecté(e) .</span>
                        <br/>
                      </center>
                    </CAlert>
                  </CCol>
                  <CCol md="3"/>
                </CRow>
              }

              {
                academicEncadrant.identifiant !== null &&
                <CAlert color="info">
                  <strong>Encadrant Académique</strong>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <span className="clignoteBlue">{academicEncadrant.nom}</span>
                  <hr/>
                  <CRow>
                    <CCol md="1">
                      <CTooltip content={tooltipNbrEncadrements} placement="bottom">
                        <Badge badgeContent={academicEncadrant.nbrEncadrements} showZero
                               color="secondary" style={{marginTop: "12px"}}>
                          <PersonIcon/>
                        </Badge>
                      </CTooltip>
                    </CCol>
                    <CCol md="5" className="colPadding">
                      <CRow>
                        <CCol xs="5">
                            <span className="myModalFieldCr">
                              Identifiant:
                            </span>
                        </CCol>
                        <CCol xs="7">
                          {academicEncadrant.identifiant}
                        </CCol>
                      </CRow>
                      <CRow>

                        <CCol xs="5">
                            <span className="myModalFieldCr">
                              Unité Pédagogique:
                            </span>
                        </CCol>
                        <CCol xs="7">
                          {
                            academicEncadrant.up ?
                              <>{academicEncadrant.up}</>
                              :
                              <>--</>
                          }
                        </CCol>
                      </CRow>
                    </CCol>
                    <CCol md="6" className="colPadding">
                      <CRow>
                        <CCol xs="5">
                            <span className="myModalFieldCr">
                              E-Mail:
                            </span>
                        </CCol>
                        <CCol xs="7">
                          {
                            academicEncadrant.mail ?
                              <>{academicEncadrant.mail}</>
                              :
                              <>--</>
                          }
                        </CCol>
                      </CRow>
                      <CRow>
                        <CCol xs="5">
                            <span className="myModalFieldCr">
                              Numéro Téléphone:
                            </span>
                        </CCol>
                        <CCol xs="7">
                          {
                            academicEncadrant.téléphone ?
                              <>{academicEncadrant.téléphone}</>
                              :
                              <>--</>
                          }
                        </CCol>
                      </CRow>
                    </CCol>
                  </CRow>
                </CAlert>
              }

              <br/>
              <CRow>
                <CCol md="3"/>
                <CCol md="6">
                  <Select  placeholder="Please Select an Expertise"
                           value={allLabelCEP.value}
                           components={animatedComponents}
                           options={allLabelCEP}
                           onChange={this.onChangeSelectCEP}/>
                </CCol>
                <CCol md="3"/>
              </CRow>

              <br/><br/>

              {
                loadExperts === true ?
                  <center>
                    <br/><br/><br/>
                    <span className="waitIcon"/>
                    <br/><br/><br/><br/><br/>
                  </center>
                  :
                  <>
                    {
                      experts.length !== 0 &&
                      <CDataTable items={experts}
                                  fields={expertsFields}
                                  tableFilter
                                  columnFilter
                                  itemsPerPageSelect
                                  hover
                                  sorter
                                  striped
                                  bordered
                                  size="sm"
                                  itemsPerPage={5}
                                  pagination
                                  noItemsContent='azerty'
                                  scopedSlots={{
                                    action: (item) => (
                                      <td>
                                        <center>
                                          {
                                            (
                                              (loadVerifAffectation === false)
                                              ||
                                              (item.identifiant !== selectedExpertId && loadVerifAffectation === true)
                                            ) &&
                                            <CButton shape="pill"
                                                     color="primary"
                                                     size="sm"
                                                     aria-expanded="true"
                                                     onClick={() => this.handleAffectExpert(item.identifiant, item.nom)}>
                                              <CTooltip content="Confirmer l'affectation"
                                                        placement="top">
                                                <CIcon content={freeSet.cilBadge}/>
                                              </CTooltip>
                                            </CButton>
                                          }
                                        </center>
                                      </td>
                                    ),
                                  }}
                      />
                    }
                  </>
              }
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClosePopupAffectAE} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={openPopupVerifAffectEXP}
                  onClose={this.handleClosePopupOKAffectExpert}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                Êtes vous sûrs de vouloir affecter
                <br/>
                l'Expert <span className="infoMarkPopup">{selectedExpFullName}</span> &nbsp;
                à l'Étudiant <span className="infoMarkPopup">{selectedStudentFullName}</span> ?.
                <br/><br/><br/>
              </center>
            </DialogContent>
            <DialogActions>

              {
                loadAffectation === true ?
                  <Spinner animation="grow" variant="primary"/>
                  :
                  <Button onClick={this.handleConfirmAffectEXPtoET} color="primary">
                    Oui
                  </Button>
              }
              &nbsp;&nbsp;
              <Button onClick={this.handleClosePopupOKAffectExpert} color="primary">
                Non
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={openPopupSuccessAffectAE}
                  onClose={this.handleClosePopupOKAffectExpert}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                <br/>
                L'affectation de
                <br/>
                l'Expert&nbsp;
                <span className="infoMarkPopup">{selectedExpFullName}</span>
                &nbsp;à l'Étudiant&nbsp;
                <span className="infoMarkPopup">{selectedStudentFullName}</span>&nbsp;
                <br/>
                a été effectué avec succès.

                {
                  noteNotCompleteSend !== "" &&
                  <>
                    <br/><br/>
                    <span className="errorS1">Erreur Envoi: </span>&nbsp;
                    <span className="errorS2">{noteNotCompleteSend}</span>
                  </>
                }

                <br/><br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClosePopupSuccessAffectExpert} color="primary">
                EXIT
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={openPopupJustifyCancelAffectation}
                  onClose={this.handleClosePopupJustifyDeleteAffectation}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-Vous sûr de vouloir annuler l'affection de
                <br/>
                l'Expert&nbsp;
                <span className="infoMarkPopup">{expert.nom}</span>
                &nbsp;à l'Étudiant&nbsp;
                <span className="infoMarkPopup">{selectedStudentFullName}</span>&nbsp;?.
                <br/><br/><br/>

                Merci de préciser le <span className="redMarkAlert">Motif Annulation</span> :
                <br/><br/>
                <textarea
                  className="form-control"
                  style={{ height: 75 }}
                  placeholder="Présenter le Motif d'Annulation de l'affectation ..."
                  value={justifCancelAffectation}
                  onChange={(e) =>
                    this.onChangeJustifCancelAffectation(e)
                  }
                  maxLength="200"
                />
                <br/>
              </center>
            </DialogContent>
            <DialogActions>
              {
                !loadCancelAffectation &&
                <Button onClick={this.handleDeleteAffectation} disabled={justifCancelAffectation.length === 0} color="primary">
                  Confirmer l'Annulation
                </Button>
              }
              {
                loadCancelAffectation &&
                <Spinner animation="grow" variant="secondary"/>
              }
              &nbsp;&nbsp;
              <Button onClick={this.handleClosePopupJustifyDeleteAffectation} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={openPopupSuccessCancelAffectAE}
                  onClose={this.handleClosePopupCancelAffectAE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <span className="myModalTitleMarginTop">
                Annulation Affectation de l'Expert
              </span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                L'affectation de
                <br/>
                l'Expert&nbsp;
                <span className="infoMarkPopup">{expert.nom}</span>
                &nbsp;à l'Étudiant&nbsp;
                <span className="infoMarkPopup">{selectedStudentFullName}</span>&nbsp;
                <br/>
                <span className="greyMarkCourrier">a été annulée</span> &nbsp;avec succès avec le motif <span className="greyMarkCourrier">{justifCancelAffectation}</span>.
                <br/><br/>
              </center>
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClosePopupCancelAffectAE} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>
        </CCard>
      </>
    );
  }
}
