var studentId = $('#student-container');
var session = getSessionStorage();
var profile = $('#user-profile');
var logout = $('#user-logout');
var cancel = $('#user-cancel');

function addContentStatus(content) {
    
    var userid = content.netid;
    session.setItem("student", userid);    
    $('#user-name').html(content.firstname);

    var exps = content.publishedExperiments;
    
    var tr = "";
    
    function addTableHead() {
        var t = '<div class="row status-table"> ' +  
                        '<h4>Hello ..</h4>' +
                        '<table  class="table table-responsive">' +
                            '<thead>' +
                                '<tr>' +
                                    '<th>Experiment Name</th>' +
                                    '<th>Due Date</th>' +
                                    '<th>Assessment</th>' +
                                    '<th>Prelab</th>' +                                    
                                '</tr>' +
                            '</thead>' +
                            '<tbody id="status-body">' +
                            '</tbody>' +
                        '</table>' +
                    '</div>';
                            
            return t;                
    }

    studentId.html("");
    studentId.append(addTableHead);
    
    var tBody = studentId.find('#status-body');
    
    function addTableBody() {        
        $.each(exps, function(key, value) {
            
            tr += "<tr>";
            tr += "<td>"+value.Name+"</td>";
            tr += "<td>"+value.dueDate+"</td>";
            tr += "<td>"+value.assessmentScore+"&nbsp;<button type = 'button' data-exp='"+value.expid+"' class = 'btn btn-link assessment'>Link</button>&nbsp;"+"</td>";
            tr += "<td>"+value.prelabScore+"&nbsp;<button type = 'button' data-exp='"+value.expid+"' class = 'btn btn-link prelab'>Link</button>&nbsp;"+"</td>";
            tr += "</tr>";
            
        });
        
        tBody.append(tr);
    }
    
    function requestExperiment(url) {
        console.log('exp : '+url);
     //   window.location.href=url;
        url = 'json/student/stu-module.json';
          $.ajax({
                url: url,
                dataType: 'json',
                complete: function(transport){
                    console.log('In Experiment Status');
                    if (200 == transport.status) {
                        result = JSON.parse(transport.responseText);
                        console.log(result);
                        
                        addExperimentModules(result);
                    }
                }
            });

    }
    
    function checkStatusButtons() {
        
        var assess = tBody.find('.assessment');
        var prelabs = tBody.find('.prelab');  
        
        assess.on('click', function() {            
            var exp = $(this).data('exp');
            var url = 'experiment/'+userid+'/assessment/'+exp;
            requestExperiment(url);
        });
        
        prelabs.on('click', function() {            
            var exp = $(this).data('exp');
            var url = 'experiment/'+userid+'/prelab/'+exp;            
            requestExperiment(url);        
        });
        
    }
    
    addTableBody();
    checkStatusButtons();
    
}

function getStatusContent(url) {
    console.log(url);
    
    $.ajax({
        url: url,
        dataType: 'json',
        complete: function(transport){
            if (200 == transport.status) {
                result = JSON.parse(transport.responseText);
                console.log(result);
                addContentStatus(result);
            }
        }
    });
}
   

function edits() {
    
    profile.on('click', function() {
       console.log('click profile'); 
    });
    
      logout.on('click', function() {
       console.log('click logout'); 
    });
    
    cancel.on('click', function() {
       console.log('click cancel');
        window.location.href='page/student.html ';
    });
}

getStatusContent('json/student/stu-status.json');
edits();