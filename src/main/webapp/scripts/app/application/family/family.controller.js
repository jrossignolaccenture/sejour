'use strict';

angular.module('sejourApp')
    .controller('FamilyController', function ($scope, $state, $stateParams, Application, currentApplication) {

    	$scope.panelOpen = 0;

    	$scope.parents = currentApplication.identity.family['parents'];
    	if(!$scope.parents || $scope.parents.length == 0) {
    		$scope.parents = [{identity: {documents: []}, address: {}}, {identity: {documents: []}, address: {}}];
    	}
    	
    	$scope.updatePanelOpen = function (index) {
    		$scope.panelOpen = $scope.panelOpen===index ? '' : index;
    		if($scope.panelOpen === 0) {
    			$('html, body').animate({ scrollTop: $("#parentHeader"+index).offset().top }, "slow");
    		} else if($scope.panelOpen > 0) {
    			$('html, body').animate({ scrollTop: $("#parentContent"+(index-1)).offset().top }, "slow");
    		}
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
