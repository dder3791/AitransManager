<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/integral/list_integral_first.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label"> 
				<span class="h4 font-thin v-middle">选择关键字种类:
				  <select id="selectSearch">
				      <option value="">----请选择-----</option>
				      <option value="cname">商品类别</option>
				      <option value="prize">商品名称</option>
				      <option value="markMin">积分数量</option>
				  </select>
				  </span>
				  </label> 
				  <input
					class="input-medium ui-autocomplete-input" id="searchInput"> 
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/integral/export.shtml')" class="btn btn-info" id="search">导出excel</a>
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
