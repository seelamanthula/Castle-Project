var info;
var index = 0;
var concept;
var id;
var moduleName;
var question;
var answer;
var questionNo;
var solutions = [];
var arrange = [];
var start = 0;


function tabActivation(start) {
    
    if(isNaN(start)) {
        console.log("tabs : "+start);
    }
    
    var tablinks = document.getElementById("tabs").getElementsByTagName("li"),
        tabpanels = document.getElementById("questionsList").getElementsByTagName("div"),
        i;

    if(start === 4) {
    	document.getElementById('btn-submit').style.visibility = 'visible';
    }
    function displayTabs(activateTab) {
        for (i = 0; i < tablinks.length; i += 1) {
            if (tablinks[i] === activateTab) {

                tablinks[i].classList.remove("hidden");
                tablinks[i].classList.add("show");
                tablinks[i].classList.add("active");
                tabpanels[i].style.display = "block";
            } else {
                tabpanels[i].style.display = "none";
                tablinks[i].classList.remove("active");

            }
        }
    }

    displayTabs(tablinks[start]);

    for (i = start; i < tablinks.length; i += 1) {
        tablinks[i].onclick = function () {
            displayTabs(this);
            return false;
        };
        tablinks[i].onfocus = function () {
            displayTabs(this);
            return false;
        };
    }
}


  
var request;
if(window.XMLHttpRequest) {
    request = new XMLHttpRequest();
} else {
    request = new ActiveXObject("Microsoft.XMLHTTP");
}
    
request.open("GET", "postlab.json");    
    
function TextQuestion(questionNo, question, answer) {
    this.questionNo = questionNo;
    this.question = question;
    this.answer = answer;
}

function module(list) {

    var name = list.concept.name;
    var id = list.concept.id;
    
    var moduleId = document.getElementById("module");
   // moduleId.innerHTML = name;
    
    var pdfFile = "getPdf/file/"+id;
    console.log('In Module');
    console.log(list.question.length);
    makeQuestion(list.question);

}

function TextQuestion(questionNo, question, answer) {
    this.questionNo = questionNo;
    this.question = question;
    this.answer = answer;
}

function makeQuestion(questionList) {
    var divId = document.getElementById("questionsList");
    var tab = document.getElementById("tabs");
    var li = document.createElement("li");
    var txt = document.createTextNode("Questions : ");
    li.appendChild(txt);
    tab.appendChild(li);
    
    for(var i = 0; i < questionList.length; i += 1) {
        arrange[i] = new TextQuestion(i, questionList[i].question, "");        
        tab.appendChild(addNumber(i));
        divId.appendChild(setQuestion(arrange[i], i));
    }
}

function addNumber(questionNo) {
    var li = document.createElement("li");
    li.setAttribute('id', 'tab' + questionNo);

    var a = document.createElement("a");
    a.setAttribute("href", "#tabpanel"+questionNo);
    var txt = document.createTextNode(questionNo+1);
    a.appendChild(txt);
    li.classList.add("hidden");
    li.appendChild(a);
    return li;
}

function setQuestion(question, i) {
    var div = document.createElement("div");
    div.setAttribute("id", "tabpanel"+question.questionNo);
    
    var ul = document.createElement("ul");
    var li1 = document.createElement("li");

    var txt = document.createTextNode(question.question);
    li1.appendChild(txt);
    
    var li2 = document.createElement("li");
    var tarea = document.createElement("textarea");
    tarea.setAttribute("id","T"+i);
    tarea.setAttribute("name","T"+i);
    tarea.setAttribute("width", "100%");
    tarea.setAttribute("height", "40%");
    li2.appendChild(tarea);
    
    ul.appendChild(li1);
    ul.appendChild(li2);

    var li3 = document.createElement("input");
    li3.setAttribute("type", "button");
    li3.setAttribute("value", "Save");
    li3.setAttribute("onclick", "dataNext(" + question.questionNo +");");
    ul.appendChild(li3);
    
    div.appendChild(ul);
    
    return div;
}


function dataNext(i) {
    console.log("I : "+i);
    var panel = document.getElementById("tabpanel"+i);
    var question = panel.getElementsByTagName("li")[0].innerHTML;
    var answer = panel.getElementsByTagName("textarea")[0].value;
    
    console.log("Question : "+question);
    console.log("Answer : "+answer);
    
    
    solutions[i] = new TextQuestion(i, question, answer);
    tabActivation(i + 2);
}


/*var submitBtn = document.getElementById("btn-submit");
submitBtn.onclick = function () {
    
    console.log("json : "+JSON.stringify(solutions));
    
    var url = "makepostlabpdf.json?solutions=";
    
    request.open("GET", url);
    request.onreadystatechange = function () {
        if ((request.readyState === 4) && (request.status === 200)) {
            console.log(request.responseText);
            window.location.href = "status.html";
        }
    }
    request.send();
}*/

    request.onreadystatechange = function () {
        if ((request.readyState === 4) && (request.status === 200)) {
            info = JSON.parse(request.responseText);
            document.getElementById('btn-submit').style.visibility = 'hidden';
            module(info);
            console.log("Got response");
                tabActivation(start + 1);
        }
    }
    request.send();
