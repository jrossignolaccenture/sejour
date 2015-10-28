'use strict';

angular.module('sejourApp')
    .controller('ProjectController', function ($scope, $state, $stateParams, File, Application, currentApplication) {
        $scope.resourceProofFile = {};
        $scope.inscriptionCertificateFile = {};
        
        $scope.datePickerOptions = {
			format: 'DD/MM/YYYY',
			minDate: 'moment', 
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        $scope.needComingDate = currentApplication.type !== 'renouvellement';
        $scope.needForm = currentApplication.type !== 'naturalisation';

        $scope.project = currentApplication.project;
        if($scope.project != null && $scope.project.comingDate != null) {
        	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
        }
        if($scope.project != null && $scope.project.trainingStart != null) {
        	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");
        }

        $scope.back = function () {
        	$state.go('address', $stateParams);
        };
        $scope.save = function () {
            $scope.project.comingDate = moment($scope.project.comingDateTxt, "DD/MM/YYYY").toDate();
            $scope.project.trainingStart = moment($scope.project.trainingStartTxt, "DD/MM/YYYY").toDate();
            currentApplication.project = $scope.project;
            Application.update(currentApplication).then(function() {
            	$state.go('summary', $stateParams);
            });
        };
        
    });
