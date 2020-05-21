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
import com.aiTrans.entity.LibraryFormMap;
import com.aiTrans.entity.ResUserFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.entity.UserGroupsFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.LibraryMapper;
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
@RequestMapping("/library/")
public class LibraryController extends BaseController {
	@Inject
	private LibraryMapper libraryMapper;
	//2017年4月21日11:15:06
	private String id;
	private String language;
	
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
	
	
	@RequestMapping("list")
	public String list(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		id = getPara("id");
//		model.addAttribute("id",id);
		if("150".equals(id))
			language="EN";
		else if("151".equals(id))
			language="JP";
		else if("152".equals(id))
			language="KOR";
		else if("153".equals(id))
			language="GER";
		else if("154".equals(id))
			language="FR";
		return Common.BACKGROUND_PATH + "/system/library/list_library_first";
	}
	
	@ResponseBody
	@RequestMapping("find_library_first_by_page")
	public PageView find_library_first_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		LibraryFormMap libraryFormMap = getFormMap(LibraryFormMap.class);
		libraryFormMap=toFormMap(libraryFormMap, pageNow, pageSize,libraryFormMap.getStr("orderby"));
		libraryFormMap.put("column", column);
		libraryFormMap.put("sort", sort);
		
		//2017年4月21日11:20:49
		libraryFormMap.put("language", language);
		for(String key : libraryFormMap.keySet()) {
			System.out.println("key= "+ key + " and value= " + libraryFormMap.get(key));
		}
		pageView.setRecords(libraryMapper.findFirstPage(libraryFormMap));
		System.out.println("------------1----------"+pageView);
		System.out.println("------------2----------"+pageView.toString());
		return pageView;
		
		/*id = getPara("id");
		if("150".equals(id)){
			pageView.setRecords(libraryMapper.findENFirstPage(libraryFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}else if("151".equals(id)){
			pageView.setRecords(libraryMapper.findJPFirstPage(libraryFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}else if("152".equals(id)){
			pageView.setRecords(libraryMapper.findKORFirstPage(libraryFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}else if("153".equals(id)){
			pageView.setRecords(libraryMapper.findGERFirstPage(libraryFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}else if("154".equals(id)){
			pageView.setRecords(libraryMapper.findFRFirstPage(libraryFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}return null;*/
		
	}
	
	
	
	
	
	//2017年4月21日15:39:51
	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/library/add";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="术语管理",methods="术语管理-新增术语")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity(String txtGroupsSelect){
		try {
			LibraryFormMap libraryFormMap = getFormMap(LibraryFormMap.class);
			
			libraryMapper.addEntity(libraryFormMap);//新增后返回新增信息
		} catch (Exception e) {
			 throw new SystemException("添加账号异常");
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-删除术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			libraryMapper.deleteByAttribute("id", id, LibraryFormMap.class);
		}
		return "success";
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			model.addAttribute("library", libraryMapper.findbyFrist("id", id, LibraryFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/library/edit";
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-修改术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity() throws Exception {
		LibraryFormMap libraryFormMap = getFormMap(LibraryFormMap.class);
		libraryMapper.editEntity(libraryFormMap);
		
		return "success";
	}
	
	
	
}