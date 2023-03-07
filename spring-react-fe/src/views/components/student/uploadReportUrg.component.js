import React, { Component } from "react";

import AuthService from "../../services/auth.service";
import uploadIcon from "../../images/uploadIcon.png";

import blueLightUpload from "../../images/blueLightUpload.png";
import blueBoldUpload from "../../images/blueBoldUpload.jpg";
import redUpload from "../../images/redUpload.jpg";
import orangeUpload from "../../images/orangeUpload.jpg";
import espritLogo from "../../images/esprit.gif";

import Modal from "react-modal";
import CIcon from "@coreui/icons-react";
import { freeSet } from '@coreui/icons';


import "react-notifications/lib/notifications.css";
import Checkbox from "@material-ui/core/Checkbox";

import { CListGroup, CListGroupItem, CCardHeader, CAlert, CBadge, CCard, CCardFooter, CCardBody, CRow, CCol, CContainer, CButton, CTooltip, CModal, CModalHeader, CModalTitle, CModalBody, CModalFooter } from "@coreui/react";
import Divider from '@material-ui/core/Divider';

import Spinner from "react-bootstrap/Spinner";

import CircularProgress from '@material-ui/core/CircularProgress';
import Backdrop from '@material-ui/core/Backdrop';

import { withStyles } from '@material-ui/core/styles';


import "../../css/style.css";

// npm install --save-dev @iconify/react @iconify-icons/mdi
import { Icon, InlineIcon } from '@iconify/react';
import handPointingDown from '@iconify-icons/mdi/hand-pointing-down';
import handPointingRight from '@iconify-icons/mdi/hand-pointing-right';


import {NotificationContainer, NotificationManager} from 'react-notifications';

import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import AccountCircle from '@material-ui/icons/AccountCircle';

import { Container } from '@material-ui/core';



const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const customStyles = {
  content: {
    top: "50%",
    left: "58%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
  },
};

