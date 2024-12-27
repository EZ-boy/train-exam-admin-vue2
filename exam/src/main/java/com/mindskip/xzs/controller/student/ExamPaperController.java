package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.ExamPaper;
import com.mindskip.xzs.domain.ExamPaperAnswer;
import com.mindskip.xzs.domain.ExamPaperQuestionCustomerAnswer;
import com.mindskip.xzs.service.ExamPaperAnswerService;
import com.mindskip.xzs.service.ExamPaperQuestionCustomerAnswerService;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.utility.DateTimeUtil;
import com.mindskip.xzs.utility.PageInfoHelper;
import com.mindskip.xzs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.xzs.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperPageVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
public class ExamPaperController extends BaseApiController {

    private final ExamPaperService examPaperService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;
    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;

    @Autowired
    public ExamPaperController(ExamPaperService examPaperService, ExamPaperAnswerService examPaperAnswerService, ApplicationEventPublisher eventPublisher, ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService) {
        this.examPaperService = examPaperService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.eventPublisher = eventPublisher;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/reDoPractice", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> reDoPractice(@RequestBody ExamPaperAnswerPageRequestVM model) {
        //重新答题之前先清空试卷题目答案表
        int examPaperAnswerId = 0;
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.getByPidUid(model.getPaperId(),model.getUserId());
        if (examPaperAnswer != null) {
            examPaperAnswerId = examPaperAnswer.getId();
        }
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = new ExamPaperQuestionCustomerAnswer();
        examPaperQuestionCustomerAnswer.setExamPaperAnswerId(examPaperAnswerId);
        examPaperQuestionCustomerAnswer.setCreateUser(model.getUserId());
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswersQ = examPaperQuestionCustomerAnswerService.selectByCond(examPaperQuestionCustomerAnswer);
        if (examPaperQuestionCustomerAnswersQ.size() > 0) {
            for (int i = 0; i < examPaperQuestionCustomerAnswersQ.size(); i++) {
                examPaperQuestionCustomerAnswerService.deleteById(examPaperQuestionCustomerAnswersQ.get(i).getId());
            }
        }
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(model.getPaperId());
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@RequestBody @Valid ExamPaperPageVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = modelMapper.map(e, ExamPaperPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
}
