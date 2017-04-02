
function getMCQ(d) {
    
    var mcq = '  <div class="mcq questionTab question-accordion" id="'+d+'">' +
                    '<h3><span class="questionTxt">Multiple Choice</span><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+d+'"></span></h3>' +
                    '<div>' +
                       '<form method="post" enctype="multipart/form-data"> ' +
                        '<ul class="ul-list">' +
                            '<li> <!-- Question -->' +
                                '<ul>' +
                                    '<li><textarea name="question" placeholder="Question" class="question b-width"></textarea></li>' +
                                    '<li><input class="ca-files" disabled type="file" id="'+d+'_qF" name="qFile" multiple></li>  ' +                          
                                '</ul>' +
                           '</li>' +

                           '<li> <!--False -->' +
                               '<ul class="falses">' +
                                   '<li><label>False</label></li>' +
                               '</ul> ' +
                           '</li>' +

                           '<li><input type="button" id="'+d+'F" data-type="false" value="False"></li>' +

                           '<li> <!--True -->' +
                               '<ul class="trues">' +
                                   '<li><label>True</label></li>      ' +                         
                              ' </ul>        ' +                   
                           '</li>' +

                           '<li><input type="button" id="'+d+'T" data-type="trues" value="True"></li>' +

                           '<li> <!-- Hints -->' +
                               '<ul class="hints">' +
                                  '<li><label>Hints</label></li>' +
                              ' </ul>     ' +                      
                            '</li>  ' +  

                            '<li><input type="button" id="'+d+'H" data-type="hints" value="Hints"></li>' +

                        '</ul>' +
                        '<input type="button" class="in-btn" id="'+d+'_save" value="save">' +
                        '</form>' +
                    '</div>' +
                '</div>';
                        
        return mcq;                
}

function addFC(t, v) {
    var a = '<li id="'+t+'"><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+t+'"></span>' +
                                       '<textarea name="invalid"  placeholder="False Statement" class="invalid">'+v+'</textarea></li>';        
    return a;
}

function addTC(t, v) {
    var b = ' <li id="'+t+'"><span class="glyphicon glyphicon-remove btn-hover float-right btn-remove '+t+'"></span>' +
                           '<textarea name="valid"  placeholder="True statement" class="valid">'+v+'</textarea></li> ';
    return b;    
}

function addChoices(md, id) {

    $('#'+id+'F').on('click', function() {
        var tab = $(this).closest('.questionTab'),
            falses = tab.find('.falses'),
            temp = id+'_F_'+falses.find('li').length;
        
        falses.append(addFC(temp, ""));        
        trackChangeWithId(id, temp);
        
        removeItem(temp, "");
    });
    
    $('#'+id+'T').on('click', function() {
        var tab = $(this).closest('.questionTab'),
            trues = tab.find('.trues'),        
            temp = id+'_T_'+trues.find('li').length;      

        trues.append(addTC(temp, ""));
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

function saveMCQButton(md, d) {
    $('#'+d+'_save').on('click', function() {
        console.log('performed save');
        var tab = $(this).closest('.questionTab'),         
            quesId = d,
            question = tab.find('.question').val(),
            falses = [],
            trues = [],     
            allHints = [],        
            maps = [];
        
        function demo() { console.log('Execute Demo'); }
        
        var moduleId, questionId, questionText, iOptions, vOptions, hOptions, fMaps, type;
        
        function prepareMCQ(id, question, falses, trues, hints, maps) {
            this.moduleId = md;
            this.questionId = id;
            this.type = "MCQ";
            this.question= question;
            this.iOptions = falses;
            this.vOptions = trues;
            this.hints = hints;
            this.fMaps = maps;
        }
        
        function prepareMCQurl() {

            var act = new prepareMCQ(quesId, question, falses, trues, allHints, maps),
                ind = orderList.indexOf(d); 
            
            if(ind < 0) {
                orderList.push(d);            
                allQuestionList.push(act);
            } else {
                allQuestionList[ind] = act;
            }
                        
            var url = 'readmcqdata',
                stat = sendURL(url, act, sendOrder);
            
            if(stat === "success") {
                console.log('Its success');
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
        function readFilesOfMCQ(allSet) {
             var total = allSet.length;
            if(total === 0) {
                prepareMCQurl();
            } else {
                // Reading the files
                $.each(allSet, function(key, value) {
                   total = total - 1;
                    if(total === 0) {
                        parseFiles(value.fid, value.type, prepareMCQurl);                    
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
           
        var invalids = [], valids = [], hints = [], allHints = [];
    
        invalids = tab.find('.invalid');
        valids = tab.find('.valid');
        hints = tab.find('.hint');
        
        falses = getParseChoices(invalids);
        trues = getParseChoices(valids);        
        
        // Question files
        checkFiles(d+"_qF", "Q");
        
        // Hint files
        $.each( hints, function( key, value ) {           
           var txt = $(this).val(),
               fid = $(this).data('file');
           allHints.push(txt);
           checkFiles(fid, "H"+key);
        });
            
        readFilesOfMCQ(allSet);
    });
}