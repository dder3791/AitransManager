<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/corpus/look_corpus.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label"> 
				<span class="h4 font-thin v-middle">选择关键字种类:
				  <select id="selectSearch">
				      <option value="">----请选择-----</option>
				      <option value="CorpusOrigin">原文</option>
				      <option value="CorpusTarget">译文</option>
				      <option value="Uploder">文件来源</option>
				  </select>
				  </span>
				  </label> 
				  <input
					class="input-medium ui-autocomplete-input" id="searchInput"> 
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search" >查询</a>
			<a href="javascript:grid.exportData('/corpus/export2.shtml')" class="btn btn-info" id="search">导出excel</a>
		    <a href="javascript:grid.exportData('/corpus/exportTXT.shtml')" class="btn btn-info" id="search">导出txt</a>
			<a href="javascript:grid.exportData('/corpus/BuildTMXDoc.shtml')" class="btn btn-info" id="search">导出tmx</a>
			<a href="javascript:grid.exportData('/corpus/BuildWord.shtml')" class="btn btn-info" id="search">导出word</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<button type="button" id="addFun" class="btn btn-primary marR10">新增</button>
		<button type="button" id="editFun" class="btn btn-info marR10">编辑</button>
		<button type="button" id="delFun" class="btn btn-danger marR10">删除</button>
		<button type="button" id="upLoad" class="btn btn-success marR10">导入</button>
		<!-- <button type="button" id="look" class="btn btn-warning marR10">查看详情</button> -->
		<input type="hidden" value="${corpusName }" id="corpusName">
		<input type="hidden" value="${id }" id="id">
		<input type="hidden" value="0" id="User_UC">
		<%-- <c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach> --%>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>
