package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.SysDict;
import com.mindskip.xzs.domain.TaskExam;
import com.mindskip.xzs.viewmodel.admin.task.TaskPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDictMapper {

    List<SysDict> selectByType(String dictType);
}
