<!DOCTYPE html>
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

		<form action="cb_post.html" method="post">
            <ul id="checks">
                <li>Question :</li>
                <li><textarea rows="8" cols="50" name="question"></textarea></li>
                <li>Answer : <input type="text" name="answer" size="40"></li>
                <li>Invalid 1 : <input type="text" name="invalid"></li>
                <li>Invalid 2 : <input type="text" name="invalid"></li>
                <li>Invalid 3 : <input type="text" name="invalid"></li>
                <li>section : <input type="text" name="section"></li>                
                <li><input type="submit" value="Enter"></li>
            </ul>

		</form>
</body>
</html>