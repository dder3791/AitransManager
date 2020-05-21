<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/term/editTermNameD.js"></script>
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
$(function() {
$("#isProofread").change(function(){
	var p=$("#isProofread").val();
	if(p==0){
		$("#translator").show();
		$("#auditor").hide();
		$("#proofread").hide();
	    findTranslator();
	}
	if(p==1){
		$("#translator").show();
		$("#proofread").show();
		$("#auditor").hide();
		findProofread();
		findAuditer();
		findTranslator();
	}
	if(p==2){
		$("#translator").show();
		$("#proofread").show();
		$("#auditor").show();
		findProofread();
		findAuditer();
		findTranslator();
	}
  });
$("#domainId").change(function() {
	var oriLanguage=$("#oriLanguage").val();
	var domainId=$("#domainId option:selected").text();
	$.post("/Aitrans/term/findTranslatorLanguage.shtml?oriLanguage="+oriLanguage+"&domainId="+domainId,
			function(data){
        var oSelect=document.getElementById("translatorId");
        var oldOptions=oSelect.getElementsByTagName("option");
        for(var k=0;k<oldOptions.length;){
           oSelect.removeChild(oldOptions[k]);
        }
       for(var i=0;i<data.length;i++){
	     	$("#translatorId").append("<option value="+data[i].id+">"+data[i].nickname+"</option>");
	    	} 
     },"json");	
});
});
//根据语言查找译员
function findTranslator(){
	var oriLanguage=$("#oriLanguage").val();
	var domainId=$("#domainId").val();
	alert(domainId);
	$.post("/Aitrans/term/findTranslatorLanguage.shtml?oriLanguage="+oriLanguage+"&domainId="+domainId,
			function(data){
        var oSelect=document.getElementById("translatorId");
        var oldOptions=oSelect.getElementsByTagName("option");
        for(var k=0;k<oldOptions.length;){
           oSelect.removeChild(oldOptions[k]);
        }
       for(var i=0;i<data.length;i++){
	     	$("#translatorId").append("<option value="+data[i].id+">"+data[i].nickname+"</option>");
	    	} 
     },"json");	
}
//根据语言查找校对员
function findProofread(){
	var oriLanguage=$("#oriLanguage").val();
	$.post("/Aitrans/term/findProofreadlanguage.shtml?oriLanguage="+oriLanguage,function(data){
        var oSelect=document.getElementById("proofreaderId");
        var oldOptions=oSelect.getElementsByTagName("option");
        for(var k=0;k<oldOptions.length;){
           oSelect.removeChild(oldOptions[k]);
        }
       for(var i=0;i<data.length;i++){
	     	$("#proofreaderId").append("<option value="+data[i].id+">"+data[i].nickname+"</option>");
	    	} 
     },"json");	
}

//根据语言查找审核员
function findAuditer(){
	var oriLanguage=$("#oriLanguage").val();
	$.post("/Aitrans/term/findAuditerLanguage.shtml?oriLanguage="+oriLanguage,function(data){
        var oSelect=document.getElementById("auditorId");
        var oldOptions=oSelect.getElementsByTagName("option");
        for(var k=0;k<oldOptions.length;){
           oSelect.removeChild(oldOptions[k]);
        }
       for(var i=0;i<data.length;i++){
	     	$("#auditorId").append("<option value="+data[i].id+">"+data[i].nickname+"</option>");
	    	} 
     },"json");	
}

</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/term/editTermNameD.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
			
			<input type="hidden" name="id" id="termId" value="${term.id}">
			<input type="hidden" name="termTableListFormMap.oriLanguage" value="${term.oriLanguage}">
			
				<label class="col-sm-3 control-label">翻译类型</label>
				<div class="col-sm-9">
					<select id="languagePair" name="termTableListFormMap.languagePair"
						class="selectpicker show-tick form-control">
						<option value="${term.languagePair}">${term.languagePair}</option>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">语料名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入术语名称" name="termTableListFormMap.termName" id="termName"
						value="${term.termName }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" >
				<label class="col-sm-3 control-label">所属领域</label>
				<div class="col-sm-9">
						<select id="domainId" name="termTableListFormMap.domainId"
							class="selectpicker show-tick form-control">
							<c:forEach items="${domain }" var="domain">
								<c:if test="${domain.id ==term.domainId}">
								<option value="${domain.id }" selected="selected">${domain.name}</option>
								</c:if>
								<c:if test="${domain.id !=term.domainId}">
								<option value="${domain.id }">${domain.name}</option>
								</c:if>
							</c:forEach>
						</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译方式</label>
				<div class="col-sm-9">
					<select id="isProofread" name="termTableListFormMap.isProofread"
						class="selectpicker show-tick form-control">
					<c:if test="${term.isProofread ==0 }">
						<option value="0" selected="selected">只翻译</option>
						<option value="1">翻译和校对</option>
						<option value="2">翻译,校对和审核</option>
						</c:if>
						<c:if test="${term.isProofread ==1 }">
						<option value="0" >只翻译</option>
						<option value="1" selected="selected">翻译和校对</option>
						<option value="2">翻译,校对和审核</option>
						</c:if>
						<c:if test="${term.isProofread ==2 }">
						<option value="0" >只翻译</option>
						<option value="1">翻译和校对</option>
						<option value="2" selected="selected">翻译,校对和审核</option>
						</c:if>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" id="translator" >
				<label class="col-sm-3 control-label">译员</label>
				<div class="col-sm-9">
						<select id="translatorId" name="termTableListFormMap.translatorId"
							class="selectpicker show-tick form-control">
							  <c:forEach items="${translatorList }" var="translator">
					       		<c:if test="${translator.id == term.translatorId}">
								<option value="${term.translatorId }" selected="selected">${translator.nickname}</option>
								</c:if>
								<c:if test="${translator.id !=term.translatorId}">
								<option value="${translator.id}">${translator.nickname}</option>
								</c:if>
								</c:forEach>
						</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" id="proofread" >
				<label class="col-sm-3 control-label">校对员</label>
				<div class="col-sm-9" >
						<select id="proofreaderId" name="proofreaderId"
							class="selectpicker show-tick form-control">
					      <c:forEach items="${proofreadList }" var="proofread">
					       		<c:if test="${proofread.id == term.proofreaderId }">
								<option value="${term.proofreaderId }" selected="selected">${proofread.nickname}</option>
								</c:if>
								<c:if test="${proofread.id !=term.proofreaderId}">
								<option value="${proofread.id}">${proofread.nickname}</option>
								</c:if>
								</c:forEach>
						</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" id="auditor" >
				<label class="col-sm-3 control-label">审核员</label>
				<div class="col-sm-9">
						<select id="auditorId" name="auditorId"
							class="selectpicker show-tick form-control">
					    <c:forEach items="${auditerList }" var="auditer">
					       		<c:if test="${auditer.id == term.auditorId}">
								<option value="${term.auditorId }" selected="selected">${auditer.nickname}</option>
								</c:if>
								<c:if test="${auditer.id !=term.proofreaderId}">
								<option value="${auditer.id}">${auditer.nickname}</option>
								</c:if>
								</c:forEach>
						</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户描述" name="termTableListFormMap.remark" id="remark" value="${term.remark}">
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