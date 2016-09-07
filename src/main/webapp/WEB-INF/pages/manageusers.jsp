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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js" ></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="http://blogs.pjw6193.tech/content/resources/css/main.css" rel="stylesheet">
<title>Manage Users</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
	<table id="userTable">
		<thead>
			<tr>
				<td>Profile Picture</td>
				<td>Email</td>
				<td>User Name</td>
				<td>Job Title</td>
				<td>LinkedIn URL</td>
				<td>Edit Profile</td>
				<td>Edit Picture</td>
				<td>Set Active</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user">
				<tr>
					<td><img src="${user.profilePicture}" width="50" height="auto"/></td>
					<td><c:out value="${user.email}" /></td>
					<td><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></td>
					<td><c:out value="${user.jobTitle}" /></td>
					<td><c:out value="${user.linkedInURL}" /></td>
					<td>
						<a data-toggle="modal" data-target="#editUserProfile" role="button" href="#editUserProfile">
							<span class="glyphicon glyphicon-edit"></span>
						</a>
					</td>
					<td>
						<input type="submit" class="btn btn-primary form-control" value="Edit Picture" />
					</td>
					<td>
						<c:choose>
							<c:when test="${user.active}">
								<input type="submit" class="btn btn-primary form-control" value="Set Inactive" />
							</c:when>
							<c:otherwise>
								<input type="submit" class="btn btn-primary form-control" value="Set Active" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- Edit User Profile Modal -->
	<div id="editUserProfile" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-sm">
  			<div class="modal-content">
  				<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	         		<span aria-hidden="true">&times;</span>
	       			</button>
	       			<h4 class="modal-title" id="myModalLabel">Edit User Profile</h4>
     			</div>
   				<div class="modal-body">
   					
   					
   					<div>
						<form:form action="updateUser.do" method="post" commandName="updateUser">
						<table>
						<tr>
							<td>
								Email:&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								<form:input path="email" class="form-control" value="${user.email}"/>
							</td>
						</tr>
						<tr>
							<td>
								First Name:&nbsp;&nbsp;&nbsp; 
							</td>
							<td>
								<form:input path="firstName" class="form-control" value="${user.firstName}"/>
							</td>
						</tr>
						<tr>
							<td>
								Last Name:&nbsp;&nbsp;&nbsp; 
							</td>
							<td>
								<form:input path="lastName" class="form-control" value="${user.lastName}"/>
							</td>
						</tr>
						<tr>
							<td>
								Job Title:&nbsp;&nbsp;&nbsp; 
							</td>
							<td>
								<form:input path="jobTitle" class="form-control" value="${user.jobTitle}"/>
							</td>
						</tr>
						<tr>
							<td>
								LinkedIn URL:&nbsp;&nbsp;&nbsp; 
							</td>
							<td>
								<form:input path="linkedInURL" class="form-control" value="${user.linkedInURL}"/>
							</td>
						</tr>
						<tr>
							<td>
								Description:&nbsp;&nbsp;&nbsp; 
							</td>
							<td>
								<input type="hidden" value="${user.description}" id="userDescription" />
								<form:textarea path="description" class="form-control" id="descriptionTextArea" rows="10"/>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<input type="submit" class="btn btn-primary form-control" value="Update Profile" />
							</td>
						</tr>
						
						</table>
						</form:form>
					</div>
   					
   					
   					
   					
   					
				</div>    		
    			<div class="modal-footer">
    				<input id="editUserButton" type="submit" value="Submit Changes" class="btn btn-primary form-control" style="width:auto;" />
        			<button id="closeEditUser" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>        			
      			</div>
      		</div>
  		</div>
	</div>
	
	
	
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script>
$(document).ready(function() {
    $('#userTable').DataTable();
} );
</script>
</html>