var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
        {
			colkey : "nameCH",
			name : "名称（中文）",
		 },{
			colkey : "countryCH",
			name : "国家（中文）",
		 },{
			colkey : "introCH",
			name : "简介（中文）",
		 },{
			colkey : "web",
			name : "官网",
		 },{
			colkey : "nameEN",
			name : "名字（英文）",
		 },{
			colkey : "countryEN",
			name : "国家（英文）",
		 },{
			colkey : "introEN",
			name : "简介（英文）",
		 },{
			colkey : "type",
			name : "是否固定",
			renderData:function(rowindex,data,rowdata,column){
				if(data=='1' || data==1){
					return "固定";
				}else{
					return "随机";
				}
			}
		 },{
			colkey : "isuseful",
			name : "是否可用",
			renderData:function(rowindex,data,rowdata,column){
				if(data=='1' || data==1){
					return "可用";
				}else{
					return "不可用";
				}
			}
	 },
		 ],
		jsonUrl : rootPath + '/advertisement/list_advertisemenList_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#type").click("click", function() {// 绑定type按扭
		search();	
	});
	$("#isUserful").click("click", function() {// 绑定isuseful按扭
		search();	
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var keyValue=document.getElementById("selectSearch").value;
		if(keyValue!=null && keyValue!=''){
			$("#searchInput").prop("name","advertiseListFormMap."+keyValue);
			var searchParams = $("#searchForm").serializeJson();// 初始化传参数
			grid.setOptions({
				data : searchParams
			});	
		}else if(keyValue==null ||keyValue==''){
			alert("请选择关键字种类！！");
		}
	});
	$("#editFun").click("click", function(serNumber) {
		editFun();
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
	$("#addFun").click("click", function() {
		addFun();
	});
});


function search(){
	
	/*var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","advertiseListFormMap."+keyValue);
	}*/
	var searchParams = $("#searchForm").serializeJson();// 初始化传参数
	grid.setOptions({
		data : searchParams
	});
}
function editFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/advertisement/edit_advertisemenListUI.shtml?id=' +cbox
	});
};

function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/advertisement/add_advertisemenListUI.shtml'
	});
}


function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/advertisement/delete_advertisemenList.shtml';
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
};

function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/advertisement/look_advertisementUI.shtml?id='+cbox;
	$("#loadhtml").load(url);
}
