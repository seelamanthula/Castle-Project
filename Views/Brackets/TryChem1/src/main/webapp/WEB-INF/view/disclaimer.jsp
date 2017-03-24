<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Disclaimer</title>
 <style type="text/css">
 	ul { list-style-type: none;}
    ul > li {padding-bottom: 0.3em; text-decoration: none;}
    
      textarea { width: 500px; height: 80px;}
 </style>
</head>
<body>
	<ul>
		<form action="prelabEntry.html" method="get" enctype="application/x-www-form-urlencoded">
			<li>Objective</li>
			<li><textarea name="objective" id="objective"></textarea></li>
			<li>Hypothesis</li>
			<li><textarea name="hypothesis" id="hypothesis"></textarea></li>
			<li>Variables</li>
			<li><textarea name="variables" id="variables"></textarea></li>
			<li>Experimental Variables</li>
			<li><textarea name="experimentalValues" id="experimentalValues"></textarea></li>
			<li>Chemical Hazards</li>
			<li><textarea name="chemicalHazards" id="chemicalHazards"></textarea></li>
			<li><input type="submit" value="submit"></li>
		</form>
	</ul>
</body>
</html>