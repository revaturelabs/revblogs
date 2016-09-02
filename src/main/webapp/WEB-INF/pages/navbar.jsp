<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.8/angular.min.js"></script>
</head>
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
        <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/"><img src="resources/img/rev-brand.png" /></a>
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
	        <li id="navuser" class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
	        	aria-haspopup="true" aria-expanded="false">
	        	<span class="glyphicon glyphicon-user"></span>
	        	User <span class="caret"></span></a>
	        	<ul class="dropdown-menu">
	        		<li><a href=#><span class="glyphicon glyphicon-pencil"></span>&nbsp;New blog entry</a></li>
	        		<li role="separator" class="divider"></li>
            		<li><a href="#"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Log out</a></li>
	        	</ul>
	        </li>
	        <li id="navadmin" class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
	        	aria-haspopup="true" aria-expanded="false">
	        	<span class="glyphicon glyphicon-user"></span>
	        	Admin <span class="caret"></span></a>
	        	<ul class="dropdown-menu">
	        		<li><a href=#><span class="glyphicon glyphicon-wrench"></span>&nbsp;Manage entries</a></li>
	        		<li><a href=#><span class="glyphicon glyphicon-wrench"></span>&nbsp;Manage Users</a></li>
	        		<li role="separator" class="divider"></li>
            		<li><a href="#"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Log out</a></li>
	        	</ul>
	        </li>
	        <li id="navlogin"><a href="${pageContext.servletContext.contextPath}/loginPage"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Log in</a></li>
        </ul>
      </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
  </nav>
  <div class="container visible-xs-block" style="margin-top: 80px">
  	<form>
       <div class="form-group input-group post-search">
         <input type="text" class="form-control" placeholder="Search">
         <span class="input-group-btn">
           <button class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
         </span>
       </div>
     </form>
  </div>
</body>
</html>