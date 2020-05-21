package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AuditorProjectFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface AuditorProjectMapper extends BaseMapper{
	/**
	 * 通过项目id查找译员id
	 * @param projectId
	 * @return
	 */
	public List<AuditorProjectFormMap> findByProjectid(int projectId);
	
	/**
	 * 通过auditoryId AND projectId进行查找数据
	 * @param auditorProjectFormMap
	 * @return
	 */
	public List<AuditorProjectFormMap> findById(AuditorProjectFormMap auditorProjectFormMap);

/*
	public List<AuditorFormMap> findIdandName(AuditorFormMap auditor);
	
	public AuditorFormMap findById(ProjectFormMap project);*/
}
