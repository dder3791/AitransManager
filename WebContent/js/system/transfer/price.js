var dder = 0;
var transtionId = $("#transtionId_").val();
var domain = $("#domain_").val();
$(function() {	
	//alert(transtionId);
	//参考 https://blog.csdn.net/mmm333zzz/article/details/51460292
	grid = lyGrid({
		pagId : 'paging',
		l_column : [  {
			colkey : "tranPrice",
			name : "翻译报价"
		}, {
			colkey : "prooPrice",
			name : "校对报价"			
		}, {
			colkey : "dayTrans",
			name : "日翻译字数"
		}, {
			colkey : "languages",
			name : "语言对"
		} 
		 ],
		//jsonUrl : rootPath + '/transfer/listquotation.shtml?transtionId='+transtionId+"&domain="+domain,
		 jsonUrl : rootPath + '/transfer/listquotation.shtml',
		data : {transtionId:transtionId,domain:domain},
		checkbox : true,
		serNumber : true,
		usePage : false// 是否分页		
	});
	
	
	//alert("listprice.js");
	$("#addPriceFun").click("click", function() {
		addPriceFun();
	});
	$("#delPriceBtn").click("click", function() {
		delPriceFun();
	});
	$("#showPriceBtn").click("click", function() {
		showPriceFun();
	});
	$("#editPriceBtn").click("click", function() {
		editPriceFun();
	});
	
	$("#numt").click("click", function() {
		alert(dder);
	});
	$("#domain").change(function() {// 绑定查询按扭
		$("#domain_").val($(this).val());
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.jsonUrl=rootPath+ "/transfer/listquotation.shtml";
		grid.setOptions({
			data : searchParams
		});
	});
	/*$('#domain').change(function() {
	    //一:
	    console.log($(this).val());
	    //二:
	    var options=$("#select option:selected");
	    console.log(options.val());
	});*/
	
});
function addPriceFun(){
	var domain = $("#domain_").val();
	pageii = layer.open({
		title : "新增报价",
		type : 2,
		area : [ "600px", "90%" ],
		content : rootPath + '/transfer/addPriceUI.shtml?transtionId='+transtionId+"&domain="+domain
	});	
}
function delPriceFun() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/transfer/delPrice.shtml';
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
function showPriceFun(){
	//var row = JSON.stringify(grid.selectRow()); 
	var row = grid.selectRow();
	if (row == "") {
		layer.msg("请选择查看项！！");
		return;
	}
	var id;
	var tranPrice;
	var prooPrice;
	var dayTrans;
	var languages;
	
	$.each(row,function(i,v){
		//console.log("output:"+v);
		id = v.id;
		tranPrice = v.tranPrice;
		prooPrice = v.prooPrice;
		dayTrans = v.dayTrans;
		languages = v.languages;
	  });
	
	pageii = layer.open({
		title : "查看报价",
		type : 2,
		area : [ "600px", "90%" ],
		content : rootPath + '/transfer/showPrice.shtml?id='+id+"&tranPrice="+tranPrice+"&prooPrice="+prooPrice+"&dayTrans="+dayTrans+"&languages="+languages
	});	
}
function editPriceFun(){
	var row = grid.selectRow();
	if (row == "") {
		layer.msg("请选择编辑项！！");
		return;
	}
	var id;
	var tranPrice;
	var prooPrice;
	var dayTrans;
	var languages;
	
	$.each(row,function(i,v){
		//console.log("output:"+v);
		id = v.id;
		tranPrice = v.tranPrice;
		prooPrice = v.prooPrice;
		dayTrans = v.dayTrans;
		languages = v.languages;
	  });
	
	pageii = layer.open({
		title : "编辑译员报价",
		type : 2,
		area : [ "600px", "90%" ],
		content : rootPath + '/transfer/editPriceUI.shtml?id='+id+"&tranPrice="+tranPrice+"&prooPrice="+prooPrice+"&dayTrans="+dayTrans+"&languages="+languages
	});	
	//alert("edit");
}

