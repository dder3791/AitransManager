package com.aiTrans.controller.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ArticleFormMap;
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.FileFormMap;
import com.aiTrans.entity.IntegralFormMap;
import com.aiTrans.entity.ProcedureTypeFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.ArticleMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * 资讯信息
 * @author Dell
 *
 */
@Controller
@RequestMapping("/article/")
public class ArticleController extends BaseController{
	@Inject
	private ArticleMapper articleMapper;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	//多文件上传
	public void upload(@RequestParam("file")CommonsMultipartFile files[],String savePath,HttpServletRequest request) throws ServletException, IOException, FileUploadException {
				//创建path目录下的文件对象
				File saveDir = new File(savePath);
				//判断文件是否存在
				if(!saveDir.exists()){
					//	文件不存在，则创建多级目录来创建这个文件	
					saveDir.mkdirs();
				}
				if(files.length>0){
					for(int i=0;i<files.length;i++){
						// 获取这个文件的传过来的名字
						String name=files[i].getOriginalFilename().toLowerCase().trim();
						File saveFile = new File(saveDir,name);
											
							try {
								InputStream instream = files[i].getInputStream();
		          //	创建一个新的输出流输出的文件就是saveFile这个文件类型
								FileOutputStream fos = new FileOutputStream(saveFile);
		          //	定义一个数组，来规定上传的速率 
								byte [] buffer = new byte [1024*10];
		         //	定义一个变量来判断是否读取完毕，
								int len = 0;
		         //	每次循环给len赋值赋予输入流读取的数据长度不为-1则一直循环下去
								while(((len=instream.read(buffer))!=-1)){
									//写出已读入buffer的内容
									fos.write(buffer,0,len);
								}
								fos.close();//关闭输入输出流
								instream.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}					
				}
	 }
				
	private String id;//对应表的主键
	private String table;//对应表的表名
	private String fk;//对应主表(ly_project)的外键
	/**
	 * 跳转展示资讯信息页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_article")
	public String list_article(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/article/list_article";
	}
	
	
	/**
	 * 查看资讯信息分页列表,应该从客户资讯信息表中查
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
		@ResponseBody
		@RequestMapping("find_article_by_page")
		public PageView find_article_by_page( String pageNow,
				String pageSize,String column,String sort) throws Exception {
			ArticleFormMap articleFormMap = getFormMap(ArticleFormMap.class);
			articleFormMap=toFormMap(articleFormMap, pageNow, pageSize,articleFormMap.getStr("orderby"));
			articleFormMap.put("column", column);
			articleFormMap.put("sort", sort);
	        pageView.setRecords(articleMapper.findArticleByPage(articleFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
	        return pageView;
		}
		
		/**
		 * 跳转添加资讯页面
		 * @return
		 */
		@RequestMapping("addUI")
		public String addUI(){
			return Common.BACKGROUND_PATH + "/system/article/add_article";
		}
		
		/**
		 * 添加资讯实体
		 * @return
		 */
		@ResponseBody
		@RequestMapping("addEntity")
		@SystemLog(module="资讯信息",methods="资讯信息-新增咨询")//凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		public String addEntity(@RequestParam("file")CommonsMultipartFile files[],HttpServletRequest request){
			ArticleFormMap articleFormMap = getFormMap (ArticleFormMap.class);
			String title=request.getParameter("articleFormMap.title");
			articleFormMap.put("title",title );
			articleFormMap.put("context", request.getParameter("articleFormMap.context"));
			articleFormMap.put("issueDate", request.getParameter("articleFormMap.issueDate"));
			articleFormMap.put("type", request.getParameter("articleFormMap.type"));
			articleFormMap.put("title", request.getParameter("articleFormMap.title"));
			articleFormMap.put("auther", request.getParameter("articleFormMap.auther"));
			articleFormMap.put("hot", request.getParameter("articleFormMap.hot"));
			articleFormMap.put("elite", request.getParameter("articleFormMap.elite"));
			ServletContext application=request.getServletContext();
			
			String savePath = application.getRealPath("/upload/"+title);
			articleFormMap.put("url","/upload/"+title+"//"+files[files.length-1].getOriginalFilename().toLowerCase().trim());
			
			try {
				//ArticleFormMap articleFormMap = getFormMap(ArticleFormMap.class);
				articleMapper.addEntity(articleFormMap);
				upload(files,savePath,request);
			} catch (Exception e) {
				 throw new SystemException("添加资讯异常");
			}
			return "success";
		}
		
		
		/**
		 * 跳转编辑资讯页面
		 * @return
		 */
		@RequestMapping("editUI")
		public String editUI(Model model){
			id = getPara("id");
			model.addAttribute("articleInfo",articleMapper.findbyFrist("id", id, ArticleFormMap.class));
			return Common.BACKGROUND_PATH + "/system/article/edit_article";
		}
		
