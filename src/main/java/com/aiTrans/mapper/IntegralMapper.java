package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.CategoryFormMap;
import com.aiTrans.entity.IntegralFormMap;
import com.aiTrans.mapper.base.BaseMapper;

/**
 * 积分
 * @author vampire
 *
 */
public interface IntegralMapper extends BaseMapper{
    
	
	public List<IntegralFormMap> findFirstPage(IntegralFormMap integralFormMap);

	public List<CategoryFormMap> findCategory();

}
