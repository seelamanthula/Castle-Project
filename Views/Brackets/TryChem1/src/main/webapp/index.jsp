<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="/showMessage.html" var="messageUrl" />
		<a href="call">Click to enter</a><br/>
		<br/>
		<c:url value="/showMessage.html" var="messageUrl" />
		<a href="assessment">Assessment</a><br/>

		<c:url value="/showMessage.html" var="messageUrl" />
		<a href="status.html">Status</a><br/>
		
		<form action="authenticate" method="get">
			<ul>
				<li><input type="text" name="netId"></li>
				<li><input type="submit" value="Submit"></li>
			</ul>
		</form>
	</body>
</html>
