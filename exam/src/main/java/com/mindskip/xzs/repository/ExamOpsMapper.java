package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.main.ExamOps;
import com.mindskip.xzs.viewmodel.admin.examOps.RxamOpsRequestVM;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: lifx
 * @ClassName:ExamOpsMapper.java
 * @date:2024/2/27 11:05
 * @Description:
 */
@Mapper
public interface ExamOpsMapper extends BaseMapper<ExamOps> {
    int addExamOps(RxamOpsRequestVM model);

    ExamOps getExamOps(RxamOpsRequestVM model);
}
