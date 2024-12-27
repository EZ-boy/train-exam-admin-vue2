package com.mindskip.xzs.service.exam;

import com.mindskip.xzs.domain.main.ExamOps;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.admin.examOps.RxamOpsRequestVM;

/**
 * @author: lifx
 * @ClassName:ExamOpsServic.java
 * @date:2024/2/27 10:56
 * @Description:
 */
public interface ExamOpsServic extends BaseService<ExamOps> {
    int addExamOps(RxamOpsRequestVM model);

    ExamOps getExamOps(RxamOpsRequestVM model);
}
