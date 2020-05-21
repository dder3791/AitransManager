<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
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
	action="#">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.name }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户中文名称</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${customer.nameZH }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户所在地</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${customer.address }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目负责人</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.head }">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目类型</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.projectType }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">座机</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${customer.cel }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${customer.email }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语言</label>
				<div class="col-sm-9">
					<%-- <input type="text" readOnly="true" class="form-control" value="${project.language }"> --%>
					<c:if test="${project.language == 'EN' }">
						<input type="text" class="form-control" value="中英">
					</c:if>
					<c:if test="${project.language eq 'JP' }">
						<input type="text" class="form-control" value="中日">
					</c:if>
					<c:if test="${project.language eq 'KOR' }">
						<input type="text" class="form-control" value="中韩">
					</c:if>
					<c:if test="${project.language eq 'GER' }">
						<input type="text" class="form-control" value="中德">
					</c:if>
					<c:if test="${project.language eq 'FR' }">
						<input type="text" class="form-control" value="中法">
					</c:if>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户详情</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${customer.description }">
				</div>
			</div>
		</div>
		
		
		
		
		<footer class="panel-footer text-right bg-light lter">
		<!-- <button type="submit" id="back" class="btn btn-success btn-s-xs">返回</button> -->
		</footer> 
	</section>
</form>
	<script type='text/javascript'>
		/* $(function(){
			$("from input[name='enable'][value='${role.enable}']").attr("checked","checked");
			alert("input[name='enable'][value='${role.enable}']");
		}); */
	</script>
</body>
</html>