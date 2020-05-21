package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ResFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface ResourcesMapper extends BaseMapper {
	public List<ResFormMap> findChildlists(ResFormMap map);

	public List<ResFormMap> findRes(ResFormMap map);

	public void updateSortOrder(List<ResFormMap> map);
	
	public List<ResFormMap> findUserResourcess(String userId);
	
	/**
	 * 通过角色id查找该角色所拥有的菜单资源
	 * @param roleId
	 * @return
	 */
	public List<ResFormMap> findTransferRes(String roleId);

	
}
