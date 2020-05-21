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
import com.aiTrans.entity.IntegralFormMap;
import com.aiTrans.entity.RankRuleFormMap;
import com.aiTrans.entity.RegulationFormMap;
import com.aiTrans.mapper.RegulationMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;
/***
 * 积分规则
 * @author vampire
 *
 */
@Controller
@RequestMapping("/regulation/")
public class RegulationController extends BaseController{
	   @Inject
	   private RegulationMapper regulationMapper;
	   
	   private String id;
	   private String name;
	   
	   public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	 //展示按钮资源菜单
	   @RequestMapping("list_regulation")
		public String list_regulation(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/regulation/list_regulation_first";
		}
	   //查看积分规则详情
	   @ResponseBody
	   @RequestMapping("findBy_regulation_page")
	   public PageView findBy_regulation_page(String pageNow, String pageSize,
			   String column, String sort) throws Exception {
		   RegulationFormMap regulationFormMap = getFormMap(RegulationFormMap.class);
		   regulationFormMap = toFormMap(regulationFormMap, pageNow, pageSize,
				   regulationFormMap.getStr("orderby"));
		   regulationFormMap.put("column", column);
		   regulationFormMap.put("sort", sort);
		   regulationFormMap.put("rname", name);
		   pageView.setRecords(regulationMapper.findFirstPage(regulationFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		   return pageView;
	   }
	   /**
	    * 二级列表请求
	    * @return
	    */
	   @RequestMapping("look_regulation")
	   public String look_regulation(Model model){
		   String id=getPara("id");
		   if (Common.isNotEmpty(id)) {
				model.addAttribute("regulation",
						regulationMapper.findbyFrist("id", id, RegulationFormMap.class));
			}
			return Common.BACKGROUND_PATH + "/system/regulation/look_regulationD";
	   }
	   /***
		 * 验证商品名称不重复
		 * @param name
		 * @return
		 */
		@RequestMapping("isExist")
		@ResponseBody
		public boolean isExist(String name) {
			boolean flag=false;
			String type=getPara("type");
			RegulationFormMap account = regulationMapper.findbyFrist("rname", name, RegulationFormMap.class);
			if("add".equals(type)){
				if (account == null) {
					return true;
				} else {
					return false;
				}
			}
			if("edit".equals(type)){
				String id=getPara("id");
				RegulationFormMap rname = regulationMapper.findbyFrist("id",id, RegulationFormMap.class);
			   if (account == null || name.equals(rname.get("name"))) {
					return true;
				} else {
					return false;
				}
			}
			return flag;
		}
	   /***
		 * 修改规则信息
		 * @param model
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("editUI_1")
		public String editUI_1(Model model,HttpServletRequest request) throws Exception {
			id=getPara("id");
			if (Common.isNotEmpty(id)) {
				model.addAttribute("regulation",
						regulationMapper.findbyFrist("id", id, RegulationFormMap.class));
			}
			return Common.BACKGROUND_PATH + "/system/regulation/editregulationD";
		}
		/**
		 * 修改规则
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("editRegulation")
		@Transactional(readOnly = false)
		public String editRegulation() throws Exception{
			RegulationFormMap regulationFormMap = getFormMap(RegulationFormMap.class);
			regulationFormMap.put("id", id);
			regulationMapper.editEntity(regulationFormMap);
			return "success";
		}
			
	    /***
	    * 增加规则信息
	    * @param model
	    * @return
	    * @throws Exception
		*/
		@RequestMapping("addUI_1")
		public String addUI_1(Model model) throws Exception {
		   return Common.BACKGROUND_PATH + "/system/regulation/addregulationD";
	  }
	@ResponseBody
	@RequestMapping("addRegulation")
	@SystemLog(module = "规则管理", methods = "规则管理-新增规则")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	public String addRegulation(){
		try {
			RegulationFormMap regulationFormMap = getFormMap(RegulationFormMap.class);
			regulationMapper.addEntity(regulationFormMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
   
   
   
   
   /***
	 * 删除规则信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "规则管理", methods = "规则管理-删除规则信息")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			regulationMapper.deleteByAttribute("id", id, RegulationFormMap.class);
		}
		return "success";
	}	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "规则列表";
		RegulationFormMap regulationFormMap = findHasHMap(RegulationFormMap.class);
		String exportData = regulationFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

		List<RegulationFormMap> lis = regulationMapper.findFirstPage(regulationFormMap);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}
}
