var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "id",
			name : "id",
		},{
			colkey : "cname",
			name : "公司名称",
		},{
			colkey : "pname",
			name : "项目名称",
		},{
			colkey : "languagePair",
			name : "翻译语种",
		},{
			colkey : "tname",
			name : "译员",
		},{
			colkey : "vname",
			name : "审核员",
		},{
			colkey : "aname",
			name : "校对员",
		},{
			colkey : "projectType",
			name : "翻译类型",
		},{
			colkey : "procedureType",
			name : "流程种类",
		},{
			colkey : "createTime",
			name : "开始时间",
			isSort : true,
		},{
			colkey : "completeTime",
			name : "返稿时间",
			isSort : true,
		},{
			colkey : "head",
			name : "项目经理"
		},{
			colkey : "stateName",
			name : "项目状态",
			isSort : true,
		},{
			colkey : "description",
			name : "备注"
		}
		 ],
		jsonUrl : rootPath + '/project/find_project_flow_by_page.shtml',
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
	/*var keyValue=document.getElementById("selectSearch").value;
	if(keyValue!=null && keyValue!=''){
		$("#searchInput").prop("name","projectFormMap."+keyValue);*/
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});	
	/*}else if(keyValue==null ||keyValue==''){
		alert("请选择关键字种类！！");
	}*/
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
		content : rootPath + '/project/addUI.shtml?type=projectflow'
	});
}
function delFun() {
	var customer=document.getElementById("customer").value;
	var name=document.getElementById("name").value;
	var languagePair=document.getElementById("languagePair").value;
	var tname=document.getElementById("tname").value;
	var vname=document.getElementById("vname").value;
	var aname=document.getElementById("aname").value;
	var projectType=document.getElementById("projectType").value;
	var processesTypeId=document.getElementById("processesTypeId").value;
	var head=document.getElementById("head").value;
	var projectState=document.getElementById("projectState").value;
	var createTime=document.getElementById("startDate").value;
	var complementTime=document.getElementById("endDate").value;
	//var ids="shortName like '%25"+customer+"%25',name like '%25"+name+"%25',languagePair like '%25"+languagePair+"%25',tname like '%25"+tname+"%25',projectType like '%25"+projectType+"%25',procedureType like '%25"+processesTypeId+"%25',projectD.head like '%25"+head+"%25',stateName like '%25"+projectState+"%25'";
	var ids=customer+","+name+","+languagePair+","+tname+","+vname+","+aname+","+projectType+","+processesTypeId+","+head+","+projectState+","+createTime+","+complementTime+",";
	pageii = layer.open({
		title : "统计",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/project/statistics.shtml?ids='+ids
	});
	
	/*var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("至少选择一个项目进行统计");
		return;
	}
	var ids=cbox.join(",");
	pageii = layer.open({
		title : "统计",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/project/statistics.shtml?ids='+ids
	});*/
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
		content : rootPath + '/project/downloadUIDB.shtml?id=' + cbox
	});
  }