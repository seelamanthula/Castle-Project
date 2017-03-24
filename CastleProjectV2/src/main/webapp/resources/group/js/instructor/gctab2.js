function gcModuleData(student, expPos) {

    var mainid = $("#grade-container"),
        modDiv, modBody;

    var currExp = student.experiments[expPos];
    
    console.log(student);
    
    mainid.html("");
  //  var gcTable = mainid.find('#gcTable');
    
    
    function moduleTableHead() {
        
        var t = '<div id="mod-table" style="width:100%;">  ' +
                  /*      '<button type="button" id="mod-close-btn" class="close" aria-label="Close">' +
                          '<span aria-hidden="true">&times;</span>' +
                        '</button>' +*/
                        '<span id="mod-text"></span>' +
                        '<label>Experiment : '+currExp.experiment.expName+'</label><br>' +
                        '<label>Due Date : '+currExp.experiment.duedate+'</label><br>' +
        

                        '<table>' +
                            '<thead>' +
                                '<tr><th>Module Name</th>' +
                                '<th class="td-width">Assessment</th>' +
                                '<th></th>' +
                                '<th class="td-width">Prelab</th>' +
                                '<th></th> ' +
                                '<th></th>' +
                                '</tr>' +
                            '</thead>' +
                            '<tbody id="mod-body">' +
               
                            '</tbody>' +    
                        '</table>' +
                        '<button class="w3-btn w3-green float-right bottom-margin top-margin" id="submit-score">submit</button><br/>' +
                        '<textarea rows="5" style="width:100%;" class="bottom-margin" id="taComments" placeholder="Comments"></textarea>' +
                        '</div>' ;
        
   //     gcTable.style.display = "none";
        mainid.append(t);
    
        modDiv = mainid.find('#mod-table');
        modBody = modDiv.find('#mod-body');
    }
    
    function moduleTableBody() {
        
        var t = "";
        var assList = currExp.assessmentList,
            preList = currExp.prelabList;
        
      $.each(assList, function(key, value) {
           var pMod = preList[key];
            t += "<tr><td>"+value.name+"</td>";
            t += "<td><input type='text' class='a-vals' data-modid='"+value.id+"' data-mposition='"+key+"' value='"+value.score+"' placeholder='"+value.score+"'></td>";
            t += "<td><a href='assessment-report/"+student.netId+"/"+currExp.experiment.expId+"/"+value.id+"' download>Report</a></td>";
            t += "<td><input type='text' class='p-vals'  data-modid='"+value.id+"' data-mposition='"+key+"' value='"+pMod.score+"' placeholder='"+pMod.score+"'></td>";            
            t += "<td><a href='prelab-report/"+student.netId+"/"+currExp.experiment.expId+"/"+value.id+"' download>Report</a></td>";
            t += "<td></td></tr>"; 

        });
       
        
      /*  $.each(assList, function(key, value) {
           var pMod = preList[key];
            t += "<tr><td>"+value.name+"</td>";
            t += "<td><input type='text' class='a-vals' data-modid='"+value.id+"' data-mposition='"+key+"' value='"+value.score+"' placeholder='"+value.score+"'></td>";
            
            t += "<td><button type='button'  class='report-btns btn btn-link'  data-type='assessment' data-expid='"+currExp.experiment.expId+"' data-modid='"+value.id+"'>Report</button></td>";
            t += "<td><input type='text' class='p-vals'  data-modid='"+value.id+"' data-mposition='"+key+"' value='"+pMod.score+"' placeholder='"+pMod.score+"'></td>";       
            
            t += "<td><button type='button' class='report-btns btn btn-link' data-type='prelab' data-expid='"+currExp.experiment.expId+"' data-modid='"+value.id+"'>Report</button></td>";
            t += "<td></td></tr>"; 

        });*/
        
        t += '<tr><td>Total</td>' +
                    '<td><label class="a-total">'+currExp.assessmentPoints+'</label></td>    ' +                           
                    '<td>+</td>' +
                    '<td><label class="p-total">'+currExp.prelabPoints+'</label></td>       ' +                        
                    '<td>=</td>      ' +
                    '<td><label class="ap-total">'+parseFloat(currExp.assessmentPoints+currExp.prelabPoints)+'</label></td>' +
                '</tr>';
 
        
        modBody.append(t);
    }
    
    
    function autoCalculationsButtons() {
    
           // var id = $('#mod-table');
            var id = modDiv;

            var aVals = id.find('.a-vals'),
                pVals = id.find('.p-vals'),
                aTotal = id.find('.a-total'),
                pTotal = id.find('.p-total'),
                apTotal = id.find('.ap-total');

            aVals.on('change', function() {
                var total = 0;
                aVals.each(function(index){
                    total += parseFloat($(this).val()?$(this).val():0);
                });

                aTotal.html(total.toFixed(2));   
                apTotal.html(parseFloat(aTotal.html()) + parseFloat(pTotal.html()));
            })

            pVals.on('change', function() {
                var total = 0;
                pVals.each(function(index){
                    total += parseFloat($(this).val()?$(this).val():0);
                });

                pTotal.html(total.toFixed(2));   
                apTotal.html(parseFloat(aTotal.html()) + parseFloat(pTotal.html()));
            });
                            
        }

    
    function checkButtons() {
        var cBtn = modDiv.find('#mod-close-btn'),
            submitBtn = modDiv.find('#submit-score'),
            reportBtn = modDiv.find('.report-btns');
        
        function toAndGoReports(url) {
        
            function sameData(url, data) {
            
                   console.log(JSON.stringify(data));
                    $.ajax({
                            url: url,
                            type: 'post',
                            data: JSON.stringify(data),
                            contentType: 'application/json',
                            dataType: 'json',
                            complete: function(transport){
                                console.log('PDF onComplete');
                                if (200 == transport.status) {
                                    result = transport.responseText;
                                    console.log(result);
                                    if(result === "pdf_success") {
                                        console.log('pdf success');
                                    }
                                    
                                }
                            }
                        });
                }
            
                function reports(url) {

                    console.log(url);
                        $.ajax({
                            url: url,
                            dataType: 'json',
                            complete: function(transport){
                                console.log('Report response');
                                if (200 == transport.status) {
                                    result = transport.responseText;
                                    console.log(JSON.parse(result));
                                    url = "make-get-pdf";
                                    sameData(url, JSON.parse(result));
                                } else {
                                    console.log('Failed in getting PDFs')
                                }
                            }
                        });
                }
            
            reports(url);
        }
        
        reportBtn.on('click', function() {
            
            console.log('clicked report');
            
            var type = $(this).data('type');
            var e = $(this).data('expid');
            var m = $(this).data('modid');
            var net = student.netId;
            
            console.log(type+" "+net+" "+e+" "+m);
//            var url = "report-"+type+"/"+net+"/"+e+"/"+m;

            var url = "";
            if(type==="assessment") {
                url = "assessment-report/"+net+"/"+e+"/"+m    
            } else if(type === "prelab") {
                url = "prelab-report/"+net+"/"+e+"/"+m    
            
            }
            toAndGoReports(url);
        });
        
        cBtn.on('click', function() {
            
            console.log('clicked cancel');
            // move-instructor.com
        });
        
        function updateScores(url, data) {
            
            console.log(url);
            console.log(JSON.stringify(data));
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
                            console.log(result);
                            if(result === "success") {
                                console.log('updated successfully');
                                modDiv.find('#mod-text').html("Updated Successfully..");
                                //move-instructor or TA
                                
                            } else if(result == "failed") {
                                console.log('failed to update');
                            }
                        }
                    }
                });
        }
        
        var currentExp, taComments;
        function content(currentExp, taComments) {
            this.currentExp = currentExp;
            this.taComments = taComments;
        }
        
        function getValues() {
            var id = modDiv;
            
              var aVals = id.find('.a-vals'),
                  pVals = id.find('.p-vals'),
                  aTotal = id.find('.a-total'),
                  pTotal = id.find('.p-total'),
                  apTotal = id.find('.ap-total');

               var student2 = student,
                   currExp2 = student.experiments[expPos];
         
            aVals.each(function(index){
            
                var aId = $(this);
              //  aId.css("border","1px solid red");
                
                var mId = aId.data('modid'),
                    mPos = aId.data('mposition');                
                var currMod = currExp2.assessmentList[mPos];
                
                currMod.score = aId.val();

                currExp2.assessmentList[mPos] = currMod;

            });

            pVals.each(function(index){
            
                var aId = $(this);
                var mPos = aId.data('mposition');
                console.log('M Pos : '+mPos);
             
                var currMod = currExp2.prelabList[mPos];
                currMod.score = aId.val();

                currExp2.prelabList[mPos] = currMod;
            });

            student2.experiments[expPos] =  currExp2;
            currExp2.experiment.netid = student.netId;
            currExp2.assessmentPoints = aTotal.html();
            currExp2.prelabPoints = pTotal.html();
            
 /*           student = student2;
            console.log(student);            
            */
            
            var cont = modDiv.find('#taComments').val();
         //   console.log('content : '+cont);
            
            var url = "update-student-scores.edu";
            updateScores(url, new content(currExp2, cont));
        }
        
        submitBtn.on('click', function() {
           
            console.log('clicked submit');
            getValues();
           /*  gcTable.style.display = "block";
            modDiv.style.display = "none";*/
        });
    }
    moduleTableHead();
    moduleTableBody();
    autoCalculationsButtons();
    checkButtons();
}  

