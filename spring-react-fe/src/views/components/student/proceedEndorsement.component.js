import React, {Component} from "react";
import Form from "react-validation/build/form";
import {Link} from "react-router-dom";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../../services/auth.service";

import DatePicker from "react-datepicker";
import Modal from "react-modal";
import "react-datepicker/dist/react-datepicker.css";
import { CCard, CCardFooter, CCardBody, CRow, CCol, CButton } from "@coreui/react";

import CIcon from "@coreui/icons-react";
import Spinner from "react-bootstrap/Spinner";
import "../../css/style.css";

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
    this.onChangeQualityResponsable = this.onChangeQualityResponsable.bind(
      this
    );
    this.onChangeResponsableEntreprise = this.onChangeResponsableEntreprise.bind(
      this
    );
    this.onChangeDateSignatureConvention = this.onChangeDateSignatureConvention.bind(
      this
    );
    this.onChangeNewDateDebut = this.onChangeNewDateDebut.bind(this);
    this.onChangeNewDateFin = this.onChangeNewDateFin.bind(this);

    this.closeModalOk = this.closeModalOk.bind(this);
    this.closeModalSuccessConfirmAddAvenant = this.closeModalSuccessConfirmAddAvenant.bind(
      this
    );
    this.closeModalFailConfirmAddAvenant = this.closeModalFailConfirmAddAvenant.bind(
      this
    );

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
      loadProceedEndorsement: false
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

    // console.log('------------RESUME-1------------ Convention : Date Debut Stage: ' + this.state.newDateDebut + ' -- ' + this.state.convention.dateDebut + ' -- ' + this.state.dateFourMonths);

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


    /****************** Plan Travail **********************/

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
      currentDay,
      libEtatFiche,
      minNewDateDebutStage,
      loadProceedEndorsement
    } = this.state;

    return (
      <>
        {(
          (libEtatFiche !== "A_SOUTENIR" && libEtatFiche !== "SOUTENUE" && libEtatFiche !== "PLANIFIED" ) &&  // FichePFE BEFORE 06-07-08
          (
            (convention.traiter === "02" && avenant.traiter === true )// && dateConvention === avenantDateConvention)  // Avenant TRAITEE & Convention TRAITEE
            ||
            (avenant.dateDebut === undefined && convention.traiter === "02")  // -Avenant & Convention TRAITEE
          )
        ) && (
          <>
            <CCard accentColor="danger">
              <br/><br/>
              <CRow>
              <CCol xs="2"/>
              <CCol xs="8">
                <Form
                  onSubmit={this.handleApplyForAddAvenant}
                  ref={(c) => {
                    this.formAddAvenant = c;
                  }}
                >
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">Libellé Entreprise :</span>
                    </CCol>
                    <CCol md="8">
                      <Input
                        type="text"
                        className="form-control"
                        disabled
                        value={this.state.convention.companyDto.designation}
                      />
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">Adresse Entreprise :</span>
                    </CCol>
                    <CCol md="8">
                      <textarea
                        type="text"
                        className="form-control"
                        disabled
                        value={this.state.convention.address}
                      />
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">
                          Représentant Entreprise :
                        </span>
                    </CCol>
                    <CCol md="8">
                      <Input
                        type="text"
                        className="form-control"
                        disabled
                        value={this.state.convention.responsable}
                      />
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">
                          Responsable Entreprise
                        </span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <Input
                        type="text"
                        className="form-control"
                        name="responsableEntreprise"
                        value={this.state.responsableEntreprise}
                        onChange={this.onChangeResponsableEntreprise}
                        validations={[required, responsableEntreprise]}
                      />
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">
                          Qualité Responsable
                        </span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <Input
                        type="text"
                        className="form-control"
                        name="qualiteResponsable"
                        value={this.state.qualiteResponsable}
                        onChange={this.onChangeQualityResponsable}
                        validations={[required, qualiteResponsable]}
                      />
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                        <span className="convFieldLibelle">
                          Nouvelle Date Début
                        </span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <DatePicker
                        selected={this.state.newDateDebut}
                        onChange={this.onChangeNewDateDebut}
                        isClearable
                        className="form-control"
                        dateFormat="dd-MM-yyyy"
                        minDate={new Date(minNewDateDebutStage)}
                      />
                      {this.state.verifyEmptyNewDateDebut.trim() ===
                      "false" && (
                        <div className="alert alert-danger" role="alert">
                          This field is required !.
                        </div>
                      )}
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">
                          Nouvelle Date Fin
                        </span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <DatePicker
                        selected={this.state.newDateFin}
                        onChange={this.onChangeNewDateFin}
                        placeholderText=" --/--/--"
                        isClearable
                        className="form-control"
                        dateFormat="dd-MM-yyyy"
                        minDate={new Date(this.state.dateFourMonths)}
                      />
                      {this.state.verifyEmptyNewDateFin.trim() === "false" && (
                        <div className="alert alert-danger" role="alert">
                          This field is required !.
                        </div>
                      )}
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">
                          Date Signature Convention
                        </span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <DatePicker
                        selected={this.state.dateSignatureConvention}
                        onChange={this.onChangeDateSignatureConvention}
                        placeholderText=" --/--/--"
                        isClearable
                        className="form-control"
                        dateFormat="dd-MM-yyyy"
                      />
                      {this.state.verifyEmptyDateSignatureConvention.trim() ===
                      "false" && (
                        <div className="alert alert-danger" role="alert">
                          This field is required !.
                        </div>
                      )}
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol md="4">
                      <span className="convFieldLibelle">N° SIREN/SIRET</span>
                      <span className="requiredStar">&nbsp;(*) :</span>
                    </CCol>
                    <CCol md="8">
                      <Input
                        type="text"
                        className="form-control"
                        name="numSiren"
                        value={this.state.numSiren}
                        onChange={this.onChangeNumSiren}
                        validations={[required, numSiren]}
                      />
                    </CCol>
                  </CRow>

                  <br/>
                  <CRow>
                    <CCol>
                      <div className="float-right">
                        <button className="btn btn-danger">
                          Demander Avenant
                        </button>
                      </div>
                    </CCol>
                  </CRow>

                  <CheckButton
                    style={{display: "none"}}
                    ref={(c) => {
                      this.checkBtnAddAvenant = c;
                    }}
                  />
                </Form>
              </CCol>
              <CCol xs="2"/>
            </CRow>
              <br/>
            </CCard>
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
                  Voulez-vous demander un Avenant ?
                  <br/>
                  <br/>
                  <br/>
                  {loadProceedEndorsement === false &&
                    <>
                      <button className="btn btn-sm btn-success"
                              onClick={this.handleApplyForAddAvenant1}>
                        Oui
                      </button>

                      &nbsp;&nbsp;
                      <button className="btn btn-sm btn-danger"
                      onClick={this.closeModalOk}>
                      Non
                      </button>
                    </>
                  }
                  <br/>
                  {
                    loadProceedEndorsement === true &&
                    <Spinner animation="grow" variant="success"/>
                  }
                  <br/>
                  <br/>
                </center>
              </Modal>
            )}
            {successConfirmAddAvenant && (
              <Modal
                isOpen={successConfirmAddAvenant}
                contentLabel="Example Modal"
                style={customStyles}
              >
                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                <hr/>
                <center>
                  <br/>
                  Enregistrement réalisé.
                  <br/>
                  <br/>
                  <br/>
                  <Link to={"/synopsisAndNews"}>
                    <button
                      className="btn btn-sm btn-success"
                      onClick={this.closeModalSuccessConfirmAddAvenant}
                    >
                      
                      Ok
                    </button>
                  </Link>
                  <br/>
                  <br/>
                </center>
              </Modal>
            )}

            {failConfirmAddAvenant && (
              <Modal
                isOpen={failConfirmAddAvenant}
                contentLabel="Example Modal"
                style={customStyles}
              >
                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                <hr/>
                <center>
                  <br/>
                  Erreur d'enregistrement !.
                  <br/>
                  <br/>
                  Please verify your entries.
                  <br/>
                  <br/>
                  <br/>
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={this.closeModalFailConfirmAddAvenant}
                  >
                    
                    Ok
                  </button>
                  <br/>
                  <br/>
                </center>
              </Modal>
            )}

            <hr/>

            <span className="note">
              Only in case of modification of the dates of the Training provided
              by the Agreement.
            </span>
          </>
        )}


        {convention.dateConvention === undefined && (  // -Convention
          <CRow>
            <CCol md="2"/>
            <CCol md="8">
              <br/><br/><br/><br />
              <CCard accentColor="danger">
                <CCardBody>
                  <center>
                    <br/>
                    <br/>
                    Vous ne pouvez pas ajouter un Avenant tant que vous n'avez
                    pas encore demander une Convention.
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
        )}

        {convention.traiter === "01" && (  // Convention DEPOSEE
          <CRow>
            <CCol md="2"/>
            <CCol md="8">
              <br/><br/><br/><br />
              <CCard accentColor="danger">
                <CCardBody>
                  <center>
                    <br/><br/>
                    Vous ne pouvez pas ajouter un Avenant.
                    <br/>
                    Le traitement de votre Convention
                    &nbsp;
                    <span className="clignoteRed">
                      est en cours
                    </span>&nbsp;
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
        )}

        {convention.traiter === "04" && (  // Demande Annulation Convention SENT
            <CRow>
              <CCol md="2"/>
              <CCol md="8">
                <br/><br/><br/><br />
                <CCard accentColor="danger">
                  <CCardBody>
                    <center>
                      <br/><br/>
                      Vous ne pouvez pas ajouter un Avenant.
                      <br/>
                      Vous avez déjà envoyé une demande de Annulation Convention qui est &nbsp;
                      <span className="clignoteRed">
                          en cours d'exécution
                          </span>&nbsp;.
                      <br/>
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
        )}

        {(
             convention.traiter === "02" &&  // Convention TRAITEE
             (libEtatFiche === "A_SOUTENIR" || libEtatFiche === "SOUTENUE" || libEtatFiche === "PLANIFIED") &&  // FichePFE IS 06-07-08
               <CRow>
                     <CCol md="2"/>
                     <CCol md="8">
                       <br/><br/><br/><br />
                       <CCard accentColor="danger">
                         <CCardBody>
                           <center>
                             <br />
                             <br />
                             Vous ne pouvez pas Etendre un Avenant.
                             <br/>
                             Votre Fiche est à l'état
                             &nbsp;
                             <span className="clignoteRed">
                              {libEtatFiche}
                             </span>&nbsp;
                             .
                             <br />
                             <br />
                             <br />
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
        )}

        {avenant.traiter === false && dateConvention === avenantDateConvention && (  // Avenant DEPOSEE
          <CRow>
            <CCol md="2"/>
            <CCol md="8">
              <br/><br/><br/><br />
              <CCard accentColor="danger">
                <CCardBody>
                  <center>
                    <br/>
                    <br/>
                    Vous avez déjà demandé un Avenant.
                    <br/>
                    Vous allez recevoir un mail &nbsp;
                    <span className="clignoteRed">
                      après avoir validé votre demande
                    </span>&nbsp;
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
        )}

        {(
          convention.traiter === "03" &&
          <CRow>
            <CCol md="2"/>
            <CCol md="8">
              <br/><br/><br/><br />
              <CCard accentColor="danger">
                <CCardBody>
                  <center>
                    <br/>
                    <br/>
                    Vous avez déjà &nbsp;
                    <span className="clignoteRed">
                      une Convention ANNULÉE
                    </span>&nbsp;.
                    <br/>
                    Vous êtes invités à ajouter &nbsp;
                    <span className="clignoteRed">
                      une Nouvelle Convention
                    </span>&nbsp;
                    avant pouvoir demander un Avenant
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
        )}

        <br/>
        <br/>
        <br/>
      </>
    );
  }
}
