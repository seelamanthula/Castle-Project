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
var cols;
var rows;
var diff = [];
var units;
var columns = [];
var exc = [];
var textarea = [];
var texta = 0;
var rowElements = [];
var colElements = [];
var one, two, three;

request.open('GET', 'lab.json');

function createText(txt) {
    var li = document.createElement('li'),
        litxt = document.createTextNode(txt);
    li.appendChild(litxt);
    return li;
}

function tabsActivation(start) {
    
    if(isNaN(start)) {
        console.log("tabs : "+start);
    }
    
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
        btn.setAttribute('value', 'next');
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
        btn.setAttribute('value', 'next');
        btn.setAttribute('onclick', 'testBlank(' + questionNo + ');');
        li.appendChild(btn);
        return li;
    }

    function DataEntry(questionNo, question, cols, rows, units, diff, columns) {
        this.questionNo = questionNo;
        this.question = question;
        this.cols = cols;
        this.rows = rows;
        this.units = units;
        this.diff = diff;
        this.columns = columns;
    }
    
    function row(questionNo, i) {
        var tr = document.createElement("tr"),
            td1 = document.createElement("td"),
            ipt1 = document.createElement("input");
     
        ipt1.setAttribute("type", "text");
        ipt1.setAttribute("name", "Q"+questionNo+"I"+i);
        ipt1.setAttribute("class", "textId");
        ipt1.setAttribute("id", "Q"+questionNo+"I"+i);
        ipt1.setAttribute("size", 20);
        td1.appendChild(ipt1);
        
        var td2 = document.createElement("td"),
            ipt2 = document.createElement("input");        
        ipt2.setAttribute("type", "text");
        ipt2.setAttribute("name", "Q"+questionNo+"F"+i);
        ipt2.setAttribute("class", "textId");

        ipt2.setAttribute("id", "Q"+questionNo+"F"+i);
        ipt2.setAttribute("size", 20);
        td2.appendChild(ipt2);        

        var td = document.createElement("td"),
            ipt3 = document.createElement("input");        
        ipt3.setAttribute("type", "text");
        ipt3.setAttribute("name", "trial"+questionNo+i);
        ipt3.setAttribute("class", "textId");

        ipt3.setAttribute("id", "trial"+questionNo+i);
        ipt3.setAttribute("size", 20);
        td.appendChild(ipt3);
        
        tr.appendChild(td);
        tr.appendChild(td1);
        tr.appendChild(td2);
        return tr;
    }
    
    function columnRow(columns) {
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        td1.innerHTML = "trials";
        var td2 = document.createElement("td");
        td2.innerHTML = columns[0];
        var td3 = document.createElement("td");
        td3.innerHTML = columns[1];
        
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        return tr;
    }
    
    function createDataEntry(question) {
        var li = document.createElement("li");

        var table = document.createElement("table");
        
        console.log("Rows : ");
        console.log(question.rows);
        
        table.appendChild(columnRow(question.columns));
        
        for(var i = 0; i < question.rows; i++) {
            table.appendChild(row(question.questionNo, i));
        }
        
        li.appendChild(table);

        var btn = document.createElement("input");
        btn.setAttribute('type', 'button');
        btn.setAttribute('value', 'Export to CSV');
        btn.setAttribute('onclick', 'exportcsv(' + questionNo + ');');
        li.appendChild(btn);

        return li;
    }

  
    function createTextArea(question) {
        var li = document.createElement("li"),
            txt = document.createElement("textarea");
        
        txt.setAttribute("value", question.question);
        li.appendChild(txt);
        
        var btn = document.createElement("input");
        btn.setAttribute('type', 'button');
        btn.setAttribute('value', 'Next');
        btn.setAttribute('onclick', 'dataTextNext(' + questionNo + ');');
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
        } else if (question.type === 'DATA ENTRY'){
            blank[i] = new DataEntry(i, question.question, question.cols, question.rows, question.units, question.diff, question.columns);
            ul.appendChild(createDataEntry(blank[i]));
        } else if (question.type === 'TEXT QUESTION') {
            blank[i] = new TextQuestion(i, question.question, question.answer);
            ul.appendChild(createTextArea(blank[i]));
        }
        div.appendChild(ul);
        return div;
    }

    for (i = 0; i < questions.length; i += 1) {
        tabs.appendChild(createLinks(i));
        questionNo = i;
        panels.appendChild(createQuestion(questions[i], i));
        
    }
        
}

    function dataTextNext(questionNo) {
        var panel = document.getElementById("tabpanel"+questionNo);
        var ques = panel.getElementsByTagName("ul")[0].getElementsByTagName("li")[0].innerHTML;
        var ans1 = panel.getElementsByTagName("ul")[0].getElementsByTagName("textarea")[0].value;

        console.log("Txt Ques : "+ques);
        console.log("Txt Ans 1 : "+ans1);
        
        textarea[texta] = new TextQuestion(questionNo, ques, ans1);
        texta += 1;
        
        console.log("Text Area length : "+textarea.length);
        
        tabsActivation(questionNo + 2);
    }


  function TextQuestion(questionNo, question, answer) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
    }
    
