import { post } from '@/utils/request'

export default {
  getUserTrainList: query => post('/api/center/list', query),
  getUserTextList: () => post('api/student/dashboard/task'),
}
