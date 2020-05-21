<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/advertisement/editAdvIndex.js"></script>
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
#file{
	width: 100px;
    height: 24px;
    position: absolute;
    left: 0;
    opacity: 0;
    z-index: 999;
}

#fileSpan{
	display: block;
    width: 100px;
    height: 24px;
    border: 1px solid #ccc;
    border-radius: 3px;
    text-align: center;
    position: absolute;
    left: 0;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" action="${pageContext.request.contextPath}/advertisement/editAdvertisement.shtml" enctype="multipart/form-data">
		<input type='hidden' name='id' value="${picAdvertiseFormMap.id }">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">链接地址：</label>
				<div class="col-sm-9">
					<input class="form-control" placeholder="请输入连接地址" name="web" id="web" value="${picAdvertiseFormMap.web }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">图片类型：</label>
				<div class="col-sm-9">
					<select class="form-control" name="type" id="type">
						<option value='1' <c:if test="${picAdvertiseFormMap.type==1 || picAdvertiseFormMap.type=='1' }">selected='selected'</c:if> >大图</option>
						<option value='2' <c:if test="${picAdvertiseFormMap.type==2 || picAdvertiseFormMap.type=='2' }">selected='selected'</c:if>>长条图</option>
						<option value='3' <c:if test="${picAdvertiseFormMap.type==3 || picAdvertiseFormMap.type=='3' }">selected='selected'</c:if>>6/1图</option>
						<option value='4' <c:if test="${picAdvertiseFormMap.type==4 || picAdvertiseFormMap.type=='4' }">selected='selected'</c:if>>文本</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">宣传位置：</label>
				<div class="col-sm-9">
					<select class="form-control" name="coord" id="coord">
						<c:if test="${picAdvertiseFormMap.type==1 || picAdvertiseFormMap.type=='1' }">
							<option value="大图" selected="selected">大图</option>
						</c:if>
						<c:if test="${picAdvertiseFormMap.type==2 || picAdvertiseFormMap.type=='2' }">
							<option value='长条图-1' <c:if test="${picAdvertiseFormMap.coord=='长条图-1' }">selected='selected'</c:if> >长条图-1</option>
							<option value='长条图-2' <c:if test="${picAdvertiseFormMap.coord=='长条图-2' }">selected='selected'</c:if>>长条图-2</option>
							<option value='长条图-3' <c:if test="${picAdvertiseFormMap.coord=='长条图-3' }">selected='selected'</c:if>>长条图-3</option>
						</c:if>
						<c:if test="${picAdvertiseFormMap.type==3 || picAdvertiseFormMap.type=='3' }">
							<option value='6/1图-z1-y1' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z1-y1' }">selected='selected'</c:if> >6/1图-z1-y1</option>
							<option value='6/1图-z2-y1' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z2-y1' }">selected='selected'</c:if> >6/1图-z2-y1</option>
							<option value='6/1图-z3-y1' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z3-y1' }">selected='selected'</c:if> >6/1图-z3-y1</option>
							<option value='6/1图-z4-y1' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z4-y1' }">selected='selected'</c:if> >6/1图-z4-y1</option>
							<option value='6/1图-z5-y1' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z5-y1' }">selected='selected'</c:if> >6/1图-z5-y1</option>
							<option value='6/1图-z6-y1' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z6-y1' }">selected='selected'</c:if> >6/1图-z6-y1</option>
							<option value='6/1图-z1-y2' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z1-y2' }">selected='selected'</c:if> >6/1图-z1-y2</option>
							<option value='6/1图-z2-y2' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z2-y2' }">selected='selected'</c:if> >6/1图-z2-y2</option>
							<option value='6/1图-z3-y2' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z3-y2' }">selected='selected'</c:if> >6/1图-z3-y2</option>
							<option value='6/1图-z4-y2' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z4-y2' }">selected='selected'</c:if> >6/1图-z4-y2</option>
							<option value='6/1图-z5-y2' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z5-y2' }">selected='selected'</c:if> >6/1图-z5-y2</option>
							<option value='6/1图-z6-y2' <c:if test="${picAdvertiseFormMap.coord=='6/1图-z6-y2' }">selected='selected'</c:if> >6/1图-z6-y2</option>
						</c:if>
						<c:if test="${picAdvertiseFormMap.type==4 || picAdvertiseFormMap.type=='4' }">
							<option value='文本-1' <c:if test="${picAdvertiseFormMap.coord=='文本-1' }">selected='selected'</c:if> >文本-1</option>
							<option value='文本-2' <c:if test="${picAdvertiseFormMap.coord=='文本-2' }">selected='selected'</c:if> >文本-2</option>
							<option value='文本-3' <c:if test="${picAdvertiseFormMap.coord=='文本-3' }">selected='selected'</c:if> >文本-3</option>
							<option value='文本-4' <c:if test="${picAdvertiseFormMap.coord=='文本-4' }">selected='selected'</c:if> >文本-4</option>
							<option value='文本-5' <c:if test="${picAdvertiseFormMap.coord=='文本-5' }">selected='selected'</c:if> >文本-5</option>
						</c:if>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">宣传图：</label>
				<div class="col-sm-9" id="newUpload1" style="margin-bottom: 20px">
					<div id='fileDIV'>
						<c:if test="${picAdvertiseFormMap.type==1 || picAdvertiseFormMap.type=='1' }">
							<img src='${picAdvertiseFormMap.url }' width="356.2px" height="180px" style="margin-bottom: 20px;border-radius:5px;">
						</c:if> 
						<c:if test="${picAdvertiseFormMap.type==2 || picAdvertiseFormMap.type=='2' }">
							<img src='${picAdvertiseFormMap.url }' width="368px" height="35px" style="margin-bottom: 20px;border-radius:5px;">
						</c:if> 
						<c:if test="${picAdvertiseFormMap.type==3 || picAdvertiseFormMap.type=='3' }">
							<img src='${picAdvertiseFormMap.url }' width="80px" height="40px" style="margin-bottom: 20px;border-radius:5px;">
						</c:if> 
					</div>
					<input type="file" id="file" onchange="frontUrls(this)" name="file"/>
					<span id='fileSpan' style="border: 1px;color: red;">选择宣传图</span>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">宣传内容：</label>
				<div class="col-sm-9">
					<input class="form-control" placeholder="请输入宣传内容" name="context" id="context" value='${picAdvertiseFormMap.context }'>
				</div>
			</div>
			
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否可用：</label>
				<div class="col-sm-9">
					<select class="form-control"name="isUseful" id="isUserful">
						<option value='1' <c:if test="${picAdvertiseFormMap.isUseful==1 || picAdvertiseFormMap.isUseful=='1' }">selected="selected"</c:if>>可用</option>
						<option value='0' <c:if test="${picAdvertiseFormMap.isUseful==0 || picAdvertiseFormMap.isUseful=='0' }">selected="selected"</c:if>>不可用</option>
					</select>
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="submit" class="btn btn-success btn-s-xs">提交</button>
		</footer> 
	</section>
