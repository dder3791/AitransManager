package com.aiTrans.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.mapper.ProofreaderMapper;

/**
 * 审核员
 * @author SS
 * @version 4.0v
 */
@Controller
@RequestMapping("/vierfier/")
public class ProofreaderController extends BaseController{
	
	@Inject
	private ProofreaderMapper verifierMapper;

	private String language;
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	
	@ResponseBody
	@RequestMapping("findIdandName")
	public List<ProofreaderFormMap> findIdandName(Model model){
		String domain=getPara("domain");
		String verifier=getPara("language");
		if("中英".equals(verifier)||"英中".equals(verifier)){
			language = "EN";
		}
		if("中日".equals(verifier)||"日中".equals(verifier)){
			language = "JP";
		}
		if("中韩".equals(verifier)||"中韩".equals(verifier)){
			language = "KOR";
		}
		if("中德".equals(verifier)||"德中".equals(verifier)){
			language = "GER";
		}
		if("中法".equals(verifier)||"法中".equals(verifier)){
			language = "FR";
		}

		
		List<ProofreaderFormMap> verifierFormMapList =new ArrayList<ProofreaderFormMap>();
		ProofreaderFormMap verifierFormMap = getFormMap(ProofreaderFormMap.class);
		verifierFormMap.put("language", language);
		verifierFormMap.put("domain", domain);
		verifierFormMapList=verifierMapper.findIdandName(verifierFormMap);
		
		System.out.println(verifierFormMapList.size());
		for(ProofreaderFormMap ve:verifierFormMapList){
			System.out.println(ve);
		}
		return verifierFormMapList;
	}
}
