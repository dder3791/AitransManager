<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/project/editProjectFlow.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	processesTypeState();
	
	$("#cooperativeState").change(function() {
		//清空译员
		var oSelect = document.getElementById("transferId");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 1; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		
		var ocheck = document.getElementById("transCheck");
		var oldOptions = ocheck.childNodes;
		//alert("原来的子元素"+oldOptions);
		for (var k = 1; k < oldOptions.length;) {
			ocheck.removeChild(oldOptions[k]);
		}
		
		//清空校对员
		var oSelect = document.getElementById("verifierId");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 1; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		
		var ocheck = document.getElementById("verifiCheck");
		var oldOptions = ocheck.childNodes;
		alert("原来的子元素"+oldOptions);
		for (var k = 1; k < oldOptions.length;) {
			ocheck.removeChild(oldOptions[k]);
		}
		
		//清空审核员
		
		var oSelect = document.getElementById("auditorId");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 1; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		
		var ocheck = document.getElementById("auditorCheck");
		var oldOptions = ocheck.childNodes;
		//alert("原来的子元素"+oldOptions);
		for (var k = 1; k < oldOptions.length;) {
			ocheck.removeChild(oldOptions[k]);
		}
		
		var cooperativeState = document.getElementById("cooperativeState").value;
		if(cooperativeState==0){
			//不协同
			$("#tselect").show();
			$("#vselect").show();
			$("#aselect").show();
			$("#transCheck").hide();
			$("#verifiCheck").hide();
			$("#auditorCheck").hide();
			
		}if(cooperativeState==1){
			//协同
			$("#tselect").hide();
			$("#vselect").hide();
			$("#aselect").hide();
			$("#transCheck").show();
			$("#verifiCheck").show();
			$("#auditorCheck").show();
			
		}
	});
	//alert("进入js");
	$("#language").change(function() {
		//alert("进入根据语言查找译员");
		var proid = document.getElementById("language").value;
		var domain = document.getElementById("domain").value;
		$.post("/AitransManager/transfer/findtransIdandName.shtml?transid="+ proid+"&domain="+domain,
		function(data) {
			var cooperativeState = document.getElementById("cooperativeState").value;
			//alert(cooperativeState);
			if(cooperativeState==0){
				var oSelect = document.getElementById("transferId");
				var oldOptions = oSelect.getElementsByTagName("option");
				for (var k = 1; k < oldOptions.length;) {
					oSelect.removeChild(oldOptions[k]);
				}
				//  alert("date"+data);
				$("#transferId").append("<option value='1138'>待定</option>");
				for (var i = 0; i < data.length; i++) {

					$("#transferId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
					}
				}
			if(cooperativeState==1){
				//获取原来的
				var ocheck = document.getElementById("transCheck");
				var oldOptions = ocheck.childNodes;
				//alert("原来的子元素"+oldOptions);
				for (var k = 1; k < oldOptions.length;) {
					ocheck.removeChild(oldOptions[k]);
				}
					for (var i = 0; i < data.length; i++) {
							//alert("江南");
						 var oin=document.createElement("input");
			        	 oin.setAttribute("type","checkbox");
			        	 oin.setAttribute("name","translatorId");
			        	 oin.setAttribute("id","transferId");
			        	 oin.setAttribute("value",data[i].id);				        	 
			        	 document.getElementById("transCheck").appendChild(oin);
			        	 var myText = document.createTextNode(data[i].nickname);
			        	 document.getElementById("transCheck").appendChild(myText);
					}
				}
			}, "json");
		select1();
		select2();
	});
	 
	$("#domain").change(function() {
		//alert("进入根据语言查找译员");
		var proid = document.getElementById("language").value;
		var domain = document.getElementById("domain").value;
		$.post("/AitransManager/transfer/findtransIdandName.shtml?transid="+ proid+"&domain="+domain,
		function(data) {
			var cooperativeState = document.getElementById("cooperativeState").value;
			//alert(cooperativeState);
			if(cooperativeState==0){
				var oSelect = document.getElementById("transferId");
				var oldOptions = oSelect.getElementsByTagName("option");
				for (var k = 1; k < oldOptions.length;) {
					oSelect.removeChild(oldOptions[k]);
				}
				//  alert("date"+data);
				$("#transferId").append("<option value='1138'>待定</option>");
				for (var i = 0; i < data.length; i++) {

					$("#transferId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
					}
				}
			if(cooperativeState==1){
				//获取原来的
				var ocheck = document.getElementById("transCheck");
				var oldOptions = ocheck.childNodes;
				//alert("原来的子元素"+oldOptions);
				for (var k = 1; k < oldOptions.length;) {
					ocheck.removeChild(oldOptions[k]);
				}
					for (var i = 0; i < data.length; i++) {
							//alert("江南");
						 var oin=document.createElement("input");
			        	 oin.setAttribute("type","checkbox");
			        	 oin.setAttribute("name","translatorId");
			        	 oin.setAttribute("id","transferId");
			        	 oin.setAttribute("value",data[i].id);				        	 
			        	 document.getElementById("transCheck").appendChild(oin);
			        	 
			        	 var myText = document.createTextNode(data[i].nickname);
			        	 document.getElementById("transCheck").appendChild(myText);
					}
				}
			}, "json");
		select1();
		select2();
	});
	
	$("#processesTypeId").change(function(){
		var proid=document.getElementById("processesTypeId").value;
		
			if(proid==1){
				$("#verifierdiv").hide();
				$("#auditordiv").hide();
				$("#verifierId").prop({"value":89});
				$("#auditorId").prop({"value":89});
			}
			if(proid==2){
				$("#verifierdiv").show();
				$("#auditordiv").hide();
				$("#auditorId").prop({"value":89});
				select1();
			}
			if(proid==3){
				$("#verifierdiv").show();
				$("#auditordiv").show();
				select1();
				select2();
			}						
	});
	
	function select1() {
		var language = document.getElementById("language").value;
		var domain = document.getElementById("domain").value;
		
		$.post("/AitransManager/vierfier/findIdandName.shtml?language="+ language+"&domain="+domain,
				function(data) {
			var cooperativeState = document.getElementById("cooperativeState").value;
			//alert(cooperativeState);
			if(cooperativeState==0){
				var oSelect = document.getElementById("verifierId");
				var oldOptions = oSelect.getElementsByTagName("option");
				for (var k = 1; k < oldOptions.length;) {
					oSelect.removeChild(oldOptions[k]);
				}
				//  alert("date"+data);
				//$("#verifierId").append("<option value='89'>待定</option>");
				for (var i = 0; i < data.length; i++) {

					$("#verifierId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
				}
				}
			if(cooperativeState==1){
				//获取原来的
				var ocheck = document.getElementById("verifiCheck");
				var oldOptions = ocheck.childNodes;
				alert("原来的子元素"+oldOptions);
				for (var k = 1; k < oldOptions.length;) {
					ocheck.removeChild(oldOptions[k]);
				}
					for (var i = 0; i < data.length; i++) {
							//alert("江南");
						 var oin=document.createElement("input");
			        	 oin.setAttribute("type","checkbox");
			        	 oin.setAttribute("name","proofreaderId");
			        	 oin.setAttribute("id","verifierId");
			        	 oin.setAttribute("value",data[i].id);				        	 
			        	 document.getElementById("verifiCheck").appendChild(oin);
			        	 var myText = document.createTextNode(data[i].nickname);
			        	 document.getElementById("verifiCheck").appendChild(myText);
					}
				}
			}, "json");
	};

	//根据语言查找审核员
	function select2() {
		var language = document.getElementById("language").value;
		var domain = document.getElementById("domain").value;
		
		$.post("/AitransManager/auditor/findIdandName.shtml?language="+ language+"&domain="+domain,
				function(data) {

			var cooperativeState = document.getElementById("cooperativeState").value;
			//alert(cooperativeState);
			if(cooperativeState==0){
				var oSelect = document.getElementById("auditorId");
				var oldOptions = oSelect.getElementsByTagName("option");
				for (var k = 1; k < oldOptions.length;) {
					oSelect.removeChild(oldOptions[k]);
				}
				//  alert("date"+data);
				//$("#auditorId").append("<option value='89'>待定</option>");
				for (var i = 0; i < data.length; i++) {

					$("#auditorId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
				}
				}
			if(cooperativeState==1){
				//获取原来的
				var ocheck = document.getElementById("auditorCheck");
				var oldOptions = ocheck.childNodes;
				//alert("原来的子元素"+oldOptions);
				for (var k = 1; k < oldOptions.length;) {
					ocheck.removeChild(oldOptions[k]);
				}
					for (var i = 0; i < data.length; i++) {
							//alert("江南");
						 var oin=document.createElement("input");
			        	 oin.setAttribute("type","checkbox");
			        	 oin.setAttribute("name","auditorId");
			        	 oin.setAttribute("id","auditorId");
			        	 oin.setAttribute("value",data[i].id);				        	 
			        	 document.getElementById("auditorCheck").appendChild(oin);
			        	 var myText = document.createTextNode(data[i].nickname);
			        	 document.getElementById("auditorCheck").appendChild(myText);
					}
				}
					
				}, "json");
	};
	
	$("#btn_add1").click(function(){  
   	 var oin=document.createElement("input");
   	 oin.setAttribute("id","file");
   	 oin.setAttribute("type","file");
   	 oin.setAttribute("name","filename");
   	 oin.setAttribute("style","margin-right: 10px;margin-left: 100px;width: 220px;");
   	 document.getElementById("newUpload1").appendChild(oin);  
   	 document.getElementById("btn_del1").removeAttribute("disabled");
   }); 
});
function processesTypeState(){
	var proid=$("#processesTypeId").val();
	if(proid==1){
		$("#verifierdiv").hide();
		$("#auditordiv").hide();
	}
	if(proid==2){
		$("#verifierdiv").show();
		$("#auditordiv").hide();
		}
	if(proid==3){
		$("#verifierdiv").show();
		$("#auditordiv").show();
	}	
}


function del_1(){  
	var odel=document.getElementById("newUpload1");
	   var ofile=odel.lastElementChild;
	   var name="";
	   name = ofile.getAttribute("id");
	   if(name=='file1'){
		   document.getElementById("btn_del1").setAttribute("disabled","true");
	   }else{
		   odel.removeChild(ofile);
	   }
   }


function radioClick(){
	var value='';  
    var radio = document.getElementsByName("parentPath");  
    for(var i = 0;i<radio.length;i++)  
    {  
        if(radio[i].checked==true)  
        {value = radio[i].value;  
        break;}  
    }  
    
    
    $.post("/AitransManager/project/findURL.shtml?url="+value,function(data) {
    	var oSelect = document.getElementById("path");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 0; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		//alert(data[0]);
		$("#path").append("<option  checked='checked' value="+data[1][0]+">"+data[0][0]+ "</option>");
		for (var i = 1; i < data[0].length; i++) {
			$("#path").append("<option  value="+data[1][i]+">"+data[0][i]+ "</option>");
		}
		
		document.getElementById("url").value=data[1][0];
		
	}, "json");
    
}

function pathChange(){
	var pathValue=document.getElementById("path").value;
	alert(pathValue);
	document.getElementById("url").value=pathValue;
	 $.post("/AitransManager/project/findURL.shtml?url="+pathValue,function(data) {
        	var oSelect = document.getElementById("path");
			var oldOptions = oSelect.getElementsByTagName("option");
			for (var k = 0; k < oldOptions.length;) {
				oSelect.removeChild(oldOptions[k]);
			}
			//alert(data[0]);
			$("#path").append("<option  checked='checked' value="+data[1][0]+">"+data[0][0]+ "</option>");
			for (var i = 1; i < data[0].length; i++) {
				$("#path").append("<option  value="+data[1][i]+">"+data[0][i]+ "</option>");
			}
			
			//document.getElementById("url").value=data[1][0];
			
		}, "json");
	
}

	
</script>

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
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/project/editProjectFlow.shtml?type=projectflow" enctype="multipart/form-data">
	<section class="panel panel-default">
		<div class="panel-body">
			<input type="hidden" value="${projectflowInfo.id }" name="projectFormMap.id" id="projectId">
			<input type="hidden" value="${projectflowInfo.fileURL }" name="projectflowInfo.fileURL" id="projectId">
			<div class="form-group">
				<label class="col-sm-3 control-label">公司案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="projectFormMap.reference" id="reference" value="${projectflowInfo.reference}" readonly="readonly">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户名称</label>
				<div class="col-sm-9">
				<input type="text" class="form-control"id="customerreference" value="${customerInfo.nameZH}" readonly="readonly">
				<input type="hidden" class="form-control" name="projectFormMap.customerId" id="customerreference" value="${projectflowInfo.customerId}">	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="projectFormMap.customerReference" id="customerreference" value="${projectflowInfo.customerReference}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="projectFormMap.name" id="name" value="${projectflowInfo.name }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">是否协同项目</label>
				<div class="col-sm-9">
					<select class="form-control" name="cooperativeState" id="cooperativeState">
					<c:if test="${projectflowInfo.cooperativeState ==1 }">
					<option value="1" selected="selected">协同</option>
					<option value="0">不协同</option>
					</c:if>
					<c:if test="${projectflowInfo.cooperativeState ==0 }">
					<option value="0" selected="selected">不协同</option>
					<option value="1">协同</option>
					</c:if>	
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译类型</label>
				<div class="col-sm-9">
					<select class="form-control" name="projectFormMap.projectTypeId" id="projectTypeId">
						<c:forEach items="${projectTypeMapperList }" var="projectType">
							<c:if test="${projectType.id != projectflowInfo.projectTypeId}">
								<option value="${projectType.id }">${projectType.projectType }</option>
							</c:if>
							<c:if test="${projectType.id == projectflowInfo.projectTypeId}">
								<option value="${projectType.id }" selected="selected">${projectType.projectType }</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
				
			
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语种 </label>
				<div class="col-sm-9">
					<select class="form-control" placeholder="请选择翻译语种" name="projectFormMap.languagePair" id="language">
							<option value="中英" <c:if test="${projectflowInfo.languagePair == '中英'}">selected</c:if>>中英</option>
							<option value="英中" <c:if test="${projectflowInfo.languagePair == '英中'}">selected</c:if>>英中</option>
							<option value="中日" <c:if test="${projectflowInfo.languagePair == '中日'}">selected</c:if>>中日</option>
							<option value="日中" <c:if test="${projectflowInfo.languagePair == '日中'}">selected</c:if>>日中</option>
							<option value="中韩" <c:if test="${projectflowInfo.languagePair == '中韩'}">selected</c:if>>中韩</option>
							<option value="韩中" <c:if test="${projectflowInfo.languagePair == '韩中'}">selected</c:if>>韩中</option>
							<option value="中德" <c:if test="${projectflowInfo.languagePair == '中德'}">selected</c:if>>中德</option>
							<option value="德中" <c:if test="${projectflowInfo.languagePair == '德中'}">selected</c:if>>德中</option>
							<option value="中法" <c:if test="${projectflowInfo.languagePair == '中法'}">selected</c:if>>中法</option>			
							<option value="法中" <c:if test="${projectflowInfo.languagePair == '法中'}">selected</c:if>>法中</option>						
						<c:if test="${projectflowInfo.languagePair == '' || projectflowInfo.languagePair == 'null'  }">
						<option value="" selected>请选择...</option>
						<option value="中英">中英</option>
						<option value="英中">英中</option>
						<option value="日中">日中</option>
						<option value="中日">中日</option>
						<option value="中韩">中韩</option>
						<option value="韩中">韩中</option>
						<option value="德中">德中</option>
						<option value="中德">中德</option>
						<option value="法中">法中</option>
						<option value="中法">中法</option>
						</c:if>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目领域 </label>
				<div class="col-sm-9">
					<select class="form-control" name="domainId"
						id="domain">
						<!-- <option value="">请选择...</option> -->
						<c:forEach items="${domainMapperList }" var="domain">
							<option value="${domain.id }"<c:if test="${domain.id==projectflowInfo.domainId }">selected="selected"</c:if> >${domain.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">译员名称 </label>
				<c:if test="${projectflowInfo.cooperativeState==0 }">
					<div class="col-sm-9" id="tselect">
					<select class="form-control" placeholder="请选择译员" name="translatorId" id="transferId">	
						<c:forEach items="${transList }" var="transfer">
							<option value="${transfer.id }" selected="selected">${transfer.nickname }</option>
						</c:forEach>
					</select>
					</div>
					<div class="col-sm-9" id="transCheck" style="display: none;">
					
					</div>
				</c:if>
				
				<c:if test="${projectflowInfo.cooperativeState==1 }">
					<div class="col-sm-9" id="tselect" style="display: none;">
					<select class="form-control" placeholder="请选择译员" name="translatorId" id="transferId">	</select>
					</div>
					<div class="col-sm-9" id="transCheck" >
						<c:forEach items="${transList }" var="transfer">
							<input type="checkbox" name="translatorId" value="${transfer.id }" checked="checked">${transfer.nickname }
						</c:forEach>
					</div>
				</c:if>
				
				
			</div>
			<div class="line line-dashed line-lg pull-in"></div>		
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">流程种类 </label>
				<div class="col-sm-9">
					<select class="form-control" name="projectFormMap.procedureTypeId" id="processesTypeId">
						<c:forEach items="${processesTypeMapperList }" var="processesType">
							<c:if test="${processesType.id != projectflowInfo.processesTypeId}">
								<option value="${processesType.id}">${processesType.procedureType }</option>
							</c:if>
							<c:if test="${processesType.id == projectflowInfo.procedureTypeId}">
								<option value="${processesType.id}" selected="selected">${processesType.procedureType }</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" id="verifierdiv">
			<label class="col-sm-3 control-label">校对员 </label>
			<c:if test="${projectflowInfo.cooperativeState==0 }">
				<div class="col-sm-9" id="vselect">
				<select class="form-control" placeholder="请选择校验员" name="proofreaderId" id="verifierId">
					<c:forEach items="${verifyList }" var="verify">
						<option value="${verify.id }" selected="selected">${verify.nickname }</option>
					</c:forEach>
				</select>
				</div>
				<div class="col-sm-9" id="verifiCheck" style="display: none;">
				
				</div>
			</c:if>
			
			<c:if test="${projectflowInfo.cooperativeState==1 }">
				<div class="col-sm-9" id="vselect" style="display: none;">
				<select class="form-control" placeholder="请选择校验员" name="proofreaderId" id="verifierId">
					
				</select>
				</div>
				<div class="col-sm-9" id="verifiCheck" >
					<c:forEach items="${verifyList }" var="verify">
						<input type="checkbox" name="proofreaderId" value="${verify.id }" checked="checked">${verify.nickname }
					</c:forEach>
				</div>
			</c:if>
			</div>	
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group" id="auditordiv">
			<label class="col-sm-3 control-label">审核员 </label>
			<c:if test="${projectflowInfo.cooperativeState==0 }">
				<div class="col-sm-9" id="aselect">
				<select class="form-control" placeholder="请选择审核员" name="auditorId" id="auditorId">
					<c:forEach items="${auditorList }" var="auditory">
						<option value="${auditory.id }" selected="selected">${auditory.nickname }</option>
					</c:forEach>
				</select>
				</div>
				<div class="col-sm-9" id="auditorCheck" style="display: none;">
				
				</div>
			</c:if>
			
			<c:if test="${projectflowInfo.cooperativeState==1 }">
				<div class="col-sm-9" id="aselect" style="display: none;">
				<select class="form-control" placeholder="请选择审核员" name="auditorId" id="auditorId">
				</select>
				</div>
				<div class="col-sm-9" id="auditorCheck" >
					<c:forEach items="${auditorList }" var="auditory">
						<input id="auditorId" type="checkbox" name="auditorId" value="${auditory.id }" checked="checked">${auditory.nickname }
					</c:forEach>
				</div>
			</c:if>
				
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择项目经理姓名" name="projectFormMap.head" id="head" value="${projectflowInfo.head }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">创建时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请选择创建时间" name="projectFormMap.createTime" id="createTime" value="${projectflowInfo.createTime }"  readonly="readonly" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">返稿时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请选择返稿时间" name="projectFormMap.completeTime" id="completeTime" value="${projectflowInfo.completeTime }" readonly="readonly" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>	
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目状态</label>
				<div class="col-sm-9">
				<%-- ${projectflowInfo.projectStateId} --%>
					<select class="form-control" name="projectFormMap.projectStateId" id="projectState">
							<option value="3"  id="noStart" <c:if test="${projectflowInfo.projectStateId eq '3' }">selected</c:if>>新建项目</option>
							<option value="4"  id="transing"<c:if test="${projectflowInfo.projectStateId eq'4' }">selected</c:if>>正在翻译</option>
							<option value="5"  id="transing"<c:if test="${projectflowInfo.projectStateId eq '5' }">selected</c:if>>翻译完成</option>
							<option value="6"  id="verifying" <c:if test="${projectflowInfo.projectStateId eq '6' }">selected</c:if>>正在校对</option>
							<option value="7"  id="verifying" <c:if test="${projectflowInfo.projectStateId eq '7' }">selected</c:if>>校对完成</option>
							<option value="8"  id="auditorying" <c:if test="${projectflowInfo.projectStateId eq '8' }">selected</c:if>>正在审核</option>
							<option value="9"  id="auditorying" <c:if test="${projectflowInfo.projectStateId eq '9' }">selected</c:if>>审核完成</option>
							<option value="10"  id="backing" <c:if test="${projectflowInfo.projectStateId eq '10' }">selected</c:if>>返稿完成</option>
							<option value="11"  id="backing" <c:if test="${projectflowInfo.projectStateId eq '11' }">selected</c:if>>等待支付</option>
							<option value="12"  id="end" <c:if test="${projectflowInfo.projectStateId eq '12' }">selected</c:if>>支付完成</option>
							<option value="13"  id="suspend" <c:if test="${projectflowInfo.projectStateId eq '13' }">selected</c:if>>中止</option>
					</select>
				</div>
			</div>	
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">文件上传</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"  readonly="readonly" id="url" name="url" value="${projectflowInfo.fileURL }">
					<select id="newPath" name="sonURL">
						<c:forEach items="${fileInfo }" var="file">
							<option value="${file}">${file }</option>
						</c:forEach>
					</select>
				</div>
				
							
				<div class="col-sm-9" id="newUpload1" align="left" style="width: 240px">
					<br>
					<input type="file" style="margin-right: 10px;margin-left: 100px;width: 220px" id="file1" name="filename"/>
				</div>
				<br><br>
				<div  style="margin-right: 70px" align="right">
					<a id="btn_add1" class="btn btn-primary marR10" style="margin-top: 25px;"> 继续添加</a>
			    	<a id="btn_del1" class="btn btn-primary marR10" style="margin-top: 25px;" onclick="del_1();">删除</a>
				</div>
			</div>
			
			
			<!-- <div class="form-group" >
				<label class="col-sm-3 control-label">文件上传</label>
				<div class="col-sm-9" id="newUpload1" align="left">
					<input type="file" id="file1" name="filename"/>
				</div>
				<div  style="margin-right: 130px" align="right">
					<button type="button" id="btn_add1" class="btn btn-primary marR10"> 继续添加</button>
			    	<button type="button" id="btn_del1" class="btn btn-primary marR10" onclick="del_1();">删除</button>
				</div>
				
			</div> -->
			
			<!-- <div class="form-group">
				<label class="col-sm-3 control-label">文件上传</label>
				<div class="col-sm-9" id="btn1">
					 <input type="text" name="projectFormMap.fileURL" placeholder="请输入文件上传路径" >
					<input type="file" name="filename" />
				</div>
			</div>-->
			<div class="line line-dashed line-lg pull-in"></div> 
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入项目描述" name="projectFormMap.description" id="description" value="${projectflowInfo.description }">
				</div>
			</div>
			
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body>
</html>