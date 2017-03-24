
function addSlotTable(result) {
    
    var contain = $('#main-container');
    
    function reservedSlot(data) {}
    
    function addTableBody(data) {
        console.log(data);
        var tr = "", bflag = true; 
        
        function getDayLength(days) {
            var count = 0;
            $.each(days, function(key, value) {
                count += value.details.length;
            });
            return count;
        }
        
        var slot = 1;        
        $.each(data, function(key, value) {
            
            var da = value;
            tr += '<tr><td rowspan='+getDayLength(da.dayDetails)+'>'+da.day+'</td>';
            
            var aflag = true; 
            $.each(da.dayDetails, function(key2, value2) {
                
                    if(!aflag) { tr += '<tr>'; }
                    tr += '<td rowspan='+value2.details.length+'>'+value2.time+'</td>'
           
                    var flag = true;
                    $.each(value2.details, function(key3, value3) {

                        if(!flag) { tr += '<tr>'; }
                        
                        tr += '<td>'+value3.classNo+'</td>';
                        tr += '<td>'+value3.room+'</td>';
                        tr += '<td>'+value3.taName+'</td>';                        
                        tr += '<td><button type="button" data-class='+value3.classNo+' class="btn btn-link slot-btn">Slot '+slot+'</button></td>'; 
                        tr += '</tr>';                       
                        
                        slot += 1;
                        flag = false;
                    });
                
                    aflag = false;
                
            });
            

        });
        
        var tbody = contain.find('#reg-tbody');
        tbody.append(tr);
        
        var sem, year, classno;
        function reserveSlot(sem, year, classno) {
            this.sem = sem;
            this.year = year;
            this.classno = classno;
        }
        
//        var sem = data.sem, year = data.semYear;
        
        // If TA No need of slotBtns code
        var slotBtns = tbody.find('.slot-btn');
        slotBtns.on('click', function() {
            var no = $(this).data('class');
            console.log('clicked slot btn : '+no);            
        })
    }
    
    function requestTAregistration() {
        var url = 'register/studentRegistration.json';
        
        $.ajax({
          dataType: "json",
          url: url,
          complete: function(transport){
                console.log('In Student Search');
                if (200 == transport.status) {
                    result = transport.responseText;
                //    console.log(result);
                    addTableBody(JSON.parse(result));
                } else {
                    console.log('Failed TA Registration');
                }
            }
        });
    }

   // requestTAregistration();
    
    addTableBody(result);
}

addSlotTable(result);

// addSlotTable();