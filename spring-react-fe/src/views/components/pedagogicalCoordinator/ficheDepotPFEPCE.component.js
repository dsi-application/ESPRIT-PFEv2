// init 1
import React, { Component } from "react";

import AuthService from "../../services/auth.service";
import PCService from "../../services/pedagogicalCoordinator.service";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import { CWidgetBrand, CFormGroup, CLabel, CInputRadio, CSwitch, CRow, CCol, CCard, CCardHeader, CCardBody, CDataTable, CBadge, CButton, CNav, CNavItem, CNavLink, CTabContent, CTabPane, CTabs, CTooltip,CSpinner, CContainer, CListGroup, CListGroupItem } from "@coreui/react";
import Select from "react-select";
import CIcon from "@coreui/icons-react";
import { freeSet } from '@coreui/icons';

import "../../css/style.css";
import pedagogicalRDVData from "../../utils/pedagogicalRDVData.js";
import TextField from '@material-ui/core/TextField';

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { Wave, Random } from "react-animated-text";
import ReactTextTransition from "react-text-transition";
import Spinner from "react-bootstrap/Spinner";
import axios from "axios";
import moment from "moment";
import makeAnimated from "react-select/animated";

const animatedComponents = makeAnimated();
// init2
const API_URL_PC = process.env.REACT_APP_API_URL_PC;
const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

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

const getBadgeEtatTreatDepotAE = (etatTreatDepotAE) => {
      switch (etatTreatDepotAE) {
        case "TRAITE":
          return "success";
        case "EN ATTENTE":
          return "danger";
        default:
          return "info";
      }
    };

const getBadgeEtatTreatDepotPC = (etatTreatDepotPC) => {
      switch (etatTreatDepotPC) {
        case "TRAITE":
          return "success";
        case "EN ATTENTE":
          return "danger";
        default:
          return "info";
      }
    };

const getRDVBadge = status => {
  switch (status) {
    case 'Active': return 'success'
    case 'Inactive': return 'secondary'
    case 'Pending': return 'warning'
    case 'Banned': return 'danger'
    default: return 'primary'
  }
}

