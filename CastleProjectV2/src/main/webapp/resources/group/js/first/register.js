function arrangeRegistrationFields(data) {
    
    var table = $('#class-table');    
    var dayTab = table.find('#reg-day');
    var timeTab = table.find('#reg-time');
    var taTab = table.find('#reg-ta');
    var classTab = table.find('#reg-classno');
    
    var temp = "";
    var days = [];
    $.each(data, function(key, value) {
    //   temp += "<option value="+value.Day+">"+value.Day+"</option>"; 
        days.push(value.Day);
    });
    
    var times = [];
    $.each(data, function(key, value) {
    //   temp += "<option value="+value.Day+">"+value.Day+"</option>"; 
        times.push(value.Time);
    });
}

function uniqueness(names) {
    var uniqueNames = [];
    $.each(names, function(i, el){
        if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
    });
    return uniqueNames;
}

function requestRegisteration() {
    var url = 'json/registration.json';
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        complete: function(transport){
            console.log('In Registration Specs');
            if (200 == transport.status) {
                result = transport.responseText;
        //        console.log(result);
                arrangeRegistrationFields(JSON.parse(result).Registration);
            } else {
                arrangeExperiments("Connection error");
            }
        }
    });
}

//requestRegisteration();