import React, { Component } from "react";
import { connect, useSelector, useDispatch } from "react-redux";
import {
  CRow,
  CCol,
  CCreateElement,
  CSidebar,
  CSidebarBrand,
  CSidebarNav,
  CSidebarNavDivider,
  CSidebarNavTitle,
  CSidebarNavDropdown,
  CSidebarNavItem,
} from "@coreui/react";

import esprit from "../views/images/esprit.gif";

import AuthService from "../views/services/auth.service";

const currentStudent = AuthService.getCurrentStudent();
const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
const currentTeacher = AuthService.getCurrentTeacher();
const currentPedagogicalCoordinator = AuthService.getCurrentPedagogicalCoordinator();

const API_URL_MESP = process.env.REACT_APP_API_URL_MESP;
const API_URL_STU = process.env.REACT_APP_API_URL_STU;

/***************************************** Student Management *****************************************/
const menuItemsStudentSecurePassword = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  /*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewStudentPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

const menuItemsAlertStudentWithoutPedagogicalEncadrant = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Tableau de Bord PFE",
    to: "/synopsisAndNews",
    icon: {
      name: "cil-lightbulb",
      className: "text-danger",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/studentProfile",
    icon: {
      name: "cil-user",
      className: "text-info",
    },
  },
  /*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewStudentPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

const menuItemsStudentSecondAccess = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Tableau de Bord PFE",
    to: "/synopsisAndNews",
    icon: {
      name: "cil-lightbulb",
      className: "text-danger",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Rendez-Vous & Visites",
    to: "/meetingsAndVisits",
    icon: {
      name: "cil-bell",
      className: "text-warning",
    },
  },
  {
	    _tag: "CSidebarNavDropdown",
	    name: "Gérer Convention",
	    icon: {
	      name: "cil-bookmark",
	      className: "text-warning",
	    },
	    _children: [
	      {
	        _tag: "CSidebarNavItem",
	        name: "Demander Convention",
	        to: "/submitAgreement",
	      },
	      {
	        _tag: "CSidebarNavItem",
	        name: "Déposer Convention Signée",
	        to: "/uploadSignedAgreement",
	      },
	    ],
  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Gérer Plan Travail",
    route: "/fichePFE",
    icon: {
      name: "cil-notes",
      className: "text-danger",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Ajouter Plan Travail",
        to: "/addESPFile",
      },
    ],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mes Documents",
    to: "/myDocuments",
    icon: {
      name: "cil-layers",
      className: "text-light"
    }
  },
  {
	_tag: "CSidebarNavItem",
	name: "Stage Ingénieur",
	to: "/uploadFicheEvalStageING",
	icon: {
	   name: "cil-star",
	   className: "text-warning",
	},
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/studentProfile",
    icon: {
      name: "cil-user",
      className: "text-info",
    },
  },
  /*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewStudentPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    }
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

const menuItemsStudentMinusAddFichePFE = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Tableau de Bord PFE",
    to: "/synopsisAndNews",
    icon: {
      name: "cil-lightbulb",
      className: "text-danger",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Rendez-Vous & Visites",
    to: "/meetingsAndVisits",
    icon: {
      name: "cil-bell",
      className: "text-warning",
    },
  },
  {
	    _tag: "CSidebarNavDropdown",
	    name: "Gérer Convention",
	    icon: {
	      name: "cil-bookmark",
	      className: "text-warning",
	    },
	    _children: [
	      {
	        _tag: "CSidebarNavItem",
	        name: "Demander Convention",
	        to: "/submitAgreement",
	      },
	      {
	        _tag: "CSidebarNavItem",
	        name: "Déposer Convention Signée",
	        to: "/uploadSignedAgreement",
	      },
	    ],
	  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Gérer Plan Travail",
    route: "/fichePFE",
    icon: {
      name: "cil-notes",
      className: "text-warning",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Modifier Plan Travail",
        to: "/modifyESPFile",
      },
      {
        _tag: "CSidebarNavItem",
        name: "Consulter Plan Travail",
        to: "/consultESPFile",
      }
    ],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mes Documents",
    to: "/myDocuments",
    icon: {
      name: "cil-layers",
      className: "text-light"
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Stage Ingénieur",
    to: "/uploadFicheEvalStageING",
    icon: {
      name: "cil-star",
      className: "text-warning",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/studentProfile",
    icon: {
      name: "cil-user",
      className: "text-info",
    },
  },
 /* {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewStudentPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  }
];

const menuItemsStudentMinusModifyFichePFE = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Tableau de Bord PFE",
    to: "/synopsisAndNews",
    icon: {
      name: "cil-lightbulb",
      className: "text-danger",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Rendez-Vous & Visites",
    to: "/meetingsAndVisits",
    icon: {
      name: "cil-bell",
      className: "text-warning",
    },
  },
  {
	    _tag: "CSidebarNavDropdown",
	    name: "Gérer Convention",
	    icon: {
	      name: "cil-bookmark",
	      className: "text-warning",
	    },
	    _children: [
	      {
	        _tag: "CSidebarNavItem",
	        name: "Demander Convention",
	        to: "/submitAgreement",
	      },
	      {
	        _tag: "CSidebarNavItem",
	        name: "Déposer Convention Signée",
	        to: "/uploadSignedAgreement",
	      },
	    ],
	  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Gérer Plan Travail",
    route: "/fichePFE",
    icon: {
      name: "cil-notes",
      className: "text-warning",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Consulter Plan Travail",
        to: "/consultESPFile",
      },
      {
        _tag: "CSidebarNavItem",
        name: "Annuler Plan Travail",
        to: "/cancelESPFile",
      },
    ],
  },
  {
	    _tag: "CSidebarNavDropdown",
	    name: "Gérer Avenants",
	    icon: {
	      name: "cil-cursor",
	      className: "text-success",
	    },
	    _children: [
	      {
	        _tag: "CSidebarNavItem",
	        name: "Ajouter Avenant",
	        to: "/proceedEndorsement",
	      },
	      {
	        _tag: "CSidebarNavItem",
	        name: "Déposer Avenant Signé",
	        to: "/uploadSignedEndorsement",
	      },
	    ],
	  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Livrables",
    route: "/fichePFE",
    icon: {
      name: "cil-cloud-download",
      className: "text-danger",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Déposer Journaux",
        to: "/uploadNewspaper"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Déposer Bilans",
        to: "/uploadBalanceSheet"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Déposer Rapports",
        to: "/uploadReport"
      }
    ],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mes Documents",
    to: "/myDocuments",
    icon: {
      name: "cil-layers",
      className: "text-light"
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Stage Ingénieur",
    to: "/uploadFicheEvalStageING",
    icon: {
      name: "cil-star",
      className: "text-warning",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/studentProfile",
    icon: {
      name: "cil-user",
      className: "text-info",
    },
  },
  /*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewStudentPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  }
];

/******************************************* RSS Management *******************************************/
const menuItemsResponsableServiceStageSecurePassword = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/responsableServiceStageProfile",
    icon: {
      name: "cil-user",
      className: "text-warning",
    }
  },
 /* {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewResponsableServiceStagePassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];


/******************************************* PC* Management *******************************************/
const menuItemsPedagogicalCoordinatorSecurePassword = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/pedagogicalCoordinatorProfile",
    icon: {
      name: "cil-user",
      className: "text-warning",
    }
  },
 /* {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewPedagogicalCoordinatorPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

const menuItemsPedagogicalCoordinatorManagement = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "État Encadrements Enseignant",
    to: "/etatEncadrement",
    icon: {
      name: "cil-graph",
      className: "text-info"
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Affecter Encadrants Académiques",
    to: "/affectAcademicEncadrant",
    icon: {
      name: "cil-bookmark",
      className: "text-info"
    }
  },
  {
    _tag: "CSidebarNavDropdown",
    name: "État Expertises Enseignant",
    to: "/",
    icon: {
      name: "cil-graph",
      className: "text-danger"
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "État Global",
        to: "/etatExpertiseGlobal"
      },
      {
        _tag: "CSidebarNavItem",
        name: "État par Expertise",
        to: "/etatExpertiseByUnit"
      }
    ]
  },
  {
    _tag: "CSidebarNavItem",
    name: "Affecter Experts",
    to: "/affectExpert",
    icon: {
      name: "cil-bookmark",
      className: "text-danger"
    }
  },
  {
      _tag: "CSidebarNavDropdown",
      name: "Quotas Enseignants",
      to: "/",
      icon: {
        name: "cil-graph",
        className: "text-info"
      },
      _children: [
        {
          _tag: "CSidebarNavItem",
          name: "Encadrements/Expertises",
          to: "/etatEncadrementAndExpertise"
        },
        {
          _tag: "CSidebarNavItem",
          name: "Presidence/Membre Jury",
          to: "/etatPresidenceAndMembreJurySTN"
        }
      ]
  },
  {
    _tag: "CSidebarNavItem",
	name: "Fiche Dépôt Coordinateur",
	to: "/FicheDepotPFEPCE",
	icon: {
	  name: "cil-layers",
	  className: "text-success",
	}
  },
  {
    _tag: "CSidebarNavItem",
    name: "Dépôts Validés",
    to: "/allValidatedDepot",
    icon: {
      name: "cil-layers",
      className: "text-light"
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Soutenances Notifiées",
    to: "/SoutenancesNotifiéesForCPS",
    icon: {
      name: "cil-calendar",
      className: "text-primary"
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/pedagogicalCoordinatorProfile",
    icon: {
      name: "cil-user",
      className: "text-warning",
    }
  },
/*  {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewPedagogicalCoordinatorPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

/******************************************** -PC & -AE Management *******************************************/
const menuItemsTeacherNotPCAEUrgentVersion = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Rapport Président",
    to: "/ListRapportPresident",
    icon: "cil-notes",
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/teacherProfile",
    icon: {
      name: "cil-user",
      className: "text-danger",
    },
  },
 /* {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewTeacherPassword",
    icon: {
      name: "cil-settings",
      className: "text-info",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];


/****************************************** PC & AE Management ****************************************/
const menuItemsTeacherAEandPCUrgentVersion = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Fiches Dépot",
    to: "/",
    icon: {
      name: "cil-spreadsheet",
      className: "text-warning",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Coordinateur Pédagogique",
        to: "/FicheDepotPFEPCE"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Encadrant Académique",
        to: "/FicheDepotPFEPE"
      }
    ]
  },
  {
    _tag: "CSidebarNavItem",
    name: "Planning des visites",
    to: "/VisitIntern",
    icon: "cil-home",
  },
  {
    _tag: "CSidebarNavItem",
    name: "Rapport Président",
    to: "/ListRapportPresident",
    icon: {
      name: "cil-notes",
      className: "text-primary"
    }
  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Gestion Fiches PFE",
    to: "/",
    icon: {
      name: "cil-bookmark",
      className: "text-success",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Gérer Fiches PFE",
        to: "/FichePFEManagement"
      },
      {
        _tag: "CSidebarNavItem",
        name: "État de Validation",
        to: "/EtatValidationFiche"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Étudiants Sans Plan",
        to: "/EtudiantSansFiche"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Gérer Demandes Annulation",
        to: "/FichePFETraitement"
      },
    ]
  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Gestion Encadrement",
    to: "/",
    icon: {
      name: "cil-user",
      className: "text-dark",
    },
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Encadrants Étudiants",
        to: "/AffectationManage"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Encadrement Étudiants",
        to: "/StudentSupervision"
      },
      {
        _tag: "CSidebarNavItem",
        name: "Encadrement & Validation",
        to: "/EncadrementValidation"
      }
    ]
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/teacherProfile",
    icon: {
      name: "cil-user",
      className: "text-danger",
    },
  },
  /*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewTeacherPassword",
    icon: {
      name: "cil-settings",
      className: "text-info",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

/******************************************** AE Management *******************************************/
const menuItemsTeacherPESecurePassword = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/teacherProfile",
    icon: {
      name: "cil-user",
      className: "text-info",
    },
  },
