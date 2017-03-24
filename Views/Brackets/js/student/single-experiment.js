var stuid = $('#student-container');

function getSpecificModule(url) {
    console.log(url);
    
    $.ajax({
        url: url,
        dataType: 'json',
        complete: function(transport){
            if (200 == transport.status) {
                result = JSON.parse(transport.responseText);
                console.log(result);
                
                if(result.type === 'Assessment') {
                    
                } else if(result.type === 'Prelab') {
                    
                }
                
            }
        }
    });
}
   

function addExperimentModules(content) {
        
    var mods = content.modules;
    var tr = "";

    function addModuleHead() {
        var t = '<div class="row status-table module-width">   ' +  
                       '<h4 id="exp-mod-name">Experiment Name</h4>' +
                        '<h5 id="exp-type" class="float-right"></h5>' +
                        '<table  class="table table-responsive">' +
                            '<thead>' +
                                '<tr>' +
                                    '<th>Module Name</th>' +
                                    '<th>Score</th>' +
                                '</tr>' +
                            '</thead>' +
                            '<tbody id="module-body">' +
                                
                            '</tbody>' +
                        '</table>' +
                    '</div>';
        
        return t;
    }
    
    stuid.html("");
    stuid.append(addModuleHead);
    
    stuid.find('#exp-mod-name').html(content.expName);
    stuid.find('#exp-type').html(content.type);
    
    var modid = stuid.find('#module-body');    

    function addModuleBody() {
        $.each(mods, function(key, value) {
            tr += "<tr>";
            tr += "<td>"+"<button type='button' data-exp="+content.expId+" data-module="+value.moduleId+" class='btn btn-link each-module'>"+value.moduleName+"</button>"+"</td>";
            tr += "<td>"+value.moduleScore+"</td>";
            tr += "</tr>";
        });
        modid.append(tr);
    }
     
    function checkModules() {
        
        var mBtns = modid.find('.each-module');
        mBtns.on('click', function() {
            
            var exp = $(this).data('exp');
            var m = $(this).data('module');            
            console.log('clicked : '+exp+' '+m);            
            
            var url = 'experiment/'+exp+'/module/'+m+'/'+content.type;    
            getSpecificModule(url);
        });
        
    }
    
    addModuleBody();
    checkModules();
}

//getStatusContent('json/student/stu-module.json');