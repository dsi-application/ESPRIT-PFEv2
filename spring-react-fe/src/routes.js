import React from "react";
import AffectAcademicEncadrant from "./views/components/pedagogicalCoordinator/affectAcademicEncadrant.component";
import AffectExpert from "./views/components/pedagogicalCoordinator/affectExpert.component";

import EtatEncadrement from "./views/components/pedagogicalCoordinator/etatEncadrement.component";
import AllValidatedDepot from "./views/components/pedagogicalCoordinator/ValidatedReportsForCPS";
import EtatExpertiseGlobal from "./views/components/pedagogicalCoordinator/etatExpertiseGlobal.component";
import EtatExpertiseByUnit from "./views/components/pedagogicalCoordinator/etatExpertiseByUnit.component";
import EncadrementsByDepartementComponent from "./views/components/responsableServiceStage/encadrementsByDepartement.component";

import EtatEcadrementAndExpertise
  from "./views/components/pedagogicalCoordinator/etatEncadrementAndExpertise.component";

import EtatPresidenceAndMembreJurySTN
  from "./views/components/pedagogicalCoordinator/etatPresidenceAndMembreJurySTN.component";

const StudentProfile = React.lazy(() =>
  import("./views/components/student/studentProfile.component")
);

const MeetingsAndVisits = React.lazy(() =>
    import("./views/components/student/meetingsAndVisits.component")
);

const ModifyAgreementByRSS = React.lazy(() =>
	import("./views/InternshipServices/modifyAgreementByRSS.component")
);

const SecureStudentPassword = React.lazy(() =>
  import("./views/components/student/secureStudentPassword.component")
);
/*const RenewStudentPassword = React.lazy(() =>
  import("./views/components/student/renewStudentPassword.component")
);*/

const PedagogicalCoordinatorProfile = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/pedagogicalCoordinatorProfile.component")
);
const SecurePedagogicalCoordinatorPassword = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/securePedagogicalCoordinatorPassword.component")
);
/*const RenewPedagogicalCoordinatorPassword = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/renewPedagogicalCoordinatorPassword.component")
);*/

const FicheDepotPFEPE = React.lazy(() =>
  import("./views/components/teacher/ficheDepotPFEPE.component")
);

const MarkForEngineeringTrainingship = React.lazy(() =>
    import("./views/components/teacher/markForEngineeringTrainingship.component")
);

const StudentProgressStatus = React.lazy(() =>
    import("./views/components/teacher/studentProgressStatus.component")
);

const StudentsForExpertise = React.lazy(() =>
    import("./views/components/teacher/studentsForExpertise.component")
);

const StudentsForMembreSoutenance = React.lazy(() =>
    import("./views/components/teacher/studentsForMembreSoutenance.component")
);

const StudentsForPresidenceSoutenance = React.lazy(() =>
    import("./views/components/teacher/studentsForPresidenceSoutenance.component")
);

const MyStudentTimeline = React.lazy(() =>
    import("./views/components/teacher/myStudentTimeline")
);

const FicheDepotPFEPCE = React.lazy(() =>
  import("./views/components/pedagogicalCoordinator/ficheDepotPFEPCE.component")
);

const ResponsableServiceStageProfile = React.lazy(() =>
  import(
    "./views/components/responsableServiceStage/responsableServiceStageProfile.component"
    )
);
const SecureResponsableServiceStagePassword = React.lazy(() =>
  import(
    "./views/components/responsableServiceStage/secureResponsableServiceStagePassword.component"
    )
);
/*const RenewResponsableServiceStagePassword = React.lazy(() =>
  import(
    "./views/components/responsableServiceStage/renewResponsableServiceStagePassword.component"
    )
);*/

const TeacherProfile = React.lazy(() =>
  import("./views/components/teacher/teacherProfile.component")
);
const SecureTeacherPassword = React.lazy(() =>
  import("./views/components/teacher/secureTeacherPassword.component")
);
/*const RenewTeacherPassword = React.lazy(() =>
  import("./views/components/teacher/renewTeacherPassword.component")
);*/

