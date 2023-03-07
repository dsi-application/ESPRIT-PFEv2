export default [
// eslint-disable-next-line
export default [
  {
    _tag: "CSidebarNavItem",
    name: "Page d'accueil Enseignant",
    to: "/TeacherHome",
    icon: "cil-speedometer",
  },
  {
    _tag: "CSidebarNavItem",
    name: "Page d'accueil Responsable",
    to: "/HomeManager",
    icon: "cil-speedometer",

  {
    _tag: "CSidebarNavItem",
    name: "Page d'accueil Service",
    to: "/HomeService",
    icon: "cil-speedometer",
  },
  {
    _tag: "CSidebarNavTitle",
    _children: ["Coaching Management"],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Encadrement Etudiants",
    to: "/StudentSupervision",
    icon: "cil-user",
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
    icon: "cil-notes",
  },
  {
    _tag: "CSidebarNavItem",
    name: "CONVENTIONS",
    to: "/ConventionsManagement",
    icon: "cil-indent-increase",
  },
  {
    _tag: "CSidebarNavItem",
    name: "AVENANTS",
    to: "/AvenantsManagement",
    icon: "cil-indent-increase",
  },
  {
    _tag: "CSidebarNavItem",
    name: "Autorisation de dépot Rapport",
    to: "/RapportManage",
    icon: {
      name: "cil-notes",
      className: "text-success",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Dépot dossier PFE",
    to: "/DepotDossierRapport",
    icon: {
      name: "cil-notes",
      className: "text-info",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Gestion encadrants",
    to: "/AffectationManage",
    icon: {
      name: "cil-people",
      className: "text-danger",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Encadrement et Validation",
    to: "/EncadrementValidation",
    icon: {
      name: "cil-people",
      className: "text-warning",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Gestion des fiches PFEs",
    to: "/FichePFEManagement",
    icon: {
      name: "cil-notes",
      className: "text-success",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Etat de Validation",
    to: "/EtatValidationFiche",
    icon: {
      name: "cil-check",
      className: "text-success",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Etudiant Sans Fiche",
    to: "/EtudiantSansFiche",
    icon: {
      name: "cil-notes",
      className: "text-warning",
    },
  },
  {
    _tag: "CSidebarNavItem",
    name: "Gestion des demandes ",
    to: "/FichePFETraitement",
    icon: "cil-check",
  },
  {
    _tag: "CSidebarNavItem",
    name: "PFE En Cours",
    to: "/PFEEnCours",
    icon: {
      name: "cil-magnifying-glass",
      className: "text-danger",
    },
  },
  {
    _tag: "CSidebarNavDropdown",
    name: "Configuration",
    route: "/Configuration",
    icon: "cil-puzzle",
    _children: [
      {
        _tag: "CSidebarNavItem",
        name: "Session",
        to: "/Configuration/SessionManagement",
      },
      {
        _tag: "CSidebarNavItem",
        name: "Secteur Entreprise",
        to: "/Configuration/SecteurManagement",
      },
    ],
  },
  {
    _tag: "CSidebarNavItem",
    name: "Recherche Plan Travail",
    to: "/RechercheFichePFEAvance",
    icon: "cil-magnifying-glass",
  },
];

]