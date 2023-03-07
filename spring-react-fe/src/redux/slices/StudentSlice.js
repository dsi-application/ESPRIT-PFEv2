import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";

import AuthService from "../../views/services/auth.service";

const currentTeacher = AuthService.getCurrentTeacher();
let initialState = {
  students: [],
  studentsStatus: "noData",
  fichePFEs: [],
  conventions: [],
  avenants: [],
  EtatFiche: {},
  selectedFiche: {},
  errors: "",
};

export const fetchStudents = () => async (dispatch) => {
  dispatch(populateLoadStudents());
  const [res, error] = await queryApi(
      "encadrement/StudentList/" + currentTeacher.idEns
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateStudents(res));
    dispatch(populateDoneStudents());
  }
};
const studentsSlice = createSlice({
  name: "students",
  initialState,
  reducers: {
    populateStudents(state, action) {
      state.students = action.payload;
    },
    populateLoadStudents(state, action) {
      state.studentsStatus = "loading";
    },
    populateDoneStudents(state, action) {
      state.studentsStatus = "data";
    },
    populateFiches(state, action) {
      state.fichePFEs = action.payload;
    },
    selectFiche(state, action) {
      state.selectedFiche = action.payload;
    },
    unselectFiche(state) {
      state.selectedFiche = null;
    },
    populateConventions(state, action) {
      state.conventions = action.payload;
    },
    unselectConventions(state) {
      state.conventions = [];
    },
    populateAvenants(state, action) {
      state.avenants = action.payload;
    },
    unselectAvenants(state) {
      state.avenants = [];
    },
    getEtatFichebyEt(state, action) {
      state.EtatFiche = action.payload;
    },
    unselectEtatFichebyEt(state, action) {
      state.EtatFiche = {};
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const selectStudents = (state) => {
  return [
    state.persistedReducer.students.students,
    state.persistedReducer.students.errors,
  ];
};
export const selectFiches = (state) => {
  return [
    state.persistedReducer.students.fichePFEs,
    state.persistedReducer.students.errors,
  ];
};
export const selectConventions = (state) => {
  return [
    state.persistedReducer.students.conventions,
    state.persistedReducer.students.errors,
  ];
};
export const selectAvenants = (state) => {
  return [
    state.persistedReducer.students.avenants,
    state.persistedReducer.students.errors,
  ];
};

export const selectEtatFiche = (state) => {
  return [
    state.persistedReducer.students.EtatFiche,
    state.persistedReducer.students.errors,
  ];
};

export const fecthFichePFEs = (id) => async (dispatch) => {
  const [res, error] = await queryApi("encadrement/GetListFichebyEt/" + id);

  // console.log('-----------------------> 1509: ' + "encadrement/GetListFichebyEt/" + id);

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateFiches(res));
  }
};
export const fecthConventions = (id) => async (dispatch) => {
  const [res, error] = await queryApi(
      "encadrement/GetListConventionbyEt/" + id
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateConventions(res));
  }
};

export const fecthAvenants = (id) => async (dispatch) => {
  const [res, error] = await queryApi("encadrement/avenantList?idET=" + id);

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateAvenants(res));
  }
};
export const getEtatFiche = (etat) => async (dispatch) => {
  const [res, error] = await queryApi(
      "encadrement/GetEtatfichePFE?etat=" + etat
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(getEtatFichebyEt(res));
  }
};

export const selectSelectedFiche = (state) => {
  return state.persistedReducer.students.selectedFiche;
};
export const {
  setErrors,
  populateStudents,
  populateFiches,
  getEtatFichebyEt,
  populateConventions,
  selectFiche,
  unselectFiche,
  populateAvenants,
  populateEntreprises,
  unselectConventions,
  unselectAvenants,
  unselectEtatFichebyEt,
  populateDoneStudents,
  populateLoadStudents,
} = studentsSlice.actions;
export default studentsSlice.reducer;
