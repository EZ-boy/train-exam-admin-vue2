package com.mindskip.xzs.service.impl.tspx;

import com.mindskip.xzs.domain.tspx.GradeDetails;
import com.mindskip.xzs.repository.GradeDetailsMapper;
import com.mindskip.xzs.service.tspx.GradeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: tspx
 * @package: com.mindskip.xzs.service.impl.tspx
 * @className: GradeDetailsServiceImpl
 * @author: yangletian
 * @date: 2024/2/27 14:53
 * @version: 1.0
 */
@Service
public class GradeDetailsServiceImpl implements GradeDetailsService {

    @Autowired
    private GradeDetailsMapper gradeDetailsMapper;
    @Override
    public List<GradeDetails> getGradeDetails(GradeDetails grade) {
        return gradeDetailsMapper.getGradeDetailsList(grade);
    }


}
