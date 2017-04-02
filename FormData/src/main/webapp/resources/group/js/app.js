
var app = angular.module('app-start', []);

app.controller('app-controller', function($scope, $compile, $http){
	$scope.count = 0;	
	$scope.data = { "QuesAns" : []}
	$scope.choices = [];
	
	$scope.showdata = function() {
		var formData = new FormData();
		var files = [];
		for(var i in $scope.data.QuesAns){
			files.push((document.getElementById("uploadFile_"+i)).files);
		}
		// window.alert("Files "+files.length);
		// for(i=0; i<files.length; i++){
			// window.alert(files[i].length);
		// }
		formData.append("files",files);
		formData.append("data", $scope.data);
		$http.post("http://localhost:8080/FormData/uploadquestions",formData);
		window.alert(angular.toJson($scope.data));
	}
	
	$scope.deleteChoice = function(i,n){
		var choices = angular.element(document.getElementById("answer_"+i));
		choices.empty();
		delete $scope.data.QuesAns[i].choices[n];
		var keyNum = -1;
		for(var key in $scope.data.QuesAns[i].choices){
			keyNum = Number(key);
			if(keyNum > n){
				var temp = $scope.data.QuesAns[i].choices[key]
				$scope.data.QuesAns[i].choices[""+(--keyNum)] = temp;
				delete $scope.data.QuesAns[i].choices[key]
			}
			var childNode = $compile("<div id=\"choiceq"+i+"c"+(keyNum)+"\"><label>Please add choice #"+(keyNum)+" </label><br/>" +
									"<textarea ng-model=\"data.QuesAns["+i+"].choices["+(keyNum)+"]\"/><button ng-click=deleteChoice("+i+","+(keyNum)+")>Delete</button></div>")($scope);
			choices.append(childNode);
		}
		var childNode = $compile("<div id=\"addMoreChoiceButton"+i+"\"><button ng-click=addMoreChoices("+i+","+(keyNum)+")>Add More Choices</button></div>")($scope);
		choices.append(childNode);		
	}
	
	$scope.addMoreChoices = function(i, n){
		//window.alert("i : "+i);
		var container = angular.element(document.getElementById("answer_"+i));
		var button = angular.element(document.getElementById("addMoreChoiceButton"+i));
		button.remove();
		//window.alert(container);
		var childNode = $compile("<div id=\"choiceq"+i+"c"+(n+1)+"\"><label>Please add choice #"+(n+1)+" </label><br/>" +
									"<textarea ng-model=\"data.QuesAns["+i+"].choices["+(n+1)+"]\"/><button ng-click=deleteChoice("+i+","+(n+1)+")>Delete</button></div>" +
									"<div id=\"addMoreChoiceButton"+i+"\"><button ng-click=addMoreChoices("+i+","+(n+1)+")>Add More Choices</button></div>")($scope);
		container.append(childNode)
		
	}
	
	$scope.enterAnswer = function(choice,i){
		var container = angular.element(document.getElementById("answer_"+i));
		container.empty();
		delete $scope.data.QuesAns[i].answer;
		delete $scope.data.QuesAns[i].choices;		
		if(choice == "text"){
			var childNode = $compile("<label>Please enter the corresponding answer here</label><br/>" +
									"<textarea id=\"answer"+i+"\" ng-model=\"data.QuesAns["+i+"].answer\"/>" +
									"<br/>")($scope);
			container.append(childNode)
			
		}else if(choice == "multiple-choice"){
			var childNode = $compile("<div id=\"choiceq"+i+"c1\"><label>Please add choice #1 </label><br/>" +
									"<textarea ng-model=\"data.QuesAns["+i+"].choices["+(1)+"]\"/><button ng-click=deleteChoice("+i+",1)>Delete</button></div>" +
									"<div id=\"addMoreChoiceButton"+i+"\"><button ng-click=addMoreChoices("+i+",1)>Add More Choices</button></div>")($scope);
			container.append(childNode);
			//window.alert(container);
			
		}
	}
	
});

app.directive("addMore", function($compile) {
	return {
		restrict : 'AE',
		link : function(scope, element) {
			element.bind("click", function(e){
			scope.count++;
			var container = angular.element(document.getElementById("q&a"));
			var childNode = $compile("<label>Please enter the question #"+(scope.count+1)+" here</label><br/>" +
									"<textarea id=\"question"+scope.count+"\" ng-model=\"data.QuesAns["+scope.count+"].question\"/> <br/>"+
									"Choose the type of answer:" +
									"<input type=\"radio\" ng-model=\"data.QuesAns["+scope.count+"].answertype\" ng-change=\"enterAnswer(data.QuesAns["+scope.count+"].answertype,"+scope.count+")\" value=\"multiple-choice\">Multiple Choice answer" +
									"<input type=\"radio\" ng-model=\"data.QuesAns["+scope.count+"].answertype\" ng-change=\"enterAnswer(data.QuesAns["+scope.count+"].answertype,"+scope.count+")\" value=\"text\">Text answer " +
									"<br/>" +
									"<div id=\"answer_"+scope.count+"\"></div><br/>" +
									"<div id=\"file_"+scope.count+"\">" +
									"<input type=\"file\" name=\"uploadFile\" id=\"uploadFile_"+scope.count+"\" multiple><br/><br/>" +
									"</div>" +
									"<hr/>")(scope);
			container.append(childNode);
			});
		}
			
	};
})