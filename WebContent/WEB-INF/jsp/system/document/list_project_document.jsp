<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/document/list_project_document.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">
	function deleteDir(){
		var urlname=document.getElementById("spanValue").value;
		var path=document.getElementById("inputValue").value;
		layer.confirm('是否删除？', function(index) {
			var url = rootPath + '/document/deleteDirectory.shtml?type=0&path='+path+'&name='+urlname;
			var s = CommnUtil.ajax(url,"json");
			
			if (s=='"success"') {
	
				layer.msg('删除成功');
				parent.$("#loadhtml").load(rootPath+"/document/document_UI.shtml");
			} else {
				layer.msg('删除失败');
			}
		});
	}
	function lookDir(){
		var name=document.getElementById("spanValue").value;
		var url=document.getElementById("inputValue").value;
		findDocument(name,url);
	}
	function newDir(){
		var path=document.getElementById("inputValue").value;
		pageii = layer.open({
			title : "新增",
			type : 2,
			area : [ "600px", "40%" ],
			content : rootPath + '/document/addUI.shtml?path='+path
		});
	}
	
	function renameDir(){
		var urlname=document.getElementById("spanValue").value;
		var path=document.getElementById("inputValue").value;
		pageii = layer.open({
			title : "重命名",
			type : 2,
			area : [ "600px", "40%" ],
			content : rootPath + '/document/renameUI.shtml?type=0&path='+path+'&oldname='+urlname
		});
	}

	function uploadfile(){

		var path=document.getElementById("inputValue").value;
		pageii = layer.open({
			title : "新增",
			type : 2,
			area : [ "600px", "60%" ],
			content : rootPath + '/document/uploadUI.shtml?path='+path
		});
	}
	
	function downFile(){
		
		var urlname=document.getElementById("spanValue").value;
		$("#name").prop("value",urlname);
		var path=document.getElementById("inputValue").value;
		$("#oldPath").prop("value",path);
		$('form').submit();
		/* pageii = layer.open({
			title : "下载文件",
			type : 2,
			area : [ "600px", "40%" ],
			content : rootPath + '/document/downloadUI.shtml?path='+path+'&name='+urlname
		}); */
		//var url = rootPath + '/document/project_download.shtml?path='+path+'&urlname='+urlname;
		//CommnUtil.ajax(url);
	}
	
	function renameFile(){
		var urlname=document.getElementById("spanValue").value;
		var path=document.getElementById("inputValue").value;
		pageii = layer.open({
			title : "重命名",
			type : 2,
			area : [ "600px", "40%" ],
			content : rootPath + '/document/renameUI.shtml?type=1&path='+path+'&oldname='+urlname
		});
	}
	
	function deleteFile(){
		var urlname=document.getElementById("spanValue").value;
		var path=document.getElementById("inputValue").value;
		layer.confirm('是否删除？', function(index) {
			var url = rootPath + '/document/deleteDirectory.shtml?type=1&path='+path+'&name='+urlname;
			var s = CommnUtil.ajax(url,"json");
			
			if (s=='"success"') {
				layer.msg('删除成功');
				parent.$("#loadhtml").load(rootPath+"/document/document_UI.shtml");
			} else {
				layer.msg('删除失败');
			}
		});
	}
	
	function back(){
		var url=document.getElementById("oldurl").value;
		findDocument("",url);
		
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="back"><a href="#" onclick="back()" style="margin-top:120px;margin-left:50px;">返回上一级</a></div>
	<input type="hidden" id="spanValue">
	<input type="hidden" id="inputValue">
	<input type="hidden" id="oldurl">
	<ul id="tableRight" style="width: 130px;position: absolute;z-index: 100;"> 
		<li class="label bg-dark" id="lookDir"><a href="#" onclick="lookDir()">打开文件夹</a></li>
		<li class="label bg-dark" id="renameDir"><a href="#" onclick="renameDir()">重命名</a></li> 
		<li class="label bg-dark" id="deleteDir"><a href="#" onclick="deleteDir()">删除文件夹</a></li> 
	</ul>
	<ul id="fileRight" style="width: 130px;position: absolute;z-index: 100;"> 
		<li class="label bg-dark" id="downFile"><a href="#" onclick="downFile()">下载</a><br></li>
		<li class="label bg-dark" id="renamefile"><a href="#" onclick="renameFile()">重命名</a></li> 
		<li class="label bg-dark" id="deletefile"><a href="#" onclick="deleteFile()">删除文件</a></li> 
	</ul>
	<ul id="documentRight" style="width: 130px;position: absolute;z-index: 100;"> 
		<li class="label bg-dark"  id="newDir"><a href="#" onclick="newDir()">新建文件夹</a></li> 
		<li class="label bg-dark" id="uploadfile"><a href="#"  onclick="uploadfile()">上传文件</a></li> 
	</ul>
	
	<div style="margin-left: 30px; margin-top: 20px;float: left;" id="myDiv">
		<table id="myTable"  class="table">	</table>
	</div>
	<div style="display: none;">
		<form id="form" name="form" class="form-horizontal" method="post" action="${pageContext.request.contextPath}/document/project_download.shtml">
			<input class="form-control checkacc" name="oldPath" id="oldPath" readonly="readonly">
			<input class="form-control checkacc" name="name" id="name">
	   </form>
    </div>
</body>
</html>