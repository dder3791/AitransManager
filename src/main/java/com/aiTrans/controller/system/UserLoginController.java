package com.aiTrans.controller.system;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.UserLoginFormMap;
import com.aiTrans.mapper.BlackMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.mapper.UserLoginMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

/**
 * 
 * @author numberONe 2014-11-19
 * @version 3.0v
 */
@Controller
@RequestMapping("/userlogin/")
public class UserLoginController extends BaseController {
	@Inject
	private UserLoginMapper userLoginMapper;
	
	@Inject
	private BlackMapper blackMapper;
	
	@Inject
	private TranslatorMapper transferMapper;

	@RequestMapping("listUI")
	public String listUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/userlogin/list";
	}
	
	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(String pageNow,
			String pageSize) throws Exception {
		UserLoginFormMap userLoginFormMap = getFormMap(UserLoginFormMap.class);
		userLoginFormMap=toFormMap(userLoginFormMap, pageNow, pageSize,userLoginFormMap.getStr("orderby"));
        pageView.setRecords(userLoginMapper.findByPage(userLoginFormMap));
		return pageView;
	}
	
	@RequestMapping("list_black")
	public String list_black(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/userlogin/list_black";
	}

	
	@ResponseBody
	@RequestMapping("find_black_by_page")
	public PageView find_black_by_page(String pageNow,
			String pageSize) throws Exception {
		TranslatorFormMap transferFormMap = getFormMap(TranslatorFormMap.class);
		transferFormMap = toFormMap(transferFormMap, pageNow, pageSize,transferFormMap.getStr("orderby"));
		pageView.setRecords(transferMapper.findBlack(transferFormMap));
		/*BlackFormMap blackFormMap = getFormMap(BlackFormMap.class);
		blackFormMap=toFormMap(blackFormMap, pageNow, pageSize,blackFormMap.getStr("orderby"));
		pageView.setRecords(blackMapper.findByPage(blackFormMap));*/
		return pageView;
	}

}