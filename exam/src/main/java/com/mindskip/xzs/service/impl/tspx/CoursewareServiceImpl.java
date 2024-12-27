package com.mindskip.xzs.service.impl.tspx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.SysDict;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.repository.CoursewareMapper;
import com.mindskip.xzs.repository.SysDictMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.tspx.CoursewareService;
import com.mindskip.xzs.utility.ModelMapperSingle;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoursewareServiceImpl extends BaseServiceImpl<Train> implements CoursewareService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final CoursewareMapper coursewareMapper;
    private final SysDictMapper sysDictMapper;

    @Autowired
    public CoursewareServiceImpl(CoursewareMapper coursewareMapper, SysDictMapper sysDictMapper) {
        super(coursewareMapper);
        this.coursewareMapper = coursewareMapper;
        this.sysDictMapper = sysDictMapper;
    }


    @Override
    public PageInfo<Train> qryCoursewareList(TrainRequestVM vo) {
        return PageHelper.startPage(vo.getPageIndex(), vo.getPageSize(), "id desc").doSelectPageInfo(() ->
                coursewareMapper.qryCoursewareList(vo)
        );
    }

    @Override
    public List<Train> getCoursewareList(TrainRequestVM vo) {
        return coursewareMapper.qryCoursewareList(vo);
    }

    @Override
    public int saveCourseware(TrainRequestVM vo) {
        List<SysDict> video = sysDictMapper.selectByType("video_type");
        List<SysDict> text = sysDictMapper.selectByType("doc_type");
        String videoType = "",textType="";
        String fileName = vo.getFileName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if(null != video && video.size() > 0 ){
            videoType = video.get(0).getDictValue();
        }
        if(null != text && text.size() > 0 ){
            textType = text.get(0).getDictValue();
        }
        if(videoType.contains(fileType)){
            vo.setFileType("01");
        } else if (textType.contains(fileType)) {
            vo.setFileType("02");
        }else{
            vo.setFileType("03");
        }
        int i = 0;
        if(null != vo.getId() && 0 != vo.getId()){
            i +=coursewareMapper.updateCourseware(vo);
        }else{
            i += coursewareMapper.insertCourseware(vo);
        }
        return i;
    }

    @Override
    public Train  getCoursewareById(TrainRequestVM vo) {
        List<Train> coursewareList = coursewareMapper.qryCoursewareList(vo);
        Train train = new Train();
        if (coursewareList != null && coursewareList.size() > 0)
            train = coursewareList.get(0);
        return train;
    }

    @Override
    public int deleteCoursewareById(Integer id) {
        return coursewareMapper.deleteCoursewareById(id);
    }


}
