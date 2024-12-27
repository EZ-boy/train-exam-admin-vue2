package com.mindskip.xzs.service.exam;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.main.TaskExam;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.admin.task.TaskPageRequestVM;
import com.mindskip.xzs.viewmodel.admin.task.TaskRequestVM;

import java.util.List;

public interface NewTaskExamService extends BaseService<TaskExam> {

    PageInfo<TaskExam> init(TaskPageRequestVM requestVM);

    PageInfo<TaskExam> getTaskListByMajor(TaskPageRequestVM requestVM);

    void edit(TaskExam model, User user);

    TaskRequestVM taskExamToVM(Integer id);

    List<TaskExam> getByUser(Integer userId);

    List<TaskExam> getTaskListByMajorId(TaskPageRequestVM model);

    TaskExam selectById(Integer id);

    int createPaper(Integer majorId, Integer user);

    void editImitate(TaskExam model, User currentUser);

    TaskRequestVM imitateTaskExamToVM(Integer id);

    PageInfo<TaskExam> getImitate(TaskPageRequestVM model);

    TaskExam selectImitateById(Integer id);
}
