//单独验证某一个input  class="telephone"
jQuery.validator.addMethod("cel", function(value, element) {
    var length = value.length;
    var telephoneRule = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/; 
    return this.optional(element) || (telephoneRule.test(value));
}, "请正确填写您的座机号码,正确格式:010-88888888");
/*jQuery.validator.addMethod("telephone",function(value, element) {
	var length = value.length;
	var telephoneRule = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
	return this.optional(element) || (telephoneRule.test(value));
}, "请正确填写您的座机号码,正确格式:010-88888888");*/
$(function() {
	$("form").validate({
		submitHandler : function(form) {//必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {//验证新增是否成功
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						layer.confirm('更新成功!是否关闭窗口?', function(index) {
							parent.grid.loadData();
							parent.layer.close(parent.pageii);
							return false;
						});
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
				rangelength : [2,40],
				remote : {
					type : "POST",
					url : "isRepeatOrExist.shtml?type=ZH",
					data : {
						nameZH : function() {
							return $("#nameZH").val();
						},
						customerId : function(){
							return $("#id").val();
						}
					}
				}
			},
			"customerFormMap.nameEN" : {
				/*required : true,*/
				rangelength : [2,40],
				remote : {
					type : "POST",
					url : "isRepeatOrExist.shtml?type=EN",
					data : {
						nameEN : function() {
							return $("#nameEN").val();
						},
						customerId : function(){
							return $("#id").val();
						}
					}
				}
			},
			"customerFormMap.shortName" : {
				/*required : true,*/
				remote : {
					type : "POST",
					url : "isRepeatOrExist.shtml?type=shortName?type=shortName",
					data : {
						shortName : function(){
							return $("#shortName").val();
						},
						customerId : function(){
							return $("#id").val();
						}
					}
			
				}
			}
		},
		messages : {
			"customerFormMap.nameZH" : {
				required : "请输入公司中文名称",
				remote : "该公司名已存在,请重新输入"
			},
			"customerFormMap.nameEN" : {
				required : "请输入公司英文名称",
				remote : "该公司名已存在,请重新输入"
			},
			"customerFormMap.shortName" : {
				required : "请输入公司简称",
				remote : "该简称已存在,请重新输入"
			}
		},
		errorPlacement : function(error, element) {//自定义提示错误位置
			$(".l_err").css('display', 'block');
			//element.css('border','3px solid #FFCCCC');
			$(".l_err").html(error.html());
		},
		success : function(label) {//验证通过后
			$(".l_err").css('display', 'none');
		}
	});
});
