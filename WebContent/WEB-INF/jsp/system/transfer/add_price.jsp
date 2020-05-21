<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/add_price.js"></script>

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
	<!-- add_price....译员报价新增

	<button type="button" id="addPriceFun" class="btn btn-danger marR10">报价管理000</button>
	<button type="button" id="numx" class="btn btn-danger marR10">设置父窗口的值121</button> -->
	
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
		<form id="form" name="form" class="form-horizontal" method="post" 	action="${ctx}/transfer/addPrice.shtml">
			<input type="hidden" class="form-control checkacc"	value="${transtionId}" name="quotationFormMap.transtionId" id="transtionId_">
			<input type="hidden" class="form-control checkacc"	value="${domain}" name="quotationFormMap.domain" id="domain">
			<%-- <lable>译员ID:${transtionId}</lable>|<lable>翻译领域:${domain}</lable> --%>
			<section class="panel panel-default">
				<div class="panel-body">
				
					<div class="form-group">
						<label class="col-sm-3 control-label">翻译报价</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="tranPrice" 	name="quotationFormMap.tranPrice" value="">
						</div>
					</div>
					
					<div class="line line-dashed line-lg pull-in"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">校对报价</label>
						<div class="col-sm-9">
							<input type="text" class="form-control"  id="prooPrice" 	name="quotationFormMap.prooPrice" value="">
						</div>
					</div>
					
					<div class="line line-dashed line-lg pull-in"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">日翻译字数</label>
						<div class="col-sm-9">
							<input type="text" class="form-control"  id="dayTrans" 	name="quotationFormMap.dayTrans" value="">
						</div>
					</div>
					
					<div class="line line-dashed line-lg pull-in"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">语言对</label>
						
                    	
						<div class="col-sm-9">
						<select class="form-control" id="language_src" style="float:left;width:200px;">
		                      <option value="">请选择源语种</option>
		                      <option value="汉">汉语</option>
		                      <option value="英">英语</option>
		                      <option value="德">德语</option>
		                      <option value="法">法语</option>		                      
		                      <option value="日">日语</option>
		                      <option value="韩">韩语</option>
		                      <option value="西">西班牙语</option>
		                      <option value="意">意大利语</option>
		                      <option value="俄">俄语</option>
		                      <option value="阿">阿拉伯语</option>
		                      
                    	</select>
                    	<select class="form-control" id="language_target" style="float:left;width:200px;">
		                      <option value="">请选择目标语种</option>
		                      <option value="汉">汉语</option>
		                      <option value="英">英语</option>
		                      <option value="德">德语</option>
		                      <option value="法">法语</option>
		                      <option value="日">日语</option>
		                      <option value="韩">韩语</option>
		                      <option value="西">西班牙语</option>
		                      <option value="意">意大利语</option>
		                      <option value="俄">俄语</option>
		                      <option value="阿">阿拉伯语</option>
                    	</select>
							<input type="text" class="form-control" id="languages" 	name="quotationFormMap.languages" value=""  readonly="readonly">
							<input type="hidden" class="form-control" id="languages_t" 	name="languages_t_" value=""  readonly="readonly">
							<input type="hidden" class="form-control" id="delt" 	name="delt_" value=""  readonly="readonly">
							<button type="button"  id="btn1" class="btn btn-warning marR10">新增语种</button>
							<button type="button"  id="btn2" class="btn btn-warning marR10">删除语种</button>
							<button type="button"  id="btn3" class="btn btn-warning marR10">完成选择</button>
							<ul id="ubox" class="list-inline nav_lists"></ul>
						</div>
					</div>
					
				</div>
				<footer class="panel-footer text-right bg-light lter">
					<button type="submit" class="btn btn-success btn-s-xs">保存</button>
				</footer>
			</section>
		</form>
	<script type="text/javascript">
	onloadurl();
</script>
</body>
</html>