package com.mindskip.xzs.service.impl.exam;

import com.mindskip.xzs.domain.main.GradeDetails;
import com.mindskip.xzs.repository.GradeDetailsMapper;
import com.mindskip.xzs.service.exam.GradeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: exam
 * @package: com.mindskip.xzs.service.impl.exam
 * @className: GradeDetailsServiceImpl
 * @author: lifx
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
