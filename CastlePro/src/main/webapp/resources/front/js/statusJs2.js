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
    
//        console.log(i);
        addRow(info.experiment.experimentId, info.experiment.title, info.experiment.dueDate,  info.assessmentVolumetric, info.assessmentAcid, info.prelabVolumetric, info.prelabAcid, info.experimentLabVolumetric, info.experimentLabAcid, info.postlab);
    
    function addRow(id, name, date, assessmentVolumetric,assessmentAcid , prelabVolumetric, prelabAcid, experimentLabVolumetric, experimentLabAcid, postlab) {
        
   
        var tr = document.createElement('tr');
        
        var td1 = document.createElement('td');
        var txt1 = document.createTextNode(id);
        td1.appendChild(txt1);

        var td2 = document.createElement('td');
        var a = document.createElement('a');
    //    a.setAttribute('href',id);
    //    a.innerHTML = name;
            td2.innerHTML = name;
    //    td2.appendChild(a);

        var td3 = document.createElement('td'),
        	txt = document.createTextNode('Mar 24, 2016');
      //  td3.innerHTML = date;
        td3.appendChild(txt);
        
        var td4 = document.createElement('td'),
            atd4 = document.createElement('a'),
            txttd4 = document.createTextNode('Volumetric Glassware (5 Questions)');      
        if(assessmentVolumetric === 1){
            var p = document.createElement('span');
            p.setAttribute('class', 'glyphicon glyphicon-ok');
            td4.appendChild(p);
        } else {
            atd4.setAttribute('href', 'assessmentvolumetric.html');
            atd4.appendChild(txttd4);
            td4.appendChild(atd4);            
        }
        
        var td5 = document.createElement('td');
            atd4 = document.createElement('a'),
            txttd4 = document.createTextNode('Acid Base Titration (10 Questions)');      
        if(assessmentAcid === 1){
        	var p = document.createElement('span');
            p.setAttribute('class', 'glyphicon glyphicon-ok');
            td5.appendChild(p);
        } else {
            atd4.setAttribute('href', 'assessmentacidbase.html');
            atd4.appendChild(txttd4);
            td5.appendChild(atd4);            
        }

        var td6 = document.createElement('td');
            atd4 = document.createElement('a'),
            txttd6 = document.createTextNode('Volumetric Glassware');
        console.log("PV : ");
        console.log(txttd6);
        
        if(prelabVolumetric === 1){
            var p = document.createElement("span");
            p.setAttribute('class', 'glyphicon glyphicon-download-alt');
            atd4.setAttribute('href', 'experiment/prelab/volumetric/document.pdf');    
            atd4.setAttribute('download', 'volumetric.pdf');
            atd4.appendChild(p);
            td6.appendChild(atd4);
            console.log("Assed : "+td6);
            
        } else {
            console.log("TD 6");
            atd4.setAttribute('href', 'prelabvolumetric.html');
            atd4.appendChild(txttd6);
            td6.appendChild(atd4);            
        }

        
        var td7 = document.createElement('td'),
            atd4 = document.createElement('a'),
            txttd4 = document.createTextNode('Acid Base Titration');      
        if(prelabAcid === 1){
        	   var p = document.createElement("span");
               p.setAttribute('class', 'glyphicon glyphicon-download-alt');
               atd4.setAttribute('href', 'experiment/prelab/acidbase/document.pdf');
               atd4.setAttribute('download', 'acidbase.pdf');
               atd4.appendChild(p);
               td7.appendChild(atd4);
           
        } else {
            atd4.setAttribute('href', 'prelabacidbase.html');
            atd4.appendChild(txttd4);
            td7.appendChild(atd4);            
        }

      
        var td8 = document.createElement('td'),
        	atd4 = document.createElement('a'),
        	txttd4 = document.createTextNode('Volumetric Lab');

        console.log('volumetric : '+experimentLabVolumetric);
        if(experimentLabVolumetric === 0) {
    
        	atd4.setAttribute('href', 'volumetriclab.html');
        	atd4.appendChild(txttd4);
        	td8.appendChild(atd4);
        } else {
        	
        	 var p = document.createElement("span");
             p.setAttribute('class', 'glyphicon glyphicon-download-alt');
             atd4.setAttribute('href', 'experiment/lab/volumetric/document.pdf');
             atd4.setAttribute('download', 'volumelab.pdf');
             atd4.appendChild(p);
             td8.appendChild(atd4);
        }
    /*
        var td9 = document.createElement('td'),
        	atd4 = document.createElement('a'),
        	txttd4 = document.createTextNode('Acid Base Lab');
    
        atd4.setAttribute('href', 'acidbaselab.html');

        td9.appendChild(txttd4);*/
        
        var td9 = document.createElement('td'),
    	atd4 = document.createElement('a'),
    	txttd4 = document.createTextNode('AcidBase Lab');

	    console.log('volumetric : '+experimentLabAcid);
	    if(experimentLabAcid === 0) {
	
	    	atd4.setAttribute('href', 'acidbaselab.html');
	    	atd4.appendChild(txttd4);
	    	td9.appendChild(atd4);
	    } else {
	    	
	    	 var p = document.createElement("span");
	         p.setAttribute('class', 'glyphicon glyphicon-download-alt');
	         atd4.setAttribute('href', 'experiment/lab/acidbase/document.pdf');
	         atd4.setAttribute('download', 'acidbase.pdf');
	         atd4.appendChild(p);
	         td9.appendChild(atd4);
	    }

	    var td10 = document.createElement('td'),
    	atd5 = document.createElement('a'),
    	txttd4 = document.createTextNode('Postlab');

	    console.log('volumetric : '+experimentLabAcid);
	    if(postlab === 0) {
	
	    	atd5.setAttribute('href', 'postlab.html');
	    	atd5.appendChild(txttd4);
	    	td10.appendChild(atd5);
	    } else {
	    	
	    	 var p = document.createElement("span");
	         p.setAttribute('class', 'glyphicon glyphicon-download-alt');
	         atd5.setAttribute('href', 'postlab/document.pdf');
	         atd5.setAttribute('download', 'postlab.pdf');
	         atd5.appendChild(p);
	         td10.appendChild(atd5);
	    }
       
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tr.appendChild(td9);
        tr.appendChild(td10);
        
        tableBody.appendChild(tr);
    }
    
    function tick(status) {
        
    }
}
