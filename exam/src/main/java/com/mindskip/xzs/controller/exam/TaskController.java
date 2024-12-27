package com.mindskip.xzs.controller.exam;


import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.Question;
import com.mindskip.xzs.domain.enums.QuestionTypeEnum;
import com.mindskip.xzs.domain.main.TaskExam;
import com.mindskip.xzs.service.exam.NewQuestionService;
import com.mindskip.xzs.service.exam.NewTaskExamService;
import com.mindskip.xzs.viewmodel.admin.task.TaskPageRequestVM;
import com.mindskip.xzs.viewmodel.admin.task.TaskRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("ExamAdminTaskController")
@RequestMapping(value = "/api/exam/task")
public class TaskController extends BaseApiController {

    private final NewTaskExamService taskExamService;
    private final NewQuestionService newQuestionService;

    @Autowired
    public TaskController(NewTaskExamService taskExamService, NewQuestionService newQuestionService) {
        this.taskExamService = taskExamService;
        this.newQuestionService = newQuestionService;
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public RestResponse<PageInfo<TaskExam>> init(@RequestBody TaskPageRequestVM model) {
        PageInfo<TaskExam> pageInfo = taskExamService.init(model);
        return RestResponse.ok(pageInfo);
    }

    @RequestMapping(value = "/getImitate", method = RequestMethod.POST)
    public RestResponse<PageInfo<TaskExam>> getImitate(@RequestBody TaskPageRequestVM model) {
        PageInfo<TaskExam> pageInfo = taskExamService.getImitate(model);
        return RestResponse.ok(pageInfo);
    }

    @RequestMapping(value = "/getTaskListByMajor", method = RequestMethod.POST)
    public RestResponse<PageInfo<TaskExam>> getTaskListByMajor(@RequestBody TaskPageRequestVM model) {
        PageInfo<TaskExam> pageInfo = taskExamService.getTaskListByMajor(model);
        return RestResponse.ok(pageInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid TaskExam model) {
        List<Question> radioQuestions = newQuestionService.getQuestionByType(QuestionTypeEnum.SingleChoice.getCode(),model.getMultipleNum(),model.getMajorIds());
        if (radioQuestions.size() > 0) {
            if (model.getMultipleNum() > radioQuestions.size()) {
                return RestResponse.fail(999, "题库单选题数目为"+radioQuestions.size()+"，请上传后再操作");
            }
        }else {
            return RestResponse.fail(999, "单选题题库为空，请上传后再操作");
        }
        List<Question> multiQuestions = newQuestionService.getQuestionByType(QuestionTypeEnum.MultipleChoice.getCode(),model.getMultipleNum(),model.getMajorIds());
        if (multiQuestions.size() > 0) {
            if (model.getMultipleNum() > multiQuestions.size()) {
                return RestResponse.fail(999, "题库多选题数目为"+multiQuestions.size()+"，请上传后再操作");
            }
        }else {
            return RestResponse.fail(999, "多选题题库为空，请上传后再操作");
        }
        taskExamService.edit(model, getCurrentUser());
        TaskRequestVM vm = taskExamService.taskExamToVM(model.getId());
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/editImitate", method = RequestMethod.POST)
    public RestResponse editImitate(@RequestBody @Valid TaskExam model) {
        taskExamService.editImitate(model, getCurrentUser());
        TaskRequestVM vm = taskExamService.imitateTaskExamToVM(model.getId());
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        TaskExam taskExam = taskExamService.selectById(id);
        taskExam.setDeleted("D");
        taskExamService.updateByIdFilter(taskExam);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<TaskExam> select(@PathVariable Integer id) {
        TaskExam vm = taskExamService.selectById(id);
        return RestResponse.ok(vm);
    }

    @RequestMapping(value = "/selectImitate/{id}", method = RequestMethod.POST)
    public RestResponse<TaskExam> selectImitate(@PathVariable Integer id) {
        TaskExam vm = taskExamService.selectImitateById(id);
        return RestResponse.ok(vm);
    }
}
