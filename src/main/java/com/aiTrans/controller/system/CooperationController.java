package com.aiTrans.controller.system;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.CooperationFormMap;
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.DomainFormMap;
import com.aiTrans.entity.MajorFormMap;
import com.aiTrans.entity.MemberFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.ResUserFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.entity.UserGroupsFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.CustomerMapper;
import com.aiTrans.mapper.DomainMapper;
import com.aiTrans.mapper.MajorMapper;
import com.aiTrans.mapper.ProcedureTypeMapper;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.ProjectTypeMapper;
import com.aiTrans.mapper.RewardMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.mapper.UserMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;
import com.aiTrans.util.PasswordHelper;

/**
 * Cooperative协同作业
 * @author SS
 * @version 4.0v
 */
@Controller
@RequestMapping("/cooperation/")
public class CooperationController extends BaseController {
	
	@Inject
	private ProjectMapper projectMapper;
	
	@Inject
	private TranslatorMapper transferMapper;
	
	@Inject
	private ProcedureTypeMapper processesTypeMapper;
	@Inject
	private ProjectTypeMapper projectTypeMapper;
	@Inject
	private CustomerMapper customerMapper;
	@Inject
	private MajorMapper majorMapper;
	@Inject
	private DomainMapper domainMapper;
	@Inject
	private RewardMapper rewardMapper;
	
	
	private String type;
	
	private String projectId;
	
	private String transferId;
	
	private String customerId;
	
	//转到展示协同项目页面
	@RequestMapping("list_project")
	public String list_project(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/cooperation/list_project";
	}
	
	//转到展示协同译员页面
	@RequestMapping("list_participants")
	public String list_participants(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/cooperation/list_participants";
	}
	
	//转到查看项目详情页面
	@RequestMapping("look_project")
	public String look_project(Model model) throws Exception {
		projectId = getPara("projectId");
		if(Common.isNotEmpty(projectId)){
			RewardFormMap rewardFormMap = rewardMapper.findbyFrist("id", projectId, RewardFormMap.class);
			ProjectFormMap projectFormMap = projectMapper.findbyFrist("id", projectId, ProjectFormMap.class);
			String customerId = projectFormMap.get("customerId")+"";
			CustomerFormMap customerFormMap = customerMapper.findbyFrist("id", customerId, CustomerFormMap.class);
			model.addAttribute("customer",customerFormMap);
			model.addAttribute("project", projectFormMap);
			model.addAttribute("reward",rewardFormMap);
		}
		return Common.BACKGROUND_PATH + "/system/cooperation/look_project";
	}
	
	//转到查看译员详情页面
	@RequestMapping("look_transfer")
	public String look_transfer(Model model) throws Exception {
		transferId = getPara("transferId");
		if(Common.isNotEmpty(transferId)){
			model.addAttribute("transfer", transferMapper.findbyFrist("id", transferId, TranslatorFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/transfer/look_transfer";
	}
	
	
	
	/**查看协同项目分页列表
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return PageView对象
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_project_by_page")
	public PageView find_project_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column", column);
		projectFormMap.put("sort", sort);
        pageView.setRecords(projectMapper.findCustomerProjectPage(projectFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	/**查看参与人员分页列表
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return PageView对象
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_cooperative_by_page")
	public PageView find_cooperative_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TranslatorFormMap transferFormMap = getFormMap(TranslatorFormMap.class);
		transferFormMap=toFormMap(transferFormMap, pageNow, pageSize,transferFormMap.getStr("orderby"));
		transferFormMap.put("column", column);
		transferFormMap.put("sort", sort);
		pageView.setRecords(transferMapper.findParticipantsPage(transferFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	
	
	
	
	
	
	
	//转到添加协同项目页面
	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		model.addAttribute("projectType",projectTypeMapper.findProjectType());
		model.addAttribute("processesType",processesTypeMapper.findType());
		model.addAttribute("customer",customerMapper.findIdAndName());
		
		model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
		model.addAttribute("domain",domainMapper.findByWhere(getFormMap(DomainFormMap.class)));
		
		type=getPara("type");
		if("project".equals(type)){
			return Common.BACKGROUND_PATH + "/system/cooperation/add";
		}else
		return Common.BACKGROUND_PATH + "/system/cooperation/add_transfer";
	}
	
	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		
		type=getPara("type");
		if("project".equals(type)){
			projectId = id;
			ProjectFormMap project = projectMapper.findbyFrist("id", projectId, ProjectFormMap.class);
			customerId = project.getInt("customerId") + "";
			CustomerFormMap customer = customerMapper.findbyFrist("id", customerId, CustomerFormMap.class);
			model.addAttribute("project", project);
			model.addAttribute("customer", customer);
			
			return Common.BACKGROUND_PATH + "/system/cooperation/edit_project";
		}else {
			transferId = id;
			TranslatorFormMap transfer = transferMapper.findbyFrist("id", transferId, TranslatorFormMap.class);
			model.addAttribute("transfer", transfer);
			model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
			model.addAttribute("domain",domainMapper.findByWhere(getFormMap(DomainFormMap.class)));
		/*if(Common.isNotEmpty(id)){
			model.addAttribute("cooperation", projectMapper.findbyFrist("id", id, ProjectFormMap.class));
		}*/
		return Common.BACKGROUND_PATH + "/system/cooperation/edit_transfer";
		}
	}

	
	
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="协同项目管理",methods="新增项目管理")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity() throws Exception {
		System.out.println("--------------添加协同项目------------------");
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		/*if("internal".equals(type)){
			cooperationFormMap.set("location", 0);
		}*/
		
		projectMapper.addEntity(projectFormMap);
		return "success";
	}
	
	
	@ResponseBody
	@RequestMapping("editEntity")
	@SystemLog(module="协同项目管理",methods="编辑协同项目")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String editEntity() throws Exception {
		if(type.equals("project")){
			ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
			projectFormMap.put("id", projectId);
//			CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
//			customerFormMap.put("id", customerId);
			projectMapper.editEntity(projectFormMap);
//			customerMapper.editEntity(customerFormMap);
			return "success";
		}else{
			TranslatorFormMap transferFormMap = getFormMap(TranslatorFormMap.class);
			transferMapper.editEntity(transferFormMap);
			return "success";
		}
	}
	
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="协同项目管理",methods="删除")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			projectMapper.deleteByAttribute("id", id, ProjectFormMap.class);
		}
		return "success";
	}
	
	/**
	 * 检验项目是否已添加
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExist")
	public boolean isExist(String name){
		ProjectFormMap project = projectMapper.findbyFrist("name", name, ProjectFormMap.class);
			if(project == null){
				return true;
			}else
				return false;
	}
	/**
	 * 检验项目创建时间是否晚于完成时间
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkTime")
	public boolean checkTime(String createTime,String completeTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date begin,end;
		try {
			begin = sdf.parse(createTime);
			end = sdf.parse(completeTime);
			if(begin.before(end)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	
}