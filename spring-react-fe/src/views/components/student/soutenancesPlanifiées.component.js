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

import Checkbox from "@material-ui/core/Checkbox";
import ReactTextTransition from "react-text-transition";
import Select from "react-select";
import makeAnimated from "react-select/animated";

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

import Badge from '@material-ui/core/Badge';
import PersonIcon from '@material-ui/icons/Person';

const animatedComponents = makeAnimated();

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const checkedStusAdvancedPl = [
    {
        key: 'identifiant',
        label: 'Identifiant',
        _style: {width: '20%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'fullName',
        label: 'Prénom & Nom',
        _style: {width: '70%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'department',
        label: 'Département',
        _style: {width: '10%'},
        sorter: true,
        filter: true,
        show: true
    }
]

const checkedStus = [
    {
        key: 'fullName',
        label: 'Prénom & Nom',
        _style: {width: '14%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'pedagogicalEncadrant',
        label: 'Encadrant',
        _style: {width: '14%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'membre',
        label: 'Membre',
        _style: {width: '20%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'juryPresident',
        label: 'Président Jury',
        _style: {width: '20%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'dateSoutenance',
        label: 'Date Soutenance',
        _style: {width: '11%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'hourStartSoutenance',
        label: 'Heure Début',
        _style: {width: '5%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'hourEndSoutenance',
        label: 'Heure Fin',
        _style: {width: '5%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'salleSoutenance',
        label: 'Salle',
        _style: {width: '10%'},
        sorter: true,
        filter: true,
        show: true
    },
    {
        key: 'department',
        label: 'Dépt',
        _style: {width: '2%'},
        sorter: true,
        filter: true,
        show: true
    }
]

const fields = [
    {
        key: 'checkboxSelect',
        label: 'Select',
        _style: {width: '5%'},
        sorter: false,
        filter: false
    },
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
        label: 'Date Dépot',
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


export default class PlanifySoutenance extends Component {
    constructor(props) {
        super(props);

        this.handleDownloadPVBinome = this.handleDownloadPVBinome.bind(this);
        this.loadStudentsAuthorizedTOSTN = this.loadStudentsAuthorizedTOSTN.bind(this);
        this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
        this.onChangeDateSoutenance = this.onChangeDateSoutenance.bind(this);
        this.onChangeDateSoutenanceAP = this.onChangeDateSoutenanceAP.bind(this);
        this.checkDisponibilityTeacher = this.checkDisponibilityTeacher.bind(this);
        this.checkDisponibilityExpert = this.checkDisponibilityExpert.bind(this);
        this.checkDisponibilityClassroom = this.checkDisponibilityClassroom.bind(this);
        this.onChangeTraineeShipKind = this.onChangeTraineeShipKind.bind(this);
        this.checkDisponibilityPedagogicalEncadrant = this.checkDisponibilityPedagogicalEncadrant.bind(this);
        this.handlePlanifySoutenance = this.handlePlanifySoutenance.bind(this);
        this.handleDownloadPV = this.handleDownloadPV.bind(this);
        this.handleDownloadPlanningSoutenanceByFilter = this.handleDownloadPlanningSoutenanceByFilter.bind(this);
        this.handleConsultPV = this.handleConsultPV.bind(this);
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

        this.handleCloseSuccessValidateUniquePlanifPopup = this.handleCloseSuccessValidateUniquePlanifPopup.bind(
            this
        );

        this.handleCloseSuccessValidateAndNotifyPopupMP = this.handleCloseSuccessValidateAndNotifyPopupMP.bind(
            this
        );

        this.handleCloseSuccessAffectJPNEnsPopup = this.handleCloseSuccessAffectJPNEnsPopup.bind(
            this
        );
        this.handleCloseSuccessAffectExpertPopup = this.handleCloseSuccessAffectExpertPopup.bind(
            this
        );

        this.handleOpenModalPlanificationMultiple = this.handleOpenModalPlanificationMultiple.bind(this);
        this.handleCloseModalPlanificationMultiple = this.handleCloseModalPlanificationMultiple.bind(this);

        this.handleOpenModalPlanificationAdvanced = this.handleOpenModalPlanificationAdvanced.bind(this);
        this.handleCloseModalPlanificationAdvanced = this.handleCloseModalPlanificationAdvanced.bind(this);

        this.handleAffectJuryPresidentEns = this.handleAffectJuryPresidentEns.bind(
            this
        );

        this.iterateNotifForAdvancedPlanif = this.iterateNotifForAdvancedPlanif.bind(this);
        this.iterateNotifForMultiplePlanif = this.iterateNotifForMultiplePlanif.bind(this);

        this.handleAffectExpertEns = this.handleAffectExpertEns.bind(this);
        this.handleChangeEndHour = this.handleChangeEndHour.bind(this);
        this.handleChangeStartHour = this.handleChangeStartHour.bind(this);

        this.handleChangeEndHourAP = this.handleChangeEndHourAP.bind(this);
        this.handleChangeStartHourAP = this.handleChangeStartHourAP.bind(this);

        this.handleAffectClassroom = this.handleAffectClassroom.bind(this);
        this.handleAffectJuryPresidentNotEns = this.handleAffectJuryPresidentNotEns.bind(
            this
        );
        this.onChangeAffectationKind = this.onChangeAffectationKind.bind(this);
        this.validateAndNotifyUniquePlanif = this.validateAndNotifyUniquePlanif.bind(this);
        this.validateUniquePlanif = this.validateUniquePlanif.bind(this);
        this.validateAndNotifyForMP = this.validateAndNotifyForMP.bind(this);
        this.validateEntriesForMP = this.validateEntriesForMP.bind(this);
        this.checkDisponibilityEncadrantForAP = this.checkDisponibilityEncadrantForAP.bind(this);
        this.handleInitilizeSTNTime = this.handleInitilizeSTNTime.bind(this);
        this.handleAutoPlanifSallesExpertsJuryPresidentsForAP = this.handleAutoPlanifSallesExpertsJuryPresidentsForAP.bind(this);

        this.handleDownloadFicheDepotPFE = this.handleDownloadFicheDepotPFE.bind(this);

        this.handleTurnSoutenanceToPlanified = this.handleTurnSoutenanceToPlanified.bind(this);

        this.changeState = this.changeState.bind(this);

        this.gotSelectedStudents = this.gotSelectedStudents.bind(this);

        this.onChangeSelectExpertMP = this.onChangeSelectExpertMP.bind(this);
        this.onChangeSelectJuryPresidentMP = this.onChangeSelectJuryPresidentMP.bind(this);
        this.onChangeDateSoutenanceMP = this.onChangeDateSoutenanceMP.bind(this);
        this.onChangeSelectClassroomMP = this.onChangeSelectClassroomMP.bind(this);
        this.onChangeEndHourMP = this.onChangeEndHourMP.bind(this);
        this.onChangeStartHourMP = this.onChangeStartHourMP.bind(this);
        this.handleSelectDeselectAll = this.handleSelectDeselectAll.bind(this);
        this.onChangeSelectSalleSoutenance = this.onChangeSelectSalleSoutenance.bind(this);
        this.onChangeSelectExpertSoutenance = this.onChangeSelectExpertSoutenance.bind(this);
        this.onChangeSelectJuryPresidentSoutenance = this.onChangeSelectJuryPresidentSoutenance.bind(this);

        this.handleClosePopupLackEntriesForNotif = this.handleClosePopupLackEntriesForNotif.bind(this);
        this.handleClosePopupFilledEntriesForNotif = this.handleClosePopupFilledEntriesForNotif.bind(this);
        this.handleVerifyEntriesForNotif = this.handleVerifyEntriesForNotif.bind(this);

        this.updateBadgeOnSidebarForGCAutoPlanif = this.updateBadgeOnSidebarForGCAutoPlanif.bind(this);


        // init2021
        this.state = {
            lol111: "azerty",
            selectedDate: new Date(),
            selectedDateAP: new Date(),

            selectedStartHour: "08:00",
            selectedEndHour: "",
            selectedStartHourAP: "08:00",
            selectedEndHourAP: "",

            listOfAuthorizedStudentsToSTN: [],
            listOfAuthorizedStudentsToSTNByFilter: [],
            listOfAvailableTeachersToSTN: [],
            listOfAvailableExpertsToSTN: [],
            listOfAvailableClassroomsToSTN: [],
            openPopupAffectSoutenance: false,
            openPopupShowPV: false,
            idEtToSTN: "",
            emailToSTN: "",
            checkedAK: 0,
            checkedAll: false,
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
            loadingValidateUniquePlanif: false,
            loadValidateAndNotifyforAMP: false,

            successAffectJPEnsPopup: false,
            successAffectJPNEnsPopup: false,
            successAffectExpertPopup: false,
            successAffectSallePopup: false,
            successValidateAndNotifyPopup: false,
            successValidateUniquePlanifPopup: false,
            successValidateAndNotifyPopupMP: false,
            successShowPlanificationMultiple: false,
            successShowPlanificationAdvanced: false,

            disponibilityPE: "",

            pedagogicalEncadrant: {},
            presidentJuryNotEns: "",
            salle: "",
            affectationPresidentJury: "",
            selectedStudentFullName: "",
            selectedStudentBinome: "",
            valAndNot: "EN ATTENTE",

            refuseEndHour: false,
            refuseEndHourAP: false,
            decideEndHourMP: "",
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
            loadList: true,
            traineeShipKind: "En Tunisie",
            allTraineeshipKinds: [],
            checkedTraineeShip: 0,
            pairFullName: "",
            indexStudentToPlanify: "",
            planifyKind: "PNORMAL",
            nbrNotifiedSTN: "",
            nbrPlanifiedSTN: "",
            nbrNotPlanifiedSTN: "",
            nbUploadedReports: "",
            nbValidatedReports: "",
            nbIncompletedReports: "",
            checked: {},
            checkedStudents: [],
            listOfCheckedStudentsForMPSTN: [],
            listOfCheckedStudentsForAPSTN: [],
            allTeachersMP: [],
            allClassroomsMP: [],
            checkBoxObj: {},
            showListOfCheckedStudentsForAPSTN: true,
            loadCheckDisponibilityPEforAP: false,
            loadAffectClassRoomsforAP: false,
            loadMultiplePlanification: false,
            allAvailableSalles: [],
            allAvailableExperts: [],
            allAvailableJuryPresidents: [],
            selectedSalles: [],
            selectedExperts: [],
            selectedJuryPresidents: [],
            alertNbrSallesToChoose: 0,
            disableCheckDispoPE: false,
            disableCheckDispoPEBTN: true,
            disableValidateAutoPlanifBTN: false,
            showFullPlanningForAP: false,
            helloworld: false,
            verifyEntriesForNotif: false,
            loadVerifyEntriesForNotif: false,
            loadVerifyEntriesForValidate: false,
            idCheckedStuds: [],
            giveOkConfirmNotifyMultiplePlanif: false,
            openPopupLackEntriesForNotif: false,
            openPopupFilledEntriesForNotif: false,
            giveOkConfirmAndNotifyMP: false,
            existAvailablePEForAdvancedPlanif: true
        };

        /* var requestAS = new XMLHttpRequest();
        requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
        requestAS.send(null);
        this.state.nbrNotPlanifiedSTN = requestAS.responseText;

        console.log('---------------------------------> BADGE 123: ' + props.nbrStudentsToSTN);
        // this.state.nbrStudentsToSTN = sessionStorage.getItem("nas");


        var requestSDS = new XMLHttpRequest();
        requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
        requestSDS.send(null);
        this.state.nbrNotifiedSTN = requestSDS.responseText;*/
        /*var requestAS = new XMLHttpRequest();
          requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
          requestAS.send(null);

          this.state.nbrNotifiedSTN= requestAS.responseText;*/

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

        /*let loli = [1, 2, 3, 4, 5];
      console.log('WAKING 1' + new Date());
      let nbocc = 1;
      for (let cs of loli)
      {
          setTimeout(function timer()
          {
             console.log('WAKING1 AFTER 10s ----------> UNIT: '  + nbocc +  " - " + cs +  " ---> " + new Date());

          }, nbocc * 10000);
          nbocc ++;
      }
      console.log('WAKING 2' + new Date());*/

        /*for (let i = 1; i < 10; i++)
      {
          setTimeout(function timer()
          {
             console.log('WAKING1 AFTER 10s ----------> UNIT: '  + i + " - " + new Date());
          }, i * 10000);
      }*/


        // console.log('------------------------> TREATMENT 1: ' + new Date() + " - " + this.state.loadList);

        this.loadStudentsAuthorizedTOSTN();

        // Trainee Kind
        var requestTK = new XMLHttpRequest();
        requestTK.open("GET", API_URL_MESP + "traineeKinds", false);
        requestTK.send(null);
        let tk = JSON.parse(requestTK.responseText);

        // console.log('-------------------------tk-1-> ', tk);

        let crsList = this.state.allTraineeshipKinds.slice();
        for (let i of tk) {
            const tkObj = {value: i, label: i, color: ""};
            crsList.push(tkObj);
        }

        this.state.allTraineeshipKinds = crsList;

    }


    onChangeSelectSalleSoutenance = (selectedCROption) => {

        // console.log('----------------> selectedCROption: ', selectedCROption);
        if (selectedCROption) {
            var selectedList = selectedCROption.values();
            // console.log(selectedList);
            this.state.selectedSalles = [];

            let virtualSTLs = this.state.selectedSalles.slice();
            for (let sl of selectedList) {
                virtualSTLs.push(sl.label);
            }
            const uniqueListSelectedCRs = Array.from(
                new Set(virtualSTLs)
            );
            this.setState({selectedSalles: uniqueListSelectedCRs});
            // console.log('NOT NULL ----------------> Selected CRs: ', uniqueListSelectedCRs);
        }
        if (selectedCROption === null) {
            // console.log('NULL ----------------> this.state.allAvailableSalles: ', this.state.allAvailableSalles.length);
            this.setState({selectedSalles: []});
        }
    };

    onChangeSelectExpertSoutenance = (selectedExpertOption) => {
        if (selectedExpertOption) {
            var selectedList = selectedExpertOption.values();
            this.state.selectedExperts = [];

            let virtualSTLs = this.state.selectedExperts.slice();
            for (let sl of selectedList) {
                virtualSTLs.push(sl.value);
                this.setState({
                    allAvailableJuryPresidents: this.state.allAvailableJuryPresidents.filter(el => el.value != sl.value),
                });
            }

            const uniqueListSelectedExps = Array.from(
                new Set(virtualSTLs)
            );

            this.setState({selectedExperts: uniqueListSelectedExps});
            // console.log('----------------> Selected Exps: ', uniqueListSelectedExps);
        }
        if (selectedExpertOption === null) {
            // console.log('NULL ----------------> this.state.allAvailableExperts: ', this.state.allAvailableExperts.length);
            this.setState({selectedExperts: []});
        }
    };

    onChangeSelectJuryPresidentSoutenance = (selectedJuryPresidentOption) => {
        if (selectedJuryPresidentOption) {
            var selectedList = selectedJuryPresidentOption.values();
            this.state.selectedJuryPresidents = [];

            let virtualSTLs = this.state.selectedJuryPresidents.slice();
            for (let sl of selectedList) {
                virtualSTLs.push(sl.value);
                this.setState({
                    allAvailableExperts: this.state.allAvailableExperts.filter(el => el.value != sl.value),
                });
            }

            const uniqueListSelectedJPs = Array.from(
                new Set(virtualSTLs)
            );

            this.setState({selectedJuryPresidents: uniqueListSelectedJPs});
            // console.log('----------------> Selected JPs: ', uniqueListSelectedJPs);
        }
        if (selectedJuryPresidentOption === null) {
            // console.log('NULL ----------------> this.state.allAvailableJuryPresidents: ', this.state.allAvailableJuryPresidents.length);
            this.setState({selectedJuryPresidents: []});
        }
    };


    changeState = () => {
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> BADGE 1');
        this.setState({
            data: 'lol'
        });
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> BADGE 2: ' + this.state.data);
    };

    getKeyByValue(object, value) {
        return Object.keys(object).filter(k => object[k] === value);
    }


    gotSelectedStudents(idStu) {
        // console.log('----------- SARS 1208 0: ', idStu);
        // console.log('----------- SARS 1208 1: ', this.state.checked);
        // console.log('----------- SARS 1208 2: ' + this.state.checkedStudents);

        //this.state.checked = {};
        const newSelected = Object.assign({}, this.state.checked);
        // console.log('----------- SARS 1208 3: ', newSelected);
        newSelected[idStu] = !this.state.checked[idStu];
        this.setState({checked: newSelected});

        this.state.checkedStudents = this.getKeyByValue(newSelected, true);
        // console.log('----------- CHECKED STU: ', this.state.checkedStudents);
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


    handleSelectDeselectAll(event) {
        const checkedAll = event.target.checked;
        // console.log('-----------------------> checkBoxObj - 1: ', this.state.checkBoxObj);

        const newCheckBoxObj = {};
        // console.log('-----------------------> checkBoxObj - 2: ', this.state.checkBoxObj);
        // for(let k in this.state.listOfAuthorizedStudentsToSTN)
        for (let unity of this.state.listOfAuthorizedStudentsToSTN) {
            //// console.log('-----------------------> checkBoxObj - 3: ', unity.identifiant);
            newCheckBoxObj[unity.identifiant] = checkedAll;

            this.setState({checked: newCheckBoxObj});
        }
        // console.log('---------------***--------> checkBoxObj - 5: ', newCheckBoxObj);
        // console.log('---------------***--------> checkBoxObj - 6: ', this.state.checkedStudents.length);
        this.state.checkedStudents = [];
        this.state.checkedStudents = this.getKeyByValue(newCheckBoxObj, true);
        // console.log('---------------***--------> checkBoxObj - 7: ', this.state.checkedStudents.length);
        this.setState({
            // checkBoxObj: newCheckBoxObj,
            checkedAll
        });
    }


    /*
      for (let unity of this.state.listOfAuthorizedStudentsToSTN)
      {
          const newSelected = Object.assign({}, this.state.checked);
          newSelected[unity.identifiant] = !this.state.checked[unity.identifiant];
          this.setState({ checked: newSelected });

          this.state.checkedStudents = this.getKeyByValue(newSelected, true);
      }
  */

    onChangeTraineeShipKind(i, val) {
        // // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete A: ' + i);
        // // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: Delete B: ' + val);
        this.setState({
            checkedTraineeShip: i,
            traineeShipKind: val,
        });

        this.state.checkedTraineeShip = i;
        this.state.traineeShipKind = val;

        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: TraineeShipKind 1: ' + this.state.checkedTraineeShip);
        // console.log('xxxxxxxxxxxxxxxxxxxxxxx> ERROR: TraineeShipKind 2: ' + this.state.traineeShipKind + " - " + this.state.idEtToSTN);

        let traineeKind = val;
        let studentId = this.state.idEtToSTN;
        this.setState({
            listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.map(el => (el.identifiant === studentId ? {
                ...el,
                traineeKind
            } : el)),
        })

        let requestUTK = new XMLHttpRequest();
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
        AuthService.soutenancesAllPlanifiées(currentResponsableServiceStage.id).then(
            (response) => {
                // console.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);

                let stuList = this.state.listOfAuthorizedStudentsToSTN.slice();
                for (let stu of response.data) {
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
        AuthService.turnSoutenanceToPlanified(this.state.idEtToSTN).then(
            (response) => {
                let idEt = this.state.idEtToSTN;

                var requestAS = new XMLHttpRequest();
                requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
                requestAS.send(null);

                var requestPS = new XMLHttpRequest();
                requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestPS.send(null);
                this.state.nbrPlanifiedSTN = requestPS.responseText;

                var requestSDS = new XMLHttpRequest();
                requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestSDS.send(null);

                var requestUD = new XMLHttpRequest();
                requestUD.open("GET", API_URL_MESP + "responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id, false);
                requestUD.send(null);
                this.state.nbUploadedReports = requestUD.responseText;

                var requestVD = new XMLHttpRequest();
                requestVD.open("GET", API_URL_MESP + "responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id, false);
                requestVD.send(null);
                this.state.nbValidatedReports = requestVD.responseText;

                var requestID = new XMLHttpRequest();
                requestID.open("GET", API_URL_MESP + "responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id, false);
                requestID.send(null);
                this.state.nbIncompletedReports = requestID.responseText;

                this.setState({
                    nbrNotifiedSTN: requestAS.responseText,
                    nbrPlanifiedSTN: requestPS.responseText,
                    nbrNotPlanifiedSTN: requestSDS.responseText,
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idEt),
                    listOfAuthorizedStudentsToSTNByFilter: this.state.listOfAuthorizedStudentsToSTNByFilter.filter(el => el.identifiant != idEt)
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

        // console.log("Check SARS0209 --------cr----- " + bpvd.fullName);

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
            checkedAK: i,
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

                this.updatingBadgeValues(this.state.idEtToSTN);

                //let crsList = this.state.listOfAvailableClassroomsToSTN.slice();

            },
            (error) => {
                // console.log("Check Disponibility --------cr----- FAIL");
            }
        );

    }

    updatingBadgeValues(idEt) {
        var requestAS = new XMLHttpRequest();
        requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
        requestAS.send(null);
        this.state.nbrNotifiedSTN = requestAS.responseText;

        var requestPS = new XMLHttpRequest();
        requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
        requestPS.send(null);
        this.state.nbrPlanifiedSTN = requestPS.responseText;

        var requestSDS = new XMLHttpRequest();
        requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
        requestSDS.send(null);
        this.state.nbrNotPlanifiedSTN = requestSDS.responseText;

        var requestUD = new XMLHttpRequest();
        requestUD.open("GET", API_URL_MESP + "responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id, false);
        requestUD.send(null);
        this.state.nbUploadedReports = requestUD.responseText;

        var requestVD = new XMLHttpRequest();
        requestVD.open("GET", API_URL_MESP + "responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id, false);
        requestVD.send(null);
        this.state.nbValidatedReports = requestVD.responseText;

        var requestID = new XMLHttpRequest();
        requestID.open("GET", API_URL_MESP + "responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id, false);
        requestID.send(null);
        this.state.nbIncompletedReports = requestID.responseText;

        this.setState({
            listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idEt),
            listOfAuthorizedStudentsToSTNByFilter: this.state.listOfAuthorizedStudentsToSTNByFilter.filter(el => el.identifiant != idEt),
        });

    }

    handlePlanifySoutenance(index, idEt, fullName, traineeKind) {
        // console.log("--------------------------> traineeKind: " + traineeKind);

        var requestPE = new XMLHttpRequest();
        requestPE.open("GET", API_URL_MESP + "findEncadrantPedagogiqueByStudent/" + idEt, false);
        requestPE.send(null);
        let pe = JSON.parse(requestPE.responseText);

        var requestTK = new XMLHttpRequest();
        requestTK.open("GET", API_URL_MESP + "binomeFullName/" + idEt, false);
        requestTK.send(null);


        this.setState({
            openPopupAffectSoutenance: true,
            idEtToSTN: idEt,
            selectedStartHour: "08:00",
            listOfAvailableTeachersToSTN: [],
            listOfAvailableExpertsToSTN: [],
            listOfAvailableClassroomsToSTN: [],
            checkedAK: "",
            fullNameJuryPresidentNotEns: "",
            selectedEndHour: "",
            affectationKind: "",
            pedagogicalEncadrant: pe,
            selectedStudentFullName: fullName,
            disabledSTNTime: false,
            traineeKind: traineeKind,
            indexStudentToPlanify: index,
            selectedStudentBinome: requestTK.responseText
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

        var requestBPVD = new XMLHttpRequest();
        requestBPVD.open("GET", API_URL_MESP + "binomePVDetails/" + this.state.idEtToSTN, false);
        requestBPVD.send(null);
        let bpvd = JSON.parse(requestBPVD.responseText);

        // console.log("-----------------PAIR-------------> res PE: " + bpvd.identifiant);
        // console.log("---------------------PAIR---------> res PE: " + bpvd.fullName);
        // console.log("-------------------------PAIR-----> res PE: " + bpvd.currentClass);

        this.setState({
            pairIdentifiant: bpvd.identifiant,
            pairFullName: bpvd.fullName,
            pairCurrentClass: bpvd.currentClass
        });

        this.gotStudentSTNAffectationDetailsPlanify(idEt);
    }

    handleDownloadAllPlanningSoutenanceExcel() {
        this.setState({showLoadingExcelGreenIcon: true})
        AuthService.downloadPlanningSoutenancePlanifiéesExcel(1, currentResponsableServiceStage.id).then(
            (response) => {
                this.setState({showLoadingExcelGreenIcon: false})
                let filename = "Planning Soutenances Planifiées.xls";
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

        let resultDPES;
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
            checkedAK: '',
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

    validateUniquePlanif() {

        this.setState({
            loadingValidateUniquePlanif: true,
        });

        AuthService.validateUniquePlanification(this.state.idEtToSTN).then(
            (response) => {

                let idEt = this.state.idEtToSTN;

                var requestAS = new XMLHttpRequest();
                requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
                requestAS.send(null);

                var requestPS = new XMLHttpRequest();
                requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestPS.send(null);
                this.state.nbrPlanifiedSTN = requestPS.responseText;

                var requestSDS = new XMLHttpRequest();
                requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestSDS.send(null);

                var requestUD = new XMLHttpRequest();
                requestUD.open("GET", API_URL_MESP + "responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id, false);
                requestUD.send(null);
                this.state.nbUploadedReports = requestUD.responseText;

                var requestVD = new XMLHttpRequest();
                requestVD.open("GET", API_URL_MESP + "responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id, false);
                requestVD.send(null);
                this.state.nbValidatedReports = requestVD.responseText;

                var requestID = new XMLHttpRequest();
                requestID.open("GET", API_URL_MESP + "responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id, false);
                requestID.send(null);
                this.state.nbIncompletedReports = requestID.responseText;

                this.state.nbrNotifiedSTN = requestAS.responseText;
                this.state.nbrPlanifiedSTN = requestPS.responseText;
                this.state.nbrNotPlanifiedSTN = requestSDS.responseText;

                this.setState({
                    successValidateUniquePlanifPopup: true,
                    openPopupAffectSoutenance: false,
                    loadingValidateUniquePlanif: false,
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idEt),
                    listOfAuthorizedStudentsToSTNByFilter: this.state.listOfAuthorizedStudentsToSTNByFilter.filter(el => el.identifiant != idEt),
                });

                /********************************************** Case GC-Binome START ************************************************/

                var requestBin = new XMLHttpRequest();
                requestBin.open("GET", API_URL_MESP + "itsGCWithPair/" + idEt, false);
                requestBin.send(null);
                let itsEM = requestBin.responseText;

                let idPairGC = "";
                if (itsEM.includes('YES')) {
                    idPairGC = itsEM.substring(3);
                    this.setState({
                        listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idPairGC),
                    });
                }

                /********************************************** Case GC-Binome END************************************************/

            },
            (error) => {
            }
        );

    }

    validateAndNotifyUniquePlanif() {
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

                var requestAS = new XMLHttpRequest();
                requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
                requestAS.send(null);

                var requestPS = new XMLHttpRequest();
                requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestPS.send(null);
                this.state.nbrPlanifiedSTN = requestPS.responseText;

                var requestSDS = new XMLHttpRequest();
                requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestSDS.send(null);

                var requestUD = new XMLHttpRequest();
                requestUD.open("GET", API_URL_MESP + "responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id, false);
                requestUD.send(null);
                this.state.nbUploadedReports = requestUD.responseText;

                var requestVD = new XMLHttpRequest();
                requestVD.open("GET", API_URL_MESP + "responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id, false);
                requestVD.send(null);
                this.state.nbValidatedReports = requestVD.responseText;

                var requestID = new XMLHttpRequest();
                requestID.open("GET", API_URL_MESP + "responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id, false);
                requestID.send(null);
                this.state.nbIncompletedReports = requestID.responseText;

                this.state.nbrNotifiedSTN = requestAS.responseText;
                this.state.nbrPlanifiedSTN = requestPS.responseText;
                this.state.nbrNotPlanifiedSTN = requestSDS.responseText;

                this.setState({
                    successValidateAndNotifyPopup: true,
                    loadingValidateAndNotify: false,
                    statusValidateAndNotify: "TRAITÉ",
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idEt),
                    listOfAuthorizedStudentsToSTNByFilter: this.state.listOfAuthorizedStudentsToSTNByFilter.filter(el => el.identifiant != idEt),

                });

                /********************************************** Case GC-Binome START************************************************/

                var requestBin = new XMLHttpRequest();
                requestBin.open("GET", API_URL_MESP + "itsGCWithPair/" + idEt, false);
                requestBin.send(null);
                let itsEM = requestBin.responseText;

                let idPairGC = "";
                if (itsEM.includes('YES')) {
                    idPairGC = itsEM.substring(3);
                    this.setState({
                        listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idPairGC),

                    });
                }

                /********************************************** Case GC-Binome END************************************************/


               /* console.log("#####################################> 2:" +
                    this.state.planifyKind + " - " +
                    this.state.indexStudentToPlanify + " -----> " +
                    this.state.listOfAuthorizedStudentsToSTNByFilter.length + " / " +
                    this.state.listOfAuthorizedStudentsToSTN.length);*/
            },
            (error) => {
            }
        );

    }

    handleVerifyEntriesForNotif() {
        this.setState({loadVerifyEntriesForNotif: true});

        // console.log('-------------- **1.1* -----------> idCheckedStuds: ', this.state.idCheckedStuds);
        // console.log('-------------- **1.2* -----------> idCheckedStuds: ', this.state.checkedStudents);

        AuthService.checkAllFilledEntriesToBePlanifiedSTN(currentResponsableServiceStage.id, this.state.checkedStudents).then(
            (response) => {

                // console.log('--------------THIS-----------> SARS123: ', response);
                this.setState({
                    loadVerifyEntriesForNotif: false,
                    giveOkConfirmNotifyMultiplePlanif: response.data,
                });

                if (this.state.giveOkConfirmNotifyMultiplePlanif === false) {
                    // console.log('-------------- THIS false -----------> SARS123: ', response);
                    this.setState({openPopupLackEntriesForNotif: true});
                }

                if (this.state.giveOkConfirmNotifyMultiplePlanif === true) {
                    // console.log('-------------- THIS true -----------> SARS123: ', response);
                    this.setState({
                        //openPopupFilledEntriesForNotif: true,
                        showFullPlanningForAP: false,
                        loadValidateAndNotifyforAMP: true
                    });
                    // console.log('----------------------------> MP-1');


                    let nbOcc = 0;

                    let inc = 1;
                    //for (let cs of this.state.listOfCheckedStudentsForMPSTN)
                    for (let cs of this.state.checkedStudents) {
                        setTimeout(() => {
                            // console.log('WAKING AFTER 10s ----------> UNIT: ' + inc + " - " + cs + " ---> " + new Date());

                            // setTimeout(this.onLoop.bind(this), 1000)
                            nbOcc++;
                            this.iterateNotifForMultiplePlanif(cs, nbOcc);

                        }, inc * 10000); // Treat Next Element AFTER 10s
                        inc++;
                    }

                    // console.log('----------------------------> MP-9');
                }

            },
            (error) => {
            }
        );
    }

    handleClosePopupLackEntriesForNotif() {
        this.setState({openPopupLackEntriesForNotif: false});
    }

    handleClosePopupFilledEntriesForNotif() {
        this.setState({
            openPopupFilledEntriesForNotif: false,
            successShowPlanificationMultiple: false
        });
    }

    /* validateAndNotifyForMP()
  {
      this.setState({ loadingValidateAdvancedPlanification:true});
  }*/

    // INIT1
    checkDisponibilityEncadrantForAP() {

        // console.log('----------------------> SARS: ' + this.state.listOfCheckedStudentsForAPSTN.length);

        let idsOfCheckedStudentsForAPSTN = [];

        for (let idStu of this.state.checkedStudents) {
            idsOfCheckedStudentsForAPSTN.push(idStu);
        }

        this.setState({
            loadCheckDisponibilityPEforAP: true,
            showListOfCheckedStudentsForAPSTN: false,
            allAvailableSalles: [],
            disableCheckDispoPE: true,
            disableCheckDispoPEBTN: true
        });


        AuthService.checkDisponibilityEncadrantForAP(
            currentResponsableServiceStage.id,
            idsOfCheckedStudentsForAPSTN,
            this.gotFormattedDate(this.state.selectedDateAP),
            this.state.selectedStartHourAP,
            this.state.selectedEndHourAP
        ).then(
            (response) => {

                this.state.listOfCheckedStudentsForAPSTN = [];
                let stuList = this.state.listOfCheckedStudentsForAPSTN.slice();

                let nbClassRooms = 0;

                for (let stu of response.data) {
                    stuList.push(stu);
                    nbClassRooms = stu.expectedNbrSalles;
                }

                // Available ClassRooms
                var requestCRs = new XMLHttpRequest();
                requestCRs.open("GET", API_URL_MESP + "/disponibility/classrooms"
                    + "/" + this.gotFormattedDate(this.state.selectedDateAP)
                    + "/" + this.state.selectedStartHourAP
                    + "/" + this.state.selectedEndHourAP, false);
                requestCRs.send(null);
                let avSalles = JSON.parse(requestCRs.responseText);

                let crsList = this.state.allAvailableSalles.slice();
                for (let sl of avSalles) {
                    crsList.push({value: sl, label: sl});
                }


                // Available Experts
                var requestExps = new XMLHttpRequest();
                requestExps.open("GET", API_URL_MESP + "/teachersDisponibilityForAP"
                    + "/" + this.gotFormattedDate(this.state.selectedDateAP)
                    + "/" + this.state.selectedStartHourAP
                    + "/" + this.state.selectedEndHourAP
                    + "/" + this.state.checkedStudents, false);
                requestExps.send(null);
                let avExperts = JSON.parse(requestExps.responseText);

                let expsList = this.state.allAvailableExperts.slice();
                for (let sl of avExperts) {
                    expsList.push({value: sl.identifiant, label: sl.nom});
                }


                this.setState({
                    loadCheckDisponibilityPEforAP: false,
                    listOfCheckedStudentsForAPSTN: stuList,
                    allAvailableSalles: crsList,
                    allAvailableExperts: expsList,
                    allAvailableJuryPresidents: expsList,
                    alertNbrSallesToChoose: nbClassRooms,
                    showListOfCheckedStudentsForAPSTN: true
                });

                if (this.state.listOfCheckedStudentsForAPSTN.length === 0) {
                    this.setState({
                        disableCheckDispoPE: false,
                        existAvailablePEForAdvancedPlanif: false
                    });
                }
            },
            (error) => {
            }
        );
    }


    handleAutoPlanifSallesExpertsJuryPresidentsForAP() {

        // console.log('----------------------> SARS: ' + this.state.listOfCheckedStudentsForAPSTN.length);

        this.setState({
            loadAffectClassRoomsforAP: true,
            showListOfCheckedStudentsForAPSTN: false,
            disableValidateAutoPlanifBTN: true
        });

        let idsOfCheckedStudentsForAPSTN = [];

        for (let idStu of this.state.checkedStudents) {
            idsOfCheckedStudentsForAPSTN.push(idStu);
        }

        AuthService.handleAutoPlanifSallesExpertsJuryPresidentsForAP(
            idsOfCheckedStudentsForAPSTN,
            this.state.selectedSalles,
            this.state.selectedExperts,
            this.state.selectedJuryPresidents).then(
            (response) => {

                for (let cs of idsOfCheckedStudentsForAPSTN) {
                    // console.log('---------------> nbvcxw 1: ' + cs.identifiant);
                    this.setState({
                        listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != cs),
                    });
                    // console.log('---------------> nbvcxw 2: ' + this.state.listOfAuthorizedStudentsToSTN.length);
                }

                var requestAS = new XMLHttpRequest();
                requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
                requestAS.send(null);
                this.state.nbrNotifiedSTN = requestAS.responseText;

                var requestPS = new XMLHttpRequest();
                requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestPS.send(null);
                this.state.nbrPlanifiedSTN = requestPS.responseText;

                var requestSDS = new XMLHttpRequest();
                requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
                requestSDS.send(null);
                this.state.nbrNotPlanifiedSTN = requestSDS.responseText;

                var requestUD = new XMLHttpRequest();
                requestUD.open("GET", API_URL_MESP + "responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id, false);
                requestUD.send(null);
                this.state.nbUploadedReports = requestUD.responseText;

                var requestVD = new XMLHttpRequest();
                requestVD.open("GET", API_URL_MESP + "responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id, false);
                requestVD.send(null);
                this.state.nbValidatedReports = requestVD.responseText;

                var requestID = new XMLHttpRequest();
                requestID.open("GET", API_URL_MESP + "responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id, false);
                requestID.send(null);
                this.state.nbIncompletedReports = requestID.responseText;

            })
            .then(() => {

                /******************************************************/
                this.state.listOfCheckedStudentsForMPSTN = [];
                this.state.allTeachersMP = [];

                var requestAllTeaMP = new XMLHttpRequest();
                requestAllTeaMP.open("GET", API_URL_MESP + "allTeachersFromPEMP", false);
                requestAllTeaMP.send(null);
                let strIntoObjTea = JSON.parse(requestAllTeaMP.responseText);

                for (let tea of strIntoObjTea) {
                    let result = Object.entries(tea);
                    result.map((item, index) => {
                        this.state.allTeachersMP.push({value: item[0], label: item[1]});
                    });
                }

                var requestAllCRMP = new XMLHttpRequest();
                requestAllCRMP.open("GET", API_URL_MESP + "allClassroomsMP", false);
                requestAllCRMP.send(null);
                let strIntoObjCR = JSON.parse(requestAllCRMP.responseText);

                strIntoObjCR.forEach((cr) => {
                    this.state.allClassroomsMP.push({value: cr, label: cr});
                });

                AuthService.checkedStudentsToMultiplePlanifSTN(this.state.checkedStudents).then(
                    (response) => {

                        let stuList = this.state.listOfCheckedStudentsForMPSTN.slice();
                        let idCheckedStuds = [];
                        for (let stu of response.data) {
                            stuList.push(stu);
                            idCheckedStuds.push(stu.identifiant);
                        }

                        var requestCMMP = new XMLHttpRequest();
                        requestCMMP.open("GET", API_URL_MESP + "giveOkConfirmNotifyMultiplePlanif/" + idCheckedStuds, false);
                        requestCMMP.send(null);
                        let giveOK = requestCMMP.responseText;

                        this.setState({
                            listOfCheckedStudentsForMPSTN: stuList,
                            showFullPlanningForAP: true,
                            showListOfCheckedStudentsForAPSTN: false,
                            loadAffectClassRoomsforAP: false,
                            giveOkConfirmAndNotifyMP: giveOK,
                            checkedStudents: []
                        });

                        // console.log('---------------> SARS 1208: ' + this.state.giveOkConfirmAndNotifyMP + " - " + giveOK);

                    },
                    (error) => {
                    }
                );
                /******************************************************/

            })
            .catch(() => {

            });
    }

    validateEntriesForMP() {
        this.setState({loadVerifyEntriesForValidate: true});

        // console.log('-------------- *2.1** -----------> idCheckedStuds: ', this.state.idCheckedStuds);
        // console.log('-------------- *2.2** -----------> idCheckedStuds: ', this.state.checkedStudents);

        /*let currentCheckedStuds = [];
      for (let stu of response.data)
      {
          idCheckedStudents.push(stu.identifiant);
      }*/

        AuthService.checkAllFilledEntriesToBePlanifiedSTN(currentResponsableServiceStage.id, this.state.checkedStudents).then(
            (response) => {

                // console.log('--------------THIS-----------> SARS123: ', response);
                this.setState({
                    loadVerifyEntriesForValidate: false,
                    // successShowPlanificationMultiple: false,
                    giveOkConfirmNotifyMultiplePlanif: response.data
                });

                this.state.checked = {};

                if (this.state.giveOkConfirmNotifyMultiplePlanif === false) {
                    // console.log('-------------- THIS false -----------> SARS123: ', response);
                    this.setState({openPopupLackEntriesForNotif: true});
                }

                // console.log('----------------------------> hi1234: ', this.state.checked);

                if (this.state.giveOkConfirmNotifyMultiplePlanif === true) {
                    // console.log('-------------- THIS true -----------> SARS123: ', response);
                    this.setState({
                        showFullPlanningForAP: false,
                        openPopupFilledEntriesForNotif: true
                    });
                    // console.log('----------------------------> MP-1');

                    for (let cs of this.state.checkedStudents) {
                        // console.log('----------------------------> MP-2');

                        this.setState({
                            //listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != cs ),
                            checkedStudents: [],
                            checked: {}
                        });

                        this.updateBadgeOnSidebarForGCAutoPlanif();

                        // console.log('----------------------------> hi123: ', this.state.checked);


                        /********************************************** Case GC-Binome START ************************************************/
                        /*
                        var requestBin = new XMLHttpRequest();
                        requestBin.open("GET", API_URL_MESP + "itsGCWithPair/" + cs, false);
                        requestBin.send(null);
                        let itsEM = requestBin.responseText;

                        let idPairGC = "";
                        if (itsEM.includes('YES'))
                        {
                            idPairGC = itsEM.substring(3);
                            this.setState({
                                listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idPairGC)
                            });
                        }
                        */
                        /********************************************** Case GC-Binome END ************************************************/

                    }

                    // console.log('----------------------------> MP-9');
                }

                // console.log('----------------------------> hi12345: ', this.state.checked);

            },
            (error) => {
            }
        );
    }

    updateBadgeOnSidebarForGCAutoPlanif() {
        let requestAS = new XMLHttpRequest();
        requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
        requestAS.send(null);

        let requestPS = new XMLHttpRequest();
        requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
        requestPS.send(null);
        this.state.nbrPlanifiedSTN = requestPS.responseText;

        let requestSDS = new XMLHttpRequest();
        requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
        requestSDS.send(null);

        this.state.nbrNotifiedSTN = requestAS.responseText;
        this.state.nbrPlanifiedSTN = requestPS.responseText;
        this.state.nbrNotPlanifiedSTN = requestSDS.responseText;
    }

    iterateNotifForMultiplePlanif(cs, nbOcc) {
        //this.setState({openPopupFilledEntriesForNotif: false});
        // console.log('----------------------------> MP-2');
        AuthService.validateAndNotifySoutenanceActorsForMP(cs).then(
            (response) => {
                // console.log('----------------------------> MP-3');
                this.setState({
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != cs),
                    checkedStudents: [],
                    checked: {}
                });

                /********************************************** Case GC-Binome START ************************************************/

                var requestBin = new XMLHttpRequest();
                requestBin.open("GET", API_URL_MESP + "itsGCWithPair/" + cs, false);
                requestBin.send(null);
                let itsEM = requestBin.responseText;

                let idPairGC = "";
                if (itsEM.includes('YES')) {
                    idPairGC = itsEM.substring(3);
                    this.setState({
                        listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idPairGC)
                    });
                }

                /********************************************** Case GC-Binome END ************************************************/

                // console.log('----------------------------> MP-4: ' + nbOcc);

                if (nbOcc === this.state.listOfCheckedStudentsForMPSTN.length) {
                    // console.log('----------------------------> MP-5: DONE');
                    this.setState({
                        loadValidateAndNotifyforAMP: false,
                        successShowPlanificationMultiple: false,
                        successValidateAndNotifyPopupMP: true,
                        successShowPlanificationAdvanced: false,
                        showListOfCheckedStudentsForAPSTN: false
                    });

                    // console.log('----------------------------> MP-6');

                    this.updateBadgeOnSidebarForGCAutoPlanif();

                    // console.log('----------------------------> MP-7');

                }

            },

            (error) => {
            }
        );

        // console.log('----------------------------> MP-8');
    }

    iterateNotifForAdvancedPlanif(cs, nbOcc) {
        // console.log('----------------------------> MP-2');
        AuthService.validateAndNotifySoutenanceActorsForMP(cs.identifiant).then(
            (response) => {
                // console.log('----------------------------> MP-3');
                this.setState({
                    listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != cs.identifiant),
                    checked: {}
                });
                // console.log('-------------------sars123---------> MP-4: ' + nbOcc + " - " + this.state.listOfCheckedStudentsForMPSTN);

                if (this.state.listOfCheckedStudentsForMPSTN.length === nbOcc) {
                    // console.log('----------------------------> MP-5: DONE');
                    this.setState({
                        loadValidateAndNotifyforAMP: false,
                        successShowPlanificationMultiple: false,
                        successValidateAndNotifyPopupMP: true,
                        successShowPlanificationAdvanced: false,
                        showListOfCheckedStudentsForAPSTN: false
                    });

                    // console.log('----------------------------> MP-6');

                    var requestAS = new XMLHttpRequest();
                    requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
                    requestAS.send(null);
                    this.state.nbrNotifiedSTN = requestAS.responseText;

                    var requestPS = new XMLHttpRequest();
                    requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
                    requestPS.send(null);
                    this.state.nbrPlanifiedSTN = requestPS.responseText;

                    var requestSDS = new XMLHttpRequest();
                    requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
                    requestSDS.send(null);
                    this.state.nbrNotPlanifiedSTN = requestSDS.responseText;

                    var requestUD = new XMLHttpRequest();
                    requestUD.open("GET", API_URL_MESP + "responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id, false);
                    requestUD.send(null);
                    this.state.nbUploadedReports = requestUD.responseText;

                    var requestVD = new XMLHttpRequest();
                    requestVD.open("GET", API_URL_MESP + "responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id, false);
                    requestVD.send(null);
                    this.state.nbValidatedReports = requestVD.responseText;

                    var requestID = new XMLHttpRequest();
                    requestID.open("GET", API_URL_MESP + "responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id, false);
                    requestID.send(null);
                    this.state.nbIncompletedReports = requestID.responseText;

                    // console.log('----------------------------> MP-7');

                }

            },

            (error) => {
            }
        );

        // console.log('----------------------------> MP-8');

        /********************************************** Case GC-Binome START ************************************************/

        var requestBin = new XMLHttpRequest();
        requestBin.open("GET", API_URL_MESP + "itsGCWithPair/" + cs.identifiant, false);
        requestBin.send(null);
        let itsEM = requestBin.responseText;

        let idPairGC = "";
        if (itsEM.includes('YES')) {
            idPairGC = itsEM.substring(3);
            this.setState({
                listOfAuthorizedStudentsToSTN: this.state.listOfAuthorizedStudentsToSTN.filter(el => el.identifiant != idPairGC),
            });
        }

        /********************************************** Case GC-Binome END ************************************************/

    }

    validateAndNotifyForMP() {

        this.setState({
            showFullPlanningForAP: false,
            loadValidateAndNotifyforAMP: true,
            giveOkConfirmAndNotifyMP: false
        });
        // console.log('----------------------------> MP-1');

        let nbOcc = 0;

        let inc = 1;
        for (let cs of this.state.listOfCheckedStudentsForMPSTN) {
            setTimeout(() => {
                // console.log('WAKING AFTER 10s ----------> UNIT: ' + inc + " - " + cs + " ---> " + new Date());

                // setTimeout(this.onLoop.bind(this), 1000)
                nbOcc++;
                this.iterateNotifForAdvancedPlanif(cs, nbOcc);

            }, inc * 10000); // Treat Next Element AFTER 10s
            inc++;
        }

        // console.log('----------------------------> MP-9');

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

        // // console.log("Check valAndNot -------CONCERV SH------ " + this.state.selectedEndHour);

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
            refuseEndHour: false
        });

        // console.log('------------------------------------- formattedSelectedDate 6--->:' + this.state.formattedSelectedDate);

        // console.log('--------------PLAN---------olk-1--> : ' + this.state.selectedStartHour + " - " + this.state.selectedEndHour);
        // console.log('--------------PLAN---------olk-2--> : ' + formattedStartHour + " - " + formattedEndHour);

        if (formattedStartHour < formattedEndHour) {
            // console.log('--------------PLAN------------ end hour: ' + e.target.value);
            // console.log('---------------PLAN----------- end hour: ' + this.state.idEtToSTN);

            this.setState({
                refuseEndHour: false
            });

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


    handleChangeStartHourAP(e) {
        let value = e.target.value;
        this.setState({
            selectedStartHourAP: value,
            selectedEndHourAP: "00:00",
            showListOfCheckedStudentsForAPSTN: false,
            existAvailablePEForAdvancedPlanif: true
        });

        // console.log("Check valAndNot -------CONCERV SH------ " + this.state.selectedEndHour);

        let formattedStartHour = Number(value.replaceAll(":", ""));
        let formattedEndHour = Number("0000");

        if (formattedStartHour >= formattedEndHour) {
            this.setState({
                refuseEndHourAP: true
            });
        }

    }

    handleChangeEndHourAP(e) {
        let value = e.target.value;
        let formattedStartHour = Number(this.state.selectedStartHourAP.replaceAll(":", ""));
        let formattedEndHour = Number(value.replaceAll(":", ""));

        // console.log("Check valAndNot -------CONCERV EH------ " + this.state.selectedEndHour);

        this.setState({
            selectedEndHourAP: value,
            refuseEndHourAP: false,
            showListOfCheckedStudentsForAPSTN: false,
            existAvailablePEForAdvancedPlanif: true
        });

        // console.log('------------------------------------- formattedSelectedDate 6--->:' + this.state.formattedSelectedDate);

        // console.log('--------------PLAN---------olk-1--> : ' + this.state.selectedStartHour + " - " + this.state.selectedEndHour);
        // console.log('--------------PLAN---------olk-2--> : ' + formattedStartHour + " - " + formattedEndHour);

        if (formattedStartHour < formattedEndHour) {
            // console.log('--------------PLAN------------ end hour: ' + e.target.value);
            // console.log('---------------PLAN----------- end hour: ' + this.state.idEtToSTN);

            this.setState({
                refuseEndHourAP: false,
                disableCheckDispoPEBTN: false
            });

            // console.log('--------------PLAN------------ Start Hour: ' + this.state.selectedStartHour);
            // console.log('--------------PLAN------------ End Hour: ' + this.state.selectedEndHour);
        }
        if (formattedStartHour >= formattedEndHour) {
            this.setState({
                refuseEndHourAP: true
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

        // console.log('------------------------------hello2021-------1-------------> HI: ' + resultDPEPL.salle);

        if (resultDPEPL.salle !== null) {
            this.setState({salle: resultDPEPL.salle})
            this.state.salle = resultDPEPL.salle;
        }

        // console.log('------------------------------hello2021-------2-------------> HI: ' + this.state.salle);


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
            (resultDPEPL.salle != null && resultDPEPL.salle !== "Remote") ||
            (resultDPEPL.dateSoutenance != null && resultDPEPL.heureDebutSoutenance != null && resultDPEPL.heureFinSoutenance != null)) {

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

        let formattedDate = null;
        if (bridgeSDt === null) {
            formattedDate = null;
        }

        if (bridgeSDt !== null) {
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

    handleClosePopupShowStudentDetails() {
        this.setState({openPopupShowStudentDetails: false});
    }

    handleCloseSuccessAffectJPEnsPopup() {
        this.setState({successAffectJPEnsPopup: false});
    }

    handleCloseSuccessValidateAndNotifyPopup() {
        this.setState({successValidateAndNotifyPopup: false});
    }

    handleCloseSuccessValidateUniquePlanifPopup() {
        this.setState({successValidateUniquePlanifPopup: false});
    }

    handleCloseSuccessValidateAndNotifyPopupMP() {
        this.setState({successValidateAndNotifyPopupMP: false});
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

    onChangeSelectExpertMP(studentId, selectedExpertId) {
        AuthService.affectExpertMP(studentId, selectedExpertId).then(
            (response) => {
                this.setState({
                    allTeachersMP: this.state.allTeachersMP.filter(el => el.value != selectedExpertId)
                })
            },
            (error) => {
            }
        );
    };

    onChangeSelectJuryPresidentMP(studentId, selectedJuryPresidentOption) {
        AuthService.affectJuryPresidentMP(studentId, selectedJuryPresidentOption).then(
            (response) => {
                this.setState({
                    allTeachersMP: this.state.allTeachersMP.filter(el => el.value != selectedJuryPresidentOption)
                })
            },
            (error) => {
            }
        );
    };

    onChangeSelectClassroomMP(studentId, selectedClassroomOption) {
        AuthService.affectSalleMP(studentId, selectedClassroomOption).then(
            (response) => {

            },
            (error) => {
            }
        );
    };

    onChangeDateSoutenanceMP(studentId, dateSoutenance) {

        // console.log('----------------------------> HI123: ', dateSoutenance);

        if (dateSoutenance === null) {
            dateSoutenance = new Date();
            let formattedSelectedDate = this.gotFormattedDate(dateSoutenance);
            let hourStartSoutenance = "08:00";
            let hourEndSoutenance = "--:--";

            AuthService.affectDateSoutenanceMPWithInit(studentId, formattedSelectedDate).then(
                (response) => {
                    this.setState({
                        listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                            ...el,
                            dateSoutenance,
                            hourStartSoutenance,
                            hourEndSoutenance
                        } : el)),
                    })
                },
                (error) => {
                }
            );
        }

        if (dateSoutenance !== null) {
            let formattedSelectedDate = this.gotFormattedDate(dateSoutenance);
            let hourStartSoutenance = "08:00";
            let hourEndSoutenance = "--:--";

            AuthService.affectDateSoutenanceMP(studentId, formattedSelectedDate).then(
                (response) => {
                    this.setState({
                        listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                            ...el,
                            dateSoutenance,
                            hourStartSoutenance,
                            hourEndSoutenance
                        } : el)),
                    })
                },
                (error) => {
                }
            );
        }

    }

    onChangeStartHourMP(studentId, hourStartSoutenance) {
        if (hourStartSoutenance === "") {
            hourStartSoutenance = "--:--";
            let hourEndSoutenance = "--:--";
            AuthService.affectStartHourSoutenanceMPWithInit(studentId, hourStartSoutenance).then(
                (response) => {
                    this.setState({
                        listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                            ...el,
                            hourStartSoutenance,
                            hourEndSoutenance
                        } : el)),
                    })
                },
                (error) => {
                }
            );
        }

        if (hourStartSoutenance !== "" && hourStartSoutenance !== "--:--") {
            let hourEndSoutenance = "--:--";
            AuthService.affectStartHourSoutenanceMP(studentId, hourStartSoutenance).then(
                (response) => {
                    this.setState({
                        listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                            ...el,
                            hourStartSoutenance,
                            hourEndSoutenance
                        } : el)),
                    })
                },
                (error) => {
                }
            );
        }
    }

    onChangeEndHourMP(studentId, hourEndSoutenance, hourStartSoutenance) {

        // console.log('--------> PIC123: ' + hourEndSoutenance);

        if (hourEndSoutenance === "") {
            hourEndSoutenance = "--:--";
            AuthService.affectEndHourSoutenanceMP(studentId, hourEndSoutenance).then(
                (response) => {
                    this.setState({
                        listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                            ...el,
                            hourEndSoutenance
                        } : el)),
                    })
                },
                (error) => {
                }
            );
        }

        let formattedStartHour = Number(hourStartSoutenance.replaceAll(":", ""));
        let formattedEndHour = Number(hourEndSoutenance.replaceAll(":", ""));

        // console.log('----------------> Result Hours - 1: ' + hourStartSoutenance + " - " + hourEndSoutenance + " | " + formattedStartHour + " - " + formattedEndHour);

        if (formattedStartHour >= formattedEndHour) {
            let decideEHS = "Error";
            this.setState({
                listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                    ...el,
                    decideEHS
                } : el)),
            });
        }

        if (formattedEndHour > formattedStartHour) {
            let decideEHS = "";
            this.setState({
                listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                    ...el,
                    decideEHS
                } : el)),
            });

            AuthService.affectEndHourSoutenanceMP(studentId, hourEndSoutenance).then(
                (response) => {
                    this.setState({
                        listOfCheckedStudentsForMPSTN: this.state.listOfCheckedStudentsForMPSTN.map(el => (el.identifiant === studentId ? {
                            ...el,
                            hourEndSoutenance
                        } : el)),
                    })
                },
                (error) => {
                }
            );
        }
    }

    handleOpenModalPlanificationMultiple() {

        this.setState({
            loadMultiplePlanification: true
        });

        this.state.listOfCheckedStudentsForMPSTN = [];
        this.state.allTeachersMP = [];

        var requestAllTeaMP = new XMLHttpRequest();
        requestAllTeaMP.open("GET", API_URL_MESP + "allTeachersFromPEMP", false);
        requestAllTeaMP.send(null);
        let strIntoObjTea = JSON.parse(requestAllTeaMP.responseText);

        for (let tea of strIntoObjTea) {
            let result = Object.entries(tea);
            result.map((item, index) => {
                //console.log('key is:- ', item[0] , ' and value is:- ', item[1]);
                this.state.allTeachersMP.push({value: item[0], label: item[1]});
            });
            //console.log('###************************************************#### Size EXP-MP TEA: ',SimpleFormExample.js);
        }

        var requestAllCRMP = new XMLHttpRequest();
        requestAllCRMP.open("GET", API_URL_MESP + "allClassroomsMP", false);
        requestAllCRMP.send(null);
        let strIntoObjCR = JSON.parse(requestAllCRMP.responseText);

        strIntoObjCR.forEach((cr) => {
            this.state.allClassroomsMP.push({value: cr, label: cr});
        });

        // console.log('############################# Size EXP-MP: ' + this.state.allTeachersMP.length)

        AuthService.checkedStudentsToMultiplePlanifSTN(currentResponsableServiceStage.id, this.state.checkedStudents).then(
            (response) => {

                let idCheckedStudents = this.state.idCheckedStuds.slice();
                let stuList = this.state.listOfCheckedStudentsForMPSTN.slice();
                for (let stu of response.data) {
                    stuList.push(stu);
                    idCheckedStudents.push(stu.identifiant);
                }

                var requestCMMP = new XMLHttpRequest();
                requestCMMP.open("GET", API_URL_MESP + "giveOkConfirmNotifyMultiplePlanif/" + idCheckedStudents, false);
                requestCMMP.send(null);
                let giveOK = requestCMMP.responseText;

                // console.log('----------------------------> SARS123: ' + giveOK);

                this.setState({
                    //loadList: false,
                    listOfCheckedStudentsForMPSTN: stuList,
                    loadMultiplePlanification: false,
                    successShowPlanificationMultiple: true,
                    helloworld: giveOK,
                    idCheckedStuds: idCheckedStudents
                });

            },
            (error) => {
            }
        );
    }


    handleOpenModalPlanificationAdvanced() {
        this.setState({
            listOfCheckedStudentsForAPSTN: [],
            disableCheckDispoPE: false,
            alertNbrSallesToChoose: 0,
            showFullPlanningForAP: false,
            disableValidateAutoPlanifBTN: false,
            selectedSalles: [],
            selectedExperts: [],
            selectedJuryPresidents: []
        });

        AuthService.checkedStudentsToAdvancedPlanifSTN(this.state.checkedStudents).then(
            (response) => {

                let stuList = this.state.listOfCheckedStudentsForAPSTN.slice();
                for (let stu of response.data) {
                    stuList.push(stu);
                }

                this.setState({
                    listOfCheckedStudentsForAPSTN: stuList,
                    successShowPlanificationAdvanced: true,
                    selectedDateAP: new Date(),
                    selectedStartHourAP: "08:00",
                    selectedEndHourAP: "--:--"
                });

            },
            (error) => {
            }
        );
    }


    handleCloseModalPlanificationMultiple() {
        this.setState({successShowPlanificationMultiple: false});
    }

    handleCloseModalPlanificationAdvanced() {
        this.setState({successShowPlanificationAdvanced: false});
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

        this.state.selectedDate = date;
        this.state.formattedSelectedDate = this.gotFormattedDate(date);
        this.state.selectedStartHour = "00:00";
        this.state.selectedEndHour = "00:00";

        // console.log('------------------------------------- formattedSelectedDate 4--->' + this.state.formattedSelectedDate);

    };

    onChangeDateSoutenanceAP = (date) => {
        this.setState({
            selectedDateAP: date,
            selectedStartHourAP: "00:00",
            selectedEndHourAP: "00:00",
            showListOfCheckedStudentsForAPSTN: false,
            existAvailablePEForAdvancedPlanif: true
        });

        this.state.selectedDateAP = date;
        this.state.selectedStartHourAP = "00:00";
        this.state.selectedEndHourAP = "00:00";

        // console.log('------------------------------------- formattedSelectedDate 4--->' + this.state.formattedSelectedDate);

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
            openPopupAffectSoutenance,
            openPopupShowPV,
            listOfAvailableTeachersToSTN,
            listOfAvailableClassroomsToSTN,
            affectationKind,
            successAffectJPEnsPopup,
            successAffectJPNEnsPopup,
            successAffectExpertPopup,
            successShowPlanificationMultiple,
            successShowPlanificationAdvanced,
            disponibilityPE,
            successAffectSallePopup,
            affectedEXP,
            affectationPresidentJury,
            listOfAvailableExpertsToSTN,
            successValidateAndNotifyPopup,
            successValidateUniquePlanifPopup,
            successValidateAndNotifyPopupMP,
            refuseEndHour,
            refuseEndHourAP,
            statusValidateAndNotify,
            loadingValidateAndNotify,
            loadValidateAndNotifyforAMP,
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
            loadList,
            allTraineeshipKinds,
            pairFullName,
            selectedStudentBinome,
            nbrNotifiedSTN,
            nbrPlanifiedSTN,
            nbrNotPlanifiedSTN,
            nbUploadedReports,
            nbValidatedReports,
            nbIncompletedReports,
            checkedStudents,
            listOfCheckedStudentsForMPSTN,
            listOfCheckedStudentsForAPSTN,
            showListOfCheckedStudentsForAPSTN,
            loadCheckDisponibilityPEforAP,
            loadAffectClassRoomsforAP,
            loadMultiplePlanification,
            alertNbrSallesToChoose,
            disableCheckDispoPE,
            disableCheckDispoPEBTN,
            selectedSalles,
            selectedExperts,
            selectedJuryPresidents,
            disableValidateAutoPlanifBTN,
            showFullPlanningForAP,
            loadVerifyEntriesForNotif,
            loadVerifyEntriesForValidate,
            openPopupLackEntriesForNotif,
            openPopupFilledEntriesForNotif,
            giveOkConfirmAndNotifyMP,
            loadingValidateUniquePlanif,
            existAvailablePEForAdvancedPlanif
        } = this.state;


        return (
            <>

                <TheSidebar data1={nbrNotifiedSTN} data3={nbrPlanifiedSTN} data2={nbrNotPlanifiedSTN}
                            dataUR={nbUploadedReports} dataVR={nbValidatedReports} dataIR={nbIncompletedReports}/>

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
                          Liste des Étudiants avec Soutenances Planifiées & Non Notifiées - ({listOfAuthorizedStudentsToSTNByFilter.length}/{listOfAuthorizedStudentsToSTN.length})
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
                                                                <><Spinner animation="grow" variant="primary"/>hi1</>
                                                            )}
                                                        </CRow>
                                                    </CCol>
                                                    <CCol md="1" className="my-auto text-right">
                                                        <CRow>
                                                            {(
                                                                !showLoadingExcelGreenIcon && listOfAuthorizedStudentsToSTN.length > 0 &&
                                                                <CButton shape="pill"
                                                                         onClick={() => this.handleDownloadAllPlanningSoutenanceExcel()}>
                                                                    <CTooltip content="Télécharger Planning Soutenances"
                                                                              placement="top">
                                                                        <img src={excelGreenIcon}
                                                                             className="cursorPointer"
                                                                             width="40px" height="40px"/>
                                                                    </CTooltip>
                                                                </CButton>
                                                            )}
                                                            {(
                                                                showLoadingExcelGreenIcon &&
                                                                <><Spinner animation="grow" variant="success"/>hi2</>
                                                            )}
                                                        </CRow>
                                                    </CCol>
                                                </CRow>
                                                <CRow>
                                                    &nbsp;&nbsp;&nbsp;
                                                    <Checkbox checked={this.state.checkedAll}
                                                              color="secondary"
                                                              onChange={this.handleSelectDeselectAll.bind(this)}
                                                              inputProps={{"aria-label": "secondary checkbox"}}/>
                                                    &nbsp;
                                                    <span
                                                        style={{color: "#ff1a75", fontSize: "12px", marginTop: "11px"}}>
                            Select / Deselect All
                          </span>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    {(
                                                        checkedStudents.length > 0 && !loadMultiplePlanification &&
                                                        <Button style={{color: "#ff00bf", fontSize: "12px"}}
                                                                onClick={() => this.handleOpenModalPlanificationMultiple()}>
                                                            <CIcon content={freeSet.cilCalendarCheck}/> &nbsp; Modifier
                                                            Planification Multiple
                                                        </Button>
                                                    )}

                                                    {(
                                                        loadMultiplePlanification &&
                                                        <Spinner animation="grow" variant="danger"/>
                                                    )}
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
                                                                         onClick={() => this.handlePlanifySoutenance(index, item.identifiant, item.fullName, item.traineeKind)}>
                                                                    <CTooltip content="Planifier Soutenance"
                                                                              placement="top">
                                                                        <CIcon content={freeSet.cilCalendarCheck}/>
                                                                    </CTooltip>
                                                                </CButton>

                                                                &nbsp;
                                                                <CButton shape="pill"
                                                                         color="info"
                                                                         size="sm"
                                                                         aria-expanded="true"
                                                                         onClick={() => this.handleConsultPV(item.identifiant, item.fullName, item.classe)}>
                                                                    <CTooltip content="Consulter PV" placement="top">
                                                                        <CIcon content={freeSet.cilContact}/>
                                                                    </CTooltip>
                                                                </CButton>

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
                                                    checkboxSelect: (item) => (
                                                        <td>
                                                            <center>
                                                                <Checkbox
                                                                    checked={this.state.checked[item.identifiant] === true}
                                                                    color="secondary"
                                                                    onChange={() => this.gotSelectedStudents(item.identifiant)}
                                                                    inputProps={{"aria-label": "secondary checkbox"}}/>
                                                            </center>
                                                        </td>
                                                    )
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
                                    <CRow>
                                        <CCol md="3">
                                            <span className="redMark">
                                                Planifier Soutenance
                                            </span>
                                            &nbsp;&nbsp;
                                        </CCol>
                                        <CCol md="9" className="colPadding">
                                            <div className="myDivMarginTop">
                                                <span className="redMark" style={{fontSize: "12px"}}>
                                                  {selectedStudentFullName}
                                                </span>
                                            </div>
                                        </CCol>
                                    </CRow>
                                    {(
                                        selectedStudentBinome !== "" &&
                                        <>
                                            &nbsp;

                                            <span className="clignotePrimary" style={{fontSize: "13px"}}>
                                    <ins>
                                      en binôme avec
                                    </ins>
                                  </span>

                                            &nbsp;
                                            <span className="redMark" style={{fontSize: "12px"}}>
                                    {selectedStudentBinome}
                                  </span>
                                        </>
                                    )}
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
                                                        {
                                                            this.state.selectedStartHour !== undefined && this.state.selectedEndHour !== null &&
                                                            this.state.selectedEndHour !== undefined &&
                                                            this.state.selectedStartHour.length !== 0 &&
                                                            this.state.selectedEndHour.length !== 0 &&
                                                            !refuseEndHour && (
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
                                                        {this.state.selectedStartHour !== undefined &&
                                                        this.state.selectedEndHour !== undefined &&
                                                        this.state.selectedEndHour !== null &&
                                                        this.state.selectedStartHour.length !== 0 &&
                                                        this.state.selectedEndHour.length !== 0 &&
                                                        !refuseEndHour &&
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
                                                            <center>
                                                                <Spinner animation="grow" variant="danger"/>
                                                                <br/><br/>
                                                                <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ...</span>
                                                                <br/>
                                                                <span style={{color: "#cc0000", fontSize: "13px"}}>Vérification de la Disponibilité des Experts En Cours.</span>
                                                            </center>
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
                                                        {this.state.selectedStartHour !== undefined &&
                                                        this.state.selectedEndHour !== undefined &&
                                                        this.state.selectedEndHour !== null &&
                                                        this.state.selectedStartHour.length !== 0 &&
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
                                                                                                .checkedAK === i
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

                                                        {this.state.selectedStartHour !== undefined &&
                                                        this.state.selectedEndHour !== undefined &&
                                                        this.state.selectedEndHour !== null &&
                                                        this.state.selectedStartHour.length !== 0 &&
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
                                                                    <center>
                                                                        <Spinner animation="grow" variant="danger"/>
                                                                        <br/><br/>
                                                                        <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ...</span>
                                                                        <br/>
                                                                        <span style={{color: "#cc0000", fontSize: "13px"}}>Vérification de la Disponibilité des Présidents Jury En Cours.</span>
                                                                    </center>
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
                                                            this.state.selectedEndHour !== undefined &&
                                                            this.state.selectedEndHour !== null &&
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
                                                            <center>
                                                                <Spinner animation="grow" variant="danger"/>
                                                                <br/><br/>
                                                                <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ...</span>
                                                                <br/>
                                                                <span style={{color: "#cc0000", fontSize: "13px"}}>Vérification de la Disponibilité des Salles En Cours.</span>
                                                            </center>
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
                                                                    &nbsp;dans la salle <span
                                                                    className="text-info"> {salle} </span>.
                                                                </>
                                                            )}
                                                            {(
                                                                salle === "Remote" &&
                                                                <>
                                                                    <ins>à distance</ins>
                                                                    &nbsp;via <span className="text-info"> un Meet envoyé par Mail </span>.
                                                                </>
                                                            )}

                                                            <br/><br/>
                                                            Cliquer sur ce bouton pour confirmer cet état et notifier
                                                            l'Étudiant, l'Encadrant Pédagogique, le Membre et le
                                                            Président de Jury de la date programmée.
                                                            <br/><br/>

                                                            {(
                                                                !loadingValidateUniquePlanif && !loadingValidateAndNotify &&
                                                                <Button variant="outlined"
                                                                        color="secondary"
                                                                        onClick={this.validateAndNotifyUniquePlanif}
                                                                        disabled={loadingValidateAndNotify}>
                                                                    Confirmer & Notifier
                                                                </Button>
                                                            )}

                                                            <br/><br/>

                                                            {(
                                                                loadingValidateAndNotify &&
                                                                <CRow>
                                                                    <CCol xs="12">
                                                                        <center>
                                                                            <CSpinner color="danger" style={{width: '3rem', height: '3rem'}}/>
                                                                            <br/><br/>
                                                                            <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ...</span>
                                                                            <br/>
                                                                            <span style={{color: "#cc0000", fontSize: "13px"}}>Envoi des Notifications aux Étudiants et Membres de Jury En Cours.</span>
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
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
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
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
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
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
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
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
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
                                    successValidateAndNotifyPopup
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessValidateAndNotifyPopup
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                La Validation et la Notification de la soutenance sont effectuées.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessValidateAndNotifyPopup
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
                                open={successValidateUniquePlanifPopup}
                                onClose={this.handleCloseSuccessValidateUniquePlanifPopup}
                                aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                La Planification de la soutenance est validée.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={this.handleCloseSuccessValidateUniquePlanifPopup}
                                        color="primary">
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
                                                                <span className="text-primary"
                                                                      style={{fontSize: "16px", fontWeight: "bold"}}>
                                                                  PV de la Soutenance
                                                                </span>
                                    <br/>
                                    <span className="redMark" style={{fontSize: "14px"}}>
                                                                  {selectedStudentFullName}
                                                                </span>
                                    {(
                                        (pairFullName !== null) &&
                                        <>
                                            &nbsp;&nbsp;
                                            <span className="clignotePrimary" style={{fontSize: "13px"}}>
                        <ins>
                          en binôme avec
                        </ins>
                      </span>

                                            &nbsp;&nbsp;
                                            <span className="redMark" style={{fontSize: "14px"}}>
                        {pairFullName}
                      </span>
                                        </>
                                    )}
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
                                                    <span style={{fontSize: "13px"}}>{affectedJP.nom}</span> &nbsp;<span
                                                    className="note">(Affectation Interne)</span>
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
                      <span style={{fontSize: "13px"}}>{selectedEndHour}</span>

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
                                    open={openPopupShowStudentDetails}
                                    onClose={this.handleClosePopupShowStudentDetails}
                                    aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                      <span className="text-primary" style={{fontSize: "16px", fontWeight: "bold"}}>
                        Contact de l'Étudiant
                      </span>
                                    <br/>
                                    <span className="text-primary" style={{fontSize: "14px"}}>
                     {selectedStudentFullName}
                  </span>
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
                            <Dialog fullHight
                                    fullWidth
                                    maxWidth="lg"
                                    keepMounted
                                    open={successShowPlanificationMultiple}
                                    onClose={this.handleCloseModalPlanificationMultiple}
                                    aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                                                          <span style={{
                                                              color: "#cc0000",
                                                              fontSize: "15px",
                                                              fontWeight: "bold"
                                                          }}>
                                                            Planification Multiple
                                                          </span>
                                    <span style={{color: "#cc0000", fontSize: "15px", fontWeight: "bold"}}>
                                                            <br/> {checkedStudents.length}
                                                          </span>&nbsp;
                                    <span style={{color: "#cc0000", fontSize: "12px", fontStyle: "italic"}}>
                                                             étudiants sélectionnés
                                                          </span>
                                </DialogTitle>
                                <DialogContent>

                                    {(
                                        !loadValidateAndNotifyforAMP &&
                                        <CDataTable items={listOfCheckedStudentsForMPSTN}
                                                    fields={checkedStus}
                                                    columnFilter
                                                    hover
                                                    sorter
                                                    striped
                                                    bordered
                                                    size="sm"
                                                    itemsPerPage={8}
                                                    pagination
                                                    scopedSlots={{
                                                        membre: (item) => (
                                                            <td>
                                                                <CRow>
                                                                    <CCol xs="2">
                                                                        <Badge badgeContent={item.nbrStuTrainedByExpert}
                                                                               showZero color="secondary"
                                                                               style={{marginTop: "12px"}}>
                                                                            <PersonIcon/>
                                                                        </Badge>
                                                                    </CCol>
                                                                    <CCol xs="10">
                                                                        <Select key={item.membre}
                                                                                defaultValue={{
                                                                                    value: item.membre,
                                                                                    label: item.membre
                                                                                }}
                                                                                closeMenuOnSelect={true}
                                                                                value={this.state.allTeachersMP.value}
                                                                                components={animatedComponents}
                                                                                options={this.state.allTeachersMP}
                                                                                size="sm"
                                                                                onChange={(e) => this.onChangeSelectExpertMP(item.identifiant, e.value)}/>
                                                                    </CCol>
                                                                </CRow>
                                                            </td>
                                                        ),
                                                        juryPresident: (item) => (
                                                            <td>
                                                                <CRow>
                                                                    <CCol xs="2">
                                                                        <Badge
                                                                            badgeContent={item.nbrStuTrainedByJuryPresident}
                                                                            showZero color="error"
                                                                            style={{marginTop: "12px"}}>
                                                                            <PersonIcon/>
                                                                        </Badge>
                                                                    </CCol>
                                                                    <CCol xs="10">
                                                                        <Select key={item.juryPresident}
                                                                                defaultValue={{
                                                                                    value: item.juryPresident,
                                                                                    label: item.juryPresident
                                                                                }}
                                                                                closeMenuOnSelect={true}
                                                                                value={this.state.allTeachersMP.value}
                                                                                components={animatedComponents}
                                                                                options={this.state.allTeachersMP}
                                                                                onChange={(e) => this.onChangeSelectJuryPresidentMP(item.identifiant, e.value)}/>
                                                                    </CCol>
                                                                </CRow>
                                                            </td>
                                                        ),
                                                        dateSoutenance: (item) => (
                                                            <td style={{width: "200px"}}>
                                                                <DatePicker selected={new Date(item.dateSoutenance)}
                                                                            onChange={(e) => this.onChangeDateSoutenanceMP(item.identifiant, e)}
                                                                            isClearable
                                                                            className="form-control"
                                                                            dateFormat="dd-MM-yyyy"
                                                                            popperPlacement="auto"
                                                                            popperProps={{positionFixed: true}}/>
                                                            </td>
                                                        ),
                                                        hourStartSoutenance: (item, index) => (
                                                            <td key={index} style={{width: "200px"}}>
                                                                <TextField id="timeHSS"
                                                                           type="time"
                                                                           value={item.hourStartSoutenance}
                                                                           className={timeStyle}
                                                                           InputLabelProps={{shrink: true}}
                                                                           inputProps={{step: 300}}
                                                                           onChange={(e) => this.onChangeStartHourMP(item.identifiant, e.target.value)}/>
                                                            </td>
                                                        ),
                                                        hourEndSoutenance: (item, index) => (
                                                            <td key={index} style={{width: "200px"}}>
                                                                <TextField id="timeHES"
                                                                           type="time"
                                                                           value={item.hourEndSoutenance}
                                                                           className={timeStyle}
                                                                           InputLabelProps={{shrink: true}}
                                                                           inputProps={{step: 300}}
                                                                           error={item.decideEHS === "Error"}
                                                                           helperText={item.decideEHS}
                                                                           onChange={(e) => this.onChangeEndHourMP(item.identifiant, e.target.value, item.hourStartSoutenance)}/>
                                                            </td>
                                                        ),
                                                        salleSoutenance: (item) => (
                                                            <td style={{width: "200px"}}>
                                                                <Select key={item.salleSoutenance}
                                                                        defaultValue={{
                                                                            value: item.salleSoutenance,
                                                                            label: item.salleSoutenance
                                                                        }}
                                                                        closeMenuOnSelect={true}
                                                                        value={this.state.allClassroomsMP.value}
                                                                        components={animatedComponents}
                                                                        options={this.state.allClassroomsMP}
                                                                        onChange={(e) => this.onChangeSelectClassroomMP(item.identifiant, e.value)}
                                                                        popperPlacement="auto"
                                                                        popperProps={{positionFixed: true}}/>
                                                            </td>
                                                        ),
                                                    }}
                                        />
                                    )}

                                    {(
                                        loadValidateAndNotifyforAMP &&
                                        <CRow>
                                            <CCol xs="12">
                                                <center>
                                                    <br/><br/>
                                                    <CSpinner color="danger" style={{width: '3rem', height: '3rem'}}/>
                                                    <br/><br/>
                                                    <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ...</span>
                                                    <br/>
                                                    <span style={{color: "#cc0000", fontSize: "13px"}}>Envoi des Notifications aux Étudiants et Membres du Jury En Cours.</span>
                                                    <br/><br/><br/>
                                                </center>
                                            </CCol>
                                        </CRow>
                                    )}


                                </DialogContent>
                                <DialogActions>

                                    {(
                                        !loadVerifyEntriesForValidate && !loadVerifyEntriesForNotif && !loadValidateAndNotifyforAMP &&
                                        <Button variant="outlined"
                                                color="secondary"
                                                onClick={this.validateEntriesForMP}>
                                            Valider
                                        </Button>
                                    )}

                                    {(
                                        loadVerifyEntriesForValidate &&
                                        <>
                                            <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ... Validation du Planning En Cours.</span> &nbsp;&nbsp;&nbsp;
                                            <Spinner animation="grow" variant="danger"/>
                                        </>
                                    )}

                                    {(
                                        !loadVerifyEntriesForNotif && !loadVerifyEntriesForValidate && !loadValidateAndNotifyforAMP &&
                                        <Button variant="outlined"
                                                color="secondary"
                                                onClick={this.handleVerifyEntriesForNotif}>
                                            Valider & Notifier
                                        </Button>
                                    )}

                                    {(
                                        loadVerifyEntriesForNotif &&
                                        <>
                                            <span style={{color: "#cc0000", fontSize: "13px"}}>Merci de Patienter un Peu ... Validation du Planning En Cours.</span> &nbsp;&nbsp;&nbsp;
                                            <Spinner animation="grow" variant="danger"/>
                                        </>
                                    )}

                                    <Button variant="outlined"
                                            color="primary"
                                            onClick={this.handleCloseModalPlanificationMultiple}>
                                        Exit
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={
                                    successValidateAndNotifyPopupMP
                                }
                                onClose={
                                    this
                                        .handleCloseSuccessValidateAndNotifyPopupMP
                                }
                                aria-labelledby="form-dialog-title"
                            >
                                <DialogTitle id="form-dialog-title">
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                La Validation et la Notification des soutenances pour
                                                les {listOfCheckedStudentsForMPSTN.length} étudiants sélectionnés sont
                                                effectuées.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button
                                        onClick={
                                            this
                                                .handleCloseSuccessValidateAndNotifyPopupMP
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
                                open={openPopupLackEntriesForNotif}
                                onClose={this.handleClosePopupLackEntriesForNotif}
                                aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Il y a manque des données.
                                                <br/>
                                                Merci de vérifier.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button onClick={this.handleClosePopupLackEntriesForNotif}
                                            color="primary">
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog
                                fullHight
                                fullWidth
                                maxWidth="sm"
                                open={openPopupFilledEntriesForNotif}
                                onClose={this.handleClosePopupFilledEntriesForNotif}
                                aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                                    <CIcon
                                        name="cil-warning"
                                        style={{color: "blue"}}
                                    />
                                    &nbsp;&nbsp;
                                    <span className="popupInfo">
                                                            Information
                                                          </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>
                                    <CRow>
                                        <CCol xs="12">
                                            <center>
                                                Vos données sont complètes.
                                            </center>
                                        </CCol>
                                    </CRow>
                                </DialogContent>
                                <DialogActions>
                                    <Button onClick={this.handleClosePopupFilledEntriesForNotif}
                                            color="primary">
                                        Ok
                                    </Button>
                                </DialogActions>
                            </Dialog>

                            <Dialog fullHight
                                    fullWidth
                                    maxWidth="lg"
                                    keepMounted
                                    open={successShowPlanificationAdvanced}
                                    onClose={this.handleCloseModalPlanificationAdvanced}
                                    aria-labelledby="form-dialog-title">
                                <DialogTitle id="form-dialog-title">
                                                          <span style={{
                                                              color: "#cc0000",
                                                              fontSize: "14px",
                                                              fontWeight: "bold"
                                                          }}>
                                                            Planification Avancée des Soutenances
                                                          </span>
                                    <span style={{color: "#cc0000", fontSize: "14px", fontWeight: "bold"}}>
                                                            <br/> {checkedStudents.length}
                                                          </span>&nbsp;
                                    <span style={{color: "#cc0000", fontSize: "12px", fontStyle: "italic"}}>
                                                             étudiants sélectionnés
                                                          </span>
                                    <hr/>
                                </DialogTitle>
                                <DialogContent>


                                    <CRow>
                                        <CCol xs="4">
                                            <CRow>
                                                <CCol xs="3">
                                                                  <span className="advancedPlanifPolyCriteria">
                                                                    Date:
                                                                  </span>
                                                </CCol>

                                                <CCol xs="9">
                                                    <DatePicker
                                                        selected={this.state.selectedDateAP}
                                                        onChange={this.onChangeDateSoutenanceAP}
                                                        isClearable
                                                        className="form-control"
                                                        disabled={disableCheckDispoPE}
                                                        dateFormat="dd-MM-yyyy"/>

                                                </CCol>
                                            </CRow>
                                            <br/>
                                            <CRow>
                                                <CCol xs="3">
                                                                  <span className="advancedPlanifPolyCriteria">
                                                                    Horaire:
                                                                  </span>
                                                </CCol>
                                                <CCol xs="4">
                                                    <TextField id="time"
                                                               label="Heure Début"
                                                               type="time"
                                                               value={this.state.selectedStartHourAP}
                                                               className={timeStyle}
                                                               InputLabelProps={{shrink: true}}
                                                               inputProps={{step: 300}}
                                                               disabled={disableCheckDispoPE}
                                                               onChange={(e) => this.handleChangeStartHourAP(e)}/>
                                                </CCol>

                                                <CCol xs="4">
                                                    <TextField
                                                        id="time"
                                                        label="Heure Fin"
                                                        type="time"
                                                        value={this.state.selectedEndHourAP}
                                                        className={timeStyle}
                                                        InputLabelProps={{shrink: true}}
                                                        inputProps={{step: 600}}
                                                        disabled={disableCheckDispoPE}
                                                        onChange={(e) => this.handleChangeEndHourAP(e)}/>
                                                    {(
                                                        refuseEndHourAP &&
                                                        <div>
                                                            <br/>
                                                            <div className="alert alert-danger" role="alert">
                                                                Heure Fin doit être supérieure à Heure Début.
                                                            </div>
                                                        </div>
                                                    )}
                                                </CCol>
                                            </CRow>
                                        </CCol>

                                        <CCol xs="2">
                                            {(
                                                alertNbrSallesToChoose > 0 &&
                                                <>
                                                                      <span className="advancedPlanifPolyCriteria">
                                                                        Salles:
                                                                      </span>
                                                    <br/>

                                                    <span className="noticeForAP">
                                                                          Veuillez choisir
                                                                        </span>
                                                    <span style={{color: "#ff1a75", fontSize: "13px"}}>
                                                                          &nbsp;&nbsp;{alertNbrSallesToChoose}&nbsp;&nbsp;
                                                                        </span>
                                                    <span className="noticeForAP">
                                                                          Salles.
                                                                        </span>

                                                    <br/>
                                                    ==>{selectedSalles.length}**
                                                    <Select closeMenuOnSelect={false}
                                                            isMulti
                                                            value={this.state.allAvailableSalles.value}
                                                            components={animatedComponents}
                                                            options={selectedSalles.length >= alertNbrSallesToChoose ? [] : this.state.allAvailableSalles}
                                                            noOptionsMessage={() => {
                                                                return selectedSalles.length >= alertNbrSallesToChoose ? 'Cant Add New Salle' : '';
                                                            }}
                                                            isDisabled={disableValidateAutoPlanifBTN}
                                                            onChange={this.onChangeSelectSalleSoutenance.bind(this)}/>
                                                </>
                                            )}
                                        </CCol>

                                        <CCol xs="3">
                                            {(
                                                alertNbrSallesToChoose > 0 &&
                                                <>
                                                                      <span className="advancedPlanifPolyCriteria">
                                                                        Experts:
                                                                      </span>
                                                    <br/>

                                                    <span className="noticeForAP">
                                                                          Veuillez choisir
                                                                        </span>
                                                    <span style={{color: "#ff1a75", fontSize: "13px"}}>
                                                                          &nbsp;&nbsp;{listOfCheckedStudentsForAPSTN.length}&nbsp;&nbsp;
                                                                        </span>
                                                    <span className="noticeForAP">
                                                                          Experts.
                                                                        </span>

                                                    <br/>
                                                    ==>{selectedExperts.length}**
                                                    <Select closeMenuOnSelect={false}
                                                            isMulti
                                                            value={this.state.allAvailableExperts.value}
                                                            components={animatedComponents}
                                                            options={selectedExperts.length >= listOfCheckedStudentsForAPSTN.length ? [] : this.state.allAvailableExperts}
                                                            noOptionsMessage={() => {
                                                                return selectedExperts.length >= listOfCheckedStudentsForAPSTN.length ? 'Cant Add New Membre' : '';
                                                            }}
                                                            isDisabled={disableValidateAutoPlanifBTN}
                                                            onChange={this.onChangeSelectExpertSoutenance.bind(this)}/>
                                                </>
                                            )}
                                        </CCol>

                                        <CCol xs="3">
                                            {(
                                                alertNbrSallesToChoose > 0 &&
                                                <>
                                                                      <span className="advancedPlanifPolyCriteria">
                                                                        Présidents Jury:
                                                                      </span>
                                                    <br/>

                                                    <span className="noticeForAP">
                                                                          Veuillez choisir
                                                                        </span>
                                                    <span style={{color: "#ff1a75", fontSize: "13px"}}>
                                                                          &nbsp;&nbsp;{listOfCheckedStudentsForAPSTN.length}&nbsp;&nbsp;
                                                                        </span>
                                                    <span className="noticeForAP">
                                                                          Présidents Jury.
                                                                        </span>

                                                    <br/>
                                                    ==>{selectedJuryPresidents.length}**
                                                    <Select closeMenuOnSelect={false}
                                                            isMulti
                                                            value={this.state.allAvailableJuryPresidents.value}
                                                            components={animatedComponents}
                                                            options={selectedJuryPresidents.length >= listOfCheckedStudentsForAPSTN.length ? [] : this.state.allAvailableJuryPresidents}
                                                            noOptionsMessage={() => {
                                                                return selectedJuryPresidents.length >= listOfCheckedStudentsForAPSTN.length ? 'Cant Add New Jury President' : '';
                                                            }}
                                                            isDisabled={disableValidateAutoPlanifBTN}
                                                            onChange={this.onChangeSelectJuryPresidentSoutenance.bind(this)}/>
                                                </>
                                            )}
                                        </CCol>
                                    </CRow>

                                    <br/><br/>
                                    {(
                                        showListOfCheckedStudentsForAPSTN && listOfCheckedStudentsForAPSTN.length > 0 &&
                                        <CDataTable items={listOfCheckedStudentsForAPSTN}
                                                    fields={checkedStusAdvancedPl}
                                                    columnFilter
                                                    hover
                                                    sorter
                                                    striped
                                                    bordered
                                                    size="sm"
                                                    itemsPerPage={3}
                                                    pagination/>
                                    )}

                                    {(
                                        !existAvailablePEForAdvancedPlanif &&
                                        <CRow>
                                            <CCol xs="12">
                                                <center>
                                                    <hr/>
                                                    <br/><br/>
                                                    <span className="text-danger" style={{fontSize: "15px"}}>
                                                                        Les Encadrants Pédagogiques des Étudiants Sélectionées ne sont pas disponibles pour ce créneau horaire.
                                                                        <br/>
                                                                        Merci d'essayer une autre intervalle.
                                                                      </span>
                                                    <br/><br/><br/><br/><br/>
                                                </center>
                                            </CCol>
                                        </CRow>
                                    )}

                                    {(
                                        showFullPlanningForAP &&
                                        <CDataTable items={listOfCheckedStudentsForMPSTN}
                                                    fields={checkedStus}
                                                    columnFilter
                                                    hover
                                                    sorter
                                                    striped
                                                    bordered
                                                    size="sm"
                                                    itemsPerPage={8}
                                                    pagination
                                                    scopedSlots={{
                                                        membre: (item) => (
                                                            <td>
                                                                <CRow>
                                                                    <CCol xs="2">
                                                                        <Badge badgeContent={item.nbrStuTrainedByExpert}
                                                                               showZero color="secondary"
                                                                               style={{marginTop: "12px"}}>
                                                                            <PersonIcon/>
                                                                        </Badge>
                                                                    </CCol>
                                                                    <CCol xs="10">
                                                                        <Select key={item.membre}
                                                                                defaultValue={{
                                                                                    value: item.membre,
                                                                                    label: item.membre
                                                                                }}
                                                                                closeMenuOnSelect={true}
                                                                                value={this.state.allTeachersMP.value}
                                                                                components={animatedComponents}
                                                                                options={this.state.allTeachersMP}
                                                                                size="sm"
                                                                                onChange={(e) => this.onChangeSelectExpertMP(item.identifiant, e.value)}/>
                                                                    </CCol>
                                                                </CRow>
                                                            </td>
                                                        ),
                                                        juryPresident: (item) => (
                                                            <td>
                                                                <CRow>
                                                                    <CCol xs="2">
                                                                        <Badge
                                                                            badgeContent={item.nbrStuTrainedByJuryPresident}
                                                                            showZero color="error"
                                                                            style={{marginTop: "12px"}}>
                                                                            <PersonIcon/>
                                                                        </Badge>
                                                                    </CCol>
                                                                    <CCol xs="10">
                                                                        <Select key={item.juryPresident}
                                                                                defaultValue={{
                                                                                    value: item.juryPresident,
                                                                                    label: item.juryPresident
                                                                                }}
                                                                                closeMenuOnSelect={true}
                                                                                value={this.state.allTeachersMP.value}
                                                                                components={animatedComponents}
                                                                                options={this.state.allTeachersMP}
                                                                                onChange={(e) => this.onChangeSelectJuryPresidentMP(item.identifiant, e.value)}/>
                                                                    </CCol>
                                                                </CRow>
                                                            </td>
                                                        ),
                                                        dateSoutenance: (item) => (
                                                            <td style={{width: "200px"}}>
                                                                <DatePicker selected={new Date(item.dateSoutenance)}
                                                                            onChange={(e) => this.onChangeDateSoutenanceMP(item.identifiant, e)}
                                                                            isClearable
                                                                            className="form-control"
                                                                            dateFormat="dd-MM-yyyy"
                                                                            popperPlacement="auto"
                                                                            popperProps={{positionFixed: true}}/>
                                                            </td>
                                                        ),
                                                        hourStartSoutenance: (item, index) => (
                                                            <td key={index} style={{width: "200px"}}>
                                                                <TextField id="timeHSS"
                                                                           type="time"
                                                                           value={item.hourStartSoutenance}
                                                                           className={timeStyle}
                                                                           InputLabelProps={{shrink: true}}
                                                                           inputProps={{step: 300}}
                                                                           onChange={(e) => this.onChangeStartHourMP(item.identifiant, e.target.value)}/>
                                                            </td>
                                                        ),
                                                        hourEndSoutenance: (item, index) => (
                                                            <td key={index} style={{width: "200px"}}>
                                                                <TextField id="timeHES"
                                                                           type="time"
                                                                           value={item.hourEndSoutenance}
                                                                           className={timeStyle}
                                                                           InputLabelProps={{shrink: true}}
                                                                           inputProps={{step: 300}}
                                                                           error={item.decideEHS === "Error"}
                                                                           helperText={item.decideEHS}
                                                                           onChange={(e) => this.onChangeEndHourMP(item.identifiant, e.target.value, item.hourStartSoutenance)}/>
                                                            </td>
                                                        ),
                                                        salleSoutenance: (item) => (
                                                            <td style={{width: "200px"}}>
                                                                <Select key={item.salleSoutenance}
                                                                        defaultValue={{
                                                                            value: item.salleSoutenance,
                                                                            label: item.salleSoutenance
                                                                        }}
                                                                        closeMenuOnSelect={true}
                                                                        value={this.state.allClassroomsMP.value}
                                                                        components={animatedComponents}
                                                                        options={this.state.allClassroomsMP}
                                                                        onChange={(e) => this.onChangeSelectClassroomMP(item.identifiant, e.value)}
                                                                        popperPlacement="auto"
                                                                        popperProps={{positionFixed: true}}/>
                                                            </td>
                                                        ),
                                                    }}
                                        />
                                    )}

                                    {(
                                        loadCheckDisponibilityPEforAP &&
                                        <CRow>
                                            <CCol xs="12">
                                                <center>
                                                    <br/><br/>
                                                    <CSpinner color="danger" style={{width: '4rem', height: '4rem'}}/>hi11
                                                    <br/><br/><br/>
                                                </center>
                                            </CCol>
                                        </CRow>
                                    )}

                                    {(
                                        loadAffectClassRoomsforAP &&
                                        <CRow>
                                            <CCol xs="12">
                                                <center>
                                                    <br/><br/>
                                                    <CSpinner color="success" style={{width: '4rem', height: '4rem'}}/>hi12
                                                    <br/><br/><br/>
                                                </center>
                                            </CCol>
                                        </CRow>
                                    )}

                                    {(
                                        loadValidateAndNotifyforAMP &&
                                        <CRow>
                                            <CCol xs="12">
                                                <center>
                                                    <br/><br/>
                                                    <CSpinner color="warning" style={{width: '4rem', height: '4rem'}}/>hi13
                                                    <br/><br/><br/>
                                                </center>
                                            </CCol>
                                        </CRow>
                                    )}

                                </DialogContent>
                                <DialogActions>
                                    <Button variant="outlined"
                                            color="purple"
                                            backgroundColor="purple"
                                            disabled={disableCheckDispoPEBTN}
                                            onClick={this.checkDisponibilityEncadrantForAP}>
                                        Check Disponibility Encadrant
                                    </Button>
                                    <Button variant="outlined"
                                            color="secondary"
                                            disabled={selectedSalles.length < alertNbrSallesToChoose || selectedExperts.length < listOfCheckedStudentsForAPSTN.length || selectedJuryPresidents.length < listOfCheckedStudentsForAPSTN.length || disableValidateAutoPlanifBTN || listOfCheckedStudentsForAPSTN.length === 0}
                                            onClick={this.handleAutoPlanifSallesExpertsJuryPresidentsForAP}>
                                        Valider Auto-Planification
                                    </Button>
                                    <Button variant="outlined"
                                            color="primary"
                                            onClick={this.validateAndNotifyForMP}
                                            disabled={!giveOkConfirmAndNotifyMP}>
                                        Confirmer & Notifier lol
                                    </Button>
                                    <Button variant="outlined"
                                            color="primary"
                                            onClick={this.handleCloseModalPlanificationAdvanced}>
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
