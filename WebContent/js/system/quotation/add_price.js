/*$(function() {
	//alert("listprice.js");
	$("#addPriceFun").click("click", function() {
		addPriceFun();
	});
	$("#numx").click("click", function() {
		//alert(0);
		//parent.dder=13;
		//alert(1);
		//alert(parent.dder);
		//alert(2);
		alert(parent.grid);
		parent.grid.loadData();
		alert("end");
	});
});
function addPriceFun(){
	pageii = layer.open({
		title : "新增报价",
		type : 2,
		area : [ "1000px", "100%" ],
		content : rootPath + '/transfer/addPriceUI.shtml'
	});
}*/

$(function() {
	
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",//ajaxSubmi带有文件上传的。不需要设置json
				success : function(data) {
					if (data == "success") {
						layer.confirm('更新成功!是否关闭窗口?', function(index) {
							parent.grid.loadData();
							parent.layer.close(parent.pageii);
							return false;
						});
					} else {
						layer.msg('更新失败！', 3);
					}
				}
			});
		},
		errorPlacement : function(error, element) {// 自定义提示错误位置
			$(".l_err").css('display', 'block');
			// element.css('border','3px solid #FFCCCC');
			$(".l_err").html(error.html());
		},
		success : function(label) {// 验证通过后
			$(".l_err").css('display', 'none');
		}
	});
	$("#language_target").change(function(){
		setLanguage();
	});
	$("#language_src").change(function(){
		setLanguage();
	});
	
	function setLanguage(){
		var src = $("#language_src").val();
		var target = $("#language_target").val();
		if(src==""){
			alert("请选择源语种!");
			return;
		}
		if(target==""){
			alert("请选择目标语种!");
			return;
		}
		if(src==target){
			alert("源语种与目标语种不能相同!");
			$("#languages_t").val("");
			return;
		}
		var languages = src+""+target;
		$("#languages_t").val(languages);
	}
	
	var i=0;
	$('#ubox').on('click','li',function(){ 
		var de = ($(this).children("input").val()); //弹出点击的li标签的value值
		$("#delt").val(de);
	});
	$('#btn1').on('click',function(){ 
		console.log("input btn1");
		var l = $("#languages_t").val();
		if(l==""){
			return;
		}
		create(i,l);
		i++;
	});
	$('#btn2').on('click',function(){ 
		console.log("input btn2");
		var d = $("#delt").val();
		remove(d);
	});
	$('#btn3').on('click',function(){ 
		console.log("input btn3");
		var languages = "";
		 $("#ubox li").each(function(){			
			languages += $(this).text()+",";
		});
		 languages = languages.substring(0,languages.length-1);
		$("#languages").val(languages);
	});
	function create(i,v){
		$("#ubox").append("<li class='item"+i+" nav_lists_li' margin-top:3px;margin-left:10px;><input type=hidden value="+i+" />"+v+"</li>");//动态生成 
	}
	function remove(i){
		//alert("要删除的项目索引"+i)
		$('#ubox .item'+i).remove(); //动态删除
	}
	
});
