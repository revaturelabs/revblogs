<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>
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
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="http://blogs.pjw6193.tech/content/resources/css/main.css" rel="stylesheet">
<title>Manage Users</title>
<link rel="blogs.pjw6193.tech/content/resources/img/favicon.png" href="blogs.pjw6193.tech/content/resources/img/favicon.png"/>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<div class="container page-content">
		<br/><br/><br/><br/><br/>
		<table id="userTable">
			<thead>
				<tr>
					<th>Profile Picture</th>
					<th>Email</th>
					<th>User Name</th>
					<th>Job Title</th>
					<th>Edit Profile</th>
					<th>Edit Picture</th>
					<th>Set Active</th>
					<th>Reset Password</th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
					<th hidden></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user">
					<tr id="${user.userId}">
						<td id="proPic${user.userId}"><img src="${user.profilePicture}" width="50" height="auto" /></td>
						<td id="email${user.userId}"><c:out value="${user.email}" /></td>
						<td> <c:out value="${user.lastName}, ${user.firstName}"/></td>
						<td id="job${user.userId}"><c:out value="${user.jobTitle}" /></td>
						<td>
							<span data-toggle="modal" data-target="#editUserProfile" id="${user.userId}" onclick=edit(this.id) style="cursor: pointer;" 
								class="glyphicon glyphicon-edit" aria-hidden="true">
							</span>
						</td>
						<td>
							<form action="resetProfile.do" method="post">
								<button name="resetProfile" value="${user.userId }" type="submit" class="btn btn-primary form-control" >Reset Picture</button>
							</form>
						</td>
						<td><c:choose>
								<c:when id="activeTest" test="${user.active}">
									<form action="deactivateUser.do" method="post">
										<button name="deactivate" value="${user.userId}" type="submit" class="btn btn-primary form-control">Deactivate</button>
									</form>
								</c:when>
								<c:otherwise>
									<form action="activateUser.do" method="post">
										<button name="activate" value="${user.userId}" type="submit" class="btn btn-primary form-control">Activate</button>
									</form>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<form action="resetUserPassword.do" method="post">
								<button name="resetPass" value="${user.userId}" type="submit" class="btn btn-primary form-control" >Reset Password</button>
							</form>
						</td>
						<td id="userId${user.userId}" hidden><c:out value="${user.userId}" /></td>
						<td id="password${user.userId}" hidden><c:out value="${user.password}" /></td>
						<td id="description${user.userId}" hidden><c:out value="${user.description}" /></td>
						<td id="firstName${user.userId}" hidden><c:out value="${user.firstName}" /></td>
						<td id="lastName${user.userId}" hidden><c:out value="${user.lastName}" /></td>
						<td id="link${user.userId}" hidden><c:out value="${user.linkedInURL}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br/><br/><br/><br/><br/>


		<!-- Edit User Profile Modal -->
		<div id="editUserProfile" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Edit User Profile</h3>
					</div>
					<form:form action="updateUserProfile.do" method="post" commandName="updateUserProfile">
						<div class="modal-body">
							<form:hidden path="password" id="selectedUserPassword" class="form-control" />
							<form:input path="userId" id="selectedUserId" class="form-control" readOnly="true" />
							<form:input path="email" id="selectedUserEmail" class="form-control" />
							<form:input path="firstName" id="selectedUserFirst" class="form-control" />
							<form:input path="lastName" id="selectedUserLast" class="form-control" />
							<form:input path="jobTitle" id="selectedUserJob" class="form-control" />
							<form:input path="linkedInURL" id="selectedLinkedInURL" class="form-control" />
							<form:textarea path="description" id="selectedUserDesc" class="form-control" />				
						</div>
					<div class="modal-footer">
						<input id="editUserButton" type="submit" value="Submit Changes" class="btn btn-primary form-control" style="width: auto;" />
						<button id="closeEditUser" type="button" class="btn btn-secondary" data-dismiss="modal">
							Close
						</button>					
					</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script>
	$(document).ready(function() {
		$('#userTable').DataTable();
	});
	
function edit(userId){
	$("#selectedUserId").val($("#userId" + userId).html());
	$("#selectedUserEmail").val($("#email" + userId).html());
	$("#selectedUserFirst").val($("#firstName" + userId).html());
	$("#selectedUserLast").val($("#lastName" + userId).html());
	$("#selectedUserJob").val($("#job" + userId).html());
	$("#selectedUserDesc").val($("#description" + userId).html());
	$("#selectedUserPassword").val($("#password" + userId).html());
	$("#selectedLinkedInURL").val($("#link" + userId).html());
	
}

// function resetPicture(userId){
	
// }

// function deactivateUser(userId){
// 	$.ajax({
// 			url: "deactivateUser.do",
// 			method: "POST",
// 			data : { 'userId'  : userId},
// 			success: function(success){
				
// 			}
// 	});
// }

// function activateUser(userId){
// 	$.ajax({
// 			url: "activateUser.do",
// 			method: "POST",
// 			data : { 'userId'  : userId},
// 			success: function(success){
				
// 			}
// 	});
// }
	
</script>
<!-- <script type="text/javascript" src="resources/js/ui.js"></script> -->
</html>