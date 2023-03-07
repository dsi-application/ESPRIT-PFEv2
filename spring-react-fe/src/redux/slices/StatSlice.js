import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentResponsableServiceStage =
  AuthService.getCurrentResponsableServiceStage();
const currentTeacher = AuthService.getCurrentTeacher();

let initialState = {
  ResponsableStat: [],
  EnseignantStat: [],
  EnseignantStat2: [],
  ServiceStat: [],
  ServiceStatEM: [],
  ServiceStatGC: [],
  ResponsableStatStatus: "noData",
  EnseignantStatStatus: "noData",
  EnseignantStat2Status: "noData",
  ServiceStatStatus: "noData",
  ServiceStatStatusEM: "noData",
  ServiceStatStatusGC: "noData"
};

export const fectchResponsableStat = () => async (dispatch) => {
  // console.log('-----------json: ' + currentTeacher.codeOptions);
  // console.log("json",encodeURIComponent(currentTeacher.codeOptions));
  dispatch(populateLoadResponsableStat());
  const [res, error] = await queryApi(
    "config/responsableStat?code=" +encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateResponsableStat(res));

    dispatch(populateDoneResponsableStat());
  }
};

export const fectchEnseignantStat = () => async (dispatch) => {
  dispatch(populateLoadEnseignantStat());
  const [res, error] = await queryApi(
    "config/EnseignantStat?id=" + currentTeacher.idEns
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEnseignantStat(res));

    dispatch(populateDoneEnseignantStat());
  }
};
export const fectchEnseignantStat2 = () => async (dispatch) => {
  dispatch(populateLoadEnseignantStat2());
  const [res, error] = await queryApi(
    "config/EnseignantStat2?id=" + currentTeacher.idEns
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEnseignantStat2(res));

    dispatch(populateDoneEnseignantStat2());
  }
};

export const fectchServiceStat = () => async (dispatch) => {
  dispatch(populateLoadServiceStat());
  const [res, error] = await queryApi(
    "config/ServiceStat?id=SR-STG-IT"
  );
  if (error) {
    dispatch(setErrors(error));
  } else {

    dispatch(populateServiceStat(res));

    dispatch(populateDoneServiceStat());
  }
};

export const fectchServiceStatEM = () => async (dispatch) => {
  dispatch(populateLoadServiceStatEM());
  const [res, error] = await queryApi(
      "config/ServiceStat?id=SR-STG-EM"
  );
  if (error) {
    dispatch(setErrors(error));
  } else {

    dispatch(populateServiceStatEM(res));

    dispatch(populateDoneServiceStatEM());
  }
};

export const fectchServiceStatGC = () => async (dispatch) => {
  dispatch(populateLoadServiceStatGC());
  const [res, error] = await queryApi(
      "config/ServiceStat?id=SR-STG-GC"
  );
  if (error) {
    dispatch(setErrors(error));
  } else {

    dispatch(populateServiceStatGC(res));

    dispatch(populateDoneServiceStatGC());
  }
};

const StatSlice = createSlice({
  name: "stat",
  initialState,
  reducers: {
    populateResponsableStat(state, action) {
      state.ResponsableStat = action.payload;
    },
    populateEnseignantStat(state, action) {
      state.EnseignantStat = action.payload;
    },
    populateEnseignantStat2(state, action) {
      state.EnseignantStat2 = action.payload;
    },
    populateServiceStat(state, action) {
      state.ServiceStat = action.payload;
    },
    populateServiceStatEM(state, action) {
      state.ServiceStatEM = action.payload;
    },
    populateServiceStatGC(state, action) {
      state.ServiceStatGC = action.payload;
    },
    populateLoadResponsableStat(state, action) {
      state.ResponsableStatStatus = "loading";
    },
    populateDoneResponsableStat(state, action) {
      state.ResponsableStatStatus = "data";
    },
    populateLoadEnseignantStat(state, action) {
      state.EnseignantStatStatus = "loading";
    },
    populateDoneEnseignantStat(state, action) {
      state.EnseignantStatStatus = "data";
    },
    populateLoadEnseignantStat2(state, action) {
      state.EnseignantStat2Status = "loading";
    },
    populateDoneEnseignantStat2(state, action) {
      state.EnseignantStat2Status = "data";
    },
    populateLoadServiceStat(state, action) {
      state.ServiceStatStatus = "loading";
    },
    populateDoneServiceStat(state, action) {
      state.ServiceStatStatus = "data";
    },
    populateLoadServiceStatEM(state, action) {
      state.ServiceStatStatusEM = "loading";
    },
    populateDoneServiceStatEM(state, action) {
      state.ServiceStatStatusEM = "data";
    },
    populateLoadServiceStatGC(state, action) {
      state.ServiceStatStatusGC = "loading";
    },
    populateDoneServiceStatGC(state, action) {
      state.ServiceStatStatusGC = "data";
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const selectResponsableStat = (state) => {
  return [
    state.persistedReducer.stat.ResponsableStat,
    state.persistedReducer.stat.errors,
  ];
};

export const selectEnseignantStat = (state) => {
  return [
    state.persistedReducer.stat.EnseignantStat,
    state.persistedReducer.stat.errors,
  ];
};

export const selectEnseignantStat2 = (state) => {
  return [
    state.persistedReducer.stat.EnseignantStat2,
    state.persistedReducer.stat.errors,
  ];
};

export const selectServiceStat = (state) => {
  return [
    state.persistedReducer.stat.ServiceStat,
    state.persistedReducer.stat.errors,
  ];
};

export const selectServiceStatEM = (state) => {
  return [
    state.persistedReducer.stat.ServiceStatEM,
    state.persistedReducer.stat.errors,
  ];
};

export const selectServiceStatGC = (state) => {
  return [
    state.persistedReducer.stat.ServiceStatGC,
    state.persistedReducer.stat.errors,
  ];
};

export const {
  setErrors,
  populateResponsableStat,
  populateEnseignantStat,
  populateEnseignantStat2,
  populateServiceStat,
  populateServiceStatEM,
  populateServiceStatGC,
  populateDoneServiceStat,
  populateDoneServiceStatEM,
  populateDoneServiceStatGC,
  populateDoneEnseignantStat,
  populateLoadEnseignantStat,
  populateDoneEnseignantStat2,
  populateDoneResponsableStat,
  populateLoadEnseignantStat2,
  populateLoadResponsableStat,
  populateLoadServiceStat,
  populateLoadServiceStatEM,
  populateLoadServiceStatGC
} = StatSlice.actions;
export default StatSlice.reducer;
