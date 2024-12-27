package com.mindskip.xzs.controller.center;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.center.Center;
import com.mindskip.xzs.service.center.CenterService;
import com.mindskip.xzs.viewmodel.admin.center.CenterRequestVM;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("CenterController")
@RequestMapping(value = "/api/center")
public class CenterController extends BaseApiController {
    private  CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestResponse<List<Center>> selectByUserNo(@RequestBody CenterRequestVM centerRequestVM) {
        User user = getCurrentUser();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",user.getId());
        List<Map<String,Object>> trainIdList = centerService.queryTrainByUserId(paramMap);
        Set<String> trainIds  = new HashSet<>();
        for(Map<String,Object> m : trainIdList){
            for(String trainId :String.valueOf(m.get("train_ids")).split(",")){
                trainIds.add(trainId);
            }

        }
        System.out.println("trainIds.size:"+trainIds.size());
        List<Center> centerList = centerService.selectByUserNo(centerRequestVM);
        List<Center> centerListNew = new ArrayList<>();
        for(Center c : centerList){
            if(trainIds.contains(String.valueOf(c.getKid()))){
                centerListNew.add(c);
            }
        }
        return RestResponse.ok(centerListNew);
    }

    /***
     * 首页-学习、考试进度统计
     * @return
     */
    @RequestMapping(value = "/studyExamInfoByUser", method = RequestMethod.POST)
    public RestResponse<List<Map<String,Object>>> selectByUserNo() {
        Map<String,Object> param= new HashMap<>();
        List<Map<String,Object>> taskingList = centerService.qryUser4OrgTasking(param);
        List<Map<String,Object>> taskingMList = centerService.qryUser4OrgTaskingByMajor(param);
        List<Map<String,Object>> examFinishedMList = centerService.qryUser4ExamFinishedByMajor(param);

        List<Map<String,Object>> studyFinishedList = centerService.qryUser4StudyFinished(param);
        List<Map<String,Object>> examFinishedList = centerService.qryUser4ExamFinished(param);
        for(Map<String,Object> tasking :taskingList){
            for(Map<String,Object> study :studyFinishedList){
                if(tasking.get("org_no")!= null) {
                    if (tasking.get("org_no").equals(study.get("org_no"))) {
                        tasking.put("study_finished", study.get("study_finished"));
                    }
                }
            }
            for(Map<String,Object> exam :examFinishedList){
                if(tasking.get("org_no")!= null) {
                    if (tasking.get("org_no").equals(exam.get("org_no"))) {
                        tasking.put("exam_finished", exam.get("exam_finished"));
                    }
                }
            }
        }

        for(Map<String,Object> tasking :taskingMList){
            for(Map<String,Object> exam :examFinishedMList){
                if(tasking.get("org_no")!= null && tasking.get("major_id")!= null) {
                    if (tasking.get("org_no").equals(exam.get("org_no")) && tasking.get("major_id").equals(exam.get("major_id"))) {
                        tasking.put("exam_major_finished", exam.get("exam_major_finished"));
                    }
                }
            }
        }
        for(Map<String,Object> tasking : taskingList){
            if(tasking.get("org_no")==null){
                continue;
            }
            List<Map<String,Object>> zyjdList = new ArrayList<>();
            for(Map<String,Object> taskingM : taskingMList){
                if(taskingM.get("org_no")==null){
                    continue;
                }
                if(tasking.get("org_no").equals(taskingM.get("org_no"))){
                    Map<String,Object> zyMap= new HashMap<>();
                    zyMap.put("exam_major_finished",taskingM.get("exam_major_finished"));
                    zyMap.put("tasking_major_cnt",taskingM.get("tasking_major_cnt"));
                    zyMap.put("major_id",taskingM.get("major_id"));
                    zyMap.put("major_name",taskingM.get("major_name"));
                    zyjdList.add(zyMap);
                }
            }
            tasking.put("zyjdList",zyjdList);
        }
        return RestResponse.ok(taskingList);
    }


}
