'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Application, currentApplication) {

    	// TODO Voir comment faire autrement que ce isAgent
    	$scope.isAgent = true;
    	
        $scope.panel = {
        	identity: {
        		open: !currentApplication.identity.admissible, 
        		valid: currentApplication.identity.admissible
        	},
	        family: {
	        	open: !currentApplication.identity.familyAdmissible,
	        	valid: currentApplication.identity.familyAdmissible
	        },
	        address: {
	        	open: !currentApplication.address.admissible,
	    	    valid: currentApplication.address.admissible
	        },
	        project: {
	        	open: !currentApplication.project.admissible,
	        	valid: currentApplication.project.admissible
	        }
        }
    	
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
    	$scope.address = currentApplication.address;
        $scope.project = currentApplication.project;
    	
    	$scope.application = currentApplication;
        
        $scope.verify = function () {
            Application.admissibility(currentApplication.id).then(function(result) {
            	$state.go('admissibility/list');
            });
        }
    });
