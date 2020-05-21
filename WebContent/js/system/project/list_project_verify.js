var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "cname",
			name : "公司名称",
		},{
			colkey : "pname",
			name : "项目名称",
		},{
			colkey : "languagePair",
			name : "翻译语种",
		},{
			colkey : "projectType",
			name : "翻译类型",
		},{
			colkey : "procedureType",
			name : "流程种类",
		},{
			colkey : "createTime",
			name : "开始时间",
		},{
			colkey : "completeTime",
			name : "返稿时间"
		},{
			colkey : "head",
			name : "项目经理"
		},{
			colkey : "stateName",
			name : "项目状态",
		},{
			colkey : "description",
			name : "备注"
		},{
			name : "操作",
			renderData : function(rowindex,data,rowdata,column){
				return "<a onclick='active("+rowdata.id+")'>开始核实项目</a>"
			}
		}
		 ],
		jsonUrl : rootPath + '/project/find_project_verify_by_page.shtml',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		search();		
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
	$("#downLoad").click("click", function() {
		download();
	});
/*	$("#active").click("click", function() {
		active(id);
	});*/

	//有效果，但都会瞬间跳转
	$(".m-b-md").keypress(function (e) {
		var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
		if (keyCode == 13){
		search();
		}
	});
	
	
	//对input监控回车，没效果
	/*$('#searchInput').bind('keydown',function(event){  
		  
        if(event.keyCode == "13")      

        {  

            alert('你输入的内容为：');  

        }  

    });*/
	
	
	/*$(document).keyup(function(event){
		  if(event.keyCode ==13){
		    search();
		  }
		});*/
	
	
	// 对IE浏览器有效果，执行search方法并查询展示，但瞬间跳转其他页面
	//火狐不调用此方法
	/*document.onkeydown = function(e){
		 if(event.keyCode ==13){
			 alert("******");
			 	search();
			  }
	};*/
		
	/*document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    alert("13");
	    if(ev.keyCode==13) {
	    	alert("入13");
	    	window.event.keyCode=0;
	     }

	};
	*/
});

/*$(function(){
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	  	    	if (window.event.keyCode==13) {window.event.keyCode=0;}  if(ev.keyCode==13) {

	     }

	};
}); 
*/




function search(){
	var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","projectFormMap."+keyValue);
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	}else if(keyValue==null ||keyValue==''){
		layer.msg("请选择关键字种类！");
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
		content : rootPath + '/project/editUI.shtml?type=projectflow&id=' + cbox
	});
}
function addFun() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/project/addUI.shtml?type=projectVerify'
	});
}
function delFun() {
	
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/project/deleteEntity.shtml?type=projectflow';
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
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	//2017年4月20日15:09:58 将customerId改为id
	var url = rootPath + '/project/look_project.shtml?type=projectflow&id='+cbox;
	//2017年4月20日13:56:37 添加资源id
	//var url = rootPath + '/project/look_project.shtml?type=customer&id=129&customerId='+cbox;
	//var url = rootPath + '/project/look_project.shtml?type=customer&customerId='+cbox;
	var tb = $("#loadhtml");
	tb.load(url);
	/*pageii = layer.open({
		title : "查看客户项目",
		type : 2,
		area : [ "700px", "90%" ],
		content : url
	});*/
	
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

function download() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "下载",
		type : 2,
		area : [ "700px", "80%" ],
		content : rootPath + '/project/downloadUI.shtml?id=' + cbox
	});
  }

function active(id){
	pageii = layer.open({
		title : "审核项目",
		type : 2,
		area : [ "1000px", "85%" ],
		content : rootPath + '/project/verifyUI.shtml?id='+id
	});
	
	
	
/*	var url = rootPath + '/project/verifyUI.shtml?id='+id;
	var tb = $("#loadhtml");
	tb.load(url);*/
	
/*	layer.confirm('确认核实完毕？', function(index) {
		var url = rootPath + '/project/verifyUI.shtml?id='+id;
		var s = CommnUtil.ajax(url, id, "json");
		if (s == "success") {
			layer.msg('核实项目成功，可以发布');
			grid.loadData();
		} else {
			layer.msg('核实项目失败');
		}
	});*/
}