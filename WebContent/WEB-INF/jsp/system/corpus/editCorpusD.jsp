<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/corpus/editCorpusD.js"></script>
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
	action="${pageContext.request.contextPath}/corpus/editCorpusD.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<input type="hidden" value="${id}" name="id">
			<div cl ass="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">原文</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入原文" name="CorpusOrigin" 
						id="CorpusOrigin" value="${corpus.CorpusOrigin }">
				</div>
			</div>
			
			<div cl ass="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">译文</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入译文" name="CorpusTarget" 
						id="CorpusTarget" value="${corpus.CorpusTarget }">
				</div>
			</div>
			
		    <div class="form-group" >
				<label class="col-sm-3 control-label">所属领域</label>
				<div class="col-sm-9">
						<select id="domainId" name="domainId"
							class="selectpicker show-tick form-control">
							<c:forEach items="${domain }" var="domain">
								<c:if test="${domain.id ==corpus.DomainId}">
								<option value="${domain.id }" selected="selected">${domain.name}</option>
								</c:if>
								<c:if test="${domain.id !=corpus.DomainId}">
								<option value="${domain.id }">${domain.name}</option>
								</c:if>
							</c:forEach>
						</select>
				</div>
			</div>
			
			<div cl ass="line line-dashed line-lg pull-in"></div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body>
</html>