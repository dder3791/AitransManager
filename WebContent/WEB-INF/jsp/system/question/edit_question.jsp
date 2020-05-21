<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/question/editQuestion.js"></script>
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
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/question/editEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">常见问题：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入常见问题" name="questionFormMap.question" id="question" value="${questionInfo.question }"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">问题回答：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入问题回答" name="questionFormMap.answer" id="answer" value="${questionInfo.answer }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">问题类型：</label>
				<div class="col-sm-9">
					<select name="questionFormMap.type">
						<option value="关于译员" <c:if test="${questionInfo.type=='关于译员' }">selected='selected'</c:if>>关于译员</option>
						<option value="关于爱译" <c:if test="${questionInfo.type=='关于爱译' }">selected='selected'</c:if>>关于爱译</option>
						<option value="关于客户" <c:if test="${questionInfo.type=='关于客户' }">selected='selected'</c:if>>关于客户</option>
					</select>
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