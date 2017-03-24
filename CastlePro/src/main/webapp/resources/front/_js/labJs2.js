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
var fromValues = [];
var withValues = [];


request.open('GET', 'lab.json');

function createText(txt) {
    var li = document.createElement('li'),
        litxt = document.createTextNode(txt);
    li.appendChild(litxt);
    return li;
}

function tabsActivation(start) {

    
        console.log("start tabs : "+start);
    
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
    
    function blankChoices(i, answer) {
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

    function DataEntry(questionNo, question, cols, rows, units, diff, columns) {
        this.questionNo = questionNo;
        this.question = question;
        this.cols = cols;
        this.rows = rows;
        this.units = units;
        this.diff = diff;
        this.columns = columns;
    }
        
    function columnRow(columns) {
        var tr, td, i, txt;
        
        tr = document.createElement("tr");
            td = document.createElement("td");
            txt = document.createTextNode("Trials");
            td.appendChild(txt);
        
        tr.appendChild(td);
        
        for(i = 0; i < columns.length; i += 1) {
            td = document.createElement("td");
            txt = document.createTextNode(columns[i]);
            td.appendChild(txt);
            tr.appendChild(td);
        }
        
        td = document.createElement("td");
            txt = document.createTextNode("----");
            td.appendChild(txt);
            tr.appendChild(td);

        return tr;
    }
   
    function createOne(questionNo, i) {
        var tr, td1, td2, td3, td4, ipt1, ipt2, ipt3, txt;

            tr = document.createElement("tr");
        
//        tr.setAttribute("id", "Q"+questionNo+"R"+i);
        td1 = document.createElement("td");
        td1.setAttribute("id", "Q"+questionNo+"D1"+i);

        td2 = document.createElement("td");
        td2.setAttribute("id", "Q"+questionNo+"D2"+i);

        td3 = document.createElement("td");
        td3.setAttribute("id", "Q"+questionNo+"D3"+i);

        td4 = document.createElement("td");
        td4.setAttribute("id", "Q"+questionNo+"D4"+i);

        txt = document.createTextNode(i);
        td1.appendChild(txt);
        if(i > 1) {
            td1.setAttribute("class","hidden");
            td2.setAttribute("class","hidden");
            td3.setAttribute("class","hidden");
            td4.setAttribute("class","hidden");
        }
        
        ipt1 = document.createElement("input");
        ipt1.setAttribute("type", "text");
        ipt1.setAttribute("id", "Q"+questionNo+"I"+i);
        ipt1.setAttribute("class", "textId");
        td2.appendChild(ipt1);
        
        ipt2 = document.createElement("input");
        ipt2.setAttribute("type", "text");
        ipt2.setAttribute("id", "Q"+questionNo+"F"+i);
        ipt2.setAttribute("class", "textId");
        td3.appendChild(ipt2);
        
        ipt3 = document.createElement("input");
        ipt3.setAttribute("type", "button");
        ipt3.setAttribute("value", "check");        
        ipt3.setAttribute("onclick", "checkRow("+questionNo+","+i+");");
        td4.appendChild(ipt3);
        
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        
        return tr;
    }
        
    function createDataEntry(question) {
        var li, ul, table, i, tr, li2, li3, btn;
        
        li = document.createElement("li");
        ul = document.createElement("ul");
        li2 = document.createElement("li");
        li3 = document.createElement("li");

        table = document.createElement("table");
        table.appendChild(columnRow(question.columns));    
        console.log("column created");
        for( i = 1; i <= 5; i += 1) {
            table.appendChild(createOne(question.questionNo, i));
        }
        li2.appendChild(table);
        ul.appendChild(li2);
        
        btn = document.createElement("input");
        btn.setAttribute("type", "button");
        btn.setAttribute("id", "exportBtn");
        btn.setAttribute('class','hidden');
        btn.setAttribute("value","Export CSV");
        btn.setAttribute("onclick", "exportcsv('" + question.questionNo + "');");
        li3.appendChild(btn);
        ul.appendChild(li3);
        
        li.appendChild(ul);        
        return li;
    }
    

    function createTextArea(question) {
        var li = document.createElement("li"),
            txt = document.createElement("textarea");
        
        txt.setAttribute("value", question.question);
        li.appendChild(txt);
        
        var btn = document.createElement("input");
        btn.setAttribute('type', 'button');
        btn.setAttribute('value', 'submit');
        btn.setAttribute('onclick', 'dataTextNext(' + questionNo + ');');
        li.appendChild(btn);

        return li;
    }
    
    function Comparision(questionNo, question, fromValues, withValues, hint) {
        this.questionNo = questionNo;
        this.question = question;
        this.fromValues = fromValues;
        this.withValues = withValues;
        this.hint = hint;
    }
    
    function createComparision(question) {
        var li, ul, li1, li2, ipt1, ipt2, li3, ipt3;
        
        li = document.createElement("li");
        ul = document.createElement("ul");
        li1 = document.createElement("li");
        ipt1 = document.createElement("input");
        ipt1.setAttribute("type", "text");
        ipt1.setAttribute("placeholder", "temperature");
        ipt1.setAttribute("id", "Q"+question.questionNo+"C1");
        ipt1.setAttribute("name", "Q"+question.questionNo+"C1");
        
        li2 = document.createElement("li");
        ipt2 = document.createElement("input");
        ipt2.setAttribute("type", "text");
        ipt2.setAttribute("placeholder", "density");
        ipt2.setAttribute("id", "Q"+question.questionNo+"C2");
        ipt2.setAttribute("name", "Q"+question.questionNo+"C2");

        li3 = document.createElement("li");
        ipt3 = document.createElement("input");
        ipt3.setAttribute("type", "button");
        ipt3.setAttribute("value", "submit");
        ipt3.setAttribute("id", "Q"+question.questionNo+"Btn");
        ipt3.setAttribute("onclick", "findcompare("+question.questionNo+")");
        li3.appendChild(ipt3);
        
        li2.appendChild(ipt2);
        li1.appendChild(ipt1);
        
        ul.appendChild(li1);
        ul.appendChild(li2);
        ul.appendChild(li3);
        
        li.appendChild(ul);
        
        return li;
    }
    
    function createQuestion(question, i) {
        var div = document.createElement('div'),
            ul = document.createElement('ul');

        div.setAttribute('id', 'tabpanel' + i);
        ul.appendChild(createText(question.question));
        if (question.type === 'COMPARISION') {
            blank[i] = new Comparision(i, question.question, question.fromValues, question.withValues, question.hint);
            ul.appendChild(createComparision(blank[i]));
        }
        else if (question.type === 'CHECKBOX QUESTION') {
            blank[i] = new CheckBoxQuestions(i, question.question, question.answer, question.invalidValues, question.hint);
            ul.appendChild(createChoices(question.invalidValues, question.answer));
        } else if (question.type === 'BLANK QUESTION') {
            blank[i] = new BlankQuestions(i, question.question, question.answer, question.hints, question.answerFromTo);
            increm = -1;
            ul.appendChild(blankChoices(i, question.answer));
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
    
    console.log("Ques len : "+questions.length);
        
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
    
 
    function checkRow(questionNo, i) {
        var k, l, r1, r2, d, n1, n2, n3, n4;
        
        k = document.getElementById("Q"+questionNo+"I"+i).value;
        l = document.getElementById("Q"+questionNo+"F"+i).value;
        d = l - k;
        r1 = blank[questionNo].diff[0];
        r2 = blank[questionNo].diff[1];

        if ((r1 < d) && (d < r2)) {
            if(i === 5) {
                alert('Nice work, you have completed this section.');
                var btn = document.getElementById("exportBtn");
                btn.classList.remove("hidden");
            } else {

                alert("Nice work, the value is accepted!  Please continue by adding another sample of water to this flask.  Note that the initial mass has been entered for you. ");

                n1 = document.getElementById("Q"+questionNo+"D1"+(i+1));
                n2 = document.getElementById("Q"+questionNo+"D2"+(i+1));
                n3 = document.getElementById("Q"+questionNo+"D3"+(i+1));
                n4 = document.getElementById("Q"+questionNo+"D4"+(i+1));

                n1.classList.remove("hidden");
                n1.classList.add("show");
                n2.classList.remove("hidden");
                n2.classList.add("show");
                n3.classList.remove("hidden");
                n3.classList.add("show");
                n4.classList.remove("hidden");
                n4.classList.add("show");
                
                
                k = document.getElementById("Q"+questionNo+"F"+i).value;
                console.log("K : "+k);
                console.log("N2: "+n2);
                
                n2.getElementsByTagName("input")[0].value = k;
                //n2.value = ;
            }

        } else {
            alert("Unfortunately the mass you entered is not consistent with adding 5.00 mL’s of water to the flask.  Please empty the flask and re-enter the mass of the empty flask to try again.");
                document.getElementById("Q"+questionNo+"I"+i).value = "";
                document.getElementById("Q"+questionNo+"F"+i).value = "";
        }
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
                tabsActivation(parseInt(questionNo) + 2 );
            }
        }
        request.send();
//        tabsActivation(parseInt(questionNo)+2);
    }
    
    console.log(exc);

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
            exc[i][0] = panelTr[i].getElementsByTagName("td")[0].innerHTML;
            for(var j = 1; j < panelTd.length; j++) {
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
        
        return true;
    }
}

    function findcompare(questionNo) {
        var k, l, i, in1, in2;

            console.log("questionNo : "+questionNo);
    
        k = document.getElementById("Q8C1");
        l = document.getElementById("Q8C2");
        
        in1 = parseInt(k.value);
        in2 = parseFloat(l.value);
        
        console.log("Compare 1 : "+in1);
        console.log("Compare 2 : "+in2);
        
        if(18 <= in1 && in1 <= 27) {
            
            if( in2 != NaN) {
                for(i = 0; i < blank[questionNo].fromValues.length; i += 1) {
                    if (blank[questionNo].fromValues[i] === in1) {
                        console.log(blank[questionNo].withValues[i]);
                        if (in2 == blank[questionNo].withValues[i]) {
                            tabsActivation(questionNo + 2);
                        } else {
                                alert("Your density doesn’t seem to match the table in the lab manual.  Remember to round to the nearest 1 degree and try again.");
                        }
                    }           
                }
            }
        } else {
            alert("Your temperature is not within the range typically expected in the lab.  Please try again.");
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
            
            var url='makelabpdf.json?lab='+JSON.stringify(textarea);
            console.log("True : called FOr postlab");
        
            request.open('GET', url, true);        
            request.onreadystatechange = function () {
                if (request.status === 200 && request.readyState === 4) {
                window.location.href = "prelab.html";
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
    
    if( i === 4) {
        var id = document.getElementById("Q2").value;
        console.log("I5 : "+id+ " "+ txtField[0].value);
        var a = parseInt(id) + 4.7;
        var b = parseInt(id) + 5.3;
        console.log("A : "+a);
        console.log("B : "+b);
        
        console.log( );
        console.log();
        
        if((a <= txtField[0].value) && (b >= txtField[0].value)) {
            tabsActivation(i + 2);
        } else {
            alert("The mass of your flask and water is different than expected, please try again.");
        }
    }
    else if(blank[i].answerFromTo.length > 0) {
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
