<html lang="en">
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
 <!--   <link rel="stylesheet" href="login.css">-->
    
    <style type="text/css">
        #netid, #firstname
        {
            width: 30%;
            display: inline-block;
        }

        #netlabel, #namelabel
        {

        }
        
        .container { margin-top: 10em;}
        </style>
        
          <%
        	String login = (String) session.getAttribute("login");
  			System.out.println(login);
          if (login == null){
          		login = "";
          	}
        	if(login.equals("true") && login.length() > 3) {
        		%>
        		<script type="text/javascript">
        		window.location.href="status.html"
        		</script>
        		<%
        	} else {
        %>
</head>
<body>
     
<div class="container">
  <h2 style="text-align:center">Login</h2>
    <br><br>
  <form role="form" id="form" method="POST" action="authenticate.html">
      <div style="text-align:center">
<!--      <label id="netlabel" for="netid">Net-Id:</label>-->
          </div>
    <div class="form-group" style="text-align:center">
      <input type="text" class="form-control" id="netid" name="netid" placeholder="Net ID">
    </div>
      <div style="text-align:center">
<!--          <label id="namelabel" for="firstname">First Name:</label>-->
      </div>
    <div class="form-group" style="text-align:center">
      <input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name">
    </div>
      <div style="text-align:center">
           <button type="submit" class="btn btn-default btn-lg" id="submit">Login</button>
      </div>
  </form>
    <div style="text-align:center">
        <a href="register.html">First Time User? Register Here!</a>
    </div>
</div>
<%} %>
</body>    
</html>

