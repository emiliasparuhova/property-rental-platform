import axios from '../axiosInstance';

const get = userId => {
    return axios.get(`/linked-account/${userId}`);
};

const create = data => {
    return axios.post('/linked-account', data);
};

const remove = id => {
    return axios.delete(`/linked-account/${id}`);
};

const LinkedAccountService = {
    get,
    create,
    remove
};

export default LinkedAccountService; 