<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/js/system/transfer/edit.js"></script> --%>

<style type="text/css">
.col-sm-3 {
	width: 12.5%;
	float: left;
}

.col-sm-9 {
	width: 87%;
	float: left;
}
</style>
</head>
<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
	<form id="form" name="form" class="form-horizontal" method="post">
	  <h3 style="text-align: center;font-family:宋体;font-style: inherit;color: blue;letter-spacing: 40px">基本信息</h3>
	  <input type="hidden" class="form-control checkacc" value="${transfer.id}" name="translatorFormMap.id" id="id">
	  <section class="panel panel-default">
		<div class="panel-body">
			<div class="form-group">
				<label class="col-sm-3 control-label">用户名</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="accountName" 
					name="translatorFormMap.accountName" value="${transfer.accountName }">
				</div>
				
				<label class="col-sm-3 control-label">昵称</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="nickname" 
					name="translatorFormMap.nickname" value="${transfer.nickname }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别</label>
				<div class="col-sm-4">
					<input type="radio" value="1" name="translatorFormMap.gender" <c:if test='${transfer.gender==1 }'>checked="checked"</c:if>>女
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="0" name="translatorFormMap.gender" <c:if test='${transfer.gender!=1}'>checked="checked"</c:if>>男
				</div>
				
				<label class="col-sm-3 control-label">出生日期</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="birthday" name="translatorFormMap.birthday" value="${transfer.birthday }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱</label>
				<div class="col-sm-4">
					<input type="text" class="form-control isEmail" id="email" 
					name="translatorFormMap.email" value="${transfer.email }">
				</div>
				
				<label class="col-sm-3 control-label">电话</label>
				<div class="col-sm-4">
					<input type="text" class="form-control isMobile" id="phone" 
					name="translatorFormMap.tel" value="${transfer.tel }">
				</div>
			</div>

			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">学历</label>
				<div class="col-sm-4">
					<select id="degree" name="translatorFormMap.degree" class="selectpicker show-tick form-control">
						<option value="本科" <c:if test="${transfer.degree eq '本科' }">selected</c:if>>本科</option>
						<option value="硕士" <c:if test="${transfer.degree eq '硕士' }">selected</c:if>>硕士</option>
						<option value="博士" <c:if test="${transfer.degree eq '博士' }">selected</c:if>>博士</option>
					</select>
				</div>
				
				<label class="col-sm-3 control-label">专业</label>
				<div class="col-sm-4">
					<select id="majorId" name="translatorFormMap.majorId" class="selectpicker show-tick form-control">
						<c:forEach items="${major }" var="major">
							<option value="${major.id }" <c:if test="${major.id eq transfer.majorId }">selected</c:if> >${major.name }</option>
						</c:forEach>
					</select>
				</div>
				
			</div>
	
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">余额：<span style="color: red">￥</span></label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="wallet" 
					name="translatorFormMap.wallet" value="${transfer.wallet }">
				</div>
				
				<label class="col-sm-3 control-label">拥有积分</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="point" 
					name="translatorFormMap.point" value="${transfer.point }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">使用软件</label>
				<div class="col-sm-4">
					<input type="radio" value="1" name="translatorFormMap.isToolUse" <c:if test='${transfer.isToolUse==1 }'>checked="checked"</c:if>>使用
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="0" name="translatorFormMap.isToolUse" <c:if test='${transfer.isToolUse!=1}'>checked="checked"</c:if>>未使用
				</div>
				
				<label class="col-sm-3 control-label">在线状态</label>
				<div class="col-sm-4">
					<input type="radio" value="1" name="translatorFormMap.onlineState" <c:if test='${transfer.onlineState==1 }'>checked="checked"</c:if>>不在线
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="2" name="translatorFormMap.onlineState" <c:if test='${transfer.onlineState!=1}'>checked="checked"</c:if>>在线
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">信誉值</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="honor" 
					name="translatorFormMap.honor" value="${transfer.honor }">
				</div>
				
				<label class="col-sm-3 control-label">排名值</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="rank" 
					name="translatorFormMap.rank" value="${transfer.rank }">
				</div>
				
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">密保问题</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="problem" 
					name="translatorFormMap.problem" value="${transfer.problem }">
				</div>
				
				<label class="col-sm-3 control-label">密保答案</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="answer" 
					name="translatorFormMap.answer" value="${transfer.answer }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">住址</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="address" 
					name="translatorFormMap.address" value="${transfer.address }">
				</div>
			</div>
			
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">注册时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="registerTime" 
					name="translatorFormMap.registerTime" value="${transfer.registerTime }">
				</div>
			</div>
			
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">简介</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="description" 
					name="translatorFormMap.description" value="${transfer.description }">
				</div>
			</div>
		</div>
		<footer class="panel-footer text-right bg-light lter">
		<button type="button" onclick="checkSubmit()" class="btn btn-success btn-s-xs">保存</button>
		</footer> 
	  </section>
	</form>
	
	
	<!-- 认证表单 -->
	<form id="formCre" name="formCre" class="form-horizontal" method="post">
	  <h3 style="text-align: center;font-family:宋体;font-style: inherit;color: blue;letter-spacing: 40px">认证信息</h3>
	  <c:if test="${transVerifyFromMap.realName !=null && transVerifyFromMap.realName!='' }">
	  	  <input type="hidden" class="form-control checkacc" value="${transfer.id}" name="transVerifyFromMap.translatorId" id="translatorId">
		  <input type="hidden" class="form-control checkacc" value="${transVerifyFormMap.id}" name="transVerifyFromMap.id">
		  <section class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-3 control-label">真实姓名</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="realName" name="transVerifyFormMap.realName" value="${transVerifyFromMap.realName }">
					</div>
					
					<label class="col-sm-3 control-label">证件号</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="nameid" name="transVerifyFormMap.IdCard" value="${transVerifyFromMap.IdCard }">
					</div>
				</div>
				
				<div class="line line-dashed line-lg pull-in"></div>		
				<div class="form-group">
					<label class="col-sm-3 control-label">手持证件照（正面）</label>
					<div class="col-sm-4">
						<c:forEach var="frontUrlFile" items="${frontUrlMap}">
							<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${frontUrlFile.value}&fileURL=${frontUrlFile.key}"><img src="${transVerifyFromMap.frontUrl}" width="80px" height="60px"/></a>
							<input type='hidden' value="${frontUrlFile.key}" id="fileUrel">
							<input type='hidden' value="${frontUrlFile.value}" id="fileName">
						</c:forEach>
					</div>
					
					<label class="col-sm-3 control-label">手持证件照（背面）</label>
					<div class="col-sm-4">
						<c:forEach var="reverseUrlFile" items="${reverseUrlMap}">
							<a href="${pageContext.request.contextPath}/clientOrder/file_download.shtml?urlname=${frontUrlFile.value}&fileURL=${frontUrlFile.key}"><img src="${transVerifyFromMap.reverseUrl}" width="80px" height="60px"/></a>
							<input type='hidden' value="${reverseUrlFile.key}" id="fileUrel">
							<input type='hidden' value="${reverseUrlFile.value}" id="fileName">
						</c:forEach>
					</div>
					
				</div>
				
			</div>
			<footer class="panel-footer text-right bg-light lter">
			  <c:if test="${transfer.certificationStatus==1 || transfer.certificationStatus==3 }">
				<button type="button" onclick="verify('yes')" id="back" class="btn btn-success btn-s-xs">审核通过</button>
				<button type="button" onclick="verify('no')" id="back" class="btn btn-success btn-s-xs">审核未通过</button>
			  </c:if>
			  <c:if test="${transfer.certificationStatus==2 }">
			  	<button type="button" onclick="editCre()" class="btn btn-success btn-s-xs">保存</button>
			  </c:if>
			</footer> 
		  </section>
	  </c:if>
	  <c:if test="${transVerifyFromMap.realName ==null || transVerifyFromMap.realName=='' }">
	  	<div class="line line-dashed line-lg pull-in"></div>		
	  		<h2 style="text-align: center;font-family:楷体;font-style: inherit;color:#d0d0d0 ;letter-spacing: 20px">未认证</h2>
	  	<div class="line line-dashed line-lg pull-in"></div>		
	  	
	  </c:if>
	</form>
	<script type="text/javascript">
	/* onloadurl(); */
	function checkSubmit(){
		if(""!=$("#nickname").val()){
			$.post("${pageContext.request.contextPath}/transfer/isExistClientNickname.shtml",{"nickname":$("#nickname").val(),"id":$("#id").val()},function(date){
				if(date.result=='success'){
					if(""!=$("#phone").val()){
						var ee=/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
						if(ee.test($("#phone").val())){
							alert(1);
							$.post("${pageContext.request.contextPath}/transfer/isClientEditPhone.shtml",{"phone":$("#phone").val(),"id":$("#id").val()},function(date){
								if(date.result=='success'){
									alert(1);
									if(""!=$("#email").val()){
										var ee=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
										if(ee.test($("#email").val())){
											$.post("${pageContext.request.contextPath}/transfer/emileExist.shtml",{"email":$("#email").val(),"id":$("#id").val()},function(date){
												if(date.result=='success'){
													 //alert("提交");
													 postFunction();
												}else{
													alert("邮箱已存在")
												}
											},'json')
										}else{
											alert("邮箱格式错误");
										}
									}else{
										 postFunction();
									}
								}else{
									alert("手机号已存在")
								}
							},'json')
						}else{
							alert("手机号格式不正确");
						}
					}else{
						if(""!=$("#email").val()){
							var ee=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
							if(ee.test($("#email").val())){
								$.post("${pageContext.request.contextPath}/transfer/emileExist.shtml",{"email":$("#email").val(),"id":$("#id").val()},function(date){
									if(date.result=='success'){
										//alert("提交");
										 postFunction();
									}else{
										alert("邮箱已存在")
									}
								},'json')
							}else{
								alert("邮箱格式错误");
							}
						}else{
							//alert("提交");
							 postFunction();
						}
					}
				}else{
					alert("译员昵称已存在");
				}
			},'json')
			}else{
			if(""!=$("#phone").val()){
				var ee=/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
				if(ee.test($("#phone").val())){
					$.post("${pageContext.request.contextPath}/transfer/isClientEditPhone.shtml",{"phone":$("#phone").val(),"id":$("#id").val()},function(date){
						if(date.result=='success'){
							alert("1");
							if(""!=$("#email").val()){
								var ee=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
								if(ee.test($("#email").val())){
									$.post("${pageContext.request.contextPath}/transfer/emileExist.shtml",{"email":$("#email").val(),"id":$("#id").val()},function(date){
										if(date.result=='success'){
											//alert("1");
											 postFunction();
										}else{
											alert("邮箱已存在")
										}
									},'json')
								}else{
									alert("邮箱格式错误");
								}
							}else{
								//alert("提交");
								 postFunction();
							}
						}else{
							alert("手机号已存在")
						}
					},'json')
				}else{
					alert("手机号格式不正确");
				}
			}else{
				if(""!=$("#email").val()){
					var ee=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
					if(ee.test($("#email").val())){
						$.post("${pageContext.request.contextPath}/transfer/emileExist.shtml",{"email":$("#email").val(),"id":$("#id").val()},function(date){
							if(date.result=='success'){
								//alert("提交");
								 postFunction();
							}else{
								alert("邮箱已存在")
							}
						},'json')
					}else{
						alert("邮箱格式错误");
					}
				}else{
					//alert("提交");
					 postFunction();
					
				}
			}
		}
			
	}
	
	//修改译员基本信息
	function postFunction(){
		var searchParams = $("#form").serializeJson();
		$.post('${pageContext.request.contextPath}/transfer/editClientTrans.shtml',
				searchParams
				,function(data){
			if (data == "success") {
				layer.confirm('更新成功!是否关闭窗口?', function(index) {
					parent.grid.loadData();
					parent.layer.close(parent.pageii);
					return false;
				});
			} else {
				layer.msg('更新失败！', 3);
			}
		},'json')
	}
	
	//修改译员认证信息
	function editCre(){
		var searchParams = $("#formCre").serializeJson();
		$.post('${pageContext.request.contextPath}/transfer/editClientTransCre.shtml',
				searchParams
				,function(data){
			if (data == "success") {
				layer.confirm('更新成功!是否关闭窗口?', function(index) {
					parent.grid.loadData();
					parent.layer.close(parent.pageii);
					return false;
				});
			} else {
				layer.msg('更新失败！', 3);
			}
		},'json')
	}	
		
		
		
</script>
	<script type="text/javascript">
	//实名认证审核是否通过
	function verify(obj){
		$.post("${pageContext.request.contextPath}/transfer/transClientCRE.shtml","translatorId="+$("#translatorId").val()+"&type="+obj,function(data){
   			if(data=='success'){
   				layer.confirm('审核信息成功!是否关闭窗口?', function(index) {
					parent.grid.loadData();
					parent.layer.close(parent.pageii);
					return false;
				});
   			}else{
   				layer.alert('审核信息失败！', 3);
   			}
   		},'json'); 
    }
	</script>
</body>
</html>