import React, { Component } from "react";

import AuthService from "../../services/auth.service";
import redUpload from "../../images/redUpload.jpg";
import Modal from "react-modal";
import CIcon from "@coreui/icons-react";
import { freeSet } from '@coreui/icons';

import { NotificationContainer, NotificationManager } from "react-notifications";
import "react-notifications/lib/notifications.css";

import { CButton, CTooltip, CAlert, CListGroup, CListGroupItem, CCard, CCardFooter, CCardBody, CRow, CCol } from "@coreui/react";
import { VerticalTimeline, VerticalTimelineElement }  from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import AttachFileIcon from '@material-ui/icons/AttachFile';
import AccessAlarmIcon from '@material-ui/icons/AccessAlarm';
import "../../css/style.css";
import orangeUpload from "../../images/orangeUpload.jpg";

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;
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
const currentStudent = AuthService.getCurrentStudent();

export default class UploadBalanceSheet extends Component {
  constructor(props) {
    super(props);
    this.selectFile = this.selectFile.bind(this);
    this.modifyMyBV1 = this.modifyMyBV1.bind(this);
    this.modifyMyBV2 = this.modifyMyBV2.bind(this);
    this.modifyMyBV3 = this.modifyMyBV3.bind(this);
    this.uploadBalanceSheetV1 = this.uploadBalanceSheetV1.bind(this);
    this.uploadBalanceSheetV2 = this.uploadBalanceSheetV2.bind(this);
    this.uploadBalanceSheetV3 = this.uploadBalanceSheetV3.bind(this);
    this.deleteReportFile = this.deleteReportFile.bind(this);
    this.closeModalSuccessUpload = this.closeModalSuccessUpload.bind(this);
    this.reportConfidentialHandler = this.reportConfidentialHandler.bind(this);

    this.state = {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: "",
      fileInfos: [],
      successUpload: false,
      showPopupSuccessUpload: false,
      showUploadBalanceSheetV1Button: false,
      showUploadBalanceSheetV2Button: false,
      showUploadBalanceSheetV3Button: false,
      projectTitle: "",
      checked: false,
      dateDepotFicheOff: "",
      dateDepotBalanceSheetV1: "",
      dateDepotBalanceSheetV2: "",
      dateDepotBalanceSheetV3: "",
      dateDepotBalanceSheetFormedV1: "",
      dateDepotBalanceSheetFormedV2: "",
      dateDepotBalanceSheetFormedV3: "",
      dateNow: "",
      timeForDepotBalanceSheetV1: "NOTYETBV1",
      timeForDepotBalanceSheetV2: "NOTYETBV2",
      timeForDepotBalanceSheetV3: "NOTYETBV3",
      givePermessionBSV1: "NOTYETGPBV1",
      currentDay:'',
      giveOKBLN: false,
      giveOkModifyBV1: false,
      giveOkModifyBV2: false,
      giveOkModifyBV3: false,
      dateDebutStageByConv: null,
      dateNewestDebutStageConvAv: "",
      fichePFEStatus: "",
      statusConvention: ""
    };

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

    // console.log('--------> 0212: ', this.state.fichePFEStatus);

    if(this.state.fichePFEStatus !== "") {
      // Date & Status Convention
      let requestConv = new XMLHttpRequest();
      requestConv.open("GET", API_URL_STU + "conventionDateConvDateDebutStageStatus/" + currentStudent.id, false);
      requestConv.send(null);
      let convDetails = JSON.parse(requestConv.responseText);

      if (convDetails.length !== 0) {
        if (convDetails.length === 1) {
          // console.log('---------RESUME ----> Conv = 1');
          // Create Timeline % date Convention
          // this.state.dateDebutStageByConv = this.gotFormattedDate(new Date(convDetails.dateDebutStage));
          this.state.dateConvention = convDetails[0].dateConvention;
          this.state.statusConvention = convDetails[0].traiter;

          this.state.dateNewestDebutStageConvAv = this.gotFormattedDate(new Date(convDetails[0].dateDebutStage));
        }

        if (convDetails.length > 1) {
          // console.log('---------RESUME ----> Conv > 1');
          // Create Timeline % date Convention
          // this.state.dateDebutStageByConv = this.gotFormattedDate(new Date(convDetails[0].dateDebutStage));
          this.state.dateConvention = convDetails[0].dateConvention;
          this.state.statusConvention = convDetails[0].traiter;

          this.state.dateNewestDebutStageConvAv = this.gotFormattedDate(new Date(convDetails[0].dateDebutStage));
        }

        // console.log('17.54 ----------------------> Conv Obj: ', convDetails);
        // console.log('17.54 ----------------------> Conv Date: ', this.state.dateNewestDebutStageConvAv);
      }


      // Date & Status Avenant
      let requestAv = new XMLHttpRequest();
      requestAv.open("GET", API_URL_MESP + "avenantDateAvDateDebutStageStatus/" + currentStudent.id, false);
      requestAv.send(null);
      let avDetails = JSON.parse(requestAv.responseText);

      let avsLength = avDetails.length;
      if (avsLength !== 0) {
        let lastTreatedIndex = avsLength - 1;
        // console.log('--17.54---loli1-- Exist AVenant: ' + lastTreatedIndex + " - " + avDetails.length + " - " + avDetails[0].traiter);
        if (avsLength === 1 && avDetails[0].traiter === true) {
          // console.log('---------RESUME ----> Avn = 1');
          // Create Timeline % date Avenant
          // this.state.dateDebutStageByAv = this.gotFormattedDate(new Date(avDetails.dateDebutStage));
          this.state.dateAvenant = avDetails[0].dateAvenant;
          this.state.statusAvenant = avDetails[0].traiter;

          this.state.dateNewestDebutStageConvAv = this.gotFormattedDate(new Date(avDetails[0].dateDebutStage));

          // console.log('-------------azerty---------> 17.54 1: ' + this.state.dateNewestDebutStageConvAv);
          // console.log('------------azerty----------> 17.54 2: ' + this.state.dateAvenant);
          // console.log('-----------azerty-----------> 17.54 3: ' + this.state.statusAvenant);
        }

        if (avsLength > 1 && avDetails[lastTreatedIndex].traiter === true) {
          // console.log('---------RESUME ----> Avn > 1');
          if (avDetails[0].traiter === true) {
            lastTreatedIndex = 0;
          }
          // Create Timeline % date Avenant
          // this.state.dateDebutStageByAv = this.gotFormattedDate(new Date(avDetails[0].dateDebutStage));
          this.state.dateAvenant = avDetails[lastTreatedIndex].dateAvenant;
          this.state.statusAvenant = avDetails[lastTreatedIndex].traiter;

          this.state.dateNewestDebutStageConvAv = this.gotFormattedDate(new Date(avDetails[lastTreatedIndex].dateDebutStage));

          // console.log('-------qsdfgh---------------> 17.54 1: ' + this.state.dateNewestDebutStageConvAv);
          // console.log('-----------qsdfgh-----------> 17.54 2: ' + this.state.dateAvenant);
          // console.log('-----------qsdfgh-----------> 17.54 3: ' + this.state.statusAvenant);
        }

        // console.log('17.54 ----------------------> Avn Obj: ', avDetails);
        // console.log('17.54 ----------------------> Avn Date: ', this.state.dateNewestDebutStageConvAv);
      }

      // console.log('17.54 ----------------------> Final Newest Date ', this.state.dateNewestDebutStageConvAv);

      // Timeline by the Newest Date Debut Stage By Convention - Avenant
      if (this.state.dateNewestDebutStageConvAv !== "") {
        /*
        let day = myDate.substring(8, 10);
        let month = myDate.substring(5, 7);
        let year = myDate.substring(0, 4);
        this.state.dateDepotFicheOff = new Date(year, month - 1, day);
        */

        let year = this.state.dateNewestDebutStageConvAv.substring(6, 10);
        let month = this.state.dateNewestDebutStageConvAv.substring(3, 5);
        let day = this.state.dateNewestDebutStageConvAv.substring(0, 2);

        let bridgeBV1Dt = new Date(year, month - 1, day);
        bridgeBV1Dt.setDate(bridgeBV1Dt.getDate() + 6 * 7);
        this.state.dateDepotBalanceSheetV1 = bridgeBV1Dt;
        this.state.dateDepotBalanceSheetFormedV1 = bridgeBV1Dt.getDate() + "-" + (bridgeBV1Dt.getMonth() + 1) + "-" + bridgeBV1Dt.getFullYear();

        let bridgeBV2Dt = new Date(year, month - 1, day);
        bridgeBV2Dt.setDate(bridgeBV2Dt.getDate() + 15 * 7);
        this.state.dateDepotBalanceSheetV2 = bridgeBV2Dt;
        this.state.dateDepotBalanceSheetFormedV2 = bridgeBV2Dt.getDate() + "-" + (bridgeBV2Dt.getMonth() + 1) + "-" + bridgeBV2Dt.getFullYear();

        let bridgeBV3Dt = new Date(year, month - 1, day);
        bridgeBV3Dt.setDate(bridgeBV3Dt.getDate() + 25 * 7);
        this.state.dateDepotBalanceSheetV3 = bridgeBV3Dt;
        this.state.dateDepotBalanceSheetFormedV3 = bridgeBV3Dt.getDate() + "-" + (bridgeBV3Dt.getMonth() + 1) + "-" + bridgeBV3Dt.getFullYear();
      }

      // March April June August
      // let currentDt = new Date('August 15, 2022 23:15:30');

      let currentDt = new Date();
      currentDt.setHours(0, 0, 0, 0);
      this.state.dateNow = currentDt;
      // console.log('0.1 -------------ss------> NOW: ' + this.state.dateNow);

      // console.log('0.1 ---------lol1----------> DATE depot: ' + this.state.dateDepotFicheOff);
      // console.log('0.2 ---------lol1-----fff-----> DATE v1: ' + this.state.dateDepotBalanceSheetV1);
      // console.log('0.3 ---------lol1------ff----> DATE now: ' + this.state.dateNow);
      // console.log('0.4 ---------lol1-----fsff-----> DATE v2: ' + this.state.dateDepotBalanceSheetV2);
      // console.log('0.4 ---------lol1-----fsff-----> DATE v3: ' + this.state.dateDepotBalanceSheetV3);

      this.state.currentDay =
          currentDt.getDate() +
          "-" +
          (currentDt.getMonth() + 1) +
          "-" +
          currentDt.getFullYear();

      /*console.log('0.4 ---------1503----------> DATE 1.1: ' + this.state.today);
      console.log('0.4 ---------1503----------> DATE 1.2: ' + this.state.today.getTime());

      console.log('0.4 ---------1503----------> DATE 2.1: ' + this.state.dateNow);
      console.log('0.4 ---------1503----------> DATE 2.2: ' + this.state.dateNow.getTime());*/


      if (this.state.dateDepotBalanceSheetV1.getTime() > this.state.dateNow.getTime())
      {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV1 = "BEFORETIMEBV1";
        this.state.givePermessionBSV1 = "BEFORETIMEBV1";
      }

      if (
          (this.state.dateDepotBalanceSheetV1.getTime() <= this.state.dateNow.getTime())
          ||
          (this.state.dateDepotBalanceSheetV1.getTime() > this.state.dateNow.getTime())
      )
      {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV1 = "ITISTIMEBV1";
      }

      if (
          this.state.dateDepotBalanceSheetV1.getTime() <
          this.state.dateNow.getTime() &&
          this.state.dateDepotBalanceSheetV2.getTime() >
          this.state.dateNow.getTime()
      ) {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV2 = "AFTERTIMEBV1BEFORETIMEBV2";
      }

      if (
          this.state.dateDepotBalanceSheetV2.getTime() <=
          this.state.dateNow.getTime()
      ) {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV2 = "ITISTIMEBV2";
      }

      if (
          this.state.dateDepotBalanceSheetV2.getTime() <
          this.state.dateNow.getTime() &&
          this.state.dateDepotBalanceSheetV3.getTime() >
          this.state.dateNow.getTime()
      ) {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV3 = "AFTERTIMEBV2BEFORETIMEBV3";
      }

      if (
          this.state.dateDepotBalanceSheetV3.getTime() <=
          this.state.dateNow.getTime()
      ) {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV3 = "ITISTIMEBV3";
      }

      if (
          this.state.dateDepotBalanceSheetV3.getTime() <
          this.state.dateNow.getTime()
      ) {
        // // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotBalanceSheetV3 = "AFTERTIMEBV3";
      }

      // console.log('----------------------------------------> Result: ' + this.state.timeForDepotBalanceSheetV1 + " - " + this.state.timeForDepotBalanceSheetV2 + " - " + this.state.timeForDepotBalanceSheetV3);
    }
  }

