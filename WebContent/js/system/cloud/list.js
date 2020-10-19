var pageii = null;
var grid = null;
$(function() {
	/**
	 * id,
			moduleName,
			moduleVersion,
			releaseTime,
			isAvailable,
			ranking,
			clientId,
			md5Value,
			releaseLog,
			fileType,
			dataPath,
			size,
			fileName
	 */
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  
			{
				colkey : "id",
				name : "ID",
			},{
			colkey : "moduleName",
			name : "模块名称",
			},{
			colkey : "moduleVersion",
			name : "模块版本",
		   },{
				colkey : "releaseTime",
				name : "发布时间",
		   },{
			colkey : "isAvailable",
			name : "是否生效",
			isSort:true,
			renderData : function(rowindex, data, rowdata, column) {
				if(data==1){
					return "是";
				}else{
					return "否";
				}
			}
		},{
			colkey : "fileName",
			name : "文件名称"
		},{
			colkey : "md5Value",
			name : "MD5值"
		},{
			colkey : "fileType",
			name : "文件类型",
		},{
			colkey : "dataPath",
			name : "文件路径"
		}, {
			colkey : "size",
			name : "文件大小"
		},
		 ],
		jsonUrl : rootPath + '/cloud/manager/cloudlist.shtml',
		checkbox : true,
		serNumber : true
	});
	
	//
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addFun").click("click", function() {// 绑定新建按扭
		add();
	});
	$("#delFun").click("click", function() {// 绑定删除按扭
		del();
	});
	$("#uploadFun").click("click", function() {// 绑定删除按扭
		upload();
	});
	//
	
});
function add() {
	
	pageii = layer.open({
		title : "新建云翻译模块",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/cloud/manager/add.shtml'
	});
}
function upload() {
	var row = grid.selectRow();
	if (row == "") {
		layer.msg("请选择下列一项云翻译模块数据！！");
		return;
	}
	var id;
	$.each(row,function(i,v){		
		id = v.id;
		name = v.moduleName;
	  });
	//alert(id);
	pageii = layer.open({
		title : "上传云翻译模块数据",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/cloud/manager/toupload.shtml?id='+id+"&moduleName="+name
	});
}
function del() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/cloud/manager/del.shtml';
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