<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<style type="text/css">
.col-sm-3 {
	width: 14%;
	float: left;
}
.col-sm-9 {
	width: 86%;
	float: left;
}

.tr-first-td{
	font-size: 16px;
	font-style: inherit;
	word-wrap:break-word;
	overflow:hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
}

.t-tr{
	text-align: center
}
</style>
<script type="text/javascript">

$(function(){
	
	/*  $('#quoationTable').on( 'click', 'a', function (e) {
		 var cellindex = $(this).parent()[0]._DT_CellIndex.col; //列号
		 var rowindex = $(this).parent()[0]._DT_CellIndex.row; //行号
		 alert("cellindex"+cellindex+"rowindex"+rowindex);
	 }); */
	document.getElementById("btn_del1").setAttribute("disabled","true");
	$.post("${pageContext.request.contextPath}/transfer/getTransQuo.shtml",{"id":$("#transId").val()},function(data){
		 if("翻译员"==$("#type").val()&&data.length>0){
			 $("#quoationTable").append("<tr class='t-tr'><td class='tr-first-td' id='onlyTrans'>翻译</td></tr>");
			 for(var i=0;i<data.length;i++){
					var languagesAndAdmian="<tr class='t-tr'><td>"+data[i].languages+"</td><td>"+data[i].domain+"</td>"
					var onlyTrans='';
					onlyTrans=languagesAndAdmian+"<td>";
					if(data[i].dayTrans!='' && data[i].dayTrans!=null && data[i].dayTrans!=undefined){
						onlyTrans+=data[i].dayTrans;
					}
					onlyTrans+="</td><td>";
					if(data[i].majorTotal!='' && data[i].majorTotal!=null && data[i].majorTotal!=undefined){
						onlyTrans+=data[i].majorTotal;
					}
					onlyTrans+="</td><td>";
					
					onlyTrans+="<select><option value='1' "
					if(data[i].tranlevels==1 || data[i].tranlevels=='1'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">1级</option>"
					
					onlyTrans+="<option value='2' "
					if(data[i].tranlevels==2 || data[i].tranlevels=='2'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">2级</option>"
					
					onlyTrans+="<option value='3' "
					if(data[i].tranlevels==3 || data[i].tranlevels=='3'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">3级</option>"
					
					onlyTrans+="<option value='4' "
					if(data[i].tranlevels==4 || data[i].tranlevels=='4'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">4级</option>"
					
					onlyTrans+="<option value='5' "
					if(data[i].tranlevels==5 || data[i].tranlevels=='5'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">5级</option></select>"
					onlyTrans+="</td><td>";
					if(data[i].tranPrice!='' && data[i].tranPrice!=null && data[i].tranPrice!=undefined){
						onlyTrans+=data[i].tranPrice;
					}
					//alert(data[i].applyStateT)
					if(data[i].applyStateT==1 || data[i].applyStateT=="1"){
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' checked='checked' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					else if(data[i].applyStateT==2 || data[i].applyStateT=="2"){
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' checked='checked' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					else{
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					
					$("#quoationTable").append(onlyTrans);
				}
		 }
		 else if("校对员"==$("#type").val()&&data.length>0){
			 $("#quoationTable").append("<tr class='t-tr'><td class='tr-first-td' id='onlyTrans'>翻译+校对</td></tr>");
				for(var i=0;i<data.length;i++){
					var languagesAndAdmian="<tr class='t-tr'><td>"+data[i].languages+"</td><td>"+data[i].domain+"</td>"
					var onlyTrans='';
					onlyTrans=languagesAndAdmian+"<td>";
					if(data[i].dayProo!='' && data[i].dayProo!=null && data[i].dayProo!=undefined){
						onlyTrans+=data[i].dayProo;
					}
					onlyTrans+="</td><td>";
					if(data[i].prooTotal!='' && data[i].prooTotal!=null && data[i].prooTotal!=undefined){
						onlyTrans+=data[i].prooTotal;
					}
					onlyTrans+="</td><td>";
					/* if(data[i].proolevels!='' && data[i].proolevels!=null && data[i].proolevels!=undefined){
						onlyTrans+=data[i].proolevels;
					} */
					onlyTrans+="<select><option value='1' "
					if(data[i].proolevels==1 || data[i].proolevels=='1'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">1级</option>"
					
					onlyTrans+="<option value='2' "
					if(data[i].proolevels==2 || data[i].proolevels=='2'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">2级</option>"
					
					onlyTrans+="<option value='3' "
					if(data[i].proolevels==3 || data[i].proolevels=='3'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">3级</option>"
					
					onlyTrans+="<option value='4' "
					if(data[i].proolevels==4 || data[i].proolevels=='4'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">4级</option>"
					
					onlyTrans+="<option value='5' "
					if(data[i].proolevels==5 || data[i].proolevels=='5'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">5级</option></select>"
					onlyTrans+="</td><td>";
					if(data[i].prooPrice!='' && data[i].prooPrice!=null && data[i].prooPrice!=undefined){
						onlyTrans+=data[i].prooPrice;
					}
					if(data[i].applyStateP==1 || data[i].applyStateP=="1"){
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' checked='checked' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					else if(data[i].applyStateP==2 || data[i].applyStateP=="2"){
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' checked='checked' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					else{
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					$("#quoationTable").append(onlyTrans);
				}
		 }
		 else if("审核员"==$("#type").val() && data.length>0){
			 $("#quoationTable").append("<tr><td class='tr-first-td' id='onlyTrans'>翻+校+审</td></tr>");
			 for(var i=0;i<data.length;i++){
					
					var languagesAndAdmian="<tr class='t-tr'><td>"+data[i].languages+"</td><td>"+data[i].domain+"</td>"
					var onlyTrans='';
					onlyTrans=languagesAndAdmian+"<td>";
					if(data[i].dayAudit!='' && data[i].dayAudit!=null && data[i].dayAudit!=undefined){
						onlyTrans+=data[i].dayAudit;
					}
					onlyTrans+="</td><td>";
					if(data[i].auditTotal!='' && data[i].auditTotal!=null && data[i].auditTotal!=undefined){
						onlyTrans+=data[i].auditTotal;
					}
					onlyTrans+="</td><td>";
					/* if(data[i].auditlevels!='' && data[i].auditlevels!=null && data[i].auditlevels!=undefined){
						onlyTrans+=data[i].auditlevels;
					} */
					
					onlyTrans+="<select><option value='1' "
					if(data[i].auditlevels==1 || data[i].auditlevels=='1'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">1级</option>"
					
					onlyTrans+="<option value='2' "
					if(data[i].auditlevels==2 || data[i].auditlevels=='2'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">2级</option>"
					
					onlyTrans+="<option value='3' "
					if(data[i].auditlevels==3 || data[i].auditlevels=='3'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">3级</option>"
					
					onlyTrans+="<option value='4' "
					if(data[i].auditlevels==4 || data[i].auditlevels=='4'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">4级</option>"
					
					onlyTrans+="<option value='5' "
					if(data[i].auditlevels==5 || data[i].auditlevels=='5'){
						onlyTrans+="selected='selected'" 
					}
					onlyTrans+=">5级</option></select>"
					
					onlyTrans+="</td><td>";
					if(data[i].auditPrice!='' && data[i].auditPrice!=null && data[i].auditPrice!=undefined){
						onlyTrans+=data[i].auditPrice;
					}
					if(data[i].applyStateA==1 || data[i].applyStateA=="1"){
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' checked='checked' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					else if(data[i].applyStateA==2 || data[i].applyStateA=="2"){
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' checked='checked' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					else{
						onlyTrans+="</td><td><input type='radio' name='action"+i+"' value='0' onclick='deleteTR(this);'/>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+i+"' value='1' onclick='deleteTR(this);'/>N</td></tr>";//<a href='javascript:void(0);'>删除</a>
					}
					$("#quoationTable").append(onlyTrans);
				}
		 }
		 $("#onlyTrans").attr("rowspan",data.length+1);
	},"json");
	
	$("#btn_add1").click(function(){  
		var trlength=document.getElementsByTagName("tr").length;
		trlength=trlength-2;
		alert(trlength);
		if("翻译员"==$("#type").val() && trlength<0){
			$("#quoationTable").append("<tr class='t-tr'><td class='tr-first-td' id='onlyTrans'>翻译</td></tr>");
		}
		if("校对员"==$("#type").val() && trlength<0){
			$("#quoationTable").append("<tr class='t-tr'><td class='tr-first-td' id='onlyTrans'>翻译+校对</td></tr>");
		}
		if("审核员"==$("#type").val() && trlength<0){
			$("#quoationTable").append("<tr class='t-tr'><td class='tr-first-td' id='onlyTrans'>翻+校+审</td></tr>");
		}
   		var oin="<tr class='t-tr add-tr'><td>"+
   		" <select name='proofLanguage'>"+
        "<option value='汉英' selected='selected'>汉英</option>"+
        "<option value='英汉'>英汉</option>"+
        "<option value='日汉'>日汉</option>"+
        "<option value='汉日'>汉日</option>"+
        "<option value='韩汉'>韩汉</option>"+
        "<option value='汉韩'>汉韩</option>"+
        "<option value='法汉'>法汉</option>"+
        "<option value='汉法'>汉法</option>"+
        "<option value='德汉'>德汉</option>"+
        "<option value='汉德'>汉德</option>"+
       "</select></td><td>"+
 		 "<select name='proofDomain'>"+
		 	"<option value='日常' selected='selected'>日常</option>"+
		 	"<option value='机械'>机械</option>"+
		 	"<option value='电子'>电子</option>"+
		 	"<option value='电器'>电器</option>"+
		 	"<option value='机电'>机电</option>"+
		 	"<option value='自动化'>自动化</option>"+
		 	"<option value='计算机'>计算机</option>"+
		 	"<option value='通信'>通信</option>"+
		 	"<option value='网络'>网络</option>"+
		 	"<option value='物理'>物理</option>"+
		 	"<option value='化学'>化学</option>"+
		 	"<option value='化工'>化工</option>"+
		 	"<option value='医药'>医药</option>"+
		 	"<option value='生化'>生化</option>"+
		  "</select></td><td></td><td></td><td>"+
	       "<select name='proolevels'>"+
	        "<option value='1' selected='selected' >1级</option>"+
	        "<option value='2'>2级</option>"+
		    "<option value='3'>3级</option>"+
		    "<option value='4'>4级</option>"+
		    "<option value='5'>5级</option>"+
		  "</select>"+
        "</td><td></td><td><input type='radio' name='action"+trlength+"' value='0' onclick='deleteTR1(this);'>Y&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='action"+trlength+"' value='1' onclick='deleteTR1(this);'>N</td>"+
        +"</tr>"
   	
   	 $("#quoationTable").append(oin);  
	 //alert(document.getElementById("onlyTrans").rowspan);
   	 $("#onlyTrans").attr("rowspan",$(".t-tr").length+1);
   	 
   	 document.getElementById("btn_del1").removeAttribute("disabled");
   }); 
})

function del_1(){  
    	var odel=document.getElementById("lastMessageDIV");
    	//得到所有div子元素
    	var a = $(".add-tr").length; 
   	  if(a==1){
   		 $("#quoationTable tr:last").remove();
 		 $("#onlyTrans").attr("rowspan",$(".t-tr").length);
   		 document.getElementById("btn_del1").setAttribute("disabled",true);
   	   }else{
   		   $("#quoationTable tr:last").remove();
   		   $("#onlyTrans").attr("rowspan",$(".t-tr").length);
   	   }
     } 


function closeWin(){
	layer.confirm('放弃此次审核？', {icon: 3,offset: '0px'}, function(index) {
    	parent.layer.close(parent.pageii);
    	return false;
	});
}

       

/* function verify(obj){
    		var pid=document.getElementById("transId").value;
    		var phone=document.getElementById("phone").value;
    		var proolevels=document.getElementsByName("proolevels");
    		var proofDomain=document.getElementsByName("proofDomain");
    		var proofLanguage=document.getElementsByName("proofLanguage");
    		
    		var type=document.getElementById("type").value;
    		
    		  var proolevelList = "";
    		  if(proolevels.length>0) {//有length个等级select
    		  for(var i = 0;i < proolevels.length;i++) {
    			  proolevelList+= proolevels[i].value+",";
    		  }
    		}
    		  
    		  var proofDomainList = "";
    		  if(proofDomain.length>0) {//有length个等级select
    		  for(var i = 0;i < proofDomain.length;i++) {
    			  proofDomainList+= proofDomain[i].value+",";
    		  }
    		}
    		  
    		  var proofLanguageList = "";
    		  if(proofLanguage.length>0) {//有length个等级select
    		  for(var i = 0;i < proofLanguage.length;i++) {
    			  proofLanguageList+= proofLanguage[i].value+",";
    		  }
    		}
    		  
    			$.post("${pageContext.request.contextPath}/transfer/proofreader.shtml","message="+obj+"&transId="+pid+"&proolevels="+proolevelList+"&proofDomain="+proofDomainList+"&proofLanguage="+proofLanguageList+"&type="+type,function(data){
        			if(data=='success'){
    					//短信通知译员
    					var dd="";
    					if('no'==obj){
    						dd='error';
    					}else if('yes'==obj){
    						dd='success';
    					}
        				$.post("${pageContext.request.contextPath}/sendMessage/sendMessageAction_sendPhone.shtml?phone="+phone+"&message="+dd+"&proolevels="+proolevelList+"&proofDomain="+proofDomainList+"&proofLanguage="+proofLanguageList+"&type="+type,function(data){
        		   		   if(data!=null && data!=""){
        		   			   
        		   			layer.confirm('短信已通知译员!', function(index) {
            					parent.grid.loadData();
            					parent.layer.close(parent.pageii);
            					
        						return false;
        					});
        		   		   }else{
        		   			layer.alert('短信通知译员发送失败，请重新发送！', 3);
        		   		   }
        		  	     },"text");
    					
    					
    					$("#form")[0].reset();
        			}else{
        				layer.alert('审核信息失败！', 3);
        			}
        		},'json'); 
        }
        
        
  function notverify(){
	  var pid=document.getElementById("transId").value;
		var phone=document.getElementById("phone").value;
		var proolevels=document.getElementsByName("proolevels");
		var proofDomain=document.getElementsByName("proofDomain");
		var proofLanguage=document.getElementsByName("proofLanguage");
		
		var type=document.getElementById("type").value;
		
		  var proolevelList = "";
		  if(proolevels.length>0) {//有length个等级select
		  for(var i = 0;i < proolevels.length;i++) {
			  proolevelList+= proolevels[i].value+",";
		  }
		}
		  
		  var proofDomainList = "";
		  if(proofDomain.length>0) {//有length个等级select
		  for(var i = 0;i < proofDomain.length;i++) {
			  proofDomainList+= proofDomain[i].value+",";
		  }
		}
		  
		  var proofLanguageList = "";
		  if(proofLanguage.length>0) {//有length个等级select
		  for(var i = 0;i < proofLanguage.length;i++) {
			  proofLanguageList+= proofLanguage[i].value+",";
		  }
		}
		  
		//短信通知译员
			$.post("${pageContext.request.contextPath}/sendMessage/sendMessageAction_sendPhone.shtml?phone="+phone+"&message=error&proolevels="+proolevelList+"&proofDomain="+proofDomainList+"&proofLanguage="+proofLanguageList+"&type="+type,function(data){
	   		   if(data!=null && data!=""){
	   			   
	   			layer.confirm('短信已通知译员!', function(index) {
					parent.grid.loadData();
					parent.layer.close(parent.pageii);
					
					return false;
				});
	   		   }else{
	   			layer.alert('短信通知译员发送失败，请重新发送！', 3);
	   		   }
	  	     },"text");  
	  } */
  
  function getRowObj(obj)
	{ 	
		var i = 0;   
		while(obj.tagName.toLowerCase() != "tr")
			{    obj = obj.parentNode;    
			if(obj.tagName.toLowerCase() == "table")
			return null;   
			}   
		return obj;
	}
 
	 //复制deleteTR方法(针对后台新添加翻译资格)
	  function deleteTR1(elem){ 
			 var trObj = getRowObj(elem);
			// alert(trObj)//行
			 var trArr = trObj.parentNode.children;
			// alert(trArr)//列
			 
			
			 var num=0;//记录是否通过审核，进行对应提示
			 var language='';
			 var domain='';
			 var level='';
			 var action='';
			 for(var trNo= 0; trNo < trArr.length; trNo++)
				{  
					if(trObj == trObj.parentNode.children[trNo])
					{  
						num=1;
						language=document.getElementById("quoationTable").rows[trNo].cells[0].children[0].value;//语言
						domain=document.getElementById("quoationTable").rows[trNo].cells[1].children[0].value;//领域
						level=document.getElementById("quoationTable").rows[trNo].cells[4].children[0].value;//等级
						action=elem.value;//操作
					}
				}
			if(num>0){
				var s='';
				if(action==1 || action=='1'){
					s='确认关闭？';
				}else{
					s='审核通过？'
				}
				 layer.confirm(s, function(index) {
						var url ='${pageContext.request.contextPath}/transfer/deleteQuo.shtml';
						$.post(url,{"transId":$("#transId").val(),"domain":domain,"language":language,"level":level,"type":$("#type").val(),"quoId":1,"action":action},function(data){
							 if(data=='success'){
								 layer.msg('操作成功！');
							 }else{
								 layer.msg("操作失败！");
							 }
						 },'json');
					});
			 } 
			}
	  
 function deleteTR(elem){ 
	 var trObj = getRowObj(elem);
	// alert(trObj)//行
	 var trArr = trObj.parentNode.children;
	// alert(trArr)//列
	 
	 var num=0;
	 var language='';
	 var domain='';
	 var level='';
	 var action='';
	 for(var trNo= 0; trNo < trArr.length; trNo++)
		{  
			if(trObj == trObj.parentNode.children[trNo])
			{  
				num=1;
				language=document.getElementById("quoationTable").rows[trNo].cells[0].innerText;//语言
				domain=document.getElementById("quoationTable").rows[trNo].cells[1].innerText;//领域
				level=document.getElementById("quoationTable").rows[trNo].cells[4].children[0].value;//等级
				action=elem.value;//操作
				alert(level);
			}
		}
	 
	if(num>0){
		var s='';
		if(action==1 || action=='1'){
			s='确认关闭？';
		}else{
			s='审核通过？'
		}
		 layer.confirm(s, function(index) {
				var url ='${pageContext.request.contextPath}/transfer/deleteQuo.shtml';
				$.post(url,{"transId":$("#transId").val(),"domain":domain,"language":language,"level":level,"type":$("#type").val(),"quoId":1,"action":action},function(data){
					 if(data=='success'){
						 layer.msg('操作成功！');
					 }else{
						 layer.msg("操作失败！");
					 }
				 },'json');
			});
	 }	
	}
	
	
	/*删除选中行*/ 
	function del(){ 
		if(selectedTr!=null){ 
			if(confirm("确定要删除吗?")){ 
				alert(selectedTr.cells[0].childNodes[0].value); 
				var tbody=selectedTr.parentNode; 
				tbody.removeChild(selectedTr); 
			} 
		}/* else{ 
		alert("请选择一行"); 
		}  */
	}
</script>
</head>
<body>
<!-- <h1 style="margin-left: 100px;font-family: 楷体;color: blue">译员详情</h1> -->
	<form id="form" name="form" class="form-horizontal" method="post">
	<input type="hidden" name="transId" value="${transfer.id }" id="transId">
	<input type="hidden" name="type" value="${type }" id="type">
		<section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">真实姓名</label>
				<div class="col-sm-4">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.realName }">
				</div>
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-4">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.nickname }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">证件号</label>
				<div class="col-sm-4">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.nameid }">
				</div>
				<label class="col-sm-3 control-label">联系电话</label>
				<div class="col-sm-4">
					<input readOnly="readonly" type="text" class="form-control"
						value="${transfer.tel }">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">手持证件照（正面）</label>
				<div class="col-sm-4">
					<c:forEach var="frontUrlFile" items="${frontUrlMap}">
						<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${frontUrlFile.value}&fileURL=${frontUrlFile.key}"><img src="${transVerify.frontUrl}" width="80px" height="60px"/></a>
						<input type='hidden' value="${frontUrlFile.key}" id="fileUrel">
						<input type='hidden' value="${frontUrlFile.value}" id="fileName">
					</c:forEach>
				</div>
				
				<label class="col-sm-3 control-label">手持证件照（背面）</label>
				<div class="col-sm-4">
					<c:forEach var="reverseUrlFile" items="${reverseUrlMap}">
						<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${frontUrlFile.value}&fileURL=${frontUrlFile.key}"><img src="${transVerify.reverseUrl}" width="80px" height="60px"/></a>
						<input type='hidden' value="${reverseUrlFile.key}" id="fileUrel">
						<input type='hidden' value="${reverseUrlFile.value}" id="fileName">
					</c:forEach>
				</div>
				
			</div>
			<div class="line line-dashed line-lg pull-in"></div>	
			
			<div class="form-group">
				<label class="col-sm-3 control-label">个人简历</label>
				<div class="col-sm-9">
					<center>
						<table border="0">
						<c:forEach var="jlFile" items="${jlPathMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								${jlFile.value}
								<input type='hidden' value="${jlFile.key}" id="fileUrel">
								<input type='hidden' value="${jlFile.value}" id="fileName">
								<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${jlFile.value}&fileURL=${jlFile.key}">下载</a>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">相关文件</label>
				<div class="col-sm-9">
				   <center>
						<table border="0">
						<c:forEach var="transFile" items="${transPathNameMap}">
						<tr>
							<!-- <td ondblclick="downFile(this)"> -->
							<td>
								${transFile.value}
								<input type='hidden' value="${transFile.key}" id="fileUrel">
								<input type='hidden' value="${transFile.value}" id="fileName">
								<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${transFile.value}&fileURL=${transFile.key}">下载</a>
							</td>
						</tr>
						</c:forEach>
						</table>
					</center>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" id="lastMessageDIV">
				<label class="col-sm-3 control-label">翻译资格</label>
				<table border="" style="width: 82%;table-layout:fixed" id="quoationTable">
					<tr style=";height: 40px;text-align: center;">
						<td style="width: 105px"></td><td>语言</td><td>领域</td><td>日翻译量</td><td>翻译总字数</td><td>等级</td><td class="tr-first-td">报价（千字/元）</td><td>操作</td> <!--<td>原文</td><td>译文</td> -->
					</tr>
				</table>
				<label class="col-sm-3 control-label"></label>
				<div class="col-sm-9">
				<a id="btn_add1" class="btn btn-primary marR10" style="margin-top: 25px;"> 添加</a>
			    <a id="btn_del1" class="btn btn-primary marR10" style="margin-top: 25px;" onclick="del_1();">删除</a>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" >
				
				<!-- <div class="col-sm-9" id="lastMessage" style="left:18%">
				<select name="proofDomain">
                   <option value="日常" selected="selected">日常</option>
                   <option value="机械">机械</option>
                   <option value="电子">电子</option>
                   <option value="电器">电器</option>
                   <option value="机电">机电</option>
                   <option value="自动化">自动化</option>
                   <option value="计算机">计算机</option>
                   <option value="通信">通信</option>
                   <option value="网络">网络</option>
                   <option value="物理">物理</option>
                   <option value="化学">化学</option>
                   <option value="化工">化工</option>
                   <option value="医药">医药</option>
                   <option value="生化">生化</option>
                 </select>
                 <select name="proofLanguage">
                     <option value="汉英" selected="selected">汉英</option>
                     <option value="英汉">英汉</option>
                     <option value="日汉">日汉</option>
                     <option value="汉日">汉日</option>
                     <option value="韩汉">韩汉</option>
                     <option value="汉韩">汉韩</option>
                     <option value="法汉">法汉</option>
                     <option value="汉法">汉法</option>
                     <option value="德汉">德汉</option>
                     <option value="汉德">汉德</option>
                   </select>
                   <select name="proolevels" >
                     <option value="1" selected="selected">1级</option>
                     <option value="2">2级</option>
					 <option value="3">3级</option>
					 <option value="4">4级</option>
					 <option value="5">5级</option>
					</select>
				</div>  -->
			</div>

			
			
			<!-- <div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group" id="lastMessageDIV">
				<label class="col-sm-3 control-label">评定结果</label>
				<div class="col-sm-9">
				<a id="btn_add1" class="btn btn-primary marR10" style="margin-top: 25px;"> 继续</a>
			    <a id="btn_del1" class="btn btn-primary marR10" style="margin-top: 25px;" onclick="del_1();">删除</a>
				</div>
				<div class="col-sm-9" id="lastMessage" style="left:18%">
				<select name="proofDomain">
                   <option value="日常" selected="selected">日常</option>
                   <option value="机械">机械</option>
                   <option value="电子">电子</option>
                   <option value="电器">电器</option>
                   <option value="机电">机电</option>
                   <option value="自动化">自动化</option>
                   <option value="计算机">计算机</option>
                   <option value="通信">通信</option>
                   <option value="网络">网络</option>
                   <option value="物理">物理</option>
                   <option value="化学">化学</option>
                   <option value="化工">化工</option>
                   <option value="医药">医药</option>
                   <option value="生化">生化</option>
                 </select>
                 <select name="proofLanguage">
                     <option value="汉英" selected="selected">汉英</option>
                     <option value="英汉">英汉</option>
                     <option value="日汉">日汉</option>
                     <option value="汉日">汉日</option>
                     <option value="韩汉">韩汉</option>
                     <option value="汉韩">汉韩</option>
                     <option value="法汉">法汉</option>
                     <option value="汉法">汉法</option>
                     <option value="德汉">德汉</option>
                     <option value="汉德">汉德</option>
                   </select>
                   <select name="proolevels" >
                     <option value="1" selected="selected">1级</option>
                     <option value="2">2级</option>
					 <option value="3">3级</option>
					 <option value="4">4级</option>
					 <option value="5">5级</option>
					</select>
				</div> 
			</div>-->
		</div>
			<footer class="panel-footer text-right bg-light lter">
			<!-- <button type="button" onclick="verify('yes')" id="back" class="btn btn-success btn-s-xs">审核通过</button>
			<button type="button" onclick="verify('no')" id="back" class="btn btn-success btn-s-xs">审核未通过</button> -->
			<button type="button" id="back" class="btn btn-default" onclick="closeWin()" data-dismiss="modal">关闭</button>
			</footer> 
		</section>
	</form>
	
	<script type="text/javascript">
		/* function down(urlname,fileURL){
			var url="${pageContext.request.contextPath}/clientOrder/file_download.shtml";
			alert("urlname="+urlname+"---fileURL="+fileURL)
		} */
		
		$(function(){
			//var urlname=$("#fileName").val();
	     var fileURL=$("#fileUrel").val();
	     var chan=fileURL.replace("\\","/");
	     $("#fileUrel").val(chan);
			
		})
		/*  function down(obj){
	    	  
	    	 // var formEle=inputElement.parentNode;
	    	 //  alert(inputElement+formEle);
	    	  
	    	 // formEle.action="/Aitrans/cilentCustomer/downloadTransFile.do?filename="+spanEle.innerHTML;
	    	  $.post('',"urlname="+urlname+"&fileURL="+chan);
	    	  
	   		  
	    	  //formEle.submit();
	      }
	      
	      function getNearEle(ele, type) {
	    	  type = type == 1 ? "previousSibling" : "nextSibling";
	    	  var nearEle = ele[type];
	    	  while(nearEle) {
	    	    if(nearEle.nodeType === 1) {
	    	      return nearEle;
	    	    }
	    	    nearEle = nearEle[type];
	    	    if(!nearEle) {
	    	      break;
	    	    }
	    	  }
	    	  return null;
	    	}
	       */
	</script>

</body>
</html>