function getShort(d) {
    
    var short = '<div class="shAns questionTab question-accordion" id="'+d+'">' +
                 '<h3><span class="questionTxt">Short Answer</span><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+d+'"></span></h3>' +
                    '<div>' +
                       '<form method="post" enctype="multipart/form-data">' + 
                        '<ul class="ul-list">' +
                            '<li> ' +
                                '<ul>' +
                                    '<li><textarea name="question" placeholder="Question" class="question"></textarea></li>' +
                                    '<li><input class="ca-files" disabled type="file" id="'+d+'_qF" name="qFile" multiple></li>                            ' +
                                '</ul>' +
                           '</li>' +

                           '<li> ' +
                                '<ul>' +
                                    '<li><label>Answer</label></li>' +
                                    '<li><textarea name="shortanswer" class="shortanswer" id="'+d+'_SA" placeholder="Answer"></textarea></li>' +
                                    '<li><input class="ca-files" disabled type="file" id="'+d+'_aF" name="aFile"></li>                            ' +
                                '</ul>' +
                           '</li>' +

                        '</ul>' +
                        '<input type="button" class="in-btn"  id="'+d+'_save" value="save">' +
                        '</form>' +
                    '</div>' +
                '</div>';
        
    return short;
}

function saveShortButton(md, d) {
    $('#'+d+'_save').on('click', function() {
        
        var tab = $(this).closest('.questionTab');
        
        var quesId = d;
        var question;
        var canswer;
        var allHints = [];        
        var maps = [];
        
        function demo() { console.log('Execute Demo');}
        
        var moduleId, questionId, questionText, cAnswer,  hOptions, fMaps, type;
        
        function prepareShort(id, question, cAnswer, hints, maps) {
            this.moduleId = md;
            this.questionId = id;
            this.type = "SHORT";
            this.question= question;
            this.cAnswer = cAnswer;
            this.hOptions = hints;
            this.fMaps = maps;
        }

        function prepareSHORTurl() {
            
            var act = new prepareShort(quesId, question, canswer, allHints, maps),
                ind = orderList.indexOf(d), 
                url = 'readshortdata';
            
            allQuestionList[ind] = act;
                        
            var stat = sendURL(url, act, sendOrder);
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
        
        // Reading the files from MCQ type
        function readFilesOfSHORT(allSet) {
             var total = allSet.length;
            if(total === 0) {
                prepareSHORTurl();
            } else {
                // Reading the files
                $.each(allSet, function(key, value) {
                   total = total - 1;
                    if(total === 0) {
                        parseFiles(value.fid, value.type, prepareSHORTurl);                    
                    } else {
                        parseFiles(value.fid, value.type, demo);
                    }
                });
            }
        }
        
        // Collecting ids of files who has files
        var allSet = [], hints = [], allHints = [];
        function checkFiles(id, type) {
            var iFiles = document.getElementById(id),
                mainFiles = iFiles.files.length;
            if(mainFiles > 0) {
                allSet.push(new fileType(id, type));
            }
        }
           
        question = tab.find('.question').val();
        canswer = tab.find('.shortanswer').val();
        
        hints = tab.find('.hint');
        
        // Question files
        checkFiles(d+"_qF", "Q");
        
        // Answer files
        checkFiles(d+"_aF", "A");

        
       /* // Hint files
        $.each( hints, function( key, value ) {           
           var txt = $(this).val(),
               fid = $(this).data('file');
            
           allHints.push(txt);
           checkFiles(fid, "H"+key);
        });*/
            
        readFilesOfSHORT(allSet);

    });
}