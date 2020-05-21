$(function() {
	
	$("form").validate({
		submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
			ly.ajaxSubmit(form, {// 验证新增是否成功
				type : "post",
				dataType : "json",//ajaxSubmi带有文件上传的。不需要设置json
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
		},rules : {
			"projectFormMap.customerReference" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'isExist.shtml?type=edit',
					data : {
						customerreference : function() {
							return $("#customerreference").val();
						},
						id:function(){
							return $("#projectId").val();
						}
					}
				}
			},

			"projectFormMap.name" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'nameExist.shtml?type=edit',
					data : {
						name : function() {
							return $("#name").val();
						},
						id : function() {
							return $("#projectId").val();
						}
					}
				}
			},
			"projectFormMap.completeTime" : {
				required : true,
				remote : { // 异步验证是否存在
					type : "POST",
					url : 'completeTimeExist.shtml?type=edit',
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
			"projectFormMap.name" : {
				required : "项目名称不能为空",
				remote : "项目名称已经存在"
			},
			"projectFormMap.customerReference" : {
				required : "请输入客户案号",
				remote : "该案号已经存在"
			},
			"projectFormMap.completeTime" : {
				required : "请选择返稿时间",
				remote : "返稿时间必须大于立项时间"
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
