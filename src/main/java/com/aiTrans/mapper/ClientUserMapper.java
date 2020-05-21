package com.aiTrans.mapper;
import java.util.List;

import com.aiTrans.entity.ClientUserFormMap;
import com.aiTrans.mapper.base.BaseMapper;

public interface ClientUserMapper extends BaseMapper{
    
	/**
	 * 分页展示平台用户
	 * @param clientUserFormMap
	 * @return
	 */
	public List<ClientUserFormMap> findClientUserByPage(ClientUserFormMap clientUserFormMap);
	
	
	public void editUserCertifiState(ClientUserFormMap clientUserFormMap);
	
	/**
	 * 根据用户银行账户查找用户
	 * @param bankAccount
	 * @return
	 */
	public ClientUserFormMap findByBankAccount(String bankAccount);
}
