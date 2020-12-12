package com.aiTrans.controller.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiTrans.entity.ClientCloudFormMap;
import com.aiTrans.entity.ClientCloudVersionFormMap;
import com.aiTrans.entity.ClientSoftInfoFormMap;
import com.aiTrans.entity.ClientUploadDataFormMap;
import com.aiTrans.entity.ClientUserSoftInfoFormMap;
import com.aiTrans.mapper.ClientUploadDataMapper;
import com.aiTrans.mapper.ClientUserSoftInfoMapper;
import com.aiTrans.util.Common;
import com.aiTrans.util.MyDateUtils;
import com.aiTrans.vo.ClientCheckData;
import com.aiTrans.vo.ClientData;
import com.aiTrans.vo.ClientUpdate;
import com.aiTrans.vo.CloudData;
import com.aiTrans.vo.CloudSimpleData;
import com.aiTrans.vo.CloudVersion;
import com.aiTrans.vo.MoData;

import org.apache.log4j.Logger;
@Controller
@RequestMapping("/cloud/client/")
public class ClientUploadDataController {
	@Inject
	private ClientUploadDataMapper clientUploadDataMapper;
	@Inject
	private ClientUserSoftInfoMapper clientUserSoftInfoMapper;
	
	public final static Logger logger = Logger.getLogger(ClientUploadDataController.class);

