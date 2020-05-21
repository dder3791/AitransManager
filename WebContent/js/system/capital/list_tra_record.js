var pageii = null;
var grid = null;
$(function() {
	//精确到两位数的减法运算
	function numSub(){
		var result=arguments[0]*100;
		var i=1;
		for(i;i<arguments.length;i++){
			result -= arguments[i]*100;
			return (result/100).toFixed(2);
		}
	}
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "id",
			name : "id",
			hide : true
		}, {
			colkey : "realName",
			name : "译员姓名",
		},  {
			colkey : "translatorFeeTotal",
			name : "译员稿酬支出",
			isSort : true,
			renderData : function(rowindex,data,rowdata,colkey){
				if(data == null || data == '')
					return "0.00";
				else
					return Number(data).toFixed(2);
			}
		}, {
			colkey : "name",
			name : "项目名称"
		},{
			colkey : "transSettlementTime",
			name : "结算时间",
			isSort : true
		},{
			name : "备注"
		} 
		 ],
		jsonUrl : rootPath + '/capital/find_tra_record_by_page.shtml',
		checkbox : true,
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
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/cooperation/look_transfer.shtml?transferId='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}