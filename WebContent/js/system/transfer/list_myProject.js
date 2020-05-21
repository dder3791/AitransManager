var pageii = null;
var grid = null;
$(function() {
	var id = $("#transferId").val();
	grid = lyGrid({
		pagId : 'paging',
		l_column : [
				{
					colkey : "name",
					name : "项目名称",
				},
				{
					colkey : "language",
					name : "项目语言",
					renderData : function(rowindex, data, rowdata, column) {
						if (data == "EN")
							return "英语";
						if (data == "JP")
							return "日语";
						if (data == "KOR")
							return "韩语";
						if (data == "GER")
							return "德语";
						if (data == "FR")
							return "法语";
						else
							return "";
					}
				},
				{
					colkey : "stateName",
					name : "项目状态",
				},
				{
					colkey : "traStartTime",
					name : "开始翻译时间",
					renderData : function(rowindex, data, rowdata, column) {
						return new Date(data).format("yyyy-MM-dd");
					}
				},
				{
					colkey : "traEndTime",
					name : "翻译完成时间",
					renderData : function(rowindex, data, rowdata, column) {
						return new Date(data).format("yyyy-MM-dd");
					}
				},
				{
					colkey : "remark",
					name : "备注",
					renderData : function(rowindex, data, rowdata, column) {
						return rowindex + " " + rowdata.id;
					}
				},
				{
					name : "操作",
					renderData : function(rowindex, data, rowdata, column) {
						if (rowdata.stateName == "新建项目")
							return '<button type="button" class="btn btn-info" onclick="accept('
									+ rowdata.id + ')">接受项目</button>';
						if (rowdata.stateName == "正在翻译")
							return '<button type="button" class="btn btn-success" onclick="complete('
									+ rowdata.id + ')">完成项目</button>';
						else
							return '已完成';
					}
				} ],
		jsonUrl : rootPath + '/transfer/find_my_project_by_page.shtml?id=' + id,
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
function accept(projectId) {
	alert("开始翻译");
	$.ajax({
		type : "POST",
		url : rootPath + "/transfer/accept.shtml",
		data : "id=" + projectId,
		success : function() {
			grid.loadData();
		}
	})
}

function complete(projectId) {
	alert("完成翻译");
	$.ajax({
		type : "POST",
		url : rootPath + "/transfer/complete.shtml",
		data : "id=" + projectId,
		success : function() {
			grid.loadData();
		}
	})
}

function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/transfer/look_transfer.shtml?transferId=' + cbox;
	pageii = layer.open({
		title : "查看详情",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}