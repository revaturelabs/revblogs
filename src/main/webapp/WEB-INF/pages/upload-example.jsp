<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload Example</title>
</head>
<body>

<h2>File Upload Example</h2>
<form method="POST" action="uploadFile.do" enctype="multipart/form-data">
	<%-- <input type="hidden" name="upc" value="${param.upc}"> --%>
	<input type="file" name="file" id="fileChooser" /><br />
	<input type="submit" name="upload" value="Upload" />
</form>

</body>
</html>