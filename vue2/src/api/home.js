import { post } from '@/utils/request';

export default {
    studyExamInfoByUser: () => post('/api/center/studyExamInfoByUser'),
};
