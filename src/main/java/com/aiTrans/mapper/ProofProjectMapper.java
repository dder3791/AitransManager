package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ProofProjectFormMap;
import com.aiTrans.entity.TraProjectFormMap;
import com.aiTrans.entity.ProofProjectFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ProofProjectMapper extends BaseMapper{

	/**
	 * 通过项目id查找译员id
	 * @param projectId
	 * @return
	 */
	public List<ProofProjectFormMap> findByProjectid(int projectId);
	
	/**
	 * 通过varifyId AND projectId进行查找数据
	 * @param verifyProjectFormMap
	 * @return
	 */
	public List<ProofProjectFormMap> findById(ProofProjectFormMap verifyProjectFormMap);
/*
	public List<AuditorFormMap> findIdandName(AuditorFormMap auditor);
	
	public AuditorFormMap findById(ProjectFormMap project);*/
}
