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
import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.RankRuleFormMap;
import com.aiTrans.entity.RegulationFormMap;
import com.aiTrans.mapper.RankRuleMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

@Controller
@RequestMapping("/rankRule/")
public class RankRuleController extends BaseController{
    
	   private String id;
	   private String name;
	   @Inject
	   private RankRuleMapper rankRuleMapper;
	   
	   public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	   
	   //展示按钮资源菜单
	   @RequestMapping("list_rankRule")
		public String list_rankRule(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/rankRule/list_rank_rule";
		}
	   /**
	    * 二级列表请求
	    * @return
	    */
	   @RequestMapping("look_rankrule")
	   public String look_rankrule(Model model){
		   String id=getPara("id");
		   if (Common.isNotEmpty(id)) {
				model.addAttribute("rankrule",
						rankRuleMapper.findbyFrist("id", id, RankRuleFormMap.class));
			}
			return Common.BACKGROUND_PATH + "/system/rankRule/look_rankruleD";
	   }
	   /***
	    * 增加规则信息
	    * @param model
	    * @return
	    * @throws Exception
		*/
		@RequestMapping("addUI_1")
		public String addUI_1(Model model) throws Exception {
		   return Common.BACKGROUND_PATH + "/system/rankRule/addrankruleD";
	  }
		
		@ResponseBody
		@RequestMapping("addRankrule")
		@SystemLog(module = "规则管理", methods = "规则管理-新增规则")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly = false)
		// 需要事务操作必须加入此注解
		public String addRankrule(){
			try {
				RankRuleFormMap rankRuleFormMap = getFormMap(RankRuleFormMap.class);
				rankRuleMapper.addEntity(rankRuleFormMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "success";
		}
	   //查看排名规则详情
		@ResponseBody
		@RequestMapping("findBy_rankRule_page")
		public PageView findBy_rankRule_page(String pageNow, String pageSize,
				String column, String sort,Model model) throws Exception {
			RankRuleFormMap rankRuleFormMap = getFormMap(RankRuleFormMap.class);
			rankRuleFormMap = toFormMap(rankRuleFormMap, pageNow, pageSize,
					rankRuleFormMap.getStr("orderby"));
			rankRuleFormMap.put("column", column);
			rankRuleFormMap.put("sort", sort);
			rankRuleFormMap.put("rname", name);
			model.addAttribute("rankRule", rankRuleMapper.findFirstPage(rankRuleFormMap));
			pageView.setRecords(rankRuleMapper.findFirstPage(rankRuleFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
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
				model.addAttribute("rankrule",
						rankRuleMapper.findbyFrist("id", id, RankRuleFormMap.class));
			}
			return Common.BACKGROUND_PATH + "/system/rankRule/editrankruleD";
		}
		/**
		 * 修改规则
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("editRankrule")
		@Transactional(readOnly = false)
		public String editRankrule() throws Exception{
			RankRuleFormMap rankRuleFormMap = getFormMap(RankRuleFormMap.class);
			rankRuleFormMap.put("id", id);
			rankRuleMapper.editEntity(rankRuleFormMap);
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
				rankRuleMapper.deleteByAttribute("id", id, RankRuleFormMap.class);
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
			RankRuleFormMap rankRuleFormMap = findHasHMap(RankRuleFormMap.class);
			String exportData = rankRuleFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<RankRuleFormMap> lis = rankRuleMapper.findFirstPage(rankRuleFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
}
