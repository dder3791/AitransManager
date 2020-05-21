<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/article/addArticle.js"></script>
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
<script type="text/javascript">
    function checkFile(obj){
    	fileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    	if(fileExt!='.png'&&fileExt!='.jpg'){
    		alert("请上传后缀名为png,jpg的图片!");
    		obj.outerHTML=obj.outerHTML; 
    		return false;
    	}
    }
</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/article/addEntity.shtml" enctype="multipart/form-data">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">资讯标题：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入资讯标题" name="articleFormMap.title" id="title"> 
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">资讯内容：</label>
				<div class="col-sm-9">
				<textarea rows="3" cols="64" class="form-control" placeholder="请输入资讯内容" name="articleFormMap.context" id="context"></textarea>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">发布日期：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="articleFormMap.issueDate" id="issueDate" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">咨询类型：</label>
				<div class="col-sm-9">
					<select name="articleFormMap.type">
						<option value="活动资讯" selected="selected">活动资讯</option>
						<option value="公告资讯">公告资讯</option>
						<option value="平台资讯">平台资讯</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">资讯图示：</label>
				<div class="col-sm-9" id="newUpload1" align="center">
					<input type="file" id="file" name="file" onchange="checkFile(this)" />
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">发布人：</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入发布作者" name="articleFormMap.auther" id="auther">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否热门：</label>
				<div class="col-sm-9">
					<input type="radio" name="articleFormMap.hot" value="1">YES 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="articleFormMap.hot" value="0"  checked="checked">NO
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否精品</label>
				<div class="col-sm-9">
					<input type="radio" name="articleFormMap.elite" value="1">YES 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="articleFormMap.elite" value="0"  checked="checked">NO
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