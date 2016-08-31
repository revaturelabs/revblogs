<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body>
	<h2>Error <c:out value="${pageContext.errorData.statusCode}" /> Occurred</h2>
	<br />
	<br />
	<br />
	<img src="${pageContext.servletContext.contextPath}/resources/images/error.gif"/>
</body>
</html>