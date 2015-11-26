'use strict';

angular.module('sejourApp')
    .controller('SummaryController', function ($scope, $state, $stateParams, $timeout, currentApplication) {
    	
    	$scope.isInDraftMode = currentApplication.statut === 'draft';
        $scope.displayFamily = currentApplication.nature === 'naturalisation';
        
        $scope.nature = currentApplication.nature;
        
        $scope.identity = currentApplication.identity;
        $scope.address = currentApplication.address;
        $scope.project = currentApplication.project;

        $scope.back = function () {
        	var stateToGo = currentApplication.statut === 'draft' ? 'project' : 'account/application';
        	$state.go(stateToGo, $stateParams);
        };
        $scope.next = function () {
            $state.go('payment', $stateParams);
        };
        
    });
