import { post, get } from '@/utils/request';

export default {
    getMajor: () => post('api/exam/major/init'),
    getTask: (query) => post('api/exam/task/init', query),
    getTaskListByMajor: (query) => post('api/exam/task/getTaskListByMajor', query),
    qryCoursewareList: (query) => post('api/exam/courseware/qryCoursewareList', query),
    deleteCoursewareById: (query) => post('/api/exam/courseware/deleteCoursewareById', query),
    insertMajor: (query) => post('api/exam/major/insertMajor', query),
    getMajorById: (query) => post('api/exam/major/getMajorById', query),
    updateMajor: (query) => post('api/exam/major/updateMajor', query),
    deleteTheme: (query) => get('api/exam/major/deleteTheme', query),
    deleteMajor: (query) => get('api/exam/major/deleteMajor', query),
    editTask: (query) => post('api/exam/task/edit', query),
    saveCourseware: (query) => post('/api/exam/courseware/saveCourseware', query),
    getSysDict: (query) => post('/api/exam/major/getSysDictByType', query),
    deleteTask: (query) => post(`/api/exam/task/delete/${query.id}`),
    queryTaskById: (query) => post(`/api/exam/task/select/${query.id}`),
};
