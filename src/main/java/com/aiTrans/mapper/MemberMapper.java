package com.aiTrans.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiTrans.entity.MemberFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface MemberMapper extends BaseMapper{

	public List<MemberFormMap> findPersonalPage(MemberFormMap memberFormMap);
	
	public List<MemberFormMap> findGroupPage(MemberFormMap memberFormMap);
	
}
