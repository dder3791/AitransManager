<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/term/addTerm.js"></script>
<style type="text/css">
#but button {
	margin-bottom: 5px;
	margin-right: 5px;
}
.col-sm-3 {
	width: 18%;
	float: left;
}

.col-sm-9 {
	width: 82%;
	float: left;
}

label[class^="btn btn-default"] {
	margin-top: -4px;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/term/addTermD.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
		    <input type="hidden" name="id" value="${id}">
			<input type="hidden" name="corpusFormMap.oriLanguage" value="${term.oriLanguage }">
			<input type="hidden" name="corpusFormMap.languagePair" value="${term.languagePair }">
			<input type="hidden" name="corpusFormMap.isProofread" value="${term.isProofread }">
			<input type="hidden" name="corpusTableFormMap.domainId" value="${term.domainId }">
			<input type="hidden" name="corpusFormMap.corpusName" value="${term.corpusName }">
			<input type="hidden" name="corpusFormMap.remark" value="${term.remark }">
			<input type="hidden" name="corpusFormMap.proofreaderId" value="${term.proofreaderId }">
			<input type="hidden" name="corpusFormMap.translatorId" value="${term.translatorId }">
			<input type="hidden" name="corpusFormMap.auditorId" value="${term.auditorId }">
			<input type="hidden" name="User_UC" value="1" id="User_UC">
			<div class="form-group">
				<label class="col-sm-3 control-label">原文</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入原文" name="TermOrigin" id="TermOrigin">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">译文</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入译文" name="TermTarget" id="TermTarget">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">所属领域</label>
				<div class="col-sm-9">
						<select id="domainId" name="domainId"
							class="selectpicker show-tick form-control">
							<c:forEach items="${domain }" var="domain">
							<option value="${domain.id }">${domain.name}</option>
							</c:forEach>
						</select> 
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body>
</html>