package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.SoftInfoFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface SoftInfoMapper extends BaseMapper{

	/**
	 * 查看软件在线用户
	 * @param softInfoFormMap
	 * @return
	 */
	public List<SoftInfoFormMap> findOnlineUserPage(SoftInfoFormMap softInfoFormMap);

	/**
	 * 查看软件使用情况(包括离线)
	 * @param softInfoFormMap
	 * @return
	 */
	public List<SoftInfoFormMap> findAllUserPage(SoftInfoFormMap softInfoFormMap);

}
