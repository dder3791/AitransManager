var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "nameEN",
			name : "公司英文名称",
		}, {
			colkey : "address",
			name : "公司地址",
		}, {
			colkey : "cel",
			name : "公司电话"
		},{
			colkey : "tel",
			name : "客户电话",
		}, {
			colkey : "email",
			name : "公司邮箱",
		}, {
			colkey : "fax",
			name : "传真号码"
		},{
			colkey : "head",
			name : "公司负责人"
		}, {
			colkey : "quotedPrice",
			name : "客户报价"
		},{
			colkey : "historicalQuotedPrice",
			name : "客户历史报价"
		},{
			colkey : "description",
			name : "详细描述"
		}
		 ],
		jsonUrl : rootPath + '/customer/find_foreign_by_page.shtml',
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
		content : rootPath + '/customer/editUI.shtml?id=' + cbox
	});
}
function addFun() {
	var type = $("#type").val();
	pageii = layer.open({
		title : "新增国内客户",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/customer/addUI.shtml?type='+type
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/customer/deleteEntity.shtml';
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
	var url = rootPath + '/customer/look_customer.shtml?customerId='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}
