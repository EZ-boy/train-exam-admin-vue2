package com.mindskip.xzs.service.impl.train;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.repository.TrainMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.train.TrainService;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl extends BaseServiceImpl<Train> implements TrainService {

    private final TrainMapper trainMapper;

    @Autowired
    public TrainServiceImpl(TrainMapper trainMapper){
        super(trainMapper);
        this.trainMapper = trainMapper;
    }

    @Override
    public PageInfo<Train> pageList(TrainRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "").doSelectPageInfo(() ->
                trainMapper.pageList(requestVM)
        );
    }

    @Override
    public Train queryStudyProgress(TrainRequestVM requestVM) {
        return trainMapper.queryStudyProgress(requestVM);
    }

    @Override
    public int insertStudyProgress(Train train) {
        return trainMapper.insertStudyProgress(train);
    }

    @Override
    public int updateStudyProgress(Train train) {
        return trainMapper.updateStudyProgress(train);
    }

    @Override
    public List<Train> getTrainsByMajorIds(TrainRequestVM requestVM){
        return trainMapper.pageList(requestVM);
    }

    @Override
    public PageInfo<Train> successPageList(TrainRequestVM model){
        return PageHelper.startPage(model.getPageIndex(), model.getPageSize(), "").doSelectPageInfo(() ->
                trainMapper.successPageList(model)
        );
    }
}
