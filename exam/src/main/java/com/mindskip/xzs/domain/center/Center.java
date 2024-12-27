package com.mindskip.xzs.domain.center;

import java.io.Serializable;
import java.util.Date;

public class Center implements Serializable {
 private Integer id;
 private String fileId;
 private String userNo;
 private Integer currentTime;
 private Integer totalTime;
 private Integer studyProgress;
 private Date createTime;
 private Date updateTime;
 private String fileType;
 private Integer majorId;
 private String title;
 private String subTitle;
 private String studyType;
 private Integer likeNum;
 private Integer commentNum;
 private String practicalOperation;
 private String operNo;

 private Integer kid;
 public Center() {
 }

 public Center(Integer id, String fileId, String userNo, Integer currentTime, Integer totalTime, Integer studyProgress, Date createTime, Date updateTime, String fileType, Integer majorId, String title, String subTitle, String studyType, Integer likeNum, Integer commentNum, String practicalOperation, String operNo) {
  this.id = id;
  this.fileId = fileId;
  this.userNo = userNo;
  this.currentTime = currentTime;
  this.totalTime = totalTime;
  this.studyProgress = studyProgress;
  this.createTime = createTime;
  this.updateTime = updateTime;
  this.fileType = fileType;
  this.majorId = majorId;
  this.title = title;
  this.subTitle = subTitle;
  this.studyType = studyType;
  this.likeNum = likeNum;
  this.commentNum = commentNum;
  this.practicalOperation = practicalOperation;
  this.operNo = operNo;
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

 public String getUserNo() {
  return userNo;
 }

 public void setUserNo(String userNo) {
  this.userNo = userNo;
 }

 public Integer getCurrentTime() {
  return currentTime;
 }

 public void setCurrentTime(Integer currentTime) {
  this.currentTime = currentTime;
 }

 public Integer getTotalTime() {
  return totalTime;
 }

 public void setTotalTime(Integer totalTime) {
  this.totalTime = totalTime;
 }

 public Integer getStudyProgress() {
  return studyProgress;
 }

 public void setStudyProgress(Integer studyProgress) {
  this.studyProgress = studyProgress;
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

 public Integer getKid() {
  return kid;
 }

 public void setKid(Integer kid) {
  this.kid = kid;
 }
}

