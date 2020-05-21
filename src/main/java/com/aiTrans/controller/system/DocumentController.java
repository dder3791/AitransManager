package com.aiTrans.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.FileFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.entity.UserFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.FileMapper;
import com.aiTrans.mapper.ProjectMapper;
import com.aiTrans.util.Common;

@Controller
@RequestMapping("/document/")
public class DocumentController extends BaseController{

	@Inject
	private FileMapper fileMapper;
	
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	@RequestMapping("document_UI")
	public String document_UI(){
		return Common.BACKGROUND_PATH + "/system/document/list_project_document";
	}
	
	@ResponseBody
	@RequestMapping("findDocuments")
	public List<List<String>> findDocuments(Model model,String name,String url){
		List<String> listFile=new ArrayList<String>();
		List<String> listDir=new ArrayList<String>();
		List<String> listpath=new ArrayList<String>();
		List<String> oldurl=new ArrayList<String>();
		List<List<String>> mapList=new ArrayList<List<String>>();
		File[] fileList;
		File file = null; 
		String path="";
		if(url!="" && url!=null&&name!=""&&name!=null){
				List<FileFormMap> listP=new ArrayList<FileFormMap>();
				String newurl=url.substring(url.length()-1);
				if("/".equals(newurl)){
					 path=url+name;
					 listP= fileMapper.findParentId(url+name);
				}else{
					path=url+"/"+name;
					 listP= fileMapper.findParentId(url+"/"+name);
				}
				
				 if(listP.size()<1){
						addDirectory(name,url);
					}
		}
		else if((name==null||name=="")&&url!=""&&url!=null){
			path=url;
		}
		else{
			 path="E:/";
			 List<FileFormMap> listP=new ArrayList<FileFormMap>();
			 listP= fileMapper.findParentId(path+name);
			 if(listP.size()<1){
					addDirectory(name,path);
				}
		 }
		
		 
		 
		 file=new File(path);
		 fileList =file.listFiles();
		 if(fileList!=null){
			 for(File f:fileList){
				 if(f.isDirectory() && !f.isHidden()){
					 List<FileFormMap> listf=new ArrayList<FileFormMap>();
					 name=f.getName();
					 String newpath=path.substring(path.length()-1);
					 if("/".equals(newpath)){
						 listf= fileMapper.findParentId(path+name);
					 }else{
						 listf= fileMapper.findParentId(path+"/"+name);
					 }
					 if(listf.size()<1){
							addDirectory(name,path);
						}
					 
					 listDir.add(name);
					 
				 }else if(f.isFile() && !f.isHidden()){
					 List<FileFormMap> listf=new ArrayList<FileFormMap>();
					 name=f.getName();
					// 获取这个文件的传过来的名字
					 int str=name.lastIndexOf(".");
					 String fileName=name.substring(0,name.lastIndexOf("."));
					//文件格式
					 String fileEit = name.substring(name.lastIndexOf(".")+ 1).toLowerCase().trim();
					 
					 
					 String newpath=path.substring(path.length()-1);
					 if("/".equals(newpath)){
						 if("E:/".equals(path)){
							 listf= fileMapper.findFile(path);
						 }else{
							 listf= fileMapper.findFile(path.substring(0, path.length()-1));
						 }
						 
					 }else{
						 listf= fileMapper.findFile(path);
					 }
					 boolean flag=true;
					 for(FileFormMap fileFormMap:listf){
						 String listfName=(String) fileFormMap.get("fileName");
						 String listformat=(String) fileFormMap.get("fileFormat");
						 if(fileName.equals(listfName)&&fileEit.equals(listformat)){
							 flag=false;
						 };
					 }
					 if(flag){
						 FileFormMap fileFormMap =new FileFormMap();
						 
						
							
							//查找父id
							List<FileFormMap> fileParentList=fileMapper.findParentId(path);
							for(FileFormMap fileParent:fileParentList){
								if(fileParent!=null){
									fileFormMap.put("parentId", fileParent.get("id"));
								}else{
									fileFormMap.put("parentId","");
								}
							}
							
							//文件上传者
							HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
							UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
							int uploaderId = (int) userFormMap.get("id");
							
							//创建时间
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
							Date date = new Date();
							String uploadTime=sdf.format(date);  
							
							
							fileFormMap.put("fileName", fileName);
							fileFormMap.put("uploadTime", uploadTime);
							fileFormMap.put("uploader", uploaderId);
							fileFormMap.put("fileFormat", fileEit);
							fileFormMap.put("fileType", "文件");
							fileFormMap.put("fileUrl", path);
							
							try {
								fileMapper.addEntity(fileFormMap);
							} catch (Exception e) {
								e.printStackTrace();
							}
						 
					 }
					 listFile.add(name); 
					
				 }
				
			 }
			 listpath.add(path);
			 oldurl.add(url);
			 mapList.add(listDir);
			 mapList.add(listFile);
			 mapList.add(listpath);
			 mapList.add(oldurl);
			 
		 }
		 else{
			 listpath.add("暂无文件");
			 mapList.add(listpath);
		 }
		return mapList;
	}
	

