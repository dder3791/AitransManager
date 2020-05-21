var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "title",
			name : "资讯标题",
			
		},
		{
			colkey : "context",
			name : "资讯内容"
		},
		{
			colkey : "url",
			name : "图片",
			renderData : function(rowindex,data,rowdata,column){
				return "<img src='http://www.aitrans.org:8081/"+rowdata.url+"' width='50' height='50'>"
			}
		},
		{
			colkey : "auther",
			name : "发布人",
		},
		{
			colkey : "issueDate",
			name : "发布日期",
			isSort:true,
		},
		{
			colkey : "type",
			name : "资讯类型"
		},
		{
			colkey : "elite",
			name : "是否精品",
			renderData : function(rowindex,data,rowdata,column){
				if(data==0){
					return "否"
				}else{
					return "是"
				}
				
			}
		},
		{
			colkey : "hot",
			name : "是否热门",
			renderData : function(rowindex,data,rowdata,column){
				if(data==0){
					return "否"
				}else{
					return "是"
				}
				
			}
		}
		 ],
		jsonUrl : rootPath + '/article/find_article_by_page.shtml',
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
	$("#hot").click("click", function() {
		hot();
	});
	$("#elite").click("click", function() {
		elite();
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
		content : rootPath + '/article/editUI.shtml?&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/article/addUI.shtml'
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/article/deleteEntity.shtml';
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
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/article/look_article.shtml?id='+cbox
	});
}

function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","articleFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
}

function hot(){
	var searchParams = $("#hotForm").serializeJson();// 初始化传参数
	grid.setOptions({
		data : searchParams
	});
}

function elite(){
	var searchParams = $("#eliteForm").serializeJson();// 初始化传参数
	grid.setOptions({
		data : searchParams
	});	
	//alert("elite");
}