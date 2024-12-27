package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.Files;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper extends BaseMapper<Files> {
    public int insertFileInfo(Files files);

    public Map<String, Object> getFileDataByFileId(String fileId);

    public String getSeqAttachId();

    public List<Map<String, Object>> getRealFileList(Map<String, Object> paramMap);
}
