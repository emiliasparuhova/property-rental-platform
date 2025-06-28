import axios from '../axiosInstance';


const getCitiesInTheNetherlands = () => {
  return axios.get('/api/countries/NL');
};

const CountryService = {
    getCitiesInTheNetherlands,
};

export default CountryService;
