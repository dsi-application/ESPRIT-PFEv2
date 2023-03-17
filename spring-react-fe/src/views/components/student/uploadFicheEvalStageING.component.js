import React, { Component } from "react";

import AuthService from "../../services/auth.service";
import greyUpload from "../../images/greyUpload.jpg";

import blueLightUpload from "../../images/blueLightUpload.png";
import orangeUpload from "../../images/orangeUpload.jpg";
import blueBoldUpload from "../../images/blueBoldUpload.jpg";

import Modal from "react-modal";
import {NotificationContainer, NotificationManager} from 'react-notifications';
import { CListGroup, CListGroupItem, CCard, CCardBody, CRow, CCol, CCardFooter, CBadge, CContainer, CButton } from "@coreui/react";
import "react-notifications/lib/notifications.css";
import "../../css/style.css";

import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import Spinner from "react-bootstrap/Spinner";

import { Icon } from '@iconify/react';
import handPointingLeft from '@iconify-icons/mdi/hand-pointing-left';
import handPointingRight from '@iconify-icons/mdi/hand-pointing-right';


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

const API_URL_STU = process.env.REACT_APP_API_URL_STU;
const currentStudent = AuthService.getCurrentStudent();

export default class UploadFicheEvalStageING extends Component
{

    constructor(props)
    {
        super(props);

        this.selectFileJournal = this.selectFileJournal.bind(this);
        this.uploadJournal = this.uploadJournal.bind(this);
        this.closeModalSuccessUploadJournal = this.closeModalSuccessUploadJournal.bind(this);

        this.selectFileAttestation = this.selectFileAttestation.bind(this);
        this.uploadAttestation = this.uploadAttestation.bind(this);
        this.closeModalSuccessUploadAttestation = this.closeModalSuccessUploadAttestation.bind(this);

        this.selectFileRapport = this.selectFileRapport.bind(this);
        this.uploadRapport = this.uploadRapport.bind(this);
        this.closeModalSuccessUploadRapport = this.closeModalSuccessUploadRapport.bind(this);

        this.closeModalUpdateDepot = this.closeModalUpdateDepot.bind(this);
        this.openModalUpdateDepot = this.openModalUpdateDepot.bind(this);

        this.closeModalConfirmDepot = this.closeModalConfirmDepot.bind(this);
        this.openModalConfirmDepot = this.openModalConfirmDepot.bind(this);

        this.confirmDepot = this.confirmDepot.bind(this);
        this.demanderMAJDepot = this.demanderMAJDepot.bind(this);

        this.state = {
            selectedFilesJournal: undefined,
            currentFileJournal: undefined,
            progressJournal: 0,
            messageJournal: "",
            fileInfosJournal: [],
            primaryUploadJournal: false,
            showUploadJournalButton: false,
            showPopupSuccessUploadJournal: false,
            giveOKNSJournal: false,
            selectedFilesAttestation: undefined,
            currentFileAttestation: undefined,
            progressAttestation: 0,
            messageAttestation: "",
            fileInfosAttestation: [],
            primaryUploadAttestation: false,
            showUploadAttestationButton: false,
            showPopupSuccessUploadAttestation: false,
            giveOKNSAttestation: false,
            selectedFilesRapport: undefined,
            currentFileRapport: undefined,
            progressRapport: 0,
            messageRapport: "",
            fileInfosRapport: [],
            primaryUploadRapport: false,
            showUploadRapportButton: false,
            showPopupSuccessUploadRapport: false,
            giveOKNSRapport: false,
            loadSpinnerConfirmDepot: false,
            etatDepot: ""
        };

        let requestEEET = new XMLHttpRequest();
        requestEEET.open("GET", API_URL_STU + "findEtatDepotStageIngenieur/" + currentStudent.id, false);
        requestEEET.send(null);
        this.state.etatDepot = requestEEET.responseText;

        console.log('----------------0610: ' + requestEEET.responseText);

    }

