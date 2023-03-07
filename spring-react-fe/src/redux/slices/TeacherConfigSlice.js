import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
let initialState = {
  teacherConfigs: [],
  selectedTeacherConfig: {},
  errors: "",
};

const TeacherConfigSlice = createSlice({
  name: "teacherConfigs",
  initialState,
  reducers: {
    populateTeacherConfigs(state, action) {
      state.teacherConfigs = action.payload;
    },
    populateTeacherConfigsByYear(state, action) {
      state.teacherConfigs = action.payload;
    },
    selectTeacherConfig(state, action) {
      state.selectedTeacherConfig = action.payload;
    },
    unselectTeacherConfig(state) {
      state.selectedTeacherConfig = null;
    },
    deleteTeacherConfig: (state, action) => {
      const payload = action.payload;

      const index = state.teacherConfigs.findIndex(
          (item) => item.idTeacher === payload.idTeacher
      );

      if (index !== -1) {
        state.teacherConfigs.splice(index, 1);
      }
    },
    updateTeacherConfig: (state, action) => {
      const payload = action.payload;
      const index = state.teacherConfigs.findIndex(
          (item) => item.idTeacher === payload.idTeacher
      );
      if (index !== -1) {
        state.teacherConfigs[index] = payload;
      }
    },
    addTeacherConfig: (state, action) => {
      const payload = action.payload;
      if (state.teacherConfigs.length === 0) {
        state.teacherConfigs = [];
        state.teacherConfigs.unshift(payload);
      } else {
        state.teacherConfigs.unshift(payload);
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const fetchTeacherConfigs = () => async (dispatch) => {
  const [res, error] = await queryApi("config/teacherConfigs");

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateTeacherConfigs(res));
  }
};

export const fetchClearTeacherConfigs = () => async (dispatch) => {
  // console.log ('... PIKATCHO SERV CLEAR -> ')
  dispatch(populateTeacherConfigsByYear());
};

export const fetchTeacherConfigsByYear = (selectedYear) => async (dispatch) => {

  const [res, error] = await queryApi("config/teacherConfigs/" + selectedYear);

  if (error) {
    dispatch(setErrors(error));
  } else {
    // console.log ('... PIKATCHO SERV FILL 1-> ' , selectedYear)
    dispatch(populateTeacherConfigsByYear(res));
    // console.log ('... PIKATCHO SERV FILL 2 -> ' , selectedYear)
  }
};

export const selectTeacherConfigs = (state) => {
  return [
    state.persistedReducer.teacherConfigs.teacherConfigs,
    state.persistedReducer.teacherConfigs.errors,
  ];
};

export const selectTeacherConfigsByYear = (state) => {
  return [
    state.persistedReducer.teacherConfigs.teacherConfigs,
    state.persistedReducer.teacherConfigs.errors,
  ];
};

export const selectSelectedTeacherConfig = (state) => {
  return state.persistedReducer.teacherConfigs.selectedTeacherConfig;
};

export const {
  setErrors,
  addTeacherConfig,
  updateTeacherConfig,
  deleteTeacherConfig,
  unselectTeacherConfig,
  selectTeacherConfig,
  populateTeacherConfigs,
  populateTeacherConfigsByYear,
} = TeacherConfigSlice.actions;

export default TeacherConfigSlice.reducer;
