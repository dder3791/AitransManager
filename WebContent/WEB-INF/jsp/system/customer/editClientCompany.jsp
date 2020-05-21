<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/customer/edit.js"></script>
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
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/clientUser/editClientCompany.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">公司中文名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司中文名称" name="onLineCustomerFormMap.nameZH" id="nameZH" value="${onLineCustomerFormMap.nameZH }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司中文名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司英文名称" name="onLineCustomerFormMap.nameEN" id="nameEN" value="${onLineCustomerFormMap.nameEN }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司简称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司英文名称" name="onLineCustomerFormMap.shortName" id="shortName" value="${onLineCustomerFormMap.shortName }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司规模</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司地址" name="onLineCustomerFormMap.size" id="address" value="${onLineCustomerFormMap.size }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">经营范围</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司地址" name="onLineCustomerFormMap.scope" id="address" value="${onLineCustomerFormMap.scope }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">座机</label>
				<div class="col-sm-9">
					<input type="text" class="form-control telephone"
						placeholder="请输入公司电话" name="onLineCustomerFormMap.cel" id="cel" value="${onLineCustomerFormMap.cel }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">移动电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户移动电话号码" name="onLineCustomerFormMap.tel" id="tel" value="${onLineCustomerFormMap.tel }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司邮箱" name="clientUserFormMap.emaile" id="email" value="${clientUserFormMap.emaile }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">传真号码</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入传真号码" name="onLineCustomerFormMap.fax" id="fax" value="${onLineCustomerFormMap.fax }">
				</div>
			</div>
			
		
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司负责人</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司负责人姓名" name="clientUserFormMap.realName" id="head" value="${clientUserFormMap.contact }">
				</div>
			</div>
			
			<%-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入报价(元/每千字)" name="customerFormMap.quotedPrice" id="quotedPrice" value="${customer.quotedPrice }">
				</div>
			</div> --%>
			
			<%-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户描述" name="customerFormMap.description" id="description" value="${customer.description }">
				</div>
			</div> --%>
			
			<%-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户规模</label>
				<div class="col-sm-9">
					<select id="type" name="customerFormMap.type"
						class="selectpicker show-tick form-control">
						<option value="0" <c:if test="${customer.type eq 0}">selected</c:if>>中小客户</option>
						<option value="1" <c:if test="${customer.type eq 1}">selected</c:if>>大客户</option>
					</select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户类型</label>
				<div class="col-sm-9">
					<select id="isForeign" name="customerFormMap.isForeign"
						class="selectpicker show-tick form-control">
						<option value="0" <c:if test="${customer.isForeign eq 0}">selected</c:if>>国内客户</option>
						<option value="1" <c:if test="${customer.isForeign eq 1}">selected</c:if>>国外客户</option>
					</select>
				</div>
			</div> --%>
			
			<%-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户类型</label>
				<div class="col-sm-9">
					<select id="isForeign" name="customerFormMap.isForeign"
						class="selectpicker show-tick form-control">
						<option value="0" <c:if test="${customer.isForeign eq 0} ">selected</c:if>>国内客户</option>
						<option value="1" <c:if test="${customer.isForeign eq 1} ">selected</c:if>>国外客户</option>
					</select>
				</div>
			</div> --%>
			
		</div>
		
		<input type="hidden" name="onLineCustomerFormMap.id" id="id" value="${onLineCustomerFormMap.id }"/>
		<input type="hidden" name="clientUserFormMap.id" id="id" value="${clientUserFormMap.id }"/>
		<!-- <input type="hidden" name="customerFormMap.type" value="1"/>
		<input type="hidden" name="customerFormMap.location" value="0"/> -->
		
		
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
	<script type='text/javascript'>
		/* $(function(){
			$("from input[name='enable'][value='${role.enable}']").attr("checked","checked");
			alert("input[name='enable'][value='${role.enable}']");
		}); */
	</script>
</body>
</html>