    componentDidMount()
    {

      let encodedString = btoa(currentStudent.id);

      const studentCodeReq = {studentCode : encodedString}
      console.log('---------------------encodedString: ' + encodedString)


      return axios
        .post(API_URL + "/journalStageINGOFF", studentCodeReq)
        .then((res) => {console.log('---------------------OK JOURNAL GET 17.03.2023', res.data)
          console.log('---------------------OK JOURNAL GET 17.03.2023')

          this.setState({fileInfosJournal: res.data});
        })

        /*AuthService.getJournalStageINGFile(currentStudent.id).then((response) => {
            this.setState({fileInfosJournal: response.data});
        });*/

        AuthService.getAttestationStageINGFile(currentStudent.id).then((response) => {
            this.setState({fileInfosAttestation: response.data});
        });

        AuthService.getRapportStageINGFile(currentStudent.id).then((response) => {
            this.setState({fileInfosRapport: response.data});
        });
    }

    confirmDepot()
    {
        this.setState({
            loadSpinnerConfirmDepot: true
        });

        AuthService.confirmMyDepot(currentStudent.id).then(
            (response) => {
                console.log('-----------SARS1-------------> response: ', response);
                this.setState({
                    etatDepot : response.data,
                    loadSpinnerConfirmDepot: false,
                    openModalValidateConfirm: false
                });
            },
            (error) => {
                // console.log('SELECT -------------- Error');
            }
        );
    }

    selectFileJournal(event)
    {
        console.log('----- Journal ---- 0 ----------------> 0510 ' , event.target.files);

        this.setState({selectedFilesJournal: event.target.files});

        if(event.target.files[0].name.toUpperCase().includes("PDF"))
        {
            this.state.giveOKNSJournal= true
        }
        else
        {
            this.state.giveOKNSJournal= false
        }

        AuthService.getJournalStageINGFile(currentStudent.id).then(
            (response) => {

            },
            (error) => {
                // console.log('SELECT -------------- Error');
            }
        );

        if(this.state.giveOKNSJournal)
        {
            this.setState({ showUploadJournalButton: true });
            let notif = "Vous avez choisi le fichier " + event.target.files[0].name + " .";
            return NotificationManager.success(notif, "Information", 6000);
        }

        console.log('----- Journal --------------------> 0510 ' , this.state.selectedFilesJournal);

    }

    openModalUpdateDepot()
    {
        this.setState({openModalValidateMAJ: true});
    }

    closeModalUpdateDepot()
    {
        this.setState({openModalValidateMAJ: false});
    }

    openModalConfirmDepot()
    {
        this.setState({openModalValidateConfirm: true});
    }

    closeModalConfirmDepot()
    {
        this.setState({openModalValidateConfirm: false});
    }

    closeModalSuccessUploadJournal()
    {
        this.setState({showPopupSuccessUploadJournal: false,});
    }

    demanderMAJDepot()
    {
        this.setState({
            loadSpinnerApplyForMAJDepot: true
        });

        AuthService.applyForUpdatingMyDepot(currentStudent.id).then(
            (response) => {
                console.log('-----------SARS1-------------> response: ', response);
                this.setState({
                    etatDepot : response.data,
                    loadSpinnerApplyForMAJDepot: false,
                    openModalValidateMAJ: false
                });
            },
            (error) => {
                // console.log('SELECT -------------- Error');
            }
        );

    }

    uploadJournal()
    {
        let currentFileJournal = this.state.selectedFilesJournal[0];

        this.setState({
            progressJournal: 0,
            currentFileJournal: currentFileJournal,
        });

        AuthService.uploadJournalStageINGFile(currentFileJournal, currentStudent.id, (event) => {
            this.setState({
                showUploadReportButton: false,
                progressJournal: Math.round((100 * event.loaded) / event.total),
                primaryUploadJournal: true
            });
        })
            .then((response) => {
                console.log('-----------SARS--------------> DONE 1', response);
                this.setState({
                    primaryUploadJournal: false,
                    messageJournal: response.data.message,
                    showPopupSuccessUploadJournal: true
                });
                return AuthService.getJournalStageINGFile(currentStudent.id);
            })
            .then((files) => {
                this.setState({fileInfosJournal: files.data});
                if( files.data.length !== 0 && this.state.fileInfosAttestation.length !== 0 && this.state.fileInfosRapport.length !== 0 )
                {
                    this.setState({etatDepot: "01"});
                }
            })
            .catch(() => {
                // console.log('-----------24.08.22--------------> messageJournal');
                this.setState({
                    progressJournal: 0,
                    messageJournal: "Could not upload the file !.",
                    currentFileJournal: undefined
                });
            });

        this.setState({selectedFilesJournal: undefined});
        console.log('-----------LOL-------------> messageJournal');
    }

