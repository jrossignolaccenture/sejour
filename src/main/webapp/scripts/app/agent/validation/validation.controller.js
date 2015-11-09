'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Application, currentApplication) {

        $scope.displayFamily = currentApplication.nature === 'naturalisation';
    	
    	//TODO Moyen plus générique à trouver pour l'ouverture et la validation par défaut des panels
        // par exemple => détection d'un changement par rapport à l'état précédent et précédente date de validation (du coup on n'a pas besoin de tester le type)
        var isRenewal = currentApplication.type === 'renouvellement';
        var isNat = currentApplication.nature === 'naturalisation';
        $scope.panel = {
        	identity: {
        		open: false, 
        		valid: true
        	},
	        family: {
	        	open: false,
	        	valid: true
	        },
	        project: {
	        	open: currentApplication.projectValidationDate == null,
	    	    valid: currentApplication.projectValidationDate != null
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
    	
        // TODO A gérer proprement en back sans avoir besoin de tester le type
    	if($scope.application.type == 'premiere') {
    		$scope.identityValidationDate = $scope.application.rdvDate;
    	}
    	
    	// TODO Créer API qui renvoit l'historique simplifié => {id, type, nature, date début, date fin}
    	$scope.history = [];
		Application.getByStatus(['validated'], currentApplication.email).then(function(validatedApplications) {
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
