import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";
import {populateVerifyAffectPEtoStudent} from "./MyStudentSlice";

let initialState = {
    studentMsg: "--", // HooksMe - 2
    dateDepotFichePFE: "--",
    timelineSteps: [],
    fichePFEDetails: {},
    depotReportsDetails: {},
    activeStudentTimelineStep: "",
    studentFullName: "",
    studentId: "",
    studentFullId: "",
    conventionsHistoric: [],
    fichePFEsHistoric: [],
    //companyForFichePFEHistoric: {}
    //companySupervisorsForFichePFEHistoric: []
    studenExpert: {},
};

function timeout(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}

// HooksMe - 3
/*export const fetchCompanyForFichePFEHistoric = (currentStudentTBS) => async (dispatch) =>
{
    console.log('------------3011----------------> CompanyForFichePFEHistoric: ', currentStudentTBS)

    const [res, error] =
        await queryApi("student/entrepriseAccueilForFichePFEHistDtoByIdEt/" + currentStudentTBS);
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        console.log('----------------------------> lol 0512: ', res)
        dispatch(populateCompanyForFichePFEHistoric(res));
    }
};*/

/*export const fetchCompanySupervisorsForFichePFEHistoric = (currentStudentTBS) => async (dispatch) =>
{
    console.log('------------3011----------------> CompanySupervisorsForFichePFEHistoric: ', currentStudentTBS)

    const [res, error] =
        await queryApi("student/entrepriseSupervisorsByStudent/" + currentStudentTBS);
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        console.log('----------------------------> lol 3011: ', res)
        dispatch(populateCompanySupervisorsForFichePFEHistoric(res));
    }
};*/

export const fetchConventionsHistoric = (currentStudentTBS) => async (dispatch) =>
{
    // console.log('------------3011----------------> currentStudentTBS: ', currentStudentTBS)

    const [res, error] =
        await queryApi("student/studentConventionsHistoric/" + currentStudentTBS);
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        // console.log('----------------------------> lol 3011: ', res)
        dispatch(populateConventionsHistoric(res));
    }
};

export const fetchFichePFEsHistoric = (currentStudentTBS) => async (dispatch) =>
{
    // console.log('------------1905----------------> fetchFichePFEsHistoric: ', currentStudentTBS)

    const [res, error] =
        await queryApi("student/studentFichePFEsHistoric/" + currentStudentTBS);
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        // console.log('----------------------------> lol 1905: ', res)
        dispatch(populateFichePFEsHistoric(res));
    }
};

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

export const fetchStudentExpert = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] =
        await queryApi("pedaCoord/findExpertByStudent/" + currentStudentTBS);
    // console.log('-----1905-------------lol----------> fetchVerifyAffectPEtoStudent - 1', currentStudentTBS)
    // console.log('-----1905-------------lol----------> fetchVerifyAffectPEtoStudent - 2', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateGotStudentExpert(res));
    }
};

export const fetchTimelineSteps = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] = await queryApi("student/myStudentTBSTimelineByIdEt/" + currentStudentTBS);
    // console.log('-------------------0412---------> studentTimelineByIdEt - 1')
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateTimelineSteps(res));
    }
};

export const fetchDateDepotFichePFE = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] = await queryApi("student/dateDepotFicheById/" + currentStudentTBS);
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

export const fetchConventionDetails = (currentStudentTBS) => async (dispatch) =>
{
    // console.log('----------2401------SLC---> currentStudentTBS: ' + currentStudentTBS);

    const [res, error] = await queryApi("student/conventionDateConvDateDebutStageStatus/" + currentStudentTBS);
    // console.log('-----------------currentStudentTBS----  2111: ', currentStudentTBS)
    // console.log('---------------------  2401: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        // console.log('----------2401------RES---> currentStudentTBS: ' + currentStudentTBS);
        dispatch(populateConventionDetails(res));
    }
};

export const fetchFichePFEDetails = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] = await queryApi("student/ficheTitreDateEtatByIdEt/" + currentStudentTBS);
    // console.log('-------------2111-----**---  fichePFEDetails: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateFichePFE(res));
    }
};

export const fetchDepotReportsDetails = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] = await queryApi("student/findDepotReportsStatusDateByIdEt/" + currentStudentTBS);
    // console.log('---------------------  findDepotReportsStatusDateByIdEt: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateDepotReports(res));
    }
};

