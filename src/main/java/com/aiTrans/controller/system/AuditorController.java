package com.aiTrans.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AuditorFormMap;
import com.aiTrans.mapper.AuditorMapper;
import com.aiTrans.mapper.ProofreaderMapper;

/**
 * 审核员
 * @author SS
 * @version 4.0v
 */
@Controller
@RequestMapping("/auditor/")
public class AuditorController extends BaseController{
	
	@Inject
	private ProofreaderMapper verifierMapper;
	@Inject
	private AuditorMapper auditorMapper;
	
	private String language;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	
	@ResponseBody
	@RequestMapping("findIdandName")
	public List<AuditorFormMap> findIdandName(Model model){
		
		String domain=getPara("domain");
		String auditory=getPara("language");
		if("中英".equals(auditory)||"英中".equals(auditory)){
			language = "EN";
		}
		if("中日".equals(auditory)||"日中".equals(auditory)){
			language = "JP";
		}
		if("中韩".equals(auditory)||"中韩".equals(auditory)){
			language = "KOR";
		}
		if("中德".equals(auditory)||"德中".equals(auditory)){
			language = "GER";
		}
		if("中法".equals(auditory)||"法中".equals(auditory)){
			language = "FR";
		}

			List<AuditorFormMap> auditorFormMapList =new ArrayList<AuditorFormMap>();
			AuditorFormMap auditorFormMap = getFormMap(AuditorFormMap.class);
			auditorFormMap.put("language", language);
			auditorFormMap.put("domain", domain);
			auditorFormMapList=auditorMapper.findIdandName(auditorFormMap);

		return auditorFormMapList;
	}
}
