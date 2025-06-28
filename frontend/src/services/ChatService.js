import axios from '../axiosInstance';

const get = id => {
    return axios.get(`/chats/${id}`);
};

const getChatsByUser = userId => {
    return axios.get(`/chats/user/${userId}`)
}

const create = data => {
    return axios.post('/chats', data);
};

const ChatService = {
    get,
    getChatsByUser,
    create
};

export default ChatService; 