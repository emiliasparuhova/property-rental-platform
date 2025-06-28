import axios from "../axiosInstance.js";

const getGoogleRedirectUri = () => {
  return axios.get(`/auth/google`);
};


  const OAuth2Service = {
    getGoogleRedirectUri
  };

export default OAuth2Service; 