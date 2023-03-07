import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";

let initialState = {
  respServiceStgConfigs: [],
  selectedRespServiceStgConfig: {},
  errors: "",
};

const RespServiceStgConfigSlice = createSlice({
  name: "respServiceStgConfigs",
  initialState,
  reducers: {
    populateRespServiceStgConfigs(state, action) {
      state.respServiceStgConfigs = action.payload;
    },
    populateRespServiceStgConfigsByYear(state, action) {
      state.respServiceStgConfigs = action.payload;
    },
    selectRespServiceStgConfig(state, action) {
      state.selectedRespServiceStgConfig = action.payload;
    },
    unselectRespServiceStgConfig(state) {
      state.selectedRespServiceStgConfig = null;
    },
    deleteRespServiceStgConfig: (state, action) => {
      const payload = action.payload;

      const index = state.respServiceStgConfigs.findIndex(
          (item) => item.idRespServiceStg === payload.idRespServiceStg
      );

      if (index !== -1) {
        state.respServiceStgConfigs.splice(index, 1);
      }
    },
    updateRespServiceStgConfig: (state, action) => {
      const payload = action.payload;
      const index = state.respServiceStgConfigs.findIndex(
          (item) => item.idRespServiceStg === payload.idRespServiceStg
      );
      if (index !== -1) {
        state.respServiceStgConfigs[index] = payload;
      }
    },
    addRespServiceStgConfig: (state, action) => {
      const payload = action.payload;
      if (state.respServiceStgConfigs.length === 0) {
        state.respServiceStgConfigs = [];
        state.respServiceStgConfigs.unshift(payload);
      } else {
        state.respServiceStgConfigs.unshift(payload);
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const fetchRespServiceStgConfigs = () => async (dispatch) => {
  const [res, error] = await queryApi("config/respServiceStgConfigs");

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateRespServiceStgConfigs(res));
  }
};

export const fetchClearRespServiceStgConfigs = () => async (dispatch) => {
  // console.log ('... PIKATCHO SERV CLEAR -> ')
  dispatch(populateRespServiceStgConfigsByYear());
};

export const fetchRespServiceStgConfigsByYear = (selectedYear) => async (dispatch) => {

  const [res, error] = await queryApi("config/respServiceStgConfigs/" + selectedYear);

  if (error) {
    dispatch(setErrors(error));
  } else {
    // console.log ('... PIKATCHO SERV FILL 1-> ' , selectedYear)
    dispatch(populateRespServiceStgConfigsByYear(res));
    // console.log ('... PIKATCHO SERV FILL 2 -> ' , selectedYear)
  }
};

export const selectRespServiceStgConfigs = (state) => {
  return [
    state.persistedReducer.respServiceStgConfigs.respServiceStgConfigs,
    state.persistedReducer.respServiceStgConfigs.errors,
  ];
};

export const selectRespServiceStgConfigsByYear = (state) => {
  return [
    state.persistedReducer.respServiceStgConfigs.respServiceStgConfigs,
    state.persistedReducer.respServiceStgConfigs.errors,
  ];
};

export const selectSelectedRespServiceStgConfig = (state) => {
  return state.persistedReducer.respServiceStgConfigs.selectedRespServiceStgConfig;
};

export const {
  setErrors,
  addRespServiceStgConfig,
  updateRespServiceStgConfig,
  deleteRespServiceStgConfig,
  unselectRespServiceStgConfig,
  selectRespServiceStgConfig,
  populateRespServiceStgConfigs,
  populateRespServiceStgConfigsByYear,
} = RespServiceStgConfigSlice.actions;

export default RespServiceStgConfigSlice.reducer;
