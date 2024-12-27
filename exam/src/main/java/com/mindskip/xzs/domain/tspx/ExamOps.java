package com.mindskip.xzs.domain.tspx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: syf
 * @ClassName:ExamOps.java
 * @date:2024/2/27 10:22
 * @Description:
 */
@Data
public class ExamOps implements Serializable {
    private Integer opsId;
    private String orderNo;
    private String opsScore;
    private String opsUpdate;
    private String opsUpdateTime;
    private String examId;
    private String opsUpdateName;
}
