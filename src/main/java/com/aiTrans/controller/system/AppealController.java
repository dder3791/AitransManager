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
import com.aiTrans.entity.AppealFormMap;
import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.AppealMapper;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.TraProjectMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;
/***
 * 
 * @author vampire
 *申诉管理
 */
@Controller
@RequestMapping("/appeal/")
public class AppealController extends BaseController{
	
	  @Inject
	  private AppealMapper appealMapper;
	  @Inject
	  private TraProjectMapper transProjectMapper;
	  @Inject
      private TranslatorMapper transferMapper;
	  @Inject
	  private ProjectMapper projectMapper;
	  @Inject
	  private TranslatorMapper translatorMapper;
	  private String id;
	   
	  public String getPara(String key) {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	   //展示按钮资源菜单
	   @RequestMapping("list_appeal")
		public String list_appeal(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/appeal/list_appeal_first";
		}
	   /**
	    * 二级列表请求
	    * @return
	    */
	   @RequestMapping("look_appeal")
	   public String look_appeal(Model model){
		    id=getPara("id");
		    AppealFormMap appealFormList=appealMapper.findbyFrist("id", id, AppealFormMap.class);
		    int translatorId=(int) appealFormList.get("translatorId");
		    int projectId=(int) appealFormList.get("projectId");
		    ProjectFormMap project=new ProjectFormMap();
			project.put("translatorId", translatorId);
			TranslatorFormMap transName=translatorMapper.findById(project);
			ProjectFormMap projectFormMap=new ProjectFormMap();
			projectFormMap.put("id", projectId);
			String projectName=projectMapper.findProject(projectFormMap);
			appealFormList.put("translatorId", transName);
			appealFormList.put("projectId", projectName);
			if(Common.isNotEmpty(id)){
				model.addAttribute("appeal",appealFormList);
			}
			return Common.BACKGROUND_PATH + "/system/appeal/look_appeal";
	   }
	   // 查看译员申诉详情
		@ResponseBody
		@RequestMapping("findBy_appeal_page")
		public PageView findBy_appeal_page(String pageNow, String pageSize,
				String column, String sort,Model model) throws Exception {
			AppealFormMap appealFormMap = getFormMap(AppealFormMap.class);
			appealFormMap = toFormMap(appealFormMap, pageNow, pageSize,
					appealFormMap.getStr("orderby"));
			appealFormMap.put("column", column);
			appealFormMap.put("sort", sort);
			pageView.setRecords(appealMapper.findFirstPage(appealFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}
		/**
		 * 修改申诉信息
		 * @param model
		 * @return
		 */
		@RequestMapping("editUI_1")
		public String  editUI_1(Model model){
	    id=getPara("id");
	    AppealFormMap appealFormList=appealMapper.findbyFrist("id", id, AppealFormMap.class);
	    int translatorId=(int) appealFormList.get("translatorId");
	    int projectId=(int) appealFormList.get("projectId");
	    ProjectFormMap project=new ProjectFormMap();
		project.put("translatorId", translatorId);
		TranslatorFormMap transName=translatorMapper.findById(project);
		ProjectFormMap projectFormMap=new ProjectFormMap();
		projectFormMap.put("id", projectId);
		String projectName=projectMapper.findProject(projectFormMap);
		appealFormList.put("translatorId", transName);
		appealFormList.put("projectId", projectName);
		if(Common.isNotEmpty(id)){
			model.addAttribute("appeal",appealFormList);
		}
		return Common.BACKGROUND_PATH + "/system/appeal/editAppeal";
		}
	    
		/**
		 * 修改申诉信息
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("editAppeal")
		@Transactional(readOnly = false)
		// 需要事务操作必须加入此注解
		@SystemLog(module = "评价管理", methods = "评价管理-修改申诉信息")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		public String editAppeal() throws Exception {
			AppealFormMap appealFormMap = getFormMap(AppealFormMap.class);
			String nickname=(String) appealFormMap.get("translatorId");
		     String translatorId=translatorMapper.findTranslatorId(nickname);
		     String name=(String)appealFormMap.get("projectId");
		     ProjectFormMap projectFormMap=new ProjectFormMap();
		     projectFormMap.put("name", name);
		     String projectId=projectMapper.findProjectName(name);
			String id=getPara("id");
			appealFormMap.put("id", id);
			appealFormMap.put("translatorId", translatorId);
		    appealFormMap.put("projectId", projectId);
			appealMapper.editEntity(appealFormMap);
			return "success";
		}
		/**
		 * 申诉信息导出Excel
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("/export")
		public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String fileName = "申诉列表";
			AppealFormMap appealFormMap = findHasHMap(AppealFormMap.class);
			String exportData = appealFormMap.getStr("exportData");// 列表头的json字符串
			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
			List<AppealFormMap> lis = appealMapper.findFirstPage(appealFormMap);
			for (AppealFormMap appealFormMaps : lis) {
				int state=(int)appealFormMaps.get("state");
				if(state == 0){
					appealFormMaps.put("state", "未申诉");
				}else if(state == 1){
					appealFormMaps.put("state", "已提交申诉");
				}else if(state == 2){
					appealFormMaps.put("state", "申诉已完成");
				}
			}
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		/***
		 * 删除申诉信息
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("deleteEntity")
		@Transactional(readOnly = false)
		// 需要事务操作必须加入此注解
		@SystemLog(module = "评价管理", methods = "评价管理-删除申诉信息")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		public String deleteEntity() throws Exception {
			String[] ids = getParaValues("ids");
			for (String id : ids) {
				appealMapper.deleteByAttribute("id", id, AppealFormMap.class);
			}
			return "success";
		}
		 
}
