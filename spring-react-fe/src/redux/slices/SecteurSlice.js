import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
let initialState = {
  secteurs: [],
  selectedSecteur: {},
  errors: "",
};

const SecteurSlice = createSlice({
  name: "secteurs",
  initialState,
  reducers: {
    populateSecteurs(state, action) {
      state.secteurs = action.payload;
    },
    selectSecteur(state, action) {
      state.selectedSecteur = action.payload;
    },
    unselectSecteur(state) {
      state.selectedSecteur = null;
    },
    deleteSecteur: (state, action) => {
      const payload = action.payload;

      const index = state.secteurs.findIndex(
        (item) => item.idSecteur === payload.idSecteur
      );

      if (index !== -1) {
        state.secteurs.splice(index, 1);
      }
    },
    updateSecteur: (state, action) => {
      const payload = action.payload;
      const index = state.secteurs.findIndex(
        (item) => item.idSecteur === payload.idSecteur
      );
      if (index !== -1) {
        state.secteurs[index] = payload;
      }
    },
    addSecteur: (state, action) => {
      const payload = action.payload;
      if (state.secteurs.length === 0) {
        state.secteurs = [];
        state.secteurs.unshift(payload);
      } else {
        state.secteurs.unshift(payload);
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const fetchSeceturs = () => async (dispatch) => {
  const [res, error] = await queryApi("config/secteurs");
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateSecteurs(res));
  }
};

export const selectSecteurs = (state) => {
  return [
    state.persistedReducer.secteurs.secteurs,
    state.persistedReducer.secteurs.errors,
  ];
};

export const selectSelectedSecteur = (state) => {
  return state.persistedReducer.secteurs.selectedSecteur;
};

export const {
  setErrors,
  addSecteur,
  updateSecteur,
  deleteSecteur,
  unselectSecteur,
  selectSecteur,
  populateSecteurs,
} = SecteurSlice.actions;

export default SecteurSlice.reducer;
