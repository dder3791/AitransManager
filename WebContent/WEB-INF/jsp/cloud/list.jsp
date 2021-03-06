<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/cloud/list.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label"> <span 	class="h4 font-thin v-middle">模块名称:</span></label>
				<input	class="input-medium ui-autocomplete-input" id="moduleName"	name="">
				<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			</div>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<button type="button" id="addFun" class="btn btn-primary marR10">新建云翻译模块</button>
		<button type="button" id="delFun" class="btn btn-danger marR10">删除云翻译模块</button>	
		<button type="button" id="uploadFun" class="btn btn-warning marR10">上传云翻译模块数据</button>		
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>