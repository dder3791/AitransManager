package com.aiTrans.controller.system;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AppealFormMap;
import com.aiTrans.entity.DomainFormMap;
import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.MajorFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.QuestionFormMap;
import com.aiTrans.entity.QuotationFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.entity.TransVerifyFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.AppealMapper;
import com.aiTrans.mapper.AuditorMapper;
import com.aiTrans.mapper.DomainMapper;
import com.aiTrans.mapper.EvaluateMapper;
import com.aiTrans.mapper.MajorMapper;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.QuotationMapper;
import com.aiTrans.mapper.TransVerifyMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;
import com.aiTrans.util.PasswordHelper;
import com.aiTrans.util.MD5Util;

@Controller
@RequestMapping("/transfer/")
public class TranslatorController extends BaseController {
	@Inject
	private TranslatorMapper translatorMapper;
	
	@Inject
	private ProjectMapper projectMapper;
	
	//2017年4月18日13:26:05
	@Inject
	private MajorMapper majorMapper;
	@Inject
	private DomainMapper domainMapper;
	
	
	private String language;
	
	//译员登录时使用
	private String translatorId;
	
	@Inject
	private EvaluateMapper evaluateMapper;
	
	@Inject
	private AppealMapper appealMapper;
	
	@Inject
	private QuotationMapper quotationMapper;
	
