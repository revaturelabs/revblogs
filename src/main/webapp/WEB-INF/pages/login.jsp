<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RevBlogs || Login</title>
</head>
<body>
	<img src="${pageContext.servletContext.contextPath}/images/RevLogo.jpg">
	<div id="login">
		Username: <input type="text" id="userAuth"/>
		Password: <input type="password" id="passAuth"/>
		<input type="button" id="sendAuth" value="submit"/>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#sendAuth").click(function(){
	
		$("#sendAuth").prop("disabled", true);
		
		var user = $("#userAuth").val();
		var pass = $("#passAuth").val();
		
		$.ajax({
			url: "getAuth.do",
		  	type: "POST",
		  	data: { 
		   		username: user, 
		      	password: pass
		  	},
		  	success: function (authorized) { 
		  		
		  		if(authroized){
		  		
		  			$("#login").replaceWith("Access Granted!");
		  		
		  		} else {
		  			
		  			$("#login").replaceWith("Access Denied!");
		  		}
		  	}
		});
	});
});
</script>
</html>