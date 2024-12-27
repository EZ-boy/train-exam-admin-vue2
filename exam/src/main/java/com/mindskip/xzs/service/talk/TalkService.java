package com.mindskip.xzs.service.talk;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.talk.TalkContent;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.talk.TalkRequestVM;

public interface TalkService extends BaseService<TalkContent> {
    PageInfo<TalkContent> pageList(TalkRequestVM talkRequestVM);
    int createTalk(TalkContent talkContent);
    int deleteTalk(TalkContent talkContent);
    TalkContent getTalkDetail(TalkContent TalkContent);
    int updateTalk(TalkContent talkContent);
}
