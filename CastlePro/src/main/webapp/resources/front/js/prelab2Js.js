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

var request;
if(window.XMLHttpRequest) {
    request = new XMLHttpRequest();
} else {
    request = new ActiveXObject("Microsoft.XMLHTTP");
}

function warning() {
    var k = confirm("WARNING \nIf you return to the main menu before you complete this section, your progress will be lost.");
    
    if( k === true) {
    	
        window.location.href="status.html";
    }
}
    function nextTab(i) {
        tabActivation(i);
    }

    function tabActivation(start) {
        var tablinks = document.getElementById("tabs").getElementsByTagName("li"),
            tabpanels = document.getElementById("questionsList").getElementsByTagName("div"),
            i;

        if(start === 2) {
            var r = confirm("Are you sure the hypothesis you wrote contains both the expected results of the experiment and a scientific reason why they are expected. \n\nPress OK to Continue \n\nPress CANCEL to rewrite the hypothesis");
                        
            if(r === true) {
            
            }
            else {
                start = 1;    
            }
        } else if (start === 5) {

        document.getElementById("btn-submit").style.visibility="visible";
        document.getElementById("disclaim").innerHTML = "Warning!!!  In order to save section you MUST click the 'Submit' button below.  Failure to click the 'Submit' button will result in your progress being lost.";
        }
        
        
        function displaySelected(tab) {
            var sel = document.getElementById("selectedTab");
            sel.innerHTML = tab;
        }

        function displayTab(activateTab) {
            for (i = 0; i < tablinks.length; i++) {
                if (tablinks[i] === activateTab) {
                    tablinks[i].classList.add("active");
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
     
        var linkList = document.getElementById("tabs").getElementsByTagName("li");
        console.log("Tags List : "+linkList.length);
        for (var j = 0; j < linkList.length; j += 1) {
            var k = linkList[j].getElementsByTagName("a")[0].innerHTML;
            linkList[j].getElementsByTagName("a")[0].innerHTML = concept.name +" "+ k;
        }
        
        document.getElementById("btn-submit").style.visibility="hidden";
            tabActivation(start);

    }
    
    request.open('GET', 'prelabacidbase.json');
    request.onreadystatechange = function () {
        if ((request.status === 200) && (request.readyState === 4)) {
            info = JSON.parse(request.responseText);
            console.log(info);
            module(info);
        }
    };
    request.send();
    
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

                prelab[moduleNos] = new Prelab(concept, objective, hypothesis, variables, experimentalOutline, chemicalHazards);

                if(moduleNos < info.length - 1) {
                    concept = new Concept(id, name);
                    clearPanels();
                    moduleNos += 1;
                    module(info[moduleNos]);

                } else {
                    console.log(JSON.stringify(prelab));
                    var url='prelabacidbaseupdate?prelab='+JSON.stringify(prelab);
                    request.open('GET', url,true);
                    request.onreadystatechange = function () {
                        if ((request.status === 200) && (request.readyState === 4)) {
                            info = request.responseText;
                                window.location.href = info;
                        }
                    };
                    request.send();
                }
            }
       
    
