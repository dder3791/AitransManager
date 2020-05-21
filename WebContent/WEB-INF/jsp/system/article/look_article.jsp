<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/article/addArticle.js"></script>
<script type="text/javascript"	src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function closeWin(){
	layer.confirm('是否关闭窗口？', {icon: 3}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}
</script>
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
	action="${pageContext.request.contextPath}/article/editEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<input type="hidden" value="${articleInfo.id }" name="articleFormMap.id">
			<div class="form-group">
				<label class="col-sm-3 control-label">资讯标题：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="articleFormMap.title" id="title" value="${articleInfo.title }" readonly="readonly"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">资讯内容：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="articleFormMap.context" id="context" value="${articleInfo.context }" readonly="readonly">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">发布日期：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="articleFormMap.issueDate" id="issueDate" readonly="readonly" value="${articleInfo.issueDate }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">发布人：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入发布作者" name="articleFormMap.auther" id="auther" value="${articleInfo.auther }" readonly="readonly">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">图片：</label>
				<div class="col-sm-9">
					<img src='${articleInfo.url}'>
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">咨询类型：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="articleFormMap.type" id="type" value="${articleInfo.type }" readonly="readonly">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否热门：</label>
				<div class="col-sm-9">
					<input type="radio" name="articleFormMap.hot" value="1" <c:if test="${articleInfo.hot==1 }">checked="checked"</c:if> readonly="readonly">YES 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="articleFormMap.hot" value="0" <c:if test="${articleInfo.hot==0 }">checked="checked"</c:if> readonly="readonly">NO
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否精品</label>
				<div class="col-sm-9">
					<input type="radio" name="articleFormMap.elite" value="1" <c:if test="${articleInfo.elite==1 }">checked="checked"</c:if> readonly="readonly">YES 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="articleFormMap.elite" value="0" <c:if test="${articleInfo.elite==0 }">checked="checked"</c:if> readonly="readonly">NO
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<a href="#" class="btn btn-s-md btn-info btn-rounded" onclick="closeWin()">关闭</a>
		</footer> 
	</section>
</form>
</body>
</html>