<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>Logged In</title>
<link rel="shortcut icon" type="image/png" href="blogs.pjw6193.tech/content/resources/img/favicon.png"/>
<style type="text/css">
.scroll{
	height: 422px;
	overflow-y: auto;
}
</style>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content scroll">
	<table align="center">
		<tr>
			<th>File</th>
			<th>Delete</th>
		</tr>
		<c:forEach items="${list}" var="name">
			<form:form action="${pageContext.servletContext.contextPath}/deleteFile" method="get" commandName="blog">
				<tr>
					<td><a href="http://blogs.pjw6193.tech/${name}"><c:out value="${name}"></c:out></a></td>
					<td><form:hidden path="blogTitle" value="${name}"/><form:button name="delete">Delete</form:button></td>
				</tr>
			</form:form>
		</c:forEach>
	</table>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="resources/js/ui.js"></script>
</html>