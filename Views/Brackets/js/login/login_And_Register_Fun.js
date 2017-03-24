var container = $('#main-container');

function loginFunctionality() {
    
    var log = '<div class="center-table" align="center">' +
                '<h2 style="text-align:center">Login</h2>' +
                '<br><br>' +

                '<span id="lr-errors"></span>' +
        
                '<div class="form-group" style="text-align:center">' +
                  '<input type="text" class="form-control netId" placeholder="Net ID">' +
                '</div>' +
                
                '<div class="form-group" style="text-align:center">' +
                  '<input type="email" class="form-control emailId" placeholder="Email">' +
                '</div>' +

                '<div class="form-group" style="text-align:center">' +
                  '<input type="password" class="form-control passwordId" placeholder="Password">' +
                '</div>' +
                
                '<div class="form-group" style="text-align:center">' +
                  '<select class="selectpicker" id="usertype">' +
                    '<option value="student">Student</option>' +
                    '<option value="teachingassistant">Teaching Assistant</option>' +
                    '<option value="instructor">Instructor</option>' +
                  '</select>' +
                '</div>' +
                
                  '<div style="text-align:center">' +
                    '<button type="submit" class="btn btn-default btn-lg" id="btn-login">Login</button>' +
                '</div>' +
                '<br>' +
                '<div style="text-align:center">' +
                  '<button type="button" class="btn btn-link btn-register">First Time Student User? Register Here!</button>' +
                '</div>' +
               '</div>';
    
//    container.append(log);
    
    return log;
}


function registerFunctionality() {
    
    var log = '<div class="center-table" id="register-div" align="center">' +
                '<h2 style="text-align:center">Registration</h2>' +
                '<br><br>' +
        
                '<span id="lr-errors"></span>' +

                '<!-- Registration Details -->' +  
                '<label>Credentials</label>' +
                '<div class="form-group" style="text-align:center">' +
                  '<input type="text" class="form-control netId" placeholder="Net ID">' +
                '</div>' +
                
                '<div class="form-group" style="text-align:center">' +
                  '<input type="email" class="form-control emailId" placeholder="Email">' +
                '</div>' +

                '<div class="form-group" style="text-align:center">' +
                  '<input type="password" class="form-control passwordId" placeholder="Password">' +
                '</div>' +

                '<div class="form-group" style="text-align:center">' +
                  '<input type="password" class="form-control confirmPasswordId" placeholder="Retype Password">' +
                '</div>       ' +           
                  '<br><br>' +
                  
                '<!-- Personal Details -->  ' +
                '<label>Personal Details</label>' +
                '<div class="form-group" style="text-align:center">' +
                  '<input type="text" class="form-control firstname" placeholder="First Name">' +
                '</div>' +
                
                '<div class="form-group" style="text-align:center">' +
                  '<input type="email" class="form-control lastname" placeholder="Last Name">' +
                '</div>' +
                  
                '<div style="text-align:center">' +
                    '<button type="submit" class="btn btn-default btn-lg btn-cancel" id="submit">Cancel</button>' +
                    '<button type="submit" class="btn btn-info btn-lg btn-next" id="submit">Next</button>' +                    
                '</div>' +           
                  
               '</div>';
    
//    container.append(log);
    
   return log;
}

function requestRegistrationInfo() {

    var url = 'get/registerinfo.json';
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        complete: function(transport){
            console.log('In Registration Page');
            if (200 == transport.status) {
                result = transport.responseText;
                console.log(result);
                arrangeRegistrationFields(JSON.parse(result));
            } else {
                arrangeExperiments("Connection error");
            }
        }
    });
}

function navigatePage(url) {
    $.ajax({
        url: url,
        type: 'post',
        contentType: 'application/json',
        dataType: 'json',
        complete: function(transport){
           if (200 == transport.status) {
            }
        }
    });
}
               

