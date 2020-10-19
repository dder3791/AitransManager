<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/cloud/upload.js"></script>

<style type="text/css">
.col-sm-3 {
	width: 15%;
	float: left;
}

.col-sm-9 {
	width: 85%;
	float: left;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	

	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	
	<form id="form" name="form" class="form-horizontal" enctype="multipart/form-data" method="post"
		action="${ctx}/cloud/manager/upload.shtml">
		<section class="panel panel-default">
		<div class="panel-body">

			<div class="form-group">
				<label class="col-sm-3 control-label">模块名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="moduleName"
						name="moduleName" value="${moduleName}">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">模块数据</label>
				<div class="col-sm-9">				 
					<input type="file" class="form-control" id="" name="file" required   lay-verify="required"  autocomplete="off" id="layui-upload" class="layui-input layui-upload"
						 value="">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				
				<div class="col-sm-9">
					<input type="hidden" class="form-control" id="id"
						name="id" value="${id}">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">上传</button>
		</footer> 
	</section>
	</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>