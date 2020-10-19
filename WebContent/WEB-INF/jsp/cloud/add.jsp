<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/cloud/add.js"></script>

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
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/cloud/manager/save.shtml">
		<section class="panel panel-default">
		<div class="panel-body">

			<div class="form-group">
				<label class="col-sm-3 control-label">模块名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="moduleName"
						name="clientCloudFormMap.moduleName" value="">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">模块版本</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="moduleVersion"
						name="clientCloudFormMap.moduleVersion" value="">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">发布时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="releaseTime"
						name="clientCloudFormMap.releaseTime" value="">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">是否生效</label>
				<div class="col-sm-9">
					<select class="form-control" id="language_src"
						style="float: left; width: 200px;" name="clientCloudFormMap.isAvailable">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">MD5值</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="md5Value"
						name="clientCloudFormMap.md5Value" value="">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>

		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">保存</button>
		</footer> 
	</section>
	</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>