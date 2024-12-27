import { post } from '@/utils/request';

export default {
    getUserPageList: (query) => post('/api/admin/user/page/list', query),
    getUserEventPageList: (query) => post('/api/admin/user/event/page/list', query),
    createUser: (query) => post('/api/exam/user/edit', query),
    selectUser: (id) => post('/api/admin/user/select/' + id),
    getCurrentUser: () => post('/api/admin/user/current'),
    updateUser: (query) => post('/api/exam/user/update', query),
    changeStatus: (id) => post('/api/admin/user/changeStatus/' + id),
    deleteUser: (id) => post('/api/exam/user/delete/' + id),
    selectByUserName: (query) => post('/api/admin/user/selectByUserName', query),
    queryOrgList: (query) => post('api/exam/user/queryOrgList', query),
    getUserListByOrgNo: (query) => post('api/exam/user/getUserListByOrgNo', query),
    updatePwd: (query) => post('/pwd/updatePwd', query),
};
