package com.mindskip.xzs.domain.tspx;

import com.mindskip.xzs.domain.TaskExamCustomerAnswer;
import com.mindskip.xzs.viewmodel.admin.exam.ExamResponseVM;
import com.mindskip.xzs.viewmodel.student.dashboard.TaskItemPaperVm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskExam implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    private Integer id;
    private Integer taskId;
    private Integer majorId;
    private String major;
    private String majorDelete;
    @NotNull
    private String taskName;
    @Size(min = 1, message = "请添加用户")
    @Valid
    private List<TaskExamCustomerAnswer> users;
    private List<ExamResponseVM> paperItems;
    private String limitStartTime;
    private String limitEndTime;
    private Integer radioNum;
    private Integer radioScore;
    private Integer multipleNum;
    private Integer multipleScore;
    private Integer actualOperNum;
    private Integer actualOperScore;
    private Integer score;
    private Date createTime;
    private Date updateTime;
    private String createUserNo;
    private String deleted;
    private String title;
    private String taskDesc;
    private Integer frameTextContentId;
    private Integer suggestTime;
    private Integer paperType;
    @NotNull
    private List<Integer> majorIds;
    @NotNull
    private String trainIds;
    private String imitate;

    private String trainTitles;
    //题型设置{ "01":8,}{

    //模拟
    private Integer opsId;

    //题型设置
    private List<MajorType> majorType;

    private String majorTypeC;

    private String opsUpdate;

    public String getOpsUpdate() {
        return opsUpdate;
    }

    public void setOpsUpdate(String opsUpdate) {
        this.opsUpdate = opsUpdate;
    }

    public Integer getOpsId() {
        return opsId;
    }

    public void setOpsId(Integer opsId) {
        this.opsId = opsId;
    }

    public String getMajorTypeC() {
        return majorTypeC;
    }

    public void setMajorTypeC(String majorTypeC) {
        this.majorTypeC = majorTypeC;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajorDelete() {
        return majorDelete;
    }

    public void setMajorDelete(String majorDelete) {
        this.majorDelete = majorDelete;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getLimitStartTime() {
        return limitStartTime;
    }

    public void setLimitStartTime(String limitStartTime) {
        this.limitStartTime = limitStartTime;
    }

    public String getLimitEndTime() {
        return limitEndTime;
    }

    public void setLimitEndTime(String limitEndTime) {
        this.limitEndTime = limitEndTime;
    }

    public Integer getRadioNum() {
        return radioNum;
    }

    public void setRadioNum(Integer radioNum) {
        this.radioNum = radioNum;
    }

    public Integer getRadioScore() {
        return radioScore;
    }

    public void setRadioScore(Integer radioScore) {
        this.radioScore = radioScore;
    }

    public Integer getMultipleNum() {
        return multipleNum;
    }

    public void setMultipleNum(Integer multipleNum) {
        this.multipleNum = multipleNum;
    }

    public Integer getMultipleScore() {
        return multipleScore;
    }

    public void setMultipleScore(Integer multipleScore) {
        this.multipleScore = multipleScore;
    }

    public Integer getActualOperNum() {
        return actualOperNum;
    }

    public void setActualOperNum(Integer actualOperNum) {
        this.actualOperNum = actualOperNum;
    }

    public Integer getActualOperScore() {
        return actualOperScore;
    }

    public void setActualOperScore(Integer actualOperScore) {
        this.actualOperScore = actualOperScore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserNo() {
        return createUserNo;
    }

    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Integer getFrameTextContentId() {
        return frameTextContentId;
    }

    public void setFrameTextContentId(Integer frameTextContentId) {
        this.frameTextContentId = frameTextContentId;
    }

    public List<TaskExamCustomerAnswer> getUsers() {
        return users;
    }

    public void setUsers(List<TaskExamCustomerAnswer> users) {
        this.users = users;
    }

    public List<ExamResponseVM> getPaperItems() {
        return paperItems;
    }

    public void setPaperItems(List<ExamResponseVM> paperItems) {
        this.paperItems = paperItems;
    }

    public Integer getSuggestTime() {
        return suggestTime;
    }

    public void setSuggestTime(Integer suggestTime) {
        this.suggestTime = suggestTime;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }

    public List<Integer> getMajorIds() {
        return majorIds;
    }

    public void setMajorIds(List<Integer> majorIds) {
        this.majorIds = majorIds;
    }

    public String getTrainIds() {
        return trainIds;
    }

    public void setTrainIds(String trainIds) {
        this.trainIds = trainIds;
    }

    public String getTrainTitles() {
        return trainTitles;
    }

    public void setTrainTitles(String trainTitles) {
        this.trainTitles = trainTitles;
    }

    public String getImitate() {
        return imitate;
    }

    public void setImitate(String imitate) {
        this.imitate = imitate;
    }

    public List<MajorType> getMajorType() {
        return majorType;
    }

    public void setMajorType(List<MajorType> majorType) {
        this.majorType = majorType;
    }
}
