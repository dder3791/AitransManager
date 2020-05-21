<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/orderClient/edit.js"></script>
<style type="text/css">
#but button {
	margin-bottom: 5px;
	margin-right: 5px;
}
.col-sm-3 {
	width: 20%;
	float: left;
}

.col-sm-9 {
	width: 80%;
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
	action="${pageContext.request.contextPath}/clientOrder/editOrder.shtml" >
	<section class="panel panel-default">
	 <div class="panel-body">
		   <input type="hidden" name="id" value="${orderFormMap.id}" id="orderId">
		   
		   <div class="form-group" >
				<label class="col-sm-3 control-label">订单号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="orderFormMap.orderNumber" value="${orderFormMap.orderNumber}" id="reference">	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
		   <div class="form-group" >
				<label class="col-sm-3 control-label">订单案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="orderFormMap.orderReference" value="${orderFormMap.orderReference}" id="reference">	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">订单标题</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.orderTitle" value="${orderFormMap.orderTitle}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">状态</label>
				<div class="col-sm-9" >
				<select name="orderFormMap.taskStateId">
					<option value="1" <c:if test="${orderFormMap.taskStateId eq '1' }">selected</c:if> >已接受</option>
					<option value="2" <c:if test="${orderFormMap.taskStateId eq '2' }">selected</c:if>>正在翻译</option>
					<option value="3" <c:if test="${orderFormMap.taskStateId eq '3' }">selected</c:if>>正在校对</option>
					<option value="4" <c:if test="${orderFormMap.taskStateId eq '4' }">selected</c:if>>正在审核</option>
					<option value="5" <c:if test="${orderFormMap.taskStateId eq '5' }">selected</c:if>>已返稿</option>
					<option value="6" <c:if test="${orderFormMap.taskStateId eq '6' }">selected</c:if>>客户签收</option>
					<option value="7" <c:if test="${orderFormMap.taskStateId eq '7' }">selected</c:if>>客户评价</option>
					<option value="8" <c:if test="${orderFormMap.taskStateId eq '8' }">selected</c:if>>订单完成</option>
					<option value="9" <c:if test="${orderFormMap.taskStateId eq '9' }">selected</c:if>>订单取消</option>
					<option value="10" <c:if test="${orderFormMap.taskStateId eq '10' }">selected</c:if>>拒签</option>
				</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">拒签理由</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="orderFormMap.rejection" disabled="disabled" value="${orderFormMap.rejection}" >
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		  
		   <div class="form-group">
				<label class="col-sm-3 control-label">翻译相关文件</label>
				<div class="col-sm-9">
					<center>
						<table border="0">
						<c:forEach var="transFile" items="${transPathNameMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								${transFile.value}
								<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${transFile.value }&fileURL=${transFile.key}">下载</a>
								<!-- <span></span> -->
								<%-- <input id="url" value="${transFile.key}"> --%>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
		   </div>
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">校对相关文件</label>
				<div class="col-sm-9">
					<center>
						<table border="0">
						<c:forEach var="proofFile" items="${proofPathNameMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								${proofFile.value}
								<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${proofFile.value }&fileURL=${proofFile.key}">下载</a>
								<span></span>
								<%-- <input id="url" value="${transFile.key}"> --%>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
		   </div>
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">审核相关文件</label>
				<div class="col-sm-9">
					<center>
						<table border="0">
						<c:forEach var="auditFile" items="${auditPathNameMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								${auditFile.value}
								<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${auditFile.value }&fileURL=${auditFile.key}">下载</a>
								<span></span>
								<%-- <input id="url" value="${transFile.key}"> --%>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
		   </div>
		   
		</div>
		<footer class="panel-footer text-right bg-light lter">
			<button type="submit" class="btn btn-success btn-s-xs">提交</button>
			<button type="button" id="back" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">取消</button>
		</footer> 
	</section>
</form> 
</body>
<script type="text/javascript">
function closeWin(){
	layer.confirm('确认关闭？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

</script>
</html>