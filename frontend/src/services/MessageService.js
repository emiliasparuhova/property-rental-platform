import axios from '../axiosInstance';

const create = data => {
    return axios.post('/messages/create', data);
};

const MessageService = {
    create
};

export default MessageService; 