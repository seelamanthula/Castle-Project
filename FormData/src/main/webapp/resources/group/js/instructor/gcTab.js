function compare(a,b) {
  if (a.expId < b.expId)
    return -1;
  if (a.expId > b.expId)
    return 1;
  return 0;
}

function compareStuds(a,b) {
  if (a.experiment.expId < b.experiment.expId)
    return -1;
  if (a.experiment.expId > b.experiment.expId)
    return 1;
  return 0;
}

function performGradeCenter() {
    var gcTab = $('#grade-container');

    function arrangeGradeCenter(data) {

        console.log(data);

        var gcTable = gcTab.find('#gcTable');

        var gcHead = gcTable.find('#tHead');
        var gcBody = gcTable.find('#tBody');    

        var pExps = data.publishedExps,
            sExps = data.studentList;


        function addTHead() {

            var objs = data.publishedExps;
            objs.sort(compare);

            var t = "<tr><th>Net ID</th><th>TA Name</th><th>Total</th>";
            $.each(objs, function(key, value) {           
                t += "<th data-id="+value.expId+">"+value.expName+"</th>";
            });

            t += "</tr>";
            gcHead.append(t);

        }

        function addTBody() {

            console.log('In Body');

            var t = "";
            $.each(sExps, function(k1, v1) {

                 t += "<tr><td>"+v1.netId+"</td>";
                 t += "<td>"+v1.taName+"</td>";
                t += "<td>"+(v1.studentAssessmentPoints+v1.studentPrelabPoints)+"</td>";

                var objs2 = v1.experiments;
                objs2.sort(compareStuds);

                console.log('GC p exps len : '+pExps.length);
                console.log('GC Objs len : '+objs2.length);
                
                
                $.each(objs2, function(key, value) {           
                    t += "<td><button class='gc-btn button3' data-eposition='"+key+"' data-sposition='"+k1+"' data-netid='"+v1.netId+"' data-expid='"+value.experiment.expId+"'>"+(value.assessmentPoints+value.prelabPoints)+"</button></td>";
                });

                var diff =  pExps.length - objs2.length;
                console.log('Difference L '+diff);
                if(diff > 0) {
                    for(i = 0; i < diff; i++) {
                        t += "<td><button class='button3'>0</button></td>";
                    }
                }
                
                t += "</tr>";

            });
            gcBody.append(t);
        }

        function gcButtons() {

            var btn = gcBody.find('.gc-btn');

            btn.on('click', function() {
               console.log('btn clicked');
                var n = $(this).data("netid");
                var e = $(this).data("expid");
                var ep = $(this).data("eposition");
                var sp = $(this).data("sposition");

                console.log(n+" "+e+" "+ep+" "+sp);

                var cStudent = sExps[sp];
                var cExp = cStudent.experiments[ep];

                gcModuleData(cStudent, ep);

            });
        }
        addTHead();
        addTBody();
        gcButtons();
        gcTable.DataTable({
             responsive: true
        });
    }


    function addTable() {

        var table = '<table id="gcTable" class="table table-responsive display"> ' +                   
                        '<thead id="tHead">' +
                        '</thead>' +
                        '<tbody id="tBody">' +                            
                        '</tbody>' +
                    '</table>';

        gcTab.html("");
        gcTab.append(table);
    }

     function getContentByURL(url) {
            console.log(url);

           $.ajax({
                url: url,
                dataType: 'json',
                complete: function(transport){
                    if (200 == transport.status) {
                      //  console.log(transport.responseText);
                        var txt = transport.responseText;
                        if(txt === "no-students") {
                        	$('#grade-container').html("<span>No Students Enrolled</span>");
                        } else {
                        	addTable();
                        	arrangeGradeCenter(JSON.parse(txt));
                        }
                    }
                }
            });        
        }

   // var url = "json/gradecenter.json";
    var url = "get/grade-center-info";

    gcTab.html("");
    getContentByURL(url);

}
