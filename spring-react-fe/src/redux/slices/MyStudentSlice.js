import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";

const currentStudent = AuthService.getCurrentStudent();

let initialState = {
  studentMsg: "--", // HooksMe - 2
  dateDepotFichePFE: "--",
  timelineSteps: [],
  conventionDetails: [],
  fichePFEDetails: {},
  depotReportsDetails: {},
  activeStudentTimelineStep: "",
  traitementFichePFE: {},
  fichePFE:{},
  allTypesTrtFPFE: [],
  libEtatFiche: "",
  myFichePFEsHistoric: [],
  studentGradCongrat: {},
  verifyAffectPEtoStudent: ""
};

function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

// HooksMe - 3
export const fetchStudentMsg = () => async (dispatch) =>
{
    const [res, error] =
    await queryApi("pedaCoord/sample");
    // console.log('----------------------------> LOL2810 - 1')
    if (error)
    {
       dispatch(setErrors(error));
    }
    else
    {
         dispatch(populateStudentMsg(res));
    }
};

export const fetchVerifyAffectPEtoStudent = () => async (dispatch) =>
{
    const [res, error] =
        await queryApi("student/verifyAffectPEtoStudent/" + currentStudent.id);
    // console.log('----------------------------> fetchVerifyAffectPEtoStudent - 1', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateVerifyAffectPEtoStudent(res));
    }
};

export const fetchTraitementFichePFE = () => async (dispatch) =>
{
    const [res, error] =
        await queryApi("student/traitementFichePFE/" + currentStudent.id);
    // console.log('----------------------------> fetchTraitementFichePFE - 1', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateTraitementFichePFE(res));
    }
};

export const fetchStudentGradCongrat = () => async (dispatch) =>
{
    const [res, error] =
        await queryApi("student/findStudentSTNCongratDtoByStudent/" + currentStudent.id);
    // console.log('--------------1212--------------> fetchTraitementFichePFE - 1', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateStudentGradCongrat(res));
    }
};

export const fetchAllTypesTrtFPFE = () => async (dispatch) =>
{
    const [res, error] =
        await queryApi("student/traitementFichePFEType");
    // console.log('----------------0812------------> fetchAllTypesTrtFPFE - 1', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateAllTypesTrtFPFE(res));
    }
};

export const fetchTimelineSteps = () => async (dispatch) =>
{
    const [res, error] =
        await queryApi("student/studentTimelineByIdEt/" + currentStudent.id);
    // console.log('----------------------------> studentTimelineByIdEt - 1')
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateTimelineSteps(res));
    }
};

export const fetchDateDepotFichePFE = () => async (dispatch) =>
{
    const [res, error] = await queryApi("student/dateDepotFicheById/" + currentStudent.id);
    // console.log('---------------------  0811: ', res)
    if (error)
    {
      dispatch(setErrors(error));
    }
    else
    {
      dispatch(populateDateDepotFichePFE(res));
    }
};

export const fetchConventionDetails = () => async (dispatch) =>
{
    const [res, error] = await queryApi("student/conventionDateConvDateDebutStageStatus/" + currentStudent.id);
    // console.log('---------------------  1611: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateConventionDetails(res));
    }
};

export const fetchFichePFEDetails = () => async (dispatch) =>
{
    const [res, error] = await queryApi("student/ficheTitreDateEtatByIdEt/" + currentStudent.id);
    // console.log('------------------HI08122---  fichePFEDetails: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateFichePFE(res));
    }
};

export const fetchDepotReportsDetails = () => async (dispatch) =>
{
    const [res, error] = await queryApi("student/findDepotReportsStatusDateByIdEt/" + currentStudent.id);
    console.log('-----------2203----------  findDepotReportsStatusDateByIdEt: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateDepotReports(res));
    }
};

export const fetchActiveStudentTimelineStep = () => async (dispatch) =>
{
    const [res, error] = await queryApi("student/activeStudentTimelineStep/" + currentStudent.id);
    // console.log('---------------------  activeStudentTimelineStep: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateActiveStudentTimelineStep(res));
    }
};

export const fetchMyFichesPFEsHistoric = () => async (dispatch) =>
{
    const [res, error] =
        await queryApi("student/studentFichePFEsHistoric/" + currentStudent.id);
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateMyFichesPFEsHistoric(res));
    }
};

