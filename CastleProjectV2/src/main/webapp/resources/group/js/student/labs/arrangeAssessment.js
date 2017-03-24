function arrangeAssessmentContent(data) {
    
    
    console.log('Inside assessment2 - arrangeAssessmentContent()');
    
    var session = getSessionStorage();
    
    var pdfId = $('#pdf');
    var hintsTab = $('#hints-tab');
    
    var questionLink = $('#question-link');
    var questionPanel = $('#question-panel');
    var eErrors = $('#module-errors');
    var eMsgs = $('#module-msgs');
    
    var dataExp = data.experiment;
    var dataModule = data.module;
    
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
                            console.log('Printing PDF');
                            
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
        var expName = $('#expname');
        var moduleName = $('#modulename');
        var fName = $('#user-name');
        
        expName.html(dataExp.expName.toUpperCase());
        moduleName.html(dataModule.name.toUpperCase());
        fName.html(data.firstName);
    }
    
    function addQuestions() {
        
        var a = "", b = "", list = dataModule.questionIds;
        var len = list.length, position;
        
        function addQuestionLinks() {
            $.each(list, function(key, value) {        
                
                if(key == len - 1) position = "done"; else position = list[key+1];
                if(key == 0) {

                    a += ' <button class="w3-bar-item w3-button w3-small w3-blue tablink" id="btn_'+value+'" onclick="openCity(event,'+value+')">'+(key + 1)+'</button>';

                    b += '<div id='+value+' class="w3-container city w3-animate-right">' +
                              '<p class="status"></p>' +
                              '<div class="ques"></div>'+
                            '<button class="w3-btn w3-green next" data-current="'+list[key]+'" data-next="'+position+'">Submit</button>' +
                     '</div>';

                } else {

                    a += ' <button class="w3-bar-item w3-button w3-small tablink display-hide" id="btn_'+value+'" onclick="openCity(event,'+value+')">'+(key+1)+'</button>';

                    b += '<div id='+value+' class="w3-container city w3-animate-right"  style="display:none">' +
                              '<p class="status"></p>' +
                            '<div class="ques"></div>'+
                            '<button class="w3-btn w3-green next" data-current="'+list[key]+'" data-next="'+position+'">Submit</button>' +                    
                     '</div>';

                }


            });

            questionLink.append(a);
            questionPanel.append(b);
        }
        
        
        function addQuestionToPanel(data) {
            
            console.log('In Add Questions Panel');
            
            var id = $('#'+data.questionId), q = "";
            
            
            if(data.type === "NUM") {
                console.log('In NUM');
                q = divNUMType(data);

            } else if(data.type === "MCQ") {            
                console.log('In MCQ');
                q = divMCQType(data);

            } else if(data.type === "SHORT") {
                console.log('In SHORT');
                q = divSHORTType(data);                
            }
            
            id.find(".ques").html("");
            id.find(".ques").append(q).show('slow');            
            
        }
        
         function getNextQuestion(id) {

                    function getRequestQuestion(url) {
                        console.log(url);

                        $.ajax({
                            url: url,
                            dataType: 'json',
                            complete: function(transport){
                                if (200 == transport.status) {
                                    data = transport.responseText;
                                    if(data === 'finished') {
                                        console.log("came to finish");
                                        // move to status page
                                        $('#module-msgs').html("Successfully Finished this module");
                                        setTimeout(function(){
                                            //do what you need here
                                            window.location.href= "move-student.com";   
                                            console.log('move to new page');
                                        }, 5000);
                                    } else {
                                    	console.log(data);
                                        result = JSON.parse(data);
                                        console.log(result);
                                        addQuestionToPanel(result);                        
                                    }
                                }
                            }
                        });        
                    }

                    var url = "request/question/"+id;
                    getRequestQuestion(url);

                }

          function compareDate() {
                
                console.log('D Date :'+dataExp.duedate);
                var currDate = new Date(Date.now()),                
                    mydate = new Date(dataExp.duedate);
               // var mydate = new Date('02/15/2017');
                
                 console.log(currDate);
                console.log(mydate)

                if(currDate>mydate)   {
                    return false;
                }

                return true;
            }
        
        function checkGetNextQuestion() {

            function checkNextQuestion() {
                
                var nextQues = $('.next').on('click', function() {

                    if(!$(this).hasClass('w3-disabled')) {

                        var a = $(this).data('next'),
                            curr = $(this).data('current'),
                            flag = false;
                        
                        var id = $('#'+curr);
                        var type = id.find('.ques-type').data('type');
                        
                        if(type === 'NUM') {
                            flag = checkNUMAnswer(curr);
                        } else if(type === 'MCQ') {                            
                            flag = checkMCQAnswer(curr);
                        } else if(type === 'SHORT') {
                            
                            flag = checkSHORTAnswer(curr);
                        }
                        
                        if(flag) {
                            
                            if(compareDate()) {
                                updateAnswers();

                                $(this).addClass('w3-disabled');
                                getNextQuestion(a);
                                $('#btn_'+a).removeClass('display-hide');
                            } else {
                                $(this).addClass('w3-disabled');                                
                                eErrors.html("Due Date is Passed");
                            }
                        }
                        

                     //   id.click();

                    }
                });
            }
            
            checkNextQuestion();
        }
        
        function startQuestions() {
            
             var t = '<button id="btn_start" class="w3-btn w3-green next" data-next="'+list[0]+'">Start</button>';            
             questionPanel.append(t);
            
            $('#btn_start').on('click', function() {
                questionPanel.html("");
                var next = $(this).data('next');
                console.log('next : '+next);
                
                getNextQuestion(next);
                    addQuestionLinks();
                    checkGetNextQuestion();
                
            });
        }  
        
        function provideQuestionsPanel(questionSets, answerSets) {
            
            console.log(questionSets);
            console.log(answerSets);
            
            function addAnswerToPanel(data,flag) {

                console.log(data);
                console.log(data.questionId);
                var id = $('#'+data.questionId);

                if(data.type === 'MCQ') {
                    var cbs = data.studentAns;
                    
                    $.each(cbs, function(key, val) {
                        console.log(val);
                        id.find('input[type=checkbox][value="'+val+'"]').attr("checked",true);
                    });
                
                } else if(data.type === 'NUM') {
                    var ansId = id.find('#text_'+data.questionId);
                    ansId.val(data.studentAns);
                    ansId.attr("disabled", "true");
                    
                } else if(data.type === 'SHORT') {
                    var txtId = id.find('#textarea_'+data.questionId);
                    console.log('short ans : '+data.studentAns);
                    txtId.val(data.studentAns);
                    txtId.attr("disabled", "true");
                }
                
                var btn = id.find('.next');
                var next = btn.data('next'),
                    curr = btn.data('current');
                    
                btn.addClass('w3-disabled');
                $('#btn_'+next).removeClass('display-hide');
                    
                if(!flag) {
                    console.log('next btn : '+next);
                    if(next !== 'done' && compareDate()) {
                        getNextQuestion(next);
                    } else {
                        eMsgs.html("Module is Finished Successfullyy..");
                    }
                }
            }
            
            var len = answerSets.length, flag = true;
            $.each(answerSets, function(key, value) {
                if(key === len - 1) {flag = false}
              //  addQuestionToPanel(value);
                  addQuestionToPanel(questionSets[key]);
                  addAnswerToPanel(value, flag);  
            //    addAnswerToPanel(answerSets[key], flag);
            });

        }
        
        function findPath() {
            var snetid = session.getItem("netid");
            console.log('ss : '+snetid);
                         
            var a = session.getItem("assessment_questions");
            if(a!== "null") {
                assessment_questions = JSON.parse(a);
            }
            
            var b = session.getItem("assessment_answers");
            if(b!=="null") {
                assessment_answers = JSON.parse(b);
            } 
            
            var c = session.getItem("assessment_modulepoints");
            if(c !== "null") {
                modulePoints = JSON.parse(c);
            }
            
            console.log('assess_ques len : '+assessment_questions);
            console.log('assess_answ len : '+assessment_answers);
            console.log('assess_mod points : '+modulePoints);
             console.log(JSON.stringify(assessment_questions));
            console.log(JSON.stringify(assessment_answers));
            
            if(assessment_answers.length > 0) {
            	  addQuestionLinks();
                  checkGetNextQuestion();
                 provideQuestionsPanel(assessment_questions, assessment_answers);
            } else {
                startQuestions();
            }
        }
        
        function checkIsItNewOrOld(url) {
            
            console.log(url);
            
             $.ajax({
                url: url,
                dataType: 'json',
                complete: function(transport){
                    if (200 == transport.status) {
                        console.log(transport.responseText);
                        result = transport.responseText;
                        if(result !== "firsttime") {
                            result = JSON.parse(transport.responseText);
                            console.log(result);
                            
                            session.setItem("assessment_questions", JSON.stringify(result.sQuestions));
                            session.setItem("assessment_answers", JSON.stringify(result.mAnswers));
                            session.setItem("assessment_modulepoints", JSON.stringify(result.score));

                        }
                       findPath();

                    }
                }
            }); 
            
        }
       // findPath();
        
        
         var expid = $('#expid').html(),
         moduleid = $('#moduleid').html(),
         netid = $('#netid').html();
        
        // gives weather the content is already stored or not
        var k = session.getItem("assessment_store");
        console.log('is saved : '+k);
        if(k === "yes") {
            findPath();
        } else {
            var url = 'check/'+netid+'/assessment/'+expid+'/moduledata/'+moduleid;
            checkIsItNewOrOld(url);
        }   
    }
    
    addHTMLBasics();
    addPDF();
    addQuestions();
    
}