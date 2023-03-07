import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
import AuthService from "../../views/services/auth.service";
import {populateRefusedReports} from "./DepotSlice";

const currentResponsableServiceStage = AuthService.getCurrentResponsableServiceStage();

let initialState = {
  conventions: [],
  conventionsForRSS: [],
  conventionsValidatedForRSS: [],
  demandesAnnulationConventions: [],
  selectedConvention: {},
  Fichebydep: [],
  FichebydepVal: [],
  FichebydepInc: [],
  errors: "",
  FichestatusALL: "noData",
  Conventionsstatus: "noData",
  ConventionsstatusForRSS: "noData",
  DemandesAnnulationConventionsstatus: "noData",
  Fichebydepstatus: "noData",
  FichebydepstatusVal: "noData",
  FichebydepstatusInc: "noData",
  nbrDemandesAnnulationConvention: 0,
  nbrDemandesAnnulationConventionNotTreated: 0,
  nbrDepositedConvention: 0,
  nbrValidatedConvention: 0
};

function timeout(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export const fetchConventions = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/allConventionList"
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadConventions());
    dispatch(populateConventions(res));
    await timeout(8000);
    dispatch(populateDoneConventions());
  }
};


/*export const fetchConventionsForRSS = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/allConventionListForRSS"
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadConventionsForRSS());
    dispatch(populateConventionsForRSS(res));
    await timeout(8000);
    dispatch(populateDoneConventionsForRSS());
  }
};*/

export const fetchConventionsForRSS = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/allConventionListByRSS?idRSS=" + currentResponsableServiceStage.id
  );

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadConventionsForRSS());
    dispatch(populateConventionsForRSS(res));
    await timeout(8000);
    dispatch(populateDoneConventionsForRSS());
  }
};

export const fetchConventionsValidatedForRSS = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/allConventionValidatedListByRSS?idRSS=" + currentResponsableServiceStage.id
  );

  console.log('--------------1-------> 05.08: ', res);

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadConventionsForRSS());
    dispatch(populateConventionsValidatedForRSS(res));
    await timeout(8000);
    dispatch(populateDoneConventionsForRSS());
  }
};

export const fetchDemandesAnnulationConventions = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/allDemandesAnnulationConventionList"
  );

  //console.log('------------------ DAC: ', res);

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadDemandesAnnulationConventions());
    dispatch(populateDemandesAnnulationConventions(res));
    await timeout(8000);
    dispatch(populateDoneDemandesAnnulationConventions());
  }
};

export const fetchFichebydep = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/FichesForDepot?id=" + currentResponsableServiceStage.id
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadFichebydep());
    dispatch(populateFichebydeps(res));
    await timeout(50);
    dispatch(populateDoneFichebydep());
  }
};

export const fetchFichebydepVal = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/FichesForDepotVal?id=" + currentResponsableServiceStage.id
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadFichebydepVal());
    dispatch(populateFichebydepsVal(res));
    await timeout(50);
    dispatch(populateDoneFichebydepVal());
  }
};

export const fetchFicheValALL = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/FichesDepotValALL"
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    console.log('----------------LOL25 - 1: ', res)
    dispatch(populateLoadFicheValALL());
    console.log('----------------LOL25 - 2: ', res)
    dispatch(populateFicheValALL(res));
    console.log('----------------LOL25 - 3: ', res)
    await timeout(50);
    dispatch(populateDoneFicheValALL());
    console.log('----------------LOL25 - 4: ', res)
  }
};

export const fetchFichebydepInc = () => async (dispatch) => {
  const [res, error] = await queryApi(
      "serviceStage/FichesForDepotInc?id=" + currentResponsableServiceStage.id
  );
  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateLoadFichebydepInc());
    dispatch(populateFichebydepsInc(res));
    await timeout(50);
    dispatch(populateDoneFichebydepInc());
  }
};

export const fetchNbrDemandesAnnulationConvention = () => async (dispatch) =>
{
  //console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
      await queryApi("student/nbrDemandesAnnulationConvention");

  if (error)
  {
    dispatch(setErrors(error));
  }
  else
  {
    //console.log('-----------------------------------------------------> LOL20211 c:' + res);
    dispatch(populateDemandesAnnulationConvention(res));
  }
};

export const fetchNbrDemandesAnnulationConventionNotTreated = () => async (dispatch) =>
{
  //console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
      await queryApi("student/nbrDemandesAnnulationConventionNotTreated");

  if (error)
  {
    dispatch(setErrors(error));
  }
  else
  {
    // console.log('-----------------------------------------------------> LOL22122021 c:' + res);
    dispatch(populateDemandesAnnulationConventionNotTreated(res));
  }
};

export const fetchNbrDepositedConventions = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
      await queryApi("student/nbrDepositedConvention");

  if (error)
  {
    dispatch(setErrors(error));
  }
  else
  {
    // console.log('---------------------------------------------1811--------> LOL20211 c:' + res);
    dispatch(populateDepositedConventions(res));
  }
};

