package com.aiTrans.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.AuditorFormMap;
import com.aiTrans.entity.AuditorProjectFormMap;
import com.aiTrans.entity.CustomerFormMap;
import com.aiTrans.entity.DomainFormMap;
import com.aiTrans.entity.FileFormMap;
import com.aiTrans.entity.ProcedureTypeFormMap;
import com.aiTrans.entity.ProjectFormMap;
import com.aiTrans.entity.ProjectTypeFormMap;
import com.aiTrans.entity.ProofreaderFormMap;
import com.aiTrans.entity.RewardFormMap;
import com.aiTrans.entity.TraProjectFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.entity.ProofProjectFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.AuditorMapper;
import com.aiTrans.mapper.AuditorProjectMapper;
import com.aiTrans.mapper.CustomerMapper;
import com.aiTrans.mapper.DomainMapper;
import com.aiTrans.mapper.FileMapper;
import com.aiTrans.mapper.ProcedureTypeMapper;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.mapper.ProjectTypeMapper;
import com.aiTrans.mapper.ProofProjectMapper;
import com.aiTrans.mapper.ProofreaderMapper;
import com.aiTrans.mapper.TraProjectMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.mapper.RewardMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
import com.aiTrans.util.EmailUtils;
import com.aiTrans.util.JsonUtils;
import com.aiTrans.util.POIUtils;

/**
 * project项目管理
 * @author SS
 * @version 4.0v
 */
@Controller
@RequestMapping("/project/")
public class ProjectController extends BaseController {
	@Inject
	private ProjectMapper projectMapper;
	@Inject
	private CustomerMapper customerMapper;
	@Inject
	private ProcedureTypeMapper processesTypeMapper;
	@Inject
	private ProjectTypeMapper projectTypeMapper;
	@Inject
	private TranslatorMapper transferMapper;
	@Inject
	private AuditorMapper auditorMapper;
	@Inject
	private ProofreaderMapper vierfierMapper;
	@Inject
	private RewardMapper rewardsMapper;
	@Inject
	private DomainMapper domainMapper;
	@Inject
	private TraProjectMapper transProjectMapper;
	@Inject
	private ProofProjectMapper verifyProjectMapper;
	@Inject
	private AuditorProjectMapper auditorProjectMapper;
	@Inject
	private FileMapper fileMapper;
	
	
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	private String typeId;//二级列表中对应的上级列表id
	private String id;//对应表的主键
	private String table;//对应表的表名
	private String fk;//对应主表(ly_project)的外键
	//2017年4月19日14:54:23
	private String type;//对应三种不用的菜单

	public  void addFile(String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId){
		FileFormMap fileFormMap = new FileFormMap();
		fileFormMap.put("fileName",fileName);
		fileFormMap.put("uploadTime", date);
		fileFormMap.put("uploader", uploaderId);
		fileFormMap.put("fileType",fileType);
		fileFormMap.put("fileUrl", fileURl);
		fileFormMap.put("parentId",parentId);
		try {
			fileMapper.addEntity(fileFormMap);
		} catch (Exception e) {
			System.out.println("文件添加失败！！！");
			e.printStackTrace();
		}
	}
	
	//多文件上传
	public void upload(String oldpath,CommonsMultipartFile files[],Object id){
		String path=oldpath.substring(0,oldpath.lastIndexOf("/"));
		
		//文件上传者
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
		int uploaderId = (int) userFormMap.get("id");
		
		//创建时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		Date date = new Date();
		String uploadTime=sdf.format(date);  
		
		
		//创建path目录下的文件对象
		File saveDir = new File(path);
		//判断文件是否存在
		if(!saveDir.exists()){
			//String parent=path.substring(0,path.indexOf("/Aitrans"));
			
			//项目名称保存路径   path
			//项目名称
			String parojectname=path.substring(path.lastIndexOf("/")+1);
			
			
			//项目名称父路径----客户名称保存路径
			String cusURl=path.substring(0, path.lastIndexOf("/"));
			//客户名称
			String cusName=cusURl.substring(cusURl.lastIndexOf("/")+1);
			
			
			//客户名称父路径----Aitrans保存路径
			String aitransURL=cusURl.substring(0, cusURl.lastIndexOf("/"));
			//Aitrans
			String aitrans= aitransURL.substring(aitransURL.lastIndexOf("/")+1);
			
			
			//服务器中项目保存根目录
			String projectParentURL=aitransURL.substring(0, aitransURL.lastIndexOf("/"));
			//根目录名称
			String projectParentName=projectParentURL.substring(projectParentURL.lastIndexOf("/")+1);
			
			
			//开始判断并添加文件记录
			List<FileFormMap> aitransId=fileMapper.findParentId(aitransURL);
			if(aitransId.size()>0){
				List<FileFormMap> cusId=fileMapper.findParentId(cusURl);
				if(cusId.size()>0){
					List<FileFormMap> parojectId=fileMapper.findParentId(path);
					if(parojectId.size()>0){
						saveDir.mkdirs();
					}else{
						Object parentId="";
						List<FileFormMap> cusParentId=fileMapper.findParentId(cusURl);
						for(FileFormMap fileparent:cusParentId){
							parentId=fileparent.get("id");
						}
						//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
						addFile(parojectname,path,"文件夹",uploadTime,uploaderId,parentId);
						saveDir.mkdirs();
					}
				}else{
					Object parentId="";
					List<FileFormMap> cusparentId=fileMapper.findParentId(aitransURL);
					for(FileFormMap fileparent:cusparentId){
						parentId=fileparent.get("id");
					}
					//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
					addFile(cusName,cusURl,"文件夹",uploadTime,uploaderId,parentId);
					
					List<FileFormMap> parojectId=fileMapper.findParentId(path);
					if(parojectId.size()>0){
						saveDir.mkdirs();
					}else{
						Object parentIdcus="";
						List<FileFormMap> cusParentId=fileMapper.findParentId(cusURl);
						for(FileFormMap fileparent:cusParentId){
							parentIdcus=fileparent.get("id");
						}
						//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
						addFile(parojectname,path,"文件夹",uploadTime,uploaderId,parentIdcus);
						saveDir.mkdirs();
					}
					
					
				}
			}else{
				Object parentId="";
				List<FileFormMap> projectParentId=fileMapper.findParentId(projectParentURL);
				for(FileFormMap fileparent:projectParentId){
					parentId=fileparent.get("id");
				}
				//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
				addFile(aitrans,aitransURL,"文件夹",uploadTime,uploaderId,parentId);

				List<FileFormMap> cusId=fileMapper.findParentId(cusURl);
				if(cusId.size()>0){
					List<FileFormMap> parojectId=fileMapper.findParentId(path);
					if(parojectId.size()>0){
						saveDir.mkdirs();
					}else{
						Object parentIdproject="";
						List<FileFormMap> cusParentId=fileMapper.findParentId(cusURl);
						for(FileFormMap fileparent:cusParentId){
							parentId=fileparent.get("id");
						}
						//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
						addFile(parojectname,path,"文件夹",uploadTime,uploaderId,parentId);
						saveDir.mkdirs();
					}
				}else{
					Object parentIdproject="";
					List<FileFormMap> cusparentId=fileMapper.findParentId(aitransURL);
					for(FileFormMap fileparent:cusparentId){
						parentIdproject=fileparent.get("id");
					}
					//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
					addFile(cusName,cusURl,"文件夹",uploadTime,uploaderId,parentIdproject);
					
					List<FileFormMap> parojectId=fileMapper.findParentId(path);
					if(parojectId.size()>0){
						saveDir.mkdirs();
					}else{
						Object parentIdcus="";
						List<FileFormMap> cusParentId=fileMapper.findParentId(cusURl);
						for(FileFormMap fileparent:cusParentId){
							parentIdcus=fileparent.get("id");
						}
						//String fileName,String fileURl,String fileType,String date,int uploaderId,Object parentId
						addFile(parojectname,path,"文件夹",uploadTime,uploaderId,parentIdcus);
						saveDir.mkdirs();
					}
					
					
				}
			}
		}
		
			//创建path原稿目录下的文件对象
			File manuscriptDir = new File(path+"/原稿文件");
			//创建path校对目录下的文件对象
			File proofDir = new File(path+"/校对文件");
			//创建path审核目录下的文件对象
			File auditoryDir = new File(path+"/审核文件");
			//创建path返稿目录下的文件对象
			File backDir = new File(path+"/返稿文件");
			//创建path备注目录下的文件对象
			File introDir = new File(path+"/备注文件");
			
			List<FileFormMap> pathIdList=fileMapper.findParentId(path);
			Object fileParentId="";
			if(pathIdList.size()>0){
				for(FileFormMap pathId:pathIdList){
					fileParentId=pathId.get("id");
				}
				
				if(!manuscriptDir.exists()){
					addFile("原稿文件",path+"/原稿文件","文件夹",uploadTime,uploaderId,fileParentId);
					manuscriptDir.mkdirs();
				}
				if(!proofDir.exists()){
					addFile("校对文件",path+"/校对文件","文件夹",uploadTime,uploaderId,fileParentId);
					proofDir.mkdirs();
				}
				if(!auditoryDir.exists()){
					addFile("审核文件",path+"/审核文件","文件夹",uploadTime,uploaderId,fileParentId);
					auditoryDir.mkdirs();
				}
				if(!backDir.exists()){
					addFile("返稿文件",path+"/返稿文件","文件夹",uploadTime,uploaderId,fileParentId);
					backDir.mkdirs();
				}
				if(!introDir.exists()){
					addFile("备注文件",path+"/备注文件","文件夹",uploadTime,uploaderId,fileParentId);
					introDir.mkdirs();
				}
			}
			
			
			for(int i=0;i<files.length;i++){
				FileFormMap fileFormMap=new FileFormMap();
				if(!files[i].isEmpty()){
					// 获取这个文件的传过来的名字
					String name=files[i].getOriginalFilename();
					String fileName=name.substring(0, name.lastIndexOf("."));
					String fileEit = name.substring(name.lastIndexOf(".") + 1).toLowerCase().trim();
					
					
//					重新编辑这个文件的名字以日期组合，让它不会重复。filename最终上传文件文件名称
				    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				    String sqlfileName=df.format(new Date()) +fileName;
				    fileName = sqlfileName+"."+fileEit;
				    
				    
					//确保数据库添加和服务器中上传同时，所以先添加，后上传
					//查找父id
				   /* if(!"E:/".equals(path)){
				    	path=path.substring(0,path.lastIndexOf("/"));
				    }*/
			
					List<FileFormMap> fileParentList=fileMapper.findParentId(oldpath);
					for(FileFormMap fileParent:fileParentList){
						if(fileParent!=null){
							fileFormMap.put("parentId", fileParent.get("id"));
						}else{
							fileFormMap.put("parentId","");
						}
					}
					
					fileFormMap.put("fileName",sqlfileName);
					fileFormMap.put("uploadTime", uploadTime);
					fileFormMap.put("uploader", uploaderId);
					fileFormMap.put("fileFormat", fileEit);
					fileFormMap.put("fileType", "文件");
					fileFormMap.put("fileUrl", oldpath);
					fileFormMap.put("projectId", id);
					
					
					try {
						fileMapper.addFile(fileFormMap);
						
						 //创建保存文件的对象路径为创建的路径saveDir，名字为组合出来的新名字
						File saveFile = new File(oldpath,fileName);
						
						//从方法传入的人间对象获取输入流
						InputStream instream;
						try {
							instream = files[i].getInputStream();
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
						} catch (IOException e) {
							System.out.println("上传失败！");
							e.printStackTrace();
						}
						
						
					} catch (Exception e1) {
						System.out.println("添加失败！");
						e1.printStackTrace();
					}
				  
		     
				}	
			}
	}
	
