<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/project/addProject_type.js"></script>
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
	action="${pageContext.request.contextPath}/project/addEntity.shtml?type=processesType">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">流程类型</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入流程类型" name="procedureTypeFormMap.procedureType" id="processesType"> 
					<!-- <select id="level" name="processesTypeFormMap.name"
						class="selectpicker show-tick form-control">
						<option value="1" >只翻译</option>
						<option value="2" >翻译和校对</option>
						<option value="3" >翻译,校对和审核</option>
					</select> -->
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入详细描述" name="procedureTypeFormMap.description" id="description">
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