function addSlotTable(result) {
        
    function addTableBody(data) {
        console.log('add slot table');
        
        function getDayLength(days) {
            var count = 0;
            $.each(days, function(key, value) {
                count += value.details.length;
            });
            return count;
        }
        
        function addSlots(data) {
            var slot = 1;        
            var tr = "", bflag = true; 

            console.log('inside add slots');
            
            console.log(data);
            
            $.each(data, function(key, value) {

                var da = value;
                tr += '<tr><td rowspan='+getDayLength(da.dayDetails)+'>'+da.day+'</td>';
                
                console.log('one');
                
                var aflag = true; 
                $.each(da.dayDetails, function(key2, value2) {

                    console.log('two');
                    
                        if(!aflag) { tr += '<tr>'; }
                        tr += '<td rowspan='+value2.details.length+'>'+value2.time+'</td>'

                        var flag = true;
                        $.each(value2.details, function(key3, value3) {

                            console.log('three');
                            
                            if(!flag) { tr += '<tr>'; }

                            tr += '<td>'+value3.classNo+'</td>';
                            tr += '<td>'+value3.room+'</td>';
                            tr += '<td>'+value3.taName+'</td>';                        
                            tr += '<td><button type="button" data-class='+value3.classNo+' class="btn btn-link slot-btn">Slot '+slot+'</button></td>'; 
                            tr += '</tr>';                       

                            slot += 1;
                            flag = false;
                        });

                        aflag = false;
                });
            });
            
            console.log('add tr');
            
            return tr;
        }

       function requestSlotregistration(data) {
           var url = 'register/studentslot.json';

            $.ajax({
             url: url,
            type: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: 'json',
              complete: function(transport){
                    console.log('In Student Slot');
                    if (200 == transport.status) {
                        result = transport.responseText;
                        console.log(result);
                        if(result === 'navigate_student') {
                            console.log('move : '+data.type);
                            window.location = 'move-student.com';
                        } else if(result === 'slot_is_no_more') {
                            console.log('move to login ');

                            container.html("");
                            container.append(loginFunctionality);
                        }
                     //   addTableBody(JSON.parse(result));
                    } else {
                        console.log('Failed TA Registration');
                    }
                }
            });
        }
        
        function clickSlots() {
            // If TA No need of slotBtns code
            var slotBtns = tbody.find('.slot-btn');
            slotBtns.on('click', function() {
                var no = $(this).data('class');
                console.log('clicked slot btn : '+no);   
                requestSlotregistration(no);
            });
        }
        
        function addTableHead() {
            var div = '<div class="table-responsive">' +
                    '<table class="table table-hover">' +
                        '<thead>' +
                            '<th>Day</th>' +
                            '<th>Time</th>' +
                            '<th>Room</th>' +
                            '<th>Class #</th>' +
                            '<th>TA Name</th>' +
                            '<th>Slot #</th>' +                    
                        '</thead>' +
                        '<tbody id="reg-tbody">' +

                        '</tbody>' +
                    '</table>' +
                '</div>';
            
            return div;
        }
        
       // var contain = $('#register-div');
         var contain = container;
        contain.html("");
        contain.append(addTableHead());
        var tbody = contain.find('#reg-tbody');
        tbody.append(addSlots(data));
        clickSlots();

        //        var sem = data.sem, year = data.semYear;
    }
    
  // requestTAregistration();
    
    addTableBody(result);
}

               
               
function loginUserInformation(url, data) {
     
    console.log(data);
            
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json',
        complete: function(transport){
            console.log('In onComplete');
            if (200 == transport.status) {
                result = transport.responseText;
                if (result === "login_failure") {
                    $('#lr-errors').html("Please check your credentials");
                } else if (result === "register_failure") {
                    $('#lr-errors').html("Please check your registration credentials");                    
                } else if (result === "already_registered") {
                    $('#lr-errors').html("User is already registered");                    
                } else if (result === "connection_lost") {
                    $('#lr-errors').html("Connection Lost");                    
                } else if (result === "slots_not_available") {
                    $('#lr-errors').html("Slots are Not Available");                    
                } else if(result === 'login_success') {
                    var url = "login_success";
                    navigatePage(url);
                    //or use href;
                    window.location = 'move-'+data.type+'.com';
                }  else {
                    // create tables
                    console.log('Adding Tables')
                    addSlotTable(JSON.parse(result));
                }
                // If its success go to the respective page (Instrucor, Student, TA)
               console.log(result);
            } else {
                $('#lr-errors').html("Failed to connect");
            }
        }
    });
}

var netId, email, pswd, type, firstname, lastname;
function registerInfo(netId, email, pswd, type, firstname, lastname) {
    this.netId = netId;
    this.email = email;
    this.pswd = pswd;
    this.type = type;    
    this.firstname = firstname;
    this.lastname = lastname;
}


function registerAction() {
     var cancelBtn = container.find('.btn-cancel');
    cancelBtn.on('click', function() {
        container.html("");
         container.append(loginFunctionality);
         loginAction();
    });
 
    var nextBtn = container.find('.btn-next');
    nextBtn.on('click', function() {
        //loginFunctionality();
        console.log('Request Next registration Information');
        
        var container  = $('#register-div');
        container.css({'border':'1px solid red'});
        
        var netId = container.find('.netId').val(),
            email = container.find('.emailId').val(),
            pswd = container.find('.passwordId').val(),
            cpswd = container.find('.confirmPasswordId').val(),
            userType = 'student',
            fname = container.find('.firstname').val(),
            lname = container.find('.lastname').val();    

        console.log('NI :'+netId);
        console.log('EM :'+email);
        console.log('PD :'+pswd);
        console.log('UT :'+userType);    
        console.log('FN :'+fname);
        console.log('LN :'+lname);    
        if(pswd !== cpswd) {
            $('#lr-errors').html("Please check your registration credentials");  
        } else {        
            var data = new registerInfo(netId, email, pswd, userType, fname, lname),
                url = "register/validation";
          //  sendLoginRegister(url, data, callBackRegularLogin)
            loginUserInformation(url, data);
        }
    });
}

var netId, email, pswd, type;
function loginInfo(netId, email, pswd, type) {
    this.netId = netId;
    this.email = email;
    this.pswd = pswd;
    this.type = type;    
}

function loginAction() {
    
    var loginBtn = container.find('#btn-login');
    console.log('In login Action');
    
    loginBtn.on('click', function() {

        var netId = container.find('.netId').val(),
            email = container.find('.emailId').val(),
            pswd = container.find('.passwordId').val(),
            userType = container.find('#usertype').val();    

        console.log('NI :'+netId);
        console.log('EM :'+email);
        console.log('PD :'+pswd);
        console.log('UT :'+userType);    

        var data = new loginInfo(netId, email, pswd, userType),
            url = "login/validation";
      //  sendLoginRegister(url, data, callBackRegularLogin)
        loginUserInformation(url, data);
    });
    
    var registerBtn = container.find('.btn-register');
    registerBtn.on('click', function() {
        console.log('Pressed registeration');
        container.html("");
        container.append(registerFunctionality);
        registerAction();
    });

}

 container.append(loginFunctionality);
loginAction();