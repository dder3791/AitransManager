// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

$(function() {
	// 2017年4月18日17:37:33
	//获得所要回显的值
	var checkeds = $("#transferDomain").val();
	//拆分为字符串数组
	var checkArray = checkeds.split(",");
	//获得所有的复选框对象
	var checkBoxAll = $("input[name='transferFormMap.domain']");
	//获得所有复选框的value值，然后，用checkArray中的值和他们比较，如果有，则说明该复选框被选中
	for (var i = 0; i < checkArray.length; i++) {
		//获取所有复选框对象的value属性，然后，用checkArray[i]和他们匹配，如果有，则说明他应被选中
		$.each(checkBoxAll, function(j, checkbox) {
			//获取复选框的value属性
			var checkValue = $(checkbox).val();
			if (checkArray[i] == checkValue) {
				$(checkbox).prop("checked", true);
			}
		})
	}
	
	var url = rootPath + '/transfer/myInfor.shtml';
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",//ajaxSubmi带有文件上传的。不需要设置json
				success : function(data) {
					if (data == "success") {
						layer.confirm('更新成功!是否关闭窗口?', function(index) {
							/*parent.grid.loadData();*/
							parent.$("#loadhtml").load(url);
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
			"transferFormMap.nickname" : {
				required : true,
				rangelength : [2,8],
				remote : {
					type : "POST",
					url : "isExistNickname.shtml",
					data : {
						name : function(){
							return $("#nickname").val();
						}
					}
				}
				
			},
			"transferFormMap.email" : {
				required : true,
				email : true,
			},
			/*"transferFormMap.phone" : {
				
			}*/
		},
		messages : {
			"transferFormMap.nickname" : {
				required : "请输入昵称",
				remote : "该昵称已经存在"
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
