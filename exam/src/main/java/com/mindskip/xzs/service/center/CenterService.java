package com.mindskip.xzs.service.center;

import com.mindskip.xzs.domain.center.Center;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.admin.center.CenterRequestVM;

import java.util.List;
import java.util.Map;

public interface CenterService extends BaseService<Center> {
    List<Center> selectByUserNo(CenterRequestVM centerRequestVM);

    /**
     *个人学习列表
     */
    List<Map<String,Object>> queryTrainByUserId(Map<String,Object> paramMap);

    /**
     *有学习考试任务的人按组织统计
     */
    List<Map<String,Object>>qryUser4OrgTasking(Map<String,Object> paramMap);
    /**
     *完成学习的人按组织统计
     */
    List<Map<String,Object>>qryUser4StudyFinished(Map<String,Object> paramMap);

    /**
     * 完成考试的人按组织统计
     */
    List<Map<String,Object>>qryUser4ExamFinished(Map<String,Object> paramMap);

    /**
     * 完成考试的人按专业、组织统计
     */
    List<Map<String,Object>>qryUser4ExamFinishedByMajor(Map<String,Object> paramMap);

    /**
     *有学习考试任务的人专业、组织统计
     */
    List<Map<String,Object>>qryUser4OrgTaskingByMajor(Map<String,Object> paramMap);

}
