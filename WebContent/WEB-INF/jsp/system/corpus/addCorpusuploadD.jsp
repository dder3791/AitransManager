<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/corpus/addCorpusupload.js"></script>
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
    $(document).ready(function(){  
        $("#btn_add1").click(function(){  
        	 var oin=document.createElement("input");
        	 oin.setAttribute("id","file");
        	 oin.setAttribute("type","file");
        	 oin.setAttribute("name","file");
        	 oin.setAttribute("onchange","checkFile(this)");
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
    function checkFile(obj){
    	fileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    	if(fileExt!='.tmx' && fileExt!=".txt" && fileExt !=".doc" && fileExt !=".xls"){
    		alert("请上传后缀名为tmx,txt,doc,xls的文件!");
    		obj.outerHTML=obj.outerHTML; 
    		return false;
    	}
    }

    </script>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/corpus/uploadCorpus1.shtml?corpusName=${corpusName }" enctype="multipart/form-data">
	<section class="panel panel-default">
		<input type="hidden" value="${id}" name="id">
		<input type="hidden" name="User_UC" value="1" id="User_UC">
		<div class="panel-body">
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">语料名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入语料名称" name="corpusName" id="corpusName"
						value="${corpusName }" disabled="disabled">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">文件上传</label>
					 <span style="color:red">*上传文件为tmx,txt,word,xls文件!</span>
				<div class="col-sm-9" id="newUpload1" align="center">
					<input type="file" id="file1" name="file" onchange="checkFile(this)" />
				</div>
				<button type="button" id="btn_add1" class="btn btn-primary marR10">继续添加</button>
			    <button type="button" id="btn_del1" class="btn btn-primary marR10" onclick="del_1();">删除</button>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group" >
				<label class="col-sm-3 control-label">所属领域</label>
				<div class="col-sm-9">
						<select id="domainId" name="domainId"
							class="selectpicker show-tick form-control">
							<c:forEach items="${domain }" var="domain">
							<option value="${domain.id }">${domain.name}</option>
							</c:forEach>
						</select> 
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