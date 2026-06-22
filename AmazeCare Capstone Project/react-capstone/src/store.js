import { configureStore } from "@reduxjs/toolkit";
import { reportReducer } from "./store/reducer/reportReducer";

export const store = configureStore({
  reducer: {
    reports: reportReducer,
    
  }
})