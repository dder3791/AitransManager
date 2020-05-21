var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "languagePair",
			name : "翻译类型"
		}, {
			colkey : "corpusName",
			name : "语料名称",
			isSort:true,
		},{
			colkey : "isProofread",
			name : "翻译方式",
			renderData:function(rowindex,data,rowdata,column){
				if(data=="0"){
					return "只翻译";
				}
				if(data=="1"){
					return "翻译和校对";
				}
				if(data=="2"){
					return "翻译、校对及审核";
				}
			}
		},{
			colkey : "dname",
			name : "所属领域",
		},{
			colkey : "pname",
			name : "校对员",
		},{
			colkey : "aname",
			name : "审核员",
		},{
			colkey : "tname",
			name : "译员",
		},{
			colkey : "remark",
			name : "备注"
		}
		 ],
		jsonUrl : rootPath + '/corpus/find_corpus_first_by_page.shtml',
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
		content : rootPath + '/corpus/editUI_1.shtml?id=' + cbox
	});
};
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/corpus/addUI_1.shtml'
	});
};
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/corpus/deleteEntitys.shtml';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "json");
		if (s == "success") {
			layer.msg('删除成功');
			grid.loadData();
		} else {
			layer.msg('请确定有无信息后再删除');
		}
	});
};
function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/corpus/look_corpus.shtml?corpusId='+cbox;
	$("#loadhtml").load(url);
}