	/**
	 * 展示所有未完成的项目,从项目表中查询
	 */
	@RequestMapping("project_flow")
	public String project_flow(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/project/project_flow";
	}
	
	@ResponseBody
	@RequestMapping("find_project_flow_by_page")
	public PageView find_project_flow_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column", column);
		projectFormMap.put("sort", sort);

		/*SimpleDateFormat  sdf=new  SimpleDateFormat("yyyy-MM-dd");
		String createTime=getPara("projectFormMap.createTime");
		String completeTime=getPara("projectFormMap.completeTime");
		if(createTime!=null && createTime!=""){
			sdf.parse(createTime);
			projectFormMap.put("createTime",sdf.parse(createTime));
		}
		if(completeTime!=null &&completeTime!=""){
			sdf.parse(completeTime);
			projectFormMap.put("completeTime",completeTime);
		}
		*/
	    pageView.setRecords(projectMapper.findProjectflowPage(projectFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
	    return pageView;
	}
	
	/**
	 * 展示的是该权限用户在此功能中的按钮资源
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_customer")
	public String list_customer(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/project/list_customer";
	}
	
	@RequestMapping("list_project_type")
	public String list_project_type(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/project/list_project_type";
	}
	
	@RequestMapping("list_processes_type")
	public String list_processes_type(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/project/list_processes_type";
	}
	
	//二级列表入口
	@RequestMapping("look_project")
	public String look_project_test_1(Model model) throws Exception {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		/*String customerId = request.getParameter("customerId");
		String processesTypeId = request.getParameter("processesTypeId");
		String projectTypeId = request.getParameter("projectTypeId");
		String type = request.getParameter("type");*/
		
		model.addAttribute("res", findByRes());
		
		//2017年4月20日14:47:19	
		/*点击查看详情后需要将客户id/流程id/项目类型id发送给前台,以便
		 *二级页面的添加操作
		*/
		id = request.getParameter("id");
		type = request.getParameter("type");
		System.out.println(id+""+type);
		model.addAttribute("id",id);
		model.addAttribute("type",type);
		if("customer".equals(type)){
			//model.addAttribute("customerId",customerId);
			//id = customerId;
			table = "customerD";
			fk = "customerId";
		}else if("processesType".equals(type)){
			//model.addAttribute("processesTypeId",processesTypeId);
			//id = processesTypeId;
			table = "procedureTypeD";
			fk = "procedureTypeId";
		}else if("projectType".equals(type)){
			//model.addAttribute("projectTypeId",projectTypeId);
			//id = projectTypeId;
			table = "projectTypeD";
			fk = "projectTypeId";
		}else if("projectState".equals(type)){
			table = "projectStateD";
			fk = "projectStateId";
		}
		else if("projectflow".equals(type)){
			int pid=Integer.parseInt(id);
			ProjectFormMap projectFormMap=new ProjectFormMap();
			RewardFormMap rewardFormMap = new RewardFormMap();
			
			
			projectFormMap=projectMapper.lookProject(pid);
			rewardFormMap=rewardsMapper.lookReward(pid);
			
			if(rewardFormMap.get("priceKilo")==null){
				rewardFormMap.put("priceKilo",0);
			}else{
				String companyReward=rewardFormMap.get("priceKilo").toString();
				rewardFormMap.put("priceKilo", (Object)companyReward.substring(0, companyReward.length()-2));
			}
			
			
			if(rewardFormMap.get("translatorFeeKilo")==null){
				rewardFormMap.put("translatorFeeKilo",0);
			}else{
				String transReward=rewardFormMap.get("translatorFeeKilo").toString();
				rewardFormMap.put("translatorFeeKilo", (Object)transReward.substring(0, transReward.length()-2));
			}
			
			
			if(rewardFormMap.get("auditoryFeeKilo")==null){
				rewardFormMap.put("auditoryFeeKilo",0);
			}else{
				String auditoryReward=rewardFormMap.get("auditoryFeeKilo").toString();
				rewardFormMap.put("auditoryFeeKilo", (Object)auditoryReward.substring(0, auditoryReward.length()-2));
			}
			
			
			if(rewardFormMap.get("proofFeeKilo")==null){
				rewardFormMap.put("proofFeeKilo",0);
			}else{
				String verifyReward=rewardFormMap.get("proofFeeKilo").toString();
				rewardFormMap.put("proofFeeKilo", (Object)verifyReward.substring(0, verifyReward.length()-2));
			}
			
			model.addAttribute("projectFormMap",projectFormMap );
			model.addAttribute("rewardFormMap",rewardFormMap );
			//System.out.println(projectFormMap.get("fileURL"));
			/*Map<String, String> fileNameMap = new HashMap<String, String>();*/
			List<FileFormMap> fileList=new ArrayList<FileFormMap>();
			
			if(projectFormMap.getStr("fileURL")!=null){
			      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
				        /* listfile(new File(projectFormMap.getStr("fileURL")),fileNameMap);*/
				         /*model.addAttribute("fileNameMap", fileNameMap); */ 
				         fileList=listfileDB(id);
				         model.addAttribute("fileList", fileList);
			}
			
	                
			return Common.BACKGROUND_PATH + "/system/project/look_project_flow";
		}return Common.BACKGROUND_PATH + "/system/project/look_project";
	}
	
