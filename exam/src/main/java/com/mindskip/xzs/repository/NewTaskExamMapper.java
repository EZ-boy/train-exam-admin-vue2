package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.main.TaskExam;
import com.mindskip.xzs.viewmodel.admin.task.TaskPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewTaskExamMapper extends BaseMapper<TaskExam> {
    List<TaskExam> init(TaskPageRequestVM requestVM);

    List<TaskExam> getTaskListByMajor(TaskPageRequestVM requestVM);

    List<TaskExam> getByUser(Integer userId);

    List<TaskExam> getByTid(Integer taskId);

    int insertTaskMajor (TaskExam taskExam);

    int deleteByTaskId(Integer taskId);

    List<TaskExam> getImitate(TaskPageRequestVM requestVM);
}
