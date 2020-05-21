package com.aiTrans.mapper;



import java.util.List;

import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ProjectTypeMapper extends BaseMapper{

	public List<ProjectTypeFormMap> findProjectType();
}
