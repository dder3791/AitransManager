package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ProofreaderMapper extends BaseMapper{

	public List<ProofreaderFormMap> findIdandName(ProofreaderFormMap verifierFormMap);
	public ProofreaderFormMap findById(ProjectFormMap project);
	
}