const rdvfields = [
  {
    key: 'label',
    label: 'RDV',
    _style: { width: '20%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'date',
    label: 'Date',
    _style: { width: '20%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'presence',
    label: 'Présence du Membre',
    _style: { width: '30%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'status',
    label: 'État',
    _style: { width: '30%' },
    sorter: true,
    filter: true,
    show: true
  },
]

const fields = [
  {
    key: 'identifiant',
    label: 'Identifiant',
    _style: { width: '6%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'fullName',
    label: 'Prénom & Nom',
    _style: { width: '28%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'option',
    label: 'Option',
    _style: { width: '11%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'classe',
    label: 'Classe',
    _style: { width: '11%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'dateDepot',
    label: 'Date Dépôt',
    _style: { width: '10%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'session',
    label: 'Session',
    _style: { width: '14%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'etatFiche',
    label: 'État Dossier',
    _style: { width: '5%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'etatTreatDepotAE',
    label: 'État Fiche Dépôt Encadrant',
    _style: { width: '8%' },
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'action',
    label: 'Action',
    _style: { width: '10%' },
    sorter: false,
    filter: false
  }
];


const currentPedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();



export default class FicheDepotPFE extends Component {
  constructor(props) {
    super(props);

    // init3

    this.loadStudentsTrainedByPCEAndYear = this.loadStudentsTrainedByPCEAndYear.bind(this);
    this.loadStudentsAffectedToAEByDefaultYear = this.loadStudentsAffectedToAEByDefaultYear.bind(this);
    this.handleConsultStudentDetails = this.handleConsultStudentDetails.bind(this);
    this.handleClosePopupShowStudentDetails = this.handleClosePopupShowStudentDetails.bind(this);
    this.handleClosePopupSaveDepotPFE = this.handleClosePopupSaveDepotPFE.bind(this);
    this.handleClosePopupValidateDepotPFE = this.handleClosePopupValidateDepotPFE.bind(this);
    this.handleClosePopupTreatDepotPFE = this.handleClosePopupTreatDepotPFE.bind(this);
    this.handleChangeNotePE = this.handleChangeNotePE.bind(this);
    this.handleFillFicheDepotPFE = this.handleFillFicheDepotPFE.bind(this);
    this.handleChangeDateRDV = this.handleChangeDateRDV.bind(this);
    this.onChangeAuthorizedStudentsToSTNByFilter = this.onChangeAuthorizedStudentsToSTNByFilter.bind(this);
    this.downloadGrilleAE = this.downloadGrilleAE.bind(this);

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
      //selectedStudentDateDépôt: "",
      selectedStudentJuryPresident: "",
      selectedStudentExpert: "",
      selectedStudentDateDebutStage: "",
      selectedStudentDateFinStage: "",
      notePE: "",
      onPlanningStg: false,
      onBilanPerDebStg: false,
      onBilanPerMilStg: false,
      onBilanPerFinStg: false,
      onFicheEvalMiPar: false,
      onFicheEvalFin: false,
      onJournalBord: false,
      onRapportStg: false,
      dateRDV1: new Date(),
      dateRDV2: new Date(),
      dateRDV3: new Date(),
      checkedRDV1: 0,
      checkedRDV2: 2,
      checkedRDV3: 4,
      checkedsRDV1: 6,
      checkedsRDV2: 8,
      checkedsRDV3: 10,
      presenceKindRDV1:"Oui",
      presenceKindRDV2:"Oui",
      presenceKindRDV3:"Oui",
      statusKindRDV1:"Oui",
      statusKindRDV2:"Oui",
      statusKindRDV3:"Oui",
      statusKinds: [
        {
          value: "Oui",
          label: "Oui",
        },
        {
          value: "Non",
          label: "Non",
        },
      ],
      satisfactionRDV1: "",
      satisfactionRDV2: "",
      satisfactionRDV3: "",
      showSpinnerSaveDepotPFE: false,
      showSpinnerValidateDepotPFE: false,
      //completeTreatDepotFichePFE: "YES",
      etatTreat: "NO",
      emptyTreat: "INIT",
      showButtons: "INIT",
      treatByPC: "INIT",
      loadList: true,
      lol: "NO",
      selectedYear: "2022",
      allSessionsLabel: []
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
    this.loadStudentsAffectedToAEByDefaultYear();

    this.state.listOfAuthorizedStudentsToSTNByFilter= [];

      // console.log("----filter---------> THIS-0: ", this.state.listOfAuthorizedStudentsToSTNByFilter);

  }
  // init5


    onChangeAuthorizedStudentsToSTNByFilter(filtredItems)
    {
        this.setState({ listOfAuthorizedStudentsToSTNByFilter: [] })

        let filteredIdentifiants = [];
        if(filtredItems.length !== this.state.listOfStudentsTrainedByPE.length)
        {
            for(let item of filtredItems)
            {
                filteredIdentifiants.push(item.identifiant);
            }
            // console.log("Check Disponibility --excel------TextScroller.js-------------> THIS-2: ", filteredIdentifiants);

            this.setState({
                listOfAuthorizedStudentsToSTNByFilter: filteredIdentifiants,
                planifyKind: "PWFILTER",
            })
        }
    }

    downloadGrilleAE(studentId) {
        axios.get(`${process.env.REACT_APP_API_URL_AE}` + "downloadGrilleAE/" + studentId, { responseType: "blob" })
            .then((response) => {
                const file = new Blob([response.data], {type: 'application/pdf'});
                const fileURL = URL.createObjectURL(file);

                const contentDispo = response.headers['content-disposition'];
                const fileName = contentDispo.substring(21);

                let a = document.createElement('a');
                a.href = fileURL;
                a.download = fileName;
                a.click();
                window.open(fileURL);
            });
    }

      loadStudentsTrainedByPCEAndYear = (selectedYearObject) => {

        this.setState({
          loadList: true,
          selectedYear: selectedYearObject.label
        });

        console.log('------------------PIKA13------> TREATMENT 2: ' + selectedYearObject.label + " - " + this.state.selectedYear);

        PCService.studentsTrainedByPCEAndYear(currentPedagogicalCoordinator.id, selectedYearObject.label).then(
          (response) => {
            // console.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);
            // console.log('--------------opt----------> TREATMENT 2: ' +currentTeacher.codeOptions);
            this.setState({
              listOfStudentsTrainedByPE: []
            });
            let stuList = this.state.listOfStudentsTrainedByPE.slice();
            for (let stu of response.data)
            {
              // console.log('------------------dd------> TREATMENT 2.1 : ', stu);
              const studentObj = stu;
              stuList.push(stu);
            }

            this.setState({
              loadList: false,
              listOfStudentsTrainedByPE: stuList
            });
            // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

          },
          (error) => {
          }
      );
    }


    loadStudentsAffectedToAEByDefaultYear()
    {
        PCService.studentsTrainedByPCEAndYear(currentPedagogicalCoordinator.id, this.state.selectedYear).then(
          (response) => {
            // console.log('------------------------> TREATMENT 2: ' + new Date() + " - " + this.state.loadList);
              // console.log('--------------opt----------> TREATMENT 2: ' +currentTeacher.codeOptions);

            let stuList = this.state.listOfStudentsTrainedByPE.slice();
            for (let stu of response.data)
            {
                // console.log('------------------dd------> TREATMENT 2.1 : ', stu);
                const studentObj = stu;
                stuList.push(stu);
            }

            this.setState({
              loadList: false,
              listOfStudentsTrainedByPE: stuList
            });
            // console.log('------------------------> TREATMENT 3: ' + new Date() + " - " + this.state.loadList);

          },
          (error) => {
          }
        );
    }

    handleChangeDateRDV(index, date)
    {
      // console.log('------------------------> DT RDV - a: ' + currentTeacher.codeOption);
      // console.log('------------------------> DT RDV - z: ',  date);


      if(index === 0)
      {
          this.setState({ dateRDV1: date });
          // console.log('------------------------> DT RDV - 1: ' + this.state.dateRDV1);
      }

      if(index === 1)
      {
          this.setState({ dateRDV2: date });
          // console.log('------------------------> DT RDV - 2: ' + this.state.dateRDV2);
      }

      if(index === 2)
      {
          this.setState({ dateRDV3: date });
          // console.log('------------------------> DT RDV - 3: ' + this.state.dateRDV3);
      }

  }

  handleFillFicheDepotPFE(e)
  {

      this.setState({
          showSpinnerSaveDepotPFE: true
      });

      // console.log('------------------------> RES-1: ' + this.state.notePE);

      // console.log('------------------------> RES-2: ' + this.state.onPlanningStg);
      // console.log('------------------------> RES-3: ' + this.state.onBilanPerDebStg);
      // console.log('------------------------> RES-4: ' + this.state.onBilanPerMilStg);
      // console.log('------------------------> RES-5: ' + this.state.onFicheEvalMiPar);
      // console.log('------------------------> RES-6: ' + this.state.onFicheEvalFin);
      // console.log('------------------------> RES-7: ' + this.state.onBilanPerFinStg);
      // console.log('------------------------> RES-8: ' + this.state.onJournalBord);
      // console.log('------------------------> RES-9: ' + this.state.onRapportStg);

      // console.log('------------------------> RES-A.1: ' + this.state.dateRDV1);
      // console.log('------------------------> RES-A.2: ' + this.state.dateRDV2);
      // console.log('------------------------> RES-A.3: ' + this.state.dateRDV3);

      // console.log('------------------------> RES-Z.1: ' + this.state.presenceKindRDV1);
      // console.log('------------------------> RES-Z.2: ' + this.state.presenceKindRDV2);
      // console.log('------------------------> RES-Z.3: ' + this.state.presenceKindRDV3);

      // console.log('------------------------> RES-E.1: ' + this.state.satisfactionRDV1);
      // console.log('------------------------> RES-E.2: ' + this.state.satisfactionRDV2);
      // console.log('------------------------> RES-E.3: ' + this.state.satisfactionRDV3);

      AuthService.fillFicheDepotPFE(this.state.selectedStudentId,
        this.state.notePE,
        this.state.onPlanningStg,
        this.state.onBilanPerDebStg,
        this.state.onBilanPerMilStg,
        this.state.onBilanPerFinStg,
        this.state.onFicheEvalMiPar,
        this.state.onFicheEvalFin,
        this.state.onJournalBord,
        this.state.onRapportStg,
        this.gotFormattedDate(this.state.dateRDV1),
        this.gotFormattedDate(this.state.dateRDV2),
        this.gotFormattedDate(this.state.dateRDV3),
        this.state.presenceKindRDV1,
        this.state.presenceKindRDV2,
        this.state.presenceKindRDV3,
        this.state.statusKindRDV1,
        this.state.statusKindRDV2,
        this.state.statusKindRDV3, "01").then(
      (response) => {

        this.setState({
          showSpinnerSaveDepotPFE: false,
          openPopupSaveDepotPFE: true
        });

      },
      (error) => {
      }
    );

    /*if(
          this.state.notePE !== "" && this.state.onPlanningStg && this.state.onBilanPerDebStg  &&
          this.state.onBilanPerMilStg && this.state.onBilanPerFinStg && this.state.onFicheEvalMiPar &&
          this.state.onFicheEvalFin && this.state.onJournalBord && this.state.onRapportStg &&
          //this.state.presenceKindRDV1 !== "" && this.state.presenceKindRDV2 !== "" && this.state.presenceKindRDV3 !== "" &&
          this.state.satisfactionRDV1 !== "" && this.state.satisfactionRDV2 !== "" && this.state.satisfactionRDV3 !== "" &&
          this.state.satisfactionRDV1 !== null && this.state.satisfactionRDV2 !== null && this.state.satisfactionRDV3 !== null
      )
    {
        this.state.completeTreatDepotFichePFE = "YES";
    }
    else
    {
        this.state.completeTreatDepotFichePFE = "NO";
    }

    console.log('------------------------> RES-SARS: ' + this.state.completeTreatDepotFichePFE);*/

  }

  gotFormattedDate(bridgeSDt)
  {
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

    let formattedDate = day + "-" + month+ "-" + bridgeSDt.getFullYear();
    return formattedDate;
  }

  handleChangeNotePE(e)
  {
     // console.log('-----------------------> NOTE: ' + e);
     this.setState({ notePE: e });
  }

  handleConsultStudentDetails(idEt, fullName)
  {
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

  handleClosePopupShowStudentDetails()
  {
      this.setState({ openPopupShowStudentDetails: false });
  }

  handleClosePopupSaveDepotPFE()
  {
      this.setState({ openPopupSaveDepotPFE: false });
  }

  handleClosePopupValidateDepotPFE()
  {
      this.setState({ openPopupValidateDepotPFE: false });
  }

  handleClosePopupTreatDepotPFE() {
    this.setState({
      openPopupTreatDepotPFE: false,
    });
  }


  render()
  {
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
        //selectedStudentDateDépôt,
        selectedStudentJuryPresident,
        selectedStudentExpert,
        selectedStudentDateDebutStage,
        selectedStudentDateFinStage,
        statusKinds,
        checkedRDV1,
        checkedRDV2,
        checkedRDV3,
        checkedsRDV1,
        checkedsRDV2,
        checkedsRDV3,
        presenceKindRDV1,
        presenceKindRDV2,
        presenceKindRDV3,
        showSpinnerSaveDepotPFE,
        showSpinnerValidateDepotPFE,
        //completeTreatDepotFichePFE,
        etatTreat,
        emptyTreat,
        showButtons,
        notePE,
        onPlanningStg,
        onBilanPerDebStg,
        onBilanPerMilStg,
        onBilanPerFinStg,
        onFicheEvalMiPar,
        onFicheEvalFin,
        onJournalBord,
        onRapportStg,
        satisfactionRDV1,
        satisfactionRDV2,
        satisfactionRDV3,
        dateRDV1,
        dateRDV2,
        dateRDV3,
        treatByPC,
        selectedYear,
        loadList,
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
                           onChange={this.loadStudentsTrainedByPCEAndYear}/>
                </CCol>
                <CCol md="4"/>
              </CRow>
              <br/>
              {
                selectedYear !== 'EMPTY' &&
                <>
                  {
                        loadList === true ?
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
                                                      <span style={{color: "#cc0000", fontSize: "14px", fontWeight: "bold"}}>
                                                          Liste des Étudiants pour l'Année Universitaire &nbsp;&nbsp;<span className="clignoteRed">{selectedYear} - {Number(selectedYear)+1}</span>
                                                      </span>
                                                    <br/>
                                                    <span className="greyMarkCourrier">
                                                      {listOfStudentsTrainedByPE.length}
                                                    </span>&nbsp;
                                                    <span className="greyMarkCourrierSmalLabel">étudiants</span>
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
                                                     onFilteredItemsChange = {(event) => {this.onChangeAuthorizedStudentsToSTNByFilter(event)}}
                                                     scopedSlots={{
                                                       dateDepot: (item)=>(
                                                           <td>
                                                             {
                                                               item.dateDepot?
                                                                   <>{moment(item.dateDepot).format("DD-MM-YYYY hh:mm")}</>
                                                                   :
                                                                   <>--</>
                                                             }
                                                           </td>
                                                       ),
                                                       etatFiche: (item) => (
                                                           <td>
                                                             <CBadge color={getBadgeEtatFiche(item.etatFiche)}>{item.etatFiche}</CBadge>
                                                           </td>
                                                       ),
                                                       etatTreatDepotAE: (item) => (
                                                           <td>
                                                             <CBadge color={getBadgeEtatTreatDepotAE(item.etatTreatDepotAE)}>{item.etatTreatDepotAE}</CBadge>
                                                           </td>
                                                       ),
                                                       action: (item) => (
                                                           <td>
                                                             <center>

                                                               <CButton  shape="pill"
                                                                         color="dark"
                                                                         size="sm"
                                                                         aria-expanded="true"
                                                                         onClick={() => this.handleConsultStudentDetails(item.identifiant, item.fullName)}>
                                                                 <CTooltip content="Consulter Coordonnées Étudiant" placement="top">
                                                                   <CIcon content={freeSet.cilPhone} />
                                                                 </CTooltip>
                                                               </CButton>
                                                               &nbsp;
                                                               {
                                                                 item.etatTreatDepotAE === "TRAITE" &&
                                                                 <CButton  shape="pill"
                                                                           color="primary"
                                                                           size="sm"
                                                                           aria-expanded="true"
                                                                           onClick={() => this.downloadGrilleAE(item.identifiant)}>
                                                                   <CTooltip content="Download Grille Encadrant Académique" placement="top">
                                                                     <CIcon content={freeSet.cilCloudDownload} />
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
                                                    <ReactTextTransition text={selectedStudentFullName} springConfig={{tension: 10, friction: 10}}/>
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
                                  maxWidth="lg"
                                  keepMounted
                                  open={openPopupTreatDepotPFE}
                                  onClose={this.handleClosePopupTreatDepotPFE}
                                  aria-labelledby="form-dialog-title">
                            <DialogTitle id="form-dialog-title">

                              <CRow>
                                <CCol md="2">
                                                    <span className="myModalTitleMarginTop">
                                                        Traiter Dépôt PFE
                                                    </span>
                                </CCol>
                                <CCol md="10" className="colPadding">
                                  <div className="myDivMarginTop">
                                                        <span className="myModalSubTitle">
                                                          <ReactTextTransition text={selectedStudentFullName} springConfig={{tension: 10, friction: 10}}/>
                                                        </span>
                                  </div>
                                </CCol>
                              </CRow>


                              <hr/>
                            </DialogTitle>
                            <DialogContent>
                              <CRow>
                                <CCol xs="9">
                                  <CRow>
                                    <CCol xs="3">
                                    <span className="myModalField">
                                        Département:
                                    </span>
                                    </CCol>
                                    <CCol xs="9">
                                      {selectedStudentDepartment}
                                    </CCol>
                                  </CRow>

                                  <CRow>
                                    <CCol xs="3">
                                    <span className="myModalField">
                                        Option:
                                    </span>
                                    </CCol>
                                    <CCol xs="9">
                                      {selectedStudentOption}
                                    </CCol>
                                  </CRow>

                                  <CRow>
                                    <CCol xs="3">
                                    <span className="myModalField">
                                        Classe:
                                    </span>
                                    </CCol>
                                    <CCol xs="9">
                                      {selectedStudentClasse}
                                    </CCol>
                                  </CRow>

                                  <CRow>
                                    <CCol xs="3">
                                    <span className="myModalField">
                                        Entreprise d'Accueil:
                                    </span>
                                    </CCol>
                                    <CCol xs="9">
                                      {selectedStudentSocieteLibelle}
                                    </CCol>
                                  </CRow>

                                  <CRow>
                                    <CCol xs="3">
                                    <span className="myModalField">
                                        Nom du ou des projets:
                                    </span>
                                    </CCol>
                                    <CCol xs="9">
                                      {selectedStudentProjectTitle}
                                    </CCol>
                                  </CRow>
                                </CCol>
                                <CCol xs="3">
                                  <center>
                                    <TextField  id="filled-number"
                                                label="Note Encadrant Académique ( ... / 20)"
                                                type="number"
                                                fullWidth
                                                margin="normal"
                                                variant="filled"
                                                inputProps={{ shrink: true, min: "0", max: "20", step: "0.25" }}
                                                value={Number(this.state.notePE)}
                                                disabled= {etatTreat === "02"}
                                                onChange={(e) =>
                                                { if (e.target.value <= 20) this.setState({notePE: e.target.value});
                                                  if (e.target.value > 20) this.setState({notePE: 20});
                                                }}/>
                                  </center>
                                </CCol>
                              </CRow>
                              <br/><hr/><br/>
                              <CRow>
                                <CCol sm="4">
                                  <CCard>
                                    <CCardHeader>
                                    <span className="littleCardTitle">
                                      <Wave style={{fontSize:"50px"}} text="Livrables" effect="fadeOut" effectChange={1.0} />
                                    </span>
                                    </CCardHeader>
                                    <CCardBody>
                                      <CRow>
                                        <CCol md="9">
                                          <span className="float-center" style={{fontSize: "15px", color: "#b30000", fontFamily:"'Courier New', monospace"}}>Livrable</span>
                                        </CCol>
                                        <CCol md="3">
                                          <span className="float-center" style={{fontSize: "15px", color: "#b30000", fontFamily:"'Courier New', monospace"}}>État</span>
                                        </CCol>
                                      </CRow>
                                      <hr/>
                                      <CListGroup accent>
                                        <CListGroupItem accent="danger" color="danger">
                                          Planning de Stage
                                          <CSwitch  className="float-right" color={'danger'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onPlanningStg}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onPlanningStg: !onPlanningStg})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="dark" color="dark">
                                          Bilan Périodique début de Stage
                                          <CSwitch  className="float-right" color={'dark'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onBilanPerDebStg}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onBilanPerDebStg: !onBilanPerDebStg})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="secondary" color="secondary">
                                          Bilan Périodique milieu de Stage
                                          <CSwitch  className="float-right" color={'secondary'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onBilanPerMilStg}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onBilanPerMilStg: !onBilanPerMilStg})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="info" color="info">
                                          Fiche d'Évaluation mi-parcours
                                          <CSwitch  className="float-right" color={'info'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onFicheEvalMiPar}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onFicheEvalMiPar: !onFicheEvalMiPar})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="primary" color="primary">
                                          Fiche d'Évaluation finale
                                          <CSwitch  className="float-right" color={'primary'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onFicheEvalFin}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onFicheEvalFin: !onFicheEvalFin})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="light" color="light">
                                          Bilan Périodique fin de Stage
                                          <CSwitch  className="float-right" color={'dark'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onBilanPerFinStg}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onBilanPerFinStg: !onBilanPerFinStg})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="warning" color="warning">
                                          Journal de Bord
                                          <CSwitch  className="float-right" color={'warning'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onJournalBord}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onJournalBord: !onJournalBord})}/>
                                        </CListGroupItem>

                                        <CListGroupItem accent="success" color="success">
                                          Rapport de Stage
                                          <CSwitch  className="float-right" color={'success'} labelOn={'\u2713'} labelOff={'\u2715'}
                                                    checked={onRapportStg}
                                                    disabled= {etatTreat === "02"}
                                                    onClick={() => this.setState({onRapportStg: !onRapportStg})}/>
                                        </CListGroupItem>
                                      </CListGroup>
                                    </CCardBody>
                                  </CCard>
                                </CCol>
                                <CCol sm="8">
                                  <CWidgetBrand color="facebook"
                                                rightHeader="Date Début Stage"
                                                rightFooter={selectedStudentDateDebutStage}
                                                leftHeader="Date Fin Stage"
                                                leftFooter={selectedStudentDateFinStage}>
                                    <CIcon  content={freeSet.cilAvTimer}
                                            height="25"
                                            className="my-3"/>
                                  </CWidgetBrand>
                                  <br/>

                                  <hr/>

                                  <span className="littleCardTitle">
                                      <Wave style={{fontSize:"50px"}} text="RDV Pédagogiques" effect="fadeOut" effectChange={1.0} />
                                  </span>
                                  <br/>
                                  <CDataTable items={pedagogicalRDVData}
                                              fields={rdvfields}
                                              striped
                                              itemsPerPage={5}
                                              pagination
                                              scopedSlots = {{
                                                'date':
                                                    (item, index)=>(
                                                        <>
                                                          {(
                                                              index === 0 &&
                                                              <td>
                                                                <br/>
                                                                <DatePicker selected={dateRDV1}
                                                                            onChange={(e) => this.handleChangeDateRDV(index, e)}
                                                                            isClearable
                                                                            className="form-control"
                                                                            dateFormat="dd-MM-yyyy"
                                                                            disabled= {etatTreat === "02"}
                                                                            popperPlacement="auto"
                                                                            popperProps={{positionFixed: true}}/>
                                                              </td>
                                                          )}

                                                          {(
                                                              index === 1 &&
                                                              <td>
                                                                <br/>
                                                                <DatePicker selected={dateRDV2}
                                                                            onChange={(e) => this.handleChangeDateRDV(index, e)}
                                                                            isClearable
                                                                            className="form-control"
                                                                            dateFormat="dd-MM-yyyy"
                                                                            disabled= {etatTreat === "02"}
                                                                            popperPlacement="auto"
                                                                            popperProps={{positionFixed: true}}/>
                                                              </td>
                                                          )}

                                                          {(
                                                              index === 2 &&
                                                              <td>
                                                                <br/>
                                                                <DatePicker selected={dateRDV3}
                                                                            onChange={(e) => this.handleChangeDateRDV(index, e)}
                                                                            isClearable
                                                                            className="form-control"
                                                                            dateFormat="dd-MM-yyyy"
                                                                            disabled= {etatTreat === "02"}
                                                                            popperPlacement="auto"
                                                                            popperProps={{positionFixed: true}}/>
                                                              </td>
                                                          )}
                                                        </>
                                                    ),
                                                'presence':
                                                    (item, index)=>(
                                                        <>
                                                          {(
                                                              index === 0 &&
                                                              <td>
                                                                <center>
                                                                  <br/>
                                                                  {statusKinds.map(
                                                                      (optionRDV1, iRDV1) => {
                                                                        return (
                                                                            <CFormGroup id="grRDV1" variant="custom-radio" inline>
                                                                              <CInputRadio  custom id={iRDV1}
                                                                                            disabled= {etatTreat === "02"}
                                                                                            checked={checkedRDV1 === iRDV1 ? true : false}
                                                                                            onChange={(eventRDV1) => {
                                                                                              // console.log(optionRDV1); // Object { value: "Non", label: "Non"}
                                                                                              // console.log('----------------> 1.1: ' + checkedRDV1 + "_" + iRDV1);
                                                                                              this.setState({checkedRDV1: iRDV1, presenceKindRDV1: optionRDV1.value});
                                                                                              // console.log('----------------> 1.2: ' + checkedRDV1 + "_" + iRDV1);
                                                                                            }}/>
                                                                              <CLabel variant="custom-checkbox" htmlFor={iRDV1}>{optionRDV1.label}</CLabel>
                                                                            </CFormGroup>
                                                                        );
                                                                      }
                                                                  )}
                                                                </center>
                                                              </td>
                                                          )}

                                                          {(
                                                              index === 1 &&
                                                              <td>
                                                                <center>
                                                                  <br/>
                                                                  {statusKinds.map(
                                                                      (optionRDV2, iRDV2) => {
                                                                        return (
                                                                            <CFormGroup id="grRDV2" variant="custom-radio" inline>
                                                                              <CInputRadio  custom id={Number(iRDV2+2)}
                                                                                            disabled= {etatTreat === "02"}
                                                                                            checked={checkedRDV2 === Number(iRDV2+2) ? true : false}
                                                                                            onChange={(eventRDV2) => {
                                                                                              // console.log(optionRDV2); // Object { value: "Non", label: "Non"}
                                                                                              // console.log('----------------> 2.1: ' + checkedRDV2 + "_" + iRDV2);
                                                                                              this.setState({checkedRDV2: Number(iRDV2+2), presenceKindRDV2: optionRDV2.value});
                                                                                              // console.log('----------------> 2.2: ' + checkedRDV2 + "_" + iRDV2);
                                                                                            }}/>
                                                                              <CLabel variant="custom-checkbox" htmlFor={Number(iRDV2+2)}>{optionRDV2.label}</CLabel>
                                                                            </CFormGroup>
                                                                        );
                                                                      }
                                                                  )}
                                                                </center>
                                                              </td>
                                                          )}

                                                          {(
                                                              index === 2 &&
                                                              <td>
                                                                <center>
                                                                  <br/>
                                                                  {statusKinds.map(
                                                                      (optionRDV3, iRDV3) => {
                                                                        return (
                                                                            <CFormGroup id="grRDV3" variant="custom-radio" inline>
                                                                              <CInputRadio  custom id={Number(iRDV3+4)}
                                                                                            disabled= {etatTreat === "02"}
                                                                                            checked={checkedRDV3 === Number(iRDV3+4) ? true : false}
                                                                                            onChange={(eventRDV3) => {
                                                                                              // console.log(optionRDV3); // Object { value: "Non", label: "Non"}
                                                                                              // console.log('----------------> 3.1: ' + checkedRDV3 + "_" + iRDV3);
                                                                                              this.setState({checkedRDV3: Number(iRDV3+4), presenceKindRDV3: optionRDV3.value});
                                                                                              // console.log('----------------> 3.2: ' + checkedRDV3 + "_" + iRDV3);
                                                                                            }}/>
                                                                              <CLabel variant="custom-checkbox" htmlFor={Number(iRDV3+4)}>{optionRDV3.label}</CLabel>
                                                                            </CFormGroup>
                                                                        );
                                                                      }
                                                                  )}
                                                                </center>
                                                              </td>
                                                          )}
                                                        </>
                                                    ),
                                                'status':
                                                    (item, index)=>(
                                                        <>
                                                          {(
                                                              index === 0 &&
                                                              <td>
                                                                <center>
                                                                  <br/>
                                                                  {statusKinds.map(
                                                                      (statusRDV1, isRDV1) => {
                                                                        return (
                                                                            <CFormGroup id="stRDV1" variant="custom-radio" inline>
                                                                              <CInputRadio  custom id={Number(isRDV1+6)}
                                                                                            disabled= {etatTreat === "02"}
                                                                                            checked={checkedsRDV1 === Number(isRDV1+6) ? true : false}
                                                                                            onChange={(eventRDV1) => {
                                                                                              // console.log(statusRDV1); // Object { value: "Non", label: "Non"}
                                                                                              // console.log('----------------> 1.1: ' + checkedsRDV1 + "_" + isRDV1);
                                                                                              this.setState({checkedsRDV1: Number(isRDV1+6), statusKindRDV1: statusRDV1.value});
                                                                                              // console.log('----------------> 1.2: ' + checkedsRDV1 + "_" + isRDV1);
                                                                                            }}/>
                                                                              <CLabel variant="custom-checkbox" htmlFor={Number(isRDV1+6)}>{statusRDV1.label}</CLabel>
                                                                            </CFormGroup>
                                                                        );
                                                                      }
                                                                  )}
                                                                </center>
                                                              </td>
                                                          )}

                                                          {(
                                                              index === 1 &&
                                                              <td>
                                                                <center>
                                                                  <br/>
                                                                  {statusKinds.map(
                                                                      (statusRDV2, isRDV2) => {
                                                                        return (
                                                                            <CFormGroup id="stRDV2" variant="custom-radio" inline>
                                                                              <CInputRadio  custom id={Number(isRDV2+8)}
                                                                                            disabled= {etatTreat === "02"}
                                                                                            checked={checkedsRDV2 === Number(isRDV2+8) ? true : false}
                                                                                            onChange={(eventRDV2) => {
                                                                                              // console.log(statusRDV2); // Object { value: "Non", label: "Non"}
                                                                                              // console.log('----------------> 2.1: ' + checkedsRDV2 + "_" + isRDV2);
                                                                                              this.setState({checkedsRDV2: Number(isRDV2+8), statusKindRDV2: statusRDV2.value});
                                                                                              // console.log('----------------> 2.2: ' + checkedsRDV2 + "_" + isRDV2);
                                                                                            }}/>
                                                                              <CLabel variant="custom-checkbox" htmlFor={Number(isRDV2+8)}>{statusRDV2.label}</CLabel>
                                                                            </CFormGroup>
                                                                        );
                                                                      }
                                                                  )}
                                                                </center>
                                                              </td>
                                                          )}

                                                          {(
                                                              index === 2 &&
                                                              <td>
                                                                <center>
                                                                  <br/>
                                                                  {statusKinds.map(
                                                                      (statusRDV3, isRDV3) => {
                                                                        return (
                                                                            <CFormGroup id="stRDV3" variant="custom-radio" inline>
                                                                              <CInputRadio  custom id={Number(isRDV3+10)}
                                                                                            disabled= {etatTreat === "02"}
                                                                                            checked={checkedsRDV3 === Number(isRDV3+10) ? true : false}
                                                                                            onChange={(eventRDV3) => {
                                                                                              // console.log(statusRDV3); // Object { value: "Non", label: "Non"}
                                                                                              // console.log('----------------> 3.1: ' + checkedsRDV3 + "_" + isRDV3);
                                                                                              this.setState({checkedsRDV3: Number(isRDV3+10), statusKindRDV3: statusRDV3.value});
                                                                                              // console.log('----------------> 3.2: ' + checkedsRDV3 + "_" + isRDV3);
                                                                                            }}/>
                                                                              <CLabel variant="custom-checkbox" htmlFor={Number(isRDV3+10)}>{statusRDV3.label}</CLabel>
                                                                            </CFormGroup>
                                                                        );
                                                                      }
                                                                  )}
                                                                </center>
                                                              </td>
                                                          )}
                                                        </>
                                                    )
                                              }}
                                  />
                                </CCol>
                              </CRow>
                            </DialogContent>
                            <DialogActions>
                              {
                                treatByPC !== "01" && showSpinnerValidateDepotPFE === false &&
                                <CButton color="success"
                                         disabled={showSpinnerSaveDepotPFE}
                                         onClick={() => this.handleValidateFicheDepotPFE()}>
                                  Valider lol
                                </CButton>
                              }
                              {
                                showSpinnerValidateDepotPFE === true &&
                                <Spinner animation="grow" variant="success"/>
                              }
                              &nbsp;&nbsp;
                              <CButton  variant="outline"
                                        color="dark"
                                        onClick={this.handleClosePopupTreatDepotPFE}>
                                Exit
                              </CButton>
                            </DialogActions>
                          </Dialog>

                          <Dialog fullHight fullWidth
                                  maxWidth="sm"
                                  keepMounted
                                  open={openPopupSaveDepotPFE}
                                  onClose={this.handleClosePopupSaveDepotPFE}
                                  aria-labelledby="form-dialog-title">
                            <DialogTitle id="form-dialog-title">
                              <span className="warningIcon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Information</span>
                              <hr/>
                            </DialogTitle>
                            <DialogContent>
                              <center>
                                Vous avez traité le Dépôt Fiche PFE de l'étudiant &nbsp;
                                <span className="text-primary" style={{fontSize: "12px"}}>
                              {selectedStudentFullName}
                            </span>
                              </center>
                              <br/>
                            </DialogContent>
                            <DialogActions>
                              <Button onClick={this.handleClosePopupSaveDepotPFE} color="primary">
                                Exit
                              </Button>
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
                                Vous avez validé le Dépôt Fiche PFE de l'étudiant &nbsp;
                                <span className="text-primary" style={{fontSize: "12px"}}>
                        {selectedStudentFullName}
                      </span>
                              </center>
                              <br/>
                            </DialogContent>
                            <DialogActions>
                              <Button onClick={this.handleClosePopupValidateDepotPFE} color="primary">
                                Exit
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
