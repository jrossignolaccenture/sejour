'use strict';

angular.module('sejourApp')
    .controller('DocumentsController', function ($scope, $state, Application, currentApplication) {
    	
    	$scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
    	$scope.application = currentApplication;
    	
    	$scope.docToValidate = 0;
    	
    	$scope.panels = {};
    	currentApplication.identity.documents.forEach(function(document) {
    		if(!document.validation) {
    			$scope.panels[document.type] = {documentType: document.type, documents: currentApplication.identity.documents, opened: true};
    			$scope.docToValidate++;
    		}
    	});
    	
    	Object.keys(currentApplication.identity.family).forEach(function(personType) {
    		var person = currentApplication.identity.family[personType];
	    	for(var i=0; i<person.length; i++) {
	    		person[i].identity.documents.forEach(function(document) {
	        		if(!document.validation) {
	        			$scope.panels[personType + i] = {documentType: document.type, documents: person[i].identity.documents, personType: personType, index: i+1, opened: true};
	        			$scope.docToValidate++;
	        		}
	        	});
	    	}
    	});
    	
    	$scope.updatePanel = function(panel, validated) {
    		panel.validated = validated;
    		if(validated) {
    			panel.opened = false;
    			$scope.docToValidate--;
    		} else {
    			$scope.docToValidate++;
    		}
    	}
        
    	$scope.certify = function() {
    		Application.identify(currentApplication.id, 'documents').then(function(result){
            	$state.go('identification/list')
            });
    	}
    });
