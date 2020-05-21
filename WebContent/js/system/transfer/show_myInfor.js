var pageii = null;
var grid = null;
$(function() {
	/*grid = lyGrid({
		pagId : 'paging',
		jsonUrl : rootPath + '/transfer/showMyInfor.shtml',
	});*/
	var tb = $("#loadhtml");
	var url = rootPath+'/transfer/showMyInfor.shtml';
	
	$.getJSON(url,function(data){
		$("#accountName").text(data.accountName);
		if(data.level == 3){
			$("#level").text('三级');
		}
		if(data.level == 4){
			$("#level").text('四级');
		}
		if(data.level == 5){
			$("#level").text('五级');
		}
		$("#nickname").text(data.nickname);
		$("#realName").text(data.realName);
		$("#point").text(data.point);
		$("#email").text(data.email);
		$("#phone").text(data.phone);
		$("#address").text(data.address);
		$("#degree").text(data.degree);
		$("#major").text(data.major);
		$("#domain").text(data.domain);
		$("#birthday").text(data.birthday);
	})
	
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});

	$("#editFun").click("click", function() {
		editFun();
	});
	
	
});
function editFun() {
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/transfer/editMyInfor.shtml'
	});
}


