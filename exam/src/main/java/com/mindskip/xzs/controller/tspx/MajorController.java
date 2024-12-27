package com.mindskip.xzs.controller.tspx;


import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.SysDict;
import com.mindskip.xzs.domain.train.Train;
import com.mindskip.xzs.domain.tspx.Major;
import com.mindskip.xzs.domain.tspx.TaskExam;
import com.mindskip.xzs.domain.tspx.Theme;
import com.mindskip.xzs.service.tspx.CoursewareService;
import com.mindskip.xzs.service.tspx.MajorService;
import com.mindskip.xzs.service.tspx.NewTaskExamService;
import com.mindskip.xzs.viewmodel.admin.task.TaskPageRequestVM;
import com.mindskip.xzs.viewmodel.admin.train.TrainRequestVM;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("TspxMajorController")
@Slf4j
@RequestMapping(value = "/api/tspx/major")
public class MajorController extends BaseApiController {

    @Autowired
    private MajorService majorService;

    @Autowired
    private CoursewareService coursewareService;

    @Autowired
    private NewTaskExamService taskExamService;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public RestResponse<List<Major>> init() {
        List<Major> pageInfo = majorService.init();
        return RestResponse.ok(pageInfo);
    }

    @RequestMapping(value = "/insertMajor", method = RequestMethod.POST)
    public RestResponse insertMajor(@RequestBody Map map) {
        Map retMap = new HashMap();
        Major major = new Major();
        major.setMajor(MapUtils.getString(map, "major"));
        major.setOperNo(MapUtils.getString(map, "operNo"));
        major.setMajorDesc(MapUtils.getString(map, "majorDesc"));
        major.setMajorType(MapUtils.getString(map, "majorType"));
        major.setDeleted("I");
        int count = majorService.insertMajor(major);
        int id = major.getId();
        List<Map<String,Object>> list = (List) map.get("themeList");
        if(list != null && list.size() > 0){
            List<Theme> themeList = new ArrayList<>();
            for (Map<String,Object> paramMap : list) {
                if(StringUtils.isNotEmpty(MapUtils.getString(paramMap, "theme"))){
                    Theme theme = new Theme();
                    theme.setMajorId(id);
                    theme.setOperNo(MapUtils.getString(map, "operNo"));
                    theme.setTheme(MapUtils.getString(paramMap, "theme"));
                    theme.setDeleted("I");
                    themeList.add(theme);
                }
            }
            if(themeList.size() > 0){
                int count1 = majorService.insertTheme(themeList);
            }
        }
        if(count > 0){
            retMap.put("message","保存成功");
        }else{
            retMap.put("message","保存失败");
        }
        return RestResponse.ok(retMap);
    }

    @RequestMapping(value = "/getMajorById", method = RequestMethod.POST)
    public RestResponse getMajorById(@RequestBody Major major) {
        Map map = majorService.getMajorById(major.getId());
        List<Theme> list = majorService.getThemeByMajorId(major.getId());
        map.put("themeList",list);
        return RestResponse.ok(map);
    }

    @RequestMapping(value = "/updateMajor", method = RequestMethod.POST)
    public RestResponse updateMajor(@RequestBody Map map) {
        Map retMap = new HashMap();
        Major major = new Major();
        major.setId((Integer) map.get("id"));
        major.setMajor(String.valueOf(map.get("major")));
        major.setOperNo(String.valueOf(map.get("operNo")));
        int count = majorService.updateMajor(major);

        List<Map<String,Object>> themeList = (List) map.get("themeList");
        if(themeList.size() > 0){
            for (int i = 0; i < themeList.size(); i++) {
                if(themeList.get(i).get("id") != null){
                    int themeCount = majorService.countThemeById((Integer) themeList.get(i).get("id"));
                    if(themeCount > 0){
                        Theme theme1 = new Theme();
                        theme1.setId((Integer) themeList.get(i).get("id"));
                        theme1.setOperNo(String.valueOf(map.get("operNo")));
                        theme1.setTheme(String.valueOf(themeList.get(i).get("theme")));
                        theme1.setDeleted("U");
                        majorService.updateTheme(theme1);
                    }
                }else{
                    List<Theme> list = new ArrayList();
                    if(!"".equals(String.valueOf(themeList.get(i).get("theme")))){
                        Theme theme = new Theme();
                        theme.setMajorId((Integer) map.get("id"));
                        theme.setOperNo(String.valueOf(map.get("operNo")));
                        theme.setTheme(String.valueOf(themeList.get(i).get("theme")));
                        theme.setDeleted("I");
                        list.add(theme);
                        majorService.insertTheme(list);
                    }
                }
            }
        }
        if(count > 0){
            retMap.put("message","修改成功");
        }else{
            retMap.put("message","修改失败");
        }
        return RestResponse.ok(retMap);
    }

