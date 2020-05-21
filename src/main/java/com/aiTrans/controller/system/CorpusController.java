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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.aiTrans.entity.CorpusTableFormMap;
import com.aiTrans.entity.CorpusTableListFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.CorpusTableListMapper;
import com.aiTrans.mapper.CorpusTableMapper;
import com.aiTrans.mapper.DomainMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * corpus语料管理
 * 
 * @author SS
 * @version 4.0v
 */
@Controller
@RequestMapping("/corpus/")
public class CorpusController extends BaseController {
	@Inject
	private CorpusTableListMapper corpusTableListDMapper;
	@Inject
	private DomainMapper domainMapper;
	@Inject
	private CorpusTableMapper corpusTableDMapper;
	// 2017年4月21日10:45:17
	private String corpusName;
	// 2017年4月17日13:37:47
	private String id;
	private String oriLanguage;
    private String domainId;
    
	public String getPara(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getParameter(key);
	}

	/**
	 * 展示的是该权限用户在此功能中的按钮资源
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */

	// 查看语料(一级列表按语种分类,二级列表按语料名称分类)
	@RequestMapping("list")
	public String list_corpus(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		String corpusId = getPara("id");
		if ("97".equals(corpusId)) {
			oriLanguage = "EN";
		} else if ("98".equals(corpusId)) {
			oriLanguage = "JP";
		} else if ("99".equals(corpusId)) {
			oriLanguage = "KOR";
		} else if ("100".equals(corpusId)) {
			oriLanguage = "GER";
		} else if ("101".equals(corpusId)) {
			oriLanguage = "FR";
		}
		return Common.BACKGROUND_PATH + "/system/corpus/list_corpus_first";
	}
    
