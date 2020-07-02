package com.aiTrans.controller.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiTrans.entity.ClientCloudFormMap;
import com.aiTrans.entity.ClientSoftInfoFormMap;
import com.aiTrans.entity.ClientUploadDataFormMap;
import com.aiTrans.mapper.ClientUploadDataMapper;
import com.aiTrans.util.Common;
import com.aiTrans.util.MyDateUtils;
import com.aiTrans.vo.ClientData;
import com.aiTrans.vo.ClientUpdate;
import com.aiTrans.vo.CloudData;
import com.aiTrans.vo.CloudSimpleData;
import com.aiTrans.vo.MoData;

import org.apache.log4j.Logger;
@Controller
@RequestMapping("/cloud/client/")
public class ClientUploadDataController {
	@Inject
	private ClientUploadDataMapper clientUploadDataMapper;
	
	public final static Logger logger = Logger.getLogger(ClientUploadDataController.class);
	
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
			params.put("userName", clientData.getUn());
			params.put("softName", clientData.getSn());
			params.put("platform", clientData.getCn());
			params.put("source", clientData.getFr());
			params.put("target", clientData.getTo());
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
			int i = clientUploadDataMapper.insertClientUploadData(params);
			if(i>0){
				result.put("co", "200");
				if(original_text_len!=client_original_text_len){
					result.put("oc", original_text_len);
				}
				if(translation_text_len!=client_translation_text_len){
					result.put("dc", translation_text_len);
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
	
	@RequestMapping("/download")
    public ResponseEntity<byte[]> downLoad(HttpServletRequest request) throws Exception{
    	ClientSoftInfoFormMap dataResult = clientUploadDataMapper.findClientUploadInfo(null);
    	String dPath = dataResult.getStr("dataPath");
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
         String bodyHeader = "";
         String bodyBoot = "";
         
         body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
         logger.info("开始下载文件3，body："+body);
         in.read(body);//读入到输入流里面 
         logger.info("开始下载文件4，in："+in);
        //fileName=new String(fileName.getBytes("gbk"),"iso8859-1");//防止中文乱码
        HttpHeaders headers=new HttpHeaders();//设置响应头
        logger.info("开始下载文件5，headers："+headers);
        headers.add("Content-Disposition", "attachment;filename="+fileName);
        logger.info("开始下载文件6，headers："+headers);
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        logger.info("开始下载文件7，statusCode："+statusCode);
        ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(body, headers, statusCode);
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
	@RequestMapping("getcloudstatus")
	public Map<String,Object> getCloudStatus(@RequestBody CloudData coludDate) {
    	logger.info("传来的映射复杂json对象："+coludDate);
    	Map<String,Object> result = new HashMap<>();
		ClientSoftInfoFormMap params = new ClientSoftInfoFormMap();
		params.put("softIp", coludDate.getIp());
		params.put("softName", coludDate.getSn());
		params.put("userName", coludDate.getUn());
		List<MoData> moDatas = coludDate.getMo();
		ClientSoftInfoFormMap clientInfo = clientUploadDataMapper.findClientInfo(params);
		if(clientInfo==null){
			result.put("co", "400");
			result.put("ds", "没有查到客户端软件数据");
			return result;
		}
		Integer cid = clientInfo.getInt("id");
		List<ClientCloudFormMap> clouds = clientUploadDataMapper.findCloudInfo(cid);
		result.put("co", "200");
		result.put("ds", "查询成功");

		result.put("sn", clientInfo.get("softName"));
		result.put("un", clientInfo.get("userName"));
		result.put("ip", clientInfo.get("softIp"));
		
		List<Map<String,Object>> moList = new ArrayList<>();
		for(ClientCloudFormMap c:clouds){
			Map<String,Object> mo = new HashMap<String,Object>();	
			mo.put("cn", c.get("moduleName"));
			mo.put("cv", c.get("moduleVersion"));
			Date cdt = c.getDate("releaseTime");	
			String cds = MyDateUtils.dateToStr(cdt,22);
			mo.put("cd", cds);
			mo.put("cen", c.get("isAvailable"));
			mo.put("cr", c.get("ranking"));
			mo.put("op", "81");
			moList.add(mo);
		}
		try{
			setNewUpdateStatus(moList,moDatas);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		result.put("mo", moList);
		
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
		}
		return result;
	}
    @RequestMapping("/downloadcloud")
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
         String bodyHStr = "{\"co\":\"200\",\"ds\":\"SUCCESS\",\"da\":\"";
         String bodyBStr = "\"}";
         byte[] bodyHByte = bodyHStr.getBytes();
         byte[] bodyBootByte = bodyBStr.getBytes();        
         body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
         byte[] rsbody = ArrayUtils.addAll(bodyHByte,body );
         byte[] frsbody = ArrayUtils.addAll(rsbody,bodyBootByte );
         logger.info("开始下载文件3，body："+body);
         in.read(body);//读入到输入流里面 
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
			int a[] = {1,2,3,4,5};
			int b[] = {7,8,9};
			int c[] = {10,11,12,13};
			int rs[] = ArrayUtils.addAll(a,b);
			int rs2[] = ArrayUtils.addAll(rs, c);
			for(int i:rs2){
				System.out.println(i);
			}
			
			
			
			
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
