package com.aiTrans.mapper;


import java.util.List;

import com.aiTrans.entity.ProcedureTypeFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface ProcedureTypeMapper extends BaseMapper{

	public List<ProcedureTypeFormMap> findType();

	
	
}
