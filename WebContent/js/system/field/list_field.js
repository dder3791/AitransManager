var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
        {
			colkey : "field",
			name : "项目领域",
		 },{
			colkey : "factor",
			name : "价格系数",
		 }
		 ],
		jsonUrl : rootPath + '/domain/findBy_field_page.shtml',
		checkbox : true,
		serNumber : true
	});
	
	$("#search").click("click", function() {// 绑定查询按扭
		search();	
	});
	
	$("#editFun").click("click", function(serNumber) {
		editFun();
	});
	$("#addFun").click("click", function() {
		addFun();
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
		area : [ "600px", "40%" ],
		content : rootPath + '/domain/edit_field_UI.shtml?&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "40%" ],
		content : rootPath + '/domain/add_field_UI.shtml'
	});
}

function search(){
	var searchParams = $("#searchForm").serializeJson();// 初始化传参数
	grid.setOptions({
		data : searchParams
	});	
}