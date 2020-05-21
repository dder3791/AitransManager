package com.aiTrans.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiTrans.entity.AuditorFormMap;
import com.aiTrans.entity.CorpusTableFormMap;
import com.aiTrans.entity.CorpusTableListFormMap;
import com.aiTrans.entity.DomainFormMap;
import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface CorpusTableListMapper extends BaseMapper{
	
	//2017年4月21日10:32:21 
	/**通过一级列表的id查找其语料名称,方便二级列表中的增删改查操作
	 * 
	 * @param id
	 * @return corpusName
	 */
	public String findCorpusNameById(String id);
	/**
	 * 查询所有语料带分页
	 * @param corpusFormMap
	 * @return
	 */
	public List<CorpusTableListFormMap> findFirstPage(CorpusTableListFormMap corpusFormMap);
	
	/**
	 * 根据语言查找对于的待定校对人员
	 * @param corpusTableListFormMap
	 * @return
	 */
	public int findProofreadnickname(CorpusTableListFormMap corpusTableListFormMap);
	/**
	 * 根据语言查找对于的待定审核人员
	 * @param corpusTableListFormMap
	 * @return
	 */
	public int findAuditernickname(CorpusTableListFormMap corpusTableListFormMap);
	
	/**
	 * 根据语言查询校对员
	 * @param corpusTableListFormMap
	 * @return
	 */
	public List<ProofreaderFormMap> findProofreadLanguage(CorpusTableListFormMap corpusTableListFormMap);
	/**
	 * 根据语言查询审核员
	 * @param corpusTableListFormMap
	 * @return
	 */
	public List<AuditorFormMap> findAuditerLanguage(CorpusTableListFormMap corpusTableListFormMap);
	/**
	 * 根据语言及领域查询译员
	 * @param corpusTableListFormMap
	 * @return
	 */
	public List<TranslatorFormMap> findTranslatorLanguage(CorpusTableListFormMap corpusTableListFormMap);
	/**
	 * 查询翻译领域	
	 * @return
	 */
	public List<CorpusTableListFormMap> findDomain();

	/**
	 * 通过id查询corpusTableList对象
	 * @param id
	 * @return
	 */
	public CorpusTableListFormMap findCorpusTableListById(String id);

	/**
	 * 通过name查询corpusTableList对象
	 * @param name
	 * @return
	 */
	public CorpusTableListFormMap findCorpusTableListByName(String name);
	
	/**
	 * 添加corpusTableList
	 * @param corpusTableListFormMap
	 */
	public void addCorpusTableList(CorpusTableListFormMap corpusTableListFormMap);

	/**
	 * 修改corpusTableList
	 * @param corpusTableListFormMap
	 */
	public void editCorpusTableList(CorpusTableListFormMap corpusTableListFormMap);

	/**
	 * 根据名字查询领域id
	 * @param corpusTableListFormMap
	 * @return
	 */
	public int findDomainName(CorpusTableListFormMap corpusTableListFormMap);
	
	/**
	 * 根据id删除corpusTableList
	 * @param id
	 */
	public void deleteById(String id);

	
	/**
	 * 根据id查找主外表有无相同字段
	 * @param id
	 * @return
	 */
	public int findForeignKey(CorpusTableListFormMap corpusTableListFormMap);
	
}

