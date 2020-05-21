<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/edit.js"></script>

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
			value="${transfer.id}" name="translatorFormMap.id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			
			<div class="form-group">
				<label class="col-sm-3 control-label">等级</label>
				<div class="col-sm-9">
					<select id="level" name="translatorFormMap.level" class="selectpicker show-tick form-control">
						<option value="3" <c:if test="${transfer.level eq '3' }">selected</c:if>>三级</option>
						<option value="4" <c:if test="${transfer.level eq '4' }">selected</c:if>>四级</option>
						<option value="5" <c:if test="${transfer.level eq '5' }">selected</c:if>>五级</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
		
			 <div class="form-group">
				<label class="col-sm-3 control-label">真实姓名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="realName" 
					name="translatorFormMap.realName" value="${transfer.realName }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="nickname" 
					name="translatorFormMap.nickname" value="${transfer.nickname }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="email" 
					name="translatorFormMap.email" value="${transfer.email }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control isMobile" id="phone" 
					name="translatorFormMap.tel" value="${transfer.tel }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">住址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="address" 
					name="translatorFormMap.address" value="${transfer.address }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">学历</label>
				<div class="col-sm-9">
					<select id="degree" name="translatorFormMap.degree" class="selectpicker show-tick form-control">
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
					<select id="degree" name="translatorFormMap.majorId" class="selectpicker show-tick form-control">
						<c:forEach items="${major}" var="major">
							<option value="${major.id }" <c:if test="${major.id eq transfer.majorId }">selected</c:if>>${major.name }</option>
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
						
							<input type="checkbox" value="${domain.name }" name="translatorFormMap.domain" 
							<c:forEach items="${transferDomains }" var="i">
								<c:if test="${i.transferDomainName eq domain.name}">checked</c:if>
							</c:forEach>
							
							
							>${domain.name }							
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