	/**
	 * 跳转到新增界面
	 * 
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(Model model) {
		String path=getPara("path");
		model.addAttribute("path", path);
		return Common.BACKGROUND_PATH + "/system/document/addDirectory";
	}
	
	
	
	/**
	 * 新建文件夹
	 * @param name
	 * @param url
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addDirectory")
	public String addDirectory(String name,String url){
		String newurl=url.substring(url.length()-1);
		String path="";
		
		
		
		if(name==null || name==""){
			String oldurl;
			Object parentId="";
			if(!"/".equals(newurl)){
				oldurl=url.substring(0, url.lastIndexOf("/"));
			}else{
				oldurl=url;
			}
			name="新建文件夹";
			List<FileFormMap> fileParentList=fileMapper.findParentId(url);
			for(FileFormMap fileParent:fileParentList){
				parentId=fileParent.get("id");
			}
			
			FileFormMap fileFormMap = new FileFormMap();
			fileFormMap.put("fileName",name);
			fileFormMap.put("fileType", "文件夹");
			fileFormMap.put("parentId", parentId);
			int num=fileMapper.countByNPT(fileFormMap);
			
			String newnum="";
			if(num<10){
				newnum="0"+num;
			}
			else{
				newnum=num+"";
			}
			name=name+newnum;
		}

		
		if(!"/".equals(newurl)){
			path=url+"/"+name;
		}else{
			path=url+name;
		}
		

		//数据库表中添加
		FileFormMap fileFormMap = new FileFormMap();
		//查找父id
		List<FileFormMap> fileParentList=fileMapper.findParentId(url);
		for(FileFormMap fileParent:fileParentList){
			if(fileParent!=null){
				fileFormMap.put("parentId", fileParent.get("id"));
			}else{
				fileFormMap.put("parentId","");
			}
		}
		//文件上传者
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
		int uploaderId = (int) userFormMap.get("id");
		
		//创建时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		Date date = new Date();
		String uploadTime=sdf.format(date);  
		
		fileFormMap.put("uploader", uploaderId);
		fileFormMap.put("fileName", name);
		fileFormMap.put("fileUrl", path);
		fileFormMap.put("fileType","文件夹");
		fileFormMap.put("uploadTime", uploadTime);
		try {
			fileMapper.addEntity(fileFormMap);
			
			//服务器中新建文件夹
			File saveDir = new File(path);
			//判断文件是否存在
			if(!saveDir.exists()){
				//	文件不存在，则创建多级目录来创建这个文件	
				saveDir.mkdirs();
			}
		} catch (Exception e) {
			
			 throw new SystemException("新建文件夹异常");
		}
		
		return "success";
	}
	
	

	/**
	 * 跳转到上传界面
	 * 
	 * @return
	 */
	@RequestMapping("uploadUI")
	public String uploadUI(Model model) {
		String path=getPara("path");
		model.addAttribute("path", path);
		return Common.BACKGROUND_PATH + "/system/document/uploadFile";
	}
	