    selectFileAttestation(event)
    {
        console.log('----- Attestation ---- 0 ----------------> 0510 ' , event.target.files);

        this.setState({selectedFilesAttestation: event.target.files,});

        if(event.target.files[0].name.toUpperCase().includes("PDF"))
        {
            this.state.giveOKNSAttestation= true
        }
        else
        {
            this.state.giveOKNSAttestation= false
        }

        AuthService.getAttestationStageINGFile(currentStudent.id).then(
            (response) => {

            },
            (error) => {
                // console.log('SELECT -------------- Error');
            }
        );

        if(this.state.giveOKNSAttestation)
        {
            this.setState({ showUploadAttestationButton: true });
            let notif = "Vous avez choisi le fichier " + event.target.files[0].name + " .";
            return NotificationManager.success(notif, "Information", 6000);
        }

        console.log('----- Attestation --------------------> 0510 ' , this.state.selectedFilesAttestation);

    }

    closeModalSuccessUploadAttestation()
    {
        this.setState({showPopupSuccessUploadAttestation: false,});
    }

    uploadAttestation()
    {
        let currentFileAttestation = this.state.selectedFilesAttestation[0];

        this.setState({
            progressAttestation: 0,
            currentFileAttestation: currentFileAttestation,
        });

        AuthService.uploadAttestationStageINGFile(currentFileAttestation, currentStudent.id, (event) => {
            this.setState({
                showUploadReportButton: false,
                progressAttestation: Math.round((100 * event.loaded) / event.total),
                primaryUploadAttestation: true
            });
        })
            .then((response) => {
                // console.log('-----------24.08.22--------------> DONE 1');
                this.setState({
                    primaryUploadAttestation: false,
                    messageAttestation: response.data.message,
                    showPopupSuccessUploadAttestation: true
                });
                return AuthService.getAttestationStageINGFile(currentStudent.id);
            })
            .then((files) => {
                this.setState({fileInfosAttestation: files.data});
                if(this.state.fileInfosJournal.length !== 0 && files.data.length !== 0 && this.state.fileInfosRapport.length !== 0)
                {
                    this.setState({etatDepot: "01"});
                }
            })
            .catch(() => {
                // console.log('-----------24.08.22--------------> ERROR');
                this.setState({
                    progressAttestation: 0,
                    messageAttestation: "Could not upload the file !.",
                    currentFileAttestation: undefined
                });
            });

        this.setState({selectedFilesAttestation: undefined});
    }


    selectFileRapport(event)
    {
        console.log('----- Rapport ---- 0 ----------------> 0510 ' , event.target.files);

        this.setState({selectedFilesRapport: event.target.files,});

        if(event.target.files[0].name.toUpperCase().includes("PDF"))
        {
            this.state.giveOKNSRapport= true
        }
        else
        {
            this.state.giveOKNSRapport= false
        }

        AuthService.getRapportStageINGFile(currentStudent.id).then(
            (response) => {

            },
            (error) => {
                // console.log('SELECT -------------- Error');
            }
        );

        if(this.state.giveOKNSRapport)
        {
            this.setState({ showUploadRapportButton: true });
            let notif = "Vous avez choisi le fichier " + event.target.files[0].name + " .";
            return NotificationManager.success(notif, "Information", 6000);
        }

        console.log('----- Rapport --------------------> 0510 ' , this.state.selectedFilesRapport);

    }

    closeModalSuccessUploadRapport()
    {
        this.setState({showPopupSuccessUploadRapport: false,});
    }

    uploadRapport()
    {
        let currentFileRapport = this.state.selectedFilesRapport[0];

        this.setState({
            progressRapport: 0,
            currentFileRapport: currentFileRapport,
        });

        AuthService.uploadRapportStageINGFile(currentFileRapport, currentStudent.id, (event) => {
            this.setState({
                showUploadReportButton: false,
                progressRapport: Math.round((100 * event.loaded) / event.total),
                primaryUploadRapport: true
            });
        })
            .then((response) => {
                // console.log('-----------24.08.22--------------> DONE 1');
                this.setState({
                    primaryUploadRapport: false,
                    messageRapport: response.data.message,
                    showPopupSuccessUploadRapport: true
                });
                return AuthService.getRapportStageINGFile(currentStudent.id);
            })
            .then((files) => {
                this.setState({fileInfosRapport: files.data});
                if(this.state.fileInfosJournal.length !== 0 && this.state.fileInfosAttestation.length !== 0 && files.data.length !== 0 )
                {
                    this.setState({etatDepot: "01"});
                }
            })
            .catch(() => {
                // console.log('-----------24.08.22--------------> ERROR');
                this.setState({
                    progressRapport: 0,
                    messageRapport: "Could not upload the file !.",
                    currentFileRapport: undefined
                });
            });

        this.setState({selectedFilesRapport: undefined});
    }

