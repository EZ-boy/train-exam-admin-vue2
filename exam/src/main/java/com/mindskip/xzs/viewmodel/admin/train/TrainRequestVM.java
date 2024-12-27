package com.mindskip.xzs.viewmodel.admin.train;

import com.mindskip.xzs.base.BasePage;

import java.util.List;

public class  TrainRequestVM extends BasePage {

    private Integer id;
    private String fileType;
    private Integer majorId;
    private String userNo;

    private String fileId;
    private String operType;
    private String commentContent;

    private Double currentTime;
    private Double totalTime;
    private Integer studyProgress;
    private String title;
    private String subTitle;
    private String studyType;
    private Integer likeNum;
    private Integer commentNum;
    private String practicalOperation;
    private String operNo;
    private Integer themeId;
    private String fileName;
    private String isCompleted;

    private List<Integer> majorIds;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getUserNo() { return userNo; }

    public void setUserNo(String userNo) { this.userNo = userNo; }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Double currentTime) {
        this.currentTime = currentTime;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getStudyProgress() {
        return studyProgress;
    }

    public void setStudyProgress(Integer studyProgress) {
        this.studyProgress = studyProgress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getPracticalOperation() {
        return practicalOperation;
    }

    public void setPracticalOperation(String practicalOperation) {
        this.practicalOperation = practicalOperation;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<Integer> getMajorIds() {
        return majorIds;
    }

    public void setMajorIds(List<Integer> majorIds) {
        this.majorIds = majorIds;
    }
}
