<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/customer/list_company.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
			 <label class="control-label">选择关键字种类 ：
			 <span class="h4 font-thin v-middle">
				<select id="selectSearch">
					<option value="nameZH">中文名称</option>
					<option value="nameEN">英文名称</option>
					<option value="tel">手机号</option>
					<!-- <option value="accountName">账号</option> -->
					<option value="emaile">邮箱</option>
				</select>
			  </span>
			 </label> 
			 <input class="input-medium ui-autocomplete-input" id="searchInput">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<!-- <a href="javascript:grid.exportData('/customer/export.shtml?type=internal')" class="btn btn-info" id="search">导出excel</a> -->
		</form>
	</div>
	<header class="panel-heading">
	<%-- <input type="hidden" id="type" value="${type }"> --%>
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
