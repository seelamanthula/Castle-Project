function addUserAccess() {

    var div =  '<!-- Aside Information -->' +
                  '<div class="panel-group" id="accordion">' +
                    '<!-- Automatic Excel Class Information -->' +
                      '<div class="panel panel-default">' +
                      '<div class="panel-heading">' +
                        '<h4 class="panel-title">' +
                          '<a data-toggle="collapse" data-parent="#accordion" href="#collapse1">Automatic Class Information</a>' +
                        '</h4>' +
                      '</div>' +
                        '<!--    This is an Automatic Access-->     ' +                     
                      '<div id="collapse1" class="panel-collapse collapse in">' +
                        '<div class="panel-body">' +
                          '</div>' +
                        '<div class="well">' +
                            '<div id="drop">Drop an XLSX / XLSM / XLSB / CSV file here to see sheet data</div>' +
                            '<p><input type="file" name="xlfile" id="xlf" /></p>      ' + 
                        '</div>' +
                        '<div class="well" id="class-info">' +
                            '<h4>Class Information</h4>' +
                            '<div class="table-responsive">' +
                            '<table class="table" id="class-table">' +
                              '<tbody>' +
/*                                '<tr>' +
                                  '<th scope="row">Semester </th>' +
                                  '<td><input type="text" class="form-control" name="new-semester" id="new-semester" required></td>' +
                                '</tr>' +*/
                                  '<tr>' +
                                  '<th scope="row">Day</th>' +
                                  '<td>' +
                                    '<select id="day">' +
                                      '</select>' +
                                    '</td>' +
                                  '<th scope="row">Time</th>' +
                                  '<td>' +
                                     '<select id="time">' +
                                      '</select>' +
                                    '</td>' +
                                '</tr>' +
                                '<tr>' +
                                  '<th scope="row">Teaching Assistant</th>' +
                                    '<td>' +
                                     '<select id="assistant">' +
                                      '</select>' +
                                    '</td>' +
                                  '<th scope="row">Class #</th>' +
                                    '<td>' +
                                     '<select id="classnos">' +
                                      '</select>' +
                                    '</td>' +
                                '</tr>' +
                              '</tbody>' +
                            '</table>' +

                            '<button type="button" class="btn btn-primary" id="btn-class-info">submit</button> ' +
                            '</div>' +
                        '</div>' +
                      '</div>' +
                    '</div>' +

                    '<!-- Manual Add User -->              ' +
                      '<div class="panel panel-default">' +
                      '<div class="panel-heading">' +
                        '<h4 class="panel-title">' +
                          '<a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Manual Add User</a>' +
                        '</h4>' +
                      '</div>' +
                      '<div id="collapse2" class="panel-collapse collapse">' +
                        '<div class="panel-body">' +
                            '<!--This is a Manual Access.-->' +
                          '<div id="user-manual">' +
                            '<ul class="ul-span list-group" id="add-usr-ins-ta-stu">' +
                                '<li class="add-usr list-group-item" data-permission="instructor">' +
                                    '<p class="add-stu-ta-ins"><b><span class="glyphicon glyphicon-chevron-down"><b><font face="verdana">Add Instructor</font></b></span></b></p>' +
                                    '<div class="display-block">' +
                                        '<span class="error-msg"></span>' +
                                        '<span class="msg-success"></span>' +
                                        '<div class="instructor-table  table-responsive">    ' +                                       
                                            '<table class="table" data-table="instructor">' +
                                                '<tr>' +
                                                    '<th>NetID</th>' +
                                                    '<td><input type="text" class="form-control netid-validate" id="add-ins-netid" required></td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Email ID</th>' +
                                                    '<td><input type="email" class="form-control email-validate" id="add-ins-emailid" required></td>' +   
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>First Name</th>' +
                                                    '<td><input type="text" class="form-control firstname-validate" id="add-ins-fname" required></td>' +                                    
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Last Name</th>' +
                                                    '<td><input type="text" class="form-control lastname-validate" id="add-ins-lname" required></td>' +                                    
                                                '</tr>' +
                                                '<tr>' +
                                                    '<td colspan="2"><button type="button" class="btn btn-success btn-add-usr" id="add-ins-btn">Add Instructor</button></td>' +
                                                '</tr> ' +                               
                                            '</table>' +
                                        '</div>' +
                                    '</div>' +
                                '</li>' +
                                '<li class="add-usr list-group-item" data-permission="teaching-assistant">' +
                                    '<p class="add-stu-ta-ins"><b><span class="glyphicon glyphicon-chevron-down"><b><font face="verdana">Add Teaching Assistant</font></b></span></b></p>' +
                                    '<div class="display-block">' +
                                        '<span class="error-msg"></span>' +
                                        '<span class="msg-success"></span>' +
                                        '<div class="teachingassistant-table  table-responsive">' +
                                           
                                            '<table class="table" data-table="teachingassistant">' +
                                                '<tr>' +
                                                    '<th>NetID</th>' +
                                                    '<td><input type="text" class="form-control netid-validate" id="add-ta-netid" required></td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Email ID</th>' +
                                                    '<td><input type="email" class="form-control email-validate" id="add-ta-emailid" required></td>' +   
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>First Name</th>' +
                                                    '<td><input type="text" class="form-control firstname-validate" id="add-ta-fname" required></td>' +                               
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Last Name</th>' +
                                                    '<td><input type="text" class="form-control lastname-validate" id="add-ta-lname" required></td>' + 
                                                '</tr>  ' +                                              
                                                '<tr>' +
                                                    '<th>Time</th>' +
                                                    '<td><input type="time" class="form-control time-validate" id="add-ta-time" required></td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Class Number</th>' +
                                                    '<td><input type="text" class="form-control classnumber-validate" id="add-ta-classnumber" required></td>' +                                    
                                                '</tr>  ' +                                  
                                                '<tr>' +
                                                    '<th>Day</th>' +
                                                    '<td><select class="selectpicker multipleday-validate" name="add-day" id="add-ta-day" multiple>' +
                                                            '<option value="sunday">Sunday</option>' +
                                                            '<option value="monday">Monday</option>     ' +                                   
                                                            '<option value="tuesday">Tuesday</option>' +
                                                            '<option value="wednesday">Wednesday</option>' +
                                                            '<option value="thursday">Thursday</option>' +                                        
                                                            '<option value="friday">Friday</option>' +
                                                        '<option value="saturday">Saturday</option>' +                                            
                                                        '</select>' +
                                                    '</td> ' +                                   
                                                '</tr>  ' +                           
                                                '<tr>' +
                                                    '<td colspan="2"><button type="button" class="btn btn-success btn-add-usr" id="add-ta-btn">Add Teaching Assistant</button></td>' +
                                                '</tr>  ' +                              
                                            '</table>' +                                        
                                        '</div>' +
                                    '</div>' +
                                '</li>' +
                                '<li class="add-usr list-group-item" data-permission="student">' +
                                    '<p class="add-stu-ta-ins"><b><span class="glyphicon glyphicon-chevron-down"><b><font face="verdana">Add Student</font></b></span></b></p>' +
                                    '<div class="display-block">' +
                                        '<span class="error-msg"></span>' +
                                        '<span class="msg-success"></span>' +
                                        '<div class="student-table table-responsive">' +
                                            '<table class="table" data-table="student">' +
                                                '<tr>' +
                                                    '<th>NetID</th>' +
                                                    '<td><input type="text" class="form-control netid-validate" id="add-stu-netid" required></td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Email ID</th>' +
                                                    '<td><input type="email" class="form-control email-validate" id="add-stu-emailid" required></td>' +   
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>First Name</th>' +
                                                    '<td><input type="text" class="form-control firstname-validate" id="add-stu-fname" required></td>'+
                                                    '</tr>' +
                                                '<tr>' +
                                                    '<th>Last Name</th>' +
                                                    '<td><input type="text" class="form-control lastname-validate" id="add-stu-lname" required></td>' +
                                                    '</tr>' +
                                                '<tr>' +
                                                    '<th>Time</th>' +
                                                    '<td><input type="text" class="form-control time-validate" id="add-stu-time" required></td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<th>Class Number</th>' +
                                                    '<td><input type="text" class="form-control classnumber-validate" id="add-stu-classnumber" required></td>' +
                                                '</tr>  ' +                                  
                                                '<tr>' +
                                                    '<th>Day</th>' +
                                                    '<td><select class="selectpicker multipleday-validate" name="add-day" id="add-stu-day">' +
                                                            '<option value="sunday">Sunday</option>' +
                                                            '<option value="monday">Monday</option>     ' +                                   
                                                            '<option value="tuesday">Tuesday</option>' +
                                                            '<option value="wednesday">Wednesday</option>' +
                                                            '<option value="thursday">Thursday</option>' +                                        
                                                            '<option value="friday">Friday</option>' +
                                                        '<option value="saturday">Saturday</option>' +                                            
                                                        '</select>' +
                                                    '</td>  ' +                                  
                                                '</tr>  ' +                           
                                                '<tr>' +
                                                    '<td colspan="2"><button type="button" class="btn btn-success tn-add-manual" id="add-stu-btn">Add Student</button></td>' +
                                                '</tr>  ' +                              
                                            '</table>' +
                                        '</div>' +
                                    '</div>' +
                                '</li>' +
                               
                            '</ul>' +
                          '</div>' +
                        '</div>' +
                     '</div>' +
                     '</div>' +
                    '</div>' +

                   '<!-- In Future Manual Delete User --> ' +             
                     '<div class="panel panel-default">' +
                          '<div class="panel-heading">' +
                            '<h4 class="panel-title">' +
                              '<a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Delete User</a>' +
                            '</h4>' +
                          '</div>' +
                          '<div id="collapse3" class="panel-collapse collapse">' +
                            '<div class="panel-body">' +
                                '<div id="manual-ta-del">' +
                                '<div class="row">' +
                                    '<div class="col-lg-12">' +
                                        '<h2>TA List</h2>' +
                                        
                                        '<div class="table-responsive">' +
                                            '<table class="table table-bordered table-hover table-striped">' +
                                                '<thead>' +
                                                    '<tr>' +
                                                        '<th>Net ID</th>' +
                                                        '<th>Full Name</th>' +
                                                        '<th>Class #</th>' +                              
                                                        '<th>Days</th>    ' +                                                                  
                                                        '<th>Times</th>' +
                                                        '<th>Action</th>' +
                                                    '</tr>' +
                                                '</thead>' +
                                                '<tbody class="table-body-del-user">' +
                                                '</tbody>' +
                                            '</table>' +
                                        '</div>' +
                                        
                                        '<!-- Indicates a dangerous or potentially negative action -->' +
                                        '<button type="button" class="btn btn-danger btn-lg btn-block user-trash" style="float:left"><span class="glyphicon glyphicon-trash"></span>&nbsp;Delete</button>' +
                                    '</div>' +
                                '</div>' +
                                '</div>' +
                              '</div>' +
                          '</div>' +
                        '</div>';
                                        
            return div;
}


