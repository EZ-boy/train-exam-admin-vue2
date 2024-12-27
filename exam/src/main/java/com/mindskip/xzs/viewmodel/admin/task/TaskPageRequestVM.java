package com.mindskip.xzs.viewmodel.admin.task;

import com.mindskip.xzs.base.BasePage;



public class TaskPageRequestVM extends BasePage {
    private String major;
    private Integer majorId;

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
}
