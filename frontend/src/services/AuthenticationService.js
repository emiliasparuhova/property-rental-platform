import axios from "../axiosInstance.js";

const create = data => {
    return axios.post(`/auth`, data);
};

const oauthLogin = linkedAccountUserId => {
    return axios.post(`/auth/oauth`, linkedAccountUserId);
}

  const AuthenticationService = {
    create,
    oauthLogin
  }

export default AuthenticationService;