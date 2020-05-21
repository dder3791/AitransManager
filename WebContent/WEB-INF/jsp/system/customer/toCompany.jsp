<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<style type="text/css">
.col-sm-3 {
	width: 18%;
	float: left;
}
.col-sm-9 {
	width: 82%;
	float: left;
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
	var userId=document.getElementById("userId").value;
	var phone=document.getElementById("phone").value;
	var type=document.getElementById("type").value;
 
	$.post("${pageContext.request.contextPath}/clientUser/toCompany.shtml" ,"message="+obj+"&userId="+userId/*+"&proolevels="+proolevelList+"&proofDomain="+proofDomainList+"&proofLanguage="+proofLanguageList+"&type="+type */,function(data){
			if(data=='success'){
				//短信通知客户
				var dd="";
				if('no'==obj){
					dd='error';
				}else if('yes'==obj){
					dd='success';
				}
				
				$.post("${pageContext.request.contextPath}/sendMessage/sendMessageAction_sendPhone.shtml?phone="+phone+"&message="+dd+/* "&proolevels="+proolevelList+"&proofDomain="+proofDomainList+"&proofLanguage="+proofLanguageList+ */"&type="+type,function(data){
		   		   if(data!=null && data!=""){
		   			   
		   			layer.confirm('短信已通知客户!', function(index) {
    					parent.grid.loadData();
    					parent.layer.close(parent.pageii);
    					
						return false;
					});
		   			
		   		   }else{
		   			layer.alert('短信通知客户发送失败，请重新发送！', 3);
		   		   }
		  	     },"text");
				
				
				$("#form")[0].reset();
			}else{
				layer.alert('审核失败！', 3);
			}
		},'json'); 	
}


function notverify(){
var pid=document.getElementById("transId").value;
var phone=document.getElementById("phone").value;
var type=document.getElementById("type").value;

  
//短信通知译员
	$.post("${pageContext.request.contextPath}/sendMessage/sendMessageAction_sendPhone.shtml?phone="+phone+"&message=error"/* &proolevels="+proolevelList+"&proofDomain="+proofDomainList+"&proofLanguage="+proofLanguageList */+"&type="+type,function(data){
		   if(data!=null && data!=""){
			   
			layer.confirm('短信已通知译员!', function(index) {
			parent.grid.loadData();
			parent.layer.close(parent.pageii);
			
			return false;
		});
		   }else{
			layer.alert('短信通知译员发送失败，请重新发送！', 3);
		   }
	     },"text");  
}
</script>
</head>
<body>
	<form id="form" name="form" class="form-horizontal" method="post">
	<input type="hidden" name="comId" value="${onLineUserImpl.id }" id="transId">
	<input type="hidden" name="userId" value="${clientUserImpl.id }" id="userId" >
	<input type="hidden" name="type" value="${type }" id="type">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">企业中文名称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.nameZH }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">企业英文名称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.nameEN }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">企业简称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.nameShort }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">公司地址</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.address }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">官网</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.Ltd }">
				</div>
			</div>
	   	   <div class="line line-dashed line-lg pull-in"></div>
	   	   
	   	   
	   	   
	   	   <div class="form-group">
				<label class="col-sm-3 control-label">注册证件号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.registrationNumber }">
				</div>
			</div>
	   	   <div class="line line-dashed line-lg pull-in"></div>
	   	   
	   	   
	   	   <div class="form-group">
				<label class="col-sm-3 control-label">注册地址</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${onLineUserImpl.registrationAddress }">
				</div>
			</div>
	   	   <div class="line line-dashed line-lg pull-in"></div>
	   	   
	   	   
	   	   <div class="form-group">
				<label class="col-sm-3 control-label">证件照</label>
				<div class="col-sm-9">
				<table border="0">
					<c:forEach var="transFile" items="${transPathNameMap}">
					<tr>
						<!-- <td ondblclick="downFile(this)"> -->
						<td>
							${transFile.value}
							<%-- <input type='hidden' value="${transFile.key}" id="fileUrel">
							<input type='hidden' value="${transFile.value}" id="fileName"> --%>
							<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${transFile.value}&fileURL=${transFile.key}">下载</a>
							<!-- <span></span> -->
							<%-- <input id="url" value="${transFile.key}"> --%>
						</td>
					</tr>
					</c:forEach>
				</table>
					<%-- <input readOnly="readonly" type="text" class="form-control"
						value="${transfer.realName }"> --%>
					<%-- <img  id="registerURL" alt="registerURL" src='http://localhost:8080/Aitrans${onLineUserImpl.registrationURL}' style="width:50px;height:70px"> --%>
					
					<%-- <a  id="registerURL" href="${pageContext.request.contextPath}/project/project_download.shtml?urlname=${ff.value}&fileURL=${ff.key}">${ff.value}</a> --%>
				</div>
			</div>
	   	   <div class="line line-dashed line-lg pull-in"></div>
	   	   
	   	   
			<div class="form-group">
				<label class="col-sm-3 control-label">移动电话</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control" name="phone" id="phone" value="${onLineUserImpl.tel }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">固定电话</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control" name="phone" id="phone" value="${onLineUserImpl.cel }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${clientUserImpl.emaile }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">公司账户</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${clientUserImpl.bankAccount }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">开户行</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${clientUserImpl.openingBank }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">联系人</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${clientUserImpl.realName }">				
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
		</div>
			<footer class="panel-footer text-right bg-light lter">
			<button type="button" onclick="verify('yes')" id="back" class="btn btn-success btn-s-xs">审核通过</button>
			<button type="button" onclick="verify('no')" id="back" class="btn btn-success btn-s-xs">审核未通过</button>
			<button type="button" id="back" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">取消</button>
			</footer> 
		</section>
	</form>
<script type="text/javascript">
	$(function(){
	    $("#registerURL").click(function(){
	        var width = $(this).width();
	        if(width==500)
	        {
	            $(this).width(50);
	            $(this).height(70);
	        }
	        else
	        {
	            $(this).width(500);
	            $(this).height(700);
	        }
	    });
	});
</script>
</body>
</html>