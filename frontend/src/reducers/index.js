import { combineReducers } from 'redux';
import authReducer from './AuthenticationReducer.js';


export default combineReducers({
  auth: authReducer,
});