	//查看客户/项目类型/流程种类的项目详情
	@RequestMapping("look")
	public String look(Model model) throws Exception{
		String projectId = getPara("projectId");
		List<ProjectFormMap> list = projectMapper.findByAttribute("id", projectId, ProjectFormMap.class);
		ProjectFormMap projectFormMap = list.get(0);
/*		
		for(String key : projectFormMap.keySet()){
			System.out.println("---------------------------key="+
		key+",value="+projectFormMap.get(key));
		}
*/		
		int customerId = projectFormMap.getInt("customerId");
		int projectTypeId = projectFormMap.getInt("projectTypeId");
		int processesTypeId = projectFormMap.getInt("procedureTypeId");
/*		
		System.out.println("===="+customerId);
		System.out.println("===="+projectTypeId);
		System.out.println("===="+processesTypeId);
		
		
*/		Map projectMap = new HashMap();
		projectMap.put("projectId", projectId);
		projectMap.put("customerId", customerId);
		projectMap.put("projectTypeId", projectTypeId);
		projectMap.put("processesTypeId", processesTypeId);
		
		System.out.println("-----------------"+projectMapper.findAll(projectMap)); 
		
		
		model.addAttribute("project",projectMapper.findAll(projectMap));
		/*思路不对,饶了大湾儿且不对
		id = getPara("id");//Project对应的外键
		type = getPara("type");
		if("customer".equals(type)){
			table = "customerD";
		}else if("projectType".equals(type)){
			table = "project_typeD";
		}else if("processesType".equals(type)){
			table = "processes_typeD";
		}
		
		Map projectMap = new HashMap();
		projectMap.put("FKid", id);
		projectMap.put("projectId", projectId);
		projectMap.put("table", table);
		
		model.addAttribute("project",projectMapper.findAll(projectMap));*/
		return Common.BACKGROUND_PATH + "/system/project/look_project_2";
	}
	
