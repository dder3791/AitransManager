//校验密码是否相同
function same(pwd) {  
    var oldPwd = $("#newpassword").val();  
    if (oldPwd == pwd){
    	return false;  
    }else{
    	//更改表单中确认密码的name的属性名
    	$("#confirmpassword").attr("name","transferFormMap.password");
    	return true;
    }  
}

jQuery.validator.addMethod("same", function(value, element) {  
    return this.optional(element) || same(value);  
}, "新密码和确认密码不一致"); 

//加入数据校验证
$(function() {
	$("#formUpdatePwd").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",//ajaxSubmi带有文件上传的。不需要设置json
				success : function(data) {
					if (data == "success") {
						layer.confirm('修改密码成功!是否关闭窗口?', function(index) {
							layer.close(index);
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭   
						});
					} else {
						layer.msg('修改密码失败！', {icon: 5});
					}
				}
			});
		},
		rules : {
			"transferFormMap.newpassword" : {
				minlength: 6,
				required : true
			},
			"transferFormMap.confirmpassword": {
				required : true,
				minlength: 6,
//				same:true, 
				equalTo: "#newpassword"
			}
		},
		messages : {
			"transferFormMap.newpassword" : {
				required : "请输入新密码",
				minlength: jQuery.format("密码不能小于{0}个字 符")
			},
			"transferFormMap.confirmpassword" : {
				required : "请输入确认密码",
				minlength: jQuery.format("密码不能小于{0}个字 符"),
				equalTo : "新密码和确认密码不一致"
			}
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