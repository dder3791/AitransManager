package com.aiTrans.controller.client;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiTrans.entity.ClientUploadDataFormMap;
import com.aiTrans.mapper.ClientUploadDataMapper;
import com.aiTrans.util.Common;
import com.aiTrans.vo.ClientData;

@Controller
@RequestMapping("/cloud/client/")
public class ClientUploadDataController {
	@Inject
	private ClientUploadDataMapper clientUploadDataMapper;
	
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
	 * 请求示例 ：
{
 "reg" : "A01",
 "un" : "aaa",
 "sn" : "Aitrans-PAT 6.0",
 "cn" : "google",
 "fr" : "1",
 "to" : "2",
 "ot" : "hello",
 "dt" : "你好",
 "oc" : 5,
 "dc" : 2
}
 响应示例：
{
 "co","200",
 "oc",5,
 "dc",2
}
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getprirce")
	public String getPrirce() throws ParseException{
		return Common.BACKGROUND_PATH + "/cloud/lang_cost";
	}
	public static void main(String args[]){
		String runtime = "2020-09-23 12:23:56";
		try {//YYYY-MM-DD hh:mm:ss
			Date runTime = DateUtils.parseDate(runtime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","yyyyMMdd"});
			System.out.println(runTime);
			
			String s = "一种这样的方法是通过低压模制将光源和相关的电子部件（统称为“封装”）封装在透明树脂中，然后在封装的封装上方或周围注塑塑料。封装的包装物被嵌入塑料中，塑料的一部分是透明或半透明的，这样透明或半透明的塑料就能看到来自封装包装的光，从而产生背光效果。\n"+
"另一种这样的方法是将光源和相关的电子器件（“包装”）安装到聚合物膜上，将膜形成为期望的形状，然后将形成的膜插入具有基本相同形状的注射模具中。接下来的步骤将塑料注模到薄膜上，从而使包装件嵌入安装在其上的薄膜和模制在其上的塑料之间，并且薄膜和/b或塑料的部分是透明或半透明的，从而使光从光源发出的光线从零件外部可见，从而产生背光效果。"+


"本实用新型公开了基于PFM脉冲频率调制的LED照明器，包括不少于两种颜色的光源，所述光源耦合至一个电源电路，该电源电路包括一个供电电源、一个常规基准电压、用于驱动各种光源发光的驱动器和一个控制器，该控制器包括一个用于识别自我并对一个分配给控制器的输入信号做出反应可该变的地址，该控制器用于产生各种PFM的信号，每一个PFM信号相当于LED各种颜色中单独的一种颜色，每一个PFM信号指令一个独立的开关根据一个特定的相关的频率进行通断，这里所说的数据流包括不少于两种LED颜色的数据；本实用新型能准确调节彩色LED照明器颜色及其亮度，在轻负载和低电流得情况下，采用PFM控制驱动器的方式比PWM更具效率。";
			System.out.println("文本长度："+s.length());	
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	
}
