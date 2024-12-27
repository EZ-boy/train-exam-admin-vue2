package com.mindskip.xzs.service.impl.talk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.talk.TalkContent;
import com.mindskip.xzs.repository.BaseMapper;
import com.mindskip.xzs.repository.TalkMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.talk.TalkService;
import com.mindskip.xzs.viewmodel.talk.TalkRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: lifx
 * @ClassName:TalkServiceImpl.java
 * @date:2023/12/12 16:19
 * @Description:
 */
@Service
public class TalkServiceImpl extends BaseServiceImpl<TalkContent> implements TalkService {
    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    public TalkServiceImpl(BaseMapper<TalkContent> baseMapper) {
        super(baseMapper);
    }

    @Override
    public PageInfo<TalkContent> pageList(TalkRequestVM talkRequestVM) {
        return PageHelper.startPage(talkRequestVM.getPageIndex(), talkRequestVM.getPageSize(), "").doSelectPageInfo(() ->
                talkMapper.getTalkList(talkRequestVM)
        );
    }

    @Override
    public int createTalk(TalkContent talkContent) {
        return talkMapper.createTalk(talkContent);
    }

    @Override
    public int deleteTalk(TalkContent talkContent) {
        return talkMapper.deleteTalk(talkContent);
    }

    @Override
    public TalkContent getTalkDetail(TalkContent TalkContent) {
        return talkMapper.getTalkDetail(TalkContent);
    }

    @Override
    public int updateTalk(TalkContent talkContent) {
        return talkMapper.updateTalk(talkContent);
    }
}
