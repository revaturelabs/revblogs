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
					<td><img src="${user.profilePicture}" /></td>
					<td><c:out value="${user.email}" /></td>
					<td><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></td>
					<td><c:out value="${user.jobTitle}" /></td>
					<td><c:out value="${user.linkedInURL}" /></td>
					<td>
						<input type="submit" class="btn btn-primary form-control" value="Edit Profile" />
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
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>