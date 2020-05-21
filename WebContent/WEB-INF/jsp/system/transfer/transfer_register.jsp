<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/register.js"></script>
<style type="text/css">
.col-sm-3 {
	width: 15%;
	float: left;
	text-align: right;
}

.col-sm-9 {
	width: 85%;
	float: left;
	text-align: left;
}

label[class^="btn btn-default"] {
	margin-top: -4px;
}
</style>
</head>
<body>

	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/transfer/register.shtml" >
		
		<input type="hidden" name="translatorFormMap.roleName" value="译员">
		
		<section class="panel panel-default">
		<div class="line line-dashed line-lg pull-in"></div>
		
			<input type="hidden" name="translatorFormMap.level" value="3">
			<div class="control-group">
				<div class="form-group">
					<div class="main_input_box">
						<label class="col-sm-3 control-label">账号</label>
						<div class="col-sm-9">
							<input type="text" placeholder="请输入账号" name="translatorFormMap.accountName"
								class="form-control checkacc" id="accountName"
								style="height: 32px; margin-bottom: 0px;"/>
						</div>
					</div>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="control-group">
				<div class="form-group">
					<div class="main_input_box">
						<label class="col-sm-3 control-label">密码</label>
						<div class="col-sm-9">
							<input type="password" placeholder="请输入密码" name="translatorFormMap.password"
								class="form-control" id="password"
								style="height: 32px; margin-bottom: 0px;"/>
						</div>
					</div>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="control-group">
				<div class="form-group">
					<div class="main_input_box">
						<label class="col-sm-3 control-label">请重新输入密码</label>
						<div class="col-sm-9">
							<input type="password" placeholder="请再次输入密码" name="translatorFormMap.rePassword" 
								class="form-control" id="rePassword"
								style="height: 32px; margin-bottom: 0px;"/>
						</div>
					</div>
				</div>
			</div>
		
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">学历</label>
				<div class="col-sm-9">
					<select id="degree" name="translatorFormMap.degree" class="selectpicker show-tick form-control">
						<option value="本科" >本科</option>
						<option value="硕士" >硕士</option>
						<option value="博士" >博士</option>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">专业</label>
				<div class="col-sm-9">
					<select id="degree" name="translatorFormMap.major" class="selectpicker show-tick form-control">
						<c:forEach items="${major }" var="major">
							<option value="${major.name }" >${major.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<input type="hidden" value="0"  name="TranslatorFormMap.point">
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译领域</label>
				<div class="col-sm-9">
					<input type="hidden" value="${transfer.domain }" id="transferDomain">
					
					<c:forEach items="${domain }" var="domain">
						<label>
							<input type="checkbox" value="${domain.name }" name="translatorFormMap.domain">${domain.name }
						</label>
					</c:forEach>
					
					
				</div>
			</div>
			
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">注册</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
	onloadurl();
	</script>
	<script type="text/javascript"
		src="${ctx}/notebook/notebook_files/bootstrap-filestyle.min.js">
	</script>
</body>
</html>