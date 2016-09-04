<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<title>Logged In</title>
</head>
<body>
	<h1>${title}</h1>
	<h2>${message}</h2>
	
	<sec:authorize access="hasRole('ADMIN')" >Welcome Admin!</sec:authorize>
	<c:if test="${user.userRole.role eq 'ADMIN'}">
		<a href="${pageContext.servletContext.contextPath}/profile">Profile</a>
		<a href="${pageContext.servletContext.contextPath}/makeClientAccount">Add a Client</a>
		<a href="${pageContext.servletContext.contextPath}/create-blog">Create a Blog</a>
		<a href="${pageContext.servletContext.contextPath}/">View Blogs</a>
		<a href="${pageContext.servletContext.contextPath}/go-logout">Logout</a>
		
		
	</c:if>
	<sec:authorize access="hasRole('CONTRIBUTOR')" >Welcome Contributor!</sec:authorize>
	<c:if test="${user.userRole.role eq 'CONTRIBUTOR'}">
		<a href="${pageContext.servletContext.contextPath}/profile">Profile</a>
		<a href="${pageContext.servletContext.contextPath}/create-blog">Create a Blog</a>
		<a href="${pageContext.servletContext.contextPath}/">View Blogs</a>
		<a href="${pageContext.servletContext.contextPath}/go-logout">Logout</a>
	</c:if>
</body>
</html>