'use strict';

angular.module('sejourApp')
    .controller('SummaryController', function ($scope, $state, $stateParams, $timeout, currentApplication) {
    	
    	$scope.isInDraftMode = currentApplication.statut === 'draft';
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        var isNaturalization = currentApplication.type === 'naturalisation';
        $scope.displayFamily = isNaturalization;
        $scope.viewSuffix = isNaturalization ? '-naturalization' : '';
        
        $scope.identity = currentApplication.identity;
        $scope.address = currentApplication.address;
        $scope.project = currentApplication.project;
    	
    	$scope.updatePanelOpen = function (view) {
    		$scope.panelOpen = $scope.panelOpen===view ? '' : view;
    		$timeout(function() {
    			$('html, body').animate({ scrollTop: $("#Panel"+view).offset().top }, "slow");
    	    }, 50);
    	}

        $scope.back = function () {
        	var stateToGo = currentApplication.statut === 'draft' ? 'project' : 'account/application';
        	$state.go(stateToGo, $stateParams);
        };
        $scope.next = function () {
            $state.go('payment', $stateParams);
        };
        
    });
