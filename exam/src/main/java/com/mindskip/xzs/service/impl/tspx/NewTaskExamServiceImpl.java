package com.mindskip.xzs.service.impl.tspx;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.*;
import com.mindskip.xzs.domain.enums.ExamPaperTypeEnum;
import com.mindskip.xzs.domain.enums.QuestionTypeEnum;
import com.mindskip.xzs.domain.exam.ExamPaperQuestionItemObject;
import com.mindskip.xzs.domain.exam.ExamPaperTitleItemObject;
import com.mindskip.xzs.domain.task.TaskItemObject;
import com.mindskip.xzs.domain.tspx.MajorType;
import com.mindskip.xzs.domain.tspx.TaskExam;
import com.mindskip.xzs.repository.*;
import com.mindskip.xzs.service.TextContentService;
import com.mindskip.xzs.service.enums.ActionEnum;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.tspx.NewQuestionService;
import com.mindskip.xzs.service.tspx.NewTaskExamService;
import com.mindskip.xzs.utility.DateTimeUtil;
import com.mindskip.xzs.utility.ExamUtil;
import com.mindskip.xzs.utility.JsonUtil;
import com.mindskip.xzs.utility.ModelMapperSingle;
import com.mindskip.xzs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.xzs.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.mindskip.xzs.viewmodel.admin.exam.ExamResponseVM;
import com.mindskip.xzs.viewmodel.admin.question.QuestionEditRequestVM;
import com.mindskip.xzs.viewmodel.admin.task.TaskPageRequestVM;
import com.mindskip.xzs.viewmodel.admin.task.TaskRequestVM;
import com.mindskip.xzs.viewmodel.student.dashboard.PaperInfo;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NewTaskExamServiceImpl extends BaseServiceImpl<TaskExam> implements NewTaskExamService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final TextContentService textContentService;
    private final TrainMapper trainMapper;
    private final NewQuestionService newQuestionService;
    private final NewTaskExamMapper taskExamMapper;
    private final NewExamPaperMapper examPaperMapper;
    private final TaskUserMapper taskUserMapper;

    @Autowired
    public NewTaskExamServiceImpl(NewTaskExamMapper taskExamMapper, TextContentService textContentService, NewQuestionService newQuestionService, TrainMapper trainMapper, NewExamPaperMapper examPaperMapper, TaskUserMapper taskUserMapper) {
        super(taskExamMapper);
        this.taskExamMapper = taskExamMapper;
        this.textContentService = textContentService;
        this.newQuestionService = newQuestionService;
        this.trainMapper = trainMapper;
        this.examPaperMapper = examPaperMapper;
        this.taskUserMapper = taskUserMapper;
    }

    @Override
    public PageInfo<TaskExam> init(TaskPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                taskExamMapper.init(requestVM)
        );
    }

    @Override
    public PageInfo<TaskExam> getImitate(TaskPageRequestVM requestVM){
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                taskExamMapper.getImitate(requestVM)
        );
    }

    @Override
    public PageInfo<TaskExam> getTaskListByMajor(TaskPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                taskExamMapper.getTaskListByMajor(requestVM)
        );
    }

    @Override
    @Transactional
    public void editImitate(TaskExam taskExam, User user) {
        ActionEnum actionEnum = (taskExam.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        Date now = new Date();
        if (actionEnum == ActionEnum.ADD) {
            taskExam.setCreateUserNo(String.valueOf(user.getId()));
            taskExam.setImitate("01");//模拟标识
            taskExam.setCreateTime(now);
            taskExam.setDeleted("I");
            taskExam.setTitle(taskExam.getTaskName());
            taskExamMapper.insertSelective(taskExam);
            //保存任务关联用户信息
            if (taskExam.getUsers().size() > 0) {
                for (int i = 0; i < taskExam.getUsers().size(); i++) {
                    TaskExamCustomerAnswer taskExamUser = new TaskExamCustomerAnswer();
                    taskExamUser.setUserId(taskExam.getUsers().get(i).getUserId());
                    taskExamUser.setTaskExamId(taskExam.getId());
                    taskExamUser.setCreateTime(now);
                    taskUserMapper.insertSelective(taskExamUser);
                }
            }
            //保存任务关联专业
            if (taskExam.getMajorIds().size() > 0) {
                for (int i = 0; i < taskExam.getMajorIds().size(); i++) {
                    TaskExam majorTask = new TaskExam();
                    majorTask.setMajorId(taskExam.getMajorIds().get(i));
                    majorTask.setTaskId(taskExam.getId());
                    taskExamMapper.insertTaskMajor(majorTask);
                }
            }
        } else {
            taskExam.setDeleted("U");
            taskExam.setUpdateTime(now);
            taskExam.setTitle(taskExam.getTaskName());
            //保存任务关联用户信息，先删除再保存
            taskUserMapper.deleteByTaskId(taskExam.getId());
            if (taskExam.getUsers().size() > 0) {
                for (int i = 0; i < taskExam.getUsers().size(); i++) {
                    TaskExamCustomerAnswer taskExamUser = new TaskExamCustomerAnswer();
                    taskExamUser.setUserId(taskExam.getUsers().get(i).getUserId());
                    taskExamUser.setTaskExamId(taskExam.getId());
                    taskExamUser.setCreateTime(now);
                    taskUserMapper.insertSelective(taskExamUser);
                }
            }
            //保存任务关联专业，先删除再保存
            taskExamMapper.deleteByTaskId(taskExam.getId());
            if (taskExam.getMajorIds().size() > 0) {
                for (int i = 0; i < taskExam.getMajorIds().size(); i++) {
                    TaskExam majorTask = new TaskExam();
                    majorTask.setMajorId(taskExam.getMajorIds().get(i));
                    majorTask.setTaskId(taskExam.getId());
                    taskExamMapper.insertTaskMajor(majorTask);
                }
            }
            taskExamMapper.updateByPrimaryKeySelective(taskExam);
        }
    }


    @Override
    @Transactional
    public void edit(TaskExam taskExam, User user) {
        ActionEnum actionEnum = (taskExam.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        Date now = new Date();
        if (actionEnum == ActionEnum.ADD) {
            taskExam.setCreateUserNo(String.valueOf(user.getId()));
            taskExam.setCreateTime(now);
            taskExam.setDeleted("I");
            taskExam.setTitle(taskExam.getTaskName());

            //创建组卷报文
            List<ExamPaperTitleItemVM> titleItemsVM = frameExamPaper(taskExam);
            //任务创建，自动化组卷开始
            if (titleItemsVM.size() > 0) {
                List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
                String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);
                //自动化组卷，根据题型与题数查询题库，再拼装结构化报文
                ExamPaperEditRequestVM examPaperEditRequestVM = new ExamPaperEditRequestVM();
                examPaperEditRequestVM.setName(taskExam.getTaskName());
                examPaperEditRequestVM.setPaperType(taskExam.getPaperType());
                examPaperEditRequestVM.setSuggestTime(taskExam.getSuggestTime());
                examPaperEditRequestVM.setTitleItems(titleItemsVM);
                ExamPaper examPaper = modelMapper.map(examPaperEditRequestVM, ExamPaper.class);
                TextContent frameTextContent = new TextContent(frameTextContentStr, now);
                textContentService.insertByFilter(frameTextContent);
                examPaper.setFrameTextContentId(frameTextContent.getId());
                examPaper.setCreateTime(now);
                examPaper.setCreateUser(user.getId());
                examPaper.setDeleted("I");
                examPaper.setQuestionCount(taskExam.getRadioNum() + taskExam.getActualOperNum() + taskExam.getMultipleNum());
                examPaper.setLimitStartTime(DateTimeUtil.parse(taskExam.getLimitStartTime(),DateTimeUtil.STANDER_FORMAT));
                examPaper.setLimitEndTime(DateTimeUtil.parse(taskExam.getLimitEndTime(),DateTimeUtil.STANDER_FORMAT));
                examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
                examPaper.setScore(taskExam.getScore());
                examPaperMapper.insertSelective(examPaper);

                //创建任务时关联试卷报文
                List<ExamResponseVM> examResponse = new ArrayList<>();
                ExamResponseVM examResponseVm = new ExamResponseVM();
                examResponseVm.setId(examPaper.getId());
                examResponseVm.setName(taskExam.getTaskName());
                examResponseVm.setCreateTime(DateTimeUtil.dateFormat(now));
                examResponseVm.setCreateUser(user.getId());
                examResponseVm.setPaperType(taskExam.getPaperType());
                examResponseVm.setFrameTextContentId(examPaper.getFrameTextContentId());
                examResponse.add(examResponseVm);
                taskExam.setPaperItems(examResponse);
                //保存任务信息
                TextContent textContent = textContentService.jsonConvertInsert(examResponse, now, p -> {
                    TaskItemObject taskItemObject = new TaskItemObject();
                    taskItemObject.setExamPaperId(p.getId());
                    taskItemObject.setExamPaperName(p.getName());
                    return taskItemObject;
                });
                textContentService.insertByFilter(textContent);
                taskExam.setFrameTextContentId(textContent.getId());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    taskExam.setMajorTypeC(objectMapper.writeValueAsString(taskExam.getMajorType()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                taskExamMapper.insertSelective(taskExam);
                //保存任务关联用户信息
                if (taskExam.getUsers().size() > 0) {
                    for (int i = 0; i < taskExam.getUsers().size(); i++) {
                        TaskExamCustomerAnswer taskExamUser = new TaskExamCustomerAnswer();
                        taskExamUser.setUserId(taskExam.getUsers().get(i).getUserId());
                        taskExamUser.setTaskExamId(taskExam.getId());
                        taskExamUser.setCreateTime(now);
                        taskUserMapper.insertSelective(taskExamUser);
                    }
                }
                //保存任务关联专业
                if (taskExam.getMajorIds().size() > 0) {
                    for (int i = 0; i < taskExam.getMajorIds().size(); i++) {
                        TaskExam majorTask = new TaskExam();
                        majorTask.setMajorId(taskExam.getMajorIds().get(i));
                        majorTask.setTaskId(taskExam.getId());
                        taskExamMapper.insertTaskMajor(majorTask);
                    }
                }
            }
        } else {
            taskExam.setDeleted("U");
            taskExam.setUpdateTime(now);
            taskExam.setTitle(taskExam.getTaskName());
            Integer textContentId = taskExamMapper.selectByPrimaryKey(taskExam.getId()).getFrameTextContentId();
            TextContent textContent = textContentService.selectById(textContentId);
            //清空试卷任务的试卷Id，后面会统一设置
            List<Integer> paperIds = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class)
                    .stream()
                    .map(d -> d.getExamPaperId())
                    .collect(Collectors.toList());
            examPaperMapper.clearTaskPaper(paperIds);

            //创建组卷报文
            List<ExamPaperTitleItemVM> titleItemsVM = frameExamPaper(taskExam);
            //任务创建，自动化组卷开始
            if (titleItemsVM.size() > 0) {
                List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
                String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);
                //自动化组卷，根据题型与题数查询题库，再拼装结构化报文
                ExamPaperEditRequestVM examPaperEditRequestVM = new ExamPaperEditRequestVM();
                examPaperEditRequestVM.setName(taskExam.getTaskName());
                examPaperEditRequestVM.setPaperType(taskExam.getPaperType());
                examPaperEditRequestVM.setSuggestTime(taskExam.getSuggestTime());
                examPaperEditRequestVM.setTitleItems(titleItemsVM);
                ExamPaper examPaper = modelMapper.map(examPaperEditRequestVM, ExamPaper.class);
                TextContent frameTextContent = new TextContent(frameTextContentStr, now);
                textContentService.insertByFilter(frameTextContent);
                examPaper.setFrameTextContentId(frameTextContent.getId());
                examPaper.setCreateTime(now);
                examPaper.setCreateUser(user.getId());
                examPaper.setDeleted("I");
                examPaper.setQuestionCount(taskExam.getRadioNum() + taskExam.getActualOperNum() + taskExam.getMultipleNum());
                examPaper.setLimitStartTime(DateTimeUtil.parse(taskExam.getLimitStartTime(),DateTimeUtil.STANDER_FORMAT));
                examPaper.setLimitEndTime(DateTimeUtil.parse(taskExam.getLimitEndTime(),DateTimeUtil.STANDER_FORMAT));
                examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
                examPaper.setScore(taskExam.getScore());
                examPaperMapper.insertSelective(examPaper);

                //创建任务时关联试卷报文
                List<ExamResponseVM> examResponse = new ArrayList<>();
                ExamResponseVM examResponseVm = new ExamResponseVM();
                examResponseVm.setId(examPaper.getId());
                examResponseVm.setName(taskExam.getTaskName());
                examResponseVm.setCreateTime(DateTimeUtil.dateFormat(now));
                examResponseVm.setCreateUser(user.getId());
                examResponseVm.setPaperType(taskExam.getPaperType());
                examResponseVm.setFrameTextContentId(examPaper.getFrameTextContentId());
                examResponse.add(examResponseVm);
                taskExam.setPaperItems(examResponse);
                //任务信息
                TextContent newTextContent = textContentService.jsonConvertInsert(examResponse, now, p -> {
                    TaskItemObject taskItemObject = new TaskItemObject();
                    taskItemObject.setExamPaperId(p.getId());
                    taskItemObject.setExamPaperName(p.getName());
                    return taskItemObject;
                });
                textContentService.insertByFilter(newTextContent);
                taskExam.setFrameTextContentId(newTextContent.getId());

                //保存任务关联用户信息，先删除再保存
                taskUserMapper.deleteByTaskId(taskExam.getId());
                if (taskExam.getUsers().size() > 0) {
                    for (int i = 0; i < taskExam.getUsers().size(); i++) {
                        TaskExamCustomerAnswer taskExamUser = new TaskExamCustomerAnswer();
                        taskExamUser.setUserId(taskExam.getUsers().get(i).getUserId());
                        taskExamUser.setTaskExamId(taskExam.getId());
                        taskExamUser.setCreateTime(now);
                        taskUserMapper.insertSelective(taskExamUser);
                    }
                }
                //保存任务关联专业，先删除再保存
                taskExamMapper.deleteByTaskId(taskExam.getId());
                if (taskExam.getMajorIds().size() > 0) {
                    for (int i = 0; i < taskExam.getMajorIds().size(); i++) {
                        TaskExam majorTask = new TaskExam();
                        majorTask.setMajorId(taskExam.getMajorIds().get(i));
                        majorTask.setTaskId(taskExam.getId());
                        taskExamMapper.insertTaskMajor(majorTask);
                    }
                }
                //更新任务结构
                textContentService.jsonConvertUpdate(newTextContent, taskExam.getPaperItems(), p -> {
                    TaskItemObject taskItemObject = new TaskItemObject();
                    taskItemObject.setExamPaperId(p.getId());
                    taskItemObject.setExamPaperName(p.getName());
                    return taskItemObject;
                });
                textContentService.updateByIdFilter(newTextContent);
                taskExamMapper.updateByPrimaryKeySelective(taskExam);
            }
        }
        //更新试卷的taskId
        List<Integer> paperIds = taskExam.getPaperItems().stream().map(d -> d.getId()).collect(Collectors.toList());
        examPaperMapper.updateTaskPaper(taskExam.getId(), paperIds);
        taskExam.setId(taskExam.getId());
    }

    @Override
    public TaskRequestVM taskExamToVM(Integer id) {
        TaskExam taskExam = taskExamMapper.selectByPrimaryKey(id);
        TaskRequestVM vm = modelMapper.map(taskExam, TaskRequestVM.class);
        TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());
        List<ExamResponseVM> examResponseVMS = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(tk -> {
            ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(tk.getExamPaperId());
            ExamResponseVM examResponseVM = modelMapper.map(examPaper, ExamResponseVM.class);
            examResponseVM.setCreateTime(DateTimeUtil.dateFormat(examPaper.getCreateTime()));
            return examResponseVM;
        }).collect(Collectors.toList());
        vm.setPaperItems(examResponseVMS);
        return vm;
    }

    @Override
    public TaskRequestVM imitateTaskExamToVM(Integer id) {
        TaskExam taskExam = taskExamMapper.selectByPrimaryKey(id);
        TaskRequestVM vm = modelMapper.map(taskExam, TaskRequestVM.class);
        return vm;
    }

    @Override
    public TaskExam selectImitateById(Integer id) {
        TaskExam taskExam = taskExamMapper.selectByPrimaryKey(id);
        if (taskExam != null) {
            List<TaskExamCustomerAnswer> users = taskUserMapper.selectByTaskId(id);
            if (users.size() > 0) {
                taskExam.setUsers(users);
            }
            List<TaskExam> taskExams = taskExamMapper.getByTid(id);
            List<Integer> majorIds = new ArrayList<>();
            if (taskExams.size() > 0) {
                for (int i = 0; i < taskExams.size(); i++) {
                    majorIds.add(taskExams.get(i).getMajorId());
                }
            }
            if (StringUtils.isNotBlank(taskExam.getTrainIds())) {
                List<String> trains = trainMapper.getTrainById(taskExam.getTrainIds());
                taskExam.setTrainTitles(StringUtils.join(trains, ","));
            }
            taskExam.setMajorIds(majorIds);
        }
        return taskExam;
    }

    @Override
    public TaskExam selectById(Integer id) {
        TaskExam taskExam = taskExamMapper.selectByPrimaryKey(id);
        if (taskExam != null) {
            PaperInfo paperInfo = examPaperMapper.getExamPaperByTid(id);
            if (paperInfo != null) {
                taskExam.setLimitStartTime(DateTimeUtil.dateFormat(paperInfo.getLimitStartTime()));
                taskExam.setLimitEndTime(DateTimeUtil.dateFormat(paperInfo.getLimitEndTime()));
                ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(paperInfo.getId());
                if (examPaper != null) {
                    taskExam.setScore(examPaper.getScore());
                    taskExam.setSuggestTime(examPaper.getSuggestTime());
                }
            }
            List<TaskExamCustomerAnswer> users = taskUserMapper.selectByTaskId(id);
            if (users.size() > 0) {
                taskExam.setUsers(users);
            }
            List<TaskExam> taskExams = taskExamMapper.getByTid(id);
            List<Integer> majorIds = new ArrayList<>();
            if (taskExams.size() > 0) {
                for (int i = 0; i < taskExams.size(); i++) {
                    majorIds.add(taskExams.get(i).getMajorId());
                }
            }
            if (StringUtils.isNotBlank(taskExam.getTrainIds())) {
                List<String> trains = trainMapper.getTrainById(taskExam.getTrainIds());
                taskExam.setTrainTitles(StringUtils.join(trains, ","));
            }
            taskExam.setMajorIds(majorIds);
        }
        return taskExam;
    }
    @Override
    public List<TaskExam> getByUser(Integer userId) {
        return taskExamMapper.getByUser(userId);
    }

    @Override
    public List<TaskExam> getTaskListByMajorId(TaskPageRequestVM vo) {
        return taskExamMapper.getTaskListByMajor(vo);
    }

    @Override
    public int createPaper(Integer majorId, Integer user) {
        int paperId = 0;
        Date now = new Date();
        TaskExam taskExam = new TaskExam();
        List <Integer> majorIds = new ArrayList<>();
        majorIds.add(majorId);
        taskExam.setMajorIds(majorIds);
        taskExam.setRadioNum(10000);
        taskExam.setRadioScore(1);
        taskExam.setMultipleNum(10000);
        taskExam.setMultipleScore(1);
        taskExam.setActualOperNum(0);
        taskExam.setScore(100);
        taskExam.setTaskName("练习卷");
        List<ExamPaperTitleItemVM> titleItemsVM = frameExamPaper(taskExam);
        //任务创建，自动化组卷开始
        if (titleItemsVM.size() > 0) {
            List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
            String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);
            //自动化组卷，根据题型与题数查询题库，再拼装结构化报文
            ExamPaperEditRequestVM examPaperEditRequestVM = new ExamPaperEditRequestVM();
            examPaperEditRequestVM.setName(taskExam.getTaskName());
            examPaperEditRequestVM.setPaperType(ExamPaperTypeEnum.Fixed.getCode());
            examPaperEditRequestVM.setTitleItems(titleItemsVM);
            ExamPaper examPaper = modelMapper.map(examPaperEditRequestVM, ExamPaper.class);
            TextContent frameTextContent = new TextContent(frameTextContentStr, now);
            textContentService.insertByFilter(frameTextContent);
            examPaper.setFrameTextContentId(frameTextContent.getId());
            examPaper.setCreateUser(user);
            examPaper.setCreateTime(now);
            examPaper.setDeleted("I");
            examPaper.setQuestionCount(taskExam.getRadioNum() + taskExam.getActualOperNum() + taskExam.getMultipleNum());
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaper.setScore(taskExam.getScore());
            examPaperMapper.insertSelective(examPaper);
            paperId = examPaper.getId();
        }
        return paperId;
    }

    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItemsVM.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore()))
                ).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        List<String> dateTimes = examPaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            examPaper.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }

    //组卷
    private List<ExamPaperTitleItemVM> frameExamPaper(TaskExam taskExam){
        //组卷
        List<ExamPaperTitleItemVM> titleItemsVM = new ArrayList<>();
        //构造单选报文
        //题目设置  根据业务、安规、党建题目的占比 查询题库  todo
        List<Question> singleQuestions=new ArrayList<>();
        List<Question> multiQuestions=new ArrayList<>();
        int  radioNum =taskExam.getRadioNum();
        int multipleNum=taskExam.getMultipleNum();
        List<MajorType> list=taskExam.getMajorType();
        if (list != null &&  list.size()>0) {
            for (MajorType type:list) {
                String majorType= type.getType();
                int pecent= type.getPecent();
                int id=type.getMajorId();
                List<Integer> ids=new ArrayList<>();

                   int num=(radioNum*pecent)/100;
                   int num2= (multipleNum*pecent)/100;

                   if("01".equals(majorType)){
                        ids=taskExam.getMajorIds();
                   }else{
                       ids.add(id);
                   }
                List<Question> questions = newQuestionService.getQuestionByType(QuestionTypeEnum.SingleChoice.getCode(),num,ids);
                singleQuestions.addAll(questions);
                List<Question> questions2 = newQuestionService.getQuestionByType(QuestionTypeEnum.MultipleChoice.getCode(),num2,ids);
                multiQuestions.addAll(questions2);
            }

        }else{
            singleQuestions = newQuestionService.getQuestionByType(QuestionTypeEnum.SingleChoice.getCode(),taskExam.getRadioNum(),taskExam.getMajorIds());
            multiQuestions = newQuestionService.getQuestionByType(QuestionTypeEnum.MultipleChoice.getCode(),taskExam.getMultipleNum(),taskExam.getMajorIds());
        }


        List<QuestionEditRequestVM> transQuestions = new ArrayList<>();
        if (taskExam.getRadioNum() > 0) {
           // List<Question> questions = newQuestionService.getQuestionByType(QuestionTypeEnum.SingleChoice.getCode(),taskExam.getRadioNum(),taskExam.getMajorIds());
            if (singleQuestions.size() > 0) {
                for (int i = 0; i < singleQuestions.size(); i++) {
                    Question question = singleQuestions.get(i);
                    QuestionEditRequestVM questionEditRequestVM = JSON.parseObject(question.getContent(), QuestionEditRequestVM.class);
                    questionEditRequestVM.setId(question.getId());
                    questionEditRequestVM.setQuestionType(question.getQuestionType());
                    questionEditRequestVM.setTitle(questionEditRequestVM.getTitleContent());
                    questionEditRequestVM.setDifficult(question.getDifficult());
                    questionEditRequestVM.setItemOrder(question.getSort());
                    questionEditRequestVM.setScore(String.valueOf(taskExam.getRadioScore()));
                    questionEditRequestVM.setItems(questionEditRequestVM.getQuestionItemObjects());
                    transQuestions.add(questionEditRequestVM);
                }
            }
        }
        ExamPaperTitleItemVM radioTitleItem = new ExamPaperTitleItemVM();
        radioTitleItem.setName("单选题");
        radioTitleItem.setQuestionItems(transQuestions);
        radioTitleItem.setRadioNum(transQuestions.size());
        titleItemsVM.add(radioTitleItem);
        //构造多选报文
        List<QuestionEditRequestVM> transMultiQuestions = new ArrayList<>();
        if (taskExam.getMultipleNum() > 0) {
            //List<Question> multiQuestions = newQuestionService.getQuestionByType(QuestionTypeEnum.MultipleChoice.getCode(),taskExam.getMultipleNum(),taskExam.getMajorIds());
            if (multiQuestions.size() > 0) {
                for (int i = 0; i < multiQuestions.size(); i++) {
                    Question question = multiQuestions.get(i);
                    QuestionEditRequestVM questionEditRequestVM = JSON.parseObject(multiQuestions.get(i).getContent(), QuestionEditRequestVM.class);
                    questionEditRequestVM.setId(question.getId());
                    questionEditRequestVM.setQuestionType(question.getQuestionType());
                    questionEditRequestVM.setTitle(questionEditRequestVM.getTitleContent());
                    List<String> correctArray = Arrays.asList(questionEditRequestVM.getCorrect().split(","));
                    questionEditRequestVM.setCorrectArray(correctArray);
                    questionEditRequestVM.setScore(String.valueOf(taskExam.getMultipleScore()));
                    questionEditRequestVM.setDifficult(question.getDifficult());
                    questionEditRequestVM.setItemOrder(question.getSort());
                    questionEditRequestVM.setItems(questionEditRequestVM.getQuestionItemObjects());
                    transMultiQuestions.add(questionEditRequestVM);
                }
            }
        }
        ExamPaperTitleItemVM multiTitleItem = new ExamPaperTitleItemVM();
        multiTitleItem.setName("多选题");
        multiTitleItem.setQuestionItems(transMultiQuestions);
        multiTitleItem.setMultiNum(transMultiQuestions.size());
        titleItemsVM.add(multiTitleItem);
        return titleItemsVM;
    }
}
