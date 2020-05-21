package com.aiTrans.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ClientUserFormMap;
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.OnLineCustomerFormMap;
import com.aiTrans.entity.TradingRecordFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.ClientUserMapper;
import com.aiTrans.mapper.OnLineCustomerMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

/**
 * 平台用户管理
 * @author Dell
 *时间：2017-10-14
 *作者：twl
 */
@Controller
@RequestMapping("/clientUser/")
public class ClientUserController extends BaseController{

	
	@Inject
	private OnLineCustomerMapper onLIneCustomerMapper;
	@Inject
	private ClientUserMapper clientUserMapper;
	
	
	@RequestMapping("file_download")
	private void file_download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 //得到要下载的文件名
       String urlname = request.getParameter("urlname");///upload/registrationURL/aitrans/56.jpg       
       String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
       // classPath=E:/Workspace02/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Aitrans02/WEB-INF/classes/ 
       //拼接需要的路劲==平台存放文件路劲
       String allPath=classPath.substring(1, classPath.indexOf("/manager"))+urlname;//路劲到平台项目+urlname;//整体路劲
		 
		 
		 
		  //通过文件名找出文件的所在目录
			//System.out.println("下载文件路径为"+path);
        //得到要下载的文件
       File file = new File(allPath);
       //如果文件不存在
       if(!file.exists()){
      	 response.getWriter().println("<font color=red>资源不存在</font>");
       }else{
       	
       
       //设置响应头，控制浏览器下载该文件
       String headName=urlname.substring(urlname.lastIndexOf("/")+1);
       String userAgent = request.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			headName = java.net.URLEncoder.encode(headName, "UTF-8");
		} else {
		//非IE浏览器的处理：
			headName = new String(headName.getBytes("UTF-8"),"ISO-8859-1");
		}
		response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", headName));
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setCharacterEncoding("UTF-8"); 
       
    
       
