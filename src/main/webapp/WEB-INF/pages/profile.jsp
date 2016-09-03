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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<link href="resources/css/main.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<title>Profile</title>
</head>
<body>

<div class="container page-content">
	<div>
	<h3>Update Profile</h3><br />
		<form:form action="updateUser.do" method="post" commandName="updateUser">
			Email: <form:input path="email" value="${user.email}"/> <br />
			First Name: <form:input path="firstName" value="${user.firstName}"/> <br />
			Last Name: <form:input path="lastName" value="${user.lastName}"/> <br />
			Job Title: <form:input path="jobTitle" value="${user.jobTitle}"/> <br />
			LinkedIn URL: <form:input path="linkedInURL" value="${user.linkedInURL}"/> <br />
			Description: <form:textarea path="description" value="${user.description}"/> <br />
			<input type="submit" value="Update Profile" /> <br />
		</form:form>
	</div>
	<div>
		<h3>Upload Profile Picture</h3>
<%-- 		<form method="POST" action="uploadProfilePicture" enctype="multipart/form-data"> --%>
			<input type="file" name="profilePicture" id="fileChooser" /><br />
			<input class="fileUploadButton" type="submit" name="upload" value="Upload" />
<%-- 		</form> --%>
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
<script type="text/javascript">
$(document).ready(function(){
	$(".fileUploadButton").click(function(e){
		var data = new FormData($("#fileChooser")[0].files);
		
		data.append("profilePicture",$("#fileChooser")[0].files[0]);
		$.ajax({
			url: "uploadProfilePicture",
			data: data,
			contentType: false,
			processData: false,
			type: "POST",
			cache: false,
			success: function(response){
				console.log(response);
			}		
		});
		
		e.preventDefault();
	})

})



</script>
</html>