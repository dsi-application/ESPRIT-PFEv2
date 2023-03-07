import { configureStore } from "@reduxjs/toolkit";
import rootReducers from "../src/redux/reducers";
import storage from "redux-persist/lib/storage";
import { persistReducer } from "redux-persist";
import thunk from "redux-thunk";

import {createStore} from 'redux'
import rootReducer from './redux/reducers'
import {Provider} from 'react-redux'

const initialState = {
  sidebarShow: "responsive",
};
const persistConfig = {
  key: "root",
  storage,
};
const changeState = (state = initialState, { type, ...rest }) => {
  switch (type) {
    case "set":
      return { ...state, ...rest };
    default:
      return state;
  }
};
const persistedReducer = persistReducer(persistConfig, rootReducers);

/*const store = createStore(
    rootReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__() 
)*/

export default configureStore({
  reducer: { persistedReducer, changeState },
  middleware: [thunk],
});

//export default store ;




