var request = new XMLHttpRequest();

request.open('GET','status.json');

request.onreadystatechange = function() {

    
    if((request.readyState == 4)) {
        var info = JSON.parse(request.responseText);
        console.log(info);
        arrangeTables(info);
    }
}
request.send();
console.log(request);

function arrangeTables(info) {
    var tableBody = document.getElementById('status-body-table');
    console.log("in Tables : "+info);
    var id;
    var name;
    var date;
    var assessment;
    var prelab;
    var experiment;
    
    console.log("Before for");
    var i = 0;
        console.log(i);
        addRow(info.experiment.experimentId, info.experiment.title, info.experiment.dueDate,  info.assessment, info.prelab, info.experimentLab);
    
    function addRow(id, name, date, assessment, prelab, experiment) {
        
        console.log(id);
        
        console.log(name);
        
        console.log(date);
        console.log(assessment);
        console.log(prelab);
        console.log(experiment);
        
        
        var tr = document.createElement('tr');
        
        var td1 = document.createElement('td');
        var txt1 = document.createTextNode(id);
        td1.appendChild(txt1);

        var td2 = document.createElement('td');
        var a = document.createElement('a');
        a.setAttribute('href',''+id);
        a.innerHTML = name;
        td2.appendChild(a);

        var td3 = document.createElement('td');
        td3.innerHTML = date;
        
        var td4 = document.createElement('td');
        td4.innerHTML = assessment;
        
        var td5 = document.createElement('td');
        td5.innerHTML = prelab;

        var td6 = document.createElement('td');
        td6.innerHTML = experiment;
        
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        
        tableBody.appendChild(tr);
    }
    
    function tick(status) {
        
    }
}