function addPanelAccess() {

     var div = '<div class="panel-group" id="accordion">' +
                    '<!-- Automatic Excel Class Information -->' +
                      '<div class="panel panel-default">' +
                      '<div class="panel-heading">' +
                        '<h4 class="panel-title">' +
                          '<a data-toggle="collapse" data-parent="#accordion" href="#collapse1">Automatic Class Information</a>' +
                        '</h4>' +
                      '</div>' +
                        '<!--    This is an Automatic Access-->                        ' +  
                      '<div id="collapse1" class="panel-collapse collapse in">' +
                        '<div class="panel-body">' +
                          '</div>' +
                            
                        '<div class="well">' +
                            '<div id="drop">Drop an XLSX / XLSM / XLSB / CSV file here to see sheet data</div>' +
                            '<p><input type="file" name="xlfile" id="xlf" /></p>       ' +
                        '</div>' +
                        
                        '<span id="auto-errors"></span>' +
                        '<div class="well">' +
                            '<select id="reg-semester">'+
                                '<option value="Fall">Fall</option>' +
                                '<option value="Spring">Spring</option>' +
                                '<option value="Summer">Summer</option>' +
                                '<option value="Winter">Winter</option>' +         
                            '</select>' +
                            
                            '<input type="text" class="sem-year" placeholder="Semester Year" id="reg-sem-year"> ' +
                        '</div>' +
                
                            '<button type="button" class="btn btn-primary" id="btn-class-info">Show</button> ' +
         
                       '<div id="access-register-container"></div>' +
                            '<span id="arc-errors"></span>' +
                      '</div>' +
                    '</div>' +
                '</div>';
    
    return div;
}