	/***
	 * 根据语言查询语料
	 * @return
	 */
	@ResponseBody
	@RequestMapping("find_corpus_first_by_page")
	public PageView find_corpus_first_by_page(String pageNow, String pageSize,
			String column, String sort) throws Exception {
		CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
		corpusTableListFormMap = toFormMap(corpusTableListFormMap, pageNow, pageSize,
		corpusTableListFormMap.getStr("orderby"));
		corpusTableListFormMap.put("column", column);
		corpusTableListFormMap.put("sort", sort);
		corpusTableListFormMap.put("oriLanguage", oriLanguage);
		pageView.setRecords(corpusTableListDMapper.findFirstPage(corpusTableListFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	@RequestMapping("addUI_1")
	public String addUI_1(Model model) throws Exception {
		model.addAttribute("oriLanguage", oriLanguage);
		model.addAttribute("domain", corpusTableListDMapper.findDomain());
		return Common.BACKGROUND_PATH + "/system/corpus/addCorpusNameD";
	}

	
	@RequestMapping("upload1")
	public String upload1(Model model,HttpServletRequest request) throws Exception {
		/*2017年4月24日18:54:13
		 * 2017年4月28日16:37:57 注掉下面两行
		 */
		String corpusName=request.getParameter("corpusName");
		id=getPara("id");
		model.addAttribute("id", id);
		model.addAttribute("corpusName", corpusName);
		model.addAttribute("domain", corpusTableListDMapper.findDomain());
		/*DomainFormMap domainFormMap = getFormMap(DomainFormMap.class);
		model.addAttribute("domain",domainMapper.findByWhere(domainFormMap));*/
		return Common.BACKGROUND_PATH + "/system/corpus/addCorpusuploadD";
	}
	
	// 二级列表请求
	@RequestMapping("look_corpus")
	public String look_corpus(Model model) {
		id = getPara("corpusId");
		//CorpusFormMap corpusFormMap=new CorpusFormMap();
		//corpusFormMap.put("id", id);
		model.addAttribute("id", id);
		String corpusName = corpusTableListDMapper.findCorpusNameById(id);
	     model.addAttribute("corpusName",corpusName);
		return Common.BACKGROUND_PATH + "/system/corpus/look_corpus";
	}

	// 根据语料名称查看语料
	@ResponseBody
	@RequestMapping("find_corpus_by_page")
	public PageView find_corpus_by_page(String pageNow, String pageSize,
			String column, String sort) throws Exception {
		id=getPara("id");
		/*List<corpusTableDFormMap> corpusTableDFormMap=new ArrayList<corpusTableDFormMap>;*/
		CorpusTableFormMap corpusTableDFormMap = getFormMap(CorpusTableFormMap.class);
		corpusTableDFormMap = toFormMap(corpusTableDFormMap, pageNow, pageSize,
		corpusTableDFormMap.getStr("orderby"));
		corpusTableDFormMap.put("column", column);
		corpusTableDFormMap.put("sort", sort);
		corpusTableDFormMap.put("corpusId", id);
		
		String name = corpusTableListDMapper.findCorpusNameById(id);
		//corpusFormMap.put("name", name);
		//corpusTableDFormMap.put("oriLanguage", oriLanguage);
		pageView.setRecords(corpusTableDMapper.findcorpus(corpusTableDFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
    
	
	@ResponseBody
	@RequestMapping("addCorpusNameD")
	@SystemLog(module = "语料管理", methods = "语料管理-新增语料")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	public String addCorpusNameD(HttpServletRequest request) {
		try {
			CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
			String domainName=getPara("domainId");
			corpusTableListFormMap.put("domainName", domainName);
			//根据领域名字查找id存入数据库
			int domainId=corpusTableListDMapper.findDomainName(corpusTableListFormMap);
			corpusTableListFormMap.put("domainId", domainId);
			corpusTableListFormMap.put("oriLanguage", oriLanguage);
			String proofreaderId = getPara("proofreaderId");
			if(proofreaderId!=null){
				  corpusTableListFormMap.put("proofreaderId",proofreaderId);
				}else{
					corpusTableListFormMap.put("proofreaderId",corpusTableListDMapper.findProofreadnickname(corpusTableListFormMap));
				}
			  String auditorId = getPara("auditorId");
			  if(auditorId!=null){
				  corpusTableListFormMap.put("auditorId",auditorId);
				}else{
					/*corpusTableListFormMap.put("auditorId","89");*/
					corpusTableListFormMap.put("auditorId",corpusTableListDMapper.findAuditernickname(corpusTableListFormMap));
				}
			  
			  corpusTableListDMapper.addEntity(corpusTableListFormMap);
		} catch (Exception e) {
			throw new SystemException("添加语料名称异常");
		}
		return "success";
	} 
    /**
     * 查看详情添加
     */
	@RequestMapping("addUI_2")
	public String addUI_2(Model model) throws Exception {
		/*String name = getPara("corpusName");
		// model.addAttribute("corpusName",name);
		// DomainFormMap domainFormMap = getFormMap(DomainFormMap.class);
		// model.addAttribute("domain",domainMapper.findByWhere(domainFormMap));
		List<CorpusFormMap> list = corpusTableListDMapper.findByAttribute("name", name,
				CorpusFormMap.class);*/
		String id=getPara("id");
		model.addAttribute("id", id);
		model.addAttribute("domain", corpusTableListDMapper.findDomain());
		/*model.addAttribute("corpus", list.get(0));*/

		return Common.BACKGROUND_PATH + "/system/corpus/addCorpusD";
	}

	@ResponseBody
	@RequestMapping("addCorpusD")
	@SystemLog(module = "语料管理", methods = "语料管理-新增语料")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	public String addCorpusD(HttpServletRequest request) {
		try {
			CorpusTableFormMap corpusTableDFormMap = new CorpusTableFormMap();
            /*String id=getPara("id");*/
			UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
	    	String accountNames=(String) userFormMap.get("accountName");
			String id=request.getParameter("id");
			corpusTableDFormMap.put("CorpusOrigin", request.getParameter("CorpusOrigin"));
			corpusTableDFormMap.put("CorpusTarget", request.getParameter("CorpusTarget"));
			String source="上传文件";
			corpusTableDFormMap.put("Uploader", accountNames+source);
			corpusTableDFormMap.put("CorpusId", id);
			corpusTableDFormMap.put("DomainId", request.getParameter("domainId"));
			corpusTableDFormMap.put("User_UC",request.getParameter("User_UC"));
			corpusTableDMapper.savefile(corpusTableDFormMap);// 新增后返回新增信息
		} catch (Exception e) {
			throw new SystemException("添加语料异常");
		}
		return "success";
	}
    
	 /***
	  * 语料界面导出EXCEL
	  * @param request
	  * @param response
	  * @throws IOException
	  */
	 @RequestMapping("/export")
		public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 CorpusTableListFormMap corpusFormMap = findHasHMap(CorpusTableListFormMap.class);
		 corpusFormMap.put("oriLanguage", oriLanguage);
		 if(oriLanguage=="JP"){
				oriLanguage="日语语料";
			}
			if(oriLanguage=="EN"){
				oriLanguage="英语语料";
			}
		    if(oriLanguage=="KOR"){
		    	oriLanguage="韩语语料";
		    }
		    if(oriLanguage=="GER"){
		    	oriLanguage="德语语料";
		    }	
		    if(oriLanguage=="FR"){
		    	oriLanguage="法语语料";
		    }
		   String fileName = URLEncoder.encode(oriLanguage+"列表","UTF-8");
			//exportData = 
			// [{"colkey":"sql_info","name":"SQL语句","hide":false},
			// {"colkey":"total_time","name":"总响应时长","hide":false},
			// {"colkey":"avg_time","name":"平均响应时长","hide":false},
			// {"colkey":"record_time","name":"记录时间","hide":false},
			// {"colkey":"call_count","name":"请求次数","hide":false}
			// ]
			String exportData = corpusFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<CorpusTableListFormMap> lis = corpusTableListDMapper.findFirstPage(corpusFormMap);
			for (CorpusTableListFormMap corpusTableListFormMap : lis) {
				int isProofread=(int) corpusTableListFormMap.get("isProofread");
				if(isProofread == 0){
					corpusTableListFormMap.put("isProofread", "只翻译");
				}else if(isProofread == 1){
					corpusTableListFormMap.put("isProofread", "翻译和校对");
				}else if(isProofread == 2){
					corpusTableListFormMap.put("isProofread","翻译、校对及审核");
				}
			}
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	   /***
	    * 查看详情导出EXCEL
	    * @param request
	    * @param response
	    * @throws IOException
	    */
	 @RequestMapping("/export2")
		public void export2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 CorpusTableFormMap corpusTableDFormMap = findHasHMap(CorpusTableFormMap.class);
			/*String name = corpusTableListDMapper.findCorpusNameById(id);*/
			// 2017年4月24日17:15:35
		   String name = corpusTableListDMapper.findCorpusNameById(id);
			corpusTableDFormMap.put("corpusId", id);
		    String fileName = URLEncoder.encode(name+"列表","UTF-8");
			String exportData = corpusTableDFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<CorpusTableFormMap> lis = corpusTableDMapper.queryByAll(corpusTableDFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	 
      
	    /***
	     * 语料多文件上传
	     * @throws Exception
	     */
	    @ResponseBody
		@RequestMapping("uploadCorpus1")
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
	    @SystemLog(module="语料管理",methods="语料管理-上传语料")//凡需要处理业务逻辑的.都需要记录操作日志
		public String uploadCorpus1(@RequestParam("file") CommonsMultipartFile files[],
				Model model,HttpServletResponse response,
				HttpServletRequest request) throws Exception{
	    	CorpusTableFormMap corpustableFormMap=findHasHMap(CorpusTableFormMap.class);
	    	 CorpusTableListFormMap corpusFormMap = findHasHMap(CorpusTableListFormMap.class);
	    	 domainId=request.getParameter("domainId");
		    corpusFormMap.put("id", id);
		   corpusFormMap.put("oriLanguage", oriLanguage);
				if(oriLanguage=="JP"){
					oriLanguage="日语语料";
				}
				if(oriLanguage=="EN"){
					oriLanguage="英语语料";
				}
			    if(oriLanguage=="KOR"){
			    	oriLanguage="韩语语料";
			    }
			    if(oriLanguage=="GER"){
			    	oriLanguage="德语语料";
			    }	
			    if(oriLanguage=="FR"){
			    	oriLanguage="法语语料";
			    }	
				//.获取当前文件的根目录，并查找"/update/images"路径
				/*String path = request.getSession().getServletContext()
						.getRealPath("/upload/images");*/
				/*String path="C:/upload/"+oriLanguage+"/";*/
				String path="C:/upload/"+oriLanguage+"/";
				//创建path目录下的文件对象
				File saveDir = new File(path);
				//判断文件是否存在
				if(!saveDir.exists()){
					//	文件不存在，则创建多级目录来创建这个文件	
					saveDir.mkdirs();
				}
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
				if (!files[i].isEmpty()) {  
		            try {  
		                FileOutputStream fos = new FileOutputStream(path+  
		                         newFileName);  
		                InputStream in = files[i].getInputStream();  
	       //    定义一个变量来判断是否读取完毕
		                byte[] buffer=new byte[2048];
		                int len = 0;  
		                while ((len = in.read(buffer)) != -1) {  
		                    fos.write(buffer,0,len);
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
			    }else if("xlsx".equals(fileEit)){
			    	uploadExcel(saxpath);
			    }
				}
				return "success";
			} 
	
	/***
	 * 导入txt文件	
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
             CorpusTableFormMap corpusTableDFormMap=null;
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
            		 corpusTableDFormMap = new CorpusTableFormMap();
            		 corpusTableDFormMap.put("CorpusOrigin",lineTxt);
            		}
            		else{
            			corpusTableDFormMap.put("CorpusTarget",lineTxt);
            			corpusTableDMapper.savefile(corpusTableDFormMap);
            		}
            		i++;
            	}
            	//判断英文标点符号
            	if(finalName.equals(".") || finalName.equals(",") ){
            		if(i%2==0){
            		corpusTableDFormMap = new CorpusTableFormMap();
            		corpusTableDFormMap.put("CorpusOrigin",lineTxt);
 					corpusTableDFormMap.put("Uploader",accountNames+source);
 					corpusTableDFormMap.put("CorpusId", id);
 					corpusTableDFormMap.put("User_UC",1 );
 					corpusTableDFormMap.put("DomainId",domainId );
 					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
 					String date1=sdf.format(new Date());
 					corpusTableDFormMap.put("UploadTime",date1);
             	 
            		}else{
            				corpusTableDFormMap.put("CorpusTarget",lineTxt);
         					corpusTableDFormMap.put("Uploader",accountNames+source);
         					corpusTableDFormMap.put("CorpusId", id);
         					corpusTableDFormMap.put("User_UC",1 );
         					corpusTableDFormMap.put("DomainId",domainId );
         					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
         					String date1=sdf.format(new Date());
         					corpusTableDFormMap.put("UploadTime",date1);
         					corpusTableDMapper.savefile(corpusTableDFormMap);
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
		CorpusTableFormMap  corpusTableDFormMap=null;
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
			 
			corpusTableDFormMap = getFormMap(CorpusTableFormMap.class);
     		corpusTableDFormMap.put("CorpusOrigin",strc00);
     		corpusTableDFormMap.put("CorpusTarget",strc01);
     		corpusTableDFormMap.put("Uploader",accountNames+source);
     		corpusTableDFormMap.put("CorpusId", id);
     		corpusTableDFormMap.put("User_UC",1 );
     		corpusTableDFormMap.put("DomainId",domainId );
     		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    		String date1=sdf.format(new Date());
    		corpusTableDFormMap.put("UploadTime",date1);
     		corpusTableDMapper.savefile(corpusTableDFormMap);
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
		      CorpusTableFormMap corpusTableFormMap=null;
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
		                    	  corpusTableFormMap = getFormMap(CorpusTableFormMap.class);
		                    	  corpusTableFormMap.put("CorpusOrigin",finalName);
		                      }
		                      if(h==1){
		                    	  corpusTableFormMap.put("CorpusTarget",finalName);
		                    	  corpusTableFormMap.put("Uploader",accountNames+source);
		                    	  corpusTableFormMap.put("CorpusId", id);
		                    	  corpusTableFormMap.put("User_UC",1 );
		                    	  corpusTableFormMap.put("DomainId",domainId );
		                    	  corpusTableDMapper.savefile(corpusTableFormMap);
		                      }
		                      if(h%2==0 && h>1){
		                    	  corpusTableFormMap.put("CorpusOrigin",finalName );
		  					 }
		                      if(h%2!=0 && h>1){
		                    	  corpusTableFormMap.put("CorpusTarget",finalName);
		                    	  corpusTableFormMap.put("Uploader",accountNames+source);
		                    	  corpusTableFormMap.put("CorpusId", id);
		                    	  corpusTableFormMap.put("User_UC",1 );
		                    	  corpusTableFormMap.put("DomainId",domainId );
		                    	  corpusTableDMapper.savefile(corpusTableFormMap);
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
		    	                source="上传文件";
		    	               String[] ff=lineWord.split("\r\n");
		    	               for (int i = 0; i < ff.length; i++) {
		    	            	   System.out.println(ff[i]);
		    	            	  String CorpusOrigin=ff[i].substring(0, ff[i].indexOf("^"));
		    	            	  String CorpusTarget=ff[i].substring(ff[i].indexOf("^")+1, ff[i].length());
		    	            	  corpusTableFormMap = getFormMap(CorpusTableFormMap.class);
		    	            	  corpusTableFormMap.put("CorpusOrigin",CorpusOrigin);
		    	            	  corpusTableFormMap.put("CorpusTarget",CorpusTarget);
		    	            	  corpusTableFormMap.put("Uploader",accountNames+source);
		    	            	  corpusTableFormMap.put("CorpusId", id);
		    	            	  corpusTableFormMap.put("User_UC",1 );
		    	            	  corpusTableFormMap.put("DomainId",domainId );
			    	            		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    	            		String date1=sdf.format(new Date());
			    	            		corpusTableFormMap.put("UploadTime",date1);
			    	            		corpusTableDMapper.savefile(corpusTableFormMap);
		    	           }
		    		} 
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		 }//end method
 
	/***
	 * 创建SAX解析器进行对TMX文件解析
	 * @param sb
	 */
		 public void SAXparserUtil(String saxpath){
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
	/***
	 * 继承DefaultHandler 具体实现解析器对于的5个方法 
	 * @author vampire
	 *
	 */
		public  class SAXDefaultHandler extends DefaultHandler{
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			CorpusTableFormMap corpusTableDFormMap = getFormMap(CorpusTableFormMap.class);
			String name=request.getParameter("corpusName");
			
			UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
	    	String accountNames=(String) userFormMap.get("accountName");
	     	private List<String>  translates;
	     	private String parent;
	     	
	    @Override
	 public void startDocument() {
	 	   this.translates=new ArrayList<>();
	 	   System.out.println("===startDocument====");
	 }
	    
	  @Override
	 		public void endDocument() {
	 	      System.out.println("====endDocument=====");
	 	     CorpusTableFormMap corpusTableDFormMap =null;
	 	      for (int i = 0; i < translates.size(); i++) {
	 	    	if((i+=2%2)==0){  
	 	    		corpusTableDFormMap = new CorpusTableFormMap();
	 	    		}
					if(i==0){
						corpusTableDFormMap.put("CorpusTarget",translates.get(i) );
					}
					if(i==1){
						String source="上传文件";
						corpusTableDFormMap.put("Uploader",accountNames+source);
						corpusTableDFormMap.put("CorpusId", id);
						
						corpusTableDFormMap.put("User_UC",1 );
						corpusTableDFormMap.put("DomainId",domainId );
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String date1=sdf.format(new Date());
						corpusTableDFormMap.put("UploadTime",date1);
						corpusTableDFormMap.put("CorpusOrigin",translates.get(i) );
						try {
							corpusTableDMapper.savefile(corpusTableDFormMap);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(i%2==0 && i>1){
						corpusTableDFormMap.put("CorpusTarget",translates.get(i) );
					}
					if(i%2!=0 && i>1){
						String source="上传文件";
						corpusTableDFormMap.put("Uploader", accountNames+source);
						corpusTableDFormMap.put("CorpusId", id);
						corpusTableDFormMap.put("User_UC", 1);
						System.out.println(id);
						corpusTableDFormMap.put("DomainId",domainId );
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String date1=sdf.format(new Date());
						corpusTableDFormMap.put("UploadTime",date1);
						corpusTableDFormMap.put("CorpusOrigin",translates.get(i) );
						try {
							corpusTableDMapper.savefile(corpusTableDFormMap);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("解析到数据库异常");
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
		
		
		
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "语料管理", methods = "语料管理-删除语料")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			corpusTableDMapper.deleteByAttribute("id", id, CorpusTableFormMap.class);
		}
		return "success";
	}
    
	@ResponseBody
	@RequestMapping("deleteEntitys")
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "语料管理", methods = "语料管理-删除语料")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntitys() throws Exception {
		CorpusTableListFormMap corpusTableListFormMap=new CorpusTableListFormMap();
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			corpusTableListFormMap.put("id", id);
			int findCorpusId=corpusTableListDMapper.findForeignKey(corpusTableListFormMap);
			if(findCorpusId >0){
			  return "failed";
			}else {
			corpusTableListDMapper.deleteByAttribute("id", id, CorpusTableListFormMap.class);
			  return "success";
			}
		}
		return "success";
	}
	
	@RequestMapping("editUI_1")
	public String editUI_1(Model model) throws Exception {
		id = getPara("id");
		CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
		model.addAttribute("domain", corpusTableListDMapper.findDomain());
		corpusTableListFormMap.put("oriLanguage", oriLanguage);
		List<ProofreaderFormMap> list = corpusTableListDMapper.findProofreadLanguage(corpusTableListFormMap);
		model.addAttribute("proofreadList", list);
		model.addAttribute("auditerList",corpusTableListDMapper.findAuditerLanguage(corpusTableListFormMap));
		model.addAttribute("translatorList",corpusTableListDMapper.findTranslatorLanguage(corpusTableListFormMap));
		
		/*model.addAttribute("nickname", corpusTableListDMapper.findproofreadD());*/
		if(Common.isNotEmpty(id)){
			model.addAttribute("corpus", corpusTableListDMapper.findbyFrist("id", id, CorpusTableListFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/corpus/editCorpusNameD";
	}
	
	@ResponseBody
	@RequestMapping("editCorpusNameD")
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "语料管理", methods = "语料管理-修改语料")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String editCorpusNameD() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
         
		corpusTableListFormMap.put("id", id);
		corpusTableListFormMap.put("oriLanguage", oriLanguage);
		String proofreaderId = getPara("proofreaderId");
		  if(proofreaderId!=null){
			  corpusTableListFormMap.put("proofreaderId",proofreaderId);
			}else{
				corpusTableListFormMap.put("proofreaderId",corpusTableListDMapper.findProofreadnickname(corpusTableListFormMap));
			}
		  String auditorId = getPara("auditorId");
		  if(auditorId!=null){
			  corpusTableListFormMap.put("auditorId",auditorId);
			}else{
				corpusTableListFormMap.put("auditorId",corpusTableListDMapper.findAuditernickname(corpusTableListFormMap));
			}
		for (String key : corpusTableListFormMap.keySet()) {
			System.out.println("key= " + key + " and value= "
					+ corpusTableListFormMap.get(key));
		}
		corpusTableListDMapper.editEntity(corpusTableListFormMap);

		return "success";
	}

	// 编辑二级列表
	@RequestMapping("editUI_2")
	public String editUI_2(Model model) throws Exception {
		id = getPara("id");
		System.out.println("==========="+id);
		model.addAttribute("domain", corpusTableListDMapper.findDomain());
	    model.addAttribute("id", id);
		if (Common.isNotEmpty(id)) {
			model.addAttribute("corpus",
					corpusTableDMapper.findbyFrist("id", id, CorpusTableFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/corpus/editCorpusD";
	}

	@ResponseBody
	@RequestMapping("editCorpusD")
	@Transactional(readOnly = false)
	// 需要事务操作必须加入此注解
	@SystemLog(module = "语料管理", methods = "语料管理-修改语料")
	// 凡需要处理业务逻辑的.都需要记录操作日志
	public String editCorpusD() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
        //主键id		
		id = getPara("id");
		CorpusTableFormMap corpusTableDFormMap = new CorpusTableFormMap();
        /*String id=getPara("id");*/
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
    	String accountNames=(String) userFormMap.get("accountName");
		/*String id=request.getParameter("id");*/
		corpusTableDFormMap.put("CorpusOrigin", request.getParameter("CorpusOrigin"));
		corpusTableDFormMap.put("CorpusTarget", request.getParameter("CorpusTarget"));
		String source="上传文件";
		corpusTableDFormMap.put("Uploader", accountNames+source);
	    corpusTableDFormMap.put("id",id );
		corpusTableDFormMap.put("DomainId", request.getParameter("domainId"));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date1=sdf.format(new Date());
		corpusTableDFormMap.put("UploadTime",date1);
		corpusTableDFormMap.put("User_UC",1);
		
		for (String key : corpusTableDFormMap.keySet()) {
			System.out.println("key= " + key + " and value= "
					+ corpusTableDFormMap.get(key));
		}
		corpusTableDMapper.editEntity(corpusTableDFormMap);
		return "success";
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
		/*CorpusTableListFormMap account = corpusTableListDMapper.findCorpusTableListByName(name);*/
		CorpusTableListFormMap account = corpusTableListDMapper.findbyFrist("corpusName", name, CorpusTableListFormMap.class);
		if("add".equals(type)){
			if (account == null) {
				return true;
			} else {
				return false;
			}
		}
		if("edit".equals(type)){
			String id=getPara("id");
			CorpusTableListFormMap Name = corpusTableListDMapper.findbyFrist("corpusName",name, CorpusTableListFormMap.class);
			CorpusTableListFormMap corpusName = corpusTableListDMapper.findbyFrist("id",id, CorpusTableListFormMap.class);
		   if (Name == null || name.equals(corpusName.get("corpusName"))) {
				return true;
			} else {
				return false;
			}
		}
		return flag;
	}
	
	
	/***
	 * 根据语言查找相对应的校对员
	 */
	@ResponseBody
	@RequestMapping("findProofreadlanguage")
	public List<ProofreaderFormMap> findProofreadlanguage(Model model){
		CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
		corpusTableListFormMap.put("oriLanguage", oriLanguage);
		List<ProofreaderFormMap> proofreaderFormMapList=corpusTableListDMapper.findProofreadLanguage(corpusTableListFormMap);
		return proofreaderFormMapList;
	}
	/***
	 *根据语言查询相对应的审核员
	 */
	@ResponseBody
	@RequestMapping("findAuditerLanguage")
	public List<AuditorFormMap> findlanguages(Model model){
		CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
		corpusTableListFormMap.put("oriLanguage", oriLanguage);
		List<AuditorFormMap> AuditorFormMapList=corpusTableListDMapper.findAuditerLanguage(corpusTableListFormMap);
		return AuditorFormMapList;
	}
	/***
	 * 根据语言查询相对应得译员
	 */
	@ResponseBody
	@RequestMapping("findTranslatorLanguage")
	public List<TranslatorFormMap> findTranslatorLanguage(Model model){
		CorpusTableListFormMap corpusTableListFormMap = getFormMap(CorpusTableListFormMap.class);
		String domainId=getPara("domainId");
		System.out.println(domainId);
		corpusTableListFormMap.put("oriLanguage", oriLanguage);
		corpusTableListFormMap.put("domain", domainId);
		List<TranslatorFormMap> TranslatorFormMapList=corpusTableListDMapper.findTranslatorLanguage(corpusTableListFormMap);
		return TranslatorFormMapList;
	}
    /***
     * 导出txt文件
     * @param response
     * @throws FileNotFoundException
     */
	@RequestMapping("exportTXT")
    public void exportTXT(HttpServletResponse response) throws FileNotFoundException{
		response.setContentType("application/vnd.ms-txt;charset=utf-8");
		 response.setContentType("text/plain");
		String filename = corpusTableListDMapper.findCorpusNameById(id);
		CorpusTableFormMap corpusTableDFormMap = findHasHMap(CorpusTableFormMap.class);
    	 Writer out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 BufferedWriter bWriter =new BufferedWriter(out); 
    	 try {
    		 String fileName = URLEncoder.encode(filename,"UTF-8");
			response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((fileName + ".txt").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			corpusTableDFormMap.put("corpusId", id);
			List<CorpusTableFormMap> lis=corpusTableDMapper.exporttxt(corpusTableDFormMap);
    	String [] str = new String [lis.size()];
    try {
    		int i =0;
			for (CorpusTableFormMap tp : lis) {
				str[i] =  tp.get("CorpusOrigin")+"\r\n"+tp.get("CorpusTarget")+"\r\n"+"\r\n";
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
	 * 导出Wrod文件
	 * @param response
	 * @throws FileNotFoundException
	 */
	@RequestMapping("BuildWord")
    public void BuildWord(HttpServletResponse response) throws FileNotFoundException{
		response.setContentType("application/vnd.ms-txt;charset=utf-8");
		 response.setContentType("text/plain");
		String filename = corpusTableListDMapper.findCorpusNameById(id);
		CorpusTableFormMap corpusTableDFormMap = findHasHMap(CorpusTableFormMap.class);
    	 /*FileWriter fileWriter = null;*/
    	 Writer out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 BufferedWriter bWriter =new BufferedWriter(out); 
    	 try {
    		 String fileName = URLEncoder.encode(filename,"UTF-8");
			response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((fileName + ".doc").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			corpusTableDFormMap.put("corpusId", id);
			List<CorpusTableFormMap> lis=corpusTableDMapper.exporttxt(corpusTableDFormMap);
    	String [] str = new String [lis.size()];
    try {
    		int i =0;
			for (CorpusTableFormMap tp : lis) {
				str[i] =  tp.get("CorpusOrigin")+"\r\n"+tp.get("CorpusTarget")+"\r\n"+"\r\n";
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
	 * 数据库导入TMX文件
	 * @return
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
	        String filename = corpusTableListDMapper.findCorpusNameById(id);
	        
	        String fileName = URLEncoder.encode(filename,"UTF-8");
			response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((fileName + ".tmx").getBytes(), "iso-8859-1"));
	        CorpusTableFormMap corpusTableDFormMap = findHasHMap(CorpusTableFormMap.class);
	        corpusTableDFormMap.put("corpusId", id);
			List<CorpusTableFormMap> lis=corpusTableDMapper.exporttxt(corpusTableDFormMap);
	        root.addContent(elements1);
	        //循环遍历，添加到seg标签中
	        for (CorpusTableFormMap str : lis) {
	        	 Element elements3 = new Element("tu"); 
	        	elements3.addContent(new Element("tuv").addContent(new Element(seg).setText(str.get("CorpusOrigin")+"")));
	            elements3.addContent(new Element("tuv").addContent(new Element(seg).setText(str.get("CorpusTarget")+"")));
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