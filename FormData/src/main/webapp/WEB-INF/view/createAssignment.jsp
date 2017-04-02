<!DOCTYPE html>
<html>
    <head>
         <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- One -->
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/bootstrap/css/bootstrap-responsive.css">   
    
    <!-- One End -->
    
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/css/main.css"/> 
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/main_body.css">    
    
    <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/external/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/lib/jquery-ui-1.11.1/jquery-ui.js"></script>
    
    <!-- Two  -->
     <script src="<%=request.getContextPath()%>/resources/lib/bootstrap/js/bootstrap.min.js"></script>
        
    </head>
    <body id="main-body">

        <span id="spam-exp-id" class="login-tfield-disp-hide" data-expId=${expId}>${expId}</span>
        <span id="spam-exp-status" class="login-tfield-disp-hide" data-expId=${expStatus}>${expStatus}</span>
                 
      <div  class="main-margin">
         <div id="main-container">
            
          </div>
      </div>
 
 <footer class="container-fluid text-center">
  <span class="float-right">&copy; CS Department</span>
</footer>       

        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/multipleQuestionTab.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/inherit.js"></script>        
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/numericalTab.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/shortAnswerTab.js"></script>        
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/module_spec.js"></script>     
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/experiment-spec.js"></script> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/experiments_js/createAssignment_session.js"></script> 
        
    </body>
</html>
