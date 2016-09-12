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
<link href="resources/css/main.css" rel="stylesheet">
<link href="resources/css/dataTable.css" rel="stylesheet">
<title>Manage Users</title>
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<br />
		<img id="loadingManage" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif"/>
	<br />
	<div class="container page-content">
		<br/><br/>
		<button data-toggle="modal" data-target="#createContributor" style="cursor: pointer;" class="btn btn-primary" aria-hidden="true">
			&nbsp;Add a Contributor
		</button>
		<br/><br/><br/>
		<table id="userTable" class="table table-hover">
			<col width="15%">
	  		<col width="15%">
	  		<col width="15%">
	  		<col width="15%">
	  		<col width="10%">
	  		<col width="15%">
	  		<col width="15%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
	  		<col width="0%">
		  	<thead>
				<tr>
					<th>Picture(Reset)</th>
					<th>Email</th>
					<th>Name</th>
					<th>Title</th>
					<th>Edit</th>
					<th>Activation</th>
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
						<td id="proPic${user.userId}">
							<form action="resetProfile.do" method="post">
								<button id="picButton" name="resetProfile" value="${user.userId}" type="submit">
									<img src="${user.profilePicture}" width="50" height="auto" alt="profile picture" />
								</button>
							</form>
						</td>
						<td id="email${user.userId}"><c:out value="${user.email}" /></td>
						<td><c:out value="${user.lastName}, ${user.firstName}"/></td>
						<td id="job${user.userId}"><c:out value="${user.jobTitle}" /></td>
						<td>
							<span data-toggle="modal" data-target="#editUserProfile" id="${user.userId}" onclick=edit(this.id) style="cursor: pointer;" 
								class="glyphicon glyphicon-edit" aria-hidden="true">
							</span>
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
							<button id="resetPassButton" name="resetPass" value="${user.userId}" type="submit" class="btn btn-primary form-control" data-toggle="modal" data-target="#confirmPasswordMod">Reset Password</button>
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
		<div id="createContributor" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Create Contributor</h3>
					</div>
					<form action="createAccount.do" method="post" >
						<div class="modal-body">
							Email: <input name="email" type="text" class="form-control"/>			
						</div>
					<div class="modal-footer">
						<input id="createUserButton" type="submit" value="Submit Changes" class="btn btn-primary form-control" style="width: auto;" />
						<button id="closeCreateUser" type="button" class="btn btn-secondary" data-dismiss="modal">
							Close
						</button>					
					</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- Confirm Password Modal -->
		<div id="confirmPasswordMod" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
						<h3 class="modal-title">Confirm Password</h3>
					</div>
					<div class="modal-body">
						Are you sure you want to reset this users password?
						<br/>
					</div>
					<img id="loading" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif" />
					<div class="modal-footer">
						<button id="confirmPassword" class="btn btn-primary form-control" style="width: auto;">
							Confirm
						</button>
						<button id="closeCreateUser" class="btn btn-secondary" data-dismiss="modal">
							Deny
						</button>					
					</div>
				</div>
			</div>
		</div>
	
		<!-- Create Contributor Modal -->
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
							<h4>Id</h4>
							<form:input path="userId" id="selectedUserId" class="form-control" readOnly="true" />
							<h4>Email</h4>
							<form:input path="email" id="selectedUserEmail" class="form-control" />
							<h4>First Name</h4>
							<form:input path="firstName" id="selectedUserFirst" class="form-control" />
							<h4>Last Name</h4>
							<form:input path="lastName" id="selectedUserLast" class="form-control" />
							<h4>Title</h4>
							<form:input path="jobTitle" id="selectedUserJob" class="form-control" />
							<h4>LinkedIn</h4>
							<form:input path="linkedInURL" id="selectedLinkedInURL" class="form-control" />
							<h4>Description</h4>
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
	
	$("#loading").hide();
	
	$('#userTable').DataTable({
		
		"dom": 'lftipr',
		"lengthMenu": [[5, 10, 20, -1], [5, 10, 20, "All"]], 
   		"pagingType": "simple_numbers"		
	});
	
	$("#confirmPassword").click(function(){
		
		$("#loading").show();
		
		var id = $("#resetPassButton").val();
		
		$.get("https://localhost:7002/revblogs/resetUserPassword.do?resetPass=" + id, function(response){
		
			$("#loading").hide();
			location.reload();
		});
	});
	
	$("#picButton").click(function(){
		
		$("#loadingManage").show();
		setTimeout(function(){
			$("#loadingManage").hide();
			location.reload();
		}, 10000);
		
	});
});
	
$(document).ready(function(){
	$('#loadingManage').hide();
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
</script>
<!-- <script type="text/javascript" src="resources/js/ui.js"></script> -->
</html>