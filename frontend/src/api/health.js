import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/health/'
});

const healthAPI = {
    async getALL() {
        return axiosInstance.get('all');
    },
    async getNoEncode() {
        return axiosInstance.get('noencode');
    },
}

export default healthAPI;