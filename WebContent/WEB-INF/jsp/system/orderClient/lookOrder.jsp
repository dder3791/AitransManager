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
	action="${pageContext.request.contextPath}/clientOrder/activeOrder.shtml" >
	<section class="panel panel-default">
	 <div class="panel-body">
	 <input type="hidden" name="id" value="${orderFormMap.id }">
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
					<input type="text" class="form-control" name="orderFormMap.orderReference" value="${orderFormMap.reference}" id="reference">	
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
			
			<%-- <div class="form-group" >
				<label class="col-sm-3 control-label">翻译语言</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.orderTitle" value="${orderFormMap.orderTitle}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div> --%>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">字数/是否加急</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.orderTitle" value="${orderFormMap.length}--${orderFormMap.cycle}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">流程</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="" value="${orderFormMap.procedureType}" >	
				 <input type="hidden" name="orderFormMap.procedureType" value="${orderFormMap.procedureTypeId }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">完稿日期</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.orderTitle" value="${orderFormMap.completeTime}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">状态</label>
				<div class="col-sm-9" >
				<input type="text" class="form-control" name="orderFormMap.orderState" disabled="disabled" value="${orderFormMap.orderState}" >
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="orderFormMap.userName" disabled="disabled" value="姓名：${orderFormMap.userName}  ，联系电话：${orderFormMap.tel}  ,其他联系方式：qq号${orderFormMap.qq}" >
				</div>
		   </div>
		   
		   <div class="form-group">
				<label class="col-sm-3 control-label">译员</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="orderFormMap.nickName" disabled="disabled" value="${orderFormMap.languagePair }--${orderFormMap.field }${orderFormMap.transLevel}级翻译员${orderFormMap.nickName}" >
				</div>
		   </div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">原文理解程度</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.understandLevel" value="${orderFormMap.understandLevel}分" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">译文通顺程度</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.degreeOfSmooth" value="${orderFormMap.degreeOfSmooth}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">译文严谨程度</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.rigor" value="${orderFormMap.rigor}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">翻译完整性</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.integrality" value="${orderFormMap.integrality}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">格式排版</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.format" value="${orderFormMap.format}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">完成速度</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.completionSpeed" value="${orderFormMap.completionSpeed}" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">评价总分</label>
				<div class="col-sm-9">	
				 <input type="text" class="form-control" name="orderFormMap.score" value="${orderFormMap.score}分" >	
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">评价内容</label>
				<div class="col-sm-9">
					<textarea rows="5" cols="130"> ${orderFormMap.evaluateDetails }
${orderFormMap.rejection }
					</textarea>
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		  
		  <div class="form-group">
				<label class="col-sm-3 control-label">客户相关文件</label>
				<div class="col-sm-9">
					<center>
						<table border="0">
						<c:forEach var="userFile" items="${userPathNameMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								${userFile.value}
								<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${userFile.value }&fileURL=${userFile.key}">下载</a>
								<span></span>
								<%-- <input id="url" value="${transFile.key}"> --%>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
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
								<span></span>
								<%-- <input id="url" value="${transFile.key}"> --%>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
		   </div>
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
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
		   <div class="line line-dashed line-lg pull-in"></div>
		   
		   
		   
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
			<c:if test="${type eq 'active' }">
			<button type="submit" class="btn btn-default" data-dismiss="modal">已通知译员</button>
			</c:if>
			<button type="button" id="back" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">关闭</button>
		</footer> 
	</section>
</form> 
</body>
<script type="text/javascript">
function closeWin(){
	layer.confirm('确认关闭？', {icon: 3,offset: '200px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

</script>
</html>