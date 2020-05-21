<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/reward/edit_reward.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	function lengthChange() {

	 	var wordLength = document.getElementById("rewardFormMap.wordLength").value;
		var imageLength = document.getElementById("rewardFormMap.imageLangth").value;
		var companyreward = document.getElementById("rewardInfo.companyReward").value;
		var transreward = document.getElementById("rewardInfo.transReward").value;
		var auditoryreward = document.getElementById("rewardInfo.auditoryReward").value;
		var verifyreward = document.getElementById("rewardInfo.verifyReward").value;
		
		var newLength=accAdd(wordLength,imageLength);
		var companytotal = newLength * companyreward/1000;
		var transtotal = newLength * transreward/1000;
		var auditorytotal = newLength * auditoryreward/1000;
		var verifytotal = newLength * verifyreward/1000;
 		alert(companytotal+"-------------"+transtotal);
		document.getElementById("rewardInfo.companyTotal").value = companytotal;
		document.getElementById("rewardInfo.translatorFeeTotal").value = transtotal;
		document.getElementById("rewardInfo.auditoryTotal").value = auditorytotal;
		document.getElementById("rewardInfo.verifyTotal").value = verifytotal;
 
	}
	
	function closeWin(){
		layer.confirm('是否关闭窗口？', {icon: 3,offset: '-100px'}, function(index) {
	    	parent.layer.close(parent.pageii);
	    	return false;
		});
	}
	
	//加法计算总字数
	function accAdd(arg1, arg2) {
	    var r1, r2, m, c;
	    try {
	        r1 = arg1.toString().split(".")[1].length;
	    }
	    catch (e) {
	        r1 = 0;
	    }
	    try {
	        r2 = arg2.toString().split(".")[1].length;
	    }
	    catch (e) {
	        r2 = 0;
	    }
	    c = Math.abs(r1 - r2);
	    m = Math.pow(10, Math.max(r1, r2));
	    if (c > 0) {
	        var cm = Math.pow(10, c);
	        if (r1 > r2) {
	            arg1 = Number(arg1.toString().replace(".", ""));
	            arg2 = Number(arg2.toString().replace(".", "")) * cm;
	        } else {
	            arg1 = Number(arg1.toString().replace(".", "")) * cm;
	            arg2 = Number(arg2.toString().replace(".", ""));
	        }
	    } else {
	        arg1 = Number(arg1.toString().replace(".", ""));
	        arg2 = Number(arg2.toString().replace(".", ""));
	    }
	    return (arg1 + arg2) / m;
	}
