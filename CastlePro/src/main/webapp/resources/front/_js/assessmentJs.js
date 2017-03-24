var request = new XMLHttpRequest();
var questionNo;
var question;
var answer;
var blankHints = [];
var hintLength;
var increm;
var information = [];
var informationLength;
var index = 0;
var blank = [];
var invalid = [];
var hint;
var answerFromTo = [];

request.open('GET', 'assessment.json');

function createText(txt) {
    var li = document.createElement('li'),
        litxt = document.createTextNode(txt);
    li.appendChild(litxt);
    return li;
}

function tabsActivation(start) {
    
    var tablinks = document.getElementById("tabs").getElementsByTagName("li"),
        tabpanels = document.getElementById("questionsList").getElementsByTagName("div"),
        i;

    function displayTabs(activateTab) {
        for (i = 0; i < tablinks.length; i += 1) {
            if (tablinks[i] === activateTab) {

                tablinks[i].classList.remove("hidden");
                tablinks[i].classList.add("show");
                tablinks[i].classList.add("active");
                         //   alert("Tabs length : "+tablinks.length+ " Panel Length : "+tabpanels.length);
                tabpanels[i].style.display = "block";
            } else {
                tablinks[i].classList.remove("active");
                tabpanels[i].style.display = "none";
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

function questionsList(questions) {
    var tabs = document.getElementById('tabs'),
        li = createText('Questions : '),
        panels,
        i;
    li.classList.add("show");
    tabs.appendChild(li);
    
    panels = document.getElementById('questionsList');
    function createLinks(i) {
        var li = document.createElement('li'),
            a = document.createElement('a'),
            txt = document.createTextNode(i + 1);

        li.setAttribute('id', 'tab' + i);
        a.setAttribute('href', 'tabpanel' + i);
        li.classList.add("hidden");

        a.appendChild(txt);
        li.appendChild(a);
        li.style.padding = '0.2em';
        return li;
    }
    
    function CheckBoxQuestions(questionNo, question, answer, invalid, hint) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
        this.invalid = invalid;
        this.hint = hint;
    }
    
    
    function createRadios(invalid, ans) {
        var li = document.createElement('li'),
            ipt = document.createElement('input'),
            txt;
        ipt.setAttribute('type', 'radio');
        ipt.setAttribute('name', 'call');
        ipt.setAttribute('value', invalid);
        li.appendChild(ipt);
        txt = document.createTextNode(invalid);
        li.appendChild(txt);
        return li;
    }

    function createChoices(invalid, ans) {
        var li = document.createElement('li'),
            ul = document.createElement('ul'),
            i, btn;
        for (i = 0; i < invalid.length; i += 1) {
            ul.appendChild(createRadios(invalid[i], ans));
        }
        
        btn = document.createElement('input');
        btn.setAttribute('type', 'button');
        btn.setAttribute('value', 'submit');
        btn.setAttribute('onclick', 'checkAnswer(' + questionNo + ')');
        
        ul.appendChild(btn);
                         
        li.appendChild(ul);
        return li;
    }

    function BlankQuestions(questionNo, qustion, answer, hints, answerFromTo) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
        this.blankHints = hints;
        this.hintLength = hints.length;
        this.answerFromTo = answerFromTo;
    }
    
    function blankChoices(answer) {
        var li = document.createElement('li'),
            ipt = document.createElement('input'),
            btn = document.createElement('input');
        
        ipt.setAttribute('type', 'text');
        ipt.setAttribute('id', 'Q' + questionNo);
        ipt.setAttribute('name', 'Q' + questionNo);
        li.appendChild(ipt);

        btn.setAttribute('type', 'button');
        btn.setAttribute('value', 'submit');
        btn.setAttribute('onclick', 'testBlank(' + questionNo + ');');
        li.appendChild(btn);
        return li;
    }

    function createQuestion(question, i) {
        var div = document.createElement('div'),
            ul = document.createElement('ul');

        div.setAttribute('id', 'tabpanel' + i);
        ul.appendChild(createText(question.question));
        if (question.type === 'CHECKBOX QUESTION') {
            blank[i] = new CheckBoxQuestions(i, question.question, question.answer, question.invalidValues, question.hint);
            ul.appendChild(createChoices(question.invalidValues, question.answer));
        } else if (question.type === 'BLANK QUESTION') {
            blank[i] = new BlankQuestions(i, question.question, question.answer, question.hints, question.answerFromTo);
            increm = -1;
            ul.appendChild(blankChoices(question.answer));
        } else {}
        div.appendChild(ul);
        return div;
    }

    for (i = 0; i < questions.length; i += 1) {
        tabs.appendChild(createLinks(i));
        questionNo = i;
        panels.appendChild(createQuestion(questions[i], i));
        
    }
        
}

function module(concept) {
    var pdfId = "getPdf/file/" + concept.id,
        ele = document.getElementById('module'),
        obj = document.createElement('object'),
        win = document.getElementById('pdfWindow');
    
    obj.setAttribute('height', '100%');
    obj.setAttribute('width', '100%');
    obj.setAttribute('data', pdfId);
  //  win.appendChild(obj);
    
    ele.innerHTML = concept.name;
}

function handle(list) {
    module(list.concept);
    questionsList(list.question);
}

function callHandle(index) {
    handle(information[index]);
}


function clearPanels() {
    var tabs = document.getElementById('tabs'),
        panels = document.getElementById('questionsList'),
        div = document.createElement('div');

    tabs.innerHTML = "";
    panels.innerHTML = "";
    panels.appendChild(div);
}

function nextRequest() {
    var btn = document.getElementById('btn-submit');
    btn.onclick = function () {
        if (index < informationLength) {
            console.log("Index : "+index);
            clearPanels();
            callHandle(index);
            index += 1;
            tabsActivation(1);
        } else {
        	alert("Congratulations! You are done with the Assessment..");
            window.location.href = "prelab.html";
        }
    };
}

request.onreadystatechange = function () {
    if (request.readyState === 4 && request.status === 200) {
        var info = JSON.parse(request.responseText);
        //console.log(info.length);
        information = info;
        informationLength = info.length;
        callHandle(index);
        index += 1;
        tabsActivation(1);
        nextRequest();
    }
};

request.send();

function increment() {
    increm += 1;
    return increm;
}

function testBlank(i) {
    var txtField = document.getElementsByName('Q' + i),
        txtFieldId = document.getElementById('Q' + i),
        pos;

    console.log(" "+blank[i].answerFromTo.length);
    if(blank[i].answerFromTo.length > 0) {
        console.log(" "+blank[i].answerFromTo[0]+" "+blank[i].answerFromTo[1]);
        if(blank[i].answerFromTo[0] <= txtField[0].value && blank[i].answerFromTo[1] >= txtField[0].value) {
            
            increm = -1;
            txtFieldId.disabled = true;
            tabsActivation(i + 2);
        } else {
            pos = increment();
            if (pos < blank[i].blankHints.length - 1) {
                alert(blank[i].blankHints[pos]);
            } else {
            	alert(blank[i].blankHints[blank[i].blankHints.length - 2]);
            }            
        }
    }
    else if (txtField[0].value === blank[i].answer) {
        increm = -1;
        txtFieldId.disabled = true;
        tabsActivation(i + 2);
    } /*else {
        pos = increment();
        if (pos < blank[i].blankHints.length - 1) {
            alert(blank[i].blankHints[pos]);
        } else {
            increm = -1;
            txtFieldId.disabled = true;
            tabsActivation(i + 2);
        }
    }*/
}

    function checkAnswer(questionNo) {
        console.log('No : '+questionNo);
        var id = document.getElementById('tabpanel'+questionNo);
        var radios = id.getElementsByTagName('input');

        for(var i = 0; i < radios.length-1; i++) {
            if(radios[i].checked) {
                if(radios[i].value === blank[questionNo].answer) {
                    testAlert(questionNo);
                } else {
                    alert('Incorrect Answer\n '+blank[questionNo].hint);
                }
                
            }
        }
    }
    

function testAlert(n) {
    var div = document.getElementById('tabpanel' + n).getElementsByTagName('input'),
        i,
        tab;

    for (i = 0; i < div.length; i += 1) {
        div[i].disabled = true;
    }
    tab = document.getElementById('tab' + n);
    tabsActivation(n + 2);
}