function dataNext(i) {
    tabsActivation(i + 2);
}
    

function exportcsv(questionNo) {
    var boo = collectExe(questionNo);
        
    if(boo == true) {
    	var url;
        console.log("Index in CSV : "+index);
    	if(index - 1 === 0) {
    		console.log("In Excel 1")
    		url ='makeexcel1.json?excel='+JSON.stringify(rowElements);
    	}
    	else {
    		console.log("In Excel 2")
    		url ='makeexcel2.json?excel='+JSON.stringify(rowElements);   		
    	} 
        request.open('GET', url, true);        
        request.onreadystatechange = function () {
            if (request.status === 200 && request.readyState === 4) {
                console.log("True : called next one");
                tabsActivation( questionNo + 2 );
            }
        }
        request.send();
    }

    function ColElements(one, two, three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    function collectExe(questionNo) {
    
        var panelTr = document.getElementById("tabpanel"+questionNo).getElementsByTagName("table")[0].getElementsByTagName("tr"),
            panelTd = panelTr[0].getElementsByTagName("td"),
            check = "",
            i = 0;
        
        exc[i] = [];
        for(var j = 0; j < panelTd.length; j++) {
            exc[i][j] = panelTr[i].getElementsByTagName("td")[j].innerHTML;
            colElements[j] = exc[i][j];
        }
        rowElements[i] = new ColElements(exc[0][0], exc[0][1],exc[0][2]);
        console.log("Row Elements : "+rowElements[i]);
        
        for(i = 1; i < panelTr.length; i++) {
            exc[i] = [];
            for(var j = 0; j < panelTd.length; j++) {
                exc[i][j] = panelTr[i].getElementsByTagName("td")[j].getElementsByTagName("input")[0].value;
            }
            rowElements[i] = new ColElements(exc[i][0], exc[i][1],exc[i][2]);
            console.log("Row Elements : "+rowElements[i]);
            
            if(!(((exc[i][2] - exc[i][1]) >= blank[questionNo].diff[0]) && ((exc[i][2] - exc[i][1]) <= blank[questionNo].diff[1]))) {
                check += i+",";
            }
        }
        
        if(check.length > 0) {
                alert("Check the Trials "+check);
            return false;
        }     
        
        if(!((exc[1][2] == exc[2][1]) && (exc[2][2] == exc[3][1]) && (exc[3][2] == exc[4][1]) && (exc[4][2] == exc[5][1]))) {
            alert("Check Final and Initial Results are not matching")
            return false;
        }
        
        return true;
    }
}


function module(concept) {
    var pdfId = "getPdf/file/" + concept.id,
        ele = document.getElementById('module'),
        obj = document.createElement('object'),
        win = document.getElementById('pdfWindow'),
        excelId = document.getElementById('excelId'),
        a = document.createElement('a'),
        txt = document.createTextNode("Excell Access"),
        excelPath = "getExcel/file/"+ index;
    
    console.log("Index in module : "+index);
    
    obj.setAttribute('height', '100%');
    obj.setAttribute('width', '100%');
    obj.setAttribute('data', pdfId);
    win.appendChild(obj);
    
    a.setAttribute("href", excelPath);
    a.appendChild(txt);
    
    excelId.appendChild(a);
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
        div = document.createElement('div'),
        exe = document.getElementById("excelId");

    tabs.innerHTML = "";
    panels.innerHTML = "";
    exe.innerHTML = "";
    panels.appendChild(div);
}

function nextRequest() {
    var btn = document.getElementById('btn-submit');
    btn.onclick = function () {
        if (index < informationLength) {
            console.log("NextRequest Index : "+index);
            clearPanels();
            callHandle(index);
            index += 1;
            tabsActivation(1);
        } else {
            
            console.log("Stringified : "+JSON.stringify(textarea));
            
               var url='makelabpdf.json?textarea='+JSON.stringify(textarea);
            
          //  var url = 'postlab.html';
            console.log("True : called FOr postlab");
        
        request.open('GET', url, true);        
        request.onreadystatechange = function () {
            if (request.status === 200 && request.readyState === 4) {
            	window.location.href = "postlab.html";
                tabsActivation( questionNo + 2 );
            }
        }
        request.send();
            
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
                increm = -1;
                txtFieldId.disabled = true;
                tabsActivation(i + 2);
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
