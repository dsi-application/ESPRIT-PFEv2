import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";
const currentTeacher = AuthService.getCurrentTeacher();
let initialState = {
  fichePFEs: [],
  fichePFEsStatus: "noData",
  selectedfichePFE: {},
  etudiant: {},
  errors: "",
};

export const fetchfichePFEs = () => async (dispatch) => {
  // console.log("test")
  // console.log("test",encodeURIComponent(JSON.stringify(currentTeacher.codeOptions)))
  dispatch(populateLoadfichePFEs());
  const [res, error] = await queryApi(
    "responsableStage/FichePFEList?codeOption=" + encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populatefichePFEs(res));
    dispatch(populateDonefichePFEs());
  }
};

const fichePFESlice = createSlice({
  name: "fichePFEs",
  initialState,
  reducers: {
    populatefichePFEs(state, action) {
      state.fichePFEs = action.payload;
    },
    populateLoadfichePFEs(state, action) {
      state.fichePFEsStatus = "loading";
    },
    populateDonefichePFEs(state, action) {
      state.fichePFEsStatus = "data";
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    selectfiche(state, action) {
      state.selectedfichePFE = action.payload;
    },
    unselectfiche(state) {
      state.selectedfichePFE = null;
    },
    updateFiche: (state, action) => {
      const payload = action.payload;

      const index = state.fichePFEs.findIndex(
        (item) =>
          item.idEt === payload.idEt &&
          item.dateFiche === payload.dateFiche
      );

      if (index !== -1) {
        state.fichePFEs[index] = payload;
      }
    },

    getetudiant(state, action) {
      state.etudiant = action.payload;
    },
  },
});
export const selectetudiant = (state) => {
  return state.persistedReducer.fichePFEs.etudiant;
};

export const getEtudiant = (id) => async (dispatch) => {
  const [res, error] = await queryApi(
    "responsableStage/getEtudiant?idET=" + id
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(getetudiant(res));
  }
};
export const selectSelectedfichePFE = (state) => {
  return state.persistedReducer.fichePFEs.selectedfichePFE;
};

export const selectfichePFEs = (state) => {
  return [
    state.persistedReducer.fichePFEs.fichePFEs,
    state.persistedReducer.fichePFEs.errors,
  ];
};

export const {
  setErrors,
  populatefichePFEs,
  selectfiche,
  unselectfiche,
  updateFiche,
  getetudiant,
  populateDonefichePFEs,
  populateLoadfichePFEs,
} = fichePFESlice.actions;
export default fichePFESlice.reducer;