       //读取要下载的文件，保存到文件输入流
       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(allPath));
       //创建输出流
       BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
       //创建一个缓冲区
       byte[] buffer = new byte[1024*8];
       //循环将缓冲输入流读入到缓冲区当中
       while(true){
           //循环将缓冲输入流读入到缓冲区当中
           int length = bis.read(buffer);
           //判断是否读取到文件末尾
           if(length == -1){
               break;
           }
           //使用BufferedOutputStream缓冲输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
           bos.write(buffer,0,length);
       }
       //关闭文件输入流
       bis.close();
       //刷新此输入流并强制写出所有缓冲的输出字节数
       bos.flush();
       //关闭文件输出流
       bos.close();
       }	
	}
	
	
	public String getPara(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getParameter(key);
	}
	
	@RequestMapping("list_personal")
	public String list_personal(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/customer/list_personal";
	}
	
	@RequestMapping("list_company")
	public String list_company(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/customer/list_company";
	}
	
	
	
	@ResponseBody
	@RequestMapping("find_company_by_page")
	public PageView find_company_by_page( String pageNow,String pageSize,String column,String sort) throws Exception{
		OnLineCustomerFormMap onLineUserFormMap = getFormMap(OnLineCustomerFormMap.class);
		onLineUserFormMap=toFormMap(onLineUserFormMap, pageNow, pageSize,onLineUserFormMap.getStr("orderby"));
		onLineUserFormMap.put("column", column);
		onLineUserFormMap.put("sort", sort);
		 pageView.setRecords(onLIneCustomerMapper.findCompanyByPage(onLineUserFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		    return pageView;
	}
	
	
	@ResponseBody
	@RequestMapping("find_personal_by_page")
	public PageView find_personal_by_page( String pageNow,String pageSize,String column,String sort) throws Exception{
		ClientUserFormMap clientUserFormMap = getFormMap(ClientUserFormMap.class);
		clientUserFormMap=toFormMap(clientUserFormMap, pageNow, pageSize,clientUserFormMap.getStr("orderby"));
		clientUserFormMap.put("column", column);
		clientUserFormMap.put("sort", sort);
		 pageView.setRecords(clientUserMapper.findClientUserByPage(clientUserFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		    return pageView;
	}
	
	
	
	@RequestMapping("toCompanyUI")
	public String toCompanyUI(String comId,HttpServletRequest request){
		Map<String, String> transPathMap= new HashMap<String, String>();
		OnLineCustomerFormMap onLineUserFormMap=onLIneCustomerMapper.findbyFrist("id", comId, OnLineCustomerFormMap.class);
		if(onLineUserFormMap!=null){
			String uploadPath=(String) onLineUserFormMap.get("registrationURL");//平台上传文件路劲=\\upload/registrationURL/aitrans/56.jpg
			
			String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
			String wtpPath=classPath.substring(1, classPath.indexOf("/manager"))+"/WEB-INF/classes/";//路劲到平台项目
			String path=wtpPath+uploadPath;
			path.replace("\\", "/");
			listfile(new File(path+"/"),transPathMap,"企业");
			request.setAttribute("transPathNameMap", transPathMap);
			/*request.getSession().getServletContext().getRealPath("");//不通过*/	
			// String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
			 //classPath=/E:/Workspace02/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Aitrans02/WEB-INF/classes/  管理系统文件路劲
			//			 E:\\Workspace02\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\Aitrans\\upload/registrationURL/aitrans/56.jpg  平台系统管理文件路劲
			
		 
			//request.setAttribute("allPath", uploadPath);
			request.setAttribute("onLineUserImpl", onLineUserFormMap);
			ClientUserFormMap clientUserFormMap = clientUserMapper.findbyFrist("id", onLineUserFormMap.get("clientUserId").toString(), ClientUserFormMap.class);
			request.setAttribute("clientUserImpl", clientUserFormMap);
		}
		
		request.setAttribute("type", request.getParameter("type"));
		return Common.BACKGROUND_PATH + "/system/customer/toCompany";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("toCompany")
	public String toCompany(String userId,String message){
		ClientUserFormMap clientUserFormMap=new ClientUserFormMap();
		clientUserFormMap.set("id", userId);
		if("no".equals(message)){
			//审核不通过
			clientUserFormMap.set("certificationState", 3);
		}else if("yes".equals(message)){
			//审核通过
			clientUserFormMap.set("certificationState", 2);
		}
		
		clientUserMapper.editUserCertifiState(clientUserFormMap);
		
		return "success";
	}
	
	
	@RequestMapping("toClientUserUI")
	public String toClientUserUI(String userId,String type,HttpServletRequest request){
		Map<String, String> topPathMap= new HashMap<String, String>();
		Map<String, String> buttomPathMap= new HashMap<String, String>();
		
		ClientUserFormMap clientUserFormMap = clientUserMapper.findbyFrist("id",userId, ClientUserFormMap.class);
		
		String uploadPathTop=(String) clientUserFormMap.get("topCardURL");//平台上传文件路劲=\\upload/registrationURL/aitrans/56.jpg
		String uploadPathButtom=(String) clientUserFormMap.get("buttomCardURL");
		
		String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
		String wtpPath=classPath.substring(1, classPath.indexOf("/manager"))+"/WEB-INF/classes/";//路劲到平台项目
		String pathTop=wtpPath+uploadPathTop;//正面照
		String pathButtom=wtpPath+uploadPathButtom;//背面照
		
		pathTop.replace("\\", "/");
		pathButtom.replace("\\", "/");
		
		
		listfile(new File(pathTop+"/"),topPathMap,"个人");
		listfile(new File(pathButtom+"/"),buttomPathMap,"个人");
		
		
		request.setAttribute("topPathMap", topPathMap);
		request.setAttribute("buttomPathMap", buttomPathMap);
		
		
		request.setAttribute("clientUserImpl", clientUserFormMap);
		request.setAttribute("type", type);
		return Common.BACKGROUND_PATH + "/system/customer/toClientUser";
	}
	
	
	
	@RequestMapping("editPersonalUI")
	public String editPersonalUI(Model model){
		String id =getPara("id");
		ClientUserFormMap clientUserFormMap=clientUserMapper.findbyFrist("id", id, ClientUserFormMap.class);
		model.addAttribute("clientUserFormMap", clientUserFormMap);
		return Common.BACKGROUND_PATH + "/system/customer/editClientUser";
		
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("editClientUser")
	public String editClientUSer()throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		ClientUserFormMap clientUserFormMap = getFormMap(ClientUserFormMap.class);
		/*//拿到前台选中的复选框中的值(domain.name);因为是多个,所以是数组类型的
		String[] param = request.getParameterValues("translatorFormMap.domain");
		if(param != null){
			//将数组转换为字符串
			String domain = Arrays.toString(param);
			//转换后的字符串个带有空格(暂不知原因),去掉
			domain = domain.replace(" ", "");
			//Arrays.toString();方法默认转换的格式为["str","str"],所以要去掉首尾的中括号[]
			translatorFormMap.put("domain",domain.substring(1, domain.length()-1));
		}*/
		
		clientUserMapper.editEntity(clientUserFormMap);
		//translatorMapper.deleteByAttribute("userId", translatorFormMap.get("id")+"", UserGroupsFormMap.class);
		return "success";
	}
	
	
	
	
	@RequestMapping("editClientCompanyUI")
	public String editClientCompanyUI(Model model){
		String id=getPara("id");//公司id
		OnLineCustomerFormMap onLineCustomerFormMap=clientUserMapper.findbyFrist("id", id, OnLineCustomerFormMap.class);
		onLineCustomerFormMap.get("clientUserId");
		ClientUserFormMap clientUserFormMap=clientUserMapper.findbyFrist("id", onLineCustomerFormMap.get("clientUserId").toString(), ClientUserFormMap.class);
		model.addAttribute("onLineCustomerFormMap", onLineCustomerFormMap);
		model.addAttribute("clientUserFormMap", clientUserFormMap);
		return Common.BACKGROUND_PATH + "/system/customer/editClientCompany";
		
	}
	
	
	
	@ResponseBody
	@RequestMapping("editClientCompany")
	public String editClientCompany()throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		ClientUserFormMap clientUserFormMap = getFormMap(ClientUserFormMap.class);
		clientUserMapper.editEntity(clientUserFormMap);
		
		OnLineCustomerFormMap onLineCustomerFormMap = getFormMap(OnLineCustomerFormMap.class);
		clientUserMapper.editEntity(onLineCustomerFormMap);
		
		//translatorMapper.deleteByAttribute("userId", translatorFormMap.get("id")+"", UserGroupsFormMap.class);
		return "success";
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
	
}
