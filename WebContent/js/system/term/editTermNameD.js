$(function() {
	var termId=$("#termId").val();
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
		rules : {
			"termTableListFormMap.termName" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExist.shtml?type=edit&id='+termId,
					data : {
						name : function() {
							return $("#termName").val();
						},
						 
					}
				}
			},
		},
		messages : {
			"termTableListFormMap.termName" : {
				required : "术语名称不能为空",
				remote : "术语名称已经存在"
			},
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
});
