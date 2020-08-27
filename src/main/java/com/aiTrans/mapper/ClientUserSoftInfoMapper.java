package com.aiTrans.mapper;

import java.util.Map;

import com.aiTrans.entity.ClientCloudFormMap;
import com.aiTrans.entity.ClientUserSoftInfoFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface ClientUserSoftInfoMapper extends BaseMapper{
	ClientUserSoftInfoFormMap findByCodeAndName(Map<String,Object> params);
	int insertData(ClientUserSoftInfoFormMap params);
	int updateData(ClientUserSoftInfoFormMap params);
	Map<String,Object> findTrans(Map<String,Object> params);
	ClientCloudFormMap findCloud(Integer id);
	
}
