var session = getSessionStorage();

function openCity(evt, cityName) {
        var i, x, tablinks;
        x = document.getElementsByClassName("city");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablink");
        for (i = 0; i < x.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" w3-blue", "");
        }
        console.log('CN : '+cityName);
        document.getElementById(cityName).style.display = "block";
        evt.currentTarget.className += " w3-blue";
    }

function openPdf(evt, pdfTab) {
        var i, x, tablinks;
        x = document.getElementsByClassName("pdf");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none"; 
        }
        tablinks = document.getElementsByClassName("pdflink");
        for (i = 0; i < x.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" w3-grey", "");
        }
        console.log('CN : '+pdfTab);
        document.getElementById(pdfTab).style.display = "block"; 
         evt.currentTarget.className += " w3-grey";
    }


function arrangeStudentAssessmentPrelabDivs() {

	function getContentByURL(url) {
        console.log(url);

       $.ajax({
            url: url,
            dataType: 'json',
            complete: function(transport){
                if (200 == transport.status) {
                  //  console.log(transport.responseText);
                    
                    var txt = transport.responseText;
                    if(txt === "no-experiment") {
                        window.location.href='user-logout.edu';
                    } else {
                        result = JSON.parse(txt);
         //               console.log(result);

                        if(result.type === 'Assessment') { // Lets change to new-module-type
                            session.setItem("dataQIds", transport.responseText);                        
                            console.log(result);
                            arrangeAssessmentContent(result);
                        }
                    }

                }
            }
        });        
    }

    
    function readStudentContentPage() {
        
         var snetid = session.getItem("netid");
         console.log('ss : '+snetid);
                         
         var expid = $('#expid').html(),
             moduleid = $('#moduleid').html(),
             netid = $('#netid').html();

        // No content saved by default
        session.setItem("assessment_store", "no");
        session.setItem("assessment_questions", "null");
        session.setItem("assessment_answers", "null");
        session.setItem("assessment_modulepoints", "null");
        
        if(netid === undefined) {
            console.log('attacking user');
            window.location.href="user-logout.edu";
        }
        else if(snetid === "null" || snetid === null || snetid !== netid) {

            session.setItem("netid", netid);
            session.setItem("expid", expid);
            session.setItem("moduleid", moduleid);       

            var url = 'request/assessment/'+expid+'/moduledata/'+moduleid;

          //  var url = 'json/student/assessment.json';
            getContentByURL(url);

        } else if(snetid === netid){
            
                console.log('In equals log same person');
            
                if(expid === session.getItem("expid") && moduleid === session.getItem("moduleid")) {
                    console.log("nem are saved in sessions");
                
                    var a = session.getItem("dataQIds");
                    console.log(a);
                    // If the module question Ids are already there
                    if(a === null || a === "null") {
                        
                        // content is not stored
                         session.setItem("assessment_store", "no");
                        var url = 'request/assessment/'+expid+'/moduledata/'+moduleid;
                        //var url = 'json/student/assessment.json';
                        getContentByURL(url);

                    } else {
                    	
                    	console.log("qIds are saved");
                         session.setItem("assessment_store", "no");
                         arrangeAssessmentContent(JSON.parse(a));
                    }
                           
                } else {
            
                    // When experiment ids AND module ids are not equal

                    console.log('When experiment and module ids are not equal');

                    session.setItem("netid", netid);
                    session.setItem("expid", expid);
                    session.setItem("moduleid", moduleid);  

                    // content is not stored
                     session.setItem("assessment_store", "no");

                    var url = 'request/assessment/'+expid+'/moduledata/'+moduleid;
                    //var url = 'json/student/assessment.json';
                    getContentByURL(url);              
            }
        }
        
    }
    
    readStudentContentPage();
}

function makeEverythingNULL() {    
    session.setItem("assessment_store", "no");
    session.setItem("assessment_questions", "null");
    session.setItem("assessment_answers", "null");
    session.setItem("assessment_modulepoints", "null");        
    session.setItem("dataQIds", "null");
}

function defaultButtons() {
    
    var profile = $('#user-profile'),
        logout = $('#user-logout'),
        cancel = $('#user-cancel');
    
    profile.on('click', function() {
       console.log('click profile'); 
        makeEverythingNULL();
    });
    
    logout.on('click', function() {
       console.log('click logout'); 
        makeEverythingNULL();
     window.location.href='user-logout.edu';
    });
    
    cancel.on('click', function() {
       console.log('click cancel');
        makeEverythingNULL();
        window.location.href='move-student.com';
    });
}


arrangeStudentAssessmentPrelabDivs();
defaultButtons();