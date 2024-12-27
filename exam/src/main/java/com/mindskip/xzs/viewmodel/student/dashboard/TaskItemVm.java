package com.mindskip.xzs.viewmodel.student.dashboard;


import java.util.Date;
import java.util.List;

public class TaskItemVm {
    private Integer id;
    private String taskName;
    private String limitStartTime;
    private String limitEndTime;
    private List<TaskItemPaperVm> paperItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<TaskItemPaperVm> getPaperItems() {
        return paperItems;
    }

    public void setPaperItems(List<TaskItemPaperVm> paperItems) {
        this.paperItems = paperItems;
    }
}
