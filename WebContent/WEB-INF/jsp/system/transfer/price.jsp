<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>

<!-- <script type="text/javascript">
alert("112"+${translatorId});
</script> -->
<style type="text/css">
.col-sm-3 {
	width: 15%;
	float: left;
}

.col-sm-9 {
	width: 85%;
	float: left;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"	name="searchForm">
			<input type="hidden" id="domain_" name="domain"  value="${defaultDomain}">
			<input type="hidden" id="transtionId_" name="transtionId"  value="${translatorId}">			
		</form>
	</div>
	
	<header class="panel-heading">
		<div class="doc-buttons">
			<button type="button" id="showPriceBtn" class="btn btn-warning marR10">查看报价详情</button>
			<button type="button" id="addPriceFun" class="btn btn-primary marR10">新增报价</button>
			<button type="button" id="editPriceBtn" class="btn btn-danger marR10">编辑报价</button>
			<button type="button" id="delPriceBtn" class="btn btn-primary marR10">删除报价</button>
			<br /><lable>译员：${translator.nickname}</lable>
	<br />
	<lable>翻译领域：</lable>
	<div class="doc-buttons" style="width:200px;">
		<select id="domain" class="selectpicker show-tick form-control" >
			<c:forEach items="${domains}"  var="domain" varStatus="id" begin="0">			
				<option value="${domain}">${domain}</option>
			</c:forEach>		    
		</select>
		</div>
		</div>
	</header>
	
		
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	<!-- <button type="button" id="addPriceFun" class="btn btn-danger marR10">报价管理</button>
	<button type="button" id="numt" class="btn btn-danger marR10">关闭弹窗回调事件</button> -->
	
	<script type="text/javascript">
	onloadurl();
</script>
<script type="text/javascript" src="${ctx}/js/system/transfer/price.js"></script>
</body>
</html>