<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
<head>
<title>Revature Blog</title>
<base href="/revblogs/" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.8/angular.min.js"></script>
<script src="resources/js/app.js"></script>
<script src="resources/js/controllers/BlogIndexController.js"></script>

</head>

<body ng-app="app" ng-controller="BlogIndexController">
  <jsp:include page="navbar.jsp"></jsp:include>
  <script>document.getElementById("navhome").className="active";</script>
  <div class="container page-content">
  	<div class="row">
  		<div class="col-xs-12">
  			<h2>Latest Posts</h2>
  		</div>
  	</div>
    <div class="row">
      <div class="col-sm-8">
      	<div ng-repeat="post in posts.posts" ng-include src="'resources/js/templates/post-preview.html'"></div>
      </div>
      <div class="col-sm-4 hidden-xs">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">Learn more about Revature</h3>
          </div>
          <div class="panel-body">
            <ul class="list-unstyled">
              <li><a href="#">Revature</a></li>
              <li><a href="#">Life at Revature</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
