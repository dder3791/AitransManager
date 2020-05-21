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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.OrderFormMap;
import com.aiTrans.mapper.OrderMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

/**
 * 平台订单
 * @author Dell
 *时间： 2017-11-1 中午12:06
 *作者：twl
 */
@Controller
@RequestMapping("/clientOrder/")
public class ClientOrderControler extends BaseController {
	
	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("file_download")
	private void project_download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 //得到要下载的文件名
		
		
        String urlname = request.getParameter("urlname");
        String path=request.getParameter("fileURL");
       
        //通过文件名找出文件的所在目录
			//System.out.println("下载文件路径为"+path);
         //得到要下载的文件
        File file = new File(path);
        //如果文件不存在
        if(!file.exists()){
       	 response.getWriter().println("<font color=red>资源不存在</font>");
        }else{
        	
        
        //设置响应头，控制浏览器下载该文件
        String headName=urlname;
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
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
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
	
	/**
	 * 展示某文件夹路劲下所有文件
	 * @param file
	 * @param map
	 */
	private void listfile(File file, Map<String,String> map){
	    //如果file代表的不是一个文件，而是一个目录
        if(file.isDirectory()){
       //列出该目录下的所有文件和目录
        	File files[] = file.listFiles();
      //遍历files[]数组
        	for(File f : files){
      //递归
        		if(f.isFile()&& !f.isHidden()){
        			String getPath=file.getPath().replace("\\", "/");
        			map.put(getPath+"/"+f.getName(), f.getName());
        		}
        	
        	//listfile(f,map);
        	}
      }else if(file.isFile()&& !file.isHidden()){
        //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key
    	  //	System.out.println("realName:"+realName);
    	  	  String getPath=file.getPath().replace("\\", "/");
		      map.put(getPath+"/"+file.getName(), file.getName());
		 }
	 }
	

	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	@Inject
	private OrderMapper orderMapper;
	
	
	/**
	 * 跳转平台订单列表页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_order")
	public String list_order(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/orderClient/list_order";
	}
	
	@ResponseBody
	@RequestMapping("find_clientOrder_By_page")
	public PageView find_clientOrder_By_page(String pageNow,String pageSize,String column,String sort) throws Exception {
		OrderFormMap orderFormMap = getFormMap(OrderFormMap.class);
		orderFormMap=toFormMap(orderFormMap, pageNow, pageSize,orderFormMap.getStr("orderby"));
		orderFormMap.put("column", column);
		orderFormMap.put("sort", sort);
		pageView.setRecords(orderMapper.findClientOrderByPage(orderFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		    return pageView;
	} 
	
	
	
	/**
	 * 跳转修改订单页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editOrderUI")
	public String editOrderUI(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			OrderFormMap orderFormMap = orderMapper.findOrderById(id);
			
			//查找相关文件
			 String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
			 System.out.println("classPath="+classPath);
			 //classPath=/D:/website/www.aitrans.org/manager/WEB-INF/classes/
			 String wtpPath=classPath.substring(1, classPath.indexOf("/manager"));//路劲到平台项目
			 System.out.println("wtpPath="+wtpPath);
			 
			
			
			 /*System.out.println("transPath="+transPath);
			 System.out.println("proofPath="+proofPath);
			 System.out.println("auditPath="+auditPath);*/
			 
			 
		      
			 if(orderFormMap.getStr("transUrl")!=null && orderFormMap.getStr("transUrl")!="" && !orderFormMap.getStr("transUrl").equals("")){
				 Map<String, String> transPathNameMap = new HashMap<String, String>();
				 
				 String transPath=wtpPath+orderFormMap.getStr("transUrl");//整体翻译文件路劲
				 
				//递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
				 listfile(new File(transPath+"\\"),transPathNameMap);
		         model.addAttribute("transPathNameMap", transPathNameMap);
			 }
	        
			 
			 
			 if(orderFormMap.getStr("proofUrl")!=null && orderFormMap.getStr("proofUrl")!="" && !orderFormMap.getStr("proofUrl").equals("")){
				 Map<String, String> proofPathNameMap = new HashMap<String, String>();
				 
				 String proofPath=wtpPath+orderFormMap.getStr("proofUrl");//整体翻校对件路劲
				 
			     //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
		         listfile(new File(proofPath+"\\"),proofPathNameMap);
		         model.addAttribute("proofPathNameMap", proofPathNameMap);
			 }
			 
	         
	         
			 if(orderFormMap.getStr("auditUrl")!=null && orderFormMap.getStr("auditUrl")!="" && !orderFormMap.getStr("auditUrl").equals("")){
				 Map<String, String> auditPathNameMap = new HashMap<String, String>();
				 
				 String auditPath=wtpPath+orderFormMap.getStr("auditUrl");//整体翻审核件路劲
				 
			     //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
		         listfile(new File(auditPath+"\\"),auditPathNameMap);
		         model.addAttribute("auditPathNameMap", auditPathNameMap);
			 }
	        
			 
			
			
			model.addAttribute("orderFormMap", orderFormMap);
		}
		return Common.BACKGROUND_PATH + "/system/orderClient/editOrder";
	}
	
	
	
	@ResponseBody
	@RequestMapping("editOrder")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="平台订单管理",methods="平台订单管理-修改订单信息")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editOrder() throws Exception {
		OrderFormMap orderFormMap = getFormMap(OrderFormMap.class);
		String id=getPara("id");
		orderFormMap.put("id", id);
		orderMapper.editEntity(orderFormMap);
		return "success";
	}
	
	
	
	/**
	 * 跳转查看订单详情页面//拒签
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lookOrder")
	public String lookOrder(Model model) throws Exception {
		String id = getPara("id");
		if(Common.isNotEmpty(id)){
			OrderFormMap orderFormMap = new OrderFormMap();
			orderFormMap=orderMapper.findOrderById(id);
			model.addAttribute("orderFormMap", orderFormMap);
			
			
			String classPath=this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径
			String wtpPath=classPath.substring(1, classPath.indexOf("/manager"));//路劲到平台项目
			 
			//查找客户相关文件
			String userFile=wtpPath+orderFormMap.getStr("fileURL");
			Map<String, String> userPathNameMap = new HashMap<String, String>();
			//递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
			listfile(new File(userFile+"\\"),userPathNameMap);
			model.addAttribute("userPathNameMap", userPathNameMap);
			
			
			
			//查找译员相关文件
			
			 String transPath=wtpPath+orderFormMap.getStr("transUrl");//整体翻译文件路劲
			 String proofPath=wtpPath+orderFormMap.getStr("proofUrl");//整体翻校对件路劲
			 String auditPath=wtpPath+orderFormMap.getStr("auditUrl");//整体翻审核件路劲
			 
			 Map<String, String> transPathNameMap = new HashMap<String, String>();
		      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
	         listfile(new File(transPath+"\\"),transPathNameMap);
	         model.addAttribute("transPathNameMap", transPathNameMap);
			 
	         Map<String, String> proofPathNameMap = new HashMap<String, String>();
		      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
	         listfile(new File(proofPath+"\\"),proofPathNameMap);
	         model.addAttribute("proofPathNameMap", proofPathNameMap);
	         
	         
	         Map<String, String> auditPathNameMap = new HashMap<String, String>();
		      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
	         listfile(new File(auditPath+"\\"),auditPathNameMap);
	         model.addAttribute("auditPathNameMap", auditPathNameMap);
	         
	         
		}
		
		model.addAttribute("type", getPara("type"));
		
		return Common.BACKGROUND_PATH + "/system/orderClient/lookOrder";
	}
	
	
	
	@ResponseBody
	@RequestMapping("activeOrder")
	public String activeOrder(){
		OrderFormMap orderFormMap = new OrderFormMap();
		String oid=getPara("id");
		String procedureType=getPara("orderFormMap.procedureType");
		
		orderFormMap.put("id",oid);
		if("1".equals(procedureType)){
			orderFormMap.put("taskStateId", 2);
		}else if("2".equals(procedureType)){
			orderFormMap.put("taskStateId", 3);
		}else if("3".equals(procedureType)){
			orderFormMap.put("taskStateId", 4);
		}
		
		orderMapper.updateTaskState(orderFormMap);
		return "success";
	}
}
