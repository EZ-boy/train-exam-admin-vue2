package com.mindskip.xzs.domain;

import java.io.Serializable;
import java.util.Date;

public class CourseFiles implements Serializable {

    private static final long serialVersionUID = 8509645224550501395L;

    private Integer id;
    private Integer majorId;
    private Integer themeId;
    private Date createTime;
    private String upDateNo;
    private Date upDateTime;
    private String fileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpDateNo() {
        return upDateNo;
    }

    public void setUpDateNo(String upDateNo) {
        this.upDateNo = upDateNo;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
