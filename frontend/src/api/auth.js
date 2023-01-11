import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/auth/'
});

const authAPI = {
    async login(username, password) {
        return axiosInstance.post('login', { username, password });
    },

    async register(username, password) {
        return axiosInstance.post('register', { username, password });
    },
}

export default authAPI;