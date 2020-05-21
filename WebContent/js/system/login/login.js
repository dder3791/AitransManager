var pageii = null;
$(function() {
	$("#addFun").click("click", function() {
		addFun();
	});
});
function addFun() {
	pageii = layer.open({
		title : "译员注册",
		type : 2,
		area : [ "600px", "90%" ],
		content : rootPath + '/transfer/transferRegister.shtml'
	});
}

