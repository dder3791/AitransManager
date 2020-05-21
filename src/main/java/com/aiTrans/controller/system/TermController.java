package com.aiTrans.controller.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AuditorFormMap;
import com.aiTrans.entity.DomainFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.TermTableFormMap;
import com.aiTrans.entity.TermTableListFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.DomainMapper;
import com.aiTrans.mapper.ProofreaderMapper;
import com.aiTrans.mapper.TermTableMapper;
import com.aiTrans.mapper.TermTableListMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * TermController术语管理
 * @author numberONe 2014-11-19
 * @version 3.0v
 * @param <MagazineBean>
 */
@Controller
@RequestMapping("/term/")
public class TermController extends BaseController {
	@Inject
	private TermTableListMapper termTableListMapper;
	//2017年4月28日11:04:43
//	@Inject
//	private TermNameMapper termNameMapper;
	@Inject
	private TermTableMapper termTableMapper;
	@Inject
	private ProofreaderMapper proofreaderMapper;
	//2017年4月24日16:08:25
	@Inject
	private DomainMapper domainMapper;
	
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	
	//2017年4月14日17:07:40
	private String oriLanguage;
	private String id;
	private String domainId;
	private String domain;
	//2017年4月14日17:15:37 将request放到方法中
	/*public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}*/
	
