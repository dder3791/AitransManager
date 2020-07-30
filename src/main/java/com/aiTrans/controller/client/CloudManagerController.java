package com.aiTrans.controller.client;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
