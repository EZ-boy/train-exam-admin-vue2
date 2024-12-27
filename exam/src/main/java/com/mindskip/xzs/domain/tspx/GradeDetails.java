package com.mindskip.xzs.domain.tspx;

import lombok.Data;

import java.io.Serializable;

/**
 * @projectName: tspx
 * @package: com.mindskip.xzs.domain.tspx
 * @className: GradeDetails
 * @author: yangletian  培训情况查看成绩实体类
 * @date: 2024/2/27 14:55
 * @version: 1.0
 */
@Data
public class GradeDetails implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    private String  orgNo;
    private String  orgName;
    private String userNo;
    private String userName;
    //实操成绩
    private String userScore;
    private String userScoreTime;
    //实操考试名
    private String userTaskName;
    //模拟成绩
    private String opsScore;
    private String opsScoreTime;
    //模拟考试名
    private String opsTaskName;
    private int pageNo;
    private int pageSize;

}
