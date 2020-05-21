<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
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
	<form id="form" name="form" class="form-horizontal" method="post">
	<section class="panel panel-default">
		<div class="panel-body">
			<!-- 客户和项目表分离的话value应该为${id},现在还未分离,暂使用type -->
			<%-- <input type="hidden" value="${id  }"> --%>
			<%-- ${id}${type }<br/> --%>
		
			<input type="hidden" name="projectFormMap.customerId" value="${id }">
		
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入项目名称" id="name"
						value="${project.name }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						id="head" value="${project.head }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语言</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" 
					value="${project.language }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否为协同项目</label>
				<div class="col-sm-9">
					<c:if test="${project.cooperativeState eq '0'}">
						<input type="text" class="form-control" 
						value="否">
					</c:if>
					<c:if test="${project.cooperativeState eq '1'}">
						<input type="text" class="form-control" 
						value="是">
					</c:if>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">创建时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					id="createTime" value="${project.createTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">完成时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					id="completeTime" value="${project.completeTime }">
				</div>
			</div>
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目类型</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					id="projectType" value="${project.projectType }">
				</div>
			</div>
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">流程种类</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					id="projectType" value="${project.procedureType }">
				</div>
			</div> 
			
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
					id="projectType" value="${project.description }">
				</div>
			</div>
			
		</div>
	</section>
</form>
</body>
</html>