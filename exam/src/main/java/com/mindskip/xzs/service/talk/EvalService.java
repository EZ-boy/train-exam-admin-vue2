package com.mindskip.xzs.service.talk;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.talk.Eval;
import com.mindskip.xzs.domain.talk.TalkContent;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.talk.EvalSRequestVM;
import com.mindskip.xzs.viewmodel.talk.TalkRequestVM;

/**
 * @author: lifx
 * @ClassName:EvalService.java
 * @date:2023/12/12 16:56
 * @Description:
 */
public interface EvalService extends BaseService<Eval> {
    PageInfo<Eval> pageList(EvalSRequestVM evalSRequestVM);
    int createEval(Eval eval);
    int getAverageOperEvalByExpId(Eval eval);
    int getOperationCountByExpId (Eval eval);
}
