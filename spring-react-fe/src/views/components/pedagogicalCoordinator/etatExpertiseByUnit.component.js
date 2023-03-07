import React, {Component} from "react";
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
  CTooltip
} from "@coreui/react";
import CIcon from "@coreui/icons-react";

import AuthService from "../../services/auth.service";
import PedagogicalCoordinatorService from "../../services/pedagogicalCoordinator.service";

import "../../css/style.css";
//import { Wave1, Wave2, Random1, Random2 } from './example.js';

import excelBlueIcon from "../../images/excelBlueIcon.jpg";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import Badge from '@material-ui/core/Badge';
import PersonIcon from '@material-ui/icons/Person';

import {freeSet} from '@coreui/icons';
import "../../css/style.css";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import ReactTextTransition from "react-text-transition";

import Spinner from "react-bootstrap/Spinner";

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const currentPedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();


const academicEncadrantsFields = [
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
    _style: {width: '49%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'nbrEncadrements',
    label: 'Nbr Expertises',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'up',
    label: 'Unité Pédagogique',
    _style: {width: '20%'},
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

const affectedStudentsFields = [
  {
    key: 'idEt',
    label: 'Identifiant',
    _style: {width: '6%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'fullName',
    label: 'Nom & Prénom',
    _style: {width: '29%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'univerYear',
    label: 'Année Universitaire',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'option',
    label: 'Option',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'classe',
    label: 'Classe',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'adressemailesp',
    label: 'E-Mail',
    _style: {width: '20%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'phone',
    label: 'Téléphone',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  }
];

const API_URL_PC = process.env.REACT_APP_API_URL_PC;

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
    _style: {width: '20%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'nom',
    label: 'Nom & Prénom',
    _style: {width: '55%'},
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



export default class EtatExpertiseByUnit extends Component {
  constructor(props) {
    super(props);

    this.loadAcademicExperts = this.loadAcademicExperts.bind(this);
    this.showAffectedStudentsList = this.showAffectedStudentsList.bind(this);
    this.handleClosePopupShowAffectedStudentsToAE = this.handleClosePopupShowAffectedStudentsToAE.bind(this);
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
      academicExperts: [],
      affectesStudentsToAE: [],
      loadAcademicExperts: true,
      loadAffectedStudentsToAE: false,
      selectedAcademicExpertId: "",
      selectedAcademicExpertFullName: "",
      openPopupShowAffectesStudentsListToAE: false,
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
      selectedCEPLabel: ""
    }

    let pcMail = currentPedagogicalCoordinator.id;
    if(pcMail.includes("CPS_"))
    {
      this.state.privilegeKind = "Option";
    }

    if(pcMail.includes("CD_") || pcMail.includes("CD-"))
    {
      this.state.privilegeKind = "Département";
    }

    let requestb = new XMLHttpRequest();
    requestb.open(
        "GET",
        API_URL_MESP + "listClassesByOption/" + currentPedagogicalCoordinator.id,
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

    // Load CEP
    let strIntoObjComp = [];
    var requestComp = new XMLHttpRequest();
    requestComp.open(
        "GET",
        API_URL_PC + "listLibCEPByYear",
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
    });

  }

  loadAcademicExperts()
  {
    this.setState({loadAcademicExperts: true});

    PedagogicalCoordinatorService.gotListExperts().then(
        (response) => {

          let aeList = this.state.academicExperts.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          this.setState({
            loadAcademicExperts: false,
            academicExperts: aeList
          });

        },
        (error) => {
        }
    );
  }

  showAffectedStudentsList(idEns, nomEns)
  {

    this.setState({
      affectesStudentsToAE: [],
      loadAffectedStudentsToAE: true,
      selectedAcademicExpertId: idEns,
      selectedAcademicExpertFullName: nomEns
    });

    AuthService.findAllAffectedStudentsToAExp(idEns).then(
        (response) => {

          let aeList = this.state.affectesStudentsToAE.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          // console.log('--------------> HI2021', aeList);

          this.setState({
            loadAffectedStudentsToAE: false,
            affectesStudentsToAE: aeList,
            openPopupShowAffectesStudentsListToAE: true
          });

        },
        (error) => {
        }
    );
  }

  handleClosePopupShowAffectedStudentsToAE()
  {
    this.setState({openPopupShowAffectesStudentsListToAE: false});
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

    AuthService.gotListSudentsByClassForExp(optionCLS, currentPedagogicalCoordinator.id).then(
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

    console.log('------1802--------> SARS: ' + cepLabel);

    this.setState({
      loadExperts: true,
      experts: [],
      selectedCEPLabel: cepLabel
    });

    PedagogicalCoordinatorService.gotListExperts(encodeURIComponent(cepLabel)).then(
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

    PedagogicalCoordinatorService.downloadExpertiseStatusByOption(currentPedagogicalCoordinator.id).then(
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

  handleConsultDetailsExpert(idEt, fullName) {
    this.setState({
      expert: {},
      selectedStudentFullName: fullName,
      selectedStudentId: idEt
    });

    let requestb = new XMLHttpRequest();
    requestb.open(
        "GET",
        API_URL_PC + "findExpertByStudent/" + idEt,
        false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);
    let result = JSON.parse(requestb.responseText);
    // console.log('-------------------> >sars 1110: ', result);

    let note = result.nom + " posséde " + result.nbrExpertises + " Expertises." ;
    this.setState({
      expert: result,
      openPopupShowAEDetails: true,
      tooltipnbrExpertises: note
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

    console.log('-----0102-------ET-> id: ' + idEt);
    console.log('-----0102-------ET-> fn: ' + fullName);

    this.setState({
      selectedStudentFullName: fullName,
      selectedStudentId: idEt,
      experts: [],
      noteNotCompleteSend: "",
      openPopupAffectAE: true
    });

  }

  handleGotListCEPsForModif()
  {

    this.setState({
      experts: [],
      noteNotCompleteSend: "",
      openPopupAffectAE: true,
      openPopupShowAEDetails: false
    });

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

          if(result === "NO-AEST")
          {
            this.setState({noteNotCompleteSend: "les E-Mails Expert et Étudiant sont inexistant(s) ou avec format incorrecte."});
          }
          if(result === "NO-AE")
          {
            this.setState({noteNotCompleteSend: "E-Mail Expert est inexistant ou avec format incorrecte."});
          }
          if(result === "NO-ST")
          {
            this.setState({noteNotCompleteSend: "E-Mail Étudiant est inexistant ou avec format incorrecte."});
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

          if(result === "NO-AEST")
          {
            this.setState({noteNotCompleteSend: "les E-Mails Expert et Étudiant sont inexistant(s) ou avec format incorrecte."});
          }
          if(result === "NO-AE")
          {
            this.setState({noteNotCompleteSend: "E-Mail Expert est inexistant ou avec format incorrecte."});
          }
          if(result === "NO-ST")
          {
            this.setState({noteNotCompleteSend: "E-Mail Étudiant est inexistant ou avec format incorrecte."});
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

  render() {

    const {
      academicExperts,
      loadAcademicExperts,
      loadAffectedStudentsToAE,
      selectedAcademicExpertId,
      selectedAcademicExpertFullName,
      openPopupShowAffectesStudentsListToAE,
      affectesStudentsToAE,
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
      selectedCEPLabel
    } = this.state;

    return (
        <>
          <CCard accentColor="danger">
            <CCardBody>
              <CRow>
                <CCol md="4"/>
                <CCol md="4">
                  <br/>
                  <Select  placeholder="Please Select an Expertise"
                           value={allLabelCEP.value}
                           components={animatedComponents}
                           options={allLabelCEP}
                           onChange={this.onChangeSelectCEP}/>
                </CCol>
                <CCol md="4"/>
              </CRow>

              {
                selectedCEPLabel === "" &&
                <center>
                  <br/><br/><br/><br/>
                  <CIcon name="cil-warning" style={{color: "#666666", width:"30px", height: "30px"}}/>
                  <br/><br/>
                  <span className="greyMarkCourrier">Désolé, Vous n'avez pas encore choisi une Expertise !.</span>
                  <br/><br/><br/><br/>
                </center>
              }

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
                          <>
                            <span className="redMark">Liste des Experts de l'expertise</span>
                            <br/>
                            <span className="greyMarkCourrier">{selectedCEPLabel}
                              <br/>
                              {experts.length}
                              </span>&nbsp;
                            <span className="greyMarkCourrierSmalLabel">experts</span>

                            <br/><br/><br/>

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
                                                        item.nbrExpertises === 0 &&
                                                        (
                                                            (loadAffectedStudentsToAE === false)
                                                            ||
                                                            (item.identifiant !== selectedAcademicExpertId && loadAffectedStudentsToAE === true)
                                                        )
                                                    ) &&
                                                    <CButton shape="pill"
                                                             color="dark"
                                                             size="sm"
                                                             disabled="true"
                                                             aria-expanded="true">
                                                      <CTooltip
                                                          content="Bouton DISABLED : Cet Enseignant n'a pas encore des expertises"
                                                          placement="top">
                                                        <CIcon content={freeSet.cilMinus}/>
                                                      </CTooltip>
                                                    </CButton>
                                                  }
                                                  {
                                                    (
                                                        item.nbrExpertises !== 0 &&
                                                        (
                                                            (loadAffectedStudentsToAE === false)
                                                            ||
                                                            (item.identifiant !== selectedAcademicExpertId && loadAffectedStudentsToAE === true)
                                                        )
                                                    ) &&
                                                    <CButton shape="pill"
                                                             color="primary"
                                                             size="sm"
                                                             aria-expanded="true"
                                                             onClick={() => this.showAffectedStudentsList(item.identifiant, item.nom)}>
                                                      <CTooltip content="Consulter la liste des Étudiants à encadrer"
                                                                placement="top">
                                                        <CIcon content={freeSet.cilBraille}/>
                                                      </CTooltip>
                                                    </CButton>
                                                  }
                                                  {
                                                    item.identifiant === selectedAcademicExpertId && loadAffectedStudentsToAE === true &&
                                                    <Spinner animation="grow" variant="primary"/>
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
            </CCardBody>
          </CCard>

          <Dialog fullHight fullWidth
                  maxWidth="md"
                  keepMounted
                  open={openPopupShowAffectesStudentsListToAE}
                  onClose={this.handleClosePopupShowAffectedStudentsToAE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <CRow>
                <CCol md="6">
                          <span className="myModalTitleMarginTop">
                            Liste des Étudiants encadrés en tant que Expert par Mme/M
                          </span>
                </CCol>
                <CCol md="6" className="colPadding">
                  <div className="myDivMarginTop">
                            <span className="myModalSubTitle">
                              <ReactTextTransition text={selectedAcademicExpertFullName}
                                                   springConfig={{tension: 10, friction: 10}}/>
                            </span>
                  </div>
                </CCol>
              </CRow>
              <hr/>
            </DialogTitle>
            <DialogContent>
              {
                <CDataTable items={affectesStudentsToAE}
                            fields={affectedStudentsFields}
                            tableFilter
                            columnFilter
                            itemsPerPageSelect
                            hover
                            sorter
                            striped
                            bordered
                            size="sm"
                            itemsPerPage={6}
                            pagination/>
              }
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClosePopupShowAffectedStudentsToAE} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>
        </>
    );
  }
}
