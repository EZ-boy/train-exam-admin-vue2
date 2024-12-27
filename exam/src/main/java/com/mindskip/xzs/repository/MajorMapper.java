package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.main.Major;
import com.mindskip.xzs.domain.main.Theme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MajorMapper extends BaseMapper<Major> {
    List<Major> init();

    int insertMajor(Major major);

    int insertTheme(List<Theme> majorList);

    Map getMajorById(Integer id);

    List<Theme> getThemeByMajorId(Integer id);

    int updateMajor(Major major);

    int countThemeById(Integer id);

    int updateTheme(Theme theme);

    int deleteTheme(Integer id);

    int deleteMajor(Integer id);

    int deleteThemeByMajorId(Integer id);

    int insertQuestionData(Major major);
}
