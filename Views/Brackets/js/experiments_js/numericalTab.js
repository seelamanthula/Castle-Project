function getNum(d) {

    var num = '<div class="num questionTab question-accordion" id="'+d+'">' +
                    '<h3><span class="questionTxt">Numerical</span><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+d+'"></span></h3>' +
                    '<div>' +
                       '<form method="post" enctype="multipart/form-data">' + 
                        '<ul class="ul-list">' +
                            '<li> <!-- Question --> ' +
                                '<ul>' +
                                    '<li><textarea name="question" class="question"></textarea></li>' +
                                    '<li><input type="file" name="qFile" id="'+d+'_qF" multiple></li>' +                            
                                '</ul>' +
                           '</li>' +

                           '<li> <!-- Correct Answers -->' +
                               '<ul class="corrects">' +
                                   '<li><label>Correct Answer</label></li>' +
                               '</ul>' +                           
                           '</li>' +

                           '<li><input type="button" class="idCA" id="'+d+'CA"  data-type="correct-answer" value="CA"></li>' +

                           '<li> ' +
                               '<ul class="ranges">' +
                                   '<li><label>Range</label></li>                               ' +
                                   '<li><input type="text" name="from" id="'+d+'_From" class="from"  placeholder="From">' +
                                        '<input type="text" name="to" id="'+d+'_To" class="to" placeholder="To"></li>' +    
                               '</ul>' +                           
                           '</li>' +
                           '<li> <!-- Hints -->' +
                               '<ul class="hints">' +
                                  '<li><label>Hints</label></li>' +
                               '</ul>    ' +                       
                            '</li>    ' +

                            '<li><input type="button" class="idH" id="'+d+'H" data-type="hints" value="Hints"></li>' +

                        '</ul>' +
                        '<input type="button"  class="in-btn"  value="save" id="'+d+'_save">' +
                        '</form>' +
                    '</div>' +
                '</div>';
    return num;
}

function addCAnswers(t, v) {
    var ca = '<li id="'+t+'"><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+t+'"></span>' +
                       '<textarea name="correct-answer" class="correct-answer">'+v+'</textarea></li> ';                                       
    return ca;   
}

function addNumChoices(id) {
    $('#'+id+'CA').on('click', function() {
        var tab = $(this).closest('.questionTab'),
            cas = tab.find('.corrects'),        
            temp = id+'_CA_'+cas.find('li').length;              
        
        cas.append(addCAnswers(temp, ""));
        trackChangeWithId(id, temp);
        removeItem(temp, "");       
    });
    
    $('#'+id+'H').on('click', function() {
        var tab = $(this).closest('.questionTab'),
            hints = tab.find('.hints'),        
            temp = id+'_H_'+hints.find('li').length;              
        
        hints.append(addCommonHint(temp, ""));
        trackChangeWithId(id, temp);
        removeItem(temp, "");
    });
}

function saveNumButton(md, d) {
    
    $('#'+d+'_save').on('click', function() {
        var tab = $(this).closest('.questionTab');
        
        var quesId = d,
         question = tab.find('.question').val(),
         canswers = [],
         allHints = [],        
         maps = [],
         from, to;
        
        function demo() { console.log('Execute Demo');}
        
        var moduleId, questionId, questionText, cOptions,  hOptions, fMaps, type;
        
        function prepareNUM(id, question, canswers, from, to, hints, maps) {
            this.moduleId = md;
            this.questionId = id;
            this.type = "NUM";
            this.question= question;
            this.cOptions = canswers;
            this.from = from;
            this.to = to;
            this.hints = hints;
            this.fMaps = maps;
        }

        function prepareNUMurl() {
            var act = new prepareNUM(quesId, question, canswers, from, to, allHints, maps),    
                ind = orderList.indexOf(d), 
                url = 'readnumdata';
            
                allQuestionList[ind] = act;
                    
            var stat = sendURL(url, act, sendOrder);            
            if(stat === "success") {
                console.log('Its success');
    //            sendOrder();
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
        
        function getParseChoices(tabs) {
            var choices = [];
            
            $.each( tabs, function( key, value ) {
              choices.push($(this).val());
            });
            
            return choices;
        }

        // Reading the files from MCQ type
        function readFilesOfNUM(allSet) {
             var total = allSet.length;
            if(total === 0) {
                prepareNUMurl();
            } else {
                // Reading the files
                $.each(allSet, function(key, value) {
                   total = total - 1;
                    if(total === 0) {
                        parseFiles(value.fid, value.type, prepareNUMurl);                    
                    } else {
                        parseFiles(value.fid, value.type, demo);
                    }
                });
            }
        }
        
        // Collecting ids of files who has files
        var allSet = [];
        function checkFiles(id, type) {
            var iFiles = document.getElementById(id),
                mainFiles = iFiles.files.length;
            if(mainFiles > 0) {
                allSet.push(new fileType(id, type));
            }
        }
           
        var corrects = [], hints = [], allHints = [];
        corrects = tab.find('.correct-answer');
        hints = tab.find('.hint');
            
        canswers = getParseChoices(corrects);
        from = tab.find('.from').val();
        to = tab.find('.to').val();
        
        // Question files
        checkFiles(d+"_qF", "Q");
        
        // Hint files
        $.each( hints, function( key, value ) {           
           var txt = $(this).val(),
               fid = $(this).data('file');
            
           allHints.push(txt);
           checkFiles(fid, "H"+key);
        });
            
        readFilesOfNUM(allSet);
    });   
}