	/*//转到查看详情页面
	@RequestMapping("look_project")
	public String look_project_by_id(Model model,String pageNow,
			String pageSize,String column,String sort) throws Exception {
		String customerId = getPara("customerId");
		System.out.println("----------------1------------------"+customerId);
		
		if(Common.isNotEmpty(customerId)){
			
			
			model.addAttribute("project", projectMapper.findbyFrist("id", customerId, ProjectFormMap.class));
		}
		
		return Common.BACKGROUND_PATH + "/system/project/look_project";
	}*/
	//通过id查看项目
	@ResponseBody
	@RequestMapping("look_project_by_id")
	public PageView look_project_by_id( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column", column);
		projectFormMap.put("sort", sort);
		
		//这个id是customer的id
		projectFormMap.put("id", id);
		projectFormMap.put("table", table);
		projectFormMap.put("fk", fk);
		System.out.println("-------------1----------------------"+id);
		System.out.println("-------------2----------------------"+table);
		System.out.println("-------------3----------------------"+fk);
		pageView.setRecords(projectMapper.findProjectById(projectFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
//        pageView.setRecords(projectMapper.findProjectById(id,table,fk));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	//查看客户项目分页列表,应该从客户表中查
	@ResponseBody
	@RequestMapping("find_customer_project_by_page")
	public PageView find_customer_project_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		String name = getPara("customerFormMap.name");
		CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
		customerFormMap=toFormMap(customerFormMap, pageNow, pageSize,customerFormMap.getStr("orderby"));
		customerFormMap.put("column", column);
		customerFormMap.put("sort", sort);
		System.out.println(name);
		customerFormMap.put("name", name);
        pageView.setRecords(customerMapper.findCustomerProjectPage(customerFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}

	/*//查看客户项目分页列表
	@ResponseBody
	@RequestMapping("find_customer_project_by_page")
	public PageView find_customer_project_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column", column);
		projectFormMap.put("sort", sort);
        pageView.setRecords(projectMapper.findCustomerProjectPage(projectFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}*/
	//查看项目类型分页列表
	@ResponseBody
	@RequestMapping("find_project_type_by_page")
	public PageView find_project_type_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProjectTypeFormMap projectTypeFormMap = getFormMap(ProjectTypeFormMap.class);
		projectTypeFormMap=toFormMap(projectTypeFormMap, pageNow, pageSize,projectTypeFormMap.getStr("orderby"));
		projectTypeFormMap.put("column", column);
		projectTypeFormMap.put("sort", sort);
		pageView.setRecords(projectMapper.findProjectTypePage(projectTypeFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	//查看流程种类分页列表
	@ResponseBody
	@RequestMapping("find_processes_type_by_page")
	public PageView find_processes_type_by_page( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProcedureTypeFormMap processesTypeFormMap = getFormMap(ProcedureTypeFormMap.class);
		processesTypeFormMap=toFormMap(processesTypeFormMap, pageNow, pageSize,processesTypeFormMap.getStr("orderby"));
		processesTypeFormMap.put("column", column);
		processesTypeFormMap.put("sort", sort);
		String process = getPara("processesFormMap.processesType");
		System.out.println(process);
		processesTypeFormMap.put("processesType", process);
		pageView.setRecords(projectMapper.findProcessesTypePage(processesTypeFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	
	@RequestMapping("addUI")
	public String addUI(Model model) throws Exception {
		type = getPara("type");
		if("customer".equals(type)){
			type = "customer";
			return Common.BACKGROUND_PATH + "/system/project/add_customer";
		}
		if("processesType".equals(type)){
			type = "processesType";
			return Common.BACKGROUND_PATH + "/system/project/add_processes_type";
		}
		if("projectType".equals(type)){
			type = "projectType";
			return Common.BACKGROUND_PATH + "/system/project/add_project_type";
		}
		if("projectflow".equals(type) || "releaseTasks".equals(type)){			
			
			List<CustomerFormMap> customerMapperList = new ArrayList<CustomerFormMap>();
			List<ProcedureTypeFormMap> processesTypeMapperList = new ArrayList<ProcedureTypeFormMap>();
			List<ProjectTypeFormMap> projectTypeMapperList = new ArrayList<ProjectTypeFormMap>();
			List<DomainFormMap> domainMapperList = new ArrayList<DomainFormMap>();
			//List<File> fileList = new ArrayList<File>();
			
			customerMapperList=customerMapper.findIdAndName();
			processesTypeMapperList=processesTypeMapper.findType();
			projectTypeMapperList=projectTypeMapper.findProjectType();
			domainMapperList=domainMapper.findByWhere(new DomainFormMap());
			
			model.addAttribute("customerMapperList", customerMapperList);
			model.addAttribute("processesTypeMapperList", processesTypeMapperList);
			model.addAttribute("projectTypeMapperList", projectTypeMapperList);
			model.addAttribute("domainMapperList", domainMapperList);
			
			//String projectStateId="";
			if("releaseTasks".equals(type)){
				model.addAttribute("projectStateId", "14");
				return Common.BACKGROUND_PATH + "/system/project/add_Release_tasks";
			}
			if("projectflow".equals(type)){
				model.addAttribute("projectStateId", "4");
				return Common.BACKGROUND_PATH + "/system/project/add_project_flow";
			}
		}
		type = getPara("id");
		model.addAttribute("id",id);
		return Common.BACKGROUND_PATH + "/system/project/add_project";
	}
	
	@RequestMapping("addUI_2")
	public String addUI_2(Model model) throws Exception {
		type = getPara("type");
		id = getPara("id");
		model.addAttribute("id",id);
		model.addAttribute("type",type);
		model.addAttribute("projectType",projectTypeMapper.findProjectType());
		model.addAttribute("processesType",processesTypeMapper.findType());
		model.addAttribute("customer",customerMapper.findIdAndName());
		
		if("customer".equals(type)){
			return Common.BACKGROUND_PATH + "/system/project/add_customer_project";
		}
		if("processesType".equals(type)){
			return Common.BACKGROUND_PATH + "/system/project/add_processes_type_project";
		}
		if("projectType".equals(type)){
			return Common.BACKGROUND_PATH + "/system/project/add_project_type_project";
		}return null;
	}

	//通过客户id查找客户的缩写名称
	@ResponseBody
	@RequestMapping("findcustomerIdandabbreviation")
	public List<String> findcustomerIdandabbreviation(Model model){
		String customer=getPara("customerid");
		CustomerFormMap customerFormMap=customerMapper.findbyFrist("id",customer,CustomerFormMap.class);
		
		//客户的缩写名称abbreviation
		String abbreviation=(String) customerFormMap.get("shortName");
		//客户中文名称
		String nameZH=(String) customerFormMap.get("nameZH");
		
		//针对该公司的项目个数 select count(主键) from 表名
		int num=projectMapper.findProjectCountByCustomerId(customer)+1;
		System.out.println(num);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String date =simpleDateFormat.format(new Date());
		
		String newNum =""+num;
		if(num<10){
			newNum=0+newNum;
		}
		String reference="AY"+abbreviation+date+newNum;
		//System.out.println(reference);
		//model.addAttribute("reference", reference);
		List<String> cusName=new ArrayList<String>();
		cusName.add(reference);
		cusName.add(nameZH);
		return cusName;
	}
	
	
	@ResponseBody
	@RequestMapping("addProjectFlow")
	@SystemLog(module="项目管理",methods="项目管理-新增项目")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addProjectFlow(@RequestParam("filename")CommonsMultipartFile files[],String txtGroupsSelect,HttpServletRequest request){
		try {
			type = getPara("type");
			if("projectflow".equals(type)||"releaseTasks".equals(type)){
				//System.out.println("进入添加");
				
				ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
						
				//获取项目的基本信息并映射实体
				//projectname:文件对应的项目名称		
				String projectName=request.getParameter("name");
				String reference=request.getParameter("reference");
				String customerId=request.getParameter("customerId");
				CustomerFormMap customerFormMap=customerMapper.findbyFrist("id",customerId, CustomerFormMap.class);
				String customerName=(String) customerFormMap.get("nameZH");
				projectFormMap.put("customerId",customerId);
				projectFormMap.put("customerReference",request.getParameter("customerReference"));
				projectFormMap.put("reference",reference);
				projectFormMap.put("projectTypeId",request.getParameter("projectTypeId"));
				projectFormMap.put("languagePair",request.getParameter("languagePair"));
				projectFormMap.put("language",request.getParameter("language"));
				projectFormMap.put("procedureTypeId",request.getParameter("procedureTypeId"));
			//	projectFormMap.put("verifierId",request.getParameter("verifierId"));
			//	projectFormMap.put("auditorId",request.getParameter("auditorId"));	
				projectFormMap.put("head",request.getParameter("head"));	
				projectFormMap.put("createTime",request.getParameter("createTime"));	
				projectFormMap.put("completeTime",request.getParameter("completeTime"));
				projectFormMap.put("cooperativeState",request.getParameter("cooperativeState"));
				projectFormMap.put("projectStateId",request.getParameter("projectStateId"));
				projectFormMap.put("description",request.getParameter("description"));
				projectFormMap.put("advance",request.getParameter("advance"));
				projectFormMap.put("domainId",request.getParameter("domainId"));
				//执行文件上传功能，为上传设置路径
				//String path="/Aitrans/uploadFile/aitrans/"+customerName+"/"+reference+"-"+projectName;
				String path=request.getParameter("url");
				//System.out.println(files.length);
				
				projectFormMap.put("fileURL", path);
				projectFormMap.put("name", projectName);
				//添加项目基本信息projectD
				projectMapper.addEntity(projectFormMap);
				
				List<ProjectFormMap> projectList =new ArrayList<ProjectFormMap>();
				
				//System.out.println("获取添加项目的id,作为下面添加稿酬信息的id");
				projectList=projectMapper.findByreference(projectFormMap);
				//接收关于稿酬信息
				int wordlength=Integer.parseInt(request.getParameter("wordLength"));
				int imagelength=Integer.parseInt(request.getParameter("imageLength"));
				int length=wordlength+imagelength;
				Double companyReward=Double.parseDouble(request.getParameter("companyReward"));
				Double translatorReward=Double.parseDouble(request.getParameter("translatorFeeKilo"));
				Double proofReward=Double.parseDouble(request.getParameter("proofFeeKilo"));
				Double auditoryReward=Double.parseDouble(request.getParameter("auditoryFeeKilo"));
				
				for(ProjectFormMap projectId:projectList){
					RewardFormMap rewardsFormMap = getFormMap(RewardFormMap.class);
					rewardsFormMap.put("priceTotal",(length*companyReward/1000));						
					rewardsFormMap.put("length",length);
					rewardsFormMap.put("wordLength",wordlength);
					rewardsFormMap.put("imageLength",imagelength);
					rewardsFormMap.put("priceKilo",companyReward);
					rewardsFormMap.put("translatorFeeKilo",translatorReward);
					rewardsFormMap.put("translatorFeeTotal",translatorReward*length/1000);
					rewardsFormMap.put("proofFeeKilo",proofReward);
					rewardsFormMap.put("proofFeeTotal",proofReward*length/1000);
					rewardsFormMap.put("auditoryKilo",auditoryReward);
					rewardsFormMap.put("auditoryTotal",auditoryReward*length/1000);
					rewardsFormMap.put("id",projectId.get("id"));
					upload(path+"/原稿文件",files,projectId.get("id"));
					rewardsMapper.addEntity(rewardsFormMap);
				}

				if("projectflow".equals(type)){
					//接收译员
					String[] transId = request.getParameterValues("transferId");
					for(ProjectFormMap projectId:projectList){
						 if(transId!=null){
							for(int i=0;i<transId.length;i++){
								/*String trans = Arrays.toString(transId);
								trans = trans.substring(1, trans.length()-1);
								trans = trans.replace(" ", "");*/
								TraProjectFormMap tp=new TraProjectFormMap();
								tp.put("translatorId",transId[i]);
								tp.put("projectId",projectId.get("id"));
								transProjectMapper.addEntity(tp);
							}
						}else{
							TraProjectFormMap tp=new TraProjectFormMap();
							tp=new TraProjectFormMap();
							tp.put("translatorId","1138");
							tp.put("projectId",projectId.get("id"));
							transProjectMapper.addEntity(tp);
						}
					}
					
					
					//接收校验员
					String[] verifierId = request.getParameterValues("verifierId");
					for(ProjectFormMap projectId:projectList){
					  if(verifierId!=null){
							for(int i=0;i<verifierId.length;i++){
								
									/*String verify = Arrays.toString(verifierId);
									verify = verify.substring(1, verify.length()-1);
									verify = verify.replace(" ", "");*/
									ProofProjectFormMap vp=new ProofProjectFormMap();
									vp.put("proofreaderId",verifierId[i]);
									vp.put("projectId",projectId.get("id"));
									verifyProjectMapper.addEntity(vp);
							}
						}else{
							ProofProjectFormMap vp=new ProofProjectFormMap();
							vp=new ProofProjectFormMap();
							vp.put("proofreaderId","89");
							vp.put("projectId",projectId.get("id"));
							verifyProjectMapper.addEntity(vp);
						}
					}

					
					//接收审核员
					String[] auditorId = request.getParameterValues("auditorId");
					
					for(ProjectFormMap projectId:projectList){
						 if(auditorId!=null){
							for(int i=0;i<auditorId.length;i++){
								/*String verify = Arrays.toString(verifierId);
								verify = verify.substring(1, verify.length()-1);
								verify = verify.replace(" ", "");*/
								AuditorProjectFormMap ap=new AuditorProjectFormMap();
								ap.put("auditorId",auditorId[i]);
								ap.put("projectId",projectId.get("id"));
								auditorProjectMapper.addEntity(ap);
							}
						}else{
							AuditorProjectFormMap ap=new AuditorProjectFormMap();
							ap=new AuditorProjectFormMap();
							ap.put("auditorId","89");
							ap.put("projectId",projectId.get("id"));
							auditorProjectMapper.addEntity(ap);
						}
				}
				
				}
				
			}else {
				ProjectFormMap projectFormMap =getFormMap(ProjectFormMap.class);				
				for(String key : projectFormMap.keySet()){
					System.out.println("----------------------key="+
				key+",value="+projectFormMap.getStr(key));
				}
				
				
				projectMapper.addEntity(projectFormMap);
			}
			/*else if("customer_project".equals(type)){
				System.out.println("------addEntity--------customerProject---------");
				ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
				for(String key : projectFormMap.keySet()){
					System.out.println("----------------------key="+
				key+",value="+projectFormMap.getStr(key));
				}
				projectMapper.addCustomerProject(projectFormMap);
				ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
				projectMapper.addEntity(projectFormMap);
			}else if("projectType_project".equals(type)){
				
			}*/
			
		} catch (Exception e) {
			 throw new SystemException("添加项目异常");
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="项目管理",methods="项目管理-新增项目")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity(String txtGroupsSelect){
		try {
			type = getPara("type");
			System.out.println("-----addEntity0----------------"+type);
			if("customer".equals(type)){
				CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
				customerMapper.addEntity(customerFormMap);
				ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
				projectMapper.addEntity(projectFormMap);//新增后返回新增信息
			}else if("processesType".equals(type)){
				ProcedureTypeFormMap processesTypeFormMap = getFormMap(ProcedureTypeFormMap.class);
				projectMapper.addEntity(processesTypeFormMap);
			}else if("projectType".equals(type)){
				ProjectTypeFormMap projectTypeFormMap = getFormMap(ProjectTypeFormMap.class);
				projectTypeMapper.addEntity(projectTypeFormMap);
			}else {
				ProjectFormMap projectFormMap =getFormMap(ProjectFormMap.class);				
				for(String key : projectFormMap.keySet()){
					System.out.println("----------------------key="+
				key+",value="+projectFormMap.getStr(key));
				}
				
				
				projectMapper.addEntity(projectFormMap);
			}
			/*else if("customer_project".equals(type)){
				System.out.println("------addEntity--------customerProject---------");
				ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
				for(String key : projectFormMap.keySet()){
					System.out.println("----------------------key="+
				key+",value="+projectFormMap.getStr(key));
				}
				projectMapper.addCustomerProject(projectFormMap);
				ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
				projectMapper.addEntity(projectFormMap);
			}else if("projectType_project".equals(type)){
				
			}*/
			
		} catch (Exception e) {
			 throw new SystemException("添加项目异常");
		}
		return "success";
	}

	@RequestMapping("statistics")
	  public String statistics(Model model)
	    throws Exception
	  {
	    String wheres = getPara("ids");
	    String[] wheresArr = wheres.split(",");
	    List<ProjectFormMap> projectList = new ArrayList();
	    ProjectFormMap projectFormMap = (ProjectFormMap)getFormMap(ProjectFormMap.class);
	    
	    double priceTotal = 0.0D;//总收入
	    
	    int length = 0;
	    
	    double transTotal = 0.0D;
	    
	    double proofTotal = 0.0D;
	    
	    double auditoryTotal = 0.0D;
	    if ((wheresArr != null) && 
	      (wheresArr.length > 0))
	    {
	      projectFormMap.put("cname", wheresArr[0]);
	      if (wheresArr.length > 1)
	      {
	        projectFormMap.put("name", wheresArr[1]);
	        if (wheresArr.length > 2)
	        {
	          projectFormMap.put("languagePair", wheresArr[2]);
	          if (wheresArr.length > 3)
	          {
	            projectFormMap.put("tname", wheresArr[3]);
	            if (wheresArr.length > 4)
	            {
	              projectFormMap.put("vname", wheresArr[4]);
	              if (wheresArr.length > 5)
	              {
	                projectFormMap.put("aname", wheresArr[5]);
	                if (wheresArr.length > 6)
	                {
	                  projectFormMap.put("projectType", wheresArr[6]);
	                  if (wheresArr.length > 7)
	                  {
	                    projectFormMap.put("processesTypeId", wheresArr[7]);
	                    if (wheresArr.length > 8)
	                    {
	                      projectFormMap.put("head", wheresArr[8]);
	                      if (wheresArr.length > 9)
	                      {
	                        projectFormMap.put("projectState", wheresArr[9]);
	                        if (wheresArr.length > 10)
	                        {
	                          projectFormMap.put("createTime", wheresArr[10]);
	                          if (wheresArr.length > 11) {
	                            projectFormMap.put("completeTime", wheresArr[11]);
	                          }
	                        }
	                      }
	                    }
	                  }
	                }
	              }
	            }
	          }
	        }
	      }
	    }
	    projectList = this.projectMapper.findProjectflowPage(projectFormMap);
	    for (ProjectFormMap project : projectList)
	    {
	      int id = project.getInt("id").intValue();
	      
	      RewardFormMap reward =rewardsMapper.findbyFrist("id", id+"", RewardFormMap.class);
	      if (reward != null)
	      {
	        priceTotal += Double.parseDouble(reward.get("priceTotal").toString());
	        length += Integer.parseInt(reward.get("length").toString());
	        if ((reward.get("translatorFeeTotal") != null) && (reward.get("translatorFeeTotal") != "")) {
	          transTotal += Double.parseDouble(reward.get("translatorFeeTotal").toString());
	        }
	        if ((reward.get("proofFeeTotal") != null) && (reward.get("proofFeeTotal") != "")) {
	          proofTotal += Double.parseDouble(reward.get("proofFeeTotal").toString());
	        }
	        if ((reward.get("auditoryFeeTotal") != null) && (reward.get("auditoryFeeTotal") != "")) {
	          auditoryTotal += Double.parseDouble(reward.get("auditoryFeeTotal").toString());
	        }
	      }
	    }
	    double price = transTotal + proofTotal + auditoryTotal;//支出
	    
	    double total = priceTotal - price;//毛利
	    
	    model.addAttribute("price", Double.valueOf(Math.rint(price)));
	    model.addAttribute("total", Double.valueOf(Math.rint(total)));
	    model.addAttribute("priceTotal", Double.valueOf(Math.rint(priceTotal)));
	    model.addAttribute("length", Double.valueOf(Math.rint(length)));
	    model.addAttribute("transTotal", Double.valueOf(Math.rint(transTotal)));
	    model.addAttribute("proofTotal", Double.valueOf(Math.rint(proofTotal)));
	    model.addAttribute("auditoryTotal", Double.valueOf(Math.rint(auditoryTotal)));
	    
	    return "WEB-INF/jsp/system/statistics/statistics";
	  }
	
	
	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="项目管理",methods="项目管理-删除项目")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		type = getPara("type");
		String[] ids = getParaValues("ids");
		for (String id : ids) {
			if("customer".equals(type)){
				customerMapper.deleteByAttribute("id", id, CustomerFormMap.class);
				return "success";
			}else if("projectType".equals(type)){
				projectMapper.deleteByAttribute("id", id, ProjectTypeFormMap.class);
				return "success";
			}else if("processesType".equals(type)){
				projectMapper.deleteByAttribute("id", id, ProcedureTypeFormMap.class);
				return "success";
			}else if("projectflow".equals(type)){
				projectMapper.deleteByAttribute("id", id, ProjectFormMap.class);
				return "success";
			}else {
				projectMapper.deleteByAttribute("id", id, ProjectFormMap.class);
				return "success";
			}
		}
		return "wrong";
	}

	@RequestMapping("editUI")
	public String editUI(Model model) throws Exception {
		id = getPara("id");
		System.out.println(id);
		type = getPara("type");
		if("customer".equals(type)){
			if(Common.isNotEmpty(id)){
				model.addAttribute("customer", customerMapper.findbyFrist("id", id, CustomerFormMap.class));
			}
			return Common.BACKGROUND_PATH + "/system/project/edit_customer";
		}else if("processesType".equals(type)){
			model.addAttribute("processesInfo",processesTypeMapper.findbyFrist("id", id, ProcedureTypeFormMap.class));
			model.addAttribute("processesType",processesTypeMapper.findType());
			return Common.BACKGROUND_PATH + "/system/project/edit_processes_type";
		}else if("projectType".equals(type)){
			model.addAttribute("projectTypeInfo",projectTypeMapper.findbyFrist("id", id, ProjectTypeFormMap.class));
			return Common.BACKGROUND_PATH + "/system/project/edit_project_type";
		}else if("projectflow".equals(type)||"releaseTasks".equals(type)){
			//System.out.println("进入projectflowEDIT");
			
			List<ProcedureTypeFormMap> processesTypeMapperList = new ArrayList<ProcedureTypeFormMap>();
			List<ProjectTypeFormMap> projectTypeMapperList = new ArrayList<ProjectTypeFormMap>();
			List<DomainFormMap> domainMapperList = new ArrayList<DomainFormMap>();
			List<ProofreaderFormMap> verifyList = new ArrayList<ProofreaderFormMap>();
			List<AuditorFormMap> auditorList = new ArrayList<AuditorFormMap>();
			List<TranslatorFormMap> transList = new ArrayList<TranslatorFormMap>();
			List<String> fileList = new ArrayList<String>();
			
			processesTypeMapperList=processesTypeMapper.findType();
			projectTypeMapperList=projectTypeMapper.findProjectType();
			
			model.addAttribute("processesTypeMapperList", processesTypeMapperList);
			model.addAttribute("projectTypeMapperList", projectTypeMapperList);
			
			ProjectFormMap projectFormMap = new ProjectFormMap();
			projectFormMap=projectMapper.findbyFrist("id", id, ProjectFormMap.class);			
			
			String url=projectFormMap.getStr("fileURL");
			File file=new File(url);
			File[] filelist=file.listFiles();
			for(File fileInfo :filelist){
				if(fileInfo.isDirectory()){
					fileList.add(fileInfo.getName());
				}
			}
			/*for(String key : projectFormMap.keySet()){
				System.out.println("--------------------------------------------key="+key+",value="+projectFormMap.get(key));
			}*/
			model.addAttribute("projectflowInfo",projectFormMap);
			model.addAttribute("fileInfo",fileList);
			model.addAttribute("customerInfo",customerMapper.findById(projectFormMap));
			
			if("projectflow".equals(type)){
				domainMapperList=domainMapper.findByWhere(new DomainFormMap());
				model.addAttribute("domainMapperList", domainMapperList);
			
			
				//查找校验员
				int projectId=Integer.parseInt(id);
				for(ProofProjectFormMap verifyProject:verifyProjectMapper.findByProjectid(projectId)){
					String verifyId=verifyProject.getInt("proofreaderId").toString();
					verifyList.add(vierfierMapper.findbyFrist("id", verifyId, ProofreaderFormMap.class));
				}
				
				//查找译员
				for(TraProjectFormMap transProject:transProjectMapper.findByProjectid(projectId)){
					String transferId=transProject.getInt("translatorId").toString();
					transList.add(transferMapper.findbyFrist("id", transferId, TranslatorFormMap.class));
				}
				
				//查找审核员
				for(AuditorProjectFormMap auditorProject:auditorProjectMapper.findByProjectid(projectId)){
					String auditoryId=auditorProject.getInt("auditorId").toString();
					auditorList.add(auditorMapper.findbyFrist("id", auditoryId, AuditorFormMap.class));
				}
				
				model.addAttribute("verifyList",verifyList);
				model.addAttribute("auditorList",auditorList);
				model.addAttribute("transList",transList);
				return Common.BACKGROUND_PATH + "/system/project/edit_project_flow";
			}else{
				return Common.BACKGROUND_PATH + "/system/project/edit_release_tasks";
			}

			
			
			
		}return null;
	}
	
	@RequestMapping("editUI_2")
	public String editUI_2(Model model) throws Exception {
		id = getPara("id");
		type = getPara("type");
		typeId = getPara("typeId");
		if(Common.isNotEmpty(id)){
			model.addAttribute("project", projectMapper.findbyFrist("id", id, ProjectFormMap.class));
		}
		if("customer".equals(type)){
			return Common.BACKGROUND_PATH + "/system/project/edit_customer_project";
		}else if("processesType".equals(type)){
			return Common.BACKGROUND_PATH + "/system/project/edit_processes_type_project";
		}else if("projectType".equals(type)){
			return Common.BACKGROUND_PATH + "/system/project/edit_project_type_project";
		}return null;
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="项目管理",methods="项目管理-修改项目")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity() throws Exception {
		type = getPara("type");
		System.out.println("--------------editEntity-----------------"+type);
		if("customer".equals(type)){
			CustomerFormMap customerFormMap = getFormMap(CustomerFormMap.class);
			customerFormMap.put("id", id);
			customerMapper.editEntity(customerFormMap);
			return "success";
		}else if("processesType".equals(type)){
			ProcedureTypeFormMap processesTypeFormMap = getFormMap(ProcedureTypeFormMap.class);
			processesTypeFormMap.put("id", id);
			processesTypeMapper.editEntity(processesTypeFormMap);
			return "success";
		}else if("projectType".equals(type)){
			ProjectTypeFormMap projectTypeFormMap = getFormMap(ProjectTypeFormMap.class);
			projectTypeFormMap.put("id", id);
			projectTypeMapper.editEntity(projectTypeFormMap);
			return "success";
		}else {
			ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
			
			for(String key : projectFormMap.keySet()){
				System.out.println("key="+key+",value="+projectFormMap.getStr(key));
			}
			projectFormMap.put("id", id);
			projectMapper.editEntity(projectFormMap);
			/*此时id是project的id,编辑成功后就会调用look_project_by_id()方法展示二级列表数据
			 * 所以应该让id换为customer的id
			 */
			id = typeId;
			return "success";
		}
		/*ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap.put("id", id);
		projectMapper.editEntity(projectFormMap);*/
		
		
	}
	
	@ResponseBody
	@RequestMapping("editProjectFlow")
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="项目管理",methods="项目管理-修改项目")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editProjectFlow(@RequestParam("filename")CommonsMultipartFile files[],HttpServletRequest request) throws Exception {
		type = getPara("type");
		System.out.println("--------------editEntity-----------------"+type);
		if("projectflow".equals(type)||"releaseTasks".equals(type)){
			String projectName=request.getParameter("projectFormMap.name");
			String reference=request.getParameter("projectFormMap.reference");
			String fileURL=request.getParameter("url");
			String projectStateId = request.getParameter("projectFormMap.projectStateId");
			String sonPath=request.getParameter("sonURL");
			ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
			
			String customerId=request.getParameter("projectFormMap.customerId");
			//CustomerFormMap customerFormMap=customerMapper.findbyFrist("id",customerId, CustomerFormMap.class);
			//String customerName=(String) customerFormMap.get("name");
			projectFormMap.put("customerId",customerId);
			projectFormMap.put("reference",reference );
			projectFormMap.put("customerReference", request.getParameter("projectFormMap.customerReference"));
			projectFormMap.put("projectTypeId", request.getParameter("projectFormMap.projectTypeId"));
			projectFormMap.put("languagePair", request.getParameter("projectFormMap.languagePair"));
		//	projectFormMap.put("transferId", request.getParameter("projectFormMap.transferId"));
			projectFormMap.put("procedureTypeId", request.getParameter("projectFormMap.procedureTypeId"));
		//	projectFormMap.put("verifierId", request.getParameter("projectFormMap.verifierId"));
		//	projectFormMap.put("auditorId", request.getParameter("projectFormMap.auditorId"));
			projectFormMap.put("head", request.getParameter("projectFormMap.head"));
			projectFormMap.put("createTime", request.getParameter("projectFormMap.createTime"));
			projectFormMap.put("completeTime", request.getParameter("projectFormMap.completeTime"));
			projectFormMap.put("projectStateId",projectStateId);
			projectFormMap.put("description",request.getParameter("projectFormMap.description"));
			projectFormMap.put("cooperativeState",request.getParameter("cooperativeState"));
			projectFormMap.put("domainId",request.getParameter("domainId"));
			projectFormMap.put("id", id);
			System.out.println("================修改project=================id="+id);
			
			//执行文件上传功能
			
			upload(fileURL+"/"+sonPath,files,id);
			projectFormMap.put("fileURL", fileURL);
			projectFormMap.put("name", projectName);
			projectMapper.editEntity(projectFormMap);
			
			if("projectflow".equals(type)){
				//接收译员
				String[] transId = request.getParameterValues("translatorId");
				if(transId!=null){
					for(String tid:transId){
							/*String trans = Arrays.toString(transId);
							trans = trans.substring(1, trans.length()-1);
							trans = trans.replace(" ", "");*/
						TraProjectFormMap tp=new TraProjectFormMap();
							tp.put("translatorId",tid);
							tp.put("projectId",id);
							//transProjectMapper.findById(tp);
							if(transProjectMapper.findById(tp).size()>0){
								transProjectMapper.deleteByNames(tp);
							};
							
							transProjectMapper.addEntity(tp);
						}		
				}else{
					TraProjectFormMap	tp=new TraProjectFormMap();
					tp.put("translatorId","1138");
					tp.put("projectId",id);
					//transProjectMapper.findById(tp);
					if(transProjectMapper.findById(tp).size()>0){
						transProjectMapper.deleteByNames(tp);
					};
					
					transProjectMapper.addEntity(tp);
				}
				
				
				//接收校验员
				String[] verifierId = request.getParameterValues("proofreaderId");
			
					if(verifierId!=null){
						for(String vid:verifierId){
							/*String verify = Arrays.toString(verifierId);
							verify = verify.substring(1, verify.length()-1);
							verify = verify.replace(" ", "");*/
							ProofProjectFormMap vp=new ProofProjectFormMap();
							vp.put("proofreaderId",vid);
							vp.put("projectId",id);
							if(verifyProjectMapper.findById(vp).size()>0){
								verifyProjectMapper.deleteByNames(vp);
							}
							
							verifyProjectMapper.addEntity(vp);
						}
				}else{
					ProofProjectFormMap vp=new ProofProjectFormMap();
					vp.put("proofreaderId","89");
					vp.put("projectId",id);
					if(verifyProjectMapper.findById(vp).size()>0){
						verifyProjectMapper.deleteByNames(vp);
					}
					
					verifyProjectMapper.addEntity(vp);
				}
	
				
				//接收审核员
				String[] auditorId = request.getParameterValues("auditorId");
					if(auditorId!=null){
						for(String aid:auditorId){
							/*String verify = Arrays.toString(verifierId);
							verify = verify.substring(1, verify.length()-1);
							verify = verify.replace(" ", "");*/
							AuditorProjectFormMap ap=new AuditorProjectFormMap();
							ap.put("auditorId",aid);
							ap.put("projectId",id);
							if(auditorProjectMapper.findById(ap).size()>0){
								auditorProjectMapper.deleteByNames(ap);
							}
							
							auditorProjectMapper.addEntity(ap);
						}	
				}else{
					AuditorProjectFormMap ap=new AuditorProjectFormMap();
					ap.put("auditorId","89");
					ap.put("projectId",id);
					if(auditorProjectMapper.findById(ap).size()>0){
						auditorProjectMapper.deleteByNames(ap);
					}
					
					auditorProjectMapper.addEntity(ap);
				}
			}
			return "success";
		}else {
			ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
			
			for(String key : projectFormMap.keySet()){
				System.out.println("key="+key+",value="+projectFormMap.getStr(key));
			}
			projectFormMap.put("id", id);
			projectMapper.editEntity(projectFormMap);
			/*此时id是project的id,编辑成功后就会调用look_project_by_id()方法展示二级列表数据
			 * 所以应该让id换为customer的id
			 */
			id = typeId;
			return "success";
		}
		/*ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap.put("id", id);
		projectMapper.editEntity(projectFormMap);*/
		
		
	}
	
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		type=getPara("type");
		
		if("projectflow".equals(type)){
			
			String fileName = "项目列表";
			ProjectFormMap projectFormMap = findHasHMap(ProjectFormMap.class);
			//exportData = 
			// [{"colkey":"sql_info","name":"SQL语句","hide":false},
			// {"colkey":"total_time","name":"总响应时长","hide":false},
			// {"colkey":"avg_time","name":"平均响应时长","hide":false},
			// {"colkey":"record_time","name":"记录时间","hide":false},
			// {"colkey":"call_count","name":"请求次数","hide":false}
			// ]
			String exportData = projectFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<ProjectFormMap> lis = projectMapper.findProjectflowPage(projectFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
		if("processectype".equals(type)){	
						
			String fileName = "流程种类";
			ProcedureTypeFormMap processesTypeFormMap = findHasHMap(ProcedureTypeFormMap.class);
			//exportData = 
			// [{"colkey":"sql_info","name":"SQL语句","hide":false},
			// {"colkey":"total_time","name":"总响应时长","hide":false},
			// {"colkey":"avg_time","name":"平均响应时长","hide":false},
			// {"colkey":"record_time","name":"记录时间","hide":false},
			// {"colkey":"call_count","name":"请求次数","hide":false}
			// ]
			String exportData = processesTypeFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<ProcedureTypeFormMap> lis =projectMapper.findProcessesTypePage(processesTypeFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
		if("projectType".equals(type)){
			
			String fileName = "项目类型";
			ProjectTypeFormMap projectTypeFormMap = findHasHMap(ProjectTypeFormMap.class);
			//exportData = 
			// [{"colkey":"sql_info","name":"SQL语句","hide":false},
			// {"colkey":"total_time","name":"总响应时长","hide":false},
			// {"colkey":"avg_time","name":"平均响应时长","hide":false},
			// {"colkey":"record_time","name":"记录时间","hide":false},
			// {"colkey":"call_count","name":"请求次数","hide":false}
			// ]
			String exportData = projectTypeFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<ProjectTypeFormMap> lis = projectMapper.findProjectTypePage(projectTypeFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		if("customer".equals(type)){
			String name = getPara("customerFormMap.name");
			String fileName = "客户列表";
			CustomerFormMap customerFormMap = findHasHMap(CustomerFormMap.class);
			customerFormMap.put("name", name);
			//exportData = 
			// [{"colkey":"sql_info","name":"SQL语句","hide":false},
			// {"colkey":"total_time","name":"总响应时长","hide":false},
			// {"colkey":"avg_time","name":"平均响应时长","hide":false},
			// {"colkey":"record_time","name":"记录时间","hide":false},
			// {"colkey":"call_count","name":"请求次数","hide":false}
			// ]
			String exportData = customerFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<CustomerFormMap> lis = customerMapper.findCustomerProjectPage(customerFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
		if("lookprojectByid".equals(type)){
			
			String fileName = "项目列表";
			ProjectFormMap projectFormMap = findHasHMap(ProjectFormMap.class);
	
			projectFormMap.put("id", id);
			projectFormMap.put("table", table);
			projectFormMap.put("fk", fk);
			System.out.println("-------------1----------------------"+id);
			System.out.println("-------------2----------------------"+table);
			System.out.println("-------------3----------------------"+fk);
			
			String exportData = projectFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);

			List<ProjectFormMap> lis = projectMapper.findProjectById(projectFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
			
			
		}
		if("verify".equals(type)){
			String fileName = "核实项目";
			ProjectFormMap projectFormMap = findHasHMap(ProjectFormMap.class);
	
			projectFormMap.put("id", id);
			projectFormMap.put("table", table);
			projectFormMap.put("fk", fk);			
			String exportData = projectFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
			listMap.remove(listMap.size()-1);
			projectFormMap.put("projectStateId", "1");
			List<ProjectFormMap> lis = projectMapper.findProjectVerifyPage(projectFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		if("tasks".equals(type)){
			String fileName = "代发项目";
			ProjectFormMap projectFormMap = findHasHMap(ProjectFormMap.class);
	
			projectFormMap.put("id", id);
			projectFormMap.put("table", table);
			projectFormMap.put("fk", fk);			
			String exportData = projectFormMap.getStr("exportData");// 列表头的json字符串

			List<Map<String, Object>> listMap = JsonUtils.parseJSONList(exportData);
			listMap.remove(listMap.size()-1);
			projectFormMap.put("projectStateId2", "2");
			projectFormMap.put("projectStateId1", "14");
			List<ProjectFormMap> lis = projectMapper.findProjectVerifyPage(projectFormMap);
			POIUtils.exportToExcel(response, listMap, lis, fileName);
		}
		
	}
	
	//验证客户案号不重复
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(String customerreference) {
		type=getPara("type");
		boolean flag=false;
		ProjectFormMap account = projectMapper.findbyFrist("customerReference", customerreference, ProjectFormMap.class);
		if("add".equals(type)){
			if (account == null) {
				flag=true;
			} else {
				flag=false;
			}
		}
		if("edit".equals(type)){
			String id=getPara("id");
			ProjectFormMap customerReference = projectMapper.findbyFrist("id", id, ProjectFormMap.class);
			if (account == null || customerreference.equals(customerReference.get("customerReference"))) {
				flag=true;
			} else {
				flag=false;
			}
		}
		return flag;
	}
	
	//验证项目名称不重复
	@RequestMapping("nameExist")
	@ResponseBody
	public boolean nameExist(String name) {
		boolean flag=false;
		type=getPara("type");

		ProjectFormMap account = projectMapper.findbyFrist("name", name, ProjectFormMap.class);
		if("add".equals(type)){
			if (account == null) {
				flag=true;
			} else {
				flag=false;
			}
		}
		if("edit".equals(type)){
			String id=getPara("id");
			ProjectFormMap projectName = projectMapper.findbyFrist("id", id, ProjectFormMap.class);

			if (account == null || name.equals(projectName.get("name"))) {
				flag=true;
			} else {
				flag=false;
			}
		}
		return flag;
	}
	
	//验证时间（返稿时间>立项时间）
	@RequestMapping("completeTimeExist")
	@ResponseBody
	public boolean completeTimeExist(String completeTime,String createTime) {		
		type=getPara("type");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date complete = sdf.parse(completeTime);
			Date create = sdf.parse(createTime);
						
			if (complete.after(create)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RequestMapping("downloadUI")
	public String downloadUI(Model model,HttpServletRequest request){
		id=getPara("id");
		ProjectFormMap projectFormMap=projectMapper.findbyFrist("id", id,ProjectFormMap.class);
		if(projectFormMap!=null){
			//找到下载文件的根目录
			String fileURL=projectFormMap.getStr("fileURL");
			//System.out.println(fileURL);
			//存储要下载的文件名
	        Map<String, String> fileNameMap = new HashMap<String, String>();
		      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
			         listfile(new File(fileURL+"\\"),fileNameMap);
			         model.addAttribute("fileNameMap", fileNameMap);
			         //model.addAttribute("fileURL", fileURL);
		}
		    return Common.BACKGROUND_PATH + "/system/project/list_project_download";
	}
	
	@RequestMapping("downloadUIDB")
	public String downloadUIDB(Model model){
		List<FileFormMap> listFile = new ArrayList<FileFormMap>();
		id=getPara("id");
		/*ProjectFormMap projectFormMap=projectMapper.findbyFrist("id", id,ProjectFormMap.class);
		if(projectFormMap!=null){
			//找到下载文件的根目录
			String fileURL=projectFormMap.getStr("fileURL");
			FileFormMap fileFormMap = new FileFormMap();
			fileFormMap.put("fileURL", fileURL);
			fileFormMap.put("fileType", "文件");
			List<FileFormMap> listFile = new ArrayList<FileFormMap>();
			listFile=fileMapper.findLikeURL(fileFormMap);*/
			 listFile=listfileDB(id);
			 model.addAttribute("listFile", listFile);
		/*}*/
		 return Common.BACKGROUND_PATH + "/system/project/list_project_download";
	}
	
	
	/***
	 * 列出目录下的所有文件
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
        			map.put(file.getPath()+"/"+f.getName(), f.getName());
        		}
        	
        	//listfile(f,map);
        	}
      }else if(file.isFile()&& !file.isHidden()){
        //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
    	 // 	String realName=file.getName().substring(14);
    	  //	System.out.println("realName:"+realName);
		      map.put(file.getPath()+"/"+file.getName(), file.getName());
		 }
	 }
	
	public List<FileFormMap> listfileDB(String id){
		List<FileFormMap> listFile = new ArrayList<FileFormMap>();
		ProjectFormMap projectFormMap=projectMapper.findbyFrist("id", id,ProjectFormMap.class);
		if(projectFormMap!=null){
			//找到下载文件的根目录
			String fileURL=projectFormMap.getStr("fileURL");
			FileFormMap fileFormMap = new FileFormMap();
			fileFormMap.put("fileURL", fileURL);
			fileFormMap.put("fileType", "文件");
			
			listFile=fileMapper.findLikeURL(fileFormMap);
		}
		return listFile;	
	}
	
	
	
	@RequestMapping("project_download")
	private void project_download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 //得到要下载的文件名
		
		
        String urlname = request.getParameter("urlname");
        String fileFormat = request.getParameter("fileFormat");
        String path=request.getParameter("fileURL");
        
 		  //通过文件名找出文件的所在目录
			//System.out.println("下载文件路径为"+path);
         //得到要下载的文件
        File file = new File(path+"/"+urlname+"."+fileFormat);
        //如果文件不存在
        if(!file.exists()){
       	 response.getWriter().println("<font color=red>资源不存在</font>");
        }else{
        	
        
        //设置响应头，控制浏览器下载该文件
        String headName=urlname.substring(14)+"."+fileFormat;
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
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path+"/"+urlname+"."+fileFormat));
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
	
	@RequestMapping("project_verify")
	public String project_verify(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/project/list_project_verify";
	}
	
	//查看核实任务分页列表
	@ResponseBody
	@RequestMapping("find_project_verify_by_page")
	public PageView find_project_verify_by_page(String pageNow,
			String pageSize,String column,String sort) throws Exception {
		//id=getPara("id");
		//System.out.println("核实任务id为："+id);
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column", column);
		projectFormMap.put("sort", sort);
		//if(id==null){
			projectFormMap.put("projectStateId", 1);
		//}		
		List<ProjectFormMap> projectList=projectMapper.findProjectVerifyPage(projectFormMap);
		/*List<ProjectFormMap> projectList2=new ArrayList<ProjectFormMap>();
		for(ProjectFormMap  project:projectList){
			int projectId=project.getInt("id");
			int projectStateId=project.getInt("projectStateId");
			String active="";
			if(projectStateId<2){
				active="<a href='javascript:void(0)' name='active' id='active'>确认核实</a>";
			}
			project.put("active",active );
			projectList2.add(project);
		}*/
		pageView.setRecords(projectList);//不调用默认分页,调用自已的mapper中findMemberPage
		return pageView;
	}
	 
	//修改状态---未核实---已发布
	@ResponseBody
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="业务管理",methods="任务核实-核实项目")//凡需要处理业务逻辑的.都需要记录操作日志
	@RequestMapping("verify")
	public String verify(Model model) throws Exception{
		id=getPara("pid");
		//System.out.println(id+"进入修改状态方法");
		ProjectFormMap projectFormMap=projectMapper.findbyFrist("id",id,ProjectFormMap.class);
		if(projectFormMap!=null){
			projectFormMap.put("projectStateId","2");
		}
		projectMapper.editEntity(projectFormMap);
		EmailUtils.sendMail("tianwenli@aitrans.org", "1493084674@qq.com","tianwenli@aitrans.org", "Bnm1234567890", "ali测试", "ceshi");
		return "success";
		//model.addAttribute("res", findByRes());
		//return Common.BACKGROUND_PATH + "/system/project/list_project_verify";
	}
	
	@RequestMapping("verifyUI")
	public String verifyUI(Model model){
		id=getPara("id");
		int pid=Integer.parseInt(id);
		ProjectFormMap projectFormMap=new ProjectFormMap();
		RewardFormMap rewardFormMap = new RewardFormMap();
		
		
		projectFormMap=projectMapper.lookProject(pid);
		rewardFormMap=rewardsMapper.lookReward(pid);
		
		if(rewardFormMap.get("companyReward")==null){
			rewardFormMap.put("companyReward",0);
		}else{
			String companyReward=rewardFormMap.get("companyReward").toString();
			rewardFormMap.put("companyReward", (Object)companyReward.substring(0, companyReward.length()-2));
		}
		
		
		if(rewardFormMap.get("transReward")==null){
			rewardFormMap.put("transReward",0);
		}else{
			String transReward=rewardFormMap.get("transReward").toString();
			rewardFormMap.put("transReward", (Object)transReward.substring(0, transReward.length()-2));
		}
		
		
		if(rewardFormMap.get("auditoryReward")==null){
			rewardFormMap.put("auditoryReward",0);
		}else{
			String auditoryReward=rewardFormMap.get("auditoryReward").toString();
			rewardFormMap.put("auditoryReward", (Object)auditoryReward.substring(0, auditoryReward.length()-2));
		}
		
		
		if(rewardFormMap.get("verifyReward")==null){
			rewardFormMap.put("verifyReward",0);
		}else{
			String verifyReward=rewardFormMap.get("verifyReward").toString();
			rewardFormMap.put("verifyReward", (Object)verifyReward.substring(0, verifyReward.length()-2));
		}
		
		model.addAttribute("projectFormMap",projectFormMap );
		model.addAttribute("rewardFormMap",rewardFormMap );
		//System.out.println(projectFormMap.get("fileURL"));
		Map<String, String> fileNameMap = new HashMap<String, String>();
		if(projectFormMap.getStr("fileURL")!=null){
		      //递归遍历filePath目录下的所有文件和目录，将文件的文件名存储到map集合中
			         listfile(new File(projectFormMap.getStr("fileURL")),fileNameMap);
			         model.addAttribute("fileNameMap", fileNameMap);  
		}else{
			fileNameMap.put("name", "暂无文件");
			model.addAttribute("message", fileNameMap);
		}		
                
		return Common.BACKGROUND_PATH + "/system/project/verify_project";
	
	}
	
	
	@ResponseBody
	@RequestMapping("findURL")
	public List<List<String>> findURL(String url){
		System.out.println(url);
		List<String> pathList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		List<List<String>> fileList = new ArrayList<List<String>>();
	
		File parentFile = new File(url);
		File[] files=parentFile.listFiles();
		if(files!=null){
			for(File file:files){
				if(file.isDirectory()&& !file.isHidden()){
					pathList.add(file.getPath().replace("\\", "/"));
					nameList.add(file.getName());
				}
				
			}
		}
		fileList.add(nameList);
		fileList.add(pathList);
		return fileList;
	}
	
	
	/**
	 * 代发任务
	 */
	@RequestMapping("Release_tasks_UI")
	public String Release_tasks_UI(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/project/list_Release_tasks";
	}
	
	@ResponseBody
	@RequestMapping("find_Release_tasks_by_page")
	public PageView find_Release_tasks_by_page(String pageNow,
			String pageSize,String column,String sort) throws Exception {
		ProjectFormMap projectFormMap = getFormMap(ProjectFormMap.class);
		projectFormMap=toFormMap(projectFormMap, pageNow, pageSize,projectFormMap.getStr("orderby"));
		projectFormMap.put("column", column);
		projectFormMap.put("sort", sort);
		projectFormMap.put("projectStateId1",14);	
		projectFormMap.put("projectStateId2", 2);
		List<ProjectFormMap> projectList=projectMapper.findProjectVerifyPage(projectFormMap);
		pageView.setRecords(projectList);
		return pageView;
	}
}