package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.TermTableFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface TermTableMapper extends BaseMapper {
	/**
	 * 查找term数据
	 * @param termTableFormMap
	 * @return
	 */
    public List<TermTableFormMap> findterms(TermTableFormMap termTableFormMap);
    /**
     * 存入原译文
     * @param yermTableFormMap
     */
	public void savefile(TermTableFormMap yermTableFormMap);
	/**
	 * 修改原译文
	 * @param termTableFormMap
	 */
    public void updatefile(TermTableFormMap termTableFormMap);
    /**
     * 导出txt
     * @param termTableFormMap
     * @return
     */
    public List<TermTableFormMap> exporttxt(TermTableFormMap termTableFormMap);
    /**
     * 根据id查询原文译文
     * @param termTableFormMap
     * @return
     */
    public TermTableFormMap findByid(TermTableFormMap termTableFormMap);
    /**
     * 查询所有原文与译文
     * @param termTableFormMap
     * @return
     */
    public List<TermTableFormMap> queryByAll(TermTableFormMap termTableFormMap);
}
