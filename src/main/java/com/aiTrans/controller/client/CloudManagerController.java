package com.aiTrans.controller.client;

import java.io.File;
import java.io.IOException;

import java.util.List;


import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ClientCloudFormMap;

import com.aiTrans.mapper.ClientUploadDataMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

@Controller
@RequestMapping("/cloud/manager/")
public class CloudManagerController extends BaseController{	
	public final static Logger logger = Logger.getLogger(CloudManagerController.class);
	
	@Inject
	private ClientUploadDataMapper clientUploadDataMapper;
	
	
	@RequestMapping("tocloud")
	public String toCloud(){
		return Common.BACKGROUND_PATH + "/cloud/list";
	}
	
	@ResponseBody
	@RequestMapping("cloudlist")
	//public List<ClientCloudFormMap> list(ClientCloudFormMap params){//不能把表映射实体做为request参数传入，会报错
	public PageView  list( ){
		try{
			ClientCloudFormMap params = getFormMap(ClientCloudFormMap.class);
			List<ClientCloudFormMap> clouds = clientUploadDataMapper.findCloudInfos(params);
			params=toFormMap(params, "1", "10",params.getStr("orderby"));
			//pageView.setPageSize(100);
			//pageView.setPageNow(1);
			//pageView.setPageCount(1000);		
			pageView.setRecords(clouds);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return pageView;	
	}
	
	@RequestMapping("add")
	public String add(){
		return Common.BACKGROUND_PATH + "/cloud/add";
	}
	@ResponseBody
	@RequestMapping("save")
	public String save(){		
		ClientCloudFormMap params = getFormMap(ClientCloudFormMap.class);
		int i = clientUploadDataMapper.insertCloudData(params);
		return i>0?"success":"failed";
		
	}
	@ResponseBody
	@RequestMapping("del")
	public String del(){		
		int i = 0;
		String idstr = getPara("ids");
		if(!StringUtils.isEmpty(idstr)){
			String[] ids = idstr.split(",");
			i = clientUploadDataMapper.delCloud(ids);
		}			
		return i>0?"success":"failed";
	}
	@RequestMapping("toupload")
	public String toUpload(Model m,String id,String moduleName ){
		logger.info("--------------id="+id);
		m.addAttribute("id", id);
		m.addAttribute("moduleName", moduleName);
		return Common.BACKGROUND_PATH + "/cloud/upload";
	}
	@ResponseBody
	@RequestMapping("upload")
	public String upload(Integer id,MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException{
		String uploadDir = "/uploadFile";
		if(file==null){
    		return "failed";
    	}
		Integer i=0;
		try{			
			/*HttpSession s= request.getSession();
			ServletContext c = s.getServletContext();			
			String path = c.getRealPath("/uploadFile/");*/
			//String path = getClass().getClassLoader().getResource("").getPath();
			String path0 = request.getRequestURI();
			logger.info("path0="+path0);
			ServletContext c = request.getServletContext();
			String path = c.getRealPath(uploadDir);
			//String path = request.getRealPath("");
			String fileName = file.getOriginalFilename();
			int extIndex = fileName.lastIndexOf(".");
			String extFileName = "";
			if(extIndex>0){
				extFileName = fileName.substring(extIndex+1);
			}
			String fileUrl = "/"+System.currentTimeMillis()+fileName;
			String saveFileName = "/"+fileName;
			logger.info("***&&&&&@&@&@&!&*@&!*&@!*&@!*&@-----path="+path);
			logger.info("*********=");
			logger.info("***&&&&&@&@&@&!&*@&!*&@!*&@!*&@-----fileUrl="+fileUrl);
			logger.info("***&&&&&@&@&@&!&*@&!*&@!*&@!*&@-----size="+file.getSize());
			file.transferTo(new File(path,fileUrl));
			ClientCloudFormMap params = new ClientCloudFormMap();
			Integer size = Integer.valueOf(String.valueOf(file.getSize()));
			params.set("id", id);
			params.set("dataPath", uploadDir+saveFileName);
			params.set("fileName", fileName);
			params.set("fileType", extFileName);
			params.set("size", size);
			 i = clientUploadDataMapper.updateCloudData(params);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return i>0?"success":"failed";
	}
	public static void main(String args[]){
		String fileName ="googletranslate.dll";
		//fileName = fileName.substring(0, fileName.lastIndexOf("."));
		int x = fileName.lastIndexOf(".");
		logger.info(x);
		String fileExtName = fileName.substring(x+1);
		System.out.println(fileExtName);
	}
}
