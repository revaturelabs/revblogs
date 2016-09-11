<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
<body>
	<nav class="page-navigation navbar navbar-default navbar-fixed-top">
	    <div class="container">
	      <div class="navbar-header">
	        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	          <span class="sr-only">Toggle navigation</span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	        </button>
	        <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/"><img src="resources/img/rev-brand.png" alt="revature"/></a>
	      </div>
	
	      <!-- Collect the nav links, forms, and other content for toggling -->
	      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	        <ul id="navs" class="nav navbar-nav">
	          <li id="navhome"><a href="${pageContext.servletContext.contextPath}/">Home</a></li>
	        </ul>
	        <ul id="rightnavs" class="nav navbar-nav navbar-right">
		        <li id="navsearch">
			        <form class="navbar-form navbar-right .hidden-xs">
			          <div class="form-group input-group post-search">
			            <input type="text" class="form-control" placeholder="Search">
			            <span class="input-group-btn">
			              <button class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
			            </span>
			          </div>
			        </form>
		        </li>
			    <li id="navlogin"><a href="${pageContext.servletContext.contextPath}/profile"><span class="glyphicon glyphicon-user"></span>&nbsp;Contributor</a></li>
	        </ul>
	      </div><!-- /.navbar-collapse -->
	    </div><!-- /.container-fluid -->
	</nav>
	<div class="container page-content">
		<h2>Error <c:out value="${pageContext.errorData.statusCode}" /> Occurred</h2>
		<br />
		<br />
		<br />
		<img src="${pageContext.servletContext.contextPath}/resources/images/error.gif" alt="sad penguin"/>
	</div>
	<!-- FOOTER -->
	  <div class="footer">
		    <div class="container">
		      <div class="row">
		        <div class="col-sm-4">
		          <img class="img-responsive" src="resources/img/rev-footer.png" alt="revature" />
		        </div>
		        <div class="col-sm-4">
		        	<h4>Contact Info</h4>
		        	<span class="glyphicon glyphicon-home"></span><span>&nbsp;11730 Plaza America Drive<br>Reston, VA 20190</span><br>
		        	<span class="glyphicon glyphicon-earphone"></span><a href="tel:(703) 570-8282">&nbsp;(703) 570-8282</a><br>
		        	<span class="glyphicon glyphicon-envelope"></span><a href="mailto:info@revature.com">&nbsp;info@revature.com</a><br>
		        	<span class="glyphicon glyphicon-link"></span><a target="_blank" href="http://revature.com">&nbsp;revature.com</a>
		        </div>
		        <div class="col-sm-4">
		        	<a href="${pageContext.servletContext.contextPath}/profile">Contributors - Log In</a><br>
	        		<a href="${pageContext.servletContext.contextPath}/go-logout">Log Out</a><br>
	        		<a href="#">Site map</a>
		        </div>
		      </div>
		    </div>
		</div>
</body>
<script type="text/javascript" src="resources/js/ui.js"></script>
</html>