function addSlotTable(call) {
    
    console.log('call : '+call);
    
    var result = call;
    
    function addTable() {     
        var div = '<div class="table-responsive">' +
                    '<table class="table table-hover" align="center">' +
                        '<thead>' +
                            '<th>Day</th>' +
                            '<th>Time</th>' +
                            '<th>Room</th>' +
                            '<th>Class #</th>' +
                            '<th>TA Name</th>' +
                           // '<th>Slot #</th>' +                    
                        '</thead>' +
                        '<tbody id="reg-tbody">' +

                        '</tbody>' +
                    '</table>' +
                    '<button type="button" class="btn btn-primary" id="btn-store-info">Store Information</button> ' +
                '</div>';
        
        return div;
    }
   
    function reservedSlot(data) {}
    
    function addTableBody(data) {
        console.log(data);
 
        function getDayLength(days) {
            var count = 0;
            $.each(days, function(key, value) {
                count += value.details.length;
            });
            return count;
        }
        
        function appendTable(data) {
            var tr = "", bflag = true; 
            
	        var slot = 1;        
	        $.each(data, function(key, value) {
	            
	            var da = value;
	            tr += '<tr><td rowspan='+getDayLength(da.dayDetails)+'>'+da.day+'</td>';
	            
	            var aflag = true; 
	            $.each(da.dayDetails, function(key2, value2) {
	                
	                    if(!aflag) { tr += '<tr>'; }
	                    tr += '<td rowspan='+value2.details.length+'>'+value2.time+'</td>'
	           
	                    var flag = true;
	                    $.each(value2.details, function(key3, value3) {
	
	                        if(!flag) { tr += '<tr>'; }
	                        
	                        tr += '<td>'+value3.classNo+'</td>';
	                        tr += '<td>'+value3.room+'</td>';
	                        tr += '<td>'+value3.taName+'</td>';                        
	                //        tr += '<td><button type="button" data-class='+value3.classNo+' class="btn btn-link slot-btn">Slot '+slot+'</button></td>'; 
	                        tr += '</tr>';                       
	                        
	                        slot += 1;
	                        flag = false;
	                    });
	                
	                    aflag = false;            
	            });
	        });
	        return tr;
        }
       
        var sem, year, classno;
        function reserveSlot(sem, year, classno) {
            this.sem = sem;
            this.year = year;
            this.classno = classno;
        }
        
        function requestTAregistration(res) {
            var url = 'register/studentRegistration.json';

            $.ajax({
               url: url,
               type: 'post',
               data: JSON.stringify(data),
               contentType: 'application/json',
               dataType: 'json',
               complete: function(transport){
                    console.log('In Student Search');
                    if (200 == transport.status) {
                        result = transport.responseText;
                    //    console.log(result);
                      //  addTableBody(JSON.parse(result));
                    //    addSlotTable(JSON.parse(result));
                         $('#arc-errors').html("Store Registration Details");
                    } else {
                         $('#arc-errors').html("Unable to store Registration");
                        console.log('Failed TA Registration');
                    }
                }
            });
        }
        
        // var sem = data.sem, year = data.semYear;
        
        // If TA No need of slotBtns code
        var tbody = contain.find('#reg-tbody');
        tbody.html("");
        tbody.append(appendTable(data));
        
        var slotBtns = $('#access-register-container').find('#btn-store-info');
        slotBtns.on('click', function() {
          //  var no = $(this).data('class');
            console.log('In btn-store-info');    
            requestTAregistration(result);
        });
    }
    
    var contain = $('#access-register-container'); 
    contain.html("");
    contain.append(addTable());
    
    addTableBody(result);
    
   // contain.find('#btn-store-info').css({'border':'1px solid red'});
 
   
    
}

