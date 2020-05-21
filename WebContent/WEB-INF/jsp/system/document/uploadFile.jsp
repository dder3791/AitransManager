<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/document/uploadFile.js"></script>
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
$(function(){
	$("#btn_add1").click(function(){  
   	 var oin=document.createElement("input");
   	 oin.setAttribute("id","file");
   	 oin.setAttribute("type","file");
   	 oin.setAttribute("name","filename");
   	
   	 document.getElementById("newUpload1").appendChild(oin);  
   	 document.getElementById("btn_del1").removeAttribute("disabled");
   }); 
});

function del_1(){  
	var odel=document.getElementById("newUpload1");
	   var ofile=odel.lastElementChild;
	   var name="";
	   name = ofile.getAttribute("id");
	   if(name=='file1'){
		   document.getElementById("btn_del1").setAttribute("disabled","true");
	   }else{
		   odel.removeChild(ofile);
	   }
   } 

function closeWin(){
	layer.confirm('是否关闭窗口？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

</script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post" action="${pageContext.request.contextPath}/document/uploadFile.shtml" enctype="multipart/form-data">
		<section class="panel panel-default">
			<div class="panel-body">
				<div class="form-group" style="width: 100%">
					<label class="col-sm-3 control-label" style="width:25%;">文件上传路径：</label>
					<div class="col-sm-9" style="width: 75%">
						<input class="form-control checkacc"  name="path" id="url" value="${path }" readonly="readonly">
					</div>
				</div>
				<div class="form-group" style="width: 100%">
					<label class="col-sm-3 control-label" style="width:25%;">上传文件:</label>			
					<div class="col-sm-9" id="newUpload1" align="left" style="width: 240px">
						<input type="file" style="margin-right: 10px;" id="file1" name="filename"/>
					</div>
					<div  style="margin-right: 70px" align="right">
						<a id="btn_add1" style="margin-top: 25px;"><img src='/AitransManager/images/document/addFile.png' height='28px' width='28px'></a>&nbsp;&nbsp;&nbsp;
				    	<a id="btn_del1" style="margin-top: 25px;" onclick="del_1();"><img src='/AitransManager/images/document/deleteFile.png' height='28px' width='28px'></a>
					</div>
				</div>
			</div>
			<footer class="panel-footer text-right bg-light lter">
			<button type="submit" class="btn btn-success btn-s-xs">上传</button>
			<a href="#" class="btn btn-s-md btn-info btn-rounded" onclick="closeWin()">关闭</a>
			</footer> 
		</section>
   </form>
</body>
</html>