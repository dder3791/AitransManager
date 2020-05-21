var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "name",
			name : "项目名称",
		}, {
			colkey : "head",
			name : "项目经理",
		}, {
			colkey : "createTime",
			name : "创建时间",
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		},{
			colkey : "completeTime",
			name : "完成时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			colkey : "description",
			name : "详细描述"
		}
		 ],
		jsonUrl : rootPath + '/project/look_project_by_id.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var keyValue=document.getElementById("selectSearch").value;
		if(keyValue!=null && keyValue!=''){
			$("#searchInput").prop("name","projectFormMap."+keyValue);
			var searchParams = $("#searchForm").serializeJson();// 初始化传参数
			grid.setOptions({
				data : searchParams
			});
		}
		else if(keyValue==null ||keyValue==''){
			alert("请选择关键字种类！！");
		}
		
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
	$("#rewardFun").click("click", function() {
		rewardFun();
	});
});
function editFun() {
	var typeId = $("#typeId").val();
	var type = $("#type").val();
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/project/editUI_2.shtml?id=' + cbox +'&type='+type+'&typeId='+typeId
	});
}
function addFun() {
	var id = $("#typeId").val();
	var type = $("#type").val();
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/project/addUI_2.shtml?id='+id+'&type='+type
	});
}
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/project/deleteEntity.shtml?type=project';
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
	var id = $("#id").val();
	var type = $("#type").val();
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/project/look.shtml?projectId='+cbox+'&id='+id+'&type='+type;//id为客户/类型/流程id
	pageii = layer.open({
		title : "查看客户项目",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}

function rewardFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "稿酬",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/reward/editUI.shtml?id=' + cbox
	});
}