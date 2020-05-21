var pageii = null;
var grid = null;
$(function() {
	var id=$("#id").val();
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "CorpusOrigin",
			name : "原文",
		}, {
			colkey : "CorpusTarget",
			name : "译文",
			isSort:true,
		},{
			colkey : "Uploader",
			name : "上传者"
		},{
			colkey : "User_UC",
			name : "用户码"
		},{
			colkey : "UploadTime",
			name : "上传时间"
		},{
			colkey : "name",
			name : "翻译领域"
		}
		 ],
		jsonUrl : rootPath + '/corpus/find_corpus_by_page.shtml?id='+id,
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
	$("#upLoad").click("click", function() {
		upload();
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
		$("#searchInput").prop("name","corpusTableFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
}
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
		content : rootPath + '/corpus/editUI_2.shtml?id=' + cbox
	});
}
function addFun() {
	var id=$("#id").val();
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/corpus/addUI_2.shtml?id='+id
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/corpus/deleteEntity.shtml';
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
function upload() {
	var domainId=$("#domainId").val();
	var corpusName=$("#corpusName").val();
	var id=$("#id").val();
	pageii = layer.open({
		title : "文件导入",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/corpus/upload1.shtml?corpusName='+corpusName+'&id='+id+'&domainId='+domainId
	});
};
function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/resources/permissions.shtml?corpusId='+cbox;
	pageii = layer.open({
		title : "分配权限",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}