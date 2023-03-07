import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";


const currentStudent = AuthService.getCurrentStudent();
const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();
const currentTeacher = AuthService.getCurrentTeacher();


let initialState = {
	DepotStat1: [],
  jwtPwdRSS: "",
  jwtPwdTea: "",
  jwtPwdStu: ""
};

function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export const fetchRSSDepotStat1 = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStage/depotStat/" + currentResponsableServiceStage.id);
  // console.log('------------------->LOL-1.1', res)
  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateDepotStat1(res));
       // console.log('------------------->LOL-1.2', res)
  }
};

export const fetchJwtPwdRSS = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("auth/responsableServiceStageJWTPWD/" + currentResponsableServiceStage.id,);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateJwtPwdRSS(res));
  }
};

export const fetchJwtPwdTea = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("auth/teacherJWTPWD/" + currentTeacher.id,);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateJwtPwdTea(res));
  }
};

export const fetchJwtPwdStu = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("auth/studentJWTPWD/" + currentStudent.id,);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateJwtPwdStu(res));
  }
};

const SidebarSlice = createSlice({
  name: "sidebarRed",
  initialState,
  reducers: {
    // HooksMe - 4
    populateDepotStat1(state, action) {  
      state.DepotStat1 = action.payload;
    },
    populateJwtPwdRSS(state, action) {  
      state.jwtPwdRSS = action.payload;
    },
    populateJwtPwdTea(state, action) {  
      state.jwtPwdRSS = action.payload;
    },
    populateJwtPwdStu(state, action) {  
      state.jwtPwdRSS = action.payload;
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const selectDepotStat1 = (state) => { 
  return [
    state.persistedReducer.sidebarRed.DepotStat1,
    state.persistedReducer.sidebarRed.errors,
  ];
};

export const selectJwtPwdRSS = (state) => { 
  return [
    state.persistedReducer.sidebarRed.jwtPwdRSS,
    state.persistedReducer.sidebarRed.errors,
  ];
};

export const selectJwtPwdTea = (state) => { 
  return [
    state.persistedReducer.sidebarRed.jwtPwdTea,
    state.persistedReducer.sidebarRed.errors,
  ];
};

export const selectJwtPwdStu = (state) => { 
  return [
    state.persistedReducer.sidebarRed.jwtPwdStu,
    state.persistedReducer.sidebarRed.errors,
  ];
};

export const {
  setErrors,
  populateDepotStat1,
  populateJwtPwdRSS,
  populateJwtPwdTea,
  populateJwtPwdStu
} = SidebarSlice.actions;
export default SidebarSlice.reducer;
