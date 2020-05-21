package com.aiTrans.mapper;


import java.util.List;

import com.aiTrans.entity.OnLineCustomerFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface OnLineCustomerMapper extends BaseMapper{
   
	/**
	 * 查询所有线上用户
	 */
	public List<OnLineCustomerFormMap> findCompanyByPage(OnLineCustomerFormMap onLineCusFormMap);
	
	
}
