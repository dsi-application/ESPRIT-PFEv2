import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";
const currentTeacher = AuthService.getCurrentTeacher();
let initialState = {
  requests: [],
  selectedrequest: {},
  fichePFE: {},
  errors: "",
  status: "noData",
};

export const fetchRequests = () => async (dispatch) => {
  dispatch(populateLoadRequests());
  const [res, error] = await queryApi(
    "responsableStage/TraitementFichePFEList?codeOption=" +
    encodeURIComponent(JSON.stringify(currentTeacher.codeOptions))
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateRequests(res));

    dispatch(populateDoneRequests());
  }
};
const RequestsSlice = createSlice({
  name: "requests",
  initialState,
  reducers: {
    populateRequests(state, action) {
      state.requests = action.payload;
    },
    populateLoadRequests(state, action) {
      state.status = "loading";
    },
    populateDoneRequests(state, action) {
      state.status = "data";
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
    selectrequest(state, action) {
      state.selectedrequest = action.payload;
    },
    unselectrequest(state) {
      state.selectedrequest = null;
    },
    updateRequest: (state, action) => {
      const payload = action.payload;

      const index = state.requests.findIndex(
        (item) =>
          item.traitementFichePK.fichePFEPK.idEt ===
            payload.traitementFichePK.fichePFEPK.idEt &&
          item.traitementFichePK.fichePFEPK.dateDepotFiche ===
            payload.traitementFichePK.fichePFEPK.dateDepotFiche &&
          item.traitementFichePK.dateTrtFiche ===
            payload.traitementFichePK.dateTrtFiche
      );

      if (index !== -1) {
        state.requests[index] = payload;
      }
    },
    getfichePFE(state, action) {
      state.fichePFE = action.payload;
    },
  },
});
export const selectfichePFE = (state) => {
  return state.persistedReducer.requests.fichePFE;
};
export const getFiche = (id, date) => async (dispatch) => {
  // console.log("data==",id,date)
  const [res, error] = await queryApi(
    "responsableStage/getFiche?idET=" + id + "&dateFiche=" + date
  );
  // console.log("res==",res)
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(getfichePFE(res));

  }
};
export const selectSelectedRequest = (state) => {
  return state.persistedReducer.requests.selectedrequest;
};

export const selectrequests = (state) => {
  return [
    state.persistedReducer.requests.requests,
    state.persistedReducer.requests.errors,
  ];
};

export const {
  setErrors,
  populateRequests,
  selectrequest,
  unselectrequest,
  updateRequest,
  getfichePFE,
  populateLoadRequests,
  populateDoneRequests,
} = RequestsSlice.actions;
export default RequestsSlice.reducer;
