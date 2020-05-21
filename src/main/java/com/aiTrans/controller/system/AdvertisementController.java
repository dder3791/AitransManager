package com.aiTrans.controller.system;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
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
import org.springframework.web.multipart.MultipartFile;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AdvertiseListFormMap;
import com.aiTrans.entity.AdvertisementFormMap;
import com.aiTrans.entity.AppealClientFormMap;
import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.PicAdvertiseFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.AdvertisementMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.LocalUtil;
import com.aiTrans.util.POIUtils;

/**
 * 平台广告管理
 * 时间20180312 twl
 * @author Dell
 *
 */
@Controller
@RequestMapping("/advertisement/")
public class AdvertisementController extends BaseController{
	
	@Inject
	private AdvertisementMapper advertisementMapper;
	public String getPara(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getParameter(key);
	}
	
	/**
	 * 跳转平台广告页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
   @RequestMapping("list_advertisementIndex")
	public String list_advertisement(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/advertisement/list_advertisementIndex";
	}
   
   
   @ResponseBody
   @RequestMapping("findBy_advertisementIndex_page")
   public PageView findBy_advertisementIndex_page(String pageNow, String pageSize,String column, String sort,Model model) throws Exception  {
	   
	   AdvertisementFormMap advertisementFormMap = getFormMap(AdvertisementFormMap.class);
	   advertisementFormMap=toFormMap(advertisementFormMap, pageNow, pageSize,advertisementFormMap.getStr("orderby"));
	   advertisementFormMap.put("column", column);
	   advertisementFormMap.put("sort", sort);
	   pageView.setRecords(advertisementMapper.findAdvertiIndexPage(advertisementFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	   }
   
   @RequestMapping("editIndexUI")
   public String editIndexUI(Model model){
	   String id=getPara("id");
	   if(Common.isNotEmpty(id)){
		   AdvertisementFormMap advertisementFormMap=advertisementMapper.findbyFrist("id", id, AdvertisementFormMap.class);
		   model.addAttribute("advertisementFormMap",advertisementFormMap );
	   }
	   return Common.BACKGROUND_PATH + "/system/advertisement/editIndex";
   }
   
   
   @ResponseBody
   @SystemLog(module="广告管理",methods="编辑广告Index")//凡需要处理业务逻辑的.都需要记录操作日志
   @Transactional(readOnly=false)//需要事务操作必须加入此注解
   @RequestMapping("editEntityIndex")
   public String editEntityIndex(@RequestParam("file")MultipartFile files,HttpServletRequest request)throws Exception {
	   String id=request.getParameter("id");
	   String web=request.getParameter("advertisementFormMap.web");
	   String type=request.getParameter("advertisementFormMap.type");
	   String isUserful=request.getParameter("advertisementFormMap.isUserful");
	   
	   // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）  
       String path = "/upload/customer/advertisement/customerIndexShow";  
       
       /* classPath=/D:/website/www.aitrans.org/manager/WEB-INF/classes/
		 wtpPath=D:/website/www.aitrans.org*/
       // 图片上传的绝对路径  
       String url = this.getClass().getClassLoader().getResource("").getPath();
       url=url.substring(1,url.indexOf("/manager"));
       url=url+path; 
       System.out.println("----------------url="+url+"-----------------");
	   AdvertisementFormMap advertisementFormMap=getFormMap(AdvertisementFormMap.class);
	   advertisementFormMap.put("id", id);
	   advertisementFormMap.put("type", type);
	   if(null!=files && !files.equals("") && !files.isEmpty()){
		   //upload(url, files);
		   File dir = new File(url);  
 	       if(!dir.exists()) {  
 	       dir.mkdirs();  
 	       }  
 	         
 	       // 上传图片  
 	      files.transferTo(new File(url+"/"+files.getOriginalFilename())); 
		  advertisementFormMap.put("url", path+"/"+files.getOriginalFilename());
	   }else{
		   advertisementFormMap.put("url","");
	   }
	   
