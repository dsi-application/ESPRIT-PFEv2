// init 1
import React, {Component} from "react";

import AcademicEncadrantService from "../../services/academicEncadrant.service";
import AuthService from "../../services/auth.service";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import axios from "axios";
import {CButton, CCard, CCardBody, CCardHeader, CCol, CContainer, CDataTable, CRow, CTooltip} from "@coreui/react";

import Spinner from "react-bootstrap/Spinner";

import CIcon from "@coreui/icons-react";
import {freeSet} from '@coreui/icons';

import Select from "react-select";
import makeAnimated from "react-select/animated";

import "../../css/style.css";
import TextField from '@material-ui/core/TextField';
import "react-datepicker/dist/react-datepicker.css";

import ReactTextTransition from "react-text-transition";
import PedagogicalCoordinatorService from "../../services/pedagogicalCoordinator.service";


// init2
const API_URL = process.env.REACT_APP_API_URL_MESP;
const API_URL_PC = process.env.REACT_APP_API_URL_PC;

const animatedComponents = makeAnimated();

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
        _style: {width: '16%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentMail',
        label: 'E-Mail',
        _style: {width: '25%'},
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
        _style: {width: '6%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentClasse',
        label: 'Classe',
        _style: {width: '9%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'studentMarkSTr',
        label: 'Note Stage Ingénieur',
        _style: {width: '9%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'action',
        label: 'Action',
        _style: {width: '17%'},
        sorter: false,
        filter: false
    }
];

const currentTeacher = AuthService.getCurrentTeacher();


export default class MarkForEngineeringTrainingship extends Component {
    constructor(props) {
        super(props);

        // init3
        this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
        this.loadStudentsAffectedToAE = this.loadStudentsAffectedToAE.bind(this);
        this.onChangeStudentsByYear = this.onChangeStudentsByYear.bind(this);
        this.handleClosePopupShowStudentDetails = this.handleClosePopupShowStudentDetails.bind(this);
        this.handleClosePopupNotifyOkGiveMarkToStudent = this.handleClosePopupNotifyOkGiveMarkToStudent.bind(this);
        this.handleClosePopupNotifyOkModifyMarkOfStudent = this.handleClosePopupNotifyOkModifyMarkOfStudent.bind(this);
        this.handleClosePopupVerifyGiveMarkToStudent = this.handleClosePopupVerifyGiveMarkToStudent.bind(this);
        this.handleClosePopupVerifyModifyMarkOfStudent = this.handleClosePopupVerifyModifyMarkOfStudent.bind(this);
        this.handleClosePopupVerifyNoMarkIngTr = this.handleClosePopupVerifyNoMarkIngTr.bind(this);
        this.handleGiveMarkToStudent = this.handleGiveMarkToStudent.bind(this);
        this.handleDownloadJournalStageING = this.handleDownloadJournalStageING.bind(this);
        this.handleDownloadAttestationStageING = this.handleDownloadAttestationStageING.bind(this);
        this.handleDownloadRapportStageING = this.handleDownloadRapportStageING.bind(this);
        this.handleModifyMarkOfStudent = this.handleModifyMarkOfStudent.bind(this);
        this.handleClosePopupGiveMarkToStudent = this.handleClosePopupGiveMarkToStudent.bind(this);
        this.handleClosePopupModifyMarkToStudent = this.handleClosePopupModifyMarkToStudent.bind(this);
        this.handleChangeNoteAE = this.handleChangeNoteAE.bind(this);
        this.handleValidateMark = this.handleValidateMark.bind(this);
        this.handleClosePopupVerifyNoModifMarkIngTr = this.handleClosePopupVerifyNoModifMarkIngTr.bind(this);
        this.handleRefuseDepot = this.handleRefuseDepot.bind(this);
        this.handleOpenPopupValidateRefusDepot = this.handleOpenPopupValidateRefusDepot.bind(this);
        this.handleOpenPopupAcceptDemandeModifyDepot = this.handleOpenPopupAcceptDemandeModifyDepot.bind(this);
        this.handleClosePopupAcceptDemandeModifyDepot = this.handleClosePopupAcceptDemandeModifyDepot.bind(this);
        this.handleClosePopupValidateRefusDepot = this.handleClosePopupValidateRefusDepot.bind(this);
        this.handleClosePopupSuccessRefusDepot = this.handleClosePopupSuccessRefusDepot.bind(this);
        this.handleOpenPopupMotifRefusDepot = this.handleOpenPopupMotifRefusDepot.bind(this);
        this.handleClosePopupMotifRefusDepot = this.handleClosePopupMotifRefusDepot.bind(this);
        this.onChangeMotifRefusDepot = this.onChangeMotifRefusDepot.bind(this);
        this.handleAcceptDemandeModifyDepot = this.handleAcceptDemandeModifyDepot.bind(this);
        this.handleClosePopupOkForAccepteDemandeModifDepot = this.handleClosePopupOkForAccepteDemandeModifDepot.bind(this);
        this.handleClosePopupVerifyAcceptDemandeModif = this.handleClosePopupVerifyAcceptDemandeModif.bind(this);
        this.handleOpenPopupVerifyAcceptDemandeModif = this.handleOpenPopupVerifyAcceptDemandeModif.bind(this);



        this.state = {
            listOfStudentsCJTrainedByPE: [],
            listOfAuthorizedStudentsCJToSTNByFilter: [],
            openPopupShowStudentDetails: false,
            openPopupGiveMarkToStudent: false,
            openPopupModifyMarkOfStudent: false,
            openPopupNotifyOkGiveMarkToStudent: false,
            openPopupNotifyOkModifyMarkOfStudent: false,
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
            showSpinnerGiveMarkToStudent: false,
            showSpinnerModifyMarkToStudent: false,
            notePE: null,
            firstNotePE: null,
            noteNotCompleteSend: "",
            openPopupVerifyMarkIngTr: false,
            openPopupVerifyModifMarkIngTr: false,
            selectedYear: '2022',
            loadStudentsCJByYear: true,
            allSessionsLabel: []
        };
        // init4

        // console.log('------------------------> TREATMENT 1: ' + new Date() + " - " + this.state.loadStudentsCJByYear);

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
            this.loadStudentsAffectedToAE();
        }
        else
        {
            localStorage.clear();
            sessionStorage.clear();
            window.location.href = "/";
        }


    }

    loadStudentsAffectedToAE() {
        AcademicEncadrantService.studentsCJByAEAndYear(currentTeacher.id, this.state.selectedYear).then(
            (response) => {
                // console.log('------------------------>  2: ' + new Date() + " - " + this.state.loadList);

                let stuList = this.state.listOfStudentsCJTrainedByPE.slice();
                for (let stu of response.data) {
                    // // console.log('------------------------> TREATMENT 2.1 : ', stu);
                    const studentObj = stu;
                    stuList.push(stu);
                }

                this.setState({
                    loadStudentsCJByYear: false,
                    listOfStudentsCJTrainedByPE: stuList
                });
                // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

            },
            (error) => {
            }
        );
    }

    onChangeStudentsByYear = (selectedYearObject) => {

        // console.log('-------------1----------> 21.09.22: ' , this.state.listOfStudentsCJTrainedByPE);
        // console.log('-------------2.1----------> 21.09.22: ' , selectedYearObject.label);

        this.setState({
            loadStudentsCJByYear: true,
            selectedYear: selectedYearObject.label
        });

        // console.log('-------------2.2----------> 21.09.22: ' , this.state.loadStudentsCJByYear);
        // console.log('-------------2.3----------> 21.09.22: ' , this.state.selectedYear);

        // console.log('-------------2.4----------> 21.09.22: ' , currentTeacher.id);
        // console.log('-------------2.5----------> 21.09.22: ' , selectedYearObject.label);

        AcademicEncadrantService.studentsCJByAEAndYear(currentTeacher.id, selectedYearObject.label).then(
            (response) => {

                this.setState({
                    listOfStudentsCJTrainedByPE: []
                });
                // console.log('-------------3----------->  21.09.22: ' + new Date() + " - " + this.state.loadStudentsCJByYear);

                let stuList = this.state.listOfStudentsCJTrainedByPE.slice();
                for (let stu of response.data) {
                    // // console.log('------------------------> TREATMENT 2.1 : ', stu);
                    const studentObj = stu;
                    stuList.push(stu);
                }

                this.setState({
                    loadStudentsCJByYear: false,
                    listOfStudentsCJTrainedByPE: stuList
                });
                // console.log('--------------4----------> 21.09.22: ' + new Date() + " - " + this.state.loadStudentsCJByYear);
            },
            (error) => {
            }
        );

    };

    onChangeAuthorizedStudentsToSTNByFilter(filtredItems) {
        this.setState({listOfAuthorizedStudentsCJToSTNByFilter: []})

        let filteredIdentifiants = [];
        if (filtredItems.length !== this.state.listOfStudentsCJTrainedByPE.length) {
            for (let item of filtredItems) {
                filteredIdentifiants.push(item.studentId);
            }
            // console.log("Check Disponibility --excel------TextScroller.js-------------> THIS-2: ", filteredIdentifiants);

            this.setState({
                listOfAuthorizedStudentsCJToSTNByFilter: filteredIdentifiants,
                planifyKind: "PWFILTER",
            })
        }
    }

    handleValidateMark(e)
    {
        this.setState({
            openPopupVerifyMarkIngTr: true
        });
    }

    handleValidateFicheDepotPFEModify(e)
    {
        this.setState({
            openPopupVerifyModifMarkIngTr: true
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

    handleChangeNoteAE(e) {
        // console.log('-----------------------> NOTE: ' + e);
        this.setState({notePE: e});
    }

    handleClosePopupShowStudentDetails() {
        this.setState({openPopupShowStudentDetails: false});
    }

    handleClosePopupNotifyOkGiveMarkToStudent() {
        this.setState({openPopupNotifyOkGiveMarkToStudent: false});
    }

    handleClosePopupNotifyOkModifyMarkOfStudent() {
        this.setState({openPopupNotifyOkModifyMarkOfStudent: false});
    }

    handleOpenPopupMotifRefusDepot() {
        this.setState({
            openPopupMotifDepotEngTr: true,
            openPopupVerifyValidDepotEngTr: false
        });
    }

    handleOpenPopupVerifyAcceptDemandeModif(idEt, studentFullName) {
        this.setState({
            openPopupVerifyAcceptValidDepotEngTr: true,
            selectedStudentId: idEt,
            selectedStudentFullName: studentFullName
            //openPopupVerifyValidDepotEngTr: false
        });
    }

    handleClosePopupMotifRefusDepot()
    {
        this.setState({
            openPopupMotifDepotEngTr: false,
            openPopupVerifyValidDepotEngTr: true
        });
    }

    handleClosePopupVerifyAcceptDemandeModif() {
        this.setState({
            openPopupVerifyAcceptValidDepotEngTr: false,
        });
    }

    handleClosePopupVerifyGiveMarkToStudent() {
        this.setState({
            showSpinnerGiveMarkToStudent: true,
            openPopupGiveMarkToStudent: false
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
                let etatDepotStageING = "04";

                // console.log('-------------15.11------------------> SARS A: ' + this.state.noteNotCompleteSend)

                this.setState({
                    listOfStudentsCJTrainedByPE: this.state.listOfStudentsCJTrainedByPE.map(el => (el.studentId === idEt ? {
                        ...el,
                        studentMarkSTr,
                        etatDepotStageING
                    } : el)),
                    showSpinnerGiveMarkToStudent: false,
                    openPopupVerifyMarkIngTr: false,
                    openPopupNotifyOkGiveMarkToStudent: true
                });

            },
            (error) => {
            }
        );
    }

    handleClosePopupVerifyModifyMarkOfStudent() {
        this.setState({
            showSpinnerModifyMarkToStudent: true,
            openPopupModifyMarkOfStudent: false
        });

        // console.log('---------------> currentTeacher: ' + currentTeacher.id);

        AcademicEncadrantService.modifyMarkforStudentEngTrainingship(encodeURIComponent(currentTeacher.id), this.state.selectedStudentId, this.state.notePE).then(
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
                    listOfStudentsCJTrainedByPE: this.state.listOfStudentsCJTrainedByPE.map(el => (el.studentId === idEt ? {
                        ...el,
                        studentMarkSTr
                    } : el)),
                    showSpinnerModifyMarkToStudent: false,
                    openPopupVerifyModifMarkIngTr: false,
                    openPopupNotifyOkModifyMarkOfStudent: true
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

    handleClosePopupVerifyNoModifMarkIngTr() {
        this.setState({
            openPopupVerifyModifMarkIngTr: false,
            notePE: null
        });
    }

    handleGiveMarkToStudent(idEt, studentFullName, markStudentForST)
    {
        this.setState({
            openPopupGiveMarkToStudent: true,
            selectedStudentFullName: studentFullName,
            notePE: markStudentForST,
            selectedStudentId: idEt
        });
    }

    handleOpenPopupValidateRefusDepot(idEt, studentFullName) {
        this.setState({
            openPopupVerifyValidDepotEngTr: true,
            selectedStudentId: idEt,
            selectedStudentFullName: studentFullName
        });
    }

    handleOpenPopupAcceptDemandeModifyDepot(idEt, studentFullName) {
        this.setState({
            openPopupVerifyAcceptModifDepotEngTr: true,
            selectedStudentId: idEt,
            selectedStudentFullName: studentFullName
        });
    }

    handleClosePopupAcceptDemandeModifyDepot()
    {
        this.setState({
            openPopupVerifyAcceptModifDepotEngTr: false,
        });
    }

    handleClosePopupValidateRefusDepot()
    {
        this.setState({
            openPopupVerifyValidDepotEngTr: false,
        });
    }

    handleClosePopupSuccessRefusDepot()
    {
        this.setState({
            openPopupSuccessRefuseDepot: false
        });
    }

    handleRefuseDepot()
    {
        this.setState({showSpinnerRefusDepot: true});
        // console.log('-----1-----------HI: ' , this.state.listOfStudentsCJTrainedByPE);

        AcademicEncadrantService.refuseDepotStageIngenieur(this.state.selectedStudentId, encodeURIComponent(this.state.justifMotifRefus)).then(
            (response) => {

                let idEt = this.state.selectedStudentId;
                let pathJournalStageINGFile = null;
                let pathAttestationStageINGFile = null;
                let pathRapportStageINGFile = null;
                let etatDepotStageING = "05";

                this.setState({
                    showSpinnerRefusDepot: false,
                    openPopupVerifyValidDepotEngTr: false,
                    openPopupSuccessRefuseDepot: true,
                    openPopupMotifDepotEngTr: false,
                    listOfStudentsCJTrainedByPE: this.state.listOfStudentsCJTrainedByPE.map(el => (el.studentId === idEt ? {
                        ...el,
                        pathJournalStageINGFile, pathAttestationStageINGFile, pathRapportStageINGFile, etatDepotStageING
                    } : el)),
                });

                // console.log('-----2-----------HI: ' , this.state.listOfStudentsCJTrainedByPE);
                // , pathAttestationStageINGFile, pathRapportStageINGFile console.log('--------------4----------> 21.09.22: ' + new Date());
            },
            (error) => {
            }
        );
    }

    handleDownloadJournalStageING(pathJournalStageING)
    {
        let encodedURL = encodeURIComponent(encodeURIComponent(pathJournalStageING));
        axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
            .then((response) => {
                //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];

                // console.log('------------------> LOL 1: ' + p);
                // console.log('------------------> LOL 2: ' + response);
                // console.log('------------------> LOL 3: ' + response.data);

                const file = new Blob([response.data], { type: "application/pdf" });
                let url = window.URL.createObjectURL(file);

                // let a = document.createElement("a");
                // a.href = url;
                // a.download = pathJournalStageING.substring(pathJournalStageING.lastIndexOf("/") + 1);

                if(pathJournalStageING.includes('.pdf'))
                {
                    window.open(url);
                }

                // a.click();
            });
    }

    handleDownloadAttestationStageING(pathAttestationStageING)
    {
        let encodedURL = encodeURIComponent(encodeURIComponent(pathAttestationStageING));
        axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
            .then((response) => {
                //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];

                // console.log('------------------> LOL 1: ' + p);
                // console.log('------------------> LOL 2: ' + response);
                // console.log('------------------> LOL 3: ' + response.data);

                const file = new Blob([response.data], { type: "application/pdf" });
                let url = window.URL.createObjectURL(file);

                // let a = document.createElement("a");
                // a.href = url;
                // a.download = pathAttestationStageING.substring(pathAttestationStageING.lastIndexOf("/") + 1);

                if(pathAttestationStageING.includes('.pdf'))
                {
                    window.open(url);
                }

                // a.click();
            });
    }

    handleDownloadRapportStageING(pathRapportStageING)
    {
        let encodedURL = encodeURIComponent(encodeURIComponent(pathRapportStageING));
        axios.get(`${process.env.REACT_APP_API_URL_STU}` + "downloadMyPDF/" + encodedURL, { responseType: "blob" })
            .then((response) => {
                //const filename =  response.headers.get('Content-Disposition').split('filename=')[1];

                // console.log('------------------> LOL 1: ' + p);
                // console.log('------------------> LOL 2: ' + response);
                // console.log('------------------> LOL 3: ' + response.data);

                const file = new Blob([response.data], { type: "application/pdf" });
                let url = window.URL.createObjectURL(file);

                // let a = document.createElement("a");
                // a.href = url;
                // a.download = pathRapportStageING.substring(pathRapportStageING.lastIndexOf("/") + 1);

                if(pathRapportStageING.includes('.pdf'))
                {
                    window.open(url);
                }

                // a.click();
            });
    }

    handleModifyMarkOfStudent(idEt, studentFullName, markStudentForST)
    {
        this.setState({
            firstNotePE: markStudentForST,
            openPopupModifyMarkOfStudent: true,
            selectedStudentFullName: studentFullName,
            notePE: markStudentForST,
            selectedStudentId: idEt
        });
    }

    handleClosePopupGiveMarkToStudent() {
        this.setState({
            openPopupGiveMarkToStudent: false,
        });
    }

    handleClosePopupModifyMarkToStudent() {
        this.setState({
            openPopupModifyMarkOfStudent: false,
        });
    }

    handleAcceptDemandeModifyDepot() {

        // console.log('---------------- PIKO: ')
        this.setState({
            showSpinnerAcceptDemandeModifDepot: true,
            // openPopupVerifyAcceptModifDepotEngTr: false
        });

        AcademicEncadrantService.acceptDemandeModifDepotStageIngenieur(this.state.selectedStudentId, this.state.selectedYear).then(
            (response) => {
                // timeout(8000);

                let result = response.data;
                // console.log('---------------- RES: ', response.data)

                let idEt = this.state.selectedStudentId;
                let studentMarkSTr = this.state.notePE;
                let etatDepotStageING = response.data;
                // console.log('-------------15.11------------------> SARS A: ' + this.state.noteNotCompleteSend)

                this.setState({
                    listOfStudentsCJTrainedByPE: this.state.listOfStudentsCJTrainedByPE.map(el => (el.studentId === idEt ? {
                        ...el,
                        etatDepotStageING
                    } : el)),
                    showSpinnerAcceptDemandeModifDepot: false,
                    openPopupVerifyAcceptValidDepotEngTr: false,
                    openPopupOKAcceptDemandeModif: true
                });

            },
            (error) => {
            }
        );
    }

    handleClosePopupOkForAccepteDemandeModifDepot() {
        this.setState({
            openPopupOKAcceptDemandeModif: false
        });
    }

    onChangeMotifRefusDepot(e) {
        this.setState({justifMotifRefus: e.target.value});
    }

    render() {
        const {
            listOfStudentsCJTrainedByPE,
            listOfAuthorizedStudentsCJToSTNByFilter,
            openPopupShowStudentDetails,
            openPopupGiveMarkToStudent,
            openPopupModifyMarkOfStudent,
            openPopupNotifyOkGiveMarkToStudent,
            openPopupNotifyOkModifyMarkOfStudent,
            selectedStudentFullName,
            selectedStudentMail,
            selectedStudentTel,
            showSpinnerGiveMarkToStudent,
            showSpinnerModifyMarkToStudent,
            notePE,
            noteNotCompleteSend,
            openPopupVerifyMarkIngTr,
            openPopupVerifyModifMarkIngTr,
            firstNotePE,
            selectedYear,
            loadStudentsCJByYear,
            allSessionsLabel,
            openPopupVerifyValidDepotEngTr,
            showSpinnerRefusDepot,
            openPopupSuccessRefuseDepot,
            openPopupMotifDepotEngTr,
            justifMotifRefus,
            openPopupVerifyAcceptModifDepotEngTr,
            openPopupOKAcceptDemandeModif,
            showSpinnerAcceptDemandeModifDepot,
            openPopupVerifyAcceptValidDepotEngTr
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
                                 onChange={this.onChangeStudentsByYear}/>
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
                                                    <CDataTable items={listOfStudentsCJTrainedByPE}
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
                                                                                {
                                                                                    (item.etatDepotStageING === "02" || item.etatDepotStageING === "04") && item.pathJournalStageINGFile !== null &&
                                                                                    <>
                                                                                        <CButton shape="pill"
                                                                                                 color="dark"
                                                                                                 size="sm"
                                                                                                 aria-expanded="true"
                                                                                                 onClick={() => this.handleDownloadJournalStageING(item.pathJournalStageINGFile)}>
                                                                                            <CTooltip content="Télécharger Journal Stage Ingénieur" placement="top">
                                                                                                <CIcon content={freeSet.cilCloudDownload}/>
                                                                                            </CTooltip>
                                                                                        </CButton>
                                                                                        &nbsp;
                                                                                    </>
                                                                                }

                                                                                {
                                                                                    (item.etatDepotStageING === "02" || item.etatDepotStageING === "04") && item.pathAttestationStageINGFile !== null &&
                                                                                    <>
                                                                                        <CButton shape="pill"
                                                                                                 color="secondary"
                                                                                                 size="sm"
                                                                                                 aria-expanded="true"
                                                                                                 onClick={() => this.handleDownloadAttestationStageING(item.pathAttestationStageINGFile)}>

                                                                                            <CTooltip
                                                                                                content="Télécharger Attestation Stage Ingénieur"
                                                                                                placement="top">
                                                                                                <CIcon
                                                                                                    content={freeSet.cilCloudDownload}/>
                                                                                            </CTooltip>
                                                                                        </CButton>
                                                                                        &nbsp;
                                                                                    </>
                                                                                }

                                                                                {
                                                                                    (item.etatDepotStageING === "02" || item.etatDepotStageING === "04") && item.pathRapportStageINGFile !== null &&
                                                                                    <>
                                                                                        <CButton shape="pill"
                                                                                                 color="light"
                                                                                                 size="sm"
                                                                                                 aria-expanded="true"
                                                                                                 onClick={() => this.handleDownloadRapportStageING(item.pathRapportStageINGFile)}>

                                                                                            <CTooltip
                                                                                                content="Télécharger Rapport Stage Ingénieur"
                                                                                                placement="top">
                                                                                                <CIcon
                                                                                                    content={freeSet.cilCloudDownload}/>
                                                                                            </CTooltip>
                                                                                        </CButton>
                                                                                        &nbsp;
                                                                                    </>
                                                                                }

                                                                                {
                                                                                    item.etatDepotStageING === "02" &&
                                                                                    <>
                                                                                        <CButton shape="pill"
                                                                                                 color="danger"
                                                                                                 size="sm"
                                                                                                 aria-expanded="true"
                                                                                                 onClick={() => this.handleOpenPopupValidateRefusDepot(item.studentId, item.studentFullName)}>
                                                                                            <CTooltip content="Refuser Dépôt" placement="top">
                                                                                                <CIcon content={freeSet.cilX}/>
                                                                                            </CTooltip>
                                                                                        </CButton>
                                                                                        &nbsp;
                                                                                    </>
                                                                                }

                                                                                {
                                                                                    item.etatDepotStageING === "03" &&
                                                                                    <>
                                                                                        <CButton shape="pill"
                                                                                                 color="primary"
                                                                                                 size="sm"
                                                                                                 aria-expanded="true"
                                                                                                 onClick={() => this.handleOpenPopupVerifyAcceptDemandeModif(item.studentId, item.studentFullName)}>

                                                                                            <CTooltip content="Accepter Demande Mise À Jour Dépôt" placement="top">
                                                                                                <CIcon content={freeSet.cilCheckAlt}/>
                                                                                            </CTooltip>
                                                                                        </CButton>
                                                                                        &nbsp;
                                                                                    </>
                                                                                }

                                                                                {
                                                                                    (
                                                                                        (item.pathJournalStageINGFile === null && item.pathAttestationStageINGFile === null && item.pathRapportStageINGFile === null )
                                                                                        ||
                                                                                        item.etatDepotStageING === "01"
                                                                                        ||
                                                                                        item.etatDepotStageING === "06"
                                                                                    )

                                                                                    &&
                                                                                    <CButton shape="pill"
                                                                                             color="warning"
                                                                                             size="sm"
                                                                                             disabled="true"
                                                                                             aria-expanded="true">
                                                                                        <CTooltip
                                                                                            content="Cet Étudiant n'a pas encore déposé ses Documents"
                                                                                            placement="top">
                                                                                            <CIcon content={freeSet.cilMinus}/>
                                                                                        </CTooltip>
                                                                                    </CButton>
                                                                                }

                                                                                {
                                                                                    item.studentMarkSTr !== null &&
                                                                                    <CButton shape="pill"
                                                                                             color="success"
                                                                                             size="sm"
                                                                                             aria-expanded="true"
                                                                                             onClick={() => this.handleModifyMarkOfStudent(item.studentId, item.studentFullName, item.studentMarkSTr)}>

                                                                                        <CTooltip content="Note déjà saisie à l'Étudiant : Cliquer pour Modifier" placement="top">
                                                                                            <CIcon content={freeSet.cilCheckAlt}/>
                                                                                        </CTooltip>
                                                                                    </CButton>
                                                                                }
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
                                            open={openPopupGiveMarkToStudent}
                                            onClose={this.handleClosePopupGiveMarkToStudent}
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
                                                Si vous acceptez le Dépôt de votre Étudiant <span className="text-primary">{selectedStudentFullName}</span>,
                                                <br/>
                                                Vous êtes invités à saisir une Note comprise entre 0 et 20
                                                <br/>
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
                                                         onClick={() => this.handleValidateMark()}>
                                                    Valider
                                                </CButton>
                                            }
                                            &nbsp;&nbsp;
                                            <CButton variant="outline"
                                                     color="dark"
                                                     onClick={this.handleClosePopupGiveMarkToStudent}>
                                                Exit
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight
                                            fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupModifyMarkOfStudent}
                                            onClose={this.handleClosePopupModifyMarkToStudent}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">

                                            <CRow>
                                                <CCol md="6">
                                            <span className="myModalTitleMarginTop">
                                                Modification Note Stage Ingénieur
                                            </span>
                                                </CCol>
                                                <CCol md="6" className="colPadding">
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
                                                         onClick={() => this.handleValidateFicheDepotPFEModify()}>
                                                    Valider
                                                </CButton>
                                            }
                                            &nbsp;&nbsp;
                                            <CButton variant="outline"
                                                     color="dark"
                                                     onClick={this.handleClosePopupModifyMarkToStudent}>
                                                Exit
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupNotifyOkGiveMarkToStudent}
                                            onClose={this.handleClosePopupNotifyOkGiveMarkToStudent}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Vous avez saisi la Note Stage Ingénieur pour votre Étudiant(e) &nbsp;
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
                                            <Button onClick={this.handleClosePopupNotifyOkGiveMarkToStudent} color="primary">
                                                Exit
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupNotifyOkGiveMarkToStudent}
                                            onClose={this.handleClosePopupNotifyOkGiveMarkToStudent}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Vous avez saisi la Note Stage Ingénieur pour votre Étudiant(e) &nbsp;
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
                                            <Button onClick={this.handleClosePopupNotifyOkGiveMarkToStudent} color="primary">
                                                Exit
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="sm"
                                            keepMounted
                                            open={openPopupOKAcceptDemandeModif}
                                            onClose={this.handleClosePopupOkForAccepteDemandeModifDepot}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Vous avez accepté la Demande de Mise À Jour de Dépôt
                                                <br/>
                                                pour votre Étudiant(e) &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            <Button onClick={this.handleClosePopupOkForAccepteDemandeModifDepot} color="primary">
                                                Exit
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupVerifyMarkIngTr}
                                            onClose={this.handleClosePopupVerifyGiveMarkToStudent}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Êtes-vous sûrs(es) de vouloir attribuer <br/>
                                                la note &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                                    {notePE}
                                                </span>
                                                &nbsp; à votre Étudiant(e) &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                                &nbsp;?.
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                !showSpinnerGiveMarkToStudent &&
                                                <CButton shape="pill" color="success" variant="outline" onClick={this.handleClosePopupVerifyGiveMarkToStudent}>
                                                    OUI
                                                </CButton>
                                            }

                                            {
                                                showSpinnerGiveMarkToStudent &&
                                                <Spinner animation="grow" variant="success"/>
                                            }
                                            <CButton shape="pill" color="danger" variant="outline" disabled={showSpinnerGiveMarkToStudent} onClick={this.handleClosePopupVerifyNoMarkIngTr}>
                                                NON
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupVerifyValidDepotEngTr}
                                            onClose={this.handleClosePopupValidateRefusDepot}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Êtes-vous sûr(e)s de vouloir refuser <br/>
                                                le dépôt Stage Ingénieur pour votre Étudiant(e) &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>&nbsp;?.
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                <CButton shape="pill" color="success" variant="outline" onClick={this.handleOpenPopupMotifRefusDepot}>
                                                    OUI
                                                </CButton>
                                            }
                                            <CButton shape="pill" color="danger" variant="outline" onClick={this.handleClosePopupValidateRefusDepot}>
                                                NON
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupVerifyAcceptValidDepotEngTr}
                                            onClose={this.handleClosePopupVerifyAcceptDemandeModif}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Êtes-vous sûr(e)s de vouloir accepter <br/>
                                                la Demande de Modification du dépôt Stage Ingénieur envoyée par votre Étudiant(e) &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>&nbsp;?.
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                !showSpinnerAcceptDemandeModifDepot &&
                                                <CButton shape="pill" color="success" variant="outline" onClick={this.handleAcceptDemandeModifyDepot}>
                                                    OUI
                                                </CButton>
                                            }
                                            {
                                                showSpinnerAcceptDemandeModifDepot &&
                                                <Spinner animation="grow" variant="success"/>
                                            }
                                            <CButton shape="pill" color="danger" variant="outline" onClick={this.handleClosePopupVerifyAcceptDemandeModif}>
                                                NON
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupSuccessRefuseDepot}
                                            onClose={this.handleClosePopupAcceptDemandeModifyDepot}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                La demande de Refus Dépôt pour votre Étudiant(e)
                                                <br/>
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                                <br/>
                                                avec le Motif
                                                <br/>
                                                <span className="text-danger" style={{fontSize: "12px"}}>
                                                    {justifMotifRefus}
                                                </span>
                                                <br/>
                                                a été effectuée avec succès.
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            <Button onClick={this.handleClosePopupSuccessRefusDepot}
                                                    color="primary">
                                                Exit
                                            </Button>
                                        </DialogActions>
                                    </Dialog>

                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupMotifDepotEngTr}
                                            onClose={this.handleClosePopupMotifRefusDepot}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Merci de décrire votre &nbsp;
                                                <span className="text-danger" style={{fontSize: "12px"}}>
                                                    Motif de Refus
                                                </span>
                                                <br/><br/>
                                                <CRow>
                                                    <CCol md="1"/>
                                                    <CCol md="10">
                                                        <textarea className="form-control"
                                                                  style={{height: 150}}
                                                                  placeholder="Présenter le Motif de Refus de Dépôt ..."
                                                                  value={justifMotifRefus}
                                                                  disabled={showSpinnerRefusDepot}
                                                                  onChange={(e) => this.onChangeMotifRefusDepot(e)}
                                                                  maxLength="500"/>
                                                    </CCol>
                                                    <CCol md="1"/>
                                                </CRow>
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                !showSpinnerRefusDepot &&
                                                <CButton shape="pill" color="success" variant="outline" onClick={this.handleRefuseDepot}>
                                                    OUI
                                                </CButton>
                                            }

                                            {
                                                showSpinnerRefusDepot &&
                                                <Spinner animation="grow" variant="success"/>
                                            }
                                            <CButton shape="pill" color="danger" variant="outline" disabled={showSpinnerRefusDepot} onClick={this.handleClosePopupMotifRefusDepot}>
                                                NON
                                            </CButton>
                                        </DialogActions>
                                    </Dialog>
                                    <Dialog fullHight fullWidth
                                            maxWidth="xs"
                                            keepMounted
                                            open={openPopupVerifyModifMarkIngTr}
                                            onClose={this.handleClosePopupVerifyModifyMarkOfStudent}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                Êtes-vous sûr(es) de vouloir modifier <br/>
                                                la note de votre Étudiant(e) &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                            {selectedStudentFullName}
                                        </span>
                                                &nbsp; de &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                            {firstNotePE}
                                        </span>
                                                &nbsp; à &nbsp;
                                                <span className="text-info" style={{fontSize: "12px"}}>
                                            {notePE}
                                        </span>&nbsp;?.
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            {
                                                !showSpinnerModifyMarkToStudent &&
                                                <Button onClick={this.handleClosePopupVerifyModifyMarkOfStudent} color="primary">
                                                    Oui
                                                </Button>
                                            }

                                            {
                                                showSpinnerModifyMarkToStudent &&
                                                <Spinner animation="grow" variant="primary"/>
                                            }
                                            <Button onClick={this.handleClosePopupVerifyNoModifMarkIngTr}
                                                    disabled={showSpinnerModifyMarkToStudent}
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

