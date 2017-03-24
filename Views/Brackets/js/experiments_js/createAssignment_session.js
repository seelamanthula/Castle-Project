var newExperimentName = "";
var modulesList = [];
var orderList = [];
var allQuestionList = [];
var moduleNamesList = [];


    
    var quesId, ques, module;

    function addMCQDetails() {
        
        var falses, trues, hints, temp;
        
        falses = quesId.find('.falses');
        trues = quesId.find('.trues');
        hints = quesId.find('.hints');
        
        var io = ques.iOptions;   
        for(var i = 0; i < io.length; i += 1) {
            temp = ques.questionId+'_F_'+(i+1);
            falses.append(addFC(temp, io[i]));
            
            trackChangeWithId(ques.questionId, temp);
            removeItem(temp, "");
        }
                
        var vo = ques.vOptions;    
        for(var i = 0; i < vo.length; i += 1) {
            temp = ques.questionId+'_T_'+(i+1);            
            trues.append(addTC(temp, vo[i]));
            
            trackChangeWithId(ques.questionId, temp);
            removeItem(temp, "");
        }
        
        var hi = ques.hints; 
        for(var i = 0; i < hi.length; i += 1) {
            temp = ques.questionId+'_H_'+(i+1);           
            hints.append(addCommonHint(temp, hi[i]));
            
            trackChangeWithId(ques.questionId, temp);
            removeItem(temp, "");
        }
        
         // Files values cannot be set      
       
    }
    
    function addNUMDetails() {
        
        var hints, temp, corrects;
        hints = quesId.find('.hints');
        corrects = quesId.find('.corrects');
        
        var cas = ques.cOptions;
        for(var i = 0; i < cas.length; i += 1) {
            temp = ques.questionId+'_CA_'+(i+1);
            corrects.append(addCAnswers(temp, cas[i]));
            
            trackChangeWithId(ques.questionId, temp);
            removeItem(temp, "");
        }
        
        quesId.find('.from').val(ques.from);
        quesId.find('.to').val(ques.to);
        
        trackChangeWithId(ques.questionId, ques.questionId+"_From");
        trackChangeWithId(ques.questionId, ques.questionId+"_To");
        
        var hi = ques.hints; 
        for(var i = 0; i < hi.length; i += 1) {
            temp = ques.questionId+'_H_'+(i+1);           
            hints.append(addCommonHint(temp, hi[i]));
            
            trackChangeWithId(ques.questionId, temp);
            removeItem(temp, "");
        }
        
         // Files values cannot be set        
    }
    
    function addSHORTDetails() {
        var temp = ques.questionId+'_SA', 
            ans = quesId.find('#'+temp);
        ans.val(ques.cAnswer);
        
        trackChangeWithId(ques.questionId, temp);
        removeItem(temp, "");
        
         // Files values cannot be set
    }
    
    function addSessions() {
        
        //    $('#experiment-name').css({border:"1px solid green"});
            $('#experiment-name').val(newExperimentName);
        
            var mod = $('#moduleList');

            if(modulesList !== null) {        
                for(var i = 0; i < modulesList.length; i += 1) {
                    mod.append(getModule(modulesList[i]));

                    mod.find('#'+modulesList[i]).find('.module-name').val(moduleNamesList[i].name);

                    addModuleChoices(modulesList[i]);
                    trackChangeWithModule(modulesList[i], modulesList[i]+"_MN");

                    removeItem(modulesList[i], "Mod");
                    applyModuleAccordion();
                }
            }

            if(allQuestionList !== null) {        

                for(var i = 0; i < allQuestionList.length; i += 1) {

                    ques = allQuestionList[i];
                    module = $('#'+ques.moduleId).find('.questionsList');

                    if(ques.type === "MCQ") {

                        module.append(getMCQ(ques.questionId));
                        addChoices(ques.moduleId, ques.questionId);
                        removeItem( ques.questionId, "Q");   

                        trackChangeInQuestion( ques.questionId);
                        saveMCQButton(ques.moduleId,  ques.questionId);

                        quesId= $('#'+ques.questionId);                    
                        addMCQDetails();

                        applyQuestionAccordion();

                    } else if(ques.type === "NUM") {
                        module.append(getNum(ques.questionId));  

                        addNumChoices(ques.questionId);
                        removeItem( ques.questionId, "Q");   

                        trackChangeInQuestion( ques.questionId);
                        saveNumButton(ques.moduleId,  ques.questionId);

                        quesId= $('#'+ques.questionId);                    
                        addNUMDetails();          

                        applyQuestionAccordion();

                    } else if(ques.type === "SHORT") {
                        module.append(getShort(ques.questionId));            

                        removeItem( ques.questionId, "Q");   

                        trackChangeInQuestion( ques.questionId);
                        saveShortButton(ques.moduleId,  ques.questionId);

                        quesId= $('#'+ques.questionId);                    
                        addSHORTDetails();                          

                        applyQuestionAccordion();

                    }
                    if(ques !== "No Data Inserted") {
                        quesId.find('.question').val(ques.question);
                        quesId.find('.questionTxt').text(ques.question);           
                    }
                }

            }
        }

function addNewExperimentTab() {

    console.log('Inside Experiment Tab');
    
    if(sessionStorage.getItem("newExperiment") === "YES") {
        console.log("In YES")

        var eName = sessionStorage.getItem("newexperimentName");
        if(eName !== "") {
            newExperimentName = JSON.parse(eName);
        }

        var mList = sessionStorage.getItem("modulesList");
        if(mList !== null) {
            modulesList = JSON.parse(mList);        
        }
    //    console.log(mList);
    //    console.log('Module List : '+modulesList);

        var modList = sessionStorage.getItem("moduleNamesList");
        if(modList !== null) {
            moduleNamesList = JSON.parse(modList);
        }
    //    console.log(modList);
    //    console.log('Module List : '+moduleNamesList);

        var oList = sessionStorage.getItem("orderList");
        if(oList !== null) {        
            orderList = JSON.parse(oList);  
        }
      //  console.log(oList);
        //console.log('Order List : '+orderList);

        var allList = sessionStorage.getItem("allQuestionsList"); 
        if(allList !== null) {        
            allQuestionList = JSON.parse(allList);
        }
    /*    console.log(allList);
        console.log('Questions List : ');
        console.log(allQuestionList);*/


      addExperimentSpecs();
      workModuleBtn();
      addSessions();

    } else {

        console.log("In NO");
        sessionStorage.setItem("newExperiment", "YES");

        addExperimentSpecs();
        workModuleBtn();
    }
}

//addNewExperimentTab();

// Get particluar experiment which is specified before with experiment-id