/*  {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewTeacherPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

const menuItemsTeacherAEUrgentVersion = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Note Stage Ingénieur",
    to: "/markForEngineeringTrainingship",
    icon: {
      name: "cil-tags",
      className: "text-warning",
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Espace Encadrement",
    to: "/studentProgressStatus",
    icon: {
      name: "cil-bookmark",
      className: "text-info"
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Espace Expertise",
    to: "/studentsForExpertise",
    icon: {
      name: "cil-bookmark",
      className: "text-secondary"
    }
  },
  {
	_tag: "CSidebarNavItem",
	 name: "Espace Présidence Soutenance",
	 to: "/studentsForPresidenceSoutenance",
	 icon: {
	   name: "cil-star",
	   className: "text-warning"
	}
  },
  {
	  _tag: "CSidebarNavItem",
	  name: "Espace Membre Soutenance",
	  to: "/studentsForMembreSoutenance",
	  icon: {
	    name: "cil-bookmark",
	    className: "text-info"
	  }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Fiche Dépôt Encadrant",
    to: "/FicheDepotPFEPE",
    icon: {
      name: "cil-layers",
      className: "text-success",
    }
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/teacherProfile",
    icon: {
      name: "cil-user",
      className: "text-danger",
},
},
/*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewTeacherPassword",
    icon: {
      name: "cil-settings",
      className: "text-info",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

/******************************************** PC Management *******************************************/
const menuItemsTeacherPCESecurePassword = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/teacherProfile",
    icon: {
      name: "cil-user",
      className: "text-info",
    },
  },
 /* {
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewTeacherPassword",
    icon: {
      name: "cil-settings",
      className: "text-danger",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];

const menuItemsTeacherPCUrgentVersion = [
  {
    _tag: "CSidebarNavTitle",
    _children: ["Menu"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Fiche Dépôt",
    to: "/FicheDepotPFEPCE",
    icon: {
      name: "cil-spreadsheet",
      className: "text-warning",
    },
  },
  
  {
    _tag: "CSidebarNavItem",
    name: "Mon Profil",
    to: "/teacherProfile",
    icon: {
      name: "cil-user",
      className: "text-danger",
    },
  },
  /*{
    _tag: "CSidebarNavItem",
    name: "Changer Mot de Passe",
    to: "/renewTeacherPassword",
    icon: {
      name: "cil-settings",
      className: "text-info",
    },
  },*/
  {
    _tag: "CSidebarNavDivider",
    className: "m-2",
  },
];


