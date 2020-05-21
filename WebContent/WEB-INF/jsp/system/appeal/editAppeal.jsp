<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/appeal/editappeal.js"></script>
<script type="text/javascript"	src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
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
	action="${pageContext.request.contextPath}/appeal/editAppeal.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">申诉人名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					 name="AppealFormMap.translatorId" id="translatorId" value="${appeal.translatorId.nickname}" readonly="readonly">
				</div>
			</div>
			<input type="hidden" value="${appeal.id}" id="id" name="id">
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">所在项目</label>
				<div class="col-sm-9">
					<input type="text" class="form-control "
					 name="AppealFormMap.projectId" id="projectId" value="${appeal.projectId }" readonly="readonly" >
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">申诉方式</label>
				<div class="col-sm-9">
				    <c:if test="${appeal.mode eq '书面' }">
					<input type="radio"  name="AppealFormMap.mode" id="mode" value="书面" checked="checked">书面
					<input type="radio"  name="AppealFormMap.mode" id="mode" value="信函">信函
				    <input type="radio"  name="AppealFormMap.mode" id="mode" value="电话">电话 
				    </c:if>
				    <c:if test="${appeal.mode eq '信函' }">
					<input type="radio"  name="AppealFormMap.mode" id="mode" value="书面" >书面
					<input type="radio"  name="AppealFormMap.mode" id="mode" value="信函" checked="checked">信函
				    <input type="radio"  name="AppealFormMap.mode" id="mode" value="电话">电话 
				    </c:if>
				    <c:if test="${appeal.mode eq '电话' }">
					<input type="radio"  name="AppealFormMap.mode" id="mode" value="书面">书面
					<input type="radio"  name="AppealFormMap.mode" id="mode" value="信函">信函
				    <input type="radio"  name="AppealFormMap.mode" id="mode" value="电话" checked="checked">电话 
				    </c:if>
				</div>
			</div>
      
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">申诉事项</label>
				<div class="col-sm-9">
					<textarea rows="10" cols="5" type="text" class="form-control"
					 name="AppealFormMap.matter" id="matter" >${appeal.matter}</textarea>
				</div>
			</div>
			 <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">申诉原因</label>
				<div class="col-sm-9">
				<textarea rows="10" cols="5" type="text" class="form-control"
					 name="AppealFormMap.cause" id="cause">${appeal.cause }</textarea>
				</div>
			</div>
          <div class="line line-dashed line-lg pull-in"></div>
          <div class="form-group">
				<label class="col-sm-3 control-label" for="time">申诉日期</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择申诉日期"
						name="AppealFormMap.appealDate" id="appealDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" value="${appeal.appealDate}">
				</div>
			</div>
			<input type="hidden" name="state" value="1">
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button id="sub1" type="submit" class="btn btn-success btn-s-xs" >提交</button>
		</footer> 
	</section>
</form> 

</script>
</body>
</html>