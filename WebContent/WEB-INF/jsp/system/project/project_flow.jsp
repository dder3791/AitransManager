<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/project/list_project_flow.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
	<!-- <div class="m-b-md" onkeydown="BindEnter(event)"> -->
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label">公司名称</label><input  style="margin-right: 90px" id="customer" name="projectFormMap.cname">
				<label class="control-label">项目名称</label><input style="margin-right: 90px" id="name" name="projectFormMap.name">
				<label class="control-label">翻译语言</label><input id="languagePair" name="projectFormMap.languagePair"><br><br>
				<label class="control-label">项目类型</label><input  style="margin-right: 90px" id="projectType" name="projectFormMap.projectType">
				<label class="control-label">翻译流程</label><input  style="margin-right: 90px" id="processesTypeId" name="projectFormMap.processesTypeId">
				<label class="control-label">项目经理</label><input id="head" name="projectFormMap.head"><br><br>
				<label class="control-label">翻&nbsp;&nbsp;译&nbsp;&nbsp;员</label><input style="margin-right: 90px" id="tname" name="projectFormMap.tname">
				<label class="control-label">校&nbsp;&nbsp;对&nbsp;&nbsp;员</label><input style="margin-right: 90px" id="vname" name="projectFormMap.vname">
				<label class="control-label">审&nbsp;&nbsp;核&nbsp;&nbsp;员</label><input id="aname" name="projectFormMap.aname"><br><br>
				<label class="control-label">项目状态</label><input style="margin-right: 90px" id="projectState" name="projectFormMap.projectState">
				<label class="control-label">起止时间</label> 
					<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" id="startDate" style="width:120px;">
-
					<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" id="endDate" style="width:120px;">
					<!-- <input type="text" id="createTime" name="projectFormMap.createTime" style="text-align: center">-
					<input type="text" id="completeTime" name="projectFormMap.completeTime"> --><br><br>
				
			
			
				<!-- <label class="control-label">公司名称：
					<span class="h4 font-thin v-middle">
						<select id="selectSearch">
							<option value="">请选择...</option>
							<option value="name">项目名称</option>
							<option value="projectState">项目进程</option>
							<option value="language">翻译语种</option>
							<option value="processesTypeId">流程种类</option>
						</select>
					</span>
				</label><input name="projectFormMap.cname" id="customer">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">项目名称：</label> <input id="name" name="projectFormMap.name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">翻译语种：</label> <input id="languagePair" name="projectFormMap.languagePair"><br><br>
				<label class="control-label">翻译员:</label> <input style="margin-right: 80px" id="tname" name="projectFormMap.tname">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">校对员:</label> <input style="margin-right: 90px" id="vname" name="projectFormMap.vname">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">审核员:</label> <input id="aname" name="projectFormMap.aname"><br><br>
				<label class="control-label">翻译类型：</label> <input id="projectType" name="projectFormMap.projectType">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">流程种类：</label> <input id="processesTypeId" name="projectFormMap.processesTypeId">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">项目经理：</label> <input id="head" name="projectFormMap.head">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label class="control-label">状态：</label> <input id="projectState" name="projectFormMap.projectState"><br><br>
				<div>
					<label class="control-label">起止时间：</label> 
					<input type="text" name="projectFormMap.createTime" id="createTime" style="text-align: center">— —
					<input type="text" name="projectFormMap.completeTime" id="completeTime"><span style="color: red">【例如：2015-1-23】</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
					<a href="javascript:grid.exportData('/project/export.shtml?type=projectflow')" class="btn btn-info" id="search">导出excel</a>	
				</div> 
					<input class="input-medium ui-autocomplete-input" id="searchInput"> -->
					<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
					<a href="javascript:grid.exportData('/project/export.shtml?type=projectflow')" class="btn btn-info" id="search">导出excel</a>
			</div>
			
				
			
		</form>
	<!-- </div> -->
	<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>