// init2021
class SidebarChild extends Component {
  constructor(props) {

    super(props);
    this.state = {
      currentResponsableServiceStage: null,
      currentStudent: null,
      currentTeacher: null,
      fichePFEStatus: "",
      convention: {},
      jwtPwdSTU: "",
      jwtPwdRSS: "",
      jwtPwdTEA: "",
      jwtPwdPC: "",
      nbrStudentsToSTN: this.props.nbrStudentsToSTN,
      nbrStudentsPlanifiedSTN: this.props.nbrStudentsPlanifiedSTN,
      nbrStudentsNotifiedSTN: this.props.nbrStudentsNotifiedSTN,
      nbUploadedReports: this.props.nbUploadedReports,
      nbDemAnnulConvs: this.props.nbDemAnnulConvs,
      nbDepositedConvs: this.props.nbDepositedConvs,
      nbValidatedConvs: this.props.nbValidatedConvs,
      nbValidatedReports: this.props.nbValidatedReports,
      nbIncompletedReports: this.props.nbIncompletedReports,
      dateConvention: null,
      cancelFichePFEType: "",
      cancelFichePFEEtat: "",
      verifyAffectPEtoStudent: ""
    }



    // console.log('---------------------------------> BADGE 123 A: ');
    /*console.log('---------------------------------> BADGE 123 A: ' + this.state.loli);
    console.log('---------------------------------> BADGE 123 Z: ' + this.props.loli);
    console.log('---------------------------------> BADGE 123 E: ' + props.loli);
*/
    this.state.currentResponsableServiceStage =
        AuthService.getCurrentResponsableServiceStage();
    this.state.currentStudent = AuthService.getCurrentStudent();
    this.state.currentTeacher = AuthService.getCurrentTeacher();

    //console.log('####################################################### PICA-ID: ' + currentStudent.id);



    // Get Title Of Plan Travail
    if (currentStudent) {

      var requestVPES = new XMLHttpRequest();
      requestVPES.open(
          "GET",
          API_URL_STU + "verifyAffectPEtoStudent/" + currentStudent.id,
          false
      );
      requestVPES.send(null);
      this.state.verifyAffectPEtoStudent = requestVPES.responseText;
      // console.log('------------verifyAffectPEtoStudent--****------->: ' + this.state.verifyAffectPEtoStudent);

      var requestSJP = new XMLHttpRequest();
      requestSJP.open(
          "GET",
          API_URL_MESP + "studentJWTPWD/" + currentStudent.id,
          false
      ); // return axios.get(API_URL_MESP + 'user/' + code);
      requestSJP.send(null);
      this.state.jwtPwdSTU = requestSJP.responseText;
      // console.log('------------init--****----------- convention entity: ' + this.state.jwtPwdSTU);

      var requestfp = new XMLHttpRequest();
      requestfp.open(
          "GET",
          API_URL_STU + "fichePFEStatus/" + currentStudent.id,
          false
      );
      requestfp.send(null);

      this.state.fichePFEStatus = requestfp.responseText;
      // console.log('-------------init------azerty------ convention id: ' + this.state.fichePFEStatus);

      // console.log('#########################*****menuItemsStudent*******############################## PICA-TITLE: ' + fichePFE.etat);

      var requestb = new XMLHttpRequest();
      requestb.open(
          "GET",
          API_URL_MESP + "conventionDto/" + currentStudent.id,
          false
      ); // return axios.get(API_URL_MESP + 'user/' + code);
      requestb.send(null);
      this.state.convention = JSON.parse(requestb.responseText);
      // console.log('------------init------------- convention entity: ' + this.state.convention.nomSociete);

      this.state.dateConvention = this.state.convention.dateConvention;

      var requesttrtfp = new XMLHttpRequest();
      requesttrtfp.open(
          "GET",
          API_URL_STU + "traitementFichePFETypeEtat/" + this.state.currentStudent.id,
          false
      ); // return axios.get(API_URL + 'user/' + code);
      requesttrtfp.send(null);

      let result = requesttrtfp.responseText;
      if(result !== "")
      {
        let treatFichePFETypeEtat = JSON.parse(requesttrtfp.responseText);
        this.state.cancelFichePFEType = treatFichePFETypeEtat.typeTrtFiche;
        this.state.cancelFichePFEEtat = treatFichePFETypeEtat.etatTrt;

        // console.log('############CANCEL######' + this.state.cancelFichePFEType + " - " + this.state.cancelFichePFEEtat);
      }

    }



    if (currentResponsableServiceStage)
    {
      var requestRJP = new XMLHttpRequest();
      requestRJP.open(
          "GET",
          API_URL_MESP + "responsableServiceStageJWTPWD/" + currentResponsableServiceStage.id,
          false
      ); // return axios.get(API_URL_MESP + 'user/' + code);
      requestRJP.send(null);
      this.state.jwtPwdRSS = requestRJP.responseText;


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

      var requestDAC = new XMLHttpRequest();
      requestDAC.open("GET", API_URL_STU + "nbrDemandesAnnulationConventionNotTreated", false);
      requestDAC.send(null);
      this.state.nbDemAnnulConvs = requestDAC.responseText;

      var requestDC = new XMLHttpRequest();
      requestDC.open("GET", API_URL_STU + "nbrDepositedConvention", false);
      requestDC.send(null);
      this.state.nbDepositedConvs = requestDC.responseText;

      var requestVC = new XMLHttpRequest();
      requestVC.open("GET", API_URL_STU + "nbrValidatedConvention", false);
      requestVC.send(null);
      this.state.nbValidatedConvs = requestVC.responseText;
      
      var requestAS = new XMLHttpRequest();
      requestAS.open("GET", API_URL_MESP + "nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id, false);
      requestAS.send(null);
      this.state.nbrStudentsToSTN = requestAS.responseText;

      // console.log('---------------------------------> BADGE 123: ' + props.nbrStudentsToSTN);
      // this.state.nbrStudentsToSTN = sessionStorage.getItem("nas");


      var requestSDS = new XMLHttpRequest();
      requestSDS.open("GET", API_URL_MESP + "nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id, false);
      requestSDS.send(null);
      this.state.nbrStudentsNotifiedSTN = requestSDS.responseText;

      // console.log("-CURRENT--------LOL-1-----------------> Sizeee: " + this.state.nbrStudentsToSTN);
      // console.log("-CURRENT--------LOL-2-----------------> Sizeee: " + this.state.nbrStudentsNotifiedSTN);


      var requestPS = new XMLHttpRequest();
      requestPS.open("GET", API_URL_MESP + "nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id, false);
      requestPS.send(null);
      this.state.nbrStudentsPlanifiedSTN = requestPS.responseText;



      /*var requestRStat = new XMLHttpRequest();
      requestRStat.open(
        "GET",
        API_URL_MESP + "responsableServiceStage/depotStat/" + currentResponsableServiceStage.id,
        false
      ); // return axios.get(API_URL_MESP + 'user/' + code);
      requestRStat.send(null);
      let depotStat = JSON.parse(requestRStat.responseText);

      this.state.nbrUplDepot= depotStat[0].nbrStudentsWithUploadedDepot;
      this.state.nbrValDepot= depotStat[0].nbrStudentsWithValidatedDepot;
      this.state.nbrIncDepot= depotStat[0].nbrStudentsWithRefusedDepot;*/

      /*if(sessionStorage.getItem("nas") !== null)
      {
        this.state.nbrStudentsNotifiedSTN= sessionStorage.getItem("nas");
      }*/

      // console.log("-CURRENT--------ITEM------------------> Responsable Service Stage: " + sessionStorage.getItem("nas"));


    }

    if (currentPedagogicalCoordinator)
    {
      var requestPC = new XMLHttpRequest();
      requestPC.open(
          "GET",
          API_URL_MESP + "pedagogicalCoordinatorJWTPWD/" + currentPedagogicalCoordinator.id,
          false
      ); // return axios.get(API_URL_MESP + 'user/' + code);
      requestPC.send(null);
      this.state.jwtPwdPC = requestPC.responseText;
    }

    if (currentTeacher !== null)  //&& currentTeacher.codeOption === null
    {

      var requestTEA = new XMLHttpRequest();
      requestTEA.open("GET", API_URL_MESP + "teacherJWTPWD/" + currentTeacher.id, false);
      requestTEA.send(null);
      this.state.jwtPwdTEA = requestTEA.responseText;

      // console.log("-CURRENT-------------------------->  JWT: " + this.state.jwtPwdTEA);

      /*if(this.state.jwtPwdTEA !== "")
      {
        if (currentTeacher.codeOptions === null)
        {
          // console.log("-CURRENT--------------------------> PE");
        }

        if (currentTeacher.codeOptions !== null)
        {
          // console.log("-CURRENT--------------------------> RS + PE");
        }
      }*/

    }

  }

  componentWillReceiveProps(nextProps)
  {
    this.setState({
      nbrStudentsToSTN: nextProps.nbrStudentsToSTN,
      nbrStudentsNotifiedSTN: nextProps.nbrStudentsNotifiedSTN,
      nbrStudentsPlanifiedSTN: nextProps.nbrStudentsPlanifiedSTN,
      nbUploadedReports: nextProps.nbUploadedReports,
      nbDemAnnulConvs: nextProps.nbDemAnnulConvs,
      nbDepositedConvs: nextProps.nbDepositedConvs,
      nbValidatedConvs: nextProps.nbValidatedConvs,
      nbValidatedReports: nextProps.nbValidatedReports,
      nbIncompletedReports: nextProps.nbIncompletedReports
    });
  }


  render() {
    const {
      currentResponsableServiceStage,
      currentStudent,
      currentTeacher,
      jwtPwdSTU,
      jwtPwdRSS,
      jwtPwdTEA,
      jwtPwdPC,
      nbrStudentsToSTN,
      nbrStudentsNotifiedSTN,
      nbrStudentsPlanifiedSTN,
      nbUploadedReports,
      nbDemAnnulConvs,
      nbDepositedConvs,
      nbValidatedConvs,
      nbValidatedReports,
      nbIncompletedReports,
      fichePFEStatus,
      dateConvention,
      cancelFichePFEType,
      cancelFichePFEEtat,
      verifyAffectPEtoStudent
    } = this.state;

    const menuItemsResponsableServiceStage = [
      {
        _tag: "CSidebarNavTitle",
        _children: ["Menu Responsable Service Stage"],
      },
      {
        _tag: "CSidebarNavItem",
        name: "Dashboard",
        to: "/homeResponsableServiceStage",
        icon: {
          name: "cil-graph",
          className: "text-danger",
        },
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "État Encadrements / Expertises",
        to: "/",
        icon: {
          name: "cil-cursor",
          className: "text-info"
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "État Encadrement Global",
            to: "/etatEncadrement"
          },
          {
            _tag: "CSidebarNavItem",
            name: "État Encadrement par Département",
            to: "/encadrementsByDepartement"
          },
          {
            _tag: "CSidebarNavItem",
            name: "État Expertises Enseignant",
            to: "/etatExpertiseGlobal"
          }
        ]
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Quotas Enseignants",
        to: "/",
        icon: {
          name: "cil-star",
          className: "text-warning"
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Encadrements / Expertises",
            to: "/etatEncadrementAndExpertise"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Présidence / Membre Jury",
            to: "/etatPresidenceAndMembreJurySTN"
          }
        ]
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Gérer Conventions",
        to: "/",
        icon: {
          name: "cil-bookmark",
          className: "text-primary",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Conventions Validées",
            to: "/ConventionsValideesManagement",
            badge: {
              color: 'success',
              text: nbValidatedConvs,
            },
            shape: "rounded-pill"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Conventions Non Traitées",
            to: "/ConventionsManagement",
            badge: {
              color: 'danger',
              text: nbDepositedConvs,
            },
            shape: "rounded-pill"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Demandes Annulation",
            to: "/ConventionsDemandesAnnulation",
            badge: {
              color: 'warning',
              text: nbDemAnnulConvs,
            },
            shape: "rounded-pill"
          }
        ],
      },
      {
          _tag: "CSidebarNavItem",
          name: "Gérer Avenants",
          to: "/AvenantsManagement",
          icon: {
            name: "cil-cursor",
            className: "text-danger"
          }
        },
      {
        _tag: "CSidebarNavDropdown",
        name: "Gérer Dépôts",
        to: "/",
        icon: {
          name: "cil-notes",
          className: "text-success",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Dépôts Non Traités",
            to: "/RapportManage",
            badge: {
              color: 'danger',
              text: nbUploadedReports,
            },
            shape: "rounded-pill"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Dépôts Validés",
            to: "/ValidatedReports",
            badge: {
              color: 'success',
              text: nbValidatedReports,
            },
          },
          {
            _tag: "CSidebarNavItem",
            name: "Dépôts Incomplets",
            to: "/IncompletedReports",
            badge: {
              color: 'info',
              text: nbIncompletedReports,
            },
          }
        ],
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Planification Soutenances",
        to: "/",
        icon: {
          name: "cil-calendar",
          className: "text-warning",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Planifier Soutenances",
            to: "/planifySoutenance",
            badge: {
              color: 'danger',
              text: nbrStudentsToSTN
            }
          },
          {
            _tag: "CSidebarNavItem",
            name: "Soutenances Planifiées",
            to: "/soutenancesPlanifiées",
            badge: {
              color: 'warning',
              text: nbrStudentsPlanifiedSTN
            }
          },
          {
            _tag: "CSidebarNavItem",
            name: "Soutenances Notifiées",
            to: "/soutenancesNotifiées",
            badge: {
              color: 'success',
              text: nbrStudentsNotifiedSTN
            }
          }
        ]
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Gérer Utilisateurs",
        icon: "cil-magnifying-glass",
        className: "text-danger",
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Étudiants",
            to: "/StudentManagement",
          },
          {
            _tag: "CSidebarNavItem",
            name: "Enseignants",
            to: "/TeacherManagement",
          },
          {
            _tag: "CSidebarNavItem",
            name: "Responsables Services Stages",
            to: "/RespServiceStgManagement",
          },
          {
            _tag: "CSidebarNavItem",
            name: "Chefs Départements / CPS",
            to: "/ChefCoordManagement",
          },
        ],
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Configuration",
        route: "/Configuration",
        icon: {
          name: "cil-puzzle",
          className: "text-success",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Session",
            to: "/SessionManagement",
          },
          {
            _tag: "CSidebarNavItem",
            name: "Secteur Entreprise",
            to: "/SecteurManagement",
          }
        ],
      },
      {
        _tag: "CSidebarNavItem",
        name: "Mon Profil",
        to: "/responsableServiceStageProfile",
        icon: {
          name: "cil-user",
          className: "text-warning",
        }
      },
     /* {
        _tag: "CSidebarNavItem",
        name: "Changer Mot de Passe",
        to: "/renewResponsableServiceStagePassword",
        icon: {
          name: "cil-settings",
          className: "text-info",
        },
      },*/
    ];

    const menuItemsResponsableServiceStageSub = [
      {
        _tag: "CSidebarNavTitle",
        _children: ["Menu Responsable Service Stage"],
      },
      {
        _tag: "CSidebarNavItem",
        name: "Dashboard",
        to: "/homeResponsableServiceStage",
        icon: {
          name: "cil-graph",
          className: "text-danger",
        },
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Gérer Conventions rrr",
        to: "/",
        icon: {
          name: "cil-notes",
          className: "text-success",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Conventions Validées",
            to: "/ConventionsValideesManagement",
            badge: {
              color: 'success',
              text: nbValidatedConvs,
            },
            shape: "rounded-pill"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Conventions Non Traitées",
            to: "/ConventionsManagement"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Demandes Annulation",
            to: "/ConventionsDemandesAnnulation"
          }
        ],
      },
      {
        _tag: "CSidebarNavItem",
        name: "Gérer Avenants",
        to: "/AvenantsManagement",
        icon: {
          name: "cil-cursor",
          className: "text-primary"
        }
      },
      {
        _tag: "CSidebarNavItem",
        name: "Dépôts Validés",
        to: "/ValidatedReports",
        icon: {
          name: "cil-layers",
          className: "text-light"
        }
      },
      {
        _tag: "CSidebarNavItem",
        name: "Soutenances Notifiées",
        to: "/soutenancesNotifiées",
        icon: {
          name: "cil-calendar",
          className: "text-success"
        }
      },
      {
        _tag: "CSidebarNavItem",
        name: "Mon Profil",
        to: "/responsableServiceStageProfile",
        icon: {
          name: "cil-user",
          className: "text-warning",
        }
      },
    /*  {
        _tag: "CSidebarNavItem",
        name: "Changer Mot de Passe",
        to: "/renewResponsableServiceStagePassword",
        icon: {
          name: "cil-settings",
          className: "text-info",
        },
      },*/
    ];

    const menuItemsResponsableServiceStageEMGC = [
      {
        _tag: "CSidebarNavTitle",
        _children: ["Menu Responsable Service Stage EM/GC"],
      },
      {
        _tag: "CSidebarNavItem",
        name: "Dashboard",
        to: "/homeResponsableServiceStage",
        icon: {
          name: "cil-graph",
          className: "text-danger",
        },
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Gérer Conventions",
        to: "/",
        icon: {
          name: "cil-notes",
          className: "text-success",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Conventions Validées",
            to: "/ConventionsValideesManagement",
            badge: {
              color: 'success',
              text: nbValidatedConvs,
            },
            shape: "rounded-pill"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Conventions Non Traitées",
            to: "/ConventionsManagement"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Demandes Annulation",
            to: "/ConventionsDemandesAnnulation"
          }
        ],
      },
      {
        _tag: "CSidebarNavItem",
        name: "Gérer Avenants",
        to: "/AvenantsManagement",
        icon: {
          name: "cil-cursor",
          className: "text-primary"
        }
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Gérer Dépôts",
        to: "/",
        icon: {
          name: "cil-layers",
          className: "text-dark",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Dépôts Non Traités",
            to: "/RapportManage",
            badge: {
              color: 'danger',
              text: nbUploadedReports,
            },
            shape: "rounded-pill"
          },
          {
            _tag: "CSidebarNavItem",
            name: "Dépôts Validés",
            to: "/ValidatedReports",
            badge: {
              color: 'success',
              text: nbValidatedReports,
            },
          },
          {
            _tag: "CSidebarNavItem",
            name: "Dépôts Incomplets",
            to: "/IncompletedReports",
            badge: {
              color: 'info',
              text: nbIncompletedReports,
            },
          }
        ],
      },
      {
        _tag: "CSidebarNavDropdown",
        name: "Planification Soutenances",
        to: "/",
        icon: {
          name: "cil-calendar",
          className: "text-danger",
        },
        _children: [
          {
            _tag: "CSidebarNavItem",
            name: "Planifier Soutenances",
            to: "/planifySoutenance",
            badge: {
              color: 'danger',
              text: nbrStudentsToSTN
            }
          },
          {
            _tag: "CSidebarNavItem",
            name: "Soutenances Planifiées",
            to: "/soutenancesPlanifiées",
            badge: {
              color: 'warning',
              text: nbrStudentsPlanifiedSTN
            }
          },
          {
            _tag: "CSidebarNavItem",
            name: "Soutenances Notifiées",
            to: "/soutenancesNotifiées",
            badge: {
              color: 'success',
              text: nbrStudentsNotifiedSTN
            }
          }
        ]
      },
      {
        _tag: "CSidebarNavItem",
        name: "Mon Profil",
        to: "/responsableServiceStageProfile",
        icon: {
          name: "cil-user",
          className: "text-warning",
        }
      },
     /* {
        _tag: "CSidebarNavItem",
        name: "Changer Mot de Passe",
        to: "/renewResponsableServiceStagePassword",
        icon: {
          name: "cil-settings",
          className: "text-info",
        },
      },*/
    ];
    

    return (
        <div>
          <CSidebarNav activeStyles={{fontWeight: "bold", color: "pink"}}>
            {currentStudent !== null && jwtPwdSTU === "" && (
                <CCreateElement
                    items={menuItemsStudentSecurePassword}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {currentStudent !== null && jwtPwdSTU !== "" && verifyAffectPEtoStudent === "" && (
                <CCreateElement
                    items={menuItemsAlertStudentWithoutPedagogicalEncadrant}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {
              (
                  (currentStudent !== null && dateConvention === undefined && jwtPwdSTU !== "")  // -Convention -FichePFE
                  ||
                  (currentStudent !== null && dateConvention !== undefined && fichePFEStatus === "")  // +Convention -FichePFE
                  ||
                  (currentStudent !== null && fichePFEStatus !== "" && fichePFEStatus === "04")  // +FichePFE REFUSEE
                  ||
                  (currentStudent !== null && dateConvention !== undefined && fichePFEStatus === "")
                  ||
                  (currentStudent !== null && fichePFEStatus !== "" && fichePFEStatus === "05" && cancelFichePFEType === "12" && cancelFichePFEEtat === "02")  // +FichePFE ANNULEE-ANNULEE
              ) && verifyAffectPEtoStudent !== "" && (
                  <CCreateElement
                      items={menuItemsStudentSecondAccess}
                      components={{
                        CSidebarNavDivider,
                        CSidebarNavDropdown,
                        CSidebarNavItem,
                        CSidebarNavTitle,
                      }}
                  />
              )
            }

            {(
                (
                    (currentStudent !== null && fichePFEStatus !== "" && fichePFEStatus === "01")  // FichePFE : +SAUVEGARDEE -DEPOSEE
                    ||
                    (currentStudent !== null && fichePFEStatus !== "" && fichePFEStatus === "02")  // +FichePFE : just DEPOSEE
                    ||
                    (currentStudent !== null && fichePFEStatus !== "" && fichePFEStatus === "05" && cancelFichePFEType === "01" && cancelFichePFEEtat === "02")  // +FichePFE ANNULEE-MODIFIEE
                ) && verifyAffectPEtoStudent !== "" &&
                <CCreateElement
                    items={menuItemsStudentMinusAddFichePFE}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {
              (
                  currentStudent !== null &&
                  verifyAffectPEtoStudent !== "" &&
                  fichePFEStatus !== "" &&
                  (
                      fichePFEStatus === "03"
                      ||
                      fichePFEStatus === "06"
                      ||
                      fichePFEStatus === "07"
                      ||
                      fichePFEStatus === "08"
                  )
              )&& (
                  <CCreateElement
                      items={menuItemsStudentMinusModifyFichePFE}
                      components={{
                        CSidebarNavDivider,
                        CSidebarNavDropdown,
                        CSidebarNavItem,
                        CSidebarNavTitle,
                      }}
                  />
              )}





            {
              currentResponsableServiceStage !== null && jwtPwdRSS === "" && (
                  <CCreateElement
                      items={menuItemsResponsableServiceStageSecurePassword}
                      components={{
                        CSidebarNavDivider,
                        CSidebarNavDropdown,
                        CSidebarNavItem,
                        CSidebarNavTitle,
                      }}
                  />
              )}

            {
              currentResponsableServiceStage !== null && jwtPwdRSS !== "" && currentResponsableServiceStage.id === "SR-STG-IT" && (
                  <CCreateElement
                      items={menuItemsResponsableServiceStage}
                      components={{
                        CSidebarNavDivider,
                        CSidebarNavDropdown,
                        CSidebarNavItem,
                        CSidebarNavTitle,
                      }}
                  />
              )}

            {
              currentResponsableServiceStage !== null && jwtPwdRSS !== "" && currentResponsableServiceStage.id !== "SR-STG-IT" && currentResponsableServiceStage.id !== "SR-STG-EM" && currentResponsableServiceStage.id !== "SR-STG-GC" && (
                  <CCreateElement
                      items={menuItemsResponsableServiceStageSub}
                      components={{
                        CSidebarNavDivider,
                        CSidebarNavDropdown,
                        CSidebarNavItem,
                        CSidebarNavTitle,
                      }}
                  />
              )}

            {
              currentResponsableServiceStage !== null && jwtPwdRSS !== "" && (currentResponsableServiceStage.id === "SR-STG-EM" || currentResponsableServiceStage.id === "SR-STG-GC") && (
                  <CCreateElement
                      items={menuItemsResponsableServiceStageEMGC}
                      components={{
                        CSidebarNavDivider,
                        CSidebarNavDropdown,
                        CSidebarNavItem,
                        CSidebarNavTitle,
                      }}
                  />
              )}

            {currentPedagogicalCoordinator !== null && jwtPwdPC === "" && (
                <CCreateElement
                    items={menuItemsPedagogicalCoordinatorSecurePassword}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {currentPedagogicalCoordinator !== null && jwtPwdPC !== "" && (
                <CCreateElement
                    items={menuItemsPedagogicalCoordinatorManagement}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {currentTeacher !== null && currentTeacher.codeOptions === null && jwtPwdTEA === "" && (
                <CCreateElement
                    items={menuItemsTeacherPESecurePassword}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {currentTeacher !== null && currentTeacher.codeOptions !== null && jwtPwdTEA === "" && (
                <CCreateElement
                    items={menuItemsTeacherPCESecurePassword}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {currentTeacher !== null && currentTeacher.codeOptions === null && currentTeacher.nbrAcademicEncadrement === 0 && jwtPwdTEA !== "" && (
                <CCreateElement
                    items={menuItemsTeacherNotPCAEUrgentVersion}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

            {currentTeacher !== null && currentTeacher.nbrAcademicEncadrement !== 0 && jwtPwdTEA !== "" && (
                <CCreateElement
                    items={menuItemsTeacherAEUrgentVersion}
                    components={{
                      CSidebarNavDivider,
                      CSidebarNavDropdown,
                      CSidebarNavItem,
                      CSidebarNavTitle,
                    }}
                />
            )}

          </CSidebarNav>
        </div>
    );
  }
}

const SBChild = connect()(SidebarChild);

const TheSidebar = (props) => {
  const dispatch = useDispatch();
  const show = useSelector((state) => state.changeState.sidebarShow);

  const passParam1 = props.data1;
  const passParam2 = props.data2;
  const passParam3 = props.data3;

  const passParamu = props.dataUR;
  const passParamv = props.dataVR;
  const passParami = props.dataIR;

  const passParamDAC = props.dataDAC;
  const passParamDC = props.dataDC;
  const passParamVC = props.dataVC;

  return (
      <CSidebar style={{ background: "#660000" }}
                show={show}
                onShowChange={(val) => dispatch({ type: "set", sidebarShow: val })}
      >
        <CSidebarBrand >
          <CRow>
            <CRow>
              <CCol md="12">
                <center>
                  <img
                      src={esprit}
                      alt="logo"
                      title="Welcome to Esprit"
                      height={35}
                      width={50}/>
                </center>
              </CCol>
            </CRow>
            &nbsp;&nbsp; Gestion des PFE
          </CRow>
        </CSidebarBrand>
        <SBChild  nbrStudentsToSTN={passParam1} nbrStudentsPlanifiedSTN={passParam3} nbrStudentsNotifiedSTN={passParam2}
                  nbUploadedReports={passParamu} nbValidatedReports={passParamv} nbIncompletedReports={passParami}
                  nbDemAnnulConvs={passParamDAC} nbDepositedConvs={passParamDC} nbValidatedConvs={passParamVC}/>
      </CSidebar>
  );
};

//
export default React.memo(TheSidebar);
