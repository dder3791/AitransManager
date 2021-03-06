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
			"articleFormMap.title" : {
				required : true,
			},
			"articleFormMap.context" : {
				required : true,
			},
			"articleFormMap.auther" : {
				required : true,
			},
			"articleFormMap.issueDate" : {
				required : true,
			}
		},
		messages : {
			"article.title" : {
				required : "请输入资讯标题",
			},
			"article.context" : {
				required : "请输入资讯内容",
			},
			"articleFormMap.auther" : {
				required : "请填写发布作者",
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
