import React, { Component } from "react";
import { CButton,
  CDropdown,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import AuthService from "../views/services/auth.service";
import { Link } from "react-router-dom";


import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import LockIcon from '@material-ui/icons/Lock';

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;

export default class TheHeaderDropdown extends Component {
  constructor(props) {
    super(props);
    this.logout = this.logout.bind(this);
    this.redirectUserProfile = this.redirectUserProfile.bind(this);

    /*
        console.log('--------------- currentResponsible: ' + currentResponsible.id);
        console.log('--------------- currentStudent: ' + currentStudent.id);
        console.log('--------------- currentTeacher: ' + currentTeacher.id);
        */

    this.state = {
      currentResponsableServiceStage: null,
      currentStudent: null,
      currentTeacher: null,
      currentPedagogicalCoordinator: null,
      studentFullName: "",
      responsableServiceStageFullName: "",
      teacherFullName: "",
      pedagogicalCoordinatorFullName: ""
    };

    this.state.currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
    this.state.currentStudent = AuthService.getCurrentStudent();
    this.state.currentTeacher = AuthService.getCurrentTeacher();
    this.state.currentPedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();

    if (this.state.currentStudent) {
      var requesta = new XMLHttpRequest();
      requesta.open(
        "GET",
          API_URL_STU + "studentFullName/" +
          this.state.currentStudent.id,
        false
      ); // return axios.get(API_URL + 'user/' + code);
      requesta.send(null);

      let resFN = requesta.responseText;
      // console.log('---------------1-> FN 2409: ' + resFN);

      this.state.studentFullName = this.titleCase(resFN);

      sessionStorage.setItem("studentFullName", this.state.studentFullName);

    }

    if (this.state.currentResponsableServiceStage) {
      var requesta = new XMLHttpRequest();
      requesta.open(
        "GET",
        API_URL_MESP + "respServStgFullName/" +
          this.state.currentResponsableServiceStage.id,
        false
      ); // return axios.get(API_URL + 'user/' + code);
      requesta.send(null);
      let resRSSFN = requesta.responseText;

      this.state.responsableServiceStageFullName = this.titleCase(resRSSFN);

      sessionStorage.setItem("respServStgFullName", this.state.responsableServiceStageFullName);
    }

    if (this.state.currentTeacher) {
      var requesta = new XMLHttpRequest();
      requesta.open(
        "GET",
        API_URL_MESP + "teacherFullName/" +
          this.state.currentTeacher.id,
        false
      ); // return axios.get(API_URL + 'user/' + code);
      requesta.send(null);
      this.state.teacherFullName =requesta.responseText;

      let resTeaFN = requesta.responseText;
      this.state.teacherFullName = this.titleCase(resTeaFN);

      // console.log('-----------------------------------> PIVA 1: ', this.state.currentTeacher);
      // console.log('-----------------------------------> PIVA 2: ' + this.state.currentTeacher.id);
      sessionStorage.setItem("teacherFullName", this.state.teacherFullName);
    }

    if (this.state.currentPedagogicalCoordinator) {
      var requesta = new XMLHttpRequest();
      requesta.open(
          "GET",
          API_URL_MESP + "pedagogicalCoordinatorFullName/" +
          this.state.currentPedagogicalCoordinator.id,
          false
      ); // return axios.get(API_URL + 'user/' + code);
      requesta.send(null);

      let resFN = requesta.responseText;
      // console.log('---------------1-> FN 2409: ' + resFN);

      this.state.pedagogicalCoordinatorFullName = this.titleCase(resFN);

      sessionStorage.setItem("pedagogicalCoordinatorFullName", this.state.pedagogicalCoordinatorFullName);

    }
  }

  componentDidMount() {
    // console.log("INIT----------------------------out 1");
  }

  titleCase(str)
  {
    let splitStr = str.toLowerCase().split(' ');
    for (let i = 0; i < splitStr.length; i++)
    {
      splitStr[i] = splitStr[i].charAt(0).toUpperCase() + splitStr[i].substring(1);
    }
    return splitStr.join(' ');
  }

  redirectUserProfile()
  {
     // console.log("FUNC---------------------------- 1");
    if (this.state.currentStudent !== null) {
       // console.log("FUNC---------------------------- Student");
      window.location.href = "/studentProfile";
    }

    if (this.state.currentResponsableServiceStage !== null) {
      // console.log("FUNC---------------------------- ResponsableServiceStage");
      window.location.href = "/responsableServiceStageProfile";
    }

    if (this.state.currentTeacher !== null) {
      // console.log("FUNC---------------------------- Teacher");
      window.location.href = "/teacherProfile";
    }

    if (this.state.currentPedagogicalCoordinator !== null) {
      // console.log("FUNC---------------------------- PedagogicalCoordinator");
      window.location.href = "/pedagogicalCoordinatorProfile";
    }
     // console.log("FUNC---------------------------- 2");
  }

  logout() {
    // console.log("FUNC----------------------------out 1");

    if (this.state.currentesponsableServiceStage !== null) {
      // console.log("FUNC---------------------------- ResponsableServiceStage");
      AuthService.logoutResponsableServiceStage();
    }

    if (this.state.currentStudent !== null) {
      // console.log("FUNC---------------------------- Student");
      AuthService.logoutStudent();
    }

    if (this.state.currentTeacher !== null) {
      // console.log("FUNC---------------------------- Teacher");
      AuthService.logoutTeacher();
    }

    if (this.state.currentPedagogicalCoordinator !== null) {
      // console.log("FUNC---------------------------- PedagogicalCoordinator");
      AuthService.logoutPedagogicalCoordinator();
    }

    // console.log("FUNC----------------------------out 2");
  }

  render() {
    const {
      studentFullName,
      teacherFullName,
      responsableServiceStageFullName,
      pedagogicalCoordinatorFullName
    } = this.state;

    return (
      <div>
        <CDropdown inNav className="c-header-nav-items mx-2" direction="down">
          <CDropdownToggle className="c-header-nav-link" caret={false}>
            {studentFullName && (
              <span className="connectedUserStyle">{studentFullName}</span>
            )}

            {teacherFullName && (
              <span className="connectedUserStyle">{teacherFullName}</span>
            )}

            {responsableServiceStageFullName && (
              <span className="connectedUserStyle">
                {responsableServiceStageFullName.substring(0, responsableServiceStageFullName.lastIndexOf(" -"))}
              </span>
            )}

            {pedagogicalCoordinatorFullName &&
                <span className="connectedUserStyle">
                {pedagogicalCoordinatorFullName}
              </span>
            }
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span className="userIcon" />
          </CDropdownToggle>
          <CDropdownMenu className="pt-0" placement="bottom-end">
            <CDropdownItem
              header
              tag="div"
              color="light"
              className="text-center"
            >
              <strong>Compte</strong>
            </CDropdownItem>
            <CDropdownItem onClick={() => this.redirectUserProfile()}>
              <AssignmentIndIcon color="action"/>
              &nbsp;&nbsp;
              Mon Profil
            </CDropdownItem>

            <CDropdownItem divider />
            <CDropdownItem onClick={() => this.logout()}>
              <LockIcon color="action"/>
              &nbsp;&nbsp;
              Se Déconnecter
            </CDropdownItem>
          </CDropdownMenu>
        </CDropdown>
      </div>
    );
  }
}
