package com.aiTrans.controller.system;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.aiTrans.entity.ClientUserFormMap;
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.OnLineCustomerFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.entity.TradingRecordFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.ClientCustomerNeedMapper;
import com.aiTrans.mapper.ClientUserMapper;
import com.aiTrans.mapper.OnLineCustomerMapper;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.TradingRecordMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;


/**
 * 平台交易记录
 * @author Dell
 *时间： 2017-10-19 下午13：25
 *作者：twl
 */
@Controller
@RequestMapping("/tradingRecordClient/")
public class TradingRecordClientController extends BaseController {

	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	@Inject
	private TradingRecordMapper tradingRecordMapper;
	@Inject
	private ClientUserMapper clientUserMapper;
	@Inject
	private TranslatorMapper translatorMapper;
	
	
	
	/**
	 * 跳转平台用户交易记录页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_userTrading")
	public String  list_userTrading(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/tradingRecordClient/list_userTrad";
	}
	
	
	
	/**
	 * 跳转平台议员交易记录页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_transTrading")
	public String  list_transTrading(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/tradingRecordClient/list_transTrad";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("find_TradingRecord_By_page")
	public PageView find_TradingRecord_By_page(String pageNow,String pageSize,String column,String sort) throws Exception {
		/*String type=getPara("type");*/
		TradingRecordFormMap tradRecordFormMap = getFormMap(TradingRecordFormMap.class);
		tradRecordFormMap=toFormMap(tradRecordFormMap, pageNow, pageSize,tradRecordFormMap.getStr("orderby"));
		tradRecordFormMap.put("column", column);
		tradRecordFormMap.put("sort", sort);
		/*tradRecordFormMap.put("type", type);*/
		 pageView.setRecords(tradingRecordMapper.findTradingRecordflowPage(tradRecordFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		    return pageView;
	}
	
	
	
	/**
	 * 跳转添加平台用户，议员交易记录页面
	 * @param model
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(Model model){
		
		List<ClientUserFormMap> clientUserList=clientUserMapper.findByWhere(new ClientUserFormMap());
		List<TranslatorFormMap> transList=translatorMapper.findByWhere(new TranslatorFormMap());
		model.addAttribute("clientUserList", clientUserList);
		model.addAttribute("transList", transList);
		
		
		/*String type=getPara("type");
		List list=new ArrayList();
		if("user".equals(type)){
			list=clientUserMapper.findByWhere(new ClientUserFormMap());
		}else if("trans".equals(type)){
			list=translatorMapper.findByWhere(new TranslatorFormMap());
		}
		http://blog.csdn.net/sun2015_07_24/article/details/53023167
		model.addAttribute("type", type);
		model.addAttribute("impl", list);*/
		return  Common.BACKGROUND_PATH + "/system/tradingRecordClient/addUserTrad";
	}
	
	
	
	
/*	@ResponseBody
	@RequestMapping("findAlltypePay")
	public List findAllClientUser(String typePay,Model model){
		List transList=new ArrayList();
		if("译员".equals(typePay)){
			transList=translatorMapper.findByWhere(new TranslatorFormMap());
			//model.addAttribute("transList", transList);
		}
		if("客户".equals(typePay)){
			transList=clientUserMapper.findByWhere(new ClientUserFormMap());
			//model.addAttribute("clientUserList", clientUserList);
		}
		
		return transList;
	}	*/
	
	
	
	
	/*
	 * 验证余额是否足够支付
	 */
	@RequestMapping("payMoney")
	@ResponseBody
	public boolean payMoney(String payMoney,String makeupCo2){
		Boolean falg=true;
		/*double d=Double.valueOf(str).doubleValue(); */
		double payNumber=Double.valueOf(payMoney).doubleValue();//需支付金额
		double wallet=0;//余额
		if(!"环宇爱译".equals(makeupCo2)){
			TranslatorFormMap translator=translatorMapper.findbyFrist("accountNumber", makeupCo2, TranslatorFormMap.class);
			if(translator!=null){
				//不为null，说明付款人是译员
				if(translator.get("wallet")!=null && translator.get("wallet")!=""){
					wallet=Double.valueOf(translator.get("wallet").toString().substring(0, translator.get("wallet").toString().lastIndexOf(".")+2)).doubleValue();//初始余额
				}else{
					falg=false;
				}
				
			}else{
				//继续查找，确认付款人是否是客户
				ClientUserFormMap clientUser=clientUserMapper.findbyFrist("bankAccount", makeupCo2, ClientUserFormMap.class);
				if(clientUser!=null){
					//不是null。说明是客户
					if(clientUser.get("balance")!=null && clientUser.get("balance")!=""){
						wallet=Double.valueOf(clientUser.get("balance").toString().substring(0,clientUser.get("balance").toString().lastIndexOf(".")+2)).doubleValue();//初始余额
					}else{
						falg=false;
					}
					
				}
			}
		}
		
		if(falg){
			if(payNumber>wallet){
				falg=false;
			}
		}
		
		return falg;
	}
	
	
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="平台交易记录",methods="新增交易记录")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity() throws Exception {
		TradingRecordFormMap tradingRecordFormMap = getFormMap(TradingRecordFormMap.class);
		try{
			clientUserMapper.addEntity(tradingRecordFormMap);
			
			
			
			
			String payee=tradingRecordFormMap.getStr("payee");//收款人
			String payer=tradingRecordFormMap.getStr("payer");//付款人
			System.out.println("收款人："+payee+"付款人"+payer);
			
			/*double d=Double.valueOf(str).doubleValue(); */
			double payMoney=Double.valueOf(tradingRecordFormMap.get("payMoney").toString()).doubleValue();
			System.out.println("支付金额"+payMoney);
			
			//查找付款人
			if(!"环宇爱译".equals(payer)){
				TranslatorFormMap translator=translatorMapper.findByAccountNumber(payer);
				if(translator!=null){
					//不为null，说明付款人是译员
					double wallet=Double.valueOf(translator.get("wallet").toString()).doubleValue();//初始余额
					System.out.println("初始余额"+wallet);
					translator.put("wallet", wallet-payMoney);
					translatorMapper.editEntity(translator);
				}else{
					//继续查找，确认付款人是否是客户
					ClientUserFormMap clientUser=clientUserMapper.findByBankAccount(payer);
					if(clientUser!=null){
						//不是null。说明是客户
						double balance=Double.valueOf(clientUser.get("balance").toString()).doubleValue();//初始余额
						System.out.println("初始余额"+balance);
						clientUser.put("balance", balance-payMoney);
					}
				}
			}
			
			//查找收款人
			if(!"环宇爱译".equals(payee)){
				TranslatorFormMap translator=translatorMapper.findByAccountNumber(payee);
				if(translator!=null){
					//不为null，说明收款人是译员
					double wallet=Double.valueOf(translator.get("wallet").toString()).doubleValue();//初始余额
					System.out.println("初始余额"+wallet);
					translator.put("wallet", payMoney+wallet);
					translatorMapper.editEntity(translator);
					
				}else{
					//继续查找，确认收款人是否是客户
					ClientUserFormMap clientUser=clientUserMapper.findByBankAccount(payee);
					if(clientUser!=null){
						//不是null。说明是客户
						double balance=Double.valueOf(clientUser.get("balance").toString()).doubleValue();//初始余额
						System.out.println("初始余额"+balance);
						clientUser.put("balance", payMoney+balance);
					}
				}
			}
			
		}catch (Exception e) {
			 throw new SystemException("添加交易记录异常");
		}
		return "success";
		
	}
	
	
	
	/**
	 * 查看平台交易详情
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("look_tradingUI")
	public String look_tradingUI(Model model) throws Exception{
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			TradingRecordFormMap tradingRecordFormMap = tradingRecordMapper.findbyFrist("id", id, TradingRecordFormMap.class);
			model.addAttribute("tradingRecordFormMap", tradingRecordFormMap);
		}
		
		return Common.BACKGROUND_PATH + "/system/tradingRecordClient/look_TradingRecord";
	}
	
	
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "交易记录";
		TradingRecordFormMap tradingRecordFormMap = findHasHMap(TradingRecordFormMap.class);
		String exportData = tradingRecordFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		
		List<TradingRecordFormMap> lis = tradingRecordMapper.findTradingRecordflowPage(tradingRecordFormMap);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}
	
}
