<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/project/add.js"></script>
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
	action="${pageContext.request.contextPath}/project/addEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
		
			<div class="form-group">
				<label class="col-sm-3 control-label">客户名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户名称" name="projectFormMap.name" id="name">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户所在地</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入术语名称" name="projectFormMap.address" id="address">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入项目经理姓名" name="projectFormMap.head" id="head">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">创建时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请选择创建时间" name="projectFormMap.createTime" id="createTime">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">完成时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请选择完成时间" name="projectFormMap.completeTime" id="completeTime">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户描述" name="projectFormMap.description" id="description">
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