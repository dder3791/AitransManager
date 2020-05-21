package com.aiTrans.controller.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AppealClientFormMap;
import com.aiTrans.entity.ClientCustomerNeedFormMap;
import com.aiTrans.entity.ClientUserFormMap;
import com.aiTrans.entity.EvaluateClientFormMap;
import com.aiTrans.entity.OrderFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.AppealClientMapper;
import com.aiTrans.mapper.ClientCustomerNeedMapper;
import com.aiTrans.mapper.ClientUserMapper;
import com.aiTrans.mapper.EvaluateClientMapper;
import com.aiTrans.mapper.OrderMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
/**
 * 平台申诉管理
 * @author Dell
 * @param <ClientCustomerNeedMapper>
 *
 */
@Controller
@RequestMapping("/appealClient/")
public class AppealClientController extends BaseController{

	  @Inject
	   private EvaluateClientMapper evaluateClientMapper;
	  @Inject
	   private OrderMapper orderMapper;
	  @Inject
	   private TranslatorMapper translatorMapper;
	  @Inject
	   private ClientUserMapper clientUserMapper;
	  @Inject
	   private ClientCustomerNeedMapper clientCustomerNeedMapper;
	  @Inject
	   private AppealClientMapper appealClientMapper;
	  
	  public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	  
	/**
	 * 跳转平台平台申诉列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
   @RequestMapping("list_appealClient")
	public String list_evelateClient(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/appealClient/list_appealClient";
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
   @RequestMapping("findBy_appealClient_page")
   public PageView findBy_evaluate_page(String pageNow, String pageSize,String column, String sort,Model model) throws Exception  {
	   
	   AppealClientFormMap appealClientFormMap = getFormMap(AppealClientFormMap.class);
	   appealClientFormMap=toFormMap(appealClientFormMap, pageNow, pageSize,appealClientFormMap.getStr("orderby"));
	   appealClientFormMap.put("column", column);
	   appealClientFormMap.put("sort", sort);
	   pageView.setRecords(appealClientMapper.findAppealPage(appealClientFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	   }
   
   /**
    * 查看申诉详细信息
    * @param aid
    * @param type
    * @param request
    * @return
    */
   @RequestMapping("appealAction")
   public String evaluateAction(String aid,String type,HttpServletRequest request){
	   AppealClientFormMap appealClientFormMap=appealClientMapper.findbyFrist("id", aid, AppealClientFormMap.class);
	   if(appealClientFormMap!=null){
		   request.setAttribute("appealClientFormMap", appealClientFormMap);
		   EvaluateClientFormMap evaluateClient=evaluateClientMapper.findbyFrist("orderId", appealClientFormMap.get("orderId").toString(), EvaluateClientFormMap.class);	
		   OrderFormMap orderForMap=orderMapper.findbyFrist("id", evaluateClient.get("orderId").toString(), OrderFormMap.class);
			   if(orderForMap!=null){
				  request.setAttribute("evaluateClient", evaluateClient);
				  request.setAttribute("order", orderForMap);
				  TranslatorFormMap translator= translatorMapper.findbyFrist("id",orderForMap.get("translatorid").toString() , TranslatorFormMap.class);
				  ClientUserFormMap clientUser=clientUserMapper.findbyFrist("id", orderForMap.get("clientUserId").toString(), ClientUserFormMap.class);
				  ClientCustomerNeedFormMap clientCustomerNeed=clientCustomerNeedMapper.findbyFrist("id", orderForMap.get("clientCustomerNeedId").toString(), ClientCustomerNeedFormMap.class);
				  request.setAttribute("translator", translator);
				  request.setAttribute("clientUser", clientUser);
				  request.setAttribute("clientCustomerNeed", clientCustomerNeed);
			   }
	   }
	  
	   request.setAttribute("type",type);
	   return  Common.BACKGROUND_PATH + "/system/appealClient/look_appealClient";
   }
   

   /**
    * 修改申诉处理状态
    * @param apealClientId
    * @param apealState
    * @return
    */
   @ResponseBody
   @RequestMapping("editAppealState")
   public String editAppealState(String apealClientId,String apealState){
	   //System.out.println("传输数据："+apealClientId+"-------"+apealState);
	   AppealClientFormMap appealClientFormMap = new AppealClientFormMap();
	   appealClientFormMap.put("id", apealClientId);
	   appealClientFormMap.put("appealstate", apealState);
	   
	   appealClientMapper.editAppealState(appealClientFormMap);
	   return "success";
   }
   }
