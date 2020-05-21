package com.aiTrans.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AppealClientFormMap;
import com.aiTrans.entity.ArticleFormMap;
import com.aiTrans.entity.ClientCustomerNeedFormMap;
import com.aiTrans.entity.ClientUserFormMap;
import com.aiTrans.entity.EvaluateClientFormMap;
import com.aiTrans.entity.FieldFormMap;
import com.aiTrans.entity.OrderFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.FieldMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * 领域管理
 * @author Dell
 * 时间 20180327 twl
 *
 */
@Controller
@RequestMapping("/domain/")
public class FieldController extends BaseController{

	    @Inject
	    private FieldMapper fieldMapper;
	  
	    public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	  
		/**
		 * 跳转平台平台申诉列表
		 * @param model
		 * @return
		 * @throws Exception
		 */
	   @RequestMapping("list_field_UI")
		public String list_field(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/field/list_field";
		}
   
   
	   /**
	    * 分页展示申诉信息 
	    * @param pageNow
	    * @param pageSize
	    * @param column
	    * @param sort
	    * @param model
	    * @return
	    * @throws Exception
	    */
	   @ResponseBody
	   @RequestMapping("findBy_field_page")
	   public PageView findBy_field_page(String pageNow, String pageSize,String column, String sort,Model model) throws Exception  {
		   
		   FieldFormMap fieldFormMap = getFormMap(FieldFormMap.class);
		   fieldFormMap=toFormMap(fieldFormMap, pageNow, pageSize,fieldFormMap.getStr("orderby"));
		   fieldFormMap.put("column", column);
		   fieldFormMap.put("sort", sort);
		   pageView.setRecords(fieldMapper.findFieldPage(fieldFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
		   }
   
  
	   @RequestMapping("add_field_UI")
	   public String add_field_UI(){
		   return Common.BACKGROUND_PATH + "/system/field/add_field";
	   }
	   
	   
	    @ResponseBody
		@RequestMapping("addEntity")
		@SystemLog(module="领域管理",methods="新增领域")//凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		public String addEntity(HttpServletRequest request){
			FieldFormMap fieldFormMap = getFormMap(FieldFormMap.class);
			try {
				fieldMapper.addEntity(fieldFormMap);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
			
		}
	    
	    
	   @RequestMapping("edit_field_UI")
	   public String edit_field_UI(Model model){
		   String id = getPara("id");
		   model.addAttribute("fieldFormMap",fieldMapper.findbyFrist("id", id, FieldFormMap.class));
		   return Common.BACKGROUND_PATH + "/system/field/edit_field";
	   }
	   
	   
	   @ResponseBody
	   @RequestMapping("editEntity")
	   @SystemLog(module="领域管理",methods="编辑领域")//凡需要处理业务逻辑的.都需要记录操作日志
	   @Transactional(readOnly=false)//需要事务操作必须加入此注解
	   public String editEntity(HttpServletRequest request){
		   	String id = getPara("id");
			FieldFormMap fieldFormMap = getFormMap(FieldFormMap.class);
			fieldFormMap.put("id", id);
			try {
				fieldMapper.editEntity(fieldFormMap);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
			
		}
	   
	   
	   @RequestMapping("/export")
		public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String fileName = "项目领域";
			FieldFormMap fieldFormMap = findHasHMap(FieldFormMap.class);
			String exportData = fieldFormMap.getStr("exportData");// 列表头的json字符串
			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
			List<FieldFormMap> lis = fieldMapper.findFieldPage(fieldFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	   
	   
   }
