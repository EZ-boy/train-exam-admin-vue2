package com.mindskip.xzs.utility;

import com.mindskip.xzs.configuration.application.ApplicationContextProvider;
import com.mindskip.xzs.configuration.property.FtpConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2023/2/5 15:04
 *
 * @author HZ
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FtpUtil {

    @Autowired
    private static FtpConfig ftpConfig;

    public static final String DIR_SPLIT = "/";

    /**
     * 获取 FTPClient
     * @return FTPClient
     */
    private static FTPClient getFTPClient() {
      //  FTPClient ftpClient = new FtpConfig().connect();
        ftpConfig=ApplicationContextProvider.getBean(FtpConfig.class);
        FTPClient ftpClient = ftpConfig.connect();
        if (ftpClient == null || !FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            throw new RuntimeException("ftp客户端异常");
        }
        return ftpClient;
    }

    /**
     * 获取FTP某一特定目录下的所有文件名称
     *
     * @param ftpDirPath FTP上的目标文件路径
     */
    public List<String> getFileNameList(String ftpDirPath) {
        FTPClient ftpClient = getFTPClient();
        try {
            // 通过提供的文件路径获取 FTPFile 文件列表
            // FTPFile[] ftpFiles = ftpClient.listFiles(ftpDirPath, FTPFile::isFile); // 只获取文件
            // FTPFile[] ftpFiles = ftpClient.listFiles(ftpDirPath, FTPFile::isDirectory); // 只获取目录
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpDirPath);
            if (ftpFiles != null && ftpFiles.length > 0) {
                return Arrays.stream(ftpFiles).map(FTPFile::getName).collect(Collectors.toList());
            }
            log.error(String.format("路径有误，或目录【%s】为空", ftpDirPath));
        } catch (IOException e) {
            log.error("文件获取异常:", e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param uploadPath 上传路径
     * @param fileName   文件名
     * @param input      文件输入流
     * @return 上传结果
     */
    public static boolean upload(String uploadPath, String fileName, InputStream input) {
        FTPClient ftpClient = getFTPClient();
        try {
            // 切换到工作目录
            if (!ftpClient.changeWorkingDirectory(uploadPath)) {
                ftpClient.makeDirectory(uploadPath);
                ftpClient.changeWorkingDirectory(uploadPath);
            }
            // 文件写入
            boolean storeFile = ftpClient.storeFile(fileName, input);
            if (storeFile) {
                log.info("文件:{}上传成功", fileName);
            } else {
                throw new RuntimeException("ftp文件写入异常");
            }
            ftpClient.logout();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 下载文件 *
     *
     * @param ftpPath     FTP服务器文件目录 *
     * @param ftpFileName 文件名称 *
     * @return
     */
    public static int downloadFile(String ftpPath, String ftpFileName, String rootPath, HttpServletResponse response) {
        FTPClient ftpClient = getFTPClient();
        InputStream inStream = null;
        OutputStream outputStream = null;
        byte[] b = new byte[204800];
        try {
            response.reset();
            response.resetBuffer();
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + getFilename(ftpFileName) + "\"");

            FTPFile[] ftpFiles = ftpClient.listFiles(rootPath, file -> file.isFile() && file.getName().equals(ftpPath));
            if (ftpFiles != null && ftpFiles.length > 0) {
                FTPFile ftpFile = ftpFiles[0];
                inStream = ftpClient.retrieveFileStream(rootPath + "/" +ftpPath);
                outputStream = response.getOutputStream();
                int len = 0;
                while ((len = inStream.read(b)) > 0) {
                    outputStream.write(b, 0, len);
                    outputStream.flush();
                }
                log.info("fileName:{},size:{}", ftpFile.getName(), ftpFile.getSize());
                log.info("下载文件成功...");
                return 1;
            } else {
                log.info("文件不存在，filePathname:{},", ftpPath + DIR_SPLIT + ftpFileName);
            }
        } catch (Exception e) {
            log.error("下载文件失败...");
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public static String getFilename(String fileName) throws  Exception{
        fileName = new String(fileName.getBytes(),"iso-8859-1");
        return fileName;
    }


    public static  void download(String ftpFilePath,String fileName, String rootPath, HttpServletResponse response, HttpServletRequest request) {
        FTPClient ftp = getFTPClient();
        OutputStream outputStream = null;
        try{
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");// win10 ie edge 浏览器 和其他系统的ie
                fileName = fileName.replace("+", "%20");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            }
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            outputStream = response.getOutputStream();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.retrieveFile(rootPath + "/" +ftpFilePath, outputStream);
            ftp.logout();
        } catch (Exception e) {
            log.error("FTP文件下载失败！" + e.toString());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException ioe) {
                log.error(ioe.toString());
            }
        }
    }
    /**
     * 从FTP服务器删除文件或目录
     * 存在文件的目录无法删除
     * 
     * @param ftpPath  服务器文件存储路径
     * @param fileName 服务器文件存储名称
     * @return 删除结果
     */
    public boolean delete(String ftpPath, String fileName) {
        FTPClient ftpClient = getFTPClient();
        try {
            // 在 ftp 目录下获取文件名与 fileName 匹配的文件信息
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath, file -> file.getName().equals(fileName));
            // 删除文件
            if (ftpFiles != null && ftpFiles.length > 0) {
                boolean del;
                String deleteFilePath = ftpPath + DIR_SPLIT + fileName;
                FTPFile ftpFile = ftpFiles[0];
                if (ftpFile.isDirectory()) {
                    del = ftpClient.removeDirectory(deleteFilePath);
                } else {
                    del = ftpClient.deleteFile(deleteFilePath);
                }
                log.info(del ? "文件:{}删除成功" : "文件:{}删除失败", fileName);
                return del;
            } else {
                log.warn("文件:{}未找到", fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}

