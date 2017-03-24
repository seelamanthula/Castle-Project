<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
   <title></title>
    <style type="text/css">
        #checks {text-decoration: none; list-style-type: none}
        #checks > li {padding: 0.5em;}
         #checks > li >input {padding : 0.5em; width: 25em;}
    </style>
</head>
<body>

		<form action="de_post.html" method="get">
            <ul id="checks">
                <li>Question :</li>
                <li><textarea rows="8" cols="50" name="question"></textarea></li>
                <li>Units : <input type="text" name="units" size="40"></li>
                <li>Cols : <input type="text" name="cols"></li>
                <li>Rows : <input type="text" name="rows"></li>
                <li>Column Names : <input type="text" name="columnNames"></li>
                <li>Difference : <input type="text" name="difference"></li>
                <li>Hint : <input type="text" name="hint"></li>
                
                <li>section : <input type="text" name="section"></li>                
                <li><input type="submit" value="Enter"></li>
            </ul>

		</form>
</body>
</html>