var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "nickname",
			name : "昵称",
			renderData : function(rowindex,data,rowdata,column){
				var nickname="";
				if(rowdata.nickname==null || rowdata.nickname==''){
					nickname="暂无昵称";
				}
				else{
					nickname=rowdata.nickname;
				}
				return "<a href='javascript:void(0);' title='"+rowdata.accountName+"' onclick='editFun("+rowdata.id+",this)'>"+nickname+"</a>";
			}
		}, {
			colkey : "accountName",
			name : "账号",
			isSort:true,
		}, {
			colkey : "tel",
			name : "手机号"
		}, {
			colkey : "address",
			name : "地址",
		}, {
			colkey : "email",
			name : "邮箱"
		},{
			colkey : "registerTime",
			name : "注册时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			colkey : "isAudit",
			name : "操作",
			renderData : function(rowindex,data,rowdata,column){
				/*if(data== "0" || data== ""){
					return "暂无操作";
				}else if(data== "2"){
					return "已通过审核";
				}else if(data== "3"){
					return "未通过审核";
				}else if(data== "1"){*/
					return "<a href='javascript:active("+rowdata.id+")'>审核信息</a>";
				/*}*/
				
			}
		}
		 ],
		jsonUrl : rootPath + '/transfer/find_auditory_by_page.shtml',
		/*checkbox : true,
		serNumber : true*/
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();
		/*var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});*/
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
	$("#look").click("click", function() {
		look();
	});
});
function editFun(num,name) {
	
	pageii = layer.open({
		title : "编辑&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+name.title,
		type : 2,
		area : [ "900px", "83%" ],
		content : rootPath + '/transfer/editClientTransUI.shtml?id=' + num
	});
/*function editFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/transfer/editClientTransUI.shtml?id=' + cbox
	});*/
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "1000px", "100%" ],
		content : rootPath + '/transfer/addClientTransUI.shtml?type="审核员"'
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
function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/transfer/look_transfer.shtml?translatorId='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "500px", "90%" ],
		content : url
	});
}

function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","translatorFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		layer.msg("请选择关键字种类！");
	}
}



function active(id){
	pageii = layer.open({
		title : "核实信息",
		type : 2,
		area : [ "1000px", "85%" ],
		content : rootPath + '/transfer/proofreaderUI.shtml?id='+id+'&type=审核员'
	});
}