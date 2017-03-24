var stuid = $('#student-container');

function getSpecificModule(url) {
    console.log(url);
    
    window.location.href=url;

}
   

function addExperimentModules(content, mainType) {
        
    var mods = content.modules;
    var tr = "";

    function addModuleHead() {
        var t = '<div class="row status-table module-width">   ' +  
                       '<h4 id="exp-mod-name">Experiment Name</h4>' +
                        '<h5 id="exp-type" class="float-right"></h5>' +
                        '<table  class="table table-responsive">' +
                            '<thead class="th-align">' +
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
            tr += "<td>"+"<button type='button' data-exp="+content.expId+" data-module="+value.id+" class='btn btn-link each-module'>"+value.name+"</button>"+"</td>";
            tr += "<td>"+value.score+"</td>";
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
            
            //var url = 'experiment/'+exp+'/module/'+m+'/'+content.type;    
           // var url = content.type+'/'+exp+'/'+m;    
            
            var url = mainType+'.edu?eid='+exp+'&mid='+m;
            getSpecificModule(url);
        });
        
    }
    
    addModuleBody();
    checkModules();
}

//getStatusContent('json/student/stu-module.json');