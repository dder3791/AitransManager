<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/evaluate/addEvaluate.js"></script>
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
			});
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
	}); 
});
</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/evaluate/addEvaluate.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
		   
		   <div class="form-group" >
				<label class="col-sm-3 control-label">译员名称</label>
				<div class="col-sm-9">
						<select id="nickname" name="nickname"
							class="selectpicker show-tick form-control">
							<option value="">--请选择--</option>
							<c:forEach items="${translators }" var="translator">
							<option value="${translator.nickname }">${translator.nickname}</option>
							</c:forEach>
						</select> 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" >
				<label class="col-sm-3 control-label">所在项目</label>
				<div class="col-sm-9">
						<select id="projectName" name="name" class="selectpicker show-tick form-control">
							<option value="">--请选择--</option>
							<c:forEach items="${projectList }" var="project">
							<option value="${project.name }">${project.name}</option>
							</c:forEach>
						</select> 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司案号</label>
				<div class="col-sm-9" >
					<input type="text" class="form-control"
					 name="EvaluateFormMap.reference" id="reference" readOnly>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">校对语言</label>
				<div class="col-sm-9">
					 <select id="oriLanguage" name="EvaluateFormMap.oriLanguage"
						class="selectpicker show-tick form-control"> 
                        <option value="EN" >英语</option>
						<option value="JP" >日语</option>
						<option value="KOR">韩语</option>
						<option value="GER">德语</option>
						<option value="FR" >法语</option>
					 </select> 
				</div>
		</div>
			<div class="line line-dashed line-lg pull-in"></div>
			 <div class="form-group" id="proofread" >
				<label class="col-sm-3 control-label">校对员</label>
				<div class="col-sm-9">
						<select id="proofreaderId" name="EvaluateFormMap.proofreaderId"
							class="selectpicker show-tick form-control">
								<option value="">--请选择--</option>
						</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">交案日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择交案日期"
						name="EvaluateFormMap.comingDate" id="comingDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			 <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">完成日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择完成日期"
						name="EvaluateFormMap.completeDate" id="completeDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">技术难度</label>
				<div class="col-sm-9">
					<input type="radio"  name="EvaluateFormMap.difficultyT" id="difficultyT" value="简单">简单
					<input type="radio"  name="EvaluateFormMap.difficultyT" id="difficultyT" value="中级">中级
				    <input type="radio"  name="EvaluateFormMap.difficultyT" id="difficultyT" value="高难">高难 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">语言难度</label>
				<div class="col-sm-9">
					<input type="radio"  name="EvaluateFormMap.difficultyL" id="difficultyL" value="简单">简单
					<input type="radio"  name="EvaluateFormMap.difficultyL" id="difficultyL" value="中级">中级
				    <input type="radio"  name="EvaluateFormMap.difficultyL" id="difficultyL" value="高难">高难 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">分值评定</label>
				<div class="col-sm-9">
					<input type="text" class="form-control score"
					 name="EvaluateFormMap.score" id="score">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">主要问题</label>
				<div class="col-sm-9">
					<textarea rows="10" cols="5" class="form-control"
					 name="EvaluateFormMap.problems" id="problems"></textarea>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">检查日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" 
						name="EvaluateFormMap.checkDate" id="checkDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			 <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">检查人员</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					 name="EvaluateFormMap.checker" id="checker">
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