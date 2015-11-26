'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Application, currentApplication) {

    	// TODO Voir comment faire autrement que ce isAgent
    	$scope.isAgent = true;
    	
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.identity.valid = currentApplication.identity.validateOn ? true : null;
    	$scope.address = currentApplication.address;
    	$scope.address.valid = currentApplication.address.validateOn ? true : null;
        $scope.project = currentApplication.project;
        
    	$scope.application = currentApplication;
        
        $scope.verify = function () {
            Application.admissibility(currentApplication.id).then(function(result) {
            	$state.go('admissibility/list');
            });
        }
    });
