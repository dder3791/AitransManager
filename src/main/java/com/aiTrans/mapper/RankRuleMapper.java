package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.RankFormMap;
import com.aiTrans.entity.RankRuleFormMap;
import com.aiTrans.entity.RegulationFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;

/***
 * 排名规则表
 * @author vampire
 *
 */
public interface RankRuleMapper extends BaseMapper{

	public List<RankRuleFormMap> findFirstPage(RankRuleFormMap rankRuleFormMap);
	
}
