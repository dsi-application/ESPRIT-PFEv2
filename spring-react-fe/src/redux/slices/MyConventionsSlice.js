import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentStudent = AuthService.getCurrentStudent();

let initialState = {
  traitee: "", // HooksMe - 2
};
function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

// HooksMe - 3
export const fetchConventionTraitee = () => async (dispatch) =>
{
  const [res, error] = 
  await queryApi("student/existConventionByTRAITEE/" + currentStudent.id);

  if (error)
  {
     dispatch(setErrors(error));
  }
  else
  {
       dispatch(populateConventionTraitee(res));
  }
};

const MyConventionsSlice = createSlice({
  name: "myConventions",
  initialState,
  reducers: {
    // HooksMe - 4
    populateConventionTraitee(state, action) {
      state.traitee = action.payload;
      // console.log('----------------------------> OUTPUT A: ' + state.traitee)
    },
    populateConventionsHistoric(state, action) {
      state.conventionsHistoric = action.payload;
      // console.log('----------------------------> OUTPUT conventionsHistoric: ' + state.conventionsHistoric)
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

// HooksMe - 1
export const selectConventionTraitee = (state) => {

  // console.log('----------------------------> OUTPUT E: ', state)

  return [
    state.persistedReducer.myConventions.traitee,
    state.persistedReducer.myConventions.errors,
  ];
};

// HooksMe - 5 
export const {
  setErrors,
  populateConventionTraitee
} = MyConventionsSlice.actions;
export default MyConventionsSlice.reducer;
