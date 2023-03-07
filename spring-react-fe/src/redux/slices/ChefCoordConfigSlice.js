import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
let initialState = {
  chefCoordConfigs: [],
  selectedChefCoordConfig: {},
  errors: "",
};

const ChefCoordConfigSlice = createSlice({
  name: "chefCoordConfigs",
  initialState,
  reducers: {
    populateChefCoordConfigs(state, action) {
      state.chefCoordConfigs = action.payload;
    },
    populateChefCoordConfigsByYear(state, action) {
      state.chefCoordConfigs = action.payload;
    },
    selectChefCoordConfig(state, action) {
      state.selectedChefCoordConfig = action.payload;
    },
    unselectChefCoordConfig(state) {
      state.selectedChefCoordConfig = null;
    },
    deleteChefCoordConfig: (state, action) => {
      const payload = action.payload;

      const index = state.chefCoordConfigs.findIndex(
          (item) => item.idChefCoord === payload.idChefCoord
      );

      if (index !== -1) {
        state.chefCoordConfigs.splice(index, 1);
      }
    },
    updateChefCoordConfig: (state, action) => {
      const payload = action.payload;
      const index = state.chefCoordConfigs.findIndex(
          (item) => item.idChefCoord === payload.idChefCoord
      );
      if (index !== -1) {
        state.chefCoordConfigs[index] = payload;
      }
    },
    addChefCoordConfig: (state, action) => {
      const payload = action.payload;
      if (state.chefCoordConfigs.length === 0) {
        state.chefCoordConfigs = [];
        state.chefCoordConfigs.unshift(payload);
      } else {
        state.chefCoordConfigs.unshift(payload);
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const fetchChefCoordConfigs = () => async (dispatch) => {
  const [res, error] = await queryApi("config/chefCoordConfigs");

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateChefCoordConfigs(res));
  }
};

export const fetchClearChefCoordConfigs = () => async (dispatch) => {
  // console.log ('... PIKATCHO SERV CLEAR -> ')
  dispatch(populateChefCoordConfigsByYear());
};

export const fetchChefCoordConfigsByYear = (selectedYear) => async (dispatch) => {

  const [res, error] = await queryApi("config/chefCoordConfigs/" + selectedYear);

  if (error) {
    dispatch(setErrors(error));
  } else {
    // console.log ('... PIKATCHO SERV FILL 1-> ' , selectedYear)
    dispatch(populateChefCoordConfigsByYear(res));
    // console.log ('... PIKATCHO SERV FILL 2 -> ' , selectedYear)
  }
};

export const selectChefCoordConfigs = (state) => {
  return [
    state.persistedReducer.chefCoordConfigs.chefCoordConfigs,
    state.persistedReducer.chefCoordConfigs.errors,
  ];
};

export const selectChefCoordConfigsByYear = (state) => {
  return [
    state.persistedReducer.chefCoordConfigs.chefCoordConfigs,
    state.persistedReducer.chefCoordConfigs.errors,
  ];
};

export const selectSelectedChefCoordConfig = (state) => {
  return state.persistedReducer.chefCoordConfigs.selectedChefCoordConfig;
};

export const {
  setErrors,
  addChefCoordConfig,
  updateChefCoordConfig,
  deleteChefCoordConfig,
  unselectChefCoordConfig,
  selectChefCoordConfig,
  populateChefCoordConfigs,
  populateChefCoordConfigsByYear,
} = ChefCoordConfigSlice.actions;

export default ChefCoordConfigSlice.reducer;
