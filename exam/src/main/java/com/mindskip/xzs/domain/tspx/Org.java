package com.mindskip.xzs.domain.tspx;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Org implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    private Integer sortNo;
    private String orgNo;
    private String orgName;
    private String pOrgNo;
    private String orgType;
    private String orgShorthand;
    private String isLeaf;
    private List<Org> children;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getpOrgNo() {
        return pOrgNo;
    }

    public void setpOrgNo(String pOrgNo) {
        this.pOrgNo = pOrgNo;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgShorthand() {
        return orgShorthand;
    }

    public void setOrgShorthand(String orgShorthand) {
        this.orgShorthand = orgShorthand;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public List<Org> getChildren() {
        return children;
    }

    public void setChildren(List<Org> children) {
        this.children = children;
    }
}
