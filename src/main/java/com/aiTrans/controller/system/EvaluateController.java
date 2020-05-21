package com.aiTrans.controller.system;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.TraProjectFormMap;
import com.aiTrans.mapper.EvaluateMapper;
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
 *评价管理
 */
@Controller
@RequestMapping("/evaluate/")
public class EvaluateController extends BaseController{
	
	   @Inject
	   private EvaluateMapper evaluateMapper;
	   @Inject
	   private TraProjectMapper transProjectMapper;
	   @Inject
		private TranslatorMapper transferMapper;
	   @Inject
		private ProjectMapper projectMapper;
	   
	   public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	   //展示按钮资源菜单
	   @RequestMapping("list_evaluate")
		public String list_evaluate(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/evaluate/list_evaluate_first";
		}
	    
	   /**
	    * 二级列表请求
	    * @return
	    */
	   @RequestMapping("look_evaluate")
	   public String look_evaluate(Model model){
		   String id=getPara("id");
			model.addAttribute("translators", transferMapper.findTranslator());
			if(Common.isNotEmpty(id)){
				EvaluateFormMap evaluateFormMap = evaluateMapper.findbyFrist("id", id, EvaluateFormMap.class);
				int projectId =(int) evaluateFormMap.get("projectId");
				String oriLanguage=(String) evaluateFormMap.get("oriLanguage");
				ProjectFormMap projectFormMap = new ProjectFormMap();
				evaluateFormMap.put("oriLanguage", oriLanguage);
				projectFormMap.put("id", projectId);
				String projectName=projectMapper.findProject(projectFormMap);
				model.addAttribute("evaluate",evaluateFormMap );
				model.addAttribute("projectName",projectName );
				List<ProofreaderFormMap> ProofreaderList=evaluateMapper.findProofreadLanguage(evaluateFormMap);
			    model.addAttribute("ProofreaderList", ProofreaderList);
			}
			return Common.BACKGROUND_PATH + "/system/evaluate/look_evaluate";
	   }
	  
	   /**
	     * 增加评价信息
	     * @param model
	     * @return
	     * @throws Exception
	     */
		@RequestMapping("addUI_1")
		public String addUI_1(Model model) throws Exception {
			model.addAttribute("translators", transferMapper.findTranslator());
			return Common.BACKGROUND_PATH + "/system/evaluate/addEvaluateD";
		}
		
		@ResponseBody
		@RequestMapping("addEvaluate")
		@SystemLog(module = "添加评价", methods = "评价管理-新增评价")
		public String addEvaluate(Model model){
			try {
			EvaluateFormMap  evaluateFormMap=getFormMap(EvaluateFormMap.class);
			String nickname=getPara("nickname");
			String translatorId=transferMapper.findTranslatorId(nickname);
			String name=getPara("name");
			evaluateFormMap.put("translatorId", translatorId);
			evaluateFormMap.put("projectId", name);
			evaluateMapper.addEntity(evaluateFormMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return "success";
		}
		
		//查找译员对应的项目
	    @ResponseBody
		@RequestMapping("findtran_project")
		public List<TraProjectFormMap> findtran_project(){
			String nickname=getPara("nickname");
			String translatorId=transferMapper.findTranslatorId(nickname);
			List<TraProjectFormMap> projectList=transProjectMapper.findTran_project(translatorId);
			return projectList;
		}
	    
	    //根据项目查询对应公司案号
	    @ResponseBody
	    @RequestMapping("findReference")
	    public String findReference(){
	    	String projectName=getPara("projectName");
	    	String reference=projectMapper.findReference(projectName);
	    	return reference;
	    }
	    
	    //根据语言查找校对员
	    @ResponseBody
	    @RequestMapping("findProofreadlanguage")
	    public List<ProofreaderFormMap> findProofreadlanguage(){
	    	String oriLanguage=getPara("oriLanguage");
	    	EvaluateFormMap evaluateFormMap=new EvaluateFormMap();
	    	evaluateFormMap.put("oriLanguage", oriLanguage);
	    	List<ProofreaderFormMap> ProofreaderList=evaluateMapper.findProofreadLanguage(evaluateFormMap);
	    	return ProofreaderList;
	    }
	    
	    
	    
	    
	  // 查看译员评价详情
		@ResponseBody
		@RequestMapping("findBy_evaluate_page")
		public PageView findBy_evaluate_page(String pageNow, String pageSize,
				String column, String sort,Model model) throws Exception {
			EvaluateFormMap evaluateFormMap = getFormMap(EvaluateFormMap.class);
			evaluateFormMap = toFormMap(evaluateFormMap, pageNow, pageSize,
			evaluateFormMap.getStr("orderby"));
			evaluateFormMap.put("column", column);
			evaluateFormMap.put("sort", sort);
			
			pageView.setRecords(evaluateMapper.findFirstPage(evaluateFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		}
		
		//验证时间（完成时间>交案时间）
		@RequestMapping("completeTimeExist")
		@ResponseBody
		public boolean completeTimeExist(String completeTime,String presentTime ) {		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/*String presentTime=getPara("presentTime");*/
			try {
				Date complete = sdf.parse(completeTime);
				Date present = sdf.parse(presentTime);
							
				if (complete.after(present)) {
					return true;
				} else {
					return false;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		
		
		
		/**
	     * 修改评价信息
	     * @param model
	     * @return
	     * @throws Exception
	     */
		@RequestMapping("editUI_1")
		public String editUI_1(Model model) throws Exception {
			String id=getPara("id");
			model.addAttribute("translators", transferMapper.findTranslator());
			if(Common.isNotEmpty(id)){
				EvaluateFormMap evaluateFormMap = evaluateMapper.findbyFrist("id", id, EvaluateFormMap.class);
				int projectId =(int) evaluateFormMap.get("projectId");
				String oriLanguage=(String) evaluateFormMap.get("oriLanguage");
				ProjectFormMap projectFormMap = new ProjectFormMap();
				evaluateFormMap.put("oriLanguage", oriLanguage);
				projectFormMap.put("id", projectId);
				String projectName=projectMapper.findProject(projectFormMap);
				model.addAttribute("evaluate",evaluateFormMap );
				model.addAttribute("projectName",projectName );
				List<ProofreaderFormMap> ProofreaderList=evaluateMapper.findProofreadLanguage(evaluateFormMap);
			    model.addAttribute("ProofreaderList", ProofreaderList);
			}
			return Common.BACKGROUND_PATH + "/system/evaluate/editEvaluateD";
		}
      		
		@ResponseBody
		@RequestMapping("editEvaluate")
		@Transactional(readOnly = false)
		// 需要事务操作必须加入此注解
		@SystemLog(module = "评价管理", methods = "评价管理-修改评价")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		public String editEvaluate() throws Exception {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			EvaluateFormMap evaluateFormMap = getFormMap(EvaluateFormMap.class);
			String id=getPara("id");
			evaluateFormMap.put("id", id);
			evaluateMapper.editEntity(evaluateFormMap);
			return "success";
		}
		
		/***
		 * 删除评价信息
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("deleteEntity")
		@Transactional(readOnly = false)
		// 需要事务操作必须加入此注解
		@SystemLog(module = "评价管理", methods = "评价管理-删除评价信息")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		public String deleteEntity() throws Exception {
			String[] ids = getParaValues("ids");
			for (String id : ids) {
				evaluateMapper.deleteByAttribute("id", id, EvaluateFormMap.class);
			}
			return "success";
		}	
		/**
		 * 评价导出Excel
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("/export")
		public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String fileName = "评价列表";
			EvaluateFormMap evaluateFormMap = findHasHMap(EvaluateFormMap.class);
			String exportData = evaluateFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<EvaluateFormMap> lis = evaluateMapper.findFirstPage(evaluateFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
		@RequestMapping("appeal")
		public String appeal(){
			String id=getPara("id");
			return Common.BACKGROUND_PATH + "/system/evaluate/appealD";
		}
}
