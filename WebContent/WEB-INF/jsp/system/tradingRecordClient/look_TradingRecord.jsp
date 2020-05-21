<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<style type="text/css">
#but button {
	margin-bottom: 5px;
	margin-right: 5px;
}
.col-sm-3 {
	width: 17%;
	float: left;
}

.col-sm-9 {
	width: 83%;
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
	action="${pageContext.request.contextPath}/tradingRecordClient/addEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
		
			<div class="form-group">
				<label class="col-sm-3 control-label">流水号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="tradingRecordFormMap.id" value="${tradingRecordFormMap.id}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">类型</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="tradingRecordFormMap.type" id="type" value="${tradingRecordFormMap.type}" >
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
		
			<div class="form-group">
				<label class="col-sm-3 control-label">收款人</label>
				
				<div class="col-sm-9" id="radioSelect">
					<input type="text" class="form-control" name="tradingRecordFormMap.payee" id="makeupCo" value='${tradingRecordFormMap.payee }'>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">收款方式</label>
				<div class="col-sm-9">
				<input type="text" class="form-control" name="tradingRecordFormMap.payeeModel" value='${tradingRecordFormMap.payeeModel}'>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">付款人</label>
				<div class="col-sm-9">
					<input type="text" name="tradingRecordFormMap.payer" id="makeupCo2" class="form-control" value=${tradingRecordFormMap.payer }>  
				    
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">付款方式</label>
				<div class="col-sm-9">
				<input type="text" name="tradingRecordFormMap.payModel"  class="form-control" value=${tradingRecordFormMap.payModel }>  
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">支付金额</label>
				<div class="col-sm-9">
					<input type="text" class="form-control money" name="tradingRecordFormMap.payMoney" id="payMoney" value=${tradingRecordFormMap.payMoney }>
				</div>  
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">付款时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="tradingRecordFormMap.payTime" id="tel" readonly="readonly" value='${tradingRecordFormMap.payTime }'>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">支付状态</label>
				<div class="col-sm-9">
				<input type="text" class="form-control" name="tradingRecordFormMap.payState" id="tel" readonly="readonly" value='${tradingRecordFormMap.payState}'>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">交易描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="tradingRecordFormMap.intro" value='${tradingRecordFormMap.intro }'>
				</div>
			</div>

		</div>
		
		<footer class="panel-footer text-right bg-light lter">
				<button type="button" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">关闭</button>
		</footer> 
	</section>
</form>
</body>
<script type="text/javascript">
function closeWin(){
	layer.confirm('是否关闭窗口？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}
</script>
</html>