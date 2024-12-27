package com.mindskip.xzs.controller.file;


import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.Files;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.center.Center;
import com.mindskip.xzs.service.file.FileService;
import com.mindskip.xzs.utility.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController("FileController")
@RequestMapping(value = "/api/file")
public class FileController extends BaseApiController {

    @Value("${spring.ftp.ip}")
    private String ftpHost;

    @Value("${spring.ftp.port}")
    private String ftpPort;

    @Value("${spring.ftp.username}")
    private String userName;

    @Value("${spring.ftp.password}")
    private String password;

    @Value("${spring.ftp.path}")
    private String rootPath;

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 上传文件-上传单个文件
     */
    @RequestMapping(value = { "/uploadfile" })
    @ResponseBody
    public  RestResponse<List<Map<String, Object>>> uploadFile(MultipartFile file,String realId) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        retList.add(doUpload(file,realId ));
        retMap.put("fileList",retList);
        return RestResponse.ok(retList);
    }

    /**
     * 上传文件-上传多个文件
     */
    @RequestMapping(value = { "/uploadfiles" })
    @ResponseBody
    public RestResponse<List<Map<String, Object>>> uploadFiles( MultipartFile[] file, String realId) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        if(file.length > 0){
            for (MultipartFile uploadFile : file) {
                Map<String, Object> fileMap = doUpload(uploadFile, realId );
                retList.add(fileMap);
            }
            retMap.put("fileList",retList);
        }
        return RestResponse.ok(retList);
    }


    //上传文件
    public  Map<String, Object> doUpload(MultipartFile file, String realId){
        Files files = new Files();
        Map<String,Object> retMap = new HashMap<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

        try {

            System.out.println("ftp信息######" + ftpHost + "##" + ftpPort + "##" + userName + "##" + password + "##" + rootPath);
            User user = getCurrentUser();
            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());

            String fileId = sd.format(new Date()) + String.format("%04d", (new Random()).nextInt(1000));
            String newFileName = fileId + "" + fileType;

            FtpUtil.upload(rootPath,newFileName,file.getInputStream());

            //数据库保存文件信息
            files.setRelaId(realId);
            files.setFileName(fileName);
            files.setFilePath(newFileName);
            files.setFileType(fileType);
            files.setFileSize( Long.toString(file.getSize() / 1000) + "KB");
            files.setOrgNo(user.getOrgNo());
            files.setOperEmpNo(user.getUserNo());
            files.setDataOperType("I");

            int i = fileService.insertFileInfo(files);
            int id = files.getFileId();
            if (i > 0) {
                retMap.put("code" ,"200");
                retMap.put("msg", "success");
                retMap.put("fileId" ,id);
                retMap.put("fileNameNew", id + fileType);
            } else {
                retMap.put("code" ,"201");
                retMap.put("msg", "false");
                retMap.put("fileId" ,id);
                retMap.put("fileNameNew", id + fileType);
            }
        } catch (Exception e) {
            retMap.put("code" ,"500");
            retMap.put("msg", "未知的错误");
            retMap.put("data", "错误信息>>>" + e);
        }
        System.out.println("======上传文件出参=====" + retMap);
        return retMap;
    }

    /**
     * 文件管理-下载文件
     */
    @RequestMapping(value = { "/download" })
    public void downLoadFile(HttpServletResponse response, HttpServletRequest request, String fileId) {
       Map<String, Object> dataMap = fileService.getFileDataByFileId(fileId);
       String filePath = dataMap.get("filePath") == null ? "" : dataMap.get("filePath").toString();
       String fileName = dataMap.get("fileName") == null ? "" : dataMap.get("fileName").toString();
       System.out.println("下载文件信息>>>>>filePath>>>" + filePath + "<<<fileName>>>" + fileName);;
       FtpUtil.download(filePath,fileName, rootPath, response, request);
    }

    /**
     * 文件管理-获取关联上传文件列表
     */
    @RequestMapping(value = { "/getRealFileList" })
    @ResponseBody
    public Map getRealFileList(@RequestBody Map<String, Object> paramMap) {
        System.out.println("初始入参："+paramMap);
        Map retMap = new HashMap();
        try {
            List<Map<String, Object>> retList = fileService.getRealFileList(paramMap);
            retMap.put("list",retList);
            retMap.put("msg","获取成功");
        } catch (Exception e) {
            System.out.println("系统错误异常信息>>>>>>>>" + e);
            retMap.put("msg","未知的系统错误");
        }
        return retMap;
    }



    public static String get4UUID(){
        long time = System.currentTimeMillis();
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return time+idd[1];
    }
}
