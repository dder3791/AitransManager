package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.RankFormMap;
import com.aiTrans.entity.RegulationFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;

/***
 * 排名实体表
 * @author vampire
 *
 */
public interface RankMapper extends BaseMapper{

	public List<TranslatorFormMap> findFirstPage(TranslatorFormMap translatorFormMap);
	
	/**
	 * 计算译员对应的积分
	 */
	public List<TranslatorFormMap> rankCount(int projectStateId);
	
	
	/**
	 * 根据译员id查找对应的评价
	 * @param translatorId
	 * @return
	 */
	public int findEvaluateD(int translatorId);
	
	
	public int findScore(RankFormMap rankFormMap);
}
