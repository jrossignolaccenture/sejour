'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Application, currentApplication) {

        $scope.isRenewal = currentApplication.type === 'renouvellement';
        $scope.viewSuffix = currentApplication.type === 'naturalisation' ? '-naturalization' : '';

    	$scope.getFormattedDate = function(date) {
    		return moment(date).format("DD/MM/YYYY");
    	}
        
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = $scope.getFormattedDate(currentApplication.identity.birthDate);
    	
        $scope.project = currentApplication.project;
        $scope.project.comingDateTxt = $scope.getFormattedDate(currentApplication.project.comingDate);
        $scope.project.trainingStartTxt = $scope.getFormattedDate(currentApplication.project.trainingStart);
    	
        $scope.application = currentApplication;
    	
    	$scope.applicationArchived = {};
    	if($scope.application.type == 'premiere') {
    		$scope.identityValidationDate = $scope.application.rdvDate;
    	} else {
    		Application.getByStatus('validated', currentApplication.email).then(function(result) {
            	$scope.applicationArchived = result[0];
            	$scope.identityValidationDate = $scope.applicationArchived.rdvDate;
            	$scope.historyStartDate = $scope.getFormattedDate(moment($scope.applicationArchived.project.trainingStart));
            	$scope.historyEndDate = $scope.getFormattedDate(moment($scope.applicationArchived.project.trainingStart).add($scope.applicationArchived.project.trainingLength, 'M').subtract(1, 'd'));
            });
    	}
        
        $scope.validate = function () {
    		Application.validate(currentApplication.id).then(function(result) {
            	$state.go('validation/list')
            });
        }
    });
