<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>assessment</title>
		<meta name="author" content="Harish" />
		
        <style type="text/css">


		body {position: relative; width:80em; margin: 1em auto;}
		main { width: 100%; height: 40em; margin: 0 auto; border: 1px solid black; clear:both; font-family: sans-serif; position: relative;}
		#pdfWindow { float: left; width:80%; height: 95%; background-color: darkgray; margin: 1em 0 1em 1em;}
		
		#call {float: left; width:40%; height: 95%;}
		#call2 {float: right; width:40%; height: 95%;}
		
		#questionTab {float: right; margin: 0.5em 1em 1em 1em; width: 10%; height: 95%; }
        
            
            #questionsList {width: 100%; height:25em; border: 1px solid black; }
        
            div > ul, div > ul > li > ul { list-style-type: none; padding: 0.5em;}
            #btn-submit {background-color: azure; padding: 7px 25px; font-size: 15px; margin-top: 1em;}

            li {line-height: 1.5em;}
            
            .hidden {display: none;}
            .show {display: inline-block;}
        </style>
        <!-- Date: 2016-01-26 -->
	</head>
	<body>
		<main>
			<article id="pdfWindow">
				<div id="call">
				<object id="pdfFile" width="100%" height="100%" data="<%=request.getContextPath()%>/experiment/prelab/volumetric/document.pdf"></object>
				</div>
			<div id="call2">
				
				<object id="pdfFile" width="100%" height="100%" data="<%=request.getContextPath()%>/experiment/prelab/acidbase/document.pdf"></object>
				</div>
			</article>
			<article id="questionTab">
                  <p class="big" id="module"></p>                  
                  <a href="status.html">Status</a>
                  
                  <a href="<%=request.getContextPath()%>/experiment/prelab/volumetric/document.pdf" download>Volumetric</a>
                  <a href="<%=request.getContextPath()%>/experiment/prelab/acidbase/document.pdf" download>Acid Base</a>

    			<form action="logout.html" method="get">
		         <input type="submit" id="btn-submit" value="Logout">
				</form>
			</article>
		</main>
	</body>
</html>