</form>
</body>
<script type="text/javascript">
function frontUrls(file){
   // var maxsize =3*1024*1024*1024;//3M
    var prevDiv = document.getElementById('fileDIV');
    //var size=file.files[0].size;
    fileExt=file.value.substr(file.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(file.value==null || file.value==''){
		layer.open({
			  title: '<spring:message  code="提示"/>',
			  content: '<spring:message code="请上传宣传图片"/>',
		    });
   }else if(fileExt!='.jpg' && fileExt!=".png" && fileExt !=".gif" && fileExt !=".bmp"){
         layer.open({
				  title: '<spring:message  code="提示"/>',
				  content: '<spring:message code="上传只支持格式为："/>jpg、JPG、gif、GIF、png、PNG、bmp、BMP',
		});
	}else if (file.files && file.files[0]) {
	      var reader = new FileReader();
	      reader.onload = function(evt) {
		var size='';
	    	if($("#type").val()==1 || $("#type").val()=='1'){
	    		size='width="356px" height="180px"';
	    	}
			if($("#type").val()==2 || $("#type").val()=='2'){
	    		size='width="368px" height="34px"';
	    	}
			if($("#type").val()==3 || $("#type").val()=='3'){
				size='width="80px" height="40px"';
		}
	        prevDiv.innerHTML = '<img src="' + evt.target.result + '"  '+size+' style="margin-bottom: 20px;border-radius:5px;"/>';
	      }
	      reader.readAsDataURL(file.files[0]);
	    } else {
	      prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
	    } 
    return;  
}

$(function(){
	$("#type").change(function(){
		document.getElementById("coord").options.length=0;
		if($("#type").val()==1 || $("#type").val()=="1"){
			document.getElementById("coord").options.add(new Option("大图","大图"));
		}
		if($("#type").val()==2 || $("#type").val()=="2"){
			document.getElementById("coord").options.add(new Option("长条图-1","长条图-1"));
			document.getElementById("coord").options.add(new Option("长条图-2","长条图-2"));
			document.getElementById("coord").options.add(new Option("长条图-3","长条图-3"));
		}
		if($("#type").val()==3 || $("#type").val()=="3"){
			document.getElementById("coord").options.add(new Option("6/1图-z1-y1","6/1图-z1-y1"));
			document.getElementById("coord").options.add(new Option("6/1图-z2-y1","6/1图-z2-y1"));
			document.getElementById("coord").options.add(new Option("6/1图-z3-y1","6/1图-z3-y1"));
			document.getElementById("coord").options.add(new Option("6/1图-z4-y1","6/1图-z4-y1"));
			document.getElementById("coord").options.add(new Option("6/1图-z5-y1","6/1图-z5-y1"));
			document.getElementById("coord").options.add(new Option("6/1图-z6-y1","6/1图-z6-y1"));
			document.getElementById("coord").options.add(new Option("6/1图-z1-y2","6/1图-z1-y2"));
			document.getElementById("coord").options.add(new Option("6/1图-z2-y2","6/1图-z2-y2"));
			document.getElementById("coord").options.add(new Option("6/1图-z3-y2","6/1图-z3-y2"));
			document.getElementById("coord").options.add(new Option("6/1图-z4-y2","6/1图-z4-y2"));
			document.getElementById("coord").options.add(new Option("6/1图-z5-y2","6/1图-z5-y2"));
			document.getElementById("coord").options.add(new Option("6/1图-z6-y2","6/1图-z6-y2"));
			
		}
		if($("#type").val()==4 || $("#type").val()=="4"){
			document.getElementById("coord").options.add(new Option("文本-1","文本-1"));
			document.getElementById("coord").options.add(new Option("文本-2","文本-2"));
			document.getElementById("coord").options.add(new Option("文本-3","文本-3"));
			document.getElementById("coord").options.add(new Option("文本-4","文本-4"));
			document.getElementById("coord").options.add(new Option("文本-5","文本-5"));
			
		}
		
		
	});
})

</script>
</html>