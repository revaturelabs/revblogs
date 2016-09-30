<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>Logged In</title>
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
<style>
#warning{
	font-style: italic;
	font-weight: bold;
	text-decoration: underline;
}
</style>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="overall-container">
<div class="container page-content scroll content-padding">
	<div id="warning">
		This tool should only be used if you CANNOT contact your S3 bucket manager.<br/>
		If that is the case, follow the instruction below.
	</div>
	<div>
		<h3>Instructions:</h3>
		Step 1: Enter the relative path of the file that will be updated.<br/>
		Note: Check the S3 bucket management tool for a list of resources for locations.<br/>
		Example: content/resources/js/controllers/BlogIndexController.js will store to http://blogs.pjw6193.tech/content/resources/js/controllers/BlogIndexController.js<br/>
		Step 2: Choose the file in your system.<br/>
		Note: The files name will not affect the path and will replace the file that you entered in step 1.<br/>
		Note: There is no type conversion, so the file should be the same extension or undesired results may occur.<br/>
		Example: Uploading pickles.js to the example in step 1 will override that location with the data of pickles.js<br/>
		Step 3: Press Upload.<br/>
	</div>
	<div>
		<form action="${pageContext.servletContext.contextPath}/upload-remote" method="post" enctype="multipart/form-data">
			Relative Path:<br/>
			<input type="text" name="path" id="path"/><br/>
			File to upload:<br/>
			<input type="file" name="file" /><br/>
			This will be uploaded to:<br/>
			http://blogs.pjw6193.tech/<span id="preview"></span><br/>
			<input type="submit" value="Upload" />
		</form>
	</div>
</div>
<div class="footer2">
<jsp:include page="footer.jsp"></jsp:include>
</div>
</div>
</body>
<script type="text/javascript" src="resources/js/ui.js"></script>
<script type="text/javascript">
$(document).ready(function() {
 $("#path").on('keyup', function() {
  $('#preview').html($('#path').val());
 });
});
</script>
</html>