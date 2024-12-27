package com.mindskip.xzs.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :
 * @date : 2023-7-19
 * @desc : 附件信息
 */
public class Files{
    /** 附件标识 */
    private int fileId ;
    /** 关联标识 */
    private String relaId ;
    /** 附件名称 */
    private String fileName ;
    /** 存放路径 */
    private String filePath ;
    /** 文件类型 */
    private String fileType ;
    /** 文件大小 */
    private String fileSize ;
    /** 管理单位 */
    private String orgNo ;
    /** 上传时间 */
    private Date operTime ;
    /** 操作员 */
    private String operEmpNo ;
    /** 数据操作类型，标准代码“I”插入“U”更新 */
    private String dataOperType ;

    /** 附件标识 */
    public int getFileId(){
        return this.fileId;
    }
    /** 附件标识 */
    public void setFileId(int fileId){
        this.fileId=fileId;
    }
    /** 关联标识 */
    public String getRelaId(){
        return this.relaId;
    }
    /** 关联标识 */
    public void setRelaId(String relaId){
        this.relaId=relaId;
    }
    /** 附件名称 */
    public String getFileName(){
        return this.fileName;
    }
    /** 附件名称 */
    public void setFileName(String fileName){
        this.fileName=fileName;
    }
    /** 存放路径 */
    public String getFilePath(){
        return this.filePath;
    }
    /** 存放路径 */
    public void setFilePath(String filePath){
        this.filePath=filePath;
    }
    /** 文件类型 */
    public String getFileType(){
        return this.fileType;
    }
    /** 文件类型 */
    public void setFileType(String fileType){
        this.fileType=fileType;
    }
    /** 文件大小 */
    public String getFileSize(){
        return this.fileSize;
    }
    /** 文件大小 */
    public void setFileSize(String fileSize){
        this.fileSize=fileSize;
    }
    /** 管理单位 */
    public String getOrgNo(){
        return this.orgNo;
    }
    /** 管理单位 */
    public void setOrgNo(String orgNo){
        this.orgNo=orgNo;
    }
    /** 上传时间 */
    public Date getOperTime(){
        return this.operTime;
    }
    /** 上传时间 */
    public void setOperTime(Date operTime){
        this.operTime=operTime;
    }
    /** 操作员 */
    public String getOperEmpNo(){
        return this.operEmpNo;
    }
    /** 操作员 */
    public void setOperEmpNo(String operEmpNo){
        this.operEmpNo=operEmpNo;
    }
    /** 数据操作类型，标准代码“I”插入“U”更新 */
    public String getDataOperType(){
        return this.dataOperType;
    }
    /** 数据操作类型，标准代码“I”插入“U”更新 */
    public void setDataOperType(String dataOperType){
        this.dataOperType=dataOperType;
    }
}