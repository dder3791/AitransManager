<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/article/list_article.js"></script>
	<div class="m-b-md" onkeydown="BindEnter(event)">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label">选择关键字种类 ：
					<span class="h4 font-thin v-middle">
						<select id="selectSearch">
							<option value="">请选择...</option>
							<option value="title">资讯标题</option>
							<option value="issueDate">发布日期</option>
							<option value="context">资讯内容</option>
							<option value="type">咨询类型</option>
							<option value="auther">发布人</option>
						</select>
					</span>
				</label> 
					<input class="input-medium ui-autocomplete-input" id="searchInput">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/article/export.shtml')" class="btn btn-info" id="search">导出excel</a>			
		</form>
		<form class="form-inline" role="form" id="hotForm" name="hotForm">
				 <input	type="hidden" id="inputhot" value=1 name="articleFormMap.hot">
		</form>
		<form id="eliteForm" name="eliteForm">
			<input type="hidden" id="inputelite" name="articleFormMap.elite" value=1>
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