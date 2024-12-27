package com.mindskip.xzs.domain;

import java.io.Serializable;
import java.util.Date;

public class TaskExamCustomerAnswer implements Serializable {

    private static final long serialVersionUID = -556842372977600137L;

    private Integer id;

    /**
     * 任务Id
     */
    private Integer taskExamId;

    /**
     * 创建者
     */
    private Integer createUser;

    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 任务完成情况(Json)
     */
    private Integer textContentId;

    private String userName;

    private String realName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskExamId() {
        return taskExamId;
    }

    public void setTaskExamId(Integer taskExamId) {
        this.taskExamId = taskExamId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTextContentId() {
        return textContentId;
    }

    public void setTextContentId(Integer textContentId) {
        this.textContentId = textContentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
