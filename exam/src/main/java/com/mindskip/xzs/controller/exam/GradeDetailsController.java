package com.mindskip.xzs.controller.exam;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.main.GradeDetails;

import com.mindskip.xzs.service.exam.GradeDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: exam
 * @package: com.mindskip.xzs.controller.exam
 * @className: GradeDetailsController
 * @author: lifx
 * @date: 2024/2/27 14:52
 * @version: 1.0
 */

@RestController("GradeDetailsController")
@RequestMapping(value = "/api/exam/gradeDetails")
public class GradeDetailsController extends BaseApiController {

    @Autowired
    private  GradeDetailsService gradeDetailsService;


    @RequestMapping(value = "/getGradeDetails", method = RequestMethod.POST)
    public RestResponse<List<GradeDetails>> getGradeDetails(@RequestBody GradeDetails grade) {
        PageHelper.startPage(grade.getPageNo(), grade.getPageSize());
        List<GradeDetails> gradeDetails = gradeDetailsService.getGradeDetails(grade);
        PageInfo pageInfo = new PageInfo(gradeDetails);
        return RestResponse.ok(pageInfo.getList(),pageInfo.getTotal());
    }



}
