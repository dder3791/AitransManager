package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.EvaluateClientFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface EvaluateClientMapper extends BaseMapper{
   
	/**
	 * 查询评价所有译员及项目信息
	 * @param evaluateClientFormMap
	 * @return
	 */
	public List<EvaluateClientFormMap> findEvaluatePage(EvaluateClientFormMap evaluateClientFormMap);
	

}
