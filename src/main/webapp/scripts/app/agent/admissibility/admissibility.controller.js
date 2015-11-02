'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Country, Application, currentApplication) {

    	//  !!!!! BEAUCOUP DE CODE A MUTUALISER AVEC VALIDATION.CONTROLLER.JS !!!!!
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
    	
        Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
    	
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = moment(currentApplication.identity.birthDate).format("DD/MM/YYYY");

    	$scope.address = currentApplication.address;
    	
        $scope.project = currentApplication.project;
        $scope.project.comingDateTxt = moment(currentApplication.project.comingDate).format("DD/MM/YYYY");
        $scope.project.trainingStartTxt = moment(currentApplication.project.trainingStart).format("DD/MM/YYYY");
    	
    	$scope.application = currentApplication;
        
        $scope.verify = function () {
            Application.admissibility(currentApplication.id).then(function(result) {
            	$state.go('admissibility/list');
            });
        }
    });
