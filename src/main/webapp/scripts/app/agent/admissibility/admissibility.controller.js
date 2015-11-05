'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Application, currentApplication) {

    	$scope.isAgent = true;
        var isNaturalization = currentApplication.type === 'naturalisation';
        $scope.withFrancisation = isNaturalization;
        $scope.displayFamily = isNaturalization;
        $scope.viewSuffix = isNaturalization ? '-naturalization' : '';

        //TODO Moyen plus générique à trouver pour l'ouverture et la validation par défaut des panels
        // par exemple => détection d'un changement par rapport à l'état précédent et précédente date de validation (du coup on n'a pas besoin de tester le type)
        $scope.identityOpened = true;
        $scope.familyOpened = true;
    	$scope.addressOpened = true;
    	$scope.projectOpened = true;
    	if(currentApplication.type === 'renouvellement') {
        	$scope.identityOpened = false;
    		$scope.identityValidated = true;
    		$scope.addressOpened = false;
    		$scope.addressValidated = true;
    	}
    	if(currentApplication.type === 'naturalisation') {
    		$scope.addressOpened = false;
    		$scope.addressValidated = true;
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
