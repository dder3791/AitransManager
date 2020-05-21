package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.IntegralFormMap;
import com.aiTrans.entity.RegulationFormMap;
import com.aiTrans.mapper.base.BaseMapper;

/**
 * 积分实体表
 * @author vampire
 *
 */
public interface RegulationMapper extends BaseMapper{
    
	
	public List<RegulationFormMap> findFirstPage(RegulationFormMap regulationFormMap);
}
