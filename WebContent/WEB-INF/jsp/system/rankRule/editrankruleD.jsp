<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/rankRule/editrankrule.js"></script>
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
	action="${pageContext.request.contextPath}/rankRule/editRankrule.shtml" >
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="line line-dashed line-lg pull-in"></div>
			<input type="hidden" name="id" value="${rankrule.id}" id="rankRuleId">
			<div class="form-group">
				<label class="col-sm-3 control-label">规则名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
				    name="RankRuleFormMap.rname" id="rname" value="${rankrule.rname}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">规则事项</label>
				<div class="col-sm-9">
					<textarea cols="10" rows="10"  class="form-control"  
					 name="RankRuleFormMap.explain" id="explain" >${rankrule.explain }</textarea>
				</div>
			</div>
			<input type="hidden" value="${id}" name="id">
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">启用状态</label>
				<div class="col-sm-9">
					<select  name="RankRuleFormMap.state"
							class="selectpicker show-tick form-control">
							<c:if test="${rankrule.state eq '启用'}">
							<option value="启用" selected="selected">启用</option>
							<option value="未启用">未启用</option>
							</c:if>
							<c:if test="${regulation.state eq '未启用'}">
							<option value="启用" >启用</option>
							<option value="未启用" selected="selected">未启用</option>
							</c:if>
						</select> 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button id=“sub1” type="submit" class="btn btn-success btn-s-xs" >提交</button>
		</footer> 
	</section>
</form> 
</body>
</html>