const useStyles = theme => ({
  backdrop: {
    width: "100%",
    height: "100%",
    position: "fixed",
    zIndex: "100",
    left: "8%",
    top: "0",
    right: "auto",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
});

const useStyles1 = theme => ({
  backdrop1: {
    width: "100%",
    height: "100%",
    position: "fixed",
    zIndex: "100",
    left: "8%",
    right: "auto",
    marginTop: "5%",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
});



const currentStudent = AuthService.getCurrentStudent();

// init2021
class UploadReportUrg extends Component {
  constructor(props) {
    super(props);

    this.addPairHandler = this.addPairHandler.bind(this);
    this.selectReportFinalVersion = this.selectReportFinalVersion.bind(this);
    this.selectReportAntiPlagiat = this.selectReportAntiPlagiat.bind(this);
    this.selectAttestationStagePFE = this.selectAttestationStagePFE.bind(this);
    this.selectDossierDocumentSupplemenaire = this.selectDossierDocumentSupplemenaire.bind(this);

    this.uploadReportFinalVersion = this.uploadReportFinalVersion.bind(this);
    this.uploadTrainingCertif = this.uploadTrainingCertif.bind(this);
    this.uploadPlagiat = this.uploadPlagiat.bind(this);
    this.uploadSupplement = this.uploadSupplement.bind(this);
    this.deleteReportFile = this.deleteReportFile.bind(this);
    this.closeModalSuccessUpload = this.closeModalSuccessUpload.bind(this);
    this.openModalConfirmRelanceDepot = this.openModalConfirmRelanceDepot.bind(this);
    this.closeModalConfirmRelanceDepot = this.closeModalConfirmRelanceDepot.bind(this);
    this.reportConfidentialHandler = this.reportConfidentialHandler.bind(this);
    this.onChangeTraineeShipKind = this.onChangeTraineeShipKind.bind(this);
    this.onChangePairKind = this.onChangePairKind.bind(this);

    this.confirmStep1 = this.confirmStep1.bind(this);
    this.confirmStep2 = this.confirmStep2.bind(this);
    this.confirmStep3 = this.confirmStep3.bind(this);
    this.confirmStepEMGC = this.confirmStepEMGC.bind(this);
    this.validateMyDepot = this.validateMyDepot.bind(this);
    this.requestForUpdateMyDepot = this.requestForUpdateMyDepot.bind(this);
    this.updateMyDepotYES = this.updateMyDepotYES.bind(this);
    this.updateMyDepotNO = this.updateMyDepotNO.bind(this);
    this.closeShowPopupSuccessDepot = this.closeShowPopupSuccessDepot.bind(this);
    this.modifyMyDepot = this.modifyMyDepot.bind(this);

    this.modifyMyRFV = this.modifyMyRFV.bind(this);


    this.state = {
      message: "",
      fileInfos: [],
      successUpload: false,
      showPopupSuccessUpload: false,
      depotStatus: "",
      checked: false,
      checkedTraineeShip: 0,
      traineeShipKind: "En Tunisie",
      checkedPair: 2,
      pairKind: "Non",
      allTraineeshipKinds: [],
      studentFullName: "",
      stepper: "Step1",
      showPopupSuccessDepot: false,
      showSpinnerWaitingDepot: false,
      showPopupUpdateDepot: false,
      pairKinds: [
        {
          value: "Non",
          label: "Non",
        },
        {
          value: "Oui",
          label: "Oui",
        }
      ],
      showButtonRFV: true, showUploadIconRFV: true, showReloadRFV: false, labelRFV: "", dateRFV: "",
      progressRFV: 0, currentFileRFV: undefined, successUploadRFV: false, giveOKRFV:false,

      showButtonRAP: true, showUploadIconRAP: true, showReloadRAP: false, labelRAP: "", dateRAP: "",
      progressRAP: 0, currentFileRAP: undefined, successUploadRAP: false, giveOKRAP:false,

      showButtonASP: true, showUploadIconASP: true, showReloadASP: false, labelASP: "", dateASP: "",
      progressASP: 0, currentFileASP: undefined, successUploadASP: false, giveOKASP:false,

      showButtonDCS: true, showUploadIconDCS: true, showReloadDCS: false, labelDCS: "", dateDCS: "",
      progressDCS: 0, currentFileDCS: undefined, successUploadDCS: false,

      itsITandEM: false,
      showModalConfirmRelanceDepot: false,
      withPair: "NO",
      pairId: "",
      pairExist: "",
      pairFullName: "",
      verifyPairField: "",
      isEMGCStudent: "",
      projectTitle: "",
    };

    this.state.studentFullName = sessionStorage.getItem("studentFullName");


    var requestAODPF = new XMLHttpRequest();
    requestAODPF.open("GET", API_URL_MESP + "itsITandEM/" + currentStudent.id, false);
    requestAODPF.send(null);

    this.state.itsITandEM= requestAODPF.responseText;

    var requestfp = new XMLHttpRequest();
    requestfp.open("GET", API_URL_MESP + "fichePFEDepot/" + currentStudent.id, false);
    requestfp.send(null);
    let depSt = requestfp.responseText;

    // console.log('----------------------------------------> RES123: ' + this.state.isEMGCStudent)


    if(depSt.includes("timestamp"))
    {
      this.state.depotStatus = "";
    }
    else
    {
      this.state.depotStatus = depSt;
    }

    let saria =  "HelloWorld";
    // console.log('------------1-------------SARS--> ', saria.split("").reverse().join(""));
    // console.log('------------2-------------SARS--> ', this.state.depotStatus);


    // Trainee Kind
    var requestTK = new XMLHttpRequest();
    requestTK.open("GET", API_URL_MESP + "traineeKinds", false);
    requestTK.send(null);
    let tk = JSON.parse(requestTK.responseText);

    // console.log('-------------------------tk-1-> ', tk);

    let crsList = this.state.allTraineeshipKinds.slice();
    for (let i of tk)
    {
      const tkObj = { value: i, label: i, color: ""};
      crsList.push(tkObj);
    };

    this.state.allTraineeshipKinds = crsList;

    // console.log('-------------------------tk-2-> ', crsList);
    // console.log('-------------------------tk-3-> ', this.state.allTraineeshipKinds);

    // console.log('-------------------------------------loli-------------tk-2-> ', this.state.stepper);

  }

  componentDidMount() {
    AuthService.getReports(currentStudent.id).then((response) => {

      this.setState({
        fileInfos: response.data,
      });

      // console.log('--------------------------------Mount-1');
      // console.log('Mount-3', this.state.fileInfos);
      // console.log('--------------------------------Mount-2');

      for (let fn of this.state.fileInfos)
      {
        if(fn.includes("RFV"))
        {
          this.setState({
            labelRFV: fn.substr(0, fn.indexOf("UNITR1RFV")) + ".pdf",
            dateRFV: fn.substr(fn.indexOf("UNITR1RFV") + 9),
            showButtonRFV: false,
            showReloadRFV: true
          });
        }

        if(fn.includes("RAP"))
        {
          this.setState({
            labelRAP: fn.substr(0, fn.indexOf("UNITR1RAP")) + ".pdf",
            dateRAP: fn.substr(fn.indexOf("UNITR1RAP") + 9),
            showButtonRAP: false,
            showReloadRAP: true
          });
        }

        if(fn.includes("ASP"))
        {
          this.setState({
            labelASP: fn.substr(0, fn.indexOf("UNITR1ASP")) + ".pdf",
            dateASP: fn.substr(fn.indexOf("UNITR1ASP") + 9),
            showButtonASP: false,
            showReloadASP: true
          });
        }

        if(fn.includes("DCS"))
        {
          this.setState({
            labelDCS: fn.substr(0, fn.indexOf("UNITR1DCS")) + ".pdf",
            dateDCS: fn.substr(fn.indexOf("UNITR1DCS") + 9),
            showButtonDCS: false,
            showReloadDCS: true
          });
        }
      }


      // {fileInfos.substr(0, fileInfos.indexOf("UNITR1"))}.pdf
      // console.log('---------------------------------> LOL123: ', this.state.labelRFV)


      // console.log('-------------------------------------------------------> A');
      // console.log(response.data);
      // console.log('-------------------------------------------------------> B');

      /*
          for (const [i, value] of response.data.entries())
          {
              console.log('Iterate -----> ' + i + ' - ' + value);
          }

          for (const [i, value] of this.state.fileInfos.entries())
          {
              console.log('HI -----> ' + i + ' - ' + value);
          }

          console.log('-------------------------------------------------------> C');
          */
    });
  }

  addPairHandler(e)
  {
    this.setState({ pairId: e.target.value });

    if (e.target.value.length !== 0 && e.target.value.length !== 10)
    {
      this.setState({ verifyPairField: "NO", pairExist: "" });
    }

    if (e.target.value.length === 0 || e.target.value.length === 10)
    {
      this.setState({ verifyPairField: "YES" });
    }
    // console.log('-------------VERIF PAIR--------------> SARS: ' + this.state.verifyPairField + " - " + e.target.value);

    if (e.target.value.length === 10)
    {
      // console.log('----------------VERIF PAIR--------------------- My Pair FullName 1: ' + this.state.pairId);

      var requestb = new XMLHttpRequest();

      requestb.open("GET", API_URL_MESP + "studentFullName/" + e.target.value, false);
      requestb.send(null);
      let pair = requestb.responseText;

      // console.log('------------------PAIR-FULL-1');
      // console.log(pair);
      // console.log('------------------PAIR-FULL-2');

      if (pair === "")
      {
        this.setState({pairExist: "NO"});
      }
      else
      {
        this.setState({ pairExist: "YES", pairFullName: pair });
      }
      // console.log('------------------VERIF PAIR------------------- My Pair FullName 2: ' + this.state.pairExist + " - " + pair);
    }
  }

  modifyMyDepot()
  {
    this.setState({ fileInfos: [] });
    AuthService.updateMyDepot(currentStudent.id).then(
      (response) =>
      {
        this.setState({
          showModalConfirmRelanceDepot: false,
          showReloadRFV: false, showButtonRFV: true, labelRFV: "", showUploadIconRFV: true, checked: false,
          showReloadRAP: false, showButtonRAP: true, labelRAP: "", showUploadIconRAP: true,
          showReloadASP: false, showButtonASP: true, labelASP: "", showUploadIconASP: true,
          showReloadDCS: false, showButtonDCS: true, labelDCS: "", showUploadIconDCS: true,
        });
        // console.log("Check Disponibility ----df----cr----- SUCCESS");
        /*this.setState({ showPopupUpdateDepot: false });*/
      },
      (error) => {
        // console.log("Check Disponibility --------cr----- FAIL");
      }
    );
  }

  modifyMyRFV()
  {
    AuthService.updateMyRFV(currentStudent.id).then(
      (response) =>
      {
        this.setState({
          showReloadRFV: false,
          showButtonRFV: true,
          labelRFV: "",
          dateRFV:"",
          showUploadIconRFV: true,
          checked: false,
        });
        // console.log("Check Disponibility ----df----cr----- FAIL");
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
      })
      .catch(() => {
        // console.log('-------------------------> 3');

      });
  }

  modifyMyRAP()
  {
    AuthService.updateMyRAP(currentStudent.id).then(
      (response) =>
      {
        this.setState({
          showReloadRAP: false,
          showButtonRAP: true,
          labelRAP: "",
          dateRAP:"",
          showUploadIconRAP: true
        });
        // console.log("Check Disponibility ----df----cr----- FAIL");
        /*this.setState({ showPopupUpdateDepot: false });*/
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
      })
      .catch(() => {
        // console.log('-------------------------> 3');

      });
  }

  modifyMyASP()
  {
    AuthService.updateMyASP(currentStudent.id).then(
      (response) =>
      {
        this.setState({
          showReloadASP: false,
          showButtonASP: true,
          labelASP: "",
          dateASP:"",
          showUploadIconASP: true
        });
        // console.log("Check Disponibility ----df----cr----- FAIL");
        /*this.setState({ showPopupUpdateDepot: false });*/
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
      })
      .catch(() => {
        // console.log('-------------------------> 3');

      });
  }

  modifyMyDCS()
  {
    AuthService.updateMyDCS(currentStudent.id).then(
      (response) =>
      {
        this.setState({
          showReloadDCS: false,
          showButtonDCS: true,
          labelDCS: "",
          dateDCS:"",
          showUploadIconDCS: true
        });
        // console.log("Check Disponibility ----df----cr----- FAIL");
        /*this.setState({ showPopupUpdateDepot: false });*/
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
      })
      .catch(() => {
        // console.log('-------------------------> 3');

      });
  }

  reportConfidentialHandler(e) {
    // console.log('----------------------------CHECKED 1: ' + e.target.checked + " - " + this.state.checked);
    if (this.state.checked === false) {
      this.setState({ checked: true });
      // console.log('----------------------2');
    }

    if (this.state.checked === true) {
      this.setState({ checked: false });
      // console.log('----------------------1');
    }

    // console.log('----------------------------CHECKED 2: ' + e.target.checked + " - " + this.state.checked);
  }

  selectReportFinalVersion(event) {
    let selFiles = event.target.files;
    this.setState({
      currentFileRFV: selFiles[0],
    });

    /*console.log('-------------RFV-1--------', event.target.files);
    console.log('-------------RFV-2---------------> All files in DB: 1' , event.target.files[0].name);

    console.log('-------------GIVE OK-1--------', this.state.itsITandEM + " - " + this.state.giveOKRFV);
    */
    if(event.target.files[0].name.toUpperCase().includes("PDF"))
    {
      this.state.giveOKRFV= true
    }
    else
    {
      this.state.giveOKRFV= false
    }

    // console.log('-------------GIVE OK-4--------', this.state.itsITandEM + " - " + this.state.giveOKRFV);

    AuthService.getReports(currentStudent.id).then(
      (response) => {
        let files = response.data;
        // console.log('---------------RFV-3--------------> All files in DB-1: ' + files.length);
        // console.log(files);
        // console.log('---------------RFV-4--------------> All files in DB-2: ' + files.length);
        /*if (files.length === 0) {

        }*/
      },
      (error) => {
        // console.log('SELECT -------------- Error');
      }
    );


    // console.log('RFV---------------ALL FILES-----> All files in DB: 0', event.target.files);

    if(this.state.giveOKRFV)
    {
      this.state.showUploadIconRFV= false;
      this.state.labelRFV= event.target.files[0].name;
      // console.log('---------------RFV-6--------------> A' + this.state.showUploadIconRFV);

      let notif =
        "Vous avez choisi le fichier " + event.target.files[0].name + " .";
      return NotificationManager.success(notif, "Information", 6000);
    }


  }

  selectReportAntiPlagiat(event) {
    // console.log('---------------RAP-0--------------> A');
    let selFiles = event.target.files;
    this.setState({
      currentFileRAP: selFiles[0]
    });

    if(event.target.files[0].name.toUpperCase().includes("PDF"))
    {
      this.state.giveOKRAP= true
    }
    else
    {
      this.state.giveOKRAP= false
    }

    // console.log('--------------------------------------------------------------> 1: ' + this.state.giveOKRAP);

    AuthService.getReports(currentStudent.id).then(
      (response) => {
        let files = response.data;
        // console.log('---------------RAP-3--------------> All files in DB-1: ' + files.length);
        // console.log(files);
        // console.log('---------------RAP-4--------------> All files in DB-2: ' + files.length);
        /*if (files.length === 0) {
          console.log('---------------RFV-5.1--hgvh------------> All files in DB: 0');

        }*/
      },
      (error) => {
        // console.log('SELECT -------------- Error');
      }
    );


    // console.log('RAP---------------ALL FILES-----> All files in DB: 0', event.target.files);

    if(this.state.giveOKRAP)
    {
      this.state.showUploadIconRAP= false;
      this.state.labelRAP= event.target.files[0].name;
      // console.log('---------------RAP-6--------------> A' + this.state.showUploadIconRAP);

      let notif =
        "Vous avez choisi le fichier " + event.target.files[0].name + " .";
      return NotificationManager.success(notif, "Information", 6000);
    }


  }

  selectAttestationStagePFE(event) {
    // console.log('---------------ASP-0--------------> A');
    let selFiles = event.target.files;
    this.setState({
      currentFileASP: selFiles[0]
    });


    if(event.target.files[0].name.toUpperCase().includes("PDF"))
    {
      this.state.giveOKASP= true
    }
    else
    {
      this.state.giveOKASP= false
    }

    AuthService.getReports(currentStudent.id).then(
      (response) => {
        let files = response.data;
        // console.log('---------------ASP-3--------------> All files in DB-1: ' + files.length);
        // console.log(files);
        // console.log('---------------ASP-4--------------> All files in DB-2: ' + files.length);
        /*if (files.length === 0) {

        }*/
      },
      (error) => {
        // console.log('SELECT -------------- Error');
      }
    );


    // console.log('ASP---------------ALL FILES-----> All files in DB: 0', event.target.files);

    if(this.state.giveOKASP)
    {
      this.state.showUploadIconASP= false;
      this.state.labelASP= event.target.files[0].name;
      // console.log('---------------ASP-6--------------> A' + this.state.showUploadIconASP);

      let notif =
        "Vous avez choisi le fichier " + event.target.files[0].name + " .";
      return NotificationManager.success(notif, "Information", 6000);
    }


  }

  selectDossierDocumentSupplemenaire(event) {
    // console.log('---------------DCS-0--------------> A');
    let selFiles = event.target.files;
    this.setState({
      currentFileDCS: selFiles[0]
    });

    AuthService.getReports(currentStudent.id).then(
      (response) => {
        let files = response.data;
        // console.log('---------------DCS-3--------------> All files in DB-1: ' + files.length);
        // console.log(files);
        // console.log('---------------DCS-4--------------> All files in DB-2: ' + files.length);
        /*if (files.length === 0) {

        }*/
      },
      (error) => {
        // console.log('SELECT -------------- Error');
      }
    );


    // console.log('DCS---------------ALL FILES-----> All files in DB: 0', event.target.files);

    this.state.showUploadIconDCS= false;
    this.state.labelDCS= event.target.files[0].name;
    // console.log('---------------DCS-6--------------> A' + this.state.showUploadIconDCS);

    let notif =
      "Vous avez choisi le fichier " + event.target.files[0].name + " .";
    return NotificationManager.success(notif, "Information", 6000);


  }

  closeModalSuccessUpload()
  {
    this.setState({showPopupSuccessUpload: false});
  }

  openModalConfirmRelanceDepot()
  {
    // console.log('--------------------- RELANCE 1');
    this.setState({showModalConfirmRelanceDepot: true});
  }

  closeModalConfirmRelanceDepot()
  {
    // console.log('--------------------- RELANCE 2');
    this.setState({showModalConfirmRelanceDepot: false});
  }

  uploadReportFinalVersion() {

    this.setState({
      progressRFV: 0,
    });

    AuthService.uploadReportV2(
      this.state.currentFileRFV,
      currentStudent.id,
      this.state.traineeShipKind,
      this.state.checked,
      (event) => {
        this.setState({
          showButtonRFV: false,
          progressRFV: Math.round((100 * event.loaded) / event.total),
          successUploadRFV: true,
        });
      }
    )
      .then((response) => {
        // console.log('-------------------------> 1');
        this.setState({
          successUploadRFV: false,
          message: response.data.message,
          showPopupSuccessUpload: true,
          showReloadRFV: true,
        });
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
        // console.log('-------------------------> 2.1');
        // console.log(this.state.fileInfos);
        // console.log('-------------------------> 2.2');
        // console.log(files.data);
        // console.log('-------------------------> 2.3');
      })
      .catch(() => {
        // console.log('-------------------------> 3');
        this.setState({
          progressRFV: 0,
          message: "Could not upload the file !.",
          currentFileRFV: undefined,
        });
      });
  }

  uploadPlagiat() {

    this.setState({
      progressRAP: 0,
    });


    AuthService.uploadPlagiat(
      this.state.currentFileRAP,
      currentStudent.id,
      this.state.checked,
      (event) => {
        this.setState({
          showButtonRAP: false,
          progressRAP: Math.round((100 * event.loaded) / event.total),
          successUploadRAP: true,
        });
      }
    )
      .then((response) => {
        // console.log('-------------------------> 1');
        this.setState({
          successUploadRAP: false,
          message: response.data.message,
          showPopupSuccessUpload: true,
          showReloadRAP: true,
        });
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
        // console.log('-------------------------> 2.1');
        // console.log(this.state.fileInfos);
        // console.log('-------------------------> 2.2');
        // console.log(files.data);
        // console.log('-------------------------> 2.3');
      })
      .catch(() => {
        // console.log('-------------------------> 3');
        this.setState({
          progressRAP: 0,
          message: "Could not upload the file !.",
          currentFileRAP: undefined,
        });
      });

  }

  uploadSupplement() {

    this.setState({
      progressDCS: 0,
    });


    AuthService.uploadSupplement(
      this.state.currentFileDCS,
      currentStudent.id,
      this.state.checked,
      (event) => {
        this.setState({
          showButtonDCS: false,
          progressDCS: Math.round((100 * event.loaded) / event.total),
          successUploadDCS: true,
        });
      }
    )
      .then((response) => {
        // console.log('-------------------------> 1');
        this.setState({
          successUploadDCS: false,
          message: response.data.message,
          showPopupSuccessUpload: true,
          showReloadDCS: true,
        });
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
        // console.log('-------------------------> 2.1');
        // console.log(this.state.fileInfos);
        // console.log('-------------------------> 2.2');
        // console.log(files.data);
        // console.log('-------------------------> 2.3');
      })
      .catch(() => {
        // console.log('-------------------------> 3');
        this.setState({
          progressDCS: 0,
          message: "Could not upload the file !.",
          currentFileDCS: undefined,
        });
      });

  }

  uploadTrainingCertif() {

    this.setState({
      progressASP: 0,
    });

    AuthService.uploadInternshipCertifcate(
      this.state.currentFileASP,
      currentStudent.id,
      this.state.checked,
      (event) => {
        this.setState({
          showButtonASP: false,
          progressASP: Math.round((100 * event.loaded) / event.total),
          successUploadASP: true,
        });
      }
    )
      .then((response) => {
        // console.log('-------------------------> 1');
        this.setState({
          successUploadASP: false,
          message: response.data.message,
          showPopupSuccessUpload: true,
          showReloadASP: true,
        });
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        this.setState({
          fileInfos: files.data,
        });
        // console.log('-------------------------> 2.1');
        // console.log(this.state.fileInfos);
        // console.log('-------------------------> 2.2');
        // console.log(files.data);
        // console.log('-------------------------> 2.3');
      })
      .catch(() => {
        // console.log('-------------------------> 3');
        this.setState({
          progressASP: 0,
          message: "Could not upload the file !.",
          currentFileASP: undefined,
        });
      });

  }

  deleteReportFile(id) {
    // console.log('-------------------------> OK: Delete 0: ' + id);

    AuthService.deleteReportById(id)
      .then((response) => {
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> OK: Delete 1: ' + id);
        return AuthService.getReports(currentStudent.id);
      })
      .then((files) => {
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> OK: Delete 2: ' + id);
        this.setState({
          fileInfos: files.data,
        });
      })
      .catch(() => {
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete 3: ' + id);
      });
  }

  onChangeTraineeShipKind(i, val)
  {
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete A: ' + i);
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete B: ' + val);
    this.setState({
      checkedTraineeShip: i,
      traineeShipKind: val,
    });

    this.state.checkedTraineeShip = i;
    this.state.traineeShipKind = val;

    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete 1: ' + this.state.checkedTraineeShip);
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete 2: ' + this.state.traineeShipKind);
  }

  onChangePairKind(i, val)
  {
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> PAIR A: ' + i);
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> PAIR B: ' + val);
    this.setState({
      checkedPair: i,
      pairKind: val,
    });

    this.state.checkedPair = i;
    this.state.pairKind = val;

    if(i === 2)
    {
      this.state.withPair = "NO";
    }
    if(i === 3)
    {
      this.state.withPair = "YES";
    }

    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> PAIR 1: ' + this.state.checkedPair);
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> PAIR 2: ' + this.state.pairKind);
    // console.log('xxxxxxxxxxxxxxxxxxxxxxx> PAIR 3: ' + this.state.withPair);
  }

  confirmStep1()
  {
    this.setState({ stepper: "Step2" });
  }

  confirmStep2()
  {
    var requestfp = new XMLHttpRequest();
    requestfp.open("GET", API_URL_MESP + "fichePFEVU/" + currentStudent.id + "/" + this.state.traineeShipKind + "/" + this.state.projectTitle, false);
    requestfp.send(null);
    this.state.isEMGCStudent = requestfp.responseText;

    var requestAODPF = new XMLHttpRequest();
    requestAODPF.open(
      "GET",
      API_URL_MESP + "itsITandEM/" + currentStudent.id,
      false
    );
    requestAODPF.send(null);

    if(this.state.isEMGCStudent === "NO")
    {
      this.setState({
        stepper: "Step3",
        itsITandEM: requestAODPF.responseText,
      });
    }
    if(this.state.isEMGCStudent === "YES")
    {
      this.setState({
        stepper: "StepEMGC",
      });
    }

    // console.log('---------------------------ALLOW OFF: ' + this.state.itsITandEM)
  }

  confirmStepEMGC()
  {
    // console.log('====================================> PAIR-1: ' + this.state.pairId);
    let pairId = "";
    if (this.state.pairId === "" || this.state.pairId === undefined)
    {
      pairId = "--";  // 219JMT0000
    }
    // console.log('====================================> PAIR-2: ' + this.state.pairId);

    var requestAODPF = new XMLHttpRequest();
    requestAODPF.open("GET", API_URL_MESP + "itsITandEM/" + currentStudent.id, false);
    requestAODPF.send(null);

    this.state.stepper = "Step3";
    this.state.itsITandEM = requestAODPF.responseText;

    this.setState({stepper : "Step3", itsITandEM : requestAODPF.responseText});

    console.log('====================================> PAIR-3: ' + this.state.pairId);
    console.log('====================================> PAIR-4: ' + this.state.withPair);

    if( this.state.withPair === "YES" )
    {
      var requestfp = new XMLHttpRequest();
      requestfp.open("GET", API_URL_MESP + "fichePFEAddBinome/" + currentStudent.id + "/" + this.state.pairId, false);
      requestfp.send(null);
      let res = requestfp.responseText;
      console.log('====================================> PAIR-5: ' + this.state.pairId);
    }

    // console.log('====================================> PAIR-4: ' + " - " + this.state.stepper + " - " + this.state.itsITandEM);
  }

  confirmStep3()
  {
    this.setState({ stepper: "Step4" });
  }

  closeShowPopupSuccessDepot()
  {
    this.setState({ showPopupSuccessDepot: false });
  }

  requestForUpdateMyDepot()
  {
    this.setState({ showPopupUpdateDepot: true });
  }

  updateMyDepotNO()
  {
    this.setState({ showPopupUpdateDepot: false });
  }

  updateMyDepotYES()
  {
    this.setState({
      fileInfos: [],
      depotStatus: "",
      showPopupUpdateDepot: false,
      checked: false,
      showReloadRFV: false, showButtonRFV: true, labelRFV: "", showUploadIconRFV: true,
      showReloadRAP: false, showButtonRAP: true, labelRAP: "", showUploadIconRAP: true,
      showReloadASP: false, showButtonASP: true, labelASP: "", showUploadIconASP: true,
    });
    AuthService.updateMyDepot(currentStudent.id).then(
      (response) =>
      {
        this.setState({ showPopupUpdateDepot: false });
      },
      (error) => {
        // console.log("Check Disponibility --------cr----- FAIL");
      }
    );
  }

  validateMyDepot()
  {
    this.setState({ stepper: "Step5", depotStatus: "NOTYET", showSpinnerWaitingDepot: true });

    AuthService.validateMyDepot(currentStudent.id).then(
      (response) =>
      {
        var requestfp = new XMLHttpRequest();
        requestfp.open(
          "GET",
          API_URL_MESP + "fichePFEDepot/" + currentStudent.id,
          false
        );
        requestfp.send(null);

        this.setState({ showSpinnerWaitingDepot: false, showPopupSuccessDepot: true, depotStatus: requestfp.responseText });
        // console.log("Check Disponibility --------cr----- SUCCESS");
      },
      (error) => {
        // console.log("Check Disponibility --------cr----- FAIL");
      }
    );
  }

  render() {
    const {
      message,
      fileInfos,
      successUpload,
      showPopupSuccessUpload,
      allTraineeshipKinds,
      pairKinds,
      withPair,
      pairId,
      studentFullName,
      pairExist,
      pairFullName,
      stepper,
      showPopupSuccessDepot,
      depotStatus,
      showSpinnerWaitingDepot,
      showPopupUpdateDepot,
      showUploadIconRFV, showButtonRFV, progressRFV, labelRFV, dateRFV, currentFileRFV, successUploadRFV, showReloadRFV,giveOKRFV,
      showUploadIconRAP, showButtonRAP, progressRAP, labelRAP, dateRAP, currentFileRAP, successUploadRAP, showReloadRAP, giveOKRAP,
      showUploadIconASP, showButtonASP, progressASP, labelASP, dateASP, currentFileASP, successUploadASP, showReloadASP, giveOKASP,
      showUploadIconDCS, showButtonDCS, progressDCS, labelDCS, dateDCS, currentFileDCS, successUploadDCS, showReloadDCS,
      itsITandEM,
      showModalConfirmRelanceDepot,
      isEMGCStudent
    } = this.state;

    const { classes } = this.props;

    return (
      <div>
        <center>
          <strong>
            <span className="menuTitleRedNet">Déposer mon Rapport PFE</span>
          </strong>
        </center>
        <hr/>

        <br/><br/>


        <CCard accentColor="info">

            <CCardBody>

              {fileInfos.length >= 0  &&(
                <>
                  <CRow>
                    <CCol md="4" class="bg-warning py-2">
                      <NotificationContainer/>
                      {(
                        labelRFV === "" && showUploadIconRFV &&
                        <center>
                          <label
                            htmlFor="filePickerRAP"
                            className="custom-file-upload">
                            <img
                              src={redUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Rapport Version Finale"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerRAP"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              accept="application/pdf"
                              onChange={this.selectReportFinalVersion}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelRFV !== "" &&
                        <CRow>
                          <CCol md="2"/>
                          <CCol md="8">
                            <CCard color="danger" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelRFV}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateRFV}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="2"/>
                        </CRow>
                      )}

                      {(
                        showReloadRFV && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="danger" aria-expanded="true"
                                    onClick={() => this.modifyMyRFV()}>
                            <CTooltip content="Modifier mon Rapport Version Finale" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileRFV && !giveOKRFV && (
                        <center>
                          <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                            Il ne s'agit pas d'un Fichier PDF !.
                            <br/>
                            Merci de respecter le format demandé.
                          </div>
                        </center>
                      )}

                      {currentFileRFV && successUploadRFV && (
                        <div className="progress">
                          <div
                            className="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressRFV}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressRFV + "%" }}>
                            {progressRFV}%
                          </div>
                        </div>
                      )}

                      {showButtonRFV && giveOKRFV && labelRFV !== "" && (
                        <center>
                          <Checkbox
                            checked={this.state.checked}
                            color="secondary"
                            onChange={(e) =>
                              this.reportConfidentialHandler(e)
                            }
                            inputProps={{ "aria-label": "secondary checkbox" }}
                          />
                          &nbsp;&nbsp; Confidendial Upload
                        </center>
                      )}

                      {(
                        showButtonRFV &&
                        <center>
                          <CButton  variant="outline" color="danger"
                                    onClick={this.uploadReportFinalVersion}
                                    disabled={labelRFV === ""}
                                    enabled={labelRFV !== ""}>
                            Déposer Rapport Version Finale
                          </CButton>
                        </center>
                      )}


                    </CCol>

                    <CCol md="4" class="bg-warning py-2">
                      {(
                        labelRAP === "" && showUploadIconRAP &&
                        <center>
                          <label
                            htmlFor="filePickerASP"
                            className="custom-file-upload">
                            <img
                              src={blueLightUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Rapport Anti-Plagiat"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerASP"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              accept="application/pdf"
                              onChange={this.selectReportAntiPlagiat}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelRAP !== "" &&
                        <CRow>
                          <CCol md="2"/>
                          <CCol md="8">
                            <CCard color="info" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelRAP}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateRAP}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="2"/>
                        </CRow>
                      )}

                      {(
                        showReloadRAP && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="info" aria-expanded="true"
                                    onClick={() => this.modifyMyRAP()}>
                            <CTooltip content="Modifier mon Rapport Anti-Plagiat" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileRAP && !giveOKRAP && (
                        <center>
                          <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                            Il ne s'agit pas d'un Fichier PDF !.
                            <br/>
                            Merci de respecter le format demandé.
                          </div>
                        </center>
                      )}

                      {currentFileRAP && successUploadRAP && (
                        <div className="progress">
                          <div
                            className="progress-bar bg-info progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressRAP}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressRAP + "%" }}>
                            {progressRAP}%
                          </div>
                        </div>
                      )}

                      {(
                        showButtonRAP &&
                        <center>
                          <CButton  variant="outline" color="info"
                                    onClick={this.uploadPlagiat}
                                    disabled={labelRAP === ""}
                                    enabled={labelRAP !== ""}>
                            Déposer Rapport Anti-Plagiat
                          </CButton>
                        </center>
                      )}

                    </CCol>

                    <CCol md="4" class="bg-warning py-2">

                      {(
                        labelASP === "" && showUploadIconASP &&
                        <center>
                          <label
                            htmlFor="filePickerRFV"
                            className="custom-file-upload">
                            <img
                              src={orangeUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Attestation Stage PFE"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerRFV"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              accept="application/pdf"
                              onChange={this.selectAttestationStagePFE}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelASP !== "" &&
                        <CRow>
                          <CCol md="2"/>
                          <CCol md="8">
                            <CCard color="warning" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelASP}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateASP}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="2"/>
                        </CRow>
                      )}

                      {(
                        showReloadASP && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="warning" aria-expanded="true"
                                    onClick={() => this.modifyMyASP()}>
                            <CTooltip content="Modifier mon Attestation Stage PFE" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileASP && !giveOKASP && (
                        <center>
                          <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                            Il ne s'agit pas d'un Fichier PDF !.
                            <br/>
                            Merci de respecter le format demandé.
                          </div>
                        </center>
                      )}

                      {currentFileASP && successUploadASP && (
                        <div className="progress">
                          <div
                            className="progress-bar bg-warning progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressASP}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressASP + "%" }}>
                            {progressASP}%
                          </div>
                        </div>
                      )}

                      {(
                        showButtonASP &&
                        <center>
                          <CButton  variant="outline" color="warning"
                                    onClick={this.uploadTrainingCertif}
                                    disabled={labelASP === ""}
                                    enabled={labelASP !== ""}>
                            Déposer Attestation Stage PFE
                          </CButton>
                        </center>
                      )}


                    </CCol>

                    <br/>

                  </CRow>
                  <br/>
                  {(fileInfos.length > 0 &&
                    <Divider />
                  )}

                </>
              )}

              {showPopupSuccessUpload && (
                <Modal
                  isOpen={showPopupSuccessUpload}
                  contentLabel="Example Modal"
                  style={customStyles}
                >
                  <CIcon
                    name="cil-warning"
                    style={{ color: "blue" }}
                  />
                  &nbsp;&nbsp;
                  <span style={{ color: "blue" }}>
                                      Information
                                    </span>
                  <hr />
                  <center>
                    <br />
                    <br />
                    {message}
                    <br />
                    <br />
                    <br />
                    <button
                      className="btn btn-sm btn-primary"
                      onClick={this.closeModalSuccessUpload}
                    >
                      Ok
                    </button>
                    <br />
                    <br />
                  </center>
                </Modal>
              )}
              {showModalConfirmRelanceDepot && (
                <Modal
                  isOpen={showModalConfirmRelanceDepot}
                  contentLabel="Example Modal"
                  style={customStyles}
                >
                  <CIcon
                    name="cil-warning"
                    style={{ color: "blue" }}
                  />
                  &nbsp;&nbsp;
                  <span style={{ color: "blue" }}>
                                      Confirmation
                                    </span>
                  <hr />
                  <center>
                    <br />
                    <br />
                    Êtes-vous sûr de vouloir relancer votre dépôt ?
                    <br />
                    <br />
                    <br />
                    <button className="btn btn-sm btn-success"
                            onClick={this.modifyMyDepot}>
                      Oui
                    </button>
                    &nbsp;
                    <button className="btn btn-sm btn-danger"
                            onClick={this.closeModalConfirmRelanceDepot}>
                      Non
                    </button>
                    <br />
                    <br />
                  </center>
                </Modal>
              )}

              {fileInfos.length >0 && (
                <div>

                  <CContainer>
                    <CRow>
                      <CCol md="9" class="bg-warning py-2">
                        <div className="card-header">
                          <span className="menuTitleBleuReport">Informations sur mes Rapports</span>
                        </div>

                        <ul className="list-group list-group-flush">
                          {fileInfos.map((file, index) => (
                            <li className="list-group-item" key={index}>
                              <CRow>
                                <CCol md="4" class="bg-warning py-2">
                                  {file.includes("RFV") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="danger">Rapport Version Finale</CListGroupItem>
                                    </CListGroup>
                                  )}
                                  {file.includes("RAP") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="info">Rapport Anti-Plagiat : URKUND</CListGroupItem>
                                    </CListGroup>
                                  )}
                                  {file.includes("ASP") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="warning">Attestation Stage PFE</CListGroupItem>
                                    </CListGroup>
                                  )}
                                </CCol>
                                <CCol md="6" class="bg-warning py-2">
                              <span>
                                  {file.substr(0, file.indexOf("UNITR1"))}.pdf
                                </span>
                                </CCol>
                                <CCol md="2" class="bg-warning py-2">
                              <span>
                                  {file.substr(file.indexOf("UNITR1") + 9)}
                                </span>
                                </CCol>
                              </CRow>
                            </li>
                          ))}
                        </ul>
                      </CCol>
                      <CCol lg="3">
                        <br/>
                        {(
                          depotStatus === "" &&
                          <center>
                            <br/><br/>

                            <span className="text-primary" style={{fontSize: "11px"}}>
                                      Cliquer sur ce bouton pour <ins>relancer</ins> votre Dépôt.
                                    </span>
                            <br/>
                            <Icon icon={handPointingDown} color="blue" width="30" height="30" />
                            <br/><br/>

                            <CButton  shape="pill" color="primary" aria-expanded="true"
                                      onClick={() => this.openModalConfirmRelanceDepot()}>
                              <CTooltip content="Relancer mon Dépôt" placement="top">
                                <CIcon content={freeSet.cilReload}/>
                              </CTooltip>
                            </CButton>
                          </center>
                        )}
                        {( depotStatus.includes("DEPOSEE") &&
                          <CAlert color="info">
                            <h5 className="alert-heading"><strong>Dépôt Effectué</strong></h5>
                            <p>
                              Votre requête a été bien déposée le <ins>{depotStatus.substring(7, depotStatus.length)}</ins>.
                            </p>
                            <p>
                              Elle va être traitée le plus tôt possible.
                            </p>
                            <hr />
                            <p className="mb-0">
                              Merci de patienter.
                            </p>
                          </CAlert>
                        )}

                        {( depotStatus.includes("VALIDEE") &&
                          <CAlert color="success">
                            <h5 className="alert-heading"><strong>Dépôt Validé</strong></h5>
                            <p>
                              Votre requête a été <ins>validée</ins> le <ins>{depotStatus.substring(7, depotStatus.length)}</ins>.
                            </p>
                            <hr />
                            <p className="mb-0">
                              Bonne Chance.
                            </p>
                          </CAlert>
                        )}

                        {( depotStatus.includes("INCOMPLET") &&
                          <CAlert color="danger">
                            <h5 className="alert-heading"><strong>Dépôt Incomplet</strong></h5>
                            <p>
                              Votre requête a été marquée comme <ins>incomplète</ins> le <ins>{depotStatus.substring(9, 19)}</ins>.
                            </p>
                            <strong>Motif: </strong> {depotStatus.substring(19, depotStatus.length)}.
                            <hr />
                            <CButton  variant="outline" color="danger"
                                      onClick={() => this.requestForUpdateMyDepot()}>
                              Mettre à jour mon Dépôt
                            </CButton>
                          </CAlert>
                        )}
                      </CCol>
                    </CRow>
                  </CContainer>
                </div>
              )}

            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol sm="4" md="3"/>
                <CCol sm="4" md="7">
                  {(
                    depotStatus === "" && fileInfos.length === 3 &&
                    <>
                                  <span className="text-success" style={{fontSize: "11px"}}>
                                    Cliquer sur ce bouton pour <strong> valider votre dépôt </strong> ( <ins>PS:</ins> Une fois Cliquer, vous ne pourrez pas modifier votre dépôt ).
                                  </span>
                      &nbsp;&nbsp;&nbsp;
                      <Icon icon={handPointingRight} color="green" width="30" height="30" />
                    </>
                  )}
                </CCol>
                <CCol sm="4" md="2">
                  {(
                    depotStatus.includes("DEPOSEE") &&
                    <h5><CBadge color="primary">Requête Envoyée</CBadge></h5>
                  )}

                  {(
                    depotStatus.includes("VALIDEE") &&
                    <h5><CBadge color="success">Dépôt Validé</CBadge></h5>
                  )}

                  {(
                    depotStatus.includes("INCOMPLET") &&
                    <h5><CBadge color="danger">Dépôt Incomplet</CBadge></h5>
                  )}
                  {(
                    (depotStatus === "" ) &&
                    <CButton  color="success"
                              disabled={fileInfos.length !== 3}
                              enabled={fileInfos.length === 3}
                              className="float-right"
                              onClick={() => this.validateMyDepot()}>
                      Je valide
                    </CButton>
                  )}

                  {(
                    showSpinnerWaitingDepot &&
                    <Backdrop className={classes.backdrop} open={showSpinnerWaitingDepot} >
                      <CircularProgress color="inherit" />
                    </Backdrop>
                  )}



                </CCol>
              </CRow>
            </CCardFooter>
          </CCard>


        {( (fileInfos.length ===3 || fileInfos.length >0) && itsITandEM === "NO" &&
          <CCard accentColor="info">

            <CCardBody>

              {fileInfos.length >= 0  &&(
                <>
                  <CRow>
                    <CCol md="3" class="bg-warning py-2">
                      <NotificationContainer/>
                      {(
                        labelRFV === "" && showUploadIconRFV &&
                        <center>
                          <label
                            htmlFor="filePickerRAP"
                            className="custom-file-upload">
                            <img
                              src={redUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Rapport Version Finale"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerRAP"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              accept="application/pdf"
                              onChange={this.selectReportFinalVersion}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelRFV !== "" &&
                        <CRow>
                          <CCol md="1"/>
                          <CCol md="10">
                            <CCard color="danger" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelRFV}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateRFV}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="1"/>
                        </CRow>
                      )}

                      {(
                        showReloadRFV && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="danger" aria-expanded="true"
                                    onClick={() => this.modifyMyRFV()}>
                            <CTooltip content="Modifier mon Rapport Version Finale" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileRFV && !giveOKRFV && (
                        <center>
                          <div className="alert alert-danger" role="alert" style= {{width: "200px"}}>
                            Il ne s'agit pas d'un Fichier PDF !.
                            <br/>
                            Merci de respecter le format demandé.
                          </div>
                        </center>
                      )}

                      {currentFileRFV && successUploadRFV && (
                        <div className="progress">
                          <div
                            className="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressRFV}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressRFV + "%" }}>
                            {progressRFV}%
                          </div>
                        </div>
                      )}

                      {showButtonRFV && giveOKRFV && labelRFV !== "" && (
                        <center>
                          <Checkbox
                            checked={this.state.checked}
                            color="secondary"
                            onChange={(e) =>
                              this.reportConfidentialHandler(e)
                            }
                            inputProps={{ "aria-label": "secondary checkbox" }}
                          />
                          &nbsp;&nbsp; Confidendial Upload
                        </center>
                      )}

                      {(
                        showButtonRFV &&
                        <center>
                          <CButton  variant="outline" color="danger"
                                    onClick={this.uploadReportFinalVersion}
                                    disabled={labelRFV === ""}
                                    enabled={labelRFV !== ""}>
                            Déposer Rapport Version Finale
                          </CButton>
                        </center>
                      )}


                    </CCol>

                    <CCol md="3" class="bg-warning py-2">
                      {(
                        labelRAP === "" && showUploadIconRAP &&
                        <center>
                          <label
                            htmlFor="filePickerASP"
                            className="custom-file-upload">
                            <img
                              src={blueLightUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Rapport Anti-Plagiat"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerASP"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              accept="application/pdf"
                              onChange={this.selectReportAntiPlagiat}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelRAP !== "" &&
                        <CRow>
                          <CCol md="1"/>
                          <CCol md="10">
                            <CCard color="info" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelRAP}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateRAP}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="1"/>
                        </CRow>
                      )}

                      {(
                        showReloadRAP && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="info" aria-expanded="true"
                                    onClick={() => this.modifyMyRAP()}>
                            <CTooltip content="Modifier mon Rapport Anti-Plagiat" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileRAP && !giveOKRAP && (
                        <center>
                          <div className="alert alert-danger" role="alert" style= {{width: "200px"}}>
                            Il ne s'agit pas d'un Fichier PDF !.
                            <br/>
                            Merci de respecter le format demandé.
                          </div>
                        </center>
                      )}

                      {currentFileRAP && successUploadRAP && (
                        <div className="progress">
                          <div
                            className="progress-bar bg-info progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressRAP}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressRAP + "%" }}>
                            {progressRAP}%
                          </div>
                        </div>
                      )}

                      {(
                        showButtonRAP &&
                        <center>
                          <CButton  variant="outline" color="info"
                                    onClick={this.uploadPlagiat}
                                    disabled={labelRAP === ""}
                                    enabled={labelRAP !== ""}>
                            Déposer Rapport Anti-Plagiat
                          </CButton>
                        </center>
                      )}

                    </CCol>

                    <CCol md="3" class="bg-warning py-2">

                      {(
                        labelASP === "" && showUploadIconASP &&
                        <center>
                          <label
                            htmlFor="filePickerRFV"
                            className="custom-file-upload">
                            <img
                              src={orangeUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Attestation Stage PFE"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerRFV"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              accept="application/pdf"
                              onChange={this.selectAttestationStagePFE}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelASP !== "" &&
                        <CRow>
                          <CCol md="1"/>
                          <CCol md="10">
                            <CCard color="warning" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelASP}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateASP}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="1"/>
                        </CRow>
                      )}

                      {(
                        showReloadASP && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="warning" aria-expanded="true"
                                    onClick={() => this.modifyMyASP()}>
                            <CTooltip content="Modifier mon Attestation Stage PFE" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileASP && !giveOKASP && (
                        <center>
                          <div className="alert alert-danger" role="alert" style= {{width: "200px"}}>
                            Il ne s'agit pas d'un Fichier PDF !.
                            <br/>
                            Merci de respecter le format demandé.
                          </div>
                        </center>
                      )}

                      {currentFileASP && successUploadASP && (
                        <div className="progress">
                          <div
                            className="progress-bar bg-warning progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressASP}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressASP + "%" }}>
                            {progressASP}%
                          </div>
                        </div>
                      )}

                      {(
                        showButtonASP &&
                        <center>
                          <CButton  variant="outline" color="warning"
                                    onClick={this.uploadTrainingCertif}
                                    disabled={labelASP === ""}
                                    enabled={labelASP !== ""}>
                            Déposer Attestation Stage PFE
                          </CButton>
                        </center>
                      )}


                    </CCol>

                    <CCol md="3" class="bg-primary py-2">
                      {(
                        labelDCS === "" && showUploadIconDCS &&
                        <center>
                          <label
                            htmlFor="filePickerDCS"
                            className="custom-file-upload">
                            <img
                              src={blueBoldUpload}
                              className="cursorPointer"
                              class="img-fluid"
                              width="100px"
                              height="75px"
                              title="Déposer Dossier ou Document Optionnel"
                              alt=""
                            />
                          </label>
                          <br/>
                          <label className="btn btn-default">
                            <input
                              id="filePickerDCS"
                              type="file"
                              className="cursorPointer"
                              style={{ visibility: "hidden" }}
                              onChange={this.selectDossierDocumentSupplemenaire}
                            />
                          </label>
                        </center>
                      )}

                      {(
                        labelDCS !== "" &&
                        <CRow>
                          <CCol md="1"/>
                          <CCol md="10">
                            <CCard color="primary" className="text-white">
                              <CCardHeader>
                                <div style={{textAlign: "right"}}>
                                  <img  src={espritLogo}
                                        className="cursorPointer"
                                        class="img-fluid"
                                        width="40px"
                                        height="50px"/>
                                </div>
                              </CCardHeader>
                              <CCardBody>
                                <div style={{textAlign: "center"}}>
                                  {labelDCS}
                                  <br/><br/>
                                  <span className="signature">
                                                By {studentFullName}
                                              </span>
                                  <br/>
                                  <span style={{fontSize: "11px", color: "black"}}>
                                                {dateDCS}
                                              </span>
                                </div>
                              </CCardBody>
                            </CCard>
                          </CCol>
                          <CCol md="1"/>
                        </CRow>
                      )}

                      {(
                        showReloadDCS && depotStatus === "" &&
                        <center>
                          <CButton  shape="pill" color="primary" aria-expanded="true"
                                    onClick={() => this.modifyMyDCS()}>
                            <CTooltip content="Modifier mon Supplément Optionnel" placement="top">
                              <CIcon content={freeSet.cilReload}/>
                            </CTooltip>
                          </CButton>
                        </center>
                      )}

                      {currentFileDCS && successUploadDCS && (
                        <div className="progress">
                          <div
                            className="progress-bar progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow={progressDCS}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progressDCS + "%" }}>
                            {progressDCS}%
                          </div>
                        </div>
                      )}

                      {(
                        showButtonDCS &&
                        <center>
                          <CButton  variant="outline" color="primary"
                                    onClick={this.uploadSupplement}
                                    disabled={labelDCS === ""}
                                    enabled={labelDCS !== ""}>
                            Déposer Dossier Technique
                          </CButton>
                        </center>
                      )}


                    </CCol>

                  </CRow>
                  <br/>
                  {(fileInfos.length > 0 &&
                    <Divider />
                  )}

                </>
              )}

              {showPopupSuccessUpload && (
                <Modal
                  isOpen={showPopupSuccessUpload}
                  contentLabel="Example Modal"
                  style={customStyles}
                >
                  <CIcon
                    name="cil-warning"
                    style={{ color: "blue" }}
                  />
                  &nbsp;&nbsp;
                  <span style={{ color: "blue" }}>
                                      Information
                                    </span>
                  <hr />
                  <center>
                    <br />
                    <br />
                    {message}
                    <br />
                    <br />
                    <br />
                    <button
                      className="btn btn-sm btn-primary"
                      onClick={this.closeModalSuccessUpload}
                    >
                      Ok
                    </button>
                    <br />
                    <br />
                  </center>
                </Modal>
              )}

              {showModalConfirmRelanceDepot && (
                <Modal
                  isOpen={showModalConfirmRelanceDepot}
                  contentLabel="Example Modal"
                  style={customStyles}
                >
                  <CIcon
                    name="cil-warning"
                    style={{ color: "blue" }}
                  />
                  &nbsp;&nbsp;
                  <span style={{ color: "blue" }}>
                                      Confirmation
                                    </span>
                  <hr />
                  <center>
                    <br />
                    <br />
                    Êtes-vous sûr de vouloir relancer votre dépôt ?
                    <br />
                    <br />
                    <br />
                    <button className="btn btn-sm btn-success"
                            onClick={this.modifyMyDepot}>
                      Oui
                    </button>
                    &nbsp;
                    <button className="btn btn-sm btn-danger"
                            onClick={this.closeModalConfirmRelanceDepot}>
                      Non
                    </button>
                    <br />
                    <br />
                  </center>
                </Modal>
              )}

              {fileInfos.length >0 && (
                <div>

                  <CContainer>
                    <CRow>
                      <CCol md="9" class="bg-warning py-2">
                        <div className="card-header">
                          <span className="menuTitleGreyReport">Informations sur mes Rapports</span>
                        </div>



                        <ul className="list-group list-group-flush">
                          {fileInfos.map((file, index) => (
                            <li className="list-group-item" key={index}>
                              <CRow>
                                <CCol md="4" class="bg-warning py-2">
                                  {file.includes("RFV") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="danger">Rapport Version Finale</CListGroupItem>
                                    </CListGroup>
                                  )}
                                  {file.includes("RAP") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="info">Rapport Anti-Plagiat : URKUND</CListGroupItem>
                                    </CListGroup>
                                  )}
                                  {file.includes("ASP") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="warning">Attestation Stage PFE</CListGroupItem>
                                    </CListGroup>
                                  )}
                                  {file.includes("DCS") && (
                                    <CListGroup accent>
                                      <CListGroupItem accent="primary">Dossier Technique &nbsp;
                                      </CListGroupItem>
                                    </CListGroup>
                                  )}
                                </CCol>
                                <CCol md="6" class="bg-warning py-2">
                              <span>
                                  {file.substr(0, file.indexOf("UNITR1"))}.pdf
                                </span>
                                </CCol>
                                <CCol md="2" class="bg-warning py-2">
                              <span>
                                  {file.substr(file.indexOf("UNITR1") + 9)}
                                </span>
                                </CCol>
                              </CRow>
                            </li>
                          ))}
                        </ul>
                      </CCol>
                      <CCol lg="3">
                        <br/>
                        {(
                          depotStatus === "" &&
                          <center>
                            <br/><br/>

                            <span className="text-dark" style={{fontSize: "11px"}}>
                                      Cliquer sur ce bouton pour <ins>relancer</ins> votre Dépôt.
                                    </span>
                            <br/>
                            <Icon icon={handPointingDown} color="grey" width="30" height="30" />
                            <br/><br/>

                            <CButton  shape="pill" color="dark" aria-expanded="true"
                                      onClick={() => this.openModalConfirmRelanceDepot()}>
                              <CTooltip content="Relancer mon Dépôt" placement="top">
                                <CIcon content={freeSet.cilReload}/>
                              </CTooltip>
                            </CButton>
                          </center>
                        )}
                        {( depotStatus.includes("DEPOSEE") &&
                          <CAlert color="info">
                            <h5 className="alert-heading"><strong>Dépôt Effectué</strong></h5>
                            <p>
                              Votre requête a été bien déposée le <ins>{depotStatus.substring(7, depotStatus.length)}</ins>.
                            </p>
                            <p>
                              Elle va être traitée le plus tôt possible.
                            </p>
                            <hr />
                            <p className="mb-0">
                              Merci de patienter.
                            </p>
                          </CAlert>
                        )}

                        {( depotStatus.includes("VALIDEE") &&
                          <CAlert color="success">
                            <h5 className="alert-heading"><strong>Dépôt Validé</strong></h5>
                            <p>
                              Votre requête a été <ins>validée</ins> le <ins>{depotStatus.substring(7, depotStatus.length)}</ins>.
                            </p>
                            <hr />
                            <p className="mb-0">
                              Bonne Chance.
                            </p>
                          </CAlert>
                        )}

                        {( depotStatus.includes("INCOMPLET") &&
                          <CAlert color="danger">
                            <h5 className="alert-heading"><strong>Dépôt Incomplet</strong></h5>
                            <p>
                              Votre requête a été marquée comme <ins>incomplète</ins> le <ins>{depotStatus.substring(9, 19)}</ins>.
                            </p>
                            <strong>Motif: </strong> {depotStatus.substring(19, depotStatus.length)}.
                            <hr />
                            <CButton  variant="outline" color="danger"
                                      onClick={() => this.requestForUpdateMyDepot()}>
                              Mettre à jour mon Dépôt
                            </CButton>
                          </CAlert>
                        )}
                      </CCol>
                    </CRow>
                  </CContainer>
                </div>
              )}

            </CCardBody>
            <CCardFooter>
              <CRow>
                <CCol sm="4" md="3"/>
                <CCol sm="4" md="7">
                  {(
                    depotStatus === "" && fileInfos.length === 3 &&
                    <>
                                  <span className="text-success" style={{fontSize: "11px"}}>
                                    Cliquer sur ce bouton pour <strong> valider votre dépôt </strong> ( <ins>PS:</ins> Une fois Cliquer, vous ne pourrez pas modifier votre dépôt ).
                                  </span>
                      &nbsp;&nbsp;&nbsp;
                      <Icon icon={handPointingRight} color="green" width="30" height="30" />
                    </>
                  )}
                </CCol>
                <CCol sm="4" md="2">
                  {(
                    depotStatus.includes("DEPOSEE") &&
                    <h5><CBadge color="primary">Requête Envoyée</CBadge></h5>
                  )}

                  {(
                    depotStatus.includes("VALIDEE") &&
                    <h5><CBadge color="success">Dépôt Validé</CBadge></h5>
                  )}

                  {(
                    depotStatus.includes("INCOMPLET") &&
                    <h5><CBadge color="danger">Dépôt Incomplet</CBadge></h5>
                  )}
                  {(
                    depotStatus === ""  && itsITandEM === "YES" &&
                    <CButton  color="success"
                              disabled={fileInfos.length !== 3}
                              enabled={fileInfos.length === 3}
                              className="float-right"
                              onClick={() => this.validateMyDepot()}>
                      Je valide
                    </CButton>
                  )}

                  {(
                    depotStatus === ""  && itsITandEM === "NO" &&
                    <CButton  color="success"
                              disabled={fileInfos.length < 4}
                              enabled={fileInfos.length > 3}
                              className="float-right"
                              onClick={() => this.validateMyDepot()}>
                      Je valide
                    </CButton>
                  )}

                  {(
                    showSpinnerWaitingDepot &&
                    <Backdrop className={classes.backdrop} open={showSpinnerWaitingDepot} >
                      <CircularProgress color="inherit" />
                    </Backdrop>
                  )}



                </CCol>
              </CRow>
            </CCardFooter>
          </CCard>
        )}

        <CModal
          show={showPopupSuccessDepot}
          onClose={() => this.closeShowPopupSuccessDepot()}
          className={classes.backdrop1}
          color="primary">
          <CModalHeader closeButton>
            <CModalTitle>Information</CModalTitle>
          </CModalHeader>
          <CModalBody>
            Vous avez validé le dépôt de vos documents.
            <br/>
            Merci de patienter jusqu'au le traitement de votre requête.
            <br/><br/>
          </CModalBody>
          <CModalFooter>
            <CButton color="primary" onClick={() => this.closeShowPopupSuccessDepot()}>
              Ok
            </CButton>
          </CModalFooter>
        </CModal>

        <CModal
          show={showPopupUpdateDepot}
          onClose={() => this.updateMyDepotNO()}
          className={classes.backdrop1}
          color="secondary"
        >
          <CModalHeader closeButton>
            <CModalTitle>Confirmation</CModalTitle>
          </CModalHeader>
          <CModalBody>
            Voulez-vous mettre à jour votre dépôt <ins>en tenir compte</ins> la cause de signaler votre requête précédante comme incomplète ?.
            <br/><br/>
          </CModalBody>
          <CModalFooter>
            <CButton color="success" onClick={() => this.updateMyDepotYES()}>
              Oui
            </CButton>
            &nbsp;
            <CButton color="danger" onClick={() => this.updateMyDepotNO()}>
              Pas Maintenant
            </CButton>
          </CModalFooter>
        </CModal>

      </div>
    );
  }
}

export default withStyles(useStyles, useStyles1)(UploadReportUrg)
