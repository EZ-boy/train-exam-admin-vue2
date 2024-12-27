package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursewareMapper extends BaseMapper<Train> {
    List<Train> qryCoursewareList(TrainRequestVM vo);

    int insertCourseware(TrainRequestVM vo);

    int updateCourseware(TrainRequestVM vo);

    int deleteCoursewareById(Integer id);
}
