package com.mindskip.xzs.service.file;

import com.mindskip.xzs.domain.Files;

import java.util.List;
import java.util.Map;



public interface FileService {

	//新增附件信息
	public int insertFileInfo(Files files);

	//根据附件Id获取附件明细信息
	public Map<String, Object> getFileDataByFileId(String fileId);

	//获取附件id序列
	public String getSeqAttachId();

	//获取关联上传文件列表
	public List<Map<String, Object>> getRealFileList(Map<String, Object> paramMap);


	
}
