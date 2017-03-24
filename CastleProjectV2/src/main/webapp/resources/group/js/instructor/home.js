var contain = $("#main-container") 

var expTab = $("#exp-nav-tab");
var searchTab = $("#search-nav-tab");
var gradeTab = $("#grade-nav-tab");
var userTab = $("#access-nav-tab");

var expContainer = $("#experiment-container");
var searchContainer = $("#search-container");
var gradeContainer = $("#grade-container");
var userContainer = $("#access-container");

expTab.on('click', function() {   
    console.log('Experiment Tab');
    addNewExperimentTab();
});

searchTab.on('click', function() {   
    console.log('Search Tab');
    addSearchTab();
});

gradeTab.on('click', function() {   
    console.log('Grade Tab');
    performGradeCenter();
});

userTab.on('click', function() {   
    console.log('User Tab');
    addAccessTab();
});

function defaultButtons() {
    
    var profile = $('#user-profile'),
        logout = $('#user-logout'),
        cancel = $('#user-cancel');
    
   /* profile.on('click', function() {
       console.log('click profile'); 
    });*/
    
    logout.on('click', function() {
       console.log('click logout'); 
     window.location.href='user-logout.edu';
    });
    
    cancel.on('click', function() {
        window.location.href='move-instructor.com';
    });
}

defaultButtons();