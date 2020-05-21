<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/advertisement/list_advertisementIndex.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				  <select id="isUserful" name='advertisementFormMap.isUserful' style="height: 30px;margin-top: 15px;background-color:#00AA00 ;border-style: dotted;"> 
				      <option value="">--是否可用--</option>
				      <option value="1">可用</option>
				      <option value="0">不可用</option>
				  </select>
			</div>
			<div class="form-group">
				 <select id="type" name='advertisementFormMap.type' style="height: 30px;margin-top: 15px;margin-right:40px;background-color:#00AA00 ;border-style: dotted;">
				      <option value="">--排序类型--</option>
				      <option value="1">固定</option>
				      <option value="0">随即</option>
				  </select>
			</div>
			<!-- <a href="javascript:void(0)" class="btn btn-default" id="search">查询</a> -->
			<a href="javascript:grid.exportData('/advertisement/exportIndex.shtml')" class="btn btn-info" id="search" style="margin-top: 15px;">导出excel</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>
