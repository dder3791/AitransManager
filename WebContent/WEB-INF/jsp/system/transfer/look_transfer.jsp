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
				<label class="col-sm-3 control-label">账号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.accountName }">
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
				<label class="col-sm-3 control-label">真实姓名</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.realName }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">等级</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.level }">
				</div>
			</div>


			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.gender=='0' ? '女' : '男' }">
				</div>
			</div>


			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">手机号码</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.tel }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">座机</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.cel }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.email }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">传真</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.fax }">
				</div>
			</div>



			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">IP注册地址</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.IP }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">住址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.address }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.birthday }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">学历</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.degree }">
				</div>
			</div>


			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">专业</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${major }">
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
				<label class="col-sm-3 control-label">翻译报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiPrice }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiCheckPrice }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">保证质量翻译报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiEQTranslatePrice }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">保证质量校对报价</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiEQCheckPrice }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">日翻译字数</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiDaysMeasure }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译经验</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiTransCount }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对经验</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiCheckCount }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否认证</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiCeritify eq '0' ? '否' : '是'}">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否使用软件翻译</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" readOnly
						value="${transfer.tiUseSoft eq '0' ? '否' : '是'}">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">个人介绍</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.description }">
				</div>
			</div>
		</div>

		</section>
	</form>

</body>
</html>