	@Inject
	private TransVerifyMapper transVerifyMapper;
	//2017年4月17日15:44:19
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getParameter(key);
	}
	
	/***
	 * 列出目录下的所有文件
	 * @param file
	 * @param map
	 */
	private void listfile(File file, Map<String,String> map,String type){
	    //如果file代表的不是一个文件，而是一个目录
        if(file.isDirectory()){
       //列出该目录下的所有文件和目录
        	File files[] = file.listFiles();
      //遍历files[]数组
        	for(File f : files){
      //递归
        		if(f.isFile()&& !f.isHidden()){
        			if(type!=null || type!=""){
        				map.put(file.getPath().replace("\\", "/"), f.getName());
        			}else{
        				map.put(file.getPath().replace("\\", "/")+"/"+f.getName(), f.getName());
        			}
        			
        		}
        	
        	//listfile(f,map);
        	}
      }else if(file.isFile()&& !file.isHidden()){
        //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
    	 // 	String realName=file.getName().substring(14);
    	  //	System.out.println("realName:"+realName);
    	  if(type!=null || type!=""){
				map.put(file.getPath().replace("\\", "/"), file.getName());
			}else{
				map.put(file.getPath().replace("\\", "/")+"/"+file.getName(), file.getName());
			}
		 }
	 }
	
	
	//转到译员列表页面
	/**
	 * 根据语言查询译员、校验员、审核员 id，名字
	 * 
	 */
	@ResponseBody
	@RequestMapping("findtransIdandName")
	public List<TranslatorFormMap> findtransIdandName(Model model) throws Exception{
		String trans=getPara("transid");
		if("中英".equals(trans)||"英中".equals(trans)){
			language = "EN";
		}
		if("中日".equals(trans)||"日中".equals(trans)){
			language = "JP";
		}
		if("中韩".equals(trans)||"中韩".equals(trans)){
			language = "KOR";
		}
		if("中德".equals(trans)||"德中".equals(trans)){
			language = "GER";
		}
		if("中法".equals(trans)||"法中".equals(trans)){
			language = "FR";
		}
		List<TranslatorFormMap> transferFormMapList=new ArrayList<TranslatorFormMap>();
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		String domain=getPara("domain");
		System.out.println(domain);
		translatorFormMap.put("language", language);
		translatorFormMap.put("domain", domain);
		transferFormMapList=translatorMapper.findtransIdandName(translatorFormMap);
		
		return transferFormMapList;
	
	}
	
	//转到译员列表
	@RequestMapping("list_transfer")
	public String list_transfer(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		String id = getPara("id");
		if("45".equals(id)){
			language = "EN";
		}
		if("46".equals(id)){
			language = "JP";
		}
		if("47".equals(id)){
			language = "KOR";
		}
		if("48".equals(id)){
			language = "GER";
		}
		if("175".equals(id)){
			language = "FR";
		}
		model.addAttribute("language",language);
		return Common.BACKGROUND_PATH + "/system/transfer/list_transfer";
	}
	
	/**
	 * 跳转  转到校对员审核列表
	 * 时间：2017\10\9     下午 17:01
	 * @param model
	 * @return Tianwenli
	 */
	@RequestMapping("list_proofreader")
	public String list_proofreader(Model model)throws Exception {
		model.addAttribute("res", findByRes());
		//String id = getPara("id");
		//System.out.println(id);
		return Common.BACKGROUND_PATH + "/system/transfer/list_proofreader";
	}
	
	
	/**
	 * 跳转到评定翻译员列表
	 * @param model
	 * @return
	 */
	
	@RequestMapping("list_toTrans")
	public String list_toTrans(Model model){
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/transfer/list_toTrans";
	}
	
	
	/**
	 * 跳转到审核员列表
	 * @param model
	 * @return
	 */
	@RequestMapping("list_auditory")
	public String list_transToAuditory(Model model){
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/transfer/list_auditory";
	}
	
	
	//转到译员注册页面
	@RequestMapping("transferRegister")
	public String transferRegister(Model model){
		model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
		model.addAttribute("domain",domainMapper.findByWhere(getFormMap(DomainFormMap.class)));
		return Common.BACKGROUND_PATH + "/system/transfer/transfer_register";
	}
	
	//译员注册
	@ResponseBody
	@RequestMapping("register")
	@Transactional(readOnly=false)
	public String register(){
		try {
			TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
			PasswordHelper passwordHelper = new PasswordHelper();
			passwordHelper.encryptPassword(translatorFormMap);
			
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			//拿到前台选中的复选框中的值(domain.name);因为是多个,所以是数组类型的
			String[] param = request.getParameterValues("translatorFormMap.domain");
			if(param != null){
				//将数组转换为字符串
				String domain = Arrays.toString(param);
				//转换后的字符串个带有空格(暂不知原因),去掉
				domain = domain.replace(" ", "");
				//Arrays.toString();方法默认转换的格式为["str","str"],所以要去掉首尾的中括号[]
				translatorFormMap.put("domain",domain.substring(1, domain.length()-1));
			}

		
			translatorMapper.addEntity(translatorFormMap);
		} catch (Exception e) {
			throw new SystemException("添加账号异常");
		}
		return "success";
	}
	
	//转到我的信息页面
	@RequestMapping("myInfor")
	public String myInfor(Model model) throws Exception{
		return Common.BACKGROUND_PATH + "/system/transfer/show_myInfor";
	}
	
	//展示我的信息
	@ResponseBody
	@RequestMapping("showMyInfor")
	public TranslatorFormMap showMyInfor(Model model) throws Exception{
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = (TranslatorFormMap) Common.findUserSession(request);
		int id = (int) translatorFormMap.get("id");
		translatorId = id + "";
		List<TranslatorFormMap> list = translatorMapper.findByAttribute("id", translatorId, TranslatorFormMap.class);
//		String transferList = JSON.toJSON(list).toString();
		//		model.addAttribute("myInfor",list.get(0));
		return list.get(0);
//		return transferList;
	}
	
	
	
	//展示译员信息
	/*@RequestMapping("myInfor")
	public String myInfor(Model model) throws Exception{
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TransferFormMap translatorFormMap = (TransferFormMap) Common.findUserSession(request);
		int id = (int) translatorFormMap.get("id");
		translatorId = id + "";
		List<TransferFormMap> list = translatorMapper.findByAttribute("id", translatorId, TransferFormMap.class);
		model.addAttribute("myInfor",list.get(0));
		
		return Common.BACKGROUND_PATH + "/system/transfer/show_myInfor";
	}*/
	
	//译员修改个人信息
	@RequestMapping("editMyInfor")
	public String editMyInfor(Model model){
		model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
		model.addAttribute("domain",domainMapper.findByWhere(getFormMap(DomainFormMap.class)));
		List<TranslatorFormMap> list = translatorMapper.findByAttribute("id", translatorId, TranslatorFormMap.class);
		for (TranslatorFormMap translatorFormMap : list) {
			System.out.println(translatorFormMap);
		}
		model.addAttribute("transfer",list.get(0));
		return Common.BACKGROUND_PATH + "/system/transfer/edit_myInfor";
	}
	
	
	//转向我的项目页面
	@RequestMapping("list_myProject")
	public String myTask() throws Exception{
		return Common.BACKGROUND_PATH + "/system/transfer/list_myProject";
	}
	
	//转向我的评价页面
	@RequestMapping("list_myEvaluate")
	public String list_myEvaluate(){
         return Common.BACKGROUND_PATH + "/system/transfer/list_myEvaluate";		
	}
	//接受项目
	@ResponseBody
	@RequestMapping("accept")
	public String accept(){
		String projectId = getPara("id");
		projectMapper.accept(projectId);
		return "success";
	}
	
	//完成项目
	@ResponseBody
	@RequestMapping("complete")
	public String complete(){
		String projectId = getPara("id");
		projectMapper.complete(projectId);
		return "success";
	}
	
	
	//转向我的稿酬页面
	@RequestMapping("list_myReward")
	public String list_myReward() throws Exception{
		return Common.BACKGROUND_PATH + "/system/transfer/list_myReward";
	}
	//转到评论申诉页面
	@RequestMapping("addappeal")
	public String addappeal(Model model) throws Exception{
		String id=getPara("id");
		EvaluateFormMap evaluateFormMap=evaluateMapper.findbyFrist("id", id, EvaluateFormMap.class);
	    int translatorId=(int) evaluateFormMap.get("translatorId");
		int projectId=(int)evaluateFormMap.get("projectId");
		ProjectFormMap project=new ProjectFormMap();
		project.put("translatorId", translatorId);
		TranslatorFormMap transName=translatorMapper.findById(project);
		ProjectFormMap projectFormMap=new ProjectFormMap();
		projectFormMap.put("id", projectId);
		String projectName=projectMapper.findProject(projectFormMap);
		model.addAttribute("translatorId", transName);
		model.addAttribute("projectId", projectName);
		return Common.BACKGROUND_PATH + "/system/transfer/addAppeal";
	}
	/**
	 * 后台添加申诉信息
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addAppeal")
	@SystemLog(module = "添加申诉", methods = "评价管理-添加申诉")
	public String addAppeal(Model model){
		try {
	     AppealFormMap appealFormMap=getFormMap(AppealFormMap.class);
	     String nickname=(String) appealFormMap.get("translatorId");
	     String translatorId=translatorMapper.findTranslatorId(nickname);
	     String name=(String)appealFormMap.get("projectId");
	     ProjectFormMap projectFormMap=new ProjectFormMap();
	     projectFormMap.put("name", name);
	     String projectId=projectMapper.findProjectName(name);
	     String state=getPara("state");
	     String cause=getPara("cause");
	     appealFormMap.put("state", state);
	     appealFormMap.put("cause", cause);
	     appealFormMap.put("translatorId", translatorId);
	     appealFormMap.put("projectId", projectId);
	     appealMapper.addEntity(appealFormMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "success";
	}
	//查看我的稿酬
	@ResponseBody
	@RequestMapping("find_my_reward_by_page")
	public PageView find_my_reward_by_page(String pageNow, String pageSize,
			String column, String sort) throws Exception {
		RewardFormMap rewardFormMap = getFormMap(RewardFormMap.class);
		rewardFormMap = toFormMap(rewardFormMap, pageNow, pageSize,
				rewardFormMap.getStr("orderby"));
		rewardFormMap.put("column", column);
		rewardFormMap.put("sort", sort);

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = (TranslatorFormMap) Common.findUserSession(request);
		int id = (int) translatorFormMap.get("id");
		translatorId = id + "";
		
		rewardFormMap.put("translatorId", translatorId);

		pageView.setRecords(translatorMapper.findMyRewardPage(rewardFormMap));
		return pageView;
	}
	
	/*//查看我的稿酬详情
	@RequestMapping("look_my_reward_detail")
	public String look_my_reward_detail(){
		return Common.BACKGROUND_PATH + "/system/transfer/my_reward_detail";
	}*/
	
	//查看我的项目
	@ResponseBody
	@RequestMapping("find_my_project_by_page")
	public PageView find_my_project_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column",column);
		projectFormMap.put("sort",sort);
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = (TranslatorFormMap) Common.findUserSession(request);
		
		int id = (int) translatorFormMap.get("id");
		translatorId = id+"";
		System.out.println("--------------------------"+id);
		projectFormMap.put("id",translatorId);
		
		List<ProjectFormMap> list = projectMapper.findMyProjectPage(projectFormMap);
		
        pageView.setRecords(list);
		return pageView;
	}
	
	//查看我的评价
	 @ResponseBody
	 @RequestMapping("find_my_Evaluate_by_page")
	 public PageView find_my_Evaluate_by_page(String pageNow,
		String pageSize,String column,String sort) throws Exception{
		EvaluateFormMap evaluateFormMap=getFormMap(EvaluateFormMap.class);
		evaluateFormMap = toFormMap(evaluateFormMap, pageNow, pageSize,
		evaluateFormMap.getStr("orderby"));
		evaluateFormMap.put("column", column);
		evaluateFormMap.put("sort", sort);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = (TranslatorFormMap) Common.findUserSession(request);
		int id = (int) translatorFormMap.get("id");
		translatorId = id+"";
		evaluateFormMap.put("translatorId", id);
		List<EvaluateFormMap> evaluateList=new ArrayList<EvaluateFormMap>();
		List<EvaluateFormMap> evaluateMap=evaluateMapper.findMyEvaluatePage(evaluateFormMap);
		for (EvaluateFormMap evaluateMaps : evaluateMap) {
			EvaluateFormMap evaluate=new EvaluateFormMap();
			int translatorId=(int) evaluateMaps.get("translatorId");
			int projectId=(int) evaluateMaps.get("projectId");
			evaluate.put("translatorId", translatorId);
			evaluate.put("projectId", projectId);
			int total=evaluateMapper.appealCount(evaluate);
			evaluateMaps.put("total", total);
			evaluateList.add(evaluateMaps);
		}
		pageView.setRecords(evaluateList);
		return pageView;
	}
	
	//查看译员列表
	@ResponseBody
	@RequestMapping("find_transfer_by_page")
	public PageView find_transfer_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		translatorFormMap=toFormMap(translatorFormMap, pageNow, pageSize,translatorFormMap.getStr("orderby"));
		translatorFormMap.put("column",column);
		translatorFormMap.put("sort",sort);
		translatorFormMap.put("language", language);
		
        pageView.setRecords(translatorMapper.findTransferPage(translatorFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	
	//查看校对员列表
	@ResponseBody
	@RequestMapping("find_proofreader_by_page")
	public PageView find_proofreader_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		translatorFormMap=toFormMap(translatorFormMap, pageNow, pageSize,translatorFormMap.getStr("orderby"));
		translatorFormMap.put("column",column);
		translatorFormMap.put("sort",sort);
		System.out.println((translatorMapper.findProofreaderPage(translatorFormMap)).size());
        pageView.setRecords(translatorMapper.findProofreaderPage(translatorFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	
	
	/**
	 * 分页展示翻译员
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("find_auditory_by_page")
	public PageView find_auditory_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		translatorFormMap=toFormMap(translatorFormMap, pageNow, pageSize,translatorFormMap.getStr("orderby"));
		translatorFormMap.put("column",column);
		translatorFormMap.put("sort",sort);
        pageView.setRecords(translatorMapper.findTransToAuditoryPage(translatorFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	/**
	 * 分页展示审核员
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("find_toTrans_by_page")
	public PageView find_toTrans_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		translatorFormMap=toFormMap(translatorFormMap, pageNow, pageSize,translatorFormMap.getStr("orderby"));
		translatorFormMap.put("column",column);
		translatorFormMap.put("sort",sort);
        pageView.setRecords(translatorMapper.findToTransPage(translatorFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}

	
	//跳转核实译员信息页面
	@RequestMapping("proofreaderUI")
	public String proofreaderUI(Model model){
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			//model.addAttribute("major",translatorMapper.findMajorNameByTraId(id));
			TranslatorFormMap tra = translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
			Map<String, String> transPathMap= new HashMap<String, String>();
			String transPath="";
			
			 /**
			  *  [org.apache.ibatis.session.defaults.DefaultSqlSession@18f7d92f]
					classPath=/D:/website/www.aitrans.org/manager/WEB-INF/classes/
					wtpPath=D:/website/www.aitrans.org/WEB-INF/classes
					transPath=D:/website/www.aitrans.org/WEB-INF/classes/upload/13121515808/审核资料/每周工作总结.doc
			  */
			 String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
			 System.out.println("classPath="+classPath);
			 String wtpPath=classPath.substring(1, classPath.indexOf("/manager"));//路劲到平台项目
			 System.out.println("wtpPath="+wtpPath);
			 
			/* classPath=/D:/website/www.aitrans.org/manager/WEB-INF/classes/
			 wtpPath=D:/website/www.aitrans.org*/
			 String minPath="";
			 if("翻译员".equals(getPara("type"))){
				 minPath=tra.getStr("transUrl");//整体翻路劲
			 }
			 else if("校对员".equals(getPara("type"))){
				 minPath=tra.getStr("proofreadUrl");//整体翻校对件路劲
			 }
			 else if("审核员".equals(getPara("type"))){
				 minPath=tra.getStr("auditUrl");//整体翻审核件路劲
			 }
			 
			
			 //System.out.println(transPath);
			//递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
			 System.out.println("minPath="+minPath);
			if(null!=minPath && ""!=minPath && !"".equals(minPath)){
				transPath=wtpPath+"/"+minPath;
				System.out.println("transPath="+transPath);
				transPath.replace("\\", "/");
				listfile(new File(transPath+"/"),transPathMap,getPara("type"));
				
				model.addAttribute("transPathNameMap", transPathMap);
			}
			
			//查找译员简历
			Map<String, String> jlPathMap= new HashMap<String, String>();
			String jianli=tra.getStr("resumeUrl");//整体简历路径
			if(jianli!=null && ""!=jianli && !"".equals(jianli)){
				jianli=wtpPath+"/"+jianli;
				System.out.println("jianli="+jianli);
				jianli.replace("\\", "/");
				listfile(new File(jianli+"/"),jlPathMap,"");
				
				model.addAttribute("jlPathMap", jlPathMap);
			}
			
			
			 
			if(!"".equals(tra.get("certificationStatus")) && null!=tra.get("certificationStatus") && !"0".equals(tra.get("certificationStatus"))){
				
				//包含译员认证信息
				 TransVerifyFormMap transVerifyFromMap=transVerifyMapper.findbyFrist("translatorId", id, TransVerifyFormMap.class);
				
				 String frontUrl=(String) transVerifyFromMap.get("frontUrl");//存储译员证件正面照地址
				 if(null!=frontUrl && !"".equals(frontUrl)){
					frontUrl=wtpPath+frontUrl;
					System.out.println("frontUrl="+frontUrl);
					
					frontUrl.replace("\\", "/");
					Map frontUrlMap=new HashMap(); //正面照
					listfile(new File(frontUrl+"/"),frontUrlMap,"");
					model.addAttribute("frontUrlMap", frontUrlMap);
					
				}
				
				
				String reverseUrl=(String) transVerifyFromMap.get("reverseUrl");//存储译员证件背面照地址
				 
				if(null!=reverseUrl && !"".equals(reverseUrl)){
					reverseUrl=wtpPath+reverseUrl;
					System.out.println("reverseUrl="+reverseUrl);
					
					reverseUrl.replace("\\", "/");
					Map reverseUrlMap=new HashMap(); //正面照
					listfile(new File(reverseUrl),reverseUrlMap,"");
					model.addAttribute("reverseUrlMap", reverseUrlMap);
					
				}
				
				model.addAttribute("transVerify", transVerifyFromMap);
			}
			
			
			model.addAttribute("transfer",tra );
			model.addAttribute("type", getPara("type"));
		}
		return Common.BACKGROUND_PATH + "/system/transfer/transfer_proofreader";
		
	}
	
	
	/**
	 * 获取译员报价信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTransQuo")
	public List getTransQuo(String id){
		//查询有关报价信息
		List<TranslatorFormMap> listTransQuo=new ArrayList<TranslatorFormMap>();
		listTransQuo=quotationMapper.queryByTransId(id);
		return listTransQuo;
	}
	
	
	/**
	 * 核实译员信息 
	 * @param message
	 * @param transId
	 * @param proolevels
	 * @param proofDomain
	 * @param proofLanguage
	 * @return
	 */	
	@ResponseBody
	@RequestMapping("proofreader")
	public String proofreader(String message,String transId,String proolevels,String proofDomain,String proofLanguage){
		//校对员审核通过，修改译员资料
		TranslatorFormMap translatorFormMap = new TranslatorFormMap() ;
		try {
			
			translatorFormMap.put("id", transId);
			String type=getPara("type");//根据type辨别是评定校对员，审核员还是翻译员
		    if("yes".equals(message)){
				//截断等级，领域，语言
				String []proolevelArry=proolevels.split(",");
				String []proofDomainArry=proofDomain.split(",");
				String []proofLanguageArry=proofLanguage.split(",");
				if(proolevelArry!=null){
					for(int i=0;i<proolevelArry.length;i++){
						QuotationFormMap quotationFormMap=new QuotationFormMap();
						quotationFormMap.put("transtionId", transId);					
						quotationFormMap.put("domain", proofDomainArry[i]);
						quotationFormMap.put("languages", proofLanguageArry[i]);
						//添加到报价表
						//1.先查找有没有相同的数据(本地传来数据不重复，数据库数据不重复)
						if(i<1 || !proolevelArry[i].equals(proolevelArry[i-1]) || !proofDomainArry[i].equals(proofDomainArry[i-1]) || !proofLanguageArry[i].equals(proofLanguageArry[i-1])){
							List<QuotationFormMap> quotationList=quotationMapper.findByNames(quotationFormMap);
							if("翻译员".equals(type)){
								quotationFormMap.put("tranlevels", proolevelArry[i]);
								quotationFormMap.put("applyStateT", 1);
							}
							if("校对员".equals(type)){
								quotationFormMap.put("proolevels", proolevelArry[i]);	
								quotationFormMap.put("applyStateP", 1);
										}
							if("审核员".equals(type)){
								quotationFormMap.put("auditlevels", proolevelArry[i]);
								quotationFormMap.put("applyStateA", 1);
							}
	
							if(quotationList.size()==0){
								//2.报价表添加数据
								quotationMapper.addEntity(quotationFormMap);
							}else{
								quotationMapper.editToTrans(quotationFormMap);
							}
						}
					}
				}

				
				if(proolevelArry!=null && quotationMapper.queryByTransId(transId).size()==0){
					//修改译员语言、等级、领域
					translatorFormMap.put("level", proolevelArry[0]);
					translatorFormMap.put("language",proofLanguageArry[0]);
					translatorFormMap.put("domain",proofDomain);
					translatorMapper.editTransLanLevDoma(translatorFormMap);
				}
				else if(proolevelArry!=null && quotationMapper.queryByTransId(transId).size()>0){
					//修改译员语言、等级、领域
					translatorFormMap.put("level",0);
					translatorFormMap.put("language","");
					
					TranslatorFormMap tran=translatorMapper.findbyFrist("id", transId, TranslatorFormMap.class);
					System.out.println(tran.getStr("domain"));
					String domainStr="";
					if(tran.getStr("domain")!=null && tran.getStr("domain")!="" && tran.getStr("domain")!="null"){
						for(int i=0;i<proofDomainArry.length;i++){
							
								if(!tran.getStr("domain").contains(proofDomainArry[i])){
									domainStr+=","+proofDomainArry[i];
								}
						}
					}else{
						domainStr=proofDomain;
					}
					
					System.out.println(domainStr);
					domainStr=tran.getStr("domain")+domainStr;
					if(domainStr.indexOf(",")==0){
						domainStr=domainStr.substring(1,domainStr.length());
					}
					System.out.println(domainStr);
					translatorFormMap.put("domain",domainStr);
					translatorMapper.editTransLanLevDoma(translatorFormMap);
				}
				
				
				
				//审核通过
				if("翻译员".equals(type)){
					translatorFormMap.put("isVerifty", 2);
					translatorMapper.editTOTrans(translatorFormMap);
				}
				if("校对员".equals(type)){
					translatorFormMap.put("isProofread", 2);	
					translatorMapper.editTransTOProofreader(translatorFormMap);
							}
				if("审核员".equals(type)){
					translatorFormMap.put("isAudit", 2);
					translatorMapper.editTransToAuditory(translatorFormMap);
				}

		    }else if("no".equals(message)){

		    	if("翻译员".equals(type)){
					translatorFormMap.put("isVerifty", 3);
					translatorMapper.editTOTrans(translatorFormMap);
				}
				if("校对员".equals(type)){
					translatorFormMap.put("isProofread", 3);	
					translatorMapper.editTransTOProofreader(translatorFormMap);
							}
				if("审核员".equals(type)){
					translatorFormMap.put("isAudit", 3);
					translatorMapper.editTransToAuditory(translatorFormMap);
				}
				
				
		    }
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	
	/**
	 * 取消译员对应翻译资格
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteQuo")
	public String deleteQuo(String domain,String language,String level,String type,String quoId,String action,String transId){
		try {
			//校对员审核通过，修改译员资料
			TranslatorFormMap translatorFormMap = new TranslatorFormMap() ;
			translatorFormMap.put("id", transId);
			QuotationFormMap quotationFormMap=new QuotationFormMap();
			quotationFormMap.put("transtionId", transId);					
			quotationFormMap.put("domain", domain);
			quotationFormMap.put("languages", language);
			//添加到报价表
			//1.先查找有没有相同的数据(本地传来数据不重复，数据库数据不重复)
			List<QuotationFormMap> quoList=quotationMapper.queryByDomLanLev(quotationFormMap);
			if("0".equals(action)){
				if("翻译员".equals(type)){
					quotationFormMap.put("tranlevels", level);
					quotationFormMap.put("applyStateT", 1);
				}
				if("校对员".equals(type)){
					quotationFormMap.put("proolevels", level);	
					quotationFormMap.put("applyStateP", 1);
							}
				if("审核员".equals(type)){
					quotationFormMap.put("auditlevels", level);
					quotationFormMap.put("applyStateA", 1);
				}
	
				if(null==quoList || quoList.size()==0){
					//2.报价表添加数据
					quotationMapper.addEntity(quotationFormMap);
				}else{
					quotationMapper.editToTrans(quotationFormMap);
				}
	
				
				if(quotationMapper.queryByTransId(transId).size()==0){
					//修改译员语言、等级、领域
					translatorFormMap.put("level",level);
					translatorFormMap.put("language",language);
					translatorFormMap.put("domain",domain);
					translatorMapper.editTransLanLevDoma(translatorFormMap);
				}
				else if(quotationMapper.queryByTransId(transId).size()>0){
					//修改译员语言、等级、领域
					translatorFormMap.put("level",0);
					translatorFormMap.put("language","");
					
					TranslatorFormMap tran=translatorMapper.findbyFrist("id", transId, TranslatorFormMap.class);
					System.out.println(tran.getStr("domain"));
					String domainStr="";
					if(tran.getStr("domain")!=null && "".equals(tran.getStr("domain")) && tran.getStr("domain")!="" && tran.getStr("domain")!="null"){
						if(!tran.getStr("domain").contains(domain)){
							domainStr+=","+domain;
						}
					}else{
						domainStr=domain;
					}
					
					System.out.println(domainStr);
					domainStr=tran.getStr("domain")+domainStr;
					if(domainStr.indexOf(",")==0){
						domainStr=domainStr.substring(1,domainStr.length());
					}
					System.out.println(domainStr);
					translatorFormMap.put("domain",domainStr);
					translatorMapper.editTransLanLevDoma(translatorFormMap);
				}
				
				
				
				//审核通过
				if("翻译员".equals(type)){
					translatorFormMap.put("isVerifty", 2);
					translatorMapper.editTOTrans(translatorFormMap);
				}
				if("校对员".equals(type)){
					translatorFormMap.put("isProofread", 2);	
					translatorMapper.editTransTOProofreader(translatorFormMap);
							}
				if("审核员".equals(type)){
					translatorFormMap.put("isAudit", 2);
					translatorMapper.editTransToAuditory(translatorFormMap);
				}
			}	
			else if("1".equals(action)){//审核不通过
				if("翻译员".equals(type)){
					quotationFormMap.put("tranlevels", level);
					quotationFormMap.put("applyStateT",2);
				}
				if("校对员".equals(type)){
					quotationFormMap.put("proolevels", level);	
					quotationFormMap.put("applyStateP", 2);
							}
				if("审核员".equals(type)){
					quotationFormMap.put("auditlevels", level);
					quotationFormMap.put("applyStateA", 2);
				}
	
				if(null==quoList || quoList.size()==0){
					//2.报价表添加数据
					quotationMapper.addEntity(quotationFormMap);
				}else{
					quotationMapper.editToTrans(quotationFormMap);
				}
			}
			return "success";	
		} catch (Exception e) {
			return "error";
		}			
				
				
		//修改译员在报价表中的报价状态
		
		/*TranslatorFormMap translatorFormMap = new TranslatorFormMap() ;
		translatorFormMap.put("domain", domain);
		translatorFormMap.put("language", language);
		translatorFormMap.put("level", level);
		
		if("翻译员".equals(type)){
			
			translatorMapper.editTOTrans(translatorFormMap);
		}
		if("校对员".equals(type)){
			translatorFormMap.put("language", language);	
			translatorMapper.editTransTOProofreader(translatorFormMap);
					}
		if("审核员".equals(type)){
			translatorFormMap.put("isAudit", 2);
			translatorMapper.editTransToAuditory(translatorFormMap);
		}*/
	}

	
	//转到添加译员页面
	@RequestMapping("addUI")
	public String addUI(Model model){
		String language = getPara("lan");
		model.addAttribute("lan",language);
		model.addAttribute("domain",domainMapper.findByWhere(new DomainFormMap()));
		model.addAttribute("major",majorMapper.findByWhere(new MajorFormMap()));
		return Common.BACKGROUND_PATH + "/system/transfer/add_transfer";
	}
	
	/**
	 * 添加译员
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="客户管理",methods="新增客户")
	@Transactional(readOnly=false)
	public String addTranslatoer() throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		
		String[] param = request.getParameterValues("translatorFormMap.domain");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		translatorFormMap.put("registerTime", sdf.format(date));
		
		if(param != null){
			String domain = Arrays.toString(param);
			domain.replace(" ", "");
			translatorFormMap.put("domain", domain.subSequence(1, domain.length()-1));
		}
		try {
			PasswordHelper passwordHelper = new PasswordHelper();
			translatorFormMap.set("password","123456789");
			passwordHelper.encryptPassword(translatorFormMap);
			translatorMapper.addEntity(translatorFormMap);
		} catch (Exception e) {
			throw new SystemException("添加译员异常");
		}
		return "success";
	}
	
	//转到译员详情页面
	@RequestMapping("look_transfer")
	public String look_transfer(Model model) throws Exception {
		String id = getPara("translatorId");
		if(Common.isNotEmpty(id)){
			model.addAttribute("major",translatorMapper.findMajorNameByTraId(id));
			TranslatorFormMap tra = translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
			model.addAttribute("transfer",tra );
			
			Map<String, String> fileNameMap = new HashMap<String, String>();
			if(tra.get("proofreadUrl")!=null){
			      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
				         listfile(new File((String)tra.get("proofreadUrl")),fileNameMap,"");
				         model.addAttribute("fileNameMap", fileNameMap);  
			}else{
				fileNameMap.put("name", "暂无文件");
				model.addAttribute("message", fileNameMap);
			}	
			
			
		}
		
		
		
		return Common.BACKGROUND_PATH + "/system/transfer/look_transfer";
	}
	
	
	//转到编辑译员信息页面
	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			
			TranslatorFormMap transfer = translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
			/*String domains = transfer.getStr("domain");
			String[] domain = domains.split(",");
			model.addAttribute("transferDomain",domain);*/
			model.addAttribute("transfer", transfer);
			
			//2017年4月18日15:35:46 改进后的代码,直接使用继承自baseMapper中的方法,不用在具体的Mapper中写单独的方法
			model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
			model.addAttribute("domain",domainMapper.findByWhere(getFormMap(DomainFormMap.class)));
			
			String  transDomain = transfer.getStr("domain");
			String [] transDomainArr = transDomain.split(",");
			
			List<Map<String,String>> transferDomains = new ArrayList<>();
			
			for(String s:transDomainArr){
				Map<String,String> i = new HashMap<>();
				i.put("transferDomainName", s);
				transferDomains.add(i);
			}
			model.addAttribute("transferDomains",transferDomains);
			
			/*List<Map<String,String>> domainx = new ArrayList<>();
			
			Map<String,String> m = new HashMap<>();
			m.put("k", "日常");
			Map<String,String> m2 = new HashMap<>();
			m2.put("k", "电子");
			
			domainx.add(m);
			domainx.add(m2);
			model.addAttribute("domainx",domainx);*/
			/*之前的代码,没有充分利用baseMapper,造成了资源的浪费
			model.addAttribute("major",majorMapper.findByTableName());
			model.addAttribute("domain",domainMapper.findByTableName());*/
		}
		return Common.BACKGROUND_PATH + "/system/transfer/edit";
	}

	//编辑译员
	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="译员管理-修改用户")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		//拿到前台选中的复选框中的值(domain.name);因为是多个,所以是数组类型的
		String[] param = request.getParameterValues("translatorFormMap.domain");
		if(param != null){
			//将数组转换为字符串
			String domain = Arrays.toString(param);
			//转换后的字符串个带有空格(暂不知原因),去掉
			domain = domain.replace(" ", "");
			//Arrays.toString();方法默认转换的格式为["str","str"],所以要去掉首尾的中括号[]
			translatorFormMap.put("domain",domain.substring(1, domain.length()-1));
		}		
		translatorMapper.editEntity(translatorFormMap);
		//translatorMapper.deleteByAttribute("userId", translatorFormMap.get("id")+"", UserGroupsFormMap.class);
		return "success";
	}
	
	//删除译员
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "译员管理", methods = "删除译员")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			translatorMapper.deleteByAttribute("id", id, TranslatorFormMap.class);
		}
		return "success";
	}
	
	/**
	 * 验证账号是否存在
	 * 
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExist")
	public boolean isExist(String name) {
		TranslatorFormMap account = translatorMapper.findbyFrist("accountName", name, TranslatorFormMap.class);
		if (account == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证昵称是否存在
	 * 
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExistNickname")
	public boolean isExistNickname(String nickname) {
		//当前登录用户
		TranslatorFormMap presentTransfer = translatorMapper.findbyFrist("nickname", nickname, TranslatorFormMap.class);
		
		if(presentTransfer == null){
			return true;
		}else
			return false;
		
		/*String nickname = presentTransfer.getStr("nickname");
		//昵称为name的用户的数量
		int size = translatorMapper.findNicknameCount(name);
		
		if (nickname.equals(name) | size == 0) {
			return true;
		} else {
			return false;
		}*/
	}
	
	//密码修改
	@RequestMapping("transferUpdatePasswordLayer")
	public String transferUpdatePasswordLayer(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/transfer/transferUpdatePassword";
	}

	// 保存新密码
	@RequestMapping("editPassword")
	@ResponseBody
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "译员管理", methods = "修改密码")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String editPassword() throws Exception {
		// 当验证都通过后，把用户信息放在session里
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		translatorFormMap.put("password", translatorFormMap.get("newpassword"));
		// 这里对修改的密码进行加密
		PasswordHelper passwordHelper = new PasswordHelper();
		passwordHelper.encryptPassword(translatorFormMap);
		
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		translatorFormMap = (TranslatorFormMap) Common.findUserSession(request);
		
		int id = (int) translatorFormMap.get("id");
		translatorId = id+"";
		
		
		translatorFormMap.put("id", translatorId);
		translatorMapper.editEntity(translatorFormMap);
		return "success";
	}
	
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "译员列表";
		TranslatorFormMap translatorFormMap = findHasHMap(TranslatorFormMap.class);
		//exportData = 
		// [{"colkey":"sql_info","name":"SQL语句","hide":false},
		// {"colkey":"total_time","name":"总响应时长","hide":false},
		// {"colkey":"avg_time","name":"平均响应时长","hide":false},
		// {"colkey":"record_time","name":"记录时间","hide":false},
		// {"colkey":"call_count","name":"请求次数","hide":false}
		// ]
		String exportData = translatorFormMap.getStr("exportData");// 列表头的json字符串

		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		translatorFormMap.put("language", language);
		List<TranslatorFormMap> lis = translatorMapper.findTransferPage(translatorFormMap);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}
	
	
	
	@RequestMapping("editClientTransUI")
	public String editClientTransUI(Model model){
		String id=getPara("id");
		
		//包含译员基本信息
		TranslatorFormMap translatorFromMap=translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
		model.addAttribute("transfer", translatorFromMap);
		
		if(!"".equals(translatorFromMap.get("certificationStatus"))&& null!=translatorFromMap.get("certificationStatus") && !"0".equals(translatorFromMap.get("certificationStatus"))){
			
			//包含译员认证信息
			 TransVerifyFormMap transVerifyFromMap=transVerifyMapper.findbyFrist("translatorId", id, TransVerifyFormMap.class);
			
			 String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
			 System.out.println("classPath="+classPath);
			 String wtpPath=classPath.substring(1, classPath.indexOf("/manager"));//路劲到平台项目
			 System.out.println("wtpPath="+wtpPath);
			 String frontUrl=(String) transVerifyFromMap.get("frontUrl");//存储译员证件正面照地址
			 
			if(null!=frontUrl && !"".equals(frontUrl)){
				frontUrl=wtpPath+frontUrl;
				System.out.println("frontUrl="+frontUrl);
				
				frontUrl.replace("\\", "/");
				Map frontUrlMap=new HashMap(); //正面照
				listfile(new File(frontUrl+"/"),frontUrlMap,"");
				model.addAttribute("frontUrlMap", frontUrlMap);
				
			}
			
			
			String reverseUrl=(String) transVerifyFromMap.get("transUrl");//存储译员证件背面照地址
			 
			if(null!=reverseUrl && !"".equals(reverseUrl)){
				reverseUrl=wtpPath+reverseUrl;
				System.out.println("transUrl="+reverseUrl);
				
				reverseUrl.replace("\\", "/");
				Map reverseUrlMap=new HashMap(); //正面照
				listfile(new File(reverseUrl),reverseUrlMap,"");
				model.addAttribute("reverseUrlMap", reverseUrlMap);
			}
			
			 model.addAttribute("transVerifyFromMap",transVerifyFromMap);
		}
		
		//译员专业查询
		model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
		return Common.BACKGROUND_PATH + "/system/transfer/editClientTrans";
	}
	
	
	@RequestMapping("editClientTrans")
	@ResponseBody
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "译员管理", methods = "修改平台译员基本信息")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String editClientTrans()throws Exception {
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		translatorMapper.editEntity(translatorFormMap);
		return "success";
	}
	
	
	/**
	 * 时间 20180308
	 * twl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editClientTransCre")
	@ResponseBody
	@Transactional(readOnly = false)
	@SystemLog(module = "译员管理", methods = "修改平台译员认证信息")
	public String editClientTransCre()throws Exception {
		TransVerifyFormMap transVerifyFomeMap = getFormMap(TransVerifyFormMap.class);
		transVerifyMapper.editEntity(transVerifyFomeMap);
		
		TranslatorFormMap translatorFormMap = new TranslatorFormMap();
		translatorFormMap.put("id", transVerifyFomeMap.get("translatorId"));
		translatorFormMap.put("realName", transVerifyFomeMap.get("realName"));
		translatorFormMap.put("nameid", transVerifyFomeMap.get("IdCard"));
		translatorMapper.editEntity(translatorFormMap);
		
		return "success";
	}
	
	

	/**
	 * 时间 20180308
	 * twl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("transClientCRE")
	@ResponseBody
	@Transactional(readOnly = false)
	@SystemLog(module = "译员管理", methods = "审核平台译员认证信息")
	public String transClientCRE(String type,String translatorId)throws Exception {
		TranslatorFormMap translatorFormMap = new TranslatorFormMap();
		translatorFormMap.put("id", translatorId);
		if("yes".equals(type)){//审核通过
			translatorFormMap.put("certificationStatus", 2);
		 }else{
			 translatorFormMap.put("certificationStatus", 3); 
		 }
		translatorMapper.transClientCre(translatorFormMap);
		return "success";
	}
	
	
	/**
	 * 译员邮箱验重
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping("emileExist")
	public Map emileExist(String email,String id){
		//当前登录用户
		Map map=new HashMap();
		TranslatorFormMap presentTransfer = translatorMapper.findbyFrist("email", email, TranslatorFormMap.class);
		TranslatorFormMap presentTransferId=translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
		if(presentTransfer == null){
			map.put("result", "success");
		}else{
			if(presentTransfer.get("id")==presentTransferId.get("id") || presentTransferId.get("id").equals(presentTransfer.get("id"))){
				map.put("result", "success");
			}else{
				map.put("result", "error");
			}
		}
		return map;
	}
	
	/**
	 * 手机号验重
	 * @param phone
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isClientEditPhone")
	public Map isClientEditPhone(String phone,String id ){
		Map map=new HashMap();
		//当前登录用户
		TranslatorFormMap presentTransfer = translatorMapper.findbyFrist("tel", phone, TranslatorFormMap.class);
		TranslatorFormMap presentTransferId=translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
		if(presentTransfer == null){
			map.put("result", "success");
		}else{
			if(presentTransfer.get("id")==presentTransferId.get("id") || presentTransferId.get("id").equals(presentTransfer.get("id"))){
				map.put("result", "success");
			}else{
				map.put("result", "error");
			}
		}
		return map;
	}
	
	/*
	 * 平台昵称验重
	 */
	@ResponseBody
	@RequestMapping("isExistClientNickname")
	public Map isExistClientNickname(String nickname,String id) {
		Map map=new HashMap();
		//当前登录用户
		TranslatorFormMap presentTransfer = translatorMapper.findbyFrist("nickname", nickname, TranslatorFormMap.class);
		TranslatorFormMap presentTransferId=translatorMapper.findbyFrist("id", id, TranslatorFormMap.class);
		if(presentTransfer == null){
			map.put("result", "success");
		}else{
			if(presentTransfer.get("id")==presentTransferId.get("id") || presentTransferId.get("id").equals(presentTransfer.get("id"))){
				map.put("result", "success");
			}else{
				map.put("result", "error");
			}
		}
		
		return map;
	}

	
	@RequestMapping("addClientTransUI")
	public String addClientTransUI(Model model){
		//译员专业查询
		model.addAttribute("major",majorMapper.findByWhere(getFormMap(MajorFormMap.class)));
		return Common.BACKGROUND_PATH + "/system/transfer/addClientTrans";
	}
	
	
	/**
	 * 添加译员
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("addClientTrans")
	@SystemLog(module="平台译员",methods="新增译员")
	@Transactional(readOnly=false)
	public String addClientTrans() throws Exception{
	
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TranslatorFormMap translatorFormMap = getFormMap(TranslatorFormMap.class);
		String initialPassword=request.getParameter("translatorFormMap.initialPassword");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		translatorFormMap.put("registerTime", sdf.format(date));
		
		translatorFormMap.put("password",MD5Util.ecode(MD5Util.ecode(initialPassword)));//加密密码);
		translatorFormMap.put("initialPassword",initialPassword);
		translatorFormMap.put("roleName","译员");
		translatorFormMap.put("state","空闲");
		translatorFormMap.put("loginStatus","1");
		translatorFormMap.put("point","0"); 
		
		translatorFormMap.put("honor","500");
		translatorFormMap.put("isVerifty","0");
		translatorFormMap.put("isProofread","0");
		translatorFormMap.put("isAudit","0");
		translatorFormMap.put("rank","10");
		translatorFormMap.put("isToolUse","0");
		translatorFormMap.put("certificationStatus","0");
		
		try {
			translatorMapper.addEntity(translatorFormMap);
		} catch (Exception e) {
			throw new SystemException("添加平台译员异常");
		}
		return "success";
	}
	
	//转到译员报价管理页面
		@RequestMapping("listPriceUI")
		public String listPriceUI(Model model){
			String id = getPara("translatorId");
			//QuotationFormMap q = new QuotationFormMap();
			//q.set("transtionId", id);
			//List<QuotationFormMap> quotations = quotationMapper.queryByDomLanLev(q);
			TranslatorFormMap pf = new TranslatorFormMap();
			pf.set("id", id);
			TranslatorFormMap tf = translatorMapper.queryById(pf);
			String tdomains = tf.getStr("domain");
			String[] domains = tdomains.split(",");
			if(domains.length>0){
				model.addAttribute("defaultDomain", domains[0]);
			}
			/*if(quotations.size()>0){
				QuotationFormMap qf = quotations.get(0);
				model.addAttribute("defaultDomain", qf.get("domain"));
			}*/
			model.addAttribute("translatorId", id);	
			model.addAttribute("domains", domains);
			model.addAttribute("translator", tf);
			
			return Common.BACKGROUND_PATH + "/system/transfer/price";
		}
		//查询译员报价信息
		@ResponseBody
		@RequestMapping("listquotation")
		public Map<String,Object> listQuotation(int transtionId,String domain,Model model){
			//queryByDomLanLev
			QuotationFormMap q = new QuotationFormMap();
			q.set("transtionId", transtionId);
			q.set("domain", domain);			
			List<QuotationFormMap> quotations = quotationMapper.queryByDomLanLev(q);			
			Map<String,Object> rs = new HashMap<>();
			rs.put("records", quotations);
			return rs;		 
		}
		
		
		//转到新增译员报价页面
		@RequestMapping("addPriceUI")
		public String addPriceUI(Integer transtionId,String domain,Model model){
			model.addAttribute("transtionId", transtionId);	
			model.addAttribute("domain", domain);
			return Common.BACKGROUND_PATH + "/system/transfer/add_price";
		}
		//新增译员报价
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
		//删除译员报价
		@ResponseBody
		@RequestMapping("delPrice")
		public String delPrice(Model model)throws Exception{
			//String[] ids = getParaValues("ids");
			int i = 0;
			String idstr = getPara("ids");
			if(!StringUtils.isEmpty(idstr)){
				String[] ids = idstr.split(",");
				i = quotationMapper.delPrice(ids);
			}			
			return i>0?"success":"failed";
		}
		//查看译员报价
		@RequestMapping("showPrice")
		public String showPrice(String id,String tranPrice,String prooPrice,String dayTrans,String languages,Model model)throws Exception{
			model.addAttribute("id", id);
			model.addAttribute("tranPrice", tranPrice);
			model.addAttribute("dayTrans", dayTrans);
			model.addAttribute("prooPrice", prooPrice);
			model.addAttribute("languages", languages);
			return Common.BACKGROUND_PATH + "/system/transfer/show_price";
		}
		//转到译员报价编辑页面
		@RequestMapping("editPriceUI")
		public String editPriceUI(String id,String tranPrice,String prooPrice,String dayTrans,String languages,Model model)throws Exception{
			model.addAttribute("id", id);
			model.addAttribute("tranPrice", tranPrice);
			model.addAttribute("dayTrans", dayTrans);
			model.addAttribute("prooPrice", prooPrice);
			model.addAttribute("languages", languages);
			return Common.BACKGROUND_PATH + "/system/transfer/edit_price";
		}
		//编辑译员报价
		@ResponseBody
		@RequestMapping("editPrice")
		public String editPrice()throws Exception{					
			QuotationFormMap quotationFormMap = getFormMap(QuotationFormMap.class);
			System.out.println("编辑报价传来的数据:"+quotationFormMap);
			int i = quotationMapper.updatePrice(quotationFormMap);
			return i>0?"success":"failed";
		}	
}