var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "nickname",
			name : "昵称",
		}, {
			colkey : "level",
			name : "译员等级",
			isSort:true,
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="3"){
					return "三级";
				}else if(data=="4"){
					return "四级";
				}else if(data=="5"){
					return "五级";
				}
			}
		},{
			colkey : "language",
			name : "擅长语言",
			isSort:true,
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="EN"){
					return "英语";
				}else if(data=="JP"){
					return "日语";
				}else if(data=="KOR"){
					return "韩语";
				}else if(data=="GER"){
					return "德语";
				}else if(data=="FRR"){
					return "法语";
				}
			}
		}, {
			colkey : "degree",
			name : "学历"
		}, {
			colkey : "major",
			name : "专业",
			isSort:true
		}, {
			colkey : "domain",
			name : "翻译领域"
		}, {
			colkey : "pname",
			name : "参与项目"
		},{
			colkey : "description",
			name : "详情"
		} 
		 ],
		jsonUrl : rootPath + '/cooperation/find_cooperative_by_page.shtml',
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