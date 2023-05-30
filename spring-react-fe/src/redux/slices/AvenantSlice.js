import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentResponsableServiceStage =
  AuthService.getCurrentResponsableServiceStage();
let initialState = {
  avenants: [],
  selectedAvenant: {},
  avenantsstatus: "noData",
  errors: "",
};

export const fetchAvenants = () => async (dispatch) => {
  dispatch(populateLoadAvenants());
  const [res, error] = await queryApi(
    "serviceStage/allAvenantList"
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateAvenants(res));

    dispatch(populateDoneAvenants());
  }
};
const avenantSlice = createSlice({
  name: "avenants",
  initialState,
  reducers: {
    populateAvenants(state, action) {
      state.avenants = action.payload;
    },
    populateLoadAvenants(state, action) {
      state.avenantsstatus = "loading";
    },
    populateDoneAvenants(state, action) {
      state.avenantsstatus = "data";
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    selectAvenant(state, action) {
      state.selectedAvenant = action.payload;
    },
    unselectAvenant(state) {
      state.selectedAvenant = null;
    },
    updateAvenant: (state, action) => {
      const payload = action.payload;

      const index = state.avenants.findIndex(
        (item) =>
          item.avenantPK.conventionPK.idEt === payload.idEt &&
          item.avenantPK.conventionPK.dateConvention === payload.dateDepotRelatedConvention &&
          item.avenantPK.dateAvenant === payload.dateDepotAvenant
      );

      if (index !== -1) {
        state.avenants[index] = payload;
      }
    },
  },
});

export const selectSelectedAvenant = (state) => {
  return state.persistedReducer.avenants.selectedAvenant;
};

export const selectAvenants = (state) => {
  return [
    state.persistedReducer.avenants.avenants,
    state.persistedReducer.avenants.errors,
  ];
};

export const {
  setErrors,
  populateAvenants,
  selectAvenant,
  unselectAvenant,
  updateAvenant,
  populateDoneAvenants,
  populateLoadAvenants,
} = avenantSlice.actions;
export default avenantSlice.reducer;
