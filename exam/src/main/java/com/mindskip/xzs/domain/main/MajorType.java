package com.mindskip.xzs.domain.main;

import com.mindskip.xzs.domain.TaskExamCustomerAnswer;
import com.mindskip.xzs.viewmodel.admin.exam.ExamResponseVM;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MajorType implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    private Integer majorId;
    private int pecent;
    private String type;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public int getPecent() {
        return pecent;
    }

    public void setPecent(int pecent) {
        this.pecent = pecent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
