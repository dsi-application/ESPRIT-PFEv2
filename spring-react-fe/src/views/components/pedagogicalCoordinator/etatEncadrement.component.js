import React, {Component} from "react";
import AuthService from "../../services/auth.service";
import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCol,
  CDataTable,
  CFormGroup,
  CInputRadio,
  CLabel,
  CRow,
  CTooltip,
  CCardHeader,
  CContainer
} from "@coreui/react";
import CIcon from "@coreui/icons-react";

import Badge from '@material-ui/core/Badge';
import PersonIcon from '@material-ui/icons/Person';

import {freeSet} from '@coreui/icons';
import "../../css/style.css";

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import Select from "react-select";
import makeAnimated from "react-select/animated";

import ReactTextTransition from "react-text-transition";

import Spinner from "react-bootstrap/Spinner";
import PedagogicalCoordinatorService from "../../services/pedagogicalCoordinator.service";
import ResponsableServiceStageService from "../../services/responsableServiceStage.service";
import excelGreenIcon from "../../images/excelGreenIcon.png";

const animatedComponents = makeAnimated();

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_PC = process.env.REACT_APP_API_URL_PC;


const currentPedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();


const academicEncadrantsFields = [
  {
    key: 'identifiant',
    label: 'Identifiant',
    _style: {width: '6%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'nom',
    label: 'Nom & Prénom',
    _style: {width: '25%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'numTel',
    label: 'Numéro Tél',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'mail',
    label: 'Adresse Mail',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'nbrEncadrements',
    label: 'Nbr Encadrements',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'up',
    label: 'Unité Pédagogique',
    _style: {width: '20%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'action',
    label: 'Action',
    _style: {width: '10%'},
    sorter: false,
    filter: false
  }
];

const affectedStudentsFields = [
  {
    key: 'idEt',
    label: 'Identifiant',
    _style: {width: '6%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'fullName',
    label: 'Nom & Prénom',
    _style: {width: '29%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'univerYear',
    label: 'Année Universitaire',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'option',
    label: 'Option',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'classe',
    label: 'Classe',
    _style: {width: '15%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'adressemailesp',
    label: 'E-Mail',
    _style: {width: '20%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'phone',
    label: 'Téléphone',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  }
];


export default class EtatEncadrement extends Component {
  constructor(props) {
    super(props);

    this.loadAcademicEncadrants = this.loadAcademicEncadrants.bind(this);
    this.showAffectedStudentsList = this.showAffectedStudentsList.bind(this);
    this.handleClosePopupShowAffectedStudentsToAE = this.handleClosePopupShowAffectedStudentsToAE.bind(this);
    this.onChangeSelectCEP = this.onChangeSelectCEP.bind(this);
    this.handleDownloadEncadrementStatus = this.handleDownloadEncadrementStatus.bind(this);

    this.state = {
      academicEncadrants: [],
      affectesStudentsToAE: [],
      loadAcademicEncadrants: true,
      loadAffectedStudentsToAE: false,
      selectedAcademicEncadrantId: "",
      selectedAcademicEncadrantFullName: "",
      openPopupShowAffectesStudentsListToAE: false,
      allSessionsLabel: [],
      selectedYear: '2022',
      loadStudentsCJByYear: true
    }

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

    this.loadAcademicEncadrants();

  }

  loadAcademicEncadrants()
  {
    this.setState({loadAcademicEncadrants: true});

    PedagogicalCoordinatorService.gotListAcademicEncadrantsByYear(this.state.selectedYear).then(
        (response) => {

          let aeList = this.state.academicEncadrants.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          this.setState({
            loadAcademicEncadrants: false,
            academicEncadrants: aeList
          });

        },
        (error) => {
        }
    );
  }

  handleDownloadEncadrementStatus()
  {
    this.setState({showLoadingExcelIcon: true})

    ResponsableServiceStageService.downloadAllEncadrementStatusByYear(this.state.selectedYear).then(
        (response) => {
          this.setState({showLoadingExcelIcon: false})

          const contentDispo = response.headers['content-disposition'];
          const fileName = contentDispo.substring(21);

          const file = new File([response.data], fileName);
          const fileURL = URL.createObjectURL(file);
          // console.log("Check Disponibility ------------- DONE EX ", response.data);
          let a = document.createElement('a');
          a.href = fileURL;
          a.download = fileName;
          a.click();
          // window.open(fileURL);
        },
        (error) => {
          // console.log("Check Disponibility --------cr----- FAIL");
        }
    );
  }

  onChangeSelectCEP = (selectedCompOption) => {

    /*
    let cepLabel = selectedCompOption.label;

    console.log('----------1-------------> 21.09.22: ' , selectedCompOption);
    console.log('----------2-------------> 21.09.22: ' , cepLabel);
    console.log('----------3.1------*-------> 21.09.22: ' , currentPedagogicalCoordinator.id);

    this.state.loadAcademicEncadrants = true;
    this.state.selectedYear = selectedCompOption.label;

    console.log('----------3.2-------------> 21.09.22: ' + selectedCompOption.label + " - " + this.state.selectedYear);
    console.log('----------3.2-------------> 21.09.22: ' + this.state.loadAcademicEncadrants);

    PedagogicalCoordinatorService.gotListAcademicEncadrantsByYear(selectedCompOption.label).then(
        (response) => {

          console.log('----------3.3-------------> 21.09.22: ' , response.data);
          for (let ae of response.data)
          {
            this.state.academicEncadrants.push(ae);
          }

        },
        (error) => {
        }
    );
  */
    console.log('----------4-------------> 21.09.22: ' , this.state.academicEncadrants);

    /*****************************************************************************************/


    this.setState({
      loadAcademicEncadrants: true,
      selectedYear: selectedCompOption.label
    });

    PedagogicalCoordinatorService.gotListAcademicEncadrantsByYear(selectedCompOption.label).then(
        (response) => {
          this.setState({
            academicEncadrants: []
          });

          let stuList = this.state.academicEncadrants.slice();
          for (let stu of response.data) {
            // console.log('----------------lol123--------> Students : ', stu);
            const studentObj = stu;
            stuList.push(stu);
          }

          this.setState({
            loadAcademicEncadrants: false,
            academicEncadrants: stuList
          });

        },
        (error) => {
        }

    );

  };

  showAffectedStudentsList(idEns, nomEns)
  {

    this.setState({
      affectesStudentsToAE: [],
      loadAffectedStudentsToAE: true,
      selectedAcademicEncadrantId: idEns,
      selectedAcademicEncadrantFullName: nomEns
    });

    AuthService.findAllAffectedStudentsToAEByYear(idEns, this.state.selectedYear).then(
        (response) => {

          let aeList = this.state.affectesStudentsToAE.slice();
          for (let ae of response.data)
          {
            aeList.push(ae);
          }

          console.log('------280922--------> HI2022', aeList);

          this.setState({
            loadAffectedStudentsToAE: false,
            affectesStudentsToAE: aeList,
            openPopupShowAffectesStudentsListToAE: true
          });

        },
        (error) => {
        }
    );
  }

  handleClosePopupShowAffectedStudentsToAE()
  {
    this.setState({openPopupShowAffectesStudentsListToAE: false});
  }

  render() {

    const {
      academicEncadrants,
      loadAcademicEncadrants,
      loadAffectedStudentsToAE,
      selectedAcademicEncadrantId,
      selectedAcademicEncadrantFullName,
      openPopupShowAffectesStudentsListToAE,
      affectesStudentsToAE,
      allSessionsLabel,
      selectedYear,
      showLoadingExcelIcon
    } = this.state;

    return (
        <CCard accentColor="danger">
          <CCardHeader>
            <CContainer>
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
                           onChange={this.onChangeSelectCEP}/>
                </CCol>
                <CCol md="3"/>
                <CCol md="1">
                  {
                    !showLoadingExcelIcon &&
                    <CButton shape="pill"
                             onClick={() => this.handleDownloadEncadrementStatus()}>
                      <CTooltip content="Télécharger l'État Encadrement Détaillé"
                                placement="top">
                        <img src={excelGreenIcon} className="cursorPointer"
                             width="40px" height="40px"/>
                      </CTooltip>
                    </CButton>
                  }
                  {
                    showLoadingExcelIcon &&
                    <Spinner animation="grow" variant="success"/>
                  }
                </CCol>
              </CRow>
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
                        loadAcademicEncadrants === true ?
                        <center>
                            <br/><br/><br/><br/><br/>
                            <span className="waitIcon"/>
                            <br/><br/><br/><br/><br/>
                          </center>
                        :
                        <>
                          <br/><br/>
                          <hr/>
                          <CRow>
                            <CCol md="10" class="bg-success py-2">
                              <span style={{color: "#cc0000", fontSize: "14px", fontWeight: "bold"}}>
                                Liste des Encadrants Académiques pour l'Année Universitaire &nbsp;&nbsp;<span className="clignoteRed">{selectedYear} - {Number(selectedYear)+1}</span>
                              </span>
                            </CCol>
                          </CRow>
                          <CDataTable items={academicEncadrants}
                                      fields={academicEncadrantsFields}
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
                                      scopedSlots={{
                                              action: (item) => (
                                                  <td>
                                                    <center>
                                                      {
                                                        (
                                                            item.nbrEncadrements === 0 &&
                                                            (
                                                                (loadAffectedStudentsToAE === false)
                                                                ||
                                                                (item.identifiant !== selectedAcademicEncadrantId && loadAffectedStudentsToAE === true)
                                                            )
                                                        ) &&
                                                        <CButton shape="pill"
                                                                 color="dark"
                                                                 size="sm"
                                                                 disabled="true"
                                                                 aria-expanded="true">
                                                          <CTooltip
                                                              content="Bouton DISABLED : Cet Enseignant n'a pas encore des encadrements"
                                                              placement="top">
                                                            <CIcon content={freeSet.cilMinus}/>
                                                          </CTooltip>
                                                        </CButton>
                                                      }
                                                      {
                                                        (
                                                            item.nbrEncadrements !== 0 &&
                                                            (
                                                                (loadAffectedStudentsToAE === false)
                                                                ||
                                                                (item.identifiant !== selectedAcademicEncadrantId && loadAffectedStudentsToAE === true)
                                                            )
                                                        ) &&
                                                        <CButton shape="pill"
                                                                 color="primary"
                                                                 size="sm"
                                                                 aria-expanded="true"
                                                                 onClick={() => this.showAffectedStudentsList(item.identifiant, item.nom)}>
                                                          <CTooltip content="Consulter la liste des Étudiants à encadrer"
                                                                    placement="top">
                                                            <CIcon content={freeSet.cilBraille}/>
                                                          </CTooltip>
                                                        </CButton>
                                                      }
                                                      {
                                                        item.identifiant === selectedAcademicEncadrantId && loadAffectedStudentsToAE === true &&
                                                        <Spinner animation="grow" variant="primary"/>
                                                      }
                                                    </center>
                                                  </td>
                                              ),
                                            }}
                          />
                            <Dialog fullHight fullWidth
                                    maxWidth="md"
                                    keepMounted
                                    open={openPopupShowAffectesStudentsListToAE}
                                    onClose={this.handleClosePopupShowAffectedStudentsToAE}
                                    aria-labelledby="form-dialog-title">
                              <DialogTitle id="form-dialog-title">
                                <CRow>
                                  <CCol md="4">
                              <span className="myModalTitleMarginTop">
                                Liste des Étudiants encadrés par Mme/M
                              </span>
                                  </CCol>
                                  <CCol md="8" className="colPadding">
                                    <div className="myDivMarginTop">
                                <span className="myModalSubTitle">
                                  <ReactTextTransition text={selectedAcademicEncadrantFullName}
                                                       springConfig={{tension: 10, friction: 10}}/>
                                </span>
                                    </div>
                                  </CCol>
                                </CRow>
                                <hr/>
                              </DialogTitle>
                              <DialogContent>
                                {
                                  <CDataTable items={affectesStudentsToAE}
                                              fields={affectedStudentsFields}
                                              tableFilter
                                              columnFilter
                                              itemsPerPageSelect
                                              hover
                                              sorter
                                              striped
                                              bordered
                                              size="sm"
                                              itemsPerPage={6}
                                              pagination/>
                                }
                              </DialogContent>
                              <DialogActions>
                                <Button onClick={this.handleClosePopupShowAffectedStudentsToAE} color="primary">
                                  Exit
                                </Button>
                              </DialogActions>
                            </Dialog>
                          </>
                    }
                  </>
              }
            </CContainer>
          </CCardHeader>
        </CCard>
    );
  }
}
