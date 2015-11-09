'use strict';

angular.module('sejourApp')
    .controller('InterviewController', function ($scope, $state, Application, currentApplication) {
    	
    	$scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
    	$scope.application = currentApplication;
    	
    	$scope.save = function() {
    		Application.interview(currentApplication.id, $scope.application.rdvReport).then(function(result){
            	$state.go('identification/list')
            });
    	}
    });
