<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/project/addproject_flow.js"></script>
<script type="text/javascript" src="${ctx}/js/system/project/add_project_flow.js"></script>
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
	<input type="hidden" id="cusZH">
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${pageContext.request.contextPath}/project/addProjectFlow.shtml?type=projectflow"
		enctype="multipart/form-data">
		<section class="panel panel-default">
		<div class="panel-body">

			<div class="form-group">
				<label class="col-sm-3 control-label">客户名称</label>
				<div class="col-sm-9">
					<select class="form-control" name="customerId" id="customerId" onchange="cusChange()">
							<option value="">请选择...</option>
						<c:forEach items="${customerMapperList }" var="customer">
							<option value="${customer.id}">${customer.nameZH }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">客户案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入客户案号"
						name="customerReference" id="customerreference" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">公司案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="reference" id="reference">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			

			<div class="form-group">
				<label class="col-sm-3 control-label">原稿文本字数<br>单位:字
				</label>
				<div class="col-sm-9">
					<input type="text" class="form-control length" name="wordLength" id="wordLength"
						placeholder="请输入稿长（必须是数字）" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">原稿图片字数<br>单位:字
				</label>
				<div class="col-sm-9">
					<input type="text" class="form-control length" name="imageLength" id="imageLength"
						placeholder="请输入稿长（必须是数字）" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">原稿语言 </label>
				<div class="col-sm-9">
					<select class="form-control" name="language" id="language" onblur="projectOnblur()">
						<option value="">请选择...</option>
						<option value="EN">英语</option>
						<option value="CH">汉语</option>
						<option value="JP">日语</option>
						<option value="KOR">韩语</option>
						<option value="GER">德语</option>
						<option value="FR">法语</option>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">公司稿酬<br>单位:元/千字
				</label>
				<div class="col-sm-9">
					<input type="text" class="form-control money"
						placeholder="请输入稿酬（数字或'.'链接）" name="companyReward" id="companyReward" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">支付定金<br>单位:元/千字
				</label>
				<div class="col-sm-9">
					<input type="text" class="form-control money" name="advance" id="advance" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					
					<input type="text" class="form-control" placeholder="请输入项目名称"
						name="name" id="name" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">是否协同项目</label>
				<div class="col-sm-9">
					<select class="form-control" name="cooperativeState"
						id="cooperativeState">
						<option value="1">协同</option>
						<option value="0" selected="selected">不协同</option>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">翻译类型</label>
				<div class="col-sm-9">
					<select class="form-control" name="projectTypeId"
						id="projectTypeId">
						<c:forEach items="${projectTypeMapperList }" var="projectType">
							<option value="${projectType.id }">${projectType.projectType }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语种 </label>
				<div class="col-sm-9">
					<select class="form-control" name="languagePair" id="languagePair" onblur="projectOnblur()">
						<option value="">请选择...</option>
						<option value="中英">中英</option>
						<option value="英中">英中</option>
						<option value="日中">日中</option>
						<option value="中日">中日</option>
						<option value="中韩">中韩</option>
						<option value="韩中">韩中</option>
						<option value="德中">德中</option>
						<option value="中德">中德</option>
						<option value="法中">法中</option>
						<option value="中法">中法</option>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">项目领域 </label>
				<div class="col-sm-9">
					<select class="form-control" id="domain">
						<!-- <option value="">请选择...</option> -->
						<c:forEach items="${domainMapperList }" var="domain">
							<option value="${domain.name }" selected="selected" title="${domain.id }">${domain.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<input type="hidden" name="domainId" id="domainId">
			<div class="form-group">
				<label class="col-sm-3 control-label">译员名称 </label>
				<div class="col-sm-9" id="tselect">
					<select class="form-control" placeholder="请选择译员" name="transferId" id="transferId">	</select>
				</div>
				<div class="col-sm-9" id="transCheck" style="display: none;">
					
				</div>
				<label class="col-sm-3 control-label">翻译费用 </label>
				<div class="col-sm-9" id="tselect">
					<input type="text" class="form-control money" name="translatorFeeKilo" id="translatorFeeKilo" value="0">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">流程种类 </label>
				<div class="col-sm-9">
					<select class="form-control" name="procedureTypeId"
						id="processesTypeId">
						<c:forEach items="${processesTypeMapperList }" var="processesType">
							<option value="${processesType.id }">${processesType.procedureType }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group" id="verifierdiv" style="display: none;">
				<label class="col-sm-3 control-label">校验员 </label>
				<div class="col-sm-9" id="vselect">
					<select class="form-control" placeholder="请选择校验员" name="verifierId" id="verifierId">	</select>
				</div>
				<div class="col-sm-9" id="verifiCheck" style="display: none;">
					
				</div>
				<label class="col-sm-3 control-label">校验费用 </label>
				<div class="col-sm-9" id="tselect">
					<input type="text" class="form-control money" name="proofFeeKilo" id="proofFeeKilo" value="0">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group" id="auditordiv" style="display: none;">
				<label class="col-sm-3 control-label">审核员 </label>
				<div class="col-sm-9" id="aselect">
					<select class="form-control" placeholder="请选择审核员" name="auditorId" id="auditorId">	</select>
				</div>
				<div class="col-sm-9" id="auditorCheck" style="display: none;">
					
				</div>
				<label class="col-sm-3 control-label">审核费用 </label>
				<div class="col-sm-9" id="tselect">
					<input type="text" class="form-control money" name="auditoryFeeKilo" id="auditoryFeeKilo" value="0">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请选择项目经理姓名"
						name="head" id="head" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">创建时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="createTime" id="createTime" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label" for="time">返稿时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="completeTime" id="completeTime" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" onblur="projectOnblur()">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<input type="hidden" name="projectStateId" id="projectStateId" value="${projectStateId }">

			<div class="form-group" >
				<label class="col-sm-3 control-label">文件上传</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"  readonly="readonly" id="url" name="url">
				</div>
				
				<div class="col-sm-9">
					<div style="margin-left: 100px" >
						选择路径:&nbsp;&nbsp;&nbsp;
						<input type="radio" onclick="radioClick()" id="parentPathE" name="parentPath" value="E:/" checked="checked"><label for="parentPathE">E:/</label>&nbsp;&nbsp;&nbsp;
						<input type="radio" onclick="radioClick()" id="parentPathD" name="parentPath" value="C:/"><label for="parentPathD">C:/</label>&nbsp;&nbsp;&nbsp;
						<select id="path" name="path" onchange="pathChange()"></select>
					</div>
				</div>
				
				<div class="col-sm-9" id="newUpload1" align="left" style="width: 240px">
					<br>
					<input type="file" style="margin-right: 10px;margin-left: 100px;width: 220px" id="file1" name="filename"/>
				</div>
				<br><br>
				<div  style="margin-right: 70px" align="right">
					<a id="btn_add1" class="btn btn-primary marR10" style="margin-top: 25px;"> 继续添加</a>
			    	<a id="btn_del1" class="btn btn-primary marR10" style="margin-top: 25px;" onclick="del_1();">删除</a>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入项目描述"
						name="description" id="description">
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