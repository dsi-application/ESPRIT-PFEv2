// init 1
import React, {Component} from "react";
import { Link } from "react-router-dom";
import AcademicEncadrantService from "../../services/academicEncadrant.service";
import AuthService from "../../services/auth.service";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import {CButton, CCard, CCardBody, CCardHeader, CCol, CContainer, CDataTable, CRow, CTooltip} from "@coreui/react";

import Spinner from "react-bootstrap/Spinner";

import CIcon from "@coreui/icons-react";
import {freeSet} from '@coreui/icons';

import "../../css/style.css";
import TextField from '@material-ui/core/TextField';
import "react-datepicker/dist/react-datepicker.css";

import ReactTextTransition from "react-text-transition";
import store from "../../../store";
import {
    fetchActiveStudentTimelineStep,
    fetchConventionDetails,
    fetchDateDepotFichePFE,
    fetchDepotReportsDetails,
    fetchFichePFEDetails,
    fetchStudentMsg,
    //fetchCompanyForFichePFEHistoric,
    //fetchCompanySupervisorsForFichePFEHistoric,
    fetchTimelineSteps,
    fetchStudentFullName,
    fetchConventionsHistoric,
    fetchFichePFEsHistoric,
    fetchStudentId,
    fetchStudentExpert
} from "../../../redux/slices/MyStudentTBSSlice";

import Select from "react-select";
import makeAnimated from "react-select/animated";

// init2
const API_URL = process.env.REACT_APP_API_URL_MESP;
const animatedComponents = makeAnimated();
const API_URL_PC = process.env.REACT_APP_API_URL_PC;

const getBadgeEtatFiche = (etatFiche) => {
    switch (etatFiche) {
        case "PAS ENCORE":
            return "danger";
        case "DEPOSE":
            return "info";
        case "VALIDE":
            return "success";
        default:
            return "dark";
    }
};