export const fetchNbrValidatedConventions = () => async (dispatch) =>
{
  // console.log('----------------> OUTPUT-0.1:' + res);
  const [res, error] =
      await queryApi("student/nbrValidatedConvention");

  if (error)
  {
    dispatch(setErrors(error));
  }
  else
  {
    // console.log('---------------------------------------------1811--------> LOL20211 c:' + res);
    dispatch(populateValidatedConventions(res));
  }
};

export const selectSelectedConvention = (state) => {
  return state.persistedReducer.conventions.selectedConvention;
};

export const selectConventions = (state) => {
  return [
    state.persistedReducer.conventions.conventions,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectConventionsForRSS = (state) => {
  return [
    state.persistedReducer.conventions.conventionsForRSS,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectConventionsValidatedForRSS = (state) => {
  return [
    state.persistedReducer.conventions.conventionsValidatedForRSS,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectDemandesAnnulationConventions = (state) => {
  return [
    state.persistedReducer.conventions.demandesAnnulationConventions,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectFichebydep = (state) => {
  return [
    state.persistedReducer.conventions.Fichebydep,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectFichebydepVal = (state) => {
  return [
    state.persistedReducer.conventions.FichebydepVal,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectFicheValALL = (state) => {
  return [
    state.persistedReducer.conventions.FicheValALL,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectFichebydepInc = (state) => {
  return [
    state.persistedReducer.conventions.FichebydepInc,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectNbrDemandesAnnulationConvention = (state) => {
  return [
    state.persistedReducer.conventions.nbrDemandesAnnulationConvention,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectNbrDemandesAnnulationConventionNotTreated = (state) => {
  return [
    state.persistedReducer.conventions.nbrDemandesAnnulationConventionNotTreated,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectNbrDepositedConventions = (state) => {
  return [
    state.persistedReducer.conventions.nbrDepositedConvention,
    state.persistedReducer.conventions.errors,
  ];
};

export const selectNbrValidatedConventions = (state) => {
  return [
    state.persistedReducer.conventions.nbrValidatedConvention,
    state.persistedReducer.conventions.errors,
  ];
};

const conventionSlice = createSlice({
  name: "conventions",
  initialState,
  reducers: {
    populateConventions(state, action) {
      state.conventions = action.payload;
    },
    populateConventionsForRSS(state, action) {
      state.conventionsForRSS = action.payload;
    },
    populateConventionsValidatedForRSS(state, action) {
      state.conventionsValidatedForRSS = action.payload;
    },
    populateDemandesAnnulationConventions(state, action) {
      state.demandesAnnulationConventions = action.payload;
    },
    populateLoadConventions(state, action) {
      state.Conventionsstatus = "loading";
    },
    populateLoadConventionsForRSS(state, action) {
      state.ConventionsstatusForRSS = "loading";
    },
    populateLoadDemandesAnnulationConventions(state, action) {
      state.DemandesAnnulationConventionsstatus = "loading";
    },
    populateDoneConventions(state, action) {
      state.Conventionsstatus = "data";
    },
    populateDoneConventionsForRSS(state, action) {
      state.ConventionsstatusForRSS = "data";
    },
    populateDoneDemandesAnnulationConventions(state, action) {
      state.DemandesAnnulationConventionsstatus = "data";
    },
    populateFichebydeps(state, action) {
      state.Fichebydep = action.payload;
    },
    populateLoadFichebydep(state, action) {
      state.Fichebydepstatus = "loading";
    },
    populateDoneFichebydep(state, action) {
      state.Fichebydepstatus = "data";
    },
    populateFichebydepsVal(state, action) {
      state.FichebydepVal = action.payload;
    },
    populateLoadFichebydepVal(state, action) {
      state.FichebydepstatusVal = "loading";
    },
    populateDoneFichebydepVal(state, action) {
      state.FichebydepstatusVal = "data";
    },
    populateFichebydepsInc(state, action) {
      state.FichebydepInc = action.payload;
    },
    populateLoadFichebydepInc(state, action) {
      state.FichebydepstatusInc = "loading";
    },
    populateDoneFichebydepInc(state, action) {
      state.FichebydepstatusInc = "data";
    },
    populateFicheValALL(state, action) {
      state.FicheValALL = action.payload;
    },
    populateLoadFicheValALL(state, action) {
      state.FichestatusValALL = "loading";
    },
    populateDoneFicheValALL(state, action) {
      state.FichestatusValALL = "data";
    },
    updateFichebydep: (state, action) => {
      const payload = action.payload;

      const index = state.Fichebydep.findIndex(
          (item) =>
              item.idEt === payload.idEt && item.dateFiche === payload.dateFiche
      );

      if (index !== -1) {
        state.Fichebydep[index] = payload;
      }
    },
    deleteElem: (state, action) => {
      const payload = action.payload;

      const index = state.Fichebydep.findIndex(
          (item) =>
              item.idEt === payload.idEt && item.dateFiche === payload.dateFiche
      );

      if (index !== -1) {
        state.Fichebydep.splice(index, 1);
      }
    },
    selectConvention(state, action) {
      state.selectedConvention = action.payload;
    },
    unselectConvention(state) {
      state.selectedConvention = null;
    },
    updateConvention: (state, action) => {
      const payload = action.payload;
      // console.log("payload", payload);
      const index = state.conventions.findIndex(
          (item) =>
              item.conventionPK.idEt === payload.conventionPK.idEt &&
              item.conventionPK.dateConvention ===
              payload.conventionPK.dateConvention
      );

      if (index !== -1) {
        state.conventions[index] = payload;
      }
    },
    selectConventionForRSS(state, action) {
      state.selectedConventionForRSS = action.payload;
    },
    unselectConventionForRSS(state) {
      state.selectedConventionForRSS = null;
    },
    updateConventionForRSS: (state, action) => {
      const payload = action.payload;

      console.log("----------zzd------- 2005 3.0 ----->", payload);

      const index = state.conventionsForRSS.findIndex(
          (item) =>
              item.conventionPK.idEt === payload.conventionPK.idEt &&
              item.conventionPK.dateConvention ===
              payload.conventionPK.dateConvention
      );

      console.log("--------SARS* 1--------- 0905 3.0 ----->" + index);
      console.log("--------SARS* 2--------- 0905 3.0 ----->" + payload.conventionPK.dateConvention);

      if (index !== -1) {
        state.conventionsForRSS[index] = payload;
        console.log("----------------- 0905 3.1 ----->", index);
        console.log("----------------- 0905 3.2 ----->", payload);
      }
    },
    updateConventionDtoForRSS: (state, action) => {
      const payload = action.payload;

      const index = state.conventionsForRSS.findIndex(
          (item) =>
              item.idEt === payload.idEt &&
              item.dateConvention === payload.dateConvention
      );

      // console.log("-----------------1702-2----->" + index + " - " + payload.conventionPK.dateConvention);
      if (index !== -1) {
        state.conventionsForRSS[index] = payload;
        console.log("-----------------1702-3----->", index);
        console.log("-----------------1702-4----->", payload);
      }
    },
    updateDemandeAnnulationConvention: (state, action) => {
      const payload = action.payload;
      // console.log("payload", payload);
      const index = state.demandesAnnulationConventions.findIndex(
          (item) =>
              item.conventionPK.idEt === payload.conventionPK.idEt &&
              item.conventionPK.dateConvention === payload.conventionPK.dateConvention
      );

      if (index !== -1) {
        state.demandesAnnulationConventions[index] = payload;
      }
    },
    populateDemandesAnnulationConvention(state, action) {
      state.nbrDemandesAnnulationConvention = action.payload;
      // console.log('----------------------------> nbrDemandesAnnulationConvention')
    },
    populateDemandesAnnulationConventionNotTreated(state, action) {
      state.nbrDemandesAnnulationConventionNotTreated = action.payload;
      // console.log('----------------------------> nbrDemandesAnnulationConventionNotTreated')
    },
    populateDepositedConventions(state, action) {
      state.nbrDepositedConvention = action.payload;
      // console.log('----------------------------> nbrDepositedConvention')
    },
    populateValidatedConventions(state, action) {
      state.nbrValidatedConvention = action.payload;
      // console.log('----------------------------> nbrValidatedConvention')
    },
    setErrors(state, action) {
      state.errors = action.payload;
    }
  },
});

export const {
  setErrors,
  populateDemandesAnnulationConventionNotTreated,
  populateConventions,
  populateValidatedConventions,
  populateDemandesAnnulationConventions,
  populateConventionsForRSS,
  populateConventionsValidatedForRSS,
  updateFichebydep,
  selectConvention,
  unselectConvention,
  updateConvention,
  updateConventionDtoForRSS,
  updateDemandeAnnulationConvention,
  populateFichebydeps,
  populateFichebydepsVal,
  populateFichebydepsInc,
  populateLoadConventions,
  populateLoadDemandesAnnulationConventions,
  populateDoneConventions,
  populateDoneConventionsForRSS,
  populateDoneDemandesAnnulationConventions,
  populateDoneFichebydep,
  populateLoadFichebydep,
  populateDoneFichebydepVal,
  populateLoadFichebydepVal,
  populateDoneFichebydepInc,
  populateLoadFichebydepInc,
  deleteElem,
  populateDemandesAnnulationConvention,
  populateLoadConventionsForRSS,
  populateDepositedConventions,
  updateConventionForRSS,
  populateFicheValALL,
  populateLoadFicheValALL,
  populateDoneFicheValALL
} = conventionSlice.actions;
export default conventionSlice.reducer;
