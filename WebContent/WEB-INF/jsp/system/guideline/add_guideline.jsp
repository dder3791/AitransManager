<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/guideline/addGuideline.js"></script>
<%-- <script type="text/javascript"	src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script> --%>
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
    function checkFile(obj){
    	fileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    	if(fileExt!='.png'&&fileExt!='.jpg'){
    		alert("请上传后缀名为png,jpg的图片!");
    		obj.outerHTML=obj.outerHTML; 
    		return false;
    	}
    }
</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/guideline/addEntity.shtml" enctype="multipart/form-data">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">指南标题：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="guidelineFormMap.flowName" id="flowName"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">指南图示：</label>
				<div class="col-sm-9" id="newUpload1" align="center">
					<input type="file" id="file" name="file" onchange="checkFile(this)" />
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body>
</html>