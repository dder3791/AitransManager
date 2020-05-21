var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
		 {
			colkey : "reference",
			name : "订单案号",
		 },{
			colkey : "nickName",
			name : "译员",
		 },{
			colkey : "publishModel",
			name : "发布模式",
		 },{
		colkey : "completeTime",
		name : "返稿时间",
		 },{
		colkey : "taskState",
		name : "状态",
		renderData : function(rowindex, data, rowdata, column) {
			if(data=="1"){
				return "已接单"
			}else if(data=="2"){
				return "正在翻译";
			}else if(data=="3"){
				return "正在校对";
			}else if(data=="4"){
				return "正在审核";
			}else if(data=="5"){
				return "已返稿";
			}else if(data=="6"){
				return "等待签收";
			}else if(data=="7"){
				return "已评价";
			}else if(data=="8"){
				return "订单完成";
			}else if(data=="9"){
				return "已取消";
			}else if(data=="10"){
				return "<a onclick='active("+rowdata.id+")'>查看拒签</a>";
			}
		   }
		 }
		 ],
		jsonUrl : rootPath + '/clientOrder/find_clientOrder_By_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#addFun").click("click",function(){
		addFun();
	});
	$("#editFun").click("click", function(serNumber) {
		editFun(serNumber);
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
	$("#state").click("click",function(){
		state();
	})
});
function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","orderFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
}
/*function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/evaluate/addUI_1.shtml'
	});
};*/
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
		content : rootPath + '/clientOrder/editOrderUI.shtml?id=' + cbox 
	});
};
/*function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/evaluate/deleteEntity.shtml';
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
*/
function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "800px", "90%" ],
		content : rootPath + '/clientOrder/lookOrder.shtml?id='+cbox+'&type=look'
	});
	
	//$("#loadhtml").load(url);
}


function active(id){
	pageii = layer.open({
		title : "处理拒签",
		type : 2,
		area : [ "1000px", "85%" ],
		content : rootPath + '/clientOrder/lookOrder.shtml?id='+id+'&type=active'
	});
}


function state(){
		$("#searchInput").val("拒签");
		$("#searchInput").prop("name","orderFormMap.testState");
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	
}
