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

<c:if test="${user.newUser eq true}">
<div class="container page-content">
	<div>
		<div >
			<h3>Update Password</h3> <br />
			<form>
			<table>
			<tr>
				<td>
					Old Password:
				</td>
				<td> 
					<input type="password" name="oldPassword" id="oldPassword" required />
				</td>
			</tr>
			<tr>
				<td>
					New Password: 
				</td>
				<td>
					<input type="password" name="newPassword" id="newPassword" required/>
				</td>
			</tr>
			<tr>
				<td>
					Confirm Password:
				</td>
				<td> 
					<input type="password" name="confirmPassword" id="confirmPassword" required
					onkeyup="validatePassword()"/> <span id="confirmMessage" />
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input type="submit" name="changePassword" id="changePassword" value="Update Password" />
				</td>
			</tr>
			</table>
			</form>
		</div>
	</div>
</div>
</c:if>

<c:if test="${user.newUser eq false}">
<div class="container page-content">
	<div>
	<h3>Update Profile</h3><br />
		<form:form action="updateUser.do" method="post" commandName="updateUser">
		<table>
		<tr>
			<td>
				Email:
			</td>
			<td>
				<form:input path="email" value="${user.email}"/>
			</td>
		</tr>
		<tr>
			<td>
				First Name: 
			</td>
			<td>
				<form:input path="firstName" value="${user.firstName}"/>
			</td>
		</tr>
		<tr>
			<td>
				Last Name: 
			</td>
			<td>
				<form:input path="lastName" value="${user.lastName}"/>
			</td>
		</tr>
		<tr>
			<td>
				Job Title: 
			</td>
			<td>
				<form:input path="jobTitle" value="${user.jobTitle}"/>
			</td>
		</tr>
		<tr>
			<td>
				LinkedIn URL: 
			</td>
			<td>
				<form:input path="linkedInURL" value="${user.linkedInURL}"/>
			</td>
		</tr>
		<tr>
			<td>
				Description: 
			</td>
			<td>
				<form:textarea path="description" value="${user.description}"/>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<input type="submit" value="Update Profile" />
			</td>
		</tr>
		</table>
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
			<table>
			<tr>
				<td>
					Old Password:
				</td>
				<td> 
					<input type="password" name="oldPassword" id="oldPassword" required />
				</td>
			</tr>
			<tr>
				<td>
					New Password: 
				</td>
				<td>
					<input type="password" name="newPassword" id="newPassword" required/>
				</td>
			</tr>
			<tr>
				<td>
					Confirm Password:
				</td>
				<td> 
					<input type="password" name="confirmPassword" id="confirmPassword" required
					onkeyup="validatePassword()"/> <span id="confirmMessage" />
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input type="submit" name="changePassword" id="changePassword" value="Update Password" />
				</td>
			</tr>
			</table>
			</form>
		</div>
	</div>
</div>
</c:if>
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
	});

});

$(document).ready(function(){
	$("#confirmPassword").keyup(function(){
		var newPass = $("#newPassword").val();
		var conPass = $("#confirmPassword").val();
		
		if(newPass !== conPass){
			
			$("#changePassword").attr("disabled","true");
			$("#confirmMessage").text("Passwords do not match.");
			$("#confirmMessage").css("color", "red");
		}
		
		else{
			
			$("#changePassword").attr("disabled","false");
			$("#confirmMessage").text("");
			$("#confirmMessage").css("color", "black");
			
		}
		
			
	});

})



</script>
</html>