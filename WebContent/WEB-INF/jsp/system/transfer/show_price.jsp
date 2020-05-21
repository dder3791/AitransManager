<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/add_price.js"></script>

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
	
	
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
		<form id="form" name="form" class="form-horizontal" method="post" 	action="${ctx}/transfer/addPrice.shtml">
			<input type="hidden" class="form-control checkacc"	value="${id}" name="id" id="id">
			
			
			<section class="panel panel-default">
				<div class="panel-body">
				
					<div class="form-group">
						<label class="col-sm-3 control-label">翻译报价</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="tranPrice" 	name="quotationFormMap.tranPrice" value="${tranPrice} " readonly />
						</div>
					</div>
					
					<div class="line line-dashed line-lg pull-in"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">校对报价</label>
						<div class="col-sm-9">
							<input type="text" class="form-control"  id="prooPrice" 	name="quotationFormMap.prooPrice" value="${prooPrice }" readonly />
						</div>
					</div>
					
					<div class="line line-dashed line-lg pull-in"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">日翻译字数</label>
						<div class="col-sm-9">
							<input type="text" class="form-control"  id="dayTrans" 	name="quotationFormMap.dayTrans" value="${ dayTrans}" readonly />
						</div>
					</div>
					
					<div class="line line-dashed line-lg pull-in"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">语言对</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="languages" 	name="quotationFormMap.languages" value="${ languages}" readonly />
						</div>
					</div>
					
				</div>
				<footer class="panel-footer text-right bg-light lter">
					<button type="submit" class="btn btn-success btn-s-xs">关闭</button>
				</footer>
			</section>
		</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>