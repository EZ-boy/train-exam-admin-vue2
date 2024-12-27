package com.mindskip.xzs.viewmodel.admin.examOps;

import lombok.Data;

/**
 * @author: lifx
 * @ClassName:RxamOpsRequestVM.java
 * @date:2024/2/27 10:51
 * @Description:
 */
@Data
public class RxamOpsRequestVM {
    private Integer opsId;
    private String orderNo;
    private String opsScore;
    private String opsUpdate;
    private String opsUpdateTime;
    private String examId;
    private String opsUpdateName;
}
