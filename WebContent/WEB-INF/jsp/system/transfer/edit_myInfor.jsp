<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/edit_myInfor.js"></script>

<!-- 时间控件 -->
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>


<%-- <script type="text/javascript" src="${ctx}/js/date/bootstrap.min.js"></script>
<link href="${ctx}/js/date/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/date/moment-with-locales.min.js"></script>
<script type="text/javascript" src="${ctx}/js/date/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/js/date/bootstrap-datetimepicker.fr.js"></script>
<script type="text/javascript" src="${ctx}/js/date/bootstrap-datetimepicker.zh-CN.js"></script> --%>


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
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/transfer/editEntity.shtml">
		<input type="hidden" class="form-control checkacc"
			value="${transfer.id}" name="transferFormMap.id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">真实姓名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="realName" 
					name="transferFormMap.realName" value="${transfer.realName }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="nickname" 
					name="transferFormMap.nickname" value="${transfer.nickname }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="email" 
					name="transferFormMap.email" value="${transfer.email }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control isMobile" id="phone" 
					name="transferFormMap.phone" value="${transfer.phone }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">住址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="address" 
					name="transferFormMap.address" value="${transfer.address }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<!-- <div class="form-group">
				<label class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="birthday" 
					name="transferFormMap.birthday">
				</div>
			</div> -->
			<!-- <div class="form-group">
				<label class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9" id='datetimepicker'>
					<input type='text' class="time-input form-control" id="birthday"
						name="transferFormMap.birthday" 
						data-date-format="yyyy-dd-mm"/> 
				</div>
			</div> -->
			<div class="form-group">
				<label class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9" id='datetimepicker'>
					<input type='text' class="time-input form-control" id="birthday"
						name="transferFormMap.birthday" value="${transfer.birthday }"
						onFocus="WdatePicker({lang:'zh-cn'})"
						readonly /> 
				</div>
			</div>



			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">学历</label>
				<div class="col-sm-9">
					<select id="degree" name="transferFormMap.degree" class="selectpicker show-tick form-control">
						<option value="本科" <c:if test="${transfer.degree eq '本科' }">selected</c:if>>本科</option>
						<option value="硕士" <c:if test="${transfer.degree eq '硕士' }">selected</c:if>>硕士</option>
						<option value="博士" <c:if test="${transfer.degree eq '博士' }">selected</c:if>>博士</option>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">专业</label>
				<div class="col-sm-9">
					<select id="degree" name="transferFormMap.major" class="selectpicker show-tick form-control">
						<c:forEach items="${major }" var="major">
							<option value="${major.name }" <c:if test="${major.name eq transfer.major }">selected</c:if>>${major.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译领域</label>
				<div class="col-sm-9">
					<input type="hidden" value="${transfer.domain }" id="transferDomain">
					<c:forEach items="${domain }" var="domain">
						<label>
							<input type="checkbox" value="${domain.name }" name="transferFormMap.domain">${domain.name }
						</label>
					</c:forEach>
				</div>
			</div>
			
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">保存</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>