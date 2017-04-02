function arrangeExperiments(data) {
    var contain = $('#experiment-container');
    
    console.log('e data : '+data);
    var expData = data;
    
    function addExperimentsHead() {
        
        var a = '<div>' +
           '<table class="table table-striped">' +
          '<thead>' +
            '<tr>' +
              '<th>#</th>' +
              '<th>Experiment Name</th>' +
              '<th>Due Date</th>' +
              '<th>Status</th>' +
            '</tr>' +
          '</thead>' +
          '<tbody id="exp-tbody">' +
          '</tbody>' +
        '</table>' +
            '<button type="button" class="btn btn-link" id="new-exp-btn">New Experiment</button>' +
        '</div>';
            
            return a;
    }
    
    contain.html("");
    contain.append(addExperimentsHead);
    
    var expTbody = $('#exp-tbody');
    
    
    function addExperimentBody() {
    	var tr = "";
    	console.log(data.length);
    	$.each(data, function(key, value) {
    		
    		tr += "<tr>";
    		tr += "<td>"+(key+1)+"</td>";
    		
    		var n = value.Name;
    		if(n === undefined) { n = "Not Specified"; }
    		tr += "<td><button type='button' data-expid="+value.expid+" class='btn btn-link expbtn'>"+n+"</button></td>";
    		
    		var d = value.dueDate;
    		if(d === undefined) { d = "Not Specified"; }
    		tr += "<td>"+d+"</td>";
    		tr += "<td>"+value.status+"</td>"; 		
    		tr += "</tr>";
    	});
    	return tr;
    }
    
    function navigateOldExperiment() {
    	
    	var btns = expTbody.find('.expbtn');  	
    	btns.on('click', function() {
    		var id = $(this).data('expid');
    		window.location.href="get-assignment.com?id="+id;
    		
    	});
    }
    
    if(expData !== 'no') {
        expTbody.append(addExperimentBody);
    }
    navigateOldExperiment();
    
    
    var expNewBtn = $('#new-exp-btn');
    
    function navigatePage(id) {
        
        var url = "navigate/experiment/"+id;
        
        console.log(url);
        
        $.ajax({
            url: url,
           type: 'post',
           data: JSON.stringify(data),
           contentType: 'application/json',
           dataType: 'json',
             complete: function(transport){
                   console.log('In Student Slot');
                   if (200 == transport.status) {
                       result = transport.responseText;
                       console.log(result);
                       if(result === 'create-assignment') {
                           window.location = 'create-assignment.com?id='+Date.now();
                       }
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
               navigatePage($(this)); 
            });
        });
        
    }
    
    console.log('data : '+data);
}

function requestAllExperiments() {
    var url = 'get/all-experiments';
    $.ajax({
        url: url,
        dataType: 'json',
        complete: function(transport){
            console.log('In Get All Experiments');
            if (200 == transport.status) {
                result = transport.responseText;
                console.log(result);
                arrangeExperiments(JSON.parse(result));
            } else {
            	console.log('Failed in getting experiments')
                arrangeExperiments("Connection error");
            }
        }
    });
}

function addNewExperimentTab() {
    requestAllExperiments();
}