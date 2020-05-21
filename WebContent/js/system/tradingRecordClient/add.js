// 公司电话码验证
/*jQuery.validator.addMethod("cel", function(value, element) {
    var length = value.length;
    var telephoneRule = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; 
    return this.optional(element) || (telephoneRule.test(value));
}, "请正确填写您的座机号码,正确格式:010-88888888");*/
/*jQuery.validator.addMethod("cel", function(value, element) {
	var length = value.length;
	var telephoneRule = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 
	return this.optional(element) || (telephoneRule.test(value));
}, "请正确填写您的座机号码,正确格式:010-88888888");

*/
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
			"tradingRecordFormMap.payMoney" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'payMoney.shtml',
					data : {
						payMoney : function() {
							return $("#payMoney").val();
						},
						makeupCo2 : function() {
							return $("#makeupCo2").val();
						}
					}
				}
			},
			"tradingRecordFormMap.payTime" : {
				required : true
			},
			"tradingRecordFormMap.intro" : {
				required : true,
			}
		},
		messages : {
			"tradingRecordFormMap.payMoney" : {
				required : "请输入需支付金额",
				remote:"余额不足"
			},
			"tradingRecordFormMap.payTime" : {
				required : "请选择输入时间",
			},
			"tradingRecordFormMap.intro" : {
				required : "请描述转账详情",
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
