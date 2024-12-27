package com.mindskip.xzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lifx
 * @ClassName:Position.java
 * @date:2024/2/5 8:36
 * @Description:岗位表
 */
@Data
public class Position implements Serializable {
    //岗位模板名称
    private String templateName;
    //岗位模板编码
    private String templateNo;
    //业务岗位名称
    private String positionName;
    //业务岗位编码
    private String positionNo;
    //组织id
    private String orgNo;

    private String userNo;

    private String userName;

    private List<Position> list=new ArrayList<>();

}
