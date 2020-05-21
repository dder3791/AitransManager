<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/project/list_Release_tasks.js"></script>
	<div class="m-b-md" onkeydown="BindEnter(event)">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label">选择关键字种类 ：
					<span class="h4 font-thin v-middle">
						<select id="selectSearch">
							<option value="">请选择...</option>
							<option value="name">项目名称</option>
							<option value="projectState">项目进程</option>
							<option value="language">翻译语种</option>
							<option value="processesTypeId">流程种类</option>
						</select>
					</span>
				</label> 
					<input class="input-medium ui-autocomplete-input" id="searchInput">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/project/export.shtml?type=tasks')" class="btn btn-info" id="search">导出excel</a>			
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
