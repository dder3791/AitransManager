<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/capital/list_company_record.js"></script>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"
			name="searchForm">
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">收入来源:</span></label> <input
					class="input-medium ui-autocomplete-input" id="name"
					name="rewardFormMap.name">
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/capital/export.shtml?type=com')" class="btn btn-info" id="search">导出excel</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
	</div>
	<div>
		<span style="font-style:inherit; ;font-family: 楷体;color: BLUE">
			本月翻译字数：${length}字，本月总收入：${priceTotal}元，本月总支出：${price }元(翻译支出${transTotal }元，校对支出${proofTotal }元，审核支出${auditoryTotal }元)，净利润：${total }元
		</span>
		<%-- ${company } --%>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>
