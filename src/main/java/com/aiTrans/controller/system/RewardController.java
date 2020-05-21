package com.aiTrans.controller.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.RewardMapper;
import com.aiTrans.util.Common;

/**
 * reward稿酬管理
 * @author SS
 * @version 4.0v
 */
@Controller
@RequestMapping("/reward/")
public class RewardController extends BaseController{
	@Inject
	private RewardMapper rewardMapper;
	@Inject
	private ProjectMapper projectMapper;
	private String id;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	
	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		id = getPara("id");
		
		RewardFormMap rewardFormMap=rewardMapper.findById(id);
		
		if(rewardFormMap.get("priceKilo")==null || rewardFormMap.get("priceTotal")==null){
			rewardFormMap.put("priceKilo",0);
			rewardFormMap.put("priceTotal", 0);
		}else{
			String companyReward=rewardFormMap.get("priceKilo").toString();
			String companyTotal=rewardFormMap.get("priceTotal").toString();
			
			rewardFormMap.put("priceKilo", (Object)companyReward.substring(0, companyReward.length()-2));
			rewardFormMap.put("priceTotal", (Object)companyTotal.substring(0, companyTotal.length()-2));
		}
		
		
		if(rewardFormMap.get("translatorFeeKilo")==null|| rewardFormMap.get("translatorFeeTotal")==null){
			rewardFormMap.put("translatorFeeKilo",0);
			rewardFormMap.put("translatorFeeTotal",0);
		}else{
			String transReward=rewardFormMap.get("translatorFeeKilo").toString();
			String transTotal=rewardFormMap.get("translatorFeeTotal").toString();
			
			rewardFormMap.put("translatorFeeKilo", (Object)transReward.substring(0, transReward.length()-2));
			rewardFormMap.put("translatorFeeTotal", (Object)transTotal.substring(0, transTotal.length()-2));
			
		}
		
		
		if(rewardFormMap.get("auditoryFeeKilo")==null||rewardFormMap.get("auditoryFeeTotal")==null){
			rewardFormMap.put("auditoryFeeKilo",0);
			rewardFormMap.put("auditoryFeeTotal",0);
		}else{
			String auditoryReward=rewardFormMap.get("auditoryFeeKilo").toString();
			String auditoryTotal=rewardFormMap.get("auditoryFeeTotal").toString();
			
			rewardFormMap.put("auditoryFeeKilo", (Object)auditoryReward.substring(0, auditoryReward.length()-2));
			rewardFormMap.put("auditoryFeeTotal", (Object)auditoryTotal.substring(0, auditoryTotal.length()-2));
		}
		
		
		if(rewardFormMap.get("proofFeeKilo")==null ||rewardFormMap.get("proofFeeTotal")==null){
			rewardFormMap.put("proofFeeKilo",0);
			rewardFormMap.put("proofFeeTotal",0);
		}else{
			String verifyReward=rewardFormMap.get("proofFeeKilo").toString();
			String verifyTotal=rewardFormMap.get("proofFeeTotal").toString();
			
			rewardFormMap.put("proofFeeKilo", (Object)verifyReward.substring(0, verifyReward.length()-2));
			rewardFormMap.put("proofFeeTotal", (Object)verifyTotal.substring(0, verifyTotal.length()-2));
		}
		
		model.addAttribute("rewardInfo",rewardFormMap);
		return Common.BACKGROUND_PATH + "/system/reward/edit_reward";
	}
	
	
	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="项目管理",methods="项目管理-项目流程-稿酬修改")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity(Model model) throws Exception{
		
		RewardFormMap rewardFormMap = getFormMap(RewardFormMap.class);
		rewardMapper.editEntity(rewardFormMap);
		return "success";
	}

}
