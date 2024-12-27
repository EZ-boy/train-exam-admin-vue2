package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.ExamPaper;
import com.mindskip.xzs.domain.other.KeyValue;
import com.mindskip.xzs.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mindskip.xzs.viewmodel.student.dashboard.PaperFilter;
import com.mindskip.xzs.viewmodel.student.dashboard.PaperInfo;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CourseFiles extends BaseMapper<ExamPaper> {

    List<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    int clearTaskPaper(@Param("paperIds") List<Integer> paperIds);


}
