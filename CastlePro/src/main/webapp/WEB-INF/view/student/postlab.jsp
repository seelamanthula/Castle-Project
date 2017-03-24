<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>PostLab</title>
		<meta name="author" content="Harish" />
		
        <link href="<%=request.getContextPath()%>/resources/CSS/_assessmentStyles.css" rel="stylesheet" type="text/css"> 
    
        <style type="text/css">

            #tabs { margin-top: 1.5em;}
            #tabs  { list-style-type: none; display: inline-block; padding-left: 0px;}
            #tabs > li {padding-bottom: 0.3em; display: inline-block; padding-left: 0.5em;}
            #tabs >li > a {  text-decoration: none; font: 1em; color: cornflowerblue;}
            #tabs > li > a:hover { font-weight: bold; cursor: pointer; color:black;}
            #tabs  .active {color:black; font-weight: bold; cursor: pointer; } 
            
            #questionsList {width: 100%; height:28em; border: 1px solid black; overflow: scroll;}
        
            div > ul, div > ul > li > ul { list-style-type: none; padding: 0.5em;}
            #btn-submit {background-color: azure; padding: 7px 25px; font-size: 15px; margin-top: 1em;}

            li {
                line-height: 1.5em;
            }
            
              textarea {
                width: 100%;
                height: 92%;
                -webkit-box-sizing : border-box; /* Safari/Chrome, other WebKit */
                -moz-box-sizing: border-box;    /* Firefox, other Gecko */
                box-sizing: border-box;         /* Opera/IE 8+ */
                font-family: serif;
                font-size: 1em;
            }
            
            
            .hidden {display: none;}
            .show {display: inline-block;}
            
            #menu { list-style-type: none;}
            #menu li {display: inline-block;}
            #menu li:hover {font-weight: bold;}
            .left {float: left;}
            .right {float: right;}
            
        </style>
        <!-- Date: 2016-01-26 -->
	</head>
	<body>
		<main>
			<article id="pdfWindow">
		      <object id="pdfFile" width="100%" height="100%" data="getPdf/file/1""></object>		
			</article>
			<article id="questionTab">
 					<form action="Postlab" method="post">
                    <section>
                         <ul id="menu">
                            <li class="left"><p>Postlab Written Test: <strong id="module"></strong></p></li>
                            <li class="right"><a href="status.html">Main Menu</a></li>
                        </ul>
                        <ul id="tabs">
                        </ul>
                    </section>  
                    <section id="questionsList">
                        <div></div>
                    </section>
			         <input type="submit" id="btn-submit" value="Submit">
			        </form> 
 			</article>
		</main>
        <script src="<%=request.getContextPath()%>/resources/_js/postlabJs.js"></script>

	</body>
</html>

