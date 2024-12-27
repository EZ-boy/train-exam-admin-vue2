package com.mindskip.xzs.controller.train;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.ExamPaperAnswer;
import com.mindskip.xzs.domain.Subject;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.service.train.TrainService;
import com.mindskip.xzs.service.exam.NewTaskExamService;
import com.mindskip.xzs.viewmodel.admin.education.SubjectEditRequestVM;
import com.mindskip.xzs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperReadVM;
import com.mindskip.xzs.viewmodel.student.exam.ExamPaperSubmitVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController("TrainStudyController")
@RequestMapping(value = "/api/train/study")
public class TrainStudyController extends BaseApiController {


    private final TrainService trainService;
    private final ExamPaperService examPaperService;

    private final NewTaskExamService newTaskExamService;

    @Autowired
    public TrainStudyController(TrainService trainService,ExamPaperService examPaperService,NewTaskExamService newTaskExamService){
        this.trainService = trainService;
        this.examPaperService = examPaperService;
        this.newTaskExamService = newTaskExamService;
    }

    /**
     * 培训学习列表数据查询
     * @param model
     * @return 列表数据
     */
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<Train>> pageList(@RequestBody TrainRequestVM model) {
        PageInfo<Train> pageList = trainService.pageList(model);
        return RestResponse.ok(pageList);
    }

    /**
     * 保存点赞评论数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveLikeComment", method = RequestMethod.POST)
    public RestResponse saveLikeComment(@RequestBody @Valid TrainRequestVM model) {
        Train train1 = modelMapper.map(model, Train.class);
        Train train2 = modelMapper.map(model, Train.class);
        //新增点赞或者评论数据
        int count = trainService.insertByFilter(train1);
        if(count > 0){
            //更新培训学习信息表点赞数评论数
            trainService.updateByIdFilter(train2);
        }
        return RestResponse.ok(count);
    }

    /**
     * 查询当前用户点击视频学习时长
     * @param model
     * @return 列表数据
     */
    @RequestMapping(value = "/queryStudyProgress", method = RequestMethod.POST)
    public RestResponse<Train> queryStudyProgress(@RequestBody TrainRequestVM model) {
        Train train = trainService.queryStudyProgress(model);
        return RestResponse.ok(train);
    }

    /**
     * 新增培训学习记录
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertStudyProgress", method = RequestMethod.POST)
    public RestResponse insertStudyProgress(@RequestBody @Valid TrainRequestVM model) {
        Train train = modelMapper.map(model, Train.class);
        int count = trainService.insertStudyProgress(train);
        return RestResponse.ok(count);
    }

    /**
     * 更新培训学习进度
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateStudyProgress", method = RequestMethod.POST)
    public RestResponse updateStudyProgress(@RequestBody @Valid TrainRequestVM model) {
        Train train = modelMapper.map(model, Train.class);
        int count = trainService.updateStudyProgress(train);
        return RestResponse.ok(count);
    }

    /**
     * 查询培训信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/getTrainsByMajorIds", method = RequestMethod.POST)
    public RestResponse getTrainsByMajorIds(@RequestBody @Valid TrainRequestVM model) {
        List<Train> trains = trainService.getTrainsByMajorIds(model);
        return RestResponse.ok(trains);
    }

    /**
     * 培训学习列表数据查询(完成度100%)
     * @param model
     * @return 列表数据
     */
    @RequestMapping(value = "/successPageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<Train>> successPageList(@RequestBody TrainRequestVM model) {
        PageInfo<Train> pageList = trainService.successPageList(model);
        return RestResponse.ok(pageList);
    }

    @RequestMapping(value = "/getPractice", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> getPractice(@RequestBody TrainRequestVM model) {
        User user = getCurrentUser();
        int paperId = 0;
        if (model.getFileId() == null || model.getMajorId() == null) {
            return RestResponse.fail(999,"文件id和专业id为必传");
        }else {
            //查询学习进度表，如果已经创建了试卷，直接获取试卷id即可
            Train train = trainService.queryStudyProgress(model);
            if (train != null) {
                if (train.getExamPaperId() !=null && train.getExamPaperId() > 0) {
                    paperId = train.getExamPaperId();
                }else {
                    //根据专业查询题库，然后进行自动化组卷，并返回试卷id
                    paperId = newTaskExamService.createPaper(model.getMajorId(), user.getId());
                    Train upTrain = new Train();
                    upTrain.setFileId(train.getFileId());
                    upTrain.setUserNo(train.getUserNo());
                    upTrain.setExamPaperId(paperId);
                    trainService.updateStudyProgress(upTrain);
                }
            }
            //根据试卷id查询试卷信息
            ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(paperId);
            if (StringUtils.isNotBlank(vm.getCode()) && "9999".equals(vm.getCode())) {
                return RestResponse.fail(9999, vm.getMsg());
            }else {
                return RestResponse.ok(vm);
            }
        }
    }
}

