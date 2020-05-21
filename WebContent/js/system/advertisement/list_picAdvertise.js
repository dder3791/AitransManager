var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
        {
			colkey : "coord",
			name : "坐标",
		 },{
			colkey : "url",
			name : "宣传图",
			renderData:function(rowindex,data,rowdata,column){
				if(data!=null && data!=""){
					if(rowdata.type==1 || rowdata.type=='1'){
						return "<img src='"+data+"' width='124.53px'  height='90px' style='border-radius:5px;'>"
					}
					if(rowdata.type==2 || rowdata.type=='2'){
						return "<img src='"+data+"' width='184px'  height='17px' style='border-radius:5px;'>"
					}
					if(rowdata.type==3 || rowdata.type=='3'){
						return "<img src='"+data+"' width='40px'  height='20px' style='border-radius:5px;'>"
					}

					
				}else{
					return "";
				}
				
			}
		 },{
			colkey : "web",
			name : "官网",
		 },{
			colkey : "type",
			name : "图片类型",
			renderData:function(rowindex,data,rowdata,column){
				if(data=='1' || data==1){
					return "大图";
				}else if(data=='2' || data==2){
					return "长条图";
				}else if(data=='3' || data==3){
					return "6/1图";
				}else if(data=='4' || data==4){
					return "文本";
				}
			}
		 },{
			colkey : "context",
			name : "宣传内容",
		 },{
			colkey : "coord",
			name : "坐标",
		 },{
			colkey : "isUseFul",
			name : "是否可用",
			renderData:function(rowindex,data,rowdata,column){
				
				if(data=='1' || data==1){
					return "可用";
				}else{
					return "不可用";
				}
			}
	 },/*{
		colkey : "id",
		name : "操作",
		renderData:function(rowindex,data,rowdata,column){
			return "<a href='javascript:editFun("+data+");' id='editFun'>编辑</a>"
		}
	 }*/
		 ],
		jsonUrl : rootPath + '/advertisement/list_picAdvertise_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#type").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#isUserful").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
	$("#addFun").click("click", function() {
		addFun();
	});
	$("#editFun").click("click", function() {
		editFun();
	});
});
function search(){
	var searchParams = $("#searchForm").serializeJson();// 初始化传参数
	grid.setOptions({
		data : searchParams
	});
}

function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/advertisement/add_advertisementUI.shtml'
	});
}

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
		content : rootPath + '/advertisement/edit_advertisementUI.shtml?&id=' + cbox
	});
};
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/advertisement/deleteEntity.shtml';
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
};

function look() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/appeal/look_appeal.shtml?id='+cbox;
	$("#loadhtml").load(url);
}
