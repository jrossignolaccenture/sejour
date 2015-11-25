'use strict';

angular.module('sejourApp')
    .controller('ProjectController', function ($scope, $state, $stateParams, File, Application, currentApplication) {
        
    	$scope.nature = currentApplication.nature;
    	
        $scope.needComingDate = currentApplication.type === 'premiere';

        $scope.project = currentApplication.project;

        $scope.resetFrancisation = function() {
        	$scope.project.lastNameFrancise = null;
        	$scope.project.firstNameFrancise = null;
    	};
        
        $scope.back = function () {
        	$state.go('address', $stateParams);
        };
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	$state.go('summary', $stateParams);
            });
        };
        
    });
