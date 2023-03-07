import React, {Component} from "react";
import Form from "react-validation/build/form";
import {Link} from "react-router-dom";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../../services/auth.service";
import Button from "@material-ui/core/Button";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import Modal from "react-modal";
import {isEmail, isInt} from "validator";

import { CAlert, CListGroup, CListGroupItem, CCard, CCardFooter, CCardBody, CRow, CCol } from "@coreui/react";
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

import "../../css/style.css";
import StudentService from "../../services/student.service";
import orangeUpload from "../../images/orangeUpload.jpg";

import {NotificationContainer, NotificationManager} from 'react-notifications';
import "react-notifications/lib/notifications.css";

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;

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
export default class SubmitAgreement extends Component {
  constructor(props) {
    super(props);

    this.selectFile = this.selectFile.bind(this);
    this.uploadSignedConvention = this.uploadSignedConvention.bind(this);
    this.closeModalSuccessUpload = this.closeModalSuccessUpload.bind(this);

    this.state = {

      currentStudent: {email: ""},
      dateConvention: "",
      convention: {},
      traitementStatus: "",
      fichePFEStatus: "",

      fileInfos: [],
      selectedFiles: undefined,
      giveOKNS: false,
      currentFile: undefined,
      progress: 0,
      message: "",
      successUpload: false,
      showUploadSignedConventionButton: false,
      showPopupSuccessUpload: false,
      mail: ""
    };

    let currentDt = new Date();
    this.state.currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();


    this.state.traineeDuration = 3;

    var requestc = new XMLHttpRequest();
    requestc.open(
        "GET",
        API_URL_MESP + "studentMailEsp/" + currentStudent.id,
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
        API_URL_MESP + "conventionDto/" + currentStudent.id,
        false
    ); // return axios.get(API_URL + 'user/' + code);
    requestb.send(null);
    this.state.convention = JSON.parse(requestb.responseText);
    console.log('------------DSI------------- convention entity: ' , this.state.convention.dateConvention);

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
        API_URL_STU + "fichePFEStatus/" + currentStudent.id,
        false
    );//API_URL_FESP
    requestfp.send(null);

    let fichePFE = requestfp.responseText;
    this.state.fichePFEStatus = fichePFE.etat;
    // console.log('-------------init------------ fichePFEStatus: ' + this.state.fichePFEStatus);

    this.state.traitementStatus = this.state.convention.traiter;
    // console.log('-------------$$$------------ traiter convention: ' + this.state.traitementStatus);

  }


  componentDidMount() {
    AuthService.getSignedConvention(currentStudent.id).then((response) => {
      this.setState({
        fileInfos: response.data,
      });

    });
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

    AuthService.getSignedConvention(currentStudent.id).then(
        (response) => {

        },
        (error) => {
          // console.log('SELECT -------------- Error');
        }
    );

    // console.log('-----------------------------> Result: ' + this.state.showUploadReportButton + " - " + this.state.showUploadPlagiatButton);

    if(this.state.giveOKNS)
    {
      this.setState({ showUploadSignedConventionButton: true });
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

  uploadSignedConvention() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadSignedConvention(currentFile, currentStudent.id, (event) => {
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
          return AuthService.getSignedConvention(currentStudent.id);
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

  render() {
    const {
      fileInfos,
      selectedFiles,
      giveOKNS,
      currentFile,
      progress,
      message,
      successUpload,
      showUploadSignedConventionButton,
      showPopupSuccessUpload,
      traitementStatus,
      fichePFEStatus,
      currentDay,
    } = this.state;

    return (
        <>
          {
              this.state.dateConvention !== undefined && traitementStatus === "02" &&
              <>
                  <br/><br/>
                  {fileInfos.length >= 0 &&
                      <CRow>
                        <CCol md="2"/>
                        <CCol md="8">
                          <CCard accentColor="warning">
                            <CCardBody>
                              <div style={{ textAlign: "right" }}>
                            <span className="noteDepotSignedConvention">
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
                                      title="Choose your Signed Agreement"
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
                                {showUploadSignedConventionButton && selectedFiles && giveOKNS && (
                                    <button
                                        className="btn btn-warning"
                                        onClick={this.uploadSignedConvention}
                                    >
                                      Déposer Convention Signée
                                    </button>
                                )}
                              </center>

                            </CCardBody>
                          </CCard>
                        </CCol>
                        <CCol md="2"/>
                      </CRow>
                  }

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
                            Détails dernier Convention Signée déposée
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

          {(
              (!this.state.successful && this.state.dateConvention === undefined)  // -Convention
              ||
              (fichePFEStatus === undefined && this.state.dateConvention !== undefined && traitementStatus === "03")  // -FichePFE + Convention ANNULEE
              ||
              (fichePFEStatus !== undefined && fichePFEStatus === "01" && this.state.dateConvention !== undefined && traitementStatus === "03")  // FichePFE DEPOSEE + Convention ANNULEE
              ||
              (fichePFEStatus !== undefined && fichePFEStatus === "02" && this.state.dateConvention !== undefined && traitementStatus === "03")  // FichePFE VALIDEE + Convention ANNULEE
              ||
              (fichePFEStatus !== undefined && fichePFEStatus === "05" && traitementStatus === "03")   // Convention ANNULEE & FichePFE ANNULEE
              ||
              (this.state.dateConvention !== undefined && traitementStatus === "01")
              ||
              (this.state.dateConvention !== undefined && traitementStatus === "03")
              ||
              (this.state.dateConvention !== undefined && traitementStatus === "04")
              ||
              (this.state.dateConvention !== undefined && traitementStatus === "05")
          ) && (
              <>
                <br/>
                <CRow>
                  <CCol md="2"/>
                  <CCol md="8">
                    <br/>
                    <CCard accentColor="danger">
                      <CCardBody>
                        <center>
                          <br/>
                          <br/>
                          Vous n'êtes pas autorisé(es) à déposer une Convention signée !.
                          <br/>
                          Vous n'avez pas encore une Convention &nbsp;
                          <span className="clignoteRed">
                          VALIDÉE
                          </span>&nbsp;.
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

          <br/>
          <br/>
          <hr/>
          <span className="note">
                Vous êtes autorisés à demander une seule Convention de stage.
              </span>
          <br/><br/>
        </>
    );
  }
}
