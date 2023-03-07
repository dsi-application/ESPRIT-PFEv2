import { createSlice } from "@reduxjs/toolkit";
import { queryApi } from "../../utils/queryApi";
let initialState = {
  sessions: [],
  selectedSession: {},
  errors: "",
};

const SessionSlice = createSlice({
  name: "sessions",
  initialState,
  reducers: {
    populateSessions(state, action) {
      state.sessions = action.payload;
    },
    selectSession(state, action) {
      state.selectedSession = action.payload;
    },
    unselectSession(state) {
      state.selectedSession = null;
    },
    deleteSession: (state, action) => {
      const payload = action.payload;

      const index = state.sessions.findIndex(
        (item) => item.idSession === payload.idSession
      );

      if (index !== -1) {
        state.sessions.splice(index, 1);
      }
    },
    updateSession: (state, action) => {
      const payload = action.payload;
      const index = state.sessions.findIndex(
        (item) => item.idSession === payload.idSession
      );
      if (index !== -1) {
        state.sessions[index] = payload;
      }
    },
    addSession: (state, action) => {
      const payload = action.payload;
      if (state.sessions.length === 0) {
        state.sessions = [];
        state.sessions.unshift(payload);
      } else {
        state.sessions.unshift(payload);
      }
    },
    setErrors(state, action) {
      state.errors = action.payload;
    },
  },
});

export const fetchSessions = () => async (dispatch) => {
  const [res, error] = await queryApi("config/sessions");

  if (error) {
    dispatch(setErrors(error));
  } else {
    dispatch(populateSessions(res));
  }
};

export const selectSessions = (state) => {
  return [
    state.persistedReducer.sessions.sessions,
    state.persistedReducer.sessions.errors,
  ];
};

export const selectSelectedSession = (state) => {
  return state.persistedReducer.sessions.selectedSession;
};

export const {
  setErrors,
  addSession,
  updateSession,
  deleteSession,
  unselectSession,
  selectSession,
  populateSessions,
} = SessionSlice.actions;

export default SessionSlice.reducer;
