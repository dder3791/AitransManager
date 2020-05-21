$(function() {
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						layer.confirm('编辑成功!是否关闭窗口?', function(index) {
							parent.grid.loadData();
							parent.layer.close(parent.pageii);
							return false;
						});
						$("#form")[0].reset();
					} else {
						layer.alert('编辑失败！', 3);
					}
				}
			});
		},
		rules : {
			"orderFormMap.orderNumber" : {
				required : true,
			},
			"orderFormMap.orderReference" : {
				required : true,
			},
			"orderFormMap.orderTitle" : {
				required : true,
			},
			"articleFormMap.issueDate" : {
				required : true,
			}
		},
		messages : {
			"orderFormMap.orderNumber" : {
				required : "请输入订单号",
			},
			"orderFormMap.orderReference" : {
				required : "订单案号不能为空",
			},
			"orderFormMap.orderTitle" : {
				required : "请填写订单标题",
			},
			"articleFormMap.issueDate" : {
				required : "请选择发布时间",
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
