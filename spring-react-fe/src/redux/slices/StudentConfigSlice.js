import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
let initialState = {
  studentConfigs: [],
  selectedStudentConfig: {},
  errors: "",
};

const StudentConfigSlice = createSlice({
  name: "studentConfigs",
  initialState,
  reducers: {
    populateStudentConfigs(state, action) {
      state.studentConfigs = action.payload;
    },
    populateStudentConfigsByYear(state, action) {
      state.studentConfigs = action.payload;
    },
    selectStudentConfig(state, action) {
      state.selectedStudentConfig = action.payload;
    },
    unselectStudentConfig(state) {
      state.selectedStudentConfig = null;
    },
    deleteStudentConfig: (state, action) => {
      const payload = action.payload;

      const index = state.studentConfigs.findIndex(
          (item) => item.idStudentConfig === payload.idStudentConfig
      );

      if (index !== -1) {
        state.studentConfigs.splice(index, 1);
      }
    },
    updateStudentConfig: (state, action) => {
      const payload = action.payload;
      const index = state.studentConfigs.findIndex(
          (item) => item.idStudent === payload.idStudent
      );
      if (index !== -1) {
        state.studentConfigs[index] = payload;
      }
    },
    addStudentConfig: (state, action) => {
      const payload = action.payload;
      if (state.studentConfigs.length === 0) {
        state.studentConfigs = [];
        state.studentConfigs.unshift(payload);
      } else {
        state.studentConfigs.unshift(payload);
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const fetchStudentConfigs = () => async (dispatch) => {
  const [res, error] = await queryApi("config/studentConfigs");

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateStudentConfigs(res));
  }
};

export const fetchClearStudentConfigs = () => async (dispatch) => {
  // console.log ('... PIKATCHO SERV CLEAR -> ')
  dispatch(populateStudentConfigsByYear());
};

export const fetchStudentConfigsByYear = (selectedYear) => async (dispatch) => {

  const [res, error] = await queryApi("config/studentConfigs/" + selectedYear);

  if (error) {
    dispatch(setErrors(error));
  } else {
    // console.log ('... PIKATCHO SERV FILL 1-> ' , selectedYear)
    dispatch(populateStudentConfigsByYear(res));
    // console.log ('... PIKATCHO SERV FILL 2 -> ' , selectedYear)
  }
};

export const selectStudentConfigs = (state) => {
  return [
    state.persistedReducer.studentConfigs.studentConfigs,
    state.persistedReducer.studentConfigs.errors,
  ];
};

export const selectStudentConfigsByYear = (state) => {
  return [
    state.persistedReducer.studentConfigs.studentConfigs,
    state.persistedReducer.studentConfigs.errors,
  ];
};

export const selectSelectedStudentConfig = (state) => {
  return state.persistedReducer.studentConfigs.selectedStudentConfig;
};

export const {
  setErrors,
  addStudentConfig,
  updateStudentConfig,
  deleteStudentConfig,
  unselectStudentConfig,
  selectStudentConfig,
  populateStudentConfigs,
  populateStudentConfigsByYear,
} = StudentConfigSlice.actions;

export default StudentConfigSlice.reducer;
