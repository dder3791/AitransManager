<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div class="form-group">
				<label class="col-sm-3 control-label">客户中文名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.nameZH }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户英文名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.nameEN }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户所在地</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.address }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">座机</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.cel }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户移动电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.tel }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.email }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">传真</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.fax }">
				</div>
			</div>
			
		
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">负责人</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly="readOnly"
						value="${customer.head }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						name="customerFormMap.quotedPrice" value="${customer.quotedPrice }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户历史报价</label>
				<div class="col-sm-9">
					<select class="selectpicker show-tick form-control">
						<c:forEach items="${price }" var="p">
							<option>${p }</option>
						</c:forEach>
					</select>
					<%-- <textarea type="text" class="form-control" readOnly
						name="customerFormMap.historicalQuotedPrice" >${customer.historicalQuotedPrice }
					</textarea> --%>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">客户详情</label>
				<div class="col-sm-9">
				
					<textarea type="text" class="form-control" readOnly="readOnly"
						>${customer.description }</textarea>
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