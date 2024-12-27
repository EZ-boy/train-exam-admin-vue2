package com.mindskip.xzs.domain.train;

import java.io.Serializable;
import java.util.Date;

public class Train implements Serializable {

    private Integer id;
    private String fileId;
    private String fileType;
    private String fileTypeName;
    private String fileName;
    private Integer majorId;
    private String major;
    private String title;
    private String subTitle;
    private String studyType;
    private Integer likeNum;
    private Integer commentNum;
    private String practicalOperation;
    private Date createTime;
    private Date updateTime;
    private String operNo;

    private Double currentTime;
    private Double totalTime;
    private Integer studyProgress;
    private Integer examPaperId;
    private String commentContent;
    private String userIsLike;
    private String userIsComment;

    private String operType;
    private String userNo;
    private Integer themeId;
    private String isCompleted;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

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

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
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

    public Integer getExamPaperId() {
        return examPaperId;
    }

    public void setExamPaperId(Integer examPaperId) {
        this.examPaperId = examPaperId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getUserIsLike() {
        return userIsLike;
    }

    public void setUserIsLike(String userIsLike) {
        this.userIsLike = userIsLike;
    }

    public String getUserIsComment() {
        return userIsComment;
    }

    public void setUserIsComment(String userIsComment) {
        this.userIsComment = userIsComment;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }
}
