import axios from 'axios';
import Cookies from 'js-cookie';
import { Message } from 'element-ui';
import vue from 'vue';

const request = function (loadtip, query) {
    if (Cookies.get('adminUserName') != undefined || query.url === '/api/user/login' || query.url === '/pwd/updatePwd') {
        let loading;
        if (loadtip) {
            loading = vue.prototype.$loading({
                lock: false,
                text: '正在加载中…',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.5)',
            });
        }
        return axios
            .request(query)
            .then((res) => {
                if (loadtip) {
                    loading.close();
                }
                if (res.data.code === 401) {
                    vue.prototype.$$router.push({ path: '/login' });
                    return Promise.reject(res.data);
                } else if (res.data.code === 500) {
                    return Promise.reject(res.data);
                } else if (res.data.code === 501) {
                    return Promise.reject(res.data);
                } else if (res.data.code === 502) {
                    vue.prototype.$$router.push({ path: '/login' });
                    return Promise.reject(res.data);
                } else {
                    return Promise.resolve(res.data);
                }
            })
            .catch((e) => {
                if (loadtip) {
                    loading.close();
                }
                vue.prototype.$message.error(e.message);
                return Promise.reject(e.message);
            });
    } else {
        Message.warning('无效的会话，或者会话已过期，请重新登录');
        vue.prototype.$$router.push({ path: '/login' });
    }
};

const post = function (url, params) {
    const query = {
        baseURL: process.env.VUE_APP_URL,
        url: url,
        method: 'post',
        withCredentials: true,
        timeout: 30000,
        data: params,
        headers: { 'Content-Type': 'application/json', 'request-ajax': true },
    };
    return request(false, query);
};

const postWithLoadTip = function (url, params) {
    const query = {
        baseURL: process.env.VUE_APP_URL,
        url: url,
        method: 'post',
        withCredentials: true,
        timeout: 30000,
        data: params,
        headers: { 'Content-Type': 'application/json', 'request-ajax': true },
    };
    return request(true, query);
};

const postWithOutLoadTip = function (url, params) {
    const query = {
        baseURL: process.env.VUE_APP_URL,
        url: url,
        method: 'post',
        withCredentials: true,
        timeout: 30000,
        data: params,
        headers: { 'Content-Type': 'application/json', 'request-ajax': true },
    };
    return request(false, query);
};

const get = function (url, params) {
    const query = {
        baseURL: process.env.VUE_APP_URL,
        url: url,
        method: 'get',
        withCredentials: true,
        timeout: 30000,
        params: params,
        headers: { 'request-ajax': true },
    };
    return request(false, query);
};

const form = function (url, params) {
    const query = {
        baseURL: process.env.VUE_APP_URL,
        url: url,
        method: 'post',
        withCredentials: true,
        timeout: 30000,
        data: params,
        headers: { 'Content-Type': 'multipart/form-data', 'request-ajax': true },
    };
    return request(false, query);
};

export { post, postWithLoadTip, postWithOutLoadTip, get, form };
