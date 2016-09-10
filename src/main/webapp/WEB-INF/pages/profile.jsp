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
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<script src="${pageContext.servletContext.contextPath}/resources/js/validatePassword.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>Profile</title>
<link rel="shortcut icon" type="image/png" href="blogs.pjw6193.tech/content/resources/img/favicon.png"/>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
	<br />
	<div id="rightColumn">
		<h3>Update Profile</h3><br />
		<form:form action="updateUser.do" method="post" commandName="updateUser">
		<table>
		<tr>
			<td>
				Email:&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<form:input path="email" class="form-control update-form" value="${user.email}"/>
			</td>
		</tr>
		<tr>
			<td>
				First Name:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="firstName" class="form-control update-form" value="${user.firstName}"/>
			</td>
		</tr>
		<tr>
			<td>
				Last Name:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="lastName" class="form-control update-form" value="${user.lastName}"/>
			</td>
		</tr>
		<tr>
			<td>
				Job Title:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="jobTitle" class="form-control update-form" value="${user.jobTitle}"/>
			</td>
		</tr>
		<tr>
			<td>
				LinkedIn URL:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="linkedInURL" class="form-control update-form" value="${user.linkedInURL}"/>
			</td>
		</tr>
		<tr>
			<td>
				Description:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<input type="hidden" value="${user.description}" id="userDescription" />
				<form:textarea path="description" class="form-control update-form" id="descriptionTextArea" rows="10"/>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				<input type="submit" class="btn btn-primary update-btn" value="Update Profile" />
			</td>
		</tr>
		
		</table>
		</form:form>
		<br />
		<c:if test="${userUpdate eq 'update'}">
			<span id=userUpdate class="label-success success-span">Profile updated successfully.</span>
		</c:if>
	</div>	
	<br />
	
	<br />
	
	<div id="leftColumn">
		<h3>Upload Profile Picture</h3>
			<a href="#profilePicModal" role="button" data-toggle="modal">
				<img id="profilePic" src="${user.profilePicture}" width="128px" height=auto/>
			</a>

	<br />
	<br />
	<hr>
	<h3>Update Password</h3>
	<br />
	<table>
		<tr>
			<td colspan=2>
				<a href="${pageContext.servletContext.contextPath}/password"><input type="button" value="Update Password" class="btn btn-primary form-control"></input></a>
			</td>
		</tr>
	</table>
	<br />
	<br />
	</div>
	<br />
	<c:if test="${passwordSuccess eq 'success'}">
		<span id="password-success" class="label-success success-span">Password changed successfully.</span>
	</c:if>
	<br />

	
	<div id="profilePicModal" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-sm">
  			<div class="modal-content">
  				<div class="modal-header">
  					<label id="picSuccess" class="label-success"></label>
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	         		<span aria-hidden="true">&times;</span>
	       			</button>
	       			<h4 class="modal-title" id="myModalLabel">Upload Profile Picture</h4>
     			</div>
   				<div class="modal-body">
   					<img id="newProfilePic" src="${user.profilePicture}" width="128px" height=auto/>
   					<br />
     				<input type="file" name="profilePicture" id="fileChooser" /><br />
				</div>
    		
    			<div class="modal-footer">
    				<input type="submit" value="Upload" class="btn btn-primary form-control" style="width:auto;" id="fileUploadButton"/>
        			<button id="closeProfilePic" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>        			
      			</div>
      		</div>
  		</div>
	</div>
</div>
<br />
<br />
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="resources/js/ui.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var currentPicture = '${user.profilePicture}';
	console.log(currentPicture);

	$("#fileChooser").change(function(event){
		newPicture = URL.createObjectURL(event.target.files[0]);
		$("#newProfilePic").attr("src", newPicture);
		
		$("#fileUploadButton").click(function(e){
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
					
					if(response == "Success"){
					alert("Picture Uploaded Successfully!");
					location.reload();
					}
				}		
			});
			
			e.preventDefault();
			
		});
		
		$("#closeProfilePic").click(function(event){
			$("#fileChooser").val("");	
		});
	});
	
	$("#closeProfilePic").click(function(event){
 		$("#newProfilePic").attr("src", currentPicture);
		$("#fileChooser").val("");	
	});

});

$(document).ready(function(){
	var description = $("#userDescription").val();
	
	$("#descriptionTextArea").val(description);
});

$(document).ready(function(){
	$('#password-success').delay(2000).fadeOut('slow');
});

$(document).ready(function(){
	$('#userUpdate').delay(2000).fadeOut('slow');
})

</script>
</html>