package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AuditorFormMap;
import com.aiTrans.entity.CorpusTableListFormMap;
import com.aiTrans.entity.DomainFormMap;
import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.TermTableListFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface TermTableListMapper extends BaseMapper{
	
	/**
	 * 2017年4月24日17:16:19
	 * 术语的二级列表入口
	 * @param termFormMap
	 * @return List<TermFormMap>
	 */
	/**
	 * 根据id查询对于的术语名称
	 * @param id
	 * @return
	 */
	public String lookTermNameById(String id);
	/**
	 * 查询所有术语带分页
	 * @param termFormMap
	 * @return
	 */
	public List<TermTableListFormMap> findFirstPage(TermTableListFormMap termTableListFormMap);
	/**
	 * 根据语言查找对于的待定校对人员
	 * @param termTableListFormMap
	 * @return
	 */
    public int findProofreadnickname(TermTableListFormMap termTableListFormMap);
    /**
     * 根据语言查找对于的待定审核人员
     * @param termTableListFormMap
     * @return
     */
	public int findAuditernickname(TermTableListFormMap termTableListFormMap);
	/**
	 * 根据id查询术语信息
	 * @param id
	 * @return
	 */
	public TermTableListFormMap findTermByid(int id);
	/**
	 * 根据名字查询领域id
	 * @param termTableListFormMap
	 * @return
	 */
	public int findDomainName(TermTableListFormMap termTableListFormMap);
	/**
	 * 根据语言查询校对员
	 * @param termTableListFormMap
	 * @return
	 */
	public List<ProofreaderFormMap> findProofreadLanguage(TermTableListFormMap termTableListFormMap);
	/**
	 * 根据语言查询审核员
	 * @param termTableListFormMap
	 * @return
	 */
	public List<AuditorFormMap> findAuditerLanguage(TermTableListFormMap termTableListFormMap);
	/**
	 * 根据语言及领域查询译员
	 * @param termTableListFormMap
	 * @return
	 */
	public List<TranslatorFormMap> findTranslatorLanguage(TermTableListFormMap termTableListFormMap);
	/**
	 * 查询领域信息
	 * @return
	 */
	public List<TermTableListFormMap> findDomain();

	/**
	 * 根据id查找主外表有无相同字段
	 * @param id
	 * @return
	 */
	public int findForeignKey(TermTableListFormMap termTableListFormMap);
	
}
