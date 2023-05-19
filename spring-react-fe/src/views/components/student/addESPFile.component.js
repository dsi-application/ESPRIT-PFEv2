import React, {Component} from "react";

import AuthService from "../../services/auth.service";

import StudentService from "../../services/student.service";

import EditableLabelFichePFEAddress from "../editableLabel/EditableLabelFichePFEAddress";
import EditableLabelFichePFENewMail from "../editableLabel/EditableLabelFichePFENewMail";
import EditableLabelFichePFEPhone from "../editableLabel/EditableLabelFichePFEPhone";

import "antd/dist/antd.css";
import {Panel} from "rsuite";
import {Button, Steps} from "antd";
import greyUpload from "../../images/greyUpload.jpg";
import Accordion from "@material-ui/core/Accordion";
import AccordionDetails from "@material-ui/core/AccordionDetails";
import AccordionSummary from "@material-ui/core/AccordionSummary";
import Typography from "@material-ui/core/Typography";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import "react-inputs-validation/lib/react-inputs-validation.min.css";
import ModalProblematic from "../modals/modalProblematic.js";
import ModalFunctionnality from "../modals/modalFunctionnality.js";
import ModalTechnology from "../modals/modalTechnology.js";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import Modal from "react-modal";

import {Textarea, Textbox} from "react-inputs-validation";

import Card from "react-bootstrap/Card";

import {NotificationContainer, NotificationManager} from 'react-notifications';
import "react-notifications/lib/notifications.css";

import {
  CAlert,
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCol,
  CContainer,
  CDataTable,
  CRow,
  CTooltip
} from "@coreui/react";

import PhoneInput from "react-phone-input-2";
import "react-phone-input-2/lib/style.css";
import {Link} from "react-router-dom";

import "../../css/style.css";

import CIcon from '@coreui/icons-react';
import {freeSet} from '@coreui/icons';

import Spinner from "react-bootstrap/Spinner";

import company from "../../images/company.jpg";
import Avatar from '@mui/material/Avatar';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';


const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;

const fields = [
  {
    key: 'supervFirstName',
    label: 'Nom',
    _style: {width: '20%'}
  },
  {
    key: 'supervLastName',
    label: 'Prénom',
    _style: {width: '20%'}
  },
  {
    key: 'supervPhoneNumber',
    label: 'Téléphone',
    _style: {width: '15%'}
  },
  {
    key: 'supervEmail',
    label: 'Email',
    _style: {width: '30%'}
  },
  {
    key: 'action',
    label: 'Actions',
    _style: {width: '15%'}
  }
]

const currentStudent = AuthService.getCurrentStudent();
const {Step} = Steps;

const animatedComponents = makeAnimated();

const customStyles = {
  content: {
    top: "50%",
    left: "60%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
  },
};

