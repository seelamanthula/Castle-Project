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
        </style>
    </head>
    <body>
        <main>
            <section>
                <p class="sizeBig">Select an Experiment</p>
                <p class="mediumSize">Welcome Back !</p>
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
       <!--  <script src="_js/statusJs.js"></script> -->
       <script type="text/javascript">
       var request = new XMLHttpRequest();

       request.open('GET','status.json');

       request.onreadystatechange = function() {

           
           if((request.readyState == 4)) {
               var info = JSON.parse(request.responseText);
               console.log(info);
               arrangeTables(info);
           }
       }
       request.send();
       console.log(request);

       function arrangeTables(info) {
           var tableBody = document.getElementById('status-body-table');
           console.log("in Tables");
           var id;
           var name;
           var date;
           var assessment;
           var prelab;
           var experiment;
           
           for(var i =0; i < info.length; i++) {
               addRow(info[i].experimentId, info[i].title, info[i].dueDate, 'No', 'No', 'No');
           }
           
           function addRow(id, name, date, assessment, prelab, experiment) {
               var tr = document.createElement('tr');
               
               var td1 = document.createElement('td');
               var txt1 = document.createTextNode(id);
               td1.appendChild(txt1);

               var td2 = document.createElement('td');
               var a = document.createElement('a');
               a.setAttribute('href','experiment/'+id);
               a.innerHTML = name;
               td2.appendChild(a);

               var td3 = document.createElement('td');
               td3.innerHTML = date;
               
               var td4 = document.createElement('td');
               td4.innerHTML = assessment;
               
               var td5 = document.createElement('td');
               td5.innerHTML = prelab;

               var td6 = document.createElement('td');
               td6.innerHTML = experiment;
               
               tr.appendChild(td1);
               tr.appendChild(td2);
               tr.appendChild(td3);
               tr.appendChild(td4);
               tr.appendChild(td5);
               tr.appendChild(td6);
               
               tableBody.appendChild(tr);
           }
           
           function tick(status) {
               
           }
       }

       </script>
    </body>
</html>