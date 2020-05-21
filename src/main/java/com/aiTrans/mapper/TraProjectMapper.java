package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AuditorProjectFormMap;
import com.aiTrans.entity.TraProjectFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface TraProjectMapper extends BaseMapper{
	/**
	 * 通过项目id查找译员id
	 * @param projectId
	 * @return
	 */
	public List<TraProjectFormMap> findByProjectid(int projectId);
	
	/**
	 * 通过transId AND projectId进行查找数据
	 * @param transProjectFormMap
	 * @return
	 */
	public List<TraProjectFormMap> findById(TraProjectFormMap transProjectFormMap);
	
	/*public List<TraProjectFormMap> findFirstPage(TraProjectFormMap transProjectFormMap);*/
	/*
	public List<AuditorFormMap> findIdandName(AuditorFormMap auditor);
	
	public AuditorFormMap findById(ProjectFormMap project);*/
	

	/**
	 * 根据译员id查询相对应的项目Id
	 * @param id
	 * @return
	 */
	public List<TraProjectFormMap> findTran_project(String translatorId);
	
}
