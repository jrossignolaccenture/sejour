'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Application, currentApplication) {

        $scope.displayFamily = currentApplication.nature === 'naturalisation';
        $scope.identityInFrance = currentApplication.nature === 'sejour_tmp_etudiant';

        // TODO date temporaire pour gérer toutes les dates de l'écran de validation de naturalisation (demo only)
        $scope.tempDate = {date: $scope.getFormattedDate(moment())};
        
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.project = currentApplication.project;
        $scope.project.valid = currentApplication.project.validateOn ? true : null;
        $scope.project.detailMode = true;
    	
        $scope.application = currentApplication;
        
        // indicateur fumeux
        $scope.needResidencyCountry = currentApplication.type === 'premiere';
    	
		Application.getHistory(currentApplication.email).then(function(applicationsHistory) {
			$scope.history = applicationsHistory;
        });
        
        $scope.validate = function () {
    		Application.validate(currentApplication.id);
        }
        
        $scope.endValidation = function () {
        	$state.go('validation/list')
        }
    });
