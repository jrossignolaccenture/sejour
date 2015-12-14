'use strict';

angular.module('sejourApp')
    .controller('SummaryController', function ($scope, $state, $stateParams, $timeout, currentApplication) {
    	
    	$scope.isInDraftMode = currentApplication.statut === 'draft';
        $scope.displayFamily = currentApplication.nature === 'naturalisation';
        
        // indicateur fumeux
        $scope.needResidencyCountry = currentApplication.type === 'premiere';
        
        $scope.nature = currentApplication.nature;
        
        $scope.identity = currentApplication.identity;
        $scope.address = currentApplication.address;
        $scope.project = currentApplication.project;

        $scope.back = function () {
        	window.history.back();
        };
        
        $scope.next = function () {
            $state.go('payment', $stateParams);
        };
        
    });
