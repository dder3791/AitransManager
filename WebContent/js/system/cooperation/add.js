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
			"projectFormMap.createTime":"required",
			"projectFormMap.completeTime" : {
				required : true,
				remote : {
					type : "POST",
					url : "checkTime.shtml",
					data : {
						createTime : function(){
							return $("#createTime").val();
						},
						completeTime : function(){
							return $("#completeTime").val();
						}
					}
				}
			},
			"projectFormMap.name" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExist.shtml',
					data : {
						name : function() {
							return $("#name").val();
						}
					}
				}
			}
		},
		messages : {
			"projectFormMap.name" : {
				required : "请输入项目名称",
				remote : "该项目已经存在"
			},
			"projectFormMap.completeTime" : {
				required : "请输入项目名称",
				remote : "完成时间不能早于开始时间"
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
