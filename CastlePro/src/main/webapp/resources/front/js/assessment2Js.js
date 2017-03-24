var request = new XMLHttpRequest();
var questionNo;
var question;
var answer;
var blankHints = [];
var hintLength;
var increm;
var information = [];
var questionLength;
var index = 0;
var blank = [];
var invalid = [];
var hint, units;
var answerFromTo = [];

request.open('GET', 'assessmentacidbase.json');

function createText(txt) {
    var li = document.createElement('li'),
        litxt = document.createTextNode(txt);
    li.appendChild(litxt);
    return li;
}


function pdfActivation(pan) {
     // when the page loads, grab the li elements
    tabLinks = document.getElementById("pdftabs").getElementsByTagName("li");
    
	// Now get all the tab panel container divs
	tabPanels = document.getElementById("pdfcontainer").getElementsByTagName("div");
	
	// activate the _first_ one
    displayPanel(tabLinks[pan]);
   
    // attach event listener to links using onclick and onfocus, fire the displayPanel function, return false to disable the link
    for (var i = 0; i < tabLinks.length; i++) {
        tabLinks[i].onclick = function() { 
			displayPanel(this); 
			return false;
		}
        tabLinks[i].onfocus = function() { 
			displayPanel(this); 
			return false;
		}
    }
}

function displayPanel(tabToActivate) {
    // go through all the <li> elements
    for (var i = 0; i < tabLinks.length; i++) {
        if (tabLinks[i] == tabToActivate) {
			// if it's the one to activate, change its class
            tabLinks[i].classList.add("active");
			// and display the corresponding panel
			tabPanels[i].style.display = "block";
        } else {
			// remove the active class on the link
        	tabLinks[i].classList.remove("active");
			// hide the panel
			tabPanels[i].style.display = "none";
        }
	}
    
}
function tabsActivation(start) {
    
    var tablinks = document.getElementById("tabs").getElementsByTagName("li"),
        tabpanels = document.getElementById("questionsList").getElementsByTagName("div"),
        i;


    if(questionLength+1 === start) {
        document.getElementById("btn-submit").style.visibility="visible";
        var id = document.getElementById("disclaim"),
        txt = document.createTextNode("Warning!!!  In order to save section you MUST click the 'Save and Continue to Next Section' button below.  Failure to click the 'Save and Continue to Next Section' button will result in your progress being lost. ");
        id.appendChild(txt);
    }
    function displayTabs(activateTab) {
        for (i = 0; i < tablinks.length; i += 1) {
            if (tablinks[i] === activateTab) {
                tablinks[i].style.visibility="visible";
                tablinks[i].classList.add("active");
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

function warning() {
    var k = confirm("WARNING \nIf you return to the main menu before you complete this section, your progress will be lost.");
    if( k === true) {
        window.location.href="status.html";
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
    //    li.classList.add("hidden");

        li.style.visibility = "hidden";
        
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

    function BlankQuestions(questionNo, qustion, answer, hints, answerFromTo, units) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
        this.blankHints = hints;
        this.hintLength = hints.length;
        this.answerFromTo = answerFromTo;
        this.units = units;
    }
    
    function blankChoices(answer, questionNo) {
        var li = document.createElement('li'),
            ipt = document.createElement('input'),
            btn = document.createElement('input'),
            txt = document.createTextNode(blank[questionNo].units);
        
        ipt.setAttribute('type', 'text');
        ipt.setAttribute('id', 'Q' + questionNo);
        ipt.setAttribute('name', 'Q' + questionNo);
        li.appendChild(ipt);

        li.appendChild(txt);
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
            blank[i] = new BlankQuestions(i, question.question, question.answer, question.hints, question.answerFromTo, question.units);
            increm = -1;
            ul.appendChild(blankChoices(question.answer, i));
        } else {}
        
        var p = document.createElement('p'),
            txt = document.createTextNode("Well done! You are on the right track.");
        
        p.classList.add("wishes");
        p.appendChild(txt);
        ul.appendChild(p);
        
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
 var ele = document.getElementById('module');
        
    ele.innerHTML = concept.name;
}

function handle(list) {
    module(list.concept);
    questionsList(list.question);
    this.questionLength = list.question.length;
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

            request.open('GET','assessmentacidbaseupdate');
            request.onreadystatechange = function () {
                if ((request.status === 200) && (request.readyState === 4)) {
                    var res = JSON.parse(request.responseText);
                    console.log("Response : "+res);
                    window.location.href = res;
                }
           //     window.location.href = "status.html";
            }
            request.send();
            console.log(request);
        }
    };


request.onreadystatechange = function () {
    if (request.readyState === 4 && request.status === 200) {
        var info = JSON.parse(request.responseText);
        //console.log(info.length);
        information = info;
        document.getElementById("btn-submit").style.visibility="hidden";
        handle(info);
        index += 1;
        pdfActivation(0);
        tabsActivation(1);
        nextRequest();
    }
};

request.send();

function increment() {
    increm += 1;
    return increm;
}

function dly() {
    document.getElementById("wishes").style.visibility="visible";
}

function dlyOut() {
    document.getElementById("wishes").style.visibility="hidden";
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
            
                 document.getElementById("tabpanel"+i).getElementsByTagName('p')[0].style.visibility="visible";
            pdfActivation(0);
            setTimeout( function () {
              tabsActivation(i + 2);      
                        }, 2000);
        } else {
            pos = increment();
            if (pos < blank[i].blankHints.length - 1) {
                        var id = document.getElementById("hints"),
                            li = document.createElement("li"),
                            p = document.createElement("p"), txt;
                
                if(pos === 0) {
                    id.innerHTML = "";
                    txt = document.createTextNode("Hint 1 : "+blank[i].blankHints[pos]);
                    p.innerHTML = "Hint 1 : "+blank[i].blankHints[pos];
                }
                else if(pos === 1)  {      
                    txt = document.createTextNode("Hint 2 : "+blank[i].blankHints[pos]);
                    p.innerHTML = "Hint 2 : "+blank[i].blankHints[pos];
                }
                 else { 
                    txt = document.createTextNode("Hint 3 : "+blank[i].blankHints[pos]);
                     p.innerHTML = "Hint 3 : "+blank[i].blankHints[pos];
                 }
                
                li.appendChild(p);
                id.appendChild(li);
                pdfActivation(1);              
            } else {
                pdfActivation(1);             
            }            
        }
    }
    else if (txtField[0].value === blank[i].answer) {
        increm = -1;
        txtFieldId.disabled = true;
        tabsActivation(i + 2);
    } 
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
                            var id = document.getElementById("hints"),
                                li = document.createElement("li"),
                                p = document.createElement("p"),
                                txt = document.createTextNode("Hint : \n"+blank[questionNo].hint);
                            
                            id.innerHTML = "";
                            
                    p.innerHTML = "Hint : \n"+blank[questionNo].hint;
                    li.appendChild(p);
                    id.appendChild(li);
                     pdfActivation(1);
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
    
 document.getElementById("tabpanel"+n).getElementsByTagName('p')[0].style.visibility="visible";
    setTimeout( function () {
          tabsActivation(n + 2);      
                }, 2000);
 pdfActivation(0);
}
