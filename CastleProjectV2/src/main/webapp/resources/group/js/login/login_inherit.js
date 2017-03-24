function createAllExperiments(experimentData) {
    
}

function getAllExperiments() {

    $.ajax({
        url: 'get/all/experiments',
        type: 'post',
        contentType: 'application/json',
        dataType: 'json',
        complete: function(transport){
            console.log('In Experiments Complete');
            if (200 == transport.status) {
                result = transport.responseText;                
                createAllExperiments(result);
               console.log(result);
            } else {
                $('#lr-errors').html("Failed to connect");
            }
        }
    });

}

function callBackRegularLogin(data) {
    console.log('regular login');
    
    if(data.type === "student") {
        console.log("Student login");
        arrangeHomeWindow("student");
    } else if (data.type === "instructor") {
        console.log("Instructor login");
        arrangeHomeWindow("instructor");
        getAllExperiments();
        
        // addNewExperimentTab();
    } else {
        console.log("TA login");
        arrangeHomeWindow("ta");
    }
}

function callBackRegistrationLogin() {
        console.log('register login');
}

function sendLoginRegister(url, data, callback) {
     
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
                if (result === "login_failure") {
                    $('#lr-errors').html("Please check your credentials");
                } else if (result === "register_failure") {
                    $('#lr-errors').html("Please check your registration credentials");                    
                }
                // If its success go to the respective page (Instrucor, Student, TA)
               console.log(result);
            } else {
                $('#lr-errors').html("Failed to connect");
            }
            callBackRegularLogin(data);
        }
    });
}