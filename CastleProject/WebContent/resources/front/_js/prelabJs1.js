var id;
var name;
var objective;
var hypothesis;
var variables;
var experimentalOutline;
var chemicalHazards;
var moduleNos = 0;
var start = 0;
var prelab = [];
var concept;
var request = new XMLHttpRequest();

window.onload = function () {

   /* var obj = document.getElementById("btn-objective");
    var hyp = document.getElementById("btn-hypothesis");
    var varia = document.getElementById("btn-variables");
    var exp = document.getElementById("btn-experimental");
    var chem = document.getElementById("btn-chemical");*/

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
    
    function Concept(id, name) {
        this.id = id;
        this.name = name;
    }

    function Prelab(concept, objective, hypothesis, variables, experimental, chemical) {
        this.concept = concept;
        this.hypothesis = hypothesis;
        this.objective = objective;
        this.experimentalOutline = experimental;
        this.chemicalHazards = chemical;
        this.variables = variables;
    }
    
    function module(concept) {
        this.concept = new Concept(concept.id, concept.name);
        var moduleName = document.getElementById("module");
        moduleName.innerHTML = concept.name;
        alert("This Module is "+concept.name);
     }
    
    request.open('GET', 'prelab.json');
    request.onreadystatechange = function () {
        if ((request.status === 200) && (request.readyState === 4)) {
            info = JSON.parse(request.responseText);
            console.log(info);
            console.log("Len : "+info.length);
            module(info[moduleNos]);
        }
    };
    request.send();
    tabActivation(start);
    
    function clearPanels() {
        document.getElementById('hypothesis').value = "";
        document.getElementById('objective').value = "";
        document.getElementById('variables').value = "";
        document.getElementById('experimentalOutline').value = "";
        document.getElementById('chemical-hazards').value = "";
    }

    document.getElementById("btn-submit").onclick = function () {
        var objective = document.getElementById("objective").value,
            hypothesis = document.getElementById("hypothesis").value,
            variables = document.getElementById("variables").value,
            experimentalOutline = document.getElementById("experimentalOutline").value,
            chemicalHazards = document.getElementById("chemical-hazards").value;

        var r = confirm("Are you sure the hypothesis you wrote contains both the expected results of the experiment and a scientific reason why they are expected. \n\nPress OK if it is Yes");
                        
        if(r === true) {
            prelab[moduleNos] = new Prelab(concept, objective, hypothesis, variables, experimentalOutline, chemicalHazards);

            if(moduleNos < info.length - 1) {
                concept = new Concept(id, name);
                clearPanels();
                moduleNos += 1;
                module(info[moduleNos]);
            } else {
                alert("Congratulations!! You are done with your prelab..")
                console.log(JSON.stringify(prelab));
                var url='makepdf.json?prelab='+JSON.stringify(prelab);
                request.open('GET', url,true);
                request.onreadystatechange = function () {
                    if ((request.status === 200) && (request.readyState === 4)) {
                    	console.log("Coming to response");
                            window.location.href = "one.html";
                    }
                };
                request.send();
            }
        }
    }
}