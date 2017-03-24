<html>
    <head>
        <link href="<%=request.getContextPath()%>/resources/CSS/_assessmentStyles.css" rel="stylesheet" type="text/css"> 

        <style type="text/css">
            #tabs >ul { list-style-type: none;}
            #tabs > ul > li {padding-bottom: 0.3em;}
            #tabs > ul >li > a {  text-decoration: none; font: 1em; color: cornflowerblue;}
            #tabs > ul > li > a:hover { text-decoration-color: black; font-weight: bold; cursor: pointer; color:black;}
            #tabs > ul .active {text-decoration-color: black; font-weight: bold; cursor: pointer; color:black;}
            
            #questionsList {width: 100%; height:60%; border: 1px solid black; }
        
            #btn-submit {background-color: azure; padding: 7px 25px; font-size: 15px; margin-top: 1em;}
            
            textarea {
                width: 100%;
                height: 92%;
                font-family: sans-serif;
                -webkit-box-sizing : border-box; /* Safari/Chrome, other WebKit */
                -moz-box-sizing: border-box;    /* Firefox, other Gecko */
                box-sizing: border-box;         /* Opera/IE 8+ */
                font-family: serif;
                font-size: 1em;
            }
            
        </style>
   
    </head>
    <body>

        <main>
			<article id="pdfWindow">
<!--  				<object width="100%" height="100%" data="getPdf/file/1"></object> -->
 				<object width="100%" height="100%" data="<%=request.getContextPath()%>/resources/pdffile.pdf"></object> 
			</article>
			<article id="questionTab">
				<form action="" method="post">
                    <section  id="tabs">
                        <p>Prelab Written Test: <strong id="module"></strong></p>
                        <ul>
                            <li id="tab1"><a href="#tabpanel1">Objective</a></li>
                            <li id="tab2"><a href="#tabpanel2">Hypothesis</a></li>
                            <li id="tab3"><a href="#tabpanel3">Variables</a></li>
                            <li id="tab4"><a href="#tabpanel4">Experimental outline</a></li>
                            <li id="tab5"><a href="#tabpanel5">Chemical Hazards</a></li>
                        </ul>
                    </section>
                    <p id="selectedTab"></p>
                    <section id="questionsList">
                        <div id="tabpanel1">
                                <textarea id="objective" name="objective" placeholder="Objective"></textarea>
                      <!--           <input type="button" id='btn-objective' name='btn-objective' value='save'>                     -->   
                        </div>
                        <div id="tabpanel2">
                            <textarea id="hypothesis" name="hypothesis" placeholder="Hypothesis"></textarea>
 <!--                            <input type="button" id='btn-hypothesis' name='btn-hypothesis' value='save'> -->
                        </div>
                        <div id="tabpanel3">
                            <textarea id="variables" name="variables" placeholder="Variables"></textarea>
    <!--                         <input type="button" id='btn-variables' name='btn-variables' value='save'> -->
                        </div>
                        <div id="tabpanel4">
                            <textarea id="experimentalOutline" name="experimentalOutline" placeholder="Experimental Outline"></textarea>
      <!--                       <input type="button" id='btn-experimental' name='btn-experimental' value='save'> -->
                        </div>
                        <div id="tabpanel5">
                            <textarea id="chemical-hazards" name="chemical-hazards" placeholder="Chemical Hazards"></textarea>
                    <!--         <input type="button" id='btn-chemical' name='btn-chemical' value='save'> -->
                        </div>
                    </section>
                <input type="button" id="btn-submit" value="submit" style="float:right">
                </form>
			</article>
     
		</main>
	     <script src="<%=request.getContextPath()%>/resources/_js/prelabJs1.js"></script>
   
    </body>
</html>