package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.AppealFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface AppealMapper extends BaseMapper{
    /**
     * 查询所有译员申诉的信息
     * @param appealFormMap
     * @return
     */
	public List<AppealFormMap> findFirstPage(AppealFormMap appealFormMap);
}
