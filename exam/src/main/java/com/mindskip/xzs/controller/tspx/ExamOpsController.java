package com.mindskip.xzs.controller.tspx;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.tspx.ExamOps;
import com.mindskip.xzs.service.tspx.ExamOpsServic;
import com.mindskip.xzs.viewmodel.admin.examOps.RxamOpsRequestVM;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: syf
 * @ClassName:ExamOpsController.java
 * @date:2024/2/27 10:52
 * @Description:
 */
@RestController("ExamOpsController")
@RequestMapping(value = "/api/tspx/examOps")
public class ExamOpsController extends BaseApiController {

    private final ExamOpsServic examOpsServic;

    public ExamOpsController(ExamOpsServic examOpsServic) {
        this.examOpsServic = examOpsServic;
    }

    /**
     * @param model:
     * @return RestResponse
     * @author syf
     * @date 2024/2/27 11:21
     * 录入模拟考试结果
     */
    @RequestMapping(value = "/addExamOps", method = RequestMethod.POST)
    public RestResponse addExamOps(@RequestBody RxamOpsRequestVM model) {
        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date currentDate = new Date();
        String currentDateString = dateFormat.format(currentDate);
        // 获取任务编号中的日期
        String examIdDateString = model.getOrderNo().substring(2, 8);
        // 判断日期并设置 opsScore
        String opsScore = "0";
        if (currentDateString.equals(examIdDateString) || (Integer.parseInt(currentDateString) - Integer.parseInt(examIdDateString)) == 1) {
            opsScore = "100";
        }
        model.setOpsScore(opsScore);
        int i = examOpsServic.addExamOps(model);
        return RestResponse.ok(i);
    }
    /**
     * @param model:
     * @return RestResponse<ExamOps>
     * @author syf
     * @date 2024/2/27 11:22
     * 查看模拟考试结果
     */
    @RequestMapping(value = "/getExamOps", method = RequestMethod.POST)
    public RestResponse<ExamOps> getExamOps(@RequestBody RxamOpsRequestVM model) {
        ExamOps examOps = examOpsServic.getExamOps(model);
        return RestResponse.ok(examOps);
    }
}
