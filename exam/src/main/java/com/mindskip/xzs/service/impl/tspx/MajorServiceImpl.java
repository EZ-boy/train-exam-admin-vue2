package com.mindskip.xzs.service.impl.tspx;

import com.mindskip.xzs.domain.SysDict;
import com.mindskip.xzs.domain.TextContent;
import com.mindskip.xzs.domain.tspx.Major;
import com.mindskip.xzs.domain.tspx.Theme;
import com.mindskip.xzs.repository.MajorMapper;
import com.mindskip.xzs.repository.SysDictMapper;
import com.mindskip.xzs.service.TextContentService;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.tspx.MajorService;
import com.mindskip.xzs.utility.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MajorServiceImpl extends BaseServiceImpl<Major> implements MajorService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final MajorMapper majorMapper;

    @Autowired
    private TextContentService textContentService;

    @Autowired
    private SysDictMapper sysDictMapper;

    @Autowired
    public MajorServiceImpl(MajorMapper majorMapper) {
        super(majorMapper);
        this.majorMapper = majorMapper;
    }

    @Override
    public List<Major> init() {
        return majorMapper.init();
    }

    @Override
    public int insertMajor(Major major){
        return majorMapper.insertMajor(major);
    }

    @Override
    public int insertTheme(List<Theme> majorList){
        return majorMapper.insertTheme(majorList);
    }

    @Override
    public Map getMajorById(Integer id){
        return majorMapper.getMajorById(id);
    }

    @Override
    public List<Theme> getThemeByMajorId(Integer id){
        return majorMapper.getThemeByMajorId(id);
    }

    @Override
    public int updateMajor(Major major){
        return majorMapper.updateMajor(major);
    }

    @Override
    public int countThemeById(Integer id){
        return majorMapper.countThemeById(id);
    }

    @Override
    public int updateTheme(Theme theme){
        return majorMapper.updateTheme(theme);
    }

    @Override
    public int deleteTheme(Integer id){
        return majorMapper.deleteTheme(id);
    }

    @Override
    public int deleteMajor(Integer id){
        return majorMapper.deleteMajor(id);
    }

    @Override
    public int deleteThemeByMajorId(Integer id){
        return majorMapper.deleteThemeByMajorId(id);
    }

    @Override
    public int insertTextContentList(List<Major> majorList) {

        int i = 0;
        if (majorList != null && majorList.size() > 0){
            for (Major major : majorList) {
                Date now = new Date();
                TextContent textContent = new TextContent(major.getContent(), now);
                textContentService.insertByFilter(textContent);
                int id = textContent.getId();
                major.setInfoTextContentId(id+"");

                major.setCreateTime(now);
                major.setDeleted("I");
                major.setStatus("1");
                i += majorMapper.insertQuestionData(major);
            }
        }
        return i;
    }

    @Override
    public List<SysDict> getSysDictByType(String dictType) {
        return sysDictMapper.selectByType(dictType);
    }
}