	   advertisementFormMap.put("web", web);
	   advertisementFormMap.put("isUserful", isUserful);
	   advertisementMapper.editEntity(advertisementFormMap);
	   return "success";
   }
   
   
   /**
	 * 导出ExcelIndex
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportIndex")
	public void export(HttpServletRequest request, HttpServletResponse response){
		String fileName="合作客户宣传信息" ;
		AdvertisementFormMap advertisementFormMap = findHasHMap(AdvertisementFormMap.class);
		String exportData = advertisementFormMap.getStr("exportData");// 列表头的json字符串
		List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		List<AdvertisementFormMap> lis = advertisementMapper.findAdvertiIndexPage(advertisementFormMap);
		POIUtils.exportToExcel(response, listMap, lis, fileName);
	}
	
	
	/**
	 * 跳转平台客户展示广告页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping("list_picAdvertise")
	 public String list_picAdvertise(Model model)throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/advertisement/list_picAdvertise";
		 
	 }
	 
	 
	 @ResponseBody
	 @RequestMapping("list_picAdvertise_page")
	 public PageView list_picAdvertise_page(String pageNow, String pageSize,String column, String sort,Model model) throws Exception  {
		 PicAdvertiseFormMap picAdvertiseFormMap = getFormMap(PicAdvertiseFormMap.class);
		 picAdvertiseFormMap=toFormMap(picAdvertiseFormMap, pageNow, pageSize,picAdvertiseFormMap.getStr("orderby"));
		 picAdvertiseFormMap.put("column", column);
		 picAdvertiseFormMap.put("sort", sort);
	     pageView.setRecords(advertisementMapper.findPicadvertisePage(picAdvertiseFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		 return pageView;
	 }
	 
	 /**
	  * 跳转到新增客户展示页面图片广告
	  * 时间 20180314  twl
	  * @return
	  */
	 @RequestMapping("add_advertisementUI")
	 public String addPicAdvertiseUI(){
		 return Common.BACKGROUND_PATH + "/system/advertisement/add_picAdvertise";
	 }
	 
	 
	/**
	 * 添加客户展示页面图片广告
	 * @param files
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add_advertisement")
	@SystemLog(module="广告管理",methods="图片广告-新增广告")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity(@RequestParam("file")MultipartFile files,HttpServletRequest request){
		 try {  
			   String web=request.getParameter("web");
			   String type=request.getParameter("type");
			   String coord=request.getParameter("coord");
			   String context=request.getParameter("context");
			   String isUseful=request.getParameter("isUseful");
			   
			   // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）  
		       String path = "/upload/customer/advertisement/picAdvertise";  
		       
		       /* classPath=/D:/website/www.aitrans.org/manager/WEB-INF/classes/
				 wtpPath=D:/website/www.aitrans.org*/
		       // 图片上传的绝对路径  
		       String url = this.getClass().getClassLoader().getResource("").getPath();
		       url=url.substring(1,url.indexOf("/manager"));
		       url=url+path; 
		       System.out.println("----------------url="+url+"-----------------");
			   PicAdvertiseFormMap picAdvertiseFormMap=getFormMap(PicAdvertiseFormMap.class);
			   picAdvertiseFormMap.put("type", type);
			   if(null!=files && !files.equals("") && !files.isEmpty()){
				   //upload(url, files);
				   File dir = new File(url);  
		 	       if(!dir.exists()) {  
		 	       dir.mkdirs();  
		 	       }  
		 	         
		 	       // 上传图片  
		 	      files.transferTo(new File(url+"/"+files.getOriginalFilename())); 
				  picAdvertiseFormMap.put("url", path+files.getOriginalFilename());
			   }else{
				   picAdvertiseFormMap.put("url", "");
			   }
			   picAdvertiseFormMap.put("web", web);
			   picAdvertiseFormMap.put("context", context);
			   picAdvertiseFormMap.put("coord", coord);
			   picAdvertiseFormMap.put("isUseFul", isUseful);
		   
		  
			   advertisementMapper.addEntity(picAdvertiseFormMap);
			   return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		  
	}
	 
	
	/**
	  * 跳转到编辑客户展示页面图片广告
	  * 时间 20180315  twl
	  * @return
	  */
	 @RequestMapping("edit_advertisementUI")
	 public String editPicAdvertiseUI(Model model){
			String id=getPara("id");
			  if(Common.isNotEmpty(id)){
			   PicAdvertiseFormMap picAdvertiseFormMap=advertisementMapper.findbyFrist("id", id, PicAdvertiseFormMap.class);
			   model.addAttribute("picAdvertiseFormMap",picAdvertiseFormMap );
			  }
			return Common.BACKGROUND_PATH + "/system/advertisement/edit_picAdvertise";
	 }
	 
	 
	 @ResponseBody
     @SystemLog(module="广告管理",methods="编辑广告")//凡需要处理业务逻辑的.都需要记录操作日志
     @Transactional(readOnly=false)//需要事务操作必须加入此注解
     @RequestMapping("editAdvertisement")
     public String editEntity(@RequestParam("file")MultipartFile files,HttpServletRequest request)throws Exception {
		 	String id=request.getParameter("id");
		 	String web=request.getParameter("web");
		 	String type=request.getParameter("type");
		 	String isUserful=request.getParameter("isUseful");
		 	String coord=request.getParameter("coord");
		 	String context=request.getParameter("context");
	   
		 	// 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）  
		 	String path = "/upload/customer/advertisement/picAdvertise";  
       
		 	/* classPath=/D:/website/www.aitrans.org/manager/WEB-INF/classes/
		 	wtpPath=D:/website/www.aitrans.org*/
		 	// 图片上传的绝对路径  
		 	String url = this.getClass().getClassLoader().getResource("").getPath();
		 	url=url.substring(1,url.indexOf("/manager"));
		 	url=url+path; 
		 	System.out.println("----------------url="+url+"-----------------");
		 	PicAdvertiseFormMap picAdvertiseFormMap=getFormMap(PicAdvertiseFormMap.class);
		 	picAdvertiseFormMap.put("id", id);
		 	picAdvertiseFormMap.put("type", type);
		 	if(null!=files && !files.equals("") && !files.isEmpty()){
		 		//upload(url, files);
		 	   File dir = new File(url);  
	 	       if(!dir.exists()) {  
	 	       dir.mkdirs();  
	 	       }  
	 	         
	 	       // 上传图片  
	 	      files.transferTo(new File(url+"/"+files.getOriginalFilename())); 
		 	   picAdvertiseFormMap.put("url", path+"/"+files.getOriginalFilename());
		 	}else{
		 		picAdvertiseFormMap.put("url","");
		 	}
	   
		 	picAdvertiseFormMap.put("web", web);
		 	picAdvertiseFormMap.put("isUseful", isUserful);
		 	picAdvertiseFormMap.put("coord", coord);
		 	picAdvertiseFormMap.put("context", context);
		 	advertisementMapper.editEntity(picAdvertiseFormMap);
		 	return "success";
   }
	 
	 @ResponseBody
	 @RequestMapping("deleteEntity")
	 @Transactional(readOnly=false)//需要事务操作必须加入此注解
	 @SystemLog(module="广告管理",methods="图片广告-删除广告")//凡需要处理业务逻辑的.都需要记录操作日志
	 public String deleteEntity() throws Exception {
		 String[] ids = getParaValues("ids");
		 for (String id : ids) {
			 advertisementMapper.deleteByAttribute("id", id, PicAdvertiseFormMap.class);
		 }
		 return "success";
	 }
	 
	 
	 /**
		 * 导出ExcelIndex
		 * @param request
		 * @param response
		 */
		@RequestMapping("/export")
		public void exportPic(HttpServletRequest request, HttpServletResponse response){
			String fileName="图品广告宣传信息" ;
			PicAdvertiseFormMap picAdvertiseFormMap=findHasHMap(PicAdvertiseFormMap.class);
			String exportData = picAdvertiseFormMap.getStr("exportData");// 列表头的json字符串
			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
			List<PicAdvertiseFormMap> lis = advertisementMapper.findPicadvertisePage(picAdvertiseFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
		
		/**
		 * 跳转平台客户列表广告页面
		 * @param model
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping("list_advertisemenList")
		 public String list_advertisemenList(Model model)throws Exception {
				model.addAttribute("res", findByRes());
				return Common.BACKGROUND_PATH + "/system/advertisement/list_advertisementList";
			 
		 }
		 
		 
		 @ResponseBody
		 @RequestMapping("list_advertisemenList_page")
		 public PageView list_advertisemenList_page(String pageNow, String pageSize,String column, String sort,Model model) throws Exception  {
			 AdvertiseListFormMap advertiseListFormMap = getFormMap(AdvertiseListFormMap.class);
			 advertiseListFormMap=toFormMap(advertiseListFormMap, pageNow, pageSize,advertiseListFormMap.getStr("orderby"));
			 advertiseListFormMap.put("column", column);
			 advertiseListFormMap.put("sort", sort);
		     pageView.setRecords(advertisementMapper.findAdverListFormMapPage(advertiseListFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
			 return pageView;
		 }
		 
		 
		 /**
		  * 跳转到新增列表广告页面
		  * 时间 20180314  twl
		  * @return
		  */
		 @RequestMapping("add_advertisemenListUI")
		 public String addAdvertisemenListUI(){
			 return Common.BACKGROUND_PATH + "/system/advertisement/add_advertisemenList";
		 }
		 
		 
		@ResponseBody 
		@RequestMapping("getCountry")
		public List<String> getCountry(HttpServletRequest request,String country,String provinces){
			//获取session语言
			//String sessionLanguage=(String) request.getSession().getAttribute("language");
			 LocalUtil lu =  LocalUtil.getInstance("zh");  
			 Map<String,String> map=new HashMap<String,String>();
			 
			 if(country!=null && country!="" && (provinces==null || provinces=="") ){
				 //查找省
				 map=lu.getProvinces(country);
			 }
			 
			 
			 Map<Integer,String> mapCountry=new HashMap<Integer,String>();
			 List<String> list=new ArrayList<String>();
			 Set<Map.Entry<String, String>> set = map.entrySet();
			 Iterator<Map.Entry<String, String>> it = set.iterator();
			 int i=0;
			 while( it.hasNext()) {
				 Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				 i++;
				 //mapCountry.put(i,entry.getValue());
				 list.add(entry.getValue());
			 }
			 return list;
		}
		 
		@ResponseBody
		@RequestMapping("add_advertisemenList")
		@SystemLog(module="广告管理",methods="新增列表广告")//凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		public String add_advertisemenList() throws Exception {
		 AdvertiseListFormMap advertiseListFormMap = getFormMap(AdvertiseListFormMap.class);
			try{
				advertisementMapper.addEntity(advertiseListFormMap);
			}catch (Exception e) {
				 throw new SystemException("添加列表广告异常");
			}
			return "success";
		}
		
		
		 @ResponseBody
		 @RequestMapping("delete_advertisemenList")
		 @Transactional(readOnly=false)//需要事务操作必须加入此注解
		 @SystemLog(module="广告管理",methods="列表广告-删除列表广告")//凡需要处理业务逻辑的.都需要记录操作日志
		 public String delete_advertisemenList() throws Exception {
			 String[] ids = getParaValues("ids");
			 for (String id : ids) {
				 advertisementMapper.deleteByAttribute("id", id, AdvertiseListFormMap.class);
			 }
			 return "success";
		 }
		 
		 
		 /**
		  * 跳转到查看详情列表广告
		  * 时间 20180315  twl
		  * @return
		  */
		 @RequestMapping("look_advertisementUI")
		 public String look_advertisementUI(Model model){
				String id=getPara("id");
				  if(Common.isNotEmpty(id)){
				   AdvertiseListFormMap advertiseListFormMap=advertisementMapper.findbyFrist("id", id, AdvertiseListFormMap.class);
				   model.addAttribute("advertiseListFormMap",advertiseListFormMap );
				  }
				return Common.BACKGROUND_PATH + "/system/advertisement/look_advertisemenList";
		 }
		 
		 
		 /**
		  * 跳转到编辑列表广告
		  * 时间 20180315  twl
		  * @return
		  */
		 @RequestMapping("edit_advertisemenListUI")
		 public String edit_advertisemenListUI(Model model){
				String id=getPara("id");
				  if(Common.isNotEmpty(id)){
				   AdvertiseListFormMap advertiseListFormMap=advertisementMapper.findbyFrist("id", id, AdvertiseListFormMap.class);
				   model.addAttribute("advertiseListFormMap",advertiseListFormMap );
				  }
				return Common.BACKGROUND_PATH + "/system/advertisement/edit_advertisemenList";
		 }
		 
		 
	   @ResponseBody
	   @RequestMapping("edit_advertisemenList")
	   public String edit_advertisemenList(String apealClientId,String apealState)throws Exception {
		   HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		   AdvertiseListFormMap advertiseListFormMap = getFormMap(AdvertiseListFormMap.class);
			String id=getPara("id");
			advertiseListFormMap.put("id", id);
			advertisementMapper.editEntity(advertiseListFormMap);
			return "success";
		}
	   
	   
	   /**
		 * 导出ExcelIndex
		 * @param request
		 * @param response
		 */
	  @RequestMapping("/exportList")
	  public void exportList(HttpServletRequest request, HttpServletResponse response){
		  String fileName="列表广告宣传信息" ;
		  AdvertiseListFormMap advertiseListFormMap=findHasHMap(AdvertiseListFormMap.class);
		  String exportData = advertiseListFormMap.getStr("exportData");// 列表头的json字符串
		  List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
		  List<AdvertiseListFormMap> lis = advertisementMapper.findAdverListFormMapPage(advertiseListFormMap);
		  POIUtils.exportToExcel(response, listMap, lis, fileName);
	  }
}
