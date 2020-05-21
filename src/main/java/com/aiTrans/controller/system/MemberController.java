package com.aiTrans.controller.system;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.MemberFormMap;
import com.aiTrans.entity.ResUserFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.entity.UserGroupsFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.MemberMapper;
import com.aiTrans.mapper.UserMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;
import com.aiTrans.util.PasswordHelper;

/**
 * 
 * @author numberONe 2014-11-19
 * @version 3.0v
 */
@Controller
@RequestMapping("/member/")
public class MemberController extends BaseController {
	@Inject
	private MemberMapper memberMapper;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	/**
	 * 展示的是该权限用户在此功能中的按钮资源
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_personal")
	public String list_personal(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		System.out.println("------------MemberController.java----------------");
		return Common.BACKGROUND_PATH + "/system/member/list_personal";
	}
	
	
	@RequestMapping("list_group")
	public String list_group(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/member/list_group";
	}
	
	//2017年4月17日15:26:01
	@RequestMapping("look_member")
	public String look_member(Model model) throws Exception {
		String id = getPara("memberId");
		if(Common.isNotEmpty(id)){
			model.addAttribute("member", memberMapper.findbyFrist("id", id, MemberFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/member/look_member";
	}
	
	/**
	 * 展示的是ly_member表中的数据
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_personal_by_page")
	public PageView find_personal_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		MemberFormMap memberFormMap = getFormMap(MemberFormMap.class);
		memberFormMap=toFormMap(memberFormMap, pageNow, pageSize,memberFormMap.getStr("orderby"));
		memberFormMap.put("column", column);
		memberFormMap.put("sort", sort);
        pageView.setRecords(memberMapper.findPersonalPage(memberFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        List<MemberFormMap> list = memberMapper.findPersonalPage(memberFormMap);
        for (MemberFormMap mem : list) {
			System.out.println("*******************"+mem);
		}
        return pageView;
	}
	
	
	@ResponseBody
	@RequestMapping("find_group_by_page")
	public PageView find_group_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		MemberFormMap memberFormMap = getFormMap(MemberFormMap.class);
		memberFormMap=toFormMap(memberFormMap, pageNow, pageSize,memberFormMap.getStr("orderby"));
		memberFormMap.put("column", column);
		memberFormMap.put("sort", sort);
		pageView.setRecords(memberMapper.findGroupPage(memberFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	
	/*@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			model.addAttribute("member", memberMapper.findbyFrist("id", id, MemberFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/member/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="会员管理-修改用户")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity(String txtGroupsSelect) throws Exception {
		MemberFormMap memberFormMap = getFormMap(MemberFormMap.class);
		memberFormMap.put("txtGroupsSelect", txtGroupsSelect);
		memberMapper.editEntity(memberFormMap);
		return "success";
	}*/
	
	
}