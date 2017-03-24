function arrangeExperiments(data) {
    var contain = $('#experiment-container');
    
    var expData = data;
    
    function addExperimentsHead() {
        
        var a = '<div>' +
           '<table class="table table-striped">' +
          '<thead>' +
            '<tr>' +
              '<th>#</th>' +
              '<th>Experiment Name</th>' +
              '<th>Publish</th>' +
              '<th>Draft</th>' +
            '</tr>' +
          '</thead>' +
          '<tbody id="exp-tbody">' +
            '<tr>' +
              '<th scope="row">1</th>' +
              '<td>Mark</td>' +
              '<td>Otto</td>' +
              '<td>@mdo</td>' +
            '</tr>' +
            '<tr>' +
              '<th scope="row">2</th>' +
              '<td>Jacob</td>' +
              '<td>Thornton</td>' +
              '<td>@fat</td>' +
            '</tr>' +
              '<tr>' +
                  '<th scope="row"></th>' +
            '</tr>' +
          '</tbody>' +
        '</table>' +
            '<button type="button" class="btn btn-link" id="new-exp-btn">New Experiment</button>' +
        '</div>';
            
            return a;
    }
    
    contain.html("");
    contain.append(addExperimentsHead);
    
    var expTbody = $('#exp-tbody');
    var expNewBtn = $('#new-exp-btn');
    
    function navigatePage(id) {
        
        var url = "navigate/experiment/"+id;
        
        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(id),
            contentType: 'application/json',
            dataType: 'json',
            complete: function(transport){
                console.log('In onComplete');
                if (200 == transport.status) {
                    result = transport.responseText;
                   console.log(result);
                }
            }
        });
        
        
    }
    
    expNewBtn.on('click', function() {
        navigatePage('new');
        
  //      addNewExperimentTab();
        
    });
    
    function addExperimentsBody() {
        
        $.each(eData, function(key, value) {
           
            var content = value;
            var t = '<tr>' +
              '<th scope="row">'+(key+1)+'</th>' +
              '<td><button type="button" class="btn btn-link" id=experiment_"'+content.id+'" data-id="'+content.id+'">'+content.name+'</button></td>' +
              '<td>'+content.status+'</td>' +
              '<td>@fat</td>' +
            '</tr>';
            
            expTbody.append(t);
            
            $('#experiment_'+content.id).on('click', function() {
               navigatePage($.this()); 
            });
        });
        
    }
    
    console.log('data : '+data);
}

function requestAllExperiments() {
    var url = 'get/all-experiments';
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        complete: function(transport){
            console.log('In Experiment Specs');
            if (200 == transport.status) {
                result = transport.responseText;
                console.log(result);
                arrangeExperiments(JSON.parse(result));
            } else {
                console.log('couldnt find all experiments');
                arrangeExperiments("Connection error");
            }
        }
    });
}

function addNewExperimentTab() {
    requestAllExperiments();
}