		/**
		 * 修改资讯信息实体
		 * @return
		 */
		@ResponseBody
		@RequestMapping("editEntity")
		@SystemLog(module="资讯信息",methods="资讯信息-编辑资讯")//凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		public String editEntity(@RequestParam("file")CommonsMultipartFile files[],HttpServletRequest request){
			ArticleFormMap articleFormMap = getFormMap (ArticleFormMap.class);
			String title=request.getParameter("articleFormMap.title");
			articleFormMap.put("title",title );
			articleFormMap.put("context", request.getParameter("articleFormMap.context"));
			articleFormMap.put("issueDate", request.getParameter("articleFormMap.issueDate"));
			articleFormMap.put("type", request.getParameter("articleFormMap.type"));
			articleFormMap.put("title", request.getParameter("articleFormMap.title"));
			articleFormMap.put("auther", request.getParameter("articleFormMap.auther"));
			articleFormMap.put("hot", request.getParameter("articleFormMap.hot"));
			articleFormMap.put("elite", request.getParameter("articleFormMap.elite"));
			ServletContext application=request.getServletContext();
			
			String savePath = application.getRealPath("/upload/"+title);
			articleFormMap.put("url","/upload/"+title+"//"+files[files.length-1].getOriginalFilename().toLowerCase().trim());
			try {
				articleFormMap.put("id", id);
				articleMapper.editEntity(articleFormMap);
				upload(files,savePath,request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "success";
		}
		
		/**
		 * 删除资讯信息实体数据
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("deleteEntity")
		@Transactional(readOnly=false)//需要事务操作必须加入此注解
		@SystemLog(module="资讯信息",methods="资讯信息-删除资讯")//凡需要处理业务逻辑的.都需要记录操作日志
		public String deleteEntity() throws Exception {
			String[] ids = getParaValues("ids");
			for (String id : ids) {
				articleMapper.deleteByAttribute("id", id, ArticleFormMap.class);
			}
			return "success";
		}
		
		/**
		 * 跳转查看资讯信息详情页面
		 * @return
		 */
		@RequestMapping("look_article")
		public String look_article(Model model){
			id = getPara("id");
			model.addAttribute("articleInfo",articleMapper.findbyFrist("id", id, ArticleFormMap.class));
			return Common.BACKGROUND_PATH + "/system/article/look_article";
		}
		
		@RequestMapping("/export")
		public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String fileName = "资讯信息";
			ArticleFormMap articleFormMap = findHasHMap(ArticleFormMap.class);
			String exportData = articleFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<ArticleFormMap> lis = articleMapper.findArticleByPage(articleFormMap);
			for(ArticleFormMap article:lis){
				int hot =(int)article.get("hot");
				if(hot==1){
					article.put("hot", "是");
				}else{
					article.put("hot", "否");
				}
				
				int elite =(int)article.get("elite");
				if(elite==1){
					article.put("elite", "是");
				}else{
					article.put("elite", "否");
				}
				
			}
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
	
}
