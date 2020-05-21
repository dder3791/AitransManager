$(function() {

	// // 手机号码验证
	// jQuery.validator.addMethod("isMobile", function(value, element) {
	// var length = value.length;
	// var mobile =
	// /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	// return this.optional(element) || (length == 11 && mobile.test(value));
	// }, "请正确填写您的手机号码");
	//

	// 原稿长度验证
	jQuery.validator.addMethod("rewardlength", function(value, element) {
		var reg = /^[0-9]+$/;
		return this.optional(element)|| reg.test(value);
	}, "only num");

	
	// 金钱验证
	jQuery.validator.addMethod("money", function(value, element) {
		var reg = /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/;
		return this.optional(element) || reg.test(value);
	}, "请输入金钱的正确格式");

	
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",// ajaxSubmi带有文件上传的。不需要设置json
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
			"rewardFormMap.length" : {
				required : true,
				digits:true
			},
			"rewardFormMap.companyReward" : {
				required : true,
			}
		},
		messages : {
			"rewardFormMap.length" : {
				required : "请输入原稿字数",
				//整数输入框验证
				digits:"只能输入数字"
			},
			"rewardFormMap.companyReward" : {
				required : "公司稿酬必须写",
			}
		},
		errorPlacement : function(error, element) {// 自定义提示错误位置
			$(".l_err").css('display', 'block');
			$(".l_err").html(error.html());
		},
		success : function(label) {// 验证通过后
			$(".l_err").css('display', 'none');
		}
	});
});
