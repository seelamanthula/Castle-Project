function searchData(data) {

  //  <!-- Using 'allInfo' global varibale which is in userwelcome.js file -->
 
    var students = data;    

    var mainDiv = $('#search-container');
    var searchDiv = mainDiv.find('#search-div');
    var searchSelect = searchDiv.find('#search-category');
    var searchBar = searchDiv.find('#search-text');
    var field = searchBar.val();    
    var searchUpdate = $('#search-update');

    var resultTxt = searchDiv.find("#search-result-text");


    
    function basicTable(student) {

          var output = "";
          output += "<table class='table'>";
          output += "<tr><th><h3>Personal Information</h3></td></tr>";
        
          output += "<tr><th>First Name : </th><td>"+student.studentDetails.firstName+"</td>";
          output += "<th>Last Name : </th><td>"+student.studentDetails.lastName+"</td></tr>";
          
          output += "<tr><th>EMail : </th><td>"+student.studentDetails.email+"</td></tr>";
          
          output += "<tr><th><h3>Class Information</h3></td></tr>";
          output += "<tr><th>TA Name : </th><td>"+student.classInformation.taDetails.taName+"</td></tr>";
          
          output += "<tr><th>Class Number : </th><td>"+student.classInformation.taDetails.classNo+"</td>";
          output += "<th>Room : </th><td>"+student.classInformation.taDetails.room+"</td></tr>";
        
          output += "<tr><th>Lab Day : </th><td>"+student.classInformation.day+"</td>";
          output += "<th>Lab Time : </th><td>"+student.classInformation.time+"</td></tr>";
        
          output += "<tr><th>Semester : </th><td>"+student.classInformation.semester+"</td>";
          output += "<th>Year : </th><td>"+student.classInformation.year+"</td></tr>";
        
          output += "</table>";

          return output;
    }

 //   <!-- Live Search from the Data received from grade center -->
        
    searchBar.keyup(function() {
        var field = searchBar.val();
        var myExp = new RegExp(field, "i");

        var fname = "firstname";
        var lname = "lastname";
        var nid = "NetId";
        
        console.log("students length : "+students.length);
        var output = '<ul class="search-results">';

        console.log('select :' + searchSelect.val());
        var findText = searchSelect.val();
        
        if(nid === findText) {
            $.each(students, function(key, val) {
                if ((val.studentDetails.login.netId.search(myExp) != -1)) {
                    output += '<li>';
                    output += "<h2>Net ID: "+val.studentDetails.login.netId.toUpperCase()+"</h2>";
                    output += basicTable(val);
                    output += '</li>';
                }
            });            
        } else if(fname === findText) {
            $.each(students, function(key, val) {
                if ((val.studentDetails.firstName.search(myExp) != -1)) {
                    output += '<li>';
                    output += "<h2>Net ID: "+val.studentDetails.login.netId.toUpperCase()+"</h2>";
                    output += basicTable(val);
                    output += '</li>';
                }
            });            
        } else if(lname === findText) {
            $.each(students, function(key, val) {
                if ((val.studentDetails.lastName.search(myExp) != -1)) {
                    output += '<li>';
                    output += "<h2>Net ID: "+val.studentDetails.login.netId.toUpperCase()+"</h2>";
                    output += basicTable(val);
                    output += '</li>';
                }
            });            
        }
        

            output += '</ul>';
            searchUpdate.html(output);
    });
};
  

function arrangeSearchTab() {

    var contain = $("#search-container");
    
    function addSearch() {

        var div =  '<!-- Search Functionality  -->' +
                        '<div id="searchbar-result" class="well-lg">' +
                            '<div class="well" id="search-div">' +
                                '<select id="search-category" class="selectpicker">' +
                                    '<option value="NetId">NetID</option>' +
                                    '<option value="firstname">First Name</option>' +
                                    '<option value="lastname">Last Name</option>' +                            
                                '</select>' +
                                '<div id="custom-search-input">' +                  
                                  '<div class="input-group col-md-12">' +
                                    '<input type="text" class="form-control input-lg" id="search-text" placeholder="search" />' +
                                    '<span class="input-group-btn">' +
                                        '<button class="btn btn-info btn-lg" id="btn-search" type="button">' +
                                            '<i class="glyphicon glyphicon-search"></i>' +
                                        '</button>' +
                                    '</span>' +
                                '</div>' +
                               '</div>' +
                            '</div>' +
                            '<div id="search-update">' +
                            '</div>' +
                        '</div>';
                    
        return div;
    }

    contain.html("");
    contain.append(addSearch);
    
   // searchData(data);
}

function requestAllStudentSearch() {
//    var url = 'json/search.json';
    var url = 'get/all-search-students';
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        complete: function(transport){
            console.log('In Student Search');
            if (200 == transport.status) {
                result = transport.responseText;
                console.log(result);
                arrangeSearchTab();
                
                if(result === "No_Students_Enrolled") {
                	$("#search-update").html("<span>No Students Enrolled</span>");
                } else if(result === "connection-fail"){
                	$("#search-update").html("<span>Connection Failed</span>");
                } else {
                    searchData(JSON.parse(result));
                }
                
  //              arrangeSearchTab(JSON.parse(result));
////                searchData(JSON.parse(result));               
            } else {
//            	arrangeSearchTab("connection-fail");
                $("#search-container").html("connection-fail");
            }
        }
    });
}

function addSearchTab() {
    requestAllStudentSearch();
}
