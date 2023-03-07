import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentResponsableServiceStage =
  AuthService.getCurrentResponsableServiceStage();
const currentTeacher = AuthService.getCurrentTeacher();

let initialState = {
  optionStudents: [],
  optionStudentsStatus: "noData",
  encadrant: {},
  enseignants: [],
  etudiantsbyEns: [],
  errors: ""
};

export const fetchOptionStudents = () => async (dispatch) => {
  dispatch(populateoptionStudentsLoad());
  const [res, error] = await queryApi(
    "responsableStage/getListEtudiantbyOption?code=" + encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateOptionStudents(res));

    dispatch(populateoptionStudentsDone());
  }
};

const encadrementSlice = createSlice({
  name: "encadrement",
  initialState,
  reducers: {
    populateOptionStudents(state, action) {
      state.optionStudents = action.payload;
    },
    populateoptionStudentsLoad(state, action) {
      state.optionStudentsStatus = "loading";
    },
    populateoptionStudentsDone(state, action) {
      state.optionStudentsStatus = "done";
    },
    populateEnseignants(state, action) {
      state.enseignants = action.payload;
    },
    populateEtudiantsbyEns(state, action) {
      state.etudiantsbyEns = action.payload;
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    getEncadrantbyEt(state, action) {
      state.encadrant = action.payload;
    },
  },
});

export const fetchEnseignants = () => async (dispatch) => {
  const [res, error] = await queryApi("responsableStage/ListEns");

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEnseignants(res));
  }
};
export const selectEnseignants = (state) => {
  return [
    state.persistedReducer.encadrement.enseignants,
    state.persistedReducer.encadrement.errors,
  ];
};

export const selectOptionStudents = (state) => {
  return [
    state.persistedReducer.encadrement.optionStudents,
    state.persistedReducer.encadrement.errors,
  ];
};
export const fetchEtudiantsbyEns = (idEns) => async (dispatch) => {
  const [res, error] = await queryApi(
    "responsableStage/getListEtudiantbyOptionbyEns?code=" +
    encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))+
      "&idEns=" +
      idEns
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEtudiantsbyEns(res));
  }
};
export const selectEtudiantsbyEns = (state) => {
  return [
    state.persistedReducer.encadrement.etudiantsbyEns,
    state.persistedReducer.encadrement.errors,
  ];
};
export const getEancadrant = (id) => async (dispatch) => {
  const [res, error] = await queryApi(
    "responsableStage/getEncadrantByEtudiant?idEt=" + id
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(getEncadrantbyEt(res));
  }
};
export const selectEncadrant = (state) => {
  return state.persistedReducer.encadrement.encadrant;
};
export const {
  setErrors,
  populateOptionStudents,
  getEncadrantbyEt,
  populateEnseignants,
  populateEtudiantsbyEns,
  populateoptionStudentsDone,
  populateoptionStudentsLoad,
} = encadrementSlice.actions;
export default encadrementSlice.reducer;
