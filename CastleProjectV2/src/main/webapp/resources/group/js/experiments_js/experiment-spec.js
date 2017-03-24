var cont = $('#main-container');
var mod;

var id, name;
function moduleDetails(id, name) {
    this.id = id;
    this.name = name;
}

function getNewExperiment(id) {
    
    var exp = '<div id="'+id+'" class="margin-top experiment-div">' +
                '<ul class="ul-list">' +
                    '<li>' +
                        '<ul class="ne-errors">' +
                        '</ul>' +
                    '</li>' +
                    '<li>' +
                        '<ul class="disp-inline float-right">' +
                            '<li><button type="button" class="btn btn-primary btn-publish" id="'+id+'_NEpublish">Publish</button></li>' +
                            '<li><button type="button" class="btn btn-success btn-drafts" id="'+id+'_NEdraft">Save as Draft</button></li>' +
                            '<li><button type="button" class="btn btn-info btn-discard" id="'+id+'_NEdiscard">Discard</button></li>' +
                        '</ul>' +
                    '</li>' +
                    '<li><input type="text" name="experiment-name" id="experiment-name" class="experiment-name experi-box-size" placeholder="Experiment Name"></li>' +
//                    '<li><input type="file" name="experiment-file" id="experiment-file" class="experiment-file" nultiple></li>' +
                    '<li><input type="file" id="'+id+'_eF" name="experiment-file" multiple></li>' +
                    '<li><p>Due Date: <input type="text" id="datepicker"></p></li>' +
        
                '</ul>' +
            '</div>' +

        // '<button type="button" id="'+id+'_save" class="btn btn-danger">Save</button>' +
        
            '<button type="button" id="add_module" class="btn btn-danger">Add Module</button>' +

            '<div>' +
                '<div id="moduleList">' +

                '</div>' +
            '</div>';
    
    return exp;
}

var id, name;
function experimentDetails(id, name) {
    this.id = id;
    this.name = name;
}

function trackChangeWithExperimentName(d, cd) {   
    $('#'+cd).on('change', function() {
        
        newExperimentName = $(this).val();
        sendURL('new-experiment/name', new experimentDetails(d, newExperimentName), demo);
    });
}

var id, fMaps;
function experimentFiles(id, fMaps) {
    this.id = id;
    this.fMaps = fMaps;
}

function trackChangeWithExperimentFile(d) {   

	$('#'+d+'_eF').on('change', function() {
              
        var maps = []; 
        function prepareEXPERIMENTurl() {
            var act = maps,
                 url = "new-experiment/files";

            console.log('maps lenm : '+maps.length);
            var stat = sendURL(url, new experimentFiles(d, act), demo);
            if(stat === "success") {
                console.log('Its success');
             //   sendOrder();
            }
        }
        
        function parseFiles(id, type, back) {

            var iFiles = document.getElementById(id),
                filesLength = iFiles.files.length;
            var total = filesLength;
            for (var i = 0; i < filesLength; i++) {
                var f = iFiles.files[i];

                if (f) {
                    var r = new FileReader();
                    r.onload = function(e) { 
                        var contents = e.target.result;
                        var x = new fileContainer(type, f.type, f.name, contents);
                        maps.push(x);
                        total = total-1;
                        if(total === 0) {
                            back();
                        }
                    }
                     r.readAsDataURL(f);
                } else { 
                    alert("Failed to load file");
                }                           
            }
        }

        // Reading the files
        function readFilesOfEXPERIMENT(allSet) {
             var total = allSet.length;
            if(total === 0) {
                prepareEXPERIMENTurl();
            } else {
                // Reading the files
                $.each(allSet, function(key, value) {
                   total = total - 1;
                    if(total === 0) {
                        parseFiles(value.fid, value.type, prepareEXPERIMENTurl);                    
                    } else {
                        parseFiles(value.fid, value.type, demo);
                    }
                });
            }
        }

        // Collecting ids of files who has files
        var allSet = [], mainFiles;
        function checkFiles(id, type) {
            console.log('CF : '+id);
           // $('#'+id).css({border:'1px solid green'});
            var iFiles = document.getElementById(id),
            //var iFiles = $('#'+id+'_eF'),
                mainFiles = iFiles.files.length;
            console.log(mainFiles);

            if(mainFiles > 0) {
                allSet.push(new fileType(id, type));
            }
        }
           
    // Experiment files
    checkFiles(d+"_eF", "Exp");
    readFilesOfEXPERIMENT(allSet);            
    });
}

