package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface CustomerMapper extends BaseMapper{
	
	public List<CustomerFormMap> findCustomerProjectPage(CustomerFormMap customerFormMap);

	public List<CustomerFormMap> findInternalPage(CustomerFormMap customerFormMap);
	
	public List<CustomerFormMap> findForeignPage(CustomerFormMap customerFormMap);
	
	public List<CustomerFormMap> findLargePage(CustomerFormMap customerFormMap);

	public List<CustomerFormMap> findOnlineCustomerPage(CustomerFormMap customerFormMap);

	public List<CustomerFormMap> findIdAndName();
	
	public int findProjectCountByCustomerId(int id);
	
	public CustomerFormMap findById(ProjectFormMap projectFormMap); 

}
