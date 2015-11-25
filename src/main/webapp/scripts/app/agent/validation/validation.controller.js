'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Application, currentApplication) {

        $scope.displayFamily = currentApplication.nature === 'naturalisation';
    	
        $scope.panel = {
        	identity: {
        		open: currentApplication.identity.validateOn == null, 
        		valid: currentApplication.identity.validateOn != null
        	},
	        family: {
	        	open: currentApplication.identity.familyValidateOn == null,
	        	valid: currentApplication.identity.familyValidateOn != null
	        },
	        project: {
	        	open: currentApplication.project.validateOn == null,
	    	    valid: currentApplication.project.validateOn != null ? true : null
	        }
        }

    	$scope.getFormattedDate = function(date) {
    		return moment(date).format("DD/MM/YYYY");
    	}

        // TODO date temporaire pour gérer toutes les dates de l'écran de validation de naturalisation (demo only)
        $scope.tempDate = {date: $scope.getFormattedDate(moment())};
        
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.project = currentApplication.project;
        $scope.project.detailMode = true;
    	
        $scope.application = currentApplication;
    	
    	// TODO Créer API qui renvoit l'historique simplifié => {id, type, nature, date début, date fin}
    	$scope.history = [];
		Application.getByStatus(['validated'], currentApplication.email).then(function(validatedApplications) {
			validatedApplications.forEach(function(validatedApplication) {
				console.log(validatedApplication.project.trainingStart);
				$scope.history.push({
					nature: validatedApplication.nature,
					startDate: $scope.getFormattedDate(moment(validatedApplication.project.trainingStart)),
					endDate: $scope.getFormattedDate(moment(validatedApplication.project.trainingStart).add(1, 'Y').subtract(1, 'd'))
				});
			});
        });
        
        $scope.validate = function () {
    		Application.validate(currentApplication.id).then(function(result) {
            	$state.go('validation/list')
            });
        }
    });
