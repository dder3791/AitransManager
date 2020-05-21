var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "name",
			name : "项目名称"
		}, {
			colkey : "translatorFeeKilo",
			name : "稿酬(RMB/千字)"
		}, {
			colkey : "length",
			name : "稿件字数"
		}, {
			colkey : "translatorFeeTotal",
			name : "总稿酬(元)"
		},{
			colkey : "transSettlementTime",
			name : "结算时间",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			colkey : "remark",
			name : "备注"
		} 
		 ],
		jsonUrl : rootPath + '/transfer/find_my_reward_by_page.shtml',
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
function look(rewardId) {
	var url = rootPath+"/transfer/look_my_reward_detail.shtml?id="+rewardId;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}