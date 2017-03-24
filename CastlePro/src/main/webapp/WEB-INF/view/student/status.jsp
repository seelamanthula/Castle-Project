<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
      <!--  <link href="CSS/_assessmentStyles.css" rel="stylesheet" type="text/css">-->
        
        <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        
        <style type="text/css">
            body {position: relative; width:80em; margin: 1em auto;}
            main { width: 100%; height: 30em; margin: 3em auto; border: 1px solid black; clear:both; font-family: sans-serif; position: relative;}

            p.sizeBig {font-size: 2em;}
            p.mediumSize {font-size: 1.5em;}
            p.smallSize{font-size: 1.2em;}

            #div-status { width: 80%; margin: 0 auto;}
            
            tr { padding: 0.5em;}
            th {word-wrap: break-word;  text-align: center;}
            td {text-align: center;}
     
            .column-2 { width:10em;}
            
            #log-out { float: right; }
              #btn-submit {background-color: azure; padding: 7px 20px; font-size: 15px; margin-top: 1em;}
            
        </style>
    </head>
    <body>
        <main>
        		<div id="log-out">
<!-- 	       		<form action="logout.html" method="get">
		        	 <input type="submit" id="btn-submit" value="Logout">
				</form> -->
				<a href="logout.html">Logout</a>
				</div>
            <section>
                <p class="sizeBig">Select an Experiment</p>
                <p class="mediumSize">Welcome Back ! <string>${username}</string></p>
            </section>
            <section>
                <p class="smallSize">Which experiment would you like to work on?</p>
                <div id="details">
         
                    <div id="div-status">
                     <table class="table table-striped" id="status-table">
                        <thead>
                          <tr>
                            <th>Experiment Number</th>
                            <th class="column-2">Experiment Name</th>
                            <th>Experiment Date</th>
                            <th>Lab Comprehension Assessment Complete</th>
                            <th>Pre-Lab Complete</th>
                            <th>Experiment Complete</th>
                          </tr>
                        </thead>
                        <tbody id="status-body-table">
                        </tbody>
                      </table>
                    </div>
                </div>
            </section>
        </main>
       <script src="<%=request.getContextPath()%>/resources/_js/statusJs.js"></script>
    </body>
</html>