    @RequestMapping(value = "/deleteMajor", method = RequestMethod.GET)
    public RestResponse deleteMajor(@RequestParam("id") Integer id) {
        Map retMap = new HashMap();

        TrainRequestVM vo = new TrainRequestVM();
        vo.setMajorId(id);
        List<Train> coursewareList = coursewareService.getCoursewareList(vo);
        if (coursewareList != null && coursewareList.size() > 0){
            retMap.put("message","该专业存在关联的课件，不可删除");
            retMap.put("code","202");
            return RestResponse.ok(retMap);
        }
        TaskPageRequestVM model = new TaskPageRequestVM();
        model.setMajorId(id);
        List<TaskExam> taskList = taskExamService.getTaskListByMajorId(model);
        if (taskList != null && taskList.size() > 0){
            retMap.put("message","该专业存在关联的任务，不可删除");
            retMap.put("code","203");
            return RestResponse.ok(retMap);
        }

        int i = majorService.deleteMajor(id);
        majorService.deleteThemeByMajorId(id);
        if(i>0){
            retMap.put("message","删除成功");
            retMap.put("code","200");
        }else{
            retMap.put("message","删除失败");
            retMap.put("code","201");
        }
        return RestResponse.ok(retMap);
    }

    @RequestMapping(value = "/deleteTheme", method = RequestMethod.GET)
    public RestResponse deleteTheme(@RequestParam("id") Integer id) {
        Map retMap = new HashMap();

        int i = majorService.deleteTheme(id);
        if(i>0){
            retMap.put("message","删除成功");
        }else{
            retMap.put("message","删除失败");
        }
        return RestResponse.ok(retMap);
    }

