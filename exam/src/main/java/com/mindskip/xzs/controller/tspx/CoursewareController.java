package com.mindskip.xzs.controller.tspx;


import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.domain.tspx.Major;
import com.mindskip.xzs.domain.tspx.Theme;
import com.mindskip.xzs.service.tspx.CoursewareService;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController("TspxCoursewareController")
@RequestMapping(value = "/api/tspx/courseware")
public class CoursewareController extends BaseApiController {

    private final CoursewareService coursewareService;

    @Autowired
    public CoursewareController(CoursewareService coursewareService) {
        this.coursewareService = coursewareService;
    }

    @RequestMapping(value = "/qryCoursewareList", method = RequestMethod.POST)
    public RestResponse<PageInfo<Train>> getTaskListByMajor(@RequestBody TrainRequestVM model) {
        PageInfo<Train> pageInfo = coursewareService.qryCoursewareList(model);
        return RestResponse.ok(pageInfo);
    }

    @RequestMapping(value = "/saveCourseware", method = RequestMethod.POST)
    public RestResponse saveCourseware(@RequestBody @Valid TrainRequestVM model) {

        RestResponse result = new RestResponse(0,"");
        model.setLikeNum(0);
        model.setCommentNum(0);
        int i = coursewareService.saveCourseware(model);
        if(i > 0){
            result = RestResponse.ok("保存成功");
        }else{
            result = RestResponse.fail(-1,"操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/getCoursewareById", method = RequestMethod.POST)
    public RestResponse getCoursewareById(@RequestBody TrainRequestVM model) {
        Train train = coursewareService.getCoursewareById(model);
        return RestResponse.ok(train);
    }

    @RequestMapping(value = "/deleteCoursewareById", method = RequestMethod.POST)
    public RestResponse deleteCoursewareById(@RequestBody @Valid TrainRequestVM model) {
        RestResponse result = new RestResponse(0,"");
        int i = coursewareService.deleteCoursewareById(model.getId());
        if(i > 0){
            result = RestResponse.ok("删除成功");
        }else{
            result = RestResponse.fail(-1,"操作失败");
        }
        return result;
    }
}
