var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "stateName",
			name : "项目状态",
		},
		{
			colkey : "description",
			name : "描述"
		}
		 ],
		jsonUrl : rootPath + '/projectState/find_projectState_by_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();	
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
		content : rootPath + '/projectState/editUI.shtml?&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/projectState/addUI.shtml'
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/projectState/deleteEntity.shtml';
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
	var url = rootPath + '/project/look_project.shtml?type=projectState&id='+cbox;
	//var url = rootPath + '/project/look_project.shtml?type=projectType&projectTypeId='+cbox;
	var tb = $("#loadhtml");
	tb.load(url);
	/*pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/projectState/look_projectState.shtml?id='+cbox
	});*/
}

function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","projectStateFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
}
