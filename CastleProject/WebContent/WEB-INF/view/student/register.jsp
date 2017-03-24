<html lang="en">
<head>
  <title>Register</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <!--<link rel="stylesheet" href="reg.css">-->
    
    <style type="text/css">
        #netid, #firstname, #lastname
            {
                width: 30%;
                display: inline-block;
            }
        
        .container { margin-top: 5em;}
    </style>
</head>
<body>
     
<div class="container">
  <h2 style="text-align:center">Register</h2>
    <br><br>
  <form role="form" id="form" method="post" action="register_post.html">
      <div style="text-align:center">
	${status}
          </div>
    <div class="form-group" style="text-align:center">
      <input type="text" class="form-control" id="netid" name="netid" placeholder="Net ID">
    </div>
      <div style="text-align:center">
      </div>
    <div class="form-group" style="text-align:center">
      <input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name">
    </div>
       <div style="text-align:center">
      </div>
    <div class="form-group" style="text-align:center">
      <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name">
    </div>
      <div style="text-align:center">
           <button type="submit" class="btn btn-default btn-lg" id="submit">Register</button>
      </div>
  </form>
    <div style="text-align:center">
        <a href="login.html">Already Have an Account? Login Here!</a>
    </div>
</div>

</body>    
</html>

<script>
    function submitFunction()
    {
    	var reuest, netId, firstName, lastName, user;
    	
    	if(window.XMLHttpRequest) {
    	    request = new XMLHttpRequest();
    	} else {
    	    request = new ActiveXObject("Microsoft.XMLHTTP");
    	}

    	function User(netId, firstName, lastName) {
    		this.netId = netId;
    		this.firstName = firstName;
    		this.lastName = lastName;
    	}
        var form = document.getElementById("form");
        var netid = document.forms["form"]["netid"].value;
        var fname = document.forms["form"]["firstname"].value;
        var lname = document.forms["form"]["lastname"].value;
        
        if(netid == "" || netid == null)
            window.alert("Error: Please enter a Net-Id");
        else if(fname == "" || fname == null)
            window.alert("Error: Please enter a First Name");
        else if(lname == "" || lname == null)
            window.alert("Error: Please enter a Last Name");
        else  {
        	user = new User(netid, fname, lname);
        //	var url = "register_post.html?user2="+JSON.stringify(user);
        		var url = "register_post.html?netid="+netid+"&firstname="+fname+"&lastname="+lname;
        		
        	request.open('POST', url,true);
            request.onreadystatechange = function () {
                if ((request.status === 200) && (request.readyState === 4)) {
                    info = JSON.parse(request.responseText);
                    console.log(info);
                        window.location.href = "login.html";
                }
            };
            request.send();
        }
    }
    
</script>