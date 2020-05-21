package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ArticleFormMap;
import com.aiTrans.entity.QuestionFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface QuestionMapper extends BaseMapper{

	public List<QuestionFormMap> findQuestionByPage(QuestionFormMap questionFormMap);
}
