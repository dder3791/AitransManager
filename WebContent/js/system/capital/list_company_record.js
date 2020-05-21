var pageii = null;
var grid = null;
$(function() {
	//加法,精确到小数点两位数
	function numAdd(num1,num2){
		var baseNum,baseNum1,baseNum2;
		try{
			baseNum1=num1.toString().split('.')[1].length;
		}catch(e){
			baseNum1=0;
		}
		try{
			baseNum2=num2.toString().split('.')[1].length;
		}catch(e){
			baseNum2=0;
		}
		baseNum= Math.pow(10,(Math.max(baseNum1,baseNum2)));
		/*//精度
		var precision=(baseNum1>baseNum2)?baseNum1:baseNum2;*/
		return ((num1*baseNum+num2*baseNum)/baseNum).toFixed(2);
	};
	//减法
	function numSub(num1,num2){
		var baseNum,baseNum1,baseNum2;
		try{
			baseNum1=num1.toString().split('.')[1].length;
		}catch(e){
			baseNum1=0;
		}
		try{
			baseNum2=num2.toString().split('.')[1].length;
		}catch(e){
			baseNum2=0;
		}
		baseNum=Math.pow(10,(Math.max(baseNum1,baseNum2)));
		/*var precision=(baseNum1>baseNum2)?baseNum1:baseNum2;*/
		return ((num1*baseNum - num2*baseNum)/baseNum).toFixed(2);
	};
	//乘法
	function numMulti(num1,num2){
		var baseNum=0;
		try{
			baseNum+=num1.toString().split('.')[1].length;
		}catch(e){
		}
		try{
			baseNum+=num2.toString().split('.')[1].length;
		}catch(e){
		}
		return (Number(num1.toString().replace('.',''))
		*Number(num2.toString().replace('.',''))/Math.pow(10,baseNum)).toFixed(2);
	};
	//除法,精度为小数点后两位
	function numDiv(num1,num2){
		var baseNum1,baseNum2,baseNum3,baseNum4;
		try{
			baseNum1=num1.toString().split('.')[1].length;
		}catch(e){
			baseNum1=0;
		}
		try{
			baseNum2=num2.toString().split('.')[1].length;
		}catch(e){
			baseNum2=0;
		}
		baseNum3=Number(num1.toString().replace('.',''));
		baseNum4=Number(num2.toString().replace('.',''));
		var precision=Math.pow(10,(baseNum2-baseNum1));
		return (baseNum3/baseNum4*precision).toFixed(2);
	};
	
	//多个参数的减法
	function numSub1() {
		var result = arguments[0]*100;
		var i = 1;
		for (i; i < arguments.length; i++) {// arguments表示传入的参数
			result -= arguments[i]*100;
		}
		return (result/100).toFixed(2);
	};
	
	/*//改进后的减法
	//1.新建排序函数
	function sortby(a,b){
		return a-b;
	}
	//2.减法
	function numSub2(){
		//新建数组,保存参数小数点后数字的长度
		var baseNum = new Array(arguments.length);
		
		var result = 0;
		var maxPre = 0;
		var precision = 0;
		int i = 0;
		for(i;i<arguments.length;i++){
			baseNum.push(arguments[i].toString().split('.')[1].length);
			maxPre = baseNum.sort(sortby)[arguments.length];
			precision = baseNum[maxPre];
		}
		int j=1;
		result = arguments[0]*Math.pow(10,precision);
		for(j;j<arguments.length;j++){
			result -= arguments[i]*Math.pow(10,precision);
		}
		return (result/Math.pow(10,precision)).toFixed(2);
	}*/
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "reward_id",
			name : "稿酬id",
			isSort:true,
			
		}, {
			colkey : "tra_id",
			name : "译员id",
		}, {
			colkey : "priceTotal",
			name : "项目总价",
			isSort : true,
			renderData : function(rowindex,data,rowdata,colkey){
				if(data == null || data == ''){
					return '0.00';
				}else
				return Number(data).toFixed(2);
			}
		},{
			colkey : "nameZH",
			name : "付款方",
		}, {
			colkey : "name",
			name : "收入来源"
		}, {
			colkey : "translatorFeeTotal",
			name : "译员稿酬支出",
			renderData : function(rowindex,data,rowdata,colkey){
				if(data == null || data == ''){
					return '0.00';
				}else
				return Number(data).toFixed(2);
			}
		}, {
			colkey : "proofFeeTotal",
			name : "校对员稿酬支出",
			renderData : function(rowindex,data,rowdata,colkey){
				if(data == null || data == ''){
					return '0.00';
				}else
				return Number(data).toFixed(2);
			}
		}, {
			colkey : "auditoryFeeTotal",
			name : "审核员稿酬支出",
			renderData : function(rowindex,data,rowdata,colkey){
				if(data == null || data == ''){
					return '0.00';
				}else
				return Number(data).toFixed(2);
			}
		},{
			name : "最终收益",
			renderData : function(rowindex,data,rowdata,column){
				if(rowdata.priceTotal==null || rowdata.priceTotal=='')
					rowdata.priceTotal=0;
				if(rowdata.translatorFeeTotal==null || rowdata.translatorFeeTotal == '')
					rowdata.translatorFeeTotal=0;
				if(rowdata.proofFeeTotal==null || rowdata.proofFeeTotal=='')
					rowdata.proofFeeTotal=0;
				if(rowdata.auditoryFeeTotal==null || rowdata.auditoryFeeTotal=='')
					rowdata.auditoryFeeTotal=0;
				/*var tra_proof = numAdd(rowdata.translatorFeeTotal,rowdata.proofFeeTotal);
				var tra_pro_aud = numAdd(tra_proof,rowdata.auditoryFeeTotal);
				return numSub(rowdata.priceTotal,tra_pro_aud);*/
				return numSub1(rowdata.priceTotal,rowdata.translatorFeeTotal,rowdata.proofFeeTotal,rowdata.auditoryFeeTotal);
			},
		},{
			colkey : "completeTime",
			name : "结算时间",
			isSort : true
		} 
		 ],
		jsonUrl : rootPath + '/capital/find_company_record_by_page.shtml',
		checkbox : true,
		checkValue : "reward_id",
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addFun").click("click", function() {
		addFun();
	});
	$("#editFun").click("click", function() {
		editFun();
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
});
function editFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/cooperation/editUI.shtml?type=participants&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/cooperation/addUI.shtml?type=participants'
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/cooperation/deleteEntity.shtml?type=participants';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "json");
		if (s == "success") {
			layer.msg('删除成功');
			grid.loadData();
		} else {
			layer.msg('删除失败');
		}
	});
}
function look() {
	var row = grid.selectRow();
	var cbox = grid.getSelectedCheckbox();
	alert(cbox+row);
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/capital/look_company_record.shtml?id='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}