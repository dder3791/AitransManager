var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		id : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
			width : "50px",
			hide : true
		}, {
			colkey : "accountName",
			name : "账号"
		},{
			colkey : "loginTime",
			name : "登入时间",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		} , {
			colkey : "loginIP",
			name : "登入IP"
		}],
		jsonUrl : rootPath + '/userlogin/find_online_user_by_page.shtml',
		checkbox : true
	});
	$("#searchForm").click("click", function() {// 绑定查询按扭
		var searchParams = $("#fenye").serializeJson();
		grid.setOptions({
			data : searchParams
		});
	});
});
