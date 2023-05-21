import React, {Component} from "react";
import { connect } from 'react-redux';

import Form from "react-validation/build/form";
import {Link} from "react-router-dom";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../services/auth.service";
import Button from "@material-ui/core/Button";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import Modal from "react-modal";
import {isEmail, isInt} from "validator";

import {CCard, CCardBody, CCardFooter, CCol, CLink, CRow, CTooltip} from "@coreui/react";
import Spinner from "react-bootstrap/Spinner";

import PhoneInput from "react-phone-input-2";
import "react-phone-input-2/lib/style.css";
import Select from "react-select";

import makeAnimated from "react-select/animated";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import PrintIcon from '@material-ui/icons/Print';
import PhoneEnabledIcon from '@material-ui/icons/PhoneEnabled';

import "../css/style.css";
import StudentService from "../services/student.service";
import {updateConventionForRSS} from "../../redux/slices/ConventionSlice";
import {queryApi} from "../../utils/queryApi";

import axios from "axios";

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();


const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;
const API_URL_RSS = process.env.REACT_APP_API_URL_RSS;

const animatedComponents = makeAnimated();

const currentStudent = AuthService.getCurrentStudent();



const customStyles = {
  content: {
    top: "50%",
    left: "60%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "600px",
  },
};

const customStylesMANC = {
  content: {
    top: "5%",
    left: "25%",
    right: "20%",
  },
  overlay: {
    background: "#e6e6e6",
  },
};

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required !.
      </div>
    );
  }
};

const adresse = (value) => {
  if (value !== "") {
    if (value.length < 6 || value.length > 200) {
      return (
        <div className="alert alert-danger" role="alert">
          The adresse must be between 6 and 200 characters.
        </div>
      );
    }
  }
};

const responsable = (value) => {
  if (value.length < 6 || value.length > 100) {
    return (
      <div className="alert alert-danger" role="alert">
        The responsable must be between 6 and 100 characters.
      </div>
    );
  }
};

const projectCompanyName = (value) => {
  if (value.length < 5 || value.length > 25) {
    return (
      <div className="alert alert-danger" role="alert">
        The projectCompanyName must be between 5 and 25 characters.
      </div>
    );
  }
};

//
const projectCompanyAddress = (value) => {
  if (value.length < 10 || value.length > 150) {
    return (
      <div className="alert alert-danger" role="alert">
        The projectCompanyAddress must be between 10 and 150 characters.
      </div>
    );
  }
};

//
const projectCompanyEmail = (value) => {
  if (!isEmail(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        It is not a valid projectCompanyEmail form.
      </div>
    );
  }
};

//
const projectCompanyPhone = (value) => {
  if (value !== "") {
    if (!isInt(value)) {
      return (
        <div className="alert alert-danger" role="alert">
          It is not a valid projectCompanyPhone form.
        </div>
      );
    }
  }
};

//
const projectCompanySiegeSociale = (value) => {
  if (value !== "") {
    if (value.length < 5 || value.length > 50) {
      return (
        <div className="alert alert-danger" role="alert">
          The projectCompanySiegeSociale must be between 5 and 50 characters.
        </div>
      );
    }
  }
};

//
const projectCompanyFax = (value) => {
  if (value !== "") {
    if (!isInt(value)) {
      return (
        <div className="alert alert-danger" role="alert">
          It is not a valid projectCompanyFax form.
        </div>
      );
    }
  }
};

//
const projectCompanyPhoneMin = (value) => {
  if (value !== "") {
    if (value.length < 8) {
      return (
        <div className="alert alert-danger" role="alert">
          The projectCompanyPhone must be at least with 8 numbers.
        </div>
      );
    }
  }
};

//
const projectCompanyFaxMin = (value) => {
  if (value !== "") {
    if (value.length < 8) {
      return (
        <div className="alert alert-danger" role="alert">
          The projectCompanyPhone must be at least with 8 numbers.
        </div>
      );
    }
  }
};

const dialogStyles = {
  dialogPaper: {
    position: 'absolute',
    left: 10,
    right: 50,
    top: 50
  }
};


