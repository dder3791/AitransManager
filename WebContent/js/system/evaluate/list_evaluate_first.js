var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
		 {
			colkey : "reference",
			name : "公司案号",
		 },{
			colkey : "tname",
			name : "译员名称",
		 },{
			colkey : "name",
			name : "项目名称",
		 },{
			colkey : "oriLanguage",
			name : "校对语言",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="EN"){
					return "英语";
				}else if(data=="JP"){
					return "日语";
				}else if(data=="KOR"){
					return "韩语";
				}else if(data=="GER"){
					return "德语";
				}else if(data=="FR"){
					return "法语";
			   }
			}
		 },{
			colkey : "pname",
			name : "校对人员",
		 },{
			colkey : "difficultyT",
			 name : "技术难度",
		 },{
	        colkey : "difficultyL",
	         name : "语言难度",  
         },{
        	 colkey : "comingDate",
        	 name : "交案时间",
         },{
        	 colkey : "completeDate",
        	 name : "完成时间",
         },{
        	 colkey : "score",
        	 name : "得分情况"
         },{
        	 colkey : "problems",
        	 name : "问题描述",
         },{
        	 colkey : "checker",
        	 name : "检查人",
         },{
        	 colkey : "checkDate",
        	 name : "检查日期",
         }
		 ],
		jsonUrl : rootPath + '/evaluate/findBy_evaluate_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();	
	});
	$("#addFun").click("click",function(){
		addFun();
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
		$("#searchInput").prop("name","EvaluateFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/evaluate/addUI_1.shtml'
	});
};
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
		content : rootPath + '/evaluate/editUI_1.shtml?id=' + cbox 
	});
};
function delFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/evaluate/deleteEntity.shtml';
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
	var url = rootPath + '/evaluate/look_evaluate.shtml?id='+cbox;
	$("#loadhtml").load(url);
}
