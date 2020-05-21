package com.aiTrans.controller.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.annotation.SystemLog;
import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.ArticleFormMap;
import com.aiTrans.entity.QuestionFormMap;
import com.aiTrans.exception.SystemException;
import com.aiTrans.mapper.ArticleMapper;
import com.aiTrans.mapper.QuestionMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

@Controller
@RequestMapping("/question/")
public class QuestionController extends BaseController{

	@Inject
	private QuestionMapper questionMapper;
	
	public String getPara(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request.getParameter(key);
	}
	
	private String id;//对应表的主键
	
	/**
	 * 跳转展示常见问题信息页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list_question")
	public String list_article(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/question/list_question";
	}
	
	
	/**
	 * 查看常见问题信息分页列表,应该从常见问题表中查
	 * @param pageNow
	 * @param pageSize
	 * @param column
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("find_question_by_page")
	public PageView find_question_by_page(String pageNow,String pageSize,String column,String sort) throws Exception {
		QuestionFormMap questionFormMap = getFormMap(QuestionFormMap.class);
		questionFormMap=toFormMap(questionFormMap, pageNow, pageSize,questionFormMap.getStr("orderby"));
		questionFormMap.put("column", column);
		questionFormMap.put("sort", sort);
        pageView.setRecords(questionMapper.findQuestionByPage(questionFormMap));//不调用默认分页,调用自已的mapper中findMemberPage
        return pageView;
	}
	
	/**
	 * 跳转添加常见问题页面
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(){
		return Common.BACKGROUND_PATH + "/system/question/add_question";
	}
	
	/**
	 * 添加常见问题实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addEntity")
	@SystemLog(module="常见问题",methods="常见问题-新增问题")//凡需要处理业务逻辑的.都需要记录操作日志
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	public String addEntity(){
		
		try {
			QuestionFormMap questionFormMap = getFormMap(QuestionFormMap.class);
			questionMapper.addEntity(questionFormMap);
		} catch (Exception e) {
			 throw new SystemException("添加问题异常");
		}
		return "success";
	}
	
	/**
	 * 跳转编辑问题页面
	 * @return
	 */
	@RequestMapping("editUI")
	public String editUI(Model model){
		id = getPara("id");
		model.addAttribute("questionInfo",questionMapper.findbyFrist("id", id, QuestionFormMap.class));
		return Common.BACKGROUND_PATH + "/system/question/edit_question";
	}
	
	/**
	 * 修改常见问题信息实体
	 * @return
	 */
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
	
	/**
	 * 删除常见问题实体数据
	 * @return
	 * @throws Exception
	 */
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
	
	
	/**
	 * 跳转查看常见问题信息详情页面
	 * @return
	 */
	@RequestMapping("look_question")
	public String look_article(Model model){
		id = getPara("id");
		model.addAttribute("questionInfo",questionMapper.findbyFrist("id", id, QuestionFormMap.class));
		return Common.BACKGROUND_PATH + "/system/question/look_question";
	}
}
