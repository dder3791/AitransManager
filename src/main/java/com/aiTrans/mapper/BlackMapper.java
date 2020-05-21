package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.BlackFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface BlackMapper extends BaseMapper{

	public List<BlackFormMap> findBlackPage(BlackFormMap blackFormMap);
	
	
}
