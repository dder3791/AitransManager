package com.aiTrans.controller.system;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.CustomerMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * CustomerController客户管理
 * @author numberONe 2014-11-19
 * @version 3.0v
 */
@Controller
@RequestMapping("/customer/")
public class CustomerController extends BaseController {
	@Inject
	private CustomerMapper customerMapper;
	
	//2017年4月19日10:58:04
	private String type;
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
	@RequestMapping("list_internal")
	public String list_internal(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		model.addAttribute("type","internal");
		return Common.BACKGROUND_PATH + "/system/customer/list_internal";
	}
	
	
	@RequestMapping("list_foreign")
	public String list_foreign(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		model.addAttribute("type","foreign");
		return Common.BACKGROUND_PATH + "/system/customer/list_foreign";
	}
	
	@RequestMapping("list_large")
	public String list_large(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		model.addAttribute("type","large");
		return Common.BACKGROUND_PATH + "/system/customer/list_large";
	}
	
	
	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		String type = getPara("type");
		model.addAttribute("type",type);
		return Common.BACKGROUND_PATH + "/system/customer/add";
	}
	
	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			CustomerFormMap customerFormMap = customerMapper.findbyFrist("id", id, CustomerFormMap.class);
			model.addAttribute("customer",customerFormMap );
		}
		return Common.BACKGROUND_PATH + "/system/customer/edit";
	}

	
	
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="客户管理",methods="新增客户")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity() throws Exception {
		CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
		/*if("internal".equals(type)){
			customerFormMap.set("location", 0);
		}*/
		//获取当前时间
		String addDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		customerFormMap.put("addDate", addDate);
//		customerFormMap.put("updateQuotedPriceDate", addDate);//修改报价时间
		String updateQuotedPriceDate = addDate;//修改报价时间
		customerFormMap.put("historicalQuotedPrice", updateQuotedPriceDate+"====="+customerFormMap.getStr("quotedPrice").trim()+"(元/每千字)C");//历史报价
		try{
			customerMapper.addEntity(customerFormMap);
		}catch (Exception e) {
			 throw new SystemException("添加客户异常");
		}
		return "success";
	}
	
	
	@ResponseBody
	@RequestMapping("editEntity")
	@SystemLog(module="客户管理",methods="编辑客户")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String editEntity() throws Exception {
		CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
		
		String id = customerFormMap.getStr("id");
		
		//当前时间
		String addDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//修改后的报价
		String updateQuotedPrice = customerFormMap.getStr("quotedPrice")+"(元/每千字)";
		
		//修改前的客户
		CustomerFormMap unupdatedCustomer = customerMapper.findbyFrist("id", id, CustomerFormMap.class);
		//历史报价
		String historicalQuotedPrice = unupdatedCustomer.getStr("historicalQuotedPrice");
		//截取上一次价格
		String[] hqds = historicalQuotedPrice.split("C");
		String lastHQP = hqds[hqds.length-1];
		int index = lastHQP.lastIndexOf("=");
		lastHQP = lastHQP.substring(index+1);
		//添加历史报价
		if(!lastHQP.equals(updateQuotedPrice))
			customerFormMap.put("historicalQuotedPrice",historicalQuotedPrice+addDate+"====="+updateQuotedPrice+"C");
		
		customerMapper.editEntity(customerFormMap);
		return "success";
	}
	
	
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="客户管理管理",methods="删除客户")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			customerMapper.deleteByAttribute("id", id, CustomerFormMap.class);
		}
		return "success";
	}
	
	@RequestMapping("look_customer")
	public String look_transfer(Model model) throws Exception {
		String id = getPara("customerId");
		if(Common.isNotEmpty(id)){
			CustomerFormMap customerFormMap = customerMapper.findbyFrist("id", id, CustomerFormMap.class);
			String[] prices = customerFormMap.getStr("historicalQuotedPrice").split("C");
			List price = Arrays.asList(prices);
			model.addAttribute("price",price);
			model.addAttribute("customer", customerFormMap);
		}
		return Common.BACKGROUND_PATH + "/system/customer/look_customer";
	}
	
	
	/**
	 * 展示的是ly_entry表中的数据
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_internal_by_page")
	public PageView find_internal_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
		customerFormMap=toFormMap(customerFormMap, pageNow, pageSize,customerFormMap.getStr("orderby"));
		customerFormMap.put("column", column);
		customerFormMap.put("sort", sort);
        pageView.setRecords(customerMapper.findInternalPage(customerFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	
	@ResponseBody
	@RequestMapping("find_foreign_by_page")
	public PageView find_foreign_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
		customerFormMap=toFormMap(customerFormMap, pageNow, pageSize,customerFormMap.getStr("orderby"));
		customerFormMap.put("column", column);
		customerFormMap.put("sort", sort);
		pageView.setRecords(customerMapper.findForeignPage(customerFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	@ResponseBody
	@RequestMapping("find_large_by_page")
	public PageView find_large_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
		customerFormMap=toFormMap(customerFormMap, pageNow, pageSize,customerFormMap.getStr("orderby"));
		customerFormMap.put("column", column);
		customerFormMap.put("sort", sort);
		pageView.setRecords(customerMapper.findLargePage(customerFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	/**
	 * 检验公司是否已添加
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExist")
	public boolean isExist(String nameZH,String nameEN,String shortName){
		String type = getPara("type");
		if(type.equals("ZH")){
			//数据库中公司中文名为nameZH的客户
			CustomerFormMap customer = customerMapper.findbyFrist("nameZH", nameZH, CustomerFormMap.class);
				if(customer == null){
					return true;
				}
		}else if(type.equals("EN")){
			//数据库中公司英文名为nameEN的客户
			CustomerFormMap customer = customerMapper.findbyFrist("nameEN", nameEN, CustomerFormMap.class);
				if(customer == null){
					return true;
				}
		}else if(type.equals("shortName")){
			//数据库中公司简称为shortName的客户
			CustomerFormMap customer = customerMapper.findbyFrist("shortName", shortName, CustomerFormMap.class);
				if(customer == null){
					return true;
				}
		}
		return false;
	}
	
	/**
	 * 1.检验公司名是否是当前客户公司
	 * 2.检验公司名是否已添加
	 * @param name
	 * @param customerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isRepeatOrExist")
	public boolean isRepeatOrExist(String nameZH,String nameEN,String shortName,String customerId){
		String type = getPara("type");
		if(type.equals("ZH")){
			CustomerFormMap targetCustomerZH = customerMapper.findbyFrist("nameZH", nameZH, CustomerFormMap.class);
			CustomerFormMap presentCustomerZH = customerMapper.findbyFrist("id", customerId, CustomerFormMap.class);
			String presentNameZH = presentCustomerZH.getStr("nameZH");
			if(targetCustomerZH == null || presentNameZH.equals(nameZH)){
				return true;
			}
		}else if(type.equals("EN")){
			
			CustomerFormMap targetCustomerEN = customerMapper.findbyFrist("nameEN", nameEN, CustomerFormMap.class);
			CustomerFormMap presentCustomerEN = customerMapper.findbyFrist("id", customerId, CustomerFormMap.class);
			if(presentCustomerEN.getStr("nameEN").equals(nameEN) || targetCustomerEN == null){
				return true;
			}
		}else{
			CustomerFormMap targetCustomerShortName = customerMapper.findbyFrist("shortName", shortName, CustomerFormMap.class);
			CustomerFormMap presentCustomerShortName = customerMapper.findbyFrist("id", customerId, CustomerFormMap.class);
			if(presentCustomerShortName.getStr("shortName").equals(shortName) || targetCustomerShortName == null){
				return true;
			}
		}
		
		return false;
	}
	
	
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "客户列表";
		CustomerFormMap customerFormMap = findHasHMap(CustomerFormMap.class);
		//exportData = 
		// [{"colkey":"sql_info","name":"SQL语句","hide":false},
		// {"colkey":"total_time","name":"总响应时长","hide":false},
		// {"colkey":"avg_time","name":"平均响应时长","hide":false},
		// {"colkey":"record_time","name":"记录时间","hide":false},
		// {"colkey":"call_count","name":"请求次数","hide":false}
		// ]
		String exportData = customerFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		String type = getPara("type"); 
		if(type.equals("foreign")){
			List<CustomerFormMap> lis = customerMapper.findForeignPage(customerFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}else if(type.equals("internal")){
			List<CustomerFormMap> lis = customerMapper.findInternalPage(customerFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}else {
			List<CustomerFormMap> lis = customerMapper.findLargePage(customerFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	}
	
	public String list_clientUser(Model model)throws Exception {
		return Common.BACKGROUND_PATH + "/system/customer/list_clientUser";
	}
	
}