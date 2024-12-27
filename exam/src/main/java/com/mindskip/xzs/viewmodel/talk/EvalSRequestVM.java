package com.mindskip.xzs.viewmodel.talk;

import com.mindskip.xzs.base.BasePage;

/**
 * @author: lifx
 * @ClassName:EvalSRequestVM.java
 * @date:2023/12/12 16:58
 * @Description:
 */
public class EvalSRequestVM extends BasePage {
    private String operId;  //评论id
    private String operType;   //操作类型 01评论 02点赞 03 下赞
    private String operContent;    //评论内容
    private String operEval;   //评分
    private String expId;  //经验id
    private String createId;   //创建人
    private String createBy;    //创建id
    private String createTime; //创建时间

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperContent() {
        return operContent;
    }

    public void setOperContent(String operContent) {
        this.operContent = operContent;
    }

    public String getOperEval() {
        return operEval;
    }

    public void setOperEval(String operEval) {
        this.operEval = operEval;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
