package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.RoleFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface RoleMapper extends BaseMapper{
	public List<RoleFormMap> seletUserRole(RoleFormMap map);
	
	public void deleteById(RoleFormMap map);
}
