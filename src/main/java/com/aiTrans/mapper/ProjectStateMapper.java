package com.aiTrans.mapper;

import java.util.List;
import com.aiTrans.entity.ProjectStateFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ProjectStateMapper extends BaseMapper{

	public List<ProjectStateFormMap> findprojectStateByPage(ProjectStateFormMap projectStateFormMap);
}
