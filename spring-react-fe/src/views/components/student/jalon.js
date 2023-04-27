import React, { useState, useEffect, useRef } from 'react'
import {CCard, CCardBody, CButton, CContainer, CRow, CCol, CTooltip, CSpinner} from '@coreui/react'
import { Grid } from '@mui/material'
import { styled } from '@mui/material/styles'
import axios from 'axios'
import { FormControl, FormLabel, FormGroup, FormControlLabel, Checkbox } from '@mui/material'
import InputLabel from '@mui/material/InputLabel'
import { CFormInput, CFormLabel } from '@coreui/react'
import Divider from "@mui/material/Divider";
import PictureAsPdfIcon from '@mui/icons-material/PictureAsPdf';
import { red } from '@mui/material/colors';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import CloseIcon from '@mui/icons-material/Close';
import "../../css/style.css";

import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import Dialog from "@mui/material/Dialog";

import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import TextField from "@material-ui/core/TextField";
import AuthService from "../../services/auth.service";


const redColor = red[900];
const API_URL_STU = process.env.REACT_APP_API_URL_STU;

const Jalon = () => {

  const [lookForJob, setLookForJob] = useState('non')
  const [alreadyGotJob, setAlreadyGotJob] = useState('')

  const [possibilityA, setPossibilityA] = useState("READY");
  const [possibilityANON1, setPossibilityANON1] = useState("INIT");
  const [possibilityANON2, setPossibilityANON2] = useState("INIT");
  const [possibilityANON3, setPossibilityANON3] = useState("INIT");
  const [possibilityANON4, setPossibilityANON4] = useState("INIT");

  const [verifSaveYESEntries, setVerifSaveYESEntries] = useState(false);
  const [verifSaveEntries, setVerifSaveEntries] = useState(false);
  const [showLoaderSaveYESEntries, setShowLoaderSaveYESEntries] = useState(false);
  const [showLoaderSaveEntries, setShowLoaderSaveEntries] = useState(false);
  const [successSaveYESEntries, setSuccessSaveYESEntries] = useState(false);
  const [successSaveEntries, setSuccessSaveEntries] = useState(false);

  const [promesse1, setPromesse1] = useState(null);
  const [promesse2, setPromesse2] = useState('');
  const [promesse3, setPromesse3] = useState('');

  const [contrat1, setContrat1] = useState(null);
  const [contrat2, setContrat2] = useState('');
  const [contrat3, setContrat3] = useState('');

  const [projet1, setProjet1] = useState(null);
  const [projet2, setProjet2] = useState('');

  const [etude1, setEtude1] = useState(null);
  const [etude2, setEtude2] = useState('');
  const [etude3, setEtude3] = useState('');

  const currentStudent = AuthService.getCurrentStudent();

  useEffect(() => {
    /*const response = axios
      .get(API_URL + 'profile/getgrade', headerconfig)
      .then((res) => {
        console.log(res.data)
        setGrades(() =>
          res.data.map((grade) => {
            return { value: grade.id, label: `${grade.name} (${grade.code})` }
          }),
        )
      })
      .catch(function (error) {
        console.log(error)
      })*/
  }, [])

  const onChangePromesse1 = (date) => {
    setPromesse1(date)
    console.log('---------------------Date : ' + date)
  }

  const onChangeContrat1 = (date) => {
    setContrat1(date)
    console.log('---------------------Date : ' + date)
  }

  const onChangeProjet1 = (date) => {
    setProjet1(date)
    console.log('---------------------Date : ' + date)
  }

  const onChangeEtude1 = (date) => {
    setEtude1(date)
    console.log('---------------------Date : ' + date)
  }

  const handleYesSearchJob = () => {
    console.log('-------------> Promesse 1: ', promesse1);
    console.log('-------------> Promesse 2: ', promesse2);
    console.log('-------------> Promesse 3: ', promesse3);

    setShowLoaderSaveYESEntries(true);

    const response = axios
      .get(API_URL_STU + `queryYesSearchJob/` + currentStudent.id)
      .then((res) => {
        console.log('--------------> SARS: ' + res.data)
        setShowLoaderSaveYESEntries(false);
        setVerifSaveYESEntries(false);
        setSuccessSaveYESEntries(true);
        console.log(res.data);
      })
  }

  const handleSavePromesse = () => {
    console.log('-------------> Promesse 1: ', promesse1);
    console.log('-------------> Promesse 2: ', promesse2);
    console.log('-------------> Promesse 3: ', promesse3);

    setPossibilityANON1("INIT");

    const surveyDatas = {dateShoosenCriteria: promesse1, entryFirstShoosenCriteria: promesse2, entrySecondShoosenCriteria: promesse3};
    const response = axios
      .post(API_URL_STU + `queryNOSearchJobPromesse/` + currentStudent.id, surveyDatas)
      .then((res) => {
        setVerifSaveEntries(false);
        setShowLoaderSaveEntries(false);
        setSuccessSaveEntries(true);
        console.log(res.data)
      })
  }

  const handleSaveContrat = () => {
    console.log('-------------> Contrat 1: ', contrat1);
    console.log('-------------> Contrat 2: ', contrat2);
    console.log('-------------> Contrat 3: ', contrat3);

    setPossibilityANON2("INIT");

    const surveyDatas = {dateShoosenCriteria: contrat1, entryFirstShoosenCriteria: contrat2, entrySecondShoosenCriteria: contrat3};
    const response = axios
      .post(API_URL_STU + `queryNOSearchJobContrat/` + currentStudent.id, surveyDatas)
      .then((res) => {
        setVerifSaveEntries(false);
        setShowLoaderSaveEntries(false);
        setSuccessSaveEntries(true);
        console.log(res.data)
      })
  }

  const handleSaveProjet = () => {
    console.log('-------------> Projet 1: ', projet1);
    console.log('-------------> Projet 2: ', projet2);
    setPossibilityANON3("INIT");

    const surveyDatas = {dateShoosenCriteria: projet1, entryFirstShoosenCriteria: projet2};
    const response = axios
      .post(API_URL_STU + `queryNOSearchJobProjet/` + currentStudent.id, surveyDatas)
      .then((res) => {
        setVerifSaveEntries(false);
        setShowLoaderSaveEntries(false);
        setSuccessSaveEntries(true);
        console.log(res.data)
      })
  }

  const handleSaveEtude = () => {
    console.log('-------------> Etude 1: ', etude1);
    console.log('-------------> Etude 2: ', etude2);
    console.log('-------------> Etude 3: ', etude3);
    setPossibilityANON4("INIT");

    const surveyDatas = {dateShoosenCriteria: etude1, entryFirstShoosenCriteria: etude2, entrySecondShoosenCriteria: etude3};
    const response = axios
      .post(API_URL_STU + `queryNOSearchJobEtude/` + currentStudent.id, surveyDatas)
      .then((res) => {
        setVerifSaveEntries(false);
        setShowLoaderSaveEntries(false);
        setSuccessSaveEntries(true);
        console.log(res.data)
      })
  }


  return (
    <>
      <span className="blueMark13Cr">À fin de Confirmer votre Dépôt, Merci de répondre à cette Enquête.</span>
      <hr/><br/>
      {
        possibilityA === "READY" &&
        <FormControl>
          <span className="greyMark13Cr">Êtes-vous à la recherche d'un emploi ?</span>
          <RadioGroup row
                      aria-labelledby="demo-row-radio-buttons-group-label"
                      name="row-radio-buttons-group"
                      value={lookForJob}
                      onChange={(event) => {setAlreadyGotJob(''); setLookForJob(event.target.value)}}>
            <FormControlLabel value="oui" control={<Radio />} label="Oui"/>
            &nbsp;&nbsp;
            <FormControlLabel value="non" control={<Radio />} label="Non"/>
          </RadioGroup>
        </FormControl>
      }
      {
        lookForJob === "oui" &&
        <>
          <br/><br/>
          <span className="redMark13Cr">Merci pour votre Temps.</span>
          <br/><br/>
          <CButton style={{backgroundColor: '#660000', color: "white", width: "300px"}}
                   size="lg"
                   onClick={() => {setVerifSaveYESEntries(true)}}>
            Sauvegarder
          </CButton>
        </>
      }
      <br/><br/>
      {
        lookForJob === "non" &&
        <CRow>
          <CCol sm="1"/>
          <CCol sm="4" className="colPadding">
            <FormControl>
              <RadioGroup aria-labelledby="demo-row-radio-buttons-group-label"
                          name="row-radio-buttons-group"
                          value={alreadyGotJob}
                          onChange={(event) => setAlreadyGotJob(event.target.value)}>
                <FormControlLabel value="promesse" control={<Radio />} label="Avez-vous une promesse d'embauche ?" />
                <FormControlLabel value="contrat" control={<Radio />} label="Avez-vous décroché un contrat ?" />
                <FormControlLabel value="projet" control={<Radio />} label="Comptez-vous lancer votre projet ?" />
                <FormControlLabel value="etude" control={<Radio />} label="Comptez-vous terminer vos études ?" />
              </RadioGroup>
            </FormControl>
          </CCol>
          <CCol sm="7" className="colPadding">
            {
              alreadyGotJob === "promesse" &&
              <>
                <br/>
                <span className="redMark13Cr">A.1 - </span><span className="greyMark13Cr"> À partir de quelle date ?</span>
                <br/>
                <DatePicker selected={promesse1}
                            onChange={onChangePromesse1}
                            isClearable
                            wrapperClassName="datePicker"
                            dateFormat="dd-MM-yyyy"/>
                <br/><br/>
                <span className="redMark13Cr">A.2 - </span><span className="greyMark13Cr"> Quelle entreprise ?</span>
                <br/>
                <TextField id="filled-basic1" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setPromesse2(e.target.value)}}/>
                <br/><br/>
                <span className="redMark13Cr">A.3 - </span><span className="greyMark13Cr"> Dans quel domaine ?</span>
                <br/>
                <TextField id="filled-basic2" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setPromesse3(e.target.value)}}/>
                <br/><br/><br/>
                <CButton style={{backgroundColor: '#660000', color: "white", width: "300px"}}
                         size="lg"
                         disabled={promesse1 === null || promesse2 === "" || promesse3 === ""}
                         onClick={() => {setPossibilityANON1("READY"); setVerifSaveEntries(true)}}>
                  Sauvegarder
                </CButton>
              </>
            }

            {
              alreadyGotJob === "contrat" &&
              <>
                <br/>
                <span className="redMark13Cr">B.1 - </span><span className="greyMark13Cr"> À partir de quelle date ?</span>
                <br/>
                <DatePicker selected={contrat1}
                            onChange={onChangeContrat1}
                            isClearable
                            wrapperClassName="datePicker"
                            dateFormat="dd-MM-yyyy"/>
                <br/><br/>
                <span className="redMark13Cr">B.2 - </span><span className="greyMark13Cr"> Quelle entreprise ?</span>
                <br/>
                <TextField id="filled-basic3" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setContrat2(e.target.value)}}/>
                <br/><br/>
                <span className="redMark13Cr">B.3 - </span><span className="greyMark13Cr"> Dans quel domaine ?</span>
                <br/>
                <TextField id="filled-basic4" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setContrat3(e.target.value)}}/>
                <br/><br/><br/>
                <CButton style={{backgroundColor: '#660000', color: "white", width: "300px"}}
                         size="lg"
                         disabled={contrat1 === null || contrat2 === "" || contrat3 === ""}
                         onClick={() => {setPossibilityANON2("READY"); setVerifSaveEntries(true)}}>
                  Sauvegarder
                </CButton>
              </>
            }

            {
              alreadyGotJob === "projet" &&
              <>
                <br/>
                <span className="redMark13Cr">C.1 - </span><span className="greyMark13Cr"> À partir de quelle date ?</span>
                <br/>
                <DatePicker selected={projet1}
                            onChange={onChangeProjet1}
                            isClearable
                            wrapperClassName="datePicker"
                            dateFormat="dd-MM-yyyy"/>
                <br/><br/>
                <span className="redMark13Cr">C.2 - </span><span className="greyMark13Cr"> Dans quel domaine ?</span>
                <br/>
                <TextField id="filled-basic5" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setProjet2(e.target.value)}}/>
                <br/><br/><br/>
                <CButton style={{backgroundColor: '#660000', color: "white", width: "300px"}}
                         size="lg"
                         disabled={projet1 === null || projet2 === ""}
                         onClick={() => {setPossibilityANON3("READY"); setVerifSaveEntries(true)}}>
                  Sauvegarder
                </CButton>
              </>
            }

            {
              alreadyGotJob === "etude" &&
              <>
                <br/>
                <span className="redMark13Cr">D.1 - </span><span className="greyMark13Cr"> À partir de quelle date ?</span>
                <br/>
                <DatePicker selected={etude1}
                            onChange={onChangeEtude1}
                            isClearable
                            wrapperClassName="datePicker"
                            dateFormat="dd-MM-yyyy"/>
                <br/><br/>
                <span className="redMark13Cr">D.2 - </span><span className="greyMark13Cr"> Dans quelle université ?</span>
                <br/>
                <TextField id="filled-basic6" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setEtude2(e.target.value)}}/>
                <br/><br/>
                <span className="redMark13Cr">D.3 - </span><span className="greyMark13Cr"> Pour obtenir quel diplôme ?</span>
                <br/>
                <TextField id="filled-basicè" size="small" label="" variant="filled" style={{width: 300}}
                           onChange={(e) => {setEtude3(e.target.value)}}/>
                <br/><br/><br/>
                <CButton style={{backgroundColor: '#660000', color: "white", width: "300px"}}
                         size="lg"
                         disabled={etude1 === null || etude2 === "" || etude3 === ""}
                         onClick={() => {setPossibilityANON4("READY"); setVerifSaveEntries(true)}}>
                  Sauvegarder
                </CButton>
              </>
            }
          </CCol>
        </CRow>
      }

      <Dialog open={verifSaveYESEntries} maxWidth="sm" fullHight fullWidth>
        <DialogTitle id="customized-dialog-title">
          <CRow>
            <CCol md={11}>
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
            </CCol>
            <CCol md={1} className="colPaddingLeft">
              <CloseIcon className="float-end" style={{cursor: 'pointer'}} onClick={() => {setVerifSaveYESEntries(false)}}/>
            </CCol>
          </CRow>
          <hr/>
        </DialogTitle>
        <DialogContent>
          <center>
            <br/>
            Êtes-vous sûr(es) de vouloir Sauvegarder votre Choix ?.
            <br/><br/><br/>
            {
              !showLoaderSaveYESEntries &&
              <>
                  <button className="btn btn-sm btn-success"
                          onClick={handleYesSearchJob}>
                    Oui
                  </button>
              </>
            }
            {
              showLoaderSaveYESEntries &&
              <CSpinner color="success" style={{width: '3rem', height: '3rem'}}/>
            }
            &nbsp;&nbsp;
            {
              !showLoaderSaveYESEntries &&
              <button className="btn btn-sm btn-dark"
                      disabled={showLoaderSaveYESEntries}
                      onClick={() => {setVerifSaveYESEntries(false)}}>
                Non
              </button>
            }
          </center>
          <br/>
        </DialogContent>
      </Dialog>

      <Dialog open={verifSaveEntries} maxWidth="sm" fullHight fullWidth>
        <DialogTitle id="customized-dialog-title">
          <CRow>
            <CCol md={11}>
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
            </CCol>
            <CCol md={1} className="colPaddingLeft">
              <CloseIcon className="float-end" style={{cursor: 'pointer'}} onClick={() => {setVerifSaveEntries(false)}}/>
            </CCol>
          </CRow>
          <hr/>
        </DialogTitle>
        <DialogContent>
          <center>
            <br/>
            Êtes-vous sûr(es) de vouloir Confirmer Votre Choix ?.
            <br/><br/><br/>
            {
              !showLoaderSaveEntries &&
              <>
                {
                  possibilityANON1 === "READY" &&
                  <button className="btn btn-sm btn-success"
                          onClick={handleSavePromesse}>
                    Oui
                  </button>
                }
                {
                  possibilityANON2 === "READY" &&
                  <button className="btn btn-sm btn-success"
                          onClick={handleSaveContrat}>
                    Oui
                  </button>
                }
                {
                  possibilityANON3 === "READY" &&
                  <button className="btn btn-sm btn-success"
                          onClick={handleSaveProjet}>
                    Oui
                  </button>
                }
                {
                  possibilityANON4 === "READY" &&
                  <button className="btn btn-sm btn-success"
                          onClick={handleSaveEtude}>
                    Oui
                  </button>
                }
              </>
            }
            {
              showLoaderSaveEntries &&
              <CSpinner color="success" style={{width: '3rem', height: '3rem'}}/>
            }
            &nbsp;&nbsp;
            {
              !showLoaderSaveEntries &&
              <button className="btn btn-sm btn-dark"
                      disabled={showLoaderSaveEntries}
                      onClick={() => {setVerifSaveEntries(false)}}>
                Non
              </button>
            }
          </center>
          <br/>
        </DialogContent>
      </Dialog>

      <Dialog open={successSaveYESEntries} maxWidth="sm" fullHight fullWidth>
        <DialogTitle id="customized-dialog-title">
          <CRow>
            <CCol md={11}>
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
            </CCol>
            <CCol md={1} className="colPaddingLeft">
              <CloseIcon className="float-end" style={{cursor: 'pointer'}} onClick={() => {setSuccessSaveYESEntries(false)}}/>
            </CCol>
          </CRow>
          <hr/>
        </DialogTitle>
        <DialogContent>
          <center>
            <br/>
              <>
                Votre Choix a été sauvegardé avec succès.
              </>
            <br/><br/><br/>
            &nbsp;&nbsp;
            <button className="btn btn-sm btn-dark"
                    onClick={() => {setSuccessSaveYESEntries(false); window.location.href = "/uploadReport"}}>
              Ok
            </button>
          </center>
          <br/>
        </DialogContent>
      </Dialog>

      <Dialog open={successSaveEntries} maxWidth="sm" fullHight fullWidth>
        <DialogTitle id="customized-dialog-title">
          <CRow>
            <CCol md={11}>
              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
            </CCol>
            <CCol md={1} className="colPaddingLeft">
              <CloseIcon className="float-end" style={{cursor: 'pointer'}} onClick={() => {setSuccessSaveEntries(false)}}/>
            </CCol>
          </CRow>
          <hr/>
        </DialogTitle>
        <DialogContent>
          <center>
            <br/>
            <>
              Vos données ont été sauvegardées avec succès.
            </>
            <br/><br/><br/>
            &nbsp;&nbsp;
            <button className="btn btn-sm btn-dark"
                    onClick={() => {setSuccessSaveEntries(false); window.location.href = "/uploadReport"}}>
              Ok
            </button>
          </center>
          <br/>
        </DialogContent>
      </Dialog>
    </>
  )
}
export default Jalon
