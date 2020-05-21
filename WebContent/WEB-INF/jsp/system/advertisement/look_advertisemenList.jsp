<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <%@include file="/common/common.jspf"%> --%>
<style type="text/css">
/* #but button {
	margin-bottom: 5px;
	margin-right: 5px;
} */
.col-sm-3 {
	width: 12%;
	float: left;
}

.col-sm-9 {
	width: 88%;
	float: left;
}

/* label[class^="btn btn-default"] {
	margin-top: -4px;
} */
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">名称（中文）</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入名称（中文）" name="advertiseListFormMap.nameCH" id="nameCH" value="${advertiseListFormMap.nameCH }"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">名称（英文）</label>
				<div class="col-sm-9">
				<input type="text" class="form-control" placeholder="请输入名称（英文）" name="advertiseListFormMap.nameEN" id="nameEN" value="${advertiseListFormMap.nameEN }"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">国家（中文）</label>
				<div class="col-sm-9">
				<input type="text" class="form-control" placeholder="请输入名称（英文）" name="advertiseListFormMap.countryCH" id="nameEN" value="${advertiseListFormMap.countryCH }"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">国家（英文）</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="advertiseListFormMap.countryEN" id='countryEN' value="${advertiseListFormMap.countryEN }">
				</div>
				
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">所在省</label>
				<div class="col-sm-9" >
					<input type="text" class="form-control"  name="advertiseListFormMap.province" id='province' value="${advertiseListFormMap.province}">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">简介（中文）</label>
				<div class="col-sm-9">
					<textarea rows="5" cols="30" class="form-control" name="advertiseListFormMap.introCH" id="introCH">
						${advertiseListFormMap.introCH }
					</textarea>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">简介（英文）</label>
				<div class="col-sm-9">
					<textarea rows="5" cols="30" class="form-control" name="advertiseListFormMap.introEN" id="introEN">
						${advertiseListFormMap.introEN }
					</textarea>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">链接地址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入链接地址" name="advertiseListFormMap.web" id="web" value="${advertiseListFormMap.web }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">排序类型</label>
				<div class="col-sm-9">
					<c:if test='${advertiseListFormMap.type==1 || advertiseListFormMap.type=="1" }'>
						<input type="text" class="form-control" name="advertiseListFormMap.type" id="type" value="固定">
					</c:if>
					<c:if test='${advertiseListFormMap.type==0 || advertiseListFormMap.type=="0" }'>
						<input type="text" class="form-control" name="advertiseListFormMap.type" id="type" value="随机">
					</c:if>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否可用</label>
				<div class="col-sm-9">
					<c:if test='${advertiseListFormMap.isuseful==1 || advertiseListFormMap.isuseful=="1" }'>
						<input type="text" class="form-control" name="advertiseListFormMap.isuseful" id="isUseful" value="可用">
					</c:if>
					<c:if test='${advertiseListFormMap.isuseful==0 || advertiseListFormMap.isuseful=="0" }'>
						<input type="text" class="form-control" name="advertiseListFormMap.isuseful" id="isUseful" value="不可用">
					</c:if>
				</div>
			</div>
		</div>
		<!-- <footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">关闭</button>
		</footer>  -->
	</section>
</form>
</body>
</html>