var tabLinks;
var tabPanels;
var start = 0;
var request = new XMLHttpRequest();
var moduleNos = 0;

var moduleName;
var hypothesis;
var objective;
var experimental;
var chemical;
var variables;
var prelab = [];
var info = [];
var concept = [];
var id;
var name;

window.onload = function () {
    
    function module(concept) {
        var moduleName = document.getElementById('module'),
            pdfFile = "getPdf/file/" + concept.id,
            txt = document.createTextNode(concept.name),
            obj = document.createElement('object'),
            win = document.getElementById('pdfWindow');
        
        id = concept.id;
        name = concept.name;
        
        obj.setAttribute('height', '100%');
        obj.setAttribute('width', '100%');
        obj.setAttribute('data', pdfFile);
        win.appendChild(obj);
        
        console.log("Module Name : "+concept.name);
        moduleName.innerHTML = concept.name;
    }

    function tabActivation(start) {
        var tablinks = document.getElementById("tabs").getElementsByTagName("li"),
            tabpanels = document.getElementById("questionsList").getElementsByTagName("div"),
            i;

        function displaySelected(tab) {
            var sel = document.getElementById("selectedTab");
            sel.innerHTML = tab;
        }

        function displayTab(activateTab) {
            for (i = start; i < tablinks.length; i++) {
                if (tablinks[i] === activateTab) {
                    tablinks[i].classList.add("active");
                    displaySelected(tablinks[i].getElementsByTagName('a')[0].innerHTML);
                    tabpanels[i].style.display = "block";
                } else {
                    tablinks[i].classList.remove("active");
                    tabpanels[i].style.display = "none";
                }
            }
        }

        displayTab(tablinks[start]);

        for (i = start; i < tablinks.length; i++) {
            tablinks[i].onclick = function () {
                displayTab(this);
                return false;
            };
            tablinks[i].onfocus = function () {
                displayTab(this);
                return false;
            };
        }
    }
    
    request.open('GET', 'prelab.json');
    request.onreadystatechange = function () {
        if ((request.status === 200) && (request.readyState === 4)) {
            info = JSON.parse(request.responseText);
            console.log(info);
            if (info.length > 0) {
                module(info[moduleNos]);
            }
        }
    };
    request.send();
    tabActivation(start);
    
    function Concept(id, name) {
    	this.id = id;
    	this.name = name;
    }
    
    function Prelab(objective, hypothesis, variables, experimental, chemical) {
    	this.concept = new Concept(id, name);
        this.objective = objective;
    	this.hypothesis = hypothesis;
        this.variables = variables;
    	this.experimental = experimental;
        this.chemical = chemical;
    }

    function clearPanels() {
    	document.getElementById('pdfWindow').innerHTML = "";
        document.getElementById('hypothesis').innerHTML = "";
        document.getElementById('objective').innerHTML = "";
        document.getElementById('variables').innerHTML = "";
        document.getElementById('experimentalOutline').innerHTML = "";
        document.getElementById('chemical-hazards').innerHTML = "";
    }
    
    var submitBtn = document.getElementById("btn-submit"),
        hypothesisBtn = document.getElementById('btn-hypothesis'),
        objectiveBtn = document.getElementById('btn-objective'),
        variablesBtn = document.getElementById('btn-variables'),
        experimentalBtn = document.getElementById('btn-experimental'),
        chemicalBtn = document.getElementById('btn-chemical');

    submitBtn.onclick = function () {
        var moduleName = document.getElementById("module").value,
            objective = document.getElementById("objective").value,
            hypothesis = document.getElementById("hypothesis").value,
            variables = document.getElementById("variables").value,
            experimental = document.getElementById("experimentalOutline").value,
            chemical = document.getElementById("chemical-hazards").value;

        prelab[moduleNos] = new Prelab(objective, hypothesis, variables, experimental, chemical);
        moduleNos++;
        
        if (moduleNos < info.length) {
            clearPanels();
            module(info[moduleNos]);
        } else {
        	var url='makepdf.json?prelab='+JSON.stringify(prelab);
        //	var url = "makepdf.json";
        	request.open('GET', url,true);
        	request.onreadystatechange = function () {
	            if ((request.status === 200) && (request.readyState === 4)) {
	            	console.log("Request Satisfied");
	            	console.log(request.responseText);
	                window.location.href = request.responseText;
	            }
        	}
        	console.log(JSON.stringify(prelab));
        	   request.send();
        //   request.send(JSON.stringify(prelab));
    }
    };

    hypothesisBtn.onclick = function () {
        var hypoTxt = document.getElementById('hypothesis');
        alert(hypoTxt.value);
    };

    objectiveBtn.onclick = function () {
        var hypoTxt = document.getElementById('objective');
        alert(hypoTxt.value);
    };

    variablesBtn.onclick = function () {
        var hypoTxt = document.getElementById('variables');
        alert(hypoTxt.value);
    };

    experimentalBtn.onclick = function () {
        var hypoTxt = document.getElementById('experimentalOutline');
        alert(hypoTxt.value);
    };

    chemicalBtn.onclick = function () {
        var hypoTxt = document.getElementById('chemical-hazards');
        alert(hypoTxt.value);
    };
};
