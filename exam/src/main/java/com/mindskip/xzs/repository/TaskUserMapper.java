package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.TaskExamCustomerAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskUserMapper extends BaseMapper<TaskExamCustomerAnswer> {

    TaskExamCustomerAnswer getByTUid(@Param("tid") Integer tid, @Param("uid") Integer uid);

    List<TaskExamCustomerAnswer> selectByTUid(@Param("taskIds") List<Integer> taskIds, @Param("uid") Integer uid);

    List<TaskExamCustomerAnswer> selectByTaskId(@Param("taskId") Integer taskId);

    int deleteByTaskId(Integer taskId);
}
