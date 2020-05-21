<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@include file="/common/common.jspf"%> --%>
<style type="text/css">
.col-sm-3 {
	width: 18%;
	float: left;
}

.col-sm-9 {
	width: 82%;
	float: left;
}
</style>
</head>
<body>
<h1 style="margin-left: 100px;font-family: 楷体;color: blue">项目详情</h1>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/project/project_download.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">项目编号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.id }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">公司案号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.reference }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户案号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.customerReference }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户名称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.cname }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.pname }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.head }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译类型</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.projectType }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">流程类型</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.procedureType }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目领域</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.dname }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">原文语言</label>
				<div class="col-sm-9">
					<c:if test="${projectFormMap.language=='KOR' }"><input readOnly="readonly" type="text" class="form-control" value="韩语"></c:if>
					<c:if test="${projectFormMap.language=='JP' }"><input readOnly="readonly" type="text" class="form-control" value="日语"></c:if>
					<c:if test="${projectFormMap.language=='EN' }"><input readOnly="readonly" type="text" class="form-control" value="英语"></c:if>
					<c:if test="${projectFormMap.language=='GER' }"><input readOnly="readonly" type="text" class="form-control" value="德语"></c:if>
					<c:if test="${projectFormMap.language=='FR' }"><input readOnly="readonly" type="text" class="form-control" value="法语"></c:if>
					<c:if test="${projectFormMap.language=='CH' }"><input readOnly="readonly" type="text" class="form-control" value="汉语"></c:if>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语种</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.languagePair }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">支付定金</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.advance }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译资费</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${rewardFormMap.translatorFeeKilo }元">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对资费</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${rewardFormMap.proofFeeKilo }元">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核资费</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${rewardFormMap.auditoryFeeKilo }元">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译员</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.tname }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对员</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.vname }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核员</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.aname }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">立项时间</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.createTime }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">返稿时间</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.completeTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目状态</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.stateName}">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">相关文件<span style="color: red;font-size: 10px">（双击下载）</span></label>
				<div class="col-sm-9">			   
					<center>
						<table border="0">
						<c:forEach var="ff" items="${fileList}">
						<tr ondblclick="downFile(this)">
							<td>
								${ff.fileName}.${ff.fileFormat}
								<input type="hidden" value="${ff.fileUrl }">
								<input type="hidden" value="${ff.fileName}">
								<input type="hidden" value="${ff.fileFormat}">
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.description }">
				</div>
			</div>
			
		</div>
		<input type="hidden" name="fileURL" id="fileURL">
		<input type="hidden" name="urlname" id="urlname">
		<input type="hidden" name="fileFormat" id="fileFormat">

		
		
		<footer class="panel-footer text-right bg-light lter">
		<!-- <button type="button" id="back" class="btn btn-default" data-dismiss="modal">返回</button> -->
		<!-- <button type="submit" id="back" class="btn btn-success btn-s-xs">返回</button> -->
		</footer> 
	</section>
</form>
	<script type='text/javascript'>
function downFile(valueTD){
	var url=valueTD.getElementsByTagName('input')[0].value;
	var name=valueTD.getElementsByTagName('input')[1].value; 
	var format=valueTD.getElementsByTagName('input')[2].value; 
	document.getElementById("fileURL").value=url;
	document.getElementById("urlname").value=name;
	document.getElementById("fileFormat").value=format;
	/* $("#urlname").val=name;
	$("#fileFormat").val=format; */
	//alert(name);
	$('form').submit();
	} 
	</script>
</body>
</html>