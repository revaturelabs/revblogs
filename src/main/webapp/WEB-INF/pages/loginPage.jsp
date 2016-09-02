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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>RevBlogs || Login</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
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
	<img src="${pageContext.servletContext.contextPath}/resources/images/RevLogo.jpg" alt="Revature Logo" width="318" height="72"/>
	<br><br>
	<div id="login">
	<form name="loginForm" action="${pageContext.request.contextPath}/login" method="post">
		Username: <input type="text" id="userAuth" name="username"/><br/>
		Password: <input type="password" id="passAuth" name="password"/>
		<hr/>
		<input type="submit" name="submit" value="Submit"/>
	</form>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>