<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/document/edit_project_directory.js"></script>
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
function closeWin(){
	layer.confirm('是否关闭窗口？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/document/rename.shtml">
		<section class="panel panel-default">
			<div class="panel-body">
			<input class="form-control checkacc" name="oldname" id="name" value="${oldname }" type="hidden">
			<input type="hidden" name="type" value="${type }">
				<div class="form-group">
					<label class="col-sm-3 control-label">文件路径：</label>
					<div class="col-sm-9">
						<input class="form-control checkacc" name="url" id="url" value="${path }" readonly="readonly">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-3 control-label">重命名为：</label>
					<div class="col-sm-9">
						<input class="form-control checkacc" name="newname" id="name" value="${oldname }">
					</div>
				</div>
				
			</div>
			<footer class="panel-footer text-right bg-light lter">
			<button type="submit" class="btn btn-success btn-s-xs">重命名</button>
			<a href="#" class="btn btn-s-md btn-info btn-rounded" onclick="closeWin()">关闭</a>
			</footer> 
		</section>
   </form>
</body>
</html>