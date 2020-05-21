<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%@include file="/common/common.jspf"%>
<script type="text/javascript">
	function closeWin(){
		layer.confirm('关闭页面?', {icon: 3,offset: '-100px'}, function(index) {
	    	parent.layer.close(parent.pageii);
	    	return false;
		});
	}
</script>
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
	<h2>统计</h2>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译总字数</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" value="${length}" readonly="readonly">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">总收入</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" value="${priceTotal}" readonly="readonly" >
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">毛利</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" value="${total}" readonly="readonly" >
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">总支出</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" value="${price}" readonly="readonly" >
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译总支出</label>
				<div class="col-sm-9">
					<input type="text" readonly="readonly" class="form-control" value="${transTotal}">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
 			<div class="form-group">
				<label class="col-sm-3 control-label">校对总支出</label>
				<div class="col-sm-9">
					<input type="text" class="form-control money" value="${proofTotal}" readonly="readonly">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核总支出</label>
				<div class="col-sm-9">
					<input type="text" class="form-control money" value="${auditoryTotal}" readonly="readonly">
				</div>
			</div>
		</div>
		
		<footer class="panel-footer text-right bg-light lter">
			<a href="#" class="btn btn-s-md btn-info btn-rounded" onclick="closeWin()">关闭</a>
		</footer> 
		</section>
	</form>
</body>
</html>