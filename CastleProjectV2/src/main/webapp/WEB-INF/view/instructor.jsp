<!DOCTYPE html>
<html lang="en">
<head>
  <title>Instructor</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- One -->
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/bootstrap/css/bootstrap-responsive.css">   
    
    <!-- One End -->
    
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/lib/w3/w3.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/css/main.css"/> 
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/searchStyle.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/access.css">    
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/css/gc.css"/>
      <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
      
    <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/external/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.js"></script>
    
    <!-- Two  -->
     <script src="<%=request.getContextPath()%>/resources/lib/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
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
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i><span id="user-name"> Instructor </span><b class="caret"></b></a>
                <ul class="dropdown-menu">
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

             <!-- Sidebar -->
         <div class="col-lg-2">
              <ul class="nav nav-pills nav-stacked sidenav">
                 <li class="active" id='exp-nav-tab'><a href="#section1">Experiments</a></li>
                 <li id='search-nav-tab'><a href="#section2">Search</a></li>
                 <li id='grade-nav-tab'><a href="#section3">Grade Center</a></li>
                 <li id='access-nav-tab'><a href="#section4">User Access</a></li>
              </ul><br>
          </div>
            <!-- Information Window -->

            <div class="col-lg-10" id='main-container'>
                <div id="experiment-container">
                    <p>Experiment Container</p>
                </div>
                <div id="search-container">
                    <p>Search Container</p>
                </div>
                <div id="grade-container">
                    <p>Grade Container</p>
                </div>
                <div id="access-container">
                    <p>User Access Container</p>                 
                </div>
            </div>

        </div>
    </div>
</div>
    
    
<footer class="container-fluid text-center">
  <span class="float-right">&copy; CS Department</span>
</footer>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/dist/shim.js"></script>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/dist/jszip.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/dist/xlsx.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/dist/ods.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/dist/xl.js"></script> 
 
<%--     <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/first/register.js" type="text/javascript"></script>      --%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/instructor/accessTab.js" type="text/javascript"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/instructor/gctab2.js" type="text/javascript"></script>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/instructor/gcTab.js" type="text/javascript"></script>    
 
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/instructor/searchTab2.js" type="text/javascript"></script>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/instructor/experimentTab.js" type="text/javascript"></script>        
               
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/instructor/home.js" type="text/javascript"></script>    
    
    <!-- Tab Activation should be at the end of all JS files -->
    <script src="<%=request.getContextPath()%>/resources/js/first/tabs.js" type="text/javascript"></script>    
    
</body>
</html>
