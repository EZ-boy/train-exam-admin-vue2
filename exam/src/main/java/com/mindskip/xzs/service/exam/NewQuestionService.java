package com.mindskip.xzs.service.exam;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.Question;
import com.mindskip.xzs.service.BaseService;
import com.mindskip.xzs.viewmodel.admin.question.QuestionEditRequestVM;
import com.mindskip.xzs.viewmodel.admin.question.QuestionPageRequestVM;

import java.util.List;

public interface NewQuestionService extends BaseService<Question> {

    PageInfo<Question> page(QuestionPageRequestVM requestVM);

    Question insertFullQuestion(QuestionEditRequestVM model, Integer userId);

    Question updateFullQuestion(QuestionEditRequestVM model);

    QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId);

    QuestionEditRequestVM getQuestionEditRequestVM(Question question);

    Integer selectAllCount();

    List<Integer> selectMothCount();

    List<Question> getQuestionByType(Integer questionType, Integer rows, List<Integer> majorIds);
}
