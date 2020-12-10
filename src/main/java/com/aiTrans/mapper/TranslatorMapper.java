package com.aiTrans.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.base.BaseMapper;


public interface TranslatorMapper extends BaseMapper{

	
	public List<TranslatorFormMap> findTransferPage(TranslatorFormMap translatorFormMap);
	
	/**
	 * 分页展示所有校对员信息
	 * @return
	 */
	public List<TranslatorFormMap>  findProofreaderPage(TranslatorFormMap trnslatorFormMap);
	
	
	/**
	 * 分页展示所有翻译员信息
	 * @return
	 */
	public List<TranslatorFormMap>  findToTransPage(TranslatorFormMap translatorFormMap);
	
	
	/**
	 * 分页展示所有审核员信息
	 * @return
	 */
	public List<TranslatorFormMap>  findTransToAuditoryPage(TranslatorFormMap translatorFormMap);
	
	
	
	/**
	 * 修改译员为翻译员
	 */
	public void editTOTrans(TranslatorFormMap translatorFormMap);
	/**
	 * 修改译员为校对员
	 */
	public void editTransTOProofreader(TranslatorFormMap trnslatorFormMap);
	/**
	 * 修改译员为审核员
	 */
	public void editTransToAuditory(TranslatorFormMap trnslatorFormMap);
	/**
	 * 20180308
	 * 平台译员认证信息审核
	 */
	public void transClientCre(TranslatorFormMap trnslatorFormMap);
	
	
	
	/**
	 * 查看参与协同项目的译员
	 * @param translatorFormMap
	 * @return
	 */
	public List<TranslatorFormMap> findParticipantsPage(TranslatorFormMap translatorFormMap);

	public List<TranslatorFormMap> findOnlineTransferPage(TranslatorFormMap translatorFormMap);

	public List<TranslatorFormMap> findBlack(TranslatorFormMap translatorFormMap);

	public List<TranslatorFormMap> findtransIdandName(TranslatorFormMap translatorFormMap);
	
	public TranslatorFormMap findById(ProjectFormMap project);


	/**
	 * 查看我的任务
	 * @param translatorFormMap
	 * @return
	 */
	public List<TranslatorFormMap> findMyTaskPage(TranslatorFormMap translatorFormMap);




	/**
	 * 查看我的稿酬
	 * @param rewardFormMap
	 * @return
	 */
	public List<RewardFormMap> findMyRewardPage(RewardFormMap rewardFormMap);




	/**
	 * 查看昵称为参数name的记录数
	 * @param name
	 * @return
	 */
	public int findNicknameCount(String name);

	/**
	 * 通过译员id查询其所学专业名称
	 * @param 译员id
	 * @return major.name
	 */
	public String findMajorNameByTraId(String id);


	/**
	 * 查询所有译员信息
	 * @param evaluateFormMap
	 * @return
	 */
	public List<TranslatorFormMap>  findTranslator();

	
	/**
	 * 根据昵称查询译员id
	 * @param nickname
	 * @return
	 */
	public String findTranslatorId(String nickname);
	
	public void editPoint(TranslatorFormMap trnslatorFormMap);
	
	public TranslatorFormMap findByAccountNumber(String accountNumber);
	
	public void editTransLanLevDoma(TranslatorFormMap translatorFormMap);
	
	public TranslatorFormMap queryById(TranslatorFormMap translatorFormMap);
	
	public List<TranslatorFormMap> findTransferSynPage(TranslatorFormMap TranslatorFormMap);
	
	public TranslatorFormMap findTransferSynDesc(@Param("qid") Integer qid);
}
