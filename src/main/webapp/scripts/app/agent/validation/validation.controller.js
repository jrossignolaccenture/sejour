'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Application, currentApplication) {

    	$scope.getFormattedDate = function(date) {
    		return moment(date).format("DD/MM/YYYY");
    	}
        
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.project = currentApplication.project;
    	
        $scope.application = currentApplication;
    	
        // TODO A gérer proprement en back sans avoir besoin de tester le type
    	if($scope.application.type == 'premiere') {
    		$scope.identityValidationDate = $scope.application.rdvDate;
    	}
    	
    	// TODO Créer API qui renvoit l'historique simplifié => {id, type, nature, date début, date fin}
    	$scope.history = [];
		Application.getByStatus('validated', currentApplication.email).then(function(validatedApplications) {
			validatedApplications.forEach(function(validatedApplication) {
				if(validatedApplication.type == 'premiere') {
					$scope.identityValidationDate = validatedApplication.rdvDate;
				}
				$scope.history.push({
					startDate: $scope.getFormattedDate(moment(validatedApplication.project.trainingStart)),
					endDate: $scope.getFormattedDate(moment(validatedApplication.project.trainingStart).add(validatedApplication.project.trainingLength, 'M').subtract(1, 'd'))
				});
			});
        });
        
        $scope.validate = function () {
    		Application.validate(currentApplication.id).then(function(result) {
            	$state.go('validation/list')
            });
        }
    });
