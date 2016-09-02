<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.servletContext.contextPath }/resources/js/angular.min.js"></script>
<script src="${pageContext.servletContext.contextPath }/resources/js/angular-route.js"></script>
<script src="${pageContext.servletContext.contextPath }/resources/js/validatePassword.js"></script>
<link href="resources/css/main.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
	<div>
	<h3>Update Profile</h3><br />
		<form:form action="updateUser.do" method="post" commandName="updateUser">
			Username: <form:input path="username" /> <br />
			Email: <form:input path="email" /> <br />
			First Name: <form:input path="firstName" /> <br />
			Last Name: <form:input path="lastName" /> <br />
			Job Title: <form:input path="jobTitle" /> <br />
			LinkedIn URL: <form:input path="linkedInURL" /> <br />
			Description: <form:textarea path="description" /> <br />
			<input type="submit" value="Update Profile" /> <br />
		</form:form>
	</div>
	<div>
		<h3>Upload Profile Picture</h3>
		<form method="POST" action="uploadProfilePicture" enctype="multipart/form-data">
			<input type="file" name="profilePicture" id="fileChooser" /><br />
			<input type="submit" name="upload" value="Upload" />
		</form>
	</div>
	<div>
		<div >
			<h3>Update Password</h3> <br />
			<form>
				Password: <input type="password" name="oldPassword" id="oldPassword" required /> <br />
				Confirm Password: <input type="password" name="newPassword" id="newPassword" required
					onkeyup="validatePassword(); return false;"/> <span id="confirmMessage" /><br />
				<input type="submit" name="changePassword" value="Update Password" />
			</form>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>