const AddESPFile = React.lazy(() =>
  import("./views/components/student/addESPFile.component")
);
const ConsultESPFile = React.lazy(() =>
  import("./views/components/student/consultESPFile")
);
const ModifyESPFile = React.lazy(() =>
  import("./views/components/student/modifyESPFile.component")
);

const CancelESPFile = React.lazy(() =>
    import("./views/components/student/cancelESPFile")
);

const SubmitAgreement = React.lazy(() =>
  import("./views/components/student/submitAgreement.component")
);

const UploadSignedAgreement = React.lazy(() =>
    import("./views/components/student/uploadSignedAgreement.component")
);

const UploadSignedEndorsement = React.lazy(() =>
    import("./views/components/student/uploadSignedEndorsement.component")
);

const ProceedEndorsement = React.lazy(() =>
  import("./views/components/student/proceedEndorsement.component")
);

const UploadBalanceSheet = React.lazy(() =>
  import("./views/components/student/uploadBalanceSheet.component")
);
const UploadNewspaper = React.lazy(() =>
  import("./views/components/student/uploadNewspaper.component")
);
const UploadReport = React.lazy(() =>
  import("./views/components/student/uploadReport.component")
);

const MyDocuments = React.lazy(() =>
    import("./views/components/student/myDocuments")
);

const StudentTimeline = React.lazy(() =>
    import("./views/components/student/studentTimeline")
);

const StudentTimeline2 = React.lazy(() =>
    import("./views/components/student/studentTimeline2")
);

const SynopsisAndNews = React.lazy(() =>
    import("./views/components/student/SynopsisAndNews")
);

const PlanifySoutenance = React.lazy(() => import('./views/components/student/planifySoutenance.component'));

const SoutenancesPlanifi??es = React.lazy(() => import('./views/components/student/soutenancesPlanifi??es.component'));

const SoutenancesNotifi??es = React.lazy(() => import('./views/components/student/soutenancesNotifi??es.component'));

const SoutenancesNotifi??esForCPS = React.lazy(() => import('./views/components/pedagogicalCoordinator/soutenancesNotifi??esForCPS.component'));

const HomeResponsableServiceStage = React.lazy(() =>
  import("./views/components/responsableServiceStage/homeResponsableServiceStage")
);

