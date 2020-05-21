<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/advertisement/list_advertisementList.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label">选择关键字种类 ：
					<span class="h4 font-thin v-middle">
						<select id="selectSearch">
							<option value="">请选择...</option>
							<option value="nameCH">名称（中文）</option>
							<option value="nameEN">名称（英文）</option>
							<option value="countryCH">国家（中文）</option>
							<option value="countryEN">国家（英文）</option>
							<option value="introCH">简介（中文）</option>
							<option value="introEN">简介（英文）</option>
						</select>
					</span>
				</label> 
					<input class="input-medium ui-autocomplete-input" id="searchInput">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			
			
			<select id="isUserful" name='advertiseListFormMap.isuseful' style="height: 33px;margin-top: 16px;background-color:#00AA00 ;border-style: dotted;"> 
		      <option value="">--是否可用--</option>
		      <option value="1">可用</option>
		      <option value="0">不可用</option>
		    </select>
		    
		    <select id="type" name='advertiseListFormMap.type' style="height: 33px;margin-top: 16px;margin-right:40px;background-color:#00AA00 ;border-style: dotted;">
		      <option value="">--排序类型--</option>
		      <option value="1">固定</option>
		      <option value="0">随机</option>
		    </select>
	    </form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
	  <a href="javascript:grid.exportData('/advertisement/exportList.shtml')" class="btn btn-info" id="search">导出excel</a>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>
