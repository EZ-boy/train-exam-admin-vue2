package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: lifx
 * @ClassName:UserOrPosition.java
 * @date:2024/2/5 9:08
 * @Description:人员岗位关联实体
 */
@Data
public class UserOrPosition implements Serializable {
    //组织id
    private String orgId;
    //业务岗位编码
    private String positionNo;
    //用户id
    private String userId;

    private String userName;

    private String deleteFlag;
}