const StudentSupervision = React.lazy(() =>
  import("./views/Monotoring/StudentSupervision")
);
const FichePFEDetails = React.lazy(() =>
  import("./views/Monotoring/FichePFEDetails")
);
const AddEvaluation = React.lazy(() =>
  import("./views/Monotoring/AddEvaluation")
);
const UpdateEvaluation = React.lazy(() =>
  import("./views/Monotoring/UpdateEvaluation")
);
const VisitIntern = React.lazy(() =>
  import("./views/Monotoring/VisiteIntern/VisitIntern")
);
const UpdateVisit = React.lazy(() =>
  import("./views/Monotoring/VisiteIntern/UpdateVisit")
);
const UpdateVisitDate = React.lazy(() =>
  import("./views/Monotoring/VisiteIntern/updateVisitDate")
);
const ConventionsManage = React.lazy(() =>
  import("./views/InternshipServices/ConventionsManage")
);
const ConventionsDemandesAnnulation = React.lazy(() =>
    import("./views/InternshipServices/ConventionsDemandesAnnulation")
);
const AvenantsManage = React.lazy(() =>
  import("./views/InternshipServices/AvenantsManage")
);
const ConventionsValideesManage = React.lazy(() =>
import("./views/InternshipServices/ConventionsValideesManage")
);
const ConventionDetails = React.lazy(() =>
  import("./views/InternshipServices/ConventionDetails")
);
const DemandeAnnulationConventionDetails = React.lazy(() =>
    import("./views/InternshipServices/DemandeAnnulationConventionDetails")
);
const AvenantDetails = React.lazy(() =>
  import("./views/InternshipServices/AvenantDetails")
);
const TRTFichePFE = React.lazy(() =>
  import("./views/InternshipManager/TRTFichePFE")
);
const FichePFEManagement = React.lazy(() =>
  import("./views/InternshipManager/FichePFEManagement")
);
const FichePFE = React.lazy(() => import("./views/InternshipManager/FichePFE"));
const RequestDetails = React.lazy(() =>
  import("./views/InternshipManager/RequestDetails")
);
const UpdateRDV = React.lazy(() =>
  import("./views/Monotoring/RDV_SUIVI/UpdateRDV")
);
const UpdateStateOb = React.lazy(() =>
  import("./views/Monotoring/RDV_SUIVI/UpdateStateOb")
);
const RapportManage = React.lazy(() =>
  import("./views/InternshipServices/rapports/RapportManage")
);
const ValidatedReports = React.lazy(() =>
  import("./views/InternshipServices/rapports/ValidatedReports")
);
const IncompletedReports = React.lazy(() =>
  import("./views/InternshipServices/rapports/IncompletedReports")
);
const AffectationManage = React.lazy(() =>
  import("./views/InternshipManager/AffectationManage")
);
const affectAcademicEncadrant = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/affectAcademicEncadrant.component")
);
const etatEncadrement = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/etatEncadrement.component")
);
const etatExpertiseGlobal = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/etatExpertiseGlobal.component")
);
const etatExpertiseByUnit = React.lazy(() =>
    import("./views/components/pedagogicalCoordinator/etatExpertiseByUnit.component")
);
const EncadrantPeda = React.lazy(() =>
  import("./views/InternshipManager/EncadrantPeda")
);
const HomeService = React.lazy(() =>
  import("./views/InternshipServices/HomeService")
);
const HomeManager = React.lazy(() =>
  import("./views/InternshipManager/HomeManager")
);
const TeacherHome = React.lazy(() => import("./views/Monotoring/TeacherHome"));
const SecteurManagement = React.lazy(() =>
  import("./views/InternshipServices/secteur/SecteurManagement")
);
const SessionManagement = React.lazy(() =>
  import("./views/InternshipServices/session/SessionManagement")
);
const StudentManagement = React.lazy(() =>
import("./views/InternshipServices/users/StudentManagement")
);
const RespServiceStgManagement = React.lazy(() =>
import("./views/InternshipServices/users/RespServiceStgManagement")
);
const ChefCoordManagement = React.lazy(() =>
import("./views/InternshipServices/users/ChefCoordManagement")
);
const TeacherManagement = React.lazy(() =>
import("./views/InternshipServices/users/TeacherManagement")
);
const ListRapportPresident = React.lazy(() =>
  import("./views/Monotoring/Soutenance/ListRapportPresident")
);
const EtudiantSansFiche = React.lazy(() =>
  import("./views/InternshipManager/EtudiantSansFiche")
);
const EncadrementValidation = React.lazy(() =>
  import("./views/InternshipManager/EncadrementValidation")
);
const EtatValidationFiche = React.lazy(() =>
  import("./views/InternshipManager/EtatValidationFiche")
);
const DepotDossierRapport = React.lazy(() =>
  import("./views/InternshipServices/rapports/DepotDossierRapport")
);
const RechercheFichePFEAvance = React.lazy(() =>
  import("./views/InternshipServices/RechercheFichePFEAvance")
);
const PFEEnCours = React.lazy(() =>
  import("./views/InternshipServices/PFEEnCours")
);

const UploadFicheEvalStageING = React.lazy(() =>
  import("./views/components/student/uploadFicheEvalStageING.component")
);

