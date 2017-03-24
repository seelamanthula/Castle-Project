var request;
if(window.XMLHttpRequest) {
    request = new XMLHttpRequest();
} else {
    request = new ActiveXObject("Microsoft.XMLHTTP");
}
var questionNo;
var question;
var questionLength;
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
var calculatedResult = [];
var cal = [];

request.open('GET', 'acidbaselab.json');

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

    console.log('start : '+start);
    if(start === 12) {
        var i = document.getElementById('Q11I1');
        var i2 = document.getElementById('Q11I2');
        var i3 = document.getElementById('Q11F1');

        var k = document.getElementById('Q10').value;
        var l = document.getElementById('Q9').value;
    
        i.value = l;
        i2.value = k;
        i3.value = k;

    }else if (start === 13 ) {
        document.getElementById("excelfile").style.visibility="visible";
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
        if(i > 2) {
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
    
    function TextQuestion(questionNo, question, answer) {
        this.questionNo = questionNo;
        this.question = question;
        this.answer = answer;
    }

     function createTextArea(question) {
        var li = document.createElement("li"),
            txt = document.createElement("textarea");
        
        txt.setAttribute("value", question.question);
        txt.setAttribute("id", "Q"+questionNo);
        txt.setAttribute("name", "Q"+questionNo);
        li.appendChild(txt);
        
        var btn = document.createElement("input");
        btn.setAttribute('type', 'button');
        btn.setAttribute('value', 'submit');
//        btn.setAttribute('onclick', 'tabsActivation(' + parseInt(questionNo) + parseInt(2) + ');');
        btn.setAttribute('onclick', 'dataTextNext(' + questionNo + ');');
        li.appendChild(btn);

        return li;
    }
    
    function createQuestion(question, i) {
        var div = document.createElement('div'),
            ul = document.createElement('ul');

        div.setAttribute('id', 'tabpanel' + i);
        ul.appendChild(createText(question.question));
        if (question.type === 'DATA ENTRY'){
            blank[i] = new DataEntry(i, question.question, question.cols, question.rows, question.units, question.diff, question.columns);
            ul.appendChild(createDataEntry(blank[i]));            
        } else if (question.type === 'CHECKBOX QUESTION') {
            blank[i] = new CheckBoxQuestions(i, question.question, question.answer, question.invalidValues, question.hint);
            ul.appendChild(createChoices(question.invalidValues, question.answer));
        } else if (question.type === 'BLANK QUESTION') {
            blank[i] = new BlankQuestions(i, question.question, question.answer, question.hints, question.answerFromTo, question.units);
            increm = -1;
            ul.appendChild(blankChoices(question.answer, i));
        } else if (question.type === 'TEXT QUESTION') {
            blank[i] = new TextQuestion(i, question.question, question.answer);
            ul.appendChild(createTextArea(blank[i]));
        }
        
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
        var ele = document.getElementById('module'),
        a = document.createElement('a'),
        txt = document.createTextNode("Excell Access"),
        excelPath = "getExcel/file/acidbase",
        excelId = document.getElementById('excelId');
    
    console.log("Index in module : "+index);
    
    a.setAttribute("href", excelPath);
    a.setAttribute("id", "excelfile");
    a.style.visibility="hidden";

    a.appendChild(txt);
     excelId.appendChild(a);
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
            var id = document.getElementById('Q15').value;
            console.log('Entered Data : '+id);
            var url = 'labacidbaseupdate?data='+id;
            console.log('URL : '+url);
            request.open('GET',url);
            request.onreadystatechange = function () {
                
                if ((request.status === 200) && (request.readyState === 4)) {
                    var res = request.responseText;
                    console.log("Response : "+res);
                    window.location.href = res;
                }
           //     window.location.href = "status.html";
            }
            request.send();
        }
    };


request.onreadystatechange = function () {
   // console.log(request);
    if (request.readyState === 4 && (request.status === 200 || request.status === 0)) {
        var info = JSON.parse(request.responseText);
        //console.log(info.length);
        information = info;
        document.getElementById("btn-submit").style.visibility="hidden";
        handle(info);
        index += 1;
        pdfActivation(0);
        tabsActivation(1);
    //    nextRequest();
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

 function checkRow(questionNo, i) {
        var k, l, r1, r2, d, n1, n2, n3, n4;
        
        k = document.getElementById("Q"+questionNo+"I"+i).value;
        l = document.getElementById("Q"+questionNo+"F"+i).value;
        d = l - k;
        r1 = blank[questionNo].diff[0];
        r2 = blank[questionNo].diff[1];

/*        if(l > 35) {
        	   alert("The Final Mass is Greater than 35, Please refill teh burrete and check your readings.");
        	   document.getElementById("Q"+questionNo+"I"+i).value = "";
               document.getElementById("Q"+questionNo+"F"+i).value = "";
        }*/
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

/*                n1.classList.remove("hidden");
                n1.classList.add("show");
                n2.classList.remove("hidden");
                n2.classList.add("show");
                n3.classList.remove("hidden");
                n3.classList.add("show");
                n4.classList.remove("hidden");
                n4.classList.add("show");*/
                
                n1.setAttribute("class","");
                n2.setAttribute("class","");
                n3.setAttribute("class","");
                n4.setAttribute("class","");
                
                k = document.getElementById("Q"+questionNo+"F"+i).value;
                console.log("K : "+k);
                console.log("N2: "+n2);
                
                n2.getElementsByTagName("input")[0].value = k;
                //n2.value = ;
            }

        } else {
            alert("Unfortunately the Burette reading you entered is not consistent with adding 10.00 mL of water to the flask.  Please empty the Burette and re-enter the mass of the empty flask to try again.");
                document.getElementById("Q"+questionNo+"I"+i).value = "";
                document.getElementById("Q"+questionNo+"F"+i).value = "";
        }
    }

function exportcsv(questionNo) {
    var boo = collectExe(questionNo);
        
    if(boo == true) {

    	var url;
        console.log("Index in CSV : "+index);
        
		url ='makeexcel2.json?excel='+JSON.stringify(rowElements);
 //       url ='files/acidcalculation.json';  	    			
        request.open('GET', url, true);        
        request.onreadystatechange = function () {
            console.log(request);
            if ((request.status === 200 || request.status === 0) && request.readyState === 4) {
                console.log("True : called next one");
                calculatedResult = JSON.parse(request.responseText);
                console.log('Got response');
                console.log(calculatedResult);
                tabsActivation(parseInt(questionNo) + 2 );
            }
        }
        request.send();
        tabsActivation(parseInt(questionNo)+2);
    }
    
//    tabsActivation(parseInt(questionNo) + 2 );
    
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

function testBlank(i) {
    var txtField = document.getElementsByName('Q' + i),
        txtFieldId = document.getElementById('Q' + i),
        pos;

   // console.log(" "+blank[i].answerFromTo.length);
     if(i > 11) {
        var id = document.getElementById("Q"+i).value, k, diff1, diff2;
        console.log('len : '+id);
        if( i === 12) {
            k = calculatedResult.volumes;
            diff1 = k - 0.1;
            diff2 = k + 0.1;
        } else if( i === 13) {
            k = calculatedResult.average;
            diff1 = k - 0.1;
            diff2 = k + 0.1;
        } else if( i === 14) {
            k = calculatedResult.rsd;
            diff1 = k - 5;
            diff2 = k + 5;
        }
        console.log('K '+ k);
         console.log(diff1);
         console.log('D2 : '+diff2);
      //  console.log(parseFloat( - 0.01));
        //console.log(parseFloat(calculatedResult.volumes + 0.01));

     /*   if(id == parseFloat(calculatedResult.volumes - 0.01) || id == parseFloat(calculatedResult.volumes + 0.01) || id == parseFloat(calculatedResult.volumes)) {*/
         
         console.log(id == diff1);
         console.log(id == diff2);
         console.log(id == k);

         if(id >= diff1 && id <= diff2 && id.length > 0) {
            document.getElementById("tabpanel"+i).getElementsByTagName('p')[0].style.visibility="visible";
            pdfActivation(0);
            setTimeout( function () {
              tabsActivation(i + 2);      
                        }, 2000);
        } else {
            var id = document.getElementById("hints"),
                            li = document.createElement("li"),
                            p = document.createElement("p"), txt;
                
                    id.innerHTML = "";
                    p.innerHTML = "Hint : "+blank[i].blankHints[0];
                
                    li.appendChild(p);
                    id.appendChild(li);
                    pdfActivation(1);               
        }
    } else if( i === 9) {
        var id = document.getElementById("Q9").value;
        console.log('len : '+id.length);
        console.log(blank[9].answerFromTo[0] < id);
        console.log(blank[9].answerFromTo[1] > id);
        if(blank[9].answerFromTo[0] < id && blank[9].answerFromTo[1] > id) {
            document.getElementById("tabpanel"+i).getElementsByTagName('p')[0].style.visibility="visible";
            pdfActivation(0);
            setTimeout( function () {
              tabsActivation(i + 2);      
                        }, 2000);
        } else {
            var id = document.getElementById("hints"),
                            li = document.createElement("li"),
                            p = document.createElement("p"), txt;
                
                    id.innerHTML = "";
                    p.innerHTML = "Hint : "+blank[i].blankHints[0];
                
                    li.appendChild(p);
                    id.appendChild(li);
                    pdfActivation(1);               
        }
    } else if (i === 10) {
         var id = document.getElementById("Q10").value,
             id2 = document.getElementById("Q9").value;
        console.log('len : '+id);
        console.log('ID 2 : '+id2);
        
        console.log(parseFloat(blank[i].answerFromTo[0]) + parseInt(id2));
          console.log(parseFloat(blank[i].answerFromTo[1]) + parseInt(id2));
        
        if(parseFloat(blank[i].answerFromTo[0]) + parseInt(id2) < id && parseFloat(blank[i].answerFromTo[1]) + parseInt(id2)> id) {
            document.getElementById("tabpanel"+i).getElementsByTagName('p')[0].style.visibility="visible";
            pdfActivation(0);
            setTimeout( function () {
              tabsActivation(i + 2);      
                        }, 2000);
        } else {
            var id = document.getElementById("hints"),
                            li = document.createElement("li"),
                            p = document.createElement("p"), txt;
                
                    id.innerHTML = "";
                    p.innerHTML = "Hint : "+blank[i].blankHints[0];
                
                    li.appendChild(p);
                    id.appendChild(li);
                    pdfActivation(1);               
        }
        
    } else if(blank[i].answerFromTo.length > 0) {
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
                    var otp = "Hint 2 : "+blank[i].blankHints[pos];
                    if( i === 4) {
                    	otp  += " <table border='1'><thead><td></td><td>A</td><td>B</td></thead><tbody><tr><td>1</td><td>5.103</td><td>=STDEV(B2:B5)/AVERAGE(B2:B5)*1000</td></tr><tr><td>2</td><td>4.987</td><td></td></tr><tr><td>3</td><td>5.031</td><td></td></tr><tr><td>4</td><td>4.899</td><td></td></tr></tbody></table>";
                    }
                    p.innerHTML = otp;
                }
                 else { 
                    txt = document.createTextNode("Hint 3 : "+blank[i].blankHints[pos]);
                    
                    var otp ="Hint 3 : "+blank[i].blankHints[pos];
                 	  if( i === 4) {
                       otp += "<table border='1'><thead><td></td><td>A</td><td>B</td></thead><tbody><tr><td>1</td><td>X.000</td><td>=STDEV(B2:B5)/AVERAGE(B2:B5)*1000</td></tr><tr><td>2</td><td>Y.000</td><td></td></tr><tr><td>3</td><td>Z.000</td><td></td></tr><tr><td>4</td><td>A.000</td><td></td></tr></tbody></table>";
                 	  }
                     p.innerHTML = otp;
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
//                    alert('Incorrect Answer\n '+blank[questionNo].hint);
                }
                
            }
        }
    }
    
function dataTextNext(n) {
    tabsActivation(n + 2);
      document.getElementById("btn-submit").style.visibility="visible";
        var id = document.getElementById("disclaim"),
        txt = document.createTextNode("Warning!!!  In order to save section you MUST click the 'Save and Continue to Next Section' button below.  Failure to click the 'Save and Continue to Next Section' button will result in your progress being lost. ");
        id.appendChild(txt);
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
