package com.mindskip.xzs.controller.tspx;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.tspx.GradeDetails;

import com.mindskip.xzs.service.tspx.GradeDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: tspx
 * @package: com.mindskip.xzs.controller.tspx
 * @className: GradeDetailsController
 * @author: yangletian
 * @date: 2024/2/27 14:52
 * @version: 1.0
 */

@RestController("GradeDetailsController")
@RequestMapping(value = "/api/tspx/gradeDetails")
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
