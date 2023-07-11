import React, { Component } from "react";

import AuthService from "../../services/auth.service";

import orangeUpload from "../../images/orangeUpload.jpg";
import Jasypt from "jasypt";
import Modal from "react-modal";

import {NotificationContainer, NotificationManager} from 'react-notifications';
import "react-notifications/lib/notifications.css";

import { CAlert, CListGroup, CListGroupItem, CCard, CCardFooter, CCardBody, CRow, CCol } from "@coreui/react";

import "../../css/style.css";

import AccessAlarmIcon from '@material-ui/icons/AccessAlarm';

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

export default class UploadNewspaper extends Component {
  constructor(props) {
    super(props);

    this.selectFile = this.selectFile.bind(this);
    this.uploadNewspaper = this.uploadNewspaper.bind(this);
    this.deleteReportFile = this.deleteReportFile.bind(this);
    this.closeModalSuccessUpload = this.closeModalSuccessUpload.bind(this);

    this.state = {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: "",
      fileInfos: [],
      successUpload: false,
      showUploadNewspaperButton: false,
      showPopupSuccessUpload: false,
      dateNow: "",
      nextDate: "",
      dateDepotNewspaper: "",
      dateDepotNewspaperFormed: "",
      timeForDepotNewspaper: "NOTYETJ",
      nextDateFormed: "",
      currentDay: "",
      giveOKNS: false,
      fichePFEStatus: "",
      dateNewestDebutStageConvAv: "",
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

    if(this.state.fichePFEStatus !== "")
    {
      // February March April June August
      // let currentDt = new Date('August 15, 2022 23:15:30');

      let currentDt = new Date();
      currentDt.setHours(0, 0, 0, 0);
      this.state.dateNow = currentDt;

      // Date & Status Convention
      let requestConv = new XMLHttpRequest();
      requestConv.open("GET",API_URL_STU + "conventionDateConvDateDebutStageStatus/" + currentStudent.id, false);
      requestConv.send(null);
      let convDetails = JSON.parse(requestConv.responseText);

      if(convDetails.length === 0)
      {
        // console.log('---------RESUME ----> Conv = 0');

        this.state.statusConvention = "null";
        this.state.dateConvention = '--';

        // Newspaper
        this.state.dateDepotNewspaperFormed = '--';

        // Balance Sheets
        this.state.dateDepotBalanceSheetFormedV1 = '--';
        this.state.dateDepotBalanceSheetFormedV2 = '--';
        this.state.dateDepotBalanceSheetFormedV3 = '--';

        // Reports
        this.state.dateDepotRapportFormedV1 = '--';
        this.state.dateDepotRapportFormedV2 = '--';
      }
      console.log('---0-----1803' + convDetails.length );
      if(convDetails.length > 0)
      {
        if(convDetails.length === 1)
        {
          // console.log('---------RESUME ----> Conv = 1');
          // Create Timeline % date Convention
          // this.state.dateDebutStageByConv = this.gotFormattedDate(new Date(convDetails.dateDebutStage));
          this.state.dateConvention = convDetails[0].dateConvention;
          this.state.statusConvention = convDetails[0].traiter;
          console.log('---1-----1803' + this.state.statusConvention );
          this.state.dateNewestDebutStageConvAv = this.gotFormattedDate(new Date(convDetails[0].dateDebutStage));
        }
        console.log('---2-----1803' + this.state.statusConvention );
        if(convDetails.length > 1)
        {
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
      requestAv.open("GET",API_URL_MESP + "avenantDateAvDateDebutStageStatus/" + currentStudent.id, false);
      requestAv.send(null);
      let avDetails = JSON.parse(requestAv.responseText);

      let avsLength = avDetails.length;
      if(avsLength > 0)
      {
        let lastTreatedIndex = avsLength - 1;
        // console.log('--17.54---loli1-- Exist AVenant: ' + lastTreatedIndex + " - " + avDetails.length + " - " + avDetails[0].traiter);
        if(avsLength === 1 && avDetails[0].traiter === true)
        {
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

        if(avsLength > 1 && avDetails[lastTreatedIndex].traiter === true)
        {
          // console.log('---------RESUME ----> Avn > 1');
          if(avDetails[0].traiter === true)
          {
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
      if(this.state.dateNewestDebutStageConvAv !== "")
      {
        let year = this.state.dateNewestDebutStageConvAv.substring(6, 10);
        let month = this.state.dateNewestDebutStageConvAv.substring(3, 5);
        let day = this.state.dateNewestDebutStageConvAv.substring(0, 2);
        /*
        let day = myDate.substring(8, 10);
        let month = myDate.substring(5, 7);
        let year = myDate.substring(0, 4);
        this.state.dateDepotFicheOff = new Date(year, month - 1, day);
        */

        let bridgeRV1Dt = new Date(year, month - 1, day);
        bridgeRV1Dt.setDate(bridgeRV1Dt.getDate() + 2 * 7);
        this.state.dateDepotNewspaper = bridgeRV1Dt;

        // console.log('0.1 ---------loli1----------> DATE depot: ' + bridgeRV1Dt.getFullYear());
        // console.log('0.2 ---------loli1-----fff-----> DATE v1: ' + bridgeRV1Dt.getMonth());
        // console.log('0.3 ---------loli1------ff----> DATE now: ' + bridgeRV1Dt.getDate());
        this.state.dateDepotNewspaperFormed = bridgeRV1Dt.getDate() + "-" + (bridgeRV1Dt.getMonth() + 1) + "-" + bridgeRV1Dt.getFullYear();

      }

      if (
          this.state.dateDepotNewspaper.getTime() > this.state.dateNow.getTime()
      ) {
        // console.log('0.4 ---------lol1----------> DATE now: ' + this.state.dateNow);
        this.state.timeForDepotNewspaper = "BEFORETIMEJ";
      }

      if (
          this.state.dateDepotNewspaper.getTime() <= this.state.dateNow.getTime()
      ) {
        this.state.timeForDepotNewspaper = "ITISTIMEJ";
      }
    }

  }

  componentDidMount() {
    const fileInfosDecrypted = [];
    const Jasypt = require('jasypt');
    const jasypt = new Jasypt();
    jasypt.setPassword('SALT');

    AuthService.getNewspaper(currentStudent.id).then((response) => {

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

  selectFile(event) {
    this.setState({
      selectedFiles: event.target.files,
    });

    if(event.target.files[0].name.toUpperCase().includes("PDF"))
    {
      this.state.giveOKNS= true
    }
    else
    {
      this.state.giveOKNS= false
    }

    AuthService.getNewspaper(currentStudent.id).then(
      (response) => {

      },
      (error) => {
        // console.log('SELECT -------------- Error');
      }
    );

    // console.log('-----------------------------> Result: ' + this.state.showUploadReportButton + " - " + this.state.showUploadPlagiatButton);

    if(this.state.giveOKNS)
    {
      this.setState({ showUploadNewspaperButton: true });
      let notif =
        "Vous avez choisi le fichier " + event.target.files[0].name + " .";
      return NotificationManager.success(notif, "Information", 6000);
    }
  }

  closeModalSuccessUpload() {
    // console.log(' Verify Tech Duplication -------------------- .');
    this.setState({
      showPopupSuccessUpload: false,
    });
  }

  uploadNewspaper() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadNewspaper(currentFile, currentStudent.id, (event) => {
      this.setState({
        showUploadReportButton: false,
        progress: Math.round((100 * event.loaded) / event.total),
        successUpload: true
      });
    })
      .then((response) => {
        // console.log('-------------------------> 1');
        this.setState({
          successUpload: false,
          message: response.data.message,
          showPopupSuccessUpload: true
        });
        return AuthService.getNewspaper(currentStudent.id);
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
        return AuthService.getNewspaper(currentStudent.id);
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
      showUploadNewspaperButton,
      showPopupSuccessUpload,
      fichePFEStatus,
      dateDepotNewspaperFormed,
      timeForDepotNewspaper,
      currentDay,
      giveOKNS,
      statusConvention
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
                            Vous ne pouvez pas déposer vos Journaux de Bord.
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
                                      Vous ne pouvez pas déposer vos Journaux de Bord.
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
                          (fichePFEStatus === "08" || fichePFEStatus === "06" || fichePFEStatus === "07")
                          &&
                          <>
                            <br/><br/>
                            <CRow>
                              <CCol md="2"/>
                              <CCol md="8">
                                <CCard accentColor="warning">
                                  <CCardBody>
                                    {fileInfos.length === 1 && (
                                        <>
                                          <CListGroup accent>
                                            <CListGroupItem accent="danger">
                                    <span className="text-danger" style={{fontSize: "14px", fontWeight: "bold"}}>
                                      Détails dernier Journal déposé
                                    </span>
                                            </CListGroupItem>
                                          </CListGroup>
                                          <br/>
                                          <CListGroup accent>
                                            <CListGroupItem accent="warning">
                                              <CRow>
                                                <CCol md="2">
                                        <span className="text-warning" style={{fontSize: "13px", fontWeight: "bold"}}>
                                          Libellé:
                                        </span>
                                                </CCol>
                                                <CCol md="10">
                                                  {fileInfos[0].substr(0, fileInfos[0].indexOf("UNITR1"))}.pdf
                                                </CCol>
                                              </CRow>
                                            </CListGroupItem>
                                            <CListGroupItem accent="warning">
                                              <CRow>
                                                <CCol md="2">
                                        <span className="text-warning" style={{fontSize: "13px", fontWeight: "bold"}}>
                                          Date Dépôt:
                                        </span>
                                                </CCol>
                                                <CCol md="10">
                                                  {fileInfos[0].substr(fileInfos[0].indexOf("UNITR1") + 6)}
                                                </CCol>
                                              </CRow>
                                            </CListGroupItem>
                                          </CListGroup>
                                        </>
                                    )}
                                    {fileInfos.length === 0 && (
                                        <center>
                                          <br/><br/>
                                          Vous n'avez pas encore déposé aucun Journal.
                                          <br/><br/><br/>
                                        </center>
                                    )}
                                  </CCardBody>
                                </CCard>
                              </CCol>
                              <CCol md="2"/>
                            </CRow>
                            <br />
                          </>
                        }

                        {
                          fichePFEStatus === "03" && timeForDepotNewspaper === "BEFORETIMEJ" &&
                          <>
                            <CRow>
                              <CCol md="2"/>
                              <CCol md="8">
                                <CAlert color="info" closeButton>
                                  <center>
                                    <AccessAlarmIcon style={{ color: "#ff6600", fontSize: 30 }} />
                                    <br /><br />
                                    <span className="journalClignoteMark">
                                      NORMALEMENT
                                    </span>
                                    &nbsp;
                                    <span className="info2InDiv">
                                      vous êtes capables de publier vos
                                      &nbsp;
                                      <span className="journalClignoteMark">
                                        Journaux
                                      </span>
                                      &nbsp;
                                      à partir
                                    </span>&nbsp;
                                    <span className="journalClignoteMark">
                                      {dateDepotNewspaperFormed}
                                    </span>&nbsp;.
                                    <br />
                                    Mais quand même, le dépôt est possible dès maintenant et à tout moment.
                                    <br/>
                                    <br/>
                                  </center>
                                </CAlert>
                              </CCol>
                              <CCol md="2"/>
                            </CRow>
                          </>
                        }

                        {fichePFEStatus === "03" &&
                        <>
                          {fileInfos.length >= 0 && (
                              <CRow>
                                <CCol md="2"/>
                                <CCol md="8">
                                  <CCard accentColor="warning">
                                    <CCardBody>
                                      <div style={{ textAlign: "right" }}>
                            <span className="noteDepotJournal">
                              Vous devez choisir un fichier PDF.
                            </span>
                                      </div>

                                      <NotificationContainer/>

                                      <center>
                                        <label
                                            htmlFor="filePicker"
                                            className="custom-file-upload"
                                        >
                                          <img
                                              src={orangeUpload}
                                              className="cursorPointer"
                                              className="img-fluid"
                                              width="80px"
                                              height="50px"
                                              title="Choose your Newspaper"
                                              alt=""
                                          />
                                        </label>
                                        <br/>
                                        <label className="btn btn-default">
                                          <input
                                              id="filePicker"
                                              type="file"
                                              style={{visibility: "hidden"}}
                                              accept="application/pdf"
                                              onChange={this.selectFile}
                                          />
                                        </label>

                                        {selectedFiles && !giveOKNS && (
                                            <center>
                                              <div className="alert alert-danger" role="alert" style= {{width: "300px"}}>
                                                Il ne s'agit pas d'un Fichier PDF !.
                                                <br/>
                                                Merci de respecter le format demandé.
                                              </div>
                                            </center>
                                        )}
                                        <br/>
                                        <>
                                          <center>
                                            {currentFile && successUpload && (
                                                <div className="progress" style={{ width: "500px" }}>
                                                  <div
                                                      className="progress-bar bg-warning progress-bar-striped progress-bar-animated"
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
                                          </center>
                                        </>
                                        {showUploadNewspaperButton && selectedFiles && giveOKNS && (
                                            <button
                                                className="btn btn-warning"
                                                onClick={this.uploadNewspaper}
                                            >
                                              Déposer Journal
                                            </button>
                                        )}
                                      </center>

                                    </CCardBody>
                                  </CCard>
                                </CCol>
                                <CCol md="2"/>
                              </CRow>
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

                                    Ok
                                  </button>
                                  <br />
                                  <br />
                                </center>
                              </Modal>
                          )}

                          {fileInfos.length > 0 && (
                              <CRow>
                                <CCol md="2"/>
                                <CCol md="8">
                                  <CListGroup accent>
                                    <CListGroupItem accent="danger">
                          <span className="text-danger" style={{fontSize: "14px", fontWeight: "bold"}}>
                            Détails dernier Journal déposé
                          </span>
                                    </CListGroupItem>
                                  </CListGroup>
                                  <br/>
                                  <CListGroup accent>
                                    <CListGroupItem accent="warning">
                                      <CRow>
                                        <CCol md="2">
                              <span className="text-warning" style={{fontSize: "13px", fontWeight: "bold"}}>
                                Libellé:
                              </span>
                                        </CCol>
                                        <CCol md="10">
                                          {fileInfos[0].substr(0, fileInfos[0].indexOf("UNITR1"))}.pdf
                                        </CCol>
                                      </CRow>
                                    </CListGroupItem>
                                    <CListGroupItem accent="warning">
                                      <CRow>
                                        <CCol md="2">
                              <span className="text-warning" style={{fontSize: "13px", fontWeight: "bold"}}>
                                Date Dépôt:
                              </span>
                                        </CCol>
                                        <CCol md="10">
                                          {fileInfos[0].substr(fileInfos[0].indexOf("UNITR1") + 6)}
                                        </CCol>
                                      </CRow>
                                    </CListGroupItem>
                                  </CListGroup>
                                </CCol>
                                <CCol md="2"/>
                              </CRow>
                          )}
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
                                  Vous ne pouvez pas déposer vos Journaux
                                  <br/>
                                  tant que votre Plan Travail
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
                           Vous ne pouvez pas déposer vos Journaux de Bord tant que
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
            Vous pouvez publier votre Journal chaque jour, par semaine ou par
            quinzaine.
          </span>
        </div>
        <br />
        <br />
      </>
    );
  }
}
