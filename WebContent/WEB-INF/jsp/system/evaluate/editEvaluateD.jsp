<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/evaluate/editEvaluate.js"></script>
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
$(function() {
	//查找译员下对应得项目	
	$("#nickname").change(function(){
		var nickname=$("#nickname").val();
		$.post("/AitransManager/evaluate/findtran_project.shtml?nickname="+nickname,
		function(data){
			var oSelect=document.getElementById("projectName");
		        var oldOptions=oSelect.getElementsByTagName("option");
		        for(var k=1;k<oldOptions.length;){
		           oSelect.removeChild(oldOptions[k]);
		        }
		       for(var i=0;i<data.length;i++){
			     	$("#projectName").append("<option value="+data[i].id+">"+data[i].name+"</option>");
			    	} 	
	},"json");	
		
	});
	
	//根据项目id,查找公司案号
	$("#projectName").change(function(){
				var projectName = document.getElementById("projectName").value;
				if (projectName != '' || projectName!=null) {
					$.post("/AitransManager/evaluate/findReference.shtml?projectName="+ projectName,function(data) {
						$("#reference").prop("value",data);
					}, "json");
				}
			})
	$("#oriLanguage").change(function(){
		   var oriLanguage=$("#oriLanguage").val();
		    $.post("/AitransManager/evaluate/findProofreadlanguage.shtml?oriLanguage="+oriLanguage,function(data){
		    	var oSelect=document.getElementById("proofreaderId");
		        var oldOptions=oSelect.getElementsByTagName("option");
		        for(var k=1;k<oldOptions.length;){
		           oSelect.removeChild(oldOptions[k]);
		        }
		       for(var i=0;i<data.length;i++){
			     	$("#proofreaderId").append("<option value="+data[i].id+">"+data[i].nickname+"</option>");
			    	} 	
		    	
		    },"json");
		
	})
})
</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/evaluate/editEvaluate.shtml" >
	<section class="panel panel-default">
		<div class="panel-body">
		   <input type="hidden" name="id" value="${evaluate.id}" id="evaluateId">
		   <div class="form-group" >
				<label class="col-sm-3 control-label">译员名称</label>
				<div class="col-sm-9">
						<select id="nickname" name="EvaluateFormMap.nickname"
							class="selectpicker show-tick form-control">
							<c:forEach items="${translators }" var="translator">
							<c:if test="${evaluate.translatorId eq translator.id }">
							<option value="${translator.nickname }" selected="selected">${translator.nickname}</option>
							</c:if>
							<c:if test="${evaluate.translatorId != translator.id }">
							<option value="${translator.nickname }" >${translator.nickname}</option>
							</c:if>
							</c:forEach>
						</select> 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" >
				<label class="col-sm-3 control-label">所在项目</label>
				<div class="col-sm-9">
						<select id="projectName" name="EvaluateFormMap.name" class="selectpicker show-tick form-control">
							<c:if test="${evaluate.name eq projectName}">
							<option value="${projectName }" selected="selected">${projectName }</option>
						    </c:if>
						    <c:if test="${evaluate.name != projectName}">
							<option value="${projectName }" >${projectName }</option>
						    </c:if>
						</select> 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司案号</label>
				<div class="col-sm-9" >
					<input type="text" class="form-control"
					 name="EvaluateFormMap.reference" id="reference" disabled="disabled" value="${evaluate.reference}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">校对语言</label>
				<div class="col-sm-9">
					 <select id="oriLanguage" name="EvaluateFormMap.oriLanguage"
						class="selectpicker show-tick form-control"> 
						<option value="EN" <c:if test="${evaluate.oriLanguage eq 'EN'}">selected</c:if>>英语</option>
						<option value="JP" <c:if test="${evaluate.oriLanguage eq 'JP' }">selected</c:if>>日语</option>
						<option value="KOR" <c:if test="${evaluate.oriLanguage eq 'KOR' }">selected</c:if>>韩语</option>
						<option value="GER" <c:if test="${evaluate.oriLanguage eq 'GER' }">selected</c:if>>德语</option>
						<option value="FR" <c:if test="${evaluate.oriLanguage eq 'FR' }">selected</c:if>>法语</option>
					 </select> 
				</div>
		</div>
		<div class="line line-dashed line-lg pull-in"></div>
			 <div class="form-group" id="proofread" >
				<label class="col-sm-3 control-label">校对员</label>
				<div class="col-sm-9">
				       <select id="proofreaderId" name="EvaluateFormMap.proofreaderId" class="selectpicker show-tick form-control">
				         <c:forEach items="${ProofreaderList }" var="proofread">
					       <c:if test="${proofread.id eq evaluate.proofreaderId }">
						   <option value="${evaluate.proofreaderId }" selected="selected">${proofread.nickname}</option>
						   </c:if>
						    <c:if test="${proofread.id != evaluate.proofreaderId }">
						   <option value="${evaluate.proofreaderId }" >${proofread.nickname}</option>
						   </c:if>
						 </c:forEach>
						 </select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">交案日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择交案日期"
						name="EvaluateFormMap.comingDate" id="comingDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" value="${evaluate.comingDate}">
				</div>
			</div>
			 <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">完成日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择完成日期"
						name="EvaluateFormMap.completeDate" id="completeDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" value="${evaluate.completeDate}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">技术难度</label>
				<div class="col-sm-9">
					<input type="radio"  name="EvaluateFormMap.difficultyT" id="difficultyT" value="简单"  <c:if test="${evaluate.difficultyT == '简单'}">checked</c:if>>简单
					<input type="radio"  name="EvaluateFormMap.difficultyT" id="difficultyT" value="中级" <c:if test="${evaluate.difficultyT == '中级'}">checked</c:if>>中级
				    <input type="radio"  name="EvaluateFormMap.difficultyT" id="difficultyT" value="高难" <c:if test="${evaluate.difficultyT == '高难'}">checked</c:if>>高难 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">语言难度</label>
				<div class="col-sm-9" >
					<input type="radio"  name="EvaluateFormMap.difficultyL" id="difficultyL" value="简单" <c:if test="${evaluate.difficultyL == '简单'}">checked</c:if>>简单
					<input type="radio"  name="EvaluateFormMap.difficultyL" id="difficultyL" value="中级" <c:if test="${evaluate.difficultyL == '中级'}">checked</c:if>>中级
				    <input type="radio"  name="EvaluateFormMap.difficultyL" id="difficultyL" value="高难" <c:if test="${evaluate.difficultyL == '高难'}">checked</c:if>>高难 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">分值评定</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					 name="EvaluateFormMap.score" id="score" value="${evaluate.score}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">主要问题</label>
				<div class="col-sm-9">
					<textarea rows="10" cols="5" type="text" class="form-control"
					 name="EvaluateFormMap.problems" id="problems">${evaluate.problems}</textarea>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">检查日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" 
						name="EvaluateFormMap.checkDate" id="checkDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" value="${evaluate.checkDate}">
				</div>
			</div>
			 <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">检查人员</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					 name="EvaluateFormMap.checker" id="checker" value="${evaluate.checker}">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button id=“sub1” type="submit" class="btn btn-success btn-s-xs" >提交</button>
		</footer> 
	</section>
</form> 
</body>
</html>