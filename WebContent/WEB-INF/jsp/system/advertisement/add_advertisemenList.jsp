<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/advertisement/add_picAdvertise.js"></script>
<style type="text/css">
/* #but button {
	margin-bottom: 5px;
	margin-right: 5px;
} */
.col-sm-3 {
	width: 22%;
	float: left;
}

.col-sm-9 {
	width: 78%;
	float: left;
}

/* label[class^="btn btn-default"] {
	margin-top: -4px;
} */
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post" action="${pageContext.request.contextPath}/advertisement/add_advertisemenList.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">名称（中文）</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入名称（中文）" name="advertiseListFormMap.nameCH" id="nameCH"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">名称（英文）</label>
				<div class="col-sm-9">
				<input type="text" class="form-control" placeholder="请输入名称（英文）" name="advertiseListFormMap.nameEN" id="nameEN"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">国家（中文）</label>
				<div class="col-sm-9">
					<select class="form-control" name="advertiseListFormMap.countryCH" id="countryCH">
						<option value='中国' selected="selected">中国</option>
						<option value='法国'>法国</option>
						<option value='美国'>美国</option>
						<option value='德国'>德国</option>
						<option value='日本'>日本</option>
						<option value='俄国'>俄国</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">国家（英文）</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="advertiseListFormMap.countryEN" id='countryEN' value="${China }">
				</div>
				
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">所在省</label>
				<div class="col-sm-9" >
					<select id="province" class="form-control" name="advertiseListFormMap.province"></select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">简介（中文）</label>
				<div class="col-sm-9">
					<textarea rows="5" cols="30" class="form-control" name="advertiseListFormMap.introCH" id="introCH"></textarea>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">简介（英文）</label>
				<div class="col-sm-9">
					<textarea rows="5" cols="30" class="form-control" name="advertiseListFormMap.introEN" id="introEN"></textarea>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">链接地址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入链接地址" name="advertiseListFormMap.web" id="auther">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">排序类型</label>
				<div class="col-sm-9">
					<select class="form-control" name="advertiseListFormMap.type" id="type">
						<option value='1' selected="selected">固定</option>
						<option value='0'>随机</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否可用</label>
				<div class="col-sm-9">
					<select class="form-control"name="advertiseListFormMap.isuseful" id="isUseful">
						<option value='1' selected="selected">可用</option>
						<option value='0'>不可用</option>
					</select>
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>

<script type="text/javascript">

function getCountry(){
	$.post("${pageContext.request.contextPath}/advertisement/getCountry.shtml",{"country":"1","provinces":""},function(data){
		for(var i=1;i<data.length;i++){
			document.getElementById("province").options.add(new Option(data[i],data[i]));
		}
	},'json');
}

$(function(){
	if($("#countryCH").val()=='中国'){
		document.getElementById("countryEN").value="China";
		getCountry();
	}
	
	$("#countryCH").change(function(){
		if($("#countryCH").val()=='中国'){
			document.getElementById("countryEN").value="China";
			getCountry();
		}
		if($("#countryCH").val()=='法国'){
			document.getElementById("province").options.length=0;
			document.getElementById("countryEN").value="France";
		}
		if($("#countryCH").val()=='德国'){
			document.getElementById("province").options.length=0;
			document.getElementById("countryEN").value="Germany";
		}
		if($("#countryCH").val()=='日本'){
			document.getElementById("province").options.length=0;
			document.getElementById("countryEN").value="Japan";
		}
		if($("#countryCH").val()=='美国'){
			document.getElementById("province").options.length=0;
			document.getElementById("countryEN").value="US";
		}
		if($("#countryCH").val()=='俄国'){
			document.getElementById("province").options.length=0;
			document.getElementById("countryEN").value="Russia";
		}
		
		
	});
})
</script>
</body>
</html>