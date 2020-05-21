package com.aiTrans.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiTrans.entity.UserFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface UserMapper extends BaseMapper{
	
	
	//2017年4月11日13:56:59
	public String findRoleName(@Param("accountName")String accountName);
	public String findUserName(@Param("accountName")String accountName);

	public List<UserFormMap> findUserPage(UserFormMap userFormMap);
	
}
