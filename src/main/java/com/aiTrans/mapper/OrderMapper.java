package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.OrderFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface OrderMapper extends BaseMapper{
    /**
     * 查询所有译员申诉的信息
     * @param appealFormMap
     * @return
     */
	public List<OrderFormMap> findClientOrderByPage(OrderFormMap appealFormMap);
	
	public OrderFormMap findOrderById(String id);
	
	/*public findClientOrderByPage(OrderFormMap orderFormMap);*/
	
	public void updateTaskState(OrderFormMap appealFormMap);
}
