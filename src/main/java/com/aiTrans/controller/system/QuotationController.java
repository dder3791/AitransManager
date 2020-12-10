package com.aiTrans.controller.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.QuotationFormMap;
import com.aiTrans.mapper.QuotationMapper;

@Controller
@RequestMapping("/quotation/")
public class QuotationController extends BaseController{

	@Inject
	private QuotationMapper quotationMapper;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	private String id;//对应表的主键
	/**
	 * //新增译员报价
		@ResponseBody
		@RequestMapping("addPrice")
		public String addPrice(Model model)throws Exception{
			//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			int a=1;
			System.out.println(a);
			QuotationFormMap quotationFormMap = getFormMap(QuotationFormMap.class);
			int i = quotationMapper.insertPrice(quotationFormMap);
			return i>0?"success":"failed";
		}	
	 */
	@ResponseBody
	@RequestMapping("add")
	public String add()throws Exception{
		QuotationFormMap quotationFormMap = getFormMap(QuotationFormMap.class);
		int i = quotationMapper.insert(quotationFormMap);
		return i>0?"success":"failed";
	}
	@ResponseBody
	@RequestMapping("edit")
	public String edit()throws Exception{
		QuotationFormMap quotationFormMap = getFormMap(QuotationFormMap.class);
		Integer id = quotationFormMap.getInt("id");
		if(StringUtils.isEmpty(id)){
			return "failed";
		}
		int i = quotationMapper.update(quotationFormMap);
		return i>0?"success":"failed";
	}
	@ResponseBody
	@RequestMapping("del")
	public String del()throws Exception{
		QuotationFormMap quotationFormMap = getFormMap(QuotationFormMap.class);
		Integer id = quotationFormMap.getInt("id");
		if(StringUtils.isEmpty(id)){
			return "failed";
		}
		int i = 0;
		String idstr = getPara("ids");
		if(!StringUtils.isEmpty(idstr)){
			String[] ids = idstr.split(",");
			i = quotationMapper.delPrice(ids);
		}
		return i>0?"success":"failed";
	}
	
	
	
	

}
