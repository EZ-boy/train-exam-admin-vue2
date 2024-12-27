package com.mindskip.xzs.service.impl.exam;

import com.mindskip.xzs.domain.main.ExamOps;
import com.mindskip.xzs.repository.ExamOpsMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.exam.ExamOpsServic;
import com.mindskip.xzs.viewmodel.admin.examOps.RxamOpsRequestVM;
import org.springframework.stereotype.Service;

/**
 * @author: lifx
 * @ClassName:ExamOpsServicImpl.java
 * @date:2024/2/27 11:03
 * @Description:
 */
@Service
public class ExamOpsServicImpl extends BaseServiceImpl<ExamOps> implements ExamOpsServic {
    private final ExamOpsMapper examOpsMapper;

    public ExamOpsServicImpl(ExamOpsMapper examOpsMapper) {
        super(examOpsMapper);
        this.examOpsMapper = examOpsMapper;
    }


    @Override
    public int addExamOps(RxamOpsRequestVM model) {
        return examOpsMapper.addExamOps(model);
    }

    @Override
    public ExamOps getExamOps(RxamOpsRequestVM model) {
        return examOpsMapper.getExamOps(model);
    }
}
