import { createSlice } from "@reduxjs/toolkit";
import moment from "moment";
import { queryApi } from "../../utils/queryApi";

import AuthService from "../../views/services/auth.service";
const currentTeacher = AuthService.getCurrentTeacher();
let initialState = {
  visits: [],
  visitsEns : [] ,
  selectedVisit: {},
  visitsStatus: "noData",
  errors: "",
};

export const fetchVisits = () => async (dispatch) => {
  dispatch(populateteVisitsLoad());
  const [res, error] = await queryApi(
      "encadrement/GetListVisitbyEns/" + currentTeacher.idEns
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateVisitsForEns(res));

    dispatch(populateteVisitsDone());
  }
};

export const fetchVisitsEtudiant = (id, date) => async (dispatch) => {

  const [res, error] = await queryApi(
      "encadrement/GetListVisitbyEtudiant?dateFiche=" + date + "&idET=" + id
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateVisits(res));


  }
};
const visitsSlice = createSlice({
  name: "visits",
  initialState,
  reducers: {
    populateVisitsForEns(state, action) {
      state.visitsEns = action.payload;
    },
    populateVisits(state, action) {
      state.visits = action.payload;
    },
    populateteVisitsLoad(state, action) {
      state.visitsStatus = "loading";
    },
    populateteVisitsDone(state, action) {
      state.visitsStatus = "done";
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    addVisit: (state, action) => {
      if (state.visits.length === 0) {
        state.visits = [];
        state.visits.unshift(action.payload);
      } else {
        state.visits.unshift(action.payload);
      }
    },
    updateVisit: (state, action) => {
      const payload = action.payload;
      // console.log("payload",payload);
      const index = state.visits.findIndex(
          (item) =>
              item.visiteStagiairePK.fichePFEPK.idEt ===
              payload[0].visiteStagiairePK.fichePFEPK.idEt &&
              item.visiteStagiairePK.fichePFEPK.dateDepotFiche ===
              payload[0].visiteStagiairePK.fichePFEPK.dateDepotFiche &&
              item.visiteStagiairePK.dateVisite === payload[0].visiteStagiairePK.dateVisite
      );

      if (index !== -1) {
        state.visits[index] = payload[1];
      }
    },
    updateVisitDate: (state, action) => {
      const payload = action.payload;
      const index = state.visits.findIndex(
          (item) =>
              item.visiteStagiairePK.fichePFEPK.idEt ===
              payload[0].visiteStagiairePK.fichePFEPK.idEt &&
              item.visiteStagiairePK.fichePFEPK.dateDepotFiche ===
              payload[0].visiteStagiairePK.fichePFEPK.dateDepotFiche &&
              moment(item.visiteStagiairePK.dateVisite)
                  .format("YYYY-MM-DD")
                  .toString() === payload[0].visiteStagiairePK.dateVisite
      );

      if (index !== -1) {
        state.visits[index] = payload[1];
      }
    },
    selectVisit(state, action) {
      state.selectedVisit = action.payload;
    },
    unselectVisit(state) {
      state.selectedVisit = null;
    },
    deleteVisit: (state, action) => {
      const payload = action.payload;
      const index = state.visits.findIndex(
          (item) =>
              item.visiteStagiairePK.fichePFEPK.idEt ===
              payload.visiteStagiairePK.fichePFEPK.idEt &&
              item.visiteStagiairePK.fichePFEPK.dateDepotFiche ===
              payload.visiteStagiairePK.fichePFEPK.dateDepotFiche &&
              item.visiteStagiairePK.dateVisite === payload.visiteStagiairePK.dateVisite
      );

      if (index !== -1) {
        state.visits.splice(index, 1);
      }
    },
  },
});

export const selectSelectedVisit = (state) => {
  return state.persistedReducer.visits.selectedVisit;
};
export const selectVisits = (state) => {
  return [
    state.persistedReducer.visits.visits,
    state.persistedReducer.visits.errors,
  ];
};
export const selectVisitsForEns = (state) => {
  return [
    state.persistedReducer.visits.visitsEns,
    state.persistedReducer.visits.errors,
  ];
};
export const {
  deleteVisit,
  updateVisitDate,
  selectVisit,
  unselectVisit,
  updateVisit,
  populatevisits,
  setErrors,
  populateVisits,
  addVisit,
  populateteVisitsDone,
  populateteVisitsLoad,
  populateVisitsForEns
} = visitsSlice.actions;
export default visitsSlice.reducer;
