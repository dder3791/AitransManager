var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "nameEN",
			name : "英文名称",
		}, {
			colkey : "nameZH",
			name : "中文名称",
		},{
			colkey : "scope",
			name : "经营范围",
		}, {
			colkey : "cel",
			name : "座机"
		},{
			colkey : "tel",
			name : "移动电话",
		}, {
			colkey : "emaile",
			name : "公司邮箱",
		}, {
			colkey : "address",
			name : "公司地址"
		},{
			colkey : "contact",
			name : "负责人"
		}, {
			colkey : "registrationNumber",
			name : "证件号码"
		},{
			colkey : "registrationAddress",
			name : "注册地址"
		},{
			colkey : "intro",
			name : "详细描述"
		},{
			colkey : "certificationState",
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
		jsonUrl : rootPath + '/clientUser/find_company_by_page.shtml',
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
		content : rootPath + '/clientUser/editClientCompanyUI.shtml?id=' + cbox
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
		$("#searchInput").prop("name","onLineCustomerFormMap."+keyValue);
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
		content : rootPath + '/clientUser/toCompanyUI.shtml?comId='+id+'&type=company'
	});
}
