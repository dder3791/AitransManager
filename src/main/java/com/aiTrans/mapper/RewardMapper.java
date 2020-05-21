package com.aiTrans.mapper;

import java.util.List;

import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface RewardMapper extends BaseMapper{

	public RewardFormMap lookReward(int pid);

	/**
	 * 查看公司的资金流动记录 
	 * @param rewardFormMap
	 * @return
	 */
	public List<RewardFormMap> findCompanyRecordByPage(RewardFormMap rewardFormMap);

	/**
	 * 查看译员稿酬记录
	 * @param rewardFormMap
	 * @return
	 */
	public List<RewardFormMap> findTraRecordByPage(RewardFormMap rewardFormMap);

	/**
	 * 根据项目id(reward和project共用同一id)查看关于该项目的资金流动
	 * @param id
	 * @return
	 */
	/*public ProjectFormMap findCompanyRecordById(String id);*/
	
	/**
	 * 根据id查找稿酬信息
	 * @param id
	 * @return
	 */
	public RewardFormMap findById(String id);
	
}
