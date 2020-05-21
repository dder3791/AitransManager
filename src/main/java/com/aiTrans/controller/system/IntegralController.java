package com.aiTrans.controller.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import com.aiTrans.entity.CorpusTableFormMap;
import com.aiTrans.entity.CorpusTableListFormMap;
import com.aiTrans.entity.IntegralFormMap;
import com.aiTrans.entity.RegulationFormMap;
import com.aiTrans.mapper.IntegralMapper;
import com.aiTrans.mapper.RegulationMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;
/***
 * 
 * @author vampire
 *积分管理
 */
@Controller
@RequestMapping("/integral/")
public class IntegralController extends BaseController{
	
	   @Inject
	   private IntegralMapper integralMapper;
	  
	   private String id;
	   private String tradeName;
	   
	   public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	   //展示按钮资源菜单
	   @RequestMapping("list_integral")
		public String list_integral(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/integral/list_integral_first";
		}
	 
	  // 查看兑换商品详情
		@ResponseBody
		@RequestMapping("findBy_integral_page")
		public PageView findBy_integral_page(String pageNow, String pageSize,
				String column, String sort,Model model) throws Exception {
				IntegralFormMap integralFormMap = getFormMap(IntegralFormMap.class);
				integralFormMap = toFormMap(integralFormMap, pageNow, pageSize,
						integralFormMap.getStr("orderby"));
				integralFormMap.put("column", column);
				integralFormMap.put("sort", sort);
				pageView.setRecords(integralMapper.findFirstPage(integralFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
				return pageView;
		}

		/***
		 * 验证商品名称不重复
		 * @param name
		 * @return
		 */
		@RequestMapping("isExist")
		@ResponseBody
		public boolean isExist(String name) {
			boolean flag=false;
			String type=getPara("type");
			IntegralFormMap account = integralMapper.findbyFrist("prize", name, IntegralFormMap.class);
			if("add".equals(type)){
				if (account == null) {
					return true;
				} else {
					return false;
				}
			}
			if("edit".equals(type)){
				String id=getPara("id");
				IntegralFormMap rname = integralMapper.findbyFrist("id",id, IntegralFormMap.class);
			   if (account == null || name.equals(rname.get("name"))) {
					return true;
				} else {
					return false;
				}
			}
			return flag;
		}
	    /**
	     * 增加商品信息
	     * @param model
	     * @return
	     * @throws Exception
	     */
		@RequestMapping("addUI_1")
		public String addUI_1(Model model) throws Exception {
			model.addAttribute("category", integralMapper.findCategory());
			return Common.BACKGROUND_PATH + "/system/integral/addIntegralD";
		}
		/***
		 * 修改商品信息
		 * @param model
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("editUI_1")
		public String editUI_1(Model model,HttpServletRequest request) throws Exception {
			id=getPara("id");
			model.addAttribute("category", integralMapper.findCategory());
			if (Common.isNotEmpty(id)) {
				model.addAttribute("integral",
						integralMapper.findbyFrist("id", id, IntegralFormMap.class));
			}
			return Common.BACKGROUND_PATH + "/system/integral/editIntegralD";
		}
		
		/***
		 * 修改商品信息
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("editGoods")
		@Transactional(readOnly = false)
		public String editGoods(@RequestParam("file")CommonsMultipartFile files[],
				HttpServletRequest request,Model model) throws Exception{
		IntegralFormMap integralFormMap = getFormMap (IntegralFormMap.class);
		integralFormMap.put("id", id);
		integralFormMap.put("prize", request.getParameter("prize"));
		integralFormMap.put("markMin", request.getParameter("markMin"));
		integralFormMap.put("categoryId", request.getParameter("categoryId"));
		integralFormMap.put("remark", request.getParameter("remark"));
		ServletContext application=request.getServletContext();
		String path = application.getRealPath("/upload/file");
		File saveDir = new File(path);
		//判断文件是否存在
		if(!saveDir.exists()){
			//	文件不存在，则创建多级目录来创建这个文件	
			saveDir.mkdirs();
		}
		if(files.length>0){
			for(int i=0;i<files.length;i++){
				// 获取这个文件的传过来的名字
				String name=files[i].getOriginalFilename();
				System.out.println("名字为"+name);
				String fileName=name.substring(0, name.indexOf("."));
				System.out.println("fielName="+fileName);
				String fileEit = name.substring(name.indexOf(".") + 1).toLowerCase().trim();
			//	重新编辑这个文件的名字以日期组合，让它不会重复。filename最终上传文件文件名称
				
			    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			    fileName = df.format(new Date()) +fileName+"."+fileEit;
				String path1 = "/upload/file"+fileName;
		   //	创建保存文件的对象路径为创建的路径saveDir，名字为组合出来的新名字
				File saveFile = new File(saveDir,fileName);
				
				try {
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
						integralFormMap.put("URL", path1);
						integralMapper.editEntity(integralFormMap);
						fos.close();//关闭输入输出流
						instream.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}					
		   }
		return "success";
		}
		/***
		 * 添加商品列表
		 * @param files
		 * @return
		 */
		@ResponseBody
		@RequestMapping("addGoods")
		@SystemLog(module = "积分管理", methods = "商品管理-新增商品")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		@Transactional(readOnly = false)
		public String addGoods(@RequestParam("file")CommonsMultipartFile files[],
				HttpServletRequest request,Model model){
			
		IntegralFormMap integralFormMap = getFormMap (IntegralFormMap.class);
		integralFormMap.put("prize", request.getParameter("prize"));
		integralFormMap.put("markMin", request.getParameter("markMin"));
		integralFormMap.put("categoryId", request.getParameter("categoryId"));
		integralFormMap.put("remark", request.getParameter("remark"));
		ServletContext application=request.getServletContext();
		String path = application.getRealPath("/upload/file");
		
		//执行文件上传功能
		/*String path="C:/"+"Aitrans";*/
		//创建path目录下的文件对象
		File saveDir = new File(path);
		//判断文件是否存在
		if(!saveDir.exists()){
			//	文件不存在，则创建多级目录来创建这个文件	
			saveDir.mkdirs();
		}
		if(files.length>0){
			for(int i=0;i<files.length;i++){
				// 获取这个文件的传过来的名字
				String name=files[i].getOriginalFilename();
				System.out.println("名字为"+name);
				String fileName=name.substring(0, name.indexOf("."));
				System.out.println("fielName="+fileName);
				String fileEit = name.substring(name.indexOf(".") + 1).toLowerCase().trim();
			//	重新编辑这个文件的名字以日期组合，让它不会重复。filename最终上传文件文件名称
			    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			    fileName = df.format(new Date()) +fileName+"."+fileEit;
			    String path1="/upload/file/"+fileName;
		   //	创建保存文件的对象路径为创建的路径saveDir，名字为组合出来的新名字
				File saveFile = new File(saveDir,fileName);
				
				try {
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
						integralFormMap.put("URL", path1);
						integralMapper.addEntity(integralFormMap);
						fos.close();//关闭输入输出流
						instream.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}					
		   }
		return "success";
		}
		
		
		
		/***
		 * 删除商品信息
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping("deleteEntity")
		@Transactional(readOnly = false)
		// 需要事务操作必须加入此注解
		@SystemLog(module = "商品管理", methods = "商品管理-删除商品信息")
		// 凡需要处理业务逻辑的.都需要记录操作日志
		public String deleteEntity() throws Exception {
			String[] ids = getParaValues("ids");
			for (String id : ids) {
				integralMapper.deleteByAttribute("id", id, IntegralFormMap.class);
			}
			return "success";
		}	
		
		
		
		
		
}
