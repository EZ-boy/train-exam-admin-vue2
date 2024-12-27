package com.mindskip.xzs.service.exam;

import com.mindskip.xzs.domain.SysDict;
import com.mindskip.xzs.domain.main.Major;
import com.mindskip.xzs.domain.main.Theme;
import com.mindskip.xzs.service.BaseService;

import java.util.List;
import java.util.Map;

public interface MajorService extends BaseService<Major> {
    List<Major> init();

    int insertMajor(Major major);

    int insertTheme(List<Theme> majorList);

    Map getMajorById(Integer id);

    List<Theme> getThemeByMajorId(Integer id);

    int updateMajor(Major major);

    int countThemeById(Integer id);

    int updateTheme(Theme theme1);

    int deleteTheme(Integer id);

    int deleteMajor(Integer id);

    int deleteThemeByMajorId(Integer id);

    int insertTextContentList(List<Major> majorList);

    List<SysDict> getSysDictByType(String dictType);
}
