import axios from "../axiosInstance.js";

const get = id => {
    return axios.get(`/adverts/${id}`);
};

const getAll = data => {
    return axios.get(`/adverts`, data);
};

const getByLandlord = landlordId => {
    return axios.get(`/adverts/user/${landlordId}`)
}

const getStatisticsByCity = city => {
    return axios.get(`/adverts/statistics/${city}`)
}

const getAdvertsCount = data => {
    return axios.get("/adverts/count", data)
}

const getMostPopular = advertCount => {
    return axios.get("/adverts/most-popular", { params: { advertCount } })
}

const create = data => {
    return axios.post('/adverts', data);
};
  
const update = (id, data) => {
    return axios.put(`/adverts/${id}`, data);
};
  
const remove = id => {
    return axios.delete(`/adverts/${id}`);
};

const AdvertService = {
    get,
    getAll,
    getByLandlord,
    getStatisticsByCity,
    getAdvertsCount,
    getMostPopular,
    create,
    update, 
    remove
};

export default AdvertService; 