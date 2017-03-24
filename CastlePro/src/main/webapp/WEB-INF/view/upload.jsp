<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PDF</title>
</head>
<body>
	<h1>${message}</h1>
	<a href="${pdfFile}">Download</a></p>
	
	<div class="pdf">
    <p>one</p>
      <object width="800px" height="500px" data="${pageContext.request.contextPath}/masterpiece/1"></object>
    <br/>
    <p>two</p>
    <object data="${pageContext.request.contextPath}/masterpiece/1" height="400"><br/>
    	<embed src="${pageContext.request.contextPath}/masterpiece/1" width="600" height="400"> </embed>
    Error: Embedded data could not be displayed.
</object>
</div>  
</body>
</html>