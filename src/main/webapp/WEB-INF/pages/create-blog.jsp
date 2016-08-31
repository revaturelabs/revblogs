<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
  <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'textarea' });</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta property="og:url" content="http://dev.pjw6193:7001/pages/create-blog.jsp" />
<meta property="og:type"content="article" />
<meta property="og:title"content="Different" />
<meta property="og:description"content="this is only a testkhjkgkjhgkj" />
<meta property="og:image"content="http://weknowyourdreams.com/images/cheese/cheese-03.jpg" />
<meta property="og:image:width" content="450" />
<meta property="og:image:height" content="298" />
<meta property="fb:app_id" content="1070815552954243"/>
<title>Insert title here</title>
</head>
<body>
<h2>Create new blog post</h2>
<button type="button">Attach Image</button>
<button type="button">Attach File</button>
<br>
<textarea rows="30" cols="100"></textarea>
<br>
<button type="button">Add Reference</button>
<br>
<textarea rows="5" cols="100"></textarea> 
<div>Share to:</div>
<button type="button" id ="shareBtn">Facebook</button>
<button type="button">LinkedIn</button> <br> <br>
<button type="button">Submit</button>
</body>
<script src="${pageContext.servletContext.contextPath }/resources/scripts/facebookConnection.js"></script>
</html>