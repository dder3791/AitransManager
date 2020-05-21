<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<%@include file="/common/common.jspf"%>
<script type="text/javascript" src="${ctx}/js/system/transfer/show_myInfor.js"></script>

</head>

<body>
	<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
		<table align="center" class="table table-striped">
			<tr>
				<td><label>账号&nbsp;&nbsp;</label> <label id="accountName" style=""></label></td>
				<td><label>昵称&nbsp;&nbsp;</label> <label id="nickname"></label></td>
				<td colspan="2"><label>真实姓名&nbsp;&nbsp;</label> <label id="realName"></label></td>
			    <td><label>积分情况&nbsp;&nbsp;</label><label id="point"></label>
			</tr>
			<tr>
				<td><label>学历&nbsp;&nbsp;</label> <label id="degree"></label></td>
				<td><label>专业&nbsp;&nbsp;</label> <label id="major"></label></td>
				<td><label>翻译领域&nbsp;&nbsp;</label> <label id="domain"></label></td>
				<td><label>等级&nbsp;&nbsp;</label> <label id="level"></label></td>
			</tr>
			<tr>
				<td><label class="isMobile">电话&nbsp;&nbsp;</label> <label id="phone"></label></td>
				<td><label>邮箱&nbsp;&nbsp;</label> <label id="email"></label></td>
				<td><label>住址&nbsp;&nbsp;</label> <label id="address"></label></td>
				<td><label>生日&nbsp;&nbsp;</label> <label id="birthday"></label></td>
			</tr>
			
		</table>
	<button type="submit" class="btn btn-success btn-s-xs" id="editFun">编辑</button>
</body>
</html>
