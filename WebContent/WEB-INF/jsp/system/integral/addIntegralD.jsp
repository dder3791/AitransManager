<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/integral/add.js"></script>
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
	action="${pageContext.request.contextPath}/integral/addGoods.shtml" enctype="multipart/form-data">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">商品名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入商品名称" name="prize" id="prize">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">所需积分</label>
				<div class="col-sm-9">
					<input type="text" class="form-control existing"
						placeholder="请输入商品积分" name="markMin" id="markMin">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">商品类别</label>
				<div class="col-sm-9">
					<select id="categoryId" name="categoryId"
							class="selectpicker show-tick form-control">
							<c:forEach items="${category }" var="category">
							<option value="${category.id }">${category.prizeName}</option>
							</c:forEach>
						</select> 
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" >
				<label class="col-sm-3 control-label">商品图片</label>
				<div class="col-sm-9" id="newUpload1" align="center">
					<input type="file" id="file" name="file" onchange="checkFile(this)" />
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入商品描述" name="remark" id="remark">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button id=“sub1” type="submit" class="btn btn-success btn-s-xs" onclick="lookfile();">提交</button>
		</footer> 
	</section>
</form> 
</body>
</html>