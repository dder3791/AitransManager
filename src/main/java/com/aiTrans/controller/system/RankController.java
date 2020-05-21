package com.aiTrans.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aiTrans.controller.index.BaseController;
import com.aiTrans.entity.EvaluateFormMap;
import com.aiTrans.entity.RankFormMap;
import com.aiTrans.entity.TranslatorFormMap;
import com.aiTrans.mapper.RankMapper;
import com.aiTrans.mapper.TranslatorMapper;
import com.aiTrans.plugin.PageView;
import com.aiTrans.util.Common;

@Controller
@RequestMapping("/rank/")
public class RankController extends BaseController {
   
	 @Inject
	 private RankMapper rankMapper;
	 @Inject
	 private TranslatorMapper translatorMapper;
	 private String id;
	   private String name;
	   
	   public String getPara(String key) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request.getParameter(key);
		}
	
	//展示按钮资源菜单
	   @RequestMapping("list_rank")
		public String list_rank(Model model) throws Exception {
			model.addAttribute("res", findByRes());
			return Common.BACKGROUND_PATH + "/system/rank/list_rank_first";
		}
	   
	// 查看排名详情
		@ResponseBody
		@RequestMapping("findBy_rank_page")
		public PageView findBy_rank_page(String pageNow, String pageSize,
			String column, String sort,Model model) throws Exception {
			
			//修改译员积分
			List<TranslatorFormMap> rankList=rankMapper.rankCount(5);
			for(TranslatorFormMap trans:rankList){
				int translatorId=trans.getInt("translatorId");
				int count=rankMapper.findEvaluateD(translatorId);
				int point=trans.getInt("points");
				if(count>0){
					RankFormMap rankFormMap=new RankFormMap();
					rankFormMap.put("score", 100);
					rankFormMap.put("translatorId",translatorId);
					rankFormMap.put("scores", 80);
					int count90=rankMapper.findScore(rankFormMap);
					if(count90>0){
						point=(int) Math.pow(point*(1+0.1),count90);
					}
					rankFormMap.put("score", 80);
					rankFormMap.put("translatorId",translatorId);
					rankFormMap.put("scores", 70);
					int count80=rankMapper.findScore(rankFormMap);
					if(count80>0){
						point=(int) Math.pow(point*(1+0.2),count80);
					}
					rankFormMap.put("score", 70);
					rankFormMap.put("translatorId",translatorId);
					rankFormMap.put("scores",60);
					int count70=rankMapper.findScore(rankFormMap);
					if(count70>0){
						point=(int) Math.pow(point*(1+0.3),count70);
					}
				}
				TranslatorFormMap translator=new TranslatorFormMap();
				translator.put("id", translatorId);
				translator.put("point", point);
				translatorMapper.editPoint(translator);
			}
			
			
			
			RankFormMap rankFormMap = getFormMap(RankFormMap.class);
			TranslatorFormMap translatorFormMap=new TranslatorFormMap();
			rankFormMap = toFormMap(rankFormMap, pageNow, pageSize,
					rankFormMap.getStr("orderby"));
			rankFormMap.put("column", column);
			rankFormMap.put("sort", sort);
			pageView.setRecords(rankMapper.findFirstPage(translatorFormMap));// 不调用默认分页,调用自已的mapper中findMemberPage
			return pageView;
			}  
	     
	   
	   
}