const routes = [
  {path: "/", exact: true, name: ""},

  {
    path: "/synopsisAndNews",
    exact: true,
    name: "Tableau de Bord PFE",
    component: SynopsisAndNews,
  },
  {
    path: "/studentProfile",
    exact: true,
    name: "Profil ??tudiant",
    component: StudentProfile,
  },
  {
    path: "/meetingsAndVisits",
    exact: true,
    name: "Rendez-Vous & Visites",
    component: MeetingsAndVisits,
  },
  {
	path: "/modifyAgreementByRSS",
	exact: true,
	name: "ModifyAgreementByRSS",
	component: ModifyAgreementByRSS,
  },
  {
    path: "/secureStudentPassword",
    exact: true,
    name: "S??curiser mon Compte",
    component: SecureStudentPassword,
  },
  /*{
    path: "/renewStudentPassword",
    exact: true,
    name: "Changer Mot de Passe",
    component: RenewStudentPassword,
  },*/

  {
    path: "/responsableServiceStageProfile",
    exact: true,
    name: "Profil Responsable Service Stage",
    component: ResponsableServiceStageProfile,
  },
  {
    path: "/secureResponsableServiceStagePassword",
    exact: true,
    name: "S??curiser mon Compte",
    component: SecureResponsableServiceStagePassword,
  },
  /*{
    path: "/renewResponsableServiceStagePassword",
    exact: true,
    name: "Changer Mot de Passe",
    component: RenewResponsableServiceStagePassword,
  },*/

  {
    path: "/teacherProfile",
    exact: true,
    name: "Mon Profil",
    component: TeacherProfile,
  },
  {
    path: "/secureTeacherPassword",
    exact: true,
    name: "S??curiser mon Compte",
    component: SecureTeacherPassword,
  },
  /*{
    path: "/renewTeacherPassword",
    exact: true,
    name: "Changer Mot de Passe",
    component: RenewTeacherPassword,
  },*/

  {
    path: "/pedagogicalCoordinatorProfile",
    exact: true,
    name: "Mon Profil",
    component: PedagogicalCoordinatorProfile,
  },
  {
    path: "/securePedagogicalCoordinatorPassword",
    exact: true,
    name: "S??curiser mon Compte",
    component: SecurePedagogicalCoordinatorPassword,
  },
  /*{
    path: "/renewPedagogicalCoordinatorPassword",
    exact: true,
    name: "Changer Mot de Passe",
    component: RenewPedagogicalCoordinatorPassword,
  },*/
  {
    path: "/affectAcademicEncadrant",
    name: "Affectation des Encadrants Acad??miques",
    component: AffectAcademicEncadrant,
  },
  {
    path: "/affectExpert",
    name: "Affectation des Experts",
    component: AffectExpert,
  },
  {
    path: "/etatEncadrement",
    name: "??tat Encadrements",
    component: EtatEncadrement
  },
  {
	path: "/allValidatedDepot",
	name: "D??p??ts Valid??s",
	component: AllValidatedDepot
  },
  {
    path: "/etatExpertiseGlobal",
    name: "??tat Expertises Global",
    component: EtatExpertiseGlobal
  },
  {
    path: "/etatExpertiseByUnit",
    name: "??tat Expertises par Unit??",
    component: EtatExpertiseByUnit
  },
  {
    path: "/encadrementsByDepartement",
    name: "??tat Encadrements",
    component: EncadrementsByDepartementComponent
  },
  {
	path: "/etatEncadrementAndExpertise",
	exact: true,
	name: "Quota Encadrements/Expertises",
	component: EtatEcadrementAndExpertise,
  },
  {
    path: "/addESPFile",
    exact: true,
    name: "D??p??t Plan Travail",
    component: AddESPFile,
  },
  {
    path: "/consultESPFile",
    exact: true,
    name: "Mon Historique Plans de Travail",
    component: ConsultESPFile,
  },
  {
	path: "/etatPresidenceAndMembreJurySTN",
	name: "??tat Pr??sidence/Membre",
	component: EtatPresidenceAndMembreJurySTN
  },
  {
    path: "/modifyESPFile",
    exact: true,
    name: "Mettre ?? Jour Plan Travail",
    component: ModifyESPFile,
  },
  {
    path: "/cancelESPFile",
    exact: true,
    name: "Demande de Modification / Annulation Plan Travail",
    component: CancelESPFile,
  },

  {
    path: "/submitAgreement",
    exact: true,
    name: "Demande de Convention",
    component: SubmitAgreement,
  },

  {
    path: "/uploadSignedAgreement",
    exact: true,
    name: "D??poser Convention Sign??e",
    component: UploadSignedAgreement,
  },

  {
    path: "/uploadSignedEndorsement",
    exact: true,
    name: "D??poser Avenant Sign??",
    component: UploadSignedEndorsement,
  },

  {
    path: "/proceedEndorsement",
    exact: true,
    name: "Ajouter Avenant",
    component: ProceedEndorsement,
  },

  {
    path: "/uploadNewspaper",
    exact: true,
    name: "D??p??t Journaux",
    component: UploadNewspaper,
  },
  {
	path: "/uploadFicheEvalStageING",
	exact: true,
	name: "D??p??t Documents Stage Ing??nieur",
	component: UploadFicheEvalStageING,
  },
  {
    path: "/uploadBalanceSheet",
    exact: true,
    name: "D??p??t Bilans",
    component: UploadBalanceSheet,
  },
  {
    path: "/uploadReport",
    exact: true,
    name: "D??p??t Rapports",
    component: UploadReport,
  },
  {
    path: "/myDocuments",
    exact: true,
    name: "Mes Documents",
    component: MyDocuments,
  },
  {
    path: "/studentTimeline",
    exact: true,
    name: "Student Timeline",
    component: StudentTimeline,
  },
  {
    path: "/studentTimeline2",
    exact: true,
    name: "Student Timeline 2",
    component: StudentTimeline2,
  },

  {path: '/planifySoutenance', exact: true, name: 'Planifier Soutenances', component: PlanifySoutenance},

  {path: '/soutenancesNotifi??es', exact: true, name: 'Soutenances Notifi??es', component: SoutenancesNotifi??es},

  {path: '/soutenancesNotifi??esForCPS', exact: true, name: 'Soutenances Notifi??es For CPS', component: SoutenancesNotifi??esForCPS},

  {path: '/soutenancesPlanifi??es', exact: true, name: 'Soutenances Planifi??es', component: SoutenancesPlanifi??es},

  {path: '/ficheDepotPFEPE', exact: true, name: 'Fiche D??p??t Encadrant', component: FicheDepotPFEPE},

  {path: '/studentProgressStatus', exact: true, name: '??tat Avancement des ??tudiants ?? encadrer en tant que Encadrant Acad??mique', component: StudentProgressStatus},

  {path: '/studentsForExpertise', exact: true, name: '??tat Avancement des ??tudiants ?? encadrer en tant que Expert', component: StudentsForExpertise},

  {path: '/studentsForMembreSoutenance', exact: true, name: '??tudiants dont je suis la/le Membre du Jury de leurs Soutenances', component: StudentsForMembreSoutenance},

  {path: '/studentsForPresidenceSoutenance', exact: true, name: '??tudiants dont je suis la/le Pr??sident(e) du Jury de leurs Soutenances', component: StudentsForPresidenceSoutenance},

  {path: '/markForEngineeringTrainingship', exact: true, name: 'Note Stage Ing??nieur', component: MarkForEngineeringTrainingship},

  {path: '/myStudentTimeline', exact: true, name: 'Timeline de mon ??tudiant', component: MyStudentTimeline},

  {path: '/ficheDepotPFEPCE', exact: true, name: 'Fiche D??p??t PFE', component: FicheDepotPFEPCE},

  {
    path: '/homeResponsableServiceStage',
    exact: true,
    name: 'Dashboard',
    component: HomeResponsableServiceStage
  },

  {path: "/HomeService", name: "Dashboard", component: HomeService},
  {
    path: "/DepotDossierRapport",
    name: "D??p??t Dossier Rapport",
    component: DepotDossierRapport,
  },
  {
    path: "/RechercheFichePFEAvance",
    name: "Recherche Avanc??e",
    component: RechercheFichePFEAvance,
  },
  {path: "/PFEEnCours", name: "PFE En Cours", component: PFEEnCours},
  {
    path: "/ListRapportPresident",
    name: "Rapports Pr??sident",
    component: ListRapportPresident,
  },
  {
    path: "/EtudiantSansFiche",
    name: "??tudiants Sans Plan",
    component: EtudiantSansFiche,
  },
  {
    path: "/EncadrementValidation",
    name: "Encadrement & Validation",
    component: EncadrementValidation,
  },
  {
    path: "/EtatValidationFiche",
    name: "??tat de Validation",
    component: EtatValidationFiche,
  },
  {path: "/HomeManager", name: "Dashboard Coordinateur P??dagogique", component: HomeManager},
  {path: "/TeacherHome", name: "Dashboard Encadrant P??dagogique", component: TeacherHome},
  {
	path: "/StudentManagement",
	name: "Gestion ??tudiants",
	component: StudentManagement,
  },
  {
	path: "/TeacherManagement",
	name: "Gestion Enseignants",
	component: TeacherManagement,
  },
  {
	path: "/respServiceStgManagement",
	name: "Gestion Responsables Services Stages",
	component: RespServiceStgManagement,
  },
  {
	path: "/chefCoordManagement",
	name: "Gestion Chefs D??partements & Coordinateurs P??dagogique des Stages",
	component: ChefCoordManagement,
  },
  {
    path: "/SecteurManagement",
    name: "Gestion Secteurs",
    component: SecteurManagement,
  },
  {
    path: "/SessionManagement",
    name: "Gestion Sessions",
    component: SessionManagement,
  },
  {
    path: "/RequestDetails",
    name: "D??tails PFE",
    component: RequestDetails,
  },
  {
    path: "/AffectationManage",
    name: "Encadrants ??tudiants de Classes Terminales",
    component: AffectationManage,
  },
  {
    path: "/gestionEncadrants",
    name: "Affectation Encadrants",
    component: EncadrantPeda,
  },
  {
    path: "/RapportManage",
    name: "G??rer Rapports",
    component: RapportManage,
  },
  {
    path: "/ValidatedReports",
    name: "Rapports Valid??s",
    component: ValidatedReports,
  },
  {
    path: "/IncompletedReports",
    name: "Rapports Incomplets",
    component: IncompletedReports,
  },
  {
    path: "/FichePFETraitement",
    name: "Traitement Plans Travail",
    component: TRTFichePFE,
  },
  {path: "/FichePFE", name: "Plans Travail", component: FichePFE},
  {
    path: "/FichePFEManagement",
    name: "G??rer Plans Travail",
    component: FichePFEManagement,
  },
  {
    path: "/ConventionDetails",
    name: "D??tails Conventions",
    component: ConventionDetails,
  },
  {
    path: "/DemandeAnnulationConventionDetails",
    name: "D??tails Convention ?? annuler",
    component: DemandeAnnulationConventionDetails,
  },
  {
    path: "/AvenantDetails",
    name: "D??tails Avenants",
    component: AvenantDetails,
  },
  {
    path: "/ConventionsManagement",
    name: "G??rer Conventions",
    component: ConventionsManage,
  },
  {
	path: "/ConventionsValideesManagement",
	name: "G??rer Conventions Valid??es",
	component: ConventionsValideesManage,
  },
  {
    path: "/ConventionsDemandesAnnulation",
    name: "Demandes Annulation Conventions",
    component: ConventionsDemandesAnnulation,
  },
  {
    path: "/AvenantsManagement",
    name: "G??rer Avenants",
    component: AvenantsManage,
  },
  {
    path: "/VisitIntern",
    name: "Planning Visites Stagiaires",
    component: VisitIntern,
  },
  {
    path: "/UpdateRDVSuivi",
    name: "Mettre ?? Jour Rendez-Vous",
    component: UpdateRDV,
  },
  {
    path: "/UpdateObservState",
    name: "Mettre ?? Jour Observation & ??tat RDV",
    component: UpdateStateOb,
  },
  {
    path: "/UpdateVisitInformation",
    name: "Mettre ?? Jour Visite Stagiaire",
    component: UpdateVisit,
  },
  {
    path: "/UpdateVisitDate",
    name: "Mettre ?? Jour Date Visite Stagiaire",
    component: UpdateVisitDate,
  },
  {
    path: "/StudentSupervision",
    name: "Encadrement ??tudiants",
    component: StudentSupervision
  },
  {
    path: "/PFESheetDetails",
    name: "Consultation Plans Travail",
    component: FichePFEDetails
  },
  {
    path: "/AddEvaluation",
    name: "Saisie Note ??valuation",
    component: AddEvaluation
  },
  {
    path: "/UpdateEvaluation",
    name: "Mettre ?? Jour ??valuation",
    component: UpdateEvaluation
  },
];

export default routes;
