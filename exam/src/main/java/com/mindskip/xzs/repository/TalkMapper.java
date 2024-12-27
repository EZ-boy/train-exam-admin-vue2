package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.talk.TalkContent;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import com.mindskip.xzs.viewmodel.talk.TalkRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TalkMapper extends BaseMapper<TalkContent> {
    //1.发帖信息列表查询(根据内容模糊搜索)
    List<TalkContent> getTalkList(TalkRequestVM talkRequestVM);

    //2.新增发帖信息
    int createTalk(TalkContent talkContent);

    //3.删除发帖信息
    int deleteTalk(TalkContent talkContent);

    //4.查询发帖信息详情
    TalkContent getTalkDetail(TalkContent talkContent);

    //5.修改发帖信息
    int updateTalk(TalkContent talkContent);
}
