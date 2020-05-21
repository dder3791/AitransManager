//单独验证某一个input  class="checkpass"
jQuery.validator.addMethod("checkacc", function(value, element) {
	return this.optional(element)
			|| ((value.length <= 30) && (value.length >= 3));
}, "账号由3至30位字符组合构成");

//原稿长度验证
jQuery.validator.addMethod("length", function(value, element) {
	var reg = /^[0-9]+$/;
	return this.optional(element)|| reg.test(value);
}, "只能填入数字");


// 金钱验证
jQuery.validator.addMethod("money", function(value, element) {
	var reg = /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/;
	return this.optional(element) || reg.test(value);
}, "请输入金钱的正确格式，例如：33.33");


$(function() {
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						layer.confirm('添加成功!是否关闭窗口?', function(index) {
							parent.grid.loadData();
							parent.layer.close(parent.pageii);
							return false;
						});
						$("#form")[0].reset();
					} else {
						layer.alert('添加失败！', 3);
					}
				}
			});
		},
		rules : {
			"auditoryFeeKilo" : {
				required : true,
			},
			"proofFeeKilo" : {
				required : true,
			},
			"translatorFeeKilo" : {
				required : true,
			},
			"customerId" : {
				required : true,
			},
			"customerReference" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExist.shtml?type=add',
					data : {
						customerreference : function() {
							return $("#customerreference").val();
						}
					}
				},
			},
			"wordLength" : {
				required : true,
			},
			"imageLength" : {
				required : true,
			},
			
			"companyReward" : {
				required : true,
			},
			"advance" : {
				required : true,
			},
			name : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'nameExist.shtml?type=add',
					data : {
						name : function() {
							return $("#name").val();
						}
					}
				}
			},
			createTime : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'completeTimeExist.shtml',
					data : {
						completeTime : function() {
							return $("#completeTime").val();
						},
						createTime : function() {
							return $("#createTime").val();
						}
					}
				}
			},
			completeTime : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'completeTimeExist.shtml',
					data : {
						completeTime : function() {
							return $("#completeTime").val();
						},
						createTime : function() {
							return $("#createTime").val();
						}
					}
				}
			}
		},
		messages : {
			"auditoryFeeKilo" : {
				required : "请输入审核费用",
			},
			"proofFeeKilo" : {
				required : "请输入校验费用",
			},
			"translatorFeeKilo" : {
				required : "请输入翻译费用",
			},
			"customerId" : {
				required : "请选择客户名称",
			},
			"customerReference" : {
				required : "请输入客户案号",
				remote : "该案号已经存在"
			},
			"wordLength" : {
				required : "请输入文本字数",
			},
			imageLength : {
				required : "请输入图片字数",
			},
			"companyReward" : {
				required : "请输入公司稿酬",
			},
			"advance" : {
				required : "请输入公司稿酬",
			},
			name : {
				required : "项目名称不能为空",
				remote : "项目名称已经存在"
			},
			createTime : {
				required : "请选择返稿时间",
				remote : "返稿时间必须早于立项时间"
			},
			completeTime : {
				required : "请选择返稿时间",
				remote : "返稿时间必须迟于立项时间"
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
