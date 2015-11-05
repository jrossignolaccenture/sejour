'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Application, currentApplication) {

    	$scope.isAgent = true;
    	
        $scope.displayFamily = currentApplication.nature === 'naturalisation';
        
        //TODO Moyen plus générique à trouver pour l'ouverture et la validation par défaut des panels
        // par exemple => détection d'un changement par rapport à l'état précédent et précédente date de validation (du coup on n'a pas besoin de tester le type)
        var isRenewal = currentApplication.type === 'renouvellement';
        var isNat = currentApplication.nature === 'naturalisation';
        $scope.panel = {
        	identity: {
        		open: isRenewal ? false: true, 
        		valid: isRenewal ? true : false
        	},
	        family: {
	        	open: true,
	        	valid: false
	        },
	        address: {
	        	open: isRenewal || isNat ? false : true,
	    	    valid: isRenewal || isNat ? true : false
	        },
	        project: {
	        	open: true,
	        	valid: false
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
