package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.Files;
import com.mindskip.xzs.domain.main.GradeDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GradeDetailsMapper  extends BaseMapper<Files>{
    List<GradeDetails> getGradeDetailsList(GradeDetails grade);


}
