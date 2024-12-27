package com.mindskip.xzs.service.impl.center;

import com.mindskip.xzs.domain.center.Center;
import com.mindskip.xzs.repository.CenterMapper;
import com.mindskip.xzs.service.center.CenterService;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.viewmodel.admin.center.CenterRequestVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CenterServiceImpl extends BaseServiceImpl<Center> implements CenterService {
    private final CenterMapper centerMapper;

    public CenterServiceImpl(CenterMapper centerMapper) {
        super(centerMapper);
        this.centerMapper = centerMapper;
    }

    @Override
    public List<Center> selectByUserNo(CenterRequestVM centerRequestVM) {
        return centerMapper.selectByUserNo(centerRequestVM);
    }

    @Override
    public List<Map<String, Object>> queryTrainByUserId(Map<String, Object> paramMap) {
        return centerMapper.queryTrainByUserId(paramMap);
    }

    @Override
    public List<Map<String, Object>> qryUser4OrgTasking(Map<String, Object> paramMap) {
        return centerMapper.qryUser4OrgTasking(paramMap);
    }

    @Override
    public List<Map<String, Object>> qryUser4StudyFinished(Map<String, Object> paramMap) {
        return centerMapper.qryUser4StudyFinished(paramMap);
    }

    @Override
    public List<Map<String, Object>> qryUser4ExamFinished(Map<String, Object> paramMap) {
        return centerMapper.qryUser4ExamFinished(paramMap);
    }

    @Override
    public List<Map<String, Object>> qryUser4ExamFinishedByMajor(Map<String, Object> paramMap) {
        return centerMapper.qryUser4ExamFinishedByMajor(paramMap);
    }

    @Override
    public List<Map<String, Object>> qryUser4OrgTaskingByMajor(Map<String, Object> paramMap) {
        return centerMapper.qryUser4OrgTaskingByMajor(paramMap);
    }


}

