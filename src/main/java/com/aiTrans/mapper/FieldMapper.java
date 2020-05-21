package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.FieldFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface FieldMapper extends BaseMapper{
	
	/**
	 * 分页查询领域
	 * @param FieldFormMap
	 * @return
	 */
	public List<FieldFormMap> findFieldPage(FieldFormMap fieldFormMap);

}
