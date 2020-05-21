var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
		 {
			colkey : "id",
			name : "转账单号",
		 },{
			colkey : "payMoney",
			name : "金额",
		 },{
			colkey : "payModel",
			name : "支付方式",
		 },{
			colkey : "payTime",
			name : "支付时间",
		 },{
			colkey : "payee",
			name : "收款人",
		 },{
			colkey : "payeeModel",
			name : "收款方式",
		 }
		 ],
		jsonUrl : rootPath + '/tradingRecordClient/find_TradingRecord_By_page.shtml?type=trans',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#addFun").click("click",function(){
		addFun();
	});
	$("#editFun").click("click", function(serNumber) {
		editFun(serNumber);
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
});
function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","appealClientFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/evaluate/addUI_1.shtml'
	});
};
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
		content : rootPath + '/evaluate/editUI_1.shtml?id=' + cbox 
	});
};
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/evaluate/deleteEntity.shtml';
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
};

function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/evaluate/look_evaluate.shtml?id='+cbox;
	$("#loadhtml").load(url);
}


/*function active(id){
	pageii = layer.open({
		title : "处理申诉",
		type : 2,
		area : [ "1000px", "85%" ],
		content : rootPath + '/appealClient/appealAction.shtml?aid='+id+'&type=appealClient'
	});
}*/