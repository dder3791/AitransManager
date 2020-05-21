var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "nameZH",
			name : "客户名称",
		}, {
			colkey : "address",
			name : "客户所在地",
		}, {
			colkey : "head",
			name : "项目经理",
		}, {
			colkey : "tel",
			name : "联系电话",
		},  {
			colkey : "description",
			name : "详细描述"
		}
		 ],
		jsonUrl : rootPath + '/project/find_customer_project_by_page.shtml',
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
		content : rootPath + '/project/editUI.shtml?type=customer&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/project/addUI.shtml?type=customer'
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/project/deleteEntity.shtml?type=customer';
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
	//2017年4月20日15:09:58 将customerId改为id
	var url = rootPath + '/project/look_project.shtml?type=customer&id='+cbox;
	//2017年4月20日13:56:37 添加资源id
	//var url = rootPath + '/project/look_project.shtml?type=customer&id=129&customerId='+cbox;
	//var url = rootPath + '/project/look_project.shtml?type=customer&customerId='+cbox;
	var tb = $("#loadhtml");
	tb.load(url);
	/*pageii = layer.open({
		title : "查看客户项目",
		type : 2,
		area : [ "700px", "90%" ],
		content : url
	});*/
	
}