<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/customer/add.js"></script>
<style type="text/css">
#but button {
	margin-bottom: 5px;
	margin-right: 5px;
}
.col-sm-3 {
	width: 15%;
	float: left;
}

.col-sm-9 {
	width: 85%;
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
	action="${pageContext.request.contextPath}/customer/addEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">公司中文名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司中文名称" name="customerFormMap.nameZH" id="nameZH">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司英文文名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司英文名称" name="customerFormMap.nameEN" id="nameEN">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司简称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司简称" name="customerFormMap.shortName" id="shortName">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司地址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司地址" name="customerFormMap.address" id="address">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">座机</label>
				<div class="col-sm-9">
					<input type="text" class="form-control cel"
						placeholder="请输入公司电话" name="customerFormMap.cel" id="cel">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户移动电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司电话" name="customerFormMap.tel" id="tel">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司邮箱" name="customerFormMap.email" id="email">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">传真号码</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入传真号码" name="customerFormMap.fax" id="fax">
				</div>
			</div>
			
		
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">公司负责人</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入公司负责人姓名" name="customerFormMap.head" id="head">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="示例:英中-300,德中-350" name="customerFormMap.quotedPrice" id="quotedPrice">(元/每千字)
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户规模</label>
				<div class="col-sm-9">
					<select id="type" name="customerFormMap.type"
						class="selectpicker show-tick form-control">
						<option value="0" >中小客户</option>
						<option value="1" <c:if test="${type eq 'large' }">selected</c:if>>大客户</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户类型</label>
				<div class="col-sm-9">
					<select id="type" name="customerFormMap.isForeign"
						class="selectpicker show-tick form-control">
						<option value="0" <c:if test="${type eq 'internal' }">selected</c:if>>国内客户</option>
						<option value="1" <c:if test="${type eq 'foreign' }">selected</c:if>>国外客户</option>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入客户描述" name="customerFormMap.description" id="description">
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