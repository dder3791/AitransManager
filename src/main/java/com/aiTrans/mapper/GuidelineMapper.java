package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.GuidelineFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface GuidelineMapper extends BaseMapper{

	public List<GuidelineFormMap> findGuidelinByPage(GuidelineFormMap guidelineFormMap);
}
