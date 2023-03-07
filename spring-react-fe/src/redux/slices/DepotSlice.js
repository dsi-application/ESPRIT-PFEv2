import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentResponsableServiceStage =
  AuthService.getCurrentResponsableServiceStage();

let initialState = {
  DepotStat: [], // HooksMe - 2
  UploadedDepotStat: [],
  ValidatedDepotStat: [],
  RefusedDepotStat: [],
  SoutenanceStat: [],
  studentsToSTNStat: [],
  studentsPLanifSTNStat: [],
  studentsDoneSTNStat: [],
  planifiedSoutenances: [],
  unplanifiedSoutenances: [],
  nbrUploadedReports: [],
  nbrValidatedReports: [],
  nbrRefusedReports: []
};
function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

// HooksMe - 3

export const fetchPlanifiedSoutenances = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
  await queryApi("auth/responsableServiceStage/planifiedSoutenances/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
      // console.log('----------------> OUTPUT-0.2:' + res);
      dispatch(populatePlanifiedSoutenances(res));
  }
};

export const fetchUnplanifiedSoutenances = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
  await queryApi("auth/responsableServiceStage/unplanifiedSoutenances/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
      // console.log('----------------> OUTPUT-0.2:' + res);
      dispatch(populateUnplanifiedSoutenances(res));
  }
};

export const fetchUploadedReports = () => async (dispatch) =>
{
  
  const [res, error] =
  await queryApi("auth/responsableServiceStage/uploadedDepot/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
     // console.log('-----------------------------------------------------> LOL20211 a:' + res);
      dispatch(populateUploadedReports(res));
  }
};

export const fetchValidatedReports = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
  await queryApi("auth/responsableServiceStage/validatedDepot/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
      // console.log('-----------------------------------------------------> LOL20211 b:' + res);
      dispatch(populateValidatedReports(res));
  }
};

export const fetchRefusedReports = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
  await queryApi("auth/responsableServiceStage/incompletedDepot/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
      // console.log('-----------------------------------------------------> LOL20211 c:' + res);
      dispatch(populateRefusedReports(res));
  }
};

export const fetchStudentToSTNStat = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
  await queryApi("auth/nbrAuthorizedStudentsToSTN/1/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
      // console.log('----------------> OUTPUT-0.2:' + res);
      dispatch(populateStudentToSTNStat(res));
  }
};

export const fetchStudentPlanifSTNStat = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
  await queryApi("auth/nbrAllStudentsPlanifiedSoutenance/1/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
      // console.log('----------------> OUTPUT-0.2:' + res);
      dispatch(populateStudentPlanifSTNStat(res));
  }
};

export const fetchStudentDoneSTNStat = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-1.1:' + res);
  const [res, error] = 
  await queryApi("auth/nbrAllStudentsDoneSoutenance/1/" + currentResponsableServiceStage.id);

  if (error)
  {
      dispatch(setErrors(error));
  }
  else
  {
      // console.log('----------------> OUTPUT-1.2:' + res);
      dispatch(populateStudentDoneSTNStat(res));
  }
};

export const fetchRSSSoutenanceStat = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStage/statDoneSoutenancesByDay/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateSoutenanceStat(res));
  }
};

export const fetchRSSDepotStat = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStage/depotStat/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateDepotStat(res));
  }
};

export const fetchRSSUploadedDepotStat = () => async (dispatch) => 
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStage/uploadedDepotReports/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateUploadedDepotStat(res));
  }
};


export const fetchRSSValidatedDepotStat = () => async (dispatch) => 
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStage/validatedDepotReports/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateValidatedDepotStat(res));
  }
};


export const fetchRSSRefusedDepotStat = () => async (dispatch) => 
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStage/refusedDepotReports/" + currentResponsableServiceStage.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateRefusedDepotStat(res));
  }
};

