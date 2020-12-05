<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/system/transfer/list_transfer_synthetical.js"></script>
<div class="m-b-md">
综合译员列表 
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<label class="col-xs-2 rowlabel"><b>专业领域</b></label>
		<div class="col-xs-2">
			 <select class="form-control select_wid" style="width: 50%;" name="translatorFormMap.domain"
				id="domain">
				<option value="">全部</option>
				<option value="日常">日常</option>
				<option value="机械">机械</option>
				<option value="电子">电子</option>
				<option value="电器">电器</option>
				<option value="机电">机电</option>
				<option value="自动化">自动化</option>
				<option value="计算机">计算机</option>
				<option value="通讯">通讯</option>
				<option value="网络">网络</option>
				<option value="物理">物理</option>
				<option value="化学">化学</option>
				<option value="化工">化工</option>
				<option value="医药">医药</option>
				<option value="生化">生化</option>
			</select>
		</div>

		<label class="col-xs-1 rowlabel"><b>选择语对</b></label>
		<div class="col-xs-2">
			<select class="form-control" style="width: 50%;" name="translatorFormMap.languages"
				id="languages">
				<option value="">全部</option>
				<option value="汉英">汉英</option>
				<option value="英汉">英汉</option>
				<option value="汉日">汉日</option>
				<option value="日汉">日汉</option>
				<option value="汉法">汉法</option>
				<option value="法汉">法汉</option>
				<option value="汉德">汉德</option>
				<option value="德汉">德汉</option>
				<option value="汉韩">汉韩</option>
				<option value="韩汉">韩汉</option>
			</select>
		</div>
		<label  class="col-xs-2 rowlabel"><b>译员等级</b></label>
			<div class="col-xs-2">
				<select class="form-control level" style="width:50%;" name="translatorFormMap.level" id="level">
			    <option value="">全部</option>
			    <option value="3">3级</option>
			    <option value="4">4级</option>
			    <option value="5">5级</option>
			</select>
		</div>

		<label class="col-xs-1 rowlabel"><b>翻译流程</b></label>
		<div class="col-xs-2">
			<select class="form-control proof" style="width: 100%;" name="translatorFormMap.proof"
				id="proof">
				<option value="">全部</option>
				<option value="1">仅翻译</option>
				<option value="2">翻译+校对</option>
				<option value="3">翻译+校对+审核</option>
			</select>
		</div>

		<div class="form-group">
			<label class="control-label"> <span
				class="h4 font-thin v-middle">昵称:</span></label> <input
				class="input-medium ui-autocomplete-input" id="nickname"
				name="translatorFormMap.nickname">
		</div>
		<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		<a href="javascript:grid.exportData('/transfer/export.shtml')"
			class="btn btn-info" id="search">导出excel</a>
	</form>
</div>
<input type="hidden" id="language" value="${language }">
<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
		<button type="button" id="listPriceFun" class="btn btn-danger marR10">报价管理</button>
	</div>
</header>
<div class="table-responsive">
	<div id="paging" class="pagclass"></div>
</div>

<div class="table-responsive">
	<div id="paging2" class="pagclass"></div>
</div>
