<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Picture</title>
</head>
<body>
<h2>Upload Picture</h2>
<form method="POST" action="upload-picture" enctype="multipart/form-data">
	<input type="file" name="file" id="fileChooser" /><br />
	<input type="submit" name="upload" value="Upload" />
</form>
<div id="pictureURL"></div>
</body>
</html>