<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logged In</title>
</head>
<body>
	<h1>${title}</h1>
	<h2>${message}</h2>
	
	<sec:authorize access="hasRole('ADMIN')" >Welcome Admin!</sec:authorize>
	<sec:authorize access="hasRole('CONTRIBUTOR')" >Welcome Contributor!</sec:authorize>
</body>
</html>