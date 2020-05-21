<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	

<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/project/look_project.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"
			name="searchForm">
			<div class="form-group">
				<label class="control-label">选择关键字种类 ：
				 <span	class="h4 font-thin v-middle">
				 	<select id="selectSearch">
				 		<option value="">请选择...</option>
				 		<option value="name">项目名称</option>
				 		<option value="head">项目经理</option>
				    </select>
				 </span>
				</label> 
				 <input class="input-medium ui-autocomplete-input" id="searchInput">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/project/export.shtml?type=lookprojectByid')" class="btn btn-info" id="search">导出excel</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<!-- <button type="button" id="addFun" class="btn btn-primary marR10">新增</button>
		<button type="button" id="editFun" class="btn btn-info marR10">编辑</button> -->
		<button type="button" id="look" class="btn btn-warning marR10">查看详情</button>
		<button type="button" id="rewardFun" class="btn btn-danger marR10">稿费</button>
		<!-- <button type="button" id="delFun" class="btn btn-danger marR10">删除</button> -->
		
		<input type="hidden" value="${id }" id="typeId"><br/>
		<input type="hidden" value="${type }" id="type">
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
<!-- 	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div> -->
