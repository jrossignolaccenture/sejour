'use strict';

angular.module('sejourApp')
    .controller('ProjectController', function ($scope, $state, $stateParams, File, Application, currentApplication) {
        
        $scope.datePickerOptions = {
			format: 'DD/MM/YYYY',
			minDate: 'moment', 
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        $scope.needComingDate = currentApplication.type !== 'renouvellement';

        $scope.project = currentApplication.project;
        if($scope.project.comingDate) {
        	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
        }
        if($scope.project.trainingStart) {
        	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");
        }

        $scope.back = function () {
        	$state.go('address', $stateParams);
        };
        $scope.save = function () {
        	if($scope.project.comingDateTxt) {
        		$scope.project.comingDate = moment($scope.project.comingDateTxt, "DD/MM/YYYY").toDate();
        	}
        	if($scope.project.trainingStartTxt) {
        		$scope.project.trainingStart = moment($scope.project.trainingStartTxt, "DD/MM/YYYY").toDate();
        	}
        	
            Application.update(currentApplication).then(function() {
            	$state.go('summary', $stateParams);
            });
        };
        
    });
