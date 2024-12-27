package com.mindskip.xzs.service.file;

import java.util.List;
import java.util.Map;

import com.mindskip.xzs.domain.Files;
import com.mindskip.xzs.repository.FileMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends BaseServiceImpl<Files> implements FileService {

	private final FileMapper fileMapper;

	@Override
	public int insertFileInfo(Files files) {
		return fileMapper.insertFileInfo(files);
	}

	@Autowired
	public FileServiceImpl(FileMapper fileMapper) {
		super(fileMapper);
		this.fileMapper = fileMapper;
	}

	@Override
	public Map<String, Object> getFileDataByFileId(String fileId) {
		return fileMapper.getFileDataByFileId(fileId);
	}

	@Override
	public String getSeqAttachId() {
		return fileMapper.getSeqAttachId();
	}

	@Override
	public List<Map<String, Object>> getRealFileList(Map<String, Object> paramMap) {
		return fileMapper.getRealFileList(paramMap);
	}




}
