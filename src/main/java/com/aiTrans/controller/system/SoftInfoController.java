package com.aiTrans.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.SoftInfoFormMap;
import com.aiTrans.mapper.SoftInfoMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * 软件使用情况
 * @author SS
 * @version 1.0v
 */
@Controller
@RequestMapping("/softInfo/")
public class SoftInfoController extends BaseController{
	
	@Inject
	public SoftInfoMapper softInfoMapper;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	//转到在线用户列表
	@RequestMapping("list_online_user")
	public String list_online_user(Model model){
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/softInfo/list_online_user";
	}
	
	/**
	 * 查看在线用户
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_online_user_by_page")
	public PageView find_online_user_by_page(String pageNow,
			String pageSize,String column,String sort) throws Exception{
		SoftInfoFormMap softInfoFormMap = getFormMap(SoftInfoFormMap.class);
		softInfoFormMap=toFormMap(softInfoFormMap, pageNow, pageSize,softInfoFormMap.getStr("orderby"));
		softInfoFormMap.put("column", column);
		softInfoFormMap.put("sort", sort);
		pageView.setRecords(softInfoMapper.findOnlineUserPage(softInfoFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	
	@RequestMapping("list_all_user")
	public String list_all_user(Model model){
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/softInfo/list_all_user";
	}
	
	@ResponseBody
	@RequestMapping("find_all_user_by_page")
	public PageView find_all_user_by_page(String pageNow,
			String pageSize,String column,String sort) throws Exception{
		SoftInfoFormMap softInfoFormMap = getFormMap(SoftInfoFormMap.class);
		softInfoFormMap=toFormMap(softInfoFormMap, pageNow, pageSize,softInfoFormMap.getStr("orderby"));
		softInfoFormMap.put("column", column);
		softInfoFormMap.put("sort", sort);
		pageView.setRecords(softInfoMapper.findAllUserPage(softInfoFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	@RequestMapping("export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = getPara("type");
		String fileName = "";
		SoftInfoFormMap softInfoFormMap = findHasHMap(SoftInfoFormMap.class);
		List<SoftInfoFormMap> lis = new ArrayList();
		if(type.equals("online")){
			fileName = "在线用户列表";
			lis = softInfoMapper.findOnlineUserPage(softInfoFormMap);
		}else if(type.equals("all")){
			fileName = "用户列表";
			lis = softInfoMapper.findAllUserPage(softInfoFormMap);
		}
		
		String exportData = softInfoFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}
}
