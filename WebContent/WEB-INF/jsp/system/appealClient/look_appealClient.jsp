<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript"	src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
#but button {
	margin-bottom: 5px;
	margin-right: 5px;
}
.col-sm-3 {
	width: 18%;
	float: left;
}

.col-sm-9 {
	width: 82%;
	float: left;
}

label[class^="btn btn-default"] {
	margin-top: -4px;
}
</style>
<script type="text/javascript">
function closeWin(){
	layer.confirm('放弃此次审核？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

       

function verify(obj){
			alert(obj);
    		var type=document.getElementById("type").value;
    		var apealClientId=document.getElementById("apealClientId").value;
    		var transphone=document.getElementById("transphone").value;
    		var clientUserPhone=document.getElementById("clientUserphone").value;
    		var reference=document.getElementById("reference").value;
    			 $.post("${pageContext.request.contextPath}/appealClient/editAppealState.shtml","apealClientId="+apealClientId+"&apealState="+obj,function(data){
        			if(data=='success'){
        				layer.confirm('处理完成!是否关闭窗口?', function(index) {
        					//短信通知译员
            				 $.post("${pageContext.request.contextPath}/sendMessage/sendMessageAction_sendPhone.shtml?clientUserPhone="+clientUserPhone+"&transphone="+transphone+"&message="+obj+"&type="+type+"&reference="+reference,function(data){
            		   		   if(data!=null && data!=""){
            		   			   
            		   			layer.confirm('短信已分别通知客户及译员!', function(index) {
                					parent.grid.loadData();
                					parent.layer.close(parent.pageii);
                					
            						return false;
            					});
            		   		   }else{
            		   			layer.alert('短信通知客户和译员发送失败，请重新发送！', 3);
            		   		   }
            		  	     },"text");
    					});
    					$("#form")[0].reset();
        			}else{
        				layer.alert('处理失败！', 3);
        			}
         		 },'json');    		 
        }
        
        
 /*  function notverify(){
	  	var type=document.getElementById("type").value;
		var transphone=document.getElementById("transphone").value;
		var clientUserPhone=document.getElementById("clientUserphone").value;
		  
		//短信通知译员
			$.post("${pageContext.request.contextPath}/sendMessage/sendMessageAction_sendPhone.shtml?clientUserPhone="+clientUserPhone+"&transphone="+transphone+"&message="+data+"&type="+type,function(data){
	   		   if(data!=null && data!=""){
	   			   
	   			layer.confirm('短信已分别通知客户及译员!', function(index) {
					parent.grid.loadData();
					parent.layer.close(parent.pageii);
					
					return false;
				});
	   		   }else{
	   			layer.alert('短信通知客户和译员发送失败，请重新发送！', 3);
	   		   }
	  	     },"text");  
  } */
</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/evaluate/editEvaluate.shtml" >
	<section class="panel panel-default">
	 <div class="panel-body">
		   <input type="hidden" name="aId" value="${appealClientFormMap.id}" id="apealClientId">
		   <input type="hidden" name="type" value="${type}" id="type">
		   <div class="form-group" >
				<label class="col-sm-3 control-label">订单案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${order.orderReference}" id="reference">	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">订单标题</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" disabled="disabled" value="${order.orderTitle}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语言</label>
				<div class="col-sm-9" >
					<input type="text" class="form-control" disabled="disabled" value="${clientCustomerNeed.languagePair}" >
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<%-- <div class="form-group">
				<label class="col-sm-3 control-label">翻译领域</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${clientCustomerNeed.languagePair}" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div> --%>
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">翻译流程</label>
				<div class="col-sm-9">
					<c:if test="${clientCustomerNeed.procedureTypeId == 1}">
						<input type="text" class="form-control" disabled="disabled" value="仅翻译" >
					</c:if>
					<c:if test="${clientCustomerNeed.procedureTypeId == 2}">
						<input type="text" class="form-control" disabled="disabled" value="翻译和校对" >
					</c:if>
					<c:if test="${clientCustomerNeed.procedureTypeId == 3}">
						<input type="text" class="form-control" disabled="disabled" value="翻译、校对及审核" >
					</c:if>
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">是否加急</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${clientCustomerNeed.cycle }" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">发布模式</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${clientCustomerNeed.publishModel }" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">发布时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${clientCustomerNeed.publishTime }" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">返稿时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${order.examinationTime }" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">译员信息</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="姓名：${translator.nickname }，联系电话：${translator.tel}" >
					<input type="hidden" id="transphone" value="${translator.tel }">
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		    <div class="form-group">
				<label class="col-sm-3 control-label">客户信息</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="姓名：${clientUser.realName }，联系电话：${clientUser.tel}" >
					<input type="hidden" id="clientUserphone" value="${clientUser.tel }">
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		    <div class="form-group">
				<label class="col-sm-3 control-label">评价内容</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${evaluateClient.evaluateDetails }" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		    <div class="form-group">
				<label class="col-sm-3 control-label">申诉内容</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" disabled="disabled" value="${appealClientFormMap.cause }" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		   
		  <!--  <div class="form-group">
				<label class="col-sm-3 control-label">相关文件</label>
				<div class="col-sm-9">
					
				</div>
		   </div> -->
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="button" onclick="verify(2)" id="back" class="btn btn-success btn-s-xs">申诉成功</button>
			<button type="button" onclick="verify(3)" id="back" class="btn btn-success btn-s-xs">申诉失败</button>
			<button type="button" id="back" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">取消</button>
		</footer> 
	</section>
</form> 
</body>
</html>