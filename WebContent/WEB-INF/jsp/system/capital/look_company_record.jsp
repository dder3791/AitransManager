<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
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
	action="#">
	<section class="panel panel-default">
		<div class="panel-body">
		项目:
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.name }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户中文名称</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.nameZH }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户英文名称</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.nameEN }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户所在地</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.address }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目完成时间</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.completeTime }">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目总价</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.priceTotal }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目总价结算时间</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.companySettlementTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户支付方式</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.companyPayment }">
				</div>
			</div>
			
			译员:
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">译员姓名</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.traRealName }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">译员稿酬</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.translatorFeeTotal }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">译员稿酬结算时间</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.transSettlementTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">译员稿酬支付方式</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.traPayment }">
				</div>
			</div>
			
			
			
			校对员:	
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对员姓名</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.proofRealName }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对员稿酬</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.proofFeeTotal }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对员稿酬结算时间</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.proofSettlementTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对员支付方式</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.proofPayment }">
				</div>
			</div>
		
			
			审核员:
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核员姓名</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.auditorRealName }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核员稿酬</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.auditoryFeeTotal }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核员稿酬结算时间</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.auditorySettlementTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核员稿酬支付方式</label>
				<div class="col-sm-9">
					<input type="text" readOnly="true" class="form-control"
						value="${project.audiPayment }">
				</div>
			</div>
			
			
		</div>
		
		
		
		
		<footer class="panel-footer text-right bg-light lter">
		<!-- <button type="submit" id="back" class="btn btn-success btn-s-xs">返回</button> -->
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