    render() {
        const {
            selectedFilesJournal,
            currentFileJournal,
            progressJournal,
            messageJournal,
            fileInfosJournal,
            primaryUploadJournal,
            showUploadJournalButton,
            showPopupSuccessUploadJournal,
            giveOKNSJournal,
            selectedFilesAttestation,
            currentFileAttestation,
            progressAttestation,
            messageAttestation,
            fileInfosAttestation,
            primaryUploadAttestation,
            showUploadAttestationButton,
            showPopupSuccessUploadAttestation,
            giveOKNSAttestation,
            selectedFilesRapport,
            currentFileRapport,
            progressRapport,
            messageRapport,
            fileInfosRapport,
            primaryUploadRapport,
            showUploadRapportButton,
            showPopupSuccessUploadRapport,
            giveOKNSRapport,
            etatDepot,
            loadSpinnerConfirmDepot,
            loadSpinnerApplyForMAJDepot,
            openModalValidateMAJ,
            openModalValidateConfirm
        } = this.state;

        const { classes } = this.props;

        return (
            <>
                <CCard accentColor="danger">
                    <CCardBody>
                        <CRow>
                            <CCol>
                                {
                                    etatDepot === '01' &&
                                    <h5 className="float-right">
                                        <CBadge color="dark">Dépôt Effectué : SAUVEGARDÉ</CBadge>
                                    </h5>
                                }
                                {
                                    etatDepot === '02' &&
                                    <h5 className="float-right">
                                        <CBadge color="dark">Dépôt Effectué : CONFIRMÉ</CBadge>
                                    </h5>
                                }
                                {
                                    etatDepot === '03' &&
                                    <>
                                        <p className="float-right">
                                            <h5><CBadge color="dark">Demande Mise À Jour du Dépôt Envoyée à votre Encadrant Académique</CBadge></h5>
                                        </p>
                                    </>
                                }
                                {
                                    etatDepot === '04' &&
                                    <>
                                        <p className="float-right">
                                            <h5><CBadge color="success">Dépôt Validé par votre Encadrant Académique</CBadge></h5>
                                        </p>
                                    </>
                                }
                                {
                                    etatDepot === '05' &&
                                    <>
                                        <p className="float-right">
                                            <h5><CBadge color="danger">Dépôt Réfusé par votre Encadrant Académique</CBadge></h5>
                                        </p>
                                    </>
                                }
                                {
                                    etatDepot === '06' &&
                                    <>
                                        <p className="float-right">
                                            <h5><CBadge color="success">Demande Mise À Jour du Dépôt accordée par votre Encadrant Académique</CBadge></h5>
                                        </p>
                                    </>
                                }
                            </CCol>
                        </CRow>
                        <CRow>
                            <CCol md="4">
                                {
                                    fileInfosJournal.length === 0 ?
                                        <>
                                            <br/>
                                            <span className="text-primary" style={{fontSize: "15px", fontWeight: "bold", fontFamily: "Courier"}}>
                                                Dépôt Journal de Stage
                                            </span>
                                            <br/><br/>
                                            <CCard accentColor="primary">
                                                <CCardBody>
                                                    <div style={{ textAlign: "right" }}>
                                                        <span className="noteGrilleEval">
                                                          Vous devez choisir un fichier PDF.
                                                        </span>
                                                    </div>
                                                    <br/>
                                                    <NotificationContainer/>

                                                    <center>
                                                        <label htmlFor="filePickerJournal" className="custom-file-upload">
                                                            <img src={blueBoldUpload}
                                                                 className="cursorPointer" className="img-fluid"
                                                                 width="80px" height="50px"
                                                                 title="Click Me and Choose your Training Newspaper File"
                                                                 alt=""/>
                                                        </label>
                                                        <br/>
                                                        <label className="btn btn-default">
                                                            <input id="filePickerJournal"
                                                                   type="file"
                                                                   style={{visibility: "hidden"}}
                                                                   accept="application/pdf"
                                                                   onChange={this.selectFileJournal}/>
                                                        </label>

                                                        {
                                                            selectedFilesJournal && !giveOKNSJournal &&
                                                            <center>
                                                                <div className="alert alert-primary" role="alert" style= {{width: "300px"}}>
                                                                    Il ne s'agit pas d'un Fichier PDF !.
                                                                    <br/>
                                                                    Merci de respecter le format demandé.
                                                                </div>
                                                            </center>
                                                        }

                                                        <br/>
                                                        <center>
                                                            {
                                                                currentFileJournal && primaryUploadJournal &&
                                                                <div className="progressJournal" style={{ width: "350px" }}>
                                                                    <div className="progressJournal-bar bg-primary progressJournal-bar-striped progressJournal-bar-animated"
                                                                         role="progressbar"
                                                                         aria-valuenow={progressJournal}
                                                                         aria-valuemin="0"
                                                                         aria-valuemax="100"
                                                                         style={{ width: progressJournal + "%" }}>
                                                                        {progressJournal}%
                                                                    </div>
                                                                </div>
                                                            }
                                                        </center>
                                                        {
                                                            showUploadJournalButton && selectedFilesJournal && giveOKNSJournal &&
                                                            <button className="btn btn-primary" onClick={this.uploadJournal}>
                                                                Déposer Journal Stage
                                                            </button>
                                                        }
                                                    </center>

                                                </CCardBody>
                                            </CCard>

                                        </>
                                        :
                                        <>
                                            <br/><br/>
                                            <CCard accentColor="primary">
                                                <CCardBody>
                                                    <CListGroup accent>
                                                        <CListGroupItem accent="primary">
                                                            <span className="text-primary" style={{fontSize: "14px", fontWeight: "bold"}}>
                                                                Détails Journal Stage déposé
                                                            </span>
                                                        </CListGroupItem>
                                                    </CListGroup>
                                                    <br/>
                                                    <CListGroup accent>
                                                        <CListGroupItem accent="primary">
                                                            <CRow>
                                                                <CCol md="4">
                                                                    <span className="text-primary" style={{fontSize: "13px", fontWeight: "bold"}}>
                                                                        Libellé:
                                                                    </span>
                                                                </CCol>
                                                                <CCol md="8">
                                                                    {fileInfosJournal[0].substr(0, fileInfosJournal[0].indexOf("UNITR1"))}.pdf
                                                                </CCol>
                                                            </CRow>
                                                        </CListGroupItem>
                                                        <CListGroupItem accent="primary">
                                                            <CRow>
                                                                <CCol md="4">
                                                                    <span className="text-primary" style={{fontSize: "13px", fontWeight: "bold"}}>
                                                                        Date Dépôt:
                                                                    </span>
                                                                </CCol>
                                                                <CCol md="8">
                                                                    {fileInfosJournal[0].substr(fileInfosJournal[0].indexOf("UNITR1") + 6)}
                                                                </CCol>
                                                            </CRow>
                                                        </CListGroupItem>
                                                    </CListGroup>
                                                </CCardBody>
                                            </CCard>
                                        </>
                                }
                            </CCol>
                            <CCol md="4">
                                {
                                    fileInfosAttestation.length === 0 ?
                                        <>
                                            <br/>
                                            <span className="text-warning" style={{fontSize: "15px", fontWeight: "bold", fontFamily: "Courier"}}>
                                                Dépôt Attestation de Stage
                                            </span>
                                            <br/><br/>
                                            <CCard accentColor="warning">
                                                <CCardBody>
                                                    <div style={{ textAlign: "right" }}>
                                                        <span className="noteGrilleEval">
                                                          Vous devez choisir un fichier PDF.
                                                        </span>
                                                    </div>
                                                    <br/>
                                                    <NotificationContainer/>

                                                    <center>
                                                        <label htmlFor="filePickerAttestation" className="custom-file-upload">
                                                            <img src={orangeUpload}
                                                                 className="cursorPointer" className="img-fluid"
                                                                 width="80px" height="50px"
                                                                 title="Click Me and Choose your Training Attestation File"
                                                                 alt=""/>
                                                        </label>
                                                        <br/>
                                                        <label className="btn btn-default">
                                                            <input id="filePickerAttestation"
                                                                   type="file"
                                                                   style={{visibility: "hidden"}}
                                                                   accept="application/pdf"
                                                                   onChange={this.selectFileAttestation}/>
                                                        </label>

                                                        {
                                                            selectedFilesAttestation && !giveOKNSAttestation &&
                                                            <center>
                                                                <div className="alert alert-primary" role="alert" style= {{width: "300px"}}>
                                                                    Il ne s'agit pas d'un Fichier PDF !.
                                                                    <br/>
                                                                    Merci de respecter le format demandé.
                                                                </div>
                                                            </center>
                                                        }

                                                        <br/>
                                                        <center>
                                                            {
                                                                currentFileAttestation && primaryUploadAttestation &&
                                                                <div className="progressAttestation" style={{ width: "350px" }}>
                                                                    <div className="progressAttestation-bar bg-warning progressAttestation-bar-striped progressAttestation-bar-animated"
                                                                         role="progressbar"
                                                                         aria-valuenow={progressAttestation}
                                                                         aria-valuemin="0"
                                                                         aria-valuemax="100"
                                                                         style={{ width: progressAttestation + "%" }}>
                                                                        {progressAttestation}%
                                                                    </div>
                                                                </div>
                                                            }
                                                        </center>
                                                        {
                                                            showUploadAttestationButton && selectedFilesAttestation && giveOKNSAttestation &&
                                                            <button className="btn btn-warning" onClick={this.uploadAttestation}>
                                                                Déposer Attestation Stage
                                                            </button>
                                                        }
                                                    </center>

                                                </CCardBody>
                                            </CCard>

                                        </>
                                        :
                                        <>
                                            <br/><br/>
                                            <CCard accentColor="warning">
                                                <CCardBody>
                                                    <CListGroup accent>
                                                        <CListGroupItem accent="warning">
                                                            <span className="text-warning" style={{fontSize: "14px", fontWeight: "bold"}}>
                                                                Détails Attestation Stage déposé
                                                            </span>
                                                        </CListGroupItem>
                                                    </CListGroup>
                                                    <br/>
                                                    <CListGroup accent>
                                                        <CListGroupItem accent="warning">
                                                            <CRow>
                                                                <CCol md="4">
                                                                    <span className="text-warning" style={{fontSize: "13px", fontWeight: "bold"}}>
                                                                        Libellé:
                                                                    </span>
                                                                </CCol>
                                                                <CCol md="8">
                                                                    {fileInfosAttestation[0].substr(0, fileInfosAttestation[0].indexOf("UNITR1"))}.pdf
                                                                </CCol>
                                                            </CRow>
                                                        </CListGroupItem>
                                                        <CListGroupItem accent="warning">
                                                            <CRow>
                                                                <CCol md="4">
                                                                    <span className="text-warning" style={{fontSize: "13px", fontWeight: "bold"}}>
                                                                        Date Dépôt:
                                                                    </span>
                                                                </CCol>
                                                                <CCol md="8">
                                                                    {fileInfosAttestation[0].substr(fileInfosAttestation[0].indexOf("UNITR1") + 6)}
                                                                </CCol>
                                                            </CRow>
                                                        </CListGroupItem>
                                                    </CListGroup>
                                                </CCardBody>
                                            </CCard>
                                        </>
                                }
                            </CCol>
                            <CCol md="4">
                                {
                                    fileInfosRapport.length === 0 ?
                                        <>
                                            <br/>
                                            <span className="text-info" style={{fontSize: "15px", fontWeight: "bold", fontFamily: "Courier"}}>
                                                Dépôt Rapport de Stage
                                            </span>
                                            <br/><br/>
                                            <CCard accentColor="info">
                                                <CCardBody>
                                                    <div style={{ textAlign: "right" }}>
                                                        <span className="noteGrilleEval">
                                                          Vous devez choisir un fichier PDF.
                                                        </span>
                                                    </div>
                                                    <br/>
                                                    <NotificationContainer/>

                                                    <center>
                                                        <label htmlFor="filePickerRapport" className="custom-file-upload">
                                                            <img src={blueLightUpload}
                                                                 className="cursorPointer" className="img-fluid"
                                                                 width="80px" height="50px"
                                                                 title="Click Me and Choose your Training Report File"
                                                                 alt=""/>
                                                        </label>
                                                        <br/>
                                                        <label className="btn btn-default">
                                                            <input id="filePickerRapport"
                                                                   type="file"
                                                                   style={{visibility: "hidden"}}
                                                                   accept="application/pdf"
                                                                   onChange={this.selectFileRapport}/>
                                                        </label>

                                                        {
                                                            selectedFilesRapport && !giveOKNSRapport &&
                                                            <center>
                                                                <div className="alert alert-primary" role="alert" style= {{width: "300px"}}>
                                                                    Il ne s'agit pas d'un Fichier PDF !.
                                                                    <br/>
                                                                    Merci de respecter le format demandé.
                                                                </div>
                                                            </center>
                                                        }

                                                        <br/>
                                                        <center>
                                                            {
                                                                currentFileRapport && primaryUploadRapport &&
                                                                <div className="progressRapport" style={{ width: "350px" }}>
                                                                    <div className="progressRapport-bar bg-info progressRapport-bar-striped progressRapport-bar-animated"
                                                                         role="progressbar"
                                                                         aria-valuenow={progressRapport}
                                                                         aria-valuemin="0"
                                                                         aria-valuemax="100"
                                                                         style={{ width: progressRapport + "%" }}>
                                                                        {progressRapport}%
                                                                    </div>
                                                                </div>
                                                            }
                                                        </center>
                                                        {
                                                            showUploadRapportButton && selectedFilesRapport && giveOKNSRapport &&
                                                            <button className="btn btn-info" onClick={this.uploadRapport}>
                                                                Déposer Rapport Stage
                                                            </button>
                                                        }
                                                    </center>

                                                </CCardBody>
                                            </CCard>
                                        </>
                                        :
                                        <>
                                            <br/><br/>
                                            <CCard accentColor="info">
                                                <CCardBody>
                                                    <CListGroup accent>
                                                        <CListGroupItem accent="info">
                                                            <span className="text-info" style={{fontSize: "14px", fontWeight: "bold"}}>
                                                                Détails Rapport Stage déposé
                                                            </span>
                                                        </CListGroupItem>
                                                    </CListGroup>
                                                    <br/>
                                                    <CListGroup accent>
                                                        <CListGroupItem accent="info">
                                                            <CRow>
                                                                <CCol md="4">
                                                                    <span className="text-info" style={{fontSize: "13px", fontWeight: "bold"}}>
                                                                        Libellé:
                                                                    </span>
                                                                </CCol>
                                                                <CCol md="8">
                                                                    {fileInfosRapport[0].substr(0, fileInfosRapport[0].indexOf("UNITR1"))}.pdf
                                                                </CCol>
                                                            </CRow>
                                                        </CListGroupItem>
                                                        <CListGroupItem accent="info">
                                                            <CRow>
                                                                <CCol md="4">
                                                                    <span className="text-info" style={{fontSize: "13px", fontWeight: "bold"}}>
                                                                        Date Dépôt:
                                                                    </span>
                                                                </CCol>
                                                                <CCol md="8">
                                                                    {fileInfosRapport[0].substr(fileInfosRapport[0].indexOf("UNITR1") + 6)}
                                                                </CCol>
                                                            </CRow>
                                                        </CListGroupItem>
                                                    </CListGroup>
                                                </CCardBody>
                                            </CCard>
                                        </>
                                }
                            </CCol>
                        </CRow>
                        <br/><br/>
                        {
                            etatDepot === "02" &&
                            <center>
                                <span className="clignoteRedAlert10S">Votre Dépôt est effectué.</span>
                                <br/>
                                <span className="clignoteRedAlert10S">Merci de patienter jusqu'à le traitement par votre Encadrant Académique.</span>
                                <br/><br/><br/>
                            </center>
                        }
                    </CCardBody>

                    <CCardFooter>
                        <br/>
                        {
                            etatDepot === "01" &&
                            <p style={{color: "#666666", fontSize: "11px", fontStyle: "italic"}} className="float-left">
                                <button className="btn btn-success" onClick={this.openModalConfirmDepot}>
                                    Confirmer Dépôt
                                </button>
                                &nbsp;&nbsp;&nbsp;
                                <Icon icon={handPointingLeft} color="green" width="30" height="30" />
                                &nbsp;
                                Pour Confirmer votre Dépôt, Veuillez cliquer sur ce bouton
                            </p>
                        }
                    </CCardFooter>
                </CCard>

                {
                    showPopupSuccessUploadJournal &&
                    <Modal  isOpen={showPopupSuccessUploadJournal}
                            contentLabel="Example Modal 1"
                            style={customStyles}>
                        <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                        <hr/>
                        <center>
                            <br/><br/>
                            {messageJournal}
                            <br/><br/><br/>
                            <button  className="btn btn-sm btn-primary" onClick={this.closeModalSuccessUploadJournal}>
                                Ok
                            </button>
                            <br/><br/>
                        </center>
                    </Modal>
                }

                {
                    showPopupSuccessUploadAttestation &&
                    <Modal  isOpen={showPopupSuccessUploadAttestation}
                            contentLabel="Example Modal 2"
                            style={customStyles}>
                        <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                        <hr/>
                        <center>
                            <br/><br/>
                            {messageAttestation}
                            <br/><br/><br/>
                            <button  className="btn btn-sm btn-warning" onClick={this.closeModalSuccessUploadAttestation}>
                                Ok
                            </button>
                            <br/><br/>
                        </center>
                    </Modal>
                }

                {
                    showPopupSuccessUploadRapport &&
                    <Modal  isOpen={showPopupSuccessUploadRapport}
                            contentLabel="Example Modal 3"
                            style={customStyles}>
                        <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                        <hr/>
                        <center>
                            <br/><br/>
                            {messageRapport}
                            <br/><br/><br/>
                            <button  className="btn btn-sm btn-info" onClick={this.closeModalSuccessUploadRapport}>
                                Ok
                            </button>
                            <br/><br/>
                        </center>
                    </Modal>
                }

                <Dialog fullHight
                        fullWidth
                        maxWidth="sm"
                        open={openModalValidateMAJ}
                        onClose={this.closeModalUpdateDepot}
                        aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">
                        <span className="myModalTitle">
                            Envoie Demande Mise À Jour Dépôt
                        </span>
                        <hr/>
                    </DialogTitle>
                    <DialogContent>
                        <CContainer>
                            <center>
                                <br/>
                                Êtes-vous sûr(e)s de vouloir envoyer
                                <br/>
                                une demande de validation de votre Dépôt à votre Encadrant(e) Académique?.
                                <br/><br/><br/>
                            </center>
                        </CContainer>
                    </DialogContent>
                    <DialogActions>
                        {
                            !loadSpinnerApplyForMAJDepot &&
                            <CButton shape="pill" color="success" variant="outline" onClick={this.demanderMAJDepot}>
                                OUI
                            </CButton>
                        }

                        {
                            loadSpinnerApplyForMAJDepot &&
                            <Spinner animation="grow" variant="success"/>
                        }
                        &nbsp;&nbsp;
                        <CButton shape="pill" color="danger" variant="outline" onClick={this.closeModalUpdateDepot}>
                            NON
                        </CButton>
                    </DialogActions>
                </Dialog>

                <Dialog fullHight
                        fullWidth
                        maxWidth="sm"
                        open={openModalValidateConfirm}
                        onClose={this.closeModalConfirmDepot}
                        aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">
                        <span className="myModalTitle">
                            Confirmation Dépôt
                        </span>
                        <hr/>
                    </DialogTitle>
                    <DialogContent>
                        <CContainer>
                            <center>
                                <br/>
                                Êtes-vous sûr(e)s de vouloir confirmer votre Dépôt ?.
                                <br/><br/><br/>
                            </center>
                        </CContainer>
                    </DialogContent>
                    <DialogActions>
                        {
                            !loadSpinnerConfirmDepot &&
                            <CButton shape="pill" color="success" variant="outline" onClick={this.confirmDepot}>
                                OUI
                            </CButton>
                        }

                        {
                            loadSpinnerConfirmDepot &&
                            <Spinner animation="grow" variant="success"/>
                        }
                        &nbsp;&nbsp;
                        <CButton shape="pill" color="danger" variant="outline" onClick={this.closeModalConfirmDepot}>
                            NON
                        </CButton>
                    </DialogActions>
                </Dialog>
            </>
        );
    }

}
