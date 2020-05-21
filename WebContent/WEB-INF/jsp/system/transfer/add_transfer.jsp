<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/add_transfer.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
.col-sm-3 {
	width: 20%;
	float: left;
}

.col-sm-9 {
	width: 80%;
	float: left;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${ctx}/transfer/addEntity.shtml">
		<input type="hidden" class="form-control"
			value="${transfer.id}" name="translatorFormMap.id" id="id">
		<section class="panel panel-default">
		<div class="panel-body">
			
			<input type="hidden" name="translatorFormMap.language" id="language"
			value="${lan }">
			<input type="hidden" name="translatorFormMap.roleName" id="roleName"
			value="译员">
			<input type="hidden" name="translatorFormMap.point" id="point"
			value="0">
			<!-- <input type="hidden" name="translatorFormMap.password" id="password"
			value="123456789"> -->
			
			<div class="form-group">
				<label class="col-sm-3 control-label">真实姓名</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="realName" 
					name="translatorFormMap.realName">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="nickname" 
					name="translatorFormMap.nickname">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别</label>
				<div class="col-sm-9">
					<label class="radio-inline"> <input type="radio"
						name="translatorFormMap.gender" id="" value="1">
						男
					</label> <label class="radio-inline"> <input type="radio"
						name="translatorFormMap.gender" id="" value="0">
						女
					</label>

				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">账号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" id="accountName" 
					name="translatorFormMap.accountName">
				</div>
				&nbsp;&nbsp;&nbsp;&nbsp;密码默认为123456789
			</div>
			

			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">等级</label>
				<div class="col-sm-9">
					<select id="level" name="translatorFormMap.level" class="selectpicker show-tick form-control">
						<option value="3" >三级</option>
						<option value="4" >四级</option>
						<option value="5" >五级</option>
					</select>
				</div>
			</div>
			

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="email" 
					name="translatorFormMap.email">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control isMobile" id="tel" 
					name="translatorFormMap.tel">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">座机</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="cel" 
					name="translatorFormMap.cel">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">传真</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="fax" 
					name="translatorFormMap.fax">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">住址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="address" 
					name="translatorFormMap.address">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="birthday" 
					name="translatorFormMap.birthday" onFocus="WdatePicker({lang:'zh-cn'})">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">学历</label>
				<div class="col-sm-9">
					<select id="degree" name="translatorFormMap.degree" class="selectpicker show-tick form-control">
						<option value="本科" >本科</option>
						<option value="硕士" >硕士</option>
						<option value="博士" >博士</option>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">专业</label>
				<div class="col-sm-9">
					<select id="degree" name="translatorFormMap.majorId" class="selectpicker show-tick form-control">
						<c:forEach items="${major }" var="major">
							<option value="${major.id }" >${major.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译领域</label>
				<div class="col-sm-9">
					<input type="hidden" value="${transfer.domain }" id="transferDomain">
					
					<c:forEach items="${domain }" var="domain">
						<label>
							<input type="checkbox" value="${domain.name }" name="translatorFormMap.domain">${domain.name }
						</label>
					</c:forEach>
				</div>
			</div>
			
			
			
			<!-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译报价</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiPrice" 
					name="translatorFormMap.tiPrice">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对报价</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiCheckPrice" 
					name="translatorFormMap.tiCheckPrice">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">保证质量翻译报价</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiEQTranslatePrice" 
					name="translatorFormMap.tiEQTranslatePrice">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">保证质量校对报价</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiEQCheckPrice" 
					name="translatorFormMap.tiEQCheckPrice">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">日翻译字数</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiDaysMeasure" 
					name="translatorFormMap.tiDaysMeasure">
				</div>
			</div> -->
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译经验</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiTransCount" 
					name="translatorFormMap.tiTransCount">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对经验</label>
				<div class="col-sm-9">
						<input type="text" class="form-control" id="tiCheckCount" 
					name="translatorFormMap.tiCheckCount">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否认证</label>
				<div class="col-sm-9">
					<label class="radio-inline"> <input type="radio"
						name="translatorFormMap.tiCeritify" id="" value="1">
						是
					</label> <label class="radio-inline"> <input type="radio"
						name="translatorFormMap.tiCeritify" id="" value="0">
						否
					</label>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否使用软件翻译</label>
				<div class="col-sm-9">
					<label class="radio-inline"> <input type="radio"
						name="translatorFormMap.tiUseSoft" id="" value="1">
						是
					</label> <label class="radio-inline"> <input type="radio"
						name="translatorFormMap.tiUseSoft" id="" value="0">
						否
					</label>
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">详细描述</label>
				<div class="col-sm-9">
						<textarea type="text" class="form-control" id="description" 
					name="translatorFormMap.description" > </textarea>
				</div>
			</div>
			
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">保存</button>
		</footer> </section>
	</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>