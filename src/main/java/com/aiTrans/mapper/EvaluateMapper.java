package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.TraProjectFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface EvaluateMapper extends BaseMapper{
   
	/**
	 * 查询评价所有译员及项目信息
	 * @param evaluateFormMap
	 * @return
	 */
	public List<EvaluateFormMap> findFirstPage(EvaluateFormMap evaluateFormMap);
	
	/**
	 * 根据语言查询校对员
	 * @param evaluateFormMap
	 * @return
	 */
	public List<ProofreaderFormMap> findProofreadLanguage(EvaluateFormMap evaluateFormMap);
	
	
	/**
	 * 根据译员id查询所有项目
	 * @param evaluateFormMap
	 * @return
	 */
	public List<EvaluateFormMap>  findMyEvaluatePage(EvaluateFormMap evaluateFormMap);


    /**
     * 根据译员id AND 项目id 查询评价数
     * @param evaluateFormMap
     * @return
     */
	public int appealCount(EvaluateFormMap evaluateFormMap);

}
