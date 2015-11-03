'use strict';

angular.module('sejourApp')
    .controller('SummaryController', function ($scope, $state, $stateParams, currentApplication) {
    	
    	$scope.isInDraftMode = currentApplication.statut === 'draft';
        
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        $scope.viewSuffix = currentApplication.type === 'naturalisation' ? '-naturalization' : '';
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
        
        $scope.address = currentApplication.address;
        
        $scope.project = currentApplication.project;
    	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
    	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");

        $scope.back = function () {
        	var stateToGo = currentApplication.statut === 'draft' ? 'project' : 'account/application';
        	$state.go(stateToGo, $stateParams);
        };
        $scope.next = function () {
            $state.go('payment', $stateParams);
        };
        
    });
