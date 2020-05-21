var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "userName",
			name : "用户名称",
		}, {
			colkey : "realName",
			name : "真实姓名",
		}, {
			colkey : "sex",
			name : "性别",
		}, {
			colkey : "birthday",
			name : "出身日期"
		},{
			colkey : "tel",
			name : "联系电话",
		}, {
			colkey : "emaile",
			name : "用户邮箱",
		}, {
			colkey : "serviceLanguage",
			name : "服务语言",
			renderData : function(rowindex,data,rowdata,column){
				if(data=="EN"){
					return "英语";
				}else if(data=="ZH"){
					return "汉语";
				}
			}	
		},{
			colkey : "cardId",
			name : "证件号码"
		}, {
			colkey : "company",
			name : "工作单位"
		},{
			colkey : "intro",
			name : "详细描述"
		},{
			colkey :"certificationState",
			name : "操作",
			renderData : function(rowindex,data,rowdata,column){
				if(data=="0"){
					return "未认证";
				}else if(data=="1"){
					return "<a href='javascript:active("+rowdata.id+")'>等待审核</a>"
				}else if(data=="2"){
					return "认证完毕"
				}else if(data=="3"){
					return "审核不通过"
				}
				
			}
		}
		 ],
		jsonUrl : rootPath + '/clientUser/find_personal_by_page.shtml',
		checkbox : true,
		serNumber : true
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
		content : rootPath + '/clientUser/editPersonalUI.shtml?id=' + cbox
	});
}
function addFun() {
	var type = $("#type").val();
	pageii = layer.open({
		title : "新增国内客户",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/customer/addUI.shtml?type='+type
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/customer/deleteEntity.shtml';
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
	var url = rootPath + '/customer/look_customer.shtml?customerId='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}

function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","clientUserFormMap."+keyValue);
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
		content : rootPath + '/clientUser/toClientUserUI.shtml?userId='+id+'&type=clientUser'
	});
}
