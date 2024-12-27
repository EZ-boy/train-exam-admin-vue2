package com.mindskip.xzs.controller.talk;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.talk.Eval;
import com.mindskip.xzs.domain.talk.TalkContent;
import com.mindskip.xzs.service.talk.EvalService;
import com.mindskip.xzs.service.talk.TalkService;
import com.mindskip.xzs.viewmodel.talk.EvalSRequestVM;
import com.mindskip.xzs.viewmodel.talk.TalkRequestVM;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: lifx
 * @ClassName:TalkConrtoller.java
 * @date:2023/12/12 16:30
 * @Description:
 */
@RestController("TalkController")
@RequestMapping(value = "/api/talk/content")
public class TalkConrtoller extends BaseApiController {
    private final TalkService talkService;
    private final EvalService evalService;

    public TalkConrtoller(TalkService talkService, EvalService evalService) {
        this.talkService = talkService;
        this.evalService = evalService;
    }

    @RequestMapping(value = "/talkPageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<TalkContent>> talkPageList(@RequestBody TalkRequestVM talkRequestVM) {
        return RestResponse.ok(talkService.pageList(talkRequestVM));
    }
    @RequestMapping(value = "/createTalk", method = RequestMethod.POST)
    public RestResponse createTalk(@RequestBody TalkContent talkContent) {
        String expId = UUID.randomUUID().toString();
        talkContent.setExpId(expId);
        return RestResponse.ok(talkService.createTalk(talkContent));
    }

    @RequestMapping(value = "/deleteTalk", method = RequestMethod.POST)
    public RestResponse deleteTalk(@RequestBody TalkContent talkContent) {
        return RestResponse.ok(talkService.deleteTalk(talkContent));
    }
    @RequestMapping(value = "/getTalkDetail", method = RequestMethod.POST)
    public RestResponse getTalkDetail(@RequestBody TalkContent talkContent) {
        return RestResponse.ok(talkService.getTalkDetail(talkContent));
    }
    @RequestMapping(value = "/updateTalk", method = RequestMethod.POST)
    public RestResponse updateTalk(@RequestBody TalkContent talkContent) {
        return RestResponse.ok(talkService.updateTalk(talkContent));
    }
    @RequestMapping(value = "/evalPageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<Eval>> evalPageList(@RequestBody EvalSRequestVM evalSRequestVM) {
        return RestResponse.ok(evalService.pageList(evalSRequestVM));
    }
    @RequestMapping(value = "/createEval", method = RequestMethod.POST)
    public RestResponse createEval(@RequestBody Eval eval) {
        eval.setOperId(UUID.randomUUID().toString());
        return RestResponse.ok(evalService.createEval(eval));
    }
    @RequestMapping(value = "/getAverageOperEvalByExpId", method = RequestMethod.POST)
    public RestResponse getAverageOperEvalByExpId(@RequestBody Eval eval) {
        Eval eval1 = new Eval();
        int averageOperEvalByExpId = evalService.getAverageOperEvalByExpId(eval);
        eval1.setFs(String.valueOf(averageOperEvalByExpId));
        return RestResponse.ok(eval1);
    }
    @RequestMapping(value = "/getOperationCountByExpId", method = RequestMethod.POST)
    public RestResponse getOperationCountByExpId(@RequestBody Eval eval) {
        Eval eval1 = new Eval();
        int operationCountByExpId = evalService.getOperationCountByExpId(eval);
        eval1.setPls(String.valueOf(operationCountByExpId));
        return RestResponse.ok(eval1);
    }
}