    /**
     * 上传题库
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadQuestionBank")
    public Map<String, Object> uploadQuestionBank(MultipartFile file,String majorId,String createUser) {
        Map<String, Object> retMap = new HashMap<String,Object>();
        String retMsg = "";
        InputStream is = null;
        FileInputStream fis = null;
        File f1 = null;
        try {

            if (file != null) {
                String fileName1 = file.getOriginalFilename();
                String fileType = fileName1.substring(fileName1.lastIndexOf(".")+1,fileName1.length());
                String allowType = "xls,xlsx";// 允许上传的附件后缀名

                if(!allowType.contains(fileType)){// 后缀名校验未通过
                    retMap.put("retCode", "201");
                    retMap.put("retMsg", "允许上传的附件类型为：" + allowType);
                    return retMap;
                }
                List<Major> majorList = new ArrayList<Major>();
                is = file.getInputStream();
                f1 = convertInputStreamToFile(file, is);
                fis = new FileInputStream(f1);

                retMap.put("retCode", "203");
                retMap.put("retMsg", "导入失败");
                // 构造 XSSFWorkbook 对象，strPath 传入文件路径
                XSSFWorkbook xwb;
                if(fis !=null){
                    xwb = new XSSFWorkbook(fis);
                    // 读取第一章表格内容
                    XSSFSheet sheet = xwb.getSheetAt(0);
                    Object value = null;
                    XSSFRow row = null;
                    XSSFCell cell = null;

                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }
                        Major majorBo = new Major();
                        for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                            cell = row.getCell(j);
                            if (j==0 && (cell == null ||"".equals(cell.toString())  || "".equals(cell.toString().trim()))) {
                                retMsg += "第"+(i+1)+"行,第"+(j+1)+"列，题型不能为空！";
                                continue;
                            }
                            if (j==1 && (cell == null ||"".equals(cell.toString())  || "".equals(cell.toString().trim()))) {
                                retMsg += "第"+(i+1)+"行,第"+(j+1)+"列，难度不能为空！";
                                continue;
                            }
                            if (j==2 && (cell == null ||"".equals(cell.toString())  || "".equals(cell.toString().trim()))) {
                                retMsg += "第"+(i+1)+"行,第"+(j+1)+"列，题目不能为空！";
                                continue;
                            }
                            if (j==9 && (cell == null ||"".equals(cell.toString())  || "".equals(cell.toString().trim()))) {
                                retMsg += "第"+(i+1)+"行,第"+(j+1)+"列，答案不能为空！";
                                continue;
                            }
                            if (cell == null) {
                                continue;
                            }
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = cell.getNumericCellValue();
                                    break;
                                default:
                                    value = cell.toString();
                            }
                            switch (j) {
                                case 0://题型
                                    String questionType = "";
                                    if (StringUtils.equals("单选题", formatString(value))){
                                        questionType = "1";
                                    } else if (StringUtils.equals("多选题", formatString(value))){
                                        questionType = "2";
                                    } else if (StringUtils.equals("判断题", formatString(value))){
                                        questionType = "3";
                                    } else if (StringUtils.equals("填空题", formatString(value))){
                                        questionType = "4";
                                    } else if (StringUtils.equals("简答题", formatString(value))){
                                        questionType = "5";
                                    }
                                    majorBo.setQuestionType(questionType);
                                    break;
                                case 1://难度
                                    String difficult = "1";
                                    if (StringUtils.equals("简单", formatString(value))){
                                        difficult = "1";
                                    } else if (StringUtils.equals("一般", formatString(value))){
                                        difficult = "2";
                                    } else if (StringUtils.equals("困难", formatString(value))){
                                        difficult = "3";
                                    }
                                    majorBo.setDifficult(difficult);
                                    break;
                                case 2://题目
                                    majorBo.setTitleContent(formatString(value));
                                    break;
                                case 3://选项1
                                    majorBo.setOption1(formatString(value));
                                    break;
                                case 4://选项2
                                    majorBo.setOption2(formatString(value));
                                    break;
                                case 5://选项3
                                    majorBo.setOption3(formatString(value));
                                    break;
                                case 6://选项4
                                    majorBo.setOption4(formatString(value));
                                    break;
                                case 7://选项5
                                    majorBo.setOption5(formatString(value));
                                    break;
                                case 8://选项6
                                    majorBo.setOption6(formatString(value));
                                    break;
                                case 9://答案
                                    majorBo.setCorrect(formatString(value));
                                    break;
                                case 10://解析
                                    majorBo.setAnalyze(formatString(value));
                                    break;
                            }
                        }

                        if(majorBo != null && majorBo.getQuestionType() != null &&StringUtils.isNotEmpty(majorBo.getQuestionType())) {
                            majorBo.setMajorId(majorId);
                            majorBo.setCreateUser(createUser);

                            String content = "{\"titleContent\":\"" + majorBo.getTitleContent() +
                                    "\",\"analyze\":\"" + majorBo.getAnalyze() + "\",\"questionItemObjects\":[";
                            if (majorBo.getOption1() != null && StringUtils.isNotEmpty(majorBo.getOption1())) {
                                content += "{\"prefix\":\"A\",\"content\":\"" + majorBo.getOption1() + "\",\"score\":\"\",\"itemUuid\":\"\"}";
                            }
                            if (majorBo.getOption2() != null && StringUtils.isNotEmpty(majorBo.getOption2())) {
                                content += ",{\"prefix\":\"B\",\"content\":\"" + majorBo.getOption2() + "\",\"score\":\"\",\"itemUuid\":\"\"}";
                            }
                            if (majorBo.getOption3() != null && StringUtils.isNotEmpty(majorBo.getOption3())) {
                                content += ",{\"prefix\":\"C\",\"content\":\"" + majorBo.getOption3() + "\",\"score\":\"\",\"itemUuid\":\"\"}";
                            }
                            if (majorBo.getOption4() != null && StringUtils.isNotEmpty(majorBo.getOption4())) {
                                content += ",{\"prefix\":\"D\",\"content\":\"" + majorBo.getOption4() + "\",\"score\":\"\",\"itemUuid\":\"\"}";
                            }
                            if (majorBo.getOption5() != null && StringUtils.isNotEmpty(majorBo.getOption5())) {
                                content += ",{\"prefix\":\"E\",\"content\":\"" + majorBo.getOption5() + "\",\"score\":\"\",\"itemUuid\":\"\"}";
                            }
                            if (majorBo.getOption6() != null && StringUtils.isNotEmpty(majorBo.getOption6())) {
                                content += ",{\"prefix\":\"F\",\"content\":\"" + majorBo.getOption6() + "\",\"score\":\"\",\"itemUuid\":\"\"}";
                            }
                            content += "],\"correct\":\"" + majorBo.getCorrect() + "\"}";

                            majorBo.setContent(content);
                            majorList.add(majorBo);
                        }
                    }
                    int i = majorService.insertTextContentList(majorList);
                    retMap.put("retCode", "200");
                    retMap.put("retMsg", "导入成功" +i+ "条，导入失败" +(majorList.size()-i)+ "条。" + retMsg);
                }
            } else {
                retMap.put("retCode", "202");
                retMap.put("retMsg", "附件为空!");
            }
        } catch (Exception e){
            log.error("上传题库系统出错>>>",e);
            retMap.put("retCode", "500");
            retMap.put("retMsg", "系统异常");
        } finally {
            try {
                if (is != null){
                    is.close();
                }
                if (fis != null){
                    fis.close();
                }
                if (f1 != null){
                    f1.delete();
                }
            } catch (Exception e){
            }
        }
        return retMap;
    }
    private String formatString(Object object) {
        String str = String.valueOf(object);
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        str = m.replaceAll("");
        return str;
    }
    public File convertInputStreamToFile(MultipartFile multipartFile, InputStream inputStream) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(inputStream, outputStream);
        outputStream.close();
        return file;
    }

    @RequestMapping(value = "/getSysDictByType", method = RequestMethod.POST)
    public RestResponse getSysDictByType(@RequestBody Map<String, Object> paramMap) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        List<SysDict> sysDictList = majorService.getSysDictByType(MapUtils.getString(paramMap, "dictType"));
        retMap.put("sysDictList",sysDictList);
        return RestResponse.ok(retMap);
    }

}
