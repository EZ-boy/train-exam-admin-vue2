import Cookies from 'js-cookie';
import userApi from '@/api/user';
// initial state
const state = {
    userName: Cookies.get('adminUserName'),
    userInfo: Cookies.get('adminUserInfo') != null ? JSON.parse(Cookies.get('adminUserInfo')) : Cookies.get('adminUserInfo'),
};

// actions
const actions = {
    initUserInfo({ commit }) {
        userApi.getCurrentUser().then((re) => {
            commit('setUserInfo', re.response);
        });
    },
};

// mutations
const mutations = {
    setUserName(state, userName) {
        let seconds = 24 * 60 * 60;
        let expires2 = new Date(new Date() * 1 + seconds * 1000);
        state.userName = userName;
        Cookies.set('adminUserName', userName, { expires: expires2 });
    },
    setUserInfo: (state, userInfo) => {
        let seconds = 24 * 60 * 60;
        let expires2 = new Date(new Date() * 1 + seconds * 1000);
        state.userInfo = userInfo;
        Cookies.set('adminUserInfo', userInfo, { expires: expires2 });
    },
    clearLogin(state) {
        state.userName = '';
        state.userInfo = {};
        Cookies.remove('adminUserName');
        Cookies.remove('adminUserInfo');
    },
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
};