const DepotSlice = createSlice({
  name: "depot",
  initialState,
  reducers: {
    // HooksMe - 4

    populateDepotStat(state, action) {  
      state.DepotStat = action.payload;
    },
    populateUploadedDepotStat(state, action) {
      state.UploadedDepotStat = action.payload;
    },
    populateValidatedDepotStat(state, action) {
      state.ValidatedDepotStat = action.payload;
    },
    populateRefusedDepotStat(state, action) {
      state.RefusedDepotStat = action.payload;
    },
    populateSoutenanceStat(state, action) {
      state.SoutenanceStat = action.payload;
      // console.log('----------------------------> OUTPUT A')
    },
    populateStudentToSTNStat(state, action) {
      state.studentsToSTNStat = action.payload;
      // console.log('----------------------------> OUTPUT Z')
    },
    populateStudentPlanifSTNStat(state, action) {
      state.studentsPlanifSTNStat = action.payload;
      // console.log('----------------------------> OUTPUT Z')
    },
    populateStudentDoneSTNStat(state, action) {
      state.studentsDoneSTNStat = action.payload;
      // console.log('----------------------------> OUTPUT E')
    },
    populatePlanifiedSoutenances(state, action) {
      state.planifiedSoutenances = action.payload;
      // console.log('----------------------------> OUTPUT E')
    },
    populateUnplanifiedSoutenances(state, action) {
      state.unplanifiedSoutenances = action.payload;
      // console.log('----------------------------> OUTPUT E')
    },
    populateUploadedReports(state, action) {
      state.nbrUploadedReports = action.payload;
      // console.log('----------------------------> OUTPUT E')
    },
    populateValidatedReports(state, action) {
      state.nbrValidatedReports = action.payload;
      // console.log('----------------------------> OUTPUT E')
    },
    populateRefusedReports(state, action) {
      state.nbrRefusedReports = action.payload;
      // console.log('----------------------------> OUTPUT E')
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

// HooksMe - 1
export const selectSoutenanceStat = (state) => { 
  return [
    state.persistedReducer.depot.SoutenanceStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectDepotStat = (state) => { 
  return [
    state.persistedReducer.depot.DepotStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectUploadedDepotStat = (state) => {
  return [
    state.persistedReducer.depot.UploadedDepotStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectValidatedDepotStat = (state) => {
  return [
    state.persistedReducer.depot.ValidatedDepotStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectRefusedDepotStat = (state) => {
  return [
    state.persistedReducer.depot.RefusedDepotStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectStudentsToStat = (state) => {
  return [
    state.persistedReducer.depot.studentsToSTNStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectStudentsPLanifStat = (state) => {
  return [
    state.persistedReducer.depot.studentsPlanifSTNStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectStudentsDoneStat = (state) => {
  return [
    state.persistedReducer.depot.studentsDoneSTNStat,
    state.persistedReducer.depot.errors,
  ];
};

export const selectPlanifiedSoutenances = (state) => {
  return [
    state.persistedReducer.depot.planifiedSoutenances,
    state.persistedReducer.depot.errors,
  ];
};

export const selectUnplanifiedSoutenances = (state) => {
  return [
    state.persistedReducer.depot.unplanifiedSoutenances,
    state.persistedReducer.depot.errors,
  ];
};

export const selectNbrUploadedReports = (state) => {
  return [
    state.persistedReducer.depot.nbrUploadedReports,
    state.persistedReducer.depot.errors,
  ];
};

export const selectNbrValidatedReports = (state) => {
  return [
    state.persistedReducer.depot.nbrValidatedReports,
    state.persistedReducer.depot.errors,
  ];
};

export const selectNbrRefusedReports = (state) => {
  return [
    state.persistedReducer.depot.nbrRefusedReports,
    state.persistedReducer.depot.errors,
  ];
};

// HooksMe - 5 
export const {
  setErrors,
  populateDepotStat, 
  populateUploadedDepotStat,
  populateValidatedDepotStat,
  populateRefusedDepotStat,
  populateSoutenanceStat,
  populateStudentToSTNStat,
  populateStudentPlanifSTNStat,
  populateStudentDoneSTNStat,
  populatePlanifiedSoutenances,
  populateUnplanifiedSoutenances,
  populateUploadedReports,
  populateValidatedReports,
  populateRefusedReports
} = DepotSlice.actions;
export default DepotSlice.reducer;
