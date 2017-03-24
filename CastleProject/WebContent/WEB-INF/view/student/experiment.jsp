<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Lab</title>
		<meta name="author" content="Harish" />
		
        <link href="<%=request.getContextPath()%>/resources/CSS/_assessmentStyles.css" rel="stylesheet" type="text/css"> 
    
        <style type="text/css">

            #tabs { margin-top: 5em;}
            #tabs  { list-style-type: none; display: inline-block; padding-left: 0px;}
            #tabs > li {padding-bottom: 0.3em;}
            #tabs >li > a {  text-decoration: none; font: 1em; color: cornflowerblue;}
            #tabs > li > a:hover { font-weight: bold; cursor: pointer; color:black;}
            #tabs  .active {color:black; font-weight: bold; cursor: pointer; } 
            
            .inactiveLink {
   pointer-events: none;
   cursor: default;
}
            td { text-align: center; }
            
            #questionsList {width: 100%; height:20em; border: 1px solid black; overflow: scroll;}
        
            div > ul, div > ul > li > ul { list-style-type: none; padding: 0.5em;}
            #btn-submit {background-color: azure; padding: 7px 25px; font-size: 15px; margin-top: 1em;}

            li {
                line-height: 1.5em;
            }
            
            .hidden {display: none;}
            .show {display: inline-block;}
            
                        textarea {
                width: 100%;
                height: 92%;
                -webkit-box-sizing : border-box; /* Safari/Chrome, other WebKit */
                -moz-box-sizing: border-box;    /* Firefox, other Gecko */
                box-sizing: border-box;         /* Opera/IE 8+ */
                font-family: serif;
                font-size: 1em;
            }
            
			.textId { width: 50px;}
			td {word-wrap: break-word;  text-align: center;}
			
        </style>
        <!-- Date: 2016-01-26 -->
	</head>
	<body>
		<main>
			<article id="pdfWindow">
<!--		      <object id="pdfFile" width="100%" height="100%" data="https://www.adobe.com/enterprise/accessibility/pdfs/acro6_pg_ue.pdf"></object>		-->
			</article>
			<article id="questionTab">
     			<form action="" method="post">
 				<div id="excelId"">
 				</div>    			
                  <p class="big" id="module"></p>
    
                    <section>
                        <ul id="tabs">
                        </ul>
                    </section>  
                    <section id="questionsList">
                        <div></div>
                    </section>
			         <input type="button" id="btn-submit" value="Next">
                </form>
			</article>
		</main>
        <script src="<%=request.getContextPath()%>/resources/_js/labJs2.js"></script>
	</body>
</html>

