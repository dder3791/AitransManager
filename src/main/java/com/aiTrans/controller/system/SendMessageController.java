package com.aiTrans.controller.system;

import java.io.IOException;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

 
/**
 * 注册用户手机发送验证码
 * @author vampire
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/sendMessage")
public class SendMessageController {
	
	
	  /**
      * 随机生成4位随机验证码
      * @return
      * @author vampire
      * @return String
     */
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 4; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
    
   /**
    * 根据注册用户信息发送手机验证码
    * @param args
    * @author vampire
    * @throws IOException 
    * @throws HttpException 
    * @throws Exception
    */
    @ResponseBody
    @RequestMapping("sendMessageAction_sendPhone")
    public String sendMessageAction_sendPhone(String phone,HttpServletRequest request) throws HttpException, IOException{
      HttpSession session=request.getSession();
      HttpClient client = new HttpClient();
      PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
      String code="";
      
      //执行是否成功
      String message=request.getParameter("message");
      //判断将译员改为校对员发送短信内容
      if(request.getParameter("type").contains("员")){
          if("success".equals(message)){
        	  String[] proolevelsArry=request.getParameter("proolevels").split(",");
        	  String[] proofDomainArry=request.getParameter("proofDomain").split(",");
        	  String[] proofLanguageArry=request.getParameter("proofLanguage").split(",");
        	  
        	  message="";
        	  for(int i=0;i<proolevelsArry.length;i++){
        		  message+=proofLanguageArry[i]+"--"+proofDomainArry[i]+"--"+proolevelsArry[i]+"级,";
        	  }
        	  
        	  code="经平台专业人员审核，您已成功被评为"+message.substring(0,message.length()-1)+request.getParameter("type");
          }else if("error".equals(message)){
        	  code="经平台专业人员审核，您还未达到"+request.getParameter("type")+"的要求";
          }
      }
      
      //处理申诉
      if(request.getParameter("type").contains("appealClient")){
    	 String date= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    	  
    	  String msg="您好，经平台审核，您对案号为"+request.getParameter("reference")+"的订单于"+date;
    	  if("2".equals(message)){
    		  code=msg+"申诉成功！";
    	  }else{
    		  code=msg+"申诉失败！";
    	  }
      }
      
      
      //企业认证审核
      if(request.getParameter("type").contains("company")){
    	  if("success".equals(message)){
    		  code="您好，经平台审核，企业认证已通过！";
    	  }else{
    		  code="您好，经平台审核，企业认证失败！";
    	  }
      }
      
      //平台个人认证审核
      if(request.getParameter("type").contains("clientUser")){
    	  if("success".equals(message)){
    		  code="您好，经平台审核，您在Aitrans网站平台认证已通过！";
    	  }else{
    		  code="您好，经平台审核，您在Aitrans网站平台认证失败！";
    	  }
      }
      code=code+",详情请咨询   010-82893875";
      post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
      NameValuePair[] data ={ new NameValuePair("Uid", "zhaolei123"),new NameValuePair("Key", "1a5ca651cd2f7335d1c9"),new NameValuePair("smsMob",phone),new NameValuePair("smsText",code)};
      post.setRequestBody(data);
      client.executeMethod(post);
      Header[] headers = post.getResponseHeaders();
      int statusCode = post.getStatusCode();
     // System.out.println("statusCode:"+statusCode);
      for(Header h : headers){
     // System.out.println(h.toString());
      }
     String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
     post.releaseConnection();
    /* String code="1132";*/
     //将获取的验证码放入用户中
     session.setAttribute("code", code);
	return code;
}
 
    /**
     * 校验验证码
     * @Title: checkCode
     * @Description: TODO
     * @throws
     */ 
  @RequestMapping("sendMessageAction_checkCode")  
  public void checkCode(HttpServletRequest request,HttpServletResponse response,String code) throws ServletException,IOException{
     String codes=(String) request.getSession().getAttribute("code"); 
     String data ="1";
      if(null==codes){//获取session中的验证码
         data="2";
      }else if(!codes.equals(code)){//获取前台传来的用户输入的验证码
         data="0";
      }
  response.setContentType("application/json;charset=UTF-8");  
          response.setHeader("Cache-Control", "no-cache");  
          PrintWriter out = response.getWriter();  
          out.write(data); 
  }
    
 

    
    
    
    
}