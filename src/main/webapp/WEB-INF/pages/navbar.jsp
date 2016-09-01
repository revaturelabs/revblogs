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
        <a class="navbar-brand" href="#"><img src="resources/img/rev-brand.png" /></a>
      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul id="navs" class="nav navbar-nav">
          <li id="navhome"><a href="#">Home</a></li>
          <li id="navabout"><a href="#">About</a></li>
        </ul>
        <form class="navbar-form navbar-right .hidden-xs">
          <div class="form-group input-group post-search">
            <input type="text" class="form-control" placeholder="Search">
            <span class="input-group-btn">
              <button class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
            </span>
          </div>
        </form>
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