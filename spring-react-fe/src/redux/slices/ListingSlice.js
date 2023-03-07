import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import axios from "axios";
import AuthService from "../../views/services/auth.service";
const currentTeacher = AuthService.getCurrentTeacher();
const currentResponsableServiceStage =
  AuthService.getCurrentResponsableServiceStage();
let initialState = {
  StudentsSoutenances: [],
  StudentsSoutenancesStatus: "noData",
  EtudiantSansFiches: [],
  EtudiantSansFichesStatus: "noData",
  EtatValidationFiches: [],
  EtatValidationFichesStatus: "noData",
  EncadrementValidations: [],
  EncadrementValidationsStatus: "noData",
  ListRechercheFiche: [],
  ListRechercheFicheStatus: "noData",
  EtudiantbySession: [],
  EtudiantbySessionStatus: "noData",
  StudentPFEDepasseList: [],
  StudentPFEDepasseListStatus: "noData",
  AllClasse: [],
  Alltechno: [],
  AllFilére: [],
  AllAnnee: [],
  RendezVousSuiviTypes: [],
  EvaluationTypes: [],
  VisitesTypes: [],
};

export const fetchEtatValidationFiche = (annee) => async (dispatch) => {
  dispatch(populateEtatValidationFichesLoad());
  const [res, error] = await queryApi(
    "responsableStage/EtatValidationFiche?annee=" +
      annee +
      "&codeOption=" +
    encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))
  );

  //console.log('--------------------------> 30-08: ', encodeURIComponent(JSON.stringify(currentTeacher.codeOptions)))

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEtatValidationFiche(res));

    dispatch(populateEtatValidationFichesDone());
  }
};
export const fetchEtudiantbySession = (sessionid) => async (dispatch) => {
  dispatch(populateEtudiantbySessionLoad());
  const [res, error] = await queryApi(
    "serviceStage/EtudiantbySession?id=" +
      currentResponsableServiceStage.id +
      "&sessionid=" +
      sessionid
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEtudiantbySession(res));

    dispatch(populateEtudiantbySessioneDone());
  }
};
export const fetchStudentPFEDepasseList = (nbrMois) => async (dispatch) => {
  dispatch(populateStudentPFEDepasseListLoad());
  const [res, error] = await queryApi(
    "serviceStage/StudentPFEDepasseList?id=" +
      currentResponsableServiceStage.id +
      "&nbrMois=" +
      nbrMois
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateStudentPFEDepasseList(res));
    dispatch(populateStudentPFEDepasseListDone());
  }
};

export const fetchStudentsSoutenances = () => async (dispatch) => {
  dispatch(populateStudentsForSoutenanceLoad());
  const [res, error] = await queryApi(
    "encadrement/GetlistStudentForSoutenance?idEns=" + currentTeacher.idEns
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateStudentsForSoutenance(res));

    dispatch(populateStudentsForSoutenanceDone());
  }
};
export const fetchEtudiantSansFiche = () => async (dispatch) => {
  dispatch(populateEtudiantSansFichesLoad());
  const [res, error] = await queryApi(
    "responsableStage/EtudiantSansFicheList?codeOption=" +
    encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEtudiantSansFiche(res));
    dispatch(populateEtudiantSansFichesDone());
  }
};
export const fetchEncadrementValidation = (annee) => async (dispatch) => {
  dispatch(populateEncadrementValidationsLoad());
  const [res, error] = await queryApi(
    "responsableStage/TeacherWithNumberEncadrement?annee=" + annee
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEncadrementValidation(res));
    dispatch(populateEncadrementValidationsDone());
  }
};
export const fetchListRechercheFiche =
  (annee, classe, etat, codeFilere, list) => async (dispatch) => {
    dispatch(populateListRechercheFicheLoad());
    const [res, err] = await queryApi(
      "config/rechercheFicheAvance?annee=" +
        annee +
        "&classe=" +
        classe +
        "&etat=" +
        etat +
        "&codeFilere=" +
        codeFilere,
      list,
      "POST",
      false
    );

    if (err) {
      dispatch(setErrors(err));
    } else {
      dispatch(populateListRechercheFiche(res));

      dispatch(populateListRechercheFicheDone());
    }
  };
const ListingSlice = createSlice({
  name: "listing",
  initialState,
  reducers: {
    populateRendezVousSuiviTypes(state, action) {
      state.RendezVousSuiviTypes = action.payload;
    },
    populateEvaluationTypes(state, action) {
      state.EvaluationTypes = action.payload;
    },
    populateVisitesTypes(state, action) {
      state.VisitesTypes = action.payload;
    },
    populateAllAnnee(state, action) {
      state.AllAnnee = action.payload;
    },
    populateAllClasse(state, action) {
      state.AllClasse = action.payload;
    },
    populateAlltechno(state, action) {
      state.Alltechno = action.payload;
    },
    populateAllFilére(state, action) {
      state.AllFilére = action.payload;
    },
    populateListRechercheFicheNull(state, action) {
      state.ListRechercheFiche = [];
    },
    populateListRechercheFiche(state, action) {
      state.ListRechercheFiche = action.payload;
    },
    populateListRechercheFicheLoad(state, action) {
      state.ListRechercheFicheStatus = "loading";
    },
    populateListRechercheFicheDone(state, action) {
      state.ListRechercheFicheStatus = "done";
    },
    populateStudentPFEDepasseList(state, action) {
      state.StudentPFEDepasseList = action.payload;
    },
    populateStudentPFEDepasseListNull(state, action) {
      state.StudentPFEDepasseList = [];
    },
    populateStudentPFEDepasseListLoad(state, action) {
      state.StudentPFEDepasseListStatus = "loading";
    },
    populateStudentPFEDepasseListDone(state, action) {
      state.StudentPFEDepasseListStatus = "done";
    },
    populateEtudiantbySession(state, action) {
      state.EtudiantbySession = action.payload;
    },
    populateStudentsForSoutenance(state, action) {
      state.StudentsSoutenances = action.payload;
    },
    populateStudentsForSoutenanceLoad(state, action) {
      state.StudentsSoutenancesStatus = "loading";
    },
    populateStudentsForSoutenanceDone(state, action) {
      state.StudentsSoutenancesStatus = "done";
    },
    populateEtudiantbySessionLoad(state, action) {
      state.EtudiantbySessionStatus = "loading";
    },
    populateEtudiantbySessioneDone(state, action) {
      state.EtudiantbySessionStatus = "done";
    },
    populateEtatValidationFiche(state, action) {
      state.EtatValidationFiches = action.payload;
    },
    populateEtatValidationFichesLoad(state, action) {
      state.EtatValidationFichesStatus = "loading";
    },
    populateEtatValidationFichesDone(state, action) {
      state.EtatValidationFichesStatus = "done";
    },
    populateEncadrementValidation(state, action) {
      state.EncadrementValidations = action.payload;
    },
    populateEncadrementValidationNull(state, action) {
      state.EncadrementValidations = [];
    },
    populateEncadrementValidationsLoad(state, action) {
      state.EncadrementValidationsStatus = "loading";
    },
    populateEncadrementValidationsDone(state, action) {
      state.EncadrementValidationsStatus = "done";
    },
    populateEtudiantSansFiche(state, action) {
      state.EtudiantSansFiches = action.payload;
    },
    populateEtudiantSansFichesLoad(state, action) {
      state.EtudiantSansFichesStatus = "loading";
    },
    populateEtudiantSansFichesDone(state, action) {
      state.EtudiantSansFichesStatus = "done";
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const selectStudentsForSoutenance = (state) => {
  return [
    state.persistedReducer.listing.StudentsSoutenances,
    state.persistedReducer.listing.errors,
  ];
};

export const selectEtudiantSansFiche = (state) => {
  return [
    state.persistedReducer.listing.EtudiantSansFiches,
    state.persistedReducer.listing.errors,
  ];
};

export const selectEtatValidationFiche = (state) => {
  return [
    state.persistedReducer.listing.EtatValidationFiches,
    state.persistedReducer.listing.errors,
  ];
};

export const selectEncadrementValidation = (state) => {
  return [
    state.persistedReducer.listing.EncadrementValidations,
    state.persistedReducer.listing.errors,
  ];
};

export const selectListRechercheFiche = (state) => {
  return [
    state.persistedReducer.listing.ListRechercheFiche,
    state.persistedReducer.listing.errors,
  ];
};

export const selectEtudiantbySession = (state) => {
  return [
    state.persistedReducer.listing.EtudiantbySession,
    state.persistedReducer.listing.errors,
  ];
};

export const selectStudentPFEDepasseList = (state) => {
  return [
    state.persistedReducer.listing.StudentPFEDepasseList,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchAllClasse = () => async (dispatch) => {
  const [res, error] = await queryApi("config/getAllClasse");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateAllClasse(res));
  }
};

export const selectAllClasse = (state) => {
  return [
    state.persistedReducer.listing.AllClasse,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchAlltechno = () => async (dispatch) => {
  const [res, error] = await queryApi("config/getAllTechnologie");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateAlltechno(res));
  }
};

export const selectAlltechno = (state) => {
  return [
    state.persistedReducer.listing.Alltechno,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchAllFilére = () => async (dispatch) => {
  const [res, error] = await queryApi("config/getAllFilére");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateAllFilére(res));
  }
};

export const selectAllFilére = (state) => {
  return [
    state.persistedReducer.listing.AllFilére,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchAllAnnee = () => async (dispatch) => {
  const [res, error] = await queryApi("config/getAllAnnee");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateAllAnnee(res));
  }
};

export const selectAllAnnee = (state) => {
  return [
    state.persistedReducer.listing.AllAnnee,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchRendezVousSuiviTypes = () => async (dispatch) => {
  const [res, error] = await queryApi("config/findRendezVousSuiviTypes");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateRendezVousSuiviTypes(res));
  }
};

export const selectRendezVousSuiviTypes = (state) => {
  return [
    state.persistedReducer.listing.RendezVousSuiviTypes,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchEvaluationTypes = () => async (dispatch) => {
  const [res, error] = await queryApi("config/findEvaluationTypes");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEvaluationTypes(res));
  }
};

export const selectEvaluationTypes = (state) => {
  return [
    state.persistedReducer.listing.EvaluationTypes,
    state.persistedReducer.listing.errors,
  ];
};
export const fetchVisitesTypes = () => async (dispatch) => {
  const [res, error] = await queryApi("config/findVisitesTypes");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateVisitesTypes(res));
  }
};

export const selectVisitesTypes = (state) => {
  return [
    state.persistedReducer.listing.VisitesTypes,
    state.persistedReducer.listing.errors,
  ];
};
export const {
  setErrors,
  populateStudentsForSoutenance,
  populateEtudiantSansFiche,
  populateEtatValidationFiche,
  populateEncadrementValidation,
  populateListRechercheFiche,
  populateEtudiantbySession,
  populateStudentPFEDepasseList,
  populateAllClasse,
  populateAlltechno,
  populateAllFilére,
  populateAllAnnee,
  populateRendezVousSuiviTypes,
  populateEvaluationTypes,
  populateVisitesTypes,
  populateStudentPFEDepasseListDone,
  populateStudentPFEDepasseListLoad,
  populateEtudiantbySessionLoad,
  populateEtudiantbySessioneDone,
  populateStudentsForSoutenanceDone,
  populateStudentsForSoutenanceLoad,
  populateEtatValidationFichesDone,
  populateEtatValidationFichesLoad,
  populateEtudiantSansFichesDone,
  populateEtudiantSansFichesLoad,
  populateEncadrementValidationsDone,
  populateEncadrementValidationsLoad,
  populateListRechercheFicheDone,
  populateListRechercheFicheLoad,
  populateListRechercheFicheNull,
  populateEncadrementValidationNull,
  populateStudentPFEDepasseListNull,
} = ListingSlice.actions;
export default ListingSlice.reducer;
