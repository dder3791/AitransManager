package com.aiTrans.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.RewardMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * 资金管理
 * @author SS
 *
 */
@Controller
@RequestMapping("/capital/")
public class CapitalController extends BaseController {
	
	@Inject
	private RewardMapper rewardMapper;
	
	@Inject
	private ProjectMapper projectMapper;

	//转到公司资金流动记录页面
	@RequestMapping("company_capital_flow_record")
	public String company_capital_flow_record(Model model) throws Exception {
		RewardFormMap rewardFormMap=new RewardFormMap();
		 List<RewardFormMap> list = rewardMapper.findCompanyRecordByPage(rewardFormMap);
	        
	        //公司总收入
	        double priceTotal=0;
	        //翻译总字数
	        int length=0;
	        //翻译支出
	        double transTotal=0;
	        //校对支出
	        double proofTotal=0;
	        //审核支出
	        double auditoryTotal=0;
	        	
	        
	        
	        for(RewardFormMap reward :list){
	        	priceTotal=priceTotal+Double.parseDouble(reward.get("priceTotal").toString());
	        	length=length+Integer.parseInt(reward.get("length").toString());
	        	if(reward.get("translatorFeeTotal")!=null && reward.get("translatorFeeTotal")!=""){
	        		transTotal=transTotal+Double.parseDouble(reward.get("translatorFeeTotal").toString());
	        	}
				if(reward.get("proofFeeTotal")!=null && reward.get("proofFeeTotal")!=""){
					proofTotal=proofTotal+Double.parseDouble(reward.get("proofFeeTotal").toString());  		
				}
				if(reward.get("auditoryFeeTotal")!=null && reward.get("auditoryFeeTotal")!=""){
					auditoryTotal=auditoryTotal+Double.parseDouble(reward.get("auditoryFeeTotal").toString());
				}
				
	        	
	        	
	        }
	        
	        //总支出
	        double price=transTotal+proofTotal+auditoryTotal;
	        //总利润
	        double total=priceTotal-price;
	       
	        model.addAttribute("price",price);
	        model.addAttribute("total",total);
	        model.addAttribute("priceTotal",priceTotal);
	        model.addAttribute("length",length);
	        model.addAttribute("transTotal",transTotal);
	        model.addAttribute("proofTotal",proofTotal);
	        model.addAttribute("auditoryTotal",auditoryTotal);
	        
	        
		model.addAttribute("res",findByRes());
		return Common.BACKGROUND_PATH + "/system/capital/list_company_record";
	}
	
	//转到查看公司资金流动详情页面
	@RequestMapping("look_company_record")
	public String look_company_record(Model model){
		String id = getPara("id");
		RewardFormMap rewardFormMap = rewardMapper.findbyFrist("id", id, RewardFormMap.class);
		ProjectFormMap projectFormMap = projectMapper.findbyFrist("id", id, ProjectFormMap.class);
		/*ProjectFormMap projectFormMap = rewardMapper.findCompanyRecordById(id);*/
		model.addAttribute("reward",rewardFormMap);
		model.addAttribute("project",projectFormMap);
		return Common.BACKGROUND_PATH + "/system/capital/look_company_record";
	}
	
	
	//转到译员稿酬记录页面
	@RequestMapping("tra_capital_flow_record")
	public String tra_capital_flow_record(){
		return Common.BACKGROUND_PATH + "/system/capital/list_tra_record";
	}
	
	
	/**
	 * 查看公司资金流动记录
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_company_record_by_page")
	public PageView find_company_record_by_page(Model model,String pageNow,
			String pageSize,String column,String sort) throws Exception {
		RewardFormMap rewardFormMap = getFormMap(RewardFormMap.class);
		rewardFormMap.put("column", column);
		rewardFormMap.put("sort", sort);
		rewardFormMap=toFormMap(rewardFormMap, pageNow, pageSize,rewardFormMap.getStr("orderby"));
		List<RewardFormMap> list = rewardMapper.findCompanyRecordByPage(rewardFormMap);
		pageView.setRecords(list);
		return pageView;
	}
	
	/**
	 * 查看译员稿酬记录
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_tra_record_by_page")
	public PageView find_tra_record_by_page(String pageNow,
			String pageSize,String column,String sort) throws Exception {
		RewardFormMap rewardFormMap = getFormMap(RewardFormMap.class);
		rewardFormMap.put("column", column);
		rewardFormMap.put("sort", sort);
		rewardFormMap=toFormMap(rewardFormMap, pageNow, pageSize,rewardFormMap.getStr("orderby"));
        List<RewardFormMap> list = rewardMapper.findTraRecordByPage(rewardFormMap);
		pageView.setRecords(list);
		return pageView;
	}
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response){
		String fileName ;
		String type=request.getParameter("type");
		RewardFormMap rewardFormMap = findHasHMap(RewardFormMap.class);
		String exportData = rewardFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		if(type.equals("com")){
			fileName = "公司资金流动列表";
			List<RewardFormMap> lis = rewardMapper.findCompanyRecordByPage(rewardFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}else if(type.equals("tra")){
			fileName="译员稿酬列表";
			List<RewardFormMap> lis = rewardMapper.findTraRecordByPage(rewardFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
	}


}