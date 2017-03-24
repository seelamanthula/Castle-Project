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
    
    var tr = "", cts = "";
    
    function addTableHead() {
        var t = '<div id="modal-div" class="row status-table class="w3-container""> ' +  
                        '<h4>Hello ..</h4>' +
                        '<table  class="table table-responsive">' +
                            '<thead class="th-align">' +
                                '<tr>' +
                                    '<th>Experiment Name</th>' +
                                    '<th>Due Date</th>' +
                                    '<th>Assessment</th>' +
                                    '<th>Prelab</th>' +        
                                    '<th>Comments</th>' + 
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
    var mDiv = studentId.find('#modal-div');
    var tBody = studentId.find('#status-body');
    
    function addTableBody() {        
        
        $.each(exps, function(key, value) {
            
            tr += "<tr>";
            tr += "<td>"+value.Name+"</td>";
            tr += "<td>"+value.dueDate+"</td>";
            tr += "<td>"+value.assessmentScore+"&nbsp;<button type = 'button' data-exp='"+value.expid+"' class = 'btn btn-link assessment'>Link</button>&nbsp;"+"</td>";
            tr += "<td>"+value.prelabScore+"&nbsp;<button type = 'button' data-exp='"+value.expid+"' class = 'btn btn-link prelab'>Link</button>&nbsp;"+"</td>";
            if(value.taComments === undefined) {
                tr += "<td></td>";
            } else {
/*                tr += "<td><button type = 'button' data-exppos='"+key+"' data-exp='"+value.expid+"' class = 'btn btn-link exp-comments'>Link</button>&nbsp;"+"</td>";*/
                tr += "<td><button data-pos="+key+" class='w3-button ecomments'>link</button</td>";
                
                cts += '<div id="comment_'+key+'" class="w3-modal">' +
                        '<div class="w3-modal-content">' +
                          '<div class="w3-container">' +
                            '<span data-pos='+key+' class="w3-button hComments w3-display-topright">&times;</span>' +
                            '<p>'+value.taComments+'</p>' +
                            '<p></p>' +
                          '</div>' +
                        '</div>' +
                      '</div>';
            }
            
            tr += "</tr>";
            
        });
        
        tBody.append(tr);
        mDiv.append(cts);
    }
    
    function requestExperiment(url) {
        console.log('exp : '+url);
     //   window.location.href=url;
    //    url = 'json/student/stu-module.json';
          $.ajax({
                url: url,
                dataType: 'json',
                complete: function(transport){
                    console.log('In Experiment Status');
                    if (200 == transport.status) {
                        result = JSON.parse(transport.responseText);
                        console.log('before type');
                        console.log(result);
                        if(result.type=== "Prelab") {
                            console.log("Inside prelab type");
                            addExperimentModules(result, "prelab");
                        } else if(result.type === "Assessment") {
                            addExperimentModules(result, "assessment");
                        }
                    }
                }
            });

    }
    
    function checkStatusButtons() {
        
        var assess = tBody.find('.assessment');
        var prelabs = tBody.find('.prelab');  
        var eComments = tBody.find('.ecomments');
        var hComments = mDiv.find('.hComments');
        
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
        
        console.log('E Comments len : '+eComments.length);
        eComments.on('click', function() {
           var pos = $(this).data("pos");
            console.log('Exp Position : '+pos);
            $('#comment_'+pos).show();
            
        });
    
        console.log('H Comments len : '+hComments.length);
        
        hComments.on('click', function() {
           var pos = $(this).data("pos");
            console.log('Exp Position : '+pos);
            $('#comment_'+pos).hide();
            
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
     window.location.href='user-logout.edu';
    });
    
    cancel.on('click', function() {
       console.log('click cancel');
        window.location.href='move-student.com';
    });
}

getStatusContent('json/student/stu-status.json');
edits();