session = getSessionStorage();

function openPrelab(evt, pdfTab) {
        var i, x, tablinks;
        x = document.getElementsByClassName("block");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none"; 
        }
        tablinks = document.getElementsByClassName("blocklink");
        for (i = 0; i < x.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" w3-grey", "");
        }
        console.log('CN : '+pdfTab);
        document.getElementById(pdfTab).style.display = "block"; 
         evt.currentTarget.className += " w3-grey";
    }

String.prototype.escapeSpecialChars = function() {
    return this.replace(/\\n/g, "\\n")
               .replace(/\\'/g, "\\'")
               .replace(/\\"/g, '\\"')
               .replace(/\\&/g, "\\&")
               .replace(/\\r/g, "\\r")
               .replace(/\\t/g, "\\t")
               .replace(/\\b/g, "\\b")
               .replace(/\\f/g, "\\f");
};

function arrangePrelabContent(data) {
    
    console.log(data);
    var pdfId = $('#pdf'),
        id = $('#question-link'),
        panel = $('#question-panel'),
        mErrors = $('#module-errors');

        
    var dataExp = data.experiment,
        dataModule = data.module;
    
    function addPDF() {

        function getRequestFile(url) {
                console.log(url);

                $.ajax({
                    url: url,
                    dataType: 'json',
                    complete: function(transport){
                        if (200 == transport.status) {
                          //  console.log(transport.responseText);
                            result = JSON.parse(transport.responseText);
                           // console.log(result[0]);
                            pdfId.html("");
                             var obj = '<object width="100%" height="600" data="'+result[0].fileX+'" type="'+result[0].extension+'"></object>';
                            pdfId.append(obj);
                         }
                    }
                });        
            }

        var url =  'request/experiment-file/'+dataExp.expId;
        getRequestFile(url);

    }
    
    
    function addHTMLBasics() {
        var expName = $('#expname'),
            moduleName = $('.modulename'),
            fName = $('#user-name');
        
        expName.html(dataExp.expName.toUpperCase());
        moduleName.html(dataModule.name.toUpperCase());
        fName.html(data.firstName);
    }
    
     function updatePrelabAnswers(url, data) { 
         
         console.log(url);
         
            $.ajax({
                url: url,
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json',
                complete: function(transport){
                    console.log('In content');
                    if (200 == transport.status) {
                        result = transport.responseText;
                      // console.log(result);
                        if(result === "success") {
                            console.log(result);
                        } else {
                            // Display Error -- failed
                            mErrors.html("Connection Lost");
                        }
                    }
                }
            });
        }

    function addPrelabContent() {        
        
        var obj = panel.find('#stu_objective'),
            hyp = panel.find('#stu_hypothesis'),
            varia = panel.find('#stu_variables'),
            expOut = panel.find('#stu_exp_outline'),
            chem = panel.find('#stu_chem_hazards');
        
            var expid = $('#expid').html(),
                 moduleid = $('#moduleid').html(),
                 netid = $('#netid').html();

        function addContent(dataPrelab) {
            obj.html(dataPrelab.objective);
            hyp.html(dataPrelab.hypothesis);
            varia.html(dataPrelab.variables);
            expOut.html(dataPrelab.experimentOutline);
            chem.html(dataPrelab.chemicalHazards);            

        }
        
          function checkContent() {

                function requestContent(url) {
                        console.log(url);

                        $.ajax({
                            url: url,
                            dataType: 'json',
                            complete: function(transport){
                                if (200 == transport.status) {
                                    console.log(transport.responseText);
                                    if(transport.responseText === 'no-saving') {
                                         console.log('Data not saved');
                                    } else {
                                        var a = transport.responseText;
                                        var txt = a.escapeSpecialChars();
                                        console.log(txt);
                                        result = JSON.parse(txt);
                                        console.log(result);
                                         session.setItem("prelab-answers", JSON.stringify(result.prelab).escapeSpecialChars());
                                        addContent(result.prelab);
                                    }
                                 }
                            }
                        });        
                    }

                var url =  'check/'+netid+'/prelab/'+expid+'/moduledata/'+moduleid;
                requestContent(url);

            }
        
        function compareDate() {
                
                console.log('D Date :'+dataExp.duedate);
                var currDate = new Date(Date.now()),                
                    mydate = new Date(dataExp.duedate);
               // var mydate = new Date('02/15/2017');
                
                /* console.log(currDate);
                console.log(mydate)
*/
                if(currDate>mydate)   {
                    return false;
                }

                return true;
            }
        
              
           
        function disableTextArea() {
             panel.find('textarea').prop('disabled', true);
            mErrors.html("Due Date is passed");
        }
        
        function listenFields() {
            
            var objective, hypothesis,  variables, experimentOutline, chemicalHazards;

            function studentPrelab(objective, hypothesis,  variables, experimentOutline, chemicalHazards) {
                this.objective = objective;
                this.hypothesis = hypothesis;
                this.variables = variables;
                this.experimentOutline = experimentOutline;
                this.chemicalHazards = chemicalHazards;
            }
            
            var basics, prelab, uexpid, umodid, unetid;
            function txtprelab(basics, prelab) {
                this.basics = basics;
                this.prelab = prelab;
                this.lastupdate = new Date(Date.now());
            }
            
            function aExperiment(expId, moduleid, netid,uexpname,umodname) {
                this.uexpid = expId;
                this.uexpname = $('#expname').html();
                this.umodid = moduleid;
                this.unetid = netid;
                this.umodname = $('#modulename').html();
            }
            
            var semn = new aExperiment(expid, moduleid, netid);
    
       
            
            panel.find('textarea').on('change', function() {
               
                var k = new studentPrelab(obj.val().escapeSpecialChars(),hyp.val().escapeSpecialChars(),varia.val().escapeSpecialChars(),expOut.val().escapeSpecialChars(),chem.val().escapeSpecialChars());
                console.log('k : '+JSON.stringify(k));
                
                session.setItem("prelab-answers", JSON.stringify(k));
                var t = new txtprelab(semn,k),
                    url = 'update/prelab-answers';
                
                console.log('Before Comparision');
                if(compareDate()) {
                    updatePrelabAnswers(url,t)
                } else {
                     disableTextArea();
                }
            });
        }
        
        var ch = session.getItem("prelab-checkup");
        if(ch === "yes") {
            checkContent();            
        } else {
            addContent(JSON.parse(session.getItem("prelab-answers")));
        }
        
        if(compareDate()) {
            listenFields();
        } else {
            disableTextArea();
        }
    }

    addPDF();
    addHTMLBasics();
    addPrelabContent();
}
    
function arrangeStudentAssessmentPrelabDivs() {

    function getContentByURL(url) {
        console.log(url);

       $.ajax({
            url: url,
            dataType: 'json',
            complete: function(transport){
                if (200 == transport.status) {
                    console.log(transport.responseText);

                    if(transport.responseText === 'lost') { // Lets change to new-module-type
                        mErrors.html("Connection Lost");
                    } else if(transport.responseText === 'no-experiment') {
                         window.location.href='user-logout.edu';
                    } else { 
                        result = transport.responseText;
                        session.setItem("prelab-tabs", result);                        

                        console.log(result);
                        arrangePrelabContent(JSON.parse(result));
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
        console.log('netid : '+netid);
    
        if(netid === undefined) {
                console.log('attacking user');
                window.location.href="user-logout.edu";
            }
        else if(snetid === null || snetid !== netid) {
            
            console.log('new person');
            session.setItem("netid", netid);
            session.setItem("expid", expid);
            session.setItem("moduleid", moduleid);                
            var url = 'request/prelab/'+expid+'/moduledata/'+moduleid;

       //     session.setItem("prelab-checkup", "no");
            session.setItem("prelab-checkup", "yes");            
          //  var url = 'json/student/assessment.json';
            getContentByURL(url);

        } else if(snetid === netid){
            
                    console.log('Same person');
                    if(expid === session.getItem("expid") && moduleid === session.getItem("moduleid")) {
                        
                        console.log("nem are saved in sessions");
                
                        var a = session.getItem("prelab-tabs");
                        console.log(a);
                        if(a === "null" || a === null) {
                            
                            console.log("not stored no");
                        
                            session.setItem("prelab-checkup", "yes");
                            
                            var url = 'request/prelab/'+expid+'/moduledata/'+moduleid;
                            //   var url = 'json/student/prelab.json';
                            getContentByURL(url);
                    
                        } else {
                            // If 
                            session.setItem("prelab-checkup", "no");
                            console.log("stored yes");
                            arrangePrelabContent(JSON.parse(a));
                        }
                
                    } else {
                    
                        // When experiment and module ids are not equal

                        console.log('When experiment and module ids are not equal');

                        session.setItem("netid", netid);
                        session.setItem("expid", expid);
                        session.setItem("moduleid", moduleid);  

                        // content is not stored
                         session.setItem("prelab-checkup", "yes");

                        var url = 'request/prelab/'+expid+'/moduledata/'+moduleid;
                         //   var url = 'json/student/prelab.json';
                        getContentByURL(url);       
                    }
            } 
        
        }
    
    readStudentContentPage();
}

function makeEverythingNULL() {    
    session.setItem("prelab-checkup", "yes");
    session.setItem("prelab-tabs", "null");
    session.setItem("prelab_answers", "null");
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