// Class
export default class AddESPFile extends Component {
  constructor(props) {
    super(props);

    this.onChange = this.onChange.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleModifySupervisorProfile = this.handleModifySupervisorProfile.bind(this);
    this.handleCloseSupervisorProfile = this.handleCloseSupervisorProfile.bind(this);

    this.handleChangeUTBUAddress = this.handleChangeUTBUAddress.bind(this);
    this.handleChangeUTBUNewMail = this.handleChangeUTBUNewMail.bind(this);
    this.handleChangeUTBUPhone = this.handleChangeUTBUPhone.bind(this);

    this.onChangeProjectTitle = this.onChangeProjectTitle.bind(this);
    this.onChangeProjectDescription = this.onChangeProjectDescription.bind(
        this
    );

    this.replaceModalItemProblematic = this.replaceModalItemProblematic.bind(
        this
    );
    this.saveModalDetailsProblematic = this.saveModalDetailsProblematic.bind(
        this
    );

    this.replaceModalItemFunctionnality = this.replaceModalItemFunctionnality.bind(
        this
    );
    this.saveModalDetailsFunctionnality = this.saveModalDetailsFunctionnality.bind(
        this
    );

    this.replaceModalItemTechnology = this.replaceModalItemTechnology.bind(
        this
    );
    this.saveModalDetailsTechnology = this.saveModalDetailsTechnology.bind(
        this
    );

    this.deleteItemTechnology = this.deleteItemTechnology.bind(this);
    this.deleteItemSupervisor = this.deleteItemSupervisor.bind(this);

    this.closeModalVerifyTechDuplication = this.closeModalVerifyTechDuplication.bind(
        this
    );
    this.closeModalGrantAddTechnology = this.closeModalGrantAddTechnology.bind(this);
    this.closeModalDenyAddFonctionnalite = this.closeModalDenyAddFonctionnalite.bind(this);
    this.closeModalDenyAddProblematique = this.closeModalDenyAddProblematique.bind(this);
    this.closeModalSuccessConfirmFichePFE = this.closeModalSuccessConfirmFichePFE.bind(
        this
    );
    this.closeModalOk = this.closeModalOk.bind(this);
    this.closeModalSuccessConfirmSauvegardFichePFE = this.closeModalSuccessConfirmSauvegardFichePFE.bind(
        this
    );

    this.onChangeSelectProjectTechnology = this.onChangeSelectProjectTechnology.bind(
        this
    );
    this.closeModalErrorConfirmSauvegardFichePFE = this.closeModalErrorConfirmSauvegardFichePFE.bind(this);
    this.closeModalAddSupervisorProfile = this.closeModalAddSupervisorProfile.bind(
        this
    );
    this.addSupervisorProfile = this.addSupervisorProfile.bind(this);
    this.changeSupervisorProfile = this.changeSupervisorProfile.bind(this);
    this.addProjectFunctionnality = this.addProjectFunctionnality.bind(this);

    this.deposerMaFichePFE = this.deposerMaFichePFE.bind(this);
    this.addPairHandler = this.addPairHandler.bind(this);

    this.verifyExistEncadrantEntreprise = this.verifyExistEncadrantEntreprise.bind(this);
    this.verifyExistEncadrantEntreprisePopup = this.verifyExistEncadrantEntreprisePopup.bind(this);
    this.handleCloseSuccessModifySupervisorProfile = this.handleCloseSuccessModifySupervisorProfile.bind(this);

    this.selectFile = this.selectFile.bind(this);
    this.uploadGanttDiagram = this.uploadGanttDiagram.bind(this);
    this.closeModalSuccessUpload = this.closeModalSuccessUpload.bind(this);

    this.state = {
      content: "",
      currentStudent: {},
      step: 0,
      expanded: false,

      userToBeUpdated: {},
      uTBUNewMail: "",
      verifyuTBUNewMail: "init",
      uTBUAddress: "",
      verifyuTBUAddress: "init",
      verifyuTBUAddressNull: "init",
      uTBUPhone: "",
      verifyuTBUPhone: "init",
      verifyuTBUPhoneNull: "init",
      verifyuTBUPhoneLimit: "init",

      projectTitle: "",
      projectDescription: "",
      projectProblematic: "",
      hasProjectProblematicError: true,
      problemItem: [{probLibelle: ""}],
      requiredProblemItem: 0,
      validateProblematicData: false,

      projectSupervisorFirstName: "",
      hasSupervisorFirstNameError: true,
      projectSupervisorLastName: "",
      hasSupervisorLastNameError: true,
      projectSupervisorPhoneNumber: "",
      hasSupervisorPhoneNumberCheck: false,
      projectSupervisorEmail: "",
      hasSupervisorEmailError: true,
      supervisorItem: [],
      requiredSupervisorItem: "",
      validateSupervisorData: false,
      successAddSupervProfile: false,

      projectFunctionnalityLibelle: "",
      hasProjectFunctionnalityLibelleError: true,
      projectFunctionnalityDescription: "",
      functionnalityItem: [{funcLibelle: "", funcDescription: ""}],
      requiredFunctionnalityItem: 0,
      validateFunctionnalityData: false,

      allTechnologies: [{value: "", label: "", color: ""}],
      selectedTechnologies: [{value: "", label: ""}],
      projectTechnologyLibelle: "",
      technologyItem: [{techLibelle: ""}],
      requiredTechnologyItem: 0,
      oldTechLibelle: "",
      listSelectedLibelleTechnologies: [],
      existTech: false,
      grantAddTech: false,

      projectCompany: {},

      successConfirmFichePFE: false,
      successConfirmSauvegarderFichePFE: false,
      errorConfirmSauvegarderFichePFE: false,
      showDeposeButton: true,
      showSauvegardeButton: false,

      pairId: "",
      verifyPairField: "",
      fichePFEStatus: "",

      conventionDateConvenion: "",
      conventionTreat: "",
      studentFullName: "",

      loadingSaveFichePFE: false,
      loadingConfirmFichePFE: false,
      currentDay: "",

      verifExistEncadEntrep: false,
      verifExistEncadEntrepPopup: false,
      successOpenSupervisorProfile: false,
      supervFN: "",
      supervLN: "",
      supervTl: "",
      supervMl: "",
      validateSupervData: false,
      hasSupervFNError: true,
      hasSupervLNError: true,
      hasSupervMlError: true,
      oldSupervMlPass: "",
      hasSupervTlCheck: false,
      successModifySupervisorProfile: false,
      libEtatFiche: "",
      lol1: false,
      lol2: true,
      lol3: 'azerty',
      verifInternalDuplication: 'INIT',
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      successUpload: false,
      showUploadNewspaperButton: false,
      diagramGanttFullPath: '',
      denyAddFonctionnalite: false,
      denyAddProblematic: false
    };
    // LOL123

    var requestefp = new XMLHttpRequest();
    requestefp.open(
        "GET",
        API_URL_MESP + "etatFicheByStudent/" + currentStudent.id,
        false
    ); // return axios.get(API_URL + 'user/' + code);
    requestefp.send(null);
    this.state.libEtatFiche = requestefp.responseText;

    var requesttrtfp = new XMLHttpRequest();
    requesttrtfp.open(
        "GET",
        API_URL_STU + "traitementFichePFETypeEtat/" + currentStudent.id,
        false
    ); // return axios.get(API_URL + 'user/' + code);
    requesttrtfp.send(null);


    // console.log('------------> libEtatFiche: ' + this.state.libEtatFiche);
    let result = requesttrtfp.responseText;
    if (result !== "") {
      let treatFichePFETypeEtat = JSON.parse(requesttrtfp.responseText);

      // LOad CompanyDto by Student
      var requestb = new XMLHttpRequest();
      requestb.open(
          "GET",
          API_URL_MESP + "companyDtoByStudent/" + currentStudent.id,
          false
      );
      requestb.send(null);
      let companyDto = JSON.parse(requestb.responseText);

      if (
          this.state.libEtatFiche === "REFUSEE"
          ||
          (
              this.state.libEtatFiche === "ANNULEE"
              &&
              treatFichePFETypeEtat.typeTrtFiche === "02"
              &&
              treatFichePFETypeEtat.typeTrtConv === "Non"
          )
      ) {
        // Supervisors
        // console.log('----------------------LOL123------------> convention: ' + convention.companyDto.designation);
        var addedSupervisors = this.state.supervisorItem;
        for (const [i] of companyDto.responsiblesDto.entries()) {
          const newObj = {
            oldSupervMlPass: companyDto.responsiblesDto[i].email,
            supervFirstName: companyDto.responsiblesDto[i].firstName,
            supervLastName: companyDto.responsiblesDto[i].lastName,
            supervPhoneNumber: companyDto.responsiblesDto[i].numTelephone,
            supervEmail: companyDto.responsiblesDto[i].email,
          };
          addedSupervisors.push(newObj);
          this.setState({supervisorItem: addedSupervisors});
        }
      }
    }

    let currentDt = new Date();
    this.state.currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();

    var requestfp = new XMLHttpRequest();
    requestfp.open(
        "GET",
        API_URL_STU + "fichePFEStatus/" + currentStudent.id,
        false
    );
    requestfp.send(null);

    this.state.fichePFEStatus = requestfp.responseText;
    // console.log('-------------------------------------------- Status: ' + this.state.fichePFEStatus);

    var requestfc = new XMLHttpRequest();
    requestfc.open(
        "GET",
        API_URL_MESP + "conventionDateStatus/" + currentStudent.id,
        false
    );
    requestfc.send(null);
    let convention = JSON.parse(requestfc.responseText);
    this.state.conventionDateConvenion = convention.dateConvention;
    this.state.conventionTreat = convention.traiter;

    // console.log('---conv------ convention entity: ' + this.state.conventionDateConvenion + " - " + this.state.conventionTreat);

    // Load Convention
    if (this.state.conventionDateConvenion !== undefined) {
      var requestconv = new XMLHttpRequest();
      requestconv.open(
          "GET",
          API_URL_MESP + "conventionDto/" + currentStudent.id,
          false
      ); // return axios.get(API_URL + 'user/' + code);
      requestconv.send(null);
      this.state.convention = JSON.parse(requestconv.responseText);
      // console.log('---123---------init-------azerty------ convention entity: ' + this.state.convention.nomSociete);

      let companyDto = this.state.convention.companyDto;
      this.state.projectCompany = companyDto;
      // console.log('---12345---------init-------SARS------ convention entity: ' + companylol.designation);
    }

    // Load the user to be updated
    var requestb = new XMLHttpRequest();
    requestb.open(
        "GET",
        API_URL_MESP + "student/" + currentStudent.id,
        false
    ); // return axios.get(API_URL + 'user/' + id);
    requestb.send(null);
    this.state.userToBeUpdated = JSON.parse(requestb.responseText);

    // console.log('---------------------------------------------------------> LOL2021: ' + this.state.userToBeUpdated.idEt);

    if (this.state.userToBeUpdated.adresseet === null) {
      this.state.uTBUAddress = "--";
    } else {
      this.state.uTBUAddress = this.state.userToBeUpdated.adresseet;
    }

    if (this.state.userToBeUpdated.emailet === null) {
      this.state.emailet = "--";
    } else {
      this.state.uTBUNewMail = this.state.userToBeUpdated.emailet;
    }

    if (this.state.userToBeUpdated.telet === null) {
      this.state.telet = "--";
    } else {
      this.state.uTBUPhone = this.state.userToBeUpdated.telet;
    }

    // console.log('---------------------------------------------------> 1: ' + currentStudent.id);
    // console.log(requestb.responseText);
    // console.log('---------------------------------------------------> 2');
    // console.log(this.state.userToBeUpdated);
    // console.log('---------------------------------------------------> 3');

    // console.log('-------------------------------------> uTBUPhone: ' + this.state.uTBUPhone);

    //  Load list of technologies
    let strIntoObjTech = [];
    var requestTech = new XMLHttpRequest();
    requestTech.open(
        "GET",
        API_URL_MESP + "allTechnologies",
        false
    ); // `false` makes the request synchronous
    requestTech.send(null);
    // console.log(requestTech);
    strIntoObjTech = JSON.parse(requestTech.responseText);
    // console.log("---------****************************************************-- 2 ---> " + strIntoObjTech);
    strIntoObjTech.forEach((tech) => {
      // console.log("----------- 3 ---> " + tech);
      this.state.allTechnologies.push({
        value: tech,
        label: tech,
        color: "#00B8D9",
      });
    });

    if (this.state.allTechnologies[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.allTechnologies.splice(0, 1);
    }

    if (this.state.selectedTechnologies[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.selectedTechnologies.splice(0, 1);
    }

  }

  handleModifySupervisorProfile(index, item) {
    // console.log('------------------------------ USP 1: ' + index);
    // console.log('------------------------------ USP 2: ', item);

    this.setState({
      successOpenSupervisorProfile: true,
      supervFN: item.supervFirstName,
      supervLN: item.supervLastName,
      supervTl: item.supervPhoneNumber,
      supervMl: item.supervEmail,
      oldSupervMlPass: item.supervEmail,
    })

  }

  handleCloseSupervisorProfile() {
    this.setState({
      successOpenSupervisorProfile: false,
      supervFN: "",
      supervLN: "",
      supervTl: "",
      supervMl: "",
      oldSupervMlPass: ""
    })
  }

  addPairHandler(e) {
    this.setState({pairId: e.target.value});
    if (e.target.value.length !== 0 && e.target.value.length !== 10) {
      this.setState({verifyPairField: "NO", studentFullName: ""});
    }
    if (e.target.value.length === 0 || e.target.value.length === 10) {
      this.setState({verifyPairField: "YES"});
    }
    // console.log('---------------------------> SARS: ' + this.state.verifyPairField);

    if (e.target.value.length === 10) {
      // console.log('----------------PAIR--------------------- My Pair FullName 1: ' + this.state.pairId);

      var requestb = new XMLHttpRequest();
      requestb.open(
          "GET",
          API_URL_STU + "studentFullName/" + e.target.value,
          false
      ); // return axios.get(API_URL + 'user/' + code);
      requestb.send(null);
      let myPairFN = requestb.responseText;

      // console.log('------------------PAIR-FULL-1');
      // console.log(pair);
      // console.log('------------------PAIR-FULL-2: ', myPairFN);
      if (e.target.value === currentStudent.id) {
          this.setState({
            studentFullName:
                "Your Pair cannot be YOU. Please Verify your entry !.",
          });
          // console.log('------------------PAIR-NO');
        } else {
          if (myPairFN === "") {
            this.setState({
              studentFullName:
                  "There's no Student with that ID. Please Verify your entry !.",
            });
            // console.log('------------------PAIR-NO');
          } else {
            this.setState({studentFullName: myPairFN});
            // console.log('------------------PAIR-YES');
          }
          // console.log('------------------PAIR-YES');
        }


      // console.log('------------------PAIR------------------- My Pair FullName 2: ' + this.state.studentFullName + " - " + myPairFN);
    }
  }

  toggleValidatingAddSupervisorProfile(validateSupervisorData) {
    this.setState({validateSupervisorData});
  }

  toggleValidatingAddSupervProfile(validateSupervData) {
    this.setState({validateSupervData});
  }

  toggleValidatingAddFunctionnality(validateFunctionnalityData) {
    this.setState({validateFunctionnalityData});
  }


  handleChangeUTBUNewMail = (event) => {
    this.setState({uTBUNewMail: event.target.value});
    // console.log('NewMail -------TextScroller.js---------> ' + this.state.uTBUNewMail);

    let entry = event.target.value;
    let ad = "--";
    let nt = "--";
    let value = "";
    if (entry.includes("<br>")) {
      value = entry.replaceAll("<br>", "");
    } else {
      value = entry;
    }

    if (value.includes("@")) {
      AuthService.createFichePFEP1(currentStudent.id, encodeURIComponent(value), encodeURIComponent(ad), nt).then(
          (response) => {
            // console.log('NewMail -------------- Edited om: ' + currentStudent.id + " - " + value + " - " + ad + " - " + nt);
          },
          (error) => {
            // console.log('NewMail -------------- Error om: ' + currentStudent.id + " - " + value + " - " + ad + " - " + nt);
          }
      );
      this.setState({verifyuTBUNewMail: "yes"});
    }
    if (!value.includes("@")) {
      // console.log('NewMail --------------------- ERROR');
      this.setState({verifyuTBUNewMail: "no"});
    }
    // console.log(' Result - NewMail -------------------->' + this.state.verifyuTBUNewMail);
  };

  handleChangeUTBUAddress = (event) => {
    this.setState({uTBUAddress: event.target.value});
    // console.log('Address -------TextScroller.js---------> ' + this.state.uTBUAddress);
    let value = "--";
    let ad = event.target.value;
    let nt = "--";

    let pureAddressValue = "";
    if (ad.includes("<br>")) {
      pureAddressValue = ad.substring(0, ad.lastIndexOf("<br>"));
    } else {
      pureAddressValue = ad;
    }

    if (pureAddressValue.length === 0) {
      // console.log('-----------------------------> EMPTY-PHONE: ');
      this.setState({uTBUAddress: "", verifyuTBUAddressNull: "yes"});
    } else {
      AuthService.createFichePFEP1(currentStudent.id, encodeURIComponent(value), encodeURIComponent(pureAddressValue), nt).then(
          (response) => {
            // console.log('Address -------------- Edited om: ' + currentStudent.id + " - " + value + " - " + pureAddressValue + " - " + nt);
          },
          (error) => {
            // console.log('Address -------------- Error om: ' + currentStudent.id + " - " + value + " - " + pureAddressValue + " - " + nt);
          }
      );
      this.setState({
        verifyuTBUAddress: "yes",
        uTBUAddress: pureAddressValue,
        verifyuTBUAddressNull: "no",
      });
    }

    // console.log(' Result - Address -------------------->' + this.state.verifyuTBUAddress);
  };

  handleChangeUTBUPhone = (event) => {
    this.setState({uTBUPhone: event.target.value});

    let value = "--";
    let ad = "--";
    let nt = event.target.value;
    let numbers = "0123456789";
    let purePhoneValue = "";

    if (nt.includes("<br>")) {
      purePhoneValue = nt.substring(0, nt.lastIndexOf("<br>"));
    } else {
      purePhoneValue = nt;
    }

    // console.log('Phone - INIT =========================================> ' + nt + " - " + purePhoneValue);

    if (purePhoneValue.length === 0) {
      //console.log('-----------------------------> EMPTY-PHONE: ');
      this.setState({
        uTBUPhone: "",
        verifyuTBUPhone: "yes",
        verifyuTBUPhoneNull: "yes",
        verifyuTBUPhoneLimit: "no",
      });
    } else {
      // console.log('-----------------------------> FULL-PHONE: ');
      if (purePhoneValue.length < 8) {
        // console.log('-----------------------------> < 8');
        for (var i = 0; i < purePhoneValue.length; i++) {
          if (numbers.indexOf(purePhoneValue[i]) > -1) {
            // console.log('Phone OK ------ssd--> ' + purePhoneValue + " - " + newText);
            this.setState({
              verifyuTBUPhone: "yes",
              verifyuTBUPhoneLimit: "yes",
              uTBUPhone: purePhoneValue,
              verifyuTBUPhoneNull: "no",
            });
          } else {
            // console.log('Phone NO --------> ' + purePhoneValue);
            this.setState({
              verifyuTBUPhone: "no",
              uTBUPhone: purePhoneValue,
              verifyuTBUPhoneNull: "no",
            });
          }
        }
      } else {
        for (var j = 0; j < purePhoneValue.length; j++) {
          if (numbers.indexOf(purePhoneValue[j]) > -1) {
            // console.log('Phone OK --------> ' + purePhoneValue + " - " + newText);

            AuthService.createFichePFEP1(currentStudent.id, encodeURIComponent(value), encodeURIComponent(ad), purePhoneValue).then(
                (response) => {
                  // console.log('Phone -------------- Edited om: ' + currentStudent.id + " - " + value + " - " + ad + " - " + purePhoneValue);
                },
                (error) => {
                  // console.log('Phone -------------- Error om: ' + currentStudent.id + " - " + value + " - " + ad + " - " + purePhoneValue);
                }
            );
            this.setState({
              verifyuTBUPhone: "yes",
              uTBUPhone: purePhoneValue,
              verifyuTBUPhoneNull: "no",
              verifyuTBUPhoneLimit: "no",
            });
          } else {
            // console.log('Phone NO --------> ' + purePhoneValue);
            this.setState({
              verifyuTBUPhone: "no",
              uTBUPhone: purePhoneValue,
              verifyuTBUPhoneNull: "no",
              verifyuTBUPhoneLimit: "no",
            });
          }
        }
      }
    }

    // console.log(' Result -Phone -------------------->' + this.state.verifyuTBUPhone + " - " + this.state.uTBUPhone + " - " + this.state.verifyuTBUPhoneNull);
  };

  onChangeSelectProjectTechnology = (selectedTechOption) => {
    if (selectedTechOption) {
      var selectedList = selectedTechOption.values();
      let newSelectedTechs = [];
      this.setState({listSelectedLibelleTechnologies: []});

      this.state.selectedTechnologies = [{value: "", label: ""}];
      if (this.state.selectedTechnologies[0].value.trim() === "") {
        // console.log(' 1 --- Clear');
        this.state.selectedTechnologies.splice(0, 1);
      }
      let allNSTechs = this.state.selectedTechnologies;
      let virtualSTLs = this.state.listSelectedLibelleTechnologies;
      // console.log("------------- Select On Change - 0: " + allNSTechs.length);
      for (let sl of selectedList) {
        //console.log('aze=============================================' + sl.label);

        virtualSTLs.push(sl.label);
        newSelectedTechs.push(sl.label);
        const selectedObj = {value: sl.value, label: sl.label};
        allNSTechs.push(selectedObj);
      }
      // console.log('------------- Select On Change - 1: ' + allNSTechs.length + " - " + virtualSTLs.length);

      const uniqueListSelectedLibelleTechnologies = Array.from(
          new Set(newSelectedTechs)
      );

      this.setState({
        listSelectedLibelleTechnologies: uniqueListSelectedLibelleTechnologies,
        selectedTechnologies: allNSTechs,
      });
    }

    /*for (const [i, value] of this.state.selectedTechnologies.entries())
        {
            console.log('Iterate -----> ' + this.state.selectedTechnologies[i].value + " - " + this.state.selectedTechnologies[i].label );
        }

        for (let tech of this.state.selectedTechnologies)
        {
            console.log('***** Problem ------------- Unit: ' + tech.label)
        }*/

    /*
        console.log('***** Technology ------------- Unit 1')
        for (let a of uniqueNames)
        {
            console.log(a);
        }
        console.log('***** Technology ------------- Unit 2')
        */
  };

  closeModalVerifyTechDuplication() {
    // console.log(' Verify Tech Duplication -------------------- .');
    this.setState({
      existTech: false,
    });
  }

  closeModalSuccessConfirmFichePFE() {
    window.location.reload();
  }

  closeModalOk(e) {
    this.setState({
      successConfirmFichePFE: false,
    });
  }

  closeModalSuccessConfirmSauvegardFichePFE() {
    // console.log(' End Step -------------------- .');
    this.setState({
      successConfirmSauvegarderFichePFE: false,
      showSauvegardeButton: false
    });
    window.location.reload();
  }

  closeModalErrorConfirmSauvegardFichePFE() {
    // console.log(' End Step -------------------- .');
    this.setState({
      errorConfirmSauvegarderFichePFE: false,
    });
    window.location.reload();
  }

  closeModalAddSupervisorProfile() {
    // console.log(' Add Supervisor Profile -------------------- .');
    this.setState({
      successAddSupervProfile: false,
    });
  }

  closeModalDenyAddFonctionnalite()
  {
    this.setState({denyAddFonctionnalite: false});
  }

  closeModalDenyAddProblematique()
  {
    this.setState({denyAddProblematic: false});
  }

  closeModalGrantAddTechnology() {
    // console.log(' Grant Add Tech -------------------- .');
    this.setState({
      grantAddTech: false,
    });

    let libelleTechnology = this.state.projectTechnologyLibelle;
    // console.log(" Catch Tech Duplication --------------------- YES-ADDING : " + libelleTechnology);
    let techList = this.state.technologyItem.slice(); //creates the clone of the state
    const obj = {techLibelle: libelleTechnology};
    techList.push(obj);

    this.setState({technologyItem: techList});

    AuthService.addTechnology(encodeURIComponent(libelleTechnology)).then(
        (response) => {
          // console.log('Add + Update ------------- SE 1: ' + response.data.idTechnologie);
          // console.log('Add + Update ------------- SE 2: ' + response.data.name);

          let techAddedList = this.state.allTechnologies.slice(); //creates the clone of the state
          const addedObj = {
            value: response.data.idTechnologie,
            label: response.data.name,
            color: "#00B8D9",
          };
          techAddedList.push(addedObj);

          // console.log('Add + Update ------------- 1: ' + this.state.allTechnologies.length);
          this.setState({
            allTechnologies: techAddedList,
            projectTechnologyLibelle: ""
          });
          // console.log('Add + Update ------------- 2: ' + this.state.allTechnologies.length);
        },
        (error) => {
          this.setState({
            content:
                (error.response && error.response.data) ||
                error.message ||
                error.toString(),
          });
        }
    );
    // console.log(this.state.technologyItem);
    // console.log(' 2 ----------------------- New Added Technology: ' + techList.length + " - " + this.state.technologyItem.length);
  }

  onChange = (nextStep) => {
    this.setState({
      step: nextStep < 0 ? 0 : nextStep > 3 ? 3 : nextStep,
    });
  };

  handleChange = (panel) => (event, isExpanded) => {
    this.setState({
      expanded: isExpanded ? panel : false,
    });
  };

  onChangeProjectTitle(e) {
    this.setState({
      projectTitle: e.target.value,
    });
  }

  onChangeProjectDescription(e) {
    this.setState({
      projectDescription: e.target.value,
    });
  }

  replaceModalItemProblematic(index) {
    this.setState({
      requiredProblemItem: index,
    });
  }

  replaceModalItemFunctionnality(index) {
    this.setState({
      requiredFunctionnalityItem: index,
    });
  }

  replaceModalItemTechnology(index, oldTechLib) {
    this.setState({
      requiredTechnologyItem: index,
      oldTechLibelle: oldTechLib,
    });
  }

  saveModalDetailsProblematic(item) {
    // console.log('------------------------------> probLibelle of selected Item 1: ' + item.probLibelle);
    const requiredProblemItem = this.state.requiredProblemItem;
    // console.log('------------------------------> requiredProblemItem: ' + requiredProblemItem);
    let tempbrochure = this.state.problemItem;
    tempbrochure[requiredProblemItem] = item;
    // console.log('------------------------------> probLibelle of selected Item 2: ' + tempbrochure[requiredProblemItem].probLibelle);
    this.setState({problemItem: tempbrochure, requiredProblemItem: 0});
    // console.log('------------------------------> Result');
    // console.log(this.state.problemItem);
  }

  saveModalDetailsFunctionnality(item) {
    const requiredFunctionnalityItem = this.state.requiredFunctionnalityItem;
    let tempbrochure = this.state.functionnalityItem;
    tempbrochure[requiredFunctionnalityItem] = item;
    this.setState({
      functionnalityItem: tempbrochure,
      requiredFunctionnalityItem: 0,
    });
  }

  saveModalDetailsTechnology(item) {
    let listLibTechs = [];
    for (let ut of this.state.allTechnologies) {
      listLibTechs.push(ut.label);
    }

    if (listLibTechs.includes(item.techLibelle)) {
      // console.log(" Catch Tech Duplication --------------------- NO : " + item.techLibelle);
      this.setState({existTech: true});
    } else {
      const requiredTechnologyItem = this.state.requiredTechnologyItem;
      let tempbrochure = this.state.technologyItem;
      tempbrochure[requiredTechnologyItem] = item;
      this.setState({
        technologyItem: tempbrochure,
        requiredTechnologyItem: 0,
      });

      // console.log('Update + Update ------------- Verify: ' + this.state.oldTechLibelle + " - " + item.techLibelle);
      // console.log(" Catch Tech Duplication --------------------- YES : " + item.techLibelle);

      AuthService.updateTechnology(
          encodeURIComponent(this.state.oldTechLibelle),
          encodeURIComponent(item.techLibelle)
      ).then(
          (response) => {
            // console.log('Delete + Update ------------- !!! ');
            let allTechs = this.state.allTechnologies.slice();
            for (const [i] of allTechs.entries()) {
              // console.log('Delete + Update ------------- EQUIVALENT - 1: ' + allTechs.length + " - " + allTechs[i].label.trim() + " - " + this.state.oldTechLibelle );
              if (allTechs[i].label.trim() === this.state.oldTechLibelle) {
                allTechs.splice(i, 1);

                // console.log('Delete + Update ------------- EQUIVALENT - 2: ' + item.techLibelle);
                this.setState({allTechnologies: allTechs});
              }
            }

            let allNTechs = this.state.allTechnologies;
            // console.log('Delete + Update ------------- EQUIVALENT - 3: ' + allNTechs.length);
            /*
                  for (const [i] of this.state.allTechnologies.entries())
                  {
                      console.log('Iterate Before -----> ' + this.state.allTechnologies[i].value + " - " + this.state.allTechnologies[i].label );
                  }
                  */

            const updatedObj = {
              value: response.data,
              label: item.techLibelle,
              color: "#00B8D9",
            };
            allNTechs.push(updatedObj);
            this.setState({allTechnologies: allNTechs});
            // console.log('Delete + Update ------------- EQUIVALENT - 4: ' + allNTechs.length);
            /*
                  for (const [i] of this.state.allTechnologies.entries())
                  {
                      console.log('Iterate After --------> ' + this.state.allTechnologies[i].value + " - " + this.state.allTechnologies[i].label );
                  }
                  */
          },
          (error) => {
            this.setState({
              content:
                  (error.response && error.response.data) ||
                  error.message ||
                  error.toString(),
            });
          }
      );
    }
  }

  deleteItemProblematic(index) {
    // console.log('--------------------- Delete PROB Item with index: ' + index);
    let tempBrochure = this.state.problemItem;
    // console.log('--------------------- Delete PROB Item with libelle: ' + tempBrochure[index].probLibelle);
    // console.log('--------------------- Delete PROB Item Size 1: ' + tempBrochure.length);
    tempBrochure.splice(index, 1);
    // console.log('--------------------- Delete PROB Item Size 2: ' + tempBrochure.length);
    this.setState({problemItem: tempBrochure});
  }

  deleteItemFunctionnality(index) {
    let tempBrochure = this.state.functionnalityItem;
    tempBrochure.splice(index, 1);
    this.setState({functionnalityItem: tempBrochure});
  }

  deleteItemTechnology(index, libelleTechnology) {
    let tempBrochure = this.state.technologyItem;
    tempBrochure.splice(index, 1);
    this.setState({technologyItem: tempBrochure});

    AuthService.deleteTechnologyByName(encodeURIComponent(libelleTechnology)).then(
        (response) => {
          // console.log('Delete + Update ------------- !!! ');
          let alltechs = this.state.allTechnologies.slice();
          /*
                  for (const [i] of this.state.allTechnologies.entries())
                  {
                      console.log('Delete + Update ------------- Before: ' + this.state.allTechnologies.length + " - " + this.state.allTechnologies.[i].value + " - " + this.state.allTechnologies.[i].label);
                  }
                  */

          for (const [i] of alltechs.entries()) {
            if (alltechs[i].label.trim() === libelleTechnology) {
              alltechs.splice(i, 1);
              this.setState({allTechnologies: alltechs});
            }
          }

          /*
                  for (const [i] of this.state.allTechnologies.entries())
                  {
                      console.log('Delete + Update ------------- After: ' + this.state.allTechnologies.length + " - " + this.state.allTechnologies.[i].value + " - " + this.state.allTechnologies.[i].label);
                  }
                  */
          this.setState({showModalPassStep2to3: true});
        },
        (error) => {
          this.setState({
            content:
                (error.response && error.response.data) ||
                error.message ||
                error.toString(),
          });
        }
    );
  }

  deleteItemSupervisor(index, supervEmailToBeDeleted) {
    let tempBrochure = this.state.supervisorItem;
    tempBrochure.splice(index, 1);
    this.setState({supervisorItem: tempBrochure});

    AuthService.deleteSupervisorCompanyByEmail(supervEmailToBeDeleted).then(
        (response) => {
          // console.log('DONE : Delete Supervisor');
        },
        (error) => {
          // console.log('ERROR : Delete Supervisor');
        }
    );
  }

  addProjectProblematic(libelleProblematic) {
    /*e.preventDefault();
        this.toggleValidatingAddProblematic(true);

        const
        {
            hasProjectProblematicError
        } = this.state;

        if(!hasProjectProblematicError)
        {*/
    let probList = this.state.problemItem.slice(); //creates the clone of the state
    const obj = {probLibelle: libelleProblematic};

    console.log(' 1 ---------2306------------- HI-7: ' + probList.length);
    if(probList.length < 6)
    {
      console.log(' 1 ---------2306------- YES');
      probList.push(obj);
      this.setState({problemItem: probList, projectProblematic: ""});
    }
    else
    {
      console.log(' 1 ---------2306------- NO');
      this.setState({denyAddProblematic: true, projectProblematic: ""});
    }

    //}
    // console.log(this.state.problemItem);
    // console.log(' 2 ----------------------- New Added Project Problematic: ' + probList.length + " - " + this.state.problemItem.length);
  }

  addProjectFunctionnality(e)
  {

    // console.log(' 1 ---------------------- HI-1');
    e.preventDefault();
    // console.log(' 1 ---------------------- HI-2');
    this.toggleValidatingAddFunctionnality(true);
    // console.log(' 1 ---------------------- HI-3');
    // console.log('---------------------------------> TextScroller.js: ' + hasProjectFunctionnalityLibelleError + " - " + hasProjectFunctionnalityDescriptionError);
    // console.log(' 1 ---------------------- HI-4: ' + this.state.hasProjectFunctionnalityLibelleError + " - " + this.state.projectFunctionnalityLibelle.length);

    const {hasProjectFunctionnalityLibelleError} = this.state;

    if (!hasProjectFunctionnalityLibelleError) {
      // console.log(' 1 ---------------------- HI-5');
      // console.log(' 1 ----------------------- New Added Functionnality: ' + this.state.problemItem.length);

      let funcList = this.state.functionnalityItem.slice(); //creates the clone of the state
      const obj = {
        funcLibelle: this.state.projectFunctionnalityLibelle,
        funcDescription: this.state.projectFunctionnalityDescription,
      };

      console.log(' 1 ---------2306------------- HI-7: ' + funcList.length);
      if(funcList.length < 11)
      {
        console.log(' 1 ---------2306------- YES');
          funcList.push(obj);
          this.setState({
            functionnalityItem: funcList,
            projectFunctionnalityLibelle: "",
            projectFunctionnalityDescription: "",
          });
      }
      else
      {
        console.log(' 1 ---------2306------- NO');
        this.setState({
          denyAddFonctionnalite: true,
          projectFunctionnalityLibelle: "",
          projectFunctionnalityDescription: "",
        });
      }

      // this.state.hasProjectFunctionnalityLibelleError = true;


      // console.log(this.state.functionnalityItem);
      // console.log(' 2 ----------------------- New Added Functionnality: ' + funcList.length + " - " + this.state.functionnalityItem.length);
      // console.log(' 1 ---------------------- HI-6: ' + this.state.hasProjectFunctionnalityLibelleError);
    }
    // console.log(' 1 ---------------------- HI-7: ' + this.state.hasProjectFunctionnalityLibelleError);
  }

  deposerMaFichePFE() {
    // console.log(' 1 ----------------------- SauvegarderMaFichePFE for student: ' + this.state.userToBeUpdated.idEt);
    this.setState({loadingSaveFichePFE: true})
    let listOfProblematics = [];
    for (let pi of this.state.problemItem) {
      listOfProblematics.push(pi.probLibelle);
    }

    listOfProblematics.splice(0, 1);
    /*------------------------------------------------------------------------------------------------------------*/
    let listOfFunctionnalities = [];
    for (let fi of this.state.functionnalityItem) {
      let funcCombination = fi.funcLibelle + "20espdsi21" + fi.funcDescription;
      listOfFunctionnalities.push(funcCombination);
    }
    listOfFunctionnalities.splice(0, 1);

    /*------------------------------------------------------------------------------------------------------------*/
    let listOfSupervisors = [];
    for (let sl of this.state.supervisorItem) {
      let supervNumTel = sl.supervPhoneNumber;
      if (supervNumTel === null) {
        supervNumTel = '--';
      }
      let supervCombination = "FN-" + sl.supervFirstName +
        "LN-" + sl.supervLastName +
        "NT-" + supervNumTel +
        "EM-" + sl.supervEmail;
      // console.log('-----------------------> Add Superv Unit: ' + sl.supervEmail);
      listOfSupervisors.push(supervCombination);
    }

    /*------------------------------------------------------------------------------------------------------------*/

    /********************************************************************************************* Manage Items ***/


    StudentService.addFichePFE(
      this.state.userToBeUpdated.idEt,
      this.state.projectTitle,
      this.state.projectDescription,
      listOfProblematics,
      listOfFunctionnalities,
      this.state.listSelectedLibelleTechnologies,
      listOfSupervisors,
      this.state.pairId,
      this.state.diagramGanttFullPath
    ).then(
      (response) => {
        // console.log('Add Plan de Travail ------------- 1');
        this.setState({
          successConfirmFichePFE: true,
          showDeposeButton: false,
          showSauvegardeButton: true,
          loadingSaveFichePFE: false
        });
      },
      (error) => {
        this.setState({
          successConfirmFichePFE: true,
          showDeposeButton: false,
          showSauvegardeButton: true,
        });
      }
    );
  }

  sauvegarderMaFichePFE() {

    this.setState({loadingConfirmFichePFE: true})
    StudentService.sauvegarderFichePFE(this.state.userToBeUpdated.idEt).then(
        (response) => {
          // // console.log('Sauvega Plan de Travail ------------- 1');
          this.setState({successConfirmSauvegarderFichePFE: true, loadingConfirmFichePFE: false});
        },
        (error) => {
          //this.setState({ successConfirmSauvegarderFichePFE: true });
        }
    );
  }

  addProjectTechnology(
      libelleTechnology // hello
  ) {
    // console.log(' 1 ----------------------- New Added Technology: ' + libelleTechnology + " - " + this.state.technologyItem.length);

    let listLibTechs = [];
    for (let ut of this.state.allTechnologies) {
      listLibTechs.push(ut.label.toUpperCase());
    }

    /*
        for(let a of listLibTechs)
        {
            console.log(" UNIT ---------------------> " + a);
        }*/

    if (listLibTechs.includes(libelleTechnology.toUpperCase().trim())) {
      //console.log(" Catch Tech Duplication ----------2306----------- YES-DUPLI : " + libelleTechnology);
      this.setState({existTech: true});
    } else {
      //console.log(" Catch Tech Duplication -----------2306---------- NO-DUPLI : " + libelleTechnology);
      this.setState({grantAddTech: true});
    }
  }

  verifyExistEncadrantEntreprise(projectSupervEmailOFF) { // lol123
    // console.log('----------------------------> VERIF EE: ' + projectSupervEmail);
    let projectSupervEmail = projectSupervEmailOFF.trim();
    if (projectSupervEmail !== '') {
      this.state.verifInternalDuplication = 'INIT';
      for (let i = 0; i < this.state.supervisorItem.length; i++) {
        /* console.log('COMPARE -------1102-------> ' + this.state.supervisorItem.length + ' : '
            + projectSupervEmail + ' - '
            + this.state.supervisorItem[i].supervEmail);*/

        if (projectSupervEmail.toLowerCase() === this.state.supervisorItem[i].supervEmail.trim().toLowerCase()) {
          this.state.verifInternalDuplication = "DUPLICATED";
          // console.log('-------1102----1---> CATCH IT: ' + this.state.verifExistEncadEntrep);
        }
      }

      // console.log('-------------1102---------------> VERIF EE: ', this.state.supervisorItem + ' - ' + this.state.verifExistEncadEntrep);

      if (this.state.verifInternalDuplication === 'DUPLICATED') {
        this.verifExistEncadEntrep = true;
        this.setState({
          projectSupervisorEmail: projectSupervEmail,
          verifExistEncadEntrep: true
        });
      } else {
        this.verifExistEncadEntrep = false;
        this.setState({
          projectSupervisorEmail: projectSupervEmail,
          verifExistEncadEntrep: false
        });
      }
    }
  }

  verifyExistEncadrantEntreprisePopup(projectSupervEmailOFF) { // lol12345
    // console.log('----------------------------> VERIF EE: ' + projectSupervEmail);

    let projectSupervEmail = projectSupervEmailOFF.trim();

    if (projectSupervEmail !== '') {

      AuthService.verifyExistMailEncadrantEntreprise(
          currentStudent.id,
          encodeURIComponent(projectSupervEmail)
      ).then(
          (response) => {

            let result = response.data;


            // console.log('-------------1102---------------> SIZE: ', this.state.supervisorItem.length);
            this.state.verifInternalDuplication = 'INIT';
            for (let i = 0; i < this.state.supervisorItem.length; i++) {
             /* console.log('COMPARE -------1102-------> ' + this.state.supervisorItem.length + ' : '
                  + projectSupervEmail + ' - '
                  + this.state.supervisorItem[i].supervEmail);*/


              if (projectSupervEmail.toLowerCase() === this.state.supervisorItem[i].supervEmail.trim().toLowerCase()) {
                this.state.verifInternalDuplication = "DUPLICATED";
                // console.log('-------1102----1---> CATCH IT: ' + this.state.verifExistEncadEntrep);
              }
            }

            // console.log('-------------1102---------------> VERIF EE: ', this.state.supervisorItem + ' - ' + this.state.verifExistEncadEntrepPopup);

            if (this.state.verifInternalDuplication === 'DUPLICATED') {
              this.verifExistEncadEntrepPopup = true;
              this.setState({
                supervMl: projectSupervEmail,
                verifExistEncadEntrepPopup: true
              });
            } else {
              this.verifExistEncadEntrepPopup = false;
              this.setState({
                supervMl: projectSupervEmail,
                verifExistEncadEntrepPopup: result
              });
            }

            // console.log('-------------1102---------------> FINAL RESULT: ', this.state.verifInternalDuplication + ' : ' + this.state.supervMl + ' - ' + this.state.verifExistEncadEntrep);

          },
          (error) => {

          }
      );

    }

  }

  addSupervisorProfile(e) {
    e.preventDefault();
    this.toggleValidatingAddSupervisorProfile(true);

    const {
      hasSupervisorFirstNameError,
      hasSupervisorLastNameError,
      hasSupervisorEmailError,
    } = this.state;

    //console.log('#############################> TEST - NB Phone: ' + hasSupervisorPhoneNumberError + ".." + this.state.projectSupervisorPhoneNumber + "**");
    //console.log('#############################> TEST - FN: ' + hasSupervisorFirstNameError + ".." + this.state.projectSupervisorFirstName + "**");
    //console.log('============================================> TEST: ' + hasSupervisorFirstNameError + " - " + hasSupervisorLastNameError + " - " + hasSupervisorPhoneNumberError + " - " + hasSupervisorEmailError);

    if (this.state.projectSupervisorPhoneNumber.trim() === "") {
      this.setState({
        hasSupervisorPhoneNumberCheck: false,
      });
      // console.log('//////////////////////////////////////////////> NOT NULL');
    }

    if (this.state.projectSupervisorPhoneNumber.trim() !== "") {
      this.setState({
        hasSupervisorPhoneNumberCheck: true,
      });
    }

    let pass = "no";
    if (
      this.state.projectSupervisorPhoneNumber.trim() === "" &&
      !hasSupervisorFirstNameError &&
      !hasSupervisorLastNameError &&
      !hasSupervisorEmailError
    ) {
      pass = "yes";
      console.log('---------------------------> NULL');
    }
    if (
      this.state.projectSupervisorPhoneNumber.trim() !== "" &&
      !hasSupervisorFirstNameError &&
      !hasSupervisorLastNameError &&
      !hasSupervisorEmailError
    ) {
      pass = "yes";
      console.log('---------------------------> NOT NULL');
    }

    if (pass.trim() === "yes") {
      /*AuthService.addProjectSupervisor(
          currentStudent.id,
          encodeURIComponent(this.state.projectSupervisorFirstName),
          encodeURIComponent(this.state.projectSupervisorLastName),
          this.state.projectSupervisorPhoneNumber,
          encodeURIComponent(this.state.projectSupervisorEmail)
      ).then(
          (response) => {

          },
          (error) => {
          }
      );*/

      let projSupervPhoneNbr = this.state.projectSupervisorPhoneNumber;
      if (projSupervPhoneNbr === "") {
        // console.log('---------1---------> 0712: ', projSupervPhoneNbr);
        projSupervPhoneNbr = "--";
        // console.log('----------2--------> 0712: ', projSupervPhoneNbr);
      }

      // console.log('---> PIKA 1: ' + this.state.projectSupervisorFirstName);
      // console.log('---> PIKA 2: ' + this.state.projectSupervisorLastName);
      // console.log('---> PIKA 3: ' + this.state.projectSupervisorEmail);

      let supervList = this.state.supervisorItem.slice(); //creates the clone of the state

      if(this.state.projectSupervisorFirstName.length >4 || this.state.projectSupervisorLastName >4 || this.state.projectSupervisorEmail >4)
      {
        const obj = {
          oldSupervMlPass: this.state.projectSupervisorEmail,
          supervFirstName: this.state.projectSupervisorFirstName,
          supervLastName: this.state.projectSupervisorLastName,
          supervPhoneNumber: projSupervPhoneNbr,
          supervEmail: this.state.projectSupervisorEmail,
        };
        supervList.push(obj);

        this.setState({
          supervisorItem: supervList,
          oldSupervMlPass: this.state.projectSupervisorEmail,
          successAddSupervProfile: true,
          projectSupervisorFirstName: "",
          projectSupervisorLastName: "",
          projectSupervisorPhoneNumber: "",
          projectSupervisorEmail: "",
          hasSupervisorFirstNameError: true,
          hasSupervisorLastNameError: true,
          hasSupervisorEmailError: true,
          hasSupervisorPhoneNumberCheck: false,
        });
      }

      //console.log('--------------> PIKATCHOOO ', supervList)

    }

  }

  changeSupervisorProfile(e) {
    e.preventDefault();
    this.toggleValidatingAddSupervProfile(true);

    const {
      hasSupervFNError,
      hasSupervLNError,
      hasSupervMlError
    } = this.state;


    if (this.state.supervTl.trim() === "") {
      this.setState({
        hasSupervTlCheck: false,
      });
      // console.log('//////////////////////////////////////////////> NOT NULL');
    }

    if (this.state.supervTl.trim() !== "") {
      this.setState({
        hasSupervTlCheck: true,
      });
      // console.log('//////////////////////////////////////////////> NOT NULL');
    }


    let pass = "no";
    if (
        this.state.supervTl.trim() === "" &&
        !hasSupervFNError &&
        !hasSupervLNError &&
        !hasSupervMlError
    ) {
      pass = "yes";
      // console.log('---------------------------> NULL');
    }
    if (
        this.state.supervTl.trim() !== "" &&
        !hasSupervFNError &&
        !hasSupervLNError &&
        !hasSupervMlError
    ) {
      pass = "yes";
      // console.log('---------------------------> NOT NULL');
    }

    // console.log('---------- USupP -----------------> FN: ' + this.state.supervFN + " " + this.state.supervLN);
    // console.log('---------- USupP -----------------> Tl: ' + this.state.supervTl);
    // console.log('---------- USupP -----------------> Ml: ' + this.state.oldSupervMlPass + " " + this.state.supervMl);

    if (pass.trim() === "yes") {

      let supervFirstName = this.state.supervFN;
      let supervLastName = this.state.supervLN;
      let supervPhoneNumber = this.state.supervTl;
      let supervEmail = this.state.supervMl;

      let currentOldSupervMlPass = this.state.oldSupervMlPass;
      let oldSupervMlPass = supervEmail;

      this.setState({
        oldSupervMlPass: this.state.supervMl,
        supervisorItem: this.state.supervisorItem.map(el => (el.oldSupervMlPass === currentOldSupervMlPass ? {
          ...el,
          oldSupervMlPass,
          supervFirstName,
          supervLastName,
          supervPhoneNumber,
          supervEmail
        } : el)),
        successOpenSupervisorProfile: false,
        successModifySupervisorProfile: true
      });

      /*AuthService.updateSupervisorCompany(
          currentStudent.id,
          encodeURIComponent(this.state.oldSupervMlPass),
          encodeURIComponent(this.state.supervFN),
          encodeURIComponent(this.state.supervLN),
          this.state.supervTl,
          encodeURIComponent(this.state.supervMl)
      ).then(
          (response) => {

            let supervFirstName = this.state.supervFN;
            let supervLastName = this.state.supervLN;
            let supervPhoneNumber = this.state.supervTl;
            let supervEmail = this.state.supervMl;

            let currentOldSupervMlPass = this.state.oldSupervMlPass;
            let oldSupervMlPass = supervEmail;

            console.log('-------modif-----STU2021: ' + this.state.supervisorItem.idStudent);
            console.log('-------modif----- FN-LN: ' + supervFirstName + " - " + supervLastName);
            console.log('-------modif----- OLD-Mail: ' + currentOldSupervMlPass + " - " + supervEmail);
            console.log('-------modif-----STU2021: ' + this.state.supervisorItem.oldSupervMlPass);


            this.setState({
              oldSupervMlPass: this.state.supervMl,
              supervisorItem: this.state.supervisorItem.map(el => (el.oldSupervMlPass === currentOldSupervMlPass ? {
                ...el,
                oldSupervMlPass,
                supervFirstName,
                supervLastName,
                supervPhoneNumber,
                supervEmail
              } : el)),
              successOpenSupervisorProfile: false,
              successModifySupervisorProfile: true
            })
          },
          (error) => {
          }
      );*/
    }
  }

  handleCloseSuccessModifySupervisorProfile() {
    this.setState({successModifySupervisorProfile: false})
  }

  selectFile(event) {
    this.setState({
      selectedFiles: event.target.files,
    });

    AuthService.getNewspaper(currentStudent.id).then(
        (response) => {

        },
        (error) => {
          // console.log('SELECT -------------- Error');
        }
    );

    // console.log('-----------------------------> Result: ' + this.state.showUploadReportButton + " - " + this.state.showUploadPlagiatButton);

    this.setState({
      showUploadNewspaperButton: true,
      diagramGanttFullPath: ''
    });
    let notif =
        "Vous avez choisi le fichier " + event.target.files[0].name + " .";
    return NotificationManager.success(notif, "Information", 6000);

  }

  gotGanttDiagLabelFromAutoLabel(ganDiagFP) {
    let gdName = ganDiagFP.substring(0, ganDiagFP.lastIndexOf("espdsi2020"));
    let gdExt = ganDiagFP.substring(ganDiagFP.lastIndexOf("."));

    let ganttDiagramFP = gdName + gdExt;
    return ganttDiagramFP;
  }

  uploadGanttDiagram() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadGanttDiagram(currentFile, currentStudent.id, (event) => {
      this.setState({
        showUploadReportButton: false,
        progress: Math.round((100 * event.loaded) / event.total),
        successUpload: true
      });
    })
        .then((response) => {
          // console.log('-------------------------> 1');
          let fullMsg = response.data.message;
          let ganttDiagFP = fullMsg.substring(fullMsg.lastIndexOf('$PATH$C:/ESP/uploads/') + 21);
          let successNotif = fullMsg.substring(0, fullMsg.lastIndexOf('$PATH$C:/ESP/uploads/'));
          // console.log('---STORE DG ---------2802-------------> 1: ', fullMsg);
          // console.log('---STORE DG ---------2802-------------> 2: ', ganttDiagFP);
          // console.log('---STORE DG ---------2802-------------> 3: ', successNotif);
          this.setState({
            successUpload: false,
            message: successNotif,
            showPopupSuccessUpload: true,
            diagramGanttFullPath: ganttDiagFP
          });
          // return AuthService.getNewspaper(currentStudent.id);
        })
        /*.then((files) => {
          this.setState({
            fileInfos: files.data,

          });
          // // console.log('-------------------------> 2.1');
          // console.log(this.state.fileInfos);
          // console.log('-------------------------> 2.2');
          // console.log(files.data);
          // console.log('-------------------------> 2.3');
        })*/
        .catch(() => {
          // console.log('-------------------------> 3');
          this.setState({
            progress: 0,
            message: "Could not upload the file !.",
            currentFile: undefined,
          });
        });

    this.setState({
      selectedFiles: undefined,
    });
  }

  closeModalSuccessUpload() {
    // console.log(' Verify Tech Duplication -------------------- .');
    this.setState({
      showPopupSuccessUpload: false,
    });
  }

  render() {
    const onNext = () => this.onChange(this.state.step + 1);
    const onPrevious = () => this.onChange(this.state.step - 1);

    const {
      projectSupervisorFirstName,
      projectSupervisorLastName,
      projectSupervisorPhoneNumber,
      projectSupervisorEmail,
      validateSupervisorData,
      fichePFEStatus,
      validateFunctionnalityData,
      validateProblematicData,
      studentFullName,
      conventionDateConvenion,
      conventionTreat,
      loadingSaveFichePFE,
      loadingConfirmFichePFE,
      currentDay,
      verifExistEncadEntrep,
      verifExistEncadEntrepPopup,
      successOpenSupervisorProfile,
      supervFN,
      supervLN,
      supervTl,
      supervMl,
      validateSupervData,
      oldSupervMlPass,
      successModifySupervisorProfile,
      libEtatFiche,
      lol1,
      lol2, lol3,
      verifInternalDuplication,
      selectedFiles,
      currentFile,
      progress,
      successUpload,
      showUploadNewspaperButton,
      diagramGanttFullPath,
      showPopupSuccessUpload,
      message
    } = this.state;

    const problemItem = this.state.problemItem.map((item, index) => {
      return (
          <div>
            {item.probLibelle !== "" && (
                <div>
                  <table className="tableAlign">
                    <tr key={index}>
                      <td>
                        <span className="blackLabelModal">{item.probLibelle}</span>
                      </td>
                      <td className="actionTableColumn">
                        {index > 0 && (
                            <div>
                              <CButton shape="pill" size="sm"
                                       color="primary"
                                       aria-expanded="true"
                                       data-toggle="modal"
                                       data-target="#exampleModalProblematic"
                                       onClick={() => this.replaceModalItemProblematic(index)}>
                                <CTooltip content="Modifier Problématique" placement="top">
                                  <CIcon content={freeSet.cilPencil}/>
                                </CTooltip>
                              </CButton>

                              &nbsp;&nbsp;
                              <CButton shape="pill" color="danger" size="sm"
                                       onClick={() => this.deleteItemProblematic(index)}>
                                <CTooltip content="Supprimer Problématique" placement="top">
                                  <CIcon content={freeSet.cilTrash}/>
                                </CTooltip>
                              </CButton>
                            </div>
                        )}
                      </td>
                    </tr>
                  </table>
                </div>
            )}
          </div>
      );
    });

    const requiredProblemItem = this.state.requiredProblemItem;
    let modalDataProblematic = this.state.problemItem[requiredProblemItem];

    const functionnalityItem = this.state.functionnalityItem.map(
        (item, index) => {
          return (
              <div>
                {item.funcLibelle !== "" && (
                    <div>
                      <table className="tableAlign">
                        <tr key={index}>
                          <td>
                            <span className="blackLabelModal">{item.funcLibelle}</span>
                          </td>
                          <td className="actionTableColumn">
                            {index > 0 && (
                                <div>
                                  <CButton size="sm"
                                           shape="pill" color="primary"
                                           data-toggle="modal"
                                           data-target="#exampleModalFunctionnality"
                                           onClick={() =>
                                               this.replaceModalItemFunctionnality(index)
                                           }
                                  >
                                    <CTooltip content="Modifier Fonctionnalité" placement="top">
                                      <CIcon content={freeSet.cilPencil}/>
                                    </CTooltip>
                                  </CButton>
                                  &nbsp;&nbsp;
                                  <CButton
                                      shape="pill" color="danger" size="sm"
                                      onClick={() => this.deleteItemFunctionnality(index)}
                                  >
                                    <CTooltip content="Supprimer Fonctionnalité" placement="top">
                                      <CIcon content={freeSet.cilTrash}/>
                                    </CTooltip>
                                  </CButton>
                                </div>
                            )}
                          </td>
                        </tr>
                      </table>
                    </div>
                )}
              </div>
          );
        }
    );

    const requiredFunctionnalityItem = this.state.requiredFunctionnalityItem;
    let modalDataFunctionnality = this.state.functionnalityItem[
        requiredFunctionnalityItem
        ];

    const technologyItem = this.state.technologyItem.map((item, index) => {
      return (
          <div>
            {item.techLibelle !== "" && (
                <div>
                  <table className="tableAlign">
                    <tr key={index}>
                      <td>
                        <span className="blackLabelModal">{item.techLibelle}</span>
                      </td>
                      <td className="actionTableColumn">
                        {index > 0 && (
                            <div>
                              <CButton shape="pill" color="primary" size="sm"
                                       data-toggle="modal"
                                       data-target="#exampleModalTechnology"
                                       onClick={() =>
                                           this.replaceModalItemTechnology(
                                               index,
                                               item.techLibelle
                                           )
                                       }
                              >
                                <CTooltip content="Modifier Technologie" placement="top">
                                  <CIcon content={freeSet.cilPencil}/>
                                </CTooltip>
                              </CButton>
                              &nbsp;&nbsp;
                              <CButton shape="pill" color="danger" size="sm"
                                       onClick={() =>
                                           this.deleteItemTechnology(index, item.techLibelle)
                                       }
                              >
                                <CTooltip content="Supprimer Technologie" placement="top">
                                  <CIcon content={freeSet.cilTrash}/>
                                </CTooltip>
                              </CButton>
                            </div>
                        )}
                      </td>
                    </tr>
                  </table>
                </div>
            )}
          </div>
      );
    });

    const requiredTechnologyItem = this.state.requiredTechnologyItem;
    let modalDataTechnology = this.state.technologyItem[requiredTechnologyItem];

    //
    return (
        <div>
          {(
              conventionDateConvenion === undefined  // -Convention
              ||
              (fichePFEStatus !== "" && fichePFEStatus === "05" && conventionTreat === "03")  // FichePFE ANNULEE & Convention ANNULEE
          )
          && (
              <>
                <br/><br/>

                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <br/><br/>
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/>
                          <br/>
                          Vous ne pouvez pas déposer votre Plan de Travail.
                          <br/>
                          Vous devez tout d'abord
                          &nbsp;

                          <span className="clignoteRed">
                            demander une Convention
                      </span>
                          &nbsp;.
                          <br/>
                          <br/>
                          <br/>
                        </center>
                      </CCardBody>
                      <CCardFooter>
                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                      Today is {currentDay}
                    </span>
                      </CCardFooter>
                    </CCard>
                  </CCol>
                  <CCol md="2"/>
                </CRow>
              </>
          )}

          {
            fichePFEStatus !== "" && fichePFEStatus === "05" && conventionTreat === "04"  // FichePFE ANNULEE & Convention DEMANDE ANNULATION
            &&
              <>
                <br/><br/>

                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <br/><br/>
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/>
                          <br/>
                          Vous ne pouvez pas déposer votre Plan de Travail.
                          <br/>
                          Merci de patienter jusqu'à
                          &nbsp;
                          <span className="clignoteRed">
                            la Validation
                          </span>
                          &nbsp;
                          de votre Demande Annulation Convention.
                          &nbsp;.
                          <br/>
                          <br/>
                          <br/>
                        </center>
                      </CCardBody>
                      <CCardFooter>
                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                      Today is {currentDay}
                    </span>
                      </CCardFooter>
                    </CCard>
                  </CCol>
                  <CCol md="2"/>
                </CRow>
              </>
          }

          {
            (
                (fichePFEStatus === "" && conventionDateConvenion !== undefined)  // +Convention -FichePFE
                ||
                (fichePFEStatus !== "" && fichePFEStatus === "05" && conventionTreat !== "03"  && conventionTreat !== "04")  // FichePFE ANNULEE & Convention NON ANNULEE
                ||
                (fichePFEStatus !== "" && fichePFEStatus === "04")  // FichePFE REFUSEE
            ) && (
                <div>
                  <Steps current={this.state.step}>
                    <Step
                        title="Informations Personnelles"
                        description="Vous pouvez modifier les données marquées avec (*) en cliquant sur la donnée désirée."
                    />
                    <Step
                        title="Plan Travail"
                        description="Préciser le Plan de votre Projet."
                    />
                    <Step
                        title="Entreprise & Encadrement"
                        description="Présenter votre Entreprise d'Accueil et vos Encadrant(s)."
                    />
                    <Step
                        title="Confirmation"
                        description="Sauvegarder et/ou Confirmer votre Plan de Travail."
                    />
                  </Steps>

                  <hr/>

                  {this.state.step + 1 === 1 && (
                      <Panel>
                        <strong>
                          <span className="clignoteRedStepTitle">Informations Personnelles</span>
                        </strong>
                        <br/><br/>
                        <CRow>
                          <CCol md="2" class="bg-success py-2">
                            <strong>
                              <span className="text-dark" style={{fontSize: "12px"}}>Nom & Prénom: </span>
                            </strong>
                          </CCol>
                          <CCol md="8" class="bg-warning py-2">
                            <span style={{fontSize: "12px"}}>
                            {this.state.userToBeUpdated.nomet} &nbsp; {this.state.userToBeUpdated.prenomet}
                            </span>
                          </CCol>
                        </CRow>

                        <br/>
                        <CRow>
                          <CCol md="2" class="bg-success py-2">
                            <strong>
                              <span className="text-dark" style={{fontSize: "12px"}}>E-Mail (Esprit) : </span>
                            </strong>
                          </CCol>
                          <CCol md="8" class="bg-warning py-2">
                            {this.state.userToBeUpdated.adressemailesp === null && (
                                <span className="note">--</span>
                            )}
                            {this.state.userToBeUpdated.adressemailesp !== null && (
                                <a
                                    href="https://mail.google.com/"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                >
                          <span className="noteMail">

                            {this.state.userToBeUpdated.adressemailesp}
                          </span>
                                </a>
                            )}
                          </CCol>
                        </CRow>
                        <br/>
                        <CRow>
                          <CCol md="2" class="bg-success py-2">
                            <strong>
                              <span className="text-dark" style={{fontSize: "12px"}}>Autre E-Mail : </span>
                            </strong>
                          </CCol>
                          <CCol md="8" class="bg-warning py-2">
                            <EditableLabelFichePFENewMail
                                uTBUNewMail={this.state.uTBUNewMail}
                                onChange={this.handleChangeUTBUNewMail}
                            />
                            {this.state.verifyuTBUNewMail.trim() === "no" && (
                                <CRow>
                                  <CCol md="2" class="bg-success py-2"/>
                                  <CCol md="8" class="bg-warning py-2">
                                    <div className="alert alert-danger" role="alert">
                                      It's not a valid New Mail format.
                                    </div>
                                  </CCol>
                                </CRow>
                            )}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol md="2" class="bg-success py-2">
                            <strong>
                              <span className="text-dark" style={{fontSize: "12px"}}>Adresse</span>
                              &nbsp;
                              <span className="requiredStar">(*)</span>&nbsp;
                              <span className="greyFieldLibelleCard">:</span>
                            </strong>
                          </CCol>
                          <CCol md="8" class="bg-warning py-2">
                            <EditableLabelFichePFEAddress
                                uTBUAddress={this.state.uTBUAddress}
                                onChange={this.handleChangeUTBUAddress}
                            />
                            {this.state.verifyuTBUAddress.trim() === "no" && (
                                <CRow>
                                  <CCol md="2" class="bg-success py-2"/>
                                  <CCol md="8" class="bg-warning py-2">
                                    <div className="alert alert-danger" role="alert">
                                      It's not a valid Address format.
                                    </div>
                                  </CCol>
                                </CRow>
                            )}

                            {this.state.verifyuTBUAddressNull.trim() === "yes" && (
                                <CRow>
                                  <CCol md="2" class="bg-success py-2"/>
                                  <CCol md="8" class="bg-warning py-2">
                                    <div className="alert alert-danger" role="alert">
                                      The Address is a required field.
                                    </div>
                                  </CCol>
                                </CRow>
                            )}
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol md="2" class="bg-success py-2">
                            <strong>
                              <span className="text-dark" style={{fontSize: "12px"}}>Num. Téléphone</span>
                              &nbsp;
                              <span className="requiredStar">(*)</span>&nbsp;
                              <span className="greyFieldLibelleCard">:</span>
                            </strong>
                          </CCol>
                          <CCol md="8" class="bg-warning py-2">
                            <>
                              <EditableLabelFichePFEPhone
                                  uTBUPhone={this.state.uTBUPhone}
                                  onChange={this.handleChangeUTBUPhone}
                              />
                              {this.state.verifyuTBUPhone.trim() === "no" && (
                                  <CRow>
                                    <CCol md="2" class="bg-success py-2"/>
                                    <CCol md="8" class="bg-warning py-2">
                                      <div className="alert alert-danger" role="alert">
                                        <span style={{fontSize: "12px"}}>
                                        The phone must be set of numbers.
                                        </span>
                                      </div>
                                    </CCol>
                                  </CRow>
                              )}

                              {this.state.verifyuTBUPhoneNull.trim() === "yes" && (
                                  <CRow>
                                    <CCol md="2" class="bg-success py-2"/>
                                    <CCol md="8" class="bg-warning py-2">
                                      <div className="alert alert-danger" role="alert">
                                        <span style={{fontSize: "12px"}}>
                                        The phone is a required field.
                                        </span>
                                      </div>
                                    </CCol>
                                  </CRow>
                              )}

                              {this.state.verifyuTBUPhoneLimit.trim() === "yes" && (
                                  <CRow>
                                    <CCol md="2" class="bg-success py-2"/>
                                    <CCol md="8" class="bg-warning py-2">
                                      <div className="alert alert-danger" role="alert">
                                        The phone Number should be at least with 8 numbers.
                                      </div>
                                    </CCol>
                                  </CRow>
                              )}
                            </>
                          </CCol>
                        </CRow>

                        <CRow>
                          <CCol md="2" class="bg-success py-2">
                            <strong>
                              <span className="text-dark" style={{fontSize: "12px"}}>Binôme ID : </span>
                            </strong>
                          </CCol>
                          <CCol md="2" class="bg-warning py-2">
                            <>
                              <input
                                  className="form-control"
                                  value={this.state.pairId}
                                  placeholder="Your Pair ID"
                                  onChange={(e) => this.addPairHandler(e)}
                              />
                              {this.state.verifyPairField.trim() === "NO" && (
                                  <CRow>
                                    <CCol md="12" class="bg-warning py-2">
                                      <div className="alert alert-danger" role="alert" style={{fontSize: "12px"}}>
                                        Your Pair ID must be with 10 characters !.
                                        <br/>
                                        Example: ***JFT****
                                      </div>
                                    </CCol>
                                  </CRow>
                              )}
                            </>
                          </CCol>
                          <CCol md="8" class="bg-warning py-2">
                            <>
                              &nbsp;&nbsp;
                              <span className="pairFullName">{studentFullName}</span>
                            </>
                          </CCol>
                        </CRow>
                      </Panel>
                  )}

                  {this.state.step + 1 === 2 && (
                      <Panel>
                        <div>
                          <strong>
                            <span className="clignoteRedStepTitle">Plan Travail</span>
                          </strong>
                          <br/><br/>
                          <Accordion expanded={this.state.expanded === "panel1"} onChange={this.handleChange("panel1")}>
                            <AccordionSummary expandIcon={<ExpandMoreIcon/>} aria-controls="panel1bh-content"
                                              id="panel1bh-header">
                              <Typography className="heading">Détails Plan Travail</Typography>
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              <Typography className="secondaryHeading">
                                Présenter l'idée de mon projet.
                              </Typography>
                            </AccordionSummary>
                            <AccordionDetails>

                              <CContainer>
                                <CRow>
                                  <CCol md="2" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      Titre Projet
                                    </span>&nbsp;
                                    <span className="requiredStar">(*)</span>&nbsp;
                                    <span className="greyFieldLibelleCard">:</span>
                                  </CCol>
                                  <CCol md="8" class="bg-warning py-2">
                                    <Textbox
                                        attributesInput={{
                                          id: "projectTitle",
                                          name: "projectTitle",
                                          type: "text",
                                          placeholder: "Saisir Titre Projet ici.",
                                        }}
                                        value={this.state.projectTitle}
                                        onChange={(projectTitle, e) => {
                                          this.setState({projectTitle});
                                        }}
                                        onBlur={(e) => {
                                          // console.log(e);
                                        }}
                                        validationOption={{
                                          name: "Titre Projet",
                                          check: true,
                                          required: true,
                                          customFunc: (projectTitle) => {
                                            if (
                                                projectTitle.length > 9 &&
                                                projectTitle.length < 101
                                            ) {
                                              return true;
                                            } else {
                                              return "Titre Projet must be between 10 and 100 characters.";
                                            }
                                          },
                                        }}
                                    />
                                  </CCol>
                                </CRow>
                                <br/>

                                <CRow>
                                  <CCol md="2" class="bg-success py-2">
                            <span className="greyFieldLibelleCard">
                              Description Project :
                            </span>
                                  </CCol>
                                  <CCol md="8" class="bg-warning py-2">
                                    <Textbox
                                        attributesInput={{
                                          id: "projectDescription",
                                          name: "projectDescription",
                                          type: "text",
                                          placeholder:
                                              "Saisir Description Project ici.",
                                        }}
                                        value={this.state.projectDescription}
                                        onChange={(projectDescription, e) => {
                                          this.setState({projectDescription});
                                        }}
                                        onBlur={(e) => {
                                          // console.log(e);
                                        }}
                                        validationOption={{
                                          name: "Description Project",
                                          check: true,
                                          required: false,
                                          customFunc: (projectDescription) => {
                                            if (projectDescription.length !== 0) {
                                              if (
                                                  projectDescription.length > 9 &&
                                                  projectDescription.length < 501
                                              ) {
                                                return true;
                                              } else {
                                                return "Description Project must be empty OR between 10 and 500 characters.";
                                              }
                                            } else {
                                              return true;
                                            }
                                          },
                                        }}
                                    />
                                  </CCol>
                                </CRow>
                                <br/>

                                <CRow>
                                  <CCol md="2" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      Problématique Projet
                                    </span>&nbsp;
                                    <span className="requiredStar">(*)</span>&nbsp;
                                    <span className="greyFieldLibelleCard">:</span>
                                  </CCol>
                                  <CCol md="8" class="bg-warning py-2">
                                    <Textarea
                                        attributesInput={{
                                          id: "projectProblematic",
                                          name: "projectProblematic",
                                          type: "text",
                                          placeholder:
                                              "Saisir Problématique Projet ici.",
                                        }}
                                        value={this.state.projectProblematic}
                                        className="form-control"
                                        validate={validateProblematicData}
                                        validationCallback={(res) =>
                                            this.setState({
                                              hasProjectProblematicError: res,
                                              validateProblematicData: false,
                                            })
                                        }
                                        onChange={(projectProblematic, e) => {
                                          this.setState({projectProblematic});
                                        }}
                                        onBlur={(e) => {
                                          // console.log(e);
                                        }}
                                        validationOption={{
                                          name: "Problématique Projet",
                                          check: true,
                                          required: true,
                                          customFunc: (projectProblematic) => {
                                            if (
                                                projectProblematic.length > 9 &&
                                                projectProblematic.length < 3001
                                            ) {
                                              return true;
                                            } else {
                                              return "Problématique Projet must be between 10 and 3000 characters.";
                                            }
                                          },
                                        }}
                                    />

                                    <br/>
                                    {this.state.projectProblematic.length > 9 &&
                                    this.state.projectProblematic.length < 3001 && (
                                        <>
                                            <span className="clignoteRedAlert">
                                                N'oublier pas de cliquer sur ce bouton pour passer à l'étape suivante
                                            </span>
                                            &nbsp;&nbsp;
                                            <CButton className="float-right"
                                                     onClick={() => this.addProjectProblematic(this.state.projectProblematic)}
                                                     color="danger">
                                              <CTooltip content="Ajouter Problématique" placement="top">
                                                <span> + </span>
                                              </CTooltip>
                                            </CButton>
                                        </>

                                    )}

                                    {
                                        this.state.denyAddProblematic &&
                                        <Modal  isOpen={this.state.denyAddProblematic}
                                                contentLabel="Example Modal"
                                                style={customStyles}>
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                            <center>
                                                <br/>
                                                Vous ne pouvez pas dépasser 5 Problématiques.
                                                <br/>
                                                <br/>
                                                <br/>
                                                <button className="btn btn-sm btn-primary"
                                                        onClick={this.closeModalDenyAddProblematique}>
                                                  Ok
                                                </button>
                                                <br/>
                                                <br/>
                                            </center>
                                        </Modal>
                                    }

                                    {this.state.problemItem.length - 1 !== 0 && (
                                        <div>
                                          <br/>
                                          <br/>
                                          <br/>
                                          <center>
                                    <span className="redTableTitle">
                                      Liste des Problématiques
                                    </span>
                                          </center>
                                          <br/>
                                          <br/>

                                          <table className="table table-striped">
                                            <thead>
                                            <tr>
                                              <th> Problématique</th>
                                            </tr>
                                            </thead>
                                            <tbody><span className="infoField">{problemItem}</span></tbody>
                                          </table>
                                          <ModalProblematic
                                              probLibelle={
                                                modalDataProblematic.probLibelle
                                              }
                                              saveModalDetailsProblematic={
                                                this.saveModalDetailsProblematic
                                              }
                                          />
                                          <span className="float-right"
                                                style={{
                                                  color: "red"
                                                }}
                                          >
                                    {this.state.problemItem.length - 1}
                                  </span>


                                        </div>
                                    )}
                                  </CCol>
                                </CRow>
                              </CContainer>

                            </AccordionDetails>
                          </Accordion>

                          <Accordion expanded={this.state.expanded === "panel2"} onChange={this.handleChange("panel2")}>
                            <AccordionSummary expandIcon={<ExpandMoreIcon/>} aria-controls="panel1bh-content"
                                              id="panel1bh-header">
                              <Typography className="heading">Fonctionnalités / Tâches</Typography>
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            </AccordionSummary>
                            <AccordionDetails>

                              <CContainer>
                                <form onSubmit={this.addProjectFunctionnality}>
                                  <CRow>
                                    <CCol md="2" class="bg-success py-2">
                                      <span className="greyFieldLibelleCard">
                                        Libellé Fonctionnalité / Tâche
                                      </span>&nbsp;
                                      <span className="requiredStar">(*)</span>&nbsp;
                                      <span className="greyFieldLibelleCard">:</span>
                                    </CCol>
                                    <CCol md="8" class="bg-warning py-2">
                                      <Textbox
                                          attributesWrapper={{}}
                                          attributesInput={{
                                            id: "projectFunctionnalityLibelle",
                                            name: "projectFunctionnalityLibelle",
                                            type: "text",
                                            placeholder:
                                                "Saisir Libellé Fonctionnalité / Tâche ici.",
                                          }}
                                          value={
                                            this.state.projectFunctionnalityLibelle
                                          }
                                          disabled={false}
                                          validate={validateFunctionnalityData}
                                          validationCallback={(res) =>
                                              this.setState({
                                                hasProjectFunctionnalityLibelleError: res,
                                                validateFunctionnalityData: false,
                                              })
                                          }
                                          onChange={(
                                              projectFunctionnalityLibelle,
                                              e
                                          ) => {
                                            this.setState({
                                              projectFunctionnalityLibelle,
                                            });
                                          }}
                                          onBlur={(e) => {
                                            // console.log(e);
                                          }}
                                          validationOption={{
                                            name: "Libellé Fonctionnalité / Tâche Projet",
                                            check: true,
                                            required: true,

                                            customFunc: (
                                                projectFunctionnalityLibelle
                                            ) => {
                                              if (
                                                  projectFunctionnalityLibelle.length >
                                                  9 &&
                                                  projectFunctionnalityLibelle.length < 81
                                              ) {
                                                return true;
                                              } else {
                                                return "Try present briefly the functionnality (10-80 characters allowed). For more details, you can express it bellow in Description area.";
                                              }
                                            },
                                          }}
                                      />
                                    </CCol>
                                  </CRow>
                                  <br/>

                                  <CRow>
                                    <CCol md="2" class="bg-success py-2">
                            <span className="greyFieldLibelleCard">
                                  Description Fonctionnalité / Tâche:
                                </span>
                                    </CCol>
                                    <CCol md="8" class="bg-warning py-2">
                                      <Textarea
                                          attributesInput={{
                                            id: "projectFunctionnalityDescription",
                                            name: "projectFunctionnalityDescription",
                                            type: "text",
                                            placeholder:
                                                "Saisir Description Fonctionnalité ici.",
                                          }}
                                          value={
                                            this.state.projectFunctionnalityDescription
                                          }
                                          className="form-control"
                                          onChange={(
                                              projectFunctionnalityDescription,
                                              e
                                          ) => {
                                            this.setState({
                                              projectFunctionnalityDescription,
                                            });
                                          }}
                                          onBlur={(e) => {
                                            // console.log(e);
                                          }}
                                          validationOption={{
                                            name: "Description Fonctionnalité Projet",
                                            check: true,
                                            required: false,
                                            customFunc: (
                                                projectFunctionnalityDescription
                                            ) => {
                                              if (
                                                  projectFunctionnalityDescription.length !==
                                                  0
                                              ) {
                                                if (
                                                    projectFunctionnalityDescription.length >
                                                    9 &&
                                                    projectFunctionnalityDescription.length <
                                                    3001
                                                ) {
                                                  return true;
                                                } else {
                                                  return "Description Fonctionnalité Projet must be between 10 and 3000 characters.";
                                                }
                                              } else {
                                                return true;
                                              }
                                            },
                                          }}
                                      />

                                      <br/>
                                      {((this.state.projectFunctionnalityLibelle.length >
                                              9 &&
                                              this.state.projectFunctionnalityLibelle.length <
                                              81 &&
                                              this.state.projectFunctionnalityDescription
                                                  .length === 0) ||
                                          (this.state.projectFunctionnalityLibelle.length >
                                              9 &&
                                              this.state.projectFunctionnalityLibelle.length <
                                              81 &&
                                              this.state.projectFunctionnalityDescription
                                                  .length !== 0 &&
                                              this.state.projectFunctionnalityDescription
                                                  .length > 9 &&
                                              this.state.projectFunctionnalityDescription
                                                  .length < 3001)) && (
                                          <>
                                              <span className="clignoteRedAlert">
                                                 N'oublier pas de cliquer sur ce bouton pour passer à l'étape suivante
                                              </span>
                                              &nbsp;&nbsp;
                                              <CButton  color="danger"
                                                        onClick={this.addProjectFunctionnality}
                                                        className="float-right">
                                                <CTooltip content="Ajouter Fonctionnalité" placement="top">
                                                  <span> + </span>
                                                </CTooltip>
                                              </CButton>
                                          </>
                                      )}

                                      {this.state.denyAddFonctionnalite &&
                                          <Modal  isOpen={this.state.denyAddFonctionnalite}
                                                  contentLabel="Example Modal"
                                                  style={customStyles}>
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                            <center>
                                              <br/>
                                              Vous ne pouvez pas dépasser 10 Fonctionnalités.
                                              <br/>
                                              <br/>
                                              <br/>
                                              <button className="btn btn-sm btn-primary"
                                                      onClick={this.closeModalDenyAddFonctionnalite}>
                                                Ok
                                              </button>
                                              <br/>
                                              <br/>
                                            </center>
                                          </Modal>
                                      }

                                      {this.state.functionnalityItem.length - 1 !== 0 && (
                                          <div>
                                            <br/>
                                            <br/>
                                            <br/>
                                            <center>
                                <span className="redTableTitle">
                                  Liste des Fonctionnalités / Tâches
                                </span>
                                            </center>
                                            <br/>
                                            <br/>

                                            <table className="table table-striped">
                                              <thead>
                                              <tr>
                                                <th>Libellé Fonctionnalité / Tâche</th>
                                              </tr>
                                              </thead>
                                              <tbody><span className="infoField">{functionnalityItem}</span></tbody>
                                            </table>
                                            <ModalFunctionnality
                                                funcLibelle={
                                                  modalDataFunctionnality.funcLibelle
                                                }
                                                funcDescription={
                                                  modalDataFunctionnality.funcDescription
                                                }
                                                saveModalDetailsFunctionnality={
                                                  this.saveModalDetailsFunctionnality
                                                }

                                            />

                                            <span className="float-right" style={{color: "red"}}>
                                              {this.state.functionnalityItem.length - 1}
                                            </span>
                                          </div>
                                      )}

                                    </CCol>
                                  </CRow>
                                </form>
                              </CContainer>

                            </AccordionDetails>
                          </Accordion>

                          <Accordion
                              expanded={this.state.expanded === "panel3"}
                              onChange={this.handleChange("panel3")}
                          >
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon/>}
                                aria-controls="panel3bh-content"
                                id="panel3bh-header"
                            >
                              <Typography className="heading">Technologies</Typography>
                            </AccordionSummary>
                            <CContainer>
                              <CRow>
                                <CCol xs="6">
                                  <br/>
                                  <span className="noteDivGrey">
                              Vous pouver choisir une ou plusieurs
                              Technologie(s).
                            </span>
                                  <hr/>

                                  <Select
                                      defaultValue={this.state.selectedTechnologies}
                                      closeMenuOnSelect={false}
                                      isMulti
                                      value={this.state.allTechnologies.value}
                                      components={animatedComponents}
                                      options={this.state.allTechnologies}
                                      onChange={this.onChangeSelectProjectTechnology.bind(
                                          this
                                      )}
                                  />
                                </CCol>
                                <CCol xs="6">
                            <span className="noteDivGrey">
                              Vous ne trouvez pas votre Technologie.
                            </span>
                                  <br/>
                                  <span className="noteDivGrey">
                              Vous pouvez ajouter une nouvelle.
                            </span>
                                  <hr/>
                                  <div>
                                    <Textbox
                                        attributesInput={{
                                          id: "projectTechnologyLibelle",
                                          name: "projectTechnologyLibelle",
                                          type: "text",
                                          placeholder:
                                              "Saisir Libellé Technologie ici.",
                                        }}
                                        value={this.state.projectTechnologyLibelle}
                                        onChange={(projectTechnologyLibelle, e) => {
                                          this.setState({projectTechnologyLibelle});
                                        }}
                                        onBlur={(e) => {
                                          // console.log(e);
                                        }}
                                        validationOption={{
                                          name: "Libellé Technologie Projet",
                                          check: true,
                                          customFunc: (projectTechnologyLibelle) => {
                                            if (
                                                !projectTechnologyLibelle.includes(",") &&
                                                projectTechnologyLibelle.length < 256
                                            ) {
                                              return true;
                                            }
                                            if (projectTechnologyLibelle.length > 255) {
                                              return "The Technology Libelle shouldn't depass 255 characters.";
                                            }
                                          },
                                        }}
                                    />
                                    <br/>

                                    {this.state.projectTechnologyLibelle.length > 0 &&
                                    this.state.projectTechnologyLibelle.length <
                                    256 && (
                                        <div className="float-right">
                                          <CButton
                                              onClick={() =>
                                                  this.addProjectTechnology(
                                                      this.state.projectTechnologyLibelle
                                                  )
                                              }
                                              color="danger"
                                          >
                                            <CTooltip content="Ajouter Technologie" placement="top">
                                              <span> + </span>
                                            </CTooltip>
                                          </CButton>
                                        </div>
                                    )}

                                    {this.state.existTech && (
                                        <Modal
                                            isOpen={this.state.existTech}
                                            contentLabel="Example Modal"
                                            style={customStyles}
                                        >
                                          <span
                                              className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                          <hr/>
                                          <center>
                                            <br/>
                                            Cette Technology est déjà existante.
                                            <br/>
                                            D'abord, vérifier si votre Technologie
                                            existe dans cette liste. Sinon, ajouter une
                                            nouvelle.
                                            <br/>
                                            <br/>
                                            <br/>
                                            <button
                                                className="btn btn-sm btn-primary"
                                                onClick={
                                                  this.closeModalVerifyTechDuplication
                                                }
                                            >
                                              Ok
                                            </button>
                                            <br/>
                                            <br/>
                                          </center>
                                        </Modal>
                                    )}

                                    {this.state.grantAddTech && (
                                        <Modal
                                            isOpen={this.state.grantAddTech}
                                            contentLabel="Example Modal"
                                            style={customStyles}
                                        >
                                          <span
                                              className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                          <hr/>
                                          <center>
                                            <br/>
                                            Votre Technology est insérée avec succès.
                                            <br/>
                                            <br/>
                                            <br/>
                                            <button
                                                className="btn btn-sm btn-primary"
                                                onClick={
                                                  this.closeModalGrantAddTechnology
                                                }
                                            >
                                              Ok
                                            </button>
                                            <br/>
                                            <br/>
                                          </center>
                                        </Modal>
                                    )}
                                    {this.state.technologyItem.length - 1 !== 0 && (
                                        <div>
                                          <br/>
                                          <br/>
                                          <br/>
                                          <center>
                                    <span className="redTableTitle">
                                      Liste de mes Technologies ajoutées
                                    </span>
                                          </center>
                                          <br/>
                                          <br/>
                                          <table className="table table-striped">
                                            <thead>
                                            <tr>
                                              <th> Libellé Technologie</th>
                                            </tr>
                                            </thead>
                                            <tbody>{technologyItem}</tbody>
                                          </table>
                                          <ModalTechnology
                                              techLibelle={
                                                modalDataTechnology.techLibelle
                                              }
                                              saveModalDetailsTechnology={
                                                this.saveModalDetailsTechnology
                                              }
                                          />
                                          <>
                                              <span className="clignoteRedAlert">
                                                 N'oublier pas d'ajouter votre(vos) propre(s) nouvelle(s) Technologie(s) à la liste à GAUCHE
                                              </span>
                                            &nbsp;&nbsp;
                                            <span style={{color: "red"}} className="float-right">
                                                {this.state.technologyItem.length - 1}
                                              </span>
                                          </>
                                        </div>
                                    )}
                                  </div>
                                </CCol>
                              </CRow>
                              <br/><br/>
                            </CContainer>
                          </Accordion>

                          <Accordion expanded={this.state.expanded === "panel4"}
                                     onChange={this.handleChange("panel4")}>
                            <AccordionSummary expandIcon={<ExpandMoreIcon/>}
                                              aria-controls="panel4bh-content"
                                              id="panel4bh-header">
                              <Typography className="heading">Diagramme de Gantt</Typography>
                            </AccordionSummary>
                            <CContainer>
                                  <span className="noteDivGrey">
                                      Déposer votre Diagramme de Gantt
                                  </span>
                              <br/>
                              <CCardBody>
                                <NotificationContainer/>
                                <center>
                                  <label htmlFor="filePicker" className="custom-file-upload">
                                    <img src={greyUpload}
                                         className="cursorPointer"
                                         className="img-fluid"
                                         width="100px"
                                         height="70px"
                                         title="Choose your Newspaper"
                                         alt=""/>
                                  </label>
                                  <br/>
                                  <label className="btn btn-default">
                                    <input id="filePicker"
                                           type="file"
                                           style={{visibility: "hidden"}}
                                           accept="application/gan"
                                           onChange={this.selectFile}/>
                                  </label>

                                  {
                                    currentFile && successUpload &&
                                    <div className="progress" style={{width: "500px"}}>
                                      <div
                                          className="progress-bar bg-secondary progress-bar-striped progress-bar-animated"
                                          role="progressbar"
                                          aria-valuenow={progress}
                                          aria-valuemin="0"
                                          aria-valuemax="100"
                                          style={{width: progress + "%"}}>
                                        {progress}%
                                      </div>
                                    </div>
                                  }

                                  {
                                    showUploadNewspaperButton && selectedFiles &&
                                    <>
                                      <br/>
                                      <button className="btn btn-secondary"
                                              onClick={this.uploadGanttDiagram}>
                                        Déposer Diagramme
                                      </button>
                                    </>
                                  }

                                  {
                                    diagramGanttFullPath.length !== 0 &&
                                    <>
                                      <div className="alert alert-success" role="alert" style={{width: "800px"}}>
                                        Votre Diagramme de Gantt est :
                                        <br/>
                                        {this.gotGanttDiagLabelFromAutoLabel(diagramGanttFullPath)}
                                      </div>
                                      <br/>
                                    </>
                                  }

                                </center>
                              </CCardBody>

                              {
                                showPopupSuccessUpload &&
                                  <Modal isOpen={showPopupSuccessUpload}
                                         contentLabel="Example Modal"
                                         style={customStyles}>
                                    <span
                                        className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                    <center>
                                      <br/>
                                      <br/>
                                      {message}

                                      <br/>
                                      <br/>
                                      <br/>
                                      <button className="btn btn-sm btn-primary" onClick={this.closeModalSuccessUpload}>
                                        Ok
                                      </button>
                                      <br/>
                                      <br/>
                                    </center>
                                  </Modal>
                              }

                            </CContainer>
                          </Accordion>

                        </div>
                      </Panel>
                  )}

                  {this.state.step + 1 === 3 && (
                      <Panel>
                        <div>
                          <strong>
                            <span className="clignoteRedStepTitle">Entreprise & Encadrement</span>
                          </strong>
                          <br/><br/>
                          <Accordion
                              expanded={this.state.expanded === "panel1"}
                              onChange={this.handleChange("panel1")}
                          >
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon/>}
                                aria-controls="panel1bh-content"
                                id="panel1bh-header"
                            >
                              <Typography className="heading">
                                Informations sur Entreprise d'Accueil
                              </Typography>
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            </AccordionSummary>
                            <CRow>
                              <CCol md="2"/>
                              <CCol md="8">
                                <Card sx={{maxWidth: 530}}>
                                  <CardHeader avatar={<Avatar src={company}
                                                              aria-label={this.state.projectCompany.designation}/>}
                                              title={this.state.projectCompany.designation}
                                              subheader="Entreprise Accueil"/>
                                  <CardMedia component="img"
                                             height="200"
                                             src={company}
                                             alt="Entreprise Accueil"/>
                                  <CardContent>
                                    <Typography variant="body2" color="text.secondary">
                                      <CRow>
                                        <CCol md="7">
                                          <CRow>
                                            <CCol md="2" className="float-center">
                                              <center>
                                                <span className="mailIcon"/>
                                              </center>
                                            </CCol>
                                            <CCol md="10">
                                              {this.state.projectCompany.addressMail}
                                            </CCol>

                                            <CCol md="2" className="float-center">
                                              <center>
                                                <span className="phoneIconify"/>
                                              </center>
                                            </CCol>
                                            <CCol md="10">
                                              {
                                                this.state.projectCompany.telephone === "--------" ?
                                                    <>--</>
                                                    :
                                                    <>{this.state.projectCompany.telephone}</>
                                              }
                                            </CCol>

                                            <CCol md="2" className="float-center">
                                              <center>
                                                <span className="faxIconify"/>
                                              </center>
                                            </CCol>
                                            <CCol md="10">
                                              {
                                                this.state.projectCompany.fax === "--------" ?
                                                    <>--</>
                                                    :
                                                    <>{this.state.projectCompany.fax}</>
                                              }
                                            </CCol>

                                            <CCol md="2" className="float-center">
                                              <center>
                                                <span className="mapIconify"/>
                                              </center>
                                            </CCol>
                                            <CCol md="10">
                                              {this.state.projectCompany.address}
                                              {
                                                this.state.projectCompany.paysDto === null ?
                                                    <></>
                                                    :
                                                    <>, {this.state.projectCompany.paysDto.nomPays}</>
                                              }
                                            </CCol>
                                          </CRow>
                                        </CCol>
                                        <CCol md="5">
                                          <CRow>
                                            <CCol md="4" className="colPaddingRight">
                                              <span className="convHistoricGreyField">
                                                Siège Social:
                                              </span>
                                            </CCol>
                                            <CCol md="8">
                                              {
                                                this.state.projectCompany.siegeSocial === "---" ?
                                                    <>--</>
                                                    :
                                                    <>{this.state.projectCompany.siegeSocial}</>
                                              }
                                            </CCol>

                                            <CCol md="4" className="colPaddingRight">
                                              <span className="convHistoricGreyField">
                                                Secteur Activité:
                                              </span>
                                            </CCol>
                                            <CCol md="8">
                                              {
                                                this.state.projectCompany.sectorEntrepriseDto === null ?
                                                    <>--</>
                                                    :
                                                    <>
                                                      {this.state.projectCompany.sectorEntrepriseDto.libelleSecteur}
                                                    </>
                                              }
                                            </CCol>
                                          </CRow>
                                        </CCol>
                                      </CRow>
                                    </Typography>
                                  </CardContent>
                                </Card>
                              </CCol>
                              <CCol md="2"/>
                            </CRow>
                          </Accordion>

                          <Accordion
                              expanded={this.state.expanded === "panel2"}
                              onChange={this.handleChange("panel2")}
                          >
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon/>}
                                aria-controls="panel1bh-content"
                                id="panel1bh-header"
                            >
                              <Typography className="heading">
                                Encadrant(s) Entreprise d'Accueil
                              </Typography>
                            </AccordionSummary>

                            <CRow>
                              <CCol xs="2"/>
                              <CCol xs="8">
                                <center>
                              <span className="redTableTitle">
                                Informations sur mon/mes Encadrant(s) Entreprise d'Accueil
                              </span>
                                </center>
                                <hr/>
                                <form onSubmit={this.addSupervisorProfile}>
                                  <CContainer>
                                    <CRow>
                                      <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      Nom
                                    </span>
                                        &nbsp;
                                        <span className="requiredStar">(*)</span>&nbsp;
                                        <span className="greyFieldLibelleCard">:</span>
                                      </CCol>
                                      <CCol md="9" class="bg-warning py-2">
                                        <Textbox
                                            attributesWrapper={{}}
                                            attributesInput={{
                                              id: "ProjectSupervisorFirstName",
                                              name: "ProjectSupervisorFirstName",
                                              type: "text",
                                              placeholder:
                                                  "Saisir Nom Encadrant ici.",
                                            }}
                                            value={projectSupervisorFirstName}
                                            disabled={false}
                                            validate={validateSupervisorData}
                                            validationCallback={(res) =>
                                                this.setState({
                                                  hasSupervisorFirstNameError: res,
                                                  validateSupervisorData: false,
                                                })
                                            }
                                            onChange={(
                                                projectSupervisorFirstName,
                                                e
                                            ) => {
                                              this.setState({
                                                projectSupervisorFirstName,
                                              });
                                            }}
                                            onBlur={(e) => {
                                              // // console.log(e);
                                            }}
                                            validationOption={{
                                              name: "Nom Encadrant",
                                              check: true,
                                              required: true,
                                              customFunc: (
                                                  projectSupervisorFirstName
                                              ) => {
                                                if (
                                                    projectSupervisorFirstName.length >
                                                    4 &&
                                                    projectSupervisorFirstName.length < 51
                                                ) {
                                                  return true;
                                                } else {
                                                  return "Nom must be between 5 and 50 characters.";
                                                }
                                              },
                                            }}
                                        />
                                      </CCol>
                                    </CRow>
                                    <br/>

                                    <CRow>
                                      <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                    Prénom
                                  </span>
                                        &nbsp;
                                        <span className="requiredStar">(*)</span>&nbsp;
                                        <span className="greyFieldLibelleCard">:</span>
                                      </CCol>
                                      <CCol md="9" class="bg-warning py-2">
                                        <Textbox
                                            attributesWrapper={{}}
                                            attributesInput={{
                                              id: "ProjectSupervisorLastName",
                                              name: "ProjectSupervisorLastName",
                                              type: "text",
                                              placeholder:
                                                  "Saisir Prénom Encadrant ici.",
                                            }}
                                            value={projectSupervisorLastName}
                                            disabled={false}
                                            validate={validateSupervisorData}
                                            validationCallback={(res) =>
                                                this.setState({
                                                  hasSupervisorLastNameError: res,
                                                  validateSupervisorData: false,
                                                })
                                            }
                                            onChange={(
                                                projectSupervisorLastName,
                                                e
                                            ) => {
                                              this.setState({
                                                projectSupervisorLastName,
                                              });
                                            }}
                                            onBlur={(e) => {
                                              // // console.log(e);
                                            }}
                                            validationOption={{
                                              name: "Prénom Encadrant",
                                              check: true,
                                              required: true,
                                              customFunc: (
                                                  projectSupervisorLastName
                                              ) => {
                                                if (
                                                    projectSupervisorLastName.length >
                                                    4 &&
                                                    projectSupervisorLastName.length < 51
                                                ) {
                                                  return true;
                                                } else {
                                                  return "Prénom must be between 5 and 50 characters.";
                                                }
                                              },
                                            }}
                                        />
                                      </CCol>
                                    </CRow>
                                    <br/>

                                    <CRow>
                                      <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      Téléphone :
                                    </span>
                                      </CCol>
                                      <CCol md="9" class="bg-warning py-2">

                                        <PhoneInput
                                            country={"tn"}
                                            name="telephone"
                                            placeholder="Téléphone Encadrant"
                                            value={
                                              this.state.projectSupervisorPhoneNumber
                                            }
                                            onChange={(projectSupervisorPhoneNumber) =>
                                                this.setState({
                                                  projectSupervisorPhoneNumber,
                                                })
                                            }
                                            validations={[projectSupervisorPhoneNumber]}
                                        />

                                      </CCol>
                                    </CRow>
                                    <br/>

                                    <CRow>
                                      <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      E-Mail
                                    </span>
                                        &nbsp;
                                        <span className="requiredStar">(*)</span>&nbsp;
                                        <span className="greyFieldLibelleCard">:</span>
                                      </CCol>
                                      <CCol md="9" class="bg-warning py-2">
                                        <Textbox
                                            attributesWrapper={{}}
                                            attributesInput={{
                                              id: "projectSupervisorEmail",
                                              placeholder:
                                                  "Saisir E-Mail Encadrant ici.",
                                              type: "text",
                                            }}
                                            value={projectSupervisorEmail}
                                            disabled={false}
                                            validate={validateSupervisorData}
                                            validationCallback={(res) =>
                                                this.setState({
                                                  hasSupervisorEmailError: res,
                                                  validateSupervisorData: false,
                                                })
                                            }
                                            onChange={(e) => this.verifyExistEncadrantEntreprise(e)}
                                            onBlur={(e) => {
                                              // console.log(e);
                                            }}
                                            validationOption={{
                                              name: "projectSupervisorEmail",
                                              check: true,
                                              required: true,
                                              customFunc: (projectSupervisorEmail) => {
                                                if (
                                                    projectSupervisorEmail.length > 4 &&
                                                    projectSupervisorEmail.length < 51
                                                ) {
                                                  // console.log('------------VAL-1: ' + verifExistEncadEntrep);
                                                  // console.log('------------VAL-2: ' + projectSupervisorEmail);
                                                  // console.log('------------VAL-3: ' + oldSupervMlPass);

                                                  const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

                                                  if (reg.test(String(projectSupervisorEmail).toLowerCase())) {
                                                    if (
                                                        verifExistEncadEntrep === false
                                                        ||
                                                        (verifExistEncadEntrep === true && projectSupervisorEmail === oldSupervMlPass && verifInternalDuplication === 'INIT')
                                                    ) {
                                                      return true;
                                                    }
                                                    if (verifExistEncadEntrep === true && verifInternalDuplication === 'DUPLICATED') {
                                                      return "You have been already register a Supervisor with that mail.";
                                                    }
                                                  }
                                                  {
                                                    return "This mail isn't a valid email address";
                                                  }
                                                } else {
                                                  return "E-Mail Encadrant must be between 5 and 50 characters.";
                                                }
                                              },
                                            }}
                                        />
                                      </CCol>
                                    </CRow>

                                    <br/>

                                    <CButton className="float-right"
                                             onClick={this.addSupervisorProfile}
                                             color="danger">
                                      <CTooltip content="Ajouter Profil Encadrant Entreprise" placement="top">
                                        <span> + </span>
                                      </CTooltip>
                                    </CButton>
                                  </CContainer>
                                </form>
                                <br/><br/>

                                {this.state.successAddSupervProfile && (
                                    <Modal
                                        isOpen={this.state.successAddSupervProfile}
                                        contentLabel="Example Modal"
                                        style={customStyles}
                                    >
                                      <span
                                          className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                      <hr/>
                                      <center>
                                        <br/>
                                        Le profil Encadrant Entreprise est ajouté avec
                                        succès.
                                        <br/>
                                        <br/>
                                        <br/>
                                        <button
                                            className="btn btn-sm btn-primary"
                                            onClick={
                                              this.closeModalAddSupervisorProfile
                                            }
                                        >
                                          Ok
                                        </button>
                                        <br/>
                                        <br/>
                                      </center>
                                    </Modal>
                                )}
                              </CCol>
                              <CCol xs="2"/>
                            </CRow>
                            <br/>
                            <br/>
                            {this.state.supervisorItem.length > 0 && (
                                <CRow>
                                  <CCol md="1"/>
                                  <CCol md="10">
                                    <hr/>
                                    <br/>
                                    <center>
                                  <span className="redTableTitle">
                                    Liste des profils Encadrant(s) Entreprise d'Accueil
                                  </span>
                                    </center>
                                    <br/>

                                    <CDataTable
                                        items={this.state.supervisorItem}
                                        fields={fields}
                                        striped
                                        itemsPerPage={5}
                                        pagination
                                        scopedSlots={{
                                          action: (item, index) => (
                                              <td>
                                                <CButton shape="pill"
                                                         color="primary"
                                                         size="sm"
                                                         aria-expanded="true"
                                                         onClick={() => this.handleModifySupervisorProfile(index, item)}>
                                                  <CTooltip content="Modifier Profil Encadrant Entreprise"
                                                            placement="top">
                                                    <CIcon content={freeSet.cilPencil}/>
                                                  </CTooltip>
                                                </CButton>

                                                &nbsp;&nbsp;
                                                <CButton shape="pill" color="danger" size="sm"
                                                         onClick={() => this.deleteItemSupervisor(index, item.supervEmail)}>
                                                  <CTooltip content="Supprimer Profil Encadrant Entreprise"
                                                            placement="top">
                                                    <CIcon content={freeSet.cilTrash}/>
                                                  </CTooltip>
                                                </CButton>
                                              </td>
                                          )

                                        }}
                                    />

                                    <br/>


                                    <span style={{color: "red"}} className="float-right">
                                  {this.state.supervisorItem.length}
                                </span>

                                    <Dialog fullHight
                                            fullWidth
                                            maxWidth="sm"
                                            open={successOpenSupervisorProfile}
                                            onClose={this.handleCloseSupervisorProfile}
                                            aria-labelledby="form-dialog-title">
                                      <DialogTitle id="form-dialog-title">
                                        <span className="myModalTitle">
                                           Modifier Profil Encadrant Entreprise
                                        </span>
                                        <hr/>
                                      </DialogTitle>
                                      <DialogContent>
                                        <form onSubmit={this.addSupervisorProfile}>
                                          <CContainer>
                                            <CRow>
                                              <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      Nom
                                    </span>
                                                &nbsp;
                                                <span className="requiredStar">(*)</span>&nbsp;
                                                <span className="greyFieldLibelleCard">:</span>
                                              </CCol>
                                              <CCol md="9" class="bg-warning py-2">
                                                <Textbox attributesWrapper={{}}
                                                         attributesInput={{
                                                           id: "supervFN",
                                                           name: "supervFN",
                                                           type: "text",
                                                           placeholder:
                                                               "Saisir Nom Encadrant ici.",
                                                         }}
                                                         value={supervFN}
                                                         disabled={false}
                                                         validate={validateSupervData}
                                                         validationCallback={(res) =>
                                                             this.setState({
                                                               hasSupervFNError: res,
                                                               validateSupervData: false,
                                                             })
                                                         }
                                                         onChange={(supervFN, e) => {
                                                           this.setState({supervFN});
                                                         }}
                                                         onBlur={(e) => {
                                                           // console.log(e);
                                                         }}
                                                         validationOption={{
                                                           name: "Nom Encadrant",
                                                           check: true,
                                                           required: true,
                                                           customFunc: (
                                                               supervFN
                                                           ) => {
                                                             if (
                                                                 supervFN.length >
                                                                 4 &&
                                                                 supervFN.length < 51
                                                             ) {
                                                               return true;
                                                             } else {
                                                               return "Nom must be between 5 and 50 characters.";
                                                             }
                                                           },
                                                         }}
                                                />
                                              </CCol>
                                            </CRow>
                                            <br/>

                                            <CRow>
                                              <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                    Prénom
                                  </span>
                                                &nbsp;
                                                <span className="requiredStar">(*)</span>&nbsp;
                                                <span className="greyFieldLibelleCard">:</span>
                                              </CCol>
                                              <CCol md="9" class="bg-warning py-2">
                                                <Textbox
                                                    attributesWrapper={{}}
                                                    attributesInput={{
                                                      id: "supervLN",
                                                      name: "supervLN",
                                                      type: "text",
                                                      placeholder:
                                                          "Saisir Prénom Encadrant ici.",
                                                    }}
                                                    value={supervLN}
                                                    disabled={false}
                                                    validate={validateSupervData}
                                                    validationCallback={(res) =>
                                                        this.setState({
                                                          hasSupervLNError: res,
                                                          validateSupervData: false,
                                                        })
                                                    }
                                                    onChange={(
                                                        supervLN,
                                                        e
                                                    ) => {
                                                      this.setState({
                                                        supervLN,
                                                      });
                                                    }}
                                                    onBlur={(e) => {
                                                      // console.log(e);
                                                    }}
                                                    validationOption={{
                                                      name: "Prénom Encadrant",
                                                      check: true,
                                                      required: true,
                                                      customFunc: (
                                                          supervLN
                                                      ) => {
                                                        if (
                                                            supervLN.length >
                                                            4 &&
                                                            supervLN.length < 51
                                                        ) {
                                                          return true;
                                                        } else {
                                                          return "Prénom must be between 5 and 50 characters.";
                                                        }
                                                      },
                                                    }}
                                                />
                                              </CCol>
                                            </CRow>
                                            <br/>

                                            <CRow>
                                              <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      Téléphone :
                                    </span>
                                              </CCol>
                                              <CCol md="9" class="bg-warning py-2">

                                                <PhoneInput
                                                    country={"tn"}
                                                    name="telephone"
                                                    placeholder="Téléphone Encadrant"
                                                    value={
                                                      this.state.supervTl
                                                    }
                                                    onChange={(supervTl) =>
                                                        this.setState({
                                                          supervTl,
                                                        })
                                                    }
                                                    validations={[supervTl]}
                                                />

                                              </CCol>
                                            </CRow>
                                            <br/>

                                            <CRow>
                                              <CCol md="3" class="bg-success py-2">
                                    <span className="greyFieldLibelleCard">
                                      E-Mail
                                    </span>
                                                &nbsp;
                                                <span className="requiredStar">(*)</span>&nbsp;
                                                <span className="greyFieldLibelleCard">:</span>
                                              </CCol>
                                              <CCol md="9" class="bg-warning py-2">
                                                <Textbox
                                                    attributesWrapper={{}}
                                                    attributesInput={{
                                                      id: "supervMl",
                                                      placeholder:
                                                          "Saisir E-Mail Encadrant ici.",
                                                      type: "text",
                                                    }}
                                                    value={supervMl}
                                                    disabled={false}
                                                    validate={validateSupervData}
                                                    validationCallback={(res) =>
                                                        this.setState({
                                                          hasSupervMlError: res,
                                                          validateSupervData: false,
                                                        })
                                                    }
                                                    onChange={(e) => this.verifyExistEncadrantEntreprisePopup(e)}
                                                    onBlur={(e) => {
                                                      // console.log(e);
                                                    }}
                                                    validationOption={{
                                                      name: "supervMl",
                                                      check: true,
                                                      required: true,
                                                      customFunc: (supervMl) => {
                                                        if (
                                                            supervMl.length > 4 &&
                                                            supervMl.length < 51
                                                        ) {
                                                          // console.log('------------VAL-1: ' + verifExistEncadEntrepPopup);
                                                          // console.log('------------VAL-2: ' + supervMl);
                                                          // console.log('------------VAL-3: ' + oldSupervMlPass);

                                                          const reg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

                                                          if (reg.test(String(supervMl).toLowerCase())) {
                                                            if (
                                                                verifExistEncadEntrepPopup === false
                                                                ||
                                                                (verifExistEncadEntrepPopup === true && supervMl === oldSupervMlPass)
                                                            ) {
                                                              return true;
                                                            }
                                                            if (verifExistEncadEntrepPopup === true) {
                                                              return "You have been already register a Supervisor with that mail.";
                                                            }
                                                          }
                                                          if (!reg.test(String(supervMl).toLowerCase())) {
                                                            return "This mail isn't a valid email address";
                                                          }

                                                        } else {
                                                          return "E-Mail Encadrant must be between 5 and 50 characters.";
                                                        }
                                                      },
                                                    }}
                                                />
                                              </CCol>

                                            </CRow>
                                            <br/><br/><br/>

                                            <CRow>
                                              <CCol md="10">
                                                <CButton className="float-right"
                                                         color="primary"
                                                         onClick={this.changeSupervisorProfile}>
                                                  Modifier Profil
                                                </CButton>
                                              </CCol>
                                              <CCol md="2">
                                                <CButton onClick={this.handleCloseSupervisorProfile} color="danger"
                                                         className="float-right">
                                                  Non
                                                </CButton>
                                              </CCol>
                                            </CRow>
                                            <br/>
                                          </CContainer>
                                        </form>
                                      </DialogContent>
                                    </Dialog>

                                    <Dialog
                                        fullHight
                                        fullWidth
                                        maxWidth="sm"
                                        open={successModifySupervisorProfile}
                                        onClose={this.handleCloseSuccessModifySupervisorProfile}
                                        aria-labelledby="form-dialog-title">
                                      <DialogTitle id="form-dialog-title">
                                        <span
                                            className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                        <hr/>
                                      </DialogTitle>
                                      <DialogContent>
                                        <CRow>
                                          <CCol xs="12">
                                            <center>
                                              <br/>
                                              Le profil de l'encadrant a été mis à jour avec succès.
                                              <br/><br/><br/>
                                            </center>
                                          </CCol>
                                        </CRow>
                                      </DialogContent>
                                      <DialogActions>
                                        <Button onClick={this.handleCloseSuccessModifySupervisorProfile}
                                                color="primary">
                                          Ok
                                        </Button>
                                      </DialogActions>
                                    </Dialog>

                                  </CCol>
                                  <CCol md="1"/>
                                </CRow>
                            )}
                            <br/><br/>
                          </Accordion>
                        </div>
                      </Panel>
                  )}
                  {this.state.step + 1 === 4 && (
                      <Panel>
                        <div>
                          <strong>
                            <span className="clignoteRedStepTitle">Confirmer mon Plan de Travail</span>
                          </strong>
                          <br/><br/><br/>

                          <CContainer>
                            <CRow>
                              <CCol md="2"/>
                              <CCol md="8" class="py-2">
                                <Card border="primary">
                                  <Card.Header
                                      expand
                                      style={{
                                        color: "#fff",
                                        background: "blue",
                                        fontWeight: "bold",
                                        fontSize: "15px",
                                      }}
                                  >
                                    Étudiant Ingénieur
                                  </Card.Header>
                                  <Card.Body>
                                    <Card.Subtitle className="mb-2 text-muted">
                                      {this.state.userToBeUpdated.nomet} &nbsp;
                                      {this.state.userToBeUpdated.prenomet}
                                    </Card.Subtitle>
                                    <Card.Text>
                                      <CRow>
                                        <CCol md="3" class="bg-success py-2">
                              <span className="greyFieldLibelleCard">
                                E-Mail :
                              </span>
                                        </CCol>
                                        <CCol md="9" class="bg-warning py-2"><span className="infoField">
                                          {this.state.uTBUNewMail === "" && (
                                              <>{this.state.userToBeUpdated.emailet}</>
                                          )}
                                          {this.state.uTBUNewMail !== "" && (
                                              <>{this.state.uTBUNewMail}</>
                                          )}</span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="3" class="bg-success py-2">
                              <span className="greyFieldLibelleCard">
                                Adresse :
                              </span>
                                        </CCol>
                                        <CCol md="9" class="bg-warning py-2"><span className="infoField">
                                          {this.state.uTBUAddress === "" && (
                                              <>
                                                {this.state.userToBeUpdated.adresseet}
                                              </>
                                          )}
                                          {this.state.uTBUAddress !== "" && (
                                              <>{this.state.uTBUAddress}</>
                                          )}</span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="3" class="bg-success py-2">
                              <span className="greyFieldLibelleCard">
                                Numéro Téléphone :
                              </span>
                                        </CCol>
                                        <CCol md="9" class="bg-warning py-2"><span className="infoField">
                                          {this.state.uTBUPhone === "" && (
                                              <>{this.state.userToBeUpdated.telet}</>
                                          )}
                                          {this.state.uTBUPhone !== "" && (
                                              <>{this.state.uTBUPhone}</>
                                          )}</span>
                                        </CCol>
                                      </CRow>
                                    </Card.Text>
                                  </Card.Body>
                                </Card>
                              </CCol>
                              <CCol md="2"/>
                            </CRow>

                            <br/>
                            <CRow>
                              <CCol md="1"/>
                              <CCol md="10" class="py-2">
                                <Card border="danger">
                                  <Card.Header
                                      expand
                                      style={{
                                        color: "#fff",
                                        background: "#cc0000",
                                        fontWeight: "bold",
                                        fontSize: "15px",
                                      }}
                                  >
                                    Stage PFE
                                  </Card.Header>
                                  <Card.Body>
                                    <Card.Subtitle className="redSubTitleCard">
                                      Entreprise d'Accueil
                                    </Card.Subtitle>
                                    <Card.Text>

                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">
                                            Libellé :
                                          </span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                            {this.state.projectCompany.designation}
                                          </span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">
                                            Secteur Activité:
                                          </span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                            {
                                              this.state.projectCompany.sectorEntrepriseDto === null ?
                                                  <>--</>
                                                  :
                                                  <>{this.state.projectCompany.sectorEntrepriseDto.libelleSecteur}</>
                                            }
                                          </span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">
                                            Siège Social :
                                          </span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                              {
                                                this.state.projectCompany.siegeSocial === "---" ?
                                                    <>--</>
                                                    :
                                                    <>{this.state.projectCompany.siegeSocial}</>
                                              }
                                          </span>
                                        </CCol>
                                      </CRow>

                                      <br/>
                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">
                                            E-Mail :
                                          </span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                            {this.state.projectCompany.addressMail}
                                          </span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">
                                            Téléphone :
                                          </span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                            {
                                              this.state.projectCompany.telephone === "--------" ?
                                                  <>--</>
                                                  :
                                                  <>{this.state.projectCompany.telephone}</>
                                            }
                                          </span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">Fax :</span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                            {
                                              this.state.projectCompany.fax === "--------" ?
                                                  <>--</>
                                                  :
                                                  <>{this.state.projectCompany.fax}</>
                                            }
                                          </span>
                                        </CCol>
                                      </CRow>

                                      <CRow>
                                        <CCol md="2" class="bg-success py-2">
                                          <span className="greyFieldLibelleCard">
                                            Adresse :
                                          </span>
                                        </CCol>
                                        <CCol md="10" class="bg-warning py-2">
                                          <span className="infoField">
                                            {this.state.projectCompany.address}
                                            {
                                              this.state.projectCompany.paysDto === null ?
                                                  <></>
                                                  :
                                                  <>, {this.state.projectCompany.paysDto.nomPays}</>
                                            }
                                          </span>
                                        </CCol>
                                      </CRow>
                                    </Card.Text>
                                    <br/>
                                    <Card.Subtitle className="redSubTitleCard">
                                      Encadrant(s) Entreprise d'Accueil - {this.state.supervisorItem.length}
                                    </Card.Subtitle>
                                    <br/>
                                    <Card.Text>
                                      {this.state.supervisorItem.map(
                                          (data) =>
                                              data.supervFirstName.trim() !== "" && (
                                                  <div>
                                                    <span className="redSubTitleCard"> - </span>
                                                    <span className="redFieldCard">
                                                      {data.supervFirstName} {data.supervLastName}
                                                    </span>

                                                    <CRow>
                                                      <CCol md="2" class="bg-success py-2">
                                                        <span className="greyFieldLibelleCard">
                                                          &nbsp;&nbsp;&nbsp;Téléphone :
                                                        </span>
                                                      </CCol>
                                                      <CCol md="8" class="bg-warning py-2">
                                                        <span className="infoField">
                                                          {
                                                            data.supervPhoneNumber === null ?
                                                                <>--</>
                                                                :
                                                                <>{data.supervPhoneNumber}</>
                                                          }
                                                        </span>
                                                      </CCol>
                                                    </CRow>

                                                    <CRow>
                                                      <CCol md="2" class="bg-success py-2">
                                                        <span className="greyFieldLibelleCard">
                                                          &nbsp;&nbsp;&nbsp;E-Mail :
                                                        </span>
                                                      </CCol>
                                                      <CCol md="8" class="bg-warning py-2">
                                                        <span className="infoField"> {data.supervEmail}</span>
                                                      </CCol>
                                                    </CRow>
                                                  </div>
                                              )
                                      )}
                                    </Card.Text>
                                  </Card.Body>
                                </Card>
                              </CCol>
                              <CCol md="1"/>
                            </CRow>
                            <br/>
                            <CRow>
                              <CCol md="1"/>
                              <CCol md="10" class="py-2">
                                <Card border="warning">
                                  <Card.Header
                                      expand
                                      style={{
                                        color: "#fff",
                                        background: "orange",
                                        fontWeight: "bold",
                                        fontSize: "15px",
                                      }}
                                  >
                                    Plan Travail - {this.state.projectTitle}
                                  </Card.Header>
                                  <Card.Body>
                                    <Card.Subtitle className="orangeSubTitleCard">
                                      Problématique(s) - {this.state.problemItem.length - 1}
                                    </Card.Subtitle>
                                    <Card.Text>
                                      {this.state.problemItem.map(
                                          (data) =>
                                              data.probLibelle.trim() !== "" && (
                                                  <div>
                                                    <span className="orangeSubTitleCard">- </span>
                                                    <span className="infoField">{data.probLibelle}</span>
                                                  </div>
                                              )
                                      )}
                                    </Card.Text>
                                    <br/>
                                    <Card.Subtitle className="orangeSubTitleCard">
                                      Fonctionnalité(s) / Tâche(s) -
                                      {this.state.functionnalityItem.length - 1}
                                    </Card.Subtitle>
                                    <Card.Text>
                                      {this.state.functionnalityItem.map(
                                          (data) =>
                                              data.funcLibelle.trim() !== "" && (
                                                  <div>
                                                    <span className="orangeSubTitleCard">- </span>
                                                    <span className="infoField">{data.funcLibelle}</span>
                                                  </div>
                                              )
                                      )}
                                    </Card.Text>
                                    <br/>
                                    <Card.Subtitle className="orangeSubTitleCard">
                                      Technologie(s) -
                                      {this.state.listSelectedLibelleTechnologies.length}
                                    </Card.Subtitle>
                                    <Card.Text>
                                      {this.state.listSelectedLibelleTechnologies.map(
                                          (data) =>
                                              data.trim() !== "" && (
                                                  <div>
                                                    <span className="orangeSubTitleCard">- </span>
                                                    <span className="infoField">{data}</span>
                                                  </div>
                                              )
                                      )}
                                    </Card.Text>
                                    <br/>
                                    <Card.Subtitle className="orangeSubTitleCard">
                                      Diagramme de Gantt
                                    </Card.Subtitle>
                                    <Card.Text>
                                      {
                                        diagramGanttFullPath.length !== 0 &&
                                        <span className="infoField">{this.gotGanttDiagLabelFromAutoLabel(diagramGanttFullPath)}</span>
                                      }
                                    </Card.Text>
                                  </Card.Body>
                                </Card>
                              </CCol>
                              <CCol md="1"/>
                            </CRow>

                          </CContainer>

                          <br/>

                          <hr/>
                          <center>
                            {this.state.showDeposeButton && !loadingSaveFichePFE && (
                                <button
                                    onClick={() => this.deposerMaFichePFE()}
                                    className="btn btn-info"
                                >
                                  Sauvegarder Plan Travail
                                </button>
                            )}

                            {(
                                loadingSaveFichePFE &&
                                <Spinner animation="grow" variant="info"/>
                            )}

                            {this.state.showSauvegardeButton && !loadingConfirmFichePFE && (
                                <button
                                    onClick={() => this.sauvegarderMaFichePFE()}
                                    className="btn btn-success"
                                >
                                  Confirmer Plan Travail
                                </button>
                            )}

                            {(
                                loadingConfirmFichePFE &&
                                <Spinner animation="grow" variant="success"/>
                            )}
                          </center>

                          <br/>
                          <br/>

                          <CRow>
                            <CCol md="6">
                              <CAlert color="info">
                                <h6 className="clignoteBlue"><strong>Étape 1: Sauvegarder Plan Travail</strong></h6>
                                <span className="noteFichePFE">En cliquant sur le bouton </span> &nbsp;&nbsp;
                                <CButton
                                    disabled="true"
                                    size="sm"
                                    className="btn btn-info"
                                >
                                  Sauvegarder Plan Travail
                                </CButton>
                                &nbsp;&nbsp;
                                <span className="noteFichePFE">
                            , vous allez <ins>uniquement</ins> sauvegarder une première version
                            de votre Plan de Travail. <br/><ins>Cepanadant</ins>, votre Encadrant Académique ne pourra pas y accéder.
                          </span>
                              </CAlert>
                            </CCol>
                            <CCol md="6">
                              <CAlert color="success">
                                <h6 className="clignoteGreen"><strong>Étape 2: Confirmer Plan Travail</strong></h6>
                                <span className="noteFichePFE">En cliquant sur le bouton </span> &nbsp;&nbsp;
                                <CButton
                                    disabled="true"
                                    size="sm"
                                    className="btn btn-success"
                                >
                                  Confirmer Plan Travail
                                </CButton>
                                &nbsp;&nbsp;
                                <span className="noteFichePFE">
                          , vous prouvez <ins>définitivement</ins> votre Plan de Travail
                          et donc la remise de votre document est confirmée. <br/>Ainsi,
                          votre Encadrant Académique pourra le traiter.
                    </span>
                              </CAlert>
                            </CCol>
                          </CRow>

                          {this.state.successConfirmFichePFE && (
                              <Modal
                                  isOpen={this.state.successConfirmFichePFE}
                                  contentLabel="Example Modal"
                                  style={customStyles}
                              >
                                <span
                                    className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                <hr/>
                                <center>
                                  <br/>
                                  Vous avez sauvegardé votre Plan de Travail.
                                  <br/>
                                  <br/>
                                  Voulez-vous le confirmer ?.
                                  <br/>
                                  <br/>
                                  <br/>
                                  <button
                                      className="btn btn-sm btn-success"
                                      onClick={this.closeModalOk}
                                  >
                                    Oui
                                  </button>
                                  &nbsp;&nbsp;
                                  <Link to={"/modifyESPFile"}>
                                    <button
                                        className="btn btn-sm btn-danger"
                                        onClick={this.closeModalSuccessConfirmFichePFE}
                                    >
                                      Pas Maintenant
                                    </button>
                                  </Link>
                                  <br/>
                                  <br/>
                                </center>
                              </Modal>
                          )}

                          {this.state.successConfirmSauvegarderFichePFE && (
                              <Modal
                                  isOpen={this.state.successConfirmSauvegarderFichePFE}
                                  contentLabel="Example Modal"
                                  style={customStyles}
                              >
                                <span
                                    className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                <hr/>
                                <center>
                                  <br/>
                                  Enregistrement réalisé.
                                  <br/>
                                  <br/>
                                  <br/>
                                  <button
                                      className="btn btn-sm btn-primary"
                                      onClick={
                                        this.closeModalSuccessConfirmSauvegardFichePFE
                                      }
                                  >

                                    Ok
                                  </button>
                                  <br/>
                                  <br/>
                                </center>
                              </Modal>
                          )}

                          {this.state.errorConfirmSauvegarderFichePFE &&
                          <Modal
                              isOpen={this.state.errorConfirmSauvegarderFichePFE}
                              contentLabel="Example Modal"
                              style={customStyles}
                          >
                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                            <hr/>
                            <center>
                              <br/>
                              Vous avez dépassé plus de 50000 charactères !.
                              <br/>
                              Merci d'essayer de nouveau.
                              <br/>
                              <br/>
                              <button
                                  className="btn btn-sm btn-primary"
                                  onClick={
                                    this.closeModalErrorConfirmSauvegardFichePFE
                                  }
                              >
                                Ok
                              </button>
                              <br/>
                              <br/>
                            </center>
                          </Modal>
                          }


                        </div>
                      </Panel>
                  )}

                  <br/><br/>
                  <hr/>
                  <CRow>
                    <CCol xs="1">
                      <Button onClick={onPrevious} disabled={this.state.step === 0}>
                        Précédant
                      </Button>
                    </CCol>
                    <CCol xs="10"/>
                    <CCol xs="1">
                      <Button onClick={onNext} className="float-right"
                              disabled={
                                this.state.step === 3
                                ||
                                (
                                    this.state.step === 0 &&
                                    (
                                        this.state.verifyuTBUNewMail === "no" ||
                                        this.state.uTBUAddress.trim() === "" ||
                                        this.state.uTBUPhone === "" ||
                                        this.state.verifyuTBUPhone === "no" ||
                                        this.state.verifyuTBUPhoneLimit === "yes" ||
                                        this.state.verifyPairField === "NO" ||
                                        studentFullName.includes("Please")
                                    )
                                )
                                ||
                                (
                                    this.state.step === 1 &&
                                    (
                                        this.state.projectTitle.trim() === "" ||
                                        this.state.problemItem.length - 1 === 0 ||
                                        this.state.functionnalityItem.length - 1 === 0 ||
                                        this.state.selectedTechnologies.length === 0 ||
                                        this.state.projectTitle.length > 100 ||
                                        this.state.projectTitle.length < 10 ||
                                        (
                                            this.state.projectDescription.length !== 0 &&
                                            (
                                                this.state.projectDescription.length < 10 ||
                                                this.state.projectDescription.length > 500
                                            )
                                        )
                                        ||
                                        diagramGanttFullPath.length === 0
                                    )
                                )
                                ||
                                (this.state.step === 2 && this.state.supervisorItem.length === 0)
                              }>
                        Suivant
                      </Button>
                    </CCol>
                  </CRow>
                </div>
            )}

          {fichePFEStatus !== "" && fichePFEStatus === "01" && (  // FichePFE SAUVEGARDEE
              <>
                <br/><br/>

                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <br/>
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/><br/>
                          Vous avez déjà sauvegardé votre Plan de Travail.
                          <br/>
                          Maintenant, vous devez le &nbsp;
                          <Link to={"/modifyESPFile"}>
                            <span className="redMarkAlert">
                              <ins>confirmer</ins>
                            </span>
                          </Link>
                          .
                          <br/>
                          <br/>
                          <br/>
                        </center>
                      </CCardBody>
                      <CCardFooter>
                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                      Today is {currentDay}
                    </span>
                      </CCardFooter>
                    </CCard>
                  </CCol>
                  <CCol md="2"/>
                </CRow>

              </>
          )}

          {fichePFEStatus !== "" && fichePFEStatus === "02" && (  // FichePFE DEPOSEE
              <>
                <br/><br/>

                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <br/><br/>
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/>
                          <br/>
                          La remise de votre Plan de Travail a été effectuée.
                          <br/>
                          Le traitement de votre requête
                          &nbsp;
                          <span className="clignoteRed">
                        est en cours
                      </span>
                          &nbsp; .
                          <br/>
                          <br/>
                          <br/>
                        </center>
                      </CCardBody>
                      <CCardFooter>
                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                      Today is {currentDay}
                    </span>
                      </CCardFooter>
                    </CCard>
                  </CCol>
                  <CCol md="2"/>
                </CRow>

              </>
          )}

          {(
              (
                  fichePFEStatus !== "" &&
                  (fichePFEStatus === "03" || fichePFEStatus === "06" || fichePFEStatus === "07" || fichePFEStatus === "08")
              ) &&
              <>
                <br/><br/>

                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <br/>
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/><br/>
                          Vous n'êtes pas autorisés à déposer un nouveau Plan de Travail.
                          <br/>
                          Actuellement, il est à l'état &nbsp;
                          <span className="clignoteRed">
                            {libEtatFiche}
                          </span>
                          &nbsp;.
                          <br/><br/>
                          Vous pouvez consulter &nbsp;
                          <Link to={"/consultESPFile"}>
                            <span className="redMarkAlert">
                              <ins>l'historique de votre Dépôt</ins>
                            </span>
                          </Link>
                          &nbsp;.
                          <br/><br/>
                          <br/>
                        </center>
                      </CCardBody>
                      <CCardFooter>
                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                      Today is {currentDay}
                    </span>
                      </CCardFooter>
                    </CCard>
                  </CCol>
                  <CCol md="2"/>
                </CRow>

              </>
          )}

          <br/>
          <br/>
        </div>
    );
  }
}