function getParseJSONInformation(data) {
    var url = 'parse/registration.json';
    
    console.log(JSON.stringify(data));
    
  //  var url = 'json/studentRegistration.json';
        
       /* $.ajax({
          dataType: "json",
          url: url,
          complete: function(transport){
                console.log('In Student Search');
                if (200 == transport.status) {
                    result = transport.responseText;
                //    console.log(result);
                 //   addTableBody(JSON.parse(result));
                    $('#arc-errors').html("");
                    addSlotTable(JSON.parse(result));
                } else {
                    
                    $('#arc-errors').html("Unable to show registration details");
                    console.log('Failed TA Registration');
                }
            }
        });*/
    
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
            //    callback();
            //   console.log(result);
               $('#arc-errors').html("");
                addSlotTable(JSON.parse(result));
            } else {
                  $('#arc-errors').html("Unable to show registration details");
                console.log('Request lost in accessTab');
            }
        }
    });
}

var sem, year, info;
function registrationDetails(sem, year, info) {
    this.sem = sem;
    this.year = year;
    this.info = info;
}

/* 
    Receives the JSON information from EXcel Sheet
*/
function arrangeJSONClassInformation(output) {
    
    var sheetInfo = output.Sheet1;
    console.log(sheetInfo);
    var accessContainer = $('#access-container');
    
    accessContainer.find('#btn-class-info').on('click', function() {
        console.log('clicked btn');
        
        var a = accessContainer.find('#reg-semester').val();
        var b = accessContainer.find('#reg-sem-year').val();
        var id = accessContainer.find('#auto-errors');    

        if(b) {
            id.html("");
            var c = new registrationDetails(a, b, sheetInfo)
            getParseJSONInformation(c);
        } else {
            id.html("Enter Semester Year");
        }
    });

   // calculateValues();
}

function arrangeAccessTab() {
    
    var accessContainer = $('#access-container');
        
    accessContainer.html("");
    accessContainer.append(addPanelAccess);
    checkExcel();
    
}

function addAccessTab() {
    arrangeAccessTab();
}