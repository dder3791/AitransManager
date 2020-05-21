var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		id : 'paging',
		l_column : [ {
			colkey : "Id",
			name : "id",
			width : "50px",
			hide : true
		}, {
			colkey : "accountName",
			name : "账号"
		},{
			colkey : "userName",
			name : "用户名"
		},{
			colkey : "roleName",
			name : "角色名称"
		},{
			colkey : "loginTime",
			name : "登入时间",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			},
			isSort:true
		} , {
			colkey : "loginIP",
			name : "登入IP"
		}],
		jsonUrl : rootPath + '/userlogin/findByPage.shtml',
		//jsonUrl : rootPath + '/userlogin/findByAccNameAndPage.shtml',
		checkbox : true
	});
	$("#searchForm").click("click", function() {// 绑定查询按扭
		var searchParams = $("#fenye").serializeJson();
		grid.setOptions({
			data : searchParams
		});
	});
});
