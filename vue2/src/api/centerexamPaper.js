import { post } from '@/utils/request';

export default {
    select: (id) => post('/api/student/exam/paper/select/' + id),
    reDoPractice: (query) => post('/api/student/exam/paper/reDoPractice', query),
    getPractice: (majorId) => post('/api/train/study/getPractice/' + majorId),
    pageList: (query) => post('/api/student/exam/paper/pageList', query),
};
