import React, {Component} from "react";
import Form from "react-validation/build/form";
import {Link} from "react-router-dom";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../../services/auth.service";

import DatePicker from "react-datepicker";
import Modal from "react-modal";
import "react-datepicker/dist/react-datepicker.css";

import CIcon from "@coreui/icons-react";
import Spinner from "react-bootstrap/Spinner";
import "../../css/style.css";
import orangeUpload from "../../images/orangeUpload.jpg";
import {NotificationContainer, NotificationManager} from 'react-notifications';
import "react-notifications/lib/notifications.css";
import { CAlert, CListGroup, CListGroupItem, CCard, CCardFooter, CCardBody, CRow, CCol } from "@coreui/react";


const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const currentStudent = AuthService.getCurrentStudent();

const customStyles = {
  content: {
    top: "50%",
    left: "60%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "400px",
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

const numSiren = (value) => {
  if (value.length < 9 || value.length > 20) {
    return (
      <div className="alert alert-danger" role="alert">
        The numSiren must be between 9 and 20 characters.
      </div>
    );
  }
};

const responsableEntreprise = (value) => {
  if (value.length < 6 || value.length > 100) {
    return (
      <div className="alert alert-danger" role="alert">
        The responsableEntreprise must be between 6 and 100 characters.
      </div>
    );
  }
};

const qualiteResponsable = (value) => {
  if (value.length < 6 || value.length > 200) {
    return (
      <div className="alert alert-danger" role="alert">
        The qualiteResponsable must be between 6 and 200 characters.
      </div>
    );
  }
};

//
export default class ProceedEndorsement extends Component {
  constructor(props) {
    super(props);

    this.handleApplyForAddAvenant = this.handleApplyForAddAvenant.bind(this);
    this.handleApplyForAddAvenant1 = this.handleApplyForAddAvenant1.bind(this);
    this.onChangeNumSiren = this.onChangeNumSiren.bind(this);
    this.onChangeQualityResponsable = this.onChangeQualityResponsable.bind(this);
    this.onChangeResponsableEntreprise = this.onChangeResponsableEntreprise.bind(this);
    this.onChangeDateSignatureConvention = this.onChangeDateSignatureConvention.bind(this);
    this.onChangeNewDateDebut = this.onChangeNewDateDebut.bind(this);
    this.onChangeNewDateFin = this.onChangeNewDateFin.bind(this);
    this.closeModalOk = this.closeModalOk.bind(this);
    this.closeModalSuccessConfirmAddAvenant = this.closeModalSuccessConfirmAddAvenant.bind(this);
    this.closeModalFailConfirmAddAvenant = this.closeModalFailConfirmAddAvenant.bind(this);

    this.selectFile = this.selectFile.bind(this);
    this.uploadSignedAvenant = this.uploadSignedAvenant.bind(this);
    this.closeModalSuccessUpload = this.closeModalSuccessUpload.bind(this);
    // console.log('--------------- currentStudent: ' + currentStudent.id);

    this.state = {
      successConfirmAddAvenant: false,
      failConfirmAddAvenant: false,
      dateSignatureConvention: null,
      verifyEmptyDateSignatureConvention: "init",
      newDateDebut: new Date(),
      verifyEmptyNewDateDebut: "init",
      newDateFin: null,
      verifyEmptyNewDateFin: "init",
      numSiren: "",
      qualiteResponsable: "",
      responsableEntreprise: "",
      convention: {},
      avenant: {},
      avenantFromConvention: {},
      avenantDateConvention: "",
      ok: false,
      currentDay: "",
      libEtatFiche: "",
      trainingDuration: "",
      dateFourMonths: null,
      minNewDateDebutStage: null,
      loadProceedEndorsement: false,

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

    var requesttd = new XMLHttpRequest();
    requesttd.open(
        "GET",
        API_URL_MESP + "traineeDuration",
        false
    ); // return axios.get(API_URL + 'user/' + code);
    requesttd.send(null);
    this.state.traineeDuration = JSON.parse(requesttd.responseText);
    // console.log('2. -------------------> DATE 2021 - 1' + this.state.traineeDuration);

    let currentDt = new Date();
    this.state.currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();

    // console.log('---------init------ cvn: ' + this.state.successConfirmAddAvenant);
    // console.log('2. -------------------> DATE: ' + myDate);

    var requesta = new XMLHttpRequest();
    requesta.open(
      "GET",
      API_URL_MESP + "conventionDto/" + currentStudent.id,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requesta.send(null);

    this.state.convention = JSON.parse(requesta.responseText);
    this.state.dateConvention = this.state.convention.dateConvention;


    // Loads Dates
    this.state.minNewDateDebutStage = this.state.convention.dateDebut;
    this.state.newDateDebut = new Date(this.state.convention.dateDebut);

    let myDatee = new Date(this.state.convention.dateDebut);
    myDatee.setMonth(myDatee.getMonth() + this.state.traineeDuration);
    this.state.dateFourMonths = myDatee;
    // console.log('3. -------------------> DATE 2021 - 4' + this.state.dateFourMonths);

    // console.log('------------RESUME-1------------ Avenant : Date Debut Stage: ' + this.state.newDateDebut + ' -- ' + this.state.convention.dateDebut + ' -- ' + this.state.dateFourMonths);

    // AvenantDto
    let requestb = new XMLHttpRequest();
    requestb.open(
      "GET",
      API_URL_MESP + "avenantDto/" +
        currentStudent.id +
        "/" +
        this.state.dateConvention,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestb.send(null);

    this.state.avenant = JSON.parse(requestb.responseText);

    if(this.state.avenant.dateDebut != undefined && this.state.avenant.traiter === true)
    {
      this.state.minNewDateDebutStage = new Date(this.state.avenant.dateDebut);

      this.state.newDateDebut = new Date(this.state.avenant.dateDebut);

      var myDate = new Date(this.state.avenant.dateDebut);
      myDate.setMonth(myDate.getMonth() + this.state.traineeDuration);
      this.state.dateFourMonths = myDate;

      // console.log('------------RESUME------------- Avenant : Date Debut Stage: ' + this.state.newDateDebut +' -- ' + this.state.avenant.dateDebut + ' -- ' + this.state.dateFourMonths);
    }

    /*
    var requestbb = new XMLHttpRequest();
    requestbb.open(
      "GET",
      API_URL_MESP + "avenantDto/" +
      currentStudent.id +
      "/" +
      this.state.dateConvention,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestbb.send(null);*/

    this.state.avenantFromConvention = JSON.parse(requestb.responseText);

    this.state.avenantDateConvention = this.state.avenant.dateConvention;

    // console.log('------------RESUME------------- CONV-AVEN : Date Debut Stage: ' + this.state.newDateDebut + ' -- ' + this.state.dateFourMonths);


    /****************** Fiche PFE **********************/

    var requestefp = new XMLHttpRequest();
    requestefp.open(
      "GET",
      API_URL_MESP + "etatFicheByStudent/" + currentStudent.id,
      false
    ); // return axios.get(API_URL + 'user/' + code);
    requestefp.send(null);
    this.state.libEtatFiche = requestefp.responseText;

    // console.log('---------------------> ETAT: ' + this.state.libEtatFiche);

  }

  onChangeDateSignatureConvention = (date) => {
    if (date == null) {
      this.setState({
        dateSignatureConvention: date,
        verifyEmptyDateSignatureConvention: "false",
      });
    } else {
      this.setState({
        dateSignatureConvention: date,
        dateFin: null,
        verifyEmptyDateSignatureConvention: "true",
      });
    }
  };

  onChangeNewDateDebut = (date) => {
    if (date == null) {
      this.setState({
        newDateDebut: date,
        verifyEmptyNewDateDebut: "false",
      });
    } else {
      var myDate = new Date(date);
      // console.log('2. -------------------> DATE: ' + myDate);

      myDate.setMonth(myDate.getMonth() + this.state.traineeDuration);

      this.setState({
        newDateDebut: date,
        newDateFin: null,
        verifyEmptyNewDateDebut: "true",
        dateFourMonths: myDate,
      });

      // console.log('2. -----------RESUME--------> DATE: ' + myDate + " - " + this.state.dateFourMonths);

    }
  };


  onChangeNewDateFin = (date) => {
    if (date == null) {
      this.setState({
        newDateFin: date,
        verifyEmptyNewDateFin: "false",
      });
    } else {
      this.setState({
        newDateFin: date,
        verifyEmptyNewDateFin: "true",
      });
    }
  };

  onChangeNumSiren(e) {
    this.setState({numSiren: e.target.value});
  }

  onChangeQualityResponsable(e) {
    this.setState({qualiteResponsable: e.target.value});
  }

  onChangeResponsableEntreprise(e) {
    this.setState({responsableEntreprise: e.target.value});
  }

  closeModalSuccessConfirmAddAvenant(e) {
    this.setState({successConfirmAddAvenant: false});
  }

  closeModalFailConfirmAddAvenant(e) {
    this.setState({failConfirmAddAvenant: false});
  }

  closeModalOk(e) {
    this.setState({ok: false});
  }

  handleApplyForAddAvenant(e) {
    // console.log('**************************************** Verify 1: ' + this.state.verifyEmptyNewDateFin);
    if (this.state.newDateFin == null) {
      // console.log(' DF **************************************** Verify 2.1');

      this.setState({
        verifyEmptyNewDateFin: "false",
      });
    }
    if (this.state.newDateFin != null) {
      // console.log('D DF **************************************** Verify 2.2: ' + this.state.newDateFin);

      this.setState({
        verifyEmptyNewDateFin: "true",
      });
    }

    if (this.state.newDateDebut == null) {
      // console.log(' DD **************************************** Verify 2.1');

      this.setState({
        verifyEmptyDateDebut: "false",
      });
    }
    if (this.state.newDateDebut != null) {
      // console.log(' DD **************************************** Verify 2.2: ' + this.state.newDateDebut);
      this.state.verifyEmptyDateDebut = "true";
    }

    if (this.state.dateSignatureConvention == null) {
      // console.log(' DD **************************************** Verify 2.1');

      this.setState({
        verifyEmptyDateSignatureConvention: "false",
      });
    }
    if (this.state.dateSignatureConvention != null) {
      // console.log(' DD **************************************** Verify 2.2: ' + this.state.dateSignatureConvention);

      this.setState({
        verifyEmptyDateSignatureConvention: "true",
      });
    }

    // console.log('**************************************** Verify 3: ' + this.state.verifyEmptyNewDateFin);

    e.preventDefault();
    this.formAddAvenant.validateAll();

    if (
      this.checkBtnAddAvenant.context._errors.length === 0 &&
      this.state.verifyEmptyNewDateFin === "true" &&
      this.state.verifyEmptyDateDebut === "true"
    ) {
      // console.log('**************************************** Verify 3.4: ' + this.state.verifyEmptyDateFin);
      this.setState({ok: true});
      // console.log('**************************************** Verify 3.5: ' + this.state.verifyEmptyDateFin);
    }
    // console.log('**************************************** Verify 3.6: ' + this.state.verifyEmptyDateFin);
  }

  handleApplyForAddAvenant1(e)
  {
    this.setState({loadProceedEndorsement: true});

    AuthService.addAvenant(
      this.state.dateSignatureConvention,
      this.state.newDateDebut,
      this.state.newDateFin,
      encodeURIComponent(this.state.numSiren),
      encodeURIComponent(this.state.qualiteResponsable),
      encodeURIComponent(this.state.responsableEntreprise)
    ).then(
      (response) => {
        // console.log('======================== DONE');
        this.setState({
          ok: false,
          loadProceedEndorsement: false,
          successConfirmAddAvenant: true
        });
      },
      (error) => {
        // console.log('======================== ERROR');
        this.setState({
          ok: false,
          failConfirmAddAvenant: true
        });
      }
    );
  }

  componentDidMount() {
    AuthService.getSignedAvenant(currentStudent.id).then((response) => {
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

    AuthService.getSignedAvenant(currentStudent.id).then(
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

  uploadSignedAvenant() {
    let currentFile = this.state.selectedFiles[0];

    this.setState({
      progress: 0,
      currentFile: currentFile,
    });

    AuthService.uploadSignedAvenant(currentFile, currentStudent.id, (event) => {
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
          return AuthService.getSignedAvenant(currentStudent.id);
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
      successConfirmAddAvenant,
      convention,
      avenant,
      dateConvention,
      avenantDateConvention,
      avenantFromConvention,
      ok,
      failConfirmAddAvenant,
      libEtatFiche,
      minNewDateDebutStage,
      loadProceedEndorsement,
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
          ( avenant.traiter === false || avenant.dateDebut === undefined ) &&
          <CRow>
            <CCol md="2"/>
            <CCol md="8">
              <br/><br/><br/><br />
              <CCard accentColor="danger">
                <CCardBody>
                  <center>
                    <br/>
                    <br/>
                    Vous n'êtes pas autorisé(es) à déposer un Avenant signé !.
                    <br/>
                    Vous n'avez pas encore un Avenant &nbsp;
                    <span className="clignoteRed">
                          VALIDÉ
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
        }

        {
          avenant.traiter === true &&
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
                              onClick={this.uploadSignedAvenant}
                          >
                            Déposer Avenant Signé
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
                            Détails dernier Avenant Signé déposé
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
        <br/>
        <br/>
        <br/>
      </>
    );
  }
}