	/**
	 * 上传文件
	 * @param files
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadFile")
	public String uploadFile(@RequestParam("filename")CommonsMultipartFile files[],String path){
		FileFormMap fileFormMap=new FileFormMap();
		
		//创建path目录下的文件对象
		File saveDir = new File(path);
		/*//判断文件是否存在
		if(!saveDir.exists()){
			//	文件不存在，则创建多级目录来创建这个文件	
			saveDir.mkdirs();
		}*/
			for(int i=0;i<files.length;i++){
				if(!files[i].isEmpty()){
					// 获取这个文件的传过来的名字
					String name=files[i].getOriginalFilename();
					String fileName=name.substring(0, name.indexOf("."));
					String fileEit = name.substring(name.indexOf(".") + 1).toLowerCase().trim();
					
					
//					重新编辑这个文件的名字以日期组合，让它不会重复。filename最终上传文件文件名称
				    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				    String sqlfileName=df.format(new Date()) +fileName;
				    fileName = sqlfileName+"."+fileEit;
				    
				    
					//确保数据库添加和服务器中上传同时，所以先添加，后上传
					//查找父id
					List<FileFormMap> fileParentList=fileMapper.findParentId(path);
					for(FileFormMap fileParent:fileParentList){
						if(fileParent!=null){
							fileFormMap.put("parentId", fileParent.get("id"));
						}else{
							fileFormMap.put("parentId","");
						}
					}
					
					//文件上传者
					HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
					UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
					int uploaderId = (int) userFormMap.get("id");
					
					//创建时间
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
					Date date = new Date();
					String uploadTime=sdf.format(date);  
					
					
					fileFormMap.put("fileName",sqlfileName);
					fileFormMap.put("uploadTime", uploadTime);
					fileFormMap.put("uploader", uploaderId);
					fileFormMap.put("fileFormat", fileEit);
					fileFormMap.put("fileType", "文件");
					fileFormMap.put("fileUrl", path);
					
					try {
						fileMapper.addEntity(fileFormMap);
						
						
						    //	创建保存文件的对象路径为创建的路径saveDir，名字为组合出来的新名字
							File saveFile = new File(saveDir,fileName);
							
//							从方法传入的人间对象获取输入流
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
						e1.printStackTrace();
					}
				}	
			}
			return "success";
	}
	
	/**
	 * 跳转重命名页面
	 */
	@RequestMapping("renameUI")
	public String renameUI(Model model){
		String path=getPara("path");
		String oldname=getPara("oldname");
		String type=getPara("type");
		
		model.addAttribute("path", path);
		model.addAttribute("type", type);
		model.addAttribute("oldname", oldname);
		return Common.BACKGROUND_PATH + "/system/document/rename";
	}

	/**
	 * 文件重命名
	 * @param url
	 * @param oldname
	 * @param newname
	 * @return
	 */
	@ResponseBody
	@RequestMapping("rename")
	public String  rename(String url,String oldname,String newname){
		String fileType=getPara("type");
		List<FileFormMap> fileList=new ArrayList<FileFormMap>();
		FileFormMap fileFormMap = new FileFormMap();
		if(newname==null || newname==""){
			newname=oldname;
		}
		if("0".equals(fileType)){
			String newurl="";
			if("/".equals(url.substring(url.length()-1))){
				newurl=url;
			}else{
				newurl=url+"/";
			}
			fileFormMap.put("where", " where fileName='"+oldname+"' and fileType="+"'文件夹' and fileUrl='"+newurl+oldname+"'");
			fileList=fileMapper.findByWhere(fileFormMap);
			FileFormMap file = fileList.get(fileList.size()-1);
		
				
				//文件上传者
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
				int uploaderId = (int) userFormMap.get("id");
				
				//创建时间
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				Date date = new Date();
				String uploadTime=sdf.format(date);  
			
				file.put("uploader", uploaderId);
				file.put("fileName", newname);
				file.put("uploadTime", uploadTime);
				file.put("fileUrl",newurl+newname);
				
				fileMapper.rename(file);
				
				File oldfile=new File(url+"/"+oldname);
				File newFile=new File(url+"/"+newname);
				oldfile.renameTo(newFile);
				System.out.println(oldfile.delete());
				oldfile.delete();
			
		}else{
			String fileName="";
			if(newname!=null && newname!=""){
				fileName=oldname.substring(0,oldname.lastIndexOf("."));
			}
			fileFormMap.put("where", " where fileName='"+fileName+"' and fileType="+"'文件' and fileUrl='"+url+"'");
			fileList=fileMapper.findByWhere(fileFormMap);
			for(FileFormMap file:fileList){
				
				//文件上传者
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
				int uploaderId = (int) userFormMap.get("id");
				
				//创建时间
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				Date date = new Date();
				String uploadTime=sdf.format(date);  
				
				//文件新名称
				String newName=newname.substring(0,newname.lastIndexOf("."));
				file.put("uploader", uploaderId);
				file.put("fileName", newName);
				file.put("uploadTime", uploadTime);
				
				fileMapper.rename(file);
				
				File oldfile=new File(url+"/"+oldname);
				File newFile=new File(url+"/"+newname);
				oldfile.renameTo(newFile);
				//oldfile.delete();
			}
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("deleteDirectory")
	public String deleteDirectory(String path,String name){
		String type=getPara("type");
		
		List<FileFormMap> fileList=new ArrayList<FileFormMap>();
		FileFormMap fileFormMap = new FileFormMap();
		
		
		if("0".equals(type)){
			String newurl="";
			if("/".equals(path.substring(path.length()-1))){
				newurl=path;
			}else{
				newurl=path+"/";
			}
			
			fileFormMap.put("where", " where fileName='"+name+"' and fileType="+"'文件夹' and fileUrl='"+newurl+name+"'");
			fileList=fileMapper.findByWhere(fileFormMap);
			for(FileFormMap file:fileList){
				int id=file.getInt("id");
				try {
					fileMapper.deleteDir(id);
					
				} catch (Exception e) {
					System.out.println("删除文件失败！");
					e.printStackTrace();
					
				}
			}
			File deleteFile = new File(newurl+name);
			deleteFile.delete();
			return "success";
		}else{
			String fileName="";
			if(name!=null&&name!=""){
				fileName=name.substring(0,name.lastIndexOf("."));
			}
			
			fileFormMap.put("where", " where fileName='"+fileName+"' and fileType="+"'文件' and fileUrl='"+path+"'");
			fileList=fileMapper.findByWhere(fileFormMap);
			for(FileFormMap file:fileList){
				int id=file.getInt("id");
				try {
					fileMapper.deleteDir(id);
					File deleteFile = new File(path+"/"+name);
					deleteFile.delete();
					return "success";
				} catch (Exception e) {
					System.out.println("删除文件失败！");
					e.printStackTrace();
					
				}
			}
			//return "success";
		}
		
		return "error";
		
	}
	
	@RequestMapping("downloadUI")
	public String downloadUI(String path,String name,Model model){
			
		
			model.addAttribute("path", path);
			model.addAttribute("name", name);
		return Common.BACKGROUND_PATH + "/system/document/download";
	}
	
	@RequestMapping("project_download")
	public void project_download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String oldPath=request.getParameter("oldPath");
		
		//得到要下载的文件名
		
		String userAgent = request.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
		name = java.net.URLEncoder.encode(name, "UTF-8");
		} else {
		//非IE浏览器的处理：
		name = new String(name.getBytes("UTF-8"),"ISO-8859-1");
		}
		response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", name));
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setCharacterEncoding("UTF-8"); 
		
		   /*response.setCharacterEncoding("text/html; charset=UTF-8");
		   response.setContentType("application/vnd.ms-txt;charset=utf-8");
		   response.setContentType("text/plain");*/
		              
           
 		  //通过文件名找出文件的所在目录
         //得到要下载的文件
		
        File file = new File(oldPath + "//" + name);
        //如果文件不存在
        if(!file.exists()){
       	 response.getWriter().println("<font color=red>资源不存在</font>");
        }else{
        	 response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(name, "UTF-8"));
             
             
             
             //读取要下载的文件，保存到文件输入流
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(oldPath + "\\" + name));
             //创建输出流
             BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
             //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newPath + "\\" +name));
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
        //设置响应头，控制浏览器下载该文件
       // String headName=urlname.substring(14);
        //String fileName = URLEncoder.encode(urlname,"UTF-8");
		//response.setHeader("Content-Disposition","attachment;filename=" + new String((fileName + ".txt").getBytes(), "iso-8859-1"));
        
        
      
	}
	
	//文件上传
		public void upload(String path,CommonsMultipartFile files[]){
			//创建path目录下的文件对象
			File saveDir = new File(path);
			//判断文件是否存在
			if(!saveDir.exists()){
				//	文件不存在，则创建多级目录来创建这个文件	
				saveDir.mkdirs();
			}
			//System.out.println("文件数组长度："+files.length);
				for(int i=0;i<files.length;i++){
					if(!files[i].isEmpty()){
						// 获取这个文件的传过来的名字
						String name=files[i].getOriginalFilename();
						String fileName=name.substring(0, name.indexOf("."));
						String fileEit = name.substring(name.indexOf(".") + 1).toLowerCase().trim();
						System.out.println(fileEit);
					//	重新编辑这个文件的名字以日期组合，让它不会重复。filename最终上传文件文件名称
					    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					    fileName = df.format(new Date()) +fileName+"."+fileEit;
					    //	创建保存文件的对象路径为创建的路径saveDir，名字为组合出来的新名字
						File saveFile = new File(saveDir,fileName);
						
//						从方法传入的人间对象获取输入流
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
			     
					}	
				}
		}

}
