var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
        {
			colkey : "id",
			name : "标识",
			renderData:function(rowindex,data,rowdata,column){
				if(data<13){
					return "国内-"+data;
				}else{
					return "国外-"+data;
				}
			}
		 },{
			colkey : "url",
			name : "宣传图",
			renderData:function(rowindex,data,rowdata,column){
				if(""!=data && null!=data){
					return "<img src='/"+data+"' width='124.53px'  height='90px' style='border-radius:5px;'>"
				}else {
					return "";
				}
				
			}
		 },{
			colkey : "web",
			name : "官网",
		 },{
			colkey : "type",
			name : "是否固定",
			renderData:function(rowindex,data,rowdata,column){
				if(data=='1' || data==1){
					return "固定";
				}else{
					return "随机";
				}
			}
		 },{
			colkey : "isUserful",
			name : "是否可用",
			renderData:function(rowindex,data,rowdata,column){
				if(data=='1' || data==1){
					return "可用";
				}else{
					return "不可用";
				}
			}
	 },{
		colkey : "id",
		name : "操作",
		renderData:function(rowindex,data,rowdata,column){
			return "<a href='javascript:editFun("+data+");' id='editFun'>编辑</a>"
		}
	 }
		 ],
		jsonUrl : rootPath + '/advertisement/findBy_advertisementIndex_page.shtml',
		/*checkbox : true,
		serNumber : true*/
	});
	$("#type").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#isUserful").click("click", function() {// 绑定查询按扭
		search();	
	});
	/*$("#editFun").click("click", function(serNumber) {
		
		alert(serNumber);
		//editFun();
	});*/
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
});
function search(){
	var searchParams = $("#searchForm").serializeJson();// 初始化传参数
	grid.setOptions({
		data : searchParams
	});
}
function editFun(ids) {
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/advertisement/editIndexUI.shtml?id=' + ids 
	});
};
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/appeal/deleteEntity.shtml';
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
