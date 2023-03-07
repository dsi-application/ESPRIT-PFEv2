import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";

let initialState = {
  selectedEvaluation: {},
  evaluations: [],
  ListCompetences: [],
  errors: "",
};

const evaluationsSlice = createSlice({
  name: "evaluations",
  initialState,
  reducers: {
    populateEvaluations(state, action) {
      state.evaluations = action.payload;
    },
    populateListCompetences(state, action) {
      state.ListCompetences = action.payload;
    },
    addEvaluation: (state, action) => {
      if (state.evaluations.length === 0) {
        state.evaluations = [];
        state.evaluations.push(action.payload);
      } else {
        state.evaluations.push(action.payload);
      }
    },

    updateEvaluation: (state, action) => {
      const payload = action.payload;

      const index = state.evaluations.findIndex(
        (item) =>
          item.evaluationStagePK.fichePFEPK.idEt ===
            payload.evaluationStagePK.fichePFEPK.idEt &&
          item.evaluationStagePK.fichePFEPK.dateDepotFiche ===
            payload.evaluationStagePK.fichePFEPK.dateDepotFiche &&
          item.evaluationStagePK.codeEvaluation ===
            payload.evaluationStagePK.codeEvaluation
      );

      if (index !== -1) {
        state.evaluations[index] = payload;
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    selectEvaluation(state, action) {
      state.selectedEvaluation = action.payload;
    },
    unselectEvaluation(state) {
      state.selectedEvaluation = null;
    },
    deleteEvaluation: (state, action) => {
      const payload = action.payload;
      const index = state.evaluations.findIndex(
        (item) =>
          item.evaluationStagePK.fichePFEPK.idEt ===
            payload.evaluationStagePK.fichePFEPK.idEt &&
          item.evaluationStagePK.fichePFEPK.dateDepotFiche ===
            payload.evaluationStagePK.fichePFEPK.dateDepotFiche &&
          item.evaluationStagePK.codeEvaluation ===
            payload.evaluationStagePK.codeEvaluation
      );
      if (index !== -1) {
        state.evaluations.splice(index, 1);
      }
    },
  },
});

export const selectSelectedEvaluation = (state) => {
  return state.persistedReducer.evaluations.selectedEvaluation;
};
export const selectEvaluations = (state) => {
  return [
    state.persistedReducer.evaluations.evaluations,
    state.persistedReducer.evaluations.errors,
  ];
};

export const fecthEvaluations = (id, date) => async (dispatch) => {
  const [res, error] = await queryApi(
    "encadrement/EncadrantPedaEvalList?idET=" + id + "&dateFiche=" + date
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateEvaluations(res));
  }
};

export const selectListCompetences = (state) => {
  return [
    state.persistedReducer.evaluations.ListCompetences,
    state.persistedReducer.evaluations.errors,
  ];
};
export const fecthListCompetences = () => async (dispatch) => {
  const [res, error] = await queryApi("config/findCompetencesTypes");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateListCompetences(res));
  }
};
export const {
  deleteEvaluation,
  setErrors,
  populateEvaluations,
  addEvaluation,
  updateEvaluation,
  selectEvaluation,
  unselectEvaluation,
  populateListCompetences,
} = evaluationsSlice.actions;
export default evaluationsSlice.reducer;