  componentDidMount() {

    const fileInfosDecrypted = [];
    const Jasypt = require('jasypt');
    const jasypt = new Jasypt();
    jasypt.setPassword('SALT');

    AuthService.getBalanceSheets(currentStudent.id).then((response) => {
      const encryptedData = response.data;
      encryptedData.forEach((encryptedItem) => {
        const decryptedItem = jasypt.decrypt(encryptedItem);
        fileInfosDecrypted.push(decryptedItem);
      });

      this.setState({

        fileInfos: fileInfosDecrypted,
      });

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

  gotFormattedDate(bridgeSDt)
  {

    let formattedDate = null;
    if(bridgeSDt === null)
    {
      formattedDate = null;
    }

    if(bridgeSDt !== null)
    {
      let month = "";
      let day = "";

      if (String(bridgeSDt.getMonth() + 1).length < 2) {
        // console.log('Check Disponibility ------------- TextScroller.js: ' + bridgeSDt.getMonth()+1);
        month = "0" + (bridgeSDt.getMonth() + 1);
      } else {
        month = bridgeSDt.getMonth() + 1;
      }

      if (String(bridgeSDt.getDate()).length < 2) {
        day = "0" + bridgeSDt.getDate();
      } else {
        day = bridgeSDt.getDate();
      }

      formattedDate = day + "-" + month + "-" + bridgeSDt.getFullYear();
    }
    return formattedDate;
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

  modifyMyBV1()
  {
    AuthService.updateMyBV1(currentStudent.id).then(
        (response) =>
        {
          this.setState({
            giveOkModifyBV1: true
          });
          // console.log("Check Disponibility ----df----cr----- FAIL");
          return AuthService.getBalanceSheets(currentStudent.id);
        })
        .then((files) => {
          this.setState({
            fileInfos: files.data,
          });

        })
        .catch(() => {
        });
  }

  modifyMyBV2()
  {
    AuthService.updateMyBV2(currentStudent.id).then(
        (response) =>
        {
          this.setState({
            giveOkModifyBV2: true
          });
          // console.log("Check Disponibility ----df----cr----- FAIL");
          return AuthService.getBalanceSheets(currentStudent.id);
        })
        .then((files) => {
          this.setState({
            fileInfos: files.data,
          });

        })
        .catch(() => {
        });
  }

  modifyMyBV3()
  {
    AuthService.updateMyBV3(currentStudent.id).then(
        (response) =>
        {
          this.setState({
            giveOkModifyBV3: true
          });
          // console.log("Check Disponibility ----df----cr----- FAIL");
          return AuthService.getBalanceSheets(currentStudent.id);
        })
        .then((files) => {
          this.setState({
            fileInfos: files.data,
          });

        })
        .catch(() => {
        });
  }


  selectFile(event) {
    this.setState({
      selectedFiles: event.target.files,
    });

    let fileName = event.target.files[0].name;

    if(fileName.toUpperCase().includes("PDF"))
    {
      this.state.giveOKBLN= true
    }
    else
    {
      this.state.giveOKBLN= false
    }

    /************************************************************************************/

    AuthService.getBalanceSheets(currentStudent.id).then(
        (response) => {
          let files = response.data;


          if (files.length === 0) {
            // console.log('-----------------------------> All files in DB: 0');
            this.setState({
              showUploadBalanceSheetV1Button: true,
            });
          }
          if (files.length === 1) {
            // console.log('--------------az1---------------> All files in DB: 1' + files[0]);

            this.setState({
              showUploadBalanceSheetV1Button: false,
              showUploadBalanceSheetV2Button: true,
            });
          }
          if (files.length === 2) {
            // console.log('--------------az1---------------> All files in DB: 1' + files[0]);

            this.setState({
              showUploadBalanceSheetV2Button: false,
              showUploadBalanceSheetV3Button: true,
            });
          }



          // console.log('---------------az1--------------> All files in DB-1: ' + files.length);
          // console.log(files);
          // console.log('---------------az1--------------> All files in DB-2: ' + files.length);
        },
        (error) => {
          // console.log('SELECT -------------- Error');
        }
    );

    // console.log('-----------------------------> Result: ' + this.state.showUploadReportButton + " - " + this.state.showUploadPlagiatButton);

    if(this.state.giveOKBLN === true)
    {
      let notif =
          "Vous avez choisi le fichier " + fileName + " .";
      return NotificationManager.success(notif, "Information", 6000);
    }

  }

  closeModalSuccessUpload() {
    // console.log(' Verify Tech Duplication -------------------- .');
    this.setState({
      showPopupSuccessUpload: false,
    });
  }

  uploadBalanceSheetV1() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadBalanceSheetV1(
        currentFile,
        currentStudent.id,
        (event) => {
          this.setState({
            showUploadBalanceSheetV1Button: false,
            progress: Math.round((100 * event.loaded) / event.total),
            successUpload: true
          });
        }
    )
        .then((response) => {
          // console.log('-------------------------> 1');
          this.setState({
            successUpload: false,
            message: response.data.message,
            showPopupSuccessUpload: true
          });
          return AuthService.getBalanceSheets(currentStudent.id);
        })
        .then((files) => {
          this.setState({
            fileInfos: files.data,
            giveOkModifyBV1: false
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
            progress: 0,
            message: "Could not upload the file !.",
            currentFile: undefined,
          });
        });

    this.setState({
      selectedFiles: undefined,
    });
  }

  uploadBalanceSheetV2() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadBalanceSheetV2(
        currentFile,
        currentStudent.id,
        (event) => {
          this.setState({
            showUploadBalanceSheetV2Button: false,
            progress: Math.round((100 * event.loaded) / event.total),
            successUpload: true
          });
        }
    )
        .then((response) => {
          // console.log('-------------------------> 1');
          this.setState({
            successUpload: false,
            message: response.data.message,
            showPopupSuccessUpload: true
          });
          return AuthService.getBalanceSheets(currentStudent.id);
        })
        .then((files) => {
          this.setState({
            fileInfos: files.data,
            giveOkModifyBV2: false
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
            progress: 0,
            message: "Could not upload the file !.",
            currentFile: undefined,
          });
        });

    this.setState({
      selectedFiles: undefined,
    });
  }

  uploadBalanceSheetV3() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadBalanceSheetV3(
        currentFile,
        currentStudent.id,
        (event) => {
          this.setState({
            progress: Math.round((100 * event.loaded) / event.total),
            successUpload: true,
            showUploadBalanceSheetV3Button: false,
          });
        }
    )
        .then((response) => {
          // console.log('-------------------------> 1');
          this.setState({
            successUpload: false,
            message: response.data.message,
            showPopupSuccessUpload: true
          });
          return AuthService.getBalanceSheets(currentStudent.id);
        })
        .then((files) => {
          // console.log('-------------------------> 2');
          this.setState({
            fileInfos: files.data,
            giveOkModifyBV3: false
          });
        })
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

  deleteReportFile(id) {
    // console.log('-------------------------> OK: Delete 0: ' + id);

    AuthService.deleteReportById(id)
        .then((response) => {
          // console.log('xxxxxxxxxxxxxxxxxxxxxxx> OK: Delete 1: ' + id);
          return AuthService.getBalanceSheets(currentStudent.id);
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

  render() {
    const {
      selectedFiles,
      currentFile,
      progress,
      message,
      fileInfos,
      successUpload,
      showPopupSuccessUpload,
      showUploadBalanceSheetV1Button,
      showUploadBalanceSheetV2Button,
      showUploadBalanceSheetV3Button,
      timeForDepotBalanceSheetV1,
      timeForDepotBalanceSheetV2,
      timeForDepotBalanceSheetV3,
      dateDepotBalanceSheetFormedV1,
      dateDepotBalanceSheetFormedV2,
      dateDepotBalanceSheetFormedV3,
      currentDay,
      giveOKBLN,
      giveOkModifyBV1,
      giveOkModifyBV2,
      giveOkModifyBV3,
      dateDebutStageByConv,
      dateNewestDebutStageConvAv,
      fichePFEStatus,
      statusConvention,
      givePermessionBSV1
    } = this.state;

    return (
        <>
          {
            fichePFEStatus === "" ?
                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/>
                          <br/>
                          Vous ne pouvez pas déposer vos Bilans.
                          <br/>
                          Vous devez tout d'abord
                          &nbsp;
                          <span className="clignoteRed">
                              déposer votre Plan Travail
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
                :
                <>
                  {
                    statusConvention === "02" ?
                        <>
                          {
                            fichePFEStatus === "" &&
                            <>
                              <CRow>
                                <CCol md="2"/>
                                <CCol md="8">
                                  <CCard accentColor="danger">
                                    <CCardBody>
                                      <center>
                                        <br/>
                                        <br/>
                                        Vous ne pouvez pas déposer votre Bilan.
                                        <br/>
                                        Vous devez tout d'abord
                                        &nbsp;
                                        <span className="clignoteRed">
                                déposer votre Plan Travail
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
                          }

                          {
                            (fichePFEStatus === "01" || fichePFEStatus === "02" || fichePFEStatus === "04" || fichePFEStatus === "05") &&
                            <CRow>
                              <CCol md="2"/>
                              <CCol md="8">
                                <br/><br/>
                                <CCard accentColor="danger">
                                  <CCardBody>
                                    <center>
                                      <br /><br />
                                      <span className="info2InDiv">
                                Vous ne pouvez pas déposer vos Bilans tant que
                                <br/>
                                votre Plan Travail
                                        &nbsp;
                                        <span className="clignoteRed">
                                  n'est pas encore validée
                                </span>
                                .
                              </span>
                                      <br />
                                      <br /><br />
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
                          }

                          {
                            (fichePFEStatus === "08" || fichePFEStatus === "06" || fichePFEStatus === "07") &&
                            <CRow>
                              <CCol md="2"/>
                              <CCol md="8">
                                <br/><br/>
                                {(fileInfos.length === 3 &&
                                    <CCard accentColor="danger">
                                      <CCardBody>
                              <span className="redMark13">
                                Liste des Bilans déposés 1
                              </span>
                                        <br/><br/>
                                        <ul className="list-group list-group-flush">
                                          {fileInfos.map((file, index) => (
                                              <li className="list-group-item" key={index}>
                                                <CRow>
                                                  <CCol md="4" class="bg-warning py-2">
                                                    {file.includes("BV1") && (
                                                        <CListGroup accent>
                                                          <CListGroupItem accent="danger"><strong>Bilan Version 1</strong></CListGroupItem>
                                                        </CListGroup>
                                                    )}
                                                    {file.includes("BV2") && (
                                                        <CListGroup accent>
                                                          <CListGroupItem accent="danger"><strong>Bilan Version 2</strong></CListGroupItem>
                                                        </CListGroup>
                                                    )}
                                                    {file.includes("BV3") && (
                                                        <CListGroup accent>
                                                          <CListGroupItem accent="danger"><strong>Bilan Version 3</strong></CListGroupItem>
                                                        </CListGroup>
                                                    )}
                                                  </CCol>
                                                  <CCol md="5" class="bg-warning py-2">
                                                    <div style={{marginTop: '13px'}}>{file.substr(0, file.indexOf("UNITBV"))}.pdf</div>
                                                  </CCol>
                                                  <CCol md="3" class="bg-warning py-2">

                                                    <div style={{marginTop: '13px'}}>{file.substr(file.indexOf("UNITBV") + 7)}</div>
                                                  </CCol>
                                                </CRow>
                                              </li>
                                          ))}
                                        </ul>
                                      </CCardBody>
                                    </CCard>
                                )}

                              </CCol>
                              <CCol md="2"/>
                            </CRow>
                          }

                          {
                            fichePFEStatus === "03" &&
                            <CRow>
                              <CCol md="8">

                                <br/>
                                {
                                  fileInfos.length === 3 &&
                                  <CAlert color="success" closeButton>
                                    <h6 className="alert-heading"><strong>État Dépôt Bilans</strong></h6>
                                    <CRow>
                                      <CCol>
                                        Vous avez achevé le dépôt de vos Bilans.
                                      </CCol>
                                    </CRow>
                                    <br/>
                                    <hr/>
                                    <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-right">
                               Today is {currentDay}
                            </span>
                                    <br/>
                                  </CAlert>
                                }

                                {
                                  fileInfos.length < 3 &&
                                  <CRow>
                                    <CCol md="8">
                                      <CAlert color="info" closeButton>
                                        <center>
                                          <AccessAlarmIcon style={{ color: "#ff0066", fontSize: 30 }} />
                                          <br /><br />
                                          <span className="bilanClignoteMark">
                                  NORMALEMENT
                                </span>
                                          &nbsp;
                                          <span className="info2InDiv">
                                  les dates programmées pour publier vos
                                            &nbsp;
                                            <span className="bilanMark">
                                    Bilans
                                  </span>
                                            &nbsp;
                                            sont planifiées comme présenté par le Timeline à droite.
                                </span>
                                          <br />
                                          Mais quand même, le dépôt est possible dès maintenant à tout moment.
                                          <br/>
                                          <br/>
                                        </center>
                                      </CAlert>
                                    </CCol>
                                    <CCol md="4">
                                      {
                                        fileInfos.length === 3 &&
                                        <CAlert color="success" closeButton>
                                          <h6 className="alert-heading"><strong>État Dépôt Bilans</strong></h6>
                                          <CRow>
                                            <CCol>
                                              Vous avez achevé le dépôt de vos Bilans.
                                            </CCol>
                                          </CRow>
                                          <br/>
                                          <hr/>
                                          <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-center">
                                    Today is {currentDay}
                                  </span>
                                          <br/>
                                        </CAlert>
                                      }

                                      {
                                        fileInfos.length === 2 &&
                                        <CAlert color="warning" closeButton>
                                          <h6 className="alert-heading"><strong>État Dépôt Bilans</strong></h6>
                                          <CRow>
                                            <CCol>
                                              Il vous reste encore 1 Bilan à déposer dans la date adéquate.
                                            </CCol>
                                          </CRow>
                                          <br/>
                                          <hr/>
                                          <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-center">
                                    Today is {currentDay}
                                  </span>
                                          <br/>
                                        </CAlert>
                                      }

                                      {
                                        fileInfos.length === 1 &&
                                        <CAlert color="warning" closeButton>
                                          <h6 className="alert-heading"><strong>État Dépôt Bilans</strong></h6>
                                          <CRow>
                                            <CCol>
                                              Il vous reste encore 2 Bilans à déposer dans les dates adéquates.
                                            </CCol>
                                          </CRow>
                                          <br/>
                                          <hr/>
                                          <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-center">
                                    Today is {currentDay}
                                  </span>
                                          <br/>
                                        </CAlert>
                                      }

                                      {
                                        fileInfos.length === 0 &&
                                        <CAlert color="warning" closeButton>
                                          <h6 className="alert-heading"><strong>État Dépôt Bilans</strong></h6>
                                          <CRow>
                                            <CCol>
                                              Vous n'avez pas encore déposé aucun Bilan.
                                            </CCol>
                                          </CRow>
                                          <br/>
                                          <hr/>
                                          <span style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-center">
                                    Today is {currentDay}
                                  </span>
                                          <br/>
                                        </CAlert>
                                      }
                                    </CCol>
                                  </CRow>
                                }

                                <>
                                  {(
                                      fileInfos.length === 0
                                      ||
                                      giveOkModifyBV1
                                  )&& (
                                      <CCard accentColor="danger">
                                        <CCardBody>
                                          <CRow>
                                            <CCol>
                                              <div style={{ textAlign: "left" }}>
                                            <span className="titleBalanceSheet">
                                              Dépôt Bilan Version 1
                                            </span>
                                              </div>
                                            </CCol>
                                            <CCol>
                                              <div style={{ textAlign: "right" }}>
                                            <span className="noteDepotBilan">
                                              Vous devez choisir un fichier PDF.
                                            </span>
                                              </div>
                                            </CCol>
                                          </CRow>

                                          <NotificationContainer/>

                                          <div>
                                            <center>
                                              <label
                                                  htmlFor="filePicker"
                                                  className="custom-file-upload"
                                              >
                                                <img
                                                    src={redUpload}
                                                    className="cursorPointer"
                                                    width="80px"
                                                    height="50px"
                                                    title="Choose your Balance Sheet version 1"
                                                    alt=""
                                                />
                                              </label>
                                              <br />
                                              <label className="btn btn-default">
                                                <input
                                                    id="filePicker"
                                                    type="file"
                                                    style={{ visibility: "hidden" }}
                                                    accept="application/pdf"
                                                    onChange={this.selectFile}
                                                />
                                              </label>
                                              {selectedFiles && !giveOKBLN && (
                                                  <center>
                                                    <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                                                      Il ne s'agit pas d'un Fichier PDF !.
                                                      <br/>
                                                      Merci de respecter le format demandé.
                                                    </div>
                                                  </center>
                                              )}

                                              {currentFile && successUpload && (
                                                  <div className="progress" style={{ width: "500px" }}>
                                                    <div
                                                        className="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                                                        role="progressbar"
                                                        aria-valuenow={progress}
                                                        aria-valuemin="0"
                                                        aria-valuemax="100"
                                                        style={{ width: progress + "%" }}
                                                    >
                                                      {progress}%
                                                    </div>
                                                  </div>
                                              )}

                                              <br/>
                                              {showUploadBalanceSheetV1Button && selectedFiles && giveOKBLN && (
                                                  <button
                                                      className="btn btn-danger"
                                                      onClick={this.uploadBalanceSheetV1}>
                                                    Déposer Bilan v1
                                                  </button>
                                              )}

                                            </center>
                                          </div>
                                        </CCardBody>
                                      </CCard>
                                  )}

                                  {showPopupSuccessUpload && (
                                      <Modal
                                          isOpen={showPopupSuccessUpload}
                                          contentLabel="Example Modal"
                                          style={customStyles}
                                      >
                                        <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                        <hr/>
                                        <center>
                                          <br />
                                          <br />
                                          {message}
                                          <br />
                                          <br />
                                          <br />
                                          <button  className="btn btn-sm btn-primary" onClick={this.closeModalSuccessUpload}>
                                            Ok
                                          </button>
                                          <br />
                                          <br />
                                        </center>
                                      </Modal>
                                  )}
                                </>

                                <>
                                  {(
                                      fileInfos.length === 1
                                      ||
                                      giveOkModifyBV2
                                  )&& (
                                      <CCard accentColor="danger">
                                        <CCardBody>
                                          <CRow>
                                            <CCol>
                                              <div style={{ textAlign: "left" }}>
                                            <span className="titleBalanceSheet">
                                              Dépôt Bilan Version 2
                                            </span>
                                              </div>
                                            </CCol>
                                            <CCol>
                                              <div style={{ textAlign: "right" }}>
                                            <span className="noteDepotBilan">
                                              Vous devez choisir un fichier PDF.
                                            </span>
                                              </div>
                                            </CCol>
                                          </CRow>

                                          <NotificationContainer/>

                                          <center>
                                            <label
                                                htmlFor="filePicker"
                                                className="custom-file-upload"
                                            >
                                              <img
                                                  src={redUpload}
                                                  className="cursorPointer"
                                                  width="80px"
                                                  height="50px"
                                                  title="Choose your Balance Sheet version 2"
                                                  alt=""
                                              />
                                            </label>
                                            <br />
                                            <label className="btn btn-default">
                                              <input
                                                  id="filePicker"
                                                  type="file"
                                                  style={{ visibility: "hidden" }}
                                                  accept="application/pdf"
                                                  onChange={this.selectFile}
                                              />
                                            </label>
                                            <br />

                                            {selectedFiles && !giveOKBLN && (
                                                <center>
                                                  <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                                                    Il ne s'agit pas d'un Fichier PDF !.
                                                    <br/>
                                                    Merci de respecter le format demandé.
                                                  </div>
                                                </center>
                                            )}

                                            {currentFile && successUpload && (
                                                <div className="progress" style={{ width: "500px" }}>
                                                  <div
                                                      className="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                                                      role="progressbar"
                                                      aria-valuenow={progress}
                                                      aria-valuemin="0"
                                                      aria-valuemax="100"
                                                      style={{ width: progress + "%" }}
                                                  >
                                                    {progress}%
                                                  </div>
                                                </div>
                                            )}

                                            {showUploadBalanceSheetV2Button && selectedFiles && giveOKBLN && (
                                                <button
                                                    className="btn btn-danger"
                                                    onClick={this.uploadBalanceSheetV2}
                                                >
                                                  Déposer Bilan v2
                                                </button>
                                            )}
                                          </center>

                                        </CCardBody>
                                      </CCard>
                                  )}

                                  {showPopupSuccessUpload && (
                                      <Modal
                                          isOpen={showPopupSuccessUpload}
                                          contentLabel="Example Modal"
                                          style={customStyles}
                                      >
                                        <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                        <hr/>
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
                                            {" "}
                                            Ok{" "}
                                          </button>
                                          <br />
                                          <br />
                                        </center>
                                      </Modal>
                                  )}
                                </>

                                <>
                                  {(
                                      fileInfos.length === 2
                                      ||
                                      giveOkModifyBV3
                                  )&& (
                                      <CCard accentColor="danger">
                                        <CCardBody>
                                          <CRow>
                                            <CCol>
                                              <div style={{ textAlign: "left" }}>
                                            <span className="titleBalanceSheet">
                                              Dépôt Bilan Version 3
                                            </span>
                                              </div>
                                            </CCol>
                                            <CCol>
                                              <div style={{ textAlign: "right" }}>
                                            <span className="noteDepotBilan">
                                              Vous devez choisir un fichier PDF.
                                            </span>
                                              </div>
                                            </CCol>
                                          </CRow>

                                          <NotificationContainer/>

                                          <div>
                                            <center>
                                              <label htmlFor="filePicker" className="custom-file-upload">
                                                <img
                                                    src={redUpload}
                                                    className="cursorPointer"
                                                    width="80px"
                                                    height="50px"
                                                    title="Choose your Balance Sheet version 3"
                                                    alt=""/>
                                              </label>
                                              <br/>

                                              <label className="btn btn-default">
                                                <input
                                                    id="filePicker"
                                                    type="file"
                                                    style={{ visibility: "hidden" }}
                                                    accept="application/pdf"
                                                    onChange={this.selectFile}
                                                />
                                              </label>
                                              <br />

                                              {selectedFiles && !giveOKBLN && (
                                                  <center>
                                                    <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                                                      Il ne s'agit pas d'un Fichier PDF !.
                                                      <br/>
                                                      Merci de respecter le format demandé.
                                                    </div>
                                                  </center>
                                              )}

                                              {currentFile && successUpload && (
                                                  <div className="progress" style={{ width: "500px" }}>
                                                    <div
                                                        className="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                                                        role="progressbar"
                                                        aria-valuenow={progress}
                                                        aria-valuemin="0"
                                                        aria-valuemax="100"
                                                        style={{ width: progress + "%" }}
                                                    >
                                                      {progress}%
                                                    </div>
                                                  </div>
                                              )}

                                              {showUploadBalanceSheetV3Button && selectedFiles && giveOKBLN && (
                                                  <button className="btn btn-danger" onClick={this.uploadBalanceSheetV3}>
                                                    Déposer Bilan v3
                                                  </button>
                                              )}
                                            </center>
                                          </div>
                                        </CCardBody>
                                      </CCard>
                                  )}

                                  {showPopupSuccessUpload && (
                                      <Modal
                                          isOpen={showPopupSuccessUpload}
                                          contentLabel="Example Modal"
                                          style={customStyles}
                                      >
                                        <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                        <hr/>
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
                                            {" "}
                                            Ok{" "}
                                          </button>
                                          <br />
                                          <br />
                                        </center>
                                      </Modal>
                                  )}

                                  {fileInfos.length >= 1 && (
                                      <CCard accentColor="danger">
                                        <CCardBody>
                                <span className="redMark13">
                                  Liste des Bilans déposés
                                </span>
                                          <br/><br/>
                                          <ul className="list-group list-group-flush">
                                            {fileInfos.map((file, index) => (
                                                <li className="list-group-item" key={index}>
                                                  <CRow>
                                                    <CCol md="3" class="bg-warning py-2">
                                                      {file.includes("BV1") && (
                                                          <CListGroup accent>
                                                            <CListGroupItem accent="danger"><strong>Bilan v1</strong></CListGroupItem>
                                                          </CListGroup>
                                                      )}
                                                      {file.includes("BV2") && (
                                                          <CListGroup accent>
                                                            <CListGroupItem accent="danger"><strong>Bilan v2</strong></CListGroupItem>
                                                          </CListGroup>
                                                      )}
                                                      {file.includes("BV3") && (
                                                          <CListGroup accent>
                                                            <CListGroupItem accent="danger"><strong>Bilan v3</strong></CListGroupItem>
                                                          </CListGroup>
                                                      )}
                                                    </CCol>
                                                    <CCol md="5" class="bg-warning py-2">
                                                      <div style={{marginTop: '13px'}}>{file.substr(0, file.indexOf("UNITBV"))}.pdf</div>
                                                    </CCol>
                                                    <CCol md="3" class="bg-warning py-2">
                                                      <div style={{marginTop: '13px'}}>{file.substr(file.indexOf("UNITBV") + 7)}</div>
                                                    </CCol>
                                                    <CCol md="1" class="bg-warning py-2">
                                                      <center>

                                                        {
                                                          file.includes("BV1") && fileInfos.length === 1 &&
                                                          <CButton  shape="pill" color="danger" aria-expanded="true"
                                                                    onClick={() => this.modifyMyBV1()}>
                                                            <CTooltip content="Modifier mon Bilan Version 1" placement="top">
                                                              <CIcon content={freeSet.cilReload}/>
                                                            </CTooltip>
                                                          </CButton>
                                                        }

                                                        {
                                                          file.includes("BV2") && fileInfos.length === 2 &&
                                                          <CButton  shape="pill" color="danger" aria-expanded="true"
                                                                    onClick={() => this.modifyMyBV2()}>
                                                            <CTooltip content="Modifier mon Bilan Version 2" placement="top">
                                                              <CIcon content={freeSet.cilReload}/>
                                                            </CTooltip>
                                                          </CButton>
                                                        }

                                                        {
                                                          file.includes("BV3") && fileInfos.length === 3 &&
                                                          <CButton  shape="pill" color="danger" aria-expanded="true"
                                                                    onClick={() => this.modifyMyBV3()}>
                                                            <CTooltip content="Modifier mon Bilan Version 3" placement="top">
                                                              <CIcon content={freeSet.cilReload}/>
                                                            </CTooltip>
                                                          </CButton>
                                                        }
                                                      </center>
                                                    </CCol>
                                                  </CRow>
                                                </li>
                                            ))}
                                          </ul>
                                        </CCardBody>
                                      </CCard>
                                  )}
                                </>

                              </CCol>

                              <CCol md="4">
                                <VerticalTimeline>
                                  <VerticalTimelineElement
                                      className="vertical-timeline-element--work"
                                      contentStyle={{ background: 'rgb(255, 204, 224)', color: '#000000' }}
                                      contentArrowStyle={{ borderRight: '10px solid  rgb(255, 204, 224)' }}
                                      date={dateDepotBalanceSheetFormedV3}
                                      iconStyle={{ background: 'rgb(255, 204, 224)', color: '#fff', width: '10px', height: '10px', marginLeft: '-4px', marginTop: '27px' }}>

                                    <h6 className="vertical-timeline-element-title">Date Dépôt Bilan Version 3</h6>
                                    <span style={{color: "#ffffff", fontSize: "11px", fontStyle: "italic"}}>
                        23ème Semaine
                      </span>
                                  </VerticalTimelineElement>
                                  <VerticalTimelineElement
                                      className="vertical-timeline-element--work"
                                      date={dateDepotBalanceSheetFormedV2}
                                      contentStyle={{ background: 'rgb(255, 153, 194)', color: '#000000' }}
                                      contentArrowStyle={{ borderRight: '10px solid  rgb(255, 153, 194)' }}
                                      iconStyle={{ background: 'rgb(255, 153, 194)', color: '#fff', width: '10px' , height: '10px', marginLeft: '-4px', marginTop: '27px' }}>

                                    <h6 className="vertical-timeline-element-title">Date Dépôt Bilan Version 2</h6>
                                    <span style={{color: "#ffffff", fontSize: "11px", fontStyle: "italic"}}>
                        14ème Semaine
                      </span>
                                  </VerticalTimelineElement>

                                  <VerticalTimelineElement
                                      className="vertical-timeline-element--education"
                                      date={dateDepotBalanceSheetFormedV1}
                                      contentStyle={{ background: 'rgb(255, 51, 133)', color: '#000000' }}
                                      contentArrowStyle={{ borderRight: '10px solid  rgb(255, 51, 133)' }}
                                      iconStyle={{ background: 'rgb(255, 51, 133)', color: '#fff', width: '10px' , height: '10px', marginLeft: '-4px', marginTop: '27px' }}>
                                    <h6 className="vertical-timeline-element-title">Date Dépôt Bilan Version 1</h6>
                                    <span style={{color: "#ffffff", fontSize: "11px", fontStyle: "italic"}}>
                        6ème Semaine
                      </span>
                                  </VerticalTimelineElement>
                                  <VerticalTimelineElement
                                      iconStyle={{ background: 'rgb(255, 0, 102)', color: '#fff' }}
                                      icon={<AccessAlarmIcon />}
                                  />
                                </VerticalTimeline>
                                <CRow>
                                  <CCol xs="12">
                                    <center>
                      <span className="float-center" style={{color: "#ff0066", fontSize: "14px", fontWeight: "bold"}}>
                        {dateNewestDebutStageConvAv}
                      </span>
                                    </center>
                                  </CCol>
                                </CRow>
                              </CCol>
                            </CRow>
                          }
                        </>
                        :
                        <CRow>
                          <CCol md="2"/>
                          <CCol md="8">
                            <br/><br/>
                            <CCard accentColor="danger">
                              <CCardBody>
                                <center>
                                  <br /><br />
                                  <span className="info2InDiv">
                           Vous ne pouvez pas déposer votre Bilan tant que
                           <br/>
                           Votre Convention Actuelle &nbsp;
                                    <span className="clignoteRed">
                              n'est pas encore VALIDÉE
                           </span>.
                        </span>
                                  <br/><br/>
                                  <span className="info2InDiv">
                            État Actuel de Convention: &nbsp;
                                    <span className="clignoteRed">
                              {
                                statusConvention === "01" &&
                                <>DÉPOSÉE</>
                              }
                                      {
                                        statusConvention === "03" &&
                                        <>ANNULATION VALIDÉE</>
                                      }
                                      {
                                        statusConvention === "04" &&
                                        <>ANNULATION DEMANDÉE</>
                                      }
                           </span>.
                        </span>
                                  <br />
                                  <br /><br />
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
                  }
                </>
          }

          <br />
          <br />
          <hr />
          <div style={{ textAlign: "left" }}>
          <span className="note">
            Vous pouvez publier votre Bilan à la 6ème, 15ème and 25ème semaine
            après avoir commancer votre stage.
          </span>
          </div>
          <br />
          <br />
        </>
    );
  }
}
