<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RevBlogs || Login</title>
</head>
<body>
	<c:if test="${'fail' eq param.auth}">
		<div>
              <h3>Failed to login. Please try again.</h3>
        </div>
	</c:if> 
	<c:if test="${'logout' eq param.auth}">
		<div>
			<h3>You have successfully signed out.</h3>
		</div>
	</c:if>

<br />
<br />
<br />
	<img src="${pageContext.servletContext.contextPath}/resources/images/RevLogo.jpg" alt="Revature Logo" width="318" height="72"/>
	<div id="login">
	<form name="loginForm" action="${pageContext.request.contextPath}/login" method="post">
		Username: <input type="text" id="userAuth" name="username"/><br/>
		Password: <input type="password" id="passAuth" name="password"/>
		<hr/>
		<input type="submit" name="submit" value="Submit"/>
	</form>
	</div>
</body>
</html>