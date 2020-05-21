package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AppealClientFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface AppealClientMapper extends BaseMapper{
   
	/**
	 * 查询申诉所有译员及项目信息
	 * @param evaluateClientFormMap
	 * @return
	 */
	public List<AppealClientFormMap> findAppealPage(AppealClientFormMap apealClientFormMap);
	

	public void editAppealState(AppealClientFormMap apealClientFormMap);
}
