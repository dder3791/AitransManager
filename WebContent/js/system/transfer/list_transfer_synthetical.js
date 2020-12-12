var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "nickname",
			name : "昵称",
		}, 
		 {
			colkey : "realName",
			name : "姓名",
		}, 
		 {
			colkey : "domain",
			name : "领域",
		}, 
		 {
			colkey : "languages",
			name : "语言对",
		}, 
		 {
			colkey : "tranlevels",
			name : "翻译等级",
		}
		, 
		 {
			colkey : "proolevels",
			name : "翻译+校对等级",
		}
		, 
		 {
			colkey : "auditlevels",
			name : "翻译+校对+审核等级",
		}
		
		
		
		
		/*{
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
		}, {
			colkey : "degree",
			name : "学历"
		}, {
			colkey : "majorName",
			name : "专业",
		}, {
			colkey : "domain",
			name : "翻译领域"
		}, */
		 ],
		jsonUrl : rootPath + '/transfer/find_transfer_syn_by_page.shtml',
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
		delAccount();
	});
	$("#showFun").click("click", function() {
		show();
	});
	$("#listPriceFun").click("click", function() {
		listPriceFun();
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
		content : rootPath + '/quotation/toedit.shtml?id=' + cbox
	});
}
function addFun() {
	var lan = $("#language").val();
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "1000px", "100%" ],
		content : rootPath + '/transfer/addUI.shtml?lan='+lan
	});
}
function delAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/transfer/deleteEntity.shtml';
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
function show() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/transfer/show_transfer_syn.shtml?qid='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "500px", "90%" ],
		content : url
	});
}
//SQ 新增译员报价
function listPriceFun(){
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	pageii = layer.open({
		title : "报价管理",
		type : 2,
		area : [ "1000px", "100%" ],
		content : rootPath + '/transfer/listPriceUI.shtml?translatorId='+cbox
	});
}