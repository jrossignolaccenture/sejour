'use strict';

angular.module('sejourApp')
    .controller('FamilyController', function ($scope, $state, $stateParams, $timeout, Application, currentApplication) {

    	$scope.parents = currentApplication.identity.family['parents'];
    	if(!$scope.parents || $scope.parents.length == 0) {
    		$scope.parents = [{identity: {documents: []}, address: {}}, {identity: {documents: []}, address: {}}];
    	}
    	
    	$scope.updatePanelOpen = function (view) {
    		$scope.panelOpen = $scope.panelOpen===view ? '' : view;
    		$timeout(function() {
    			$('html, body').animate({ scrollTop: $("#Panel"+view).offset().top }, "slow");
    	    }, 50);
    	}

        $scope.back = function () {
        	$state.go('identity', $stateParams);
        };
        
        $scope.save = function () {
        	currentApplication.identity.family['parents'] = $scope.parents;
        	
        	Application.update(currentApplication).then(function() {
            	$state.go('address', $stateParams);
            });
        };
        
    });