const MyStudentSlice = createSlice({
  name: "myStudents",
  initialState,
  reducers: {
    // HooksMe - 4
    populateStudentMsg(state, action) {
      state.studentMsg = action.payload;
    },
    populateVerifyAffectPEtoStudent(state, action) {
      state.verifyAffectPEtoStudent = action.payload;
    },
    populateStudentGradCongrat(state, action) {
      state.studentGradCongrat = action.payload;
    },
    populateDateDepotFichePFE(state, action) {
      state.dateDepotFichePFE = action.payload;
      // console.log('----------------------------> LOL2810 - 2')
    },
    populateTraitementFichePFE(state, action) {
        state.traitementFichePFE = action.payload;
        // console.log('----------------------------> LOL2810 - 2')
    },
    populateAllTypesTrtFPFE(state, action) {
        state.allTypesTrtFPFE = action.payload;
        // console.log('----------------------------> LOL2810 - 2')
    },
    populateTimelineSteps(state, action) {
      state.timelineSteps = action.payload;
      // console.log('----------------------------> timelineSteps - 2')
    },
    populateConventionDetails(state, action) {
      state.conventionDetails = action.payload;
      // console.log('----------------------------> conventionDetails - 2')
    },
    populateFichePFE(state, action) {
      state.fichePFEDetails = action.payload;
      // console.log('----------------------------> fichePFEDetails - 2')
    },
    populateDepotReports(state, action) {
      state.depotReportsDetails = action.payload;
      // console.log('----------------------------> depotReportsDetails - 2')
    },
    populateActiveStudentTimelineStep(state, action) {
      state.activeStudentTimelineStep = action.payload;
      // console.log('----------------------------> activeStudentTimelineStep - 2')
    },
    populateMyFichesPFEsHistoric(state, action) {
      state.myFichePFEsHistoric = action.payload;
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

// HooksMe - 1
export const gotStudentMsg = (state) => {
  return [
    state.persistedReducer.myStudents.studentMsg,
    state.persistedReducer.myStudents.errors,
  ];
};

export const gotVerifyAffectPEtoStudent = (state) => {
    return [
        state.persistedReducer.myStudents.verifyAffectPEtoStudent,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotStudentGradCongrat = (state) => {
    return [
        state.persistedReducer.myStudents.studentGradCongrat,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotTraitementFichePFE = (state) => {
    return [
        state.persistedReducer.myStudents.traitementFichePFE,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotDateDepotFichePFE = (state) => {
  return [
    state.persistedReducer.myStudents.dateDepotFichePFE,
    state.persistedReducer.myStudents.errors,
  ];
};

export const gotAllTypesTrtFPFE = (state) => {
    return [
        state.persistedReducer.myStudents.allTypesTrtFPFE,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotTimelineSteps = (state) => {
    return [
        state.persistedReducer.myStudents.timelineSteps,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotConventionDetails = (state) => {
    return [
        state.persistedReducer.myStudents.conventionDetails,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotFichePFEDetails = (state) => {
    return [
        state.persistedReducer.myStudents.fichePFEDetails,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotDepotReportsDetails = (state) => {
    return [
        state.persistedReducer.myStudents.depotReportsDetails,
        state.persistedReducer.myStudents.errors,
    ];
};

export const gotActiveStudentTimelineStep = (state) => {
    return [
        state.persistedReducer.myStudents.activeStudentTimelineStep,
        state.persistedReducer.myStudents.errors,
    ];
};

export const selectMyFichesPFEsHistoric = (state) => {
    return [
        state.persistedReducer.myStudents.myFichePFEsHistoric,
        state.persistedReducer.myStudents.errors,
    ];
};


// HooksMe - 5
export const {
  setErrors,
  populateStudentMsg,
  populateStudentGradCongrat,
  populateTraitementFichePFE,
  populateDateDepotFichePFE,
  populateTimelineSteps,
  populateConventionDetails,
  populateFichePFE,
  populateDepotReports,
  populateActiveStudentTimelineStep,
  populateAllTypesTrtFPFE,
  populateMyFichesPFEsHistoric,
  populateVerifyAffectPEtoStudent
} = MyStudentSlice.actions;
export default MyStudentSlice.reducer;