function publishValidations(d) {
    var exptab = cont.find('#'+d);
    var name = exptab.find('#experiment-name').val();
    var errors = exptab.find('.ne-errors');
    errors.html("");
    
    var k = "";
    var flag = true;
    
   function makeNENullVoid() {
        sessionStorage.setItem("newExperiment", "NO");
         sessionStorage.setItem("newexperimentId", "null")
        sessionStorage.setItem("newexperimentName", "null");
        sessionStorage.setItem("modulesList", "null");
        sessionStorage.setItem("moduleNamesList", "null");
        sessionStorage.setItem("orderList", "null");
        sessionStorage.setItem("allQuestionsList", "null");
         sessionStorage.setItem("expduedate", "null");
        
        newExperimentName = "";
        modulesList = [];
        orderList = [];
        allQuestionList = [];
        moduleNamesList = [];
    }
    
    function checkExperimentBasics() {
                
        function checkExperimentName() {
            if(name.length === 0) {
                // Specify Name
                console.log("No Experiment Name");
                k = "<li><p>Specify the Experiment Name</p></li>";

                flag = false;
            }
        }

         function checkDueDate() {
                var dpick = $('#datepicker').val();
                console.log(dpick);
                if(dpick === "") { console.log("No Data");
                    k += "<li><p>Choose the Due Date</p></li>";
                                  flag = false;
                }
            }

        function checkNamesOnModules() {
           if(modulesList !== null) {        
                var j = "";

                for(var i = 0; i < modulesList.length; i += 1) {
    //                console.log('MN : '+moduleNamesList[i].name);
                    if(moduleNamesList[i].name === "No Name" || moduleNamesList[i].name === "") {
                        j += "'"+(i+1)+"',";
                        console.log("It has no name at '"+(i+1)+"' module")
                    }
                }
               if(j.length > 0) {
                    k += "<li><p>Specify Module Names for : "+j+" Modules</p></li>";
                   flag = false;
                }
            }
        }   

        checkExperimentName();
        checkNamesOnModules();
        checkDueDate();
    
    }

      function getExperimentFileInfo(url, id) { 
    	  console.log(url);
            $.ajax({
                url: url,
                type: 'post',
                data: JSON.stringify(id),
                contentType: 'application/json',
                dataType: 'json',
                complete: function(transport){
                    console.log('In Experiment file check ');
                    if (200 == transport.status) {
                        result = transport.responseText;
                       console.log('f rES : '+result);        
                        checkExperimentBasics();
                        if(result === "failed") {
                            console.log('No File exists');
                            k += "<li><p>Upload the Experiment File</p></li>";

                            console.log(k);
                            flag = false;
                            console.log('Made flag false : '+flag);
                            
                            errors.append(k);
                        } else if(result === "move") {
                   
                            console.log("move");
                            makeNENullVoid();
                            window.location.href="move-instructor.com";
                            
                        } else {
                            console.log('In Teue : ');
                    //        makeNENullVoid();
                            var url = 'newexperiment/publish';
                            getExperimentFileInfo(url, id);
                   //         window.location.href="move-instructor.com";
                            
                        }
                    }
                }
            });
         }
        
    var eId;
    function get(id) {this.eId = id;}
    var fl = getExperimentFileInfo('check/experiment-file', d);
    
    console.log('FL .. '+fl);

}

