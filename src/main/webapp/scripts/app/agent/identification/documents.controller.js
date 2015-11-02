'use strict';

angular.module('sejourApp')
    .controller('DocumentsController', function ($scope, $state, Application, currentApplication) {
    	
    	$scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
    	$scope.application = currentApplication;
    	
    	$scope.passportOpened = true;
    	$scope.birthActOpened = true;
        
    	$scope.certify = function() {
    		Application.identify(currentApplication.id, 'documents').then(function(result){
            	$state.go('identification/list')
            });
    	}
    });
