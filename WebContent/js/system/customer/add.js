// 公司电话码验证
jQuery.validator.addMethod("cel", function(value, element) {
    var length = value.length;
    var telephoneRule = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; 
    return this.optional(element) || (telephoneRule.test(value));
}, "请正确填写您的座机号码,正确格式:010-88888888");
/*jQuery.validator.addMethod("cel", function(value, element) {
	var length = value.length;
	var telephoneRule = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 
	return this.optional(element) || (telephoneRule.test(value));
}, "请正确填写您的座机号码,正确格式:010-88888888");
*/$(function() {
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
			"customerFormMap.email" : {
				email : true
			},
			"customerFormMap.address" : {
				rangelength : [2,40]
			},
			"customerFormMap.nameZH" : {
				/*required : true,*/
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExist.shtml?type=ZH',
					data : {
						nameZH : function() {
							return $("#nameZH").val();
						}
					}
				}
			},
			"customerFormMap.nameEN" : {
				/*required : true,*/
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExist.shtml?type=EN',
					data : {
						nameEN : function() {
							return $("#nameEN").val();
						}
					}
				}
			},
			"customerFormMap.shortName" : {
				required : true,
				remote : {
					type : "POST",
					url : "isExist.shtml?type=shortName",
					data : {
						shortName : function(){
							return $("#shortName").val();
						}
					}
			
				}
			}
		},
		messages : {
			"customerFormMap.nameZH" : {
				required : "请输入公司中文名称",
				remote : "该公司已添加,不能重复添加"
			},
			"customerFormMap.nameEN" : {
				required : "请输入公司英文名称",
				remote : "该公司已添加,不能重复添加"
			},
			"customerFormMap.shortName" : {
				required : "请输入公司简称",
				remote : "该简称已添加,不能重复添加"
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
