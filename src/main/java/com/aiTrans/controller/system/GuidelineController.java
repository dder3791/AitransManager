package com.aiTrans.controller.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
import com.aiTrans.entity.GuidelineFormMap;
import com.aiTrans.entity.QuestionFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.ArticleMapper;
import com.aiTrans.mapper.GuidelineMapper;
import com.aiTrans.mapper.QuestionMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

@Controller
@RequestMapping("/guideline/")
public class GuidelineController extends BaseController{

	@Inject
	private GuidelineMapper guidelineMapper;
	
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
	
	/**
	 * 跳转展示指南信息页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_guideline")
	public String list_guidelin(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/guideline/list_guideline";
	}
	
	
	/**
	 * 查看指南分页列表,应该从常见问题表中查
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_guideline_by_page")
	public PageView find_guideline_by_page(String pageNow,String pageSize,String column,String sort) throws Exception {
		GuidelineFormMap guidelineFormMap = getFormMap(GuidelineFormMap.class);
		guidelineFormMap=toFormMap(guidelineFormMap, pageNow, pageSize,guidelineFormMap.getStr("orderby"));
		guidelineFormMap.put("column", column);
		guidelineFormMap.put("sort", sort);
        pageView.setRecords(guidelineMapper.findGuidelinByPage(guidelineFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	/**
	 * 跳转添加指南页面
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(){
		return Common.BACKGROUND_PATH + "/system/guideline/add_question";
	}
	
	/**
	 * 添加新手指南实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="新手指南",methods="新手指南-新增指南")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity(@RequestParam("file")CommonsMultipartFile files[],HttpServletRequest request){
		
		String flowName=request.getParameter("guidelineFormMap.flowName");
		GuidelineFormMap guidelineFormMap = new GuidelineFormMap();
		guidelineFormMap.put("flowName",flowName );
		
		ServletContext application=request.getServletContext();
		String savePath = application.getRealPath("/upload/"+flowName);
		guidelineFormMap.put("url","/Aitrans/upload/"+flowName+"//"+files[files.length-1].getOriginalFilename().toLowerCase().trim());
		try {
			guidelineMapper.addEntity(guidelineFormMap);
			upload(files,savePath,request);
		} catch (Exception e) {
			 throw new SystemException("添加问题异常");
		}
		return "success";
	}
	
	/**
	 * 跳转编辑问题页面
	 * @return
	 *//*
	@RequestMapping("editUI")
	public String editUI(Model model){
		id = getPara("id");
		model.addAttribute("questionInfo",questionMapper.findbyFrist("id", id, QuestionFormMap.class));
		return Common.BACKGROUND_PATH + "/system/question/edit_question";
	}
	
	*//**
	 * 修改常见问题信息实体
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping("editEntity")
	@SystemLog(module="常见问题",methods="常见问题-编辑问题")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String editEntity(){
		
		try {
			QuestionFormMap questionFormMap = getFormMap(QuestionFormMap.class);
			questionFormMap.put("id", id);
			questionMapper.editEntity(questionFormMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	*//**
	 * 删除常见问题实体数据
	 * @return
	 * @throws Exception
	 *//*
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="常见问题",methods="常见问题-删除问题")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			questionMapper.deleteByAttribute("id", id, QuestionFormMap.class);
		}
		return "success";
	}
	
	
	*//**
	 * 跳转查看常见问题信息详情页面
	 * @return
	 *//*
	@RequestMapping("look_question")
	public String look_article(Model model){
		id = getPara("id");
		model.addAttribute("questionInfo",questionMapper.findbyFrist("id", id, QuestionFormMap.class));
		return Common.BACKGROUND_PATH + "/system/question/look_question";
	}*/
}
