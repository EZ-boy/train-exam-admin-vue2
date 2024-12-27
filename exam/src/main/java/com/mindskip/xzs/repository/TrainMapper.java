package com.mindskip.xzs.repository;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainMapper extends BaseMapper<Train> {

    List<Train> pageList(TrainRequestVM requestVM);

    Train queryStudyProgress(TrainRequestVM requestVM);

    int insertStudyProgress(Train train);

    int updateStudyProgress(Train train);

    List<String> getTrainById(@Param("ids") String ids);

    List<Train> successPageList(TrainRequestVM model);
}
