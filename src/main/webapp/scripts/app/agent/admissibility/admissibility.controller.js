'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Application, currentApplication) {

    	$scope.isAgent = true;
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        $scope.viewSuffix = currentApplication.type === 'naturalisation' ? '-naturalization' : '';

    	$scope.identityOpened = true;
    	$scope.addressOpened = true;
    	$scope.projectOpened = true;
    	if($scope.isRenewal) {
        	$scope.identityOpened = false;
    		$scope.identityValidated = true;
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
