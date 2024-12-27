import { post } from '@/utils/request';

export default {
    getTrainPageList: (query) => post('/api/train/study/pageList', query),
    getMajorList: (query) => post('/api/exam/major/init', query),
    saveLikeComment: (query) => post('/api/train/study/saveLikeComment', query),
    queryStudyProgress: (query) => post('/api/train/study/queryStudyProgress', query),
    insertStudyProgress: (query) => post('/api/train/study/insertStudyProgress', query),
    updateStudyProgress: (query) => post('/api/train/study/updateStudyProgress', query),
    getTrainsByMajorIds: (query) => post('/api/train/study/getTrainsByMajorIds', query),
    successPageList: (query) => post('/api/train/study/successPageList', query),
    getPractice: (query) => post('api/train/study/getPractice', query),

    downLoad: (query) => get('/api/file/download', query),
};
