<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	width: 60%;
	float: left;
}
.yes {
	width:11%;
	float:left;
}
.no {
	width:11%;
	float:left;
}
</style>
<%-- <script type="text/javascript" src="${ctx}/js/system/project/verify_project.js"></script> --%>
<script type="text/javascript">	
$(function(){
	$("#all").click(function(){
		var radio =$(".ra");
        for(var i = 0;i<radio.length;i++)  
        {  
        	value = radio[i].value; 
        	if(value=='yes'){            		
        		radio[i].checked=true;
        	}
        }
	});
})
	function verify(){
		var value='';  
		var flag=true;  
		var count=0;
		var count1=0;
        var radio =$(".ra");
        for(var i = 0;i<radio.length;i++)  
        {  
        	//alert("------------");
            if(radio[i].checked==true)  
            {	count1=count1+1;
            	value = radio[i].value; 
            	if(value!='yes'){            		
            		flag=false;
            	}
            }else{
            	count=count+1;
            }  
        } 
                
        if(count<=radio.length&&count>19){
        	flag=false;
        	layer.alert('有漏选，审核失败！', 3);
        }else{
        	if(flag){
        		var pid=document.getElementById("pid").value;
        		$.post("${pageContext.request.contextPath}/project/verify.shtml?pid="+pid,function(data){
        			if(data=='success'){
        				layer.confirm('审核成功!是否关闭窗口?', function(index) {
        					parent.grid.loadData();
        					parent.layer.close(parent.pageii);
							return false;
						});
						$("#form")[0].reset();
        			}else{
        				layer.alert('审核失败！', 3);
        			}
        		},'json');
            }
            else {
            	layer.alert('审核失败！', 3);
    		} 
        }
	}
	
function closeWin(){
	layer.confirm('是否关闭窗口？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

function downFile(ele){
	//alert(ele.innerText);
	var name=ele.getElementsByTagName('span')[0].innerText;
	var url=document.getElementById("url").value;
	
	
}
</script>
<script type="text/javascript">

</script>
</head>
<body>

	<h1 style="margin-left: 100px;font-family: 楷体;color: blue">
		项目核实<span style="color: red;font-size: 20px">(勾选后即可核实项目)</span>
		<input type="button" onclick="all()" id="all" style="font-size: 20px;color:green;margin-left:340px;top:200px;" value="yijian">
	</h1>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="">
	<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">项目编号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control" id="pid" name="pid" value="${projectFormMap.id }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="id" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="id" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">公司案号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.reference }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="reference" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="reference" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户案号</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.customerReference }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="customerReference" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="customerReference" style="margin-left:40px;"  value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">客户名称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.cname }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="cname" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="cname" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			
			<div class="form-group">
				<label class="col-sm-3 control-label">项目名称</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.pname }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="pname" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="pname" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目经理</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.head }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="head" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="head" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译类型</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.projectType }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="projectType" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="projectType" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">流程类型</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.procedureType }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="procedureType" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="procedureType" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			
			<%-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译语种</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.language }">
				</div>
			</div> --%>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译资费</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${rewardFormMap.transReward }元">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="transReward" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="transReward" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对资费</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${rewardFormMap.verifyReward }元">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="verifyReward" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="verifyReward" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核资费</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${rewardFormMap.auditoryReward }元">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="auditoryReward" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="auditoryReward" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">翻译员</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.tname }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="tname" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="tname" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">校对员</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.vname }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="vname" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="vname" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">审核员</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.aname }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="aname" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="aname" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">立项时间</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.createTime }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="createTime" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="createTime" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">返稿时间</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.completeTime }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="completeTime" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="completeTime" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">项目状态</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.stateName}">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="stateName" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="stateName" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">相关文件</label>
				<div class="col-sm-9">
					<center>
						<table border="0">
						<c:forEach var="ff" items="${fileNameMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								<a href="${pageContext.request.contextPath}/project/project_download.shtml?urlname=${ff.value}&fileURL=${ff.key}">${ff.value}</a>
								<span></span>
								<input id="url" value="${ff.key}">
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
					${message.name }
					
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="fileNameMap" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="fileNameMap" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注</label>
				<div class="col-sm-9">
					<input readOnly="readonly" type="text" class="form-control"
						value="${projectFormMap.description }">
				</div>
				<div class="yes">
					<input class="ra" id="yes" type="radio" name="description" style="margin-left:40px;" value="yes">
					<img alt="" src="/AitransManager/images/verify_project/yes.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
				<div class="no">
					<input class="ra" id="no" type="radio" name="description" style="margin-left:40px;" value="no">
					<img alt="" src="/AitransManager/images/verify_project/no.png" style="width:26px;height: 26px;margin-buttom:3px;">
				</div>
			</div>
			
		</div>
		
		
		
		
		<footer class="panel-footer text-right bg-light lter">
		<button type="button" onclick="verify()" id="back" class="btn btn-success btn-s-xs">审核完毕</button>
		<button type="button" id="back" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">取消</button>
		</footer> 
	</section>
</form>
</body>
</html>