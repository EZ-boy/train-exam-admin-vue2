package com.mindskip.xzs.service.train;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;

import java.util.List;

public interface TrainService extends BaseService<Train> {

    PageInfo<Train> pageList(TrainRequestVM requestVM);

    Train queryStudyProgress(TrainRequestVM requestVM);

    int insertStudyProgress(Train train);

    int updateStudyProgress(Train train);

    List<Train> getTrainsByMajorIds(TrainRequestVM requestVM);

    PageInfo<Train> successPageList(TrainRequestVM model);
}
