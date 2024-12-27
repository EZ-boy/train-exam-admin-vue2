package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.talk.Eval;
import com.mindskip.xzs.viewmodel.talk.EvalSRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EvalMapper extends BaseMapper<Eval> {
    List<Eval> pageList(EvalSRequestVM evalSRequestVM);
    int createEval(Eval eval);
    int getAverageOperEvalByExpId(Eval eval);
    int getOperationCountByExpId (Eval eval);
}
