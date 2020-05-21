package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.CorpusTableFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface CorpusTableMapper extends BaseMapper{
	/**
	 * 查询所有语料信息带分页
	 * @param corpusTableFormMap
	 * @return
	 */
	public List<CorpusTableFormMap> findcorpus(CorpusTableFormMap corpusTableFormMap);
	/**
	 * 添加原文译文文件信息
	 * @param corpusTableFormMap
	 */
	public void savefile(CorpusTableFormMap corpusTableFormMap);
	/**
	 * 修改原文译文文件信息
	 * @param corpusTableFormMap
	 */
	public void updatefile(CorpusTableFormMap corpusTableFormMap);
	/**
	 * 导出txt语句
	 * @param corpusTableFormMap
	 * @return
	 */
	public List<CorpusTableFormMap> exporttxt(CorpusTableFormMap corpusTableFormMap);
	/**
	 * 根据id查询对于domainId
	 * @param corpusTableFormMap
	 * @return
	 */
	public CorpusTableFormMap finddomainId(CorpusTableFormMap corpusTableFormMap);
	/**
	 * 查询所有原文与译文
	 * @param corpusTableFormMap
	 * @return
	 */
	public List<CorpusTableFormMap> queryByAll(CorpusTableFormMap corpusTableFormMap);
	/*public String findUser(String id);*/
}
