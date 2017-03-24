<!DOCTYPE html>
<html>
    <head>
      <!--  <link href="CSS/_assessmentStyles.css" rel="stylesheet" type="text/css">-->
        
        <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        
        <title>Main Menu</title>
        <style type="text/css">
            body {position: relative; width:90em; margin: 1em auto;}
            main { width: 100%; height: 30em; margin: 3em auto; border: 1px solid black; clear:both; font-family: sans-serif; position: relative;}
            
            #container {margin: 3em; }
            p.sizeBig {font-size: 2em;}
            p.mediumSize {font-size: 1.5em;}
            p.smallSize{font-size: 1.2em;}

            #div-status { width: 95%; margin: 0 auto;}
            
            tr { padding: 0.5em;}
            th {word-wrap: break-word;  text-align: center;}
            td {text-align: center;}
     
            #log-out { float: right;  margin-right: 1em;}
  			.already {color: red; text-align: center;}
            .column-2 { width:10em;}
            
            .glyphicon {   font-size: 2em;}
        </style>
    </head>
    <body>
        <main>
            <article id="container">
             <p class="already">${status}</p>
            
            <div id="log-out">
				<a href="logout.html">Logout</a>
				</div>
            <section>
                <p class="sizeBig">Select an Experiment</p>
                <p class="mediumSize">Welcome Back<strong>${username}</strong>!</p>
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
                            <th colspan="2">Lab Comprehension Assessment Pre-Lab</th>                              
                            <th colspan="2">Written Pre-Lab</th>
                            <th colspan="2">In-Lab Experiment</th>
                            <th>PostLab</th>
                          </tr>
                        </thead>
                        <tbody id="status-body-table">
                        </tbody>
                      </table>
                    </div>
                </div>
            </section>
            </article>
        </main>
       <script src="<%=request.getContextPath()%>/resources/js/statusJs2.js"></script>
    </body>
</html>