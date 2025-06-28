import axios from "../axiosInstance.js";

const get = id => {
    return axios.get(`/users/${id}`); 
  };

const getUserByToken = () => {
    return axios.get(`/users/accessToken`)
}

const getLandlordResponseRate = id => {
    return axios.get(`/users/landlord-response-rate/${id}`)
}
  
const create = data => {
    return axios.post('/users', data);

};
  
const update = (id, data) => {
    return axios.put(`/users/${id}`, data);
};
  
const remove = id => {
    return axios.delete(`/users/${id}`);
};

  const UserService = {
    get,
    getUserByToken,
    getLandlordResponseRate,
    create,
    update, 
    remove
  };

export default UserService; 