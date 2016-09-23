<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>Revature Blogs Home</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="overall-container">
<div class="container page-content content-padding">
	<!-- place change form here. Have a post call, change it, 
	update it and the newUser parameter, and then redirect 
	to page with reload. -->
	
	<c:if test="${user.newUser eq true}">
		<c:redirect url="/password"/>
	</c:if>
	<div id="home-page-content">
		<div class="row">
			<div class="col-xs-1"></div>
			<div class="col-xs-4">
				<h1 style="color:#444444">Dashboard</h1>
			</div>
			<div class="col-xs-7"></div>
		</div>
		<div class="row">
			<div class="col-xs-2"></div>
			<div class="col-xs-4">
				<c:choose>
					<c:when test="${user.firstName ne ' ' and user.lastName ne ' '}">
						<h2><a href="create-blog"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Create Blog</a></h2>
					</c:when>
					<c:otherwise>
						<h2><a href="profile"><span class="glyphicon glyphicon-alert"></span>&nbsp;Who are you?</a></h2>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-xs-4">
				<h2><a href="profile"><span class="glyphicon glyphicon-wrench"></span>&nbsp;Profile</a></h2>
			</div>
			<div class="col-xs-2"></div>
		</div>
		<div class="row">
			<div class="col-xs-2"></div>
			<div class="col-xs-4">
				<h2><a href="user-blogs"><span class="glyphicon glyphicon-eye-open"></span>&nbsp;My Blogs</a></h2>
			</div>
			<div class="col-xs-4">
				<h2><a href="all-blogs"><span class="glyphicon glyphicon-book"></span>&nbsp;All Blogs</a></h2>
			</div>
			<div class="col-xs-2"></div>
		</div>
		<c:if test="${user.userRole.role eq 'ADMIN'}">
			<div class="row">
				<div class="col-xs-2"></div>
				<div class="col-xs-4">
					<h2><a href="manageusers"><span class="glyphicon glyphicon-user"></span>&nbsp;Manage Users</a></h2>
				</div>
				<div class="col-xs-4">
					<h2><a href="manage-S3"><span class="glyphicon glyphicon-remove"></span>&nbsp;Manage Bucket</a></h2>
				</div>
				<div class="col-xs-2"></div>
			</div>
		</c:if>
		<div class="row">
			<div class="col-xs-2"></div>
			<div class="col-xs-4 offset-xs-2">
				<h2><a href="go-logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Logout</a></h2>
			</div>
			<div class="col-xs-4">
			</div>
			<div class="col-xs-2"></div>
		</div>
	</div>
</div>
<div class="footer2">
	<jsp:include page="footer.jsp"></jsp:include>
</div>
</div>
</body>
	<script type="text/javascript" src="resources/js/ui.js"></script>
</html>