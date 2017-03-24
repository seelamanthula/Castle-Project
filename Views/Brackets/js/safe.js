function saveButton(d) {
    $('#'+d+'_save').on('click', function() {
         var tab = $(this).closest('.questionTab');
         
        var quesId = d;
        var question = tab.find('.question').val();
        var questionFiles = []; 
        
        var iFiles = document.getElementById(d+"_qF"),
            filesLength = iFiles.files.length;
        
        for (var i = 0; i < filesLength; i++) {
            questionFiles.push(iFiles.files[i].name);
        }
        
        var maps = [];

        function parseFiles(id, type) {
    
            var iFiles = document.getElementById(id),
                filesLength = iFiles.files.length;

            for (var i = 0; i < filesLength; i++) {
                //console.log(imageFiles.files[i].name);
                var f = iFiles.files[i];

                if (f) {
                    var r = new FileReader();
                    r.onload = function(e) { 
                        var contents = e.target.result;
                        /*    alert( "Got the file.n" 
                                      +"name: " + f.name + "n"
                                      +"type: " + f.type + "n"
                                      +"size: " + f.size + " bytesn"
                                      + "starts with: " + contents.substr(1, contents.indexOf("n"))
                            ); */
                        var x = new fileContainer(type, contents);
                        maps.push(x);
                        console.log('maps 1 len : '+maps.length);           
                    }
                   // r.readAsText(f);
                     r.readAsDataURL(f);
                } else { 
                    alert("Failed to load file");
                }                           
            }
            console.log('maps 2 len : '+maps.length);           
        }
        
        console.log('Question : '+question);        
        parseFiles(d+"_qF", "Q");
        
        function getParseChoices(tabs) {
            var choices = [];
            
            $.each( tabs, function( key, value ) {
              choices.push($(this).val());
            });
            
            return choices;
        }

        var invalids = [], valids = [], hints = [];
        invalids = tab.find('.invalid');
        valids = tab.find('.valid');
        hints = tab.find('.hint');
        
        var falses = getParseChoices(invalids);
        var trues = getParseChoices(valids);
        
        var allHints = [];
        $.each( hints, function( key, value ) {
           
           var txt = $(this).val();
           allHints.push(txt);
           var fid = $(this).data('file');           
           parseFiles(fid, "H"+key);
                    
        });
        

     //   console.log('All Hints len : '+allHints.length);
        
      //  console.log(allHints);
       // console.log(JSON.stringify(allHints));
    });
}
