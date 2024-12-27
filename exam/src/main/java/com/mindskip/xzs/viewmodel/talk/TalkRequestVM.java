package com.mindskip.xzs.viewmodel.talk;

import com.mindskip.xzs.base.BasePage;

/**
 * @author: lifx
 * @ClassName:Talk.java
 * @date:2023/12/12 15:59
 * @Description:
 */
public class TalkRequestVM extends BasePage {
    private String expId;  //id
    private String expTitle;    //经验标题
    private String expContent;  //经验内容
    private String subjectId;   //专业分类
    private String createBy;   //创建人
    private String createTime;  //创建时间
    private String expState;    //状态 01 草稿  02 已发布  03 已删除

    private int avgOperEval;    //评分平均值

    public int getAvgOperEval() {
        return avgOperEval;
    }

    public void setAvgOperEval(int avgOperEval) {
        this.avgOperEval = avgOperEval;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getExpTitle() {
        return expTitle;
    }

    public void setExpTitle(String expTitle) {
        this.expTitle = expTitle;
    }

    public String getExpContent() {
        return expContent;
    }

    public void setExpContent(String expContent) {
        this.expContent = expContent;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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

    public String getExpState() {
        return expState;
    }

    public void setExpState(String expState) {
        this.expState = expState;
    }

}
