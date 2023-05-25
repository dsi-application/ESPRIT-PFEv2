import React, {Component} from "react";
import AuthService from "../../services/auth.service";
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
  CTooltip
} from "@coreui/react";

import excelGreenIcon from "../../images/excelGreenIcon.png";
import CIcon from "@coreui/icons-react";

import Badge from '@material-ui/core/Badge';
import PersonIcon from '@material-ui/icons/Person';

import {freeSet} from '@coreui/icons';
import "../../css/style.css";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import ReactTextTransition from "react-text-transition";

import Spinner from "react-bootstrap/Spinner";

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
    key: 'affected',
    label: 'État',
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
    label: 'Nbr Encadrements',
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

export default class AffectAcademicEncadrantComponent extends Component {
  constructor(props) {
    super(props);

    // this.onChangeClass = this.onChangeClass.bind(this);
    this.handleConsultDetailsAcademicEncadrant = this.handleConsultDetailsAcademicEncadrant.bind(this);
    this.handleAffectAcademicEncadrant = this.handleAffectAcademicEncadrant.bind(this);
    this.handleClosePopupShowAEDetails = this.handleClosePopupShowAEDetails.bind(this);
    this.handleClosePopupAffectAE = this.handleClosePopupAffectAE.bind(this);
    this.handleClosePopupCancelAffectAE = this.handleClosePopupCancelAffectAE.bind(this);
    this.handleGotListAcademicEncadrantsByYear = this.handleGotListAcademicEncadrantsByYear.bind(this);
    this.handleGotListAcademicEncadrantsByYearForModif = this.handleGotListAcademicEncadrantsByYearForModif.bind(this);
    this.handleOpenPopupJustifyDeleteAffectation = this.handleOpenPopupJustifyDeleteAffectation.bind(this);
    this.handleClosePopupJustifyDeleteAffectation = this.handleClosePopupJustifyDeleteAffectation.bind(this);
    this.handleDeleteAffectation = this.handleDeleteAffectation.bind(this);
    this.handleClosePopupSuccessAffectAcademicEncadrant = this.handleClosePopupSuccessAffectAcademicEncadrant.bind(this);
    this.loadingAcademicEncadrant = this.loadingAcademicEncadrant.bind(this);
    this.handleDownloadEncadrementStatus = this.handleDownloadEncadrementStatus.bind(this);
    this.onChangeJustifCancelAffectation = this.onChangeJustifCancelAffectation.bind(this);
    this.onChangeSelectSessionLabel = this.onChangeSelectSessionLabel.bind(this);
    this.onChangeSelectCEP = this.onChangeSelectCEP.bind(this);

// INIT2
    this.state = {
      myClasses: [],
      checked: null,
      studentsByClass: [],
      academicEncadrants: [],
      loadStudentsByClass: false,
      loadAcademicEncadrants: false,
      loadAcademicEncadrantsForModif: false,
      loadAffectation: false,
      academicEncadrant: {},
      openPopupShowAEDetails: false,
      openPopupAffectAE: false,
      openPopupSuccessAffectAE: false,
      openPopupSuccessCancelAffectAE: false,
      selectedStudentFullName: "",
      selectedStudentId: "",
      selectedAEFullName: "",
      selectedAcademicEncadrantId: "",
      selectedClass: "",
      tooltipNbrEncadrements: "",
      noteNotCompleteSend: "",
      showLoadingExcelIcon: false,
      privilegeKind: "",
      justifCancelAffectation: "",
      openPopupJustifyCancelAffectation: false,
      loadCancelAffectation: false,
      allSessionsLabel: [],
      selectedYear: '2022',
      loadStudentsCJByYear: true
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

    let requestcls = new XMLHttpRequest();
    requestcls.open(
        "GET",
        API_URL_MESP + "listClassesByOptionAndByYear/" + currentPedagogicalCoordinator.id + "/" + this.state.selectedYear,
        false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestcls.send(null);
    let result = JSON.parse(requestcls.responseText);
    // console.log('-------------------> >sars 1110: ' + result);

    for (let cls of result) {
      // console.log('----sars 1110---------- unit cls: ' + cls);
      this.state.myClasses.push({
        value: cls,
        label: cls,
        color: "#00B8D9",
      });
    }

    console.log('----- CLASSES PIK -----------> ', this.state.myClasses);

  }

  onChangeClass(iCLS, optionCLS) {
    console.log('-22.09.22-------------->', this.state.selectedYear); // Object { value: "Non", label: "Non"}
    console.log('-----22.09.22-----------> 1.1: ', iCLS);
    console.log('-----22.09.22-----------> 1.2: ', optionCLS);

    this.setState({
      checked: iCLS,
      selectedClass: optionCLS,
      loadStudentsByClass: true
    });

    // console.log('---------lol123-------> 1.2: ' + this.state.checked + "_" + iCLS + " - " + optionCLS);

    AuthService.gotListSudentsByYearAndClass(this.state.selectedYear, optionCLS, currentPedagogicalCoordinator.id).then(
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

  handleDownloadEncadrementStatus()
  {
    this.setState({showLoadingExcelIcon: true})

    PedagogicalCoordinatorService.downloadEncadrementStatusByOptionAndYear(currentPedagogicalCoordinator.id, this.state.selectedYear).then(
        (response) => {
          this.setState({showLoadingExcelIcon: false})


          const contentDispo = response.headers['content-disposition'];
          console.log(' 1 ---***********OPI***** Clear: ' , contentDispo);
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

  handleConsultDetailsAcademicEncadrant(idEt, fullName) {
    this.setState({
      academicEncadrant: {},
      selectedStudentFullName: fullName,
      selectedStudentId: idEt
    });

    let requestb = new XMLHttpRequest();
    requestb.open(
        "GET",
        API_URL_PC + "findPedagogicalEncadrantByStudentAndYear/" + idEt + "/" + this.state.selectedYear,
        false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);
    let result = JSON.parse(requestb.responseText);
    // console.log('-------------------> >sars 1110: ', result);

    let note = result.nom + " posséde " + result.nbrEncadrements + " Encadrement(s)." ;
    this.setState({
      academicEncadrant: result,
      openPopupShowAEDetails: true,
      tooltipNbrEncadrements: note
    });
  }

  handleClosePopupShowAEDetails()
  {
    this.setState({
      openPopupShowAEDetails: false,
      loadAcademicEncadrantsForModif: false,
      academicEncadrants: []
    });
  }

  handleClosePopupAffectAE()
  {
    this.setState({openPopupAffectAE: false});
  }

  handleClosePopupCancelAffectAE()
  {
    this.setState({openPopupSuccessCancelAffectAE: false});
  }

  handleClosePopupSuccessAffectAcademicEncadrant()
  {
    this.setState({openPopupSuccessAffectAE: false});
  }

  loadingAcademicEncadrant(idEt)
  {
    this.setState({
      loadAcademicEncadrants: true
    });
    return this.state.loadAcademicEncadrants;
  }

  handleGotListAcademicEncadrantsByYear(idEt, fullName)
  {
    this.setState({
      selectedStudentFullName: fullName,
      selectedStudentId: idEt,
      academicEncadrants: [],
      noteNotCompleteSend: ""
    });

    if(this.state.selectedStudentId = idEt)
    {
      // console.log('------------------- I select' + this.state.selectedStudentId + " - " + idEt)
      this.loadingAcademicEncadrant(idEt);
    }

    PedagogicalCoordinatorService.gotListAcademicEncadrantsByYear(this.state.selectedYear).then(
        (response) => {

          let aeList = this.state.academicEncadrants.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          this.setState({
            loadAcademicEncadrants: false,
            academicEncadrants: aeList,
            openPopupAffectAE: true
          });

        },
        (error) => {
        }
    );
  }

  onChangeSelectSessionLabel = (selectedSessionComp) => {

    let sessionLabel = selectedSessionComp.label;

    console.log('------1802--------> SARS: ' + sessionLabel);

    this.setState({
      loadSessions: true,
      sessions: [],
      selectedSessionLabel: sessionLabel
    });

    PedagogicalCoordinatorService.gotListSessions(encodeURIComponent(sessionLabel)).then(
        (response) => {

          let aeList = this.state.experts.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          this.setState({
            loadSessions: false,
            sessions: aeList,
            openPopupAffectAE: true
          });

        },
        (error) => {
        }
    );

  };

  handleGotListAcademicEncadrantsByYearForModif()
  {
    this.setState({
      academicEncadrants: [],
      loadAcademicEncadrantsForModif: true,
      noteNotCompleteSend: ""
    });

    PedagogicalCoordinatorService.gotListAcademicEncadrantsByYear(this.state.selectedYear).then(
        (response) => {

          let aeList = this.state.academicEncadrants.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          this.setState({
            loadAcademicEncadrantsForModif: false,
            academicEncadrants: aeList,
            openPopupAffectAE: true
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
    // console.log('-----------------------> Affect 1: ' + this.state.selectedAcademicEncadrantId);

    this.setState({
      loadCancelAffectation: true,
    });

    PedagogicalCoordinatorService.cancelAffectAcademicEncadrantToStudent(this.state.academicEncadrant.identifiant, this.state.selectedStudentId, currentPedagogicalCoordinator.id, this.state.justifCancelAffectation).then(
        (response) => {
          let result = response.data;
          // console.log('---------------- RES: ', response.data)
          let affected = "PAS ENCORE";
          let id = this.state.selectedStudentId;
          //let nbrEncadrements = Number(nbrEncs+1);

          if(result === "NO-AEST")
          {
            this.setState({noteNotCompleteSend: "les E-Mails Encadrant Académique et Étudiant sont inexistant(s) ou avec format incorrecte."});
          }
          if(result === "NO-AE")
          {
            this.setState({noteNotCompleteSend: "E-Mail Encadrant Académique est inexistant ou avec format incorrecte."});
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
            //academicEncadrants: this.state.academicEncadrants.map(el => (el.id === id ? {...el, nbrEncadrements } : el))
          });
        },
        (error) => {
        }
    );
  }

  onChangeSelectCEP = (selectedCompOption) => {

    let cepLabel = selectedCompOption.label;

    console.log('----------1-------------> 21.09.22: ' , selectedCompOption);
    console.log('----------2-------------> 21.09.22: ' , cepLabel);
    console.log('----------3-------------> 21.09.22: ' , currentPedagogicalCoordinator.id);

    this.state.myClasses = [];
    this.setState({
      selectedClass: "",
      experts: [],
      selectedYear : selectedCompOption.label,
      studentsByClass: [],
      checked: null
    });

    let requestb = new XMLHttpRequest();
    requestb.open(
        "GET",
        API_URL_MESP + "listClassesByOptionAndByYear/" + currentPedagogicalCoordinator.id + "/" + cepLabel,
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

    console.log('----- CLASSES PIK -----------> ', this.state.myClasses);

  };

  handleAffectAcademicEncadrant(idEns, nom)
  {

    // console.log('-----------------------> Affect 1: ' + this.state.selectedStudentId);
    // console.log('-----------------------> Affect 2: ' + this.state.selectedStudentFullName);

    this.setState({
      selectedAcademicEncadrantId: idEns,
      loadAffectation: true
    });

    PedagogicalCoordinatorService.affectAcademicEncadrantToStudent(idEns, this.state.selectedStudentId, currentPedagogicalCoordinator.id).then(
        (response) => {

          let result = response.data;
          // console.log('---------------- RES: ', response.data)
          let affected = "AFFECTEE";
          let id = this.state.selectedStudentId;
          //let nbrEncadrements = Number(nbrEncs+1);

          if(result === "NO-AEST")
          {
            this.setState({noteNotCompleteSend: "les E-Mails Encadrant Académique et Étudiant sont inexistant(s) ou avec format incorrecte."});
          }
          if(result === "NO-AE")
          {
            this.setState({noteNotCompleteSend: "E-Mail Encadrant Académique est inexistant ou avec format incorrecte."});
          }
          if(result === "NO-ST")
          {
            this.setState({noteNotCompleteSend: "E-Mail Étudiant est inexistant ou avec format incorrecte."});
          }

          this.setState({
            openPopupShowAEDetails: false,
            openPopupAffectAE: false,
            openPopupSuccessAffectAE: true,
            selectedAEFullName: nom,
            loadAffectation: false,
            studentsByClass: this.state.studentsByClass.map(el => (el.id === id ? {...el, affected } : el))
            //academicEncadrants: this.state.academicEncadrants.map(el => (el.id === id ? {...el, nbrEncadrements } : el))
          });
        },
        (error) => {
        }
    );

  }

// INIT1
  render() {

    const {
      myClasses,
      checked,
      studentsByClass,
      loadStudentsByClass,
      loadAcademicEncadrants,
      loadAcademicEncadrantsForModif,
      loadAffectation,
      openPopupShowAEDetails,
      openPopupAffectAE,
      openPopupSuccessAffectAE,
      selectedStudentFullName,
      selectedAEFullName,
      selectedStudentId,
      selectedAcademicEncadrantId,
      academicEncadrant,
      academicEncadrants,
      selectedClass,
      tooltipNbrEncadrements,
      noteNotCompleteSend,
      showLoadingExcelIcon,
      privilegeKind,
      justifCancelAffectation,
      openPopupJustifyCancelAffectation,
      loadCancelAffectation,
      openPopupCancelAffectAE,
      openPopupSuccessCancelAffectAE,
      allSessionsLabel,
      selectedYear
    } = this.state;

    return (
        <CCard accentColor="danger">
          <CCardBody>
            <CRow>
              <CCol md="4"/>
              <CCol md="3">
                <br/>
                <Select  placeholder="Please Select an Academic Year"
                         defaultValue={{value: '2022', label: '2022', color: "#00B8D9"}}
                         value={allSessionsLabel.value}
                         components={animatedComponents}
                         options={allSessionsLabel}
                         onChange={this.onChangeSelectCEP}/>
              </CCol>
              <CCol md="5"/>
            </CRow>
            {
              selectedYear === 'EMPTY' &&
              <>
                <br/><br/>
              </>
            }
            {
              selectedYear !== 'EMPTY' && myClasses.length === 0 &&
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
                  <CCol md="11">
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
                               onClick={() => this.handleDownloadEncadrementStatus()}>
                        <CTooltip content="Télécharger l'État Encadrement de mon Option"
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
                        <CRow>
                          <CCol md="4"/>
                          <CCol md="3">
                            <span className="waitIcon"/>
                          </CCol>
                          <CCol md="5"/>
                        </CRow>
                        <br/><br/><br/><br/><br/>
                      </center>
                      :
                      <>
                        {
                          selectedClass && studentsByClass.length !== 0 &&
                          <>
                            <hr/>
                            <span className="redMark">Promotion</span> &nbsp; <span className="clignoteRed12">{selectedYear} - {Number(selectedYear)+1}</span>
                            <br/>
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
                                                                 onClick={() => this.handleConsultDetailsAcademicEncadrant(item.id, item.fullName)}>
                                                          <CTooltip content="Consulter Coordonnées Encadrants Académique"
                                                                    placement="top">
                                                            <CIcon content={freeSet.cilClipboard}/>
                                                          </CTooltip>
                                                        </CButton>
                                                        :
                                                        <>
                                                          {
                                                            (
                                                                (selectedStudentId !== item.id && loadAcademicEncadrants === true)
                                                                ||
                                                                loadAcademicEncadrants === false
                                                            ) &&
                                                            <>
                                                              <CButton shape="pill"
                                                                       color="dark"
                                                                       size="sm"
                                                                       aria-expanded="true"
                                                                       onClick={() => this.handleGotListAcademicEncadrantsByYear(item.id, item.fullName)}>
                                                                <CTooltip content="Affecter Encadrant Académique" placement="top">
                                                                  <CIcon content={freeSet.cilUser}/>
                                                                </CTooltip>
                                                              </CButton>
                                                            </>

                                                          }

                                                          {
                                                            selectedStudentId === item.id && loadAcademicEncadrants === true &&
                                                            <Spinner animation="grow" variant="dark"/>
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
                        {
                          selectedClass && studentsByClass.length === 0 &&
                          <>
                             <hr/>
                            <CRow>
                              <CCol md="11">
                                <center>
                                  <br/><br/>
                                  <CIcon name="cil-warning" style={{color: "#666666", width:"30px", height: "30px"}}/>
                                  <br/><br/>
                                  <span className="greyMarkCourrier">Désolé, il n'y a pas encore des Étudiants affectés à cette Classe.</span>
                                  <br/><br/><br/><br/>
                                </center>
                              </CCol>
                              <CCol md="1"/>
                            </CRow>
                          </>
                        }
                      </>
                }
                <br/><br/>
              </>
            }
          </CCardBody>


          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={openPopupShowAEDetails}
                  onClose={this.handleClosePopupShowAEDetails}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <CRow>
                <CCol md="8">
                  <span className="myModalTitleMarginTop">
                    Contact de l'Encadrant Académique de l'Étudiant(e)
                  </span>
                </CCol>
                <CCol md="4" className="colPadding">
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
                <CCol md="10">
                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Nom & Prénom:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {academicEncadrant.nom}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Identifiant:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {academicEncadrant.identifiant}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    E-Mail:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {
                        academicEncadrant.mail ?
                            <>{academicEncadrant.mail}</>
                            :
                            <>--</>
                      }
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Numéro Téléphone:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {
                        academicEncadrant.téléphone ?
                            <>{academicEncadrant.téléphone}</>
                            :
                            <>--</>
                      }
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Type:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {academicEncadrant.type === "P" && <>Permanant</>}
                      {academicEncadrant.type === "V" && <>Vacataire</>}
                      {academicEncadrant.type === "S" && <>Stagiaire</>}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Unité Pédagogique:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {
                        academicEncadrant.up ?
                            <>{academicEncadrant.up}</>
                            :
                            <>--</>
                      }
                    </CCol>
                  </CRow>
                </CCol>
                <CCol md="2">

                  <br/><br/>
                  <CTooltip content={tooltipNbrEncadrements} placement="bottom">
                    <Badge badgeContent={academicEncadrant.nbrEncadrements} showZero
                           color="secondary" style={{marginTop: "12px"}}>
                      <PersonIcon/>
                    </Badge>
                  </CTooltip>
                </CCol>
              </CRow>

              {
                loadAcademicEncadrantsForModif === false &&
                <center>
                  <br/><br/>
                  <CButton shape="pill"
                           color="warning"
                           size="sm"
                           aria-expanded="true"
                           onClick={() => this.handleGotListAcademicEncadrantsByYearForModif()}>
                    <CTooltip content="Modifier Affectation" placement="top">
                      <CIcon content={freeSet.cilReload}/>
                    </CTooltip>
                  </CButton>
                  &nbsp;&nbsp;
                  <CButton shape="pill"
                           color="dark"
                           size="sm"
                           aria-expanded="true"
                           onClick={() => this.handleOpenPopupJustifyDeleteAffectation()}>
                    <CTooltip content="Annuler Affectation" placement="top">
                      <CIcon content={freeSet.cilDelete}/>
                    </CTooltip>
                  </CButton>
                </center>
              }

              {
                loadAcademicEncadrantsForModif === true &&
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
                  keepMounted
                  open={openPopupAffectAE}
                  onClose={this.handleClosePopupAffectAE}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <CRow>
                <CCol md="6">
                  <span className="myModalTitleMarginTop">
                    Affectation de l'Encadrant Académique à l'Étudiant(e)
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
              </CRow>

              <hr/>
            </DialogTitle>
            <DialogContent>
              <CDataTable items={academicEncadrants}
                          fields={academicEncadrantsFields}
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
                                          (loadAffectation === false)
                                          ||
                                          (item.identifiant !== selectedAcademicEncadrantId && loadAffectation === true)
                                      ) &&
                                      <CButton shape="pill"
                                               color="primary"
                                               size="sm"
                                               aria-expanded="true"
                                               onClick={() => this.handleAffectAcademicEncadrant(item.identifiant, item.nom)}>
                                        <CTooltip content="Affecter"
                                                  placement="top">
                                          <CIcon content={freeSet.cilBadge}/>
                                        </CTooltip>
                                      </CButton>
                                    }
                                    {
                                      item.identifiant === selectedAcademicEncadrantId && loadAffectation === true &&
                                      <Spinner animation="grow" variant="primary"/>
                                    }
                                  </center>
                                </td>
                            ),
                          }}
              />
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
                  open={openPopupSuccessAffectAE}
                  onClose={this.handleClosePopupSuccessAffectAcademicEncadrant}
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
                l'Encadrant Académique&nbsp;
                <span className="infoMarkPopup">{selectedAEFullName}</span>
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
              <Button onClick={this.handleClosePopupSuccessAffectAcademicEncadrant} color="primary">
                Exit
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
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                Êtes-Vous sûr de vouloir annuler l'affection de
                <br/>
                l'Encadrant Académique&nbsp;
                <span className="infoMarkPopup">{academicEncadrant.nom}</span>
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
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
              <hr/>
            </DialogTitle>
            <DialogContent>
              <center>
                L'affectation de
                <br/>
                l'Encadrant Académique&nbsp;
                <span className="infoMarkPopup">{academicEncadrant.nom}</span>
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
    );
  }
}
