'use strict';

angular.module('sejourApp')
    .controller('ProjectController', function ($scope, $state, $stateParams, File, Application, currentApplication) {
        
        $scope.needComingDate = currentApplication.type !== 'renouvellement';

        $scope.project = currentApplication.project;

        $scope.back = function () {
        	$state.go('address', $stateParams);
        };
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	$state.go('summary', $stateParams);
            });
        };
        
    });
