<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<nav class="page-navigation navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="http://blogs.pjw6193.tech"><img src="resources/img/rev-brand.png" alt = "revature"/></a>
      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul id="navs" class="nav navbar-nav">
        <c:if test="${user.userRole.role eq 'CONTRIBUTOR'}">
        	<li id="navhome"><a href="contributor">Dashboard</a></li>
        </c:if>
        <c:if test="${user.userRole.role eq 'ADMIN'}">
        	<li id="navhome"><a href="admin">Dashboard</a></li>
        </c:if>
        </ul>
        <ul id="rightnavs" class="nav navbar-nav navbar-right">
	        
	        <c:choose>
	        	<c:when test="${user.userRole.role eq 'CONTRIBUTOR'}">
			        <li id="navuser" class="dropdown">
			        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
			        	aria-haspopup="true" aria-expanded="false">
			        	<span class="glyphicon glyphicon-user"></span>
			        	<c:out value="${user.firstName}" /> <span class="caret"></span></a>
			        	<ul class="dropdown-menu">
			        		<li><a href="${pageContext.servletContext.contextPath}/contributor">Home</a></li>
			        		<li role="separator" class="divider"></li>
			        		<li><a href="${pageContext.servletContext.contextPath}/create-blog"><span class="glyphicon glyphicon-pencil"></span>&nbsp;New Blog Entry</a></li>
			        		<li role="separator" class="divider"></li>
			        		<li><a href="${pageContext.servletContext.contextPath}/profile"><span class="glyphicon glyphicon-wrench"></span>&nbsp;Update profile</a></li>
			        		<li role="separator" class="divider"></li>
		            		<li><a href="${pageContext.servletContext.contextPath}/go-logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Log Out</a></li>
			        	</ul>
			        </li>
		        </c:when>
		        <c:when test="${user.userRole.role eq 'ADMIN'}">
			        <li id="navadmin" class="dropdown">
			        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
			        	aria-haspopup="true" aria-expanded="false">
			        	<span class="glyphicon glyphicon-user"></span>
			        	<c:out value="${user.firstName}" /> <span class="caret"></span></a>
			        	<ul class="dropdown-menu">
			        		<li><a href="${pageContext.servletContext.contextPath}/admin">Home</a></li>
			        		<li role="separator" class="divider"></li>
			        		<li><a href="${pageContext.servletContext.contextPath}/create-blog"><span class="glyphicon glyphicon-pencil"></span>&nbsp;New Blog Entry</a></li>
			        		<li role="separator" class="divider"></li>
			        		<li><a href="${pageContext.servletContext.contextPath}/profile"><span class="glyphicon glyphicon-wrench"></span>&nbsp;Update profile</a></li>
			        		<li role="separator" class="divider"></li>
		            		<li><a href="${pageContext.servletContext.contextPath}/go-logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Log Out</a></li>
			        	</ul>
			        </li>
		        </c:when>
		        <c:otherwise>
		        	<li id="navlogin"><a href="${pageContext.servletContext.contextPath}/login"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Log in</a></li>
				</c:otherwise>
			</c:choose>
        </ul>
      </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
  </nav>
  