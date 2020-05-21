package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AuditorFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface AuditorMapper extends BaseMapper{

	public List<AuditorFormMap> findIdandName(AuditorFormMap auditor);
	
	public AuditorFormMap findById(ProjectFormMap project);
}
