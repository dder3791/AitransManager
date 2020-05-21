var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "id",
			name : "id",
			hide : true
		},{
			colkey : "User_Name",
			name : "用户名称",
		}, {
			colkey : "User_UC",
			name : "用户码",
		}, {
			colkey : "User_IP",
			name : "用户IP"
		}, {
			colkey : "User_OnlineDate",
			name : "用户上线时间",
			renderData : function(rowindex,data,rowdata,colkey){
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		},  {
			colkey : "User_Remark",
			name : "备注"
		}
		 ],
		jsonUrl : rootPath + '/softInfo/find_online_user_by_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	
	$("#look").click("click", function() {
		look();
	});
});

function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/softInfo/look_transfer.shtml?transferId='+cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}