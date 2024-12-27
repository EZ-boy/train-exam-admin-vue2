package com.mindskip.xzs.service.exam;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;

import java.util.List;
import java.util.Map;

public interface CoursewareService extends BaseService<Train> {

    PageInfo<Train> qryCoursewareList(TrainRequestVM vo);

    List<Train> getCoursewareList(TrainRequestVM vo);
    int saveCourseware(TrainRequestVM vo);

    Train getCoursewareById(TrainRequestVM model);

    int deleteCoursewareById(Integer id);
}