	/**
	 * 展示的是该权限用户在此功能中的按钮资源
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	//2017年4月14日17:08:41
	@RequestMapping("list")
	public String list(Model model) throws Exception {
		
		model.addAttribute("res", findByRes());
		id = getPara("id");
		if("71".equals(id)){
			oriLanguage="EN";
		}
		else if("72".equals(id)){
			oriLanguage="JP";
		}else if("73".equals(id)){
			oriLanguage="KOR";
		}else if("74".equals(id)){
			oriLanguage="GER";
		}else if("75".equals(id)){
			oriLanguage="FR";
		}
		return Common.BACKGROUND_PATH + "/system/term/list_term_first";
		
	}
	
	//2017-4-17 11:58:57
	//二级列表入口
	@RequestMapping("look_term")
	public String look_term(Model model) throws Exception {
		String id = getPara("termId");
//		model.addAttribute("res", findByRes());
		/*2017年4月24日17:22:48
		 * 2017年4月28日16:26:59注掉下面两行
		 * 2017年5月2日10:56:06 去掉注释
		 */
		model.addAttribute("id",id);
		String termName = termTableListMapper.lookTermNameById(id);
	     model.addAttribute("termName",termName);
		/*String name = look_termName_by_id(id);
		model.addAttribute("term",termTableListMapper.findByAttribute("name", name,TermTableListFormMap.class));
		System.out.println("-----------------------------------"+termTableListMapper.findByAttribute("name", name,TermTableListFormMap.class));*/
		return Common.BACKGROUND_PATH + "/system/term/look_term";
		
	}
	
	
	//2017年5月2日11:00:23
	@ResponseBody
	@RequestMapping("find_term_by_page")
	public PageView find_term_by_page(String pageNow, String pageSize,
			String column, String sort) throws Exception {
		id=getPara("id");
		TermTableFormMap termDfileFormMap = getFormMap(TermTableFormMap.class);
		termDfileFormMap = toFormMap(termDfileFormMap, pageNow, pageSize,
				termDfileFormMap.getStr("orderby"));
		termDfileFormMap.put("column", column);
		termDfileFormMap.put("sort", sort);
		termDfileFormMap.put("termId", id);
		String name = look_termName_by_id(id);
		// 2017年4月24日17:15:35
		pageView.setRecords(termTableMapper.findterms(termDfileFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	/***
	 * 根据语言查找相对应的校对员
	 */
	@ResponseBody
	@RequestMapping("findProofreadlanguage")
	public List<ProofreaderFormMap> findProofreadlanguage(Model model){
		TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
		termTableListFormMap.put("oriLanguage", oriLanguage);
		termTableListFormMap.put("domain", domain);
		List<ProofreaderFormMap> proofreaderFormMapList=termTableListMapper.findProofreadLanguage(termTableListFormMap);
		return proofreaderFormMapList;
	}
	/***
	 *根据语言查询相对应的审核员
	 */
	@ResponseBody
	@RequestMapping("findAuditerLanguage")
	public List<AuditorFormMap> findlanguages(Model model){
		TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
		termTableListFormMap.put("oriLanguage", oriLanguage);
		List<AuditorFormMap> AuditorFormMapList=termTableListMapper.findAuditerLanguage(termTableListFormMap);
		return AuditorFormMapList;
	}
	/***
	 * 根据语言查询相对应得译员
	 */
	@ResponseBody
	@RequestMapping("findTranslatorLanguage")
	public List<TranslatorFormMap> findTranslatorLanguage(Model model){
		TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
		String domainId=getPara("domainId");
		System.out.println(domainId);
		termTableListFormMap.put("oriLanguage", oriLanguage);
		termTableListFormMap.put("domain", domainId);
		List<TranslatorFormMap> TranslatorFormMapList=termTableListMapper.findTranslatorLanguage(termTableListFormMap);
		return TranslatorFormMapList;
	}
	//2017年4月28日16:26:05
	/*@ResponseBody
	@RequestMapping("find_term_by_page")
	public PageView find_term_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TermTableListFormMap termFormMap = getFormMap(TermTableListFormMap.class);
		termFormMap=toFormMap(termFormMap, pageNow, pageSize,termFormMap.getStr("orderby"));
		termFormMap.put("column", column);
		termFormMap.put("sort", sort);
		//2017年4月24日17:15:35
		termFormMap.put("termNameId", id);
		pageView.setRecords(termTableListMapper.findTermPage(termFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}*/
	
	/**
	 * 通过术语id查找术语名称
	 * @param termId
	 * @return
	 */
	public String look_termName_by_id(String termId){
		return termTableListMapper.lookTermNameById(termId);
	}
	
	//2017年4月28日10:27:18	
	/*@ResponseBody
	@RequestMapping("find_term_first_by_page")
	public PageView find_term_first_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TermNameFormMap termNameFormMap = getFormMap(TermNameFormMap.class);
		termNameFormMap=toFormMap(termNameFormMap, pageNow, pageSize,termNameFormMap.getStr("orderby"));
		
		//2017年4月17日09:55:42
		termNameFormMap.put("column", column);
		termNameFormMap.put("sort", sort);
		termNameFormMap.put("oriLanguage", oriLanguage);
		for (String key : termNameFormMap.keySet()) {
			System.out.println("key= "+ key + " and value= " + termNameFormMap.get(key));
		}
		List<TermNameFormMap> termInfo = termNameMapper.findFirstPage(termNameFormMap);
		for (Object object : termInfo) {
			System.out.println(object);
		}
		pageView.setRecords(termNameMapper.findFirstPage(termNameFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}*/
	//主表查看信息
	@ResponseBody
	@RequestMapping("find_term_first_by_page")
	public PageView find_term_first_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		TermTableListFormMap termFormMap = getFormMap(TermTableListFormMap.class);
		termFormMap=toFormMap(termFormMap, pageNow, pageSize,termFormMap.getStr("orderby"));
		
		//2017年4月17日09:55:42
		termFormMap.put("column", column);
		termFormMap.put("sort", sort);
		termFormMap.put("oriLanguage", oriLanguage);
		for (String key : termFormMap.keySet()) {
			   System.out.println("key= "+ key + " and value= " + termFormMap.get(key));
			  }
		pageView.setRecords(termTableListMapper.findFirstPage(termFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	
	
	@RequestMapping("addUI_1")
	public String addUI_1(Model model) throws Exception {
		/*2017年4月24日18:54:13
		 * 2017年4月28日16:37:57 注掉下面两行
		 */
//		String name = getPara("name");
//		model.addAttribute("termName",name);
	    model.addAttribute("oriLanguage", oriLanguage);
	    model.addAttribute("domain", termTableListMapper.findDomain());
		return Common.BACKGROUND_PATH + "/system/term/addTermNameD";
	}

	
	@ResponseBody
	@RequestMapping("addTermNameD")
	@SystemLog(module="术语管理",methods="术语管理-新增术语")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addTermNameD(HttpServletRequest request){
		try {
        TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
        String domainName=getPara("domainId");
        termTableListFormMap.put("domainName", domainName);
        //根据领域名字查找id存入数据库
        int domainId=termTableListMapper.findDomainName(termTableListFormMap);
        termTableListFormMap.put("domainId", domainId);
        termTableListFormMap.put("oriLanguage", oriLanguage);
			String proofreaderId = getPara("proofreaderId");
			  if(proofreaderId!=null){
				  termTableListFormMap.put("proofreaderId",proofreaderId);
				}else{
					termTableListFormMap.put("proofreaderId",termTableListMapper.findProofreadnickname(termTableListFormMap));
				}
			  String auditorId = getPara("auditorId");
			  if(auditorId!=null){
				  termTableListFormMap.put("auditorId",auditorId);
				}else{
					/*termTableListFormMap.put("auditorId","89");*/
					termTableListFormMap.put("auditorId",termTableListMapper.findAuditernickname(termTableListFormMap));
				}
			termTableListMapper.addEntity(termTableListFormMap);// 新增后返回新增信息
		} catch (Exception e) {
			 throw new SystemException("添加术语名称异常");
		}
		return "success";
	}
	
	@RequestMapping("upload1")
	public String upload1(Model model,HttpServletRequest request) throws Exception {
		/*2017年4月24日18:54:13
		 * 2017年4月28日16:37:57 注掉下面两行
		 */
//		String name = getPara("name");
//		model.addAttribute("termName",name);
		id = getPara("id");
		String termName=request.getParameter("termName");
		model.addAttribute("termName", termName);
		model.addAttribute("domain", termTableListMapper.findDomain());
		DomainFormMap domainFormMap = getFormMap(DomainFormMap.class);
		if(Common.isNotEmpty(id)){
			model.addAttribute("term", termTableListMapper.findbyFrist("id", id, TermTableListFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/term/addTermuploadD";
	}
	/***
	 * 术语多文件上传
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("uploadTerm1")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-上传术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String uploadTerm1(@RequestParam("file") CommonsMultipartFile files[],
			Model model,HttpServletResponse response,HttpServletRequest request
			) throws Exception{
		TermTableFormMap termTableFormMap=findHasHMap(TermTableFormMap.class);
		    TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
		    termTableListFormMap.put("id", id);
		    domainId=request.getParameter("domainId");
		    termTableListFormMap.put("oriLanguage", oriLanguage);
			if(oriLanguage=="JP"){
				oriLanguage="日语术语";
			}
			if(oriLanguage=="EN"){
				oriLanguage="英语术语";
			}
		    if(oriLanguage=="KOR"){
		    	oriLanguage="韩语术语";
		    }
		    if(oriLanguage=="GER"){
		    	oriLanguage="德语术语";
		    }	
		    if(oriLanguage=="FR"){
		    	oriLanguage="法语术语";
		    }	
			//.获取当前文件的根目录，并查找"/upload/images"路径
			/*String path = request.getSession().getServletContext()
					.getRealPath("/upload/images");*/
			String path="C:/Aitrans/"+oriLanguage+"/";
			
			//创建path目录下的文件对象
			File saveDir = new File(path);
			//判断文件是否存在
			if(!saveDir.exists()){
				//	文件不存在，则创建多级目录来创建这个文件	
				saveDir.mkdirs();
			}
			
			StringBuffer sb=new StringBuffer();
			for (int i = 0; i < files.length; i++) {
				 
			// 获取这个文件的传过来的名字
			String fileName = files[i].getOriginalFilename();
			System.out.println("fileName---------->" + files[i].getOriginalFilename());  
			String newFileName=fileName.substring(0, fileName.indexOf("."));
			String fileEit = fileName.substring(fileName.indexOf(".") + 1).toLowerCase().trim();
			
			System.out.println(fileEit);
		   //重新编辑这个文件的名字以日期组合，让它不会重复。
		    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		    newFileName = df.format(new Date()) +newFileName+"."+fileEit;
		    String saxpath=path+newFileName;
		    if(sb.length()>0){
		    	sb.append("&").append(path).append(newFileName);
		    }else{
		    	sb.append(path).append(newFileName);
		    }
		    if (!files[i].isEmpty()) { 
	            try {  
	                FileOutputStream fos = new FileOutputStream(path+  
	                         newFileName);  
	                InputStream in = files[i].getInputStream();  
       //    定义一个变量来判断是否读取完毕
	                int len = 0;  
	                while ((len = in.read()) != -1) {  
	                    fos.write(len);  
	                }  
	                fos.flush();
	    			fos.close();  
	    			in.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	                throw new SystemException("上传文件异常");
	            }  
		    }
		    if("tmx".equals(fileEit)){
				SAXparserUtil(saxpath);
			}else if("txt".equals(fileEit)){
			    uploadTxt(saxpath);
		    }else if("doc".equals(fileEit)){
		    	uploadWord(saxpath);
		    }else if("xls".equals(fileEit)){
		    	uploadExcel(saxpath);
		    }
		    }
			return "success";
		} 
	 
	
	
	
	/***
	 * 导入txt文件存入数据库中	
	 * @param saxpath
	 */
	public void uploadTxt(String saxpath) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
    	String accountNames=(String) userFormMap.get("accountName");
		try{	
		 String encoding="UTF-8";
         File file=new File(saxpath);
         if(file.isFile() && file.exists()){ //判断文件是否存在
             InputStreamReader read = new InputStreamReader(
             new FileInputStream(file),encoding);//考虑到编码格式
             BufferedReader bufferedReader = new BufferedReader(read);
             String lineTxt = null;
             String finalName=null;
             TermTableFormMap termTableFormMap=null;
             String source="上传文件";
             int i = 2;
             while((lineTxt = bufferedReader.readLine()) != null){
            	 if (lineTxt != null && !lineTxt.equals("")){
            	 if(lineTxt.length()>0){
                //截取中英文最后标点符号
            	finalName=lineTxt.substring(lineTxt.length()-1);
            	System.out.println(finalName);
            	System.out.println(lineTxt);
            	System.out.println(finalName.equals("．"));
            	//判断中文标点符号
            	if(finalName.equals("。") || finalName.equals("，")||finalName.equals("．")){
            		if(i%2==0){
            			termTableFormMap = new TermTableFormMap();
            			termTableFormMap.put("TermOrigin",lineTxt);
            		}
            		else{
            			termTableFormMap.put("TermTarget",lineTxt);
            			termTableMapper.savefile(termTableFormMap);
            		}
            		i++;
            	}
            	//判断英文标点符号
            	if(finalName.equals(".") || finalName.equals(",") ){
            		if(i%2==0){
            			termTableFormMap = new TermTableFormMap();
            			termTableFormMap.put("TermOrigin",lineTxt);
            			termTableFormMap.put("Uploader",accountNames+source);
            			termTableFormMap.put("TermId", id);
            			termTableFormMap.put("User_UC",1 );
            			termTableFormMap.put("DomainId",domainId );
 					    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
 					    String date1=sdf.format(new Date());
 					    termTableFormMap.put("UploadTime",date1);
             	 
            		}else{
            			termTableFormMap.put("TermTarget",lineTxt);
            			termTableFormMap.put("Uploader",accountNames+source);
            			termTableFormMap.put("TermId", id);
            			termTableFormMap.put("User_UC",1 );
            			termTableFormMap.put("DomainId",domainId );
         				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
         				String date1=sdf.format(new Date());
         				termTableFormMap.put("UploadTime",date1);
         				termTableMapper.savefile(termTableFormMap);
                     }
            		i++;
            		}
            	}	 
             }
          
          }
             read.close();
         }else{
             System.out.println("找不到指定的文件");
         }
         } catch (Exception e) {
             System.out.println("读取文件内容出错");
             e.printStackTrace();
         }
	}
	
	
	/***
	 * Excel导入数据库
	 * @param saxPath
	 */
	public void uploadExcel(String saxPath){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
    	String accountNames=(String) userFormMap.get("accountName");
    	TermTableFormMap  TermTableFormMap=null;
		try {
			InputStream is = new FileInputStream(saxPath); 
			Workbook rwb=Workbook.getWorkbook(is);
			 String[] str = rwb.getSheetNames();
			 String source="上传文件";
			 for (int i = 0; i < str.length; i++) {
				 Sheet st = rwb.getSheet(str[i]);  
				 int rs=st.getColumns();  
				 int rows=st.getRows();  
				 System.out.println("列数===>"+rs+"行数："+rows);
			
			     for(int k=0;k<rows;k++){//行  
			          
			        Cell c00 = st.getCell(0,k);  
			        Cell c01 = st.getCell(1,k); 
			    //通用的获取cell值的方式,返回字符串  
			     String strc00 = c00.getContents();  
			     String strc01 = c01.getContents();
			 
			  TermTableFormMap = getFormMap(TermTableFormMap.class);
			  TermTableFormMap.put("TermOrigin",strc00);
			  TermTableFormMap.put("TermTarget",strc01);
			  TermTableFormMap.put("Uploader",accountNames+source);
			  TermTableFormMap.put("TermId", id);
			  TermTableFormMap.put("User_UC",1 );
			  TermTableFormMap.put("DomainId",domainId );
			  termTableMapper.savefile(TermTableFormMap);
			 	}	
          }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/***
	 * 导入word到数据库
	 * @param saxPath
	 */
	public void uploadWord(String saxPath){
		  try{
			  HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			  UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
		      String accountNames=(String) userFormMap.get("accountName");
		      String source="上传文件";
		      TermTableFormMap termTableFormMap=null;
		      FileInputStream in = new FileInputStream(saxPath);//载入文档
		     POIFSFileSystem pfs = new POIFSFileSystem(in);   
		      HWPFDocument hwpf = new HWPFDocument(pfs);   
		      int h=0;
		      Range range = hwpf.getRange();//得到文档的读取范围
		      TableIterator it = new TableIterator(range);
		    	  System.out.println(it.equals(""));
		      System.out.println(it.toString().length());
		      if(it.hasNext()){
		     //迭代文档中的表格
		      while (it.hasNext()) {   
		          Table tb = (Table) it.next();   
		          System.out.println(tb.toString().length());
		          //迭代行，默认从0开始
		          for (int i = 0; i < tb.numRows(); i++) {   
		              TableRow tr = tb.getRow(i);   
		              //迭代列，默认从0开始
		              for (int j = 0; j < tr.numCells(); j++) {   
		                  TableCell td = tr.getCell(j);//取得单元格
		                  //取得单元格的内容
		                  for(int k=0;k<td.numParagraphs();k++){   
		                      Paragraph para =td.getParagraph(k);   
		                      String s = para.text();  
		                      String finalName=s.replace("", "");
		                      System.out.println(finalName);
		                      if(h==0){
		                    	  termTableFormMap = getFormMap(TermTableFormMap.class);
		                    	  termTableFormMap.put("TermOrigin",finalName);
		                      }
		                      if(h==1){
		                    	  termTableFormMap.put("TermTarget",finalName);
		                    	  termTableFormMap.put("Uploader",accountNames+source);
		                    	  termTableFormMap.put("TermId", id);
		                    	  termTableFormMap.put("User_UC",1 );
		                    	  termTableFormMap.put("DomainId",domainId );
		       					termTableMapper.savefile(termTableFormMap);
		                      }
		                      if(h%2==0 && h>1){
		                    	  termTableFormMap.put("TermOrigin",finalName );
		  					 }
		                      if(h%2!=0 && h>1){
		                    	  termTableFormMap.put("TermTarget",finalName);
		                    	  termTableFormMap.put("Uploader",accountNames+source);
		                    	  termTableFormMap.put("TermId", id);
		                    	  termTableFormMap.put("User_UC",1 );
		                    	  termTableFormMap.put("DomainId",domainId );
			       				  termTableMapper.savefile(termTableFormMap);
		                      }
		                      h++;
		                  } //end for    
		              }   //end for
		          }   //end for
		      } //end while
		      }//end if
		      else {
		    	  InputStream is = new FileInputStream(new File(saxPath));
		    	               WordExtractor ex = new WordExtractor(is);
		    	               String lineWord=ex.getText();
		    	               System.out.println(lineWord);
		    	               int a=0;
		    	              userFormMap = (UserFormMap) Common.findUserSession(request);
		    	 		      accountNames=(String) userFormMap.get("accountName");
		    	               String source1="上传文件";
		    	               String[] ff=lineWord.split("\r\n");
		    	               for (int i = 0; i < ff.length; i++) {
		    	            	   System.out.println(ff[i]);
		    	            	  String TermOrigin=ff[i].substring(0, ff[i].indexOf("^"));
		    	            	  String TermTarget=ff[i].substring(ff[i].indexOf("^")+1, ff[i].length());
		    	            	  termTableFormMap = getFormMap(TermTableFormMap.class);
		    	            	  termTableFormMap.put("TermOrigin",TermOrigin);
		    	            	  termTableFormMap.put("TermTarget",TermTarget);
		    	            	  termTableFormMap.put("Uploader",accountNames+source1);
		    	            	  termTableFormMap.put("TermId", id);
		    	            	  termTableFormMap.put("User_UC",1 );
		    	            	  termTableFormMap.put("DomainId",domainId );
			    	              SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    	              String date1=sdf.format(new Date());
			    	              termTableFormMap.put("UploadTime",date1);
			    	              termTableMapper.savefile(termTableFormMap);
		    	           }
		    		} 
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		 }//end method
    /***
     * 运用SAX解析器对TMX文件进行解析
     * @param
     */
	 public void SAXparserUtil(String saxpath){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		 
         File deskFile=new File(saxpath);
         SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
			try {
				SAXParser saxparser = saxParserFactory.newSAXParser();
				try {
					saxparser.parse(deskFile, new SAXDefaultHandler());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (org.xml.sax.SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.xml.sax.SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 }
	public  class SAXDefaultHandler extends DefaultHandler{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String name=request.getParameter("name");
		TermTableFormMap termTableFormMap=findHasHMap(TermTableFormMap.class);
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
    	String accountNames=(String) userFormMap.get("accountName");
     	private List<String>  translates;
     	
     	private String parent;
     	
    @Override
 public void startDocument() {
 	   this.translates=new ArrayList<>();
 }
    
  @Override
 		public void endDocument() {
	  TermTableFormMap termDfileFormMap =null;
 	      for (int i = 0; i < translates.size(); i++) {
 	    	if((i+=2%2)==0){  
 	    		termTableFormMap = new TermTableFormMap();
 	    		}
				if(i==0){
					termTableFormMap.put("TermTarget",translates.get(i) );
				}
				if(i==1){
					String source="上传文件";
					termTableFormMap.put("Uploader",accountNames+source);
					termTableFormMap.put("TermId", id);
					
					termTableFormMap.put("User_UC",1 );
					termTableFormMap.put("DomainId",domainId );
					
					termTableFormMap.put("TermOrigin",translates.get(i) );
					
					try {
						termTableMapper.savefile(termTableFormMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(i%2==0 && i>1){
					termTableFormMap.put("TermTarget",translates.get(i) );
				}
				if(i%2!=0 && i>1){
					String source="上传文件";
					termTableFormMap.put("Uploader",accountNames+source);
					termTableFormMap.put("TermId", id);
					termTableFormMap.put("User_UC",1 );
					termTableFormMap.put("DomainId",domainId );
					termTableFormMap.put("TermOrigin",translates.get(i) );
					try {
						termTableMapper.savefile(termTableFormMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("导入数据库异常");
					}
				}
				
 	      }
     }  
  
  @Override
 		public void startElement(String uri, String localName,
 				String qName, Attributes attributes) {
 	            this.parent=qName;
 	         if("tuv".equals(qName)){
 	        	String seg= attributes.getValue("seg");
 	         }
 		}
  
  @Override
 		public void endElement(String uri, String localName,
 				String qName) {
 	             this.parent=null;
 	             if("tuv".equals(qName)){
 	            
 	             }
 		}
  @Override
 		public void characters(char[] ch, int start, int length) {
 	             String data=new String(ch,start,length);
 	             if("seg".equals(this.parent)){
 	            	 translates.add(data);
 	            
 	            	 
 	             }
 		}
}
	 
	
	/**
	 * 验证账号是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(String name) {
		boolean flag=false;
		String type=getPara("type");
		TermTableListFormMap account = termTableListMapper.findbyFrist("termName", name, TermTableListFormMap.class);
		if("add".equals(type)){
			if (account == null) {
				return true;
			} else {
				return false;
			}
		}
		if("edit".equals(type)){
			String id=getPara("id");
			TermTableListFormMap Name = termTableListMapper.findbyFrist("termName", name, TermTableListFormMap.class);
			TermTableListFormMap termName = termTableListMapper.findbyFrist("id", id, TermTableListFormMap.class);
		   if ( Name==null || name.equals(termName.get("termName"))) {
				return true;
			} else {
				return false;
			}
		}
		return flag;
	}
	/*分表后的代码
	@ResponseBody
	@RequestMapping("addTermNameD")
	@SystemLog(module="术语管理",methods="术语管理-新增术语")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addTermNameD(){
		try {
			TermNameFormMap termNameFormMap = getFormMap(TermNameFormMap.class);
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
			System.out.println("=================================");
			String[] param = request.getParameterValues("termNameFormMap.domain");
			String domain = Arrays.toString(param);
			domain = domain.substring(1, domain.length()-1);
			domain = domain.replace(" ", "");
			termNameFormMap.put("domain",domain);
			System.out.println("------------TermController.1-----------------"+domain);
			System.out.println("------------TermController.2-----------------"+termNameFormMap);
			termNameMapper.addEntity(termNameFormMap);//新增后返回新增信息
		} catch (Exception e) {
			 throw new SystemException("添加术语名称异常");
		}
		return "success";
	}*/
	  /**
	   * 导出术语一级列表Excel格式文件
	   * @param request
	   * @param response
	   * @throws IOException
	   */
	   @RequestMapping("/export")
	   public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
			TermTableListFormMap termTableListFormMap = findHasHMap(TermTableListFormMap.class);
			termTableListFormMap.put("oriLanguage", oriLanguage);
			if(oriLanguage=="JP"){
				oriLanguage="日语术语";
			}
			if(oriLanguage=="EN"){
				oriLanguage="英语术语";
			}
		    if(oriLanguage=="KOR"){
		    	oriLanguage="韩语术语";
		    }
		    if(oriLanguage=="GER"){
		    	oriLanguage="德语术语";
		    }	
		    if(oriLanguage=="FR"){
		    	oriLanguage="法语术语";
		    }
		    String fileName = URLEncoder.encode(oriLanguage+"列表","UTF-8");
			//exportData = 
			// [{"colkey":"sql_info","name":"SQL语句","hide":false},
			// {"colkey":"total_time","name":"总响应时长","hide":false},
			// {"colkey":"avg_time","name":"平均响应时长","hide":false},
			// {"colkey":"record_time","name":"记录时间","hide":false},
			// {"colkey":"call_count","name":"请求次数","hide":false}
			// ]
			String exportData = termTableListFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<TermTableListFormMap> lis = termTableListMapper.findFirstPage(termTableListFormMap);
			for (TermTableListFormMap termTableList : lis) {
				int isProofread =(int) termTableList.get("isProofread");
				if(isProofread == 0){
					termTableList.put("isProofread", "只翻译");
				}else if(isProofread == 1){
					termTableList.put("isProofread", "翻译和校对");
				}else if(isProofread == 2){
					termTableList.put("isProofread","翻译、校对及审核");
				} 
			}
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	   
	   /***
	    * 导出术语二级列表Excel
	    * @param request
	    * @param response
	    * @throws IOException
	    */
		 @RequestMapping("/export2")
			public void export2(HttpServletRequest request, HttpServletResponse response) throws IOException {
			 TermTableFormMap termDfileFormMap = findHasHMap(TermTableFormMap.class);
			    String name = look_termName_by_id(id);
				termDfileFormMap.put("termId", id);
			    String fileName = URLEncoder.encode(name+"列表","UTF-8");
				String exportData = termDfileFormMap.getStr("exportData");// 列表头的json字符串

				List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

				List<TermTableFormMap> lis = termTableMapper.queryByAll(termDfileFormMap);
				POIUtils.exportToExcel(response, listMap, lis, fileName);
			}
    /**
     * 查看详情添加
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("addUI_2")
	public String addUI_2(Model model,HttpServletRequest request) throws Exception {
		String id=getPara("id");
		model.addAttribute("id", id);
		model.addAttribute("domain", termTableListMapper.findDomain());
		
		return Common.BACKGROUND_PATH + "/system/term/addTermD";
	}
	
	@ResponseBody
	@RequestMapping("addTermD")
	@SystemLog(module="术语管理",methods="术语管理-新增术语")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addTermD(HttpServletRequest request){
		try {
			TermTableFormMap termTableFormMap = new TermTableFormMap();
            /*String id=getPara("id");*/
			UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
	    	String accountNames=(String) userFormMap.get("accountName");
			String id=request.getParameter("id");
			termTableFormMap.put("TermOrigin", request.getParameter("TermOrigin"));
			termTableFormMap.put("TermTarget", request.getParameter("TermTarget"));
			String source="上传文件";
			termTableFormMap.put("Uploader", accountNames+source);
			termTableFormMap.put("TermId", id);
			termTableFormMap.put("DomainId", request.getParameter("domainId"));
			termTableFormMap.put("User_UC",request.getParameter("User_UC"));
			termTableMapper.savefile(termTableFormMap);// 新增后返回新增信息
		} catch (Exception e) {
			 throw new SystemException("添加术语异常");
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-删除术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			termTableMapper.deleteByAttribute("id", id, TermTableFormMap.class);
		}
		return "success";
	}
     
	
	@ResponseBody
	@RequestMapping("deleteEntitys")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-删除术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntitys() throws Exception {
		TermTableListFormMap termTableListFormMap = new TermTableListFormMap();
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			termTableListFormMap.put("id", id);
			int findTermId=termTableListMapper.findForeignKey(termTableListFormMap);
			if(findTermId >0){
			  return "failed";
			}else {
			termTableListMapper.deleteByAttribute("id", id, TermTableListFormMap.class);
			  return "success";
			}
		}
		return "success";
	}
	//编辑一级列表
	@RequestMapping("editUI_1")
	public String editUI_1(Model model) throws Exception {
		id = getPara("id");
		TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
		DomainFormMap domainFormMap = getFormMap(DomainFormMap.class);
		model.addAttribute("domain", termTableListMapper.findDomain());
		termTableListFormMap.put("oriLanguage", oriLanguage);
		List<ProofreaderFormMap> list = termTableListMapper.findProofreadLanguage(termTableListFormMap);
		model.addAttribute("proofreadList", list);
		model.addAttribute("auditerList",termTableListMapper.findAuditerLanguage(termTableListFormMap));
		model.addAttribute("translatorList",termTableListMapper.findTranslatorLanguage(termTableListFormMap));
		/*model.addAttribute("nickname", termTableListDMapper.findproofreadD());*/
		if(Common.isNotEmpty(id)){
			model.addAttribute("term", termTableListMapper.findbyFrist("id", id, TermTableListFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/term/editTermNameD";
	}

	@ResponseBody
	@RequestMapping("editTermNameD")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-修改术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editTermNameD() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		TermTableListFormMap termTableListFormMap = getFormMap(TermTableListFormMap.class);
		termTableListFormMap.put("id",id);
		termTableListFormMap.put("oriLanguage", oriLanguage);
		String proofreaderId = getPara("proofreaderId");
	    if(proofreaderId!=null){
		termTableListFormMap.put("proofreaderId",proofreaderId);
		}else{
			termTableListFormMap.put("proofreaderId",termTableListMapper.findProofreadnickname(termTableListFormMap));
		}
		String auditorId = getPara("auditorId");
	    if(auditorId!=null){
			termTableListFormMap.put("auditorId",auditorId);
		}else{
			termTableListFormMap.put("auditorId",termTableListMapper.findAuditernickname(termTableListFormMap));
		}
		for(String key : termTableListFormMap.keySet()){
			System.out.println("key= "+ key + " and value= " + termTableListFormMap.get(key));
		}
		termTableListMapper.editEntity(termTableListFormMap);
		
		return "success";
	}
	
	//编辑二级列表
	@RequestMapping("editUI_2")
	public String editUI_2(Model model) throws Exception {
		id = getPara("id");
		System.out.println("==========="+id);
		model.addAttribute("domain", termTableListMapper.findDomain());
	    model.addAttribute("id", id);
	    TermTableFormMap termTableFormMap=new TermTableFormMap();
	    termTableFormMap.put("id", id);
	    /*termTableMapper.findByid(termTableFormMap);*/
	    
		if (Common.isNotEmpty(id)) {
			
			model.addAttribute("term",termTableMapper.findByid(termTableFormMap));
		}
		return Common.BACKGROUND_PATH + "/system/term/editTermD";
	}
	/***
	 * 修改术语二级列表
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("editTermD")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="术语管理",methods="术语管理-修改术语")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editTermD() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				id = getPara("id");
				TermTableFormMap termTableDFormMap = new TermTableFormMap();
		        /*String id=getPara("id");*/
				UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
		    	String accountNames=(String) userFormMap.get("accountName");
				/*String id=request.getParameter("id");*/
		    	termTableDFormMap.put("TermOrigin", request.getParameter("TermOrigin"));
		    	termTableDFormMap.put("TermTarget", request.getParameter("TermTarget"));
				String source="上传文件";
				termTableDFormMap.put("Uploader", accountNames+source);
				termTableDFormMap.put("id",id );
				termTableDFormMap.put("DomainId", request.getParameter("domainId"));
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String date1=sdf.format(new Date());
				termTableDFormMap.put("UploadTime",date1);
				termTableDFormMap.put("User_UC",1);
				termTableMapper.updatefile(termTableDFormMap);
		return "success";
	}
	
    /***
     * 导出术语txt文件
     * @param response
     * @param request
     * @throws FileNotFoundException
     */
	@RequestMapping("exportTXT")
    public void  exportTXT(HttpServletResponse response,HttpServletRequest request) throws FileNotFoundException{
		response.setContentType("application/vnd.ms-txt;charset=utf-8");
		 response.setContentType("text/plain");
		 String filename = termTableListMapper.lookTermNameById(id);
		/*String filename = look_termName_by_id(id);*/
    	TermTableFormMap termTableFormMap=findHasHMap(TermTableFormMap.class);
    	/* FileWriter fileWriter = null;*/
    	 Writer out = null;
 		try {
 			out = response.getWriter();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	 BufferedWriter bWriter =new BufferedWriter(out);
     	String fileName;
		try {
			fileName = URLEncoder.encode(filename,"UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".txt").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		termTableFormMap.put("termId", id);
			List<TermTableFormMap> lis=termTableMapper.exporttxt(termTableFormMap);
    	String [] str = new String [lis.size()];
    	 
    	try {
    		int i =0;
			for (TermTableFormMap tp : lis) {
				str[i] =  tp.get("TermOrigin")+"\r\n"+tp.get("TermTarget")+"\r\n"+"\r\n";
				bWriter.write(String.valueOf(str[i])+" ");
				bWriter.flush();
				i++;
			}
			bWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
     
	/***
	 * 导出术语word文件
	 * @param response
	 * @param request
	 * @throws FileNotFoundException
	 */
	@RequestMapping("BuildWord")
    public void  BuildWord(HttpServletResponse response,HttpServletRequest request) throws FileNotFoundException{
		response.setContentType("application/vnd.ms-txt;charset=utf-8");
		 response.setContentType("text/plain");
		 String filename = termTableListMapper.lookTermNameById(id);
		/*String filename = look_termName_by_id(id);*/
    	TermTableFormMap termTableFormMap=findHasHMap(TermTableFormMap.class);
    	/* FileWriter fileWriter = null;*/
    	 Writer out = null;
 		try {
 			out = response.getWriter();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	 BufferedWriter bWriter =new BufferedWriter(out);
     	String fileName;
		try {
			fileName = URLEncoder.encode(filename,"UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".doc").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		termTableFormMap.put("termId", id);
			List<TermTableFormMap> lis=termTableMapper.exporttxt(termTableFormMap);
    	String [] str = new String [lis.size()];
    	 
    	try {
    		int i =0;
			for (TermTableFormMap tp : lis) {
				str[i] =  tp.get("TermOrigin")+"\r\n"+tp.get("TermTarget")+"\r\n"+"\r\n";
				bWriter.write(String.valueOf(str[i])+" ");
				bWriter.flush();
				i++;
			}
			bWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	/***
	 * 导出术语tmx格式文件
	 * @param response
	 * @throws IOException
	 * @throws JDOMException
	 */
	@RequestMapping("BuildTMXDoc")
    public void BuildTMXDoc(HttpServletResponse response) throws IOException, JDOMException {  
		response.setContentType("application/vnd.ms-tmx;charset=utf-8");
	        //定义变量  
	        String seg = "seg";  
	        // 创建根节点 tmx;      
	        Element root = new Element("tmx");  
	        // 根节点添加到文档中；      
	        Document Doc = new Document(root);  
	        //创建子节点
	        Element elements1 = new Element("header"); 
	        Element elements2 = new Element("body"); 
	       /* String filename = look_termName_by_id(id);*/
	        String filename = termTableListMapper.lookTermNameById(id);
	        String fileName = URLEncoder.encode(filename,"UTF-8");
			response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((fileName + ".tmx").getBytes(), "iso-8859-1"));
			TermTableFormMap termDfileFormMap=findHasHMap(TermTableFormMap.class);
			termDfileFormMap.put("termId", id);
	        List<TermTableFormMap> lis=termTableMapper.exporttxt(termDfileFormMap);
	        root.addContent(elements1);
	        //循环遍历，添加到seg标签中
	        for (TermTableFormMap str : lis) {
	        	 Element elements3 = new Element("tu"); 
	        	elements3.addContent(new Element("tuv").addContent(new Element(seg).setText(str.get("TermOrigin")+"")));
	            elements3.addContent(new Element("tuv").addContent(new Element(seg).setText(str.get("TermTarget")+"")));
	            elements2.addContent(elements3);
	        }  
	        // 给父节点tmx添加子节点;    
			root.addContent(elements2);
	        XMLOutputter XMLOut = new XMLOutputter();  
	        // 输出 XMLOutput.xml 文件到项目根目录；     
	        ServletOutputStream out = response.getOutputStream();
	     
	        XMLOut.output(Doc, out);  
	    }  
}