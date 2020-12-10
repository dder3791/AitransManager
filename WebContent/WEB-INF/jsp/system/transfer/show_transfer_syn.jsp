<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
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
<!-- <h1 style="margin-left: 100px;font-family: 楷体;color: blue">译员详情</h1> -->
	<form id="form" name="form" class="form-horizontal" method="post"
		action="#">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">姓名</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.realName }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.nickname }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译领域</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.domain }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">语言对</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.languages }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译等级</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.tranlevels }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译总字数</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.majorTotal }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">日翻译量</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.dayTrans }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译报价</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.tranPrice }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译+校对等级</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.proolevels }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译+校对总字数</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.prooTotal }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">日翻译+校对量</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.dayProo }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译+校对报价</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.prooPrice }">
				</div>
			</div>
						
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译+校对+审核等级</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.auditlevels }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译+校对+审核总字数</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.auditTotal }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">日翻译+校对+审核量</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.dayAudit }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译+校对+审核报价</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.auditPrice }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			
		</div>

		</section>
	</form>

</body>
</html>