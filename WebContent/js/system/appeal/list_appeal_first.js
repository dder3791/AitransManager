var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
		 {
			colkey : "tname",
			name : "译员名称",
		 },{
			colkey : "pname",
			name : "所在项目",
		 },{
			colkey : "mode",
			name : "申诉方式",
		 },{
			colkey : "matter",
			name : "申诉事项",
		 },{
			colkey : "cause",
			 name : "申诉原因",
		 },{
	        colkey : "state",
	        name : "申诉状态",  
	        renderData:function(rowindex,data,rowdata,column){
			  if(data=="0"){
			   return "未申诉";
			  }
			  if(data=="1"){
			   return "已提交申诉";
			  }
			  if(data=="2"){
			  return "申诉已完成";
			   }
			 }
         },{
            colkey : "appealDate",
             name : "申诉时间"
         }
		 ],
		jsonUrl : rootPath + '/appeal/findBy_appeal_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#editFun").click("click", function(serNumber) {
		editFun(serNumber);
	});
	$("#delFun").click("click", function() {
		delFun();
	});
	$("#look").click("click", function() {
		look();
	});
});
function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","AppealFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
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
		content : rootPath + '/appeal/editUI_1.shtml?id=' + cbox 
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