export const fetchActiveStudentTimelineStep = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] = await queryApi("student/activeStudentTimelineStep/" + currentStudentTBS);
    // console.log('--------2111-------------  activeStudentTimelineStep - 1: ', currentStudentTBS)
    // console.log('--------2111-------------  activeStudentTimelineStep - 2: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateActiveStudentTimelineStep(res));
    }
};

export const fetchStudentFullName = (currentStudentTBS) => async (dispatch) =>
{
    const [res, error] = await queryApi("student/studentFullName/" + currentStudentTBS);
    // console.log('--------21111-------------  studentFullName - 1: ', res)
    if (error)
    {
        dispatch(setErrors(error));
    }
    else
    {
        dispatch(populateStudentFullName('État Avancement de l\'Étudiant - ' + res));
    }
};

export const fetchStudentId = (currentStudentTBS) => async (dispatch) =>
{
    dispatch(populateStudentId(currentStudentTBS));
};

const MyStudentTBSSlice = createSlice({
    name: "myStudentsTBS",
    initialState,
    reducers: {
        // HooksMe - 4
        /*populateCompanyForFichePFEHistoric(state, action) {
          state.companyForFichePFEHistoric = action.payload;
        },
        populateCompanySupervisorsForFichePFEHistoric(state, action) {
          state.companySupervisorsForFichePFEHistoric = action.payload;
        },*/
        populateStudentMsg(state, action) {
            state.studentMsg = action.payload;
        },
        populateGotStudentExpert(state, action) {
            // console.log('------1905-----------> HI: ', action.payload)
            state.studenExpert = action.payload;
        },
        populateDateDepotFichePFE(state, action) {
            state.dateDepotFichePFE = action.payload;
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
        populateStudentFullName(state, action) {
            state.studentFullName = action.payload;
            // console.log('----------------------------> Student FullName - 2')
        },
        populateStudentId(state, action) {
            state.studentId = action.payload;
            // console.log('----------------------------> Student Id - 2')
        },
        populateConventionsHistoric(state, action) {
            state.conventionsHistoric = action.payload;
            // console.log('----------------------------> OUTPUT conventionsHistoric: ' + state.conventionsHistoric)
        },
        populateFichePFEsHistoric(state, action) {
            state.fichePFEsHistoric = action.payload;
            // console.log('----------------------0112------> OUTPUT conventionsHistoric: ' + state.fichePFEsHistoric)
        },
        setErrors(state, action) {
            state.errors = action.payload;
        },
    },
});

// HooksMe - 1
/*export const gotCompanyForFichePFEHistoric = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.companyForFichePFEHistoric,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotCompanySupervisorsForFichePFEHistoric = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.companySupervisorsForFichePFEHistoric,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};*/

export const gotStudentMsg = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.studentMsg,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotDateDepotFichePFE = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.dateDepotFichePFE,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotTimelineSteps = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.timelineSteps,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotConventionDetails = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.conventionDetails,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotFichePFEDetails = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.fichePFEDetails,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotDepotReportsDetails = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.depotReportsDetails,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotActiveStudentTimelineStep = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.activeStudentTimelineStep,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotStudentExpert = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.studenExpert,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotStudentFullName = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.studentFullName,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const gotStudentId = (state) => {
    return [
        state.persistedReducer.myStudentsTBS.studentId,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const selectConventionsHistoric = (state) => {

    // console.log('----------------------3011------> ConventionsHistoric: ', state.persistedReducer.myStudentsTBS.conventionsHistoric)

    return [
        state.persistedReducer.myStudentsTBS.conventionsHistoric,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};

export const selectFichePFEsHistoric = (state) => {

    // console.log('----------------------3011------> selectFichePFEsHistoric: ', state.persistedReducer.myStudentsTBS.fichePFEsHistoric)

    return [
        state.persistedReducer.myStudentsTBS.fichePFEsHistoric,
        state.persistedReducer.myStudentsTBS.errors,
    ];
};


// HooksMe - 5
export const {
    setErrors,
    populateStudentMsg,
    populateGotStudentExpert,
    populateDateDepotFichePFE,
    populateTimelineSteps,
    populateConventionDetails,
    populateFichePFE,
    populateDepotReports,
    populateActiveStudentTimelineStep,
    populateStudentFullName,
    populateConventionsHistoric,
    populateFichePFEsHistoric,
    populateStudentId
} = MyStudentTBSSlice.actions;
export default MyStudentTBSSlice.reducer;
