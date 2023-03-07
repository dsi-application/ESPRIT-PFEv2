import React, {Component} from "react";
import AuthService from "../../services/auth.service";
import ResponsableServiceStageService from "../../services/responsableServiceStage.service";
import PedagogicalCoordinatorService from "../../services/pedagogicalCoordinator.service";
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
  CTooltip
} from "@coreui/react";

import excelGreenIcon from "../../images/excelGreenIcon.png";
import CIcon from "@coreui/icons-react";

import Badge from '@material-ui/core/Badge';
import PersonIcon from '@material-ui/icons/Person';

import {freeSet} from '@coreui/icons';
import "../../css/style.css";
//import { Wave1, Wave2, Random1, Random2 } from './example.js';

import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import ReactTextTransition from "react-text-transition";

import Spinner from "react-bootstrap/Spinner";
import excelBlueIcon from "../../images/excelBlueIcon.jpg";

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();

const studentsByDeptFields = [
  {
    key: 'studentId',
    label: 'Identifiant',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'studentFullName',
    label: 'Nom & Prénom',
    _style: {width: '20%'},
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
    label: 'Numéro Téléphone',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'studentClasse',
    label: 'Classe',
    _style: {width: '10%'},
    sorter: true,
    filter: true,
    show: true
  },
  {
    key: 'academicEncadFullName',
    label: 'Nom Encadrant',
    _style: {width: '20%'},
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

export default class EncadrementsByDepartementComponent extends Component {
  constructor(props) {
    super(props);

    this.onChangeClassByDept = this.onChangeClassByDept.bind(this);
    this.handleConsultDetailsAcademicEncadrant = this.handleConsultDetailsAcademicEncadrant.bind(this);
    this.handleClosePopupShowAEDetails = this.handleClosePopupShowAEDetails.bind(this);
    this.handleDownloadEncadrementStatus = this.handleDownloadEncadrementStatus.bind(this);

    this.state = {
      checked: null,
      myDepartements: [
        {value: "IT", label: "IT"},
        {value: "EM", label: "EM"},
        {value: "GC", label: "GC"},
        {value: "ALT", label: "ALT"}
      ],
      loadStudentsByDept: false,
      studentsByDept: [],
      selectedDepartement: "",
      academicEncadrant: {},
      selectedStudentFullName: "",
      selectedStudentId: "",
      openPopupShowAEDetails: false,
      showLoadingExcelIcon: false,
    }
  }

  onChangeClassByDept(iDEPT, optionDEPT) {
    // console.log('-lol123-------------->', iDEPT); // Object { value: "Non", label: "Non"}
    // console.log('-----lol123-----------> 1.1: ' + this.state.checked + "_" + iDEPT);
    this.setState({
      checked: iDEPT,
      selectedDepartement: optionDEPT,
      loadStudentsByDept: true
    });

    // console.log('------&&---lol123-------> 1.2: ' + this.state.checked + "_" + iDEPT + " - " + optionDEPT);

    ResponsableServiceStageService.gotListStudentsByDepartement(optionDEPT).then(
        (response) => {
          this.setState({
            studentsByDept: []
          });

          let stuList = this.state.studentsByDept.slice();
          for (let stu of response.data) {
            // console.log('----------------lol123--------> Students : ', stu);
            const studentObj = stu;
            stuList.push(stu);
          }

          this.setState({
            loadStudentsByDept: false,
            studentsByDept: stuList
          });

        },
        (error) => {
        }
    );

  }

  handleDownloadEncadrementStatus()
  {
    this.setState({showLoadingExcelIcon: true})

    ResponsableServiceStageService.downloadAllEncadrementStatus().then(
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

  handleConsultDetailsAcademicEncadrant(idEt, fullName) {
    this.setState({
      academicEncadrant: {},
      selectedStudentFullName: fullName,
      selectedStudentId: idEt
    });

    let requestb = new XMLHttpRequest();
    requestb.open(
        "GET",
        API_URL_MESP + "findPedagogicalEncadrantByStudent/" + idEt,
        false
    ); // return axios.get(API_URL_MESP + 'user/' + code);
    requestb.send(null);
    let result = JSON.parse(requestb.responseText);
    // console.log('-------------------> >sars 1110: ', result);

    this.setState({
      academicEncadrant: result,
      openPopupShowAEDetails: true
    });
  }

  handleClosePopupShowAEDetails()
  {
    this.setState({openPopupShowAEDetails: false});
  }


  render() {

    const {
      checked,
      myDepartements,
      loadStudentsByDept,
      studentsByDept,
      selectedDepartement,
      academicEncadrant,
      openPopupShowAEDetails,
      selectedStudentFullName,
      showLoadingExcelIcon
    } = this.state;

    return (
        <CCard accentColor="danger">
          <CCardBody>
            <CRow>
              <CCol md="12">
                <center>
                  <CRow>
                    <CCol md="1"/>

                    <CCol md="10">
                      <br/>
                      <span className="redMark">Les Départements</span>
                      <br/><br/>
                      {
                        myDepartements.map(
                            (optionDEPT, iDEPT) => {
                              return (
                                  <CFormGroup id="grRDV1" variant="custom-radio" inline>
                                    <CInputRadio custom id={iDEPT}
                                                 checked={checked === iDEPT ? true : false}
                                                 onChange={this.onChangeClassByDept.bind(this, iDEPT, optionDEPT.label)}/>
                                    <CLabel variant="custom-checkbox" htmlFor={iDEPT}>{optionDEPT.label}</CLabel>
                                  </CFormGroup>
                              );
                            }
                        )}
                      <br/><br/>
                    </CCol>

                    <CCol md="1">
                      {
                        !showLoadingExcelIcon &&
                        <CButton shape="pill"
                                 onClick={() => this.handleDownloadEncadrementStatus()}>
                          <CTooltip content="Télécharger l'État Encadrement"
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
                </center>
              </CCol>
            </CRow>
            {
              loadStudentsByDept === true ?
                  <center>
                    <br/><br/><br/>
                    <span className="waitIcon"/>
                    <br/><br/><br/><br/><br/>
                  </center>
                  :
                  <>
                    {
                      selectedDepartement && loadStudentsByDept.length !== 0 &&
                      <>
                        <hr/>
                        <span className="redMark">Liste des Étudiants du département</span>
                        <br/>
                        <span className="greyMarkCourrier">{selectedDepartement}
                          <br/>
                          {studentsByDept.length}
                                </span>&nbsp;
                        <span className="greyMarkCourrierSmalLabel">étudiants</span>

                        <br/><br/><br/>

                        <CDataTable items={studentsByDept}
                                    fields={studentsByDeptFields}
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
                                                item.academicEncadFullName !== "--" ?
                                                    <CButton shape="pill"
                                                             color="success"
                                                             size="sm"
                                                             aria-expanded="true"
                                                             onClick={() => this.handleConsultDetailsAcademicEncadrant(item.studentId, item.studentFullName)}>
                                                      <CTooltip content="Consulter Coordonnées Encadrants Académique"
                                                                placement="top">
                                                        <CIcon content={freeSet.cilClipboard}/>
                                                      </CTooltip>
                                                    </CButton>
                                                    :
                                                    <>
                                                      <CButton shape="pill"
                                                               color="dark"
                                                               size="sm"
                                                               disabled
                                                               aria-expanded="true">
                                                        <CTooltip content="Cet Étudiant n'a pas encore un Encadrant Académique" placement="top">
                                                          <CIcon content={freeSet.cilMinus}/>
                                                        </CTooltip>
                                                      </CButton>
                                                    </>
                                              }
                                            </center>
                                          </td>
                                      ),
                                    }}
                        />
                      </>
                    }
                  </>
            }
          </CCardBody>

          <Dialog fullHight fullWidth
                  maxWidth="sm"
                  keepMounted
                  open={openPopupShowAEDetails}
                  onClose={this.handleClosePopupShowAEDetails}
                  aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">
              <CRow>
                <CCol md="8">
                  <span className="myModalTitleMarginTop">
                    Contact de l'Encadrant Académique de l'Étudiant(e)
                  </span>
                </CCol>
                <CCol md="4" className="colPadding">
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
                <CCol md="12">
                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Nom & Prénom:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {academicEncadrant.nom}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Identifiant:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {academicEncadrant.identifiant}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    E-Mail:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {
                        academicEncadrant.mail ?
                            <>{academicEncadrant.mail}</>
                            :
                            <>--</>
                      }
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Numéro Téléphone:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {
                        academicEncadrant.téléphone ?
                            <>{academicEncadrant.téléphone}</>
                            :
                            <>--</>
                      }
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Type:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {academicEncadrant.type === "P" && <>Permanant</>}
                      {academicEncadrant.type === "V" && <>Vacataire</>}
                      {academicEncadrant.type === "S" && <>Stagiaire</>}
                    </CCol>
                  </CRow>

                  <CRow>
                    <CCol xs="4">
                  <span className="myModalField">
                    Unité Pédagogique:
                  </span>
                    </CCol>
                    <CCol xs="8">
                      {
                        academicEncadrant.up ?
                            <>{academicEncadrant.up}</>
                            :
                            <>--</>
                      }
                    </CCol>
                  </CRow>
                </CCol>
              </CRow>
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClosePopupShowAEDetails} color="primary">
                Exit
              </Button>
            </DialogActions>
          </Dialog>
        </CCard>
    );
  }
}
