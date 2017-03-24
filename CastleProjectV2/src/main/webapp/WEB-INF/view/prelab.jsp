<!DOCTYPE html>
<html lang="en">
<head>
  <title>Castle</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- One -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/lib/bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/lib/bootstrap/css/bootstrap-responsive.css">   
    
    <!-- One End -->
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.css"/>
     <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/lib/w3/w3.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css"/> 
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/student.css"/> 
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/external/jquery/jquery.js"></script>
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

            <span id="netid" class="display-hide">${netid}</span>
            <div class="col-sm-7" id='pdf-container'>
                
                 <!-- 1 -->
                    <!-- PDF Window -->
                    <span id="expname">Experiment Name</span>
                    <span id="expid" class="display-hide">${expid}</span>
                
                    <div class="pdf-div">
                            <div>
                                <!-- 1.1 -->
                                <div class="w3-bar w3-black" >
                                    <button class="w3-bar-item w3-button w3-small pdflink w3-grey" onclick="openPdf(event,'pdf')">PDF</button>
                                    <button class="w3-bar-item w3-button w3-small pdflink" onclick="openPdf(event,'hints-tab')">Hints</button>
                                </div>
                            </div>
                            
                            <div>
                                <!-- 1.2 -->
                                <div id="pdf" class="w3-container pdf w3-animate-left">
        <!--                          <object width="100%" height="600" data="https://www.w3schools.com/w3css/w3css_buttons.asp"></object>-->
                                  <object width="100%" height="600" data=""></object>                            
                                </div>

                                <div id="hints-tab" class="w3-container pdf w3-animate-left hint-div"  style="display:none">
                                    <h2>Hints</h2>
                                </div>
                            </div>
                    </div>
                 <!-- 1 -->
            </div>
            

            <div class="col-sm-5" id='question-container'>
                 <!-- 2 -->
                <!-- Question Container -->
                <span id="modulename" class="modulename">MODULE NAME</span>            
                <span id="moduleid" class="display-hide">${moduleid}</span>
                  <br><p id="module-errors" class="c-errors"></p>
                    <div>
                        <div class="w3-bar w3-black bottom-margin" id="question-link">
                            
                            <button class="w3-button w3-block blocklink w3-grey" style="width:100%" onclick="openPrelab(event,'block1')"><span class="modulename"></span> Objective</button>
                            <button class="w3-button w3-block blocklink " style="width:100%" onclick="openPrelab(event,'block2')"><span class="modulename"></span> Hypothesis</button>
                            <button class="w3-button w3-block blocklink " style="width:100%" onclick="openPrelab(event,'block3')"><span class="modulename"></span> Environmental Variables</button>
                            <button class="w3-button w3-block blocklink " style="width:100%" onclick="openPrelab(event,'block4')"><span class="modulename"></span> Experiment Outline</button>
                            <button class="w3-button w3-block blocklink " style="width:100%" onclick="openPrelab(event,'block5')"><span class="modulename"></span> Chemical Hazards</button>
                        
                            
                        </div>                        
                    </div>
                
                    <div id="question-panel">
                         <!-- 2.1 -->
                        <div>
                            
                            <div id="block1" class="w3-container block  w3-animate-right">
                                <textarea id="stu_objective" class="prelab-tarea-color" rows="17"  style="width:100%" placeholder="Objective"></textarea>
                            </div>
                            
                            <div id="block2" class="w3-container block w3-animate-right" style="display:none">
                                <textarea id="stu_hypothesis" class="prelab-tarea-color" rows="17"  style="width:100%" placeholder="Hypothesis"></textarea>
                            </div>
                            <div id="block3" class="w3-container block w3-animate-right" style="display:none">
                                <textarea id="stu_variables"  class="prelab-tarea-color" rows="17"  style="width:100%" placeholder="Variables"></textarea>
                            </div>
                            
                            <div id="block4" class="w3-container block w3-animate-right" style="display:none">
                                <textarea id="stu_exp_outline" class="prelab-tarea-color" rows="17"  style="width:100%" placeholder="Experiment Outline"></textarea>
                            </div>
                            <div id="block5" class="w3-container block w3-animate-right" style="display:none">
                                <textarea id="stu_chem_hazards" class="prelab-tarea-color" rows="17"  style="width:100%" placeholder="Chemical Hazards"></textarea>
                            </div>
                            
                        </div>
                        
                         <!-- 2.2 -->
                    </div>     
                
                <!-- 2.1 -->
            </div>

        </div>
    </div>
</div>
    
    
<footer class="container-fluid text-center">
  <span class="float-right">&copy; CS Department</span>
</footer>
        
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/session-store.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/student/labs/prelab.js"></script>
</body>
</html>
