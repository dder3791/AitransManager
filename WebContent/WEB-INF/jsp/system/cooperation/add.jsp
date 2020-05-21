<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/cooperation/add.js"></script>
<!-- 时间控件 -->
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
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
	action="${pageContext.request.contextPath}/cooperation/addEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<!-- 客户和项目表分离的话value应该为${id},现在还未分离,暂使用type -->
			<%-- <input type="hidden" value="${id  }"> --%>
			<%-- ${id}${type } --%>
		
			<input type="hidden" name="projectFormMap.cooperativeState" value="1">
		
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入项目名称" name="projectFormMap.name" id="name">
					<%-- <select name="projectFormMap.customerId"
					class="selectpicker show-tick form-control">
						<c:forEach items="${project }" var="project">
							<option value="${project.id }">${project.name }</option>
						</c:forEach>
					</select> --%>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户名称</label>
				<div class="col-sm-9">
					<select name="projectFormMap.customerId"
					class="selectpicker show-tick form-control">
						<c:forEach items="${customer }" var="c">
							<option value="${c.id }">${c.name }</option>
						</c:forEach>
						
					</select>
				
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入项目经理姓名" name="projectFormMap.head" id="head">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语言</label>
				<div class="col-sm-9">
					<select id="transType" name="projectFormMap.transType"
						class="selectpicker show-tick form-control">
						<option value="中英">中英</option>
						<option value="英中">英中</option>
						<option value="中日">中日</option>
						<option value="日中">日中</option>
						<option value="中韩">中韩</option>
						<option value="韩中">韩中</option>
						<option value="中德">中德</option>
						<option value="德中">德中</option>
						<option value="中法">中法</option>
						<option value="法中">法中</option>
					</select>
				</div>
			</div>
			
			
			<!-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否为协同项目</label>
				<div class="col-sm-9">
					<select name="projectFormMap.cooperativeState" 
					class="selectpicker show-tick form-control">
						<option  value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
			</div> -->
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">创建时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请选择创建时间" name="projectFormMap.createTime" id="createTime"
						onFocus="WdatePicker({lang:'zh-cn'})">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">完成时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请选择完成时间" name="projectFormMap.completeTime" id="completeTime"
						onFocus="WdatePicker({lang:'zh-cn'})">
				</div>
			</div>
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目类型</label>
				<div class="col-sm-9">
					<select name="projectFormMap.projectTypeId"
					class="selectpicker show-tick form-control">
						<c:forEach items="${projectType }" var="pt">
							<option value="${pt.id }">${pt.projectType }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">流程种类</label>
				<div class="col-sm-9">
					<select name="projectFormMap.processesTypeId"
					class="selectpicker show-tick form-control">
						<c:forEach items="${processesType }" var="p">
							<option value="${p.id }">${p.processesType }</option>
						</c:forEach>
					</select>
				</div>
			</div> 
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户描述" name="projectFormMap.description" id="description">
				</div>
			</div>
			
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body>
</html>