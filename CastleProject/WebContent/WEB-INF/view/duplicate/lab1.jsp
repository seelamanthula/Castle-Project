<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Volumetric Lab</title>
		<meta name="author" content="Harish" />
		
	        <link href="<%=request.getContextPath()%>/resources/CSS/_assessmentStyles.css" rel="stylesheet" type="text/css"> 
    
        <!--  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>  -->
    
        <style type="text/css">

            #pdfWindow { background-color: aliceblue; }
            #pdfWindow > ul {list-style-type: square; display: block; }
            #pdfWindow > ul > li {list-style-type: square; display: block; }
            #pdfWindow > ul > li:hover {list-style-type: square; display: block; font-weight: bold; cursor: pointer; color:black;}
        
            #tabs { margin-top: 1em;}
            #tabs  { list-style-type: none; display: inline-block; padding-left: 0px;}
            #tabs > li {padding-bottom: 0.3em; display: inline-block;}
            #tabs >li > a {  text-decoration: none; font: 1em; color: cornflowerblue;}
            #tabs > li > a:hover { font-weight: bold; cursor: pointer; color:black;}
            #tabs  .active {color:black; font-weight: bold; cursor: pointer; } 
            
            
            #questionsList {width: 100%; height:18em;  }
        
            div > ul, div > ul > li > ul { list-style-type: none; padding: 0.5em;}
            #btn-submit {background-color: azure; padding: 7px 25px; font-size: 15px; margin-top: 1em;}

            li {
                line-height: 1.5em;
            }
            
            .modulesList {list-style-type: square; display: block; };
            .modulesList:hover {list-style-type: square; display: block; font-weight: bold; cursor: pointer; color:black;};

            .hidden {display: none;}
            .show {display: inline-block;}
            .btn-show {display: block};
            
            #excelId {float: right; margin-left: 50em; list-style-type: none;}
            #excelId li { list-style-type: none; word-break: keep-all;}
            #excelId li:hover { font-weight: bold;}
            
            .wishes { color:green; visibility: hidden;}
            .textId { width:80px;}
              textarea {
                width: 100%;
                height: 85%;
                border : 1px solid black;
                font-family: sans-serif;
                -webkit-box-sizing : border-box; /* Safari/Chrome, other WebKit */
                -moz-box-sizing: border-box;    /* Firefox, other Gecko */
                box-sizing: border-box;         /* Opera/IE 8+ */
                font-family: serif;
                font-size: 1em;
            }
            
            #pdfWindow.languages {
	font: .8em "Lucida Sans Unicode", "Lucida Grande", sans-serif;
	background:#2e2e2e;
	padding: 25px;
	padding-top: 1em;
	float: left;
	width:50%; height: 90%;
	margin-left: 1em;
}
            #pdfWindow h4 {
	color: rgb(83, 104, 138);
	font-size: 2em;
}

#pdftabs{
	height:30px;
	overflow:hidden;
}

#pdftabs > ul{
	font: 1em;
	list-style:none;
}
#pdftabs ul, #pdftabs li {
	margin:0;
	padding:0;
}

#pdftabs > ul > li{
	margin:0 2px 0 0;
	padding:7px 10px;
	display:block;
	float:left;
	color:#FFF;
	border-top-left-radius:4px;
	border-top-right-radius: 4px;
	background: #CCC;
    
}

#pdftabs > ul > li:hover{
	background: white; 
	cursor:pointer;
}

#pdftabs > ul > .active{
	background: white; /* old browsers */
	cursor:pointer;
}

#pdfcontainer div {
	background: white;
	padding:10px 10px 25px;
	margin:0;
	color:#333;
}
#pdftabs a {
	text-decoration: none;
	color: black;
}
            #disclaim { color: red; font-size: 1em;}
            
            #hints li {line-height: 2em; padding-bottom: 2em;}
        </style>
        <!-- Date: 2016-01-26 -->
	</head>
	<body>
		<main>
            <article id="pdfWindow" class="languages">
                <div id="pdftabs">
                    <ul>
                        <li id="pdf1"><a href="#pdfpanel1">PDF</a></li>
                        <li id="pdf2"><a href="#pdfpanel2">Feedback</a></li>
                    </ul>
                </div>   
                <div id="pdfcontainer">
                    <div id="pdfpanel1"><object width="100%" height="90%" data="getPdf/file/1"></object></div>
                    <div id="pdfpanel2"><p>Feedback</p>
                        <ul id="hints">
                            <li id="hint1"></li>
                            <li id="hint2"></li>
                            <li id="hint3"></li>
                        </ul>
                    </div>
                </div>   
			</article>
			<article id="questionTab">
 			<form action="VolumetricLab" method="get"> 
                <ul id="excelId">
                    <li align="right" onclick="warning();">Main Menu</li>
                </ul>
                <p class="big">Module : <strong id="module"></strong></p>
                <p id="disclaim"></p>
                <section>
                    <ul id="tabs">
                    </ul>
                    </section>  
                    <section id="questionsList">
                        <div></div>
                    </section>
			         <input type="submit" id="btn-submit" value="Save & Continue to Next Section">
 		         </form> 
			</article>
		</main>
         <script src="<%=request.getContextPath()%>/resources/js/labJs1.js"></script>

	</body>
</html>

