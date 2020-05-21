<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/register/register.js"></script>
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


<link rel="stylesheet"	href="${pageContext.servletContext.contextPath }/admin_files/min.css">
<link rel="stylesheet"	href="${pageContext.servletContext.contextPath }/admin_files/login.css">
<link	href="${pageContext.servletContext.contextPath }/admin_files/css.css"	rel="stylesheet" type="text/css">




</head>
<%-- <body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/transfer/register.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
		
			<div class="form-group">
				<label class="col-sm-3 control-label">账号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入账号" name="transferFormMap.name" id="account">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入昵称" name="transferFormMap.nickname" id="nickname">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">密码</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入密码" name="transferFormMap.pwd" id="pwd">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">重新输入密码</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请再输入一次密码" name="transferFormMap.repwd" id="repwd">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">真实姓名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入真实姓名" name="transferFormMap.realName" id="realName">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入您的邮箱" name="transferFormMap.email" id="email">
				</div>
			</div>
			
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body> --%>




<body 
	style="background-image: url('${pageContext.servletContext.contextPath }/admin_files/9.jpg');margin-top:0px;background-repeat:no-repeat;background-size: 100% auto;">
	<div id="loginbox" style="padding-top: 10%;">
		<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
		<form id="form" name="form" class="form-vertical"
			style="background-color: rgba(0, 0, 0, 0.5) !important; background: #000; filter: alpha(opacity = 50); *background: #000; *filter: alpha(opacity = 50); /*黑色透明背景结束*/ color: #FFF; bottom: 0px; right: 0px; border: 1px solid #000;"
			action="${ctx}/transfer/register.shtml"
			method="post">
			<div class="control-group normal_text">
				<table style="width: 100%">
					<tr>
						<td align="left"><img
							src="${pageContext.servletContext.contextPath }/admin_files/logo_left.png"
							alt="Logo"></td>
							<td align="center" style="font-weight: bold;color: gray;">译员注册</td>
						<td align="right"><img
							src="${pageContext.servletContext.contextPath }/admin_files/logo_left.png"
							alt="Logo"></td>
					</tr>
				</table>

			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span >账号:</span>
						<input type="text" placeholder="请输入账号" name="transferFormMap.accountName"
							class="form-control checkacc" id="accountName"
							style="height: 32px; margin-bottom: 0px;"/>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span>密码:</span>
						<input type="password" placeholder="请输入密码" name="transferFormMap.password"
							style="height: 32px; margin-bottom: 0px;"/>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span>Again:</span>
						<input type="password" placeholder="请再次输入密码" name="transferFormMap.rePassword" 
							style="height: 32px; margin-bottom: 0px;"/>
					</div>
				</div>
			</div>
			<div class="form-actions">
					 <span class="pull-right"><button type="submit"
						 class="btn btn-success">注&nbsp;&nbsp;册</button></span>
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="${ctx}/notebook/notebook_files/bootstrap-filestyle.min.js"></script>
</body>
</html>