<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Different Types</title>
   <style type="text/css">
	   	body { font-family: sans-serif;}
        ul {text-decoration: none; list-style-type: none}
        li {padding: 0.5em;}
    </style>
</head>
<body>

${message}
	<ul id="checks">
		<li>
			<form action="module" method="post" enctype="multipart/form-data">
				<ul>
					<li>Module Name : <input type="text" name="name"></li>
					<li>PDF FIle : <input type="file" name="file"></li>
					<li><input type="submit" value="ADD"></li>
				</ul>
			</form>
		</li>
		<li>Questions </li>
		<li>	
			<ul>
				<li>
					<c:url value="/checkbox.html" var="messageUrl" />
				<a href="checkbox">checkbox</a></li>
		
				<li><c:url value="/blankQues.html" var="messageUrl" />
				<a href="blankques">Blank Question</a></li>
	
				<li><c:url value="/disclaimer.html" var="messageUrl" />
				<a href="disclaimer">Disclaimers</a></li>				
			</ul>
		</li>	
	</ul>

</body>
</html>