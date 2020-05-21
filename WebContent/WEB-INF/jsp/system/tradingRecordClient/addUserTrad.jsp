<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/tradingRecordClient/add.js"></script>
<script type="text/javascript"	src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
#but button {
	margin-bottom: 5px;
	margin-right: 5px;
}
.col-sm-3 {
	width: 17%;
	float: left;
}

.col-sm-9 {
	width: 83%;
	float: left;
}

label[class^="btn btn-default"] {
	margin-top: -4px;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
	action="${pageContext.request.contextPath}/tradingRecordClient/addEntity.shtml">
	<section class="panel panel-default">
		<div class="panel-body">
		
			<div class="form-group">
				<label class="col-sm-3 control-label">类型</label>
				<div class="col-sm-9">
					<input type="radio" name="tradingRecordFormMap.type" id="type" value="充值" checked="checked">充值
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="tradingRecordFormMap.type" id="type" value="提现">提现
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
		
			<div class="form-group">
				<label class="col-sm-3 control-label">收款人</label>
				
				<div class="col-sm-9" id="radioSelect">
				   <!--  <input type="radio" name="typePayee" value="译员" style="width: 5%" onchange="fuindPayee(this)">译员
				    <input type="radio" name="typePayee" value="客户" style="width: 5%" onchange="fuindPayee(this)">客户 -->
					<input type="text" class="form-control" name="tradingRecordFormMap.payee" id="makeupCo" onfocus="setfocus(this)" oninput="setinput(this);" placeholder="请选择或输入" style="width: 57%;right: 0px;float: left;">
				    <div></div>
				    <select name="makeupCoSe" id="typenum" onchange="changeF(this)" size="10" style="display:none">  
				        <option value="环宇爱译" selected="selected">环宇爱译</option>  
				        <c:forEach items="${clientUserList }" var="clientuser">
				        <option value="${clientuser.bankAccount }">${clientuser.bankAccount }</option>
				        </c:forEach>
				        <c:forEach items="${transList }" var="trans">
				        <option value="${trans.accountNumber }">${trans.accountNumber}</option>
				        </c:forEach>
				        
				    </select>  
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">收款方式</label>
				<div class="col-sm-9">
					<select name="tradingRecordFormMap.payeeModel">
						<option value="微信">微信</option>
						<option value="支付宝">支付宝</option>
						<option value="刷卡">刷卡</option>
						<option value="现金" selected="selected">现金</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">付款人</label>
				<div class="col-sm-9">
					<!-- <input type="radio" name="typePayer" value="译员" style="width: 5%" onchange="fuindPayee(this)">译员
				    <input type="radio" name="typePayer" value="客户" style="width: 5%" onchange="fuindPayee(this)">客户 -->
					<input type="text" name="tradingRecordFormMap.payer" id="makeupCo2" class="form-control" onfocus="setfocus(this)" oninput="setinput(this);" placeholder="请选择或输入" style="width: 57%;right: 0px;float: left;"/>  
				    <div></div>
				    <select name="makeupCoSe" id="typenum2" onchange="changeF(this)" size="10" style="display:none;">  
				        <option value="环宇爱译" selected="selected">环宇爱译</option>  
				        <c:forEach items="${clientUserList }" var="clientuser">
				        <option value="${clientuser.bankAccount }">${clientuser.bankAccount }</option>
				        </c:forEach>
				        <c:forEach items="${transList }" var="trans">
				        <option value="${trans.accountNumber }">${trans.accountNumber}</option>
				        </c:forEach>
				    </select>
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">付款方式</label>
				<div class="col-sm-9">
					<select name="tradingRecordFormMap.payModel">
						<option value="微信">微信</option>
						<option value="支付宝">支付宝</option>
						<option value="刷卡">刷卡</option>
						<option value="现金" selected="selected">现金</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">支付金额</label>
				<div class="col-sm-9">
					<input type="text" class="form-control money" name="tradingRecordFormMap.payMoney" id="payMoney">
				</div>  
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">付款时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" name="tradingRecordFormMap.payTime" id="tel" readonly="readonly"
						onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">支付状态</label>
				<div class="col-sm-9">
					<select name="tradingRecordFormMap.payState">
						<option value="支付成功" selected="selected">支付成功</option>
						<option value="等待支付">等待支付</option>
						<option value="支付失败">支付失败</option>
						<option value="退款成功">退款成功</option>
						<option value="等待退款">等待退款</option>
						<option value="退款失败">退款失败</option>
					</select>
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">交易描述</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="请输入传真号码" name="tradingRecordFormMap.intro" id="fax">
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
/* function fuindPayee(obj){
	//判断查找数据类型
	var value=obj.value;
	$.post("${pageContext.request.contextPath}/tradingRecordClient/findAlltypePay.shtml","typePay="+value,function(data){
		//判断收款人或付款人
		var name=obj.name;
		var select;
		if("typePayer"==name){
			select=$("#typenum2")
		}else{
			select=$("#typenum1")
		}
		
		select.html("");
		if("译员"==value){
			for(i=0;i<data.length;i++){
		        var option = $("<option value='"+data[i].accountNumber+"'></option>");
		        select.append(option);
		    }
		}else{
			for(i=0;i<data.length;i++){
		        var option = $("<option value='"+data[i].bankAccount+"'></option>");
		        select.append(option);
		    }
		}
		
		
		
	},'json');
} */



var TempArr=[];//存储option
var TempArrValue=[];
$(function(){
    /*先将数据存入数组*/
    $("#typenum option").each(function(index, el) {
        TempArr[index] = $(this).text();
        TempArrValue[index]=$(this).val();
    });
    $(document).bind('click', function(e) {  
        var e = e || window.event; //浏览器兼容性   
        var elem = e.target || e.srcElement;  
        while (elem) { //循环判断至跟节点，防止点击的是div子元素   
            if (elem.id && (elem.id == 'typenum' || elem.id == "makeupCo" || elem.id == 'typenum2' || elem.id == "makeupCo2")) {  
                return;  
            }
            $('#typenum').css('display', 'none'); //点击的不是div或其子元素   
            $('#typenum2').css('display', 'none');
            elem = elem.parentNode;  
        }  
       
    });  
})

function changeF(this_) {
	if(this_.id=='typenum'){
		$("#makeupCo").val($(this_).find("option:selected").val());
		$("#typenum").css({"display":"none"});
	}else{
		$("#makeupCo2").val($(this_).find("option:selected").val());
		$("#typenum2").css({"display":"none"});
	}
   
}
function setfocus(this_){
	var select;
	if(this_.id=='makeupCo'){
		$("#typenum").css({"display":""});
	    select = $("#typenum");
	   
	}else{
		$("#typenum2").css({"display":""});
	    select = $("#typenum2");
	}
	select.html("");
    for(i=0;i<TempArr.length;i++){
        var option = $("<option value='"+TempArrValue[i]+"'></option>").text(TempArr[i]);
        select.append(option);
    } 
   
}

function setinput(this_){
	var select;
	if(this_.id=='makeupCo'){
		select= $("#typenum");
	    
	}else{
		select= $("#typenum2");
	}
	
	select.html("");
    for(i=0;i<TempArr.length;i++){
        //若找到以txt的内容开头的，添option
        if(TempArr[i].substring(0,this_.value.length).indexOf(this_.value)==0){
            var option = $("<option value='"+TempArrValue[i]+"'></option>").text(TempArr[i]);
            select.append(option);
        }
    }
}
  </script> 
</html>