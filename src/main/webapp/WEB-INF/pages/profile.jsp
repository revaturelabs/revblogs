<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
</head>
<body>

<form:form action="updateUser.do" method="post" commandName="updateUser">
	Username: <form:input path="username" /> <br />
	Password: <form:password path="password" /> <br />
	Email: <form:input path="email" /> <br />
	First Name: <form:input path="firstName" /> <br />
	Last Name: <form:input path="lastName" /> <br />
	Profile Picture: <form:input path="profilePicture" /> <br />
	Job Title: <form:input path="jobTitle" /> <br />
	LinkedIn URL: <form:input path="linkedInURL" /> <br />
	Description: <form:textarea path="description" /> <br />
	<input type="submit" value="Update Profile" /> <br />
</form:form>

</body>
</html>