	private static final Exception Exception = null;
	/**
	 * （C001）客户端上传云翻译数据
	 * @param clientData
	 * @return
	 */
	@ResponseBody
	@RequestMapping("upload")
	public Map<String,Object> upload(@RequestBody ClientData clientData) {
		Map<String,Object> result = new HashMap<>();
		try{
			ClientUploadDataFormMap params = new ClientUploadDataFormMap();
			String runtime = clientData.getDd();
			if(!StringUtils.isEmpty(runtime)){
				Date runTime = DateUtils.parseDate(runtime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","yyyyMMdd"});
				params.put("runtime", runTime);
			}
			String un = clientData.getUn();
        	String uc = clientData.getUc();
        	String op = clientData.getOp();
			params.put("userName", un);
			params.put("softName", uc);
			params.put("platform", clientData.getCn());
			params.put("source", clientData.getFr());
			params.put("target", clientData.getTo());
			String bn = clientData.getBn();
        	String ua = clientData.getUa();
			String original_text = clientData.getOt();
			String translation_text = clientData.getDt();
			int original_text_len = original_text.length();
			int translation_text_len = translation_text.length();
			int client_original_text_len = clientData.getOc();
			int client_translation_text_len = clientData.getDc();
			params.put("original", original_text);//统计字数到oc
			params.put("translation", translation_text);//统计字数dc
			params.put("originalLen", original_text_len);
			params.put("translationLen", translation_text_len);
			
			//这里添加对账逻辑，income字段是充值金额
			Map<String,Object> query = new HashMap<>();
        	query.put("clientSoftName",un);
        	query.put("clientSoftCode",uc);
			Map<String,Object> trans = clientUserSoftInfoMapper.findTrans(query);//查询译员信息
    		BigDecimal serverBalance = (BigDecimal)trans.get("wallet");
    		BigDecimal incomeBalance = (BigDecimal)trans.get("income");
    		BigDecimal clientBalance = new BigDecimal(0);
    		BigDecimal noUploadBalance = new BigDecimal(0);
    		BigDecimal transPrice = new BigDecimal(0);
    		
    		if(bn!=null){
    			clientBalance = new BigDecimal(bn);
    		}
    		if(ua!=null){
    			noUploadBalance = new BigDecimal(ua);
    		}
    		if(serverBalance==null){
    			serverBalance = new BigDecimal(0);
    		}
    		if(incomeBalance==null){
    			incomeBalance = new BigDecimal(0);
    		}                		
      		if(op!=null){
      			transPrice = new BigDecimal(op);
      		}
    		if(serverBalance.equals(clientBalance.add(noUploadBalance))){
    			serverBalance = serverBalance.subtract(transPrice);
    			result.put("bn", serverBalance);
    			result.put("rc", incomeBalance);
    			result.put("co", "200");
        		result.put("ds", "客户端用户认证成功");
    		}else if((serverBalance.add(incomeBalance)).equals(clientBalance.add(noUploadBalance))){
    			serverBalance = serverBalance.add(incomeBalance);
    			serverBalance = serverBalance.subtract(transPrice);
    			result.put("bn", serverBalance);
    			result.put("rc", incomeBalance);
    			//exec update sql  .....  ......  ...
    			result.put("co", "200");
        		result.put("ds", "客户端用户认证成功");
    		}else{
    			serverBalance = serverBalance.add(incomeBalance);
    			serverBalance = serverBalance.subtract(transPrice);
    			result.put("bn", serverBalance);
    			result.put("rc", incomeBalance);
    			result.put("co", "300");
        		result.put("ds", "客户端用户授权信息验证失败！译员账户余额与客户端不一致！");
    		}
    		int i = clientUploadDataMapper.insertClientUploadData(params);//加上本次翻译费用
    		result.put("bn", String.valueOf(serverBalance.setScale(2)));
    		result.put("rc", String.valueOf(incomeBalance.setScale(2)));
    		
    		
    		
    		
    		
    			//exec update sql  .....  ......  ...
    			
			//cloud ..... balance calc．．．．
			if(i>0){
				result.put("co", "200");
				if(original_text_len!=client_original_text_len){
					//result.put("oc", original_text_len);//统计功能尚未完善
					result.put("oc", client_original_text_len);
				}
				if(translation_text_len!=client_translation_text_len){
					//result.put("dc", translation_text_len);//统计功能尚未完善
					result.put("dc", client_translation_text_len);
				}				
			}else{
				result.put("co", "300");
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;	
	}
	/**
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getprirce")
	public String getPrirce() throws ParseException{
		return Common.BACKGROUND_PATH + "/cloud/lang_cost";
	}
	@ResponseBody
	@RequestMapping("getstatus")
	public Map<String,Object> getStatus(@RequestBody ClientUpdate clientUpdate) {
		ClientSoftInfoFormMap params = new ClientSoftInfoFormMap();
		params.put("softIp", clientUpdate.getIp());
		params.put("softName", clientUpdate.getSn());
		params.put("userName", clientUpdate.getUn());		
		params.put("softVersion", clientUpdate.getSv());
		params.put("lastUpdateTime", clientUpdate.getSd());
		ClientSoftInfoFormMap dataResult = clientUploadDataMapper.findClientUploadInfo(params);
		Map<String,Object> result = new HashMap<>();
		if(dataResult!=null){
			result.put("co", "200");	
			if(compare(clientUpdate.getSd(),(Date)dataResult.get("lastUpdateTime"))){
				result.put("Op", "1");
			}else{
				result.put("Op", "0");
			}
			//需要比较时间
			result.put("sv", dataResult.get("softVersion"));
			result.put("sd", dataResult.get("releaseTime"));
			result.put("sl", dataResult.get("releaseLog"));
			result.put("Sh", dataResult.get("md5Value"));
			result.put("co", "200");			
		}else{
			result.put("co", "200");
			result.put("ds", "没有查到数据");
		}
		return result;
	}
	
	@RequestMapping("/downloadcloud")
	public void downLoad(HttpServletRequest request,HttpServletResponse response,@RequestBody CloudSimpleData coludDate){
    //public void downLoad(HttpServletRequest request,HttpServletResponse response,CloudSimpleData coludDate){		
    	try{    		
        	ClientSoftInfoFormMap clientSoftInfo =queryClient(coludDate);
        	if(clientSoftInfo==null){
        		throw Exception;
        	}
        	String moduleVersion = coludDate.getCv();
        	String moduleName = coludDate.getCn();
        	if(StringUtils.isEmpty(moduleName)||StringUtils.isEmpty(moduleVersion)){    	
        		throw Exception;
        	}
        	Integer cid = clientSoftInfo.getInt("id");
        	ClientCloudFormMap queryParams = new ClientCloudFormMap();
    		queryParams.put("clientId", cid);
    		queryParams.put("moduleName", moduleName);
    		queryParams.put("moduleVersion", coludDate.getCv());
    		ClientCloudFormMap clouds = clientUploadDataMapper.findCloudSingle(queryParams);
        	String dPath = clouds.getStr("dataPath");
    	    ServletContext servletContext = request.getServletContext();
    	    String realPath = servletContext.getRealPath(dPath);
    	    if(StringUtils.isEmpty(realPath)){
    	    	throw Exception;
    	    }
    	    String fileName = dPath.substring(dPath.lastIndexOf("/")+1, dPath.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(realPath));
            logger.info("开始下载文件，InputStream："+inputStream);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName));
            response.addHeader("Content-Length", "" + buffer.length);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush(); 
            outputStream.close();

    	}catch (Exception ex){
    		logger.error("下载文件出错:" + ex);
    		response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out;
			try {
				out = response.getWriter();
				out.flush();				
	            out.println("{\"co\":\"400\",\"ds\":\"FAILED\"}");	            
			} catch (IOException e) {
				e.printStackTrace();
			}
            
    	}
    	 
    }
	/**
	 * A10 请求
	 * （C010）查询云翻译模块状态（是否更新）
	 * @param coludDate
	 * @return
	 */
	
    @ResponseBody
	@RequestMapping("getcloudstatus")
	public Map<String,Object> getCloudStatus(@RequestBody CloudData coludDate) {    	
    		logger.info("传来的映射复杂json对象："+coludDate);
        	Map<String,Object> result = new HashMap<>();
    		try{
	    		ClientSoftInfoFormMap params = new ClientSoftInfoFormMap();
	    		params.put("softIp", coludDate.getIp());
	    		params.put("softName", coludDate.getSn());
	    		params.put("userName", coludDate.getUn());
	    		List<MoData> clientMos = coludDate.getMo();
	    		//ClientSoftInfoFormMap clientInfo = clientUploadDataMapper.findClientInfo(params);//正式环境打开此行
	    		/**
	    		 * {id=3, softIp=192.168.1.108, dataPath=/WEB-INF/downloads/libaow.dll, 
	    		 * softName=Aitrans-PAT 6.0, md5Value=0123456789abcdeffedcba986543210,
	    		 *  lastUpdateTime=2020-07-28 10:31:16.0, 
	    		 *  userName=ZlpApple, 
	    		 *  releaseTime=2020-07-28 10:31:19.0, 
	    		 *  softCode=22LIJO1TTDZWY, releaseLog=1}
	    		 */
	    		/*if(clientInfo==null){//测试用
	    			result.put("co", "400");
	    			result.put("ds", "没有查到客户端软件数据");
	    			return result;
	    		}*/
	    		/**
	    		 * 下面是测试数据，正式环境，删除
	    		 */
	    		ClientSoftInfoFormMap clientInfo = new ClientSoftInfoFormMap();
	    		clientInfo.set("id", 3);
	    		clientInfo.set("softName", coludDate.getSn());
	    		clientInfo.set("userName", coludDate.getUn());
	    		clientInfo.set("softIp", coludDate.getIp());
	    		/**
	    		 * 测试数据结束 
	    		 */
	    		Integer cid = clientInfo.getInt("id");
	    		List<ClientCloudFormMap> clouds = clientUploadDataMapper.findCloudInfo(cid);		    		
	    		List<MoData> serverMos = new ArrayList<>();
	    		for(ClientCloudFormMap c:clouds){
	    			MoData mo = new MoData();
	    			mo.setCn(c.getStr("moduleName"));
	    			mo.setCv(c.getStr("moduleVersion"));
	    			Date cdt = c.getDate("releaseTime");	
	    			String cds = MyDateUtils.dateToStr(cdt,22);
	    			mo.setCd(cds);
	    			mo.setCen(String.valueOf(c.getInt("isAvailable")));
	    			mo.setCr(String.valueOf(c.get("ranking")));
	    			mo.setCh(c.getStr("md5Value"));
	    			mo.setCl(c.getStr("releaseLog"));
	    			mo.setCt(c.getStr("fileType"));
	    			mo.setCs(c.get("size").toString());
	    			mo.setCp(c.getStr("fileName"));
	    			
	    			mo.setOp("0");	    			
	    			serverMos.add(mo);
	    		}		
	    		List<MoData> rsmos = compareData(clientMos,serverMos);
	    		result.put("co", "200");
	    		result.put("ds", "查询成功");
	    		result.put("sn", clientInfo.get("softName"));
	    		result.put("un", clientInfo.get("userName"));
	    		result.put("ip", clientInfo.get("softIp"));	    		
	    		result.put("mo", rsmos);		
    		
	    	}catch (Exception ex){
	    		ex.printStackTrace();
	    	}
    		return result;
    	
	}
    /*private void setUpdateStatus(List<MoData> from,List<Map<String,Object>> to){    	
    	if(from!=null){
			if(from.size()>0){
				for(int i=0;i<from.size();i++){
					for(int j=0;j<to.size();j++){
						Map<String,Object> dataBaseMo = to.get(j);
						MoData md = from.get(i);
						if(md.getCn().equals(dataBaseMo.get("cn"))){
							if(md.getCv().equals(dataBaseMo.get("cv"))){
								dataBaseMo.put("op", "82");
							}
						}
					}
				}
			}
		}    	
    }*/
    private List<MoData> compareData(List<MoData> client,List<MoData> server){
    	List<MoData> result = new ArrayList<>();
    	Set<String> clientNameSet = new HashSet<>();
    	Set<String> shareNameSet = new HashSet<>();
    	Set<String> serverNameSet = new HashSet<>();
    	for(MoData c:client){
			for(MoData s:server){
				if(s.getCn().equals(c.getCn())){
					shareNameSet.add(c.getCn());
					continue;
				}					
			}
		}
    	for(MoData c:client){
    		int b = 0;
			for(String n:shareNameSet){				
				if(n.equals(c.getCn())){
					b++;
					continue;
				}				
			}
			if(b==0){
				clientNameSet.add(c.getCn());
			}
		}
    	for(MoData s:server){
    		int b = 0;
			for(String n:shareNameSet){				
				if(n.equals(s.getCn())){
					b++;
					continue;
				}
			}
			if(b==0){
				serverNameSet.add(s.getCn());
			}
		}    	
    	for(String key:serverNameSet){
    		MoData serverObject = findObject(key, server);
    		serverObject.setOp("81");
    		result.add(serverObject);
    	}
    	for(String key:shareNameSet){
    		MoData serverObject = findObject(key, server);
    		MoData clientObject = findObject(key, client); 
    		serverObject.setOp("82");
    		if(!clientObject.getCr().equals(serverObject.getCr())){
				serverObject.setOp("10");
			}
    		/*MoData clientObject = findObject(key, client);    		
    		if(clientObject.getCn().equals(serverObject.getCn())){
    			serverObject.setOp("0");
    			if(!clientObject.getCv().equals(serverObject.getCv())){
    				serverObject.setOp("82");
    			}
    			if(!clientObject.getCr().equals(serverObject.getCr())){
    				serverObject.setOp("10");
    			}
    		}*/
    		result.add(serverObject);
    	}
    	for(String key:clientNameSet){
    		MoData clientObject = findObject(key, client);
    		clientObject.setOp("83");
    		result.add(clientObject);
    	}
    	return result;
    }
	/**
	 * from中的所有对象 与 to 中的每一个对象比较，如果相同，就在form匹配的对象中增加一个字段compareCount，赋值+1；全部比较完后；遍历from的所有对象，compareCount值小于1的那个对象，就是新增的对象，设置OP的值为81
	 */
    private void setNewUpdateStatus(List<Map<String,Object>> from,List<MoData> to){   
		for(int i=0;i<from.size();i++){
			Map<String,Object> dataBaseMo = from.get(i);
			dataBaseMo.put("cont", 0);
			for(int j=0;j<to.size();j++){				
				MoData md = to.get(j);
				if(md.getCn().equals(dataBaseMo.get("cn"))){
					if(!md.getCv().equals(dataBaseMo.get("cv"))){
						dataBaseMo.put("op", "82");
						countAddOne(dataBaseMo);
					}
					countAddOne(dataBaseMo);
				}
			}
		}
		for(int k=0;k<from.size();k++){
			Map<String,Object> e = from.get(k);
			int c = (Integer)e.get("cont");
			if(c==0){
				e.put("op", "81");
			}
			if(c==1){
				e.put("op", "0");
			}
		}
	
    }
    /**
	 *	setNewUpdateStatus 的测试版本
	 **/
    private void setNewUpdateStatus0(List<Map<String,Object>> from,List<MoData> to){   
		for(int i=0;i<from.size();i++){
			Map<String,Object> dataBaseMo = from.get(i);
			dataBaseMo.put("cont", 0);
			for(int j=0;j<to.size();j++){				
				MoData md = to.get(j);
				if(md.getCn().equals(dataBaseMo.get("cn"))){
					dataBaseMo.put("op", "82");
					countAddOne(dataBaseMo);
				}
			}
		}
		for(int k=0;k<from.size();k++){
			Map<String,Object> e = from.get(k);
			int c = (Integer)e.get("cont");
			if(c==0){
				e.put("op", "81");
			}			
		}
    }
    private void countAddOne(Map<String,Object> dataBaseMo){
    	int cont = (Integer)dataBaseMo.get("cont");
		cont += 1;
		dataBaseMo.put("cont", cont);
    }
    @ResponseBody
	@RequestMapping("getcloudversion")
	public Map<String,Object> getCloudVersion(@RequestBody CloudSimpleData coludDate) {
    	Map<String,Object> result = new HashMap<>();
    	String moduleVersion = coludDate.getCv();
    	String moduleName = coludDate.getCn();
    	if(StringUtils.isEmpty(moduleName)||StringUtils.isEmpty(moduleVersion)){
    		result.put("co", "402");
			result.put("ds", "云翻译模块名称和版本不能为空");
			return result;
    	}
		ClientSoftInfoFormMap params = new ClientSoftInfoFormMap();
		params.put("softIp", coludDate.getIp());
		params.put("softName", coludDate.getSn());
		params.put("userName", coludDate.getUn());		
		ClientSoftInfoFormMap clientInfo = clientUploadDataMapper.findClientInfo(params);
		if(clientInfo==null){
			result.put("co", "400");
			result.put("ds", "没有查到客户端软件数据");
			return result;
		}
		Integer cid = clientInfo.getInt("id");
		if(cid==null){
			result.put("co", "401");
			result.put("ds", "客户端软件ID为空");
			return result;
		}
		
		ClientCloudFormMap queryParams = new ClientCloudFormMap();
		queryParams.put("clientId", cid);
		queryParams.put("moduleVersion", moduleVersion);
		queryParams.put("moduleName", moduleName);
		ClientCloudFormMap clouds = clientUploadDataMapper.findCloudSingle(queryParams);
		if(clouds==null){
			result.put("co", "200");
			result.put("ds", "查询成功,无返回数据");
		}else{
			result.put("co", "200");
			result.put("ds", "查询成功");			
			result.put("cn", clouds.get("moduleName"));//	云翻译模块名称	
			result.put("cv",  clouds.get("moduleVersion"));//云翻译模块版本(新版本)
			Date cdt = clouds.getDate("releaseTime");	
			String cds = MyDateUtils.dateToStr(cdt,22);
			result.put("cd",  cds);//发布时间(新版本)
			result.put("ch",  clouds.get("md5Value"));//云翻译模块发布日志(新版本)
			result.put("cl",  clouds.get("releaseLog"));//云翻译模块发布日志(新版本)
			result.put("cen",  clouds.getInt("isAvailable"));//云翻译模块是否生效（默认为生效 (1,0)）
			result.put("cr",  clouds.get("ranking"));//云翻译模块排名(新版本)
			result.put("ct",  clouds.get("fileType"));//云翻译模块文件类型(值：ZIP或DLL(默认DLL，如果该字段不存在，则自动误别个DLL))
			result.put("cp",  clouds.get("fileName"));//云翻译模块名称(本地存储名称)
			result.put("cs",  clouds.get("size"));//云翻译模块文件大小（字节）
			
		}
		return result;
	}
    @ResponseBody
	@RequestMapping("uploadoldversion")
	public Map<String,Object> uploadOldVersion(@RequestBody CloudSimpleData coludDate) {
    	Map<String,Object> result = new HashMap<>();
    	String moduleVersion = coludDate.getCv();
    	String moduleName = coludDate.getCn();
    	if(StringUtils.isEmpty(moduleName)||StringUtils.isEmpty(moduleVersion)){
    		result.put("co", "402");
			result.put("ds", "云翻译模块名称和版本不能为空");
			return result;
    	}
		ClientSoftInfoFormMap params = new ClientSoftInfoFormMap();
		params.put("softIp", coludDate.getIp());
		params.put("softName", coludDate.getSn());
		params.put("userName", coludDate.getUn());		
		ClientSoftInfoFormMap clientInfo = clientUploadDataMapper.findClientInfo(params);
		if(clientInfo==null){
			result.put("co", "400");
			result.put("ds", "没有查到客户端软件数据");
			return result;
		}
		Integer cid = clientInfo.getInt("id");
		if(cid==null){
			result.put("co", "401");
			result.put("ds", "客户端软件ID为空");
			return result;
		}
		
		ClientCloudFormMap queryParams = new ClientCloudFormMap();
		queryParams.put("clientId", cid);
		queryParams.put("moduleName", moduleName);
		queryParams.put("isAvailable", coludDate.getCen());
		queryParams.put("moduleVersion", coludDate.getCv());
		queryParams.put("ranking", coludDate.getCr());
		ClientCloudFormMap clouds = clientUploadDataMapper.findCloudNew(queryParams);
		if(clouds==null){
			result.put("co", "200");
			result.put("ds", "查询成功,无返回数据");
		}else{
			result.put("co", "200");
			result.put("ds", "查询成功");
			result.put("cn", clouds.get("moduleName"));//	云翻译模块名称
			result.put("cv",  clouds.get("moduleVersion"));//云翻译模块版本(新版本)
			Date cdt = clouds.getDate("releaseTime");	
			String cds = MyDateUtils.dateToStr(cdt,22);
			result.put("cd",  cds);//发布时间(新版本)
			result.put("ch",  clouds.get("md5Value"));//云翻译模块发布日志(新版本)
			result.put("cl",  clouds.get("releaseLog"));//云翻译模块发布日志(新版本)
			result.put("cen",  clouds.getInt("isAvailable"));//云翻译模块是否生效（默认为生效 (1,0)）
			result.put("cr",  clouds.get("ranking"));//云翻译模块排名(新版本)
			result.put("ct",  clouds.get("fileType"));//云翻译模块文件类型(值：ZIP或DLL(默认DLL，如果该字段不存在，则自动误别个DLL))
			result.put("cp",  clouds.get("fileName"));//云翻译模块名称(本地存储名称)
			result.put("cs",  clouds.get("size"));//云翻译模块文件大小（字节）
		}
		return result;
	}
    @RequestMapping("/downloadcloud0")
    public ResponseEntity<byte[]> downLoadCloud(HttpServletRequest request,@RequestBody CloudSimpleData coludDate) throws Exception{    	
    	Map<String,Object> result = new HashMap<>();
    	ClientSoftInfoFormMap clientSoftInfo =queryClient(coludDate);
    	if(clientSoftInfo==null){
    		return null;
    	}
    	String moduleVersion = coludDate.getCv();
    	String moduleName = coludDate.getCn();
    	if(StringUtils.isEmpty(moduleName)||StringUtils.isEmpty(moduleVersion)){    	
			return null;
    	}
    	Integer cid = clientSoftInfo.getInt("id");
    	ClientCloudFormMap queryParams = new ClientCloudFormMap();
		queryParams.put("clientId", cid);
		queryParams.put("moduleName", moduleName);
		queryParams.put("moduleVersion", coludDate.getCv());
		ClientCloudFormMap clouds = clientUploadDataMapper.findCloudSingle(queryParams);		
    	String dPath = clouds.getStr("dataPath");
	    ServletContext servletContext = request.getServletContext();
	    String realPath = servletContext.getRealPath(dPath);
	    if(StringUtils.isEmpty(realPath)){
	    	return null;
	    }
	    String fileName = dPath.substring(dPath.lastIndexOf("/")+1, dPath.length());
	    logger.info("开始下载文件，文件名："+fileName);
	    //String realPath = servletContext.getRealPath("/WEB-INF/download/"+fileName);//得到文件所在位置
        InputStream in=new FileInputStream(new File(realPath));//将该文件加入到输入流之中
        logger.info("开始下载文件2，InputStream："+in);
         byte[] body=null;
         String bodyHStr = "{\"co\":\"200\",\"ds\":\"SUCCESS\",\"fg\":\"\",\"fg\":\"885ef994864051ad6b384637367a7f074d8f4960\",\"da\":\"";
         String bodyBStr = "\"}";
         byte[] bodyHByte = bodyHStr.getBytes();
         byte[] bodyBootByte = bodyBStr.getBytes();
         body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数         
         logger.info("开始下载文件3，body："+body);
         in.read(body);//读入到输入流里面 
         byte[] rsbody = ArrayUtils.addAll(bodyHByte,body );
         byte[] frsbody = ArrayUtils.addAll(rsbody,bodyBootByte );
         logger.info("开始下载文件4，in："+in);
        //fileName=new String(fileName.getBytes("gbk"),"iso8859-1");//防止中文乱码
        HttpHeaders headers=new HttpHeaders();//设置响应头
        logger.info("开始下载文件5，headers："+headers);
        headers.add("Content-Disposition", "attachment;filename="+fileName);
        logger.info("开始下载文件6，headers："+headers);
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        logger.info("开始下载文件7，statusCode："+statusCode);
        ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(frsbody, headers, statusCode);
        logger.info("开始下载文件7，ResponseEntity<byte[]>："+response);
        return response;

        //public ResponseEntity（T  body，
        //                       MultiValueMap < String，String > headers，
        //                       HttpStatus  statusCode）
        //HttpEntity使用给定的正文，标题和状态代码创建一个新的。
        //参数：
        //body - 实体机构
        //headers - 实体头
        //statusCode - 状态码
    }
    @ResponseBody
	@RequestMapping("list")
	public String list(Integer clientId,Model model) {		
    	List<ClientCloudFormMap> clientClouds = new ArrayList<>();
			if(clientId==null){
				model.addAttribute("clientClouds", clientClouds);				
			}else{
				clientClouds = clientUploadDataMapper.findCloudInfo(clientId);
				model.addAttribute("clientClouds", clientClouds);	
			}
			return Common.BACKGROUND_PATH + "/cloud/***";
	}
    /**
     * 
     * 返回云翻译模块的历史版本信息
	 * @param params
	 * @return
     */
    @ResponseBody
	@RequestMapping("listcloudversions")
	public List<CloudVersion> listCloudVersions(String moduleName,Integer cloudId,String moduleVersion) {	
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("cloudId", cloudId);
    	params.put("moduleName", moduleName);
    	params.put("moduleVersion", moduleVersion);
    	System.out.println("params:"+params);
    	return clientUploadDataMapper.findCloudVersion(params);    	
	}
    @ResponseBody
   	@RequestMapping("addcloudversion")
   	public String addCloudVersions(Integer moduleId,String moduleVersion,Date releaseTime,String md5Value,Integer fileType,String dataPath,Long size) {	    	
    	ClientCloudVersionFormMap params = new ClientCloudVersionFormMap();
       	params.put("moduleId", moduleId);
       	params.put("moduleVersion", moduleVersion);
       	params.put("releaseTime", releaseTime);
       	params.put("md5Value", md5Value);
       	params.put("fileType", fileType);
       	params.put("dataPath", dataPath);
       	params.put("size", size);       	
       	System.out.println("params:"+params);
       	int i = clientUploadDataMapper.insertCloudVersion(params);       	
       	return i>0?"success":"fail";
   	}
    /**
     * A03请求（新A001请求）
     * （A001）客户端软件用户认证
     * @param ClientCheckData
     * @return
     */
    @ResponseBody
   	@RequestMapping("checkclientuser")
   	public Map<String, Object> checkClientUser(@RequestBody ClientCheckData clientCheckData) {
    	Map<String,Object> result = new HashMap<>();
    	try{
        	String un = clientCheckData.getUn();
        	String uc = clientCheckData.getUc();
        	//String sn = clientCheckData.getSn();
        	//String at = clientCheckData.getAt();
        	//String wp = clientCheckData.getWp();
        	//String ip = clientCheckData.getIp();
        	String ll = clientCheckData.getLl();
        	String lc = clientCheckData.getLc();
        	String lt = clientCheckData.getLt();
        	//String tp = clientCheckData.getTp();
        	String bn = clientCheckData.getBn();
        	String ua = clientCheckData.getUa();
        	if(StringUtils.isEmpty(un)||StringUtils.isEmpty(uc)){
        		result.put("co", "401");
        		result.put("ds", "客户端用户认证失败-用户名和软件码不能为空");
        		return result;
        	}
        	StringBuffer msbody = new StringBuffer();
        	Map<String,Object> query = new HashMap<>();
        	query.put("clientSoftName",un);
        	query.put("clientSoftCode",uc);
        	Map<String,Object> params = new HashMap<>();
    		params.put("userName",un);
    		params.put("userCode",uc);
        	Map<String,Object> trans = clientUserSoftInfoMapper.findTrans(query);//查询译员信息
        	ClientUserSoftInfoFormMap softUser = clientUserSoftInfoMapper.findByCodeAndName(params); //查询软件用户信息
        	if(trans!=null){
        		if(softUser!=null){
        			//校验授权信息是否一致
        			String licenseLevel = String.valueOf(softUser.get("licenseLevel"));
        			String licenseCode = String.valueOf(softUser.get("licenseCode"));
        			String licenseTime = String.valueOf(softUser.get("licenseTime"));
        			if(StringUtils.isEmpty(ll)||StringUtils.isEmpty(lc)||StringUtils.isEmpty(lt)){
        				result.put("co", "400");
                		result.put("ds", "客户端用户没有授权信息，将由服务端已下发！");
                		//在此记录此客户端的信息，更新状态为，即将接受服务端授权信息的下发，此功能未开发                		
        			}else if(StringUtils.isEmpty(licenseLevel)||StringUtils.isEmpty(licenseCode)||StringUtils.isEmpty(licenseTime)){
        				//服务端没有软件用户的授权信息，通知管理员，更新授权信息
        				logger.info("请管理员添加软件授权信息");
        				result.put("co", "400");
                		result.put("ds", "客户端用户授权信息验证失败！请管理员添加软件授权信息");
        				//dosthing....
        			}else if(lc.equals(licenseCode)){//客户端提交的授权信息与服务端一致
        				//}else if(ll.equals(licenseLevel)&&lc.equals(licenseCode)&&lt.equals(licenseTime)){正式环境，请修改为此行......
        				result.put("co", "200");
                		result.put("ds", "客户端用户认证成功");
                		
                		//这里添加对账逻辑，income字段是充值金额
                		BigDecimal serverBalance = (BigDecimal)trans.get("wallet");//2, RoundingMode.HALF_UP
                		BigDecimal incomeBalance = (BigDecimal)trans.get("income");
                		BigDecimal clientBalance = new BigDecimal(0);
                		BigDecimal noUploadBalance = new BigDecimal(0);
                		
                		
                		if(bn!=null){
                			clientBalance = new BigDecimal(bn);
                		}
                		if(ua!=null){
                			noUploadBalance = new BigDecimal(ua);
                		}
                		if(serverBalance==null){
                			serverBalance = new BigDecimal(0);
                		}
                		if(incomeBalance==null){
                			incomeBalance = new BigDecimal(0);
                		}                		
                		if(serverBalance.compareTo(clientBalance.add(noUploadBalance))==0){
                			result.put("bn", serverBalance);
                			result.put("rc", incomeBalance);                			
                		}else if((serverBalance.add(incomeBalance)).compareTo(clientBalance.add(noUploadBalance))==0){
                			serverBalance = serverBalance.add(incomeBalance);
                			result.put("bn", serverBalance);
                			result.put("rc", incomeBalance);
                			//exec update sql  .....  ......  ...
                			
                		}else{
                			result.put("co", "300");
                    		result.put("ds", "客户端用户授权信息验证失败！译员账户余额与客户端不一致！");
                		}
                		
                		result.put("bn", serverBalance.setScale(2));
                		result.put("rc", incomeBalance.setScale(2));
                		msbody.append("译员昵称："+trans.get("nickname")+",译员级别："+trans.get("level")+",账户余额:"+serverBalance+",");
                		
                		//在此添加云翻译信息
                    	List<ClientCloudFormMap> clouds = clientUserSoftInfoMapper.findCloud(2);
                    	List<Map<String,Object>> rclouds = new ArrayList<>();
                    	for(ClientCloudFormMap i:clouds){
                    		Map<String,Object> r = new HashMap<>();
                    		String fileName = i.getStr("fileName");
                    		fileName = fileName.substring(0, fileName.lastIndexOf("."));
                    		r.put("cn",fileName);
                    		BigDecimal price = i.getBigDecimal("price");
                    		String priceStr = "";
                    		if(price!=null){
                    			priceStr = String.valueOf(price);
                    		}
                    		r.put("db", priceStr);
                    		r.put("pfkey", i.getStr("secretKey"));
                    		r.put("pfuc", i.getStr("secretCode"));
                    		rclouds.add(r);
                    	}
                    	result.put("ms", msbody);      
                    	result.put("ct", rclouds);
                    	result.put("ll", Common.nullToBlank(licenseLevel));
            			result.put("lc", Common.nullToBlank(licenseCode));
            			result.put("lt", Common.nullToBlank(licenseTime));
                		
        			}else{//客户端提交的授权信息与服务端不一致
        				result.put("co", "400");
                		result.put("ds", "客户端用户授权信息验证失败！");
        			}
        			
        		}else{//该译员不是软件用户，将初始化译员的客户端软件信息
        			int i = initSoftUser(clientCheckData);//保存软件用户信息   
            		if(i>0){
            			logger.info("保存客户端软件用户数据成功");
            			msbody.append("以为您创建成为客户端软件用户");
            			result.put("co", "200");
            		}else{
            			logger.info("保存客户端软件用户数据失败");
            			msbody.append("创建成为客户端软件用户失败，系统异常");
            			result.put("co", "402");
            		}
        		}
        	}else{//没有查到译员信息
        		if(softUser!=null){//该用户已经是是软件用户，引导其注册成为译员
        			msbody.append("您是客户端软件用户，请注册成为平台译员，并完善客户端软件的相关信息，详情登录官网http");
        			result.put("co", "400");
        			result.put("ms", msbody);
        			result.put("ds", "客户端用户授权信息验证失败！");
        		}else{//创建软件用户信息
            		int i = initSoftUser(clientCheckData);//保存软件用户信息   
            		if(i>0){
            			logger.info("保存客户端软件用户数据成功");
            			result.put("co", "400");
            			result.put("ms", "已经将您的信息保存，请联系管理员添加或核对软件授权信息");
            			result.put("ds", "客户端用户授权信息验证失败！");
            		}else{
            			logger.info("保存客户端软件用户数据失败");
            			msbody.append("创建成为客户端软件用户失败，系统异常");
            			result.put("co", "400");
            			result.put("ds", "客户端用户授权信息验证失败！");
            			result.put("ms",msbody);
            		}
        		}
        	}
        	
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return result;
   	}
    
    private int initSoftUser(ClientCheckData params){
    	ClientUserSoftInfoFormMap saveParams = new ClientUserSoftInfoFormMap();
		saveParams.put("userName", params.getUn());
		saveParams.put("userCode", params.getUc());
		saveParams.put("softName", params.getSn());
		saveParams.put("authTime", new Date());
		saveParams.put("os", params.getWp());
		saveParams.put("ip", params.getIp());
		saveParams.put("licenseLevel", params.getLl());
		saveParams.put("licenseCode", params.getLc());
		saveParams.put("licenseTime", params.getLt());
		saveParams.put("translatePair", params.getTp());  
		return  clientUserSoftInfoMapper.insertData(saveParams);
    }
    
    private ClientSoftInfoFormMap queryClient(CloudSimpleData coludDate){
    	Map<String,Object> result = new HashMap<>();
    	String moduleVersion = coludDate.getCv();
    	String moduleName = coludDate.getCn();
    	if(StringUtils.isEmpty(moduleName)||StringUtils.isEmpty(moduleVersion)){
			return null;
    	}
		ClientSoftInfoFormMap params = new ClientSoftInfoFormMap();
		params.put("softIp", coludDate.getIp());
		params.put("softName", coludDate.getSn());
		params.put("userName", coludDate.getUn());		
		ClientSoftInfoFormMap clientInfo = clientUploadDataMapper.findClientInfo(params);
		return clientInfo;
    }    
    private static void removeElement(List<Map<String,Object>> list){
    	for(Map<String,Object> m : list){
    		for (String key : m.keySet()) {
                if(key.equals("cont")){
                    m.remove(key);
                }
            }
    	}
    }
    private MoData findObject(String key,List<MoData> mos){
    	MoData rs = null;
    	for(MoData m:mos){
    		if(m.getCn().equals(key)){
    			rs = m;
    			break;
    		}
    	}
    	return rs;
    }
	public static void main(String args[]){
		//String runtime = "2020-09-23 12:23:56";
		try {//YYYY-MM-DD hh:mm:ss
			//Date runTime = DateUtils.parseDate(runtime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","yyyyMMdd"});
			//System.out.println(runTime);
			
			//String s = "，该电源电路包括一个供电电源、一个常规基准电压、用于驱动各种光源发光的驱动器和一个控制器，该控制器包括一个用于识别自我并对一个分配给控制器的输入信号做出反应可该变的地址，该控制器用于产生各种PFM的信号，每一个PFM信号相当于LED各种颜色中单独的一种颜色，每一个PFM信号指令一个独立的开关根据一个特定的相关的频率进行通断，这里所说的数据流包括不少于两种LED颜色的数据；本实用新型能准确调节彩色LED照明器颜色及其亮度，在轻负载和低电流得情况下，采用PFM控制驱动器的方式比PWM更具效率。";
			//System.out.println("文本长度："+s.length());	
			
			
			//Integer i = 0xfafafa;
			
			//System.out.println(i);
			//String realPath = "/WEB-INF/downloads/libaow.dll";
			//String fileName = realPath.substring(realPath.lastIndexOf("/")+1, realPath.length());
			//System.out.println(fileName);
			/*int a[] = {1,2,3,4,5};
			int b[] = {7,8,9};
			int c[] = {10,11,12,13};
			int rs[] = ArrayUtils.addAll(a,b);
			int rs2[] = ArrayUtils.addAll(rs, c);
			for(int i:rs2){
				System.out.println(i);
			}*/
			/*List<Map<String, Object>> maps = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.put("a", "1");
			map.put("cont", "2");
			Map<String, Object> map2 = new HashMap<>();
			map.put("a", "10");
			map.put("cont", "20");
			maps.add(map);
			maps.add(map2);
			removeElement(maps);
                
                
            System.out.println(maps);*/
			
			/*List<String> client = new ArrayList<>();
			client.add("A");
			client.add("B");
			client.add("C");
			client.add("F");
			client.add("G");
			client.add("C");
			
			List<String> server = new ArrayList<>();
			server.add("B");
			server.add("C");
			server.add("D");
			server.add("E");
			
			Set<String> JSET = new HashSet<>();
			for(String s:client){
				for(String s2:server){
					if(s.equals(s2)){
						JSET.add(s);
						continue;
					}					
				}
			}
			System.out.println("交集:"+JSET);
			
			Set<String> CSET = new HashSet<>();
			for(String s:client){
				int b = 0;
				for(String s2:JSET){
					if(s.equals(s2)){
						b++;
						continue;
					}					
				}
				if(b==0){
					CSET.add(s);
				}
			}
			System.out.println("客户端独有集:"+CSET);
			
			Set<String> SSET = new HashSet<>();
			for(String s:server){
				int b = 0;
				for(String s2:JSET){
					if(s.equals(s2)){
						b++;
						continue;
					}					
				}
				if(b==0){
					SSET.add(s);
				}
			}
			System.out.println("服务端独有集:"+SSET);*/
			
			/*BigDecimal a = new BigDecimal(99);
			BigDecimal b  = a.add(BigDecimal.valueOf(new Long(99l)));
			System.out.println(b.setScale(2));*/
			
			BigDecimal serverBalance = new BigDecimal("22.0000");
			BigDecimal clientBalance = new BigDecimal(22.0000);
			//BigDecimal noUploadBalance = new BigDecimal(0);
			
			
			
			if(serverBalance.compareTo(clientBalance)==0){
				System.out.println(1);
			}else{
				System.out.println(2);
			}
				
			
			
			/*System.out.println(serverBalance.add(new BigDecimal("1.00")));
			
			if(serverBalance.equals(clientBalance.add(noUploadBalance))){
				System.out.println(1);
			}else{
				System.out.println(0);
			}*/
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public boolean compare(String time1,Date time2){
		boolean rs = false;
		try{
			Date t1 = DateUtils.parseDate(time1, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","yyyyMMdd"});
			if(t1.getTime()<time2.getTime()){
				rs = true;
			}else{
				rs = false;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return rs;
	}
	
	
	
	
	
}