const fields = [
    {
        key: 'studentId',
        label: 'Identifiant',
        _style: {width: '7%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentFullName',
        label: 'Prénom & Nom',
        _style: {width: '24%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentMail',
        label: 'E-Mail',
        _style: {width: '27%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentPhone',
        label: 'Numéro Tél',
        _style: {width: '10%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentOption',
        label: 'Option',
        _style: {width: '12%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentClasse',
        label: 'Classe',
        _style: {width: '12%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'action',
        label: 'Action',
        _style: {width: '5%'},
        sorter: false,
        filter: false
    }
];

const currentTeacher = AuthService.getCurrentTeacher();


export default class StudentProgressStatus extends Component {
    constructor(props) {
        super(props);

        // init3
        this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
        this.loadStudentsAffectedToAE = this.loadStudentsAffectedToAE.bind(this);
        this.loadStudentsAffectedToAEByDefaultYear = this.loadStudentsAffectedToAEByDefaultYear.bind(this);
        this.handleClosePopupShowStudentDetails = this.handleClosePopupShowStudentDetails.bind(this);
        this.handleClosePopupSaveDepotPFE = this.handleClosePopupSaveDepotPFE.bind(this);
        this.handleClosePopupValidateDepotPFE = this.handleClosePopupValidateDepotPFE.bind(this);
        this.handleClosePopupVerifyYesMarkIngTr = this.handleClosePopupVerifyYesMarkIngTr.bind(this);
        this.handleClosePopupVerifyNoMarkIngTr = this.handleClosePopupVerifyNoMarkIngTr.bind(this);
        this.handleTreatDepotPFE = this.handleTreatDepotPFE.bind(this);
        this.handleClosePopupTreatDepotPFE = this.handleClosePopupTreatDepotPFE.bind(this);
        this.handleChangeNotePE = this.handleChangeNotePE.bind(this);
        this.handleValidateFicheDepotPFE = this.handleValidateFicheDepotPFE.bind(this);
        this.handleChangeDateRDV = this.handleChangeDateRDV.bind(this);

        this.state = {
            listOfStudentsTrainedByPE: [],
            listOfAuthorizedStudentsToSTNByFilter: [],
            openPopupShowStudentDetails: false,
            openPopupTreatDepotPFE: false,
            openPopupSaveDepotPFE: false,
            openPopupValidateDepotPFE: false,
            selectedStudentId: "",
            selectedStudentFullName: "",
            selectedStudentMail: "",
            selectedStudentTel: "",
            selectedStudentDepartment: "",
            selectedStudentOption: "",
            selectedStudentSocieteLibelle: "",
            selectedStudentProjectTitle: "",
            selectedStudentClasse: "",
            selectedStudentJuryPresident: "",
            selectedStudentExpert: "",
            selectedStudentDateDebutStage: "",
            selectedStudentDateFinStage: "",
            showSpinnerSaveDepotPFE: false,
            showSpinnerValidateDepotPFE: false,
            etatTreat: "NO",
            emptyTreat: "INIT",
            showButtons: "INIT",
            loadList: true,
            notePE: null,
            noteNotCompleteSend: "",
            openPopupVerifyMarkIngTr: false,
            allSessionsLabel: [],
            selectedYear: '2022',
            loadStudentsCJByYear: true
        };
        // init4

        // console.log('------------------------> TREATMENT 1: ' + new Date() + " - " + this.state.loadList);

        let requestSessions = new XMLHttpRequest();
        requestSessions.open(
            "GET",
            API_URL_PC + "findAllSessions",
            false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestSessions.send(null);
        let resultSession = JSON.parse(requestSessions.responseText);
        // console.log('-------------------> >sars 1110: ' + result);

        for (let cls of resultSession) {
            // console.log('----sars 1110---------- unit cls: ' + cls);
            this.state.allSessionsLabel.push({
                value: cls,
                label: cls,
                color: "#00B8D9",
            });
        }


	    let requestAv = new XMLHttpRequest();
	    requestAv.open("GET",API_URL + "pwdESPTea/" + encodeURIComponent(currentTeacher.id) + "/" + encodeURIComponent(currentTeacher.idEns), false);
	    requestAv.send(null);
	    let pwdTea = requestAv.responseText;

	    let requestTkn = new XMLHttpRequest();
	    requestTkn.open("GET", API_URL + "validateJWT/" + currentTeacher.token, false);
	    requestTkn.send(null);
	    let tkBack = requestTkn.responseText;

	    let requestATkn = new XMLHttpRequest();
	    requestATkn.open("GET", API_URL + "validateJWT/" + currentTeacher.accessToken, false);
	    requestATkn.send(null);
	    let atkBack = requestATkn.responseText;


		if(currentTeacher !== null && pwdTea === currentTeacher.password && atkBack === 'YES' && tkBack === 'YES')
	    {
			this.loadStudentsAffectedToAEByDefaultYear();
	    }
		else
	    {
	      localStorage.clear();
	      sessionStorage.clear();
	      window.location.href = "/";
	    }

    }

    loadStudentsAffectedToAEByDefaultYear () {
    	AcademicEncadrantService.studentsTBSByAEAndYear(currentTeacher.id, this.state.selectedYear).then(
    	// AcademicEncadrantService.studentsTBSByAE(currentTeacher.id).then(
            (response) => {
                // console.log('-----------------2111-------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);

                let stuList = this.state.listOfStudentsTrainedByPE.slice();
                for (let stu of response.data) {
                    // console.log('------------------------> TREATMENT 2.1 : ', stu);
                    const studentObj = stu;
                    stuList.push(stu);
                }

                this.setState({
                    loadStudentsCJByYear: false,
                    listOfStudentsTrainedByPE: stuList
                });
                // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

            },
            (error) => {
            }
        );
    }

    // init5
    loadStudentsAffectedToAE = (selectedYearObject) => {

        // console.log('-------------1----------> 21.09.22: ' , this.state.listOfStudentsTrainedByPE);
        // console.log('-------------2.1----------> 21.09.22: ' , selectedYearObject.label);

        this.setState({
            loadStudentsCJByYear: true,
            selectedYear: selectedYearObject.label
        });

        // console.log('-------------2.2----------> 21.09.22: ' , this.state.loadStudentsCJByYear);
        // console.log('-------------2.3----------> 21.09.22: ' , this.state.selectedYear);

        // console.log('-------------2.4----------> 21.09.22: ' , currentTeacher.id);
        // console.log('-------------2.5----------> 21.09.22: ' , selectedYearObject.label);

        AcademicEncadrantService.studentsTBSByAEAndYear(currentTeacher.id, selectedYearObject.label).then(
            (response) => {

                this.setState({
                    listOfStudentsTrainedByPE: []
                });
                // console.log('-------------3----------->  21.09.22: ' + new Date() + " - " + this.state.loadStudentsCJByYear);

                let stuList = this.state.listOfStudentsTrainedByPE.slice();
                for (let stu of response.data) {
                    // console.log('------------------------> TREATMENT 2.1 : ', stu);
                    const studentObj = stu;
                    stuList.push(stu);
                }

                this.setState({
                    loadStudentsCJByYear: false,
                    listOfStudentsTrainedByPE: stuList
                });
                // console.log('--------------4----------> 21.09.22: ' + new Date() + " - " + this.state.loadStudentsCJByYear);
            },
            (error) => {
            }
        );

    };

    onChangeAuthorizedStudentsToSTNByFilter(filtredItems) {
        this.setState({listOfAuthorizedStudentsToSTNByFilter: []})

        let filteredIdentifiants = [];
        if (filtredItems.length !== this.state.listOfStudentsTrainedByPE.length) {
            for (let item of filtredItems) {
                filteredIdentifiants.push(item.studentId);
            }
            // console.log("Check Disponibility --excel------TextScroller.js-------------> THIS-2: ", filteredIdentifiants);

            this.setState({
                listOfAuthorizedStudentsToSTNByFilter: filteredIdentifiants,
                planifyKind: "PWFILTER",
            })
        }
    }

    handleChangeDateRDV(index, date) {
        // console.log('------------------------> DT RDV - a: ' + index);
        // console.log('------------------------> DT RDV - z: ',  date);


        if (index === 0) {
            this.setState({dateRDV1: date});
            // console.log('------------------------> DT RDV - 1: ' + this.state.dateRDV1);
        }

        if (index === 1) {
            this.setState({dateRDV2: date});
            // console.log('------------------------> DT RDV - 2: ' + this.state.dateRDV2);
        }

        if (index === 2) {
            this.setState({dateRDV3: date});
            // console.log('------------------------> DT RDV - 3: ' + this.state.dateRDV3);
        }

    }

    handleValidateFicheDepotPFE(e)
    {
        this.setState({
            openPopupVerifyMarkIngTr: true
        });
    }

    gotFormattedDate(bridgeSDt) {
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

        let formattedDate = day + "-" + month + "-" + bridgeSDt.getFullYear();
        return formattedDate;
    }

    handleChangeNotePE(e) {
        // console.log('-----------------------> NOTE: ' + e);
        this.setState({notePE: e});
    }

    handleClosePopupShowStudentDetails() {
        this.setState({openPopupShowStudentDetails: false});
    }

    handleClosePopupSaveDepotPFE() {
        this.setState({openPopupSaveDepotPFE: false});
    }

    handleClosePopupValidateDepotPFE() {
        this.setState({openPopupValidateDepotPFE: false});
    }

    handleClosePopupVerifyYesMarkIngTr() {
        this.setState({
            showSpinnerValidateDepotPFE: true,
            openPopupTreatDepotPFE: false
        });

        AcademicEncadrantService.giveMarkforStudentEngTrainingship(encodeURIComponent(currentTeacher.id), this.state.selectedStudentId, this.state.notePE).then(
            (response) => {

                let result = response.data;
                // console.log('---------------- RES: ', response.data)

                if (result === "NO-AE") {
                    this.setState({noteNotCompleteSend: "Votre E-Mail est inexistant ou avec format incorrecte."});
                }

                let idEt = this.state.selectedStudentId;
                let studentMarkSTr = this.state.notePE;
                // console.log('-------------15.11------------------> SARS A: ' + this.state.noteNotCompleteSend)

                this.setState({
                    listOfStudentsTrainedByPE: this.state.listOfStudentsTrainedByPE.map(el => (el.studentId === idEt ? {
                        ...el,
                        studentMarkSTr
                    } : el)),
                    showSpinnerValidateDepotPFE: false,
                    openPopupVerifyMarkIngTr: false,
                    openPopupValidateDepotPFE: true,
                    showButtons: "HIDE"
                });

            },
            (error) => {
            }
        );
    }

    handleClosePopupVerifyNoMarkIngTr() {
        this.setState({
            openPopupVerifyMarkIngTr: false,
            notePE: null
        });
    }

    // SARS
    handleTreatDepotPFE(idEt, studentFullName)
    {
        sessionStorage.removeItem("studentTBS");
        // console.log('-----------------1-----> 2111: ' + idEt + ' - ' + sessionStorage.getItem("studentTBS"));

        sessionStorage.setItem("studentTBS", idEt);

        // console.log('-----------------2-----> 2111: ' + idEt + ' - ' + sessionStorage.getItem("studentTBS"));

        store.dispatch(fetchStudentMsg());
        //store.dispatch(fetchConventionTraitee(idEt));
        store.dispatch(fetchDateDepotFichePFE(idEt));
        store.dispatch(fetchTimelineSteps(idEt));
        store.dispatch(fetchConventionDetails(idEt));
        store.dispatch(fetchFichePFEDetails(idEt));
        store.dispatch(fetchDepotReportsDetails(idEt));
        store.dispatch(fetchActiveStudentTimelineStep(idEt));
        store.dispatch(fetchStudentFullName(idEt));
        store.dispatch(fetchConventionsHistoric(idEt));
        store.dispatch(fetchFichePFEsHistoric(idEt));
        store.dispatch(fetchStudentId(idEt));

        store.dispatch(fetchStudentExpert(idEt));

        //store.dispatch(fetchCompanyForFichePFEHistoric(idEt));
        // store.dispatch(fetchCompanySupervisorsForFichePFEHistoric(idEt));

        this.setState({
            emptyTreat: "INIT"
        })

        this.setState({
            openPopupTreatDepotPFE: true,
            selectedStudentFullName: studentFullName,
            selectedStudentId: idEt
        });

        this.props.history.push("/myStudentTimeline");
    }

    handleClosePopupTreatDepotPFE() {
        this.setState({
            openPopupTreatDepotPFE: false,
        });
    }


    render() {
        const {
            listOfStudentsTrainedByPE,
            listOfAuthorizedStudentsToSTNByFilter,
            openPopupShowStudentDetails,
            openPopupTreatDepotPFE,
            openPopupSaveDepotPFE,
            openPopupValidateDepotPFE,
            selectedStudentFullName,
            selectedStudentMail,
            selectedStudentTel,
            selectedStudentDepartment,
            selectedStudentOption,
            selectedStudentSocieteLibelle,
            selectedStudentProjectTitle,
            selectedStudentClasse,
            selectedStudentDateDebutStage,
            selectedStudentDateFinStage,
            //selectedStudentDateDépôt,
            selectedStudentJuryPresident,
            selectedStudentExpert,
            statusKinds,
            showSpinnerSaveDepotPFE,
            showSpinnerValidateDepotPFE,
            //completeTreatDepotFichePFE,
            etatTreat,
            emptyTreat,
            showButtons,
            notePE,
            loadList,
            noteNotCompleteSend,
            openPopupVerifyMarkIngTr,
            selectedYear,
            loadStudentsCJByYear,
            allSessionsLabel
        } = this.state;
        // init6

        return (
            <>
                <CRow>
                    <CCol md="4"/>
                    <CCol md="4">
                        <br/>
                        <p className="greyMarkForSelectComp">Merci de choisir une Année pour consulter la résultante</p>
                        <Select  placeholder="Please Select an Academic Year"
                                 defaultValue={{value: '2022', label: '2022', color: "#00B8D9"}}
                                 value={allSessionsLabel.value}
                                 components={animatedComponents}
                                 options={allSessionsLabel}
                                 onChange={this.loadStudentsAffectedToAE}/>
                    </CCol>
                    <CCol md="4"/>
                </CRow>
                <br/>
                {
                    selectedYear === 'EMPTY' &&
                    <>
                        <br/><br/><br/>
                    </>
                }
                {
                    selectedYear !== 'EMPTY' &&
                    <>
                        {
                            loadStudentsCJByYear === true ?
                                <center>
                                    <br/><br/><br/><br/><br/>
                                    <span className="waitIcon"/>
                                    <br/><br/><br/><br/><br/>
                                </center>
                                :
                                <>
                                    <br/>
                                    <CRow>
                                        <CCol>
                                            <CCard>
                                                <CCardHeader>
                                                    <CContainer>
                                                        <CRow>
                                                            <CCol md="10" class="bg-success py-2">
                                                        <span style={{
                                                            color: "#cc0000",
                                                            fontSize: "14px",
                                                            fontWeight: "bold"
                                                        }}>
                                                            Liste des Étudiants pour l'Année Universitaire &nbsp;&nbsp;<span className="clignoteRed">{selectedYear} - {Number(selectedYear)+1}</span>
                                                        </span>
                                                            </CCol>
                                                        </CRow>
                                                    </CContainer>
                                                </CCardHeader>
                                                <CCardBody>
                                                    <CDataTable items={listOfStudentsTrainedByPE}
                                                                fields={fields}
                                                                tableFilter
                                                                columnFilter
                                                                itemsPerPageSelect
                                                                hover
                                                                sorter
                                                                striped
                                                                bordered
                                                                size="sm"
                                                                itemsPerPage={10}
                                                                pagination
                                                                noItemsContent='azerty'
                                                                onFilteredItemsChange={(event) => {
                                                                    this.onChangeAuthorizedStudentsToSTNByFilter(event)
                                                                }}
                                                                scopedSlots={{
                                                                    action: (item) => (
                                                                        <td>
                                                                            <center>
                                                                                <CButton shape="pill"
                                                                                         color="dark"
                                                                                         size="sm"
                                                                                         aria-expanded="true"
                                                                                         onClick={() => this.handleTreatDepotPFE(item.studentId, item.studentFullName)}>
                                                                                    <CTooltip content="Consulter Timeline" placement="top">
                                                                                        <CIcon content={freeSet.cilMagnifyingGlass}/>
                                                                                    </CTooltip>
                                                                                </CButton>
                                                                            </center>
                                                                        </td>
                                                                    ),
                                                                }}
                                                    />
                                                </CCardBody>
                                            </CCard>
                                        </CCol>
                                    </CRow>

                                    <Dialog fullHight fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupShowStudentDetails}
                                            onClose={this.handleClosePopupShowStudentDetails}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">

                                            <CRow>
                                                <CCol md="4">
                                            <span className="myModalTitleMarginTop">
                                                Contact de l'Étudiant
                                            </span>
                                                </CCol>
                                                <CCol md="8" className="colPadding">
                                                    <div className="myDivMarginTop">
                                                <span className="myModalSubTitle">
                                                  <ReactTextTransition text={selectedStudentFullName}
                                                                       springConfig={{tension: 10, friction: 10}}/>
                                                </span>
                                                    </div>
                                                </CCol>
                                            </CRow>


                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <CRow>
                                                <CCol xs="3">
                            <span className="myModalField">
                              Num. Téléphone:
                            </span>
                                                </CCol>
                                                <CCol xs="9">
                                                    {selectedStudentTel}
                                                </CCol>
                                            </CRow>

                                            <CRow>
                                                <CCol xs="3">
                            <span className="myModalField">
                              E-Mail:
                            </span>
                                                </CCol>
                                                <CCol xs="9">
                                                    {selectedStudentMail}
                                                </CCol>
                                            </CRow>

                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            <Button onClick={this.handleClosePopupShowStudentDetails} color="primary">
                                                Exit
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight
                                            fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupTreatDepotPFE}
                                            onClose={this.handleClosePopupTreatDepotPFE}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">

                                            <CRow>
                                                <CCol md="5">
                                            <span className="myModalTitleMarginTop">
                                                Saisie Note Stage Ingénieur
                                            </span>
                                                </CCol>
                                                <CCol md="7" className="colPadding">
                                                    <div className="myDivMarginTop">
                                                <span className="myModalSubTitle">
                                                  <ReactTextTransition text={selectedStudentFullName}
                                                                       springConfig={{tension: 10, friction: 10}}/>
                                                </span>
                                                    </div>
                                                </CCol>
                                            </CRow>

                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Vous êtes invités à saisir une Note comprise entre 0 et 20
                                                <CRow>
                                                    <CCol xs="3"/>
                                                    <CCol xs="6">
                                                        <TextField id="filled-number"
                                                                   label="Note Encadrant Académique ( ... / 20)"
                                                                   type="number"
                                                                   fullWidth
                                                                   margin="normal"
                                                                   variant="filled"
                                                                   inputProps={{
                                                                       shrink: true,
                                                                       min: "0",
                                                                       max: "20",
                                                                       step: "0.25"
                                                                   }}
                                                                   value={Number(this.state.notePE)}
                                                                   disabled={etatTreat === "02"}
                                                                   onChange={(e) => {
                                                                       if (e.target.value <= 20) this.setState({notePE: e.target.value});
                                                                       if (e.target.value > 20) this.setState({notePE: 20});
                                                                   }}/>
                                                    </CCol>
                                                    <CCol xs="3"/>
                                                </CRow>
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                notePE !== null &&
                                                <CButton color="primary"
                                                         onClick={() => this.handleValidateFicheDepotPFE()}>
                                                    Valider
                                                </CButton>
                                            }
                                            &nbsp;&nbsp;
                                            <CButton variant="outline"
                                                     color="dark"
                                                     onClick={this.handleClosePopupTreatDepotPFE}>
                                                Exit
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupValidateDepotPFE}
                                            onClose={this.handleClosePopupValidateDepotPFE}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Vous avez saisi la Note Stage Ingénieur pour l'Étudiant &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                            {selectedStudentFullName}
                                        </span>
                                                {
                                                    noteNotCompleteSend !== "" &&
                                                    <>
                                                        <br/><br/>
                                                        <span className="errorS1">Erreur Envoi: </span>&nbsp;
                                                        <span className="errorS2">{noteNotCompleteSend}</span>
                                                    </>
                                                }
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            <Button onClick={this.handleClosePopupValidateDepotPFE} color="primary">
                                                Exit
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupVerifyMarkIngTr}
                                            onClose={this.handleClosePopupVerifyYesMarkIngTr}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Êtes-vous sûr de vouloir attribuer <br/>
                                                la note &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                            {notePE}
                                        </span>
                                                &nbsp; à l'Étudiant(e) &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                            {selectedStudentFullName}
                                        </span>
                                                &nbsp;?.

                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                !showSpinnerValidateDepotPFE &&
                                                <Button onClick={this.handleClosePopupVerifyYesMarkIngTr} color="primary">
                                                    Oui
                                                </Button>
                                            }

                                            {
                                                showSpinnerValidateDepotPFE &&
                                                <Spinner animation="grow" variant="primary"/>
                                            }
                                            <Button onClick={this.handleClosePopupVerifyNoMarkIngTr}
                                                    disabled={showSpinnerValidateDepotPFE}
                                                    color="primary">
                                                Non
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                </>
                        }
                    </>
                }
            </>
        );
    }
}
