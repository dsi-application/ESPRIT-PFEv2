import React, {Component} from "react";

import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import AuthService from "../../services/auth.service";

import excelGreenIcon from "../../images/excelGreenIcon.png";
import excelBlueIcon from "../../images/excelBlueIcon.jpg";

import TheSidebar from "../../../containers/TheSidebar";
import ReactTextTransition from "react-text-transition";

import {
    CButton,
    CCard,
    CCardBody,
    CCardHeader,
    CCol,
    CContainer,
    CDataTable,
    CNav,
    CNavItem,
    CNavLink,
    CRow,
    CSpinner,
    CTabContent,
    CTabPane,
    CTabs,
    CTooltip
} from "@coreui/react";

import CIcon from "@coreui/icons-react";
import {freeSet} from '@coreui/icons';

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import Spinner from "react-bootstrap/Spinner";

import Fab from "@material-ui/core/Fab";
import NavigationIcon from "@material-ui/icons/Navigation";

import "../../css/style.css";


const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const fields = [
    {
        key: 'identifiant',
        label: 'Identifiant',
        _style: {width: '7%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'fullName',
        label: 'Prénom & Nom',
        _style: {width: '25%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'classe',
        label: 'Classe',
        _style: {width: '10%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'dateDepot',
        label: 'Date Dépôt',
        _style: {width: '13%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'dateSoutenance',
        label: 'Date Soutenance',
        _style: {width: '13%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'session',
        label: 'Session',
        _style: {width: '14%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'action',
        label: 'Action',
        _style: {width: '21%'},
        sorter: false,
        filter: false
    }
];


const fieldAvExps = [
    {
        key: 'identifiant',
        label: 'Identifiant',
        _style: {width: '15%'},
        sorter: true,
        filter: true
    },
    {
        key: 'up',
        label: 'Unité Pédagogique',
        _style: {width: '25%'},
        sorter: true,
        filter: true
    },
    {
        key: 'nom',
        label: 'Nom & Prénom',
        _style: {width: '50%'},
        sorter: true,
        filter: true
    },
    {
        key: 'action',
        label: 'Action',
        _style: {width: '10%'},
        sorter: false,
        filter: false
    }
];

const fieldAvTs = [
    {
        key: 'identifiant',
        label: 'Identifiant',
        _style: {width: '15%'},
        sorter: true,
        filter: true
    },
    {
        key: 'up',
        label: 'Unité Pédagogique',
        _style: {width: '25%'},
        sorter: true,
        filter: true
    },
    {
        key: 'nom',
        label: 'Nom & Prénom',
        _style: {width: '50%'},
        sorter: true,
        filter: true
    },
    {
        key: 'action',
        label: 'Action',
        _style: {width: '10%'},
        sorter: false,
        filter: false
    }
];

const fieldAvCs = ["codeSalle", {
    key: 'action',
    label: 'action',
    _style: {width: '1%'},
    sorter: false,
    filter: false
}];


const timeStyle = {
    container: {
        display: "flex",
        flexWrap: "wrap",
    },
};

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();


export default class SoutenancesNotifiées extends Component {
    constructor(props) {
        super(props);


        this.loadStudentsAuthorizedTOSTN = this.loadStudentsAuthorizedTOSTN.bind(this);
        this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
        this.onChangeDateSoutenance = this.onChangeDateSoutenance.bind(this);
        this.checkDisponibilityTeacher = this.checkDisponibilityTeacher.bind(this);
        this.checkDisponibilityExpert = this.checkDisponibilityExpert.bind(this);
        this.checkDisponibilityClassroom = this.checkDisponibilityClassroom.bind(this);
        this.onChangeTraineeShipKind = this.onChangeTraineeShipKind.bind(this);
        this.checkDisponibilityPedagogicalEncadrant = this.checkDisponibilityPedagogicalEncadrant.bind(this);
        this.handlePlanifySoutenance = this.handlePlanifySoutenance.bind(this);
        this.handleDownloadPV = this.handleDownloadPV.bind(this);
        this.handleDownloadPVBinome = this.handleDownloadPVBinome.bind(this);
        this.handleDownloadPlanningSoutenanceByFilter = this.handleDownloadPlanningSoutenanceByFilter.bind(this);
        this.handleConsultPV = this.handleConsultPV.bind(this);
        this.handleMakeSTNEditable = this.handleMakeSTNEditable.bind(this);
        this.handleConsultStudentDetails = this.handleConsultStudentDetails.bind(this);
        this.handleDownloadAllPlanningSoutenanceExcel = this.handleDownloadAllPlanningSoutenanceExcel.bind(this);
        this.handleClosePopupAffectSoutenance = this.handleClosePopupAffectSoutenance.bind(this);
        this.handleClosePopupShowPV = this.handleClosePopupShowPV.bind(this);
        this.handleClosePopupShowStudentDetails = this.handleClosePopupShowStudentDetails.bind(this);

        this.handleCloseSuccessAffectJPEnsPopup = this.handleCloseSuccessAffectJPEnsPopup.bind(
            this
        );
        this.handleCloseSuccessAffectSallePopup = this.handleCloseSuccessAffectSallePopup.bind(
            this
        );
        this.handleCloseSuccessValidateAndNotifyPopup = this.handleCloseSuccessValidateAndNotifyPopup.bind(
            this
        );
        /*this.handleCloseAskTMakeSTNModifPopup = this.handleCloseAskTMakeSTNModifPopup.bind(
            this
        );*/
        this.handleCloseSuccessAffectJPNEnsPopup = this.handleCloseSuccessAffectJPNEnsPopup.bind(
            this
        );
        this.handleCloseSuccessAffectExpertPopup = this.handleCloseSuccessAffectExpertPopup.bind(
            this
        );
        this.handleAffectJuryPresidentEns = this.handleAffectJuryPresidentEns.bind(
            this
        );
        this.handleAffectExpertEns = this.handleAffectExpertEns.bind(this);
        this.handleChangeEndHour = this.handleChangeEndHour.bind(this);
        this.handleChangeStartHour = this.handleChangeStartHour.bind(this);

        this.handleAffectClassroom = this.handleAffectClassroom.bind(this);
        this.handleAffectJuryPresidentNotEns = this.handleAffectJuryPresidentNotEns.bind(
            this
        );
        this.onChangeAffectationKind = this.onChangeAffectationKind.bind(this);
        this.validateAndNotify = this.validateAndNotify.bind(this);
        this.handleInitilizeSTNTime = this.handleInitilizeSTNTime.bind(this);

        this.handleDownloadFicheDepotPFE = this.handleDownloadFicheDepotPFE.bind(this);

        this.handleTurnSoutenanceToPlanified = this.handleTurnSoutenanceToPlanified.bind(this);

        this.handleClosePopupArchiveSoutenance = this.handleClosePopupArchiveSoutenance.bind(this);
        this.handleOpenPopupArchiveSoutenance = this.handleOpenPopupArchiveSoutenance.bind(this);

        this.handleClosePopupSuccessArchiveSoutenance = this.handleClosePopupSuccessArchiveSoutenance.bind(this);


        // init2021
        this.state = {
            lol111: "",
            selectedDate: new Date(),
            selectedStartHour: "08:00",
            selectedEndHour: "",
            listOfAuthorizedStudentsToSTN: [],
            listOfAuthorizedStudentsToSTNByFilter: [],
            listOfAvailableTeachersToSTN: [],
            listOfAvailableExpertsToSTN: [],
            listOfAvailableClassroomsToSTN: [],
            openPopupAffectSoutenance: false,
            openPopupShowPV: false,
            openPopupArchiveSoutenance: false,
            idEtToSTN: "",
            emailToSTN: "",
            checked: 0,
            affectationKind: "Affectation Interne",
            allTAffectationKinds: [
                {
                    value: "Affectation Interne",
                    label: "Affectation Interne",
                    color: "",
                },
                {
                    value: "Affectation Externe",
                    label: "Affectation Externe",
                    color: "",
                },
            ],
            fullNameJuryPresidentNotEns: "",

            loadingListOfAvTsToSTN: false,
            loadingListOfAvExpsToSTN: false,
            loadingListOfAvCsToSTN: false,
            loadingValidateAndNotify: false,

            successAffectJPEnsPopup: false,
            successAffectJPNEnsPopup: false,
            successAffectExpertPopup: false,
            successAffectSallePopup: false,
            successValidateAndNotifyPopup: false,

            disponibilityPE: "",

            pedagogicalEncadrant: {},
            presidentJuryNotEns: "",
            salle: "",
            affectationPresidentJury: "",
            selectedStudentFullName: "",
            valAndNot: "EN ATTENTE",

            refuseEndHour: false,
            statusValidateAndNotify: "TODO",
            formattedSelectedDate: "",
            classe: "",
            pathPVFile: "",
            disabledSTNTime: false,
            showLoadingExcelGreenIcon: false,
            showLoadingExcelBlueIcon: false,
            traineeKind: "",
            affectedJP: {},
            affectedEXP: {},
            selectedStudentMail: "",
            selectedStudentTel: "",
            openPopupShowStudentDetails: false,
            showSpinnerListAutorizedStudents: true,
            loadList: true,
            traineeShipKind: "En Tunisie",
            allTraineeshipKinds: [],
            checkedTraineeShip: 0,
            pairFullName: "",
            pairIdentifiant: "",
            pairCurrentClass: "",
            indexStudentToPlanify: "",
            planifyKind: "PNORMAL"
        };


        /* console.log(
           "------------------------------> Start Hour: " +
             this.state.selectedStartHour
         );*/

        /*console.log('--------------------- Current Resp Serv Stg: ' + currentResponsableServiceStage.id);*/
        //

        /*
        var requestStn = new XMLHttpRequest();
        requestStn.open(
          "GET",
          API_URL_MESP + "authorizedStudentsToSTN/" + 1 + "/" + currentResponsableServiceStage.id,
          false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestStn.send(null);
        let listAuthStuToSTN = JSON.parse(requestStn.responseText);
        let stuList = this.state.listOfAvailableClassroomsToSTN.slice();
        console.log('-----------------LOAD---------------> 2: ', new Date())
        for (let stu of listAuthStuToSTN)
        {
            console.log('-------------LOAD-------------------> 3: ', new Date())
            //this.state.showSpinnerListAutorizedStudents = true;
            //this.setState({showSpinnerListAutorizedStudents: true})
            // identifiant, nom, prénom, mail, téléphone, classe, dateDepot, session, etat, traineeKind
            const studentObj = stu;
            stuList.push(stu);

            console.log('--------------------------------> 4: ', new Date())
        }

        this.state.listOfAuthorizedStudentsToSTN = stuList;
        this.setState({showSpinnerListAutorizedStudents: false})
        this.state.showSpinnerListAutorizedStudents = false;

        console.log('----------------LOAD----------------> 5: ', new Date())
        console.log('----------------LOAD----------------> AZERT: ', this.state.showSpinnerListAutorizedStudents)
        */

        // console.log('------------------------> TREATMENT 1: ' + new Date() + " - " + this.state.loadList);

        this.loadStudentsAuthorizedTOSTN();

        // Trainee Kind
        var requestTK = new XMLHttpRequest();
        requestTK.open("GET", API_URL_MESP + "traineeKinds", false);
        requestTK.send(null);
        let tk = JSON.parse(requestTK.responseText);

        // // console.log('-------------------------tk-1-> ', tk);

        let crsList = this.state.allTraineeshipKinds.slice();
        for (let i of tk) {
            const tkObj = {value: i, label: i, color: ""};
            crsList.push(tkObj);
        }
        ;

        this.state.allTraineeshipKinds = crsList;

    }

    onChangeTraineeShipKind(i, val) {
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete A: ' + i);
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete B: ' + val);
        this.setState({
            checkedTraineeShip: i,
            traineeShipKind: val,
        });

        this.state.checkedTraineeShip = i;
        this.state.traineeShipKind = val;

        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: TraineeShipKind 1: ' + this.state.checkedTraineeShip);
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: TraineeShipKind 2: ' + this.state.traineeShipKind);

        var requestUTK = new XMLHttpRequest();
        requestUTK.open("GET", API_URL_MESP + "updateTraineeKind/" + this.state.idEtToSTN + "/" + this.state.traineeShipKind, false);
        requestUTK.send(null);
        let utk = requestUTK.responseText;

        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: TraineeShipKind 3: ' + this.state.salle + " - " + this.state.traineeShipKind);

        this.setState({
            traineeKind: val,
            salle: utk
        });

        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: TraineeShipKind 4: ' + this.state.salle + " - " + this.state.traineeKind);

    }

    loadStudentsAuthorizedTOSTN() {
        AuthService.soutenancesAllNotifiées(currentResponsableServiceStage.id).then(
            (response) => {
                // console.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);

                let stuList = this.state.listOfAuthorizedStudentsToSTN.slice();
                for (let stu of response.data) {
                    const studentObj = stu;
                    stuList.push(stu);
                }

                this.setState({
                    loadList: false,
                    listOfAuthorizedStudentsToSTN: stuList
                });
                // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

            },
            (error) => {
            }
        );
    }

    handleTurnSoutenanceToPlanified() {
        AuthService.validateUniquePlanification(this.state.idEtToSTN).then(
            (response) => {
                let idEt = this.state.idEtToSTN;

                this.setState({
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idEt),
                    listOfAuthorizedStudentsToSTNByFilter: this.state.listOfAuthorizedStudentsToSTNByFilter.filter(el => el.identifiant != idEt),
                    openPopupArchiveSoutenance: false,
                    openPopupSuccessArchiveSoutenance: true
                });
            },
            (error) => {
                // console.log("Check valAndNot -------NOT ENS------ FAIL");
            }
        );

    }

    handleDownloadFicheDepotPFE(identifiant, fullName, classe) {
        // console.log("Check Disponibility --------cr----- 1: " + identifiant);
        // console.log("Check Disponibility --------cr----- 2: " + fullName);
        // console.log("Check Disponibility --------cr----- 3: " + classe);

        let pairClass = "--";
        AuthService.downloadFicheDepotPFE(identifiant, classe, fullName, pairClass).then(
            (response) => {
                // console.log("Check Disponibility --------cr----- 4");

                let filename = "Fiche Dépôt PFE - " + fullName + ".pdf";
                const file = new Blob([response.data], {type: 'application/pdf'});
                const fileURL = URL.createObjectURL(file);

                let a = document.createElement('a');
                a.href = fileURL;
                a.download = filename;
                a.click();
                window.open(fileURL);
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );


        var requestBPVD = new XMLHttpRequest();
        requestBPVD.open("GET", API_URL_MESP + "binomeDetails/" + identifiant, false);
        requestBPVD.send(null);
        let bpvd = JSON.parse(requestBPVD.responseText);

        // console.log('------------hi08091---hjghj--------> SARS 0209: ' + bpvd);
        /*if (bpvd.identifiant === "--") {
            // console.log('------------hi0809-----------> SARS 0209: ' + bpvd);
        }*/

        if (bpvd.identifiant !== "--") {
            AuthService.downloadFicheDepotPFE(identifiant, classe, bpvd.fullName, bpvd.currentClass).then(
                (response) => {
                    // console.log("Check Disponibility --------cr----- 4");

                    let filename = "Fiche Dépôt PFE - " + bpvd.fullName + ".pdf";
                    const file = new Blob([response.data], {type: 'application/pdf'});
                    const fileURL = URL.createObjectURL(file);

                    let a = document.createElement('a');
                    a.href = fileURL;
                    a.download = filename;
                    a.click();
                    window.open(fileURL);
                },
                (error) => {
                    // console.log("Check Disponibility --------cr----- FAIL");
                }
            );
        }
    }

    onChangeAffectationKind(i, val) {
        this.setState({
            checked: i,
            affectationKind: val,
        });

        // console.log(' 1 ---**************** Clear: ' + this.state.checked + ' - ' + val + ' - ' + this.state.trtType);
    }

    handleInitilizeSTNTime() {
        // console.log("--------------------------> RESET : " + this.state.disabledSTNTime);
        AuthService.initiliazeSoutenanceTime(this.state.idEtToSTN).then(
            (response) => {

                this.setState({
                    selectedDate: new Date(),
                    selectedStartHour: "08:00",
                    selectedEndHour: "",
                    listOfAvailableTeachersToSTN: [],
                    listOfAvailableExpertsToSTN: [],
                    listOfAvailableClassroomsToSTN: [],
                    disponibilityPE: "",
                    affectationPresidentJury: "NOTYET",
                    presidentJuryNotEns: "",
                    affectedJP: {},
                    affectedEXP: {},
                    salle: "",
                    disabledSTNTime: false,
                    statusValidateAndNotify: "TODO",
                });

                //let crsList = this.state.listOfAvailableClassroomsToSTN.slice();

            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );

    }

    handlePlanifySoutenance(index, idEt, fullName, traineeKind) {
        // console.log("--------------------------> idEt: " + idEt);

        var requestPE = new XMLHttpRequest();
        requestPE.open("GET", API_URL_MESP + "findEncadrantPedagogiqueByStudent/" + idEt, false);
        requestPE.send(null);
        let pe = JSON.parse(requestPE.responseText);

        this.setState({
            openPopupAffectSoutenance: true,
            idEtToSTN: idEt,
            selectedStartHour: "08:00",
            listOfAvailableTeachersToSTN: [],
            listOfAvailableExpertsToSTN: [],
            listOfAvailableClassroomsToSTN: [],
            checked: "",
            fullNameJuryPresidentNotEns: "",
            selectedEndHour: "",
            affectationKind: "",
            pedagogicalEncadrant: pe,
            selectedStudentFullName: fullName,
            disabledSTNTime: false,
            traineeKind: traineeKind,
            indexStudentToPlanify: index
        });

        // console.log(this.state.pedagogicalEncadrant);
        // console.log("------------------------------> res PE: " + this.state.pedagogicalEncadrant.nom);
        this.gotStudentSTNAffectationDetailsPlanify(idEt);
    }

    handleConsultStudentDetails(idEt, fullName) {
        var requestStu = new XMLHttpRequest();
        requestStu.open("GET", API_URL_MESP + "studentMailTel/" + idEt, false);
        requestStu.send(null);
        let stu = JSON.parse(requestStu.responseText);

        this.setState({
            openPopupShowStudentDetails: true,
            selectedStudentFullName: fullName,
            selectedStudentMail: stu.mail,
            selectedStudentTel: stu.tel,
        });
    }

    handleConsultPV(idEt, fullName, classe) {
        //console.log("------------PV-------currentClass-----------------------> idEt: " + classe);
        // console.log("------------PV------------------------------> nomet: " + nomet);
        // console.log("------------PV------------------------------> prenomet: " + prenomet);
        // console.log("------------PV------------------------------> mailet: " + mailet);

        var requestPE = new XMLHttpRequest();
        requestPE.open("GET", API_URL_MESP + "findEncadrantPedagogiqueByStudent/" + idEt, false);
        requestPE.send(null);
        let pe = JSON.parse(requestPE.responseText);

        this.setState({
            openPopupShowPV: true,
        });

        this.state.idEtToSTN = idEt;
        this.state.selectedStudentFullName = fullName;
        this.state.pedagogicalEncadrant = pe;
        this.state.currentClass = classe;

        // console.log("------------------------------> res PE: " + this.state.pedagogicalEncadrant.nom);

        // console.log("------dfv------- PV-1 ----------------> res id: " + this.state.idEtToSTN);
        // console.log("----sdf--------- PV-1 ----------------> res FN: " + this.state.selectedStudentFullName);
        // console.log("--sdf---------- PV-1 ----------------> res Mail: " + this.state.emailToSTN);
        // console.log("----df--- PV-1 -------> res PE: " + this.state.pedagogicalEncadrant.nom);

        this.gotStudentSTNAffectationDetailsPlanify(idEt);


        var requestBPVD = new XMLHttpRequest();
        requestBPVD.open("GET", API_URL_MESP + "binomePVDetails/" + this.state.idEtToSTN, false);
        requestBPVD.send(null);
        let bpvd = JSON.parse(requestBPVD.responseText);

        this.setState({
            pairIdentifiant: bpvd.identifiant,
            pairFullName: bpvd.fullName,
            pairCurrentClass: bpvd.currentClass
        });

    }

    handleConsultPV(idEt, fullName, classe) {
        //console.log("------------PV-------currentClass-----------------------> idEt: " + classe);
        // console.log("------------PV------------------------------> nomet: " + nomet);
        // console.log("------------PV------------------------------> prenomet: " + prenomet);
        // console.log("------------PV------------------------------> mailet: " + mailet);

        var requestPE = new XMLHttpRequest();
        requestPE.open("GET", API_URL_MESP + "findEncadrantPedagogiqueByStudent/" + idEt, false);
        requestPE.send(null);
        let pe = JSON.parse(requestPE.responseText);

        this.setState({
            openPopupShowPV: true,
        });

        this.state.idEtToSTN = idEt;
        this.state.selectedStudentFullName = fullName;
        this.state.pedagogicalEncadrant = pe;
        this.state.currentClass = classe;

        // console.log("------------------------------> res PE: " + this.state.pedagogicalEncadrant.nom);

        // console.log("------dfv------- PV-1 ----------------> res id: " + this.state.idEtToSTN);
        // console.log("----sdf--------- PV-1 ----------------> res FN: " + this.state.selectedStudentFullName);
        // console.log("--sdf---------- PV-1 ----------------> res Mail: " + this.state.emailToSTN);
        // console.log("----df--- PV-1 -------> res PE: " + this.state.pedagogicalEncadrant.nom);

        this.gotStudentSTNAffectationDetailsPlanify(idEt);


        var requestBPVD = new XMLHttpRequest();
        requestBPVD.open("GET", API_URL_MESP + "binomePVDetails/" + this.state.idEtToSTN, false);
        requestBPVD.send(null);
        let bpvd = JSON.parse(requestBPVD.responseText);

        this.setState({
            pairIdentifiant: bpvd.identifiant,
            pairFullName: bpvd.fullName,
            pairCurrentClass: bpvd.currentClass
        });

    }

    handleClosePopupModifSTN(idEt) {
        this.setState({
            openPopupSuccessArchiveSoutenance : false,
        })
    }

    handleMakeSTNEditable(idEt) {
        //console.log("------------PV-------currentClass-----------------------> idEt: " + classe);
        // console.log("------------PV------------------------------> nomet: " + nomet);
        // console.log("------------PV------------------------------> prenomet: " + prenomet);
        // console.log("------------PV------------------------------> mailet: " + mailet);

        AuthService.validateUniquePlanification(idEt).then(
            (response) => {
                // console.log("Check Disponibility -----AT--cr------ SUCCESS");
                // let crsList = this.state.listOfAvailableClassroomsToSTN.slice();
                // crsList.splice(index, 1);
                this.setState({
                    /*OUI SARS*/
                });
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );

        this.setState({
            openPopupShowPV: true,
        });

    }

    handleDownloadAllPlanningSoutenanceExcel() {
        this.setState({showLoadingExcelGreenIcon: true})
        AuthService.downloadPlanningSoutenanceNotifiéesExcel(1, currentResponsableServiceStage.id).then(
            (response) => {
                this.setState({showLoadingExcelGreenIcon: false})
                let filename = "Planning Soutenances Notifiées.xls";
                const file = new File([response.data], "planningS.xls");
                const fileURL = URL.createObjectURL(file);
                // console.log("Check Disponibility ------------- DONE EX ", response.data);
                let a = document.createElement('a');
                a.href = fileURL;
                a.download = filename;
                a.click();
                // window.open(fileURL);
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );
    }

    handleDownloadPlanningSoutenanceByFilter() {
        this.setState({showLoadingExcelBlueIcon: true})
        AuthService.downloadPlanningSoutenanceExcelByFilter(this.state.listOfAuthorizedStudentsToSTNByFilter).then(
            (response) => {
                this.setState({showLoadingExcelBlueIcon: false})
                let filename = "Planning Soutenances suivant Filtre.xls";
                const file = new File([response.data], "planningSFs.xls");
                const fileURL = URL.createObjectURL(file);
                // console.log("Check Disponibility ------------- DONE EX ", response.data);
                let a = document.createElement('a');
                a.href = fileURL;
                a.download = filename;
                a.click();
                // window.open(fileURL);
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );
    }

    handleDownloadPV() {

        let juryPresident = "";
        if (this.state.affectedJP.nom !== "") {
            juryPresident = this.state.affectedJP.nom;
        }
        if (this.state.presidentJuryNotEns !== "") {
            juryPresident = this.state.presidentJuryNotEns;
        }

        // console.log('------------------------------------- formattedSelectedDate 1--->' + this.state.formattedSelectedDate);
        AuthService.downloadStudentPV(
            this.state.idEtToSTN,
            this.state.selectedStudentFullName,
            this.state.currentClass,
            juryPresident,
            this.state.affectedEXP.nom,
            this.state.pedagogicalEncadrant.nom,
            this.state.formattedSelectedDate
        ).then(
            (response) => {
                let filename = "Procès Verbal - " + this.state.selectedStudentFullName + ".pdf";
                const file = new Blob([response.data], {type: 'application/pdf'});
                const fileURL = URL.createObjectURL(file);

                let a = document.createElement('a');
                a.href = fileURL;
                a.download = filename;
                a.click();
                window.open(fileURL);
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );
    }

    handleDownloadPVBinome() {

        let juryPresident = "";
        if (this.state.affectedJP.nom !== "") {
            juryPresident = this.state.affectedJP.nom;
        }
        if (this.state.presidentJuryNotEns !== "") {
            juryPresident = this.state.presidentJuryNotEns;
        }

        // console.log('------------------------------------- formattedSelectedDate 1--->' + this.state.formattedSelectedDate);
        AuthService.downloadBinomePV(
            this.state.idEtToSTN,
            this.state.pairIdentifiant,
            this.state.pairFullName,
            this.state.pairCurrentClass,
            juryPresident,
            this.state.affectedEXP.nom,
            this.state.pedagogicalEncadrant.nom,
            this.state.formattedSelectedDate
        ).then(
            (response) => {
                let filename = "Procès Verbal - " + this.state.pairFullName + ".pdf";
                const file = new Blob([response.data], {type: 'application/pdf'});
                const fileURL = URL.createObjectURL(file);

                let a = document.createElement('a');
                a.href = fileURL;
                a.download = filename;
                a.click();
                window.open(fileURL);
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );
    }

    checkDisponibilityPedagogicalEncadrant() {
        /*console.log(
          "---------az-----------------> SD: " + this.state.selectedStartHour
        );
        console.log(
          "---az-----------------------> ED: " + this.state.selectedEndHour
        );*/

        //let unity = this.state.selectedStartHour + '-' + this.state.selectedEndHour;

        //console.log('Check Disponibility ------------- TextScroller.js: ' + unity);

        /*this.setState({
          loadingListOfAvTsToSTN: false,
          loadingListOfAvCsToSTN: false,
        });*/

        // console.log('Check Disponibility ------------- FormDt: ' + this.state.selectedDate);

        let formattedDate = this.gotFormattedDate(this.state.selectedDate);

        // console.log('Check Disponibility ------------- FormDt: ' + formattedDate);

        var requestStn = new XMLHttpRequest();
        requestStn.open(
            "GET",
            API_URL_MESP + "pedagogicalEncadrantDisponibility/" +
            this.state.pedagogicalEncadrant.identifiant +
            "/" +
            this.state.idEtToSTN +
            "/" +
            formattedDate +
            "/" +
            this.state.selectedStartHour +
            "/" +
            this.state.selectedEndHour,
            false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestStn.send(null);
        let resultDPE = JSON.parse(requestStn.responseText);

        let resultDPES = "";
        if (resultDPE === true) {
            resultDPES = "YES";
        } else {
            resultDPES = "NO";
        }

        // console.log("----------------*1*------->RES-1: " + resultDPES);
        // console.log(resultDPE);
        // console.log("----------------*2*------->RES-1: " + resultDPES);

        this.setState({disponibilityPE: resultDPES});
        /*this.setState  ({
                open: true,
                idEtToSTN: idEt,
                listOfAvailableTeachersToSTN: [],
                checked: '',
                fullNameJuryPresidentNotEns: '',
                selectedStartHour: '09:00',
                selectedEndHour: '',
                affectationKind: '',
                pedagogicalEncadrant: pe
            });*/

        var requestBFN = new XMLHttpRequest();
        requestBFN.open("GET", API_URL_MESP + "binomeFullName/" + this.state.idEtToSTN, false);
        requestBFN.send(null);
        this.setState({pairFullName: requestBFN.responseText});

        // console.log("----------------PAIR------>RES-1:" + requestBFN.responseText + "-" + this.state.pairFullName);

    }

    handleAffectClassroom(index, codeSalle) {
        /*console.log(
          "----------------------cr----> IDS: " +
            this.state.selectedDate +
            " - " +
            this.state.idEtToSTN +
            " - " +
            codeSalle
        );*/
        this.setState({openPopupAffectSoutenance: true});
        // , this.state.selectedDate

        let soutenanceDate = this.gotFormattedDate(this.state.selectedDate);

        AuthService.affectClassRoom(
            this.state.idEtToSTN,
            codeSalle,
            soutenanceDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour
        ).then(
            (response) => {
                // console.log("Check Disponibility -----AT--cr------ SUCCESS");
                let crsList = this.state.listOfAvailableClassroomsToSTN.slice();
                crsList.splice(index, 1);
                this.setState({
                    listOfAvailableClassroomsToSTN: crsList,
                    successAffectSallePopup: true,
                    salle: codeSalle,
                    disabledSTNTime: true,
                });
            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );
    }

    handleAffectJuryPresidentEns(index, idEns) {
        /*console.log(
          "--------------------------> IDS: " +
            index +
            " - " +
            this.state.idEtToSTN +
            " - " +
            idEns
        );*/


        let soutenanceDate = this.gotFormattedDate(this.state.selectedDate);

        AuthService.affectJuryPresidentEns(
            this.state.idEtToSTN,
            idEns,
            soutenanceDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour
        ).then(
            (response) => {
                // console.log("Check Disponibility -----AT-------- SUCCESS");
                let teachersList = this.state.listOfAvailableTeachersToSTN.slice();

                this.setState({
                    affectedJP: this.gotTeacher(idEns),
                });


                // console.log("Check Disponibility ----- AFF-HELLO-PJ ----- SUCCESS", teachersList[index]);

                teachersList.splice(index, 1);
                this.setState({
                    listOfAvailableTeachersToSTN: teachersList,
                    successAffectJPEnsPopup: true,
                    affectationPresidentJury: "DONE",
                    disabledSTNTime: true,
                });
                // console.log("Check Disponibility -----AT----LOL---- SUCCESS", this.state.affectationPresidentJury);
            },
            (error) => {
                // console.log("Check Disponibility ------------- FAIL");
            }
        );
    }

    validateAndNotify() {
        // console.log('-----------------------> v&n 1: ' + this.state.selectedDate);
        // console.log('-----------------------> v&n 2: ' + this.state.selectedStartHour);
        // console.log('-----------------------> v&n 3: ' + this.state.selectedEndHour);

        // console.log('-----------------------> v&n 4: ' + this.state.pedagogicalEncadrant.identifiant);
        // console.log('-----------------------> v&n 5: ' + this.state.presidentJuryNotEns);
        // console.log('-----------------------> v&n 6: ' + this.state.affectedJP.identifiant);
        // console.log('-----------------------> v&n 7: ' + this.state.affectedEXP.identifiant);
        // console.log('-----------------------> v&n 8: ' + this.state.salle);
        // console.log('-----------------------> v&n 9: ' + this.state.idEtToSTN);

        // console.log('-------------LOL123----------> v&n A: ' + this.state.presidentJuryNotEns);

        let presjury = "";
        if (this.state.presidentJuryNotEns !== "") {
            // console.log('-------------LOL123----------> v&n Z: ' + presjury);
            presjury = this.state.presidentJuryNotEns + "EXTERNE";
        }
        if (this.state.affectedJP !== null && this.state.presidentJuryNotEns === "") {
            // console.log('-------------LOL123----------> v&n E: ' + presjury);
            presjury = this.state.affectedJP.identifiant;
        }

        // console.log('-------------LOL123----------> v&n R: ' + presjury);

        let dateSTN = this.gotFormattedDate(this.state.selectedDate);

        this.setState({
            loadingValidateAndNotify: true,
        });

        AuthService.validateAndNotifySoutenanceActors(
            this.state.idEtToSTN,
            dateSTN,
            this.state.selectedStartHour,
            this.state.selectedEndHour,
            this.state.salle,
            this.state.pedagogicalEncadrant.identifiant,
            presjury,
            this.state.affectedEXP.identifiant
        ).then(
            (response) => {
                /*console.log("#####################################> 1:" +
                    this.state.planifyKind + " - " +
                    this.state.indexStudentToPlanify + " -----> " +
                    this.state.listOfAuthorizedStudentsToSTNByFilter.length + " / " +
                    this.state.listOfAuthorizedStudentsToSTN.length);*/


                let idEt = this.state.idEtToSTN;

                /*if(this.state.planifyKind === "PNORMAL")
                {*/
                /*let listAuthStuToSTN = this.state.listOfAuthorizedStudentsToSTN.slice();
                listAuthStuToSTN.splice(this.state.indexStudentToPlanify, 1);
    */
                this.setState({
                    successValidateAndNotifyPopup: true,
                    loadingValidateAndNotify: false,
                    statusValidateAndNotify: "TRAITÉ",
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idEt),
                    listOfAuthorizedStudentsToSTNByFilter: this.state.listOfAuthorizedStudentsToSTNByFilter.filter(el => el.identifiant != idEt),

                });
                /*}*/

                /*this.setState(prevState => ({
              list: prevState.list.filter(row => (
                row.id !== id
              ))
            }))*/

                /*
                data: prevState.data.filter(el => el != id )
                        this.setState({
                          listOfStudentsTrainedByPE: this.state.listOfStudentsTrainedByPE.map(el => (el.identifiant === idEt ? {...el, etatTreatDepotPC } : el)),
                */
                /*if(this.state.planifyKind === "PWFILTER")
                {

                    this.setState({
                      successValidateAndNotifyPopup:true,
                      loadingValidateAndNotify: false,
                      statusValidateAndNotify:"TRAITÉ",
                      //listOfAuthorizedStudentsToSTNByFilter:
                    });
                }*/

                /*console.log("#####################################> 2:" +
                    this.state.planifyKind + " - " +
                    this.state.indexStudentToPlanify + " -----> " +
                    this.state.listOfAuthorizedStudentsToSTNByFilter.length + " / " +
                    this.state.listOfAuthorizedStudentsToSTN.length);*/

                // console.log('-------------------------------POP VN: ' + this.state.successValidateAndNotifyPopup);

                /*this.setState(prevState => ({
                 data: prevState.data.map(el => (el.id === id ? { ...el, text } : el)) }))
        */

                /*for (let stu of listAuthStuToSTN) {
                  // {id: 0, name: 'John Doe', registered: '2018/01/01', role: 'Guest', status: 'Pending'},
                  const teacherObj = {
                    identifiant: stu.idEt,
                    nom: stu.nomet,
                    prénom: stu.prenomet,
                    mail: stu.adressemailesp,
                    téléphone: stu.telet,
                    etat: stu.statusPlanifySTN,
                  };*/

                // this.setState({ valAndNot: "DONE" });
            },
            (error) => {
                // console.log("Check valAndNot -------NOT ENS------ FAIL");
            }
        );

    }

    handleChangeStartHour(e) {
        let value = e.target.value;
        this.setState({
            selectedStartHour: value,
            selectedEndHour: "00:00",
            disponibilityPE: "",
            loadingListOfAvTsToSTN: false,
            loadingListOfAvExpsToSTN: false,
            loadingListOfAvCsToSTN: false,
        });

        // console.log("Check valAndNot -------CONCERV SH------ " + this.state.selectedEndHour);

        let formattedStartHour = Number(value.replaceAll(":", ""));
        let formattedEndHour = Number("0000");

        if (formattedStartHour >= formattedEndHour) {
            this.setState({
                refuseEndHour: true
            });
        }

    }

    handleChangeEndHour(e) {
        let value = e.target.value;
        let formattedStartHour = Number(this.state.selectedStartHour.replaceAll(":", ""));
        let formattedEndHour = Number(value.replaceAll(":", ""));

        // console.log("Check valAndNot -------CONCERV EH------ " + this.state.selectedEndHour);

        this.setState({
            selectedEndHour: value,
            listOfAvailableTeachersToSTN: [],
            listOfAvailableExpertsToSTN: [],
            listOfAvailableClassroomsToSTN: [],
            refuseEndHour: false,
            disponibilityPE: "",
            loadingListOfAvTsToSTN: false,
            loadingListOfAvExpsToSTN: false,
            loadingListOfAvCsToSTN: false,
        });

        // console.log('------------------------------------- formattedSelectedDate 6--->:' + this.state.formattedSelectedDate);

        // console.log('--------------PLAN---------olk-1--> : ' + this.state.selectedStartHour + " - " + this.state.selectedEndHour);
        // console.log('--------------PLAN---------olk-2--> : ' + formattedStartHour + " - " + formattedEndHour);

        if (formattedStartHour < formattedEndHour) {
            // console.log('--------------PLAN------------ end hour: ' + e.target.value);
            // console.log('---------------PLAN----------- end hour: ' + this.state.idEtToSTN);

            this.gotStudentSTNAffectationDetails(this.state.idEtToSTN);

            // console.log('--------------PLAN------------ Start Hour: ' + this.state.selectedStartHour);
            // console.log('--------------PLAN------------ End Hour: ' + this.state.selectedEndHour);
        }
        if (formattedStartHour >= formattedEndHour) {
            this.setState({
                refuseEndHour: true
            });
        }
        // console.log('--------------PLAN---------olk--- End Hour: ' + this.state.refuseEndHour);
    }

    gotStudentSTNAffectationDetailsPlanify(idEt) {

        this.setState({
            affectedEXP: {},
            salle: "",
            statusValidateAndNotify: "TODO",
            presidentJuryNotEns: "",
            affectedJP: {},
        });

        var requestStnpl = new XMLHttpRequest();
        requestStnpl.open(
            "GET",
            API_URL_MESP + "fichePFEPlanifySoutenance/" +
            idEt,
            false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestStnpl.send(null);
        let resultDPEPL = JSON.parse(requestStnpl.responseText);
        //this.state.presidentJury = resultDPES.presidentJuryNotEns;
        // console.log("--------------AZE--------->RES-1: ", resultDPEPL);
        /*
          if(resultDPEPL.presidentJuryEns !== null)
          {
            console.log("--------------PLAN--------->RES-2: " + resultDPEPL.presidentJuryEns.nomEns);
          }


          if(resultDPEPL.membre !== null)
          {
            console.log("--------------PLAN--------->RES-3: " + resultDPEPL.membre.nomEns);
          }

        console.log("--------------PLAN--------->RES-4: " + resultDPEPL.salle);
    */

        // this.state.presidentJury = resultDPES.presidentJuryNotEns;
        if (resultDPEPL.presidentJuryNotEns === null && resultDPEPL.presidentJuryEns === null) {
            this.setState({affectationPresidentJury: "NOTYET"})
        }
        if (resultDPEPL.presidentJuryNotEns !== null) {
            this.setState({
                affectationPresidentJury: "DONE",
                presidentJuryNotEns: resultDPEPL.presidentJuryNotEns
            })
        }

        if (resultDPEPL.presidentJuryEns !== null) {
            this.setState({
                affectationPresidentJury: "DONE",
                affectedJP: resultDPEPL.presidentJuryEns
            })
        }

        if (resultDPEPL.membre !== null) {
            this.setState({
                affectedEXP: resultDPEPL.membre,
            })
            // console.log('--------------------------------------------------> HI: ' + resultDPEPL.membre.nom);
        }

        if (resultDPEPL.salle !== null) {
            this.setState({salle: resultDPEPL.salle})
            this.state.salle = resultDPEPL.salle;
        }

        /* if(resultDPEPL.dateSoutenance !== null)
         {
           this.setState({ stnDate:  resultDPEPL.dateSoutenance});
           this.state.stnDate = resultDPEPL.dateSoutenance;
           console.log('---------------RETRIEVE DT - 1: ' + resultDPEPL.dateSoutenance + " - " + this.state.stnDate);
         }

         if(resultDPEPL.heureDebutSoutenance !== null)
         {
           this.setState({ stnHeureDebut:  resultDPEPL.heureDebutSoutenance});
           this.state.stnHeureDebut = resultDPEPL.heureDebutSoutenance;
           console.log('---------------RETRIEVE HD - 2: ' + resultDPEPL.heureDebutSoutenance + " - " + this.state.stnHeureDebut);
         }

         if(resultDPEPL.heureFinSoutenance !== null)
         {
           this.setState({ stnHeureFin:  resultDPEPL.heureFinSoutenance});
           this.state.stnHeureFin = resultDPEPL.heureFinSoutenance;
           //this.state.selectedStartHour = resultDPEPL.heureFinSoutenance;
           console.log('---------------RETRIEVE HF - 3: ' + resultDPEPL.heureFinSoutenance + " - " + this.state.stnHeureFin);
         }*/

        // console.log('------PV---------RETRIEVE HF - PJ-EN: ', this.state.affectedJP.nom);
        // console.log('------PV---------RETRIEVE HF - PJ-NE: ', this.state.presidentJuryNotEns);
        // console.log('------PV---------RETRIEVE HF - EXP:', this.state.affectedEXP.nom);
        // console.log('------PV---------RETRIEVE HF - SAL:', this.state.salle);

        if ((resultDPEPL.presidentJuryEns != null || resultDPEPL.presidentJuryNotEns != null) && resultDPEPL.membre != null && resultDPEPL.salle != null) {

            let mydate = new Date(resultDPEPL.dateSoutenance);
            /*console.log('---------------RETRIEVE HF - 8', mydate);
            console.log('---------------RETRIEVE HF - 9');*/

            this.setState({
                selectedDate: mydate,
                selectedStartHour: resultDPEPL.heureDebutSoutenance,
                selectedEndHour: resultDPEPL.heureFinSoutenance,
                statusValidateAndNotify: "NOTFINISH",
                disabledSTNTime: true,
            })
        }

        if ((resultDPEPL.presidentJuryEns != null ||
                resultDPEPL.presidentJuryNotEns != null) ||
            resultDPEPL.membre != null ||
            (resultDPEPL.salle != null && resultDPEPL.salle !== "Remote")) {

            let mydate = new Date(resultDPEPL.dateSoutenance);
            /*console.log('---------------RETRIEVE HF - 8', mydate);
            console.log('---------------RETRIEVE HF - 9');*/

            // console.log('-----------------------------formSelDt-------- formattedSelectedDate aa--->' + resultDPEPL.dateSoutenance,);


            this.setState({
                selectedDate: mydate,
                formattedSelectedDate: this.gotFormattedDate(mydate),
                selectedStartHour: resultDPEPL.heureDebutSoutenance,
                selectedEndHour: resultDPEPL.heureFinSoutenance,
                disabledSTNTime: true,
            })
        }

        // console.log('-----------------------------formSelDt-------- formattedSelectedDate zz--->' + this.state.formattedSelectedDate);

        // console.log('----------ENDVN-1-> presidentJuryNotEns - 1:' + resultDPEPL.etat);

        // console.log('------PV---------RETRIEVE HF - SD: ', this.state.formattedSelectedDate);
        // console.log('------PV---------RETRIEVE HF - SSH: ', this.state.selectedStartHour);
        // console.log('------PV---------RETRIEVE HF - SEH: ', this.state.selectedEndHour);

        // console.log('----------ENDVN-2-> presidentJuryNotEns - 1:' + this.state.statusValidateAndNotify);

        /*
        console.log('----------------plan---------> presidentJuryNotEns - 1:' + this.state.presidentJuryNotEns);
        if(resultDPEPL.presidentJuryEns !== null)
        {
            console.log('---------plan----------------> presidentJuryEns - 2:' + this.state.presidentJuryEns);
        }
        if(resultDPEPL.membre !== null)
        {
            console.log('----plan---------------------> expertEns - 3:' + this.state.expertEns);
        }
        console.log('-----plan--------------------> salle - 4:' + this.state.salle);
        */
    }

    gotStudentSTNAffectationDetails(idEt) {

        // this.setState({ formattedSelectedDate: this.gotFormattedDate(new Date())});

        // console.log('------------------------------------- formattedSelectedDate 3--->' + this.state.formattedSelectedDate);

        var requestStnpl = new XMLHttpRequest();
        requestStnpl.open(
            "GET",
            API_URL_MESP + "fichePFEPlanifySoutenance/" +
            idEt,
            false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestStnpl.send(null);
        let resultDPEPL = JSON.parse(requestStnpl.responseText);
        //this.state.presidentJury = resultDPES.presidentJuryNotEns;
        /*console.log("--------------PLAN--------->RES-1: " + resultDPEPL.presidentJuryNotEns);

          if(resultDPEPL.presidentJuryEns !== null)
          {
            console.log("--------------PLAN--------->RES-2: " + resultDPEPL.presidentJuryEns.nomEns);
          }


          if(resultDPEPL.membre !== null)
          {
            console.log("--------------PLAN--------->RES-3: " + resultDPEPL.membre.nomEns);
          }

        console.log("--------------PLAN--------->RES-4: " + resultDPEPL.salle);
    */


        // this.state.presidentJury = resultDPES.presidentJuryNotEns;
        if (resultDPEPL.presidentJuryNotEns === null && resultDPEPL.presidentJuryEns === null) {
            this.setState({affectationPresidentJury: "NOTYET"})
        }
        if (resultDPEPL.presidentJuryNotEns !== null) {
            this.setState({
                affectationPresidentJury: "DONE",
                presidentJuryNotEns: resultDPEPL.presidentJuryNotEns
            })
        }

        if (resultDPEPL.presidentJuryEns !== null) {
            this.setState({
                affectationPresidentJury: "DONE",
                affectedJP: resultDPEPL.presidentJuryEns,
            })
        }

        if (resultDPEPL.membre !== null) {
            this.setState({
                affectedEXP: resultDPEPL.membre,
            })
        }

        if (resultDPEPL.salle !== null) {
            this.setState({salle: resultDPEPL.salle})
            this.state.salle = resultDPEPL.salle;
        }

    }

    handleAffectExpertEns(index, idEns) {
        /*console.log(
          "--------------------------> IDS: " +
            index +
            " - " +
            this.state.idEtToSTN +
            " - " +
            idEns
        );*/

        let soutenanceDate = this.gotFormattedDate(this.state.selectedDate);

        AuthService.affectExpertEns(
            this.state.idEtToSTN,
            idEns,
            soutenanceDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour
        ).then(
            (response) => {
                // console.log("Check Disponibility -----exper-1 ----TextScroller.js--- SUCCESS", index);
                let teachersList = this.state.listOfAvailableExpertsToSTN.slice();
                // console.log("Check Disponibility -----exper-2 -----TextScroller.js-- SUCCESS", teachersList[index].identifiant);

                // console.log("Check Disponibility ----- AFF-HELLO-EXP ----- SUCCESS", teachersList[index]);

                this.setState({
                    affectedEXP: this.gotTeacher(idEns),
                });

                teachersList.splice(index, 1);
                this.setState({
                    listOfAvailableExpertsToSTN: teachersList,
                    successAffectExpertPopup: true,
                    disabledSTNTime: true,
                });
                // console.log("Check Disponibility -----exper-3 -----TextScroller.js-- SUCCESS", this.state.affectedEXP.nom);
                // this.state.expertEns = teachersList[index].idEns;
                /*this.state.expertEns = "LOL 222";
                console.log("Check Disponibility -----exper-4 -----TextScroller.js-- SUCCESS", this.state.expertEns);*/
            },
            (error) => {
                // console.log("Check Disponibility ------------- FAIL");
            }
        );
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

    handleAffectJuryPresidentNotEns(nomJuryPresident) {
        /*console.log(
          "--------------------------> nomJuryPresident: " + nomJuryPresident
        );*/

        let soutenanceDate = this.gotFormattedDate(this.state.selectedDate);

        AuthService.affectJuryPresidentNotEns(
            this.state.idEtToSTN,
            nomJuryPresident,
            soutenanceDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour
        ).then(
            (response) => {
                // console.log("Check Disponibility -----NOT ENS-------- SUCCESS");
                this.setState({
                    successAffectJPNEnsPopup: true,
                    affectationPresidentJury: "DONE",
                    presidentJuryNotEns: nomJuryPresident,
                    disabledSTNTime: true
                });
            },
            (error) => {
                // console.log("Check Disponibility -------NOT ENS------ FAIL");
            }
        );
    }

    handleClosePopupAffectSoutenance() {
        this.setState({
            openPopupAffectSoutenance: false,
            disponibilityPE: "",
            idEtToSTN: "",
            refuseEndHour: false,
            selectedDate: new Date(),
            loadingListOfAvTsToSTN: false,
            loadingListOfAvExpsToSTN: false,
            loadingListOfAvCsToSTN: false,
            listOfAvailableTeachersToSTN: [],
            listOfAvailableExpertsToSTN: [],
            listOfAvailableClassroomsToSTN: [],
            pairFullName: ""
        });
    }

    handleClosePopupShowPV() {
        this.setState({openPopupShowPV: false});
    }

    handleOpenPopupArchiveSoutenance(idEt, fullName) {
        this.setState({
            idEtToSTN: idEt,
            openPopupArchiveSoutenance: true,
            selectedStudentFullName: fullName
        });
    }

    handleClosePopupArchiveSoutenance() {
        this.setState({openPopupArchiveSoutenance: false});
    }

    handleClosePopupSuccessArchiveSoutenance() {
        this.setState({openPopupSuccessArchiveSoutenance: false});
    }

    handleClosePopupShowStudentDetails() {
        this.setState({openPopupShowStudentDetails: false});
    }

    handleCloseSuccessAffectJPEnsPopup() {
        this.setState({successAffectJPEnsPopup: false});
    }

    handleCloseSuccessValidateAndNotifyPopup() {
        this.setState({successValidateAndNotifyPopup: false});
    }

    handleCloseSuccessAffectSallePopup() {
        this.setState({successAffectSallePopup: false});
    }

    handleCloseSuccessAffectJPNEnsPopup() {
        this.setState({successAffectJPNEnsPopup: false});
    }

    handleCloseSuccessAffectExpertPopup() {
        this.setState({successAffectExpertPopup: false});
    }

    onChangeAuthorizedStudentsToSTNByFilter(filtredItems) {
        this.setState({listOfAuthorizedStudentsToSTNByFilter: []})

        // console.log("----filter---------> THIS-1: ", filtredItems);
        // console.log("------filter-------> THIS-2: " + filtredItems.length + " - " + this.state.listOfAuthorizedStudentsToSTN.length);

        let filteredIdentifiants = [];
        if (filtredItems.length !== this.state.listOfAuthorizedStudentsToSTN.length) {
            for (let item of filtredItems) {
                filteredIdentifiants.push(item.identifiant);
            }
            // console.log("Check Disponibility --excel------TextScroller.js-------------> THIS-2: ", filteredIdentifiants);

            this.setState({
                listOfAuthorizedStudentsToSTNByFilter: filteredIdentifiants,
                planifyKind: "PWFILTER",
            })
        }

    }

    onChangeDateSoutenance = (date) => {
        this.setState({
            selectedDate: date,
            selectedStartHour: "00:00",
            selectedEndHour: "00:00",
            formattedSelectedDate: this.gotFormattedDate(date),
        });

        // console.log("Check Disponibility -------Retrieve--1---- TextScroller.js: " + date);

        // console.log("Check Disponibility ---------1---- TextScroller.js: " + this.state.selectedStartHour);

        this.state.selectedDate = date;
        this.state.formattedSelectedDate = this.gotFormattedDate(date);
        this.state.selectedStartHour = "00:00";
        this.state.selectedEndHour = "00:00";

        // console.log('------------------------------------- formattedSelectedDate 4--->' + this.state.formattedSelectedDate);
        /*console.log(
          "Check Disponibility ----------2--- TextScroller.js: " +
            this.state.selectedStartHour +
            " - " +
            this.state.selectedEndHour
        );*/

    };

    checkDisponibilityTeacher() {
        // console.log('--------------------------------------- PJ Treatment A: ' + new Date());
        this.setState({
            listOfAvailableTeachersToSTN: [],
            loadingListOfAvTsToSTN: true,
        });

        let formattedDate = this.gotFormattedDate(this.state.selectedDate);
        // console.log('--------------------------------------- PJ Treatment B: ' + new Date());
        AuthService.findTeachersDisponibility(
            formattedDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour,
            this.state.pedagogicalEncadrant.identifiant
        ).then(
            (response) => {
                // console.log('--------------------------------------- PJ Treatment C: ' + new Date());
                let teachersList = this.state.listOfAvailableTeachersToSTN.slice();
                for (let tea of response.data) {
                    //teachersList.push(tea);
                    teachersList.push({identifiant: tea.identifiant, up: tea.up, nom: tea.nom, action: ""});
                    //console.log('----Treatment: ', tea);
                }
                this.setState({
                    listOfAvailableTeachersToSTN: teachersList,
                    loadingListOfAvTsToSTN: false,
                });
                // console.log('--------------------------------------- PJ Treatment D: ' + new Date());
            },
            (error) => {
                // console.log("Check Disponibility ------------- FAIL");
            }
        );
    }

    gotTeacher(id) {
        var requestStn = new XMLHttpRequest();
        requestStn.open(
            "GET",
            API_URL_MESP + "SimpleFormExample.js/" + id,
            false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestStn.send(null);
        // console.log('--------------------------------------- gotTeacher: ', JSON.parse(requestStn.responseText));
        return JSON.parse(requestStn.responseText);
    }

    checkDisponibilityExpert() {
        // console.log('--------------------------------------- EXP Treatment A: ' + new Date());
        this.setState({
            listOfAvailableExpertsToSTN: [],
            loadingListOfAvExpsToSTN: true,
        });


        let formattedDate = this.gotFormattedDate(this.state.selectedDate);
        // console.log('--------------------------------------- EXP Treatment B: ' + new Date());
        AuthService.findTeachersDisponibility(
            formattedDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour,
            this.state.pedagogicalEncadrant.identifiant
        ).then(
            (response) => {
                // console.log('--------------------------------------- EXP Treatment C: ' + new Date());
                let teachersList = this.state.listOfAvailableExpertsToSTN.slice();
                for (let tea of response.data) {
                    teachersList.push({identifiant: tea.identifiant, up: tea.up, nom: tea.nom, action: ""});
                }
                this.setState({
                    listOfAvailableExpertsToSTN: teachersList,
                    loadingListOfAvExpsToSTN: false,
                });
                // console.log('--------------------------------------- EXP Treatment D: ' + new Date());
            },
            (error) => {
                // console.log("Check Disponibility ------------- FAIL");
            }
        );

    }

    checkDisponibilityClassroom() {
        // console.log('--------------------------------------- S Treatment A: ' + new Date());
        this.setState({
            listOfAvailableClassroomsToSTN: [],
            loadingListOfAvCsToSTN: true,
        });

        let formattedDate = this.gotFormattedDate(this.state.selectedDate);
        // console.log('--------------------------------------- S Treatment B: ' + new Date());
        AuthService.findClassroomsDisponibility(
            formattedDate,
            this.state.selectedStartHour,
            this.state.selectedEndHour
        ).then(
            (response) => {
                // console.log('--------------------------------------- S Treatment C: ' + new Date());
                let crsList = this.state.listOfAvailableClassroomsToSTN.slice();
                for (let cr of response.data) {
                    crsList.push({codeSalle: cr, action: "Pending"});
                }
                this.setState({
                    listOfAvailableClassroomsToSTN: crsList,
                    loadingListOfAvCsToSTN: false,
                });
                // console.log('--------------------------------------- S Treatment D: ' + new Date());
            },
            (error) => {
                // console.log("Check Disponibility -------cls------ FAIL");
            }
        );
    }

    render() {
        const {
            listOfAuthorizedStudentsToSTN,
            openAskMakeSTNModif,
            openPopupSuccessArchiveSoutenance,
            openPopupAffectSoutenance,
            openPopupShowPV,
            openPopupArchiveSoutenance,
            listOfAvailableTeachersToSTN,
            listOfAvailableClassroomsToSTN,
            affectationKind,
            successAffectJPEnsPopup,
            successAffectJPNEnsPopup,
            successAffectExpertPopup,
            disponibilityPE,
            selectedDate,
            successAffectSallePopup,
            affectedEXP,
            affectationPresidentJury,
            listOfAvailableExpertsToSTN,
            valAndNot,
            successValidateAndNotifyPopup,
            refuseEndHour,
            statusValidateAndNotify,
            loadingValidateAndNotify,
            idEtToSTN,
            selectedStudentFullName,
            pedagogicalEncadrant,
            presidentJuryNotEns,
            salle,
            formattedSelectedDate,
            selectedStartHour,
            selectedEndHour,
            disabledSTNTime,
            loadingListOfAvTsToSTN,
            loadingListOfAvExpsToSTN,
            loadingListOfAvCsToSTN,
            listOfAuthorizedStudentsToSTNByFilter,
            showLoadingExcelBlueIcon,
            showLoadingExcelGreenIcon,
            traineeKind,
            affectedJP,
            selectedStudentMail,
            selectedStudentTel,
            openPopupShowStudentDetails,
            showSpinnerListAutorizedStudents,
            loadList,
            allTraineeshipKinds,
            pairFullName
        } = this.state;

        const generate = (element) => {
            return [0, 1, 2].map((value) =>
                React.cloneElement(element, {
                    key: value,
                })
            );
        };

        return (
            <>

                <TheSidebar data1={this.state.nbrNotifiedSTN} data2={this.state.nbrNotPlanifiedSTN}/>

                {(
                    loadList ?
                        <CRow>
                            <CCol xs="12">
                                <center>
                                    <br/><br/><br/><br/><br/>
                                    <span className="waitIcon"/>
                                </center>
                            </CCol>
                        </CRow>
                        :
                        <>
                            <CRow>
                                <CCol>
                                    <CCard>
                                        <CCardHeader>
                                            <CContainer>

                                                <CRow>
                                                    <CCol md="10" class="bg-success py-2">
                        <span style={{color: "#cc0000", fontSize: "14px", fontWeight: "bold"}}>
                          Liste des Étudiants avec Soutenances Planifiées & Notifiées - ({listOfAuthorizedStudentsToSTNByFilter.length}/{listOfAuthorizedStudentsToSTN.length})
                        </span><br/><br/>
                                                    </CCol>
                                                    <CCol md="1" className="my-auto text-right">
                                                        <CRow>
                                                            {(
                                                                listOfAuthorizedStudentsToSTNByFilter.length > 0 && !showLoadingExcelBlueIcon &&
                                                                <CButton shape="pill"
                                                                         onClick={() => this.handleDownloadPlanningSoutenanceByFilter()}>
                                                                    <CTooltip
                                                                        content="Télécharger Planning Soutenance suivant Filtre"
                                                                        placement="top">
                                                                        <img src={excelBlueIcon}
                                                                             className="cursorPointer"
                                                                             width="40px" height="40px"/>
                                                                    </CTooltip>
                                                                </CButton>
                                                            )}
                                                            {(
                                                                listOfAuthorizedStudentsToSTNByFilter.length > 0 && showLoadingExcelBlueIcon &&
                                                                <Spinner animation="grow" variant="primary"/>
                                                            )}
                                                        </CRow>
                                                    </CCol>
                                                    <CCol md="1" className="my-auto text-right">
                                                        <CRow>
                                                            {(
                                                                !showLoadingExcelGreenIcon && listOfAuthorizedStudentsToSTN.length > 0 &&
                                                                <CButton shape="pill"
                                                                         onClick={() => this.handleDownloadAllPlanningSoutenanceExcel()}>
                                                                    <CTooltip content="Télécharger Planning Soutenance"
                                                                              placement="top">
                                                                        <img src={excelGreenIcon}
                                                                             className="cursorPointer"
                                                                             width="40px" height="40px"/>
                                                                    </CTooltip>
                                                                </CButton>
                                                            )}
                                                            {(
                                                                showLoadingExcelGreenIcon &&
                                                                <Spinner animation="grow" variant="success"/>
                                                            )}
                                                        </CRow>
                                                    </CCol>
                                                </CRow>
                                            </CContainer>
                                        </CCardHeader>
                                        <CCardBody>
                                            <CDataTable
                                                items={listOfAuthorizedStudentsToSTN}
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
                                                onFilteredItemsChange={(event) => {
                                                    this.onChangeAuthorizedStudentsToSTNByFilter(event)
                                                }}
                                                noItemsContent='azerty'
                                                scopedSlots={{
                                                    action: (item, index) => (
                                                        <td>
                                                            <center>
                                                                <CButton shape="pill"
                                                                         color="dark"
                                                                         size="sm"
                                                                         aria-expanded="true"
                                                                         onClick={() => this.handleConsultStudentDetails(item.identifiant, item.fullName)}>
                                                                    <CTooltip content="Consulter Contacts Étudiant"
                                                                              placement="top">
                                                                        <CIcon content={freeSet.cilPhone}/>
                                                                    </CTooltip>
                                                                </CButton>
                                                                &nbsp;

                                                                <CButton shape="pill"
                                                                         color="danger"
                                                                         size="sm"
                                                                         aria-expanded="true"
                                                                         onClick={() => this.handleConsultPV(item.identifiant, item.fullName, item.classe)}>
                                                                    <CTooltip content="Consulter PV" placement="top">
                                                                        <CIcon content={freeSet.cilContact}/>
                                                                    </CTooltip>
                                                                </CButton>
                                                                &nbsp;
                                                                {
                                                                    (
                                                                        currentResponsableServiceStage.id === 'SR-STG-IT' ||
                                                                        currentResponsableServiceStage.id === 'SR-STG-EM' ||
                                                                        currentResponsableServiceStage.id === 'SR-STG-GC'
                                                                    ) &&
                                                                        <>
                                                                            <CButton shape="pill"
                                                                                     color="warning"
                                                                                     size="sm"
                                                                                     aria-expanded="true"
                                                                                     onClick={() => this.handleOpenPopupArchiveSoutenance(item.identifiant, item.fullName, item.classe)}>
                                                                                <CTooltip content="Rendre Soutenance Modifiable" placement="top">
                                                                                    <CIcon content={freeSet.cilPen}/>
                                                                                </CTooltip>
                                                                            </CButton>
                                                                        </>
                                                                }

                                                                {(
                                                                    item.etatTreatDepotFichePFE === '02' &&
                                                                    <>
                                                                        &nbsp;
                                                                        <CButton shape="pill"
                                                                                 color="warning"
                                                                                 size="sm"
                                                                                 aria-expanded="true"
                                                                                 onClick={() => this.handleDownloadFicheDepotPFE(item.identifiant, item.fullName, item.classe)}>
                                                                            <CTooltip
                                                                                content="Télécharger Fiche Dépôt PFE"
                                                                                placement="top">
                                                                                <CIcon
                                                                                    content={freeSet.cilCloudDownload}/>
                                                                            </CTooltip>
                                                                        </CButton>
                                                                    </>
                                                                )}


                                                            </center>
                                                        </td>
                                                    ),
                                                }}
                                            />
                                        </CCardBody>
                                    </CCard>
                                </CCol>
                            </CRow>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="md"
                                keepMounted
                                open={openPopupAffectSoutenance}
                                onClose={this.handleClosePopupAffectSoutenance}
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                            <span className="text-primary" style={{fontSize: "19px", fontWeight: "bold"}}>
                              Planifier Soutenance
                            </span>
                                    &nbsp; - &nbsp;
                                    <span className="text-info" style={{fontSize: "15px"}}>
                              {selectedStudentFullName}
                            </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="4">
                                <span className="convFieldLibelle">
                                  Date Soutenance
                                </span>
                                            <span className="requiredStar">
                                  &nbsp;(*) :
                                </span>
                                        </CCol>

                                        <CCol xs="8">
                                            <DatePicker
                                                selected={this.state.selectedDate}
                                                onChange={this.onChangeDateSoutenance}
                                                isClearable
                                                className="form-control"
                                                dateFormat="dd-MM-yyyy"
                                                disabled={disabledSTNTime}
                                            />
                                            {this.state.selectedDate === null && (
                                                <div
                                                    className="alert alert-danger"
                                                    role="alert"
                                                >
                                                    This field is required !.
                                                </div>
                                            )}
                                        </CCol>
                                    </CRow>
                                    <br/>
                                    <CRow>
                                        <CCol xs="4">
                                            <span className="convFieldLibelle">Horaire Soutenance</span>
                                            <span className="requiredStar">
                                  &nbsp;(*) :
                                </span>
                                        </CCol>
                                        <br/>
                                        <CCol xs="4">
                                            <TextField
                                                id="time"
                                                label="Heure Début"
                                                type="time"
                                                defaultValue={selectedStartHour}
                                                value={this.state.selectedStartHour}
                                                className={timeStyle}
                                                InputLabelProps={{shrink: true}}
                                                inputProps={{step: 300}}
                                                onChange={(e) => this.handleChangeStartHour(e)}
                                                disabled={disabledSTNTime}
                                            />
                                        </CCol>

                                        <CCol xs="4">
                                            <TextField
                                                id="time"
                                                label="Heure Fin"
                                                type="time"
                                                value={this.state.selectedEndHour}
                                                className={timeStyle}
                                                InputLabelProps={{shrink: true}}
                                                inputProps={{step: 600}}
                                                onChange={(e) => this.handleChangeEndHour(e)}
                                                disabled={disabledSTNTime}
                                            />
                                            {(
                                                refuseEndHour &&
                                                <div>
                                                    <br/>
                                                    <div className="alert alert-danger" role="alert">
                                                        Heure Fin doit être supérieure à Heure Début.
                                                    </div>
                                                </div>
                                            )}
                                        </CCol>
                                    </CRow>
                                    <br/>
                                    <CRow>
                                        <CCol xs="11"/>
                                        <CCol xs="1">
                                            <CButton shape="pill" color="primary" aria-expanded="true"
                                                     onClick={() => this.handleInitilizeSTNTime()}>
                                                <CTooltip content="Reset To Modify" placement="top">
                                                    <CIcon content={freeSet.cilReload}/>
                                                </CTooltip>
                                            </CButton>
                                        </CCol>
                                    </CRow>

                                    <CCardBody>
                                        <CTabs>
                                            <CNav variant="tabs">
                                                <CNavItem>
                                                    <CNavLink>Encadrant Pédagogique</CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink>Membre</CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink>Président Jury</CNavLink>
                                                </CNavItem>
                                                <CNavItem>
                                                    <CNavLink>Salle</CNavLink>
                                                </CNavItem>


                                                <CNavItem>
                                                    <CNavLink>
                                                        <CIcon content={freeSet.cilBell}/>
                                                        Validation & Notification
                                                    </CNavLink>
                                                </CNavItem>


                                            </CNav>
                                            <CTabContent>
                                                <CTabPane>
                                                    <div>
                                                        <br/>
                                                        Encadrant Pédagogique: &nbsp;&nbsp;
                                                        <span className="text-info">
                                          {
                                              this.state.pedagogicalEncadrant.nom
                                          }
                                        </span>
                                                        <br/>

                                                        {disponibilityPE === "YES" && (
                                                            <div>
                                                                <span>Disponibilité: </span>
                                                                <span className="text-success">
                                              Oui
                                            </span>
                                                            </div>
                                                        )}
                                                        {disponibilityPE === "NO" && (
                                                            <div>
                                                                <span>Disponibilité: </span>
                                                                <span className="text-danger">
                                              Non
                                            </span>
                                                            </div>
                                                        )}
                                                        <br/><br/>
                                                        {this.state.selectedStartHour !== undefined &&
                                                        this.state.selectedStartHour.length !== 0 &&
                                                        this.state.selectedEndHour.length !== 0 && !refuseEndHour && (
                                                            <Fab
                                                                variant="extended"
                                                                size="small"
                                                                color="primary"
                                                                aria-label="add"
                                                                onClick={() => this.checkDisponibilityPedagogicalEncadrant()}
                                                            >
                                                                <NavigationIcon/>
                                                                Check Disponibility EP
                                                            </Fab>
                                                        )}

                                                    </div>
                                                </CTabPane>
                                                <CTabPane>
                                                    <div>
                                                        <br/>
                                                        {
                                                            affectedEXP.identifiant === undefined &&
                                                            (
                                                                <div>
                                                <span className="text-danger">
                                                  Membre pas encore affecté !.
                                                </span>
                                                                </div>
                                                            )
                                                        }
                                                        {
                                                            affectedEXP.identifiant !== undefined &&
                                                            (
                                                                <div>
                                              <span className="text-success">
                                                  Membre affecté :
                                                </span>
                                                                    <br/>
                                                                    <br/>
                                                                    <CRow>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Identifiant:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {affectedEXP.identifiant}
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Nom & Prénom:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {affectedEXP.nom}
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Unité Péda.:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedEXP.up ?
                                                                                    <>{affectedEXP.up}</>
                                                                                    :
                                                                                    <>--</>
                                                                            }
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    E-Mail:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedEXP.mail ?
                                                                                    <>{affectedEXP.mail}</>
                                                                                    :
                                                                                    <>--</>
                                                                            }
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Téléphone:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedEXP.téléphone ?
                                                                                    <>{affectedEXP.téléphone}</>
                                                                                    :
                                                                                    <>--</>
                                                                            }
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Type:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedEXP.type === "P" ?
                                                                                    <>Permanant</>
                                                                                    :
                                                                                    <>Vacataire</>
                                                                            }
                                                                        </CCol>
                                                                    </CRow>


                                                                </div>
                                                            )
                                                        }

                                                        <br/>
                                                        {this.state.selectedStartHour !==
                                                        undefined &&
                                                        this.state.selectedStartHour
                                                            .length !== 0 &&
                                                        this.state.selectedEndHour.length !== 0 && !refuseEndHour &&
                                                        affectedEXP.identifiant === undefined && (
                                                            <div
                                                                style={{
                                                                    width: 450,
                                                                    textAlign: "left",
                                                                }}>
                                                                <div>
                                                                    <Fab
                                                                        variant="extended"
                                                                        size="small"
                                                                        color="primary"
                                                                        aria-label="add"
                                                                        onClick={() => {
                                                                            this.checkDisponibilityExpert()
                                                                        }}
                                                                        disabled={loadingListOfAvExpsToSTN}
                                                                    >
                                                                        <NavigationIcon/>
                                                                        Check Disponibility Exp -&nbsp;
                                                                        {
                                                                            listOfAvailableExpertsToSTN.length
                                                                        }
                                                                    </Fab>
                                                                </div>
                                                            </div>
                                                        )}

                                                        <br/>

                                                        <br/>
                                                        <br/>
                                                        {loadingListOfAvExpsToSTN && (
                                                            <div>
                                                                <center>
                                                                    <Spinner
                                                                        animation="grow"
                                                                        variant="danger"
                                                                    />
                                                                </center>
                                                            </div>
                                                        )}
                                                        {listOfAvailableExpertsToSTN.length !==
                                                        0 && affectedEXP.identifiant === undefined && (
                                                            <CDataTable
                                                                items={
                                                                    listOfAvailableExpertsToSTN
                                                                }
                                                                fields={fieldAvExps}
                                                                columnFilter
                                                                hover
                                                                sorter
                                                                striped
                                                                bordered
                                                                size="sm"
                                                                itemsPerPage={5}
                                                                pagination
                                                                scopedSlots={{
                                                                    action: (item, index) => (
                                                                        <tr key={index}>
                                                                            <td>
                                                                                <CButton shape="pill"
                                                                                         color="primary"
                                                                                         aria-expanded="true"
                                                                                         onClick={() =>
                                                                                             this.handleAffectExpertEns(
                                                                                                 index,
                                                                                                 item.identifiant
                                                                                             )
                                                                                         }
                                                                                >
                                                                                    <CTooltip content="Affecter Membre"
                                                                                              placement="top">
                                                                                        <CIcon
                                                                                            content={freeSet.cilUserFollow}/>
                                                                                    </CTooltip>
                                                                                </CButton>
                                                                            </td>
                                                                        </tr>
                                                                    ),
                                                                }}
                                                            />
                                                        )}
                                                    </div>
                                                </CTabPane>
                                                <CTabPane>
                                                    <div>
                                                        <br/>
                                                        {
                                                            affectationPresidentJury.includes('DONE') && presidentJuryNotEns !== '' &&
                                                            (
                                                                <div>
                                              <span className="text-success">
                                                  Président Jury affecté (Affectation Externe) :
                                                </span>
                                                                    <br/>
                                                                    <span className="text-info">
                                                {presidentJuryNotEns}
                                              </span>

                                                                </div>
                                                            )
                                                        }
                                                        {
                                                            affectationPresidentJury.includes('DONE') && affectedJP !== null && presidentJuryNotEns === '' &&
                                                            (
                                                                <div>


                                              <span className="text-success">
                                                  Président Jury affecté (Affectation Interne) :
                                                </span>
                                                                    <br/><br/>

                                                                    <CRow>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Identifiant:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {affectedJP.identifiant}
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Nom & Prénom:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {affectedJP.nom}
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Unité Péda.:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedJP.up ?
                                                                                    <>{affectedJP.up}</>
                                                                                    :
                                                                                    <>--</>
                                                                            }
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    E-Mail:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedJP.mail ?
                                                                                    <>{affectedJP.mail}</>
                                                                                    :
                                                                                    <>--</>
                                                                            }
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Téléphone:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedJP.téléphone ?
                                                                                    <>{affectedJP.téléphone}</>
                                                                                    :
                                                                                    <>--</>
                                                                            }
                                                                        </CCol>
                                                                        <CCol lg="2" class="bg-success py-2">
                                                  <span className="text-info">
                                                    Type:
                                                  </span>
                                                                        </CCol>
                                                                        <CCol md="10" class="bg-warning py-2">
                                                                            {
                                                                                affectedJP.type === "P" ?
                                                                                    <>Permanant</>
                                                                                    :
                                                                                    <>Vacataire</>
                                                                            }
                                                                        </CCol>
                                                                    </CRow>

                                                                </div>
                                                            )
                                                        }
                                                        {
                                                            affectationPresidentJury.includes('NOTYET') &&
                                                            (
                                                                <div>
                                                <span className="text-danger">
                                                  Président Jury pas encore affecté !.
                                                </span>
                                                                </div>
                                                            )
                                                        }

                                                        <br/>
                                                        {this.state.selectedStartHour !==
                                                        undefined &&
                                                        this.state.selectedStartHour
                                                            .length !== 0 &&
                                                        this.state.selectedEndHour.length !== 0 && !refuseEndHour &&
                                                        affectationPresidentJury.includes('NOTYET') && (
                                                            <div
                                                                style={{
                                                                    width: 450,
                                                                    textAlign: "left",
                                                                }}
                                                            >
                                                                <div className="radio-toolbar">
                                                                    {this.state.allTAffectationKinds.map(
                                                                        (option, i) => {
                                                                            return (
                                                                                <label key={i}>
                                                                                    <input
                                                                                        type="radio"
                                                                                        defaultValue={
                                                                                            this.state
                                                                                                .affectationKind
                                                                                        }
                                                                                        checked={
                                                                                            this.state
                                                                                                .checked === i
                                                                                                ? true
                                                                                                : false
                                                                                        }
                                                                                        key={i + 100}
                                                                                        onChange={this.onChangeAffectationKind.bind(
                                                                                            this,
                                                                                            i,
                                                                                            option.label
                                                                                        )}
                                                                                        value={i}
                                                                                    />
                                                                                    {option.label}
                                                                                </label>
                                                                            );
                                                                        }
                                                                    )}
                                                                </div>
                                                            </div>
                                                        )}

                                                        {this.state.selectedStartHour !==
                                                        undefined &&
                                                        this.state.selectedStartHour
                                                            .length !== 0 &&
                                                        this.state.selectedEndHour.length !== 0 &&
                                                        affectationKind.trim() === "Affectation Interne" &&
                                                        affectationPresidentJury.includes('NOTYET') && (
                                                            <div>
                                                                <br/>
                                                                <Fab
                                                                    variant="extended"
                                                                    size="small"
                                                                    color="primary"
                                                                    aria-label="add"
                                                                    onClick={() => {
                                                                        this.checkDisponibilityTeacher()
                                                                    }}
                                                                    disabled={loadingListOfAvTsToSTN}
                                                                >
                                                                    <NavigationIcon/>
                                                                    check Disponibility PJ -&nbsp;
                                                                    {
                                                                        listOfAvailableTeachersToSTN.length
                                                                    }
                                                                </Fab>

                                                                <br/>
                                                                <br/>
                                                                <br/>
                                                                {loadingListOfAvTsToSTN && (
                                                                    <div>
                                                                        <center>
                                                                            <Spinner
                                                                                animation="grow"
                                                                                variant="danger"
                                                                            />
                                                                        </center>
                                                                    </div>
                                                                )}

                                                                {
                                                                    listOfAvailableTeachersToSTN.length !== 0
                                                                    && affectationPresidentJury.includes('NOTYET') && (
                                                                        <div>
                                                                            <CDataTable
                                                                                items={
                                                                                    listOfAvailableTeachersToSTN
                                                                                }
                                                                                fields={fieldAvTs}
                                                                                columnFilter
                                                                                hover
                                                                                sorter
                                                                                striped
                                                                                bordered
                                                                                size="sm"
                                                                                itemsPerPage={5}
                                                                                pagination
                                                                                scopedSlots={{
                                                                                    action: (item, index) => (
                                                                                        <tr key={index}>
                                                                                            <td>

                                                                                                <CButton shape="pill"
                                                                                                         color="primary"
                                                                                                         aria-expanded="true"
                                                                                                         onClick={() =>
                                                                                                             this.handleAffectJuryPresidentEns(
                                                                                                                 index,
                                                                                                                 item.identifiant
                                                                                                             )
                                                                                                         }
                                                                                                >
                                                                                                    <CTooltip
                                                                                                        content="Affecter Président Jury"
                                                                                                        placement="top">
                                                                                                        <CIcon
                                                                                                            content={freeSet.cilUserFollow}/>
                                                                                                    </CTooltip>
                                                                                                </CButton>
                                                                                            </td>

                                                                                        </tr>
                                                                                    ),
                                                                                }}
                                                                            />

                                                                        </div>
                                                                    )}
                                                            </div>
                                                        )}
                                                        {affectationKind.trim() ===
                                                        "Affectation Externe" && affectationPresidentJury.includes('NOTYET') && (
                                                            <div>
                                                                <TextField
                                                                    autoFocus
                                                                    margin="dense"
                                                                    id="fullNameJuryPresidentNotEns"
                                                                    label="Nom & Prénom Président Jury"
                                                                    fullWidth
                                                                    defaultValue={
                                                                        this.state.fullNameJuryPresidentNotEns
                                                                    }
                                                                    onChange={(event) => {
                                                                        const {value} = event.target;
                                                                        this.setState({
                                                                            fullNameJuryPresidentNotEns: value,
                                                                        });
                                                                    }}
                                                                />
                                                                <br/>
                                                                <br/>
                                                                <Button
                                                                    variant="outlined"
                                                                    color="primary"
                                                                    disabled={
                                                                        this.state.fullNameJuryPresidentNotEns
                                                                            .length < 5 ||
                                                                        this.state.fullNameJuryPresidentNotEns
                                                                            .length > 20
                                                                    }
                                                                    onClick={() =>
                                                                        this.handleAffectJuryPresidentNotEns(
                                                                            this.state
                                                                                .fullNameJuryPresidentNotEns
                                                                        )
                                                                    }
                                                                >
                                                                    Affecter NE
                                                                </Button>

                                                            </div>
                                                        )}

                                                    </div>
                                                </CTabPane>
                                                <CTabPane>
                                                    <div>
                                                        <br/>
                                                        <CRow>
                                                            <CCol md="8">
                                                                Cet Étudiant passe sont PFE <span
                                                                className="text-info">{traineeKind}</span>
                                                                <br/><br/>
                                                                {salle !== "" && traineeKind === "En Tunisie" &&
                                                                (
                                                                    <div>

                                                  <span className="text-success">
                                                    Salle affectée :
                                                  </span>
                                                                        <br/>
                                                                        <span className="text-info">
                                                    {salle}
                                                  </span>

                                                                    </div>
                                                                )
                                                                }
                                                                {salle === "" && traineeKind === "En Tunisie" &&
                                                                (
                                                                    <div>
                                                  <span className="text-danger">
                                                    Salle pas encore affectée !.
                                                  </span>
                                                                    </div>
                                                                )
                                                                }
                                                            </CCol>
                                                            <CCol md="4">
                                                                Mettre à jour Lieu de Stage du PFE:
                                                                <br/>
                                                                <div className="radio-toolbar">
                                                                    {allTraineeshipKinds.map((option, i) => {
                                                                            return (
                                                                                <label key={i}>
                                                                                    <input type="radio"
                                                                                           defaultValue={this.state.traineeShipKind}
                                                                                           checked={this.state.checkedTraineeShip === i ? true : false}
                                                                                           key={i + 100}
                                                                                           onChange={this.onChangeTraineeShipKind.bind(this, i, option.label)}
                                                                                           value={i}/>
                                                                                    {option.label}
                                                                                </label>
                                                                            );
                                                                        }
                                                                    )}
                                                                </div>
                                                            </CCol>
                                                        </CRow>
                                                        <br/>
                                                        {
                                                            this.state.selectedStartHour !== undefined &&
                                                            this.state.selectedStartHour.length !== 0 &&
                                                            this.state.selectedEndHour.length !== 0 && !refuseEndHour &&
                                                            salle === "" &&

                                                            (
                                                                <Fab variant="extended"
                                                                     size="small"
                                                                     color="primary"
                                                                     aria-label="add"
                                                                     onClick={() => {
                                                                         this.checkDisponibilityClassroom()
                                                                     }}
                                                                     disabled={loadingListOfAvCsToSTN}
                                                                >
                                                                    <NavigationIcon/>

                                                                    Check Disponibility S - &nbsp;
                                                                    {
                                                                        listOfAvailableClassroomsToSTN.length
                                                                    }

                                                                </Fab>
                                                            )
                                                        }

                                                        <br/>
                                                        <br/>
                                                        <br/>
                                                        {loadingListOfAvCsToSTN && (
                                                            <div>
                                                                <center>
                                                                    <Spinner
                                                                        animation="grow"
                                                                        variant="danger"
                                                                    />
                                                                </center>
                                                            </div>
                                                        )}

                                                        {listOfAvailableClassroomsToSTN.length !== 0 && salle === "" && (
                                                            <CDataTable
                                                                items={
                                                                    listOfAvailableClassroomsToSTN
                                                                }
                                                                fields={fieldAvCs}
                                                                columnFilter
                                                                hover
                                                                sorter
                                                                striped
                                                                bordered
                                                                size="sm"
                                                                itemsPerPage={5}
                                                                pagination
                                                                scopedSlots={{
                                                                    action: (item, index) => (
                                                                        <tr key={index}>
                                                                            <td>

                                                                                <CButton shape="pill"
                                                                                         color="primary"
                                                                                         aria-expanded="true"
                                                                                         onClick={() =>
                                                                                             this.handleAffectClassroom(
                                                                                                 index,
                                                                                                 item.codeSalle
                                                                                             )
                                                                                         }
                                                                                >
                                                                                    <CTooltip content="Affecter Salle"
                                                                                              placement="top">
                                                                                        <CIcon
                                                                                            content={freeSet.cilRoom}/>
                                                                                    </CTooltip>
                                                                                </CButton>
                                                                            </td>
                                                                        </tr>
                                                                    ),
                                                                }}
                                                            />
                                                        )}
                                                    </div>
                                                </CTabPane>
                                                <CTabPane>
                                                    <br/>
                                                    {(
                                                        disponibilityPE === "YES" && affectedEXP.identifiant !== undefined && affectationPresidentJury.includes('DONE') && salle !== "" && !statusValidateAndNotify.includes('TRAITÉ') &&
                                                        <div>

                                                            {(
                                                                pairFullName === "" &&
                                                                <>
                                                                    La planification de soutenance pour l'étudiant
                                                                    <span
                                                                        className="text-info"> {selectedStudentFullName} </span>
                                                                </>
                                                            )}

                                                            {(
                                                                pairFullName !== "" &&
                                                                <>
                                                                    La planification de soutenance pour le binôme
                                                                    <span
                                                                        className="text-info"> {selectedStudentFullName} </span>
                                                                    et
                                                                    <span className="text-info"> {pairFullName} </span>
                                                                </>
                                                            )}


                                                            a été effectuée le <span
                                                            className="text-info"> {formattedSelectedDate} </span>
                                                            de <span className="text-info"> {selectedStartHour} </span>
                                                            à <span className="text-info"> {selectedEndHour} </span>

                                                            {(
                                                                salle !== "Remote" &&
                                                                <>
                                                                    <ins>en présentiel</ins>
                                                                    dans la salle <span
                                                                    className="text-info"> {salle} </span>.
                                                                </>
                                                            )}
                                                            {(
                                                                salle === "Remote" &&
                                                                <>
                                                                    <ins>à distance</ins>
                                                                    via <span className="text-info"> un Meet envoyé par Mail </span>.
                                                                </>
                                                            )}

                                                            <br/><br/>
                                                            Cliquer sur ce bouton pour confirmer cet état et notifier
                                                            l'Étudiant, l'Encadrant Pédagogique, le Membre et le
                                                            Président de Jury de la date programmée.
                                                            <br/><br/>
                                                            <Button variant="outlined"
                                                                    color="primary"
                                                                    onClick={this.validateAndNotify}
                                                                    disabled={loadingValidateAndNotify}>
                                                                Confirmer & Notifier
                                                            </Button>
                                                            <br/>
                                                            {(
                                                                loadingValidateAndNotify &&
                                                                <CRow>
                                                                    <CCol xs="12">
                                                                        <center>
                                                                            <CSpinner color="primary" style={{
                                                                                width: '4rem',
                                                                                height: '4rem'
                                                                            }}/>
                                                                        </center>
                                                                    </CCol>
                                                                </CRow>
                                                            )}
                                                        </div>
                                                    )}

                                                    {((affectedEXP.identifiant === undefined || !affectationPresidentJury === 'DONE' || salle === "") &&
                                                        <div>
                                                            La planification de soutenance
                                                            <span
                                                                className="text-danger"> n'est pas encore achevée</span>.
                                                        </div>
                                                    )}

                                                    {((disponibilityPE !== "YES" && affectedEXP.identifiant !== undefined && affectationPresidentJury === 'DONE' && salle !== "" && statusValidateAndNotify === 'NOTFINISH') &&
                                                        <div>
                                                            La planification de soutenance
                                                            <span
                                                                className="text-danger"> n'est pas encore achevée</span>.
                                                            <br/>
                                                            <div>
                                                                Il vous reste la <span className="text-warning"> vérification de la disponibilité de l'Encadrant Pédagogique </span>.
                                                            </div>
                                                        </div>
                                                    )}
                                                    {((disponibilityPE !== "YES" && affectedEXP.identifiant !== undefined && affectationPresidentJury === 'DONE' && salle !== "" && statusValidateAndNotify === 'TODO') &&
                                                        <div>
                                                            La planification de soutenance
                                                            <span
                                                                className="text-danger"> n'est pas encore achevée</span>.
                                                            <br/>
                                                            <div>
                                                                Il vous reste la <span className="text-warning"> vérification de la disponibilité de l'encadrant Pédagogique. </span>.
                                                            </div>
                                                        </div>
                                                    )}
                                                    {(
                                                        statusValidateAndNotify === 'TRAITÉ' &&
                                                        <div>
                                                            La soutenance planifiée <span className="text-success"> est confirmée</span> et
                                                            les Notifications aux membres de jury <span
                                                            className="text-success"> sont envoyées</span>.
                                                        </div>
                                                    )}

                                                    <br/>
                                                </CTabPane>
                                            </CTabContent>
                                        </CTabs>
                                    </CCardBody>
                                    <br/>
                                    <br/>
                                </DialogContent>
                                <DialogActions>
                                    <Button onClick={this.handleClosePopupAffectSoutenance} color="primary">
                                        Exit
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={
                                    successAffectJPEnsPopup
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessAffectJPEnsPopup
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Affectation du Président Jury (Enseignant) est réalisée.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessAffectJPEnsPopup
                                        }
                                        color="primary"
                                    >
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>
                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={
                                    successAffectJPNEnsPopup
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessAffectJPNEnsPopup
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Affectation du Président Jury (Non Enseignant) est réalisée.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessAffectJPNEnsPopup
                                        }
                                        color="primary"
                                    >
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={
                                    successAffectExpertPopup
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessAffectExpertPopup
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Affectation du Membre est réalisée.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessAffectExpertPopup
                                        }
                                        color="primary"
                                    >
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={
                                    successAffectSallePopup
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessAffectSallePopup
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Affectation de la Salle est réalisée.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessAffectSallePopup
                                        }
                                        color="primary"
                                    >
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>
                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={
                                    successAffectSallePopup
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessAffectSallePopup
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Affectation de la Salle est réalisée.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessAffectSallePopup
                                        }
                                        color="primary"
                                    >
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={openPopupSuccessArchiveSoutenance}
                                onClose={this.handleClosePopupSuccessArchiveSoutenance}
                                aria-labelledby="form-dialog-title"
                            >

                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                              Vous pouvez procéder à la modification de la Planification de Soutenance
                                                <br/>
                                                via le le Menu &nbsp;
                                                <span className="journalClignoteMark">
                                      Soutenances Planifiées
                                    </span>
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>

                                    <Button
                                        onClick={
                                            this
                                                .handleClosePopupSuccessArchiveSoutenance
                                        }
                                        color="primary"
                                    >
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog fullHight
                                    fullWidth
                                    maxWidth="sm"
                                    keepMounted
                                    open={openPopupShowPV}
                                    onClose={this.handleClosePopupShowPV}
                                    aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">

                                    <CRow>
                                        <CCol md="4">
                                            <span className="myModalTitleMarginTop">
                                                PV de la Soutenance
                                            </span>
                                        </CCol>
                                        <CCol md="8" className="colPadding">

                                                <span className="myModalSubTitle">
                                                  {selectedStudentFullName}
                                                </span>

                                            {(
                                                pairFullName !== null &&
                                                <>
                                                    &nbsp;&nbsp;
                                                    <span className="clignotePrimary" style={{fontSize: "13px"}}>
                                                                        <ins>
                                                                          en binôme avec
                                                                        </ins>
                                                                      </span>

                                                    &nbsp;&nbsp;
                                                    <span className="myModalSubTitle">
                                                                        {pairFullName}
                                                                      </span>
                                                </>
                                            )}

                                        </CCol>
                                    </CRow>

                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="5">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Identifiant:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="7">
                                            <span style={{fontSize: "13px"}}>{idEtToSTN}</span>
                                        </CCol>
                                    </CRow>
                                    <CRow>
                                        <CCol xs="5">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Président Jury:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="7">
                                            {(
                                                affectedJP.nom !== "" && presidentJuryNotEns === "" &&
                                                <div>
                                                    <span style={{fontSize: "13px"}}>{affectedJP.nom}</span> &nbsp;
                                                    <span className="note">(Affectation Interne)</span>
                                                </div>
                                            )}
                                            {(
                                                presidentJuryNotEns !== "" &&
                                                <div>
                                                    <span style={{fontSize: "13px"}}>{presidentJuryNotEns}</span> &nbsp;
                                                    <span className="note">(Affectation Externe)</span>
                                                </div>
                                            )}
                                        </CCol>
                                    </CRow>
                                    <CRow>
                                        <CCol xs="5">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Membre:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="7">
                                            <span style={{fontSize: "13px"}}>{affectedEXP.nom}</span>
                                        </CCol>
                                    </CRow>
                                    <CRow>
                                        <CCol xs="5">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Encadrant Pédagogique:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="7">
                                            <span style={{fontSize: "13px"}}>{pedagogicalEncadrant.nom}</span>
                                        </CCol>
                                    </CRow>
                                    <CRow>
                                        <CCol xs="5">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Date Soutenance:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="7">
                                            <span style={{fontSize: "13px"}}>{formattedSelectedDate}</span>
                                            &nbsp;de&nbsp;
                                            <span style={{fontSize: "15px", color: "#808080"}}>
                                                                      {selectedStartHour}
                                                                    </span>
                                            &nbsp;à&nbsp;
                                            <span style={{fontSize: "15px", color: "#808080"}}>
                                                                      {selectedEndHour}
                                                                    </span>
                                        </CCol>
                                    </CRow>
                                    <CRow>
                                        <CCol xs="5">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Salle Soutenance:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="7">
                                            <span style={{fontSize: "13px"}}>{salle}</span>
                                        </CCol>
                                    </CRow>
                                    <br/>
                                    <hr/>
                                    <CRow>
                                        <CCol xs="4"/>
                                        <CCol xs="4">
                                            <center>
                                                <CButton shape="pill"
                                                         color="primary"
                                                         size="lg"
                                                         aria-expanded="true"
                                                         onClick={() => this.handleDownloadPV()}>
                                                    <CTooltip content="Télécharger PV" placement="top">
                                                        <CIcon content={freeSet.cilCloudDownload}/>
                                                    </CTooltip>
                                                </CButton>
                                                &nbsp;&nbsp;
                                                {(
                                                    pairFullName !== null &&
                                                    <CButton shape="pill"
                                                             color="success"
                                                             size="lg"
                                                             aria-expanded="true"
                                                             onClick={() => this.handleDownloadPVBinome()}>
                                                        <CTooltip content="Télécharger PV Binôme" placement="top">
                                                            <CIcon content={freeSet.cilCloudDownload}/>
                                                        </CTooltip>
                                                    </CButton>
                                                )}
                                            </center>
                                        </CCol>
                                        <CCol xs="4"/>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button onClick={this.handleClosePopupShowPV} color="primary">
                                        Exit
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog fullHight
                                    fullWidth
                                    maxWidth="sm"
                                    keepMounted
                                    open={openPopupArchiveSoutenance}
                                    onClose={this.handleClosePopupArchiveSoutenance}
                                    aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                                    <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <br/>
                                    <center>
                                        Êtes-vous sûrs(es) de vouloir rendre
                                        <br/>
                                        la Planification de Soutenance pour l'étudiant(e)&nbsp;
                                        <span className="text-info" style={{fontSize: "12px"}}>
                                                                    {selectedStudentFullName}
                                                                  </span>
                                        &nbsp;modifiable ?.
                                    </center>
                                    <br/><br/>
                                </DialogContent>
                                <DialogActions>
                                    <CButton onClick={this.handleTurnSoutenanceToPlanified} variant="outline"
                                             color="success">
                                        Oui
                                    </CButton>
                                    &nbsp;
                                    <CButton onClick={this.handleClosePopupArchiveSoutenance} variant="outline"
                                             color="danger">
                                        Non
                                    </CButton>
                                </DialogActions>
                            </Dialog>

                            <Dialog fullHight
                                    fullWidth
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
                                        <CCol xs="2">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      Téléphone:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="10">
                                            <span style={{fontSize: "13px"}}>{selectedStudentTel}</span>
                                        </CCol>
                                    </CRow>

                                    <CRow>
                                        <CCol xs="2">
                                                                    <span className="text-dark" style={{
                                                                        fontSize: "14px",
                                                                        fontWeight: "bold"
                                                                    }}>
                                                                      E-Mail:
                                                                    </span>
                                        </CCol>
                                        <CCol xs="10">
                                            <span style={{fontSize: "13px"}}>{selectedStudentMail}</span>
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

                        </>
                )}

            </>
        );
    }
}
