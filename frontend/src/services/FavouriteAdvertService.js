import axios from "../axiosInstance.js";

const getByUser = data => {
    return axios.get(`/favourite-adverts`, data);
};

const getIsAdvertFavourite = data => {
    return axios.get(`/favourite-adverts/isFavourite`, data);
}

const create = data => {
    return axios.post('/favourite-adverts', data);
};
  
  
const remove = (userId, advertId) => {
    return axios.delete(`/favourite-adverts/${userId}/${advertId}`);
};

const FavouriteAdvertService = {
    getByUser,
    getIsAdvertFavourite,
    create,
    remove
  };

export default FavouriteAdvertService; 