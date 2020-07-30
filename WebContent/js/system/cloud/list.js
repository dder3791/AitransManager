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
	//
	
});