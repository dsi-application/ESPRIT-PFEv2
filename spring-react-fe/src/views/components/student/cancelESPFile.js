import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import AuthService from "../../services/auth.service";
import { Link } from "react-router-dom";
import Modal from "react-modal";

import { CLabel, CFormGroup, CInputRadio, CCard, CCardFooter, CCardBody, CRow, CCol } from "@coreui/react";
import CIcon from "@coreui/icons-react";

import "../../css/style.css";
import Spinner from "react-bootstrap/Spinner";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import {
    gotTraitementFichePFE,
    gotFichePFEDetails,
    gotAllTypesTrtFPFE,
    fetchTraitementFichePFE, fetchFichePFEDetails, fetchMyFichesPFEsHistoric
} from "../../../redux/slices/MyStudentSlice";
import {queryApi} from "../../../utils/queryApi";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import blackAttach from "../../images/blackAttach.png";

import {NotificationContainer, NotificationManager} from 'react-notifications';
import "react-notifications/lib/notifications.css";

const CancelESPFile = () => {

    const dispatch = useDispatch();
    const [traitementFichePFE, errds1] = useSelector(gotTraitementFichePFE);
    const [fichePFEDetails, errds2] = useSelector(gotFichePFEDetails);
    const [allTypesTrtFPFEObj, errds3] = useSelector(gotAllTypesTrtFPFE);

    // console.log('----------------> HI0812 1: ', fichePFEDetails)

    const [descriptionFichePFE, setDescriptionFichePFE] = useState("");
    const [projectTitle, setProjectTitle] = useState("");
    const [etatFiche, setEtatFiche] = useState("");
    const [etatTrt, setEtatTrt] = useState("");
    const [libEtatFiche, setLibEtatFiche] = useState("");

    //const [allTypesTrtFPFE, setAllTypesTrtFPFE] = useState([]);

    const [checked, setChecked] = useState(0);
    const [checkedAnnulationConv, setCheckedAnnulationConv] = useState(2);

    const [trtType, setTrtType] = useState("Modification");
    const [annulConvKind, setAnnulConvKind] = useState("Non");
    const [statusKinds, setStatusKinds] = useState([{value: "Non", label: "Non"}, {value: "Oui", label: "Oui"}]);

    const [successCancelApplication, setSuccessCancelApplication] = useState(false);
    const [showPossibilityCancelConvention, setShowPossibilityCancelConvention] = useState(false);
    const [loadSendDemandeCancelESPFile, setLoadSendDemandeCancelESPFile] = useState(false);


    // Accord Annulation
    const [cancellationAgreementFile, setCancellationAgreementFile] = useState(undefined);
    const [cancellationAgreementFileName, setCancellationAgreementFileName] = useState("");
    const [currentFile, setCurrentFile] = useState(undefined);
    const [progress, setProgress] = useState(0);
    const [successUpload, setSuccessUpload] = useState(false);
    const [selectedFiles, setSelectedFiles] = useState(undefined);
    const [diagramGanttFullPath, setDiagramGanttFullPath] = useState("");
    const [showPopupSuccessUpload, setShowPopupSuccessUpload] = useState(false);
    const [showUploadNewspaperButton, setShowUploadNewspaperButton] = useState(false);
    const [showUploadReportButton, setShowUploadReportButton] = useState(false);
    const [message, setMessage] = useState("");


    const currentStudent = AuthService.getCurrentStudent();

    const loadDataOnlyOnce = () => {dispatch(fetchTraitementFichePFE(currentStudent.id));};

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

    useEffect(() => {
        loadDataOnlyOnce(); // this will fire only on first render
    }, []);


    const onChangeTypeTraitementFichePFE = (iTT, optionTT) =>
    {
        setChecked(iTT);
        setTrtType(optionTT);

        if(iTT === 1)
        {
            setShowPossibilityCancelConvention(true);
        }
        else
        {
            setShowPossibilityCancelConvention(false);
        }
    }

    const onChangeTypeTraitementConvention = (iRDV2, optionRDV2Val) =>
    {
        setCheckedAnnulationConv(Number(iRDV2+2));
        setAnnulConvKind(optionRDV2Val);
    }

    const onChangeDescriptionTraitementFichePFE = (e) =>
    {
        // console.log('----------------------DESC: ', e)
        setDescriptionFichePFE(e);
    }

    const closeModalApplyCancellingFichePFE = (e) =>
    {
        // console.log('---5------------3112SARIA');
        dispatch(fetchTraitementFichePFE(currentStudent.id));
        setSuccessCancelApplication(false);
        // console.log('---6------------3112SARIA');
    }

    const treatFichePFE = async () =>
    {

        let formData = new FormData();
        formData.append("file", cancellationAgreementFile);  // formData.append("file", file);

        setLoadSendDemandeCancelESPFile(true);

        // console.log('---1------------1403 --------- SARIA' + diagramGanttFullPath);
        const [res, err] = await queryApi(
            "student/treatFichePFE?idEt=" + currentStudent.id
                    + "&libTRTFiche=" + trtType
                    + "&libTRTConv=" + annulConvKind
                    + "&treatmentDescription=" + encodeURIComponent(encodeURIComponent(descriptionFichePFE))
                    + "&diagramGanttFullPath=" + diagramGanttFullPath,
            {},
            "POST",
            false
        );

        if (err) {
            // console.log('NOOOOOOOOOOOOOOOOOOOOOOO--------2-------0812SARIA');
        }
        else
        {
            // console.log('--------3-------0812SARIA');
            setSuccessCancelApplication(true);
            setLoadSendDemandeCancelESPFile(false);
            setEtatTrt('01');
            // console.log('----------4-----0812SARIA');
        }
    };

    const applyForModifCancelESPFile = () =>
    {
        let etatFiche = fichePFEDetails.etat;
        let titreProjet = fichePFEDetails.titreProjet;
        let dateAnnulationFiche = traitementFichePFE.dateAnnulationFiche;
        let etatTrt = traitementFichePFE.etatTrt;

        // console.log('---------------------< HIhh: ', etatTrt)
        let allTypesTrtFPFE = [];
        allTypesTrtFPFEObj.forEach((comp) => {
            allTypesTrtFPFE.push({
                value: comp,
                label: comp,
                color: "#00B8D9",
            });
        });

        let currentDt = new Date();
        let currentDay = currentDt.getDate() + "-" + (currentDt.getMonth() + 1) + "-" + currentDt.getFullYear();

        function selectFile(event)
        {
            let attachedFileName = event.target.files[0].name;

            // console.log("----------1--------> 1403");

            let currentFile = event.target.files[0];

            setProgress(0);
            setCurrentFile(currentFile);

            AuthService.uploadCancellationAgreement(currentFile, (event) => {

                let prog = Math.round((100 * event.loaded) / event.total);
                setShowUploadReportButton(false);
                setProgress(prog);
                setSuccessUpload(true);

            })
                .then((response) => {
                    // console.log('-------------------------> 1');
                    let fullMsg = response.data.message;
                    let ganttDiagFP = fullMsg.substring(fullMsg.lastIndexOf('$PATH$C:/ESP/uploads/') + 21);
                    let successNotif = fullMsg.substring(0, fullMsg.lastIndexOf('$PATH$C:/ESP/uploads/'));
                    // console.log('---STORE DG ---------1403-------------> 1: ', fullMsg);
                    // console.log('---STORE DG ---------1403-------------> 2: ', ganttDiagFP);
                    // console.log('---STORE DG ---------1403-------------> 3: ', successNotif);

                    setSuccessUpload(false);
                    setMessage(successNotif);
                    setShowPopupSuccessUpload(true);
                    setDiagramGanttFullPath(ganttDiagFP);
                })
                .catch(() => {
                    // console.log('-------------------------> 3');
                    setProgress(0);
                    setMessage("Could not upload the file !.");
                    setCurrentFile(undefined);
                });

            setSelectedFiles(undefined);

            setCancellationAgreementFileName(attachedFileName);

            // console.log("----------2--------> 1403: " + cancellationAgreementFileName);

            let notif = "Vous avez choisi le fichier " + attachedFileName + " .";
            return NotificationManager.success(notif, "Information", 6000);

        }

        return (
            <>
                {
                    (etatFiche === "A_SOUTENIR" || etatFiche === "SOUTENUE" || etatFiche === "PLANIFIED") &&
                    <>
                        <CRow>
                            <CCol md="2"/>
                            <CCol md="8">
                                <br/><br/>
                                <CCard accentColor="danger">
                                    <CCardBody>
                                        <center>
                                            <br />
                                            <br />
                                            Vous ne pouvez pas envoyer une demande de Modification /
                                            Annulation Plan de Travail
                                            <br/>
                                            Votre Plan de Travail est à l'état
                                            &nbsp;
                                            <span className="clignoteRed">
                                              {etatFiche}
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
                    </>
                }
                {
                    (etatFiche === "ANNULEE" || etatFiche === "REFUSEE" || etatFiche === "DEPOSEE" || etatFiche === "SAUVEGARDEE") &&
                    <>
                        <CRow>
                            <CCol md="2"/>
                            <CCol md="8">
                                <br/><br/>
                                <CCard accentColor="danger">
                                    <CCardBody>
                                        <center>
                                            <br />
                                            <br />
                                            Vous ne pouvez pas envoyer une demande de Modification /
                                            Annulation Plan de Travail
                                            <br/>
                                            tant que votre Plan de Travail actuelle
                                            &nbsp;
                                            <span className="clignoteRed">
                                              n'est pas VALIDÉE
                                            </span>&nbsp;
                                            .
                                            <br /><br />
                                            État Actuel du Plan de Travail: &nbsp;
                                            <span className="clignoteRed">
                                              {etatFiche}
                                            </span>
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
                    </>
                }
                {
                    titreProjet === undefined &&
                    <CRow>
                        <CCol md="2"/>
                        <CCol md="8">
                            <br/><br/>
                            <CCard accentColor="danger">
                                <CCardBody>
                                    <center>
                                        <br />
                                        <br />
                                        Vous n'avez pas encore créer votre Plan de Travail.
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
                }
                {
                    dateAnnulationFiche && etatTrt === "01" && // +Cancel
                    <CRow>
                        <CCol md="2"/>
                        <CCol md="8">
                            <br/><br/>
                            <CCard accentColor="danger">
                                <CCardBody>
                                    <center>
                                        <br />
                                        <br />
                                        Vous avez déjà envoyé une demande le
                                        &nbsp;
                                        <span className="clignoteRed">
                                          {dateAnnulationFiche}
                                        </span>&nbsp;
                                        .
                                        <br />
                                        Votre requête est
                                        &nbsp;
                                        <span className="clignoteRed">
                                          en cours d'exécution
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
                }
                {
                    (
                        etatFiche === "VALIDEE" &&
                        (etatTrt === null || etatTrt === "02" || etatTrt === "03") // FichePFE 03 & (-Cancel +Cancel REALISE)
                    ) &&
                    <>
                        <CCard accentColor="danger">
                            <br/><br/><br/>
                            <CRow>
                            <CCol xs="2" />
                            <CCol xs="8">
                                <CRow>
                                    <CCol md="3">
                                        <span className="greyFieldLibelleCard">
                                          Type Traitement:
                                        </span>
                                    </CCol>
                                    <CCol md="9">
                                        {
                                            allTypesTrtFPFE.map(
                                                (optionTT, iTT) => {
                                                    return (
                                                        <CFormGroup id="grRDV1" variant="custom-radio" inline>
                                                            <CInputRadio  custom id={iTT}
                                                                          checked={checked === iTT ? true : false}
                                                                          onChange={() => onChangeTypeTraitementFichePFE(iTT, optionTT.label)}/>
                                                            <CLabel variant="custom-checkbox" htmlFor={iTT}>{optionTT.label}</CLabel>
                                                        </CFormGroup>
                                                    );
                                                }
                                            )}
                                    </CCol>
                                </CRow>
                                {
                                    showPossibilityCancelConvention &&
                                    <>
                                        <br/>
                                        <CRow>
                                            <CCol md="3">
                                                <span className="greyFieldLibelleCard">Annulation Convention: </span>
                                            </CCol>
                                            <CCol md="9">
                                                {statusKinds.map(
                                                    (optionRDV2, iRDV2) => {
                                                        return (
                                                            <CFormGroup id="grRDV1" variant="custom-radio" inline>
                                                                <CInputRadio  custom id={Number(iRDV2+2)}
                                                                              checked={checkedAnnulationConv === Number(iRDV2+2) ? true : false}
                                                                              onChange={() => onChangeTypeTraitementConvention(iRDV2, optionRDV2.value)}
                                                                />
                                                                <CLabel variant="custom-checkbox" htmlFor={Number(iRDV2+2)}>{optionRDV2.label}</CLabel>
                                                            </CFormGroup>
                                                        );
                                                    }
                                                )}
                                            </CCol>
                                        </CRow>
                                        <NotificationContainer/>
                                        <br/>
                                        <CRow>
                                            <CCol md="3">
                                                <span className="greyFieldLibelleCard">Accord Annulation: </span>
                                            </CCol>
                                            <CCol md="9">
                                                <label htmlFor="filePicker" className="custom-file-upload">
                                                    <img src={blackAttach}
                                                         className="cursorPointer"
                                                         className="img-fluid"
                                                         width="25px"
                                                         height="5px"
                                                         title="Attach your cancellation Agreement"
                                                         alt=""/>
                                                </label>

                                                <label className="btn btn-default">
                                                    <input id="filePicker"
                                                           type="file"
                                                           style={{visibility: "hidden"}}
                                                           accept="application/gan"
                                                           onChange={selectFile}/>
                                                </label>

                                                {
                                                    cancellationAgreementFileName.length !== 0 &&
                                                    <>
                                                        <div className="alert alert-success" role="alert" style={{width: "615px"}}>
                                                            Vous avez choisi le Fichier:
                                                            <br/>
                                                            {cancellationAgreementFileName}
                                                        </div>
                                                    </>
                                                }
                                            </CCol>
                                        </CRow>
                                    </>
                                }
                                <br/>
                                <CRow>
                                    <CCol md="3">
                                        <span className="greyFieldLibelleCard">Description: </span>
                                    </CCol>
                                    <CCol md="9">
                                        <CKEditor
                                            editor={ClassicEditor}
                                            config={{
                                                toolbar: [
                                                    "heading",
                                                    "|",
                                                    "bold",
                                                    "italic",
                                                    "link",
                                                    "bulletedList",
                                                    "numberedList",
                                                    "blockQuote",
                                                ],
                                            }}
                                            //data="<p>Hello from CKEditor 5!</p>"
                                            onReady={(editor) => {
                                                // You can store the "editor" and use when it is needed.
                                            }}
                                            onChange={(event, editor) => {
                                                const data = editor.getData();
                                                // console.log('-----------> lol: ', data)
                                                onChangeDescriptionTraitementFichePFE(data);
                                            }}
                                            onBlur={(event, editor) => {
                                                //console.log("Blur.", editor);
                                            }}
                                            onFocus={(event, editor) => {
                                                //console.log("Focus.", editor);
                                            }}
                                            disabled={loadSendDemandeCancelESPFile}
                                        />
                                    </CCol>
                                </CRow>
                                <br/>
                                <CRow>
                                    <CCol md="12">
                                        <div className="float-right">
                                            {
                                                (
                                                    (loadSendDemandeCancelESPFile === false && trtType === 'Modification')
                                                    ||
                                                    (loadSendDemandeCancelESPFile === false && trtType === 'Annulation' && cancellationAgreementFileName.length !== 0)
                                                )
                                                &&
                                                <button type="button"
                                                        className="btn btn-primary"
                                                        disabled={descriptionFichePFE.length < 12 || descriptionFichePFE.length > 750}
                                                        onClick={() => {treatFichePFE()}}>
                                                    Envoyer
                                                </button>
                                            }
                                            {
                                                loadSendDemandeCancelESPFile === true &&
                                                <Spinner animation="grow" variant="primary"/>
                                            }
                                        </div>
                                    </CCol>
                                </CRow>
                            </CCol>
                        </CRow>
                            <br/><br/><br/><br/><br/><br/><br/>
                        </CCard>

                        <Dialog fullHight fullWidth
                                maxWidth="sm"
                                keepMounted
                                open={successCancelApplication}
                                aria-labelledby="form-dialog-title">
                            <DialogTitle id="form-dialog-title">
                                <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                <hr/>
                            </DialogTitle>
                            <DialogContent>
                                <center>
                                    <br />
                                    Vous avez envoyé une demande de Modification / Annulation
                                    Plan de Travail avec succès.
                                    <br />
                                    Merci de patienter jusqu'à le traitement de votre requête.
                                    <br />
                                    <br />
                                    <br />
                                </center>
                            </DialogContent>
                            <DialogActions>
                                <Button  color="primary"
                                         onClick={() => closeModalApplyCancellingFichePFE()}>
                                    EXIT
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </>
                }
            </>
        );
    }

    return (
        <>
            {applyForModifCancelESPFile()}
        </>
    );
};

export default CancelESPFile
