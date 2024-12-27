package com.mindskip.xzs.service.impl.talk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.talk.Eval;
import com.mindskip.xzs.repository.BaseMapper;
import com.mindskip.xzs.repository.EvalMapper;
import com.mindskip.xzs.repository.TalkMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.talk.EvalService;
import com.mindskip.xzs.viewmodel.talk.EvalSRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lifx
 * @ClassName:EvalServiceImpl.java
 * @date:2023/12/12 17:00
 * @Description:
 */
@Service
public class EvalServiceImpl extends BaseServiceImpl<Eval> implements EvalService {
    @Autowired
    private EvalMapper evalMapper;

    @Autowired
    public EvalServiceImpl(BaseMapper<Eval> baseMapper) {
        super(baseMapper);
    }

    @Override
    public PageInfo<Eval> pageList(EvalSRequestVM evalSRequestVM) {
        PageInfo<Eval> evalPageInfo = PageHelper.startPage(evalSRequestVM.getPageIndex(), evalSRequestVM.getPageSize(), "").doSelectPageInfo(() ->
                evalMapper.pageList(evalSRequestVM)
        );
        return evalPageInfo;
    }

    @Override
    public int createEval(Eval eval) {
        return evalMapper.createEval(eval);
    }

    @Override
    public int getAverageOperEvalByExpId(Eval eval) {
        return evalMapper.getAverageOperEvalByExpId(eval);
    }

    @Override
    public int getOperationCountByExpId(Eval eval) {
        return evalMapper.getOperationCountByExpId(eval);
    }

}
