function searchData(data) {

  //  <!-- Using 'allInfo' global varibale which is in userwelcome.js file -->
 //   var JSONinfo = allInfo;
    //var students = JSONinfo.StudentsInformation;
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
          output += "<tr><th>First Name : </th><td>"+student.FirstName+"</td>";
          output += "<th>Last Name : </th><td>"+student.LastName+"</td></tr>";
          output += "<tr><th>TA Name : </th><td>"+student.TAName+"</td>";
          output += "<th>Class Number : </th><td>"+student.ClassNumber+"</td></tr>";
          output += "<tr><th>Lab Day : </th><td>"+student.LabDay+"</td>";
          output += "<th>Lab Time : </th><td>"+student.LabTime+"</td></tr>";
          output += "</table>";

          return output;
    }

    function experimentTable(student) {

        var output = "";
        output += "<table class='table'>";
        output += "<tr><th>Experiments</th><th>Modules</th><th>Assessment</th><th>Prelab</th><th>Total</th></tr>";

        //console.log(JSON.stringify(student));

        $.each(student.ExperimentLists, function(key, val) {
          //  console.log(JSON.stringify(val));
            
            output += "<tr>";
            output += "<th rowspan="+val.Modules.length+">"+val.ExperimentName+"</th>";
            
            $.each(val.Modules, function(key2, val2) {
                var sum = 0.0;                
                output += "<td>"+val2.ModuleName+"</td>";
                output += "<td>"+val2.AssessmentScore+"</td>";
                output += "<td>"+val2.PrelabScore+"</td>";                
                sum += parseFloat(val2.AssessmentScore) + parseFloat(val2.PrelabScore);
                
                if(isNaN(sum)) {
                    output += "<td><a href='#'> -- </a></td>";                    
                } else {
                    output += "<td><a href='#'>"+ sum +"</a></td>";                    
                }

                output += "</tr>";                                
            });
        });

        output += "</table>";
        return output;
    }

   // <!-- Live Search from the Data received from grade center -->
        
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
                if ((val.NetID.search(myExp) != -1)) {
                    output += '<li>';
                    output += "<h2>Net ID: "+val.NetID+"</h2>";
                    output += basicTable(val);
                    output += "<h4>Experiments List</h4>";
                    output += experimentTable(val);				
                    output += '</li>';
                }
            });            
        } else if(fname === findText) {
            $.each(students, function(key, val) {
                if ((val.FirstName.search(myExp) != -1)) {
                    output += '<li>';
                    output += "<h2>Net ID: "+val.NetID+"</h2>";
                    output += basicTable(val);
                    output += "<h4>Experiments List</h4>";
                    output += experimentTable(val);				
                    output += '</li>';
                }
            });            
        } else if(lname === findText) {
            $.each(students, function(key, val) {
                if ((val.LastName.search(myExp) != -1)) {
                    output += '<li>';
                    output += "<h2>Net ID: "+val.NetID+"</h2>";
                    output += basicTable(val);
                    output += "<h4>Experiments List</h4>";
                    output += experimentTable(val);				
                    output += '</li>';
                }
            });            
        }
        

            output += '</ul>';
            searchUpdate.html(output);
    });
};
  

function arrangeSearchTab(data) {

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
    
    searchData(data);

}

function requestAllStudentSearch() {
  //  var url = 'json/search.json';
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
                arrangeSearchTab(JSON.parse(result));
            } else {
                arrangeSearchTab("Connection error");
            }
        }
    });
}

function addSearchTab() {
    requestAllStudentSearch();
}
