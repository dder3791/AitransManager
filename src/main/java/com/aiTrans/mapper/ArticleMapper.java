package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ArticleFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ArticleMapper extends BaseMapper{

	public List<ArticleFormMap> findArticleByPage(ArticleFormMap articleFormMap);
}