//
export default class ModifyAgreementByRSS extends Component {
  constructor(props) {
    super(props);

    this.handleApplyForAddConvention = this.handleApplyForAddConvention.bind(this);
    this.handleApplyForAddConvention1 = this.handleApplyForAddConvention1.bind(this);
    this.handleApplyForUpdateConvention = this.handleApplyForUpdateConvention.bind(this);
    this.onChangeMail = this.onChangeMail.bind(this);
    this.onChangeDateDebut = this.onChangeDateDebut.bind(this);
    this.onChangeDateFin = this.onChangeDateFin.bind(this);
    this.onChangeSociete = this.onChangeSociete.bind(this);
    this.onChangeAdresse = this.onChangeAdresse.bind(this);
    this.onChangeTelephone = this.onChangeTelephone.bind(this);
    this.onChangeResponsable = this.onChangeResponsable.bind(this);
    this.closeModalOk = this.closeModalOk.bind(this);
    this.closeModalSuccessConfirmAddConvention = this.closeModalSuccessConfirmAddConvention.bind(this);
    this.closeModalFailConfirmAddConvention = this.closeModalFailConfirmAddConvention.bind(this);
    this.closeModalSuccessConfirmUpdateConvention = this.closeModalSuccessConfirmUpdateConvention.bind(this);
    this.onChangeSelectProjectCompany = this.onChangeSelectProjectCompany.bind(this);
    this.openModalAddCompany = this.openModalAddCompany.bind(this);
    this.closeModalAddCompany = this.closeModalAddCompany.bind(this);
    this.onChangeSelectProjectPays = this.onChangeSelectProjectPays.bind(this);
    this.onChangeSelectProjectStates = this.onChangeSelectProjectStates.bind(this);
    this.onChangeSelectProjectSector = this.onChangeSelectProjectSector.bind(this);
    this.addProjectCompany = this.addProjectCompany.bind(this);
    this.onChangeProjectCompanyName = this.onChangeProjectCompanyName.bind(this);
    this.onChangeProjectCompanyAddress = this.onChangeProjectCompanyAddress.bind(this);
    this.onChangeProjectCompanyEmail = this.onChangeProjectCompanyEmail.bind(this);
    this.onChangeSupervisorCompanyEmail = this.onChangeSupervisorCompanyEmail.bind(this);
    this.onChangeProjectCompanyPhone = this.onChangeProjectCompanyPhone.bind(this);
    this.onChangeProjectCompanyFax = this.onChangeProjectCompanyFax.bind(this);
    this.onChangeProjectCompanySiegeSociale = this.onChangeProjectCompanySiegeSociale.bind(this);
    this.onChangeJustifCancelAffectation = this.onChangeJustifCancelAffectation.bind(this);
    this.handleOpenPopupJustifyCancelAgreement = this.handleOpenPopupJustifyCancelAgreement.bind(this);
    this.handleCancelAgreement = this.handleCancelAgreement.bind(this);
    this.handleClosePopupCancelAgreement = this.handleClosePopupCancelAgreement.bind(this);
    this.handleClosePopupJustifyDeleteAffectation = this.handleClosePopupJustifyDeleteAffectation.bind(this);

    // console.log('--------------- currentStudent: ' + currentStudent.id);

    this.state = {
      successConfirmAddConvention: false,
      failConfirmAddConvention: false,
      ok: false,
      loading: false,
      successConfirmUpdateConvention: false,
      mail: "",
      dateDebut: new Date(),
      verifyEmptyDateDebut: "init",
      dateFin: null,
      verifyEmptyDateFin: "init",
      dateFourMonths: null,
      societe: "",
      adresse: "",
      telephone: "",
      responsable: "",
      currentStudent: {email: ""},
      dateConvention: "",
      convention: {},
      traitementStatus: "",
      fichePFEStatus: "",
      allCompanies: [{value: "", label: "", color: ""}],
      showModalAddNewCompany: false,
      showModalAddNewCompanySuccess: false,
      allSectors: [{value: "", label: "", color: ""}],
      selectedSectors: [{value: "", label: ""}],

      selectedProjectSector: {},

      allPays: [{value: "", label: "", color: ""}],
      selectedPays: [{value: "", label: ""}],
      selectedProjectPays: {},
      companyPaysTBA:"",

      allStates: [],
      selectedState: null,

      existEntreprise: "INIT",
      duplicateCompany: "INIT",
      traineeDuration: "",
      currentDay: "",

      justifCancelAgreement: "",
      mailEncadrantEntreprise: "",
      mailEncadrantEntrepriseVerify: "",
      openPopupJustifyCancelAgreement: false,
      loadCancelAgreement: false,
      openPopupSuccessCancelAgreement: false,

      studentId: "",
      companyLibProject: {},
      companySectorProject: {},
      companyPaysProject: {},
      companyGovProject: {},
      allConvsRSS: [],
      currentConv: {}
    };

    this.state.studentId = sessionStorage.getItem("studentId"); //"193JMT0926";

    let currentDt = new Date();
    this.state.currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();


    this.state.traineeDuration = 3;

    var requestc = new XMLHttpRequest();
    requestc.open(
      "GET",
      API_URL_MESP + "studentMailEsp/" + this.state.studentId,
      false
    );
    requestc.send(null);
    this.state.mail = requestc.responseText;

    if (this.state.mail === null) {
      this.state.mail = "--";
    }

    // console.log('----------------------> SARS: ' + this.state.mail);
    // console.log('---------init------ cvn: ' + this.state.successConfirmAddConvention);

    var myDate = new Date();
    // console.log('1. -------------------> DATE: ' + d);
    myDate.setMonth(myDate.getMonth() + this.state.traineeDuration);
    // console.log('2. -------------------> DATE: ' + myDate);

    this.state.dateFourMonths = myDate;

    var requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "conventionDto/" + this.state.studentId,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestb.send(null);
    this.state.convention = JSON.parse(requestb.responseText);
    // console.log('------------init------------- convention entity: ' + this.state.convention.nomSociete);

    this.state.dateConvention = this.state.convention.dateConvention;

    // console.log('-------------init------------ convention id: ' + this.state.dateConvention);

    if (this.state.dateConvention !== undefined) {
      this.state.dateDebut = new Date(this.state.convention.dateDebut);
      this.state.dateFin = new Date(this.state.convention.dateFin);
      //console.log('-------------init-----###------- Status convention: ' + this.state.traitementStatus + " - " + this.state.convention.traiter);
    }

    var requestfp = new XMLHttpRequest();
    requestfp.open(
      "GET",
      API_URL_STU + "fichePFEStatus/" + this.state.studentId,
      false
    );//API_URL_FESP
    requestfp.send(null);

    let fichePFE = requestfp.responseText;
    this.state.fichePFEStatus = fichePFE.etat;
    // console.log('-------------init------------ fichePFEStatus: ' + this.state.fichePFEStatus);

    this.state.traitementStatus = this.state.convention.traiter;
    // console.log('-------------$$$------------ traiter convention: ' + this.state.traitementStatus);

    //  Load list of companies
    let strIntoObjComp = [];
    var requestComp = new XMLHttpRequest();
    requestComp.open(
      "GET",
      API_URL_MESP + "allCompanies",
      false
    ); // `false` makes the request synchronous
    requestComp.send(null);
    strIntoObjComp = JSON.parse(requestComp.responseText);

    strIntoObjComp.forEach((comp) => {
      // console.log('-------------- Verify companies: ' + comp);
      this.state.allCompanies.push({
        value: comp,
        label: comp,
        color: "#00B8D9",
      });
    });

    if (this.state.allCompanies[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.allCompanies.splice(0, 1);
    }


    /************************************************************************** For Modif ***/

    this.state.projectCompanyName = this.state.convention.companyDto.designation;
    this.state.projectCompanyEmail = this.state.convention.companyDto.addressMail;
    this.state.projectCompanySiegeSociale = this.state.convention.companyDto.siegeSocial;
    this.state.projectCompanyPhone = this.state.convention.companyDto.telephone;
    this.state.projectCompanyAddress = this.state.convention.companyDto.address;
    this.state.projectCompanyFax = this.state.convention.companyDto.fax;

    // console.log('--10------------------------------ Convention: ', this.state.convention);
    // console.log('....10....projectCompanyName: ', this.state.projectCompanyName);
    // console.log('....10....projectCompanyEmail: ', this.state.projectCompanyEmail);
    // console.log('....10....projectCompanySiegeSociale: ', this.state.projectCompanySiegeSociale);
    // console.log('....10....projectCompanyPhone: ', this.state.projectCompanyPhone);
    // console.log('....10....projectCompanyAddress: ', this.state.projectCompanyAddress);
    // console.log('....10....projectCompanyFax: ', this.state.projectCompanyFax);


    /**---------- Recuperate Company Label ----------**/
    let compDesignation = this.state.convention.companyDto.designation;
    // console.log('-----------------------> 0905 SimpleFormExample.js: ' + compDesignation);
    this.state.companyLibProject = {value: compDesignation, label: compDesignation, color: "#00B8D9"};
    // console.log('----------MIKI-------------> 0905 compDesignation: ' , this.state.companyLibProject);
    this.state.companyPaysProject = {value: compDesignation, label: compDesignation, color: "#00B8D9"};
    // console.log('----------MIKI-------------> 0905 compDesignation: ' , this.state.companyLibProject);

    /**---------- Recuperate Company Sector ----------**/
    // console.log('----------SECTOR-------------> 0905 compDesignation: ' , this.state.convention.companyDto);
    if(this.state.convention.companyDto.sectorEntrepriseDto !== null)
    {
      let compSector = this.state.convention.companyDto.sectorEntrepriseDto.libelleSecteur;
      // console.log('------------NONO-----------> 1 compSector: ' + compSector);
      this.state.companySectorProject = {value: compSector, label: compSector, color: "#00B8D9"};
      // console.log('-----------MIKI------------> 2 compSector: ' , this.state.companySectorProject);

      let requestFS = new XMLHttpRequest();
      requestFS.open(
        "GET",
        API_URL_MESP + "sectors/" + compSector,
        false
      ); // `false` makes the request synchronous
      requestFS.send(null);

      this.state.selectedProjectSector = JSON.parse(requestFS.responseText);

    }
    // companySectorProject

    /**---------- Recuperate Company Pays ----------**/
    let compPays = this.state.convention.companyDto.paysDto.nomPays;
    // console.log('-----------------------> 0905 SimpleFormExample.js: ' + compPays);
    this.state.companyPaysProject = {value: compPays, label: compPays, color: "#00B8D9"};
    // console.log('----------LOOL-------------> 0905 compPays: ' , this.state.companyPaysProject);
    // console.log('--------------------LOOL-----1----> PAYS 0: ',this.state.selectedProjectPays);

    var requestFS = new XMLHttpRequest();
    requestFS.open(
      "GET",
      API_URL_MESP + "pays/" + compPays,
      false
    ); // `false` makes the request synchronous
    requestFS.send(null);
    this.state.selectedProjectPays= JSON.parse(requestFS.responseText);
    // console.log('----------LOOL-------------> 0905 selectedProjectPays: ' , this.state.selectedProjectPays);



    this.state.adresse = this.state.convention.companyDto.address;
    this.state.responsable = this.state.convention.responsable;
    this.state.telephone = this.state.convention.telephone;

    this.state.societe = this.state.companyLibProject.value;

    // console.log('-----------------------> 0905 telephone: ' , this.state.telephone);

    /***************************************************************************/


    //  Load list of sectors
    let strIntoObjSector = [];
    var requestSector = new XMLHttpRequest();
    requestSector.open("GET", API_URL_MESP + "sectors", false); // `false` makes the request synchronous
    requestSector.send(null);
    strIntoObjSector = JSON.parse(requestSector.responseText);

    strIntoObjSector.forEach((sector) => {
      // console.log('-------------- Verify sectors: ' + sector);
      this.state.allSectors.push({
        value: sector,
        label: sector,
        color: "#00B8D9",
      });
    });

    if (this.state.allSectors[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.allSectors.splice(0, 1);
    }

    if (this.state.selectedSectors[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.selectedSectors.splice(0, 1);
    }

    // Load Country
    let strIntoObjPays = [];
    var requestPays = new XMLHttpRequest();
    requestPays.open("GET", API_URL_MESP + "pays", false); // `false` makes the request synchronous
    requestPays.send(null);
    strIntoObjPays = JSON.parse(requestPays.responseText);

    strIntoObjPays.forEach((comp) => {
      // console.log('-------------- Verify companies: ' + comp);
      this.state.allPays.push({value: comp, label: comp, color: "#00B8D9"});
    });

    if (this.state.allPays[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.allPays.splice(0, 1);
    }

    if (this.state.selectedPays[0].value.trim() === "") {
      // console.log(' 1 --- Clear');
      this.state.selectedPays.splice(0, 1);
    }

  }

  handleClosePopupCancelAgreement() {
    this.setState({openPopupSuccessCancelAgreement: false});
  }

  handleClosePopupJustifyDeleteAffectation() {
    this.setState({openPopupJustifyCancelAgreement: false});
  }

  onChangeJustifCancelAffectation(e) {
    this.setState({justifCancelAgreement: e.target.value});
  }

  handleOpenPopupJustifyCancelAgreement() {
    this.setState({
      justifCancelAgreement: "",
      mailEncadrantEntreprise: "",
      openPopupShowAEDetails: false,
      openPopupJustifyCancelAgreement: true
    });
  }

  handleCancelAgreement() {
    // console.log('-----------------------> Affect 1: ' + this.state.justifCancelAgreement);

    this.setState({
      loadCancelAgreement: true,
    });

    StudentService.applyForCancelAgreement(this.state.studentId, encodeURIComponent(this.state.justifCancelAgreement)).then(
      (response) => {
        let result = response.data;

        // console.log('-----------------------> Affect INSIDE');

        this.setState({
          openPopupJustifyCancelAgreement: false,
          openPopupSuccessCancelAgreement: true,
          loadCancelAgreement: false
        });
      },
      (error) => {
      }
    );
  }

  onChangeSelectProjectCompany = (selectedCompOption) => {

    let companyLabel = selectedCompOption.label;
    AuthService.findCompanyByName(
      encodeURIComponent(companyLabel)
    ).then(
      (response) => {

        let companyObject = response.data;
        let loadedPhone = companyObject.telephone;
        let loadedAddress = companyObject.address;
        let loadedEntrepriseName = companyObject.designation;

        if (loadedEntrepriseName === "") {
          this.state.existEntreprise = "EMPTY";
        }

        if (loadedEntrepriseName !== "") {
          this.state.existEntreprise = "FULL";
        }

        this.setState({
          telephone: loadedPhone,
          adresse: loadedAddress,
          societe: loadedEntrepriseName,
        });

      },
      (error) => {

      }
    );

  };

  onChangeSelectProjectSector = (selectedSectorOption) => {
    // console.log(selectedSectorOption);
    let allNSSectors = this.state.selectedSectors;
    // console.log('------TextScroller.js------- Select On Change - 0: ' + allNSSectors.length);
    // console.log(selectedSectorOption.label);
    const selectedObj = {
      value: selectedSectorOption.value,
      label: selectedSectorOption.label,
    };
    allNSSectors.push(selectedObj);

    let labelSector = selectedSectorOption.label;
    let requestFS = new XMLHttpRequest();
    requestFS.open(
      "GET",
      API_URL_MESP + "sectors/" + labelSector,
      false
    ); // `false` makes the request synchronous
    requestFS.send(null);

    this.setState({
      selectedProjectSector: JSON.parse(requestFS.responseText),
      showDetailsAboutSelectedSector: true,
      companySectorProject: {value: labelSector, label: labelSector, color: "#00B8D9"}
    });

    // console.log('-------------------------------Get Details about Selected Sector: ' + selectedSectorOption.value + " : " + this.state.selectedProjectSector.idSociete);
    // console.log('-----TextScroller.js-------- Select On Change - 1: ' + allNSSectors.length);
    this.setState({
      showDetailsAboutSelectedSector: true,
      selectedProjectSector: JSON.parse(requestFS.responseText),
    });

    // console.log('--------------------------> 1');
    // console.log(strIntoObjFS);
    // console.log(this.state.selectedProjectSector);
    // console.log(this.state.showDetailsAboutSelectedSector);
    // console.log('--------------------------> 2');
    /*
        for (const [i] of this.state.selectedSectors.entries())
        {
            console.log('Iterate ---TextScroller.js---> ' + this.state.selectedSectors[i].value + " - " + this.state.selectedSectors[i].label );
        }
        */
    // console.log('Iterate ----- Finish > ' + this.state.selectedProjectSector.libelleSecteur + " - " + this.state.selectedSectors[finalIndex].value + " - " + this.state.selectedSectors[finalIndex].label );
  };

  onChangeSelectProjectPays = (selectedPaysOption) => {
    // console.log('-----------------STATE-------------------------> Got States');
    // console.log(selectedPaysOption);
    let allNSPays = this.state.selectedPays;
    // console.log('------TextScroller.js------- Select On Change - 0: ' + allNSPays.length);
    // console.log(selectedPaysOption.label);
    const selectedObj = {
      value: selectedPaysOption.value,
      label: selectedPaysOption.label,
    };
    allNSPays.push(selectedObj);

    var requestFS = new XMLHttpRequest();
    requestFS.open(
      "GET",
      API_URL_MESP + "pays/" + selectedPaysOption.label,
      false
    ); // `false` makes the request synchronous
    requestFS.send(null);

    this.setState({
      selectedPays: allNSPays,
      selectedProjectPays: JSON.parse(requestFS.responseText),
      companyPaysTBA: "",
      allStates: [],
      companyGovProject:{}
    });



    const finalIndex = this.state.selectedPays.length - 1;

    // console.log('---------------2112-----------> 1: ', this.state.selectedProjectPays.nomPays);
    // console.log('---------------2112-----------> 3: ', this.state.selectedPays[finalIndex].value);

    // Load list of States
    let strIntoObjStates = [];
    var requestStates = new XMLHttpRequest();
    requestStates.open(
      "GET",
      API_URL_MESP + "pays/states/" +
      this.state.selectedPays[finalIndex].value,
      false
    ); // `false` makes the request synchronous
    requestStates.send(null);
    strIntoObjStates = JSON.parse(requestStates.responseText);

    let allCtrStates = this.state.allStates.slice();
    this.state.allStates = [];
    allCtrStates = [];
    this.state.companyGovProject = {};

    // console.log('----------2212-------STATE-------------------------> Got States of: ' + allCtrStates.length + " - " + this.state.allStates.length + " || " + this.state.selectedPays[finalIndex].value);


    for (let ste of strIntoObjStates) {
      // console.log('----sars 2212---------- unit cls: ' + ste);
      allCtrStates.push({
        value: ste,
        label: ste,
        color: "#00B8D9",
      });
    }

    /*strIntoObjStates.forEach((ste) => {
      console.log('---------2212----- UNIT States: ' + ste);

      const selectedObj = {value: ste, label: ste, color: "#00B8D9"};
      allCtrStates.push(selectedObj);
    });

    this.state.allStates = allCtrStates;
    */
    this.setState({
      allStates: allCtrStates,
      selectedState: null,
      companyGovProject: {}
    });
    this.state.companyGovProject = {value: "", label: "", color: "#00B8D9"};

    // console.log('------------------------------- STATE Size: ' + this.state.allStates.length);
  };

  onChangeSelectProjectStates = (selectedState) => {
    this.setState({selectedState});
  };


  // lol2021
  addProjectCompany(e)
  {
    // console.log('----lol----------------LOOL-----1----> PAYS 0: ',this.state.selectedProjectPays);

    // console.log("REG ----------------------------- 1 --> " + this.state.projectCompanyName);
    // // console.log("REG ----------------------------- 2 --> " + this.state.projectCompanyPhone);

    // console.log("REG PAYS ----------------------------- 3 --> " + this.state.selectedProjectPays.nomPays);
    // console.log("REG SECT ----------------------------- 4 --> " + this.state.selectedProjectSector.libelleSecteur);
    // console.log("REG SECT ----------------------------- 5 --> " + this.state.projectCompanySiegeSociale);

    e.preventDefault();
    this.setState({successfulRNC: false});
    this.form.validateAll();

    let phone = "";
    let fax = "";
    let pays = "";
    let sector = "---";
    let siegesocial = "";

    // console.log('--------------PH---------------> NULL-1: ' + this.state.projectCompanyPhone);
    // console.log('---------30.10--------------------> PHONE NULL : ' + phone + " - " + this.state.projectCompanyPhone);

    if (
      this.state.projectCompanyPhone.length < 11 ||
      this.state.projectCompanyPhone === undefined ||
      this.state.projectCompanyPhone.trim() === ""
    ) {
      // console.log('------------30.10-----------------> PHONE NULL');
      this.state.phoneVerify = "EMPTY";
      phone = "--------";
    } else {
      this.state.phoneVerify = "FILLED";
      phone = this.state.projectCompanyPhone;
    }

    // console.log('-------------30.10----------------> PHONE NULL : ' + phone + " - " + this.state.projectCompanyPhone);

    if (
      this.state.projectCompanyFax === undefined ||
      this.state.projectCompanyFax.trim() === ""
    ) {
      // console.log('-----------------------------> FAX NULL');
      fax = "--------";
    } else {
      fax = this.state.projectCompanyFax;
    }

    // console.log('--------------------2112---------> PAYS 1: ',this.state.selectedProjectPays);
    // console.log('--------------------2112---------> PAYS 2: ',this.state.companyPaysProject.value);

    // console.log('-----------------5mis---1---------> PAYS 2: ',this.state.companyPaysProject.value);
    // console.log('-----------------5mis---2---------> PAYS 2: ',this.state.selectedProjectPays.nomPays);
    if (
      this.state.companyPaysProject.value === undefined
    ) {//lol2021
      // console.log('-----------------------------> PAYS NULL');
      this.state.companyPaysTBA = "EMPTY";
      pays = "---";
    } else {
      pays = this.state.selectedProjectPays.nomPays;
    }

    if (this.state.projectCompanySiegeSociale === "") {
      // console.log('-----------------------------> SIEGE SOCIAL NULL');
      siegesocial = "---";
    } else {
      siegesocial = this.state.projectCompanySiegeSociale;
    }
    // console.log('----WORLD-----------> SIEGE SOCIAL NULL: ', this.state.selectedState);

    let fullAddress = "";
    if (this.state.selectedState === null) {
      fullAddress = this.state.projectCompanyAddress;
    } else {
      fullAddress =
        this.state.projectCompanyAddress +
        ", " +
        this.state.selectedState.value;
      this.state.projectCompanyAddress = fullAddress;
    }

    // console.log('----ESSID-------1----> SIEGE SOCIAL NULL: ', fullAddress);
    // console.log('----ESSID-------2----> SIEGE SOCIAL NULL: ', this.state.projectCompanyAddress);

    // console.log('--------------------------------------------------30.10----------> SIEGE SOCIAL NULL: ' + this.state.phoneVerify);

    if(this.state.projectCompanyAddress !== "")
    {
      this.state.projectCompanyAddressVerify = "yes";
    }

    if(this.state.projectCompanyEmail !== "")
    {
      if (isEmail(this.state.projectCompanyEmail)) {
        this.state.projectCompanyEmailVerify = "yes";
      }
      if (!isEmail(this.state.projectCompanyEmail)) {
        this.state.projectCompanyEmailVerify = "no";
      }

    }

    if(this.state.companyPaysProject.value !== undefined)
    {
      pays = this.state.companyPaysProject.value;
      this.state.companyPaysTBA = "";
    }


    // console.log('----------lili---------30.10----------> pays: ' + pays);

    // console.log('--------PIU-----------30.10----------> projectCompanyNameVerify: ' + this.state.companyPaysProject.value);
    // console.log('-------------------30.10----------> projectCompanyAddressVerify: ' + this.state.projectCompanyAddressVerify);
    // console.log('-------------------30.10----------> projectCompanyEmailVerify: ' + this.state.projectCompanyEmailVerify);
    // console.log('-------------------30.10----------> companyPaysTBA: ' + this.state.companyPaysTBA);
    // // console.log('-------------------30.10----------> SIEGE SOCIAL NULL: ' + this.state.duplicateCompany);
    // console.log('-------------------30.10----------> SphoneVerify: ' + this.state.phoneVerify);



    // Sector
    // console.log('-------11-------------SECTOR---------> PAYS 1: ',this.state.companySectorProject);
    // console.log('-------12-------------SECTOR---------> PAYS 1: ',this.state.selectedProjectSector);
    // console.log('-------121-------------SECTOR---------> PAYS 1: ',this.state.selectedProjectSector.libelleSecteur);

    /*
    if (this.state.companySectorProject.value !== undefined && this.state.selectedProjectSector.libelleSecteur !== undefined) {
      sector = this.state.companySectorProject.value;
      // console.log('###SECTOR########### 0: ', sector);
    }*/

    if (this.state.companySectorProject.value !== undefined) {
      sector = this.state.companySectorProject.value;
      // console.log('###SECTOR########### 1: ', sector);
    }
    if (this.state.selectedProjectSector.libelleSecteur !== undefined) {
      sector = this.state.selectedProjectSector.libelleSecteur;
      // console.log('###SECTOR########### 2: ', sector);
    }

    this.state.companySectorProject = {value: sector, label: sector, color: "#00B8D9"};
    // console.log('--------------------SECTOR-----FINAL----> PAYS 0: ', sector);

    if (
      //this.state.projectCompanyNameVerify === "yes" &&
      this.state.projectCompanyAddressVerify === "yes" &&
      this.state.projectCompanyEmailVerify === "yes" &&
      this.state.companyPaysTBA !== "EMPTY" &&
      //this.state.duplicateCompany === "AUTHORIZED" &&
      this.state.phoneVerify === "FILLED"
    ) {
      AuthService.modifyProjectCompany(
        encodeURIComponent(this.state.projectCompanyName),
        encodeURIComponent(fullAddress),
        encodeURIComponent(this.state.projectCompanyEmail),
        encodeURIComponent(phone),
        encodeURIComponent(fax),
        encodeURIComponent(this.state.selectedProjectPays.nomPays),
        encodeURIComponent(sector),
        encodeURIComponent(siegesocial)
      ).then(
        (response) => {
          /*let compAddedList = this.state.allCompanies.slice(); //creates the clone of the state
          const addedObj = {
            value: response.data.idEntreprise,
            label: response.data.designation,
            color: "#00B8D9",
          };
          compAddedList.push(addedObj);*/

          // // console.log('Add Project Company ------------- 1: ' + this.state.allCompanies.length);
          this.setState({
            //allCompanies: compAddedList,
            showModalAddNewCompanySuccess: true,
            selectedProjectSector: {},
          });
          // // console.log('Add Project Company ------------- 2: ' + this.state.allCompanies.length);
        },
        (error) => {
          this.setState({successfulRNC: false});
        }
      );
    }
  }

  openModalAddCompany() {

    // console.log('----------MODIF 1----------SECTOR-----2----> PAYS 0: ',this.state.companySectorProject);

    this.setState({
      showModalAddNewCompany: true,
      duplicateCompany: "INIT",
      // projectCompanyName: "",
      // projectCompanyAddress: "",
      // projectCompanyFax: "",
      // projectCompanyEmail: "",
      // projectCompanyPhone: "",
      // projectCompanyPays: "",
      // projectCompanySiegeSociale: "",
      // selectedPays: [],
      // selectedProjectPays: {},
      // companyPaysTBA: "",
      // allStates: []
    });

    // console.log('---------------------------> 1211: ', this.state.companySectorProject)
    /**---------- Recuperate Company Gov ----------**/
      // Load list of States

    let hello = "";
    if(this.state.companyPaysProject.value !== undefined)
    {
      hello = this.state.companyPaysProject.value;
      // console.log('..........PIKO.......2: ' , hello);
    }

    if(this.state.selectedProjectPays.nomPays !== undefined)
    {
      hello = this.state.selectedProjectPays.nomPays;
      // console.log('.......PIKO..........1: ' , hello);
    }

    /*
    defaultValue={this.state.companyPaysProject}
    placeholder="Pays Entreprise"
    value={this.state.allPays.value}
    */

    this.state.companyPaysProject = {value: hello, label: hello, color: "#00B8D9"};
    // console.log('-------############################### PIKO---------- 3-' , this.state.companyPaysProject);

    let strIntoObjStates = [];
    var requestStates = new XMLHttpRequest();
    requestStates.open(
      "GET",
      API_URL_MESP + "pays/states/" +
      hello,
      false
    ); // `false` makes the request synchronous
    requestStates.send(null);
    strIntoObjStates = JSON.parse(requestStates.responseText);

    let allCtrStates = this.state.allStates.slice();
    this.state.allStates = [];
    allCtrStates = [];

    for (let ste of strIntoObjStates) {
      // console.log('----sars 2212---------- unit cls: ' + ste);
      allCtrStates.push({
        value: ste,
        label: ste,
        color: "#00B8D9",
      });
    }

    this.state.allStates = allCtrStates;
    this.setState({
      //allStates: allCtrStates,
      selectedState: null,
    });

    // console.log('----ESSID-------3----> SIEGE SOCIAL NULL: ', this.state.projectCompanyAddress);

    let address = this.state.projectCompanyAddress;

    // console.log('-------############################### PIKO---------- 1-' , this.state.selectedProjectPays)
    // console.log('-------############################### PIKO---------- 2-' , this.state.companyPaysProject)
    // this.state.companyPaysProject =

    let govLabel = address.substring(address.lastIndexOf(", ") + 2, address.length);
    // console.log('----------------- COMPARING-' + govLabel + "-")

    for (let ste of allCtrStates) {
      // console.log('-------COMPARING---------- GOV : ' + govLabel + ">>>>>>>" + ste.value)
      if(ste.value === govLabel)
      {
        // console.log('--######COMPARING######### GOT IT---------- GOV-' + govLabel + "-" + ste.value)


        this.state.companyGovProject = ste; //{value: ste, label: ste, color: "#00B8D9"};
        // console.log('----sars SARSA---COMPARING------- unit cls: ' , ste);
        //this.state.companyGovProject = {value: govLabel, label: govLabel, color: "#00B8D9"};
        //console.log('----sars SARSA---------- unit cls: ' , this.state.companyGovProject);
        break;
      }

    }

    // console.log('----sars 1011---------- unit cls: ' , allCtrStates);
    // console.log('----sars 1011---------- unit cls: ' , this.state.allStates);

    // this.state.companyGovProject = {value: compGov, label: compGov, color: "#00B8D9"};
    // console.log('-------------------ESSID----> 0905 compPays a: ' , this.state.companyGovProject);
    // console.log('-------------------ESSID----> 0905 compPays z: ' , this.state.companyPaysProject);


    // console.log('---------MODIF 2-----------SECTOR-----2----> PAYS 0: ',this.state.companySectorProject);


  }

  closeModalAddCompany() {
    // console.log(' Add New Company CM -------------------- ');
    this.setState({
      showModalAddNewCompany: false,
      showModalAddNewCompanySuccess: false
    });
  }

  onChangeMail(e) {
    this.setState({mail: e.target.value});
  }

  onChangeDateDebut = (date) => {
    if (date === null) {
      this.setState({
        dateDebut: date,
        verifyEmptyDateDebut: "false",
      });
    } else {
      var myDate = new Date(date);
      // console.log('1. -------------------> DATE: ' + d);

      // console.log('1. -------------------> DATE: ');
      // console.log(traineeDuration);
      // console.log('2. -------------------> DATE: ');

      myDate.setMonth(myDate.getMonth() + this.state.traineeDuration);
      // console.log('2. -------------------> DATE: ' + myDate);

      this.setState({
        dateDebut: date,
        dateFin: null,
        verifyEmptyDateDebut: "true",
        dateFourMonths: myDate,
      });

      // console.log(' 1 --------> DATE 4 Months: ' + this.state.dateFourMonths + " - " + myDate);
    }
  };

  onChangeDateFin = (date) => {
    if (date === null) {
      this.setState({
        dateFin: date,
        verifyEmptyDateFin: "false",
      });
    } else {
      this.setState({
        dateFin: date,
        verifyEmptyDateFin: "true",
      });
    }
  };

  onChangeProjectCompanyName(e) {
    let insertedName = e.target.value;
    if (insertedName.length < 5 || insertedName.length > 25) {
      this.setState({
        projectCompanyNameVerify: "no",
        projectCompanyName: insertedName,
      });
    } else {
      this.setState({
        projectCompanyNameVerify: "yes",
        projectCompanyName: insertedName,
      });
    }

    let strIntoObjComp = [];
    let companiesLibs = [];
    var requestComp = new XMLHttpRequest();
    requestComp.open(
      "GET",
      API_URL_MESP + "allCompanies",
      false
    ); // `false` makes the request synchronous
    requestComp.send(null);
    strIntoObjComp = JSON.parse(requestComp.responseText);

    strIntoObjComp.forEach((comp) => {
      companiesLibs.push(comp);
    });

    for (let sl of companiesLibs) {
      if (insertedName.toUpperCase().trim() === sl.toUpperCase().trim()) {
        // console.log('--------NO--------------------------------------------------------------lol123->' + insertedName + " - " + sl);
        this.setState({duplicateCompany: "NOTAUTHORIZED"});
        break;
      } else {
        this.setState({duplicateCompany: "AUTHORIZED"});
        // console.log('lol123->' + insertedName + " - " + sl);
      }
    }

    // console.log('-----------------lol123->' + insertedName + " - " + this.state.duplicateCompany)
  }

  onChangeProjectCompanyAddress(e) {
    let insertedAddress = e.target.value;

    if (insertedAddress.length < 10 || insertedAddress.length > 150) {
      this.setState({
        projectCompanyAddressVerify: "no",
        projectCompanyAddress: insertedAddress,
      });
    } else {
      this.setState({
        projectCompanyAddressVerify: "yes",
        projectCompanyAddress: insertedAddress,
      });
    }
  }

  onChangeProjectCompanyEmail(e) {
    let insertedMail = e.target.value;
    if (isEmail(insertedMail)) {
      this.setState({
        projectCompanyEmailVerify: "yes",
        projectCompanyEmail: insertedMail,
      });
    }
    if (!isEmail(insertedMail)) {
      this.setState({
        projectCompanyEmailVerify: "no",
        projectCompanyEmail: insertedMail,
      });
    }
  }

  onChangeSupervisorCompanyEmail(e) {
    let insertedMail = e.target.value;
    if (isEmail(insertedMail)) {
      this.setState({
        mailEncadrantEntrepriseVerify: "yes",
        mailEncadrantEntreprise: insertedMail,
      });
    }
    if (!isEmail(insertedMail)) {
      this.setState({
        mailEncadrantEntrepriseVerify: "no",
        mailEncadrantEntreprise: insertedMail,
      });
    }
  }

  onChangeProjectCompanyPhone(e) {
    this.setState({projectCompanyPhone: e.target.value});
  }

  onChangeProjectCompanyFax(e) {
    this.setState({projectCompanyFax: e.target.value});
  }

  onChangeProjectCompanySiegeSociale(e) {
    this.setState({projectCompanySiegeSociale: e.target.value});
  }

  onChangeProjectCodePays(e) {
    let insertedCodePays = e.target.value;
    if (insertedCodePays.length < 2 || insertedCodePays.length > 2) {
      this.setState({
        projectCodePaysVerify: "no",
        projectCodePays: insertedCodePays,
      });
    } else {
      this.setState({
        projectCodePaysVerify: "yes",
        projectCodePays: insertedCodePays,
      });
      // console.log('---------------------> OK Verify projectCodePaysVerify: ' + this.state.projectCodePaysVerify);
    }
  }

  onChangeProjectCodePostal(e) {
    let insertedCodePostal = e.target.value;
    if (insertedCodePostal.length < 3 || insertedCodePostal.length > 10) {
      this.setState({
        projectCodePostalVerify: "no",
        projectCodePostal: insertedCodePostal,
      });
    } else {
      this.setState({
        projectCodePostalVerify: "yes",
        projectCodePostal: insertedCodePostal,
      });
      // console.log('---------------------> OK Verify projectCodePostalVerify: ' + this.state.projectCodePostalVerify);
    }
  }

  onChangeProjectNomPays(e) {
    let insertedNomPays = e.target.value;
    if (insertedNomPays.length < 2 || insertedNomPays.length > 50) {
      this.setState({
        projectNomPaysVerify: "no",
        projectNomPays: insertedNomPays,
      });
    } else {
      this.setState({
        projectNomPaysVerify: "yes",
        projectNomPays: insertedNomPays,
      });
      // console.log('---------------------> OK Verify projectNomPaysVerify: ' + this.state.projectNomPaysVerify);
    }
  }

  onChangeProjectRegion(e) {
    let insertedRegion = e.target.value;
    if (insertedRegion.length < 3 || insertedRegion.length > 50) {
      this.setState({
        projectRegionVerify: "no",
        projectRegion: insertedRegion,
      });
    } else {
      this.setState({
        projectRegionVerify: "yes",
        projectRegion: insertedRegion,
      });
      // console.log('---------------------> OK Verify projectRegionVerify: ' + this.state.projectRegionVerify);
    }
  }

  onChangeSociete(e) {
    this.setState({societe: e.target.value});
  }

  onChangeAdresse(e) {
    this.setState({adresse: e.target.value});
  }

  onChangeTelephone(e) {
    this.setState({telephone: e.target.value});
  }

  onChangeResponsable(e) {
    this.setState({responsable: e.target.value});
  }

  closeModalOk() {
    this.setState({ok: false});
  }

  closeModalSuccessConfirmAddConvention() {
    this.setState({successConfirmAddConvention: false});


    /************************************************************/

    let stuId = sessionStorage.getItem("studentId");
    axios.get(
      `${process.env.REACT_APP_API_URL}` + "serviceStage/gotConvUnit?idET=" + stuId + "&date=" + this.state.convention.dateConvention, {responseType: 'blob'}
    ).then((response) => {
      //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];

      // console.log('------------- SARS 2005 : ', response.data);
      // console.log('------------- SARS 2005 : ', response);

      this.props.history.push('/ConventionDetails');
    });

  }

  /*closeModalSuccessConfirmAddConvention() {
    this.setState({successConfirmAddConvention: false});

    // window.location.reload();
    // window.location.href = "/ConventionsManagement";
    this.props.history.push('/ConventionsManagement');
  }*/

  closeModalFailConfirmAddConvention() {
    this.setState({failConfirmAddConvention: false, loading: false});
  }

  closeModalSuccessConfirmUpdateConvention() {
    this.setState({successConfirmUpdateConvention: false});
  }

  componentDidMount() {
  }

  handleApplyForAddConvention(e) { // lol2022
    // console.log('**************************************** Verify 1: ' + this.state.verifyEmptyDateFin);
    if (this.state.dateFin === null) {
      // console.log(' DF **************************************** Verify 2.1');

      this.setState({
        verifyEmptyDateFin: "false",
      });
    }
    if (this.state.dateFin !== null) {
      // console.log('D DF **************************************** Verify 2.2: ' + this.state.dateFin);

      this.setState({
        verifyEmptyDateFin: "true",
      });
    }

    if (this.state.dateDebut === null) {
      // console.log(' DD **************************************** Verify 2.1');

      this.setState({
        verifyEmptyDateDebut: "false",
      });
    }
    if (this.state.dateDebut !== null) {
      // console.log(' DD **************************************** Verify 2.2.1: ' + this.state.dateDebut);

      this.state.verifyEmptyDateDebut = "true";
      // console.log(' DD **************************************** Verify 2.2.2: ' + this.state.verifyEmptyDateDebut);
    }

    if (this.state.societe === "") {
      this.state.existEntreprise = "EMPTY";
    }

    if (this.state.societe !== "") {
      this.state.existEntreprise = "FULL";
    }

    // console.log('**************************************** Verify 3.1: ' + this.state.verifyEmptyDateFin);

    e.preventDefault();

    // console.log('**************************************** Verify 3.2: ' + this.state.verifyEmptyDateFin);

    this.formAddConvention.validateAll();

    // console.log('**************************************** Verify 3.3: ' + this.state.verifyEmptyDateFin);

    // console.log('**************************************** Verify 3.3.1: ' + this.checkBtnAddConvention.context._errors.length);
    // console.log('**************************************** Verify 3.3.2: ' + this.state.verifyEmptyDateFin);
    // console.log('**************************************** Verify 3.3.3: ' + this.state.verifyEmptyDateDebut);

    if (
      this.checkBtnAddConvention.context._errors.length === 0 &&
      this.state.verifyEmptyDateFin === "true" &&
      this.state.verifyEmptyDateDebut === "true"
    ) {
      // console.log('**************************************** Verify 3.4: ' + this.state.verifyEmptyDateFin);
      this.setState({
        ok: true,
      });
      // console.log('**************************************** Verify 3.5: ' + this.state.verifyEmptyDateFin);
    }
    // console.log('**************************************** Verify 3.6: ' + this.state.verifyEmptyDateFin);
  }

  handleApplyForAddConvention1() {

    //sessionStorage.setItem("studentId", "193JMT0926");
    // console.log('******************* PHONE ******ff*************** 0905: ' + sessionStorage.getItem("studentId"));

    if (this.state.mail === "") {
      this.setState({
        mail: "--",
      });
    }

    this.setState({
      loading: true,
    });

    AuthService.modifyConvention(
      encodeURIComponent(this.state.mail),
      this.state.dateDebut,
      this.state.dateFin,
      encodeURIComponent(this.state.societe),
      encodeURIComponent(this.state.adresse),
      encodeURIComponent(this.state.telephone),
      encodeURIComponent(this.state.responsable)
    ).then(
      (response) => {
        // console.log('======================== SUCCESS.');
        this.setState({
          message: "",
          successful: false,
          ok: false,
          successConfirmAddConvention: true,
          dateDebut: new Date(),
          dateFin: null
        });

        /******************************************************************************************************************/
        /*var requestefp = new XMLHttpRequest();
        requestefp.open(
            "GET",
            API_URL_RSS + "allConventionListForRSS",
            false
        );
        requestefp.send(null);
        this.state.allConvsRSS = JSON.parse(requestefp.responseText);*/



        /******************************************************************************************************************/
        this.props.history.push('/ConventionsValideesManagement');
      },
      (error) => {
        // console.log('======================== FAIL.');
        this.setState({
          ok: false,
          failConfirmAddConvention: true,
        });
      }
    );
  }

  handleApplyForUpdateConvention(e) {
    // console.log('**************************************** Verify 1: ' + this.state.verifyEmptyDateFin);
    if (this.state.dateFin == null) {
      // console.log(' DF-Update **************************************** Verify 2.1');

      this.setState({
        verifyEmptyDateFin: "false",
      });
    }
    if (this.state.dateFin !== null) {
      // console.log(' DF-Update **************************************** Verify 2.2: ' + this.state.dateFin);

      this.setState({
        verifyEmptyDateFin: "true",
      });
    }

    if (this.state.dateDebut == null) {
      // console.log(' DD-Update **************************************** Verify 2.1');

      this.setState({
        verifyEmptyDateDebut: "false",
      });
    }
    if (this.state.dateDebut !== null) {
      // console.log(' DD-Update **************************************** Verify 2.2: ' + this.state.dateDebut);

      this.setState({
        verifyEmptyDateDebut: "true",
      });
    }

    // console.log(' DD-Update **************************************** Verify 3: ' + this.state.verifyEmptyDateDebut + " -- " + this.state.verifyEmptyDateFin);

    e.preventDefault();
    this.formUpdateConvention.validateAll();

    if (
      (this.checkBtnUpdateConvention.context._errors.length === 0 &&
        this.state.verifyEmptyDateFin === "true" &&
        this.state.verifyEmptyDateDebut === "true") ||
      (this.checkBtnUpdateConvention.context._errors.length === 0 &&
        this.state.verifyEmptyDateFin === "init" &&
        this.state.verifyEmptyDateDebut === "init")
    ) {
      this.setState({
        message: "",
        successful: false,
        successConfirmUpdateConvention: true,
      });
      // console.log("----------------------------- 3 --> " + this.state.telephone);

      AuthService.addConvention(
        this.state.convention.mail,
        this.state.dateDebut,
        this.state.dateFin,
        this.state.convention.nomSociete,
        this.state.convention.address,
        this.state.convention.telephone,
        this.state.responsable
      ).then(
        (response) => {
          // console.log('UPDATE ======================== DONE');
        },
        (error) => {
          // console.log('UPDATE ======================== ERROR');
        }
      );
    }
  }

  render() {
    const {
      successConfirmUpdateConvention,
      ok,
      successConfirmAddConvention,
      failConfirmAddConvention,
      traitementStatus,
      fichePFEStatus,
      duplicateCompany,
      currentDay,
      justifCancelAgreement,
      mailEncadrantEntreprise,
      mailEncadrantEntrepriseVerify,
      openPopupJustifyCancelAgreement,
      loadCancelAgreement,
      openPopupSuccessCancelAgreement,
      convention
    } = this.state;

    return (
      <>

        <br/>
        <CRow>
          <CCol xs="2"/>
          <CCol xs="8">
            <Form
              onSubmit={this.handleApplyForAddConvention}
              ref={(c) => {
                this.formAddConvention = c;
              }}
            >
              <CRow>
                <CCol md="3">
                  <span className="convFieldLibelle">E-Mail : </span>
                </CCol>
                <CCol md="9">
                  <Input
                    type="text"
                    className="form-control"
                    name="mail"
                    disabled
                    value={this.state.mail}
                  />
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="9">
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">Date Début</span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <DatePicker
                        selected={this.state.dateDebut}
                        onChange={this.onChangeDateDebut}
                        isClearable
                        style={{marginLeft: "2000px"}}
                        className="form-control"
                        dateFormat="dd-MM-yyyy"/>
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">Date Fin</span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <DatePicker
                        selected={this.state.dateFin}
                        onChange={this.onChangeDateFin}
                        placeholderText=" --/--/--"
                        isClearable
                        className="form-control"
                        dateFormat="dd-MM-yyyy"
                        minDate={this.state.dateFourMonths}/>
                      {this.state.verifyEmptyDateFin.trim() === "false" && (
                        <div className="alert alert-danger" role="alert">
                          This field is required !.
                        </div>
                      )}
                    </CCol>
                  </CRow>
                </CCol>
                <CCol md="3">
                      <span className="alertConvDuration">
                        Pour un cas exceptionnel, vous pouvez insérer une durée inférieure à 6 mois.
                      </span>
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="3">
                      <span className="convFieldLibelle">
                          Libellé Entreprise
                        </span>
                  <span className="requiredStar">&nbsp;(*) :</span>
                </CCol>
                <CCol md="9">
                  <CRow>
                    <CCol md="6">
                          <span className="noteDivGrey">
                            Choisir l'Entreprise d'Accueil où vous passez votre Stage PFE.
                          </span>
                      <hr/>
                      <Select
                        defaultValue={this.state.companyLibProject}
                        value={this.state.allCompanies.value}
                        components={animatedComponents}
                        options={this.state.allCompanies}
                        onChange={this.onChangeSelectProjectCompany}
                        validations={[required]}
                        isDisabled
                      />
                      {this.state.existEntreprise.trim() === "EMPTY" && (
                        <div className="alert alert-danger" role="alert">
                          This field is required !.
                        </div>
                      )}
                    </CCol>
                    <CCol md="6">
                              <span className="noteDivGrey">
                                Pour Mettre À Jour le Profil de cette Entreprise, Cliquez sur ce bouton :
                              </span>
                      <br/>
                      <hr/>
                      <center>
                        <Button variant="outlined"
                                onClick={() => this.openModalAddCompany()}
                                color="success" style={{fontSize: "10px"}}>
                          Modifier Profil Entreprise
                        </Button>
                      </center>
                    </CCol>
                  </CRow>
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="3">
                      <span className="convFieldLibelle">
                          Représentant Entrp.
                        </span>
                  <span className="requiredStar">&nbsp;(*) :</span>
                </CCol>
                <CCol md="9">
                  <Input
                    type="text"
                    className="form-control"
                    name="responsable"
                    value={this.state.responsable}
                    onChange={this.onChangeResponsable}
                    validations={[required, responsable]}/>
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="12">
                  <div className="float-right">
                    {
                      currentResponsableServiceStage.id.includes("IT") ? (
                        <button className="btn btn-danger">
                          <span>Sauvegarder</span>
                        </button>
                      ) : (
                        <button disabled className="btn btn-danger">
                          <span>Sauvegarder</span>
                        </button>
                      )
                    }
                  </div>
                </CCol>
              </CRow>
              <br/>


              <CheckButton
                style={{display: "none"}}
                ref={(c) => {
                  this.checkBtnAddConvention = c;
                }}
              />
            </Form>
            <br/>
            {ok && (
              <Modal
                isOpen={ok}
                contentLabel="Example Modal"
                style={customStyles}
              >
                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                <hr/>
                <center>
                  <br/>
                  Êtes vous sûres de vouloir confirmer le(s) mise(s) à jour de cette Convention ?.
                  <br/>
                  <br/>
                  <br/>
                  {!this.state.loading &&
                    <button className="btn btn-success"
                            onClick={this.handleApplyForAddConvention1}>
                      Yes
                    </button>
                  }

                  {this.state.loading &&
                    <Spinner animation="grow" variant="success"/>
                  }

                  &nbsp;&nbsp;&nbsp;
                  {!this.state.loading &&
                    <button className="btn btn-danger"
                            onClick={this.closeModalOk}
                            disabled={this.state.loading}>
                      No
                    </button>
                  }
                  <br/>
                  <br/>
                </center>
              </Modal>
            )}

            {successConfirmAddConvention && (
              <Modal
                isOpen={successConfirmAddConvention}
                contentLabel="Example Modal"
                style={customStyles}
              >
                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                <hr/>
                <center>
                  <br/>
                  Mise(s) à jour effectuée(s) avec succès.
                  <br/>
                  <br/>

                  <button
                    className="btn btn-sm btn-primary"
                    onClick={this.closeModalSuccessConfirmAddConvention}
                  >
                    Ok
                  </button>

                  <br/>
                  <br/>
                </center>
              </Modal>
            )}

            {failConfirmAddConvention && (
              <Modal
                isOpen={failConfirmAddConvention}
                contentLabel="Example Modal"
                style={customStyles}
              >
                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                <hr/>
                <center>
                  <br/>
                  Erreur d'enregistrement !.
                  <br/>
                  <br/>
                  Prière de verifier les données.
                  <br/>
                  <br/>
                  <br/>
                  <button
                    className="btn btn-sm btn-primary"
                    onClick={this.closeModalFailConfirmAddConvention}
                  >
                    {" "}
                    Ok{" "}
                  </button>
                  <br/>
                  <br/>
                </center>
              </Modal>
            )}
          </CCol>
          <CCol xs="2"/>
        </CRow>

        <Dialog
          fullHight
          fullWidth
          maxWidth="md"
          open={this.state.showModalAddNewCompany}
          onClose={this.closeModalAddCompany}
          aria-labelledby="form-dialog-title"
          PaperProps={{style: {overflowY: 'visible'}}}>
          <DialogTitle id="form-dialog-title">
            <CRow>
              <CCol>
                <span className="float-left" style={{color: "#ff6600", fontSize: "15px", fontWeight: "bold"}}>
                  Mettre À Jour Profil Entreprise d'Accueil
                </span>

                <Button className="float-right" onClick={this.closeModalAddCompany} color="primary">
                  X
                </Button>
              </CCol>
            </CRow>
            <hr/>
          </DialogTitle>
          <DialogContent style={{ overflowY: 'visible' }}>
            <Form onSubmit={this.addProjectCompany}
                  ref={(c) => {
                    this.form = c;
                  }}>

              <CRow>
                <CCol md="6">
                  <Input
                    type="text"
                    className="form-control"
                    name="projectCompanyName"
                    placeholder="Nom Entreprise"
                    value={this.state.projectCompanyName}
                    onChange={this.onChangeProjectCompanyName}
                    validations={[required, projectCompanyName]}
                    disabled/>
                  <br/>
                  {duplicateCompany.trim() ===
                    "NOTAUTHORIZED" && (
                      <div>
                        <p className="confirmPasswordsFail">
                          This Label already exists. Please
                          Return Back to select your Company.
                        </p>
                      </div>
                    )}
                </CCol>
                <CCol md="6">
                  <Select
                    defaultValue={this.state.companySectorProject}
                    placeholder="Secteur Entreprise"
                    value={this.state.allSectors.value}
                    components={animatedComponents}
                    options={this.state.allSectors}
                    onChange={this.onChangeSelectProjectSector}/>
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="6">
                  <Input
                    type="text"
                    className="form-control"
                    name="projectCompanyEmail"
                    placeholder="E-Mail Entreprise"
                    value={this.state.projectCompanyEmail}
                    onChange={this.onChangeProjectCompanyEmail}
                    validations={[required, projectCompanyEmail]}
                    maxLength="100"/>
                </CCol>
                <CCol md="6">
                  <Input
                    className="form-control"
                    name="projectCompanySiegeSociale"
                    placeholder="Siège Social Entreprise"
                    value={this.state.projectCompanySiegeSociale}
                    onChange={this.onChangeProjectCompanySiegeSociale}
                    validations={[projectCompanySiegeSociale]}
                    maxLength="50"/>
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="5">
                  <PhoneInput
                    country={"tn"}
                    name="telephone"
                    placeholder="Téléphone Entreprise"
                    value={this.state.projectCompanyPhone}
                    onChange={(projectCompanyPhone) => this.setState({projectCompanyPhone})}
                    validations={[projectCompanyPhone, projectCompanyPhoneMin]}/>
                  {
                    this.state.phoneVerify === "EMPTY" && this.state.projectCompanyPhone.length < 11 &&
                    <div className="alert alert-danger" role="alert">
                      This field is required !.
                    </div>
                  }
                </CCol>

                <CCol md="1">
                  <CTooltip content="Saisir le numéro de Téléphone de votre Entreprise" placement="right-end">
                    <PhoneEnabledIcon style={{color: "#ff6600"}}/>
                  </CTooltip>
                </CCol>
                <CCol md="6">
                  <Input
                    type="text"
                    className="form-control"
                    name="projectCompanyAddress"
                    placeholder="Adresse Entreprise"
                    value={this.state.projectCompanyAddress}
                    onChange={this.onChangeProjectCompanyAddress}
                    validations={[required, projectCompanyAddress]}
                    maxLength="150"/>
                </CCol>
              </CRow>

              <br/>
              <CRow>
                <CCol md="5">
                  <PhoneInput
                    country={"tn"}
                    name="projectCompanyFax"
                    placeholder="Company Fax"
                    value={this.state.projectCompanyFax}
                    onChange={(projectCompanyFax) => this.setState({projectCompanyFax})}
                    validations={[projectCompanyFax, projectCompanyFaxMin]}/>
                </CCol>
                <CCol md="1">
                  <CTooltip content="Saisir le numéro de Fax de votre Entreprise" placement="right-end">
                    <PrintIcon style={{color: "#ff6600"}}/>
                  </CTooltip>
                </CCol>
                <CCol md="6">
                  <Select
                    defaultValue={this.state.companyPaysProject}
                    placeholder="Pays Entreprise"
                    value={this.state.allPays.value}
                    components={animatedComponents}
                    options={this.state.allPays}
                    onChange={this.onChangeSelectProjectPays}
                  />
                  {this.state.companyPaysTBA === "EMPTY" && (
                    <div className="alert alert-danger" role="alert">
                      This field is required !.
                    </div>
                  )}

                  {
                    (this.state.selectedPays.length !== 0 || this.state.companyPaysProject !== "")&&
                    <>
                      <br/>
                      <Select
                        defaultValue={this.state.companyGovProject}
                        placeholder="Gouvernorat Entreprise"
                        value={this.state.allStates.value}
                        components={animatedComponents}
                        options={this.state.allStates}
                        onChange={this.onChangeSelectProjectStates}
                      />
                    </>

                  }
                </CCol>
              </CRow>

              <br/><br/>
              <CRow>
                <CCol md="12">
                  <div className="float-right">
                    <button className="btn btn-warning">
                      <span>Confirmer</span>
                    </button>
                  </div>
                </CCol>
              </CRow>
              <br/>
            </Form>
          </DialogContent>
        </Dialog>

        <Dialog
          fullHight
          fullWidth
          maxWidth="sm"
          open={this.state.showModalAddNewCompanySuccess}
          onClose={this.closeModalAddCompany}
          aria-labelledby="form-dialog-title">
          <DialogTitle id="form-dialog-title">
            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
            <hr/>
          </DialogTitle>
          <DialogContent>
            <CRow>
              <CCol xs="12">
                <center>
                  <br/>
                  Le profil de votre Entreprise d'Accueil a été ajouté avec succès.
                  <br/>
                  Vous allez le trouver dans la liste des Entreprises.
                  <br/><br/><br/>
                </center>
              </CCol>
            </CRow>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.closeModalAddCompany}
                    color="primary">
              Ok
            </Button>
          </DialogActions>
        </Dialog>

        <Dialog fullHight fullWidth
                maxWidth="sm"
                keepMounted
                open={openPopupJustifyCancelAgreement}
                onClose={this.handleClosePopupJustifyDeleteAffectation}
                aria-labelledby="form-dialog-title"
                classes={{paper: dialogStyles.dialogPaper}}>
          <DialogTitle id="form-dialog-title">
            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
            <hr/>
          </DialogTitle>
          <DialogContent>
            <center>
              Êtes-Vous sûr de vouloir demander une annulation de votre Convention Actuelle ?.
              <br/><br/><br/>

              <br/><br/>
              <CRow>
                <CCol md="3">
                  <span className="greyMark">Mail Encadrant Entreprise (*)</span>:
                </CCol>
                <CCol md="9">
                  <input  type="text"
                          placeholder="Présenter le Mail Encadrant Entreprise"
                          className="form-control"
                          value={mailEncadrantEntreprise}
                          onChange={this.onChangeSupervisorCompanyEmail}
                          maxLength="100"/>
                  {
                    mailEncadrantEntrepriseVerify === "no" &&
                    <div className="alert alert-danger" role="alert">
                      It is not a valid Supervisor Company E-Mail form.
                    </div>
                  }
                </CCol>
              </CRow>
              <br/>
              <CRow>
                <CCol md="3">
                  <span className="greyMark">Motif Annulation (*)</span>:
                </CCol>
                <CCol md="9">
                    <textarea className="form-control"
                              style={{height: 75}}
                              placeholder="Présenter le Motif d'Annulation de votre Convention ..."
                              value={justifCancelAgreement}
                              onChange={(e) => this.onChangeJustifCancelAffectation(e)}
                              maxLength="200"/>
                </CCol>
              </CRow>

              <br/><br/><br/>
              <span style={{color: "#cc0000", fontSize: "11px", fontStyle: "italic"}}>
                   Après validation de votre part, la demande d'Annulation de votre Convention devra être acceptée par votre Entreprise.
                   <br/>
                   Un mail parviendra à votre Encadrant Professionnel lui demandant d'accepter cette demande.
                   <br/>
                   Sans son accord, votre Convention ne pourra être annulée.
                </span>
              <br/><br/>
            </center>
          </DialogContent>
          <DialogActions>
            {
              !loadCancelAgreement &&
              <Button onClick={this.handleCancelAgreement} disabled={justifCancelAgreement.length === 0 || mailEncadrantEntrepriseVerify === "no"}
                      color="primary">
                Confirmer l'Annulation
              </Button>
            }
            {
              loadCancelAgreement &&
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
                open={openPopupSuccessCancelAgreement}
                onClose={this.handleClosePopupCancelAgreement}
                aria-labelledby="form-dialog-title">
          <DialogTitle id="form-dialog-title">
            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
            <hr/>
          </DialogTitle>
          <DialogContent>
            <center>
              Votre convention &nbsp;
              <span className="greyMarkCourrier">a été annulée</span>&nbsp;
              avec succès avec le motif <span className="greyMarkCourrier">{justifCancelAgreement}</span> .
              <br/><br/>
            </center>
          </DialogContent>
          <DialogActions>
            <Link to={"/synopsisAndNews"}>
              <button className="btn btn-sm btn-secondary"
                      onClick={this.handleClosePopupCancelAgreement}>
                EXIT
              </button>
            </Link>
          </DialogActions>
        </Dialog>
        <br/><br/><br/><br/>
      </>
    );
  }
}
