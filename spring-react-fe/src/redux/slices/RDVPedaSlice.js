import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";

let initialState = {
  rdvPedas: [],
  selectedrdvPedas: {},
  entreprises: [],
  errors: "",
};

const rdvPedaSlice = createSlice({
  name: "rdvPedas",
  initialState,
  reducers: {
    populateRdvPedas(state, action) {
      state.rdvPedas = action.payload;
    },
    populateEntreprises(state, action) {
      state.entreprises = action.payload;
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    addRdvPedas: (state, action) => {
      if (state.rdvPedas.length === 0) {
        state.rdvPedas = [];
        state.rdvPedas.push(action.payload);
      } else {
        state.rdvPedas.push(action.payload);
      }
    },
    updateRdvPeda: (state, action) => {
      const payload = action.payload;

      const index = state.rdvPedas.findIndex(
        (item) =>
          item.rdvSuiviStagePK.fichePFEPK.idEt ===
            payload.rdvSuiviStagePK.fichePFEPK.idEt &&
          item.rdvSuiviStagePK.fichePFEPK.dateDepotFiche ===
            payload.rdvSuiviStagePK.fichePFEPK.dateDepotFiche &&
          item.rdvSuiviStagePK.dateSaisieRendezVous ===
            payload.rdvSuiviStagePK.dateSaisieRendezVous
      );

      if (index !== -1) {
        state.rdvPedas[index] = payload;
      }
    },

    selectRdvPeda(state, action) {
      state.selectedrdvPedas = action.payload;
    },
    unselectRdvPeda(state) {
      state.selectedrdvPedas = null;
    },
    unselectRdvPedaList(state) {
      state.rdvPedas = [];
    },
  },
});

export const fetchRdvPedas = (id, dateFiche) => async (dispatch) => {
  const [res, error] = await queryApi(
    "encadrement/RdvSuiviStageListbyEt?idET=" + id + "&dateFiche=" + dateFiche
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateRdvPedas(res));
  }
};

export const fetchEntreprises = (id) => async (dispatch) => {
  const [res, error] = await queryApi("encadrement/entreprisebyEt?idET=" + id);

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEntreprises(res));
  }
};
export const selectEntreprises = (state) => {
  return [
    state.persistedReducer.rdvPedas.entreprises,
    state.persistedReducer.rdvPedas.errors,
  ];
};
export const selectSelectedrdv = (state) => {
  return state.persistedReducer.rdvPedas.selectedrdvPedas;
};
export const selectRdvPedas = (state) => {
  return [
    state.persistedReducer.rdvPedas.rdvPedas,
    state.persistedReducer.rdvPedas.errors,
  ];
};

export const {
  setErrors,
  populateRdvPedas,
  populateEntreprises,
  addRdvPedas,
  updateRdvPeda,
  selectRdvPeda,
  unselectRdvPeda,
  unselectRdvPedaList,
} = rdvPedaSlice.actions;
export default rdvPedaSlice.reducer;
