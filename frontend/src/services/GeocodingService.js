import axios from '../axiosInstance';


const getCoordinatesByAddress = address => {
  return axios.get(`/api/geocoding/${address}`);
};

const GeocodingService = {
    getCoordinatesByAddress,
};

export default GeocodingService;