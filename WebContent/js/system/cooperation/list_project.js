var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			width : "40px",
			hide : false
		},{
			colkey : "nameZH",
			name : "客户中文名称",
		}, {
			colkey : "p_name",
			name : "项目名称",
		}, {
			colkey : "address",
			name : "客户所在地",
		}, {
			colkey : "language",
			name : "项目语言",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="EN"){
					return "英语";
				}else if(data=="JP"){
					return "日语";
				}else if(data=="KOR"){
					return "韩语";
				}else if(data=="GER"){
					return "德语";
				}else if(data=="FR"){
					return "法语";
				}else{
					return "";
				}
					
			}
		},{
			colkey : "tname",
			name : "参与译员"
		},{
			colkey : "id",
			name : "Test",
			renderData : function(rowindex,data,rowdata,colkey){
				return rowdata;
			}
		}
		 ],
		jsonUrl : rootPath + '/cooperation/find_project_by_page.shtml',
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
		content : rootPath + '/cooperation/editUI.shtml?type=project&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/cooperation/addUI.shtml?type=project'
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/cooperation/deleteEntity.shtml?type=project';
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
	var url = rootPath + '/cooperation/look_project.shtml?projectId='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}