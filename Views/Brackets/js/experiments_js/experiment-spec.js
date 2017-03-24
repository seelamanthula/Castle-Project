var cont = $('#main-container');
//var cont = $('#body-container');

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
                    '<li><p>Due Date: <input type="text" placeholder="Due Date" id="datepicker"></p></li>' +
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

var id, fMaps;
function experimentFiles(id, fMaps) {
    this.id = id;
    this.fMaps = fMaps;
}

function trackChangeWithExperimentName(d, cd) {   
    $('#'+cd).on('change', function() {
        
        newExperimentName = $(this).val();
        sendURL('new-experiment/name', new experimentDetails(d, newExperimentName), demo);
    });
}

function trackChangeWithExperimentFile(d) {   

    $('#'+d+'_eF').on('change', function() {
              
        var maps = []; 
        function prepareEXPERIMENTurl() {
            var act = maps,
                 url = "new-experiment/files";

    //        console.log('maps lenm : '+maps.length);
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

    if(name.length === 0) {
        // Specify Name
        console.log("No Experiment Name");
        k = "<li><p>Specify the Experiment Name</p></li>";
    }
    
    var stat = sendURL('check/experiment-file', d, demo);;
    if(stat !== "success") {
        console.log('No File exists');
        k += "<li><p>Upload the Experiment File</p></li>";
    }

    
   if(modulesList !== null) {        
        var j = "";
       console.log("MN List : "+modulesList.length);
       
        for(var i = 0; i < modulesList.length; i += 1) {
            console.log('MN : '+moduleNamesList[i].name);
            if(moduleNamesList[i].name === "No Name" || moduleNamesList[i].name === "") {
                j += "'"+(i+1)+"',";
                console.log("It has no name at '"+(i+1)+"' module")
            }
        }
       if(j.length > 0) {
            k += "<li><p>Specify Module Names for : "+j+" Modules</p></li>";
        }
    }
    
    errors.append(k);
}

function experimentChoices(d) {
    
    var exptab = cont.find('#'+d);

    function makeNENullVoid() {
        sessionStorage.setItem("newExperiment", "NO");
         sessionStorage.setItem("newexperimentId", null)
        sessionStorage.setItem("newexperimentName", null);
        sessionStorage.setItem("modulesList", null);
        sessionStorage.setItem("moduleNamesList", null);
        sessionStorage.setItem("orderList", null);
        sessionStorage.setItem("allQuestionsList", null);

        newExperimentName = "";
        modulesList = [];
        orderList = [];
        allQuestionList = [];
        moduleNamesList = [];
    }
    
    exptab.find('#'+d+'_NEpublish').on('click', function() {
        
        console.log('In publish');
        
        // Do validations   
        var name = exptab.find('#experiment-name').val();        
        
        publishValidations(d);
        
        // d is the experiment id        
        var stat = sendURL('newexperiment/publish', d, demo);
        if(stat === "success") {
            console.log('Its success');
            // Move to main window
            
            makeNENullVoid();
        }
        
          //  makeNENullVoid();
    });
    
    exptab.find('#'+d+'_NEdraft').on('click', function() {
        // d is the experiment id   
        var stat = sendURL('newexperiment/draft', d, demo);
        if(stat === "success") {
            console.log('Its success');
            // Move to main window
            
            makeNENullVoid();
        }
        //    makeNENullVoid();
    });
    
    exptab.find('#'+d+'_NEdiscard').on('click', function() {
        // d is the experiment id
        
        var retVal = confirm("Are you sure to remove WHOLE EXPERIMENT ?");
        if( retVal == true ){
            var stat = sendURL('newexperiment/discard', d, demo);
            if(stat === "success") {
                console.log('Its success');
                // Move to main window

                makeNENullVoid();
            }
            //    makeNENullVoid();
       }

    });
}



function addExperimentSpecs() {
    
    var id = Date.now();
  
    console.log('SSE : '+sessionStorage.getItem("newexperimentId"));
    
    if(sessionStorage.getItem("newexperimentId") !== null){
        id = JSON.parse(sessionStorage.getItem("newexperimentId"));
    } else {
        // Read the experiment Id from the index3 page from 'spam' tag
        var k = $('#spam-exp-id').data("expId");
        if(k != "newExp") {
            console.log('spam exp id : '+k);
            id = k;
        }
        sessionStorage.setItem("newexperimentId", id);
    }
  
  //  cont = $('#body-container');
    cont.append(getNewExperiment(id));
    $( "#datepicker" ).datepicker();
    
    $(document).ready(function() {
        sendURL('new-experiment/id', id, demo);

        trackChangeWithExperimentName(id, 'experiment-name');
        trackChangeWithExperimentFile(id);

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