<!DOCTYPE html>
<html>
<head>
	<title></title>
    <style type="text/css">
        #checks {text-decoration: none; list-style-type: none}
        #checks > li {padding: 0.5em;}
    </style>
</head>
<body>

		<form action="blank.html" method="post">
            <ul id="checks">
                <li>Question :</li>
                <li><textarea rows="8" cols="50" name="question"></textarea></li>
                <li>Range : <textarea rows="2" cols="40" name="range"></textarea></li>
                <li>Answer : <textarea rows="4" cols="50" name="answer"></textarea>
                    Formula : <textarea rows="2" cols="30" name="formula"></textarea></li>
                <li>Hint 1 : <textarea rows="3" cols="50" name="hints"></textarea></li>
                <li>Hint 2 : <textarea rows="3" cols="50" name="hints"></textarea></li>
                <li>Hint 3 : <textarea rows="3" cols="50" name="hints"></textarea></li>
                <li>Section : <input type="text" name="section"></li>
                <li><input type="submit" value="Enter"></li>
            </ul>

		</form>
</body>
</html>