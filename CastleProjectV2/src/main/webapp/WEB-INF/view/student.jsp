<!DOCTYPE html>
<html lang="en">
<head>
  <title>Castle</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- One -->
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/bootstrap/css/bootstrap-responsive.css">   
    
    <!-- One End -->
    
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.css"/>
      <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/lib/w3/w3.css"/>
       <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css"/> 
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/css/student.css"/> 
    
    <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/external/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.js"></script>
    
    <!-- Two  -->
     <script src="<%=request.getContextPath()%>/resources/lib/bootstrap/js/bootstrap.min.js"></script>
  
  <style>

  </style>
</head>
<body id="body-container">

     <!-- Sidebar and navbar -->
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Castle Project</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav n-header">
<!--        <li><a href="iwelcome2.html">Home</a></li>
        <li class="active"><a href="#">Rooster</a></li>
        <li><a href="CalendarDate.html">Due Dates</a></li>-->
      </ul>
      <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i><span id="user-name"> Student </span><b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#" id="user-profile"><i class="fa fa-fw fa-power-off"></i></a></li>
                    <li><a href="#" id="user-cancel"><i class="fa fa-fw fa-power-off"></i> Cancel</a></li>

                    <li class="divider"></li>
                    <li><a href="#" id="user-logout"><i class="fa fa-fw fa-power-off"></i> Log Out</a></li>
                </ul>
          </li>
      </ul>
    </div>
  </div>
</nav>
      
<div id="page-wrapper">
    <!-- Main Container -->
    <div class="container">
        <div class="row">

            <!-- Information Window -->

            <div class="col-lg-12" id='main-container'>
                <div class="container-fluid border-draw" id="student-container">
                    <p>Student Container</p>
                    
                   <!--<div class="row status-table module-width">     
                       <h4 id="exp-mod-name">Experiment Name</h4>
                        <table  class="table table-responsive">
                            <thead>
                                <tr>
                                    <th>Module Name</th>
                                    <th>Score</th>
                                </tr>
                            </thead>
                            <tbody id="module-body">
                                <tr>
                                
                                </tr>
                            </tbody>
                        </table>
                    </div>-->
                    
                </div>
            </div>

        </div>
    </div>
</div>
    
    
<footer class="container-fluid text-center">
  <span class="float-right">&copy; CS Department</span>
</footer>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/session-store.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/student/single-experiment.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/student/status.js"></script>
 
</body>
</html>
