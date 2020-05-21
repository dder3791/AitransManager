<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>文件下载</title>
</head>
<body>
<h3>文件下载</h3><hr color="blue">
<center>
<table border="1">
<tr>
	<td>文件名</td>
	<td>操作</td>
</tr>
<%-- <c:forEach var="ff" items="${fileNameMap}">
<tr>
	<td>
		${ff.value}
		<c:url value="project_download.shtml" var="downurl">
         <c:param name="urlname" value="${ff.value}"></c:param>
         <c:param name="fileURL" value="${ff.key}"></c:param>
        </c:url>
	</td>
	<td><a href="${downurl}">点击下载</a></td>
</tr>
</c:forEach> --%>
<c:forEach var="ff" items="${listFile}">
<tr>
	<td>
		${ff.fileName}
		<c:url value="project_download.shtml" var="downurl">
         <c:param name="urlname" value="${ff.fileName}"></c:param>
         <c:param name="fileURL" value="${ff.fileUrl}"></c:param>
         <c:param name="fileFormat" value="${ff.fileFormat}"></c:param>
        </c:url>
	</td>
	<td><a href="${downurl}">点击下载</a></td>
</tr>
</c:forEach>
</table>
</center>

<%-- 
 <c:forEach var="ff" items="${fileNameMap}">
19     <c:url value="/term/download2.shtml" var="downurl">
20         <c:param name="urlname" value="${ff.key}"></c:param>
21     </c:url>
22     ${ff.value}<a href="${downurl}">点击下载</a>
23     <br/>
24 </c:forEach>
 --%>

</body>
</html>