function experimentChoices(d) {
    
	console.log('Exp D : '+d);
    
    var exptab = cont.find('#'+d);
    var errors = exptab.find('.ne-errors');
    var k = "";
     errors.html("");
    
    function makeNENullVoid() {
        sessionStorage.setItem("newExperiment", "NO");
         sessionStorage.setItem("newexperimentId", "null")
        sessionStorage.setItem("newexperimentName", "null");
        sessionStorage.setItem("modulesList", "null");
        sessionStorage.setItem("moduleNamesList", "null");
        sessionStorage.setItem("orderList", "null");
        sessionStorage.setItem("allQuestionsList", "null");
         sessionStorage.setItem("expduedate", "null");
        
        newExperimentName = "";
        modulesList = [];
        orderList = [];
        allQuestionList = [];
        moduleNamesList = [];
    }
    
     function checkAndMoveInstructor(url, id) { 
            console.log(url);
         
            $.ajax({
                url: url,
                type: 'post',
                data: JSON.stringify(id),
                contentType: 'application/json',
                dataType: 'json',
                complete: function(transport){
                    console.log('check buttons ');
                    if (200 == transport.status) {
                        result = transport.responseText;
                        console.log('buttons res : '+result);        
                        if(result === "failed") {
                            k += "<li><p>Cannot be made this operation this time</p></li>";
                        } else {
                            console.log('In Teue : ');
                            sessionStorage.setItem("newExperiment", "NO");
                            makeNENullVoid();
                            window.location.href="move-instructor.com";
                            
                        }
                    }
                }
            });
         }
    
    function buttonGroups() {

        exptab.find('#'+d+'_NEpublish').on('click', function() {

            console.log('In publish');
            // Do validations   
            var name = exptab.find('#experiment-name').val();        
        //    console.log('Name : '+name);
            var eId;
            function get(id) {this.eId = id;}

            publishValidations(d);
            
        });

        exptab.find('#'+d+'_NEdraft').on('click', function() {
            // d is the experiment id   
            checkAndMoveInstructor('newexperiment/draft', d);
        });

        exptab.find('#'+d+'_NEdiscard').on('click', function() {
            // d is the experiment id
            checkAndMoveInstructor('newexperiment/discard', d);            
        });
        
    }
    
    buttonGroups();
}


function addExperimentSpecs() {
    var id = Date.now();
  
    console.log('SSE : '+sessionStorage.getItem("newexperimentId"));
    
    if(sessionStorage.getItem("newexperimentId") !== null){
        id =  JSON.parse(sessionStorage.getItem("newexperimentId"));
    } else {
        sessionStorage.setItem("newexperimentId", id);
    }
  
    cont.append(getNewExperiment(id));
    
    
    function checkAndTrackDueDate() {
        $( "#datepicker" ).datepicker({ minDate: new Date(2007, 6, 12) });

        function trackDueDate(id, eid) {
            $('#'+id).on('change', function() {
                console.log('val : '+$(this).val());  
                sendURL('update/duedate', new eDueDate(eid, $(this).val(), demo));
            });
        }

        trackDueDate("datepicker", id);
    }
    
    checkAndTrackDueDate();
    
    var eId;
    function get(id) {this.eId = id;}
    
    $(document).ready(function() {
        sendURL('new-experiment/id', id, demo);
        
        console.log('sending new exp : '+id);
        
        trackChangeWithExperimentName(id, 'experiment-name');
        trackChangeWithExperimentFile(id);

        console.log('choices : '+id);
        
        experimentChoices(id);        
    });
}

function workModuleBtn() {
    var modBtn = $('#add_module');
    mod = $('#moduleList');

    modBtn.on('click', function() {
        var md = Date.now();
        mod.append(getModule(md));

        addModuleChoices(md);
        console.log('md : '+md);
        
        modulesList.push(md);
        moduleNamesList.push(new moduleDetails(md, "No Name"));

        trackChangeWithModule(md, md+'_MN');    

        removeItem(md, "Mod");

        applyModuleAccordion();
    });
}