</script>
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
</head>
<body>
	<h2>账单详情</h2>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post"
		action="${pageContext.request.contextPath}/reward/editEntity.shtml">
		<section class="panel panel-default">
		<div class="panel-body">

			<div class="form-group">
				<label class="col-sm-3 control-label">项目案号</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" value="${rewardInfo.reference}---${rewardInfo.customerReference}" readonly="readonly" >
					<input type="hidden" readonly="readonly" class="form-control"	name="rewardFormMap.id" id="rewardInfo.id" value="${rewardInfo.id}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>

			<div class="form-group">
				<label class="col-sm-3 control-label">文字数量<br>单位:字
				</label>
				<div class="col-sm-9">
					<input type="text" class="form-control rewardlength" name="rewardFormMap.wordLength"
						id="rewardFormMap.wordLength" value="${rewardInfo.wordLength}" onblur="lengthChange()">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">图片文字<br>单位:字
				</label>
				<div class="col-sm-9">
					<input type="text" class="form-control rewardlength" name="rewardFormMap.imageLangth"
						id="rewardFormMap.imageLangth" value="${rewardInfo.imageLength}" onblur="lengthChange()">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			公司明细
			<div class="line line-dashed line-lg pull-in"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label">公司稿酬<br>单位:元/千字
					</label>
					<div class="col-sm-9">
						<input type="text" class="form-control money" name="rewardFormMap.priceKilo"
							id="rewardInfo.companyReward" value="${rewardInfo.priceKilo}"
							onblur="lengthChange()">
					</div>
				</div>
				<div class="line line-dashed line-lg pull-in"></div>


				<div class="form-group">
					<label class="col-sm-3 control-label">公司总稿酬<br>单位:元
					</label>
					<div class="col-sm-9">
						<input type="text" readonly="readonly" class="form-control"
							name="rewardFormMap.companyTotal" id="rewardInfo.priceTotal" value="${rewardInfo.priceTotal}">
					</div>
				</div>
				<div class="line line-dashed line-lg pull-in"></div>


				<div class="form-group">
					<label class="col-sm-3 control-label">结算时间</label>
					<div class="col-sm-9">
						<input type="text" class="form-control"
							name="rewardFormMap.companySettlementTime" readonly="readonly"
							id="rewardInfo.companySettlementTime"
							value="${rewardInfo.companySettlementTime}" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
					</div>
				</div>

			
			<div class="line line-dashed line-lg pull-in"></div>
				译员明细
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
			<label class="col-sm-3 control-label">译员稿酬<br>单位:元/千字
			</label>
			<div class="col-sm-9">
				<input type="text" class="form-control money" name="rewardFormMap.translatorFeeKilo"
					id="rewardInfo.transReward" value="${rewardInfo.translatorFeeKilo}"
					onblur="lengthChange()">
			</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">译员总稿酬<br>单位:元
				</label>
				<div class="col-sm-9">
					<input type="text" readonly="readonly" class="form-control"
						name="rewardFormMap.translatorFeeTotal" id="rewardInfo.translatorFeeTotal" value="${rewardInfo.translatorFeeTotal}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">结算时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						name="rewardFormMap.transSettlementTime" readonly="readonly"
						id="rewardInfo.transSettlementTime"
						value="${rewardInfo.transSettlementTime}" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			校对明细
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
			<label class="col-sm-3 control-label">校对稿酬<br>单位:元/千字
			</label>
			<div class="col-sm-9">
				<input type="text" class="form-control money" name="rewardFormMap.proofFeeKilo"
					id="rewardInfo.verifyReward" value="${rewardInfo.proofFeeKilo}"
					onblur="lengthChange()">
			</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">校对总稿酬<br>单位:元
				</label>
				<div class="col-sm-9">
					<input type="text" readonly="readonly" class="form-control"
						name="rewardFormMap.proofFeeTotal" id="rewardInfo.verifyTotal"
						value="${rewardInfo.proofFeeTotal}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">结算时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						name="rewardFormMap.proofSettlementTime" readonly="readonly"
						id="rewardInfo.verifySettlementTime"
						value="${rewardInfo.proofSettlementTime}" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			
			审核明细
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
			<label class="col-sm-3 control-label">审核稿酬<br>单位:元/千字
			</label>
			<div class="col-sm-9">
				<input type="text" class="form-control money" name="rewardFormMap.auditoryFeeKilo"
					id="rewardInfo.auditoryReward" value="${rewardInfo.auditoryFeeKilo}"
					onblur="lengthChange()">
			</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">审核总稿酬<br>单位:元
				</label>
				<div class="col-sm-9">
					<input type="text" readonly="readonly" class="form-control"
						name="rewardFormMap.auditoryFeeTotal" id="rewardInfo.auditoryTotal"
						value="${rewardInfo.auditoryFeeTotal}">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>


			<div class="form-group">
				<label class="col-sm-3 control-label">结算时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						name="rewardFormMap.auditorySettlementTime"
						id="rewardInfo.auditorySettlementTime" readonly="readonly"
						value="${rewardInfo.auditorySettlementTime}" onfocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
		</div>
		
		<footer class="panel-footer text-right bg-light lter">
			<button type="submit" class="btn btn-success btn-s-xs">提交</button>
			<a href="#" class="btn btn-s-md btn-info btn-rounded" onclick="closeWin()">关闭</a>
		</footer> 
		</section>
	</form>
</body>
</html>