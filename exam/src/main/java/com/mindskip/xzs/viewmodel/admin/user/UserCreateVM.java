package com.mindskip.xzs.viewmodel.admin.user;



import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserCreateVM {

    private Integer id;

    @NotBlank
    private String userName;

    private String userNo;

    private String password;

    @NotBlank
    private String realName;

    private String age;

    private Integer status;

    private Integer sex;

    private String birthDay;

    private String phone;

    private Integer role;

    private Integer userLevel;

    private String orgNo;

    //岗位模板名称
    private String templateName;

    //岗位模板编码
    private String templateNo;
    //业务岗位名称
    private String positionName;
    //业务岗位编码
    private String positionNo;

    private String orgId;

    private String userId;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }
}
