function addCommonHint(tId, v) {
    var h =  '<li id="'+tId+'">' +
                   '<ul>' +
                       '<li><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+tId+'"></span></li>' +
                       '<li><textarea name="hints" class="hint" data-file="'+tId+'_Fs">'+v+'</textarea></li>    ' + 
                       '<li><input class="ca-files" disabled type="file" id="'+tId+'_Fs" class="hint_Files" name="hints_'+tId+'"></li>    ' + 
                  ' </ul>' +
               '</li>';
    
    return h;
}

var type, extension, name, fileX;
function fileContainer(type, extension, name, fileX) {
    this.type = type;
    this.extension = extension;
    this.name = name;
    this.fileX = fileX;
}

var fid, type;
function fileType(fid, type) {
    this.fid = fid;
    this.type = type;
}

function demo() {}

var expId, oList, mList, mNamesList;
function OMList(expId, oList, mList, mNamesList) {
    this.expId = expId;
    this.oList = oList;
    this.mList = mList;
    this.mNamesList = mNamesList;
}

function sendOrder() {
    sendURL('read_order_modules', new OMList(sessionStorage.getItem("newexperimentId"), orderList, modulesList, moduleNamesList), demo);
}

 function sendURL(url, data, callback) {
     
     sessionStorage.setItem("newExperiment", "YES");

     sessionStorage.setItem("newexperimentName", JSON.stringify(newExperimentName));
     sessionStorage.setItem("modulesList", JSON.stringify(modulesList));
      sessionStorage.setItem("moduleNamesList", JSON.stringify(moduleNamesList));
     sessionStorage.setItem("orderList", JSON.stringify(orderList));
     sessionStorage.setItem("allQuestionsList", JSON.stringify(allQuestionList));
     
    $.ajax({
        url: url,
        type: 'post',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json',
        complete: function(transport){
            console.log('In onComplete');
            if (200 == transport.status) {
                result = transport.responseText;
                callback();
               console.log(result);
            }
        }
    });
}

var expId, module;
function moduleTable(expId, module) {
    this.expId = expId;
    this.module = module;
}

function trackChangeWithModule(d, cd) {   
    $('#'+cd).on('change', function() {
        console.log('val : '+$(this).val());
        
        var ind = modulesList.indexOf(d);
        moduleNamesList[ind] = new moduleDetails(d, $(this).val()); 
        
        sendURL('update/modulename', new moduleTable(JSON.parse(sessionStorage.getItem("newexperimentId")) ,new moduleDetails(d, $(this).val())), sendOrder);
    });
}

var eid, dDate;
function eDueDate(eid, dDate) {
	this.eid = eid;
	this.dDate = dDate;
}
/*
function trackDueDate(id, eid) {
	$('#'+id).on('change', function() {
        console.log('val : '+$(this).val());  
        sendURL('update/duedate', new eDueDate(eid, $(this).val(), demo));
    });
}*/

function trackChangeWithId(d, cd) {   
    $('#'+cd).on('change', function() {
        $('#'+d+'_save').click();
    });
}

function trackChangeInQuestion(d) {
    
    var quesDiv = $('#'+d);
    
    quesDiv.find('.question').on('keyup', function() {
        quesDiv.find('.questionTxt').text($(this).val());
    });
    
    quesDiv.find('.question').on('change', function() {
        $('#'+d+'_save').click();
    });
    
    quesDiv.find('#'+d+'_qF').on('change', function() {
        $('#'+d+'_save').click();
    });

}

function removeItem(d, type) {

    var remove = $('.'+d);
    remove.on('click', function() {

        console.log('remove clicked : '+$(this));        
        console.log(orderList);
        console.log(allQuestionList);
        
        
        function removeOperations() {
/*            if(stat === 'success') {
                orderList.splice(i, 1);
                allQuestionList.splice(i, 1);
                $('#'+d).remove();            
            }*/
                orderList.splice(i, 1);
                allQuestionList.splice(i, 1);
                $('#'+d).remove();        

            var stat = sendURL(rq, d, sendOrder);                        
        }
        
        var rq = "remove/question";
        
        if(type === "Mod") {
            if (confirm("Are you sure to delete the entire module!") == true) {
                // Delete all the questions in this module
                rq = "remove/module";
                
                var ind = modulesList.indexOf(d);
                modulesList.splice(ind, 1);
                
                var n = allQuestionList.length;
                for(var j = 0; j < n; j += 1) {
                    var obj = allQuestionList[j];
                    if(obj.moduleId === d) {
                      //  console.log('came inside')
                        orderList.splice(j, 1);
                        allQuestionList.splice(j, 1);
                        j -= 1;
                        n -= 1;
                    }
                }
                
                $('#'+d).remove(); 
                var stat = sendURL(rq, d, sendOrder);
                
            } else {
                return;
            }
        } else {

            var i = orderList.indexOf(d);

            if(i > -1) {    
                // Send id to the database            
                removeOperations();
            } else {

            // Remove options in a question
                $('#'+d).remove(); 
                var k =  d.substring(0,d.indexOf('_'));

                $('#'+k+'_save').click();

            }
       }
  
        console.log(orderList.length);
        console.log(orderList);
        console.log(allQuestionList);
           
    });    
}