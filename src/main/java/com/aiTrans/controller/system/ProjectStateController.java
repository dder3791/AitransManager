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
import com.aiTrans.entity.AppealFormMap;
import com.aiTrans.entity.ProjectStateFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.ProjectStateMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

/**
 * 项目状态
 * @author Dell
 *
 */
@Controller
@RequestMapping("/projectState/")
public class ProjectStateController extends BaseController{
	@Inject
	private ProjectStateMapper projectStateMapper;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
			
	private String id;//对应表的主键
	/**
	 * 跳转展示协议信息页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_projectState")
	public String list_article(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/projectState/list_projectState";
	}
	
	
	/**
	 * 查看协议信息分页列表,应该从协议信息表中查
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
		@ResponseBody
		@RequestMapping("find_projectState_by_page")
		public PageView find_article_by_page( String pageNow,
				String pageSize,String column,String sort) throws Exception {
			ProjectStateFormMap projectStateFormMap = getFormMap(ProjectStateFormMap.class);
			projectStateFormMap=toFormMap(projectStateFormMap, pageNow, pageSize,projectStateFormMap.getStr("orderby"));
			projectStateFormMap.put("column", column);
			projectStateFormMap.put("sort", sort);
	        pageView.setRecords(projectStateMapper.findprojectStateByPage(projectStateFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
	        return pageView;
		}
		
		/**
		 * 跳转添加协议页面
		 * @return
		 */
		@RequestMapping("addUI")
		public String addUI(){
			return Common.BACKGROUND_PATH + "/system/projectState/add_projectState";
		}
		
		/**
		 * 添加协议实体
		 * @return
		 */
		@ResponseBody
		@RequestMapping("addEntity")
		@SystemLog(module="项目状态",methods="项目状态-新增状态")//凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		public String addEntity(){
			ProjectStateFormMap projectStateFormMap = getFormMap (ProjectStateFormMap.class);			
			try {
				projectStateMapper.addEntity(projectStateFormMap);
			} catch (Exception e) {
				 throw new SystemException("添加状态异常");
			}
			return "success";
		}
		
		
		/**
		 * 跳转编辑资讯页面
		 * @return
		 */
		@RequestMapping("editUI")
		public String editUI(Model model){
			id = getPara("id");
			model.addAttribute("projectState",projectStateMapper.findbyFrist("id", id, ProjectStateFormMap.class));
			return Common.BACKGROUND_PATH + "/system/projectState/edit_projectState";
		}
		
		/**
		 * 修改资讯信息实体
		 * @return
		 */
		@ResponseBody
		@RequestMapping("editEntity")
		@SystemLog(module="项目状态",methods="项目状态-编辑状态")//凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		public String editEntity(){
			ProjectStateFormMap projectStateFormMap = getFormMap (ProjectStateFormMap.class);
			try {
				projectStateFormMap.put("id", id);
				projectStateMapper.editEntity(projectStateFormMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "success";
		}
		
		
		 @RequestMapping("look_projectState")
		   public String look_projectState(Model model){
			 id=getPara("id");
			    ProjectStateFormMap projectStateForm=projectStateMapper.findbyFrist("id", id, ProjectStateFormMap.class);
			    model.addAttribute("projectStateForm",projectStateForm);
			    return Common.BACKGROUND_PATH + "/system/projectState/look_projectState";
		 }
		
		/**
		 * 删除协议实体数据
		 * @return
		 * @throws Exception
		 *//*
		@ResponseBody
		@RequestMapping("deleteEntity")
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		@SystemLog(module="协议信息",methods="协议信息-删除协议")//凡需要处理业务逻辑的.都需要记录操作日志
		public String deleteEntity() throws Exception {
			String[] ids = getParaValues("ids");
			for (String id : ids) {
				agreetmentMapper.deleteByAttribute("id", id, AgreetmentFormMap.class);
			}
			return "success";
		}
		
		*//**
		 * 跳转查看资讯信息详情页面
		 * @return
		 *//*
		@RequestMapping("look_agreetment")
		public String look_article(Model model){
			id = getPara("id");
			model.addAttribute("agreetment",agreetmentMapper.findbyFrist("id", id, AgreetmentFormMap.class));
			return Common.BACKGROUND_PATH + "/system/agreetment/look_agreetment";
		}
		
		@RequestMapping("/export")
		public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String fileName = "协议表";
			AgreetmentFormMap agreetmentFormMap = findHasHMap(AgreetmentFormMap.class);
			String exportData = agreetmentFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<AgreetmentFormMap> lis = agreetmentMapper.findAgreetmentByPage(agreetmentFormMap);
			
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	*/
}
