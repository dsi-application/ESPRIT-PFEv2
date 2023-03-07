import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentPedagogicalCoordinator =
  AuthService.getCurrentPedagogicalCoordinator();

let initialState = {
  classesByOption: [],
  classesByOptionStatus: "noData",
  errors: ""
};

export const fetchClassesByOption = () => async (dispatch) => {
  dispatch(populateClassesByOptionLoad());
  const [res, error] = await queryApi(
      "/auth/listClassesByOption/" + currentPedagogicalCoordinator.id
      //+ encodeURIComponent(JSON.stringify(currentPedagogicalCoordinator.id))
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateClassesByOption(res));

    dispatch(populateClassesByOptionDone());
  }
};

const pedagogicalCoordinatorSlice = createSlice({
  name: "pedagogicalCoordinatorPriv",
  initialState,
  reducers: {
    populateClassesByOption(state, action) {
      state.classesByOption = action.payload;
    },
    populateClassesByOptionLoad(state, action) {
      state.classesByOptionStatus = "loading";
    },
    populateClassesByOptionDone(state, action) {
      state.classesByOptionStatus = "done";
    },
  },
});

export const selectClassesByOption = (state) => {
  return [
    state.persistedReducer.pedagogicalCoordinatorPriv.classesByOption,
    state.persistedReducer.pedagogicalCoordinatorPriv.errors,
  ];
};

export const {
  setErrors,
  populateClassesByOption,
  populateClassesByOptionLoad,
  populateClassesByOptionDone
} = pedagogicalCoordinatorSlice.actions;
export default pedagogicalCoordinatorSlice.reducer;
