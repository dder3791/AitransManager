// 数字验证添加方法需要在class里面添加需要验证的字段信息
jQuery.validator.addMethod("score", function(value, element) {
	var reg = /^(0|[1-9]\d?|100)$/;
	return this.optional(element) || reg.test(value);
}, "请输入正确的分值格式");
$(function() {
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				cache:false,
				dataType : "json",//ajaxSubmi带有文件上传的。不需要设置json
				success : function(data) {
					if (data == "success") {
						layer.confirm('添加评论成功!是否关闭窗口?', function(index) {
							parent.grid.loadData();
							parent.layer.close(parent.pageii);
							return false;
						});
					} else {
						layer.msg('添加评论失败！', 3);
					}
				}
			});
		},
		rules : {
			"EvaluateFormMap.completeDate" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'completeTimeExist.shtml',
					data : {
						completeTime : function() {
							return $("#completeDate").val();
						},
						presentTime:function(){
							var comingDateValue=document.getElementById("comingDate").value;
							return comingDateValue;
						}
					}
				}
			},
		"EvaluateFormMap.score" : {
			  required : true,
		},
		"EvaluateFormMap.checker" : {
			 required : true,
		},
		"EvaluateFormMap.problems" : {
			 required : true,
		}
		},
		messages : {
			"EvaluateFormMap.completeDate" : {
				required : "请选择完成时间",
				remote : "完成时间必须大于交案时间"
			},
			"EvaluateFormMap.score" : {
				required : "请输入评定分数"
			},
			"EvaluateFormMap.checker" : {
				required : "请输入检查人员"
			},
			"EvaluateFormMap.problems" : {
				required : "请输入问题"
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
