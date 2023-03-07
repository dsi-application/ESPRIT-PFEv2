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

const animatedComponents = makeAnimated();
const API_URL_PC = process.env.REACT_APP_API_URL_PC;
// init2
const API_URL = process.env.REACT_APP_API_URL_MESP;
const API_URL_CONF = process.env.REACT_APP_API_URL_CONF;


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
    }
];

const currentTeacher = AuthService.getCurrentTeacher();


export default class StudentsForMembreSoutenance extends Component {
    constructor(props) {
        super(props);

        // init3
        this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
        this.loadStudentsAffectedToExpertByDefaultYear = this.loadStudentsAffectedToExpertByDefaultYear.bind(this);
        this.loadStudentsAffectedToExpert = this.loadStudentsAffectedToExpert.bind(this);
        this.handleClosePopupVerifyNoMarkIngTr = this.handleClosePopupVerifyNoMarkIngTr.bind(this);
        this.handleChangeNotePE = this.handleChangeNotePE.bind(this);
        this.handleChangeDateRDV = this.handleChangeDateRDV.bind(this);
        this.handleGiveMarkToStudent = this.handleGiveMarkToStudent.bind(this);
        this.handleModifyMarkOfStudent = this.handleModifyMarkOfStudent.bind(this);
        this.handleValidateMark = this.handleValidateMark.bind(this);
        this.handleClosePopupVerifyNoModifMarkIngTr = this.handleClosePopupVerifyNoModifMarkIngTr.bind(this);
        this.handleClosePopupNotifyOkGiveMarkToStudent = this.handleClosePopupNotifyOkGiveMarkToStudent.bind(this);
        this.handleClosePopupVerifyGiveMarkToStudent = this.handleClosePopupVerifyGiveMarkToStudent.bind(this);
        this.handleClosePopupVerifyModifyMarkOfStudent = this.handleClosePopupVerifyModifyMarkOfStudent.bind(this);
        this.handleClosePopupGiveMarkToStudent = this.handleClosePopupGiveMarkToStudent.bind(this);
        this.handleClosePopupNotifyOkModifyMarkOfStudent = this.handleClosePopupNotifyOkModifyMarkOfStudent.bind(this);
        this.handleClosePopupModifyMarkToStudent = this.handleClosePopupModifyMarkToStudent.bind(this);
        this.handleTreatDepotPFE = this.handleTreatDepotPFE.bind(this);

        this.state = {
            listOfStudentsTrainedByPE: [],
            listOfAuthorizedStudentsToSTNByFilter: [],
            openPopupShowStudentDetails: false,
            openPopupTreatDepotPFE: false,
            openPopupValidateDepotPFE: false,
            selectedStudentId: "",
            selectedStudentFullName: "",
            selectedStudentMail: "",
            selectedStudentTel: "",
            showSpinnerValidateDepotPFE: false,
            showButtons: "INIT",
            loadList: true,
            notePE: null,
            noteNotCompleteSend: "",
            openPopupVerifyMarkIngTr: false,
            openPopupGiveMarkToStudent: false,
            openPopupNotifyOkGiveMarkToStudent: false,
            openPopupNotifyOkModifyMarkOfStudent: false,
            showSpinnerGiveMarkToStudent: false,
            openPopupVerifyModifMarkIngTr: false,
            firstNotePE: null,
            showSpinnerModifyMarkToStudent: false,
            allSessionsLabel: [],
            selectedYear: 'Septembre 2022',
            loadStudentsCJByYear: true
        };
        // init4

        // console.log('------------------------> TREATMENT 1: ' + new Date() + " - " + this.state.loadList);
        let requestSessions = new XMLHttpRequest();
        requestSessions.open(
            "GET",
            API_URL_CONF + "sessionsLabel",
            false
        ); // return axios.get(API_URL_MESP + 'user/' + code);
        requestSessions.send(null);
        let resultSession = JSON.parse(requestSessions.responseText);
        // console.log('---------28.10----------> >sars 1110: ' , resultSession);

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
    		this.loadStudentsAffectedToExpertByDefaultYear();
    	}
    	else
        {
          localStorage.clear();
          sessionStorage.clear();
          window.location.href = "/";
        }
        

    }

    loadStudentsAffectedToExpertByDefaultYear() {
        AcademicEncadrantService.studentsByJuryMemberForSTN(currentTeacher.id, this.state.selectedYear).then(
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
                // console.log('----------2303-----2---------> TREATMENT 3: ', this.state.listOfStudentsTrainedByPE);

            },
            (error) => {
            }
        );
    }

    loadStudentsAffectedToExpert = (selectedYearObject) => {

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

        AcademicEncadrantService.studentsByJuryMemberForSTN(currentTeacher.id, selectedYearObject.label).then(
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

    handleValidateMark(e)
    {
        this.setState({
            openPopupVerifyMarkIngTr: true
        });
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

    handleClosePopupGiveMarkToStudent() {
        this.setState({
            openPopupGiveMarkToStudent: false,
        });
    }

    handleClosePopupVerifyNoMarkIngTr() {
        this.setState({
            openPopupVerifyMarkIngTr: false,
            notePE: null
        });
    }

    handleClosePopupShowStudentDetails() {
        this.setState({openPopupShowStudentDetails: false});
    }

    handleGiveMarkToStudent(idEt, studentFullName, markStudentForST)
    {
        // console.log('-------------------------> 2203 ---> 1: ' + idEt);
        // console.log('-------------------------> 2203 ---> 2: ' + studentFullName);
        // console.log('-------------------------> 2203 ---> 3: ' + markStudentForST);

        this.setState({
            openPopupGiveMarkToStudent: true,
            selectedStudentFullName: studentFullName,
            notePE: markStudentForST,
            selectedStudentId: idEt
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

    handleClosePopupVerifyNoModifMarkIngTr() {
        this.setState({
            openPopupVerifyModifMarkIngTr: false,
            notePE: null
        });
    }

    handleClosePopupNotifyOkGiveMarkToStudent() {
        this.setState({openPopupNotifyOkGiveMarkToStudent: false});
    }

    handleClosePopupNotifyOkModifyMarkOfStudent() {
        this.setState({openPopupNotifyOkModifyMarkOfStudent: false});
    }

    handleClosePopupVerifyGiveMarkToStudent() {
        this.setState({
            showSpinnerGiveMarkToStudent: true,
            openPopupGiveMarkToStudent: false
        });

        AcademicEncadrantService.giveMarkforStudentByExpertAndYear(encodeURIComponent(currentTeacher.id), this.state.selectedStudentId, this.state.notePE, this.state.selectedYear).then(
            (response) => {
                let result = response.data;
                // console.log('-------1--------- RES 2403: ', response.data)

                if (result === "NO-AExp") {
                    this.setState({noteNotCompleteSend: "Votre E-Mail est inexistant ou avec format incorrecte."});
                }

                if (result === "NO-AEnc") {
                    this.setState({noteNotCompleteSend: "E-Mail Encadrant Académique est inexistant ou avec format incorrecte."});
                }

                if (result === "NO-AExpAEnc") {
                    this.setState({noteNotCompleteSend: "Votre E-Mail ainsi que E-Mail Encadrant Académique sont inexistants ou avec format incorrectes."});
                }

                let idEt = this.state.selectedStudentId;
                let studentMarkRest = this.state.notePE;
                // console.log('-------------2403-----------------> SARS A: ' + this.state.noteNotCompleteSend)

                if(result === "NO-AExp" || result === "NO-AExpAEnc")
                {
                    this.setState({
                        showSpinnerGiveMarkToStudent: false,
                        openPopupVerifyMarkIngTr: false,
                        openPopupNotifyOkGiveMarkToStudent: true
                    });
                }

                if(result === "YES" || result === "NO-AEnc")
                {
                    this.setState({
                        listOfStudentsTrainedByPE: this.state.listOfStudentsTrainedByPE.map(el => (el.studentId === idEt ? {
                            ...el,
                            studentMarkRest
                        } : el)),
                        showSpinnerGiveMarkToStudent: false,
                        openPopupVerifyMarkIngTr: false,
                        openPopupNotifyOkGiveMarkToStudent: true
                    });
                }

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

        AcademicEncadrantService.modifyMarkforStudentByExpertAndYear(encodeURIComponent(currentTeacher.id), this.state.selectedStudentId, this.state.notePE, this.state.selectedYear).then(
            (response) => {

                let result = response.data;
                // console.log('-------1--------- RES 0404: ', response.data)

                if (result === "NO-AExp") {
                    this.setState({noteNotCompleteSend: "Votre E-Mail est inexistant ou avec format incorrecte."});
                }

                if (result === "NO-AEnc") {
                    this.setState({noteNotCompleteSend: "E-Mail Encadrant Académique est inexistant ou avec format incorrecte."});
                }

                if (result === "NO-AExpAEnc") {
                    this.setState({noteNotCompleteSend: "Votre E-Mail ainsi que E-Mail Encadrant Académique sont inexistants ou avec format incorrectes."});
                }

                let idEt = this.state.selectedStudentId;
                let studentMarkRest = this.state.notePE;
                // console.log('-------------3103-----------------> 0404 A: ' + this.state.noteNotCompleteSend)
                // console.log('-------------3103-----------------> 0404 Z: ' + result)

                if(result === "NO-AExp" || result === "NO-AExpAEnc")
                {
                    //this.setState({noteNotCompleteSend: "Votre E-Mail est inexistant ou avec format incorrecte."});
                    this.setState({
                        openPopupVerifyModifMarkIngTr: false,
                        showSpinnerModifyMarkToStudent: false,
                        openPopupNotifyOkModifyMarkOfStudent: true
                    });
                    // openPopupNotifyOkModifyMarkOfStudent

                    /*
                    showSpinnerGiveMarkToStudent: false,
                    openPopupVerifyMarkIngTr: false,
                    openPopupNotifyOkGiveMarkToStudent: true
                    */
                }

                if(result === "YES" || result === "NO-AEnc")
                {
                    this.setState({
                        listOfStudentsTrainedByPE: this.state.listOfStudentsTrainedByPE.map(el => (el.studentId === idEt ? {
                            ...el,
                            studentMarkRest
                        } : el)),
                        showSpinnerModifyMarkToStudent: false,
                        openPopupVerifyModifMarkIngTr: false,
                        openPopupNotifyOkModifyMarkOfStudent: true
                    });
                }

            },
            (error) => {
            }
        );
    }

    handleValidateFicheDepotPFEModify(e)
    {
        this.setState({
            openPopupVerifyModifMarkIngTr: true
        });
    }

    handleClosePopupModifyMarkToStudent() {
        this.setState({
            openPopupModifyMarkOfStudent: false,
        });
    }

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


    render() {
        const {
            showSpinnerModifyMarkToStudent,
            firstNotePE,
            openPopupVerifyModifMarkIngTr,
            listOfStudentsTrainedByPE,
            listOfAuthorizedStudentsToSTNByFilter,
            openPopupShowStudentDetails,
            openPopupModifyMarkOfStudent,
            openPopupNotifyOkGiveMarkToStudent,
            openPopupNotifyOkModifyMarkOfStudent,
            showSpinnerGiveMarkToStudent,
            openPopupTreatDepotPFE,
            openPopupValidateDepotPFE,
            selectedStudentFullName,
            selectedStudentMail,
            selectedStudentTel,
            showSpinnerValidateDepotPFE,
            showButtons,
            notePE,
            loadList,
            noteNotCompleteSend,
            openPopupVerifyMarkIngTr,
            openPopupGiveMarkToStudent,
            selectedStudentId,
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
                        <p className="greyMarkForSelectComp">Merci de choisir une Session pour consulter la résultante</p>
                        <Select  placeholder="Please Select an Academic Year"
                                 defaultValue={{value: 'Septembre 2022', label: 'Septembre 2022', color: "#00B8D9"}}
                                 value={allSessionsLabel.value}
                                 components={animatedComponents}
                                 options={allSessionsLabel}
                                 onChange={this.loadStudentsAffectedToExpert}/>
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
                                                            Liste des Étudiants pour la Session &nbsp;&nbsp;<span className="clignoteRed">{selectedYear}</span>
                                                        </span>
                                                            </CCol>
                                                        </CRow>
                                                    </CContainer>
                                                </CCardHeader>
                                                <CCardBody>
                                                    {
                                                        listOfStudentsTrainedByPE.length === 0 ?
                                                            <>
                                                                <CRow>
                                                                    <CCol md="11">
                                                                        <center>
                                                                            <br/><br/>
                                                                            <CIcon name="cil-warning" style={{color: "#666666", width:"30px", height: "30px"}}/>
                                                                            <br/><br/>
                                                                            <span className="greyMarkCourrier">Désolé, Vous n'êtes pas un(e) Membre Jury  d'aucune soutenance pour cette Session.</span>
                                                                            <br/><br/><br/><br/>
                                                                        </center>
                                                                    </CCol>
                                                                    <CCol md="1"/>
                                                                </CRow>
                                                            </>
                                                            :
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
                                                            />
                                                    }
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
                                                Saisie Note Restitution
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
                                                Modification Note Restitution
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
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                <br/>
                                                {
                                                    (noteNotCompleteSend.includes("Votre E-Mail")) &&
                                                    <>
                                                        <span className="errorS1">Erreur Envoi: </span>&nbsp;
                                                        <span className="errorS2">{noteNotCompleteSend}</span>
                                                        <br/><br/>
                                                    </>
                                                }

                                                {
                                                    noteNotCompleteSend !== "" && noteNotCompleteSend.includes("Encadrant Académique") &&
                                                    <>
                                                        Vous avez saisi la Note Restitution pour l'Étudiant &nbsp;
                                                        <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                                        <br/><br/>
                                                        <span className="errorS1">Erreur Envoi: </span>&nbsp;
                                                        <span className="errorS2">{noteNotCompleteSend}</span>
                                                        <br/><br/>
                                                    </>
                                                }

                                                {
                                                    noteNotCompleteSend === "" &&
                                                    <>
                                                        Vous avez saisi la Note Restitution pour l'Étudiant &nbsp;
                                                        <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                                        <br/><br/>
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
                                            open={openPopupNotifyOkModifyMarkOfStudent}
                                            onClose={this.handleClosePopupNotifyOkModifyMarkOfStudent}
                                            aria-labelledby="form-dialog-title">
                                        <DialogTitle id="form-dialog-title">
                                            <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirmation</span>
                                            <hr/>
                                        </DialogTitle>
                                        <DialogContent>
                                            <center>
                                                <br/>
                                                {
                                                    (noteNotCompleteSend.includes("Votre E-Mail")) &&
                                                    <>
                                                        <span className="errorS1">Erreur Envoi: </span>&nbsp;
                                                        <span className="errorS2">{noteNotCompleteSend}</span>
                                                        <br/><br/>
                                                    </>
                                                }

                                                {
                                                    noteNotCompleteSend !== "" && noteNotCompleteSend.includes("Encadrant Académique") &&
                                                    <>
                                                        Vous avez modifié la Note Restitution pour l'Étudiant &nbsp;
                                                        <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                                        <br/><br/>
                                                        <span className="errorS1">Erreur Envoi: </span>&nbsp;
                                                        <span className="errorS2">{noteNotCompleteSend}</span>
                                                        <br/><br/>
                                                    </>
                                                }

                                                {
                                                    noteNotCompleteSend === "" &&
                                                    <>
                                                        Vous avez saisi la Note Restitution pour l'Étudiant &nbsp;
                                                        <span className="text-info" style={{fontSize: "12px"}}>
                                                    {selectedStudentFullName}
                                                </span>
                                                        <br/><br/>
                                                    </>
                                                }
                                            </center>
                                            <br/>
                                        </DialogContent>
                                        <DialogActions>
                                            <Button onClick={this.handleClosePopupNotifyOkModifyMarkOfStudent} color="primary">
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
                                                !showSpinnerGiveMarkToStudent &&
                                                <Button onClick={this.handleClosePopupVerifyGiveMarkToStudent} color="primary">
                                                    Oui
                                                </Button>
                                            }

                                            {(
                                                showSpinnerGiveMarkToStudent &&
                                                <Spinner animation="grow" variant="primary"/>
                                            )}
                                            <Button onClick={this.handleClosePopupVerifyNoMarkIngTr}
                                                    disabled={showSpinnerGiveMarkToStudent}
                                                    color="primary">
                                                Non
                                            </Button>
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
                                                Êtes-vous sûr de vouloir modifier <br/>
                                                la note de l'Étudiant(e) &nbsp;
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

                                            {(
                                                showSpinnerModifyMarkToStudent &&
                                                <Spinner animation="grow